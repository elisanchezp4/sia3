package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import uk.co.jemos.podam.annotations.PodamExclude;

/**
 * Clase persistente que relaciona los usuarios y sus aplicaciones.
 *
 */
@Entity
@Table(name = "USUARIO_APLICACION")
@NamedQueries({	@NamedQuery(name = "UsuariosAplicacion.findAll", query = "SELECT p FROM UsuariosAplicacion p"),
})
public class UsuariosAplicacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_USUARIOS_APP_PK_USUARIO_ID = "usuariosAplicacionPK.usuarioId";
	public static final String ENTIDAD_USUARIOS_APP_PK_APLICACION_ID = "usuariosAplicacionPK.aplicacionId";
	private static final String[] ATRIBUTOS_ENTIDAD_USUARIOS_APP = { ENTIDAD_USUARIOS_APP_PK_USUARIO_ID,
			ENTIDAD_USUARIOS_APP_PK_APLICACION_ID };

	@EmbeddedId
	private UsuariosAplicacionPK usuariosAplicacionPK;

	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID", insertable = false, updatable = false)
	@PodamExclude
	private Usuario usuarios;

	@ManyToOne
	@JoinColumn(name = "APLICACION_ID", referencedColumnName = "APLICACION_ID", insertable = false, updatable = false)
	@PodamExclude
	private Aplicaciones aplicaciones;

	public UsuariosAplicacion() {
		usuariosAplicacionPK = new UsuariosAplicacionPK();
	}

	/**
	 * @return the usuariosAplicacionPK
	 */
	public UsuariosAplicacionPK getUsuariosAplicacionPK() {
		return usuariosAplicacionPK;
	}

	/**
	 * @param usuariosAplicacionPK the usuariosAplicacionPK to set
	 */
	public void setUsuariosAplicacionPK(UsuariosAplicacionPK usuariosAplicacionPK) {
		this.usuariosAplicacionPK = usuariosAplicacionPK;
	}

	/**
	 * @return the usuarios
	 */
	public Usuario getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the aplicaciones
	 */
	public Aplicaciones getAplicaciones() {
		return aplicaciones;
	}

	/**
	 * @param aplicaciones the aplicaciones to set
	 */
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
		for (final String atr : ATRIBUTOS_ENTIDAD_USUARIOS_APP) {
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
		UsuariosAplicacion other = (UsuariosAplicacion) obj;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		if (aplicaciones == null) {
			if (other.aplicaciones != null)
				return false;
		} else if (!aplicaciones.equals(other.aplicaciones))
			return false;
		if (usuariosAplicacionPK == null) {
			if (other.usuariosAplicacionPK != null)
				return false;
		} else if (!usuariosAplicacionPK.equals(other.usuariosAplicacionPK))
			return false;
		return true;
	}
}
