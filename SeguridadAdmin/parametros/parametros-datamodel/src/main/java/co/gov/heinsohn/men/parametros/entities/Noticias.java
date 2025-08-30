/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.heinsohn.men.parametros.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa la tabla PA_NOTICIAS
 *
 * @author HEINSOHN BUSINESS TECHNOLOGY – HBT
 */
@Entity
@Table(name = "PA_NOTICIAS")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Noticias.findAll", query = "SELECT n FROM Noticias n"),
		@NamedQuery(name = "Noticias.findById", query = "SELECT n FROM Noticias n WHERE n.id = :id"),
		@NamedQuery(name = "Noticias.findByTitulo", query = "SELECT n FROM Noticias n WHERE n.titulo = :titulo"),
		@NamedQuery(name = "Noticias.findByDescripcion", query = "SELECT n FROM Noticias n WHERE n.descripcion = :descripcion"),
		@NamedQuery(name = "Noticias.findByFechaInicio", query = "SELECT n FROM Noticias n WHERE n.fechaInicio = :fechaInicio"),
		@NamedQuery(name = "Noticias.findByFechaFin", query = "SELECT n FROM Noticias n WHERE n.fechaFin = :fechaFin"),
		@NamedQuery(name = "Noticias.findByEstado", query = "SELECT n FROM Noticias n WHERE n.estado = :estado"),
		@NamedQuery(name = "Noticias.findByFechaPublicacion", query = "SELECT n FROM Noticias n WHERE n.fechaPublicacion = :fechaPublicacion") })
public class Noticias implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private BigDecimal id;
	@Size(max = 100)
	@Column(name = "TITULO")
	private String titulo;
	@Size(max = 500)
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Lob
	@Column(name = "IMAGEN")
	private Serializable imagen;
	@Column(name = "FECHA_INICIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	@Column(name = "FECHA_FIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	@Size(max = 100)
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "FECHA_PUBLICACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPublicacion;

	public Noticias() {
	}

	public Noticias(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Serializable getImagen() {
		return imagen;
	}

	public void setImagen(Serializable imagen) {
		this.imagen = imagen;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Noticias)) {
			return false;
		}
		Noticias other = (Noticias) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "Noticias [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", imagen=" + imagen
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", estado=" + estado
				+ ", fechaPublicacion=" + fechaPublicacion + "]";
	}

}
