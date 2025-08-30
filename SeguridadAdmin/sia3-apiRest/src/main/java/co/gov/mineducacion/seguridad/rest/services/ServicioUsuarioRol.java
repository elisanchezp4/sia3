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

import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;

/**
 * Servicios REST para la entidad Aplicacion
 * 
 * @author jfonseca
 *
 */
@Stateless
@Path("/servicios/usuarioRol")
public class ServicioUsuarioRol {
	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ServicioUsuarioRol.class.getName());

	@Inject
	private NegocioUsuariosRol negocioUsuarioRol;

	@Inject
	private NegocioMensaje negocioMensaje;

	/**
	 * Servicio REST que asigna roles a una lista de usuarios
	 * 
	 * @param usuariosDTOList
	 * @param rolId
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response agregarUsuariosARol(List<UsuariosDTO> usuariosDTOList, @QueryParam("rolId") BigDecimal rolId) {
		logger.info("Inicio servicio agregarUsuariosARol con usuariosDTOList =>" + usuariosDTOList + " y rolId => "
				+ rolId);
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			negocioUsuarioRol.agregarUsuariosARol(usuariosDTOList, rolId, usuariosDTOList.get(0).getUsuarioId());
			logger.info("Finalizó el servicio agregarUsuariosARol");
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100089);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (SIA3Exception e) {
			try {
				mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100088);
			} catch (SIA3Exception se1) {
				mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP000003_CODE, MessagesConstants.APP000003);
			}
			logger.error("Error en servicio agregarUsuariosARol");
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en servicio agregarUsuariosARol:" + e.getCause());
			return Response.status(Response.Status.BAD_REQUEST).entity(MessagesConstants.APP000003).build();
		}
	}

}
