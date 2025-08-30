package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import uk.co.jemos.podam.annotations.PodamExclude;

/**
 * TClase persistente que relaciona los usuarios y sus roles.
 *
 */
@Entity
@Table(name = "USUARIOS_ROL")
@NamedQueries({	@NamedQuery(name = "UsuariosRol.findAll", query = "SELECT p FROM UsuariosRol p"),
				@NamedQuery(name = "UsuariosRol.findRolUserByUserApp", 
					query = "SELECT p FROM UsuariosRol p "
							+ " INNER JOIN p.roles r "
							+ " WHERE p.usuariosRolPK.usuarioId = ?1 "
							+ " AND r.aplicacionId = ?2"
							+ " AND r.aplicaciones.estado = ?3"
							+ " AND r.estado = ?4"
							+ " AND p.estadoVinculacion = ?5"),
				@NamedQuery(name = "UsuariosRol.findRolUserByUser", 
					query = "SELECT p FROM UsuariosRol p INNER JOIN p.roles r WHERE p.usuariosRolPK.usuarioId = ?1"
							+ " and r.nombre NOT IN ('GESTIONADOR','AUTENTICADOR')"),
				@NamedQuery(name = "UsuariosRol.findRolUserByRol", 
				query = "SELECT p FROM UsuariosRol p WHERE p.usuariosRolPK.rolId = ?1"),
			})
public class UsuariosRol implements Serializable {

	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_USUARIOS_ROL_PK_ROL_ID = "usuariosRolPK.rolId";
	public static final String ENTIDAD_USUARIOS_ROL_PK_USUARIO_ID = "usuariosRolPK.usuarioId";
	private static final String[] ATRIBUTOS_ENTIDAD_USUARIOS_ROL = { ENTIDAD_USUARIOS_ROL_PK_ROL_ID,
			ENTIDAD_USUARIOS_ROL_PK_USUARIO_ID };

	@EmbeddedId
	private UsuariosRolPK usuariosRolPK;

	@ManyToOne
	@JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID", insertable = false, updatable = false)
	@PodamExclude
	private Roles roles;

	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID", insertable = false, updatable = false)
	@PodamExclude
	private Usuario usuarios;

	@Column(name = "FECHA_VINCULACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVinculacion;
	
	@Column(name = "FECHA_DESVINCULACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDesvinculacion;
	
	@Column(name = "ESTADO_VINCULACION")
	private String estadoVinculacion;
	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public UsuariosRol() {
		usuariosRolPK = new UsuariosRolPK();
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public UsuariosRolPK getUsuariosRolPK() {
		return this.usuariosRolPK;
	}

	public void setUsuariosRolPK(UsuariosRolPK usuariosRolPK) {
		this.usuariosRolPK = usuariosRolPK;
	}

	public Roles getRoles() {
		return this.roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Usuario getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
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

	/**
	 * Verifica si la entidad contiene el atributo que se pasa como parámetro
	 *
	 * @param atributo
	 *            Nombre del atributo a validar
	 * @return Verdadero si la entidad contiene al atributo.
	 */
	public static boolean contieneAtributo(String atributo) {

		boolean contiene = false;
		for (final String atr : ATRIBUTOS_ENTIDAD_USUARIOS_ROL) {
			if (atr.equals(atributo)) {
				contiene = true;
			}
		}

		return contiene;
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
		UsuariosRol other = (UsuariosRol) obj;
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
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		if (usuariosRolPK == null) {
			if (other.usuariosRolPK != null)
				return false;
		} else if (!usuariosRolPK.equals(other.usuariosRolPK))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsuariosRol [usuariosRolPK=" + usuariosRolPK + ", roles=" + roles + ", usuarios=" + usuarios
				+ ", fechaVinculacion=" + fechaVinculacion + ", fechaDesvinculacion=" + fechaDesvinculacion
				+ ", estadoVinculacion=" + estadoVinculacion + "]";
	}	
	
}
