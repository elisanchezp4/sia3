/**
 * 
 */
package co.gov.heinsohn.men.parametros.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO encargado de Transportar los datos referentes a Parametro
 * 
 * @author HEINSOHN BUSINESS TECHNOLOGY – HBT
 *
 */
@XmlRootElement
public class ParametroDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String aplicacion;

	private String dominio;

	private String dominioPadre;

	private String codigo;

	private String nombre;

	private String codigoPadre;

	private Short estado;
	
	private String modificable;
	
	private String tipoParametro;
	
	private String descripcion;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion
	 *            the aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the dominio
	 */
	public String getDominio() {
		return dominio;
	}

	/**
	 * @param dominio
	 *            the dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	/**
	 * @return the dominioPadre
	 */
	public String getDominioPadre() {
		return dominioPadre;
	}

	/**
	 * @param dominioPadre
	 *            the dominioPadre to set
	 */
	public void setDominioPadre(String dominioPadre) {
		this.dominioPadre = dominioPadre;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the codigoPadre
	 */
	public String getCodigoPadre() {
		return codigoPadre;
	}

	/**
	 * @param codigoPadre
	 *            the codigoPadre to set
	 */
	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	/**
	 * @return the estado
	 */
	public Short getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
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
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * 
	 * @param descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParametroDTO [id=" + id + ", aplicacion=" + aplicacion + ", dominio=" + dominio + ", dominioPadre="
				+ dominioPadre + ", codigo=" + codigo + ", nombre=" + nombre + ", codigoPadre=" + codigoPadre
				+ ", estado=" + estado + ", modificable=" + modificable + ", tipoParametro=" + tipoParametro 
				+ ", descripcion=" + descripcion + "]";
	}


}
