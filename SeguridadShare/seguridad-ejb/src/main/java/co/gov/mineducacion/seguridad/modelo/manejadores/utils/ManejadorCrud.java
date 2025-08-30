package co.gov.mineducacion.seguridad.modelo.manejadores.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.Parameter;
import javax.persistence.Query;

import co.gov.mineducacion.seguridad.modelo.enums.FuncionAgrupamientoJPQL;
import co.gov.mineducacion.seguridad.modelo.enums.TipoFiltro;
import co.gov.mineducacion.seguridad.modelo.enums.TipoOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.enums.TipoQuery;
import static co.gov.mineducacion.seguridad.modelo.utils.UtilConstantes.NOT_NULL_VALUE;
import static co.gov.mineducacion.seguridad.modelo.utils.UtilConstantes.NULL_VALUE;
import co.gov.mineducacion.seguridad.modelo.utils.UtilReflection;

/**
 * Clase abstracta que define las operaciones CRUD y que implementan todos los manejadores de persistencia de 
 * cada una de las entidades.
 *
 * @param <T>  Clase de la entidad asociada al manejador
 * @param <U>  Clase de la llave primaria asociada al manejador
 * 
 * @author jsoto
 */
public abstract class ManejadorCrud<T, U> implements IManejadorCrud<T, U> {

	/**
	 * a imprimir logs...
	 */
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(ManejadorCrud.class.getName());

	public static final Integer IGNORAR_PARAMETRO_CONSULTA = -1;

	@EJB
	protected ManejadorPersistencia<T> mp;

	private Class<T> claseEntidad;

