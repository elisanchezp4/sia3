package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRolPK;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DAO que contiene la información de la entidad OperacionesRolDTO que se transmite
 * por los servicios REST. Solo se transmiten los atributos simples, es decir,
 * se omiten aquellos atributos que definen relaciones con otras entidades.
 * 
 * @author jsoto
 */
@XmlRootElement
public class OperacionesPorRolDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal rolId;
	private BigDecimal operacionId;
	
	private OperacionesRolPK operacionesRolPK;

	

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end

	public OperacionesRolPK getOperacionesRolPK() {
		return operacionesRolPK;
	}



	public void setOperacionesRolPK(OperacionesRolPK operacionesRolPK) {
		this.operacionesRolPK = operacionesRolPK;
	}



	public OperacionesPorRolDTO() {
		operacionesRolPK = new OperacionesRolPK();
		// protected region procedimientos adicionales de inicialización on
		// begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
	}



	public BigDecimal getRolId() {
		return rolId;
	}

	public void setRolId(BigDecimal rolId) {
		this.rolId = rolId;
	}

	public BigDecimal getOperacionId() {
		return operacionId;
	}

	public void setOperacionId(BigDecimal operacionId) {
		this.operacionId = operacionId;
	}

	@Override
	public String toString() {
		return "OperacionesPorRolDTO [rolId=" + rolId + ", operacionId=" + operacionId + "]";
	}


}
