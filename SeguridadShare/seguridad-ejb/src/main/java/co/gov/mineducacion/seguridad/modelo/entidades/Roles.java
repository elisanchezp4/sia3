package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.JoinColumn;
import uk.co.jemos.podam.annotations.PodamExclude;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the CATEGORIES database table.
 *
 */
@Entity
@Table(name = "ROLES")
@NamedQueries({ @NamedQuery(name = "Roles.findAll", query = "SELECT p FROM Roles p"),
@NamedQuery(name = "Roles.findByNombreRolYAplicacion", query = "SELECT p FROM Roles p WHERE UPPER(p.nombre) = UPPER(:nombre) AND p.aplicacionId = :aplicacionId and p.estado = 1"),
@NamedQuery(name = "Roles.findByNombreRol", query = "SELECT p FROM Roles p WHERE UPPER(p.nombre) = UPPER(:nombre) AND p.estado = 1 ORDER BY p.rolId DESC") })

public class Roles implements Serializable {

	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_ROLES_PK = "rolId";
	public static final String ENTIDAD_ROLES_NOMBRE = "nombre";
	public static final String ENTIDAD_ROLES_ESTADO = "estado";
	public static final String ENTIDAD_ROLES_APLICACION_ID = "aplicacionId";
	public static final String ENTIDAD_ROLES_FECHA_CREACION = "fechaCreacion";
	public static final String ENTIDAD_ROLES_ULTIMA_MODIFICACION = "ultimaModificacion";
	public static final String ENTIDAD_ROLES_USUARIO_MODIFICACION = "usuarioModificacion";
	public static final String ENTIDAD_ROLES_PATH = "path";
	private static final String[] ATRIBUTOS_ENTIDAD_ROLES = { ENTIDAD_ROLES_PK, ENTIDAD_ROLES_NOMBRE,
			ENTIDAD_ROLES_ESTADO, ENTIDAD_ROLES_APLICACION_ID, ENTIDAD_ROLES_ULTIMA_MODIFICACION,
			ENTIDAD_ROLES_USUARIO_MODIFICACION, ENTIDAD_ROLES_FECHA_CREACION, ENTIDAD_ROLES_PATH };

	@Id
	@Column(name = "ROL_ID")
	private BigDecimal rolId;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "RUTA_DIRECTORIO_ACTIVO")
	private String path;

	@PodamExclude
	@Column(name = "APLICACION_ID")
	private BigDecimal aplicacionId;

	@Column(name = "FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name = "ULTIMA_MODIFICACION")
	private Timestamp ultimaModificacion;

	@Column(name = "USUARIO_MODIFICACION")
	private BigDecimal usuarioModificacion;

	@ManyToOne
	@JoinColumn(name = "APLICACION_ID", referencedColumnName = "APLICACION_ID", insertable = false, updatable = false)
	@PodamExclude
	private Aplicaciones aplicaciones;

	@OneToMany(mappedBy = "roles")
	@PodamExclude
	private List<UsuariosRol> usuariosRolList;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public Roles() {
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

	public List<UsuariosRol> getUsuariosRolList() {
		return this.usuariosRolList;
	}

	public void setUsuariosRolList(List<UsuariosRol> usuariosRolList) {
		this.usuariosRolList = usuariosRolList;
	}

	public Aplicaciones getAplicaciones() {
		return this.aplicaciones;
	}

	public void setAplicaciones(Aplicaciones aplicaciones) {
		this.aplicaciones = aplicaciones;
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
		for (final String atr : ATRIBUTOS_ENTIDAD_ROLES) {
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
		Roles other = (Roles) obj;
		if (aplicacionId == null) {
			if (other.aplicacionId != null)
				return false;
		} else if (!aplicacionId.equals(other.aplicacionId))
			return false;
		if (aplicaciones == null) {
			if (other.aplicaciones != null)
				return false;
		} else if (!aplicaciones.equals(other.aplicaciones))
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
		if (usuariosRolList == null) {
			if (other.usuariosRolList != null)
				return false;
		} else if (!usuariosRolList.equals(other.usuariosRolList))
			return false;
		return true;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return "Rol [rolId=" + rolId + ", nombre=" + nombre + ", estado=" + estado
				+ ", aplicacionId=" + aplicacionId + ", fechaCreacion=" + fechaCreacion
				+ ", ultimaModificacion=" + ultimaModificacion + ", usuarioModificacion=" + usuarioModificacion 
				+", path=" + path + "]";
	}


}
