package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Almacena la cabecera de seguridad en una solicitud a los servicos REST
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class HeaderDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7277809476720928449L;

	/**
	 * direcci�n ip o nombre del cliente que realiza la solicitud
	 */
	String ipHost;
	
	/**
	 * fecha en que se est� realiza la solicitud
	 */
	Timestamp fechaPeticion;
	
	/**
	 * identificador de la aplicaci�n en el sistema de seguridad
	 */
	String apiKey;
	
	/**
	 * Identificador del usuario en el sistema de seguridad
	 */
	Integer userId;

	Integer userToModify;

	String email;

	String numeroDocumento;

	String apellidos;

	String nombres;

	String nombreCompleto;

	String rutaDirectorio;

	String password;

	Boolean notificarUsuario;

	public String getIpHost() {
		return ipHost;
	}

	public void setIpHost(String ipHost) {
		this.ipHost = ipHost;
	}

	public Timestamp getFechaPeticion() {
		return fechaPeticion;
	}

	public void setFechaPeticion(Timestamp fechaPeticion) {
		this.fechaPeticion = fechaPeticion;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getUserToModify() {
		return userToModify;
	}

	public void setUserToModify(Integer userToModify) {
		this.userToModify = userToModify;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getRutaDirectorio() {
		return rutaDirectorio;
	}

	public void setRutaDirectorio(String rutaDirectorio) {
		this.rutaDirectorio = rutaDirectorio;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Boolean getNotificarUsuario() {
		return notificarUsuario;
	}

	public void setNotificarUsuario(Boolean notificarUsuario) {
		this.notificarUsuario = notificarUsuario;
	}
}
