package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Almacena un listado de permisos (normalmente opciones del menu) y un listado de roles, estos
 * pertenecen a un usuario dentro del sistema de seguridad
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class InformacionPermisosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4516255475968523861L;

	private List<OpcionDTO> permisos;
	
	private List<String> roles;

	public List<OpcionDTO> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<OpcionDTO> permisos) {
		this.permisos = permisos;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
