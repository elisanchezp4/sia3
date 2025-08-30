package co.gov.mineducacion.seguridad.ejb.servicios.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion;
import co.gov.mineducacion.seguridad.ejb.servicios.ServicioLDAP;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.MapasDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.OperacionesDTOCmp;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;
import co.gov.mineducacion.seguridad.modelo.utils.UtilToken;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioBitacoraAuditoria;
import co.gov.mineducacion.seguridad.negocio.NegocioOperaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioTokensActivos;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;

/**
 * Implementaci&oacute;n del servicio de autenticaci&oacute;n
 *
 * @author hfabra
 * @since 9/02/2017
 */
@Stateless
public class ServicioAutenticacion implements IServicioAutenticacion {

	private static final Logger logger = Logger.getLogger(ServicioAutenticacion.class);
	private String mesagge = "Error en la autenticación. SIA3 no está disponible. Por favor, intente más tarde.";
	private String mesaggeU = " Usuario: ";

	@EJB
	protected NegocioTokensActivos negocioTokenActivos;

	@EJB
	protected NegocioUsuarios negocioUsuarios;

	@EJB
	protected NegocioUsuariosRol negocioUsuariosRol;

	@EJB
	protected NegocioOperaciones negocioOperaciones;

	@EJB
	protected NegocioBitacoraAuditoria negocioBitacoraAuditoria;

	@EJB
	protected NegocioAplicaciones negocioAplicaciones;

	@EJB
	private ParametrosSng parametrosSng;

	/*
	 * (non-Javadoc)
	 *
	 * @see co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion#
	 * obtenerToken(java.lang.String)
	 */
	@Override
	public TokensActivosDTO obtenerToken(String codigoAutorizacion, Integer userId, String clientId) throws SeguridadException {
		TokensActivosDTO tokenAutorizacion = null;
		TokensActivosDTO tokenAcceso = null;
		BigDecimal idUsuario = null;
		logger.info("Llama obtenerToken---> " + " codigoAutorizacion: " + codigoAutorizacion + " userId: " + userId + " clientId: " + clientId);

		try {
			validarNumerico(clientId);
			validarCampos(codigoAutorizacion, clientId);

			// Se carga la informacion de aplicacion
			AplicacionesDTO aplicacion = negocioAplicaciones.buscarAplicacion(clientId);
			logger.info("Busca la aplicacion---> " + " aplicacion: " + aplicacion);

			/*
			 * Busca el token de autorizacion
			 */
			tokenAutorizacion = negocioTokenActivos.buscarToken(codigoAutorizacion);


			//Validacion del token
			if (tokenAutorizacion == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mesagge, null));
				throw new SeguridadException(mesagge);
			}

			this.validarAsociacionUserToken(tokenAutorizacion, userId.toString());
			logger.info("validarAsociacionUserToken---> " + " tokenAutorizacion: " + tokenAutorizacion.getTokenId() + mesaggeU + userId.toString());

			/* Validaciones */
			isTokenValidoVigente(tokenAutorizacion);

			idUsuario = tokenAutorizacion.getUsuarioId();


			//se intenta la generación del token N veces
			String code; //token generado
			int intentos = 1;
			boolean existeToken = Boolean.TRUE;

			do{

				// Genera el token de sesion y lo ingresa en base de datos
				code = UtilOperaciones.randomString(Constantes.LONGITUD_CODIGO);

				TokensActivosDTO tokenEnBd = negocioTokenActivos.buscarToken(code);

				if(tokenEnBd == null){
					existeToken = Boolean.FALSE;
				}

				intentos++;
			}while (intentos <= Constantes.INTENTOS_GENERACION_TOKEN && existeToken);

			if(existeToken){
				throw new SeguridadException(SeguridadException.ID_MSG_ERR_IMP_GENERAR_TOKEN);
			}
			logger.info("Token generado---> " + code);

			tokenAcceso = UtilToken.armarTokenDTO(code, Constantes.ID_TIPO_TOKEN_ACCESO, idUsuario, aplicacion.getMinutosVigenciaToken());
			logger.info("Armo el Token---> " + "tokenAutorizacion: " + tokenAcceso.getTokenId() + mesaggeU + tokenAcceso.getUsuarioId() + " fechaVencimiento: " + tokenAcceso.getVencimiento());

