package co.gov.mineducacion.seguridad.web.servicio.utils;

import javax.persistence.PersistenceException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ExceptionMapper;
/**
 * Clase que implementa el ExceptionMapper de JAX-RS para manejar las respuestas del servidor
 * y devolver un objeto ResponseError como objeto JSON.
 * @author Asesoftware
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<Throwable>{

    @Override
    public Response toResponse(Throwable exception) {
        
        ResponseError err = new ResponseError();
        err.setErrorClass(exception.getClass().toString());
        err.setMessage(exception.getMessage());
        if(exception.getCause()!=null && (exception.getCause().getClass().isAssignableFrom(PersistenceException.class))){
            err.setErrorClass(exception.getCause().getClass().getCanonicalName());
            err.setMessage(exception.getCause().getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }
        if(exception.getClass().isAssignableFrom(WebApplicationException.class)){
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }
        if(exception.getClass().isAssignableFrom(NotFoundException.class)){
            
            return Response.status(Response.Status.NOT_FOUND).entity(err).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
    }
    
}
