package co.gov.heinsohn.men.parametros.entitymanagers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.entities.Parametro;
import co.gov.heinsohn.men.parametros.utils.Constants;
import co.gov.heinsohn.men.parametros.utils.SqlConstants;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

public class AdministrarParametroEntityManagerEJB extends AbstractEntityManagerEJB<Parametro>
		implements IAdministrarParametroEntityManagerEJB {

	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	public AdministrarParametroEntityManagerEJB() {
		super(Parametro.class);
	}

	@Override
	protected EntityManager getEntityManager() {

		return em;

	}

	/**
	 * permite obtener un listado de parametros por filtros
	 */
	@Override
	public List<ParametroDTO> consultaParemetrosFiltros(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException {

		try {
			Query query = null;
			
			StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_PARAMETROS_FILTROS);

			if (estado != null && !"".equals(estado)) {
				consulta.append(SqlConstants.FILTRO_ESTADO);
			}

			if (dominio != null && !"".equals(dominio)) {
				consulta.append(SqlConstants.FILTRO_PARAM_DOMINIO);
			}

			consulta.append(SqlConstants.FILTRO_ORDENAMIENTO_NOMBRE);
			query = em.createNativeQuery(consulta.toString());
			query.setParameter(1, tipoParametro);
			query.setParameter(2, nombreApp);
		
			if (dominio != null && !"".equals(dominio)) {
				query.setParameter(3, dominio);
			}

			if (estado != null && !"".equals(estado)) {
				query.setParameter(4, Integer.parseInt(estado));
			}

			List<Object[]> objectList = query.getResultList();

			return asignarValores(objectList);

		} catch (Exception e) {

			throw new PARAMETROSException("Fallo consultaParemetrosFiltros");
		}

	}

	/**
	 * permite obtener un listado de parametros por filtro o filtros
	 */
	public List<ParametroDTO> consultaParemetrosFiltrosDiferentes(String tipoParametro, String nombreApp,
			String dominio, String estado) throws PARAMETROSException {

		try {
			Query query = null;
			List<ParametroDTO> listaParametroDTO = new ArrayList<>();

			StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_PARAMETROS_FILTROS_DIFERENTES);

			append(consulta, tipoParametro, SqlConstants.FILTRO_TIPO_PARAMETRO);
			append(consulta, estado, SqlConstants.FILTRO_ESTADO);
			append(consulta, dominio, SqlConstants.FILTRO_PARAM_DOMINIO);

			consulta.append(SqlConstants.FILTRO_ORDENAMIENTO);
			query = em.createNativeQuery(consulta.toString());
			query.setParameter(1, nombreApp);

			setParameter(query, 2, tipoParametro);
			setParameter(query, 3, dominio);
			setParameter(query, 4, estado);

			List<Object[]> objectList = query.getResultList();

			for (Object[] objects : objectList) {
				// Se obtiene los datos del parametro
				ParametroDTO parametroDTO = new ParametroDTO();
				parametroDTO.setDominio(objects[0] != null ? (objects[0]).toString() : "");
				parametroDTO.setAplicacion(objects[1] != null ? (objects[1]).toString() : "");
				parametroDTO.setEstado(objects[2] != null ? ((BigDecimal) objects[2]).shortValue() : null);
				parametroDTO.setTipoParametro(objects[3] != null ? (objects[3]).toString() : "");
				listaParametroDTO.add(parametroDTO);
			}
			return listaParametroDTO;
		} catch (Exception e) {
			throw new PARAMETROSException("Fallo consultaParemetrosFiltrosDiferentes");
		}
	}

	private void append(StringBuilder consulta, String campo, String value){
		if (campo != null && !"".equals(campo)) {
			consulta.append(value);
		}
	}

	private void setParameter(Query query, int id, String campo){
		if (campo != null && !"".equals(campo)) {
			query.setParameter(id, campo);
		}
	}

	/**
	 * permite obtener un listado de parametros no modificables
	 * 
	 */
	public List<ParametroDTO> consultaParemetrosNoModificables(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException {

		try {
			Query query = null;

			query = em.createNativeQuery(SqlConstants.CONSULTAR_PARAMETROS_NO_MODIFICABLES);
			query.setParameter(1, tipoParametro);
			query.setParameter(2, nombreApp);
			query.setParameter(3, dominio);
			query.setParameter(4, Integer.parseInt(estado));
			query.setParameter(5, Constants.MODIFICABLE_NO);

			List<Object[]> objectList = query.getResultList();

			return asignarValores(objectList);

		} catch (Exception e) {

			throw new PARAMETROSException("Fallo consultaParemetrosNoModificables");
		}

	}

	/**
	 * Asigna los valores consultados al DTO
	 *
	 */
	public List<ParametroDTO> asignarValores(List<Object[]> objectList) {
		List<ParametroDTO> listaParametroDTO = new ArrayList<>();

		for (Object[] objects : objectList) {
			// Se obtiene los datos del parametro
			ParametroDTO parametroDTO = new ParametroDTO();
			parametroDTO.setId(objects[0] != null ? ((BigDecimal) objects[0]).longValue() : null);
			parametroDTO.setAplicacion(toString(objects[1]));
			parametroDTO.setDominio(toString(objects[2]));
			parametroDTO.setDominioPadre(toString(objects[3]));
			parametroDTO.setCodigo(toString(objects[4]));
			parametroDTO.setNombre(toString(objects[5]));
			parametroDTO.setCodigoPadre(toString(objects[6]));
			parametroDTO.setEstado(objects[7] != null ? ((BigDecimal) objects[7]).shortValue() : null);
			parametroDTO.setModificable(toString(objects[8]));
			parametroDTO.setTipoParametro(toString(objects[9]));
			listaParametroDTO.add(parametroDTO);
		}
		return listaParametroDTO;
	}

	private String toString(Object valor){
		return valor != null ? valor.toString() : "";
	}

	/**
	 * Valida si un parametro y aexiste
	 */
	@Override
	public List<ParametroDTO> consultaParemetroExiste(String tipoParametro, String nombreApp, String dominio,
			String estado, String nombre) throws PARAMETROSException {
		try {
			Query query = null;

			query = em.createNativeQuery(SqlConstants.CONSULTAR_PARAMETRO_EXISTE);
			query.setParameter(1, tipoParametro);
			query.setParameter(2, nombreApp);
			query.setParameter(3, dominio);
			query.setParameter(4, nombre);

			List<Object[]> objectList = query.getResultList();

			return asignarValores(objectList);

		} catch (Exception e) {

			throw new PARAMETROSException("Fallo consultaParemetroExiste");
		}
	}

	/**
	 * permite obtener un listado de Dominio
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<ParametroDTO> consultaListasDominio(String nombreApp, String tipoParametro) throws PARAMETROSException {
		try {
			Query query = null;
			List<ParametroDTO> listaParametroDTO = new ArrayList<>();

			query = em.createNativeQuery(SqlConstants.FILTRO_DOMINIO);
			query.setParameter(1, nombreApp);
			query.setParameter(2, tipoParametro);

			List<Object[]> objectList = query.getResultList();

			for (Object[] objects : objectList) {

				// Se obtiene los datos del parametro
				ParametroDTO parametroDTO = new ParametroDTO();
				parametroDTO.setDominio(objects[0] != null ? objects[0].toString() : "");
				parametroDTO.setDescripcion(objects[1] != null ? (objects[1]).toString() : "");
				listaParametroDTO.add(parametroDTO);

			}

			return listaParametroDTO;

		} catch (Exception e) {

			throw new PARAMETROSException("Fallo consultaListasDominio. Detalle:" + e);
		}
	}

	/**
	 * permite obtener un listado de tipo paramero
	 * 
	 */
	public List<ParametroDTO> consultaListasTipoParametro(String nombreApp) throws PARAMETROSException {
		try {
			Query query = null;
			List<ParametroDTO> listaParametroDTO = new ArrayList<>();

			query = em.createNativeQuery(SqlConstants.FILTRO_TIPO_PARAM);
			query.setParameter(1, nombreApp);

			List<String> objectList = query.getResultList();

			for (String objects : objectList) {

				// Se obtiene los datos del parametro
				ParametroDTO parametroDTO = new ParametroDTO();
				parametroDTO.setTipoParametro(objects != null ? objects : "");
				listaParametroDTO.add(parametroDTO);

			}

			return listaParametroDTO;

		} catch (Exception e) {

			throw new PARAMETROSException("Fallo consultaListasTipoParametro");
		}
	}

	/**
	 * permite obtener un listado de aplicaciones
	 * 
	 */
	public List<ParametroDTO> consultaListasAplicacion() throws PARAMETROSException {
		try {
			Query query = null;
			List<ParametroDTO> listaParametroDTO = new ArrayList<>();

			query = em.createNativeQuery(SqlConstants.FILTRO_APP);

			List<String> objectList = query.getResultList();

			for (String objects : objectList) {

				// Se obtiene los datos del parametro
				ParametroDTO parametroDTO = new ParametroDTO();
				parametroDTO.setAplicacion(objects != null ? objects : "");
				listaParametroDTO.add(parametroDTO);

			}

			return listaParametroDTO;

		} catch (Exception e) {

			throw new PARAMETROSException("Fallo consultaListasAplicacion");
		}
	}

	/**
	 * permite obtener un listado de departamentos
	 * 
	 */
	public List<ParametroDTO> consultaListasDepartamentos() throws PARAMETROSException {
		try {
			Query query = null;
			List<ParametroDTO> listaParametroDTO = new ArrayList<>();

			query = em.createNativeQuery(SqlConstants.FILTRO_DEPARTAMENTO);

			query.setParameter(1, Constants.DOMINIO_DEPARTAMENTO);

			List<Object[]> objectList = query.getResultList();

			for (Object[] objects : objectList) {

				// Se obtiene los datos del parametro
				ParametroDTO parametroDTO = new ParametroDTO();

				parametroDTO.setNombre(objects[0] != null ? (objects[0]).toString() : "");
				parametroDTO.setCodigo(objects[1] != null ? (objects[1]).toString() : "");

				listaParametroDTO.add(parametroDTO);

			}

			return listaParametroDTO;

		} catch (Exception e) {

			throw new PARAMETROSException("Fallo consultaListasAplicacion");
		}
	}

}
