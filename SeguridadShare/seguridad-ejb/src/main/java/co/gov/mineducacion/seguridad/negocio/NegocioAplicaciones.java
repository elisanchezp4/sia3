package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorAplicaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

//Inicio import HBT
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;

//Fin import HBT

// protected region Incluya importaciones adicionales en esta seccion on begin

// protected region Incluya importaciones adicionales en esta seccion end

/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad Aplicaciones
 *
 * @author jsoto
 */
@Stateless
public class NegocioAplicaciones extends NegocioAbstracto<Aplicaciones, AplicacionesDTO> {

    public static final String ERROR_INESPERADO_AL_TRATAR_DE_CONSULTAR_APLICACIONES = "Error inesperado al tratar de consultar Aplicaciones";
    public static final String NO_HAY_APLICACIONES = "Error en metodo consultar: No hay aplicaciones.";
    @EJB
    private ManejadorAplicaciones manejadorAplicaciones;

    @EJB
    private ManejadorUsuarios manejadorUsuarios;

    @EJB
    private NegocioOperaciones negocioOperaciones;

    @EJB
    private NegocioUsuariosAplicacion negocioUsuariosAplicacion;

    @EJB
    private NegocioBitacoraAuditoria auditoria;

    @EJB
    private NegocioRoles negocioRoles;

    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(NegocioAplicaciones.class.getName());

    // protected region Declare atributos adicionales en esta seccion on begin

    // protected region Declare atributos adicionales en esta seccion end

    /**
     * Realiza un consulta en la entidad Aplicaciones aplicando los filtros, el
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
     *                 {@literal aplicacionesId>1&aplicacionesName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser: aplicacionesId$ASC,
     *                 aplicacionesName$DESC
     * @param from     Número de registro inicial que se quiere retornar de la
     *                 consulta realizada. Entero mayor o igual a 0
     * @param to       Número de registro final que se quiere retornar de la consulta
     *                 realizada. Entero mayor o igual al parámetro from
     * @return Una lista de DAOs de los Aplicaciones que se consultaron con los
     * parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url
     *                                   tenía un error de sintáxis por lo que no pudo ser procesado
     *                                   correctamente
     */
    public List<AplicacionesDTO> consultar(String filterBy, String orderBy, Integer from, Integer to)
            throws InvalidParameterException {
        // protected region Modifique el metodo consultar on begin
        logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
        RangoConsulta rango = validarParametrosBloque(from, to);

        return convertirListaEntidadesADao(manejadorAplicaciones.consultar(filtros, ordenamiento, rango));
        // protected region Modifique el metodo consultar end
    }

    /**
     * Crea el aplicaciones que se pasa como parámetro en la base de datos. HBT
     * cambia firma metodo a void
     *
     * @param aplicacionesDTO El DAO de la entidad Aplicaciones a crear. Este se envía en el
     *                        cuerpo de la solicitud POST como un objeto JSON.
     * @return La insntancia de Aplicaciones recién creado
     * @throws SIA3Exception
     */
    public void crear(AplicacionesDTO aplicacionesDTO) throws SIA3Exception {
        // HBT agrega metodo para validacion antes de la creacion
        try {
            validarAplicaciones(aplicacionesDTO);
            //verificar que nombres y url's vengan sin espacios
            aplicacionesDTO.setDescripcion(aplicacionesDTO.getDescripcion());
            aplicacionesDTO.setNombre(aplicacionesDTO.getNombre().trim());
            aplicacionesDTO.setUrlInicioExitoso(aplicacionesDTO.getUrlInicioExitoso().trim());
            aplicacionesDTO.setMinVigTokenActConstrasenia(aplicacionesDTO.getMinVigTokenActConstrasenia());

            logService(this.getClass().getName(), "crear", aplicacionesDTO);

            Aplicaciones aplicaciones = new Aplicaciones();
            copiarPropiedades(aplicaciones, aplicacionesDTO);

            // Inicio sentencias HBT
            // Setear fecha de creacion y modificacion
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            aplicaciones.setFechaCreacion(timestampActual);
            aplicaciones.setUltimaModificacion(timestampActual);
            // Fin sentencias HBT
            manejadorAplicaciones.crear(aplicaciones);
            //Registrar en auditoria el evento
            logger.info("Inicio registro auditoria evento APP_CREATED con usuario modificacion:" + aplicacionesDTO.getUsuarioModificacion());
            //Validar que venga el usuario que realiza la operacion
            if (aplicacionesDTO.getUsuarioModificacion() == null) {
                logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
                throw new SIA3Exception(MessagesConstants.APP100120);
            }
            auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.APP_CREATED), aplicacionesDTO.getUsuarioModificacion().toString(),
                    Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Crea aplicacion con nombre: " + aplicacionesDTO.getNombre().trim());
            logger.info("Fin registro auditoria evento APP_CREATED");
        } catch (SIA3Exception se) {
            logger.error("Error al validar aplicacionDTO al crear Aplicacion->" + se.getMessage());
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de crear Aplicacion");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
        // Fin HBT
        // protected region Modifique el metodo crear on begin

        // protected region Modifique el metodo crear end
    }