			negocioTokenActivos.crear(tokenAcceso);
			logger.info("Crea Token---> " + "tokenAutorizacion: " + tokenAcceso.getTokenId() + mesaggeU + tokenAcceso.getUsuarioId());

			//ERROR: No debe eliminar el codigo de autorizacion porque el proceso ya mas adelante elimina todos los tokens vencidos

			return tokenAcceso;

		} catch (SeguridadException e) {

			negocioBitacoraAuditoria.registrarEventoAuditoria(e.getId(), userId != null? userId.toString(): "1", clientId);

			logger.error("SeguridadException", e);

			throw e;

		} catch (Exception e) {

			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_TOKEN_NOT_FOUND, userId != null? userId.toString(): "1", clientId);

			logger.error("Exception " + e.getMessage(), e);

			throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
		}
	}

	/**
	 * @param codigoAutorizacion
	 * @param clientId
	 * @author hfabra
	 * @throws SeguridadException
	 * @since 31/03/2017
	 */
	private void validarCampos(String codigoAutorizacion, String clientId) throws SeguridadException {
		if ( ((codigoAutorizacion == null) || (clientId == null))
				|| (((codigoAutorizacion != null) && (codigoAutorizacion.equals("")))
				|| ((clientId != null) && (clientId.equals(""))))
				|| (((codigoAutorizacion != null) && (!codigoAutorizacion.equals(""))
				&& (codigoAutorizacion.length() > 50))
				|| ((clientId != null) && (!clientId.equals("")) && (clientId.length() > 50)))) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}

	}

	/**
	 *
	 * @param usuario
	 * @param password
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 2/04/2017
	 */
	private void validarCampos(String password) throws SeguridadException {
		if (password == null || password.isEmpty() || password.length() > 25) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}

	/**
	 * @param clientId
	 * @author hfabra
	 * @throws SeguridadException
	 * @since 3/04/2017
	 */
	private void validarNumerico(String clientId) throws SeguridadException {
		try {
			new BigDecimal(clientId);
		} catch (Exception e) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}

	/**
	 * M&eacute;toddo que valide que el c&oacute;digo de acceso se encuentre en
	 * la base de datos y est&eacute; vigente
	 *
	 * @param token
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public void isTokenValidoVigente(TokensActivosDTO token) throws SeguridadException {
		if (token != null) {
			if (token.getVencimiento().before(UtilToken.obtenerFechaActual())) {
				logger.info("Fecha vencimiento token> " + token.getVencimiento());
				logger.info("Fecha actual>" + UtilToken.obtenerFechaActual());
				logger.info("Elimino token--->" + token.getTokenId() + " Fecha Vencimiento: " + token.getVencimiento() + " Tipo: " + token.getTipo() +  mesaggeU + token.getUsuarioId());
				negocioTokenActivos.eliminar(token.getTokenId());
				throw new SeguridadException(SeguridadException.ID_MSG_CODIGO_NO_VIGENTE);
			}
		} else
			throw new SeguridadException(SeguridadException.ID_MSG_CODIGO_NO_VIGENTE);
	}

	/**
	 * M&eacute;toddo que valide que el c&oacute;digo de acceso se encuentre en
	 * la base de datos
	 *
	 * @param token
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public void isTokenValido(TokensActivosDTO token) throws SeguridadException {
		if (token == null)
			throw new SeguridadException(SeguridadException.TOKEN_NO_ENCONTRADO, TipoExcepcion.ERROR);
	}

	/**
	 * M&eacute;toddo que valida que el usuario se encuentre en la base de datos
	 *
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	private void isExistingUsername(UsuariosDTO usuario) throws SeguridadException {
		if (usuario == null) {
			throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO, TipoExcepcion.ERROR);
		}
	}

	/**
	 * M&eacute;toddo que valida que el token y usuario enviados se encuentren
	 * asociados
	 *
	 * @param token
	 * @param usuario
	 * @author hfabra
	 * @throws SeguridadException
	 * @since 9/02/2017
	 */
	public void validarAsociacionUserToken(TokensActivosDTO token, String usuario) throws SeguridadException {
		if(token == null){
			throw new SeguridadException(SeguridadException.TOKEN_USU_NO_ASOCIADO);
		}
		if (!token.getUsuarioId().toString().equals(usuario)) {
			throw new SeguridadException(SeguridadException.TOKEN_USU_NO_ASOCIADO);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion#
	 * finalizarSesion(java.lang.String, java.lang.String)
	 */
	@Override
	public void finalizarSesion(String token, Integer usuario, String clientId) throws SeguridadException {
		TokensActivosDTO tokenDTO = null;

		String userID = usuario.toString();

		try {
			validarNumerico(clientId);
			validarCampos(token, clientId);
			/*
			 * Busca el token de autorizacion
			 */
			tokenDTO = negocioTokenActivos.buscarToken(token);
			/*
			 * Carga la informacion del usuario
			 */
			UsuariosDTO userDTO = negocioUsuarios.buscarUsuario(userID);

			/* Validaciones */
			isTokenValido(tokenDTO);
			isExistingUsername(userDTO);
			validarAsociacionUserToken(tokenDTO, userID);

			/* Inactivar token */
			negocioTokenActivos.eliminar(token);

		} catch (SeguridadException e) {

			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_LOGOUT_FAIL, userID , clientId);
			negocioBitacoraAuditoria.registrarEventoAuditoria(e.getId(), userID , clientId);

			logger.error("SeguridadException", e);

			throw e;

		} catch (Exception e) {

			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_LOGOUT_FAIL, userID , clientId);

			logger.error("Exception" + e.getMessage(), e);

			throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion#
	 * modificarPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void modificarPassword(String token, Integer usuario, String password, String clientId, String usuarioPeticion) throws SeguridadException {
		String userID = usuario.toString();
		try {
		logger.info("Inicia proceso para modificar password, token: " + token + ", usuario: " + usuario + ", cliente: " + clientId);
			validarNumerico(clientId);
			validarCampos(token, clientId);
			modificarPassword(usuario, password, clientId, usuarioPeticion);
		} catch (SeguridadException e) {
			logger.error("Error actualizando password security exception: "+e.getMessage());
			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_UPDATE_PASSWORD_FAIL, userID, clientId);
			negocioBitacoraAuditoria.registrarEventoAuditoria(e.getId(), userID, clientId);
			throw new SeguridadException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error actualizando password exception: "+e.getMessage());
			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_UPDATE_PASSWORD_FAIL, userID, clientId);
			throw new SeguridadException("Error al actualizar la contraseña", e);
		}
	}

	@Override
	public void modificarPassword(Integer usuario, String password, String clientId, String usuarioPeticion) throws SeguridadException {
		String userID = usuario.toString();
		try {
			logger.info("Inicia proceso para modificar password, usuario: " + usuario + ", cliente: " + clientId);
			validarNumerico(clientId);
			validarCampos(password);
			logger.info("Finaliza proceso validacion campos");
			// Se carga la información del usuario
			UsuariosDTO username = negocioUsuarios.buscarUsuario(userID);
			logger.info("Se carga la informacion del usuario: " + new Gson().toJson(username));
			/*
			 * Se valida si el usuario está asociado a alguna aplicacion
			 */
			verificarUsuarioAplicacion(userID);

			/* Validaciones */
			verificarUsuarioExterno(username);
			verificarUsuarioActivo(username);
			ServicioLDAP.validarUsuarioExisteLDAP(username.getLogonName(), username.getRuta(), parametrosSng.obtenerParametros());

			ServicioLDAP.modificarPassword(password, username.getLogonName(), parametrosSng.obtenerParametros());

			JsonObject detalle = new JsonObject();
			detalle.addProperty("descripcion", "Se actualiza el password");
			detalle.addProperty("actual", "encryption");
			detalle.addProperty("nuevo", "encryption");

			negocioBitacoraAuditoria.gestionarAuditoriaConDetalleYCampoActivo(Constantes.EVT_USER_CHANGED_PASSWORD, usuarioPeticion, clientId, detalle.toString(), "password");
		} catch (SeguridadException e) {
			logger.error("Error actualizando password security exception: "+e.getMessage());
			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_UPDATE_PASSWORD_FAIL, userID, clientId);
			negocioBitacoraAuditoria.registrarEventoAuditoria(e.getId(), userID, clientId);
			throw new SeguridadException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error actualizando password exception: "+e.getMessage());
			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_UPDATE_PASSWORD_FAIL, userID, clientId);
			throw new SeguridadException("Error al actualizar la contraseña", e);
		}
	}



	/**
	 * M&eacute;toddo que valida que el usuario se encuentre asociado a una
	 * aplicaci&ocute;n
	 *
	 * @param usuario
	 * @author hfabra
	 * @throws SeguridadException
	 * @since 11/02/2017
	 */
    private void verificarUsuarioAplicacion(String usuario) throws SeguridadException {
        logger.info("Inicia comando para verificar usuario aplicacion: " + usuario);
        List<UsuariosRolDTO> usersRol = negocioUsuariosRol.buscarUsuarioRolPorUsuario(usuario);
        if (usersRol.isEmpty()) {
            logger.error("Error validando usuario aplicacion: " + usersRol);
            throw new SeguridadException(SeguridadException.USER_NOT_ANY_APP);
        }
    }

	/**
	 * M&eacute;toddo que valida que el usuario sea de tipo externo
	 *
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 12/02/2017
	 */
	public void verificarUsuarioExterno(UsuariosDTO usuario) throws SeguridadException {
		logger.info("emepiza metodo verificar usuario externo");
		BigDecimal tipoUsuario = usuario.getTipo();
		if (tipoUsuario.equals(Constantes.TIPO_USUARIO_INTERNO_N)) {
			throw new SeguridadException(Constantes.ERROR_USUARIO_NO_EXTERNO, TipoExcepcion.ERROR);
		}
	}

	/**
	 * M&eacute;toddo que valida que el usuario est&eacute; activo
	 *
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 12/02/2017
	 */
	public void verificarUsuarioActivo(UsuariosDTO usuario) throws SeguridadException {
        logger.info("Inicia proceso para validar usuario activo: "+new Gson().toJson(usuario));
		BigDecimal estadoUsuario = usuario.getEstado();
		if (estadoUsuario.equals(Constantes.ESTADO_ACTIVO_N)) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_USU_INACTIVO);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion#
	 * actualizarFechaVencimientoToken(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Timestamp actualizarFechaVencimientoToken(String token, Integer usuario, String clientId)
			throws SeguridadException {
		TokensActivosDTO tokenDTO = null;

		String userID = usuario.toString();

		try {
			validarNumerico(clientId);
			validarCampos(token, clientId);
			/*
			 * Se carga la informacion del usuario y de la aplicacion
			 */
			UsuariosDTO username = negocioUsuarios.buscarUsuario(userID);
			AplicacionesDTO aplicacion = negocioAplicaciones.buscarAplicacion(clientId);

			/* Validaciones */
			verificarUsuarioAplicacion(userID);

			verificarUsuarioActivo(username);

			/*
			 * Busca el token de autorizacion
			 */
			tokenDTO = negocioTokenActivos.buscarToken(token);

			if(tokenDTO == null){
				throw new SeguridadException(SeguridadException.TOKEN_USU_NO_ASOCIADO);
			}

			/* Validaciones */
			validarAsociacionUserToken(tokenDTO, userID);

			negocioBitacoraAuditoria.gestionarAuditoriaDetalle(Constantes.EVT_UPDATED_TOKEN_DATE,
					userID, clientId, "Aplicación: " + aplicacion.getNombre());

			// Se actualiza el token
			Timestamp nuevoVencimiento = new Timestamp(obtenerFechaActual().getTime()
					+ (aplicacion.getMinutosVigenciaToken().intValue() * Constantes.SECONDS * Constantes.MILISECONDS));

			tokenDTO.setVencimiento(nuevoVencimiento);
			negocioTokenActivos.actualizar(tokenDTO);

			return tokenDTO.getVencimiento();

		} catch (SeguridadException e) {

			negocioBitacoraAuditoria.registrarEventoAuditoria(e.getId(), userID, clientId);

			throw e;

		} catch (Exception e) {
			throw new SeguridadException(SeguridadException.NO_CONTROLADA);
		}

	}

	private Timestamp obtenerFechaActual() {
		return new Timestamp(System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion#
	 * obtenerRolesYPermisos(java.lang.String)
	 */
	@Override
	public OperacionesRolDTO obtenerRolesYPermisos(Integer usuario, String clientId, String tokenAcceso) throws SeguridadException {
		String userID = usuario.toString();
		OperacionesRolDTO rolesPermisos = new OperacionesRolDTO();

		try {

			//se valida que el token esté activo
			TokensActivosDTO tokenDTO = negocioTokenActivos.buscarToken(tokenAcceso);
			this.isTokenValidoVigente(tokenDTO);
			this.validarAsociacionUserToken(tokenDTO, usuario.toString());

			/*
			 * Carga la informacion del usuario y se carga informacion basica
			 * del LDAP
			 */
			UsuariosDTO userDTO = negocioUsuarios.buscarUsuario(userID);
			userDTO = negocioUsuarios.completarInformacionUsuario(userDTO.getLogonName());
			cargarInformacionBasicaUsuariO(userDTO);

			/*
			 * Validaciones
			 */
			isExistingUsername(userDTO);
			verificarUsuarioActivo(userDTO);
			List<UsuariosRolDTO> userRolList = validarAplicacionUsuario(userDTO.getUsuarioId(), clientId);

			// Se enlistan los roles a retornar y consultar a la vez
			List<String> role = enlistarNombresRoles(userRolList);
			List<BigDecimal> roleIDs = enlistarCodigoRoles(userRolList);

			// Se validan los permisos asociados a los roles
			List<OperacionesDTO> permisos = verificarPermisosRol(roleIDs, clientId);

			if (role.isEmpty() || permisos.isEmpty()) {
				String mensajeError = mesagge;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, null));
				throw new SeguridadException(mensajeError);
			}

			rolesPermisos.setRolesList(role);
			rolesPermisos.setOperacionesList(permisos);
			rolesPermisos.setUsuario(userDTO);
		} catch (SeguridadException e) {

			if (e.getId().equals(SeguridadException.USUARIO_NO_ENCONTRADO))
				userID = "1";

			negocioBitacoraAuditoria.registrarEventoAuditoria(e.getId(), userID, clientId);
			throw e;

		} catch (Exception e) {
		throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
		}
		return rolesPermisos;
	}

	/**
	 * @param userDTO
	 * @author hfabra
	 * @throws SeguridadException
	 * @since 24/02/2017
	 */
	private void cargarInformacionBasicaUsuariO(UsuariosDTO userDTO) throws SeguridadException {
		UsuarioLdap usuarioLDAP = ServicioLDAP.buscarUsuarioPorLogin(userDTO.getLogonName(), userDTO.getRuta(), parametrosSng.obtenerParametros());

		if (usuarioLDAP == null) {
			throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO_LDAP, TipoExcepcion.ERROR);
		} else {
			userDTO.setApellidosUsuario(usuarioLDAP.getSn());
			userDTO.setNombres(usuarioLDAP.getGivenName());
			userDTO.setEmailUsuario(usuarioLDAP.getMail());
			userDTO.setNombreUsuario(usuarioLDAP.getGivenName() + " "+ usuarioLDAP.getSn());
			userDTO.setLogonName(usuarioLDAP.getsAMAccountName());
		}

	}

	/**
	 * M&eacute;toddo que valida si un usuario tiene asociado una
	 * aplicaci&oacute;n
	 *
	 * @param usuarioId
	 * @param clientId
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 10/02/2017
	 */
	private List<UsuariosRolDTO> validarAplicacionUsuario(String usuarioId, String clientId) throws SeguridadException {

		//se valida que la aplicación se encuentrea activa
		AplicacionesDTO app = negocioAplicaciones.buscarAplicacion(clientId);

		if(app.getEstado().equals(Constantes.ESTADO_ACTIVO_N)){

			throw new SeguridadException(SeguridadException.APP_INACTIVA, TipoExcepcion.ERROR);

		}

		List<UsuariosRolDTO> usersRol = negocioUsuariosRol.buscarUsuarioRolPorUsuarioApp(usuarioId, clientId);
		if (usersRol.isEmpty()) {
			throw new SeguridadException(SeguridadException.USER_NO_PERTENECE_APLICACION, TipoExcepcion.ERROR);
		}

		return usersRol;
	}

	/**
	 * @param userRolList
	 * @return
	 * @author hfabra
	 * @since 17/02/2017
	 */
	private List<BigDecimal> enlistarCodigoRoles(List<UsuariosRolDTO> userRolList) {
		List<BigDecimal> codigoRoles = new ArrayList<>();
		for (UsuariosRolDTO userRol : userRolList) {
			codigoRoles.add(userRol.getUsuariosRolPK().getRolId());
		}
		return codigoRoles;
	}

	/**
	 * @param userRolList
	 * @return
	 * @author hfabra
	 * @since 17/02/2017
	 */
	private List<String> enlistarNombresRoles(List<UsuariosRolDTO> userRolList) {
		List<String> nombresRoles = new ArrayList<>();
		for (UsuariosRolDTO userRol : userRolList) {
			nombresRoles.add(userRol.getRoles().getNombre());
		}
		return nombresRoles;
	}

	/**
	 * Ordena el listado de operaciones según el orden de visualización
	 * @param lstOperaciones
	 */
	private void ordenarOperaciones(List<OperacionesDTO> lstOperaciones){

		if(lstOperaciones != null){
			Collections.sort(lstOperaciones, new OperacionesDTOCmp());
			for(OperacionesDTO opDto : lstOperaciones){
				ordenarOperaciones(opDto.getListadoOperaciones());
			}
		}
	}

	/**
	 * M&eacute;toddo que busca los roles asociados a un usuario, de acuerdo a la
	 * aplicac&oacute;n la cula va a ingresar
	 *
	 * @param userRole
	 * @param clientId
	 * @author hfabra
	 * @throws SeguridadException
	 * @since 11/02/2017
	 */
	private List<OperacionesDTO> verificarPermisosRol(List<BigDecimal> role, String clientId)
			throws SeguridadException {
		// y crear un nodo hijo
		MapasDTO optionsRole = negocioOperaciones.buscarUsuarioRolPorUsuario(role, clientId);

		if (optionsRole.getMapaCompleto().isEmpty())
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_USU_NOT_ROLE, TipoExcepcion.ERROR);
		// MapaPadreHijo se obtiene del negocio
		// MapaCompleto se obtiene del negocio

		Map<String, ArrayList<BigDecimal>> mapaOcurrenciaPadreHijo = obtenerMapaReversado(
				optionsRole.getMapaPadreHijo());

		List<OperacionesDTO> permisos = obtenerNodos(optionsRole.getMapaCompleto(), mapaOcurrenciaPadreHijo, "null");

		this.ordenarOperaciones(permisos);

		return permisos;
	}

	/**
	 * M&eacute;toddo que retorna la ocurrencia de valores de un mapa
	 *
	 * @param mapa
	 * @return
	 * @author hfabra
	 * @since 19/02/2017
	 */
	private Map<String, ArrayList<BigDecimal>> obtenerMapaReversado(Map<BigDecimal, BigDecimal> mapa) {
		Map<String, ArrayList<BigDecimal>> reverseMap = new HashMap<>();

		for (Map.Entry<BigDecimal, BigDecimal> entry : mapa.entrySet()) {
			if (!reverseMap.containsKey(entry.getValue() + "")) {
				reverseMap.put(entry.getValue() + "", new ArrayList<>());
			}
			ArrayList<BigDecimal> keys = (reverseMap.get(entry.getValue() + "") != null)
					? reverseMap.get(entry.getValue() + "") : new ArrayList<>();
			keys.add(entry.getKey());
			reverseMap.put(entry.getValue() + "", keys);
		}
		return reverseMap;
	}

	/**
	 * M&eacute;toddo recuersivo que obtiene los valores de los permisos y los
	 * hijos
	 *
	 * @param mapaCompleto
	 * @param mapaOcurrenciaPadreHijo
	 * @param idPadre
	 * @return
	 * @author hfabra
	 * @since 19/02/2017
	 */
	private List<OperacionesDTO> obtenerNodos(Map<String, OperacionesDTO> mapaCompleto,
											  Map<String, ArrayList<BigDecimal>> mapaOcurrenciaPadreHijo, String idPadre) {
		ArrayList<BigDecimal> padre = mapaOcurrenciaPadreHijo.get(idPadre);
		List<OperacionesDTO> listadoOpciones = new ArrayList<>();
		for (BigDecimal i : padre) {
			// Operaciones
			OperacionesDTO opcion = (OperacionesDTO) mapaCompleto.get(i + "");

			if (mapaOcurrenciaPadreHijo.containsKey(i + "")) {
				opcion.setListadoOperaciones(obtenerNodos(mapaCompleto, mapaOcurrenciaPadreHijo, i.toString()));
			}
			listadoOpciones.add(opcion);
		}

		return listadoOpciones;
	}
}
