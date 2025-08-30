package co.gov.mineducacion.seguridad.ejb.servicios.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;

import co.gov.mineducacion.seguridad.ejb.servicios.IPassword;
import co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion;
import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import co.gov.mineducacion.seguridad.ejb.servicios.ServicioLDAP;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilEmail;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;
import co.gov.mineducacion.seguridad.modelo.utils.UtilToken;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioBitacoraAuditoria;
import co.gov.mineducacion.seguridad.negocio.NegocioTokensActivos;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import javax.ejb.Stateless;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
/**
 * Implementa los servicios necesarios para administrar las claves de usuarios.
 * @author Asesoftware - hfabra
 *
 */

@Stateless
public class ServicioPassword implements IPassword {

	private static final Logger logger = Logger.getLogger(ServicioPassword.class);

	@EJB
	protected NegocioAplicaciones negocioAplicaciones;

	@EJB
	protected NegocioTokensActivos negocioTokensActivos;

	@EJB
	protected NegocioUsuariosRol negocioUsuariosRol;

	@EJB
	protected NegocioBitacoraAuditoria negocioBitacoraAuditoria;

	@EJB
	protected NegocioUsuarios negocioUsuarios;

	@EJB
	protected IUsuarios usuariosImpl;

	@EJB
	protected IServicioAutenticacion servicioAutenticacion;

	@EJB
	private ParametrosSng parametrosSng;

    /**
     * @see IPassword#procesoRestablecerPassword(String, String, String)
     */
    @Override
    public String procesoRestablecerPassword(String logonName, String context, String idApp) throws SeguridadException {
        String code = null;

        try {
            //Validaciones
            List<UsuariosDTO> userActive = verificarExisteUsuario(logonName);
            //se calculan los minutos de vigencia de la aplicación para cambio de contraseña
            BigDecimal minutosVigencia = Constantes.MINUTOS_VIGENCIA_CAMBIO_CLAVE_DEFECTO;
            if (idApp != null) {
                AplicacionesDTO aplicacion = negocioAplicaciones.buscarAplicacion(idApp);
                minutosVigencia = aplicacion.getMinVigTokenActConstrasenia();
            }

            UsuariosDTO usuario = userActive.get(0);

            servicioAutenticacion.verificarUsuarioExterno(usuario);
            servicioAutenticacion.verificarUsuarioActivo(usuario);

            cargasInfoAdicionalUsuario(usuario);
            logger.info("Finaliza proceso cargasInfoAdicionalUsuario");
            // Se crea el codigo de autorizacion
            int intentos = 1;
            boolean existeToken = Boolean.TRUE;

            do {
                // Genera el token de sesion y lo ingresa en base de datos
                code = UtilOperaciones.randomString(Constantes.LONGITUD_CODIGO);
				TokensActivosDTO tokenEnBd = negocioTokensActivos.buscarToken(code);
                logger.info("Finaliza buscar token");
                if (tokenEnBd == null) {
                    existeToken = Boolean.FALSE;
                }
                intentos++;
            } while (intentos <= Constantes.INTENTOS_GENERACION_TOKEN && existeToken);
			if (existeToken) {
                throw new SeguridadException(SeguridadException.ID_MSG_ERR_IMP_GENERAR_TOKEN, TipoExcepcion.ERROR);
            }

            String enlace = armarEnlace(context, code, usuario.getUsuarioId());
            logger.info("Finaliza proceso armar enlace: "+enlace);
            UtilEmail.enviarPasswordEmail(usuario, enlace, false, parametrosSng.obtenerParametros());
            // Se registra el token de autorizacion en base de datos
            negocioTokensActivos.crear(UtilToken.armarTokenDTO(code, Constantes.ID_TIPO_TOKEN_AUTORIZACION,
                    new BigDecimal(usuario.getUsuarioId()), minutosVigencia));

			JsonObject detalle = new JsonObject();
			detalle.addProperty("descripcion", "Proceso para restablecer password");
			detalle.addProperty("actual", "encryption");
			detalle.addProperty("nuevo", "encryption");

            negocioBitacoraAuditoria.gestionarAuditoriaConDetalleYCampoActivo(Constantes.EVT_USER_FORGET_PASSWORD,
                    usuario.getUsuarioId(), idApp, detalle.toString(), "password");
        } catch (SeguridadException e) {
			logger.error("Error actualizando password security exception: "+e.getMessage());
            throw new SeguridadException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error("error en procesoRestablecerPassword: " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
        }
        return code;
    }

	/**
	 * @param usuario
	 * @author hfabra
	 * @throws SeguridadException
	 * @since 24/03/2017
	 */
	private void cargasInfoAdicionalUsuario(UsuariosDTO usuario) throws SeguridadException {
		UsuarioLdap userLdap = ServicioLDAP.buscarUsuarioPorLogin(usuario.getLogonName(), usuario.getRuta(), parametrosSng.obtenerParametros());
		if (userLdap == null) {
			throw new SeguridadException("El usuario no existe en el sistema o no está activo.", TipoExcepcion.ERROR);
		}
		usuario.setEmailUsuario(userLdap.getMail());
		usuario.setNombres(userLdap.getGivenName());
		usuario.setApellidosUsuario(userLdap.getSn());
	}

	/**
	 * @param context
	 * @param code
	 * @return
	 * @author hfabra
	 * @since 24/03/2017
	 */
	private String armarEnlace(String context, String code, String userId) {
		StringBuilder enlace = new StringBuilder(context)
				.append("/ingresarNuevaContrasenia.jsf?code=")
				.append(code)
				.append("&userID="+userId);
		return enlace.toString();
	}

	/**
	 *
	 * @param usuarioAsociado
	 * @return
	 * @throws SeguridadException
	 * @throws InvalidParameterException
	 * @author hfabra
	 * @since 25/03/2017
	 */
	private List<UsuariosDTO> verificarExisteUsuario(String usuarioAsociado) throws SeguridadException, InvalidParameterException {
		/*
		 * Se cargan los datos del usuario
		 */
		List<UsuariosDTO> existeUsuario = negocioUsuarios.consultar("logonName="+usuarioAsociado, null, null, null);
		if ((existeUsuario==null) || ((existeUsuario!=null) && (existeUsuario.isEmpty()))) {
			throw new SeguridadException(SeguridadException.ID_MSG_WEB_USUARIO_NO_ENCONTRADO, TipoExcepcion.ALERTA);
		}
		return existeUsuario;
	}
}
