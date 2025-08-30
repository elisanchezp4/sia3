/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.heinsohn.men.parametros.entities;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa la tabla PA_PARAMETRO
 *
 * @author HEINSOHN BUSINESS TECHNOLOGY – HBT
 */
@Entity
@Table(name = "PA_PARAMETRO")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p"),
		@NamedQuery(name = "Parametro.findById", query = "SELECT p FROM Parametro p WHERE p.id = :id"),
		@NamedQuery(name = "Parametro.findByAplicacion", query = "SELECT p FROM Parametro p WHERE p.aplicacion = :aplicacion"),
		@NamedQuery(name = "Parametro.findByDominioPadre", query = "SELECT p FROM Parametro p WHERE p.dominioPadre = :dominioPadre"),
		@NamedQuery(name = "Parametro.findByCodigo", query = "SELECT p FROM Parametro p WHERE p.codigo = :codigo"),
		@NamedQuery(name = "Parametro.findByNombre", query = "SELECT p FROM Parametro p WHERE p.nombre = :nombre"),
		@NamedQuery(name = "Parametro.findByCodigoPadre", query = "SELECT p FROM Parametro p WHERE p.codigoPadre = :codigoPadre"),
		@NamedQuery(name = "Parametro.findByEstado", query = "SELECT p FROM Parametro p WHERE p.estado = :estado"),
		@NamedQuery(name = Parametro.BUSCAR_POR_VALORES_SIMPLE, query = "SELECT p FROM Parametro p WHERE p.aplicacion= :nombreApp  AND p.dominio = :dominio AND  p.estado = :estado ORDER BY p.nombre ASC"),
		@NamedQuery(name = Parametro.BUSCAR_POR_VALORES_MULTIPLE, query = "SELECT p FROM Parametro p WHERE p.aplicacion= :nombreApp  AND p.dominio = :dominio AND p.dominioPadre= :dominioPadre AND  p.codigoPadre = :codigoPadre AND p.estado = :estado ORDER BY p.nombre ASC") })
public class Parametro implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BUSCAR_POR_VALORES_SIMPLE = "Parametro.findByDominio";
	public static final String BUSCAR_POR_VALORES_MULTIPLE = "Parametro.findByMultiple";

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	@GeneratedValue(generator="my_seq_param")
	@SequenceGenerator(name="my_seq_param",sequenceName="COMMON_OBJECTS.SEQ_PARAMETRO", allocationSize=1)
	private Long id;
	@Size(max = 100)
	@Column(name = "APLICACION")
	private String aplicacion;
	@Size(max = 100)
	@Column(name = "DOMINIO")
	private String dominio;
	@Size(max = 100)
	@Column(name = "DOMINIO_PADRE")
	private String dominioPadre;
	@Size(max = 100)
	@Column(name = "CODIGO")
	private String codigo;
	@Size(max = 100)
	@Column(name = "NOMBRE")
	private String nombre;
	@Size(max = 100)
	@Column(name = "CODIGO_PADRE")
	private String codigoPadre;
	@Column(name = "ESTADO")
	private Short estado;
	@Column(name = "MODIFICABLE")
	private String modificable;
	@Column(name = "TIPO_PARAMETRO")
	private String tipoParametro;
	@Column(name = "DESCRIPCION")
	private String descripcion;

	public Parametro() {
	}

	public Parametro(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getDominioPadre() {
		return dominioPadre;
	}

	public void setDominioPadre(String dominioPadre) {
		this.dominioPadre = dominioPadre;
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

	public String getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	public Short getEstado() {
		return estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	/**
	 * @return the modificable
	 */
	public String getModificable() {
		return modificable;
	}

	/**
	 * @param modificable the modificable to set
	 */
	public void setModificable(String modificable) {
		this.modificable = modificable;
	}

	/**
	 * @return the tipoParametro
	 */
	public String getTipoParametro() {
		return tipoParametro;
	}

	/**
	 * @param tipoParametro the tipoParametro to set
	 */
	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}
	
	/**
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aplicacion == null) ? 0 : aplicacion.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((codigoPadre == null) ? 0 : codigoPadre.hashCode());
		result = prime * result + ((dominio == null) ? 0 : dominio.hashCode());
		result = prime * result + ((dominioPadre == null) ? 0 : dominioPadre.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modificable == null) ? 0 : modificable.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipoParametro == null) ? 0 : tipoParametro.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Parametro other = (Parametro) obj;
		if (aplicacion == null) {
			if (other.aplicacion != null)
				return false;
		} else if (!aplicacion.equals(other.aplicacion))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (codigoPadre == null) {
			if (other.codigoPadre != null)
				return false;
		} else if (!codigoPadre.equals(other.codigoPadre))
			return false;
		if (dominio == null) {
			if (other.dominio != null)
				return false;
		} else if (!dominio.equals(other.dominio))
			return false;
		if (dominioPadre == null) {
			if (other.dominioPadre != null)
				return false;
		} else if (!dominioPadre.equals(other.dominioPadre))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modificable == null) {
			if (other.modificable != null)
				return false;
		} else if (!modificable.equals(other.modificable))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipoParametro == null) {
			if (other.tipoParametro != null)
				return false;
		} else if (!tipoParametro.equals(other.tipoParametro))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Parametro [id=" + id + ", aplicacion=" + aplicacion + ", dominio=" + dominio + ", dominioPadre="
				+ dominioPadre + ", codigo=" + codigo + ", nombre=" + nombre + ", codigoPadre=" + codigoPadre
				+ ", estado=" + estado + ", modificable=" + modificable + ", tipoParametro=" + tipoParametro
				+ ", descripcion=" + descripcion + "]";
	}



}
