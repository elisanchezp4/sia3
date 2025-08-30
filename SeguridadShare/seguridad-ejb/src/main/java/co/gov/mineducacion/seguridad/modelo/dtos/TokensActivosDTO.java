package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DAO que contiene la información de la entidad TokensActivosDTO que se
 * transmite por los servicios REST. Solo se transmiten los atributos simples,
 * es decir, se omiten aquellos atributos que definen relaciones con otras
 * entidades.
 * 
 * @author jsoto
 */
@XmlRootElement
public class TokensActivosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8667636573049516819L;

	private String tokenId;

	private Timestamp creacion;
	private Timestamp vencimiento;
	private BigDecimal tipo;
	private BigDecimal usuarioId;

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public TokensActivosDTO() {
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}

	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Timestamp getCreacion() {
		return this.creacion;
	}

	public void setCreacion(Timestamp creacion) {
		this.creacion = creacion;
	}

	public Timestamp getVencimiento() {
		return this.vencimiento;
	}

	public void setVencimiento(Timestamp vencimiento) {
		this.vencimiento = vencimiento;
	}

	public BigDecimal getTipo() {
		return this.tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(BigDecimal usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creacion == null) ? 0 : creacion.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
		result = prime * result + ((usuarioId == null) ? 0 : usuarioId.hashCode());
		result = prime * result + ((vencimiento == null) ? 0 : vencimiento.hashCode());
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
		TokensActivosDTO other = (TokensActivosDTO) obj;
		if (creacion == null) {
			if (other.creacion != null)
				return false;
		} else if (!creacion.equals(other.creacion))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (tokenId == null) {
			if (other.tokenId != null)
				return false;
		} else if (!tokenId.equals(other.tokenId))
			return false;
		if (usuarioId == null) {
			if (other.usuarioId != null)
				return false;
		} else if (!usuarioId.equals(other.usuarioId))
			return false;
		if (vencimiento == null) {
			if (other.vencimiento != null)
				return false;
		} else if (!vencimiento.equals(other.vencimiento))
			return false;
		return true;
	}

}
