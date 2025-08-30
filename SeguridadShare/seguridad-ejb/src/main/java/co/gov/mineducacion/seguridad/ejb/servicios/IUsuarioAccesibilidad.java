package co.gov.mineducacion.seguridad.ejb.servicios;

import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

import java.util.Map;

import javax.ejb.Local;


@Local
public interface IUsuarioAccesibilidad {

	public Map<String, Object> consultaPropAcceUsuario(String usrsId);
 
	public void almacenarPropAcc(Object objectMessage) throws SIA3Exception;
}
