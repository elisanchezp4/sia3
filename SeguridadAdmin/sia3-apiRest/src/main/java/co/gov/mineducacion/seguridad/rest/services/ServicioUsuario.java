package co.gov.mineducacion.seguridad.rest.services;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.EJB;
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

import co.gov.mineducacion.seguridad.rest.util.HandleMensajeDTO;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.ejb.servicios.ServicioLDAP;
import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Servicios REST para la entidad Aplicacion
 * 
 * @author gvalverde
 *
 */
@Stateless
@Path("/servicios/usuario")
public class ServicioUsuario {
	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ServicioUsuario.class.getName());

	@Inject
	private NegocioUsuarios negocioUsuario;

	@Inject
	private NegocioMensaje negocioMensaje;
	
	@EJB 
	private ParametrosSng parametrosSng;

	/**
	 * Servicio REST para consultar un usuario segun el nombre indicado Usa el
	 * usuarioSesionId recibido para registrar eventos de auditoria
	 * 
	 * @param nombreUsuario
	 * @param usuarioSesionId
	 * @return
	 */
	@GET
	@Path("/getUsuarioPorNombre")
	@Produces({ APPLICATION_JSON })
	public Response getUsuarioPorNombre(@QueryParam("nombreUsuario") String nombreUsuario,
			@QueryParam("usuarioSesionId") String usuarioSesionId) {
		try {
			logger.info("Inicio metodo getUsuarioPorNombre con parametro nombreUsuario:" + nombreUsuario
					+ " y usuarioSesionId:" + usuarioSesionId);
			UsuariosDTO usuarioDTO = negocioUsuario.consultarUsuarioPorNombre(nombreUsuario, usuarioSesionId);
			return Response.ok(usuarioDTO).build();
		}
		catch (Exception e) {
			return handleException(e, "getUsuarioPorNombre");
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
	 * Servicio REST para activar un usuario
	 * 
	 * @param usuarioDTO
	 * @return
	 */
	@PUT
	@Path("/activarUsuario")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response activarUsuario(UsuariosDTO usuarioDTO) {
		logger.info("Inicio servicio activarUsuario con usuarioDTO =>" + usuarioDTO);
		logger.info("Usuario DTO Ruta =>" + usuarioDTO.getRuta());
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100070);

			String contexto = "http://" + usuarioDTO.getAdressLocal() + ":" + usuarioDTO.getPortLocal()
					+ Constantes.PATH_CONTEXT_MAIL;

			negocioUsuario.activarUsuario(usuarioDTO, contexto);
			logger.info("Finalizó el servicio activarUsuario");
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (Exception e) {
			return handleException(e, "activarUsuario");
		}
	}

	/**
	 * Servicio REST para inactivar un usuario
	 * 
	 * @param usuarioDTO
	 * @return
	 */
	@PUT
	@Path("/inactivarUsuario")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response inactivarUsuario(UsuariosDTO usuarioDTO) {
		logger.info("Inicio servicio inactivarUsuario con usuarioDTO =>" + usuarioDTO);
		try {
			MensajeDTO mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100080);
			negocioUsuario.inactivarUsuario(usuarioDTO);
			logger.info("Finalizó el servicio inactivarUsuario");
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		}  catch (SIA3Exception e) {
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Error inesperado en servicio inactivarUsuario");
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response crearUsuario(UsuariosDTO usuarioDTO) {
		logger.info("Inicio metodo crearUsuario con usuarioDTO =>" + usuarioDTO);
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			negocioUsuario.crear(usuarioDTO);
			logger.info("Finalizó el metodo crearUsuario");
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100091);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		}catch (Exception e) {
			return handleException(e, "crearUsuario");
		}
	}

	/**
	 * Servicio REST que filtra usuarios del LDAP con base en criterios de
	 * busqueda. Usa el campo usuarioSesionId para el registro de eventos de
	 * auditoria
	 * 
	 * @param logonName
	 * @param tipoUsuario
	 * @param nombres
	 * @param apellidos
	 * @param email
	 * @param usuarioSesionId
	 * @return
	 */
	@GET
	@Path("/getUsuariosPorFiltros")
	@Produces({ APPLICATION_JSON })
	public Response getUsuariosPorFiltros(@QueryParam("logonName") String logonName,
			@QueryParam("tipoUsuario") BigDecimal tipoUsuario, @QueryParam("nombres") String nombres,
			@QueryParam("apellidos") String apellidos, @QueryParam("email") String email,
			@QueryParam("usuarioSesionId") String usuarioSesionId) {
		try {
			logger.info("Inicio servicio REST getUsuariosPorFiltros con parametros nombreUsuario:" + logonName
					+ ". tipoUsuario:" + tipoUsuario + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:"
					+ email + ", usuarioSesionId: " + usuarioSesionId);
			List<UsuariosDTO> usuarioDTOList = negocioUsuario.consultarUsuarioPorFiltros(logonName, tipoUsuario,
					nombres, apellidos, email, usuarioSesionId);
			List<UsuariosDTO> listaFiltrada = usuarioDTOList.stream()
					.filter(usuario -> usuario.getTipo().equals(tipoUsuario))
					.collect(Collectors.toList());
			if (listaFiltrada.isEmpty()) {
				logger.error(
						"Error en metodo consultarUsuarioPorFiltros: No se encontraron usuarios en el LDAP con parametros logonName:"
								+ logonName + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email + ". tipo de Usuario:" + tipoUsuario);
				throw new SIA3Exception(MessagesConstants.APP100097);
			} else {
				return Response.ok(listaFiltrada).build();
			}
		} catch (Exception e) {
			return handleException(e, "getUsuariosPorFiltros");
		}
	}

	/**
	 * Servicio REST que lista los usuarios relacionados con una aplicacion y
	 * rol indicados
	 * 
	 * @param opcionId
	 * @param rolId
	 * @return
	 */
	@GET
	@Path("/getUsuariosPorAppRol")
	@Produces({ APPLICATION_JSON })
	public Response getUsuariosPorAppRol(@QueryParam("opcionId") BigDecimal opcionId,
			@QueryParam("rolId") BigDecimal rolId) {
		try {
			logger.info("Inicio metodo getUsuarioPorAppRol con parametro opcionId:" + opcionId + " y rolId:" + rolId);
			List<UsuariosDTO> usuarioDTOList = negocioUsuario.getUsuariosPorAppRol(opcionId, rolId);
			return Response.ok(usuarioDTOList).build();
		}  catch (Exception e) {
			return handleException(e, "getUsuariosPorAppRol");
		}
	}

	private MensajeDTO obtenerMensajeDTOFromException(Exception e) {
		return HandleMensajeDTO.obtenerMensajeDTOFromException(negocioMensaje, e);
	}


	/**
	 * Servicio REST que lista los usuarios relacionados con una aplicacion y
	 * rol
	 * 
	 * @param opcionId
	 * @return
	 */
	@GET
	@Path("/getUsuariosPorApp")
	@Produces({ APPLICATION_JSON })
	public Response getUsuariosPorApp(@QueryParam("opcionId") BigDecimal opcionId,
			@QueryParam("aplicacionId") BigDecimal aplicacionId) {
		try {
			logger.info("Inicio metodo getUsuarioPorApp con parametro opcionId:" + opcionId + "aplicacionId: "
					+ aplicacionId);
			List<UsuariosDTO> usuarioDTOList = negocioUsuario.getUsuariosPorApp(opcionId, aplicacionId);
			return Response.ok(usuarioDTOList).build();
		} catch (Exception e) {
			return handleException(e, "getUsuariosPorApp");
		}
	}

	/**
	 * Servicio REST para inactivar un usuario
	 * 
	 * @param usuarioDTO
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Path("/desvincularUsuarios")
	public Response desvincularUsuarios(@QueryParam("rolId") String rolId, List<UsuariosDTO> listaUsuarios) {
		logger.info("Inicio el servicio desvincularUsuarios =>" + listaUsuarios);
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {

			negocioUsuario.desvincularUsuarios(rolId, listaUsuarios);
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100117);
			logger.info("Finalizó el servicio desvincularUsuarios");
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (Exception e) {
			return handleException(e, "desvincularUsuarios");
		}
	}

	/**
	 * Servicio REST que obtiene la lista de todos los usuarios
	 * 
	 * @param estado
	 * @return
	 */
	@GET
	@Path("/getAllUsuarios")
	@Produces({ APPLICATION_JSON })
	public Response getAllUsuarios(@QueryParam("estado") Long estado) {
		try {
			logger.info("Inicio servicio metodo getAllUsuarios. Filtro estado:" + estado);
			List<UsuariosDTO> usuarios = negocioUsuario.getAllUsuarios(estado);
			return Response.ok(usuarios).build();
		} catch (Exception e) {
			return handleException(e, "getAllUsuarios");
		}
	}

	/**
	 * Servicio REST que obtiene la lista de los usuarios de la aplicacion de
	 * seguridad
	 * 
	 * @param estado
	 * @return
	 */
	@GET
	@Path("/getAllUsuariosSeguridad")
	@Produces({ APPLICATION_JSON })
	public Response getAllUsuariosSeguridad(@QueryParam("estado") Long estado) {
		try {
			logger.info("Inicio servicio metodo getAllUsuariosSeguridad. Filtro estado:" + estado);
			List<UsuariosDTO> usuarios = negocioUsuario.getAllUsuariosSeguridad(estado);
			return Response.ok(usuarios).build();
		} catch (Exception e) {
			return handleException(e, "getAllUsuariosSeguridad");
		}
	}

	@GET()
	@Path("/getAllUsuariosPorApp")
	@Produces({ APPLICATION_JSON })
	public Response getAllUsuariosPorApp(@QueryParam("aplicacionId") Long aplicacionId,  @QueryParam("usuario") String usuario){
		try{
			logger.info("Inicio metodo getAllUsuariosPorApp con parametro aplicacionId:"+aplicacionId);
			List<UsuariosDTO> usuarios = negocioUsuario.getAllUsuariosPorApp(aplicacionId, usuario);
			return Response.ok(usuarios).build();
		} catch (Exception e) {
			return handleException(e, "getAllUsuariosPorApp");
		}
	}

	// Se agrego el servicio para la lista de inactivos
	@GET
	@Consumes({ APPLICATION_JSON })
	@Produces({ APPLICATION_JSON })
	@Path("obtenerlistDesactivados")
	public List<UsuarioLdap> Listainactivos(@QueryParam("url") String url) throws SeguridadException {
		List<UsuarioLdap> usuarioLdapList = new ArrayList<>();
		try {
			logger.info("Servicio De Usuarios Inactivos");
			usuarioLdapList = ServicioLDAP.listaUserInactivos(parametrosSng.obtenerParametros());
			logger.info("informa cargo bien el servicio Inactivos");
			return usuarioLdapList;
		} catch (SeguridadException e) {
			return usuarioLdapList;
		}
	}

	// Se agrego el servicio para la lista de activos
	@GET
	@Consumes({ APPLICATION_JSON })
	@Produces({ APPLICATION_JSON })
	@Path("obtenerlistActivos")
	public List<UsuarioLdap> UserActivo(@QueryParam("url") String url) throws SeguridadException {
		logger.info("Servicio de Usuarios Activos");
		List<UsuarioLdap> usuarioLdapList = ServicioLDAP.listaUserActivos(parametrosSng.obtenerParametros());
		logger.info("informa cargo bien el servicio Activos");
		return usuarioLdapList;

	}
	
	/** 
	 * 
	 * Servicio que lista los usuarios relacionados con una aplicaci󮠹
	 * nombre del rol indicados
	 * 
	 * @param opcionId
	 * @param rol
	 * @return
	 */
	
	@GET
	@Path("/getUsuariosPorAppNombreRol")
	@Produces({ APPLICATION_JSON })
	public Response getUsuariosPorAppNombreRol(@QueryParam("opcionId") BigDecimal opcionId,
			@QueryParam("rol") String rol, 
			@QueryParam("nombres") String nombres, @QueryParam("estado") Long estado,
			@QueryParam("idUsuario") Long idUsuario, @QueryParam("pagInicio") Integer pagInicio,
			@QueryParam("pagFin") Integer pagFin) {
		try {
			logger.info("Inicio metodo getUsuariosPorAppNombreRol con parametro opcionId:" + opcionId + " y rol:" + rol);
			List<UsuariosDTO> usuarioDTOList = negocioUsuario.getUsuariosPorAppNombreRol(opcionId, rol,estado,idUsuario,  pagInicio,  pagFin);
	
			if (nombres != null) {
				
				List<UsuariosDTO> usuariosEncontrados = new ArrayList<UsuariosDTO>();			
					for (UsuariosDTO usuarioFiltro : usuarioDTOList) {
						if (usuarioFiltro.getNombres().contains(nombres)) {
							usuariosEncontrados.add(usuarioFiltro);
						}
					
				}
				usuarioDTOList=usuariosEncontrados;
			}
			return Response.ok(usuarioDTOList).build();
		} catch (Exception e) {
			return handleException(e, "getUsuariosPorAppNombreRol");
		}
	}
	
	/** 
	 * 
	 * Servicio utilizado para realizar la paginaci󮠤el m굯do  getUsuariosPorAppNombreRol
	 * 
	 * @param opcionId
	 * @param rol
	 * @return
	 */
	
	@GET
	@Path("/getCountUsuariosPorAppNombreRol")
	@Produces({ APPLICATION_JSON })
	public Response getCountUsuariosPorAppNombreRol(@QueryParam("opcionId") BigDecimal opcionId,
			@QueryParam("rol") String rol, 
			@QueryParam("nombres") String nombres, @QueryParam("estado") Long estado,
			@QueryParam("idUsuario") Long idUsuario) {
		try {
			logger.info("Inicio metodo getCountUsuariosPorAppNombreRol con parametro opcionId:" + opcionId + " y rol:" + rol);
			Long countUsuarios = negocioUsuario.getCountUsuariosPorAppNombreRol(opcionId, rol,estado,idUsuario);
	
			return Response.ok(countUsuarios).build();
		} catch (Exception e) {
			return handleException(e, "getCountUsuariosPorAppNombreRol");
		}
	}
}
