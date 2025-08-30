package co.gov.mineducacion.seguridad.ejb.servicios.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarioAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.entidades.PropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorPropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarioAccesibilidad;

@Stateless
public class UsuarioAccesibilidadImpl implements IUsuarioAccesibilidad {

	@EJB
	NegocioUsuarioAccesibilidad negocioUsuarioAccesibilidad;

	@EJB
	private ManejadorPropiedadesAccesibilidad manejadorPropiedadesAccesibilidad;

	private static final Logger logger = Logger.getLogger(UsuarioAccesibilidadImpl.class);
	
	

	/**
	 * Método encargado de consultar las propiedades de accesiilidad configurados por el usuario	
	 * @param userId
	 * @return listUsrsAcc
	 */
	@Override
	public  Map<String, Object> /* List<UsuarioAccesibilidadDTO>*/ consultaPropAcceUsuario(String usrsId) {
		return negocioUsuarioAccesibilidad.consultrarPropAccUsuario(usrsId);
	}

	/**
	 * Método encargado de actualizar las propiedades de accesiilidad configurados por el usuario	
	 * @param objectM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void almacenarPropAcc(Object objectM) {
		String uId = "usrsId";
		try {
			Map<String, String> object = (Map<String, String>) objectM;
    
			String usrsId = object.get(uId);
			object.remove(uId);
		     
		    List<PropiedadesAccesibilidad> propAcc = manejadorPropiedadesAccesibilidad.obtenerPropAcc();
			boolean validado = true;
			if (propAcc.size() != object.size()) {
				throw new SIA3Exception(Constantes.ERROR_ESTRUCTURA_INCORRECTA);
			}
			for (PropiedadesAccesibilidad propiedadesBD : propAcc){
				validado = true;
				for (Map.Entry<String, String> propiedadWS : object.entrySet()) {
					validado = !(propiedadesBD.getPropiedadAccId().trim().equals(propiedadWS.getKey().trim()));
				}
				if (validado) {
					break;
				}
			}
			if (validado) {
				throw new SIA3Exception(Constantes.ERROR_PROPIEDAD_NO_EXISTE);
			}
			for (Map.Entry<String, String> propiedad : object.entrySet()) {
				negocioUsuarioAccesibilidad.almacenarParamAcces(propiedad.getValue(), propiedad.getKey(), usrsId);
			}
		} catch (SIA3Exception e) {
			logger.error(e);
		}
	}

}
