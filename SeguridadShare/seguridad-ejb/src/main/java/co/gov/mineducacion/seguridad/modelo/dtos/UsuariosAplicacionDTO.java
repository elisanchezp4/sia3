package co.gov.mineducacion.seguridad.modelo.dtos;

import java.io.Serializable;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosAplicacionPK;

/**
 * DAO que contiene la informacion de la entidad UsuariosAplicacionDTO que se transmite
 * por los servicios REST. Solo se transmiten los atributos simples, es decir,
 * se omiten aquellos atributos que definen relaciones con otras entidades.
 * 
 * @author jsoto
 * 
 */
public class UsuariosAplicacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuariosAplicacionPK usuariosAplicacionPK;
	private Usuario usuarios;
	private Aplicaciones aplicaciones;

	public UsuariosAplicacionDTO(){
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuariosAplicacionPK == null) ? 0 : usuariosAplicacionPK.hashCode());
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
		UsuariosAplicacionDTO other = (UsuariosAplicacionDTO) obj;
		if (usuariosAplicacionPK == null) {
			if (other.usuariosAplicacionPK != null)
				return false;
		} else if (!usuariosAplicacionPK.equals(other.usuariosAplicacionPK))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuariosAplicacionDTO [usuariosAplicacionPK=" + usuariosAplicacionPK + ", usuarios=" + usuarios
				+ ", aplicaciones=" + aplicaciones + "]";
	}
}
