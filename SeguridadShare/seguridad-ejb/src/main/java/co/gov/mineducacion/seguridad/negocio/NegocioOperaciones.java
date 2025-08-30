package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.RespuestaImportarOperacionesDTO;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.dtos.MapasDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;

import java.util.*;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRol;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperacionesRol;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;

import java.math.BigDecimal;
import java.sql.Timestamp;

// protected region Incluya importaciones adicionales en esta seccion on begin

// protected region Incluya importaciones adicionales en esta seccion end

/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad Operaciones
 *
 * @author jsoto
 */
@Stateless
public class NegocioOperaciones extends NegocioAbstracto<Operaciones, OperacionesDTO> {

    @EJB
    private ManejadorOperaciones manejadorOperaciones;

    @EJB
    private NegocioAplicaciones negocioAplicacion;

    @EJB
    private ManejadorOperacionesRol manejadorOperacionesRol;

    @EJB
    private NegocioOperacionesRol negocioOperacionesRol;

    @EJB
    NegocioUsuariosRol negocioUsuariosRol;

    //Inicio variables HBT
    @EJB
    private NegocioAplicaciones negocioAplicaciones;

    @EJB
    private NegocioBitacoraAuditoria auditoria;
    //Fin variables HBT

    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(NegocioOperaciones.class.getName());
    private static final String OPERACION_RAIZ = "Es una operacion raiz, se guardara con padre nulo";
    private static final String ERROR_AUDITORIA = "Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio";
    private static final String MENSAJE = ". Mensaje:";

    // protected region Declare atributos adicionales en esta seccion on begin

    // protected region Declare atributos adicionales en esta seccion end

    /**
     * Realiza un consulta en la entidad Operaciones aplicando los filtros, el
     * ordenamiento, y el rango (from y to) que se pasan como parámetro. Los
     * parámetros filterBy y orderBy pueden ser nulos. El parámetro from y to
     * están relacionados. Si from es diferente de nulo to puedo ser nulo, pero
     * no al revés. Ambos pueden ser nulos, en cuyo caso no se aplica una
     * restricción de rango a la consulta.
     *
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere filtrar, seguido por un operador de comparación que
     *                 puede tomar los valores
     *                 {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
     *                 por último el valor por el que se quiere filtrar. Los filtros
     *                 se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     *                 Ej. Una secuencia de parámetros de filtrado puede ser
     *                 {@literal operacionesId>1&operacionesName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser: operacionesId$ASC,
     *                 operacionesName$DESC
     * @param from     Número de registro inicial que se quiere retornar de la
     *                 consulta realizada. Entero mayor o igual a 0
     * @param to       Número de registro final que se quiere retornar de la consulta
     *                 realizada. Entero mayor o igual al parámetro from
     * @return Una lista de DAOs de los Operaciones que se consultaron con los
     * parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url
     *                                   tenía un error de sintáxis por lo que no pudo ser procesado
     *                                   correctamente
     */
    public List<OperacionesDTO> consultar(String filterBy, String orderBy, Integer from, Integer to)
            throws InvalidParameterException {
        // protected region Modifique el metodo consultar on begin
        logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
        RangoConsulta rango = validarParametrosBloque(from, to);

        return convertirListaEntidadesADao(manejadorOperaciones.consultar(filtros, ordenamiento, rango));
        // protected region Modifique el metodo consultar end
    }

    /**
     * Crea el operaciones que se pasa como parámetro en la base de datos. HBT
     * incluye manejo de excepciones y validacion de datos antes de la creacion
     *
     * @param operacionesDTO El DAO de la entidad Operaciones a crear. Este se envía en el
     *                       cuerpo de la solicitud POST como un objeto JSON.
     * @return La insntancia de Operaciones recién creado
     */
    public void crear(OperacionesDTO operacionesDTO) throws SIA3Exception {
        // protected region Modifique el metodo crear on begin
        try {
            //verificar que nombres y url's vengan sin espacios
            operacionesDTO.setDescripcion(operacionesDTO.getDescripcion().trim());
            operacionesDTO.setNombreObjeto(operacionesDTO.getNombreObjeto().trim());
            if (operacionesDTO.getUrlImgGif() != null) {
                operacionesDTO.setUrlImgGif((operacionesDTO.getUrlImgGif().trim()).replace(" ", ""));
            }


            Aplicaciones aplicacion = BuilderDTO
                    .toAplicacion(negocioAplicacion.buscarAplicacion(operacionesDTO.getAplicacionId().toString()));
            operacionesDTO.setAplicaciones(aplicacion);
            logger.info("******obtiene Aplicacion=>" + aplicacion);
            validarOperaciones(operacionesDTO);
            logger.info("*****Validacion de campos satisfactoria. Preparado para crear operacion");
            logService(this.getClass().getName(), "crear", operacionesDTO);

            Operaciones operaciones = new Operaciones();
            copiarPropiedades(operaciones, operacionesDTO);
            logger.info("*****Las propiedades de operaciones:" + operaciones);
            // Inicio sentencias HBT
            // Setear fecha de creacion y modificacion
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            operaciones.setFechaCreacion(timestampActual);
            operaciones.setUltimaModificacion(timestampActual);

            //Si es una operacion raiz setea en null opcion padre
            if (operacionesDTO.getOpcionPadre() == null || operacionesDTO.getOpcionPadre().compareTo(BigDecimal.ZERO) == 0) {
                logger.error(OPERACION_RAIZ);
                operaciones.setOpcionPadre(null);
            }

            // Fin sentencias HBT

            manejadorOperaciones.crear(operaciones);

            //Registrar en auditoria el evento
            logger.info("Inicio registro auditoria evento OPERATION_CREATED");
            //Validar que venga el usuario que realiza la operacion
            if (operacionesDTO.getUsuarioModificacion() == null) {
                logger.error(ERROR_AUDITORIA);
                throw new SIA3Exception(MessagesConstants.APP100120);
            }
            auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.OPERATION_CREATED), operacionesDTO.getUsuarioModificacion().toString(), Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Crea operacion con nombre: " + operacionesDTO.getNombreObjeto() + ", Aplicacion: " + operacionesDTO.getAplicaciones().getNombre());
            logger.info("Fin registro auditoria evento OPERATION_CREATED");

        } catch (SIA3Exception se) {
            logger.error("Error al validar operacionDTO al crear Operacion");
            throw new SIA3Exception(MessagesConstants.APP100054);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de crear Operacion: " + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

        // protected region Modifique el metodo crear end
    }

