package co.gov.mineducacion.seguridad.ejb.servicios;


import javax.ejb.Local;

import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;

/**
 * Expone a la capa de presentacion (Servicios REST) los servicios necesarios para 
 * administrar las aplicaciones
 * @author Asesoftware - Henry Fabra
 *
 */
@Local
public interface IAplicacion {
	
	/**
	 * Retorna la aplicación (en un objeto AplicacionesDTO) cuyo identificador se recibe por parametro, si no encuentra
	 * la aplicación se retornar null.
	 * @param appId identificador de la aplicacion a consultar
	 * @return
	 */
	public AplicacionesDTO buscarAplicacion(String appId);
}
