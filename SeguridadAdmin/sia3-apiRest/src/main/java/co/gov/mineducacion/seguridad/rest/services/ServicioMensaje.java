package co.gov.mineducacion.seguridad.rest.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;

/**
 * Servicios REST para la entidad Aplicacion
 * 
 * @author gvalverde
 *
 */
@Stateless
@Path("/servicios/mensaje")
public class ServicioMensaje {
	/**
	 * Imprimir logs
	 */
	private static final Logger LOG = LogManager.getLogger(ServicioMensaje.class.getName());	
	
	@Inject
	private NegocioMensaje negocioMensaje;

	@GET
	@Path("{codigo}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response buscarMensajes(@PathParam("codigo") String codigo) {
		try {
			MensajeDTO buscarMensajes = negocioMensaje.mensajePorCodigo(codigo);
			return Response.status(Status.OK).entity(buscarMensajes).build();
		} catch (SIA3Exception e) {
			LOG.error(e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response buscarMensajesCod(@QueryParam("codigo") String codigo) {
		try {
			MensajeDTO paMensajeDTO = negocioMensaje.mensajePorCodigo(codigo);
			
			return Response.status(Status.OK).entity(paMensajeDTO).build();
		} catch (SIA3Exception e) {
			// se busca el mensaje para retornar
			MensajeDTO mensajeDTO;
			try {
				if (MessagesConstants.APP000001.equals(e.getMessage())) {
					throw new SIA3Exception("");
				}
				mensajeDTO = negocioMensaje.mensajePorCodigo(e.getMessage());
			} catch (SIA3Exception e1) {
				mensajeDTO = new MensajeDTO();
				mensajeDTO.setCodigo("APP000001");
				mensajeDTO.setTipoMensaje(MessagesConstants.TIPO_MENSAJE_ERROR);
				mensajeDTO.setDescripcion(MessagesConstants.APP000001);
			}
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}
}
