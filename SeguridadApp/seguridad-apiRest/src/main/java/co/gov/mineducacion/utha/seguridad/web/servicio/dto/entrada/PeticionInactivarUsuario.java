package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contiene informaci�n osbre la petici�n de inactivaci�n de usuarios, utilizada en los servicios REST de
 * gestion de usuarios
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class PeticionInactivarUsuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5690523883051669231L;

	private HeaderDTO header;
	
	private Integer userIdInactivar;

	public HeaderDTO getHeader() {
		return header;
	}

	public void setHeader(HeaderDTO header) {
		this.header = header;
	}

	public Integer getUserIdInactivar() {
		return userIdInactivar;
	}

	public void setUserIdInactivar(Integer userIdInactivar) {
		this.userIdInactivar = userIdInactivar;
	}

	


}
