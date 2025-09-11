package co.gov.mineducacion.seguridad.modelo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import uk.co.jemos.podam.annotations.PodamExclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.jemos.podam.annotations.PodamExclude;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import uk.co.jemos.podam.annotations.PodamExclude;

/**
 * The persistent class for the USUARIOS database table.
 *
 */
@Entity
@Table(name = "USUARIOS")
@NamedQueries({ @NamedQuery(name = "Usuarios.findAll", query = "SELECT p FROM Usuario p"),
		@NamedQuery(name = "Usuario.findUsuariosByIdUsuario", query = "SELECT p FROM Usuario p where p.usuarioId =:idUsuario"),
		@NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT p FROM Usuario p where p.logonName = :nombreUsuario and p.estado = 1") })
@Builder(toBuilder = true)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_USUARIOS_PK = "usuarioId";
	public static final String ENTIDAD_USUARIOS_RUTA = "ruta";
	public static final String ENTIDAD_USUARIOS_TIPO = "tipo";
	public static final String ENTIDAD_USUARIOS_ESTADO = "estado";
	public static final String ENTIDAD_USUARIOS_FECHA_CREACION = "fechaCreacion";
	public static final String ENTIDAD_USUARIOS_ULTIMA_MODIFICACION = "ultimaModificacion";
	public static final String ENTIDAD_USUARIOS_USUARIO_MODIFICACION = "usuarioModificacion";
	public static final String ENTIDAD_USUARIOS_LOGON_NAME = "logonName";
	private static final String[] ATRIBUTOS_ENTIDAD_USUARIOS = { ENTIDAD_USUARIOS_TIPO, ENTIDAD_USUARIOS_ESTADO,
			ENTIDAD_USUARIOS_PK, ENTIDAD_USUARIOS_FECHA_CREACION, ENTIDAD_USUARIOS_ULTIMA_MODIFICACION,
			ENTIDAD_USUARIOS_RUTA, ENTIDAD_USUARIOS_USUARIO_MODIFICACION, ENTIDAD_USUARIOS_LOGON_NAME };

	@Id
	@Column(name = "USUARIO_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuariosSec")
	@SequenceGenerator(name = "usuariosSec", sequenceName = "SEQ_USUARIOS_USUARIO_ID", allocationSize = 1)
	private String usuarioId;

	@Column(name = "RUTA")
	private String ruta;

	@Column(name = "NUEVO_PASSWORD")
	private String nuevoPass;

	@Column(name = "CONTRASENA_HASH", length = 2000)
	private String passwordHash;

	@Column(name = "TIPO")
	private BigDecimal tipo;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "FECHA_CREACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "ULTIMA_MODIFICACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimaModificacion;

	@Column(name = "USUARIO_MODIFICACION")
	private String usuarioModificacion;

	@Column(name = "LOGON_NAME")
	private String logonName;

	@Column(name = "NOMBRE_USUARIO")
	private String nombreUsuario;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@OneToMany(mappedBy = "usuarios")
	@PodamExclude
	@JsonIgnore
	private List<BitacoraAuditoria> bitacoraAuditoriaList;
	@OneToMany(mappedBy = "usuarios")
	@PodamExclude
	@JsonIgnore
	private List<TokensActivos> tokensActivosList;
	@OneToMany(mappedBy = "usuarios")
	@PodamExclude
	@JsonIgnore
	private List<UsuariosRol> usuariosRolList;

	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private InformacionAdicionalUsuario informacionAdicional;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public Usuario() {
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public String getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public BigDecimal getTipo() {
		return this.tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getUltimaModificacion() {
		return this.ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public String getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	/**
	 * @author hfabra
	 * @return the logonName
	 */
	public String getLogonName() {
		return logonName;
	}

	/**
	 * @author hfabra
	 * @param logonName
	 *            the logonName to set
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public List<BitacoraAuditoria> getBitacoraAuditoriaList() {
		return this.bitacoraAuditoriaList;
	}

	public void setBitacoraAuditoriaList(List<BitacoraAuditoria> bitacoraAuditoriaList) {
		this.bitacoraAuditoriaList = bitacoraAuditoriaList;
	}

	public List<TokensActivos> getTokensActivosList() {
		return this.tokensActivosList;
	}

	public void setTokensActivosList(List<TokensActivos> tokensActivosList) {
		this.tokensActivosList = tokensActivosList;
	}

	public List<UsuariosRol> getUsuariosRolList() {
		return this.usuariosRolList;
	}

	public void setUsuariosRolList(List<UsuariosRol> usuariosRolList) {
		this.usuariosRolList = usuariosRolList;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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
		for (final String atr : ATRIBUTOS_ENTIDAD_USUARIOS) {
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
		Usuario other = (Usuario) obj;
		if (bitacoraAuditoriaList == null) {
			if (other.bitacoraAuditoriaList != null)
				return false;
		} else if (!bitacoraAuditoriaList.equals(other.bitacoraAuditoriaList))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
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
		if (tokensActivosList == null) {
			if (other.tokensActivosList != null)
				return false;
		} else if (!tokensActivosList.equals(other.tokensActivosList))
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
		if (usuariosRolList == null) {
			if (other.usuariosRolList != null)
				return false;
		} else if (!usuariosRolList.equals(other.usuariosRolList))
			return false;
		return true;
	}

	public String getNuevoPass() {
		return nuevoPass;
	}

	public void setNuevoPass(String nuevoPass) {
		this.nuevoPass = nuevoPass;
	}

	public InformacionAdicionalUsuario getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(InformacionAdicionalUsuario informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	@Override
	public String toString() {
		return "Usuarios [usuarioId=" + usuarioId + ", ruta=" + ruta+ ", nuevoPass=" + nuevoPass+ ", tipo=" + tipo+ ", estado=" + estado
				+ ", fechaCreacion=" + fechaCreacion+ ", ultimaModificacion=" + ultimaModificacion+ ", usuarioModificacion=" + usuarioModificacion+ ", logonName=" + logonName+ ", usuariosRolList=" + usuariosRolList
				+  "]";
	}
	
}
