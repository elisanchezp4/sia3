package co.gov.mineducacion.seguridad.rest.services;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesPorRolDTO;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.negocio.NegocioOperacionesRol;

/**
 * Servicios REST para la entidad Aplicacion
 * 
 * @author gvalverde
 *
 */
@Stateless
@Path("/servicios/operacionRol")
public class ServicioOperacionRol {
	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ServicioOperacionRol.class.getName());

	@Inject
	private NegocioMensaje negocioMensaje;

	@Inject
	private NegocioOperacionesRol negocioOperacionRol;


	private MensajeDTO obtenerMensajeDTOFromException(Exception e) {
		return HandleMensajeDTO.obtenerMensajeDTOFromException(negocioMensaje, e);
	}


	/**
	 * Servicio que asocia operaciones a un rol de acuerdo a los datos que
	 * vienen de la lista que contiene el id de la operacion y el id del rol
	 * 
	 * @param operacionesPorRolDTOList
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response crearOperacionPorRol(List<OperacionesPorRolDTO> operacionesPorRolDTOList) {
		logger.info(
				"Inicio servicio crearOperacionPorRol con lista operacionesPorRolDTO =>" + operacionesPorRolDTOList);
		MensajeDTO mensajeDTO;
		try {
			negocioOperacionRol.crearOperacionPorRol(operacionesPorRolDTOList);
			logger.info("Finalizó el metodo crearOperacionPorRol con exito");
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100045);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (SIA3Exception e) {
			 mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Error inesperado en servicio crearOperacionPorRol");
			 mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}
	
	/**
	 * Servicio que elimina operaciones a un rol de acuerdo a los datos que
	 * el id del rol
	 * 
	 * @param operacionesPorRolDTOList
	 * @return
	 */
	@POST
	@Path("/eliminarOperacionPorRol")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response eliminarOperacionPorRol(@QueryParam("rolId") BigDecimal rolId) {
		logger.info(
				"Inicio servicio eliminarOperacionPorRol con el id =>" + rolId);
		MensajeDTO mensajeDTO;
		try {
			negocioOperacionRol.eliminarOperacionesRolXRol(rolId);
			logger.info("Finalizó el metodo eliminarOperacionPorRol con exito");
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100121);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		}  catch (SIA3Exception e) {
			mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Error inesperado en servicio eliminarOperacionPorRol");
			mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

}
