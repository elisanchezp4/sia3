package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuariosRolPKEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8911038859574301860L;

	@Column
	private BigDecimal rolId;

	@Column
	private BigDecimal usuarioId;

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

	public UsuariosRolPKEntity(BigDecimal rolId, BigDecimal usuarioId) {
		super();
		this.rolId = rolId;
		this.usuarioId = usuarioId;
	}

	public UsuariosRolPKEntity() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rolId == null) ? 0 : rolId.hashCode());
		result = prime * result + ((usuarioId == null) ? 0 : usuarioId.hashCode());
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
		UsuariosRolPKEntity other = (UsuariosRolPKEntity) obj;
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
