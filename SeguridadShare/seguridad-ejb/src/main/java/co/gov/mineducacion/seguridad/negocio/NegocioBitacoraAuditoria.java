package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.ModifyAuditoriaParameterDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.dtos.BitacoraAuditoriaDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import co.gov.mineducacion.seguridad.modelo.entidades.BitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorBitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.ReportesUtil;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;
import co.gov.mineducacion.seguridad.modelo.utils.UtilToken;
import co.gov.mineducacion.seguridad.reports.datasources.BitacoraAuditoriaDS;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

// protected region Incluya importaciones adicionales en esta seccion on begin

// protected region Incluya importaciones adicionales en esta seccion end

/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad
 * BitacoraAuditoria
 *
 * @author jsoto
 */
@Stateless
public class NegocioBitacoraAuditoria extends NegocioAbstracto<BitacoraAuditoria, BitacoraAuditoriaDTO> {

    @EJB
    private ManejadorBitacoraAuditoria manejadorBitacoraAuditoria;

    @EJB
    private NegocioOrquestadorNotificaciones negocioOrquestadorNotificaciones;
    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(NegocioBitacoraAuditoria.class.getName());

    // protected region Declare atributos adicionales en esta seccion on begin

    // protected region Declare atributos adicionales en esta seccion end

    /**
     * Realiza un consulta en la entidad BitacoraAuditoria aplicando los
     * filtros, el ordenamiento, y el rango (from y to) que se pasan como
     * parámetro. Los parámetros filterBy y orderBy pueden ser nulos. El
     * parámetro from y to están relacionados. Si from es diferente de nulo to
     * puedo ser nulo, pero no al revés. Ambos pueden ser nulos, en cuyo caso no
     * se aplica una restricción de rango a la consulta.
     *
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere filtrar, seguido por un operador de comparación que
     *                 puede tomar los valores
     *                 {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
     *                 por último el valor por el que se quiere filtrar. Los filtros
     *                 se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     *                 Ej. Una secuencia de parámetros de filtrado puede ser
     *                 {@literal bitacoraAuditoriaId>1&bitacoraAuditoriaName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser:
     *                 bitacoraAuditoriaId$ASC, bitacoraAuditoriaName$DESC
     * @param from     Número de registro inicial que se quiere retornar de la
     *                 consulta realizada. Entero mayor o igual a 0
     * @param to       Número de registro final que se quiere retornar de la consulta
     *                 realizada. Entero mayor o igual al parámetro from
     * @return Una lista de DAOs de los BitacoraAuditoria que se consultaron con
     * los parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url
     *                                   tenía un error de sintáxis por lo que no pudo ser procesado
     *                                   correctamente
     */
    public List<BitacoraAuditoriaDTO> consultar(String filterBy, String orderBy, Integer from, Integer to)
            throws InvalidParameterException {
        // protected region Modifique el metodo consultar on begin
        logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
        RangoConsulta rango = validarParametrosBloque(from, to);

        return convertirListaEntidadesADao(manejadorBitacoraAuditoria.consultar(filtros, ordenamiento, rango));
        // protected region Modifique el metodo consultar end
    }

    /**
     * Crea el bitacoraAuditoria que se pasa como parámetro en la base de datos.
     *
     * @param bitacoraAuditoriaDTO El DAO de la entidad BitacoraAuditoria a crear. Este se envía
     *                             en el cuerpo de la solicitud POST como un objeto JSON.
     * @return La insntancia de BitacoraAuditoria recién creado
     */
    public BitacoraAuditoriaDTO crear(BitacoraAuditoriaDTO bitacoraAuditoriaDTO) {
        // protected region Modifique el metodo crear on begin

        logService(this.getClass().getName(), "crear", bitacoraAuditoriaDTO);

        BitacoraAuditoria bitacoraAuditoria = new BitacoraAuditoria();
        copiarPropiedades(bitacoraAuditoria, bitacoraAuditoriaDTO);

        if (bitacoraAuditoria.getBitacoraId() != null
                && bitacoraAuditoria.getBitacoraId().intValue() == 0) {
            bitacoraAuditoria.setBitacoraId(null);
        }

        manejadorBitacoraAuditoria.crear(bitacoraAuditoria);

        return bitacoraAuditoriaDTO;
        // protected region Modifique el metodo crear end
    }

