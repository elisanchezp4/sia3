package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRolPK;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;

/**
 * DAO que contiene la información de la entidad OperacionesRolDTO que se transmite
 * por los servicios REST. Solo se transmiten los atributos simples, es decir,
 * se omiten aquellos atributos que definen relaciones con otras entidades.
 * 
 * @author jsoto
 */
@XmlRootElement
public class OperacionesRolDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OperacionesRolPK operacionesRolPK;
	private Roles roles;
	private Operaciones operaciones;
	
	private List<String> rolesList;
	private List<OperacionesDTO> operacionesList;
	private UsuariosDTO usuario;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public OperacionesRolDTO() {
		operacionesRolPK = new OperacionesRolPK();
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public OperacionesRolPK getOperacionesRolPK() {
		return this.operacionesRolPK;
	}

	public void setOperacionesRolPK(OperacionesRolPK operacionesRolPK) {
		this.operacionesRolPK = operacionesRolPK;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Operaciones getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(Operaciones operaciones) {
		this.operaciones = operaciones;
	}

	/**
	 * @author hfabra
	 * @return the rolesList
	 */
	public List<String> getRolesList() {
		return rolesList;
	}

	/**
	 * @author hfabra
	 * @param rolesList the rolesList to set
	 */
	public void setRolesList(List<String> rolesList) {
		this.rolesList = rolesList;
	}

	/**
	 * @author hfabra
	 * @return the operacionesList
	 */
	public List<OperacionesDTO> getOperacionesList() {
		return operacionesList;
	}

	/**
	 * @author hfabra
	 * @param operacionesList the operacionesList to set
	 */
	public void setOperacionesList(List<OperacionesDTO> operacionesList) {
		this.operacionesList = operacionesList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operacionesRolPK == null) ? 0 : operacionesRolPK.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperacionesRolDTO other = (OperacionesRolDTO) obj;
		if (operacionesRolPK == null) {
			if (other.operacionesRolPK != null)
				return false;
		} else if (!operacionesRolPK.equals(other.operacionesRolPK))
			return false;
		return true;
	}

	/**
	 * @author hfabra
	 * @return the usuario
	 */
	public UsuariosDTO getUsuario() {
		return usuario;
	}

	/**
	 * @author hfabra
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuariosDTO usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "OperacionesRolDTO [roles=" + roles.toString() + ", operaciones=" + operaciones.toString() + "]";
	}

}
