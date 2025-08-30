package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * DAO que contiene la información de la entidad RolesDTO que se transmite por
 * los servicios REST. Solo se transmiten los atributos simples, es decir, se
 * omiten aquellos atributos que definen relaciones con otras entidades.
 * 
 * @author jsoto
 */
@XmlRootElement
public class RolesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal rolId;

	private String nombre;
	private BigDecimal estado;
	private BigDecimal aplicacionId;
	private Timestamp fechaCreacion;
	private Timestamp ultimaModificacion;
	private BigDecimal usuarioModificacion;
	private String path;
	private List<UsuariosRolDTO> usuariosRolDTOs;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public RolesDTO() {
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public BigDecimal getRolId() {
		return this.rolId;
	}

	public void setRolId(BigDecimal rolId) {
		this.rolId = rolId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public BigDecimal getAplicacionId() {
		return this.aplicacionId;
	}

	public void setAplicacionId(BigDecimal aplicacionId) {
		this.aplicacionId = aplicacionId;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getUltimaModificacion() {
		return this.ultimaModificacion;
	}

	public void setUltimaModificacion(Timestamp ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public BigDecimal getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(BigDecimal usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<UsuariosRolDTO> getUsuariosRolDTOs() {
		return usuariosRolDTOs;
	}

	public void setUsuariosRolDTOs(List<UsuariosRolDTO> usuariosRolDTOs) {
		this.usuariosRolDTOs = usuariosRolDTOs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aplicacionId == null) ? 0 : aplicacionId.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((rolId == null) ? 0 : rolId.hashCode());
		result = prime * result + ((ultimaModificacion == null) ? 0 : ultimaModificacion.hashCode());
		result = prime * result + ((usuarioModificacion == null) ? 0 : usuarioModificacion.hashCode());
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
		RolesDTO other = (RolesDTO) obj;
		if (aplicacionId == null) {
			if (other.aplicacionId != null)
				return false;
		} else if (!aplicacionId.equals(other.aplicacionId))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (rolId == null) {
			if (other.rolId != null)
				return false;
		} else if (!rolId.equals(other.rolId))
			return false;
		if (ultimaModificacion == null) {
			if (other.ultimaModificacion != null)
				return false;
		} else if (!ultimaModificacion.equals(other.ultimaModificacion))
			return false;
		if (usuarioModificacion == null) {
			if (other.usuarioModificacion != null)
				return false;
		} else if (!usuarioModificacion.equals(other.usuarioModificacion))
			return false;
		return true;
	}
}