    /**
     * Actualiza en la base de datos el operaciones que se pasa como parámetro.
     * HBT cambia firma de metodo a tipo void agrega metodo para validar DTO
     * antes de actualizar
     *
     * @param operacionesDTO El DAO de la entidad Operaciones a actualizar. Este se envía
     *                       en el cuerpo de la solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad Operaciones que ha sido actualizado
     */
    public void actualizar(OperacionesDTO operacionesDTO) throws SIA3Exception {
        logger.info("Inicia proceso para actualizar operaciones: " + new Gson().toJson(operacionesDTO));
        try {
            validarOperaciones(operacionesDTO, true);
            //verificar que nombres y url's vengan sin espacios
            operacionesDTO.setDescripcion(operacionesDTO.getDescripcion().trim());
            operacionesDTO.setNombreObjeto(operacionesDTO.getNombreObjeto().trim());
            if (operacionesDTO.getUrlImgGif() != null) {
                operacionesDTO.setUrlImgGif((operacionesDTO.getUrlImgGif().trim()).replace(" ", ""));
            }

            logService(this.getClass().getName(), "actualizar", operacionesDTO);

            Operaciones operaciones = manejadorOperaciones.buscar(operacionesDTO.getOpcionId());
            copiarPropiedades(operaciones, operacionesDTO);

            // Inicio sentencias HBT
            // Setear fecha de creacion y modificacion
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            operaciones.setFechaCreacion(timestampActual);
            operaciones.setUltimaModificacion(timestampActual);

            //Si es una operacion raiz setea en null opcion padre
            if (operacionesDTO.getOpcionPadre() == null || operacionesDTO.getOpcionPadre().compareTo(BigDecimal.ZERO) == 0) {
                logger.warn(OPERACION_RAIZ);
                operaciones.setOpcionPadre(null);
            }

            // Fin sentencias HBT

            manejadorOperaciones.actualizar(operaciones);

            //Registrar en auditoria el evento
            logger.info("Inicio registro auditoria evento OPERATION_MODIFIED");
            //Validar que venga el usuario que realiza la operacion
            if (operacionesDTO.getUsuarioModificacion() == null) {
                logger.error(ERROR_AUDITORIA);
                throw new SIA3Exception(MessagesConstants.APP100120);
            }
            auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.OPERATION_MODIFIED), operacionesDTO.getUsuarioModificacion().toString(), Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Actualiza operacion con nombre:" + operacionesDTO.getNombreObjeto());
            logger.info("Fin registro auditoria evento OPERATION_MODIFIED");

        } catch (SIA3Exception se) {
            logger.error("Error al validar operacionesDTO al editar Operacion");
            throw new SIA3Exception(MessagesConstants.APP100056);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de editar Operacion");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

        // protected region Modifique el metodo actualizar end
    }

    /**
     * Elimina el operaciones con el identificador que se pasa como parámetro.
     *
     * @param opcionId Valor del atributo del identificador de la instancia de la
     *                 entidad operaciones a eliminar
     * @return El identificador del operaciones que ha sido eliminado
     */
    public String eliminar(@QueryParam("opcionId") BigDecimal opcionId) {
        // protected region Modifique el metodo eliminar on begin

        logService(this.getClass().getName(), "eliminar", opcionId);
        manejadorOperaciones.eliminarPorId(opcionId);

        StringBuilder valores = new StringBuilder();
        valores.append(String.valueOf(opcionId));
        return valores.toString();
        // protected region Modifique el metodo eliminar end
    }

