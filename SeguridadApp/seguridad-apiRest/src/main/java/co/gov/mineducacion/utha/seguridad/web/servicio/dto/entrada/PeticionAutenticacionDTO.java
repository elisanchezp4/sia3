package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Almacena la informaci�n necesaria para realizar una solicitud sobre algunos
 * de los servicios REST de atenticaci�n
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class PeticionAutenticacionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4889114421488232932L;

	private HeaderDTO header;
	
	/**
	 * token que identifica la sesi�n del usuario que realiza la solicitud.
	 */
	private String tokenAcceso;

	public HeaderDTO getHeader() {
		return header;
	}

	public void setHeader(HeaderDTO header) {
		this.header = header;
	}

	public String getTokenAcceso() {
		return tokenAcceso;
	}

	public void setTokenAcceso(String tokenAcceso) {
		this.tokenAcceso = tokenAcceso;
	}
	
	
}
