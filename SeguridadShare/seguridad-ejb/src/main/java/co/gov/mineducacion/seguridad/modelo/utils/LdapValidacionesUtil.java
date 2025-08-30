package co.gov.mineducacion.seguridad.modelo.utils;


import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.enums.CamposLdap;
import co.gov.mineducacion.seguridad.modelo.enums.TipoValidacion;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

public class LdapValidacionesUtil {

    private static final Logger logger = Logger.getLogger(LdapValidacionesUtil.class);

    private static final Pattern SOLO_NUMERO_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    private static final Pattern ALFABETICO_PATTERN = Pattern.compile("^[ A-Za-z]+$");

    private static final Pattern ALFANUMERICO_PATTERN = Pattern.compile("^[ a-zA-Z0-9]*$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); // NOSONAR


    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!#%*\\-_?&])[A-Za-z\\d$@$#!%*\\-_?&]{8,}$"); // NOSONAR

    private static Properties properties;

    private LdapValidacionesUtil() {

    }

    public static void inizializar(String rutaArchivo) throws SIA3Exception {
        try {
            logger.info("Iniciaciondo clase para validaciones de campos del ldap");
            InputStream inputStream = new FileInputStream(rutaArchivo);
            properties = new Properties();
            logger.info("rutaArchivo is: " + rutaArchivo);
            armarInizializar(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            logger.error("No se encuentra el archivo de propiedades");
        } catch (IOException e) {
            throw new SIA3Exception(e.getMessage(), e);
        }
        logger.info("Read Properties." + properties);
    }

    private static void armarInizializar(InputStream inputStream) throws SIA3Exception {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Ha ocurrido un erro leyendo Properties, " + e.getMessage());
            throw new SIA3Exception(e.getMessage(), e);
        }
    }

    public static void validarUsuario(UsuarioLdap usuarioLdap) throws SeguridadException {
        logger.info("Inicia proceso validar usuario Ldap utils: " + new Gson().toJson(usuarioLdap));
        if(properties != null) {
            Map<String, String> usuarioMap = usuarioLdap.toMap();
            for (Object campo : properties.keySet()) {
                CamposLdap campoLdap = CamposLdap.fromString((String) campo);
                if (campoLdap != null) {
                    if (!campoLdap.equals(CamposLdap.PASSWORD)){
                        String valor = usuarioMap.getOrDefault(campo, null);
                        verificarCampo(campoLdap, valor);
                    }
                } else {
                    logger.info("Campo no han sido configuradas correctamente." + campo);
                }
            }
        } else {
            logger.info("Propiedades no han sido configuradas correctamente." + properties);
        }

    }

    public static boolean isOptional(String validacionesStr, String valor){
        return ((valor == null || valor.equals("") || valor.contains("null")) && !validacionesStr.contains(TipoValidacion.REQUIRED.getDescripcion()));
    }

    public static void verificarCampo(CamposLdap campo, String valor) throws SeguridadException {
        if(properties != null) {
            String validacionesStr = properties.getProperty(campo.getId());
            String[] validaciones = validacionesStr.split("\\|");
            for (String validacion : validaciones) {
                String[] separacionValidacion = validacion.split("=");
                TipoValidacion tipoValidacion = getTipoValidacion(validacion);
                if (tipoValidacion != null) {
                    if (!isOptional(validacionesStr, valor) && esInvalido(tipoValidacion, valor, separacionValidacion)){
                        logger.error("Error campo: "+ campo +" validacion: " + tipoValidacion + " valor: " + valor);
                        throw new SeguridadException(tipoValidacion.getIdMensaje());
                    }
                }else {
                    logger.info("Validacion no han sido configuradas correctamente." + validacion);
                }
            }
        }else {
            logger.info("Propiedades no han sido configuradas correctamente." + properties);
        }
    }

    public static boolean validarMaximoCampo(CamposLdap campo, String valor,Integer valorMaximo){
        try {
            String validacionesStr = properties.getProperty(campo.getId());
            List<String> validaciones = Arrays.asList(validacionesStr.split("\\|"));
            String response = validaciones.stream()
                    .filter(val -> val.contains(TipoValidacion.MAX_LENGTH.getDescripcion()))
                    .findAny()
                    .orElse(null);
            if (response != null){
                String[] separacion = response.split("=");
                valorMaximo = Integer.parseInt(separacion[1].trim());
            }
        } catch (Exception e) {
            logger.info("Error al obtener propiedad: " + new Gson().toJson(e));
        }
        if (valorMaximo != null){
            return valor.length() >= valorMaximo;
        }
        logger.info("Error al obtener propiedad");
        return true;
    }

    private static boolean esInvalido(TipoValidacion tipoValidacion, String valor, String[] separacionValidacion) {
        String valorSeparacion = getValorValidacion(separacionValidacion);
        switch (tipoValidacion) {
            case REQUIRED:
                return esInvalidoParaRequerido(valor);
            case NUMERIC:
                return !esNumerico(valor);
            case ALPHANUMERIC:
                return !esAlfanumerico(valor);
            case ALPHABETIC:
                return !esAlfabetico(valor);
            case EMAIL:
                return !esEmail(valor);
            case PASSWORD:
                return !esPassword(valor);
            case MAX:
                return Objects.nonNull(valorSeparacion) && esMayor(valor, valorSeparacion);
            case MIN:
                return Objects.nonNull(valorSeparacion) && esMinimo(valor, valorSeparacion);
            case MAX_LENGTH:
                return Objects.nonNull(valorSeparacion) && esMayorLength(valor, valorSeparacion);
            case MIN_LENGTH:
                return Objects.nonNull(valorSeparacion) && esMinimoLength(valor, valorSeparacion);
            case PATTERN:
                return Objects.nonNull(valorSeparacion) && esValidoConPattern(valor, valorSeparacion);
            default:
                return false;
        }
    }

    private static String getValorValidacion(String[] separacionValidacion) {
        if (separacionValidacion.length > 1) {
            return separacionValidacion[1];
        }
        return null;
    }

    private static boolean esInvalidoParaRequerido(String valor) {
        valor = valor.replace("null","");
        return Objects.isNull(valor) || "".equals(valor.trim());
    }

    private static boolean esEmail(String valor) {
        if (valor == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(valor).matches();
    }

    private static boolean esPassword(String valor) {
        if (valor == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(valor).matches();
    }

    private static boolean esAlfanumerico(String valor) {
        if (valor == null) {
            return false;
        }
        return ALFANUMERICO_PATTERN.matcher(valor).matches();
    }

    private static boolean esAlfabetico(String valor) {
        if (valor == null) {
            return false;
        }
        return ALFABETICO_PATTERN.matcher(valor).matches();
    }

    private static boolean esNumerico(String valor) {
        if (valor == null) {
            return false;
        }
        return SOLO_NUMERO_PATTERN.matcher(valor).matches();
    }

    private static boolean esMayor(String valor, String valorMaximo) {
        if (esNumerico(valor) && esNumerico(valorMaximo)) {
            return Integer.parseInt(valor) > Integer.parseInt(valorMaximo);
        }
        return false;
    }

    private static boolean esMayorLength(String valor, String valorMaximo) {
        if (Objects.nonNull(valor) && esNumerico(valorMaximo)) {
            return valor.length() > Integer.parseInt(valorMaximo);
        }
        return false;
    }

    private static boolean esMinimo(String valor, String valorMinimo) {
        if (esNumerico(valor) && esNumerico(valorMinimo)) {
            return Integer.parseInt(valor) < Integer.parseInt(valorMinimo);
        }
        return false;
    }

    private static boolean esMinimoLength(String valor, String valorMinimo) {
        if (Objects.nonNull(valor) && esNumerico(valorMinimo)) {
            return valor.length() < Integer.parseInt(valorMinimo);
        }
        return false;
    }

    private static boolean esValidoConPattern(String valor, String pattern) {
        if (valor == null) {
            return false;
        }
        return Pattern.compile(pattern).matcher(valor).matches();
    }

    private static TipoValidacion getTipoValidacion(String validacion) {
        if (validacion.contains("=")){
            return TipoValidacion.fromString(validacion.split("=")[0]);
        }
        return TipoValidacion.fromString(validacion);
    }


}
