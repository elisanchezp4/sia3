package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Embeddable
public class UsuariosRolPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Column(name = "ROL_ID")
	private BigDecimal rolId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "USUARIO_ID")
	private String usuarioId;

	public UsuariosRolPK() {

	}

	public UsuariosRolPK(BigDecimal rolId, String usuarioId) {
		this.rolId = rolId;
		this.usuarioId = usuarioId;
	}

	public BigDecimal getRolId() {
		return this.rolId;
	}

	public void setRolId(BigDecimal rolId) {
		this.rolId = rolId;
	}

	public String getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
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
		UsuariosRolPK other = (UsuariosRolPK) obj;
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
