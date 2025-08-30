package co.gov.mineducacion.seguridad.ejb.servicios;

import java.sql.Timestamp;

import javax.ejb.Local;

import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;

/**
 * Interfaz donde se manejaran los servicios de autenticacion
 * @author hfabra
 * @since 9/02/2017
 */
@Local
public interface IServicioAutenticacion {
	
	/**
	 * Servicio para obtener el token de acceso
	 * @param codigoAutorizacion
	 * @return el codigo de acceso (token)
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public TokensActivosDTO obtenerToken(String codigoAutorizacion, Integer userId, String clientId) throws SeguridadException;
	
	/**
	 * Servicio para inactivar el token en la sesi&oacute;n
	 * @param token
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public void finalizarSesion(String token, Integer usuario, String clientId) throws SeguridadException;

	/**
	 * Servicio para realizar modificaci&oacute;n de contrasenia en LDAP
	 * @param token
	 * @param usuario
	 * @param password
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public void modificarPassword(String token, Integer usuario, String password, String clientId, String usuarioPeticion) throws SeguridadException;

	/**
	 * Servicio para realizar modificaci&oacute;n de contrasenia en LDAP sin TOKEN
	 * @param usuario
	 * @param password
	 * @param clientId
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public void modificarPassword(Integer usuario, String password, String clientId, String usuarioPeticion) throws SeguridadException;

	/**
	 * Servicio para actualizar la fecha de vencimiento del token
	 * @param token
	 * @param usuario
	 * @param clientId
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 12/02/2017
	 */
	public Timestamp actualizarFechaVencimientoToken(String token, Integer usuario, String clientId) throws SeguridadException;
	
	/**
	 * Servicio que consulta los roles y/o permisos asignados al usuario 
	 * en sesi&oacute;n
	 * @param usuario
	 * @return
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public OperacionesRolDTO obtenerRolesYPermisos(Integer usuario, String clientId, String tokenAcceso) throws SeguridadException;
	
	/**************************************** Operaciones auxiliares **********************************************/
	
	/**
	 * 
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 24/03/2017
	 */
	public void verificarUsuarioExterno(UsuariosDTO usuario) throws SeguridadException;
	
	/**
	 * 
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 24/03/2017
	 */
	public void verificarUsuarioActivo(UsuariosDTO usuario) throws SeguridadException;
	
	/**
	 * 
	 * @param token
	 * @param usuario
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 25/03/2017
	 */
	public void validarAsociacionUserToken(TokensActivosDTO token, String usuario) throws SeguridadException;
	
	/**
	 * M&eacute;toddo que valide que el c&oacute;digo de acceso se encuentre en
	 * la base de datos y est&eacute; vigente
	 * 
	 * @param token
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public void isTokenValidoVigente(TokensActivosDTO token) throws SeguridadException;
	
	/**
	 * M&eacute;toddo que valide que el c&oacute;digo de acceso se encuentre en
	 * la base de datos
	 * 
	 * @param token
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public void isTokenValido(TokensActivosDTO token) throws SeguridadException;

}
