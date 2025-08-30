package co.gov.mineducacion.seguridad.modelo.dtos;

import java.io.Serializable;

import co.gov.mineducacion.seguridad.modelo.entidades.PropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;

public class UsuarioAccesibilidadDTO implements Serializable{


	private static final long serialVersionUID = 1L;

	private Long usuarioAccId;
	private Usuario usuarios;
	private PropiedadesAccesibilidad propAcc;
	private String valorPropiedad;
	private Long estado;

	public UsuarioAccesibilidadDTO() {/* Recomendacion por sonar */}

	public Long getUsuarioAccId() {
		return usuarioAccId;
	}

	public void setUsuarioAccId(Long usuarioAccId) {
		this.usuarioAccId = usuarioAccId;
	}

	public Usuario getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}

 

	public String getValorPropiedad() {
		return valorPropiedad;
	}

	public void setValorPropiedad(String valorPropiedad) {
		this.valorPropiedad = valorPropiedad;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public PropiedadesAccesibilidad getPropAcc() {
		return propAcc;
	}

	public void setPropAcc(PropiedadesAccesibilidad propAcc) {
		this.propAcc = propAcc;
	}


	
	
}
