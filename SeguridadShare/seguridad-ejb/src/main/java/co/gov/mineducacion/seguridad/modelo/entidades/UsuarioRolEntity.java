package co.gov.mineducacion.seguridad.modelo.entidades;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USUARIOS_ROL database table.
 *
 */
@Entity
@Table(name = "USUARIOS_ROL")
@NamedQueries({ @NamedQuery(name = "UsuarioRolEntity.findAll", query = "SELECT p FROM UsuarioRolEntity p"),
		@NamedQuery(name = "UsuarioRolEntity.findUsuariosByIdRol", query = "SELECT p.usuarioId FROM UsuarioRolEntity p where p.rolId = :idRol and p.usuarioId NOT IN (0)") })
@IdClass(UsuariosRolPKEntity.class)
public class UsuarioRolEntity {

	@Id
	@Column(name = "ROL_ID")
	private BigDecimal rolId;

	@Id
	@Column(name = "USUARIO_ID")
	private BigDecimal usuarioId;
	
	@Column(name = "FECHA_VINCULACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVinculacion;
	
	@Column(name = "FECHA_DESVINCULACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDesvinculacion;
	
	@Column(name = "ESTADO_VINCULACION")
	private String estadoVinculacion;

	@Column(name = "MOTIVO_VINCULACION")
	private String motivoVinculacion;

	public BigDecimal getRolId() {
		return rolId;
	}

	public void setRolId(BigDecimal rolId) {
		this.rolId = rolId;
	}

	public BigDecimal getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(BigDecimal usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the fechaVinculacion
	 */
	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}

	/**
	 * @param fechaVinculacion the fechaVinculacion to set
	 */
	public void setFechaVinculacion(Date fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}

	/**
	 * @return the fechaDesvinculacion
	 */
	public Date getFechaDesvinculacion() {
		return fechaDesvinculacion;
	}

	/**
	 * @param fechaDesvinculacion the fechaDesvinculacion to set
	 */
	public void setFechaDesvinculacion(Date fechaDesvinculacion) {
		this.fechaDesvinculacion = fechaDesvinculacion;
	}

	/**
	 * @return the estadoVinculacion
	 */
	public String getEstadoVinculacion() {
		return estadoVinculacion;
	}

	/**
	 * @param estadoVinculacion the estadoVinculacion to set
	 */
	public void setEstadoVinculacion(String estadoVinculacion) {
		this.estadoVinculacion = estadoVinculacion;
	}

	public String getMotivoVinculacion() {
		return motivoVinculacion;
	}

	public void setMotivoVinculacion(String motivoVinculacion) {
		this.motivoVinculacion = motivoVinculacion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estadoVinculacion == null) ? 0 : estadoVinculacion.hashCode());
		result = prime * result + ((fechaDesvinculacion == null) ? 0 : fechaDesvinculacion.hashCode());
		result = prime * result + ((fechaVinculacion == null) ? 0 : fechaVinculacion.hashCode());
		result = prime * result + ((rolId == null) ? 0 : rolId.hashCode());
		result = prime * result + ((usuarioId == null) ? 0 : usuarioId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioRolEntity other = (UsuarioRolEntity) obj;
		if (estadoVinculacion == null) {
			if (other.estadoVinculacion != null)
				return false;
		} else if (!estadoVinculacion.equals(other.estadoVinculacion))
			return false;
		if (fechaDesvinculacion == null) {
			if (other.fechaDesvinculacion != null)
				return false;
		} else if (!fechaDesvinculacion.equals(other.fechaDesvinculacion))
			return false;
		if (fechaVinculacion == null) {
			if (other.fechaVinculacion != null)
				return false;
		} else if (!fechaVinculacion.equals(other.fechaVinculacion))
			return false;
		if (rolId == null) {
			if (other.rolId != null)
				return false;
		} else if (!rolId.equals(other.rolId))
			return false;
		if (usuarioId == null) {
			if (other.usuarioId != null)
				return false;
		} else if (!usuarioId.equals(other.usuarioId))
			return false;
		return true;
	}


}