    /**
     * Cuenta la cantidad de registros que devuelve la consulta a la tabla de
     * aplicando los filtros o rangos que se pasen como parámetro. Estos pueden
     * ser nulos, en cuyo caso a la consulta no se le realiza ningún tipo de
     * filtrado.
     *
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere filtrar, seguido por un operador de comparación que
     *                 puede tomar los valores
     *                 {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
     *                 por último el valor por el que se quiere filtrar. Los filtros
     *                 se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     *                 Ej. Una secuencia de parámetros de filtrado puede ser
     *                 {@literal operacionesId>1&operacionesName:LIKE:juan}
     * @param from     Número de registro inicial que se quiere retornar de la
     *                 consulta realizada. Entero mayor o igual a 0
     * @param to       Número de registro final que se quiere retornar de la consulta
     *                 realizada. Entero mayor o igual al parámetro from
     * @return El número de registros contados a partir de los parámetros
     * enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url
     *                                   tenía un error de sintáxis por lo que no pudo ser procesado
     *                                   correctamente
     */
    public String contar(String filterBy, Integer from, Integer to) throws InvalidParameterException {
        // protected region Modifique el metodo contar on begin

        logService(this.getClass().getName(), "contar", filterBy);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        RangoConsulta rango = validarParametrosBloque(from, to);

        return String.valueOf(manejadorOperaciones.consultarTotalRegistros(filtros, rango));
        // protected region Modifique el metodo contar end
    }

    /**
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere filtrar, seguido por un operador de comparación que
     *                 puede tomar los valores
     *                 {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
     *                 por último el valor por el que se quiere filtrar. Los filtros
     *                 se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     *                 Ej. Una secuencia de parámetros de filtrado puede ser
     *                 {@literal operacionesId>1&operacionesName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser: operacionesId$ASC,
     *                 operacionesName$DESC
     * @param atributo Nombre del atributo de la entidad Operaciones del cual se
     *                 quieren obtener los diferentes valores.
     * @return Una lista con los diferentes valores que se encuentran en la
     * columna de la tabla asociada al atributo.
     * @throws InvalidParameterException Si el atributo no existe en la entidad o si los filtros y el
     *                                   ordenamiento contienen atributos de la entidad que no
     *                                   existen.
     */
    public List<String> consultarLista(String filterBy, String orderBy, String atributo)
            throws InvalidParameterException {
        // protected region Modifique el metodo consultarLista on begin

        logService(this.getClass().getName(), "contar", filterBy, orderBy, atributo);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
        InformacionAgrupamiento infoAgrupamiento = decodificarInformacionAgrupamiento(atributo);

        return UtilOperaciones.convertirListaObjetosAString(
                manejadorOperaciones.consultarLista(filtros, ordenamiento, infoAgrupamiento));
        // protected region Modifique el metodo consultarLista end
    }

