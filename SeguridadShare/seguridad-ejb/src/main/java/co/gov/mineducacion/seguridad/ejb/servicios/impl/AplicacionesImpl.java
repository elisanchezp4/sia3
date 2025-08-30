package co.gov.mineducacion.seguridad.ejb.servicios.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import co.gov.mineducacion.seguridad.ejb.servicios.IAplicacion;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;

/**
 * Implementa los servicios que permiten administrar las aplicaciones 
 * @author Administrador
 *
 */
@Stateless
@Local
public class AplicacionesImpl implements IAplicacion {

	@EJB
	private NegocioAplicaciones negocioAplicaciones;

	/**
	 * @see IAplicacion#buscarAplicacion(String)
	 */
	@Override
	public AplicacionesDTO buscarAplicacion(String appId) {
		return negocioAplicaciones.buscarAplicacion(appId);
	}

}
