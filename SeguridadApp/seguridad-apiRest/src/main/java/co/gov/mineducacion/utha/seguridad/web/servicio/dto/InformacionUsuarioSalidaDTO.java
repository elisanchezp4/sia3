package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InformacionUsuarioSalidaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2103526907730721512L;
	private Integer userId;
	private String nombreUsuario;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	
}
