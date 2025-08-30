package co.gov.mineducacion.seguridad.ejb.servicios;

import javax.ejb.Local;

import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;

/**
 * Expone los servicios que permiten administrar la sesión de los usuarios
 * @author Asesoftware - hfabra
 *
 */
@Local
public interface ISesion {

	/**
	 * Atentica en el sistema validando el usuario y clave recibidos por parmetro contra el directorio
	 * activo, inicia sesión y retorna el código de autorización generado (necesario para obtener el token).
	 * @param usuario identificador del usuario que pretende iniciar sesion
	 * @param contrasenia clave para autenticar
	 * @param clientId identificador de la aplicación (para determinar la duración del código de autorizacion)
	 * @return codigo de autorizacion 
	 * @throws SeguridadException lanzada en caso que se presente un error al iniciar la sesion, por ejemplo cuando
	 * 			falla la autenticacion en el directorio activo
	 */
	public String iniciarSesion(String usuario, String contrasenia, String clientId) throws SeguridadException;
	
	/**
	 * obtiene el token
	 * @param authCode codigo de autorización
	 * @throws SeguridadException
	 */
	public void obtenerToken(String authCode) throws SeguridadException;
	
	/**
	 * finaliza la seisión
	 * @return
	 * @throws SeguridadException
	 */
	public String finalizarSesion() throws SeguridadException;
}
