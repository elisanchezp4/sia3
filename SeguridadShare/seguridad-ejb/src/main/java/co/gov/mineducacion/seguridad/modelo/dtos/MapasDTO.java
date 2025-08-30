/**
 * 
 * @author hfabra
 * @since 17/02/2017
 */
package co.gov.mineducacion.seguridad.modelo.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TreeMap;

/**
 * @author hfabra
 *
 */
public class MapasDTO implements Serializable {

	/**
	 * 
	 * @author hfabra
	 */
	private static final long serialVersionUID = 1L;
	
	private TreeMap<BigDecimal, BigDecimal> mapaPadreHijo;
	private TreeMap<String, OperacionesDTO> mapaCompleto;
	
	/**
	 * 
	 */
	public MapasDTO(TreeMap<BigDecimal, BigDecimal> mapaPadreHijo,
			TreeMap<String, OperacionesDTO> mapaCompleto) {
		super();
		this.mapaPadreHijo = mapaPadreHijo;
		this.mapaCompleto = mapaCompleto;
	}

	/**
	 * @author hfabra
	 * @return the mapaPadreHijo
	 */
	public TreeMap<BigDecimal, BigDecimal> getMapaPadreHijo() {
		return mapaPadreHijo;
	}

	/**
	 * @author hfabra
	 * @param mapaPadreHijo the mapaPadreHijo to set
	 */
	public void setMapaPadreHijo(TreeMap<BigDecimal, BigDecimal> mapaPadreHijo) {
		this.mapaPadreHijo = mapaPadreHijo;
	}

	/**
	 * @author hfabra
	 * @return the mapaCompleto
	 */
	public TreeMap<String, OperacionesDTO> getMapaCompleto() {
		return mapaCompleto;
	}

	/**
	 * @author hfabra
	 * @param mapaCompleto the mapaCompleto to set
	 */
	public void setMapaCompleto(TreeMap<String, OperacionesDTO> mapaCompleto) {
		this.mapaCompleto = mapaCompleto;
	}
	
	
}
