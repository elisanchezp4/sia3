/**
 * 
 * @author hfabra
 * @since 24/03/2017
 */
package co.gov.mineducacion.seguridad.ejb.servicios;

import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;

/**
 * Expone los servicios necesarios para administrar las claves de usuarios.
 * @author Asesoftware - hfabra
 *
 */
public interface IPassword {
	
	/**
	 * Envía un correo al buzón configurado del usuario con una URL donde al ingresar es posible realizar el cambio del 
	 * clave. La url contiene un token que permite validar la autenticidad de la petición al momento de realizar el cambio
	 * @param logonName identificador del usuario
	 * @param context contiene el protocolo, ip o nombre y puerto del servidor donde se está ejecutando la aplicación (se utiliza para
	 * 					armar la url)
	 * @param idApp identificador de la aplicación a la que pertenece el usuario que restablecer la clave, usado para determinar el tiempo de vigencia del token
	 * @return
	 * @throws SeguridadException
	 */
	public String procesoRestablecerPassword(String logonName, String context, String idApp) throws SeguridadException;
}