    /**
     * Actualiza en la base de datos el bitacoraAuditoria que se pasa como
     * parámetro.
     *
     * @param bitacoraAuditoriaDTO El DAO de la entidad BitacoraAuditoria a actualizar. Este se
     *                             envía en el cuerpo de la solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad BitacoraAuditoria que ha sido
     * actualizado
     */
    public BitacoraAuditoriaDTO actualizar(BitacoraAuditoriaDTO bitacoraAuditoriaDTO) {
        // protected region Modifique el metodo actualizar on begin

        logService(this.getClass().getName(), "actualizar", bitacoraAuditoriaDTO);

        BitacoraAuditoria bitacoraAuditoria = manejadorBitacoraAuditoria.buscar(bitacoraAuditoriaDTO.getBitacoraId());
        copiarPropiedades(bitacoraAuditoria, bitacoraAuditoriaDTO);

        manejadorBitacoraAuditoria.actualizar(bitacoraAuditoria);

        return bitacoraAuditoriaDTO;
        // protected region Modifique el metodo actualizar end
    }

    /**
     * Elimina el bitacoraAuditoria con el identificador que se pasa como
     * parámetro.
     *
     * @param bitacoraId Valor del atributo del identificador de la instancia de la
     *                   entidad bitacoraAuditoria a eliminar
     * @return El identificador del bitacoraAuditoria que ha sido eliminado
     */
    public String eliminar(@QueryParam("bitacoraId") BigDecimal bitacoraId) {
        // protected region Modifique el metodo eliminar on begin

        logService(this.getClass().getName(), "eliminar", bitacoraId);
        manejadorBitacoraAuditoria.eliminarPorId(bitacoraId);

        StringBuilder valores = new StringBuilder();
        valores.append(String.valueOf(bitacoraId));
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
     *                 {@literal bitacoraAuditoriaId>1&bitacoraAuditoriaName:LIKE:juan}
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

        return String.valueOf(manejadorBitacoraAuditoria.consultarTotalRegistros(filtros, rango));
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
     *                 {@literal bitacoraAuditoriaId>1&bitacoraAuditoriaName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser:
     *                 bitacoraAuditoriaId$ASC, bitacoraAuditoriaName$DESC
     * @param atributo Nombre del atributo de la entidad BitacoraAuditoria del cual
     *                 se quieren obtener los diferentes valores.
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
                manejadorBitacoraAuditoria.consultarLista(filtros, ordenamiento, infoAgrupamiento));
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
        return BitacoraAuditoria.contieneAtributo(nombreAtributo);
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
    protected BitacoraAuditoriaDTO instanciarDAO() {
        return new BitacoraAuditoriaDTO();
    }

    /**
     * Registra un evento de auditoria
     *
     * @param errorId
     * @param userId
     * @param clientId
     */
    public void registrarEventoAuditoria(Long errorId, String userId, String clientId) {
        BigDecimal errCorrespondiente = null;

        if (errorId == null) {
            return;
        }

        switch (errorId.intValue()) {

            case (int) SeguridadException.ID_MSG_USUARIO_YA_INACTIVO:
            case (int) SeguridadException.ID_MSG_ERROR_USU_INACTIVO:
            case (int) SeguridadException.ID_MSG_WEB_ERROR_USU_INACTIVO:
                errCorrespondiente = Constantes.EVT_USER_INACTIVE;
                break;

            case (int) SeguridadException.ID_MSG_USR_NO_ROLES_ACTIVOS:
            case (int) SeguridadException.USER_NOT_ANY_APP:
            case (int) SeguridadException.USER_NO_PERTENECE_APLICACION:
                errCorrespondiente = Constantes.EVT_USER_NOT_ANY_APP;
                break;

            case (int) SeguridadException.TOKEN_USU_NO_ASOCIADO:
                errCorrespondiente = Constantes.EVT_TOKEN_NOT_USER;
                break;

            case (int) SeguridadException.TOKEN_NO_ENCONTRADO:
            case (int) SeguridadException.ID_MSG_ERR_IMP_GENERAR_TOKEN:
                errCorrespondiente = Constantes.EVT_TOKEN_NOT_FOUND;
                break;

            case (int) SeguridadException.USUARIO_NO_ENCONTRADO_INACTIVAR:
            case (int) SeguridadException.USUARIO_NO_ENCONTRADO:
                errCorrespondiente = Constantes.EVT_USER_NOT_FOUND;
                break;

            case (int) SeguridadException.ID_MSG_USUARIO_NO_EXTERNO_SIA3:
            case (int) SeguridadException.ID_MSG_USUARIO_NO_EXTERNO:
                errCorrespondiente = Constantes.EVT_USER_NOT_EXTERNAL;
                break;

            case (int) SeguridadException.ID_MSG_ERROR_USU_NO_EXISTE:
            case (int) SeguridadException.USUARIO_NO_ENCONTRADO_LDAP:
            case (int) SeguridadException.ID_MSG_USUARIO_NO_EXTERNO_LDAP:
                errCorrespondiente = Constantes.EVT_USER_NOT_REGISTERED_LDAP;
                break;

            case (int) SeguridadException.ID_MSG_ERROR_USU_NOT_ROLE:
                errCorrespondiente = Constantes.EVT_USER_NOT_ROLE;
                break;

            case (int) SeguridadException.ID_MSG_CODIGO_NO_VIGENTE:
                errCorrespondiente = Constantes.EVT_CODIGO_NO_VIGENTE;
                break;

            case (int) SeguridadException.ID_MSG_ERROR_USU_EXISTE:
                errCorrespondiente = Constantes.EVT_USER_REGISTERED;
                break;

            case (int) SeguridadException.ROL_NO_EXISTE:
            case (int) SeguridadException.ROL_NO_PERTENECE_APLICACION:

                errCorrespondiente = Constantes.EVT_ROL_APP_NOT_FOUND;
                break;
            default:
                break;
        }

        if (errCorrespondiente != null) {
            this.gestionarAuditoria(errCorrespondiente, userId, clientId);
        }
    }

    /**
     * M&eacute;toddo que realiza las operaciones de auditor&iacute;a sobre el
     * inicio de sesi&oacute;n
     *
     * @param errorId
     * @param user
     * @param clientId
     * @return el objeto de auditor&iacute;a
     * @author hfabra
     * @since 13/02/2017
     */
    public void gestionarAuditoria(BigDecimal errorId, String userId, String clientId) {
        Timestamp fechaEvento = UtilToken.obtenerFechaActual();
        BigDecimal appID = new BigDecimal(clientId);
        BitacoraAuditoriaDTO audit = new BitacoraAuditoriaDTO(errorId, fechaEvento, userId, appID);
        crear(audit);
    }

    // protected region Use esta region para su implementacion de otros metodos
    // on begin

    // protected region Use esta region para su implementacion de otros metodos
    // end

    //Inicio metodos HBT

    /**
     * Metodo que registra en auditoria con el detalle
     *
     * @param errorId
     * @param userId
     * @param clientId
     * @param detalle
     */
    public void gestionarAuditoriaDetalle(BigDecimal errorId, String userId, String clientId, String detalle) {
        gestionarAuditoriaDetalle(errorId, userId,clientId, detalle, null);
    }

    /**
     * Metodo que registra en auditoria con el detalle
     *
     * @param errorId
     * @param userId
     * @param clientId
     * @param detalle
     */
    public void gestionarAuditoriaDetalle(BigDecimal errorId, String userId, String clientId, String detalle, String logonName) {
        Timestamp fechaEvento = UtilToken.obtenerFechaActual();
        BigDecimal appID = new BigDecimal(clientId);
        BitacoraAuditoriaDTO audit = new BitacoraAuditoriaDTO(errorId, fechaEvento, userId, appID, detalle);
        audit.setLogonName(logonName);
        crear(audit);
        negocioOrquestadorNotificaciones.orquestarNotificacionesAuditoriaPorAplicaciones(audit);
    }


    /**
     * Metodo que lista los registros de auditoria segun parametros dados
     *
     * @param aplicacionId BigDecimal
     * @param tipoEvento
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws SIA3Exception
     */
    public List<BitacoraAuditoriaDTO> filtrarAuditoria(BigDecimal aplicacionId, BigDecimal tipoEvento, Long fechaInicio, Long fechaFin, String usuarioId, Integer paginaInicio, Integer paginaFin, String campoActivo) throws SIA3Exception {
        try {
            logger.info("Inicio metodo negocio filtrarAuditoria con aplicacionId: " + aplicacionId + ", tipoEvento: " + tipoEvento + ", fechaInicio: " + fechaInicio + ", fechaFin: " + fechaFin);
            //Verificar que vengan los filtros obligatorios
            if (tipoEvento == null || fechaInicio == null || fechaFin == null) {
                logger.error("Error en metodo filtrarAuditoria. Debe indicar tipo de evento y rango de fechas a consultar.");
                throw new SIA3Exception(MessagesConstants.APP100114);
            }

            //convertir fechas al formato adecuado
            String formatFechaInicio = null;
            String formatFechaFin = null;

            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
            Date dateFechaIni = new Date(fechaInicio);
            Date dateFechaFin = new Date(fechaFin);
            formatFechaInicio = formateador.format(dateFechaIni);
            formatFechaFin = formateador.format(dateFechaFin);
            logger.info("Convierte fechas a formatFechaInicio:" + formatFechaInicio + " y formatFechaFin:" + formatFechaFin);

            if (dateFechaIni.after(dateFechaFin)) {
                logger.error("Fecha inicial no puede ser mayor que la fecha final");
                throw new SIA3Exception(MessagesConstants.APP100099);
            }


            //calcular rangos de paginacion
            RangoConsulta rango = null;
            if (paginaInicio != null && paginaFin != null) {
                rango = new RangoConsulta(paginaInicio, paginaFin);
            }

            List<BitacoraAuditoriaDTO> bitacoraAuditoriaDTOList = BuilderDTO.toBitacoraAuditoriaDTOList(manejadorBitacoraAuditoria.filtrarAuditoria(aplicacionId, tipoEvento, formatFechaInicio, formatFechaFin, usuarioId, rango, campoActivo));
            if (bitacoraAuditoriaDTOList.isEmpty()) {
                logger.error("Error en metodo filtrarAuditoria: No hay registros de auditoria que coincidan con aplicacionId: " + aplicacionId);
                throw new SIA3Exception(MessagesConstants.APP100093);
            }
            logger.info("fin metodo filtrarAuditoria");
            return bitacoraAuditoriaDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo filtrarAuditoria:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que genera reporte auditoria
     *
     * @param aplicacionId
     * @param tipoEvento
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws SIA3Exception
     */
    public byte[] generarReporteAuditoria(BigDecimal aplicacionId, BigDecimal tipoEvento, Long fechaInicio, Long fechaFin, String usuarioId, Integer paginaInicio, Integer paginaFin, String campoActivo) throws SIA3Exception {
        HashMap<String, Object> params = new HashMap<>();
        params.put("LOGO", getClass().getResource("/reportes/logos_min.png").toString());
        params.put("FECHA_GENERACION", ReportesUtil.obtenerFechaActual().getTime());
        byte[] bytes = null;
        try {
            logger.info("Inicio metodo negocio generarReporteAuditoria con aplicacionId:" + aplicacionId + ", tipoEvento:" + tipoEvento + ", fechaInicio:" + fechaInicio + ", fechaFin" + fechaFin + ", campoActivo" + campoActivo);
            //Verificar que vengan los filtros obligatorios
            if (tipoEvento == null || fechaInicio == null || fechaFin == null) {
                logger.error("Error en metodo generarReporteAuditoria. Debe indicar tipo de evento y rango de fechas a consultar.");
                throw new SIA3Exception(MessagesConstants.APP100114);
            }
            List<BitacoraAuditoriaDTO> bitacoraAuditoriaList = filtrarAuditoria(aplicacionId, tipoEvento, fechaInicio, fechaFin, usuarioId, paginaInicio, paginaFin, campoActivo);
            params.put("DATA_SOURCE", new BitacoraAuditoriaDS(bitacoraAuditoriaList));
            //genera reporte en formato excel
            bytes = ReportesUtil.generarExcel(getClass().getResource("/reportes/reporte_auditoria.jasper"), params, null);
            return bytes;
        } catch (SIA3Exception se) {
            logger.error("Error en metodo generarReporteAuditoria: No hay registros de auditoria que coincidan con los filtros aplicacionId:" + aplicacionId + ", tipoEvento:" + tipoEvento + ", fechaInicio:" + fechaInicio + ", fechaFin:" + fechaFin);
            if (se.getMessage().equals(MessagesConstants.APP100093)) {
                logger.info("No hay registros. Genera excel en Blanco");
                List<BitacoraAuditoriaDTO> bitacoraAuditoriaList = new ArrayList<>();
                params.put("DATA_SOURCE", bitacoraAuditoriaList);
                return bytes;
            } else {
                logger.error("Error al generar reporte de auditoria ->" + se);
                throw new SIA3Exception(se.getMessage());
            }

        } catch (Exception e) {
            logger.error("Fallo generando el reporte de auditoria:", e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que cuenta los registros de auditoria
     *
     * @param aplicacionId
     * @param tipoEvento
     * @param fechaInicio
     * @param fechaFin
     * @param usuarioId
     * @return
     * @throws SIA3Exception
     */
    public Long contarRegistrosAuditoria(BigDecimal aplicacionId, BigDecimal tipoEvento, Long fechaInicio, Long fechaFin, String usuarioId, String campoActivo) throws SIA3Exception {
        try {

            logger.info("INICIO Negocio: contarRegistrosAuditoria");
            //Verificar que vengan los filtros obligatorios
            if (tipoEvento == null || fechaInicio == null || fechaFin == null) {
                logger.error("Error en metodo contarRegistrosAuditoria. Debe indicar tipo de evento y rango de fechas a consultar.");
                throw new SIA3Exception(MessagesConstants.APP100114);
            }
            //convertir fechas al formato adecuado
            String formatFechaInicio = null;
            String formatFechaFin = null;

            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
            Date dateFechaIni = new Date(fechaInicio);
            Date dateFechaFin = new Date(fechaFin);
            formatFechaInicio = formateador.format(dateFechaIni);
            formatFechaFin = formateador.format(dateFechaFin);
            logger.info("Convierte fechas a formatFechaInicio:" + formatFechaInicio + " y formatFechaFin:" + formatFechaFin);

            if (dateFechaIni.after(dateFechaFin)) {
                logger.error("Fecha inicial no puede ser mayor que la fecha final");
                throw new SIA3Exception(MessagesConstants.APP100099);
            }



            Long cantidadRegistros = manejadorBitacoraAuditoria.contarRegistrosAuditoria(aplicacionId, tipoEvento, formatFechaInicio, formatFechaFin, usuarioId, campoActivo);

            logger.info("FIN Negocio: contarRegistrosAuditoria");
            return cantidadRegistros;
        } catch (SIA3Exception e) {
            logger.error("Captura la execpción de negocio lanzada en el try", e);
            throw new SIA3Exception(MessagesConstants.APP100093);
        } catch (Exception e) {
            logger.error("Error inesperado en metodo filtrarAuditoria:" + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    public void gestionarAuditoriaConDetalleYCampoActivo(BigDecimal errorId, String userId, String clientId, String detalle, String campoActivo) {
        Timestamp fechaEvento = UtilToken.obtenerFechaActual();
        BigDecimal appID = new BigDecimal(clientId);
        BitacoraAuditoriaDTO audit = new BitacoraAuditoriaDTO(errorId, fechaEvento, userId, appID, detalle, campoActivo);
        crear(audit);
        negocioOrquestadorNotificaciones.orquestarNotificacionesAuditoriaPorAplicaciones(audit);
    }
    //Fin metodos HBT

    public void construirAuditoriaDatosBasicosConColas(UsuariosDTO datosActualizar, UsuarioLdap datosActualizados, String usuarioMod, String aplicacionId){
        logger.info("Inicia proceso para gestionar auditoria datos basicos");
        int actualizar = 0;
        if(datosActualizar.getApellidosUsuario() != null && !datosActualizar.getApellidosUsuario().equals(datosActualizados.getSn())){
                emitirAuditoriaActualizacionDatosAplicacion(new ModifyAuditoriaParameterDTO( datosActualizados.getSn(), datosActualizar.getApellidosUsuario(), usuarioMod, "apellidosUsuario", aplicacionId).logonName(datosActualizar.getLogonName()));
                actualizar++;
        }

        if(datosActualizar.getNombres() != null && !datosActualizar.getNombres().equals(datosActualizados.getGivenName())){
                emitirAuditoriaActualizacionDatosAplicacion(new ModifyAuditoriaParameterDTO(datosActualizados.getGivenName(), datosActualizar.getNombres(), usuarioMod, "nombreUsuario", aplicacionId).logonName(datosActualizar.getLogonName()));
                actualizar++;
        }

        if(datosActualizar.getNumeroDocumento() != null && !datosActualizar.getNumeroDocumento().equals(datosActualizados.getDescription())){
                emitirAuditoriaActualizacionDatosAplicacion(new ModifyAuditoriaParameterDTO(datosActualizados.getDescription(), datosActualizar.getNumeroDocumento(), usuarioMod, "numeroDocumento", aplicacionId).logonName(datosActualizar.getLogonName()));
                actualizar++;
        }

        //Se agrega para servicio  de actualizarEmail no se debe contabilizar actualizar++; porque no entra dentro del servicio de actualizar datos basicos
        if(datosActualizar.getEmailUsuario() != null && !datosActualizar.getEmailUsuario().equals(datosActualizados.getMail())){
                emitirAuditoriaActualizacionDatosAplicacion(new ModifyAuditoriaParameterDTO(datosActualizados.getMail(), datosActualizar.getEmailUsuario(), usuarioMod, "email", aplicacionId).logonName(datosActualizar.getLogonName()));
        }

        if (actualizar>0){
            gestionarAuditoriaDetalle(Constantes.EVT_USER_CHANGED_ATTRIBUTE, usuarioMod, aplicacionId, "Usuario modificado: " + datosActualizar.getUsuarioId(), datosActualizar.getLogonName());
        }
    }

    public void emitirAuditoriaActualizacionDatosAplicacion(ModifyAuditoriaParameterDTO parameter) {
        logger.info("CON ID_APLICACION ->Inicia proceso para emitir auditoria actualizacion de datos, actual: " + parameter.getAntes() + ", nuevo: " + parameter.getDespues() + ", usuarioId: " + parameter.getUsuario());
        gestionarAuditoriaConDetalleYCampoActivo(Constantes.EVT_USER_CHANGED_ATTRIBUTE, parameter.getUsuario(), parameter.getAplicacionId(), constructorDetalleAuditoriaParaUsuario(parameter.getAntes(), parameter.getDespues(), parameter.getLogonName()), parameter.getCampoActivo());
    }



    public void emitirAuditoriaActualizacionDatos(ModifyAuditoriaParameterDTO parameter) {
        logger.info("Sin ID_APLICACION -> Inicia proceso para emitir auditoria actualizacion de datos, actual: " + parameter.getAntes() + ", nuevo: " + parameter.getDespues() + ", usuarioId: " + parameter.getUsuario());
        gestionarAuditoriaConDetalleYCampoActivo(Constantes.EVT_USER_CHANGED_ATTRIBUTE, parameter.getUsuario(), Constantes.HBT_ID_APP_SEGURIDAD.toString(), constructorDetalleAuditoriaParaUsuario(parameter.getAntes(), parameter.getDespues(),parameter.getLogonName()), parameter.getCampoActivo());
    }

    private String constructorDetalleAuditoriaParaUsuario(String antes, String despues, String logonName) {
        JsonObject detalle = new JsonObject();
        detalle.addProperty("descripcion", "Se actualizo un valor del usuario");
        detalle.addProperty("actual", antes);
        detalle.addProperty("nuevo", despues);
        detalle.addProperty("logonName", logonName);
        return detalle.toString();
    }
}
