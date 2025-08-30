package co.gov.heinsohn.men.parametros.rest.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.apache.log4j.Logger;

import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.facades.IConsultaItemListaValoresFacadeEJB;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

/**
 * Servicio rest que expone los metodos de negocio de parametrización de
 * parametros
 * 
 * @author Jhonnatan Orozco Duque <jorozco@heinsohn.com.co>
 *
 */

@Path("/ConsultaItemListaValores")
@RequestScoped
public class ConsultaItemListaValoresService {

	// log de la aplicacion para la clase

	private static final Logger logger = Logger.getLogger(ConsultaItemListaValoresService.class.getName());
	private static final String FALLO_CONSULTA_PARAMETROS = "Fallo consultando los Parametros_";

	/*
	 * instancia de la fachada de parametrización de parametros inyectada por el
	 * servidor
	 */
	@Inject
	private IConsultaItemListaValoresFacadeEJB conItemListValFacade;

	/**
	 * Método que expone la funcionalidad de consultaListaValoresSimple
	 * 
	 * @param nombreApp
	 *            dominio
	 * 
	 * @return Respuesta de jax-rs con el resultado de la petición OK o ERROR
	 */
	@GET
	@Path("/ValoresSimple")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaListaValoresSimple(@QueryParam("nombreApp") String nombreApp,
			@QueryParam("dominio") String dominio) {
		logger.info("Inició el metodo consultaListaValoresSimple ");
		try {
			logger.info( "Consulta los Parametros del  ");
			List<ParametroDTO> parametroDTO = conItemListValFacade.consultaListaValoresSimple(nombreApp, dominio);

			logger.info("Finalizó el metodo consultaListaValoresSimple ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error(FALLO_CONSULTA_PARAMETROS, e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	
	
	/**
	 * Método que expone la funcionalidad de consultarParametroSimple
	 * 
	 * @param nombreApp
	 *            dominio
	 * 
	 * @return Respuesta de jax-rs con el resultado de la petición OK o ERROR
	 */
	@GET
	@Path("/ParametroSimple")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultarParametroSimple(@QueryParam("nombreApp") String nombreApp,
			@QueryParam("dominio") String dominio) {
		logger.info("Inició el metodo consultarParametroSimple " + dominio);
		try {
			logger.info("Consulta los Parametros del  " + dominio);
			ParametroDTO parametroDTO = conItemListValFacade.consultarParametroSimple(nombreApp, dominio);

			logger.info("Finalizó el metodo consultarParametroSimple " + dominio);
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			logger.error(FALLO_CONSULTA_PARAMETROS, e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone la funcionalidad de consultarListaValoresMultiple
	 * 
	 * @param nombreApp
	 *            dominio dominioPadre codigoPadre
	 * 
	 * @return Respuesta de jax-rs con el resultado de la petición OK o ERROR
	 */
	@GET
	@Path("/ValoresMultiple")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultarListaValoresMultiple(@QueryParam("nombreApp") String nombreApp,
			@QueryParam("dominio") String dominio, @QueryParam("dominioPadre") String dominioPadre,
			@QueryParam("codigoPadre") String codigoPadre) {
		logger.info("Inició el metodo consultarListaValoresMultiple ");
		try {
			logger.info("Consulta los Parametros del  ");
			List<ParametroDTO> parametroDTO = conItemListValFacade.consultarListaValoresMultiple(nombreApp, dominio,
					dominioPadre, codigoPadre);

			logger.info("Finalizó el metodo consultarListaValoresMultiple ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			logger.error(FALLO_CONSULTA_PARAMETROS, e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	
	
}
