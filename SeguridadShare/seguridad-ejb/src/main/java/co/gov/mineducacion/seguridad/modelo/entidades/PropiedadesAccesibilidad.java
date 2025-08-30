package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@NamedQueries({
	@NamedQuery(name = "PropiedadesAccesibilidad.findAll", query = " select pa from PropiedadesAccesibilidad pa where pa.estado = 1 order by pa.indice asc ")	 
})

@Entity
@Table(name = "PROPIEDADESACCESIBILIDAD")
public class PropiedadesAccesibilidad  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PROPIEDADACC_ID")
	private String propiedadAccId;
	
	@Column(name = "INDICE")
	private Long indice;
	
	@Column(name = "VALORDEFECTO")
	private String valorDefecto;
	
	@Column(name = "ESTADO")
	private Long estado;

	public String getPropiedadAccId() {
		return propiedadAccId;
	}

	public void setPropiedadAccId(String propiedadAccId) {
		this.propiedadAccId = propiedadAccId;
	}

	public Long getIndice() {
		return indice;
	}

	public void setIndice(Long indice) {
		this.indice = indice;
	}

	public String getValorDefecto() {
		return valorDefecto;
	}

	public void setValorDefecto(String valorDefecto) {
		this.valorDefecto = valorDefecto;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	
}

