package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * The persistent class for the CATEGORIES database table.
 *
 */
@Entity
@Table(name = "CATALOGOS")
@NamedQuery(name = "Catalogos.findAll", query = "SELECT p FROM Catalogos p")
public class Catalogos implements Serializable {

	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_CATALOGOS_PK = "catalogoId";
	public static final String ENTIDAD_CATALOGOS_NOMBRE = "tipoCatalogo";
	public static final String ENTIDAD_CATALOGOS_DESCRIPCION = "descripcion";
	public static final String ENTIDAD_CATALOGOS_TEXTO_AYUDA = "textoAyuda";
	private static final String[] ATRIBUTOS_ENTIDAD_CATALOGOS = { ENTIDAD_CATALOGOS_DESCRIPCION,
			ENTIDAD_CATALOGOS_PK, ENTIDAD_CATALOGOS_NOMBRE, ENTIDAD_CATALOGOS_TEXTO_AYUDA};

	@Id
	@Column(name = "CATALOGO_ID")
	private BigDecimal catalogoId;

	@Column(name = "TIPO_CATALOGO")
	private String tipoCatalogo;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "TEXTO_AYUDA")
	private String textoAyuda;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public Catalogos() {
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public BigDecimal getCatalogoIdId() {
		return this.catalogoId;
	}

	public void setCatalogoIdId(BigDecimal catalogoId) {
		this.catalogoId = catalogoId;
	}

	public String getNombre() {
		return this.tipoCatalogo;
	}

	public void setNombre(String tipoCatalogo) {
		this.tipoCatalogo = tipoCatalogo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	
	public String getTextoAyuda() {
		return textoAyuda;
	}

	public void setTextoAyuda(String textoAyuda) {
		this.textoAyuda = textoAyuda;
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
		for (final String atr : ATRIBUTOS_ENTIDAD_CATALOGOS) {
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
		Catalogos other = (Catalogos) obj;
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
		return true;
	}

}
