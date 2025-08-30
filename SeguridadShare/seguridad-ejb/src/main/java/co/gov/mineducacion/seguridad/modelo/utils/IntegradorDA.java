package co.gov.mineducacion.seguridad.modelo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import co.gov.mineducacion.seguridad.modelo.dtos.externos.FiltrosUsuarioDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.externos.ServicioInstregacionDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.externos.UsuarioResponseDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;

import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.LLAVE_INTEGRACION;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.URL_ACTUALIZAR_USUARIO;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.URL_BASE_INTEGRACION;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.URL_CONSULTAR_TODOS_LOS_USUARIOS;

/**
 * Clase que implementa el servicio de comunicación con el directorio
 * activo a través del uso de una DLL con código nativo.
 *
 * @author Asesoftware - Javier Estévez
 */
public class IntegradorDA {

    public static final String PROPIEDADES = " propiedades: ";

    private IntegradorDA(){/* Recomendacion por sonar */}
    private static final Logger logger = Logger.getLogger(IntegradorDA.class);



    /**
     * Se auténtica en el directorio activo con el usuario y la clave recibidos por
     * parámetro, si la autenticación es exitosa se retorna verdadero de lo contrario
     * se retorna falso
     *
     * @param usrName  login del usuario
     * @param password contraseña del usuario
     * @return boolean
     */
    public static boolean validarUsuario(String usrName, String password, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para validar el usuario: " + usrName + PROPIEDADES + propiedades);

        String url = propiedades.getProperty(URL_BASE_INTEGRACION) + "validarusuario";
        try {
            ServicioInstregacionDTO response = ServicioHTTP.enviarPublicacionPost(url, new Gson().toJson(new UsuarioResponseDTO(usrName, password)), ServicioInstregacionDTO.class, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if (response.getError()) {
                throw new SIA3Exception(response.getMensaje());
            }
            return response.getCodigoRespuesta().equals(0L) && Objects.equals(response.getData(), "Exitoso");
        } catch (SIA3Exception e) {
            throw new SeguridadException(e.getMessage());
        }
    }

    /**
     * Crea en el directorio activo el usuario que se recibe por parámetro
     *
     * @param usuario
     */
    public static void crearUsuario(UsuarioLdap usuario, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para crear usuario: " + new Gson().toJson(usuario) + PROPIEDADES + propiedades);
        try {
            String url = propiedades.getProperty(URL_BASE_INTEGRACION) + "crearUsuario";
            UsuarioResponseDTO body = new UsuarioResponseDTO(usuario);
            ServicioInstregacionDTO response = ServicioHTTP.enviarPublicacionPost(url, new Gson().toJson(body), ServicioInstregacionDTO.class, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if (response.getError() || !response.getCodigoRespuesta().equals(0L)) {
                throw new SIA3Exception(response.getMensaje());
            }
            logger.info(response.getMensaje());
        } catch (SIA3Exception e) {
            throw new SeguridadException(e.getMessage());
        }
    }

    /**
     * Crea en el directorio activo el usuario que se recibe por parámetro
     *
     * @param usuario
     */
    public static void modificarUsuario(UsuarioLdap usuario, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para actualizar usuario: " + new Gson().toJson(usuario) + PROPIEDADES + propiedades);
        try {
            String url = propiedades.getProperty(URL_BASE_INTEGRACION) + URL_ACTUALIZAR_USUARIO;
            ServicioInstregacionDTO response = ServicioHTTP.enviarPublicacionPut(url, new Gson().toJson(new UsuarioResponseDTO(usuario)), ServicioInstregacionDTO.class, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            logger.info("resultado peticion de actualizacion integracion: " + new Gson().toJson(response));
            if (response.getError() || !response.getCodigoRespuesta().equals(0L)) {
                throw new SIA3Exception(response.getMensaje());
            }
            logger.info("Finaliza proceso modificar usuario integradorDA: respuesta externo: " + response.getMensaje());
        } catch (SIA3Exception e) {
            logger.error("Error modificando usuario integradorDA: " + e.getMessage());
            throw new SeguridadException(e.getMessage());
        }
    }

    /**
     * Busca en el directorio activo el usuario cuyo nombre de usuario o login
     * se recbie por parámetro
     *
     * @param loginUsuario identificador del usuario
     * @param entryDN      si contiene 'Usuarios Externos' la busqueda se realiza sobre la rama de Usuario Externos, de
     *                     lo contrario se realiza sobre toddo el arbol
     * @return
     */
    public static UsuarioLdap buscarUsuarioPorLogin(String loginUsuario, String entryDN, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para consultar usuario por login: " + loginUsuario + PROPIEDADES + propiedades);
        String url = propiedades.getProperty(URL_BASE_INTEGRACION) + "buscarusuario/" + loginUsuario;
        try {
            ServicioInstregacionDTO response = ServicioHTTP.enviarPeticionGet(url, ServicioInstregacionDTO.class, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if("UsuarioNoExiste".equals(response.getData())){
                return null;
            }
            if (response.getError() || !response.getCodigoRespuesta().equals(0L) ) {
                throw new SIA3Exception(response.getMensaje());
            }
            UsuarioResponseDTO modeloBase = new Gson().fromJson(new Gson().toJson(response.getData()), UsuarioResponseDTO.class);
            return contruirUsuarioSIA(modeloBase);
        } catch (SIA3Exception e) {
            throw new SeguridadException("El usuario no existe en él sistema o no está activo");
        }
    }

    /**
     * Modifica el password del usuario cuyo login se recibe por parámetro.
     *
     * @param logonName     identificador del usuario
     * @param nuevoPassword nueva clave del usuario
     * @throws SeguridadException
     */
    public static void modificarPassword(String logonName, String nuevoPassword, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para modificar la constraseña usuario: " + logonName + PROPIEDADES + propiedades);
        String url = propiedades.getProperty(URL_BASE_INTEGRACION) + "editarpassword";
        try {
            ServicioInstregacionDTO response = ServicioHTTP.enviarPublicacionPut(url, new Gson().toJson(new UsuarioResponseDTO(logonName, nuevoPassword)), ServicioInstregacionDTO.class, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if (response.getError() || !response.getCodigoRespuesta().equals(0L)) {
                throw new SIA3Exception(response.getMensaje());
            }
            logger.info(response.getMensaje());
        } catch (SIA3Exception e) {
            throw new SeguridadException(e.getMessage());
        }
    }

    /**
     * Retorna los usuarios encontrados en el directorio activo que cumplan con todos los
     * filtros recibidos por parámetro. Si los filtros son nulos o cadenas vacías no se tienen
     * en cuenta.
     *
     * @param logonName identificador (login) del usuario
     * @param nombres
     * @param apellidos
     * @param email
     * @throws SeguridadException
     */
    public static List<UsuarioLdap> listarUsuariosPorFiltros(String logonName, String nombres,
                                                             String apellidos, String email, String entryDN, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para listar usuarios con parametros logonName:" + logonName + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email + "entryDn: " + entryDN);
        try {
            String url = propiedades.getProperty(URL_BASE_INTEGRACION) + URL_CONSULTAR_TODOS_LOS_USUARIOS;
            String filtros = new Gson().toJson(new FiltrosUsuarioDTO(nombres, apellidos, email, logonName));
            ServicioInstregacionDTO response = ServicioHTTP.enviarPeticionGetConBody(url, ServicioInstregacionDTO.class, filtros, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if (response.getError() || !response.getCodigoRespuesta().equals(0L) && !response.getCodigoRespuesta().equals(4L)) {
                throw new SIA3Exception(response.getMensaje());
            }
            if (response.getCodigoRespuesta() == 4L) {
                return new ArrayList<>();
            }
            return construirListaUsuarioSIA(response.getData());
        } catch (SIA3Exception e) {
            throw new SeguridadException(e.getMessage());
        }
    }

    /**
     * Retorna los usuarios encontrados en la rama de Usuarios Excternos que se encuentren activos o inactivos
     *
     * @param activos verdadero retorna los activos, falso los inactivos,  null toddos
     * @return
     * @throws SeguridadException
     */
    public static List<UsuarioLdap> listarUsuariosActivosInactivos(Boolean activos, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para listar usuarios por estado: " + activos + PROPIEDADES + propiedades);
        try {
            String url = propiedades.getProperty(URL_BASE_INTEGRACION) + URL_CONSULTAR_TODOS_LOS_USUARIOS;
            String filtro = new Gson().toJson(new FiltrosUsuarioDTO().constructorFiltroEstado(activos));
            ServicioInstregacionDTO response = ServicioHTTP.enviarPeticionGetConBody(url, ServicioInstregacionDTO.class, filtro, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if (response.getError() || !response.getCodigoRespuesta().equals(0L)) {
                throw new SIA3Exception(response.getMensaje());
            }
            return construirListaUsuarioSIA(response.getData());
        } catch (SIA3Exception e) {
            throw new SeguridadException(e.getMessage());
        }
    }

    /**
     * Activa el usuario cuyo login se recibe por parametro
     *
     * @return
     * @throws SeguridadException
     */
    public static void activarUsuario(String login, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para activar usuario: " + login + PROPIEDADES + propiedades);
        try {
            String url = propiedades.getProperty(URL_BASE_INTEGRACION) + URL_ACTUALIZAR_USUARIO;
            ServicioInstregacionDTO response = ServicioHTTP.enviarPublicacionPut(url,
                    new Gson().toJson(new UsuarioResponseDTO(login, true)), ServicioInstregacionDTO.class, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if (response.getError() || !response.getCodigoRespuesta().equals(0L)) {
                throw new SIA3Exception(response.getMensaje());
            }
            logger.info(response.getMensaje());
        } catch (SIA3Exception e) {
            throw new SeguridadException(e.getMessage());
        }
    }

    /**
     * inactiva el usuario cuyo login se recibe por parametro
     *
     * @return
     * @throws SeguridadException
     */
    public static void inactivarUsuario(String login, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para inactivar usuario: " + login + PROPIEDADES + propiedades);
        try {
            String url = propiedades.getProperty(URL_BASE_INTEGRACION) + URL_ACTUALIZAR_USUARIO;
            ServicioInstregacionDTO response = ServicioHTTP.enviarPublicacionPut(url,
                    new Gson().toJson(new UsuarioResponseDTO(login, false)), ServicioInstregacionDTO.class, consultarLLave(propiedades.getProperty(LLAVE_INTEGRACION)));
            if (response.getError() || !response.getCodigoRespuesta().equals(0L)) {
                throw new SIA3Exception(response.getMensaje());
            }
            logger.info(response.getMensaje());
        } catch (SIA3Exception e) {
            throw new SeguridadException(e.getMessage());
        }
    }

    private static UsuarioLdap contruirUsuarioSIA(UsuarioResponseDTO usuarioResponseDTO) {
        UsuarioLdap resultado = new UsuarioLdap();
        resultado.setDescription(usuarioResponseDTO.getDescription());
        resultado.setDisplayName(usuarioResponseDTO.getDisplayName());
        resultado.setDistinguishedName(usuarioResponseDTO.getDistinguishedName());
        resultado.setGivenName(usuarioResponseDTO.getGivenName());
        resultado.setMail(usuarioResponseDTO.getMail());
        resultado.setName(usuarioResponseDTO.getName());
        resultado.setsAMAccountName(usuarioResponseDTO.getSamAccountName());
        resultado.setSn(usuarioResponseDTO.getSurname());
        return resultado;
    }

    private static List<UsuarioLdap> construirListaUsuarioSIA(Object data) {
        List<UsuarioResponseDTO> listaUsuarios = new Gson().fromJson(new Gson().toJson(data), new TypeToken<ArrayList<UsuarioResponseDTO>>() {
        }.getType());
        return listaUsuarios.stream().map(IntegradorDA::contruirUsuarioSIA).collect(Collectors.toList());
    }

    private static String consultarLLave(String llave) {
        return UtilsDesencriptar.desencriptar(llave);
    }
}
