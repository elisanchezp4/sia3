package co.gov.mineducacion.seguridad.rest.services;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.rest.util.HandleMensajeDTO;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesPorRolDTO;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.negocio.NegocioOperaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioOperacionesPorRol;

/**
 * Servicios REST para la entidad Aplicacion
 * 
 * @author jfonseca
 *
 */
@Stateless
@Path("/servicios/operacion")
public class ServicioOperacion {
	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ServicioOperacion.class.getName());

	@Inject
	private NegocioOperaciones negocioOperacion;

	@Inject
	private NegocioMensaje negocioMensaje;

	@Inject
	private NegocioOperacionesPorRol negocioOperacionPorRol;


	private MensajeDTO obtenerMensajeDTOFromException(Exception e) {
		return HandleMensajeDTO.obtenerMensajeDTOFromException(negocioMensaje, e);
	}

	/**
	 * Metodo para consultar todas las entidades Operacion registradas en la
	 * base de datos
	 * 
	 * @return Response OK o Error(BAD_REQUEST)
	 * 
	 */
	@GET
	@Path("/getAllOperaciones")
	@Produces({ APPLICATION_JSON })
	public Response getAllOperaciones() {
		try {
			logger.info("Inicio metodo getAllOperaciones");
			List<OperacionesDTO> operaciones = negocioOperacion.getAllOperaciones();
			return Response.ok(operaciones).build();
		}  catch (Exception e) {
			return handleException(e, "getAllOperaciones");
		}
	}

	private Response handleException(Exception ex, String method){
		if(!(ex instanceof SIA3Exception)){
			logger.error("Error inesperado en servicio "+method);
		}
		MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(ex);
		return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
	}

	/**
	 * Servicio que obtiene todas las operaciones relacionadas a un rol
	 * especifico
	 * 
	 * @param rolId
	 * @param aplicacionId
	 * @return
	 */
	@GET
	@Path("/getOperacionesPorRol")
	@Produces({ APPLICATION_JSON })
	public Response getOperacionesPorRol(@QueryParam("rolId") BigDecimal rolId, @QueryParam("aplicacionId") BigDecimal aplicacionId) {
		try {
			logger.info("Inicio metodo getOperacionesPorRol");
			List<OperacionesPorRolDTO> operacionesPorRol = negocioOperacionPorRol.getOperacionesPorRol(rolId, aplicacionId);
			return Response.ok(operacionesPorRol).build();
		}  catch (Exception e) {
			return handleException(e, "getOperacionesPorRol");
		}
	}

	/**
	 * Servicio que obtiene el arbol de operaciones con su respectiva aplicacion
	 *
	 * @param aplicacionId Id de la aplicacion (obligatorio)
	 * @param nombreObjeto Nombre de la operación (opcional)
	 * @return
	 */
	@GET
	@Path("/getArbolOperaciones")
	@Produces({ APPLICATION_JSON })
	public Response getArbolOperaciones(@QueryParam("aplicacionId") BigDecimal aplicacionId,
			@QueryParam("nombreObjeto") String nombreObjeto) {
		try {
			logger.info("Inicio metodo getArbolOperaciones");
			List<OperacionesDTO> operaciones = negocioOperacion.armarArbolPermisosRol(aplicacionId, nombreObjeto);
			return Response.ok(operaciones).build();
		}  catch (Exception e) {
			return handleException(e, "getArbolOperaciones");
		}
	}

	/**
	 * Servicio REST que crea una nueva operacion
	 * 
	 * @param operacionesDTO
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response crearOperacion(OperacionesDTO operacionesDTO) {
		logger.info("Inicio servicio crearOperacion con operacionesDTO =>" + operacionesDTO);
		MensajeDTO mensajeDTO;
		try {
			negocioOperacion.crear(operacionesDTO);
			logger.info("Finalizó el metodo crearOperacion");
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100053);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		}
		catch (Exception e) {
			return handleException(e, "crearOperacion");
		}
	}

	/**
	 * Servicio REST para editar una operacion
	 * 
	 * @param operacionDTO
	 * @return
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response editarOperacion(OperacionesDTO operacionDTO) {
		logger.info("Inicio metodo editarOperacion con operacionDTO =>" + operacionDTO);
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100055);
			negocioOperacion.actualizar(operacionDTO);
			logger.info("Finalizó el metodo editarOperacion");
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		}
		catch (Exception e) {
			return handleException(e, "editarOperacion");
		}
	}

	/**
	 * Servicio REST que elimina una operación del sistema.
	 * 
	 * @param operacionId
	 * @return
	 */
	@DELETE
	@Path("/eliminarOperacion")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response eliminarOperacion(@QueryParam("operacionId") BigDecimal operacionId, @QueryParam("usuarioId") String usuarioId) {
		logger.info("Inicio metodo eliminarOperacion con operacionId =>" + operacionId);
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100057);
			negocioOperacion.eliminarOperacion(operacionId, usuarioId);
			logger.info("Finalizó el metodo eliminarOperacion");
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		}
		catch (Exception e) {
			return handleException(e, "eliminarOperacion");
		}
	}

	@GET
	@Path("/getOperacionesPorAplicacion")
	@Produces({ APPLICATION_JSON })
	public Response getOperacionesPorAplicacion(@QueryParam("aplicacionId") BigDecimal aplicacionId) {
		try {
			logger.info("Inicio metodo getOperacionesPorAplicacion");
			List<OperacionesDTO> operaciones = negocioOperacion.getOperacionesPorAplicacion(aplicacionId);
			return Response.ok(operaciones).build();
		}
		catch (Exception e) {
			return handleException(e, "getOperacionesPorAplicacion");
		}
	}
}
