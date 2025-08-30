package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * DAO que contiene la informacion de la entidad UsuariosDTO que se transmite
 * por los servicios REST. Solo se transmiten los atributos simples, es decir,
 * se omiten aquellos atributos que definen relaciones con otras entidades.
 * 
 * @author jsoto
 * 
 * @Hbt se agrega el atributo nombreRol lineas afectadas 45, 280-292
 * 
 */
@XmlRootElement
public class UsuariosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2655195869444560334L;

	private String usuarioId;

	private String nuevoPass;
	private String ruta;
	private BigDecimal tipo;
	private BigDecimal estado;
	private Date fechaCreacion;
	private Date ultimaModificacion;
	private String usuarioModificacion;
	private String loginUsuario;
	private String apellidosUsuario;
	private String emailUsuario;
	private String nombres;
	private String password;
	private String logonName;
	private String nombreUsuario;
	private String numeroDocumento;
	private String nombreRol;

	private List<RolesDTO> roles;
	private String adressLocal;
	private String portLocal;
	// Modificacion Cambios Solicitados Certificación
	private Date fechaVinculacion;
	private Date fechaDesvinculacion;
	private String estadoVinculacion;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public UsuariosDTO() {
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	/**
	 * @return the usuarioId
	 */
	public String getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId
	 *            the usuarioId to set
	 */
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the nuevoPass
	 */
	public String getNuevoPass() {
		return nuevoPass;
	}

	/**
	 * @param nuevoPass
	 *            the nuevoPass to set
	 */
	public void setNuevoPass(String nuevoPass) {
		this.nuevoPass = nuevoPass;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @param ruta
	 *            the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	/**
	 * @return the tipo
	 */
	public BigDecimal getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the estado
	 */
	public BigDecimal getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the ultimaModificacion
	 */
	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	/**
	 * @param ultimaModificacion
	 *            the ultimaModificacion to set
	 */
	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	/**
	 * @return the usuarioModificacion
	 */
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	/**
	 * @param usuarioModificacion
	 *            the usuarioModificacion to set
	 */
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	/**
	 * @return the loginUsuario
	 */
	public String getLoginUsuario() {
		return loginUsuario;
	}

	/**
	 * @param loginUsuario
	 *            the loginUsuario to set
	 */
	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	/**
	 * @return the apellidosUsuario
	 */
	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	/**
	 * @param apellidosUsuario
	 *            the apellidosUsuario to set
	 */
	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	/**
	 * @return the emailUsuario
	 */
	public String getEmailUsuario() {
		return emailUsuario;
	}

	/**
	 * @param emailUsuario
	 *            the emailUsuario to set
	 */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres
	 *            the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the logonName
	 */
	public String getLogonName() {
		return logonName;
	}

	/**
	 * @param logonName
	 *            the logonName to set
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento
	 *            the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * @return the nombreRol
	 */
	public String getNombreRol() {
		return nombreRol;
	}

	/**
	 * @param nombreRol
	 *            the nombreRol to set
	 */
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	/**
	 * @return the fechaVinculacion
	 */
	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}

	/**
	 * @param fechaVinculacion
	 *            the fechaVinculacion to set
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
	 * @param fechaDesvinculacion
	 *            the fechaDesvinculacion to set
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
	 * @param estadoVinculacion
	 *            the estadoVinculacion to set
	 */
	public void setEstadoVinculacion(String estadoVinculacion) {
		this.estadoVinculacion = estadoVinculacion;
	}

	public String getAdressLocal() {
		return adressLocal;
	}

	public void setAdressLocal(String adressLocal) {
		this.adressLocal = adressLocal;
	}

	public String getPortLocal() {
		return portLocal;
	}

	public void setPortLocal(String portLocal) {
		this.portLocal = portLocal;
	}

	public List<RolesDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesDTO> roles) {
		this.roles = roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidosUsuario == null) ? 0 : apellidosUsuario.hashCode());
		result = prime * result + ((emailUsuario == null) ? 0 : emailUsuario.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((estadoVinculacion == null) ? 0 : estadoVinculacion.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((fechaDesvinculacion == null) ? 0 : fechaDesvinculacion.hashCode());
		result = prime * result + ((fechaVinculacion == null) ? 0 : fechaVinculacion.hashCode());
		result = prime * result + ((loginUsuario == null) ? 0 : loginUsuario.hashCode());
		result = prime * result + ((logonName == null) ? 0 : logonName.hashCode());
		result = prime * result + ((nombreRol == null) ? 0 : nombreRol.hashCode());
		result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
		result = prime * result + ((nuevoPass == null) ? 0 : nuevoPass.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((ultimaModificacion == null) ? 0 : ultimaModificacion.hashCode());
		result = prime * result + ((usuarioId == null) ? 0 : usuarioId.hashCode());
		result = prime * result + ((usuarioModificacion == null) ? 0 : usuarioModificacion.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		UsuariosDTO other = (UsuariosDTO) obj;
		if (apellidosUsuario == null) {
			if (other.apellidosUsuario != null)
				return false;
		} else if (!apellidosUsuario.equals(other.apellidosUsuario))
			return false;
		if (emailUsuario == null) {
			if (other.emailUsuario != null)
				return false;
		} else if (!emailUsuario.equals(other.emailUsuario))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (estadoVinculacion == null) {
			if (other.estadoVinculacion != null)
				return false;
		} else if (!estadoVinculacion.equals(other.estadoVinculacion))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
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
		if (loginUsuario == null) {
			if (other.loginUsuario != null)
				return false;
		} else if (!loginUsuario.equals(other.loginUsuario))
			return false;
		if (logonName == null) {
			if (other.logonName != null)
				return false;
		} else if (!logonName.equals(other.logonName))
			return false;
		if (nombreRol == null) {
			if (other.nombreRol != null)
				return false;
		} else if (!nombreRol.equals(other.nombreRol))
			return false;
		if (nombreUsuario == null) {
			if (other.nombreUsuario != null)
				return false;
		} else if (!nombreUsuario.equals(other.nombreUsuario))
			return false;
		if (nombres == null) {
			if (other.nombres != null)
				return false;
		} else if (!nombres.equals(other.nombres))
			return false;
		if (nuevoPass == null) {
			if (other.nuevoPass != null)
				return false;
		} else if (!nuevoPass.equals(other.nuevoPass))
			return false;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (ultimaModificacion == null) {
			if (other.ultimaModificacion != null)
				return false;
		} else if (!ultimaModificacion.equals(other.ultimaModificacion))
			return false;
		if (usuarioId == null) {
			if (other.usuarioId != null)
				return false;
		} else if (!usuarioId.equals(other.usuarioId))
			return false;
		if (usuarioModificacion == null) {
			if (other.usuarioModificacion != null)
				return false;
		} else if (!usuarioModificacion.equals(other.usuarioModificacion))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsuariosDTO [usuarioId=" + usuarioId + ", nuevoPass=" + nuevoPass + ", ruta=" + ruta + ", tipo=" + tipo
				+ ", estado=" + estado + ", fechaCreacion=" + fechaCreacion + ", ultimaModificacion="
				+ ultimaModificacion + ", usuarioModificacion=" + usuarioModificacion + ", loginUsuario=" + loginUsuario
				+ ", apellidosUsuario=" + apellidosUsuario + ", emailUsuario=" + emailUsuario + ", nombres=" + nombres
				+ ", password=" + password + ", logonName=" + logonName + ", nombreUsuario=" + nombreUsuario
				+ ", numeroDocumento=" + numeroDocumento + ", nombreRol=" + nombreRol + ", fechaVinculacion="
				+ fechaVinculacion + ", fechaDesvinculacion=" + fechaDesvinculacion + ", estadoVinculacion="
				+ estadoVinculacion + "]";
	}

}
