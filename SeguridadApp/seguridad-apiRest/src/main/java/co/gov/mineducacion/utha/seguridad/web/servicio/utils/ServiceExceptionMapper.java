package co.gov.mineducacion.utha.seguridad.web.servicio.utils;

import java.sql.SQLSyntaxErrorException;

import javax.persistence.PersistenceException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;

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
        
        if(exception instanceof SeguridadException){
        	SeguridadException e = (SeguridadException)exception;
        	err.setErrorClass(e.getClass().getCanonicalName());
        	err.setMessage(e.getDetalle() != null?e.getDetalle():e.getMessage());
        }
        
        if(exception.getCause()!=null && (exception.getCause().getClass().isAssignableFrom(PersistenceException.class))){
            err.setErrorClass(exception.getCause().getClass().getCanonicalName());
            err.setMessage(exception.getCause().getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }
        if(exception.getClass().isAssignableFrom(SQLSyntaxErrorException.class)){
        	 err.setErrorClass(exception.getClass().toString());
             err.setMessage(exception.getMessage());
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }
        if(exception.getClass().isAssignableFrom(WebApplicationException.class)){
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
    }
    
}
