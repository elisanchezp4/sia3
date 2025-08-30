package co.gov.mineducacion.utha.seguridad.web.servicio.accesibilidad;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarioAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;

@Stateless
@Path("/accesibilidad/propiedadesAccUser")
public class ServicioPropiedadesAccUser {

	@EJB
	IUsuarioAccesibilidad servicio;

	/**
	 * Servicio encargado de retornar las propiedades de accesibilidad configuradas
	 * por el usuario. Si el usuario no tiene propiedades configuradas el sistema
	 * crea los valores por defecto y los retorna.
	 * 
	 * @param userId
	 * @return
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obtenerPropAccesibilidad")
	public Response obtenerPropAccesibilidad(@QueryParam(Constantes.HEADER_PARAM_USERID) String userId) {
		Map<String, Object> accesibilidad = new HashMap<>();
		String internalCode;
		String exceptionMessage;
		try {
			accesibilidad = servicio.consultaPropAcceUsuario(userId);
		} catch (Exception e) {
			internalCode = "-2";
			exceptionMessage = Constantes.ERROR_SIN_DATOS;
			accesibilidad.put("ExceptionMessage", exceptionMessage);
			accesibilidad.put("InternalCode", internalCode);
			return Response.status(Response.Status.BAD_REQUEST).entity(accesibilidad).build();		}

		if (accesibilidad.containsKey("InternalCode")) {
			return Response.status(Response.Status.OK).entity(accesibilidad).build();

		} else {
			return Response.status(Response.Status.OK).entity(Collections.singletonMap("accesibilidad", accesibilidad))
					.build();

		}
	}

}