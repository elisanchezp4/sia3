package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contiene informaci�n de entrada de los usuarios a crear, utilizados para los servicios REST de 
 * gesti�n de usuarios.
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class InformacionUsuarioDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055068215395390432L;
	private Long numeroIdentificacion;
	private String tipoIdentificacion;
	private String apellidos;
	private String email;
	private String nombreUsuario;
	private String nombres;
	private String tipo;
	private Integer userId;
	public Long getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(Long numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
