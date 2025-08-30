package co.gov.heinsohn.men.parametros.business;

import java.util.List;

import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

public interface IAdministrarParametroBusinessEJB {

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
	public List<ParametroDTO> consultaParemetrosFiltros(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException;

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
	public List<ParametroDTO> consultaParemetrosNoModificables(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException;

	/**
	 * permite crear un nuevo parametro
	 * 
	 * @param parametroDTO
	 *            el parametro que se desea crear
	 * @throws PARAMETROSException
	 */
	public void adicionarParametro(ParametroDTO parametroDTO) throws PARAMETROSException;

	/**
	 * permite modificar un parametro existente
	 * 
	 * @param parametroDTO
	 *            el multivalor que se desea modificar
	 * @throws PARAMETROSException
	 */
	public void editarParametro(ParametroDTO parametroDTO) throws PARAMETROSException;

	/**
	 * permite crear un nuevo parametro
	 * 
	 * @param parametroDTO
	 *            el parametro que se desea crear
	 * @throws PARAMETROSException
	 */
	public void adicionarDependiente(ParametroDTO parametroDTO) throws PARAMETROSException;

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
	public List<ParametroDTO> consultaParemetrosFiltrosDiferentes(String tipoParametro, String nombreApp,
			String dominio, String estado) throws PARAMETROSException;

	/**
	 * permite obtener un listado de Dominio
	 * 
	 * @param nombreApp
	 *            tipoParametro
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasDominio(String nombreApp, String tipoParametro) throws PARAMETROSException;

	/**
	 * permite obtener un listado de tipo parametro
	 * 
	 * @param nombreApp
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasTipoParametro(String nombreApp) throws PARAMETROSException;
	
	/**
	 * permite obtener un listado de aplicación
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasAplicacion() throws PARAMETROSException;
	
	/**
	 * permite obtener un listado de Departamentos
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasDepartamentos() throws PARAMETROSException;
}
