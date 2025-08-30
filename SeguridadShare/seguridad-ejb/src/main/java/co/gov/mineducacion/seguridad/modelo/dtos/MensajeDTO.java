package co.gov.mineducacion.seguridad.modelo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * clase que representa un pojo de mensajes
 * Agregada por HBT para manejo de mensajes de la app
 * @author jfonseca
 *
 */
@XmlRootElement
public class MensajeDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** identificador del mensaje */
	private String id;
	/** codigo del mensaje */
	private String codigo;
	/** nombre del mensaje */
	private String nombre;
	/** descripcion del mensaje */
	private String descripcion;
	/** tipo de mensaje */
	private String tipoMensaje;
	/** estado del mensaje */
	private String estado;
	
	public MensajeDTO(){/* Recomendacion por sonar */}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "MensajeDTO [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", tipoMensaje=" + tipoMensaje + ", estado=" + estado + "]";
	}

}
