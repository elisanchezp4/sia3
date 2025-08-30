package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Almacena informaci�n b�sica del usuario registrado en el sistema de seguridad
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class UsuarioDTO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6145797095827980348L;

	private String apellidos;

	private String nombres;

	private Integer userId;

	private String logonName;

	private String nombreUsuario;

	private String numeroDocumento;

	private String emailUsuario;

	private String loginUsuario;

	private BigDecimal tipo;

	private Date fechaCreacion;

	private Date ultimaModificacion;
    
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public BigDecimal getTipo() {
		return tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}
}
