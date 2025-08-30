package co.gov.heinsohn.men.parametros.rest.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.facades.IAdministrarParametroFacadeEJB;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

@Path("/AdministrarParametro")
@RequestScoped
public class AdministrarParametroService {

	// log de la aplicacion para la clase
	private static final Logger logger = Logger.getLogger(AdministrarParametroService.class.getName());
	private static final String CONSULTA_PRAMETROS = "Consulta los Parametros del  ";
	private static final String FALLO_CONSULTA_PRAMETROS = "Fallo consultando los Parametros";

	/*
	 * instancia de la fachada de parametrización de parametros inyectada por el
	 * servidor
	 */
	@Inject
	private IAdministrarParametroFacadeEJB administrarParametroFacadeEJB;

	/**
	 * Método que expone la funcionalidad de consultaParemetrosFiltros
	 *
	 * @param tipoParametro
	 * @param nombreApp
	 * @param dominio
	 * @param estado
	 * @return
	 */
	@GET
	@Path("/consultaParemetrosFiltros")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaParemetrosFiltros(@QueryParam("tipoParametro") String tipoParametro,
			@QueryParam("nombreApp") String nombreApp, @QueryParam("dominio") String dominio,
			@QueryParam("estado") String estado) {
		logger.info("Inició el metodo consultaParemetrosFiltros ");
		try {
			logger.info(CONSULTA_PRAMETROS);
			List<ParametroDTO> parametroDTO = administrarParametroFacadeEJB.consultaParemetrosFiltros(tipoParametro,
					nombreApp, dominio, estado);

			logger.info("Finalizó el metodo consultaParemetrosFiltros ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error(FALLO_CONSULTA_PRAMETROS, e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone la funcionalidad de consultaParemetrosNoModificables
	 *
	 * @param tipoParametro
	 * @param nombreApp
	 * @param dominio
	 * @param estado
	 * @return
	 */
	@GET
	@Path("/consultaParemetrosNoModificables")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaParemetrosNoModificables(@QueryParam("tipoParametro") String tipoParametro,
			@QueryParam("nombreApp") String nombreApp, @QueryParam("dominio") String dominio,
			@QueryParam("estado") String estado) {
		logger.info("Inició el metodo consultaParemetrosNoModificables ");
		try {
			logger.info("Consulta los Parametros solo consulta  ");
			List<ParametroDTO> parametroDTO = administrarParametroFacadeEJB.consultaParemetrosFiltros(tipoParametro,
					nombreApp, dominio, estado);

			logger.info("Finalizó el metodo consultaParemetrosNoModificables ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error(FALLO_CONSULTA_PRAMETROS, e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone por post la funcionalidad para adicionar un parametro
	 *
	 * @param parametroDTO
	 *            el parametro que se desea crear
	 * @return Respuesta de jax-rs con el resultado de la petición OK o ERROR
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/adicionarParametro")
	public Response adicionarParametro(ParametroDTO parametroDTO) {
		logger.info("Inició el metodo adicionarMultivalores con parametroDTO => " + parametroDTO);
		try {
			administrarParametroFacadeEJB.adicionarParametro(parametroDTO);
			logger.info("Finalizó el metodo adicionarParametro");
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("Fallo en la adicionar Parametro ", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * metodo que espone por put la funcionalidad para modificar un paarmetro
	 *
	 * @param parametroDTO
	 *            el parmetro que se desea modificar
	 * @return Respuesta de jax-rs con el resultado de la petición OK o ERROR
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Path("/editarParametro")
	public Response editarParametro(ParametroDTO parametroDTO) {
		logger.info("Inició el metodo editarParametro con parametroDTO => " + parametroDTO);
		try {
			administrarParametroFacadeEJB.editarParametro(parametroDTO);
			logger.info("Finalizó el metodo editarParametro");
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("Fallo en la edición del Parametro", e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone por post la funcionalidad para adicionar un parametro
	 *
	 * @param parametroDTO
	 *            el parametro que se desea adicionar
	 * @return Respuesta de jax-rs con el resultado de la petición OK o ERROR
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/adicionarDependiente")
	public Response adicionarDependiente(ParametroDTO parametroDTO) {
		logger.info("Inició el metodo adicionarDependiente con parametroDTO => " + parametroDTO);
		try {
			logger.info("Finalizó el metodo adicionarDependiente");
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("Fallo en la creación de dependiente", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * metodo que espone por put la funcionalidad para modificar un Univalores
	 *
	 * @param parametroDTO
	 *            el parametro que se desea modificar
	 * @return Respuesta de jax-rs con el resultado de la petición OK o ERROR
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Path("/editarUnivalores")
	public Response editarUnivalores(ParametroDTO parametroDTO) {
		logger.info("Inició el metodo editarUnivalores con parametroDTO => " + parametroDTO);
		try {
			logger.info("Finalizó el metodo editarSmlv");
			return Response.ok().build();
		} catch (Exception e) {
			logger.error("Fallo en la edición del  Univalor", e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone la funcionalidad de consultaParemetrosFiltrosDiferentes
	 *
	 * @param tipoParametro
	 * @param nombreApp
	 * @param dominio
	 * @param estado
	 * @return
	 */
	@GET
	@Path("/consultaParemetrosFiltrosDiferentes")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaParemetrosFiltrosDiferentes(@QueryParam("tipoParametro") String tipoParametro,
			@QueryParam("nombreApp") String nombreApp, @QueryParam("dominio") String dominio,
			@QueryParam("estado") String estado) {
		logger.info("Inició el metodo consultaParemetrosFiltrosDiferentes ");
		try {
			logger.info(CONSULTA_PRAMETROS);
			List<ParametroDTO> parametroDTO = administrarParametroFacadeEJB.consultaParemetrosFiltrosDiferentes(tipoParametro,
					nombreApp, dominio, estado);

			logger.info("Finalizó el metodo consultaParemetrosFiltrosDiferentes ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error(FALLO_CONSULTA_PRAMETROS, e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone la funcionalidad de consultaListasDominio
	 *
	 * @param nombreApp
	 * @param tipoParametro
	 * @return
	 */
	@GET
	@Path("/consultaListasDominio")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaListasDominio(@QueryParam("nombreApp") String nombreApp, @QueryParam("tipoParametro") String tipoParametro) {
		logger.info("Inició el metodo consultaListasDominio ");
		try {
			logger.info("Consulta los Parametros del consultaListasDominio ");
			List<ParametroDTO> parametroDTO = administrarParametroFacadeEJB.consultaListasDominio(nombreApp,tipoParametro);

			logger.info("Finalizó el metodo consultaListasDominio ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error("Fallo consultando los dominios", e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone la funcionalidad de consultaListasTipoParametro
	 *
	 * @param nombreApp
	 * @return
	 */
	@GET
	@Path("/consultaListasTipoParametro")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaListasTipoParametro(@QueryParam("nombreApp") String nombreApp) {
		logger.info("Inició el metodo consultaListasTipoParametro ");
		try {
			logger.info(CONSULTA_PRAMETROS);
			List<ParametroDTO> parametroDTO = administrarParametroFacadeEJB.consultaListasTipoParametro(nombreApp);

			logger.info("Finalizó el metodo consultaListasTipoParametro ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error("Fallo consultando los tipo Parametros", e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	/**
	 * Método que expone la funcionalidad de consultaListasAplicacion
	 *
	 * @param nombreApp
	 * @return
	 */
	@GET
	@Path("/consultaListasAplicacion")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaListasAplicacion() {
		logger.info("Inició el metodo consultaListasAplicacion ");
		try {
			logger.info("Consulta los Parametros del consultaListasAplicacion ");
			List<ParametroDTO> parametroDTO = administrarParametroFacadeEJB.consultaListasAplicacion();

			logger.info("Finalizó el metodo consultaListasAplicacion ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error("Fallo consultando las app", e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}


	/**
	 * Método que expone la funcionalidad de consultaListasAplicacion
	 *
	 * @param nombreApp
	 * @return
	 */
	@GET
	@Path("/consultaListasDepartamentos")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response consultaListasDepartamentos() {
		logger.info("Inició el metodo consultaListasAplicacion ");
		try {
			logger.info("Consulta los Parametros del consultaListasDepartamentos ");
			List<ParametroDTO> parametroDTO = administrarParametroFacadeEJB.consultaListasDepartamentos();

			logger.info("Finalizó el metodo consultaListasDepartamentos ");
			return Response.ok(parametroDTO).build();
		} catch (PARAMETROSException e) {
			e.printStackTrace();
			logger.error("Fallo consultaListasDepartamentos", e);
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
}
