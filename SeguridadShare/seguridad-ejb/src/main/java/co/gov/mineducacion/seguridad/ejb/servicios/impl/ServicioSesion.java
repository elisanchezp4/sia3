package co.gov.mineducacion.seguridad.ejb.servicios.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;

import co.gov.mineducacion.seguridad.ejb.servicios.ISesion;
import co.gov.mineducacion.seguridad.ejb.servicios.ServicioLDAP;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperaciones;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;
import co.gov.mineducacion.seguridad.modelo.utils.UtilToken;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioBitacoraAuditoria;
import co.gov.mineducacion.seguridad.negocio.NegocioTokensActivos;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;

/**
 * Implementa los servicios que permiten administrar la sesión de los usuarios
 * @author Asesoftware - hfabra
 *
 */
@Stateless
public class ServicioSesion implements ISesion {

	private static final Logger logger = Logger.getLogger(ServicioSesion.class);

	@EJB
	protected NegocioAplicaciones negocioAplicaciones;

	@EJB
	protected NegocioUsuariosRol negocioUsuariosRol;

	@EJB
	protected NegocioTokensActivos negocioTokensActivos;

	@EJB
	protected NegocioBitacoraAuditoria negocioBitacoraAuditoria;

	@EJB
	protected NegocioUsuarios negocioUsuarios;
	
	@EJB
	protected ManejadorOperaciones manejadorOperaciones;
	
	@EJB 
	private ParametrosSng parametrosSng;

	/**
	 * @see ISesion#iniciarSesion(String, String, String)
	 */
	@Override
	public String iniciarSesion(String usuario, String contrasenia, String clientId) throws SeguridadException {

		logger.info("iniciarSesion--->" + " Usuario: " + usuario + " Cliente Id: " + clientId);
		String code = null;

		/*
		 * Se cargan los datos del usuario y de la aplicacion
		 */
		Usuario userActive = negocioUsuarios.consultarUsuario(usuario);
		AplicacionesDTO appDto = negocioAplicaciones.buscarAplicacion(clientId);
		try {
			/*
			 * Validaciones
			 */
			validarUsurioExisteSeguridad(userActive);
			
			ServicioLDAP.verificarExisteUsuario(usuario, contrasenia, parametrosSng.obtenerParametros());

			validarEstadoActivo(userActive);
			validarAplicacionUsuario(userActive.getUsuarioId(), clientId);
			
			//se valida que tenga operaciones relacionadas
			List<Operaciones> lstOperaciones = manejadorOperaciones.buscarOperacionesPorsuario(userActive.getUsuarioId(), appDto.getAplicacionId());
		    if(lstOperaciones == null ||lstOperaciones.isEmpty()){
		    	throw new SeguridadException(SeguridadException.ID_MSG_USR_NO_OPERACIONES);
		    }

			// Se crea el codigo de autorizacion
			int intentos = 1;
			boolean existeToken = Boolean.TRUE;

			do{
				
				// Genera el token de sesion y lo ingresa en base de datos
				code = UtilOperaciones.randomString(Constantes.LONGITUD_CODIGO);
				
				TokensActivosDTO tokenEnBd = negocioTokensActivos.buscarToken(code);
				
				if(tokenEnBd == null){
					existeToken = Boolean.FALSE;
				}
				
				intentos++;
			}while (intentos <= Constantes.INTENTOS_GENERACION_TOKEN && existeToken);
			
			if(existeToken){
				throw new SeguridadException(SeguridadException.ID_MSG_ERR_IMP_GENERAR_TOKEN);
			}

			// Se registra el token de autorizacion en base de datos
			negocioTokensActivos.crear(UtilToken.armarTokenDTO(code, Constantes.ID_TIPO_TOKEN_AUTORIZACION, new BigDecimal(userActive.getUsuarioId()), appDto.getMinutosVigenciaCodigo()));

			logger.info("crea Token de Autorizacion--->" + " Codigo Autorizacion: " + code + " Tipo Token: " + Constantes.ID_TIPO_TOKEN_AUTORIZACION);

			negocioBitacoraAuditoria.gestionarAuditoriaDetalle(Constantes.EVT_LOGIN_OK, userActive.getUsuarioId(), clientId, "Ingresó a la aplicación: " + appDto.getNombre());
			
			//se eliminan los tokens inactivos de la base de datos
			AplicacionesDTO app = negocioAplicaciones.buscarAplicacion(clientId);
			Timestamp fechaTokenVencidos = new Timestamp(UtilToken.obtenerFechaActual().getTime() - 2 * app.getMinutosVigenciaToken().intValue() * Constantes.SECONDS * Constantes.MILISECONDS);

			logger.info("fechaTokenVencidos para eliminacion de tokens--->" + fechaTokenVencidos);
			negocioTokensActivos.eliminarTokensVencidos(fechaTokenVencidos);

		} catch (SeguridadException e) {
			
				
			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_LOGIN_FAIL, 
														userActive != null?userActive.getUsuarioId():"1",
														clientId);
			negocioBitacoraAuditoria.registrarEventoAuditoria(e.getId(),
														userActive != null?userActive.getUsuarioId():"1",
														clientId);
			
			throw e;
			
		} catch (Exception e) {
			
			negocioBitacoraAuditoria.gestionarAuditoria(Constantes.EVT_LOGIN_FAIL, 
					userActive != null?userActive.getUsuarioId():"1",
					clientId);
			
			throw new SeguridadException(SeguridadException.NO_CONTROLADA,TipoExcepcion.FATAL);
		}
		return code;
	}

	/**
	 * @param userActive
	 * @author hfabra
	 * @throws SeguridadException 
	 * @since 10/04/2017
	 */
	private void validarUsurioExisteSeguridad(Usuario userActive) throws SeguridadException {
		if (userActive == null) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_USU_NO_EXISTE,TipoExcepcion.ERROR);
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
	private void validarAplicacionUsuario(String usuarioId, String clientId) throws SeguridadException {
		
		//se valida que la aplicación se encuentrea activa
		AplicacionesDTO app = negocioAplicaciones.buscarAplicacion(clientId);
		
		if(app.getEstado().equals(Constantes.ESTADO_ACTIVO_N)){
			
			throw new SeguridadException(SeguridadException.APP_INACTIVA, TipoExcepcion.ERROR);
			
		}

		
		List<UsuariosRolDTO> usersRol = negocioUsuariosRol.buscarUsuarioRolPorUsuarioApp(usuarioId, clientId);
		if (usersRol.isEmpty()) {
			throw new SeguridadException(SeguridadException.ID_MSG_USR_NO_ROLES_ACTIVOS, TipoExcepcion.ERROR);
		}
	}

	/**
	 * M&eacute;toddo que valida si el usuario en el sistema se encuenta activo o
	 * no
	 * 
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	private void validarEstadoActivo(Usuario userActive) throws SeguridadException {

		if (!userActive.getEstado().equals(Constantes.ESTADO_ACTIVO_S)) {
			throw new SeguridadException(SeguridadException.ID_MSG_WEB_ERROR_USU_INACTIVO, TipoExcepcion.ERROR);
		}
	}

	/**
	 * @see ISesion#obtenerToken(String)
	 */
	@Override
	public void obtenerToken(String authCode) throws SeguridadException {
		// TODDO Auto-generated method stub
	}

	/**
	 * @see ISesion#finalizarSesion()
	 */
	@Override
	public String finalizarSesion() throws SeguridadException {
		// TODDO Auto-generated method stub
		return null;
	}

}
