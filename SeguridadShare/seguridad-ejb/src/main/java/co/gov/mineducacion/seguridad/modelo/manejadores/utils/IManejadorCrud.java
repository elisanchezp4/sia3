package co.gov.mineducacion.seguridad.modelo.manejadores.utils;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @author jsoto
 * @param <T> Clase de la entidad asociada al manejador
 * @param <U> Clase del pk de la entidad T. Si es un PK compuesto es una clase con la etiqueta @Embbeded, sino una clase "primitiva" como Integer, Long, String, etc.
 */
public interface IManejadorCrud<T,U> {

    /**
     * Busca el objeto de tipo T cuyo identificar es pId
     * 
     * @param pId Identificador el objeto de clase T
     * @return El objeto de la entidad T encontrado en la busqueda
     */
    public T buscar(U pId);   

    /**
     * Crea el objeto de clase T que se pasa como parámetro en la base de datos
     * @param obj Instancia de la entidad T a almacenar en la base de datos
     */
    public void crear(T obj);

    /**
     * Crea una instancia de la clase T sin ningún valor seteado
     * @return Instancia de la entidad T sin valores asignados
     */
    public T crear();

    /**
     * Actualiza en base de datos el registro correspondiente al objeto de la entidad
     * T que se pasa como parámetro
     * @param obj Instancia de la entidad T a actualizar en la base de datos
     */
    public void actualizar(T obj);
    
    /**
     * Actualiza en base de datos el registro correspondiente al objeto de la entidad
     * T que se pasa como parámetro
     * @param obj Instancia de la entidad T a actualizar en la base de datos
     */
    /**
     * Ejecuta un query de actualización para la entidad T aplicando los filtros y las
     * asignaciones que se definan en los parámetros.
     * 
     * @param asignaciones Lista con los campos y sus correspondientes valores a actualizar
     * @param filtros Los filtros que definen los registros a actualizar. Si es nulo no se aplica
     * filtro y se actualizan todos los registros de la tabla.
     */
    public void actualizarPorFiltros(Collection<InformacionAsignacion> asignaciones, Collection<InformacionFiltro> filtros);

    /**
     * Elimina en base de datos el registro correspondiente al objeto de la entidad
     * T que se pasa como parámetro
     * @param obj Instancia de la entidad T a eliminar de la base de datos
     */
    public void eliminar(T obj);

    /**
     * Elimina en base de datos el registro correspondiente al objeto de la entidad
     * T que se identifica con el parámetro pId
     * @param pId Instancia del identificador del objeto de la entidad T que se va a eliminar 
     * de la base de datos.
     */
    public void eliminarPorId(U pId);
    
    /**
     * Elimina en base de datos el o los registro(s) correspondiente(s) a la entidad
     * T que cumplen con el criterio definido en la lista de filtros que se pasa 
     * como parámetro.
     * @param filtros Los filtros que definen los registros a eliminar. Puede ser nulo.
     */
    public void eliminarPorFiltro(Collection<InformacionFiltro> filtros);

    /**
     * Devuelve el total de registros encontrados tras consultar la tabla correspondiente
     * a la entidad T que cumplen los criterios de búsqueda especificados en los parámetros.
     * @param filtros Los filtros que definen los registros a consultar. Si es nulo no se aplica ningún filtro.
     * @param rangoConsulta El rango de registros sobre el cual limitar la consulta. Si
     *  es nulo no se aplica ningún rango.
     * @return El total de registro encontrados en la consulta.
     */
    public Integer consultarTotalRegistros(Collection<InformacionFiltro> filtros, RangoConsulta rangoConsulta);

    /**
     * Realiza una consulta jpql (select) a la tabla correspondiente a la entidad T aplicando los filtros, ordenamiento
     * y restricciones de rango especificados en los parámetros.
     * 
     * @param filtros Los filtros que definen los registros a consultar. Si es nulo no se aplica ningún filtro. Si es
     *  nulo no se aplica ningún filtro a la consulta.
     * @param informacionOrdenamiento Definición del ordenamiento a realizar en la consulta. De igual forma que vienen definidos
     * en la lista se inserta en la consulta JPQL. Si la lista es nula se deja el ordenamiento por defecto de la consulta.
     * @param rangoConsulta Define el bloque de datos a seleccionar de la consulta realizada con los filtros y ordenamiento especificado.
     *  Si no se define ningún rango se retornan todos los objetos consultados.
     * @return Una lista de objetos de la entidad T.
     */
    public List<T> consultar(Collection<InformacionFiltro> filtros, Collection<InformacionOrdenamiento> informacionOrdenamiento, RangoConsulta rangoConsulta);
    
    
    /**
     * Devuelve una lista de los diferentes valores que se encuentran en la columna especificada en la tabla T 
     * aplicando los filtros y ordenamientos especificados.
     * 
     * @param filtros Los filtros que definen los registros a consultar. Si es nulo no se aplica ningún filtro. Si es
     *  nulo no se aplica ningún filtro a la consulta.
     * @param infoOrdenamiento Definición del ordenamiento a realizar en la consulta. De igual forma que vienen definidos
     * en la lista se inserta en la consulta JPQL. Si la lista es nula se deja el ordenamiento por defecto de la consulta.
     * @param infoAgrupamiento Especifica la información de agurpamiento para realizar en las consultas tipo SELECT (COUNT, DISTINCT, NINGUNA).
     * @return Una lista de elementos no repetidos
     */
    public List<Object> consultarLista(Collection<InformacionFiltro> filtros, Collection<InformacionOrdenamiento> infoOrdenamiento, InformacionAgrupamiento infoAgrupamiento);
    
}
