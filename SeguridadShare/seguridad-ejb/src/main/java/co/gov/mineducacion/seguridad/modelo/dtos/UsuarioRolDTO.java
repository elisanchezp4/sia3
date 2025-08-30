package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DAO que contiene la información de la entidad UsuarioRolDTO que se transmite
 * por los servicios REST. Solo se transmiten los atributos simples, es decir,
 * se omiten aquellos atributos que definen relaciones con otras entidades.
 * 
 * @author jvera
 */
@XmlRootElement
public class UsuarioRolDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal rol_id;
	private BigDecimal aplicacion_id;
	private String nombre;
	private String estado_vinculacion;

	public BigDecimal getRol_id() {
		return rol_id;
	}

	public void setRol_id(BigDecimal rol_id) {
		this.rol_id = rol_id;
	}

	public BigDecimal getAplicacion_id() {
		return aplicacion_id;
	}

	public void setAplicacion_id(BigDecimal aplicacion_id) {
		this.aplicacion_id = aplicacion_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado_vinculacion() {
		return estado_vinculacion;
	}

	public void setEstado_vinculacion(String estado_vinculacion) {
		this.estado_vinculacion = estado_vinculacion;
	}

	public UsuarioRolDTO(BigDecimal rol_id, BigDecimal aplicacion_id, String nombre, String estado_vinculacion) {
		this.rol_id = rol_id;
		this.aplicacion_id = aplicacion_id;
		this.nombre = nombre;
		this.estado_vinculacion = estado_vinculacion;
	}
}
