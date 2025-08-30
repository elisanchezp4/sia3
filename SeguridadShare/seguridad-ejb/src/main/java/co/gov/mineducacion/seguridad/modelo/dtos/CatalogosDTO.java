package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DAO que contiene la información de la entidad CatalogoesDTO que se
 * transmite por los servicios REST. Solo se transmiten los atributos simples,
 * es decir, se omiten aquellos atributos que definen relaciones con otras
 * entidades.
 * 
 * @author jsoto
 */
@XmlRootElement
public class CatalogosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal catalogoId;
	private String tipoCatalogo;
	private String descripcion;
	private String textoAyuda;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public CatalogosDTO() {
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public BigDecimal getCatalogoId() {
		return this.catalogoId;
	}

	public void setCatalogoId(BigDecimal aplicacionId) {
		this.catalogoId = aplicacionId;
	}

	public String getNombre() {
		return this.descripcion;
	}

	public void setNombre(String nombre) {
		this.descripcion = nombre;
	}

	public String getDescripcion() {
		return this.tipoCatalogo;
	}

	public void setDescripcion(String descripcion) {
		this.tipoCatalogo = descripcion;
	}

	public String getTextoAyuda() {
		return textoAyuda;
	}

	public void setTextoAyuda(String textoAyuda) {
		this.textoAyuda = textoAyuda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catalogoId == null) ? 0 : catalogoId.hashCode());
		result = prime * result + ((tipoCatalogo == null) ? 0 : tipoCatalogo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((textoAyuda == null) ? 0 : textoAyuda.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatalogosDTO other = (CatalogosDTO) obj;
		if (catalogoId == null) {
			if (other.catalogoId != null)
				return false;
		} else if (!catalogoId.equals(other.catalogoId))
			return false;
		if (tipoCatalogo == null) {
			if (other.tipoCatalogo != null)
				return false;
		} else if (!tipoCatalogo.equals(other.tipoCatalogo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (textoAyuda == null) {
			if (other.textoAyuda != null)
				return false;
		} else if (!textoAyuda.equals(other.textoAyuda))
			return false;
		return true;
	}

}
