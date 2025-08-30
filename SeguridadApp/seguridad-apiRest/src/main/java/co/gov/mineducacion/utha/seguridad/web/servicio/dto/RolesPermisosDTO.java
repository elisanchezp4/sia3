package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contiene informaci�n sobre los permisos (normalmente opciones del men�) que tiene
 * un usuario en una aplicaci�n
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class RolesPermisosDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 588273723054533032L;

	InformacionPermisosDTO permisos;
	
	UsuarioDTO usuario;

	String tokenAcceso;

	public InformacionPermisosDTO getPermisos() {
		return permisos;
	}

	public void setPermisos(InformacionPermisosDTO permisos) {
		this.permisos = permisos;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public String getTokenAcceso() {
		return tokenAcceso;
	}

	public void setTokenAcceso(String tokenAcceso) {
		this.tokenAcceso = tokenAcceso;
	}
}
