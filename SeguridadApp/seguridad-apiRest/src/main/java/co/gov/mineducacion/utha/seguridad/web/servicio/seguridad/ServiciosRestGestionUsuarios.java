package co.gov.mineducacion.utha.seguridad.web.servicio.seguridad;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.PeticionConsultaUsuarioRol;
import co.gov.mineducacion.utha.seguridad.web.servicio.utils.dto.UtilsDTO;

/**
 * Expone los servicios REST disponibles para realizar operaciones de gestion de usuarios sobre el sistema de seguridad
 * @author Asesoftware - Javier Estévez
 */
@Stateless
@Path("servicios/gestionusuarios")
public class ServiciosRestGestionUsuarios {
	
	@EJB
	private IUsuarios servicioUsuarios;
	
	
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
    

}
