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

import co.gov.mineducacion.seguridad.negocio.NegocioRoles;
import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
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
@Path("/servicios/rol")
public class ServicioRol {
	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ServicioRol.class.getName());

	@Inject
	private NegocioRoles negocioRol;
	
	@Inject
	private NegocioMensaje negocioMensaje;

	/**
	 * Metodo para consultar todas las entidades Aplicacion registradas en la
	 * base de datos
	 * 
	 * @return Response OK o Error(BAD_REQUEST)
	 * 
	 */
	@GET
	@Path("/getAllRoles")
	@Produces({ APPLICATION_JSON })
	public Response getAllRoles() {
		try {
			logger.info("Inicio metodo getAllRoles");
			List<RolesDTO> roles = negocioRol.getAllRoles();
			return Response.ok(roles).build();
		} catch (SIA3Exception e) {
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Error inesperado en servicio getAllRoles");
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}
	
	/**
	 * Servicio que busca un rol por id de aplicacion
	 * @param idAplicacion
	 * @return
	 */
	@GET
	@Path("/getRolPorApp")
	@Produces({ APPLICATION_JSON })
	public Response getRolesPorAplicacion(@QueryParam("aplicacionId") Long aplicacionId) {
		try {
			logger.info("Inicio metodo getRolesPorAplicacion con parametro aplicacionId:"+aplicacionId);
			List<RolesDTO> roles = negocioRol.getRolesPorAplicacion(aplicacionId);
			return Response.ok(roles).build();
		} catch (SIA3Exception se) {
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(se);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en getRolesPorAplicacion:" + e.getCause());
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}



	private MensajeDTO obtenerMensajeDTOFromException(Exception e) {
		try {
			return negocioMensaje.mensajePorCodigo(e.getMessage());
		} catch (SIA3Exception se1) {
			MensajeDTO mensajeDTO = new MensajeDTO();
			mensajeDTO.setCodigo("APP000003");
			mensajeDTO.setTipoMensaje(MessagesConstants.TIPO_MENSAJE_ERROR);
			mensajeDTO.setDescripcion(MessagesConstants.APP000003);
			return mensajeDTO;
		}
	}
	/**
	 * Servico REST para crear un nuevo rol
	 * 
	 * @param aplicacionDTO
	 * @return Response OK o Error(BAD_REQUEST)
	 * @throws SIA3Exception
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response crearRol(RolesDTO rolDTO) {
		logger.info("Inicio servicio crearRol con rolDTO =>" + rolDTO);
		MensajeDTO mensajeDTO;
		try {
			negocioRol.crear(rolDTO);
			logger.info("Finalizó el metodo crearrol");
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100023);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (SIA3Exception se) {
			 mensajeDTO = obtenerMensajeDTOFromException(se);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en crearRol:" + e.getCause());
			 mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

	
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response editarRol(RolesDTO rolDTO){
		logger.info("Inicio metodo editarRol con rolDTO =>" + rolDTO);
		MensajeDTO mensajeDTO;
		try{
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100026);
			negocioRol.actualizar(rolDTO);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (SIA3Exception se) {
			 mensajeDTO = obtenerMensajeDTOFromException(se);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en editarRol:" + e.getCause());
			 mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

	/**
	 * Servicio REST para eliminar un rol existente
	 * 
	 * @param rolId
	 * @return
	 */
	@DELETE
	@Path("/eliminarRol")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response eliminarRol(@QueryParam("rolId") BigDecimal rolId, @QueryParam("usuarioId") String usuarioId) {
		logger.info("Inicio metodo eliminarRol con rolId =>" + rolId);
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100034);
			negocioRol.eliminarRol(rolId, usuarioId);
			logger.info("Finalizó el metodo eliminarRol");
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (SIA3Exception se) {
			try{
				if(se.getMessage() == MessagesConstants.APP100118){
					mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP100118_CODE, MessagesConstants.APP100118);
				}else{
					mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100118);
				}
				
			}catch(SIA3Exception se1){
				mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP000003_CODE, MessagesConstants.APP000003);
			}
			logger.error("Error en metodo eliminarRol");
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}catch (Exception e){
			logger.error("Ocurrio un error inesperado en eliminarRol:" + e.getCause());
			return Response.status(Response.Status.BAD_REQUEST).entity(MessagesConstants.APP000003).build();
		}
	}
	
	/**
	 * Servicio REST que busca todos los roles asociados a un usuario
	 * @param usuarioId
	 * @return
	 */
	@GET
	@Path("/getRolPorUsuario")
	@Produces({ APPLICATION_JSON })
	public Response getRolesPorUsuario(@QueryParam("usuarioId") Long usuarioId) {
		try {
			logger.info("Inicio metodo getRolesPorUsuario con parametro usuarioId:"+usuarioId);
			List<RolesDTO> roles = negocioRol.getRolesPorUsuario(usuarioId);
			return Response.ok(roles).build();
		} catch (SIA3Exception e) {
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Error inesperado en servicio getRolesPorUsuario");
			MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		}
	}

}
