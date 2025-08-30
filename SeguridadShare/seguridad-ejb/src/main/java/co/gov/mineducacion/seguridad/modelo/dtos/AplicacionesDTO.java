package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DAO que contiene la información de la entidad AplicacionesDTO que se
 * transmite por los servicios REST. Solo se transmiten los atributos simples,
 * es decir, se omiten aquellos atributos que definen relaciones con otras
 * entidades.
 * Modificada por HBT para integracion modulo seguridad
 *
 * @author jsoto
 */
@XmlRootElement
public class AplicacionesDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal aplicacionId;

	private String nombre;
	private String descripcion;
	private BigDecimal estado;
	private Timestamp fechaCreacion;
	private Timestamp ultimaModificacion;
	private BigDecimal usuarioModificacion;
	private String urlInicioExitoso;
	private BigDecimal minVigTokenActConstrasenia;
	private BigDecimal minutosVigenciaCodigo;
	private BigDecimal minutosVigenciaToken;

	private BigDecimal recibirNotificacion;


	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public AplicacionesDTO() {
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public BigDecimal getAplicacionId() {
		return this.aplicacionId;
	}

	public void setAplicacionId(BigDecimal aplicacionId) {
		this.aplicacionId = aplicacionId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
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


	/**
	 * @author hfabra
	 * @return the urlInicioExitoso
	 */
	public String getUrlInicioExitoso() {
		return urlInicioExitoso;
	}

	/**
	 * @author hfabra
	 * @param urlInicioExitoso the urlInicioExitoso to set
	 */
	public void setUrlInicioExitoso(String urlInicioExitoso) {
		this.urlInicioExitoso = urlInicioExitoso;
	}


	public BigDecimal getMinVigTokenActConstrasenia() {
		return minVigTokenActConstrasenia;
	}

	public void setMinVigTokenActConstrasenia(BigDecimal minVigTokenActConstrasenia) {
		this.minVigTokenActConstrasenia = minVigTokenActConstrasenia;
	}

	/**
	 * @author hfabra
	 * @return the minutosVigenciaCodigo
	 */
	public BigDecimal getMinutosVigenciaCodigo() {
		return minutosVigenciaCodigo;
	}

	/**
	 * @author hfabra
	 * @param minutosVigenciaCodigo the minutosVigenciaCodigo to set
	 */
	public void setMinutosVigenciaCodigo(BigDecimal minutosVigenciaCodigo) {
		this.minutosVigenciaCodigo = minutosVigenciaCodigo;
	}

	/**
	 * @author hfabra
	 * @return the minutosVigenciaToken
	 */
	public BigDecimal getMinutosVigenciaToken() {
		return minutosVigenciaToken;
	}

	/**
	 * @author hfabra
	 * @param minutosVigenciaToken the minutosVigenciaToken to set
	 */
	public void setMinutosVigenciaToken(BigDecimal minutosVigenciaToken) {
		this.minutosVigenciaToken = minutosVigenciaToken;
	}
	public BigDecimal getRecibirNotificacion() {
		return recibirNotificacion;
	}

	public void setRecibirNotificacion(BigDecimal recibirNotificacion) {
		this.recibirNotificacion = recibirNotificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aplicacionId == null) ? 0 : aplicacionId.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((ultimaModificacion == null) ? 0 : ultimaModificacion.hashCode());
		result = prime * result + ((usuarioModificacion == null) ? 0 : usuarioModificacion.hashCode());
		result = prime * result + ((urlInicioExitoso == null) ? 0 : urlInicioExitoso.hashCode());
		result = prime * result + ((minVigTokenActConstrasenia == null) ? 0 : minVigTokenActConstrasenia.hashCode());
		result = prime * result + ((minutosVigenciaCodigo == null) ? 0 : minutosVigenciaCodigo.hashCode());
		result = prime * result + ((minutosVigenciaToken == null) ? 0 : minutosVigenciaToken.hashCode());
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
		AplicacionesDTO other = (AplicacionesDTO) obj;
		if (aplicacionId == null) {
			if (other.aplicacionId != null)
				return false;
		} else if (!aplicacionId.equals(other.aplicacionId))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
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
		if (urlInicioExitoso == null) {
			if (other.urlInicioExitoso != null)
				return false;
		} else if (!urlInicioExitoso.equals(other.urlInicioExitoso))
			return false;
		if (minVigTokenActConstrasenia == null) {
			if (other.minVigTokenActConstrasenia != null)
				return false;
		} else if (!minVigTokenActConstrasenia.equals(other.minVigTokenActConstrasenia))
			return false;
		if (minutosVigenciaCodigo == null) {
			if (other.minutosVigenciaCodigo != null)
				return false;
		} else if (!minutosVigenciaCodigo.equals(other.minutosVigenciaCodigo))
			return false;
		if (minutosVigenciaToken == null) {
			if (other.minutosVigenciaToken != null)
				return false;
		} else if (!minutosVigenciaToken.equals(other.minutosVigenciaToken))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aplicacion [aplicacionId=" + aplicacionId + ", nombre=" + nombre + ", descripcion=" + descripcion + ", estado=" + estado
				+ ", urlInicioExitoso=" + urlInicioExitoso + ", minVigTokenActConstrasenia=" + minVigTokenActConstrasenia
				+ ", minutosVigenciaCodigo=" + minutosVigenciaCodigo + ", minutosVigenciaToken=" + minutosVigenciaToken
				+ ", fechaCreacion=" + fechaCreacion + ", ultimaModificacion=" + ultimaModificacion
				+ ", usuarioModificacion=" + usuarioModificacion + "]";
	}

}
