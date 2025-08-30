package co.gov.heinsohn.men.parametros.entitymanagers;

import java.util.List;

import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.entities.Parametro;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

public interface IAdministrarParametroEntityManagerEJB {

	/**
	 * permite obtener un listado de parametros
	 * 
	 * @param tipoParametro,
	 *            nombreApp dominio, estado
	 * 
	 * @return List<Parametro>
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
	 * Metodo para verificar si existe un parametro
	 * 
	 * @param tipoParametro
	 * @param nombreApp
	 * @param dominio
	 * @param estado
	 * @param nombre
	 * @return
	 * @throws PARAMETROSException
	 */
	public List<ParametroDTO> consultaParemetroExiste(String tipoParametro, String nombreApp, String dominio,
			String estado, String nombre) throws PARAMETROSException;

	/**
	 * permite crear un parametro
	 * 
	 * @param tasaUsura
	 */
	void create(Parametro parametro);

	/**
	 * permite editar un parametro
	 * 
	 * @param tasaUsura
	 */
	void edit(Parametro parametro);

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
	 *            dominio
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasDominio(String nombreApp, String tipoParametro) throws PARAMETROSException;

	/**
	 * permite obtener un listado de tipo parametro
	 * 
	 * @param nombreApp
	 *            dominio
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasTipoParametro(String nombreApp) throws PARAMETROSException;
	
	/**
	 * permite obtener un listado de aplicaciones
	 * 
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasAplicacion() throws PARAMETROSException;
	
	/**
	 * permite obtener un listado de departamentos
	 * 
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> consultaListasDepartamentos() throws PARAMETROSException;

}
