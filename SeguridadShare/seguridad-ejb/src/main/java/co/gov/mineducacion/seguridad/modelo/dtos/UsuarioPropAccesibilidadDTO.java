package co.gov.mineducacion.seguridad.modelo.dtos;

import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;

public class UsuarioPropAccesibilidadDTO {

	
	private Long usuarioAccId;
	private Usuario usuarios;
	private PropiedadesAccesibilidadDTO propAcc;
	private String valorPropiedad;
	private Long estado;

	
	public UsuarioPropAccesibilidadDTO() {/* Recomendacion por sonar */}


	public Long getEstado() {
		return estado;
	}


	public void setEstado(Long estado) {
		this.estado = estado;
	}


	public String getValorPropiedad() {
		return valorPropiedad;
	}


	public void setValorPropiedad(String valorPropiedad) {
		this.valorPropiedad = valorPropiedad;
	}


	public PropiedadesAccesibilidadDTO getPropAcc() {
		return propAcc;
	}


	public void setPropAcc(PropiedadesAccesibilidadDTO propAcc) {
		this.propAcc = propAcc;
	}


	public Usuario getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}


	public Long getUsuarioAccId() {
		return usuarioAccId;
	}


	public void setUsuarioAccId(Long usuarioAccId) {
		this.usuarioAccId = usuarioAccId;
	}
}
