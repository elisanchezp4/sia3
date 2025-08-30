package co.gov.heinsohn.men.parametros.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;


import org.apache.log4j.Logger;
import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.entities.Parametro;
import co.gov.heinsohn.men.parametros.entitymanagers.IConsultaItemListaValoresEntityManagerEJB;
import co.gov.heinsohn.men.parametros.utils.BuilderDTO;
import co.gov.heinsohn.men.parametros.utils.exception.MessagesConstants;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

/**
 * Implemantación de los metodos para parametrizar los Parametros
 * 
 * @author Jhonnatan Orozco Duque <jorozco@heinsohn.com.co>
 *
 */
@Stateless
public class ConsultaItemListaValoresBusinessEJB implements IConsultaItemListaValoresBusinessEJB {

	// log de la aplicacion para la clase
	private static final Logger logger = Logger.getLogger(ConsultaItemListaValoresBusinessEJB.class.getName());

	@Inject
	private IConsultaItemListaValoresEntityManagerEJB conItemListValEM;

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
		logger.info("Inició el metodo consultaListaValoresSimple");
		try {
			List<Parametro> valores = conItemListValEM.consultaListaValoresSimple(nombreApp, dominio);
			logger.info("Finalizó el metodo consultaListaValoresSimple");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return BuilderDTO.toParametroDTOList(valores);

		} catch (PARAMETROSException re) {
			logger.error("No encontro registros para para los valores ingresados", re);
			throw re;
		} catch (Exception e) {
			logger.error("Fallo consultando el metodo consultaListaValoresSimple ", e);
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
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
		logger.info("Inició el metodo consultarParametroSimple");
		try {
			Parametro parametro = conItemListValEM.consultarParametroSimple(dominio, dominio);
			logger.info("Finalizó el metodo buscarSmlvPorAnio");
			if (parametro == null) {
				throw new PARAMETROSException(MessagesConstants.APP100004);
			}
			return BuilderDTO.toParametroDTO(parametro);

		} catch (PARAMETROSException re) {
			logger.error("No encontro registros para para el parametro ingresado", re);
			throw re;
		} catch (Exception e) {
			logger.error("Fallo consultando el metodo consultarParametroSimple ", e);
			throw new PARAMETROSException(MessagesConstants.APP100001, e.getCause());
		}
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
		logger.info("Inició el metodo consultarListaValoresMultiple");
		try {
			List<Parametro> valores = conItemListValEM.consultarListaValoresMultiple(nombreApp, dominio, dominioPadre,
					codigoPadre);
			logger.info("Finalizó el metodo consultarListaValoresMultiple");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return BuilderDTO.toParametroDTOList(valores);

		} catch (PARAMETROSException re) {
			logger.error("No encontro registros para para los valores ingresados", re);
			throw re;
		} catch (Exception e) {
			logger.error("Fallo consultando el metodo consultarListaValoresMultiple ", e);
			throw new PARAMETROSException(MessagesConstants.APP100002, e.getCause());
		}
	}

}
