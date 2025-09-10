package co.gov.mineducacion.utha.seguridad.web.servicio.seguridad;

import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import co.gov.mineducacion.seguridad.ejb.servicios.strategy.UserCreationStrategy;
import co.gov.mineducacion.seguridad.ejb.servicios.strategy.impl.ExternalUserCreationStrategy;
import co.gov.mineducacion.seguridad.ejb.servicios.strategy.impl.InternalUserCreationStrategy;
import co.gov.mineducacion.seguridad.modelo.dtos.UserDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.PeticionConsultaUsuarioRol;
import co.gov.mineducacion.utha.seguridad.web.servicio.utils.dto.UtilsDTO;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Expone los servicios REST disponibles para realizar operaciones de gestion de usuarios sobre el sistema de seguridad
 * @author Asesoftware - Javier Estévez
 */
@Stateless
@Path("servicios/gestionusuarios")
@Slf4j
public class ServiciosRestGestionUsuarios {
	
	@EJB
	private IUsuarios servicioUsuarios;

	@EJB
	private NegocioUsuarios negocioUsuarioBean;

	@EJB
	private InternalUserCreationStrategy internalUserCreationStrategy;

	@EJB
	private ExternalUserCreationStrategy externalUserCreationStrategy;

	private Map<BigDecimal, UserCreationStrategy> creationStrategies;
	
	
    /**
     * Retorna los usuarios que pertenecen al rol recibido por parámetro en el objeto PeticionConsultaUsuarioRol
     * @param peticion
     * @return
     * @throws SeguridadException
     */
	@POST
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("consultarroles")
	public List<UsuariosDTO> consultarUsuariosRol(PeticionConsultaUsuarioRol peticion)
			throws SeguridadException {
		
    	UtilsDTO.validarPeticionConsultaUsuarioRol(peticion);
    	
		List<UsuariosDTO> listaUsuarios;
		
		servicioUsuarios.verificarUsuarioAdministrador(peticion.getHeader().getUserId(), Constantes.ROL_GESTIONADOR);
		listaUsuarios = servicioUsuarios.consultarRolesXUSuario(peticion.getRol(), peticion.getHeader().getApiKey(), peticion.getHeader().getUserId());
		
		return listaUsuarios;
		
	}

	@POST
	@Consumes({APPLICATION_JSON})
	@Produces({APPLICATION_JSON})
	@Path("/user")
	public UserDTO createUser(UserDTO userDTO, @HeaderParam("access_token") String token, @HeaderParam("client_id") String clientId, @HeaderParam("user_id") Integer userId) throws SIA3Exception, SeguridadException {
		log.info("Petición REST para crear un usuario de tipo: {}", userDTO.getUserType());

		if (userDTO == null) {
			log.warn("Petición de creación de usuario nula.");
			throw new SIA3Exception("La información del usuario no puede ser nula.");
		}

		// Toda la lógica de negocio se delega al EJB NegocioUsuarios
		UserDTO usuarioCreado = negocioUsuarioBean.userCreate(userDTO);

		log.info("Usuario creado con éxito en la capa de negocio.");
		return usuarioCreado;
	}
}