    /**
     * Actualiza en la base de datos el aplicaciones que se pasa como parámetro.
     *
     * @param aplicacionesDTO El DAO de la entidad Aplicaciones a actualizar. Este se envía
     *                        en el cuerpo de la solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad Aplicaciones que ha sido actualizado
     */
    public void actualizar(AplicacionesDTO aplicacionesDTO) throws SIA3Exception {
        // protected region Modifique el metodo actualizar on begin

        logService(this.getClass().getName(), "actualizar", aplicacionesDTO);

        // HBT agrega metodo para validacion antes de la creacion
        try {
            validarAplicaciones(aplicacionesDTO);
            //verificar que nombres y url's vengan sin espacios
            aplicacionesDTO.setDescripcion(aplicacionesDTO.getDescripcion());
            aplicacionesDTO.setNombre(aplicacionesDTO.getNombre().trim());
            aplicacionesDTO.setUrlInicioExitoso(aplicacionesDTO.getUrlInicioExitoso().trim());
            aplicacionesDTO.setMinVigTokenActConstrasenia(aplicacionesDTO.getMinVigTokenActConstrasenia());
            logger.info("La fecha de creacion es" + aplicacionesDTO.getFechaCreacion());

            Aplicaciones aplicaciones = manejadorAplicaciones.buscar(aplicacionesDTO.getAplicacionId());
            copiarPropiedades(aplicaciones, aplicacionesDTO);

            // Inicio sentencias HBT
            // Setear fecha de creacion y modificacion
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            aplicaciones.setFechaCreacion(aplicacionesDTO.getFechaCreacion());
            aplicaciones.setUltimaModificacion(timestampActual);
            logger.info("Aplicaciones" + aplicaciones);
            // Fin sentencias HBT
            manejadorAplicaciones.actualizar(aplicaciones);
            //Registrar en auditoria el evento
            logger.info("Inicio registro auditoria evento APP_MODIFIED con usuario modificacion:" + aplicacionesDTO.getUsuarioModificacion());
            //Validar que venga el usuario que realiza la operacion
            if (aplicacionesDTO.getUsuarioModificacion() == null) {
                logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
                throw new SIA3Exception(MessagesConstants.APP100120);
            }
            auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.APP_MODIFIED), aplicacionesDTO.getUsuarioModificacion().toString(),
                    Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Actualiza aplicacion con nombre:" + aplicacionesDTO.getNombre() + ", urlInicioExitoso: " + aplicacionesDTO.getUrlInicioExitoso());
            logger.info("Fin registro auditoria evento APP_MODIFIED");
        } catch (SIA3Exception se) {
            logger.error("Error al validar aplicacionDTO al editar Aplicacion->" + se.getMessage());
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de editar Aplicacion");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
        // Fin HBT

        // protected region Modifique el metodo actualizar end
    }

