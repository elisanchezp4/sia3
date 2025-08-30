package co.gov.mineducacion.seguridad.rest.services;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import co.gov.mineducacion.seguridad.negocio.NegocioCatalogos;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.modelo.dtos.CatalogosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

import co.gov.mineducacion.seguridad.rest.util.HandleMensajeDTO;
import org.apache.log4j.Logger;


/**
 * Servicios REST para la entidad Catalogo
 * 
 * @author jfonseca
 *
 */
@Stateless
@Path("/servicios/catalogo")
public class ServicioCatalogo {
	/**
	 * Imprimir logs
	 */
	private static final Logger LOG = Logger.getLogger(ServicioRol.class.getName());


	@Inject
	private NegocioCatalogos negocioCatalogo;
	
	@Inject
	private NegocioMensaje negocioMensaje;


	private MensajeDTO obtenerMensajeDTOFromException(Exception e) {
		return HandleMensajeDTO.obtenerMensajeDTOFromException(negocioMensaje, e);
	}
	
	/**
	 * Metodo que lista los catalogos segun un tipo pasado por parametro
	 * @param tipoCatalogo
	 * @return
	 */
	@GET
	@Path("/listarCatalogoTipo")
	@Produces({ APPLICATION_JSON })
	public Response listarCatalogoTipo(@QueryParam("tipoCatalogo") String tipoCatalogo) {
		try {
			LOG.info("Inicio servicio  listarCatalogoTipo con parametro tipoCatalogo => "+tipoCatalogo);
			List<CatalogosDTO> catalogos = negocioCatalogo.listarCatalogoPorTipo(tipoCatalogo);
			return Response.ok(catalogos).build();
		} catch (SIA3Exception e) {
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			LOG.error("Error inesperado en servicio listarCatalogoTipo");
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}
	
	/**
	 * Servicio REST que lista los catalogos segun un tipo pasado por parametro
	 * y que pertenencen solo a la aplicacion de seguridad
	 * 
	 * @param tipoCatalogo
	 * @return
	 */
	@GET
	@Path("/listarCatalogoTipoSeguridad")
	@Produces({ APPLICATION_JSON })
	public Response listarCatalogoTipoSeguridad(@QueryParam("tipoCatalogo") String tipoCatalogo) {
		try {
			LOG.info("Inicio servicio  listarCatalogoTipoSeguridad con parametro tipoCatalogo => "+tipoCatalogo);
			List<CatalogosDTO> catalogos = negocioCatalogo.listarCatalogoPorTipoSeguridad(tipoCatalogo);
			return Response.ok(catalogos).build();
		} catch (Exception e) {
			LOG.error("Error inesperado en servicio listarCatalogoTipo");
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

}
