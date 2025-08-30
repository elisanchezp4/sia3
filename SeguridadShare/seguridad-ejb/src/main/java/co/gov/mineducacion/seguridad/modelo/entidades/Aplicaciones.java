package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the CATEGORIES database table.
 * Modificada por HBT para integracion modulo de seguridad
 *
 */
@Entity
@Table(name = "APLICACIONES")
@NamedQuery(name = "Aplicaciones.findAll", query = "SELECT p FROM Aplicaciones p")
public class Aplicaciones implements Serializable {

	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_APLICACIONES_PK = "aplicacionId";
	public static final String ENTIDAD_APLICACIONES_NOMBRE = "nombre";
	public static final String ENTIDAD_APLICACIONES_DESCRIPCION = "descripcion";
	public static final String ENTIDAD_APLICACIONES_ESTADO = "estado";
	public static final String ENTIDAD_APLICACIONES_FECHA_CREACION = "fechaCreacion";
	public static final String ENTIDAD_APLICACIONES_ULTIMA_MODIFICACION = "ultimaModificacion";
	public static final String ENTIDAD_APLICACIONES_USUARIO_MODIFICACION = "usuarioModificacion";
	public static final String ENTIDAD_APLICACIONES_MIN_VIG_TOKEN_ACT_CONTRA = "minVigTokenActConstrasenia";
	public static final String ENTIDAD_APLICACIONES_RECIBIR_NOTIFICACION = "recibirNotificacion";
	private static final String[] ATRIBUTOS_ENTIDAD_APLICACIONES = { ENTIDAD_APLICACIONES_DESCRIPCION,
			ENTIDAD_APLICACIONES_FECHA_CREACION, ENTIDAD_APLICACIONES_ESTADO, ENTIDAD_APLICACIONES_ULTIMA_MODIFICACION,
			ENTIDAD_APLICACIONES_PK, ENTIDAD_APLICACIONES_NOMBRE, ENTIDAD_APLICACIONES_USUARIO_MODIFICACION,
			ENTIDAD_APLICACIONES_MIN_VIG_TOKEN_ACT_CONTRA,ENTIDAD_APLICACIONES_RECIBIR_NOTIFICACION};

	@Id
	@Column(name = "APLICACION_ID")
	private BigDecimal aplicacionId;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name = "ULTIMA_MODIFICACION")
	private Timestamp ultimaModificacion;

	@Column(name = "USUARIO_MODIFICACION")
	private BigDecimal usuarioModificacion;

	@Column(name = "URI_INICIO_EXITOSO")
	private String urlInicioExitoso;

	@Column(name = "MIN_VIG_TOKEN_ACT_CONTRASENIA")
	private BigDecimal minVigTokenActConstrasenia;

	@Column(name = "MINUTOS_VIGENCIA_CODIGO")
	private BigDecimal minutosVigenciaCodigo;

	@Column(name = "MINUTOS_VIGENCIA_TOKEN")
	private BigDecimal minutosVigenciaToken;

	@Column(name = "RECIBIR_NOTIFICACION")
	private BigDecimal recibirNotificacion;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public Aplicaciones() {
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

	public String getUrlInicioExitoso() {
		return urlInicioExitoso;
	}

	public void setUrlInicioExitoso(String urlInicioExitoso) {
		this.urlInicioExitoso = urlInicioExitoso;
	}


	public BigDecimal getMinVigTokenActConstrasenia() {
		return minVigTokenActConstrasenia;
	}

	public void setMinVigTokenActConstrasenia(BigDecimal minVigTokenActConstrasenia) {
		this.minVigTokenActConstrasenia = minVigTokenActConstrasenia;
	}

	public BigDecimal getMinutosVigenciaCodigo() {
		return minutosVigenciaCodigo;
	}

	public void setMinutosVigenciaCodigo(BigDecimal minutosVigenciaCodigo) {
		this.minutosVigenciaCodigo = minutosVigenciaCodigo;
	}

	public BigDecimal getMinutosVigenciaToken() {
		return minutosVigenciaToken;
	}

	public void setMinutosVigenciaToken(BigDecimal minutosVigenciaToken) {
		this.minutosVigenciaToken = minutosVigenciaToken;
	}

	public BigDecimal getRecibirNotificacion() {
		return recibirNotificacion;
	}

	public void setRecibirNotificacion(BigDecimal recibirNotificacion) {
		this.recibirNotificacion = recibirNotificacion;
	}

	/**
	 * Verifica si la entidad contiene el atributo que se pasa como parámetro
	 *
	 * @param atributo
	 *            Nombre del atributo a validar
	 * @return Verdadero si la entidad contiene al atributo.
	 */
	public static boolean contieneAtributo(String atributo) {

		boolean contiene = false;
		for (final String atr : ATRIBUTOS_ENTIDAD_APLICACIONES) {
			if (atr.equals(atributo)) {
				contiene = true;
			}
		}

		return contiene;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aplicaciones other = (Aplicaciones) obj;
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
		if (this.minVigTokenActConstrasenia == null) {
			if (other.minVigTokenActConstrasenia != null)
				return false;
		} else if (!this.minVigTokenActConstrasenia.equals(other.minVigTokenActConstrasenia))
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
		return "Aplicacion [aplicacionId=" + aplicacionId + ", estado=" + estado + ", fechaCreacion=" + fechaCreacion
				+ ", minutosVigenciaCodigo=" + minutosVigenciaCodigo + ", minutosVigenciaToken=" + minutosVigenciaToken
				+ ", nombre=" + nombre + ", ultimaModificacion=" + ultimaModificacion + ", urlInicioExitoso="
				+ urlInicioExitoso + ", minVigTokenActConstrasenia=" + minVigTokenActConstrasenia + ", usuarioModificacion="
				+ usuarioModificacion + "]";
	}


}