	/**
	 * @param claseEntidad
	 *            Clase de la entidad T
	 */
	public ManejadorCrud(Class<T> claseEntidad) {
		this.claseEntidad = claseEntidad;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param pId
	 *            {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public T buscar(U pId) {
		return mp.find(getClaseEntidad(), pId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public T crear() {
		T instancia = null;
		try {
			instancia = getClaseEntidad().newInstance();
		} catch (InstantiationException ex) {
			logger.error(ex);
		} catch (IllegalAccessException ex) {
			logger.error(ex);
		}
		return instancia;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param filtros
	 *            {@inheritDoc}
	 * @param rangoConsulta
	 *            {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public Integer consultarTotalRegistros(Collection<InformacionFiltro> filtros, RangoConsulta rangoConsulta) {

		Query qCount = construirJpqlQuery(filtros, null, null, rangoConsulta, TipoQuery.SELECT,
				new InformacionAgrupamiento(FuncionAgrupamientoJPQL.COUNT));

		return ((Long) qCount.getSingleResult()).intValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param filtros
	 *            {@inheritDoc}
	 * @param informacionOrdenamiento
	 *            {@inheritDoc}
	 * @param rangoConsulta
	 *            {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public List<T> consultar(Collection<InformacionFiltro> filtros,
			Collection<InformacionOrdenamiento> informacionOrdenamiento, RangoConsulta rangoConsulta) {

		Query q = construirJpqlQuery(filtros, informacionOrdenamiento, null, rangoConsulta, TipoQuery.SELECT,
				new InformacionAgrupamiento(FuncionAgrupamientoJPQL.NINGUNA));

		return mp.listData(q);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param filtros
	 *            {@inheritDoc}
	 * @param infoOrdenamiento
	 *            {@inheritDoc}
	 * @param infoAgrupamiento
	 *            {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public List<Object> consultarLista(Collection<InformacionFiltro> filtros,
			Collection<InformacionOrdenamiento> infoOrdenamiento, InformacionAgrupamiento infoAgrupamiento) {
		Query q = construirJpqlQuery(filtros, infoOrdenamiento, null, null, TipoQuery.SELECT, infoAgrupamiento);

		return mp.listData(q);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param asignaciones
	 *            {@inheritDoc}
	 * @param filtros
	 *            {@inheritDoc}
	 */
	@Override
	public void actualizarPorFiltros(Collection<InformacionAsignacion> asignaciones,
			Collection<InformacionFiltro> filtros) {

		Query q = construirJpqlQuery(filtros, null, asignaciones, null, TipoQuery.UPDATE,
				new InformacionAgrupamiento(FuncionAgrupamientoJPQL.NINGUNA));
		q.executeUpdate();

	}

	/**
	 * Método que se ejecuta antes de crear un registro en la base de datos y en
	 * el que la clase que extienda esta clase puede añadir funcionalidad
	 * personalizada.
	 */
	public void preCrear() {

	}

	/**
	 * Método que se ejecuta después de crear un registro en la base de datos y
	 * en el que la clase que extienda esta clase puede añadir funcionalidad
	 * personalizada.
	 */
	public void postCrear() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param pData
	 *            {@inheritDoc}
	 */
	@Override
	public void crear(T pData) {
		preGuardar();
		preCrear();
		mp.create(pData);
		postCrear();
		postGuardar();
	}

	/**
	 * Método que se ejecuta antes de actualizar un registro en la base de datos
	 * y en el que la clase que extienda esta clase puede añadir funcionalidad
	 * personalizada.
	 */
	public void preActualizar() {

	}

	/**
	 * Método que se ejecuta después de crear un registro en la base de datos y
	 * en el que la clase que extienda esta clase puede añadir funcionalidad
	 * personalizada.
	 */
	public void postActualizar() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param pData
	 *            {@inheritDoc}
	 */
	@Override
	public void actualizar(T pData) {
		preGuardar();
		preActualizar();
		mp.update(pData);
		postActualizar();
		postGuardar();
	}

	/**
	 * Método que se ejecuta antes de eliminar un registro en la base de datos y
	 * en el que la clase que extienda esta clase puede añadir funcionalidad
	 * personalizada.
	 */
	public void preEliminar() {

	}

	/**
	 * Método que se ejecuta después de eliminar un registro en la base de datos
	 * y en el que la clase que extienda esta clase puede añadir funcionalidad
	 * personalizada.
	 */
	public void postEliminar() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param pData
	 *            {@inheritDoc}
	 */
	@Override
	public void eliminar(T pData) {

		preEliminar();
		mp.delete(pData);
		postEliminar();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param pId
	 *            {@inheritDoc}
	 */
	@Override
	public void eliminarPorId(U pId) {

		preEliminar();
		mp.delete(getClaseEntidad(), pId);
		postEliminar();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param filtros
	 *            {@inheritDoc}
	 */
	@Override
	public void eliminarPorFiltro(Collection<InformacionFiltro> filtros) {

		preEliminar();
		Query query = construirJpqlQuery(filtros, null, null, null, TipoQuery.DELETE,
				new InformacionAgrupamiento(FuncionAgrupamientoJPQL.NINGUNA));
		query.executeUpdate();
		postEliminar();
	}

	/**
	 * Método que se ejecuta antes de crear o actualizar un registro en la base
	 * de datos y en el que la clase que extienda esta clase puede añadir
	 * funcionalidad personalizada.
	 */
	public void preGuardar() {
	}

	/**
	 * Método que se ejecuta después de crear o actualizar un registro en la
	 * base de datos y en el que la clase que extienda esta clase puede añadir
	 * funcionalidad personalizada.
	 */
	public void postGuardar() {
	}

	/**
	 * @return the claseEntidad
	 */
	public Class<T> getClaseEntidad() {
		return claseEntidad;
	}

	/**
	 * @param claseEntidad
	 *            the claseEntidad to set
	 */
	public void setClaseEntidad(Class<T> claseEntidad) {
		this.claseEntidad = claseEntidad;
	}

	/**
	 * Construye una consulta JPQL de tipo SELECT, DELETE o UPDATE para la tabla
	 * de la entidad T con las condiciones de filtrado, ordenamiento,
	 * asignación, rango y agrupamiento especificadas según corresponda al tipo
	 * de consulta.
	 * 
	 * Si la consulta es de tipo SELECT aplican los parametros filtros,
	 * informacionOrdenamiento, rangoConsulta y funcionAgrupamiento.
	 * 
	 * Si la consulta es de tipo UPDATE aplican los parametros filtros e
	 * infoAsignaciones.
	 * 
	 * Si la consulta es de tipo DELETE aplica unicamente el parametro filtros.
	 * 
	 * @param filtros
	 *            Las condiciones de filtrado de la consulta. Si es nulo no se
	 *            aplican condiciones de filtrado.
	 * @param informacionOrdenamiento
	 *            Las condiciones de ordenamiento de la consulta. Si es nulo no
	 *            se aplican condiciones de ordenamiento.
	 * @param infoAsignaciones
	 *            Las asignaciones a realizar en la actualización. Si es nulo no
	 *            se aplican asignaciones. Una actualización debe tener por lo
	 *            menos una asignación.
	 * @param rangoConsulta
	 *            Rango de registros a seleccionar en la consulta realizada.
	 * @param tipoQuery
	 *            Tipo de query a construir: SELECT, UPDATE o DELETE. No puede
	 *            ser nulo.
	 * @param infoAgrupamiento
	 *            Especifica la información de agurpamiento para realizar en las
	 *            consultas tipo SELECT (COUNT, DISTINCT, NINGUNA).
	 * @return El query jpql listo para ejecución.
	 */
	private Query construirJpqlQuery(Collection<InformacionFiltro> filtros,
			Collection<InformacionOrdenamiento> informacionOrdenamiento,
			Collection<InformacionAsignacion> infoAsignaciones, RangoConsulta rangoConsulta, TipoQuery tipoQuery,
			InformacionAgrupamiento infoAgrupamiento) {

		Collection<InformacionFiltro> filters = filtros;
		if (filters == null) {
			filters = Collections.emptyList();
		}

		Collection<InformacionOrdenamiento> ordenamiento = informacionOrdenamiento;
		if (ordenamiento == null) {
			ordenamiento = Collections.emptyList();
		}

		Collection<InformacionAsignacion> asignaciones = infoAsignaciones;
		if (asignaciones == null) {
			asignaciones = Collections.emptyList();
		}

		if (tipoQuery == null) {
			throw new RuntimeException("Se debe especificar un tipo de query para poder construir la consulta");
		}

		StringBuilder jpql = new StringBuilder();
		switch (tipoQuery) {
		case DELETE:
			jpql.append(generarQueryEliminacion(filters));
			break;
		case SELECT:
			jpql.append(generarQueryConsulta(filters, ordenamiento, infoAgrupamiento));
			break;
		case UPDATE:
			jpql.append(generarQueryActualizacion(asignaciones, filters));
			break;
		default:
			jpql.append(generarQueryConsulta(filters, ordenamiento, infoAgrupamiento));
		}

		Query query = mp.createQuery(jpql.toString());
		logger.debug("Query a ejecutar : " + jpql.toString());

		setearValoresConsulta(query, filters, asignaciones, rangoConsulta, tipoQuery);

		return query;

	}

	/**
	 * Setea los valores de parametros de filtrado, de asignación y de rango
	 * dependiendo del tipo de query.
	 * 
	 * @param query
	 *            El query jpql de consulta con los parámetros pendientes por
	 *            asignar un valor.
	 * @param filters
	 *            Las condiciones de filtrado de la consulta. Si es nulo no se
	 *            aplican condiciones de filtrado.
	 * @param asignaciones
	 *            Las asignaciones a realizar en la actualización. Si es nulo no
	 *            se aplican asignaciones. Una actualización debe tener por lo
	 *            menos una asignación.
	 * @param rangoConsulta
	 *            Rango de registros a seleccionar en la consulta realizada.
	 * @param tipoQuery
	 *            Tipo de query a construir: SELECT, UPDATE o DELETE
	 */
	public void setearValoresConsulta(Query query, Collection<InformacionFiltro> filters,
			Collection<InformacionAsignacion> asignaciones, RangoConsulta rangoConsulta, TipoQuery tipoQuery) {

		asignarValoresFiltros(query, filters);

		if (tipoQuery.equals(TipoQuery.UPDATE)) {
			asignarValoresAsignacion(query, asignaciones);
		}

		if (rangoConsulta != null) {
			if (rangoConsulta.isFromValid()) {
				query.setFirstResult(rangoConsulta.getFrom());
			}

			if (rangoConsulta.isToValid()) {
				query.setMaxResults(rangoConsulta.getMaxResultsParameter());
			}
		}

	}

	/**
	 * Construye una consulta jpql de tipo SELECT con las condiciones de
	 * filtrado, ordenamiento y agrupamiento especificadas.
	 * 
	 * @param filtros
	 *            Las condiciones de filtrado de la consulta. Si es nulo no se
	 *            aplican condiciones de filtrado.
	 * @param ordenamientos
	 *            Las condiciones de ordenamiento de la consulta. Si es nulo no
	 *            se aplican condiciones de ordenamiento.
	 * @param infoAgrupamiento
	 *            Especifica la información de agurpamiento para realizar en las
	 *            consultas tipo SELECT (COUNT, DISTINCT, NINGUNA).
	 * @return El query jpql de consulta con los parámetros pendientes por
	 *         asignar un valor.
	 */
	private String generarQueryConsulta(Collection<InformacionFiltro> filtros,
			Collection<InformacionOrdenamiento> ordenamientos, InformacionAgrupamiento infoAgrupamiento) {

		StringBuilder jpql = new StringBuilder();
		jpql.append(TipoQuery.SELECT.toString());

		FuncionAgrupamientoJPQL funcionAgrupamiento = infoAgrupamiento.getFuncionAgrupamiento();
		switch (funcionAgrupamiento) {
		case DISTINCT:
			jpql.append(" ").append(funcionAgrupamiento.toString()).append("( p.")
					.append(infoAgrupamiento.getAtributo()).append(" )");
			break;
		case COUNT:
			jpql.append(" ").append(funcionAgrupamiento.toString()).append("( p )");
			break;
		case NINGUNA:
			jpql.append(" p");
			break;
		default:
			jpql.append(" p");
		}

		jpql.append(" FROM ");
		jpql.append(claseEntidad.getSimpleName()).append(" p ");
		jpql.append(getJpqlWhere(filtros));
		jpql.append(getJpqlOrder(ordenamientos));

		return jpql.toString();
	}

	/**
	 * Construye una consulta jpql de tipo UPDATE con las condiciones de
	 * filtrado, y asignación especificadas.
	 * 
	 * @param asignaciones
	 *            Las asignaciones a realizar en la actualización. Si es nulo no
	 *            se aplican asignaciones. Una actualización debe tener por lo
	 *            menos una asignación.
	 * @param filtros
	 *            Las condiciones de filtrado de la consulta. Si es nulo no se
	 *            aplican condiciones de filtrado.
	 * @return El query jpql de actualización con los parámetros pendientes por
	 *         asignar un valor.
	 */
	private String generarQueryActualizacion(Collection<InformacionAsignacion> asignaciones,
			Collection<InformacionFiltro> filtros) {

		StringBuilder jpql = new StringBuilder();
		jpql.append(TipoQuery.UPDATE.toString());
		jpql.append(" ");
		jpql.append(claseEntidad.getSimpleName()).append(" p ");
		jpql.append(getJpqlSet(asignaciones));
		jpql.append(getJpqlWhere(filtros));

		return jpql.toString();
	}

	/**
	 * Construye una consulta jpql de tipo DELETE con las condiciones de
	 * filtrado especificadas.
	 * 
	 * @param filtros
	 *            Las condiciones de filtrado de la consulta. Si es nulo no se
	 *            aplican condiciones de filtrado.
	 * @return El query jpql de eliminación con los parámetros pendientes por
	 *         asignar un valor.
	 */
	private String generarQueryEliminacion(Collection<InformacionFiltro> filtros) {

		StringBuilder jpql = new StringBuilder();
		jpql.append(TipoQuery.DELETE.toString());
		jpql.append(" FROM ");
		jpql.append(claseEntidad.getSimpleName()).append(" p ");
		jpql.append(getJpqlWhere(filtros));

		return jpql.toString();
	}

	/**
	 * Asigna los valores de los filtros especificados en el query.
	 * 
	 * @param query
	 *            Query jpql a ejecutar en la base de datos
	 * @param filtros
	 *            Filtros previamentes definidos en el query. Si es nulo no se
	 *            realiza ninguna asignación.
	 * @return El query con los parámetros de filtrado seteados.
	 */
	private void asignarValoresFiltros(Query query, Collection<InformacionFiltro> filtros) {
		for (InformacionFiltro filtro : filtros) {
			if (NULL_VALUE.equals(filtro.valor.toString()) || NOT_NULL_VALUE.equals(filtro.valor.toString())) {
				break;
			}
			Parameter parameter = query.getParameter(obtenerNombreParametro(filtro.campo));
			String pValue = filtro.valor.toString();
			if (filtro.tipo.equals(TipoFiltro.LIKE) || filtro.tipo.equals(TipoFiltro.NOT_LIKE)) {
				pValue = "%" + pValue.toUpperCase() + "%";
			}
			UtilReflection.setParameter(query, parameter, pValue);
		}
	}

	/**
	 * Asigna los valores de las asignaciones especificadas en el query.
	 * 
	 * @param query
	 *            query Query jpql a ejecutar en la base de datos
	 * @param asignaciones
	 *            Las asignaciones a realizar en la actualización. Si es nulo no
	 *            se aplican asignaciones. Una actualización debe tener por lo
	 *            menos una asignación.
	 * @return El query con los parámetros de actualización seteados.
	 */
	private void asignarValoresAsignacion(Query query, Collection<InformacionAsignacion> asignaciones) {
		for (InformacionAsignacion asignacion : asignaciones) {
			if (NULL_VALUE.equals(asignacion.valor.toString()) || NOT_NULL_VALUE.equals(asignacion.valor.toString())) {
				break;
			}
			Parameter parameter = query.getParameter(obtenerNombreParametro(asignacion.campo));
			String pValue = asignacion.valor.toString();
			UtilReflection.setParameter(query, parameter, pValue);
		}
	}

	/**
	 * Devuelve la condición de ordenamiento para una consulta jpql a partir de
	 * los ordenamientos definidos.
	 * 
	 * @param informacionOrdenamiento
	 *            Las condiciones de ordenamiento de la consulta. Si es nulo no
	 *            se aplican condiciones de ordenamiento.
	 * @return Una claúsula ORDER BY para añadir a una consulta jpql de tipo
	 *         select.
	 */
	private String getJpqlOrder(Collection<InformacionOrdenamiento> informacionOrdenamiento) {

		StringBuilder orderClause = new StringBuilder();
		if (!informacionOrdenamiento.isEmpty()) {
			orderClause.append(" ORDER BY ");
		}
		for (InformacionOrdenamiento ordenamiento : informacionOrdenamiento) {
			if (ordenamiento.tipo != TipoOrdenamiento.SIN_ORDENAR) {
				if (orderClause.length() > 10) {
					orderClause.append(", ");
				}
				orderClause.append("p.").append(ordenamiento.campo);
				if (ordenamiento.tipo == TipoOrdenamiento.ASCENDENTE) {
					orderClause.append(" ASC ");
				} else {
					orderClause.append(" DESC ");
				}
			}
		}

		return orderClause.toString();
	}

	/**
	 * Devuelve las asignaciones de una actualización jpql (claúsula SET) a
	 * partir de las asiganciones definidas.
	 * 
	 * @param asignaciones
	 *            Las asignaciones a definir en el SET de la actualización.
	 * @return Una claúsula SET para añadir a una actualización jpql.
	 */
	private String getJpqlSet(Collection<InformacionAsignacion> asignaciones) {
		StringBuilder jpqlSet = new StringBuilder();
		if (!asignaciones.isEmpty()) {
			jpqlSet.append(" SET ");
		}
		for (InformacionAsignacion asignacion : asignaciones) {
			if (asignacion.valor != null) {
				if (jpqlSet.length() > 5) {
					jpqlSet.append(", ");
				}
				jpqlSet.append(obtenerAsignacion(asignacion));
			}
		}

		return jpqlSet.toString();

	}

	/**
	 * Devuelve la asignación como una cadena de caracteres para añadir en una
	 * claúsula SET de una actualización jpql. Ej. nombre=:nombre donde :nombre
	 * es el parámetro a asignar un valor.
	 * 
	 * @param asignacion
	 *            La asignación a transformar.
	 * @return Una condición de asignación de la claúsula SET.
	 */
	private String obtenerAsignacion(InformacionAsignacion asignacion) {
		return asignacion.campo + " = :" + obtenerNombreParametro(asignacion.campo);
	}

	/**
	 * Devuelve una condición de filtrado para una consulta jpql a partir de los
	 * filtros definidos.
	 * 
	 * @param filtros
	 *            Filtros previamentes definidos en el query. Si es nulo no se
	 *            realiza ninguna asignación.
	 * @return Una claúsula WHERE para añadir a una consulta jpql de tipo
	 *         select.
	 */
	private String getJpqlWhere(Collection<InformacionFiltro> filtros) {

		StringBuilder jpqlWhere = new StringBuilder();
		if (!filtros.isEmpty()) {
			jpqlWhere.append(" WHERE ");
		}
		for (InformacionFiltro filtro : filtros) {
			if (filtro.valor != null) {
				if (jpqlWhere.length() > 7) {
					jpqlWhere.append(" ");
					jpqlWhere.append(filtro.operador);
					jpqlWhere.append(" ");
				}
				jpqlWhere.append(obtenerCondicionFiltro(filtro));
			}
		}

		return jpqlWhere.toString();

	}

	/**
	 * Devuelve la condición de filtrado como una cadena de caracteres para
	 * añadir en una claúsula WHERE de una consulta jpql. Ej. nombre=:nombre
	 * donde :nombre es el parámetro a asignar un valor.
	 * 
	 * @param filtro
	 *            El filtro a transformar
	 * @return Una condición de filtrado de la claúsula WHERE.
	 */
	private String obtenerCondicionFiltro(InformacionFiltro filtro) {

		StringBuilder condicionJpql = new StringBuilder();

		switch (filtro.tipo) {
		case LIKE:
			condicionJpql.append("UPPER(").append("p.").append(filtro.campo).append(")");
			condicionJpql.append(" LIKE ");
			break;
		case NOT_LIKE:
			condicionJpql.append("UPPER(").append("p.").append(filtro.campo).append(")");
			condicionJpql.append(" NOT LIKE");
			break;
		case MAYOR:
			condicionJpql.append("p.").append(filtro.campo);
			condicionJpql.append(" > ");
			break;
		case MAYOR_O_IGUAL:
			condicionJpql.append("p.").append(filtro.campo);
			condicionJpql.append(" >= ");
			break;
		case MENOR:
			condicionJpql.append("p.").append(filtro.campo);
			condicionJpql.append(" < ");
			break;
		case MENOR_O_IGUAL:
			condicionJpql.append("p.").append(filtro.campo);
			condicionJpql.append(" <= ");
			break;
		case DIFERENTE:
			condicionJpql.append("p.").append(filtro.campo);
			condicionJpql.append(" <> ");
			break;
		default:
			condicionJpql.append("p.").append(filtro.campo);
			condicionJpql.append(obtenerComparadorCondicion(filtro.valor.toString()));
		}

		condicionJpql.append(" :").append(obtenerNombreParametro(filtro.campo)).append(" ");

		return condicionJpql.toString();
	}

	/**
	 * Obtiene el comparador a utilizar cuando el idReducido del TipoFiltro no
	 * concuerda con el utilizado en las consultas jpql.
	 * 
	 * @param valorFiltro
	 *            Nombre del TipoFiltro (TipoFiltro.toString())
	 * @return El comparador a insertar en la condición de la consulta jpql.
	 */
	private String obtenerComparadorCondicion(String valorFiltro) {

		StringBuilder comparador = new StringBuilder();

		if (NULL_VALUE.equals(valorFiltro)) {
			comparador.append(" IS NULL ");
		} else if (NOT_NULL_VALUE.equals(valorFiltro)) {
			comparador.append(" IS NOT NULL ");
		} else {
			comparador.append(" = ");
		}

		return comparador.toString();

	}

	/**
	 * Ejecuta el query nativo que se pasa como parámetro con los fitlros
	 * definidos.
	 * 
	 * @param querystr
	 *            El query a ejecutar sin una claúsula WHERE
	 * @param filtros
	 *            Los filtros a añadir a la consulta
	 * @return El resultado de ejecución de la consulta
	 */
	private List<Object[]> doNativeQuery(String querystr, Collection<InformacionFiltro> filtros) {

		String sqlWhere = getNativeSqlWhere(filtros);
		if (!filtros.isEmpty()) {
			return mp.doNativeQuery(querystr + " " + sqlWhere);
		} else {
			return mp.doNativeQuery(querystr);
		}

	}

	/**
	 * Devuelve la condición de filtrado a añadir a una consulta JPQL
	 * 
	 * @param filtros
	 *            Los filtros a añadir a la consulta
	 * @return Una claúsula WHERE con la información de filtrado especificada.
	 */
	private String getNativeSqlWhere(Collection<InformacionFiltro> filtros) {

		String rs = "";
		if (!filtros.isEmpty()) {
			rs = "where ";
			int idx = 0;
			for (InformacionFiltro filtro : filtros) {

				String concatenador = idx > 0 ? " and " : "";
				String comparador = filtro.tipo == TipoFiltro.LIKE ? "like" : "=";
				String aperturaValor = filtro.tipo == TipoFiltro.LIKE ? "'%" : "'";
				String cierreValor = filtro.tipo == TipoFiltro.LIKE ? "%'" : "'";
				rs += concatenador + filtro.campo + " " + comparador + " " + aperturaValor + filtro.valor + cierreValor;

				idx++;

			}
		}

		return rs;
	}

	/**
	 * Debido a que en los nombres de los parametros de las consultas no se
	 * puede insertar el caracter '.' y algunos nombres compuestos de atributos
	 * de entidades se identifican con el nombre de la entidad del PK compuesto,
	 * seguido del carácter '.' y del nombre del atributo, se reemplaza el '.'
	 * por el caracter '_'.
	 * 
	 * @param nombreAtributo
	 *            Nombre del atributo a parsear
	 * @return El nombre del atributo parseado para su utilización como nombre
	 *         del parámetro.
	 */
	private String obtenerNombreParametro(String nombreAtributo) {
		return nombreAtributo.replaceAll("[.]", "_");
	}
	
	@SuppressWarnings("unchecked")
	public List<T> createNamedQuery(ArrayList<Object> parameters, String namedQuery) {
		Query query = mp.createNamedQuery(namedQuery);
		if (parameters != null) {
			int count = 1;
			for (Object o: parameters) {
				query.setParameter(count, o);
				count++;
			}
		}
		logger.info("Inicia consulta: "+ query);
		return query.getResultList();
	}
	
	public void confirmar() {
		mp.flush();
	}

}
