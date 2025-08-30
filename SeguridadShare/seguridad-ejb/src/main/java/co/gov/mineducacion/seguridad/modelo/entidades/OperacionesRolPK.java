package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Embeddable
public class OperacionesRolPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Column(name = "ROL_ID")

	private BigDecimal rolId;
	@Basic(optional = false)
	@NotNull
	@Column(name = "OPCION_ID")

	private String opcionId;

	public OperacionesRolPK() {

	}

	public OperacionesRolPK(BigDecimal rolId, String opcionId) {
		this.rolId = rolId;
		this.opcionId = opcionId;
	}

	public BigDecimal getRolId() {
		return this.rolId;
	}

	public void setRolId(BigDecimal rolId) {
		this.rolId = rolId;
	}

	public String getOpcionId() {
		return this.opcionId;
	}

	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rolId == null) ? 0 : rolId.hashCode());
		result = prime * result + ((opcionId == null) ? 0 : opcionId.hashCode());
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
		OperacionesRolPK other = (OperacionesRolPK) obj;
		if (rolId == null) {
			if (other.rolId != null)
				return false;
		} else if (!rolId.equals(other.rolId))
			return false;
		if (opcionId == null) {
			if (other.opcionId != null)
				return false;
		} else if (!opcionId.equals(other.opcionId))
			return false;
		return true;
	}

}
