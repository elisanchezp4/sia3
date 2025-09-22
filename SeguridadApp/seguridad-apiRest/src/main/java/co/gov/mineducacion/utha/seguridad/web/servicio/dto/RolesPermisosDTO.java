package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Contiene informaci�n sobre los permisos (normalmente opciones del men�) que tiene
 * un usuario en una aplicaci�n
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class RolesPermisosDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 588273723054533032L;

	InformacionPermisosDTO permisos;
	
	UsuarioDTO usuario;

	String tokenAcceso;

	private Date fechaValidacionRnec;
	private Date fechaExpedicionDocumento;
	private String estadoValidacion;
	private String motivoValidacion;

	public InformacionPermisosDTO getPermisos() {
		return permisos;
	}

	public void setPermisos(InformacionPermisosDTO permisos) {
		this.permisos = permisos;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public String getTokenAcceso() {
		return tokenAcceso;
	}

	public void setTokenAcceso(String tokenAcceso) {
		this.tokenAcceso = tokenAcceso;
	}

	public Date getFechaValidacionRnec() {
		return fechaValidacionRnec;
	}

	public void setFechaValidacionRnec(Date fechaValidacionRnec) {
		this.fechaValidacionRnec = fechaValidacionRnec;
	}

	public Date getFechaExpedicionDocumento() {
		return fechaExpedicionDocumento;
	}

	public void setFechaExpedicionDocumento(Date fechaExpedicionDocumento) {
		this.fechaExpedicionDocumento = fechaExpedicionDocumento;
	}

	public String getEstadoValidacion() {
		return estadoValidacion;
	}

	public void setEstadoValidacion(String estadoValidacion) {
		this.estadoValidacion = estadoValidacion;
	}

	public String getMotivoValidacion() {
		return motivoValidacion;
	}

	public void setMotivoValidacion(String motivoValidacion) {
		this.motivoValidacion = motivoValidacion;
	}
}
