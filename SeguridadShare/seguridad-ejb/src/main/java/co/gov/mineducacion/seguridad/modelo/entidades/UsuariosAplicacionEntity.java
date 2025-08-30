package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Clase persistente para la tabla USUARIO_APLICACION
 * @author Administrador
 *
 */
@Entity
@Table(name = "USUARIO_APLICACION")
@IdClass(UsuariosAplicacionEntity.class)
public class UsuariosAplicacionEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USUARIO_ID")
	private BigDecimal usuarioId;

	@Id
	@Column(name = "APLICACION_ID")
	private BigDecimal aplicacionId;

	/**
	 * @return the usuarioId
	 */
	public BigDecimal getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(BigDecimal usuarioId) {
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
		UsuariosAplicacionEntity other = (UsuariosAplicacionEntity) obj;
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
