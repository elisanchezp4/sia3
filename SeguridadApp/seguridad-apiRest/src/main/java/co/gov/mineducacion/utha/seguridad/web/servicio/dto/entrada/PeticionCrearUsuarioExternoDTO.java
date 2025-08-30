package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contiene informaci�n de entrada de la petici�n que permite creear usuarios, utilizada para los servicios REST de 
 * gesti�n de usuarios.
 * @author Administrador
 *
 */

@XmlRootElement
public class PeticionCrearUsuarioExternoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4404337750424622272L;

	private HeaderDTO header;
	
	private String rol;
	
	private InformacionUsuarioDTO informacionUsuario;

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

	public InformacionUsuarioDTO getInformacionUsuario() {
		return informacionUsuario;
	}

	public void setInformacionUsuario(InformacionUsuarioDTO informacionUsuario) {
		this.informacionUsuario = informacionUsuario;
	}
	
	
	
}
