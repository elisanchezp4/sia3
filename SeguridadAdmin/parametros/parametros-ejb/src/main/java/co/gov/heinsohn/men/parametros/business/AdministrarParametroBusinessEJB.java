package co.gov.heinsohn.men.parametros.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;


import org.apache.log4j.Logger;

import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.entities.Parametro;
import co.gov.heinsohn.men.parametros.entitymanagers.IAdministrarParametroEntityManagerEJB;
import co.gov.heinsohn.men.parametros.utils.BuilderDTO;
import co.gov.heinsohn.men.parametros.utils.exception.MessagesConstants;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

@Stateless
public class AdministrarParametroBusinessEJB implements IAdministrarParametroBusinessEJB {
	// log de la aplicacion para la clase
	private static final Logger LOG = Logger.getLogger(AdministrarParametroBusinessEJB.class.getName());

	@Inject
	private IAdministrarParametroEntityManagerEJB administrarParametroEntityManagerEJB;

	/**
	 * Consulta lso parametros por todos os filtros
	 * 
	 * @param tipoParametro
	 * @param nombreApp
	 * @param dominio
	 * @param estado
	 * @return
	 * @throws PARAMETROSException
	 */
	@Override
	public List<ParametroDTO> consultaParemetrosFiltros(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException {

		LOG.info("Inició el metodo consultaParemetrosFiltros");
		try {
			List<ParametroDTO> valores = administrarParametroEntityManagerEJB.consultaParemetrosFiltros(tipoParametro,
					nombreApp, dominio, estado);
			LOG.info("Finalizó el metodo consultaParemetrosFiltros");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return valores;

		} catch (PARAMETROSException re) {
			LOG.error("No encontro registros para para los valores ingresados", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo consultando el metodo consultaParemetrosFiltros ", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
	}

	/**
	 * Permite obtener los parametros no editables
	 * 
	 * @param tipoParametro
	 * @param nombreApp
	 * @param dominio
	 * @param estado
	 * @param odificable
	 * @return
	 * @throws PARAMETROSException
	 */
	@Override
	public List<ParametroDTO> consultaParemetrosNoModificables(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException {

		LOG.info("Inició el metodo consultaParemetrosNoModificables");
		try {
			List<ParametroDTO> valores = administrarParametroEntityManagerEJB
					.consultaParemetrosNoModificables(tipoParametro, nombreApp, dominio, estado);
			LOG.info("Finalizó el metodo consultaParemetrosNoModificables");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return valores;

		} catch (PARAMETROSException re) {
			LOG.error("No encontro registros para para los valores ingresados", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo consultando el metodo consultaParemetrosNoModificables ", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
	}

	/**
	 * permite crear un nuevo parametro
	 * 
	 * @param parametroDTO
	 *            el parametro que se desea crear
	 * @throws PARAMETROSException
	 */
	@Override
	public void adicionarParametro(ParametroDTO parametroDTO) throws PARAMETROSException {

		try {

			if (parametroDTO != null) {

				if (!parametroDTO.getDominio().equals("")) {

					// Se valida si el parametro ya existe
					existeParametro(parametroDTO);

					Parametro parametro = BuilderDTO.toParametro(parametroDTO);
					// Crear parametro Depandiente
					administrarParametroEntityManagerEJB.create(parametro);
					LOG.info("Finalizó el metodo adicionarParametro con parametro => " + parametro);

				} else {

					LOG.error(MessagesConstants.APP100006);
					throw new PARAMETROSException(MessagesConstants.APP100006);
				}

			} else {
				LOG.error(MessagesConstants.APP100005);
				throw new PARAMETROSException(MessagesConstants.APP100005);
			}

		} catch (PARAMETROSException e) {
			LOG.error("Fallo en la creacion del parametro", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000007);
		}

	}

	/**
	 * permite modificar un parametro existente
	 * 
	 * @param parametroDTO
	 *            el multivalor que se desea modificar
	 * @throws PARAMETROSException
	 */
	@Override
	public void editarParametro(ParametroDTO parametroDTO) throws PARAMETROSException {

		LOG.info("Inició el metodo editarMultivalores con parametroDTO =>  " + parametroDTO);
		try {
			if (parametroDTO != null && !parametroDTO.getDominio().equals("")) {

				Parametro parametro = BuilderDTO.toParametro(parametroDTO);

				administrarParametroEntityManagerEJB.edit(parametro);
				LOG.info("Finalizó el metodo editarMultivalores con parametro =>  " + parametro);
			} else {
				LOG.error(MessagesConstants.APP100005);
				throw new PARAMETROSException(MessagesConstants.APP100005);
			}

		} catch (PARAMETROSException re) {
			LOG.error("Fallo en la edición del parametro", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo en la edición del parametro", e.getCause());
			throw new PARAMETROSException("");
		}

	}


	/**
	 * Valida que no exista información previamente registrada para el parametro
	 * 
	 * @param parametroDTO
	 * @throws PARAMETROSException
	 */
	public void existeParametro(ParametroDTO parametroDTO) throws PARAMETROSException {

		List<ParametroDTO> listParametroDTO = administrarParametroEntityManagerEJB.consultaParemetroExiste(
				parametroDTO.getTipoParametro(), parametroDTO.getAplicacion(), parametroDTO.getDominio(),
				parametroDTO.getEstado().toString(), parametroDTO.getNombre());

		if (listParametroDTO != null && !listParametroDTO.isEmpty()) {
			LOG.error(MessagesConstants.APP000009);
			throw new PARAMETROSException(MessagesConstants.APP000009);
		}
	}

	/**
	 * permite crear un nuevo parametro
	 * 
	 * @param parametroDTO
	 *            el parametro que se desea crear
	 * @throws PARAMETROSException
	 */
	@Override
	public void adicionarDependiente(ParametroDTO parametroDTO) throws PARAMETROSException {
		// Auto-generated method stub
	}

	/**
	 * Metodo para buscar por filtro o filtros
	 * 
	 * @param tipoParametro
	 * @param nombreApp
	 * @param dominio
	 * @param estado
	 * @return
	 * @throws PARAMETROSException
	 */
	@Override
	public List<ParametroDTO> consultaParemetrosFiltrosDiferentes(String tipoParametro, String nombreApp,
			String dominio, String estado) throws PARAMETROSException {

		LOG.info("Inició el metodo consultaParemetrosFiltrosDiferentes");
		try {

			if (nombreApp == null || "".equals(nombreApp)) {
				throw new PARAMETROSException(MessagesConstants.APP000010);
			}

			List<ParametroDTO> valores = administrarParametroEntityManagerEJB
					.consultaParemetrosFiltrosDiferentes(tipoParametro, nombreApp, dominio, estado);
			LOG.info("Finalizó el metodo consultaParemetrosFiltrosDiferentes");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return valores;

		} catch (PARAMETROSException re) {
			LOG.error("Fallo consultando el metodo consultaParemetrosFiltrosDiferentes ", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo consultando el metodo consultaParemetrosFiltrosDiferentes ", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
	}

	/**
	 * permite obtener un listado de Dominio
	 * 
	 * @param nombreApp
	 *            tipoParametro
	 * 
	 * @return List<ParametroDTO>
	 */
	@Override
	public List<ParametroDTO> consultaListasDominio(String nombreApp, String tipoParametro) throws PARAMETROSException {
		LOG.info("Inició el metodo consultaListasDominio");
		try {

			if (nombreApp == null || "".equals(nombreApp) || tipoParametro == null || "".equals(tipoParametro)) {
				throw new PARAMETROSException(MessagesConstants.APP000010);
			}

			List<ParametroDTO> valores = administrarParametroEntityManagerEJB.consultaListasDominio(nombreApp,
					tipoParametro);
			LOG.info("Finalizó el metodo consultaListasDominio");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return valores;

		} catch (PARAMETROSException re) {
			LOG.error("Fallo consultando el metodo consultaListasDominio ", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo consultando el metodo consultaListasDominio ", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
	}

	/**
	 * permite obtener un listado de tipo parametro
	 * 
	 * @param nombreApp
	 * 
	 * @return List<ParametroDTO>
	 */
	@Override
	public List<ParametroDTO> consultaListasTipoParametro(String nombreApp) throws PARAMETROSException {
		LOG.info("Inició el metodo consultaListasTipoParametro");
		try {

			if (nombreApp == null || "".equals(nombreApp)) {
				throw new PARAMETROSException(MessagesConstants.APP000010);
			}

			List<ParametroDTO> valores = administrarParametroEntityManagerEJB.consultaListasTipoParametro(nombreApp);
			LOG.info("Finalizó el metodo consultaListasTipoParametro");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return valores;

		} catch (PARAMETROSException re) {
			LOG.error("Fallo consultando el metodo consultaListasTipoParametro ", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo consultando el metodo consultaListasTipoParametro ", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
	}

	/**
	 * permite obtener un listado de aplicación
	 * 
	 * @return List<ParametroDTO>
	 */
	@Override
	public List<ParametroDTO> consultaListasAplicacion() throws PARAMETROSException {
		LOG.info("Inició el metodo consultaListasAplicacion");
		try {

			List<ParametroDTO> valores = administrarParametroEntityManagerEJB.consultaListasAplicacion();
			LOG.info("Finalizó el metodo consultaListasAplicacion");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return valores;

		} catch (PARAMETROSException re) {
			LOG.error("Fallo consultando el metodo consultaListasAplicacion ", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo consultando el metodo consultaListasAplicacion ", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
	}

	/**
	 * permite obtener un listado de departamentos
	 * 
	 * 
	 * @return List<ParametroDTO>
	 */
	@Override
	public List<ParametroDTO> consultaListasDepartamentos() throws PARAMETROSException {
		LOG.info("Inició el metodo consultaListasDepartamentos");
		try {

			List<ParametroDTO> valores = administrarParametroEntityManagerEJB.consultaListasDepartamentos();
			LOG.info("Finalizó el metodo consultaListasDepartamentos");
			if (valores == null || valores.isEmpty()) {
				throw new PARAMETROSException(MessagesConstants.APP100003);
			}
			return valores;

		} catch (PARAMETROSException re) {
			LOG.error("Fallo consultando el metodo consultaListasDepartamentos ", re.getCause());
			throw re;
		} catch (Exception e) {
			LOG.error("Fallo consultando el metodo consultaListasDepartamentos ", e.getCause());
			throw new PARAMETROSException(MessagesConstants.APP000000, e.getCause());
		}
	}

}
