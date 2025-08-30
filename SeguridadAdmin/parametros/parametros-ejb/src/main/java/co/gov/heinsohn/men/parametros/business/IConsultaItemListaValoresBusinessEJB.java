package co.gov.heinsohn.men.parametros.business;

import java.util.List;

import javax.ejb.Local;

import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

/**
 * Interfaz de los metodos para parametrizar los Parametros
 * 
 * @author Jhonnatan Orozco Duque <jorozco@heinsohn.com.co>
 *
 */
@Local
public interface IConsultaItemListaValoresBusinessEJB {

	/**
	 * permite obtener un listado de Valores
	 * 
	 * @param nombreApp
	 *            dominio
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListaValoresSimple(String nombreApp, String dominio) throws PARAMETROSException;


	
	/**
	 * permite obtener un listado de Parametros
	 * 
	 * @param nombreApp
	 *            dominio
	 * @return ParametroDTO
	 */
	public ParametroDTO consultarParametroSimple(String nombreApp, String dominio) throws PARAMETROSException;

	/**
	 * permite obtener un listado de Valores multiples
	 * 
	 * @param nombreApp
	 *            dominio dominioPadre codigoPadre
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultarListaValoresMultiple(String nombreApp, String dominio, String dominioPadre,
			String codigoPadre) throws PARAMETROSException;
}
