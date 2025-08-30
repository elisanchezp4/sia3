package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRolPK;

/**
 * DAO que contiene la información de la entidad UsuariosRolDTO que se transmite
 * por los servicios REST. Solo se transmiten los atributos simples, es decir,
 * se omiten aquellos atributos que definen relaciones con otras entidades.
 * 
 * @author jsoto
 */
@XmlRootElement
public class UsuariosRolDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuariosRolPK usuariosRolPK;
	private Roles roles;
	private Usuario usuarios;
	private String rol_id;
	private String usuario_id;
	private String estado_vinculacion;
	private Timestamp fecha_desvinculacion;
	private Timestamp fecha_vinculacion;
	private String estadoVinculacion;
	private Date fechaVinculacion;
	
	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public UsuariosRolDTO() {
		usuariosRolPK = new UsuariosRolPK();
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public UsuariosRolDTO(String rol_id, String usuario_id, String estado_vinculacion, Timestamp fecha_desvinculacion,
			Timestamp fecha_vinculacion) {
		this.rol_id = rol_id;
		this.usuario_id = usuario_id;
		this.estado_vinculacion = estado_vinculacion;
		this.fecha_desvinculacion = fecha_desvinculacion;
		this.fecha_vinculacion = fecha_vinculacion;
	}

	public UsuariosRolPK getUsuariosRolPK() {
		return this.usuariosRolPK;
	}

	public void setUsuariosRolPK(UsuariosRolPK usuariosRolPK) {
		this.usuariosRolPK = usuariosRolPK;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Usuario getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}

	public String getRol_id() {
		return rol_id;
	}

	public void setRol_id(String rol_id) {
		this.rol_id = rol_id;
	}

	public String getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(String usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getEstado_vinculacion() {
		return estado_vinculacion;
	}

	public void setEstado_vinculacion(String estado_vinculacion) {
		this.estado_vinculacion = estado_vinculacion;
	}

	public Timestamp getFecha_desvinculacion() {
		return fecha_desvinculacion;
	}

	public void setFecha_desvinculacion(Timestamp fecha_desvinculacion) {
		this.fecha_desvinculacion = fecha_desvinculacion;
	}

	public Timestamp getFecha_vinculacion() {
		return fecha_vinculacion;
	}

	public void setFecha_vinculacion(Timestamp fecha_vinculacion) {
		this.fecha_vinculacion = fecha_vinculacion;
	}
	
	public String getEstadoVinculacion() {
		return estadoVinculacion;
	}

	public void setEstadoVinculacion(String estadoVinculacion) {
		this.estadoVinculacion = estadoVinculacion;
	}

	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}

	public void setFechaVinculacion(Date fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuariosRolPK == null) ? 0 : usuariosRolPK.hashCode());
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
		UsuariosRolDTO other = (UsuariosRolDTO) obj;
		if (usuariosRolPK == null) {
			if (other.usuariosRolPK != null)
				return false;
		} else if (!usuariosRolPK.equals(other.usuariosRolPK))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuariosRolDTO [usuariosRolPK=" + usuariosRolPK + ", roles=" + roles + ", usuarios=" + usuarios + "]";
	}

}
