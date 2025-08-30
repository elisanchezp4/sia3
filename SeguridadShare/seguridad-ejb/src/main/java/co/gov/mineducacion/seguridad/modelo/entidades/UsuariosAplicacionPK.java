package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class UsuariosAplicacionPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "USUARIO_ID")
	private String usuarioId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "APLICACION_ID")
	private BigDecimal aplicacionId;
	
	public UsuariosAplicacionPK(){
		
	}
	public UsuariosAplicacionPK(String usuarioId, BigDecimal aplicacionId) {
		this.usuarioId = usuarioId;
		this.aplicacionId = aplicacionId;
	}

	/**
	 * @return the usuarioId
	 */
	public String getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the aplicacionId
	 */
	public BigDecimal getAplicacionId() {
		return aplicacionId;
	}

	/**
	 * @param aplicacionId the aplicacionId to set
	 */
	public void setAplicacionId(BigDecimal aplicacionId) {
		this.aplicacionId = aplicacionId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuarioId == null) ? 0 : usuarioId.hashCode());
		result = prime * result + ((aplicacionId == null) ? 0 : aplicacionId.hashCode());
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
		UsuariosAplicacionPK other = (UsuariosAplicacionPK) obj;
		if (usuarioId == null) {
			if (other.usuarioId != null)
				return false;
		} else if (!usuarioId.equals(other.usuarioId))
			return false;
		if (aplicacionId == null) {
			if (other.aplicacionId != null)
				return false;
		} else if (!aplicacionId.equals(other.aplicacionId))
			return false;
		return true;
	}
	
}
