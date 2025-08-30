/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *Agregada por HBT para el manejo de mensajes en la aplicacion
 * @author jfonseca
 */
@Entity
@Table(name = "MENSAJE")
@NamedQueries({ @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m"),
		@NamedQuery(name = "Mensaje.findById", query = "SELECT m FROM Mensaje m WHERE m.id = :id"),
//		@NamedQuery(name = Mensaje.ACTUALIZAR_MENSAJE, query = "UPDATE Mensaje m SET m.descripcion= :descripcion, m.estado= :estado WHERE m.codigo= :codigo"),
		@NamedQuery(name = "Mensaje.findByDescripcion", query = "SELECT m FROM Mensaje m WHERE m.descripcion = :descripcion"),
		@NamedQuery(name = "Mensaje.findByTipoMensaje", query = "SELECT m FROM Mensaje m WHERE m.tipoMensaje = :tipoMensaje"),
		@NamedQuery(name = "Mensaje.findByEstado", query = "SELECT m FROM Mensaje m WHERE m.estado = :estado"),
		@NamedQuery(name = Mensaje.BUSCAR_POR_CODIGO, query = "SELECT m FROM Mensaje m WHERE m.codigo = :codigo") })
public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BUSCAR_POR_CODIGO = "Mensaje.findByCodigo";

	// consider using these annotations to enforce field validation
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	@GeneratedValue(generator="my_seq_mensaje")
	@SequenceGenerator(name="my_seq_mensaje",sequenceName="SEQ_MENSAJE", allocationSize=1)
	private Long id;
	@Size(max = 100)
	@Column(name = "NOMBRE")
	private String nombre;
	@Size(max = 255)
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Size(max = 100)
	@Column(name = "TIPO_MENSAJE")
	private String tipoMensaje;
	@Size(max = 100)
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "CODIGO")
	private String codigo;

	public Mensaje() {/* Recomendacion por sonar */}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Mensaje)) {
			return false;
		}
		Mensaje other = (Mensaje) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Mensaje{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", tipoMensaje="
				+ tipoMensaje + ", estado=" + estado + ", codigo=" + codigo + '}';
	}

}
