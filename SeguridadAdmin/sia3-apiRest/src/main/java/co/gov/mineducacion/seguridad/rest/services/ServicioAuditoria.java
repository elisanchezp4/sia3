package co.gov.mineducacion.seguridad.rest.services;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioBitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.dtos.BitacoraAuditoriaDTO;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.rest.util.HandleMensajeDTO;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;

/**
 * Servicios REST para la entidad Aplicacion
 *
 * @author gvalverde
 *
 */
@Stateless
@Path("/servicios/auditoria")
public class ServicioAuditoria {
	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ServicioAuditoria.class.getName());

	@Inject
	private NegocioBitacoraAuditoria negocioBitacoraAuditoria;

	@Inject
	private NegocioMensaje negocioMensaje;

	@Inject
	private NegocioAplicaciones negocioAplicaciones;


	private MensajeDTO obtenerMensajeDTOFromException(Exception e) {
		return HandleMensajeDTO.obtenerMensajeDTOFromException(negocioMensaje, e);
	}

	/**
	 * Servicio REST para buscar registros de auditorio por filtros
	 *
	 * @param aplicacionId
	 * @param tipoEvento
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */


    @GET
    @Path("/filtrarAuditoria")
    @Produces({APPLICATION_JSON})
    public Response filtrarAuditoria(@QueryParam("aplicacionId") BigDecimal aplicacionId,
                                     @QueryParam("tipoEvento") BigDecimal tipoEvento, @QueryParam("fechaInicio") Long fechaInicio,
                                     @QueryParam("fechaFin") Long fechaFin, @QueryParam("usuarioId") String usuarioId,
									 @QueryParam("paginaInicio") Integer paginaInicio, @QueryParam("paginaFin") Integer paginaFin, @QueryParam("campoActivo") String campoActivo) {
		MensajeDTO mensajeDTO = null;
		try {
			logger.info("Inicio servicio filtrarAuditoria con aplicacionId:" + aplicacionId +
					", tipoEvento:" + tipoEvento + ", fechaInicio:" + fechaInicio + ", fechaFin" + fechaFin + "campoActivo: " + campoActivo + "usuarioId: " + usuarioId);
			List<BitacoraAuditoriaDTO> bitacoraAuditoria = negocioBitacoraAuditoria.filtrarAuditoria(aplicacionId,
					tipoEvento, fechaInicio, fechaFin, usuarioId, paginaInicio, paginaFin, campoActivo);
			return Response.ok(bitacoraAuditoria).build();
		} catch (SIA3Exception e) {
			try {
				mensajeDTO  = negocioMensaje.mensajePorCodigo(e.getMessage());
			} catch (Exception ex) {
				mensajeDTO = obtenerMensajeDTOFromException(e);
			}

			if (mensajeDTO == null) {
				mensajeDTO = new MensajeDTO();
				mensajeDTO.setCodigo("APP000003");
				mensajeDTO.setTipoMensaje(MessagesConstants.TIPO_MENSAJE_ERROR);
				mensajeDTO.setDescripcion(MessagesConstants.APP000003);
			}

			logger.error("Error al filtrar Auditoria");
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

	/**
	 * Servicio REST que genera reporte en excel de la consulta de auditoria
	 *
	 * @param aplicacionId
	 * @param tipoEvento
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@GET
	@Path("/reporteAuditoria")
	@Produces({ APPLICATION_JSON })
	public Response generarReporteAuditoria(@QueryParam("aplicacionId") BigDecimal aplicacionId,
											@QueryParam("tipoEvento") BigDecimal tipoEvento, @QueryParam("fechaInicio") Long fechaInicio,
											@QueryParam("fechaFin") Long fechaFin, @QueryParam("usuarioId") String usuarioId,
											@QueryParam("paginaInicio") Integer paginaInicio, @QueryParam("paginaFin") Integer paginaFin,@QueryParam("campoActivo")String campoActivo) {
		try {
			logger.info("Inicio servicio generarReporteAuditoria con aplicacionId:" + aplicacionId + ", tipoEvento:"
					+ tipoEvento + ", fechaInicio:" + fechaInicio + ", fechaFin" + fechaFin);
			byte[] reporte = negocioBitacoraAuditoria.generarReporteAuditoria(aplicacionId, tipoEvento, fechaInicio,
					fechaFin, usuarioId, paginaInicio, paginaFin,campoActivo);
			return Response.ok(reporte, MediaType.APPLICATION_OCTET_STREAM)
					.header("Content-Disposition", "attachment; filename=\"reporteAuditoria.xlsx\"").build();
		} catch (SIA3Exception e) {
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Error inesperado en servicio generarReporteAuditoria");
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

	/**
	 * Servicio REST que cuenta los registros de auditoria encontrados
	 *
	 * @param aplicacionId
	 * @param tipoEvento
	 * @param fechaInicio
	 * @param fechaFin
	 * @param usuarioId
	 * @return
	 */
	@GET
	@Produces({ APPLICATION_JSON + ";charset=utf-8" })
	@Path("/contarRegistrosAuditoria")
	public Response contarRegistrosAuditoria(@QueryParam("aplicacionId") BigDecimal aplicacionId,
											 @QueryParam("tipoEvento") BigDecimal tipoEvento, @QueryParam("fechaInicio") Long fechaInicio,
											 @QueryParam("fechaFin") Long fechaFin, @QueryParam("usuarioId") String usuarioId,@QueryParam("campoActivo")String campoActivo) {
		logger.info("Inicio Servicio: contarRegistrosAuditoria");
		try {
			Long cantidadRegistrosAuditoria = negocioBitacoraAuditoria.contarRegistrosAuditoria(aplicacionId,
					tipoEvento, fechaInicio, fechaFin, usuarioId,campoActivo);
			logger.info("FIN Servicio: consultarCantidadReferentes");
			return Response.ok(cantidadRegistrosAuditoria).build();
		} catch (SIA3Exception e) {
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Error inesperado en servicio contarRegistrosAuditoria");
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

	@POST
	@Path("/auditarExportar")
	public void adicionarDependiente(@QueryParam("auditarRolOperacion") @NotNull String auditarRolOperacion,
									 @QueryParam("aplicacionId") @NotNull BigDecimal aplicacionId,
									 @QueryParam("usuarioId") String usuarioId) {
		Aplicaciones aplicacion = BuilderDTO.toAplicacion(negocioAplicaciones.buscarAplicacion(aplicacionId.toString()));
		switch (auditarRolOperacion) {
			case Constantes.AUDITAR_ROL:
				negocioBitacoraAuditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_EXPORT_ROL), usuarioId, Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Exporta los roles de la aplicacion:" + aplicacion.getNombre());
				break;
			case Constantes.AUDITAR_OPERACION:
				negocioBitacoraAuditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_EXPORT_OPERATION), usuarioId, Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Exporta las operaciones de la aplicacion:" + aplicacion.getNombre());
				break;
			default:
				// Manejar caso inesperado o lanzar una excepción si es necesario
				break;
		}
	}
}
