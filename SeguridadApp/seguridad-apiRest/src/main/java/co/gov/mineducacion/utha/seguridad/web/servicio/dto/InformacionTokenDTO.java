package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contiene informaci�n sobre el token que identifica un usario en el sistema de seguridad
 * @author Asesoftware - Javier Estevez
 *
 */
@XmlRootElement
public class InformacionTokenDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -288119516432214310L;

	/**
	 * fecha de expiraci�n del token en el sistema de seguridad
	 */
	private Timestamp fechaExpiracion;
	
	private String tokenAcceso;
	

	public Timestamp getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Timestamp fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String getTokenAcceso() {
		return tokenAcceso;
	}

	public void setTokenAcceso(String tokenAcceso) {
		this.tokenAcceso = tokenAcceso;
	}
	
	
	
}
