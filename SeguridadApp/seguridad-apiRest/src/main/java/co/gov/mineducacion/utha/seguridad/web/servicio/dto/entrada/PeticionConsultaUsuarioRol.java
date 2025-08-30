package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Almacena la informaci�n necesaria para realizar una solicitud sobre algunos
 * de los servicios REST de gesti�n de usuarios
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class PeticionConsultaUsuarioRol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -194239108240369198L;

	private HeaderDTO header;
	
	/**
	 * rol del usuario sobre el cual se est� realizando la solicitud
	 */
	private String rol;

	public HeaderDTO getHeader() {
		return header;
	}

	public void setHeader(HeaderDTO header) {
		this.header = header;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
}
