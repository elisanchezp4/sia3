package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EmbeddedId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.JoinColumn;

import uk.co.jemos.podam.annotations.PodamExclude;

/**
 * The persistent class for the CATEGORIES database table.
 * HBT agrega namedQuery
 *
 */
@Entity
@Table(name = "OPERACIONES_ROL")
@NamedQueries({
@NamedQuery(name = "OperacionesRol.findAll", query = "SELECT p FROM OperacionesRol p"),
@NamedQuery(name = "OperacionesRol.findOperacionRolByOperacion", 
query = "SELECT p FROM OperacionesRol p WHERE p.operacionesRolPK.opcionId = ?1"),
@NamedQuery(name = "OperacionesRol.findOperacionRolByRol", 
query = "SELECT p FROM OperacionesRol p WHERE p.operacionesRolPK.rolId = ?1"),
@NamedQuery(name = "OperacionesRol.findRolOperacionesByRol", 
query = "SELECT opr FROM OperacionesRol opr INNER JOIN opr.operaciones op WHERE opr.operacionesRolPK.rolId IN ?1 "
		+ "AND op.aplicacionId = ?2 order by op.ordenVisualizacion"),
})
public class OperacionesRol implements Serializable {

	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_OPERACIONES_ROL_PK_ROL_ID = "operacionesRolPK.rolId";
	public static final String ENTIDAD_OPERACIONES_ROL_PK_USUARIO_ID = "operacionesRolPK.opcionId";
	private static final String[] ATRIBUTOS_ENTIDAD_OPERACIONES_ROL = { ENTIDAD_OPERACIONES_ROL_PK_ROL_ID,
			ENTIDAD_OPERACIONES_ROL_PK_USUARIO_ID };

	@EmbeddedId
	private OperacionesRolPK operacionesRolPK;

	@ManyToOne
	@JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID", insertable = false, updatable = false)
	@PodamExclude
	private Roles roles;

	@ManyToOne
	@JoinColumn(name = "OPCION_ID", referencedColumnName = "OPCION_ID", insertable = false, updatable = false)
	@PodamExclude
	private Operaciones operaciones;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public OperacionesRol() {
		operacionesRolPK = new OperacionesRolPK();
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public OperacionesRolPK getOperacionesRolPK() {
		return this.operacionesRolPK;
	}

	public void setOperacionesRolPK(OperacionesRolPK operacionesRolPK) {
		this.operacionesRolPK = operacionesRolPK;
	}

	public Roles getRoles() {
		return this.roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Operaciones getOperaciones() {
		return this.operaciones;
	}

	public void setOperaciones(Operaciones operaciones) {
		this.operaciones = operaciones;
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
		for (final String atr : ATRIBUTOS_ENTIDAD_OPERACIONES_ROL) {
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
		OperacionesRol other = (OperacionesRol) obj;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (operaciones == null) {
			if (other.operaciones != null)
				return false;
		} else if (!operaciones.equals(other.operaciones))
			return false;
		if (operacionesRolPK == null) {
			if (other.operacionesRolPK != null)
				return false;
		} else if (!operacionesRolPK.equals(other.operacionesRolPK))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OperacionesRol [roles=" + roles + ", operaciones=" + operaciones + "]";
	}

}