    /**
     * {@inheritDoc}
     *
     * @param nombreAtributo {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean entidadContieneAtributo(String nombreAtributo) {
        return Operaciones.contieneAtributo(nombreAtributo);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    protected OperacionesDTO instanciarDAO() {
        return new OperacionesDTO();
    }

    // protected region Use esta region para su implementacion de otros metodos
    // on begin

    // protected region Use esta region para su implementacion de otros metodos
    // end

    // Inicio metodos HBT

    /**
     * Metodo para obtener todas las operaciones
     *
     * @return
     * @throws SIA3Exception
     */
    public List<OperacionesDTO> getAllOperaciones() throws SIA3Exception {
        try {
            logger.info("Inicio metodo getAllOperaciones");
            List<OperacionesDTO> rolDTOList = BuilderDTO.toOperacionDTOList(manejadorOperaciones.getAllOperaciones());
            if (rolDTOList.isEmpty()) {
                logger.error("Error en metodo consultar: No hay Operaciones.");
                throw new SIA3Exception(MessagesConstants.APP100041);
            }
            logger.info("fin metodo getAllOperaciones");
            return rolDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getAllOperaciones:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que obtiene las operaciones para un rol especifico
     *
     * @param idRol
     * @return
     * @throws SIA3Exception
     */
    public List<OperacionesDTO> getOperacionesPorRol(Long idRol) throws SIA3Exception {
        try {
            logger.info("Inicio metodo getOperacionesPorRol con idRol:" + idRol);
            List<OperacionesDTO> rolDTOList = BuilderDTO
                    .toOperacionDTOList(manejadorOperaciones.getOperacionesPorRol(idRol));
            if (rolDTOList.isEmpty()) {
                logger.error("Error en metodo consultar: No hay Operaciones por rol. Detalle:");
                throw new SIA3Exception(MessagesConstants.APP100041);
            }
            logger.info("fin metodo getOperacionesPorRol");
            return rolDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getOperacionesPorRol:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    // Metodos para construir arbol de operaciones

    /**
     * Metodo que construye el arbol de operaciones tomando como base todos los
     * roles.
     *
     * @param aplicacionId. Id de la aplicacion. Obligatorio
     * @param nombreObjeto. Nombre de la operación. Opcional
     * @return
     * @throws SIA3Exception
     */
    public List<OperacionesDTO> armarArbolPermisosRol(BigDecimal aplicacionId, String nombreObjeto)
            throws SIA3Exception {
        // busca las operaciones y su rol respectivo que correspondan a la
        // aplicacion y nombre operacion respectiva
        MapasDTO optionsRole = buscarOperacionesPorRol(aplicacionId, nombreObjeto);

        if (optionsRole.getMapaCompleto().isEmpty())
            throw new SIA3Exception(MessagesConstants.APP100044);
        // obtener mapa padre hijo
        Map<String, ArrayList<BigDecimal>> mapaOcurrenciaPadreHijo = obtenerMapaReversado(
                optionsRole.getMapaPadreHijo());
        // armar lista de operaciones con el arbol completo
        List<OperacionesDTO> permisos = obtenerNodos(optionsRole.getMapaCompleto(), mapaOcurrenciaPadreHijo, "null");
        return permisos;
    }

    /**
     * Metodo que busca las operaciones relacionadas por cada rol registrado que
     * coincidan con la aplicacion y nombre de operacion indicado
     *
     * @param aplicacionId. Id de la aplicacon (Obligatorio)
     * @param nombreObjeto. Nombre de la operacion (opcional)
     * @return
     * @throws SIA3Exception
     */
    public MapasDTO buscarOperacionesPorRol(BigDecimal aplicacionId, String nombreObjeto) throws SIA3Exception {
        TreeMap<BigDecimal, BigDecimal> mapaPadreHijo = new TreeMap<>();
        TreeMap<String, OperacionesDTO> mapaCompleto = new TreeMap<>();
        // Validar que venga el id de la aplicacion
        if (aplicacionId == null || aplicacionId.compareTo(BigDecimal.ZERO) == 0) {
            logger.error("Error en metodo buscarOperacionesPorRol: El campo aplicacionId es obligatorio.");
            throw new SIA3Exception(MessagesConstants.APP100082);
        }

        List<Operaciones> operacionesRol = manejadorOperaciones.getOperacionesPorFiltro(aplicacionId, nombreObjeto);
        // Si no encuentra operaciones que coincidann con el filtro de busqueda
        // mostrar mensaje
        logger.info("Operaciones rol : " + operacionesRol);
        if (operacionesRol.isEmpty()) {
            logger.error("No se encuentran operaciones con los filtros seleccionados.");
            throw new SIA3Exception(MessagesConstants.APP100041);
        }

        for (Operaciones optionRole : operacionesRol) {
            OperacionesDTO operacionesDTO = instanciarDAO();
            copiarPropiedades(operacionesDTO, optionRole);
            mapaPadreHijo.put(optionRole.getOpcionId(), optionRole.getOpcionPadre());

            mapaCompleto.put(optionRole.getOpcionId() + "", operacionesDTO);
        }
        logger.info("MapaCompleto : " + mapaCompleto);
        return new MapasDTO(mapaPadreHijo, mapaCompleto);
    }

    /**
     * Retorna el mapa reversado de un mapa
     *
     * @param mapa
     * @return
     */
    private Map<String, ArrayList<BigDecimal>> obtenerMapaReversado(Map<BigDecimal, BigDecimal> mapa) {
        Map<String, ArrayList<BigDecimal>> reverseMap = new HashMap<>();

        for (Map.Entry<BigDecimal, BigDecimal> entry : mapa.entrySet()) {
            if (!reverseMap.containsKey(entry.getValue() + "")) {
                reverseMap.put(entry.getValue() + "", new ArrayList<>());
            }
            ArrayList<BigDecimal> keys = (reverseMap.get(entry.getValue() + "") != null)
                    ? reverseMap.get(entry.getValue() + "") : new ArrayList<>();
            keys.add(entry.getKey());
            reverseMap.put(entry.getValue() + "", keys);
        }
        return reverseMap;
    }

    /**
     * Metodo que construye recursivamente los nodos del arbol de operaciones
     *
     * @param mapaCompleto
     * @param mapaOcurrenciaPadreHijo
     * @param idPadre
     * @return
     */
    private List<OperacionesDTO> obtenerNodos(Map<String, OperacionesDTO> mapaCompleto,
                                              Map<String, ArrayList<BigDecimal>> mapaOcurrenciaPadreHijo, String idPadre) {
        ArrayList<BigDecimal> padre = mapaOcurrenciaPadreHijo.get(idPadre);
        List<OperacionesDTO> listadoOpciones = new ArrayList<>();
        for (BigDecimal i : padre) {
            // Operaciones
            OperacionesDTO opcion = (OperacionesDTO) mapaCompleto.get(i + "");

            if (mapaOcurrenciaPadreHijo.containsKey(i + "")) {
                opcion.setListadoOperaciones(obtenerNodos(mapaCompleto, mapaOcurrenciaPadreHijo, i.toString()));
            }
            listadoOpciones.add(opcion);
        }

        return listadoOpciones;
    }

    /**
     * Metodo que obtiene una operacion que coincida con el id que se busca
     *
     * @param idOperacion
     * @return
     * @throws SIA3Exception
     */
    public Operaciones consultarOperacionPorId(BigDecimal idOperacion) throws SIA3Exception {
        try {
            logger.info("Inicio metodo consultarOperacionPorId con idOperacion:" + idOperacion);
            Operaciones operacion = manejadorOperaciones.getOperacionPorId(idOperacion);
            if (operacion == null) {
                logger.error("Error en metodo consultarOperacionPorId: No hay operacion con id:" + idOperacion);
                throw new SIA3Exception(MessagesConstants.APP100048);
            }
            logger.info("fin metodo consultarOperacionPorId");
            return operacion;
        } catch (SIA3Exception se) {
            logger.error("Error al consultar operacion con el id especificado");
            throw new SIA3Exception(MessagesConstants.APP100052);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de consultar operación: " + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    private void validarOperaciones(OperacionesDTO operacionDTO) throws SIA3Exception {
        validarOperaciones(operacionDTO, false);
    }

    /**
     * Metodo que valida que los datos que vienen del DTO sean correctos
     *
     * @param operacionDTO
     * @throws SIA3Exception
     */
    private void validarOperaciones(OperacionesDTO operacionDTO, boolean importar) throws SIA3Exception {
        logger.info("Inicio metodo validarOperaciones con operacionDTO => " + operacionDTO);

        // Se valida que los campos que vienen del DTO tengan datos
        logger.info("Valida campo AplicacionId=> " + operacionDTO.getAplicacionId());
        if (operacionDTO.getAplicacionId() == null || operacionDTO.getAplicacionId().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarOperaciones: Campo AplicacionId no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100060);
        }

        validaCampo("Descripcion", operacionDTO.getDescripcion(), MessagesConstants.APP100061);
        validaCampo("NombreObjeto", operacionDTO.getNombreObjeto(), MessagesConstants.APP100062);
        validaCampo("Tipo", operacionDTO.getTipo(), MessagesConstants.APP100064);

        logger.info("Valida campo Estado=> " + operacionDTO.getEstado());
        if (operacionDTO.getEstado() == null || operacionDTO.getEstado().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarOperaciones: Campo Estado no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100065);
        }

        logger.info("Valida campo Usuario Modificacion=> " + operacionDTO.getUsuarioModificacion());
        if (operacionDTO.getUsuarioModificacion() == null || operacionDTO.getUsuarioModificacion().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarOperaciones: Campo Usuario Modificacion no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100066);
        }

        // Validar que no haya otra operacion con el mismo nombre
        if (!importar) {
            List<Operaciones> operacionesExistentes = manejadorOperaciones.buscarOperacionesPorNombre(operacionDTO.getNombreObjeto(), operacionDTO.getAplicacionId().toString());
            logger.info("Validar operaciones existentes: " + new Gson().toJson(operacionesExistentes));
            for (Operaciones operacionLista : operacionesExistentes) {
                if (!operacionLista.getOpcionId().equals(operacionDTO.getOpcionId())) {
                    logger.error("Error en metodo validarOperaciones: Ya existe operación con el nombre objeto:" + operacionDTO.getNombreObjeto());
                    throw new SIA3Exception(MessagesConstants.APP100067);
                }
            }
        }
        logger.info("Fin metodo negocio validarOperaciones");
    }

    private void validaCampo(String nombreCampo, String valor, String codeException) throws SIA3Exception {
        logger.info("Valida campo " + nombreCampo + "=> " + valor);
        if (valor == null || valor.equals("")) {
            logger.error("Error en metodo validarOperaciones: Campo " + nombreCampo + " no puede ser vacio o nulo");
            throw new SIA3Exception(codeException);
        }
    }

    /**
     * Metodo que elimina operación del sistema
     *
     * @param idOperacion
     * @throws SIA3Exception
     */
    public void eliminarOperacion(BigDecimal operacionId, String usuarioId) throws SIA3Exception {
        logger.info("Inicio metodo eliminarOperacion con operacionId =>" + operacionId);
        try {
            String nombreOperacion = "";
            // Verificar que la operacion exista
            List<Operaciones> operacionesExistentes = manejadorOperaciones.buscarOperacionesPorId(operacionId);
            if (operacionesExistentes == null || operacionesExistentes.isEmpty()) {
                logger.error("Error en metodo eliminarOperacion: NO EXISTE operacion con el id:" + operacionId);
                throw new SIA3Exception(MessagesConstants.APP100059);
            }
            // eliminar las relaciones que tenga en operacionesRol
            logger.info("Pasa a eliminar asociaciones operacionrol");
            eliminarAsociacionOperacionRol(operacionId);
            // eliminar la operacion y sus operaciones hijas
            logger.info("Eliminar operacion junto con operaciones hijas");
            List<Operaciones> operacionesXEliminar = obtenerOperacionesHijas(operacionId);
            for (Operaciones operacion : operacionesXEliminar) {
                nombreOperacion = operacion.getNombreObjeto();
                eliminar(operacion.getOpcionId());
            }
            //Registrar en auditoria el evento
            logger.info("Inicio registro auditoria evento OPERATION_DELETED");
            //Validar que venga el usuario que realiza la operacion
            if (usuarioId == null || usuarioId.equals("")) {
                logger.error(ERROR_AUDITORIA);
                throw new SIA3Exception(MessagesConstants.APP100120);
            }
            auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.OPERATION_DELETED), usuarioId, Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Borra operacion con nombre:" + nombreOperacion);
            logger.info("Fin registro auditoria evento OPERATION_DELETED");
            logger.info("Finalizó el metodo eliminarOperacion con operacion => " + operacionId);
        } catch (SIA3Exception e) {
            logger.error("Error en metodo eliminarOperacion:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP100058);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de eliminar Operacion" + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que elimina las relaciones Operacion-Rol existentes para la
     * operacion pasada por parametro
     *
     * @param idOperacion
     * @throws SIA3Exception
     */
    private void eliminarAsociacionOperacionRol(BigDecimal operacionId) throws SIA3Exception {
        try {
            logger.info("Inicio metodo eliminarAsociacionOperacionRol con operacionId => " + operacionId);
            List<OperacionesRol> listaOperacionesRol = negocioOperacionesRol
                    .buscarOperacionRolPorOperacion(operacionId.toString());
            negocioOperacionesRol.eliminarOperacionRolPorOperacion(listaOperacionesRol);
            logger.info("Fin metdo eliminarAsociacionOperacionRol");
        } catch (SIA3Exception se) {
            logger.error("Error en metodo eliminarAsociacionOperacionRol:" + se);
            throw new SIA3Exception(MessagesConstants.APP100083);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de eliminar OperacionRol en eliminarAsociacionOperacionRol:" + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que lista las operaciones hijas que tenga la operacion con id dado
     * por parametro
     *
     * @param operacionId
     * @return
     * @throws SIA3Exception
     */
    private List<Operaciones> obtenerOperacionesHijas(BigDecimal operacionId) throws SIA3Exception {
        try {
            logger.info("Inicio metodo obtenerOperacionesHijas con operacionId => " + operacionId);
            List<Operaciones> listaOperaciones = manejadorOperaciones.buscarOperacionesHijas(operacionId);
            logger.info("Fin metdo obtenerOperacionesHijas. Devuelve listaOperaciones =>" + listaOperaciones);
            if (listaOperaciones.isEmpty()) {
                logger.error("No se encontraron operaciones con el id operacion:" + operacionId);
                throw new SIA3Exception(MessagesConstants.APP100041);
            }
            return listaOperaciones;
        } catch (SIA3Exception se) {
            logger.error("Error en metodo obtenerOperacionesHijas. No hay operaciones con id:" + operacionId
                    + MENSAJE + se);
            throw new SIA3Exception(MessagesConstants.APP100041);
        } catch (Exception e) {
            logger.error(
                    "Error inesperado al tratar de obtener operaciones hijas con id:" + operacionId + MENSAJE + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que elimina las operaciones relacionadas con la aplicacion dada
     * por parametro
     *
     * @param aplicacionId
     * @throws SIA3Exception
     */
    public void eliminarOperacionesXAplicacion(BigDecimal aplicacionId) throws SIA3Exception {
        try {
            logger.info("Inicio metodo eliminarOperacionesXAplicacion con aplicacionId => " + aplicacionId);
            //buscar las operaciones que coincidan con la aplicacion dada
            List<Operaciones> listaOperaciones = manejadorOperaciones.buscarOperacionesPorAplicacion(aplicacionId);
            logger.info("Recorrer listaOperaciones ----" + listaOperaciones);
            for (Operaciones operacion : listaOperaciones) {
                logger.info("Dentro de la lista, eliminar operacion--->" + operacion);
                //eliminar la operacion
                eliminarOperacion(operacion.getOpcionId(), operacion.getUsuarioModificacion().toString());
            }
            logger.info("Fin metdo eliminarOperacionesXAplicacion");
        } catch (SIA3Exception se) {
            logger.error("Error en metodo eliminarOperacionesXAplicacion:" + se);
            throw new SIA3Exception(MessagesConstants.APP100041);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de eliminar Operaciones relacionadas con la aplicacion id:"
                    + aplicacionId + MENSAJE + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que valida las operaciones relacionadas con la aplicacion dada
     * por parametro
     *
     * @param aplicacionId
     * @throws SIA3Exception
     */
    public void validarOperacionesXAplicacion(BigDecimal aplicacionId) throws SIA3Exception {
        try {
            logger.info("Inicio metodo validarOperacionesXAplicacion con aplicacionId => " + aplicacionId);
            //buscar las operaciones que coincidan con la aplicacion dada
            List<Operaciones> listaOperaciones = manejadorOperaciones.buscarOperacionesPorAplicacion(aplicacionId);
            logger.info("Recorrer listaOperaciones ----" + listaOperaciones);
            if (!listaOperaciones.isEmpty()) {
                throw new SIA3Exception(MessagesConstants.APP100119);
            }
            logger.info("Fin metdo validarOperacionesXAplicacion");
        } catch (SIA3Exception se) {
            logger.error("Error en metodo validarOperacionesXAplicacion:" + se);
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de validar Operaciones relacionadas con la aplicacion id:"
                    + aplicacionId + MENSAJE + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    // Fin metodos HBT


    /**
     * Actualiza en la base de datos el operacionesRol que se pasa como parámetro.
     *
     * @param operacionesRolDTO El DAO de la entidad OperacionesRol a actualizar. Este se envía en el cuerpo de la
     *                          solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad OperacionesRol que ha sido actualizado
     */
    public MapasDTO buscarUsuarioRolPorUsuario(List<BigDecimal> rolID, String clientID) {
        // protected region Modifique el metodo actualizar on begin

        TreeMap<BigDecimal, BigDecimal> mapaPadreHijo = new TreeMap<>();
        TreeMap<String, OperacionesDTO> mapaCompleto = new TreeMap<>();

        logService(this.getClass().getName(), "actualizar", rolID);

        List<Operaciones> operacionesRol = manejadorOperaciones
                .buscarOperacionesRolXUsuario(rolID, new BigDecimal(clientID));

        List<OperacionesRol> rolOperaciones = manejadorOperacionesRol
                .buscarOperacionesRolXRol(rolID, new BigDecimal(clientID));

        for (Operaciones optionRole : operacionesRol) {
            OperacionesDTO operacionesDTO = instanciarDAO();
            copiarPropiedades(operacionesDTO, optionRole);


            for (OperacionesRol optionRoles : rolOperaciones) {
                if (optionRoles.getOperacionesRolPK().getOpcionId().equals(optionRole.getOpcionId() + "")) {
                    operacionesDTO.setNombreRolAsociado(optionRoles.getRoles().getNombre());

                    BigDecimal opcionPadre = (optionRole.getOpcionPadre() != null
                            ? (new BigDecimal(optionRole.getOpcionPadre() + "" + optionRoles.getRoles().getRolId()))
                            : optionRole.getOpcionPadre());

                    mapaPadreHijo.put(new BigDecimal(optionRole.getOpcionId() + "" + optionRoles.getRoles().getRolId()),
                            opcionPadre);

                    mapaCompleto.put(optionRole.getOpcionId() + "" + optionRoles.getRoles().getRolId(), operacionesDTO);
                    rolOperaciones.remove(optionRoles);
                    break;
                }
            }

        }

        return new MapasDTO(mapaPadreHijo, mapaCompleto);
        // protected region Modifique el metodo actualizar end
    }

    public RespuestaImportarOperacionesDTO importar(List<OperacionesDTO> operacionesList, Long aplicacionId) throws SIA3Exception {
        logger.info("Inicia comando para importar operaciones: " + new Gson().toJson(operacionesList) + " aplicacionId: " + aplicacionId);
        try {
            List<OperacionesDTO> resultadoOperacionesCreadas = new ArrayList<>();
            List<OperacionesDTO> resultadoOperacionesActualizadas = new ArrayList<>();
            Aplicaciones aplicacion = BuilderDTO.toAplicacion(negocioAplicacion.buscarAplicacion(aplicacionId.toString()));
            logger.info("****** obtieneAplicacion=>" + aplicacion.getNombre());
            addAplicacionAOperacionYValidar(operacionesList, aplicacion);

            operacionesList.stream().distinct().forEach(operacionesDTO -> {
                List<Operaciones> operacionesExistentes = manejadorOperaciones.buscarOperacionesPorNombre(operacionesDTO.getNombreObjeto(), operacionesDTO.getAplicacionId().toString());
                logger.info("Resultado operaciones contador: " + (long) operacionesExistentes.size());
                if (!operacionesExistentes.isEmpty()) {
                    logger.info("Operacion ya existe se procede a actualizar");
                    for (OperacionesDTO operacionesActualizar : convertirListaEntidadesADao(operacionesExistentes)) {
                        try {
                            logger.info("Inicia proceso para construir operacion para actualizar: " + new Gson().toJson(operacionesActualizar));
                            OperacionesDTO dto = new OperacionesDTO(operacionesActualizar, operacionesDTO);
                            logger.info("Construcion realizada: " + new Gson().toJson(dto));
                            actualizar(dto);
                            resultadoOperacionesActualizadas.add(dto);
                            List<OperacionesDTO> listaOP = operacionesDTO.getListadoOperaciones();
                            if(listaOP != null && listaOP.get(0) != null){
                                for(OperacionesDTO operDTO : listaOP){
                                    Operaciones oper = manejadorOperaciones.buscarOperacionesPorNombreYAplicacion(operDTO.getNombreObjeto(), operDTO.getAplicacionId().toString());
                                    if(oper == null){
                                        logger.info("Se procede a crear la operacion hija");
                                        Operaciones operacionesAGuardar = new Operaciones();
                                        copiarPropiedades(operacionesAGuardar, operDTO);
                                        operacionesAGuardar.setOpcionId(null);
                                        operacionesAGuardar.setOpcionPadre(operacionesActualizar.getOpcionId());
                                        Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
                                        operacionesAGuardar.setFechaCreacion(timestampActual);
                                        operacionesAGuardar.setUltimaModificacion(timestampActual);
                                        operacionesAGuardar.setAplicaciones(operacionesActualizar.getAplicaciones());
                                        operacionesAGuardar.setAplicacionId(operacionesActualizar.getAplicacionId());
                                        manejadorOperaciones.crear(operacionesAGuardar);
                                        logger.info("Se crea operacion hija ==> " + new Gson().toJson(operacionesAGuardar));
                                    }
                                }
                                activeTempo();
                            }
                        } catch (SIA3Exception e) {
                            logger.error("Error en proceso de actualizacion: " + new Gson().toJson(e));
                        }
                    }
                    activeTempo();
                } else {
                    logService(this.getClass().getName(), "crear", operacionesDTO);
                    Operaciones operaciones = new Operaciones();
                    copiarPropiedades(operaciones, operacionesDTO);
                    logger.info("*****Las propiedades de operaciones:" + operaciones);
                    Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
                    operaciones.setFechaCreacion(timestampActual);
                    operaciones.setUltimaModificacion(timestampActual);
                    if (operacionesDTO.getOpcionPadre() == null || operacionesDTO.getOpcionPadre().compareTo(BigDecimal.ZERO) == 0) {
                        logger.warn(OPERACION_RAIZ);
                        operaciones.setOpcionPadre(null);
                    }
                    logger.info("Operacion antes de crear operacion: " + new Gson().toJson(operaciones));
                    manejadorOperaciones.crear(operaciones);
                    resultadoOperacionesCreadas.add(operacionesDTO);
                    Operaciones operacionGuardada = buscarOperacionesPorNombreYAplicacion(operaciones.getNombreObjeto(), operaciones.getAplicacionId().toString());
                    logger.info("Operacion guardada --> : " + new Gson().toJson(operacionGuardada));
                    List<OperacionesDTO> listadoOperaciones = operacionesDTO.getListadoOperaciones();
                    if (listadoOperaciones == null || listadoOperaciones.isEmpty() || listadoOperaciones.get(0) == null) {
                        logger.info("No hay nada: " + (listadoOperaciones != null ? listadoOperaciones.get(0) : ""));
                    } else {
                        logger.info("Crear Operaciones Hijas: " + new Gson().toJson(listadoOperaciones));
                        crearOperacionesHijas(operacionGuardada, listadoOperaciones);
                        logger.info("Finaliza creación de operaciones hijas");
                    }
                    activeTempo();
                }
            });
            logger.info("Inicio registro auditoria evento OPERATION_IMPORTED");
            auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_IMPORT_OPERATION), operacionesList.get(0).getUsuarioModificacion().toString(), Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Importa operaciones para aplicacion: " + aplicacion.getNombre());
            logger.info("Fin registro auditoria evento OPERATION_IMPORTED");
            return new RespuestaImportarOperacionesDTO(resultadoOperacionesCreadas, resultadoOperacionesActualizadas);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de importar Operaciones: " + new Gson().toJson(e));
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    private void activeTempo(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void addAplicacionAOperacionYValidar(List<OperacionesDTO> operacionesList, Aplicaciones aplicacion) {
        operacionesList.forEach(operacion -> {
            operacion.setOpcionId(null);
            operacion.setAplicacionId(aplicacion.getAplicacionId());
            operacion.setAplicaciones(aplicacion);
            operacion.setDescripcion(operacion.getDescripcion().trim());
            operacion.setNombreObjeto(operacion.getNombreObjeto().trim());
            if (operacion.getUrlImgGif() != null) {
                operacion.setUrlImgGif((operacion.getUrlImgGif().trim()).replace(" ", ""));
            }
            try {
                logger.info("Inicia proceso para validar operacion con importacion: " + new Gson().toJson(operacion));
                validarOperaciones(operacion, true);
            } catch (SIA3Exception e) {
                logger.error("Error al validar operacionDTO al importar Operaciones");
                try {
                    throw new SIA3Exception(MessagesConstants.APP100054);
                } catch (SIA3Exception ex) {
                    throw new RuntimeException(ex); // NOSONAR
                }
            }
        });
    }

    private void crearOperacionesHijas(Operaciones operaciones, List<OperacionesDTO> operacionesDTOList) {
        if (!operacionesDTOList.isEmpty()) {
            operacionesDTOList.forEach(opD -> {
                Operaciones operacionesAGuardar = new Operaciones();
                copiarPropiedades(operacionesAGuardar, opD);
                operacionesAGuardar.setOpcionId(null);
                operacionesAGuardar.setOpcionPadre(operaciones.getOpcionId());
                Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
                operacionesAGuardar.setFechaCreacion(timestampActual);
                operacionesAGuardar.setUltimaModificacion(timestampActual);
                operacionesAGuardar.setAplicaciones(operaciones.getAplicaciones());
                operacionesAGuardar.setAplicacionId(operaciones.getAplicacionId());
                manejadorOperaciones.crear(operacionesAGuardar);
                logger.info("Crear operacion hija " + new Gson().toJson(operacionesAGuardar));
            });
        }
    }

    public List<OperacionesDTO> getOperacionesPorAplicacion(BigDecimal idAplicacion) throws SIA3Exception {
        try {
            logger.info("Inicio metodo getOperacionesPorAplicacion con idAplicacion:" + idAplicacion);
            List<OperacionesDTO> rolDTOList = BuilderDTO
                    .toOperacionDTOList(manejadorOperaciones.buscarOperacionesPorAplicacion(idAplicacion));
            if (rolDTOList.isEmpty()) {
                logger.error("Error en metodo consultar: No hay Operaciones por aplicacion. Detalle:");
                throw new SIA3Exception(MessagesConstants.APP100041);
            }
            logger.info("fin metodo getOperacionesPorAplicacion");
            return rolDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getOperacionesPorAplicacion:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    public Operaciones buscarOperacionesPorNombreYAplicacion(String nombre, String aplicacionId) {
        return manejadorOperaciones
                .buscarOperacionesPorNombreYAplicacion(nombre, aplicacionId);
    }

}
