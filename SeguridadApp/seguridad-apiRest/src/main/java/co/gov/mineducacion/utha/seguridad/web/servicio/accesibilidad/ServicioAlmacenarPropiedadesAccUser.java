package co.gov.mineducacion.utha.seguridad.web.servicio.accesibilidad;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.negocio.NegocioColaAccesibilidad;

@Stateless
@Path("/accesibilidad/AlmacenarPropiedadesAccUser")
public class ServicioAlmacenarPropiedadesAccUser {

	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	@EJB
	private NegocioColaAccesibilidad negocioColaAccesibilidad;

	/**
	 * Servicio encargado de actualizar las propiedades de accesibilidad
	 * configurados por el usuario
	 * @param propiedades
	 * @param userId
	 * @param token
	 * @return
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/almacenarPropAccesibilidad")
	public Response almacenarPropAcces(Map<String, String> propiedades,
			@HeaderParam(Constantes.HEADER_PARAM_USERID) String userId,
			@HeaderParam(Constantes.HEADER_PARAM_TOKEN) String token) {

		Map<String, String> respuesta = new HashMap<>();
		try {
			negocioColaAccesibilidad.enviarColaPropAccesibilidad(propiedades, userId);
			respuesta.put("mensaje", Constantes.MENSAJE_OK);
		} catch (Exception e) {
			respuesta.put("mensaje", Constantes.ERROR_SIN_DATOS);
			respuesta.put("codigo", "-1" );
			return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
		}
		return Response.status(Response.Status.OK).entity(respuesta).build();
	}

}
