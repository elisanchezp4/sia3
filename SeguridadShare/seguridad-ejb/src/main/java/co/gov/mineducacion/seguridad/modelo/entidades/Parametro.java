package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the PARAMETRO database table.
 *
 */
@Entity
@Table(name = "PARAMETRO")
@NamedQueries({ @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p") })
public class Parametro implements Serializable {

	private static final long serialVersionUID = 1L;

	// Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_PARAMETRO_PK = "nombre";
	public static final String ENTIDAD_PARAMETRO_VALOR = "ruta";
	public static final String ENTIDAD_PARAMETRO_DESCRIPCION = "descripcion";

	private static final String[] ATRIBUTOS_ENTIDAD_PARAMETRO = { ENTIDAD_PARAMETRO_PK, ENTIDAD_PARAMETRO_VALOR,
			ENTIDAD_PARAMETRO_DESCRIPCION};

	@Id
	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "VALOR")
	private String valor;

	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "TIPO")
	private String tipo;


	public Parametro(){/* Recomendacion por sonar */}
	
	


	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getValor() {
		return valor;
	}




	public void setValor(String valor) {
		this.valor = valor;
	}




	public String getDescripcion() {
		return descripcion;
	}




	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}




	public String getTipo() {
		return tipo;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		for (final String atr : ATRIBUTOS_ENTIDAD_PARAMETRO) {
			if (atr.equals(atributo)) {
				contiene = true;
			}
		}

		return contiene;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.nombre == null) ? 0 : this.nombre.hashCode());
		result = prime * result + ((this.valor == null) ? 0 : this.valor.hashCode());
		result = prime * result + ((this.descripcion == null) ? 0 : this.descripcion.hashCode());
		result = prime * result + ((this.tipo == null) ? 0 : this.tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Parametro other = (Parametro) obj;
        
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        
        return Objects.equals(this.tipo, other.tipo);
	}

	

	@Override
	public String toString() {
		return "Parametro [nombre=" + nombre + ", valor=" + valor + ", descripcion=" + descripcion + ", tipo=" + tipo+ "]";
	}
	
}