    /**
     * Elimina el aplicaciones con el identificador que se pasa como parámetro.
     *
     * @param aplicacionId Valor del atributo del identificador de la instancia de la
     *                     entidad aplicaciones a eliminar
     * @return El identificador del aplicaciones que ha sido eliminado
     */
    public String eliminar(@QueryParam("aplicacionId") BigDecimal aplicacionId) {
        // protected region Modifique el metodo eliminar on begin

        logService(this.getClass().getName(), "eliminar", aplicacionId);
        manejadorAplicaciones.eliminarPorId(aplicacionId);

        StringBuilder valores = new StringBuilder();
        valores.append(String.valueOf(aplicacionId));
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
     *                 {@literal aplicacionesId>1&aplicacionesName:LIKE:juan}
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

        return String.valueOf(manejadorAplicaciones.consultarTotalRegistros(filtros, rango));
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
     *                 {@literal aplicacionesId>1&aplicacionesName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser: aplicacionesId$ASC,
     *                 aplicacionesName$DESC
     * @param atributo Nombre del atributo de la entidad Aplicaciones del cual se
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
                manejadorAplicaciones.consultarLista(filtros, ordenamiento, infoAgrupamiento));
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
        return Aplicaciones.contieneAtributo(nombreAtributo);
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
    protected AplicacionesDTO instanciarDAO() {
        return new AplicacionesDTO();
    }

    public AplicacionesDTO buscarAplicacion(String appId) {
        BigDecimal big = new BigDecimal(appId);
        AplicacionesDTO app = instanciarDAO();
        Aplicaciones aplicacion = manejadorAplicaciones.buscar(big);
        if (aplicacion == null) {
            return null;
        }
        copiarPropiedades(app, aplicacion);
        return app;

    }

    // protected region Use esta region para su implementacion de otros metodos
    // on begin

    // protected region Use esta region para su implementacion de otros metodos
    // end

    // Inicio metodos HBT

    /**
     * Metodo que busca todas la aplicaciones en el estado que se recibe por parametro
     *
     * @param estado estado de las aplicaciones a retornar
     * @return
     * @throws SIA3Exception
     */
    public List<AplicacionesDTO> getAllAplicaciones(Long estado) throws SIA3Exception {
        try {
            logger.info("Inicio metodo getAllAplicaciones. Filtro estado:" + estado);
            List<AplicacionesDTO> aplicacionDTOList = BuilderDTO
                    .AplicacionDTOList(manejadorAplicaciones.getAllAplicaciones(estado));
            if (aplicacionDTOList == null || aplicacionDTOList.isEmpty()) {
                logger.error(NO_HAY_APLICACIONES);
                throw new SIA3Exception(MessagesConstants.APP100011);
            }
            logger.info("fin metodo getAllAplicaciones");
            return aplicacionDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getAllAplicaciones:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP100002);
        } catch (Exception e) {
            logger.error(ERROR_INESPERADO_AL_TRATAR_DE_CONSULTAR_APLICACIONES);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

    }

    public List<AplicacionesDTO> getAllAplicacionesNotificaciones() throws SIA3Exception {
        try {
            logger.info("Inicio metodo getAllAplicacionesNotificaciones. ");
            List<AplicacionesDTO> aplicacionDTOList = BuilderDTO
                    .AplicacionDTOList(manejadorAplicaciones.getAllAplicacionesNotificaciones());
            if (aplicacionDTOList.isEmpty()) {
                logger.error(NO_HAY_APLICACIONES);
                return Collections.emptyList();
            }
            logger.info("fin metodo getAllAplicacionesNotificaciones");
            return aplicacionDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getAllAplicacionesNotificaciones:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP100002);
        } catch (Exception e) {
            logger.error(ERROR_INESPERADO_AL_TRATAR_DE_CONSULTAR_APLICACIONES);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

    }

    /**
     * Metodo para contar la aplicaciones que hay en base de datos
     *
     * @return
     * @throws SIA3Exception
     */
    public Long contarAplicaciones() throws SIA3Exception {
        try {
            logger.info("Inicio metodo contarAplicaciones");
            Long cantidadAplicaciones = manejadorAplicaciones.contarAplicaciones();
            if (cantidadAplicaciones == 0) {
                logger.error("Error en metodo contarAplicaciones: No hay aplicaciones.");
                throw new SIA3Exception(MessagesConstants.APP100010);
            }
            logger.info("fin metodo contarAplicaciones");
            return cantidadAplicaciones;
        } catch (PersistenceException e) {
            logger.error("Error en metodo contarAplicaciones:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP100011);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de contar Aplicaciones");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo para eliminar una aplicacion del sistema
     *
     * @param aplicacionId identificador de la aplicación a eliminar
     * @param usuarioId    identificador del usuario que realiza la operacion
     * @throws SIA3Exception
     */
    public void eliminarAplicacion(BigDecimal aplicacionId, String usuarioId) throws SIA3Exception {
        logger.info("Inicio metodo eliminarAplicacion con aplicacionId =>" + aplicacionId);
        try {
            String nombreAplicacion = "";
            // Verificar que la aplicacion exista
            List<Aplicaciones> aplicacionesExistentes = manejadorAplicaciones.buscarAplicacionPorId(aplicacionId);
            if (aplicacionesExistentes == null || aplicacionesExistentes.isEmpty()) {
                logger.error("Error en metodo eliminarAplicacion: NO EXISTE aplicacion con el id:" + aplicacionId);
                throw new SIA3Exception(MessagesConstants.APP100007);
            } else {
                for (Aplicaciones aplicacion : aplicacionesExistentes) {
                    nombreAplicacion = aplicacion.getNombre();
                }
            }
            //Eliminar las operaciones relacionadas a la aplicacion
            negocioOperaciones.validarOperacionesXAplicacion(aplicacionId);
            //Eliminar roles relacionados con la aplicacion
            negocioRoles.validarRolXAplicacion(aplicacionId.longValue());
            //Eliminar usuarios relacionados con la aplicacion
            negocioUsuariosAplicacion.validarUsuarioXAplicacion(aplicacionId);
            // Eliminar la operacion del sistema
            eliminar(aplicacionId);
            //Registrar en auditoria el evento
            logger.info("Inicio registro auditoria evento APP_DELETED con usuario modificacion:" + usuarioId);
            //Validar que venga el usuario que realiza la operacion
            if (usuarioId == null || usuarioId.equals("")) {
                logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
                throw new SIA3Exception(MessagesConstants.APP100120);
            }
            auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.APP_DELETED), usuarioId, Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Borra aplicacion con nombre:" + nombreAplicacion);
            logger.info("Fin registro auditoria evento APP_DELETED");
            logger.info("Finalizó el metodo eliminarAplicacion con aplicacionId => " + aplicacionId);

        } catch (SIA3Exception e) {
            logger.error("Error en metodo eliminarAplicacion:" + e.getCause());
            throw new SIA3Exception(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de eliminar Aplicacion");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo para validar los datos que vienen en el DTO de una aplicacion
     *
     * @param aplicacionDTO
     * @throws SIA3Exception
     */
    private void validarAplicaciones(AplicacionesDTO aplicacionDTO) throws SIA3Exception {
        // obligatoriedad
        if (aplicacionDTO.getNombre().equals("") || aplicacionDTO.getNombre() == null){
            logger.error("Error en metodo validarAplicaciones: Campo nombre no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100013);
        }
        if (aplicacionDTO.getEstado() == null) {
            logger.error("Error en metodo validarAplicaciones: Campo estado no puede ser  nulo");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }
        if (aplicacionDTO.getEstado() != null && aplicacionDTO.getEstado().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarAplicaciones: Campo estado no puede ser igual a cero(0)");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }

        if (aplicacionDTO.getUsuarioModificacion() == null) {
            logger.error("Error en metodo validarAplicaciones: Campo usuario modificacion no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100015);
        }
        if (aplicacionDTO.getUsuarioModificacion() != null && aplicacionDTO.getUsuarioModificacion().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarAplicaciones: Campo usuario modificacion no puede ser igual a cero(0)");
            throw new SIA3Exception(MessagesConstants.APP100015);
        }

        if (aplicacionDTO.getUrlInicioExitoso().equals("") || aplicacionDTO.getUrlInicioExitoso() == null) {
            logger.error("Error en metodo validarAplicaciones: Campo Url Inicio Exitoso no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100016);
        } else if (!validarURL(aplicacionDTO.getUrlInicioExitoso())) {
            logger.error("Error en metodo validarAplicaciones: Campo Url Inicio Exitoso no es correcto. Debe iniciar por http o https ");
            throw new SIA3Exception(MessagesConstants.APP100103);
        }

        if (aplicacionDTO.getMinVigTokenActConstrasenia() == null) {
            logger.error("Error en metodo validarAplicaciones: Campo Minutos Vigencia token cambio de constraseña no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100017);
        }

        if (aplicacionDTO.getMinVigTokenActConstrasenia() != null
                && (aplicacionDTO.getMinVigTokenActConstrasenia().compareTo(Constantes.HBT_MAXIMO_MINUTOS_PASSWORD) > 0
                || aplicacionDTO.getMinVigTokenActConstrasenia().compareTo(Constantes.HBT_MINIMO_MINUTOS_PASSWORD) < 0)
        ) {
            logger.error("Error en metodo validarAplicaciones: Campo Minutos Vigencia token cambio de constraseña debe estar entre:" + Constantes.HBT_MINIMO_MINUTOS + " y: " + Constantes.HBT_MAXIMO_MINUTOS);
            throw new SIA3Exception(MessagesConstants.APP100123);
        }
        if (aplicacionDTO.getMinutosVigenciaCodigo().equals(new BigDecimal(0)) || aplicacionDTO.getMinutosVigenciaCodigo() == null) {
            logger.error("Error en metodo validarAplicaciones: Campo Minutos Vigencia Codigo no puede ser igual a cero(0) o nulo");
            throw new SIA3Exception(MessagesConstants.APP100018);
        }
        if (aplicacionDTO.getMinutosVigenciaCodigo() != null && aplicacionDTO.getMinutosVigenciaCodigo().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarAplicaciones: Campo Minutos Vigencia Codigo no puede ser  nulo");
            throw new SIA3Exception(MessagesConstants.APP100018);
        }
        if (aplicacionDTO.getMinutosVigenciaCodigo() != null
                && (aplicacionDTO.getMinutosVigenciaCodigo().compareTo(Constantes.HBT_MAXIMO_MINUTOS) > 0
                || aplicacionDTO.getMinutosVigenciaCodigo().compareTo(Constantes.HBT_MINIMO_MINUTOS) < 0)
        ) {
            logger.error("Error en metodo validarAplicaciones: Campo Campo Minutos Vigencia Codigo debe estar entre:" + Constantes.HBT_MINIMO_MINUTOS + " y: " + Constantes.HBT_MAXIMO_MINUTOS);
            throw new SIA3Exception(MessagesConstants.APP100102);
        }

        if (aplicacionDTO.getMinutosVigenciaToken().equals(new BigDecimal(0)) || aplicacionDTO.getMinutosVigenciaToken() == null) {
            logger.error("Error en metodo validarAplicaciones: Campo Minutos Vigencia Token no puede ser igual a cero(0) o nulo");
            throw new SIA3Exception(MessagesConstants.APP100019);
        }
        if (aplicacionDTO.getMinutosVigenciaToken() != null && aplicacionDTO.getMinutosVigenciaToken().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarAplicaciones: Campo Campo Minutos Vigencia  no puede ser igual a cero(0) o nulo");
            throw new SIA3Exception(MessagesConstants.APP100019);
        }
        if (aplicacionDTO.getMinutosVigenciaToken() != null
                && (aplicacionDTO.getMinutosVigenciaToken().compareTo(Constantes.HBT_MAXIMO_MINUTOS) > 0
                || aplicacionDTO.getMinutosVigenciaToken().compareTo(Constantes.HBT_MINIMO_MINUTOS) < 0)
        ) {
            logger.error("Error en metodo validarAplicaciones: Campo Campo Minutos Vigencia Token debe estar entre:" + Constantes.HBT_MINIMO_MINUTOS + " y: " + Constantes.HBT_MAXIMO_MINUTOS);
            throw new SIA3Exception(MessagesConstants.APP100101);
        }

        // nombre unico
        List<Aplicaciones> aplicacionesExistentes = manejadorAplicaciones
                .buscarAplicacionPorNombre(aplicacionDTO.getNombre().trim());
        for (Aplicaciones aplicacionLista : aplicacionesExistentes) {
            if (!aplicacionLista.getAplicacionId().equals(aplicacionDTO.getAplicacionId())) {
                logger.error("Error en metodo validarAplicaciones: Ya existe aplicacion con el nombre:"
                        + aplicacionDTO.getNombre());
                throw new SIA3Exception(MessagesConstants.APP100020);
            }
        }

    }

    /**
     * Metodo que consulta una aplicacion por su nombre
     *
     * @param nombre
     * @return
     * @throws SIA3Exception
     */
    public Aplicaciones consultarAplicacionPorNombre(String nombre) throws SIA3Exception {
        try {
            logger.info("Inicio metodo consultarAplicacionPorNombre con nombre:" + nombre);
            List<Aplicaciones> aplicacionList = manejadorAplicaciones.buscarAplicacionPorNombre(nombre);
            if (aplicacionList.isEmpty()) {
                logger.error("Error en metodo consultarAplicacionPorNombre: No hay aplicación con nombre:" + nombre);
                throw new SIA3Exception(MessagesConstants.APP100010);
            } else {
                for (Aplicaciones aplicacion : aplicacionList) {
                    if (aplicacion.getAplicacionId() != null){
                        logger.info("fin metodo consultarAplicacionPorNombre");
                        return aplicacion;
                    }
                }
            }
            return null;
        } catch (SIA3Exception se) {
            logger.error("Error al consultar aplicación con el nombre especificado");
            throw new SIA3Exception(MessagesConstants.APP100010);
        } catch (Exception e) {
            logger.error("Error inesperado en metodo metodo consultarAplicacionPorNombre: " + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que valida si una URL esta bien formada
     *
     * @param url
     * @return
     */
    public boolean validarURL(String url) {
        try {
            logger.info("Inicio metodo validarURL con valor:" + url);
            new URL(url);
            logger.info("Fin validacion URL");
            return true;
        } catch (MalformedURLException malformedURLException) {
            logger.error("Error en metodo validarURL. La URL es invalida");
            return false;
        }
    }

    /**
     * Metodo que busca todas la aplicaciones
     *
     * @return
     * @throws SIA3Exception
     */
    public List<AplicacionesDTO> getAplicacionesPorUsuario(Long estado, BigDecimal usuarioId) throws SIA3Exception {
        try {
            logger.info("Inicio metodo getAplicacionesPorUsuario. Filtro estado:" + estado + "usuario" + usuarioId);

            List<AplicacionesDTO> aplicacionDTOList;

            if (manejadorUsuarios.userIsAdmin(usuarioId).equals(true)) {
                aplicacionDTOList = BuilderDTO
                        .AplicacionDTOList(manejadorAplicaciones.getAllAplicaciones(estado));
            } else {
                aplicacionDTOList = BuilderDTO
                        .AplicacionDTOList(manejadorAplicaciones.getAplicacionesPorUsuario(estado, usuarioId));
            }

            logger.info("fin metodo getAplicacionesPorUsuario");
            return aplicacionDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getAplicacionesPorUsuario:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP100002);
        } catch (Exception e) {
            logger.error(ERROR_INESPERADO_AL_TRATAR_DE_CONSULTAR_APLICACIONES);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

    }

    /**
     * Metodo que busca todas las aplicaciones por nombre
     *
     * @return
     * @throws SIA3Exception
     */
    public List<AplicacionesDTO> getAplicacionesNombre(Long estado, String nombre) throws SIA3Exception {
        try {
            logger.info("Inicio metodo getAplicacionesNombre. Filtro estado:" + estado + " " + "Filtro estado: " + nombre);
            List<AplicacionesDTO> aplicacionDTOList = BuilderDTO
                    .AplicacionDTOList(manejadorAplicaciones.getAplicacionesNombre(estado, nombre));
            if (aplicacionDTOList.isEmpty()) {
                logger.error(NO_HAY_APLICACIONES);
                throw new SIA3Exception(MessagesConstants.APP100122);
            }
            logger.info("fin metodo getAplicacionesNombre");
            return aplicacionDTOList;
        } catch (SIA3Exception e) {
            logger.error("Se capturó la exepción propia lanzada en el try", e.getCause());
            throw e;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getAplicacionesNombre:" + e.getCause());
            logger.error("Error en metodo getAplicacionesNombre:" + e.getMessage());
            throw new SIA3Exception(e.getMessage());
        } catch (Exception e) {
            logger.error(ERROR_INESPERADO_AL_TRATAR_DE_CONSULTAR_APLICACIONES);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

    }

    /**
     * Metodo para listar directorio activo
     * @return List<String>
     */
    public List<String> consultarListadoDirectorioActivo() {
        List<String> camposActivos = new ArrayList<>();
        camposActivos.add("usuarioId");
        camposActivos.add("nuevoPass");
        camposActivos.add("ruta");
        camposActivos.add("tipo");
        camposActivos.add("estado");
        camposActivos.add("fechaCreacion");
        camposActivos.add("ultimaModificacion");
        camposActivos.add("usuarioModificacion");
        camposActivos.add("loginUsuario");
        camposActivos.add("loginUsuario");
        camposActivos.add("apellidosUsuario");
        camposActivos.add("emailUsuario");
        camposActivos.add("nombres");
        camposActivos.add("password");
        camposActivos.add("logonName");
        camposActivos.add("nombreUsuario");
        camposActivos.add("numeroDocumento");
        camposActivos.add("nombreRol");
        camposActivos.add("roles");
        camposActivos.add("adressLocal");
        camposActivos.add("portLocal");
        camposActivos.add("fechaVinculacion");
        camposActivos.add("fechaDesvinculacion");
        camposActivos.add("estadoVinculacion");
        return camposActivos;
    }
    // Fin metodos HBT

}
