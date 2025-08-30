package co.gov.heinsohn.men.parametros.facades;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import co.gov.heinsohn.men.parametros.business.IConsultaItemListaValoresBusinessEJB;
import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

/**
 * Implemantación de los metodos para parametrizar los Parametros
 * 
 * @author Jhonnatan Orozco Duque <jorozco@heinsohn.com.co>
 *
 */
@Stateless
public class ConsultaItemListaValoresFacadeEJB implements IConsultaItemListaValoresFacadeEJB {

	@Inject
	private IConsultaItemListaValoresBusinessEJB conItemListValBusinessEJB;

	/**
	 * permite obtener un listado de Valores
	 * 
	 * @param nombreApp
	 *            dominio
	 * 
	 * @return List<ParametroDTO>
	 */
	@Override
	public List<ParametroDTO> consultaListaValoresSimple(String nombreApp, String dominio) throws PARAMETROSException {

		return conItemListValBusinessEJB.consultaListaValoresSimple(nombreApp, dominio);
	}

	/**
	 * permite obtener un listado de Parametros
	 * 
	 * @param nombreApp
	 *            dominio
	 * @return ParametroDTO
	 */
	@Override
	public ParametroDTO consultarParametroSimple(String nombreApp, String dominio) throws PARAMETROSException {

		return conItemListValBusinessEJB.consultarParametroSimple(nombreApp, dominio);
	}

	/**
	 * permite obtener un listado de Valores multiples
	 * 
	 * @param nombreApp
	 *            dominio dominioPadre codigoPadre
	 * @return List<ParametroDTO>
	 */
	@Override
	public List<ParametroDTO> consultarListaValoresMultiple(String nombreApp, String dominio, String dominioPadre,
			String codigoPadre) throws PARAMETROSException {

		return conItemListValBusinessEJB.consultarListaValoresMultiple(nombreApp, dominio, dominioPadre, codigoPadre);
	}

}
