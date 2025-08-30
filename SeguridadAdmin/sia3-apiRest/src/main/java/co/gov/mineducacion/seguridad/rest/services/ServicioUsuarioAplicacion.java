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

import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosAplicacion;

@Stateless
@Path("/servicios/usuarioAplicacion")
public class ServicioUsuarioAplicacion {

	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ServicioUsuarioRol.class.getName());

	@Inject
	private NegocioUsuariosAplicacion negocioUsuariosAplicacion;
	
	@Inject
	private NegocioMensaje negocioMensaje;

	@Inject
	private NegocioUsuariosRol negocioUsuariosRol;




	/**
	 * Servicio REST que asigna usuarios a una aplicacion
	 * 
	 * @param usuariosDTOList
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response agregarUsuariosApp(List<UsuariosDTO> usuariosDTOList, @QueryParam("aplicacionId") BigDecimal aplicacionId) {
		logger.info("Inicio servicio agregarUsuariosApp con usuariosDTOList =>" + usuariosDTOList + " y aplicacionId => "
				+ aplicacionId);
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			negocioUsuariosAplicacion.agregarUsuariosApp(usuariosDTOList, aplicacionId);
			logger.info("Finalizó el servicio agregarUsuariosApp");
			mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100089);
			return Response.status(Response.Status.OK).entity(mensajeDTO).build();
		} catch (SIA3Exception e) {
			try {
				mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100088);

			/*
				Se asignan los roles Gestionador y Autenticador al momento de agregar el usuario a la app
			 */

				boolean tieneRolGestionador=false;
				boolean tieneRolAutenticador = false;

				for(UsuariosDTO usuario: usuariosDTOList){
					List<UsuariosRolDTO> usuariosRol = negocioUsuariosRol.buscarUsuarioRolPorUsuario(usuario.getUsuarioId());
					for(UsuariosRolDTO rol: usuariosRol){
						tieneRolGestionador = rol.getRol_id().equals("47");
						tieneRolAutenticador = rol.getRol_id().equals("48");
					}
				}

				if(!(tieneRolGestionador)){ negocioUsuariosRol.agregarUsuariosARol(usuariosDTOList,new BigDecimal(47), usuariosDTOList.get(0).getUsuarioId()); }
				if(!(tieneRolAutenticador)){ negocioUsuariosRol.agregarUsuariosARol(usuariosDTOList,new BigDecimal(48), usuariosDTOList.get(0).getUsuarioId()); }

			} catch (SIA3Exception | SeguridadException se1) {
				mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP000003_CODE, MessagesConstants.APP000003);
			}
			logger.error("Error en servicio agregarUsuariosApp");
			return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
		} catch (Exception e) {
			logger.error("Ocurrio un error inesperado en servicio agregarUsuariosApp:" + e.getCause());
			return Response.status(Response.Status.BAD_REQUEST).entity(MessagesConstants.APP000003).build();
		}
	}
}
