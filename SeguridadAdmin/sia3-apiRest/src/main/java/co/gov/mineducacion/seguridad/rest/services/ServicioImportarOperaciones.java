package co.gov.mineducacion.seguridad.rest.services;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.negocio.NegocioOperaciones;
import co.gov.mineducacion.seguridad.rest.dtos.ImportarOperacionesDTO;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/servicios/operacion")
public class ServicioImportarOperaciones {

    private static final Logger logger = Logger.getLogger(ServicioImportarOperaciones.class.getName());

    @Inject
    private NegocioOperaciones negocioOperacion;

    @Inject
    private NegocioMensaje negocioMensaje;

    @POST
    @Path("/importar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response importarOperaciones(ImportarOperacionesDTO importarOperacionesDTO) {
        logger.info("Inicio servicio importarOperaciones con importarOperacionesDTO =>" + new Gson().toJson(importarOperacionesDTO));
        MensajeDTO mensajeDTO;
        try {
            negocioOperacion.importar(
                    importarOperacionesDTO.getOperacionesList(),
                    importarOperacionesDTO.getIdAplicacion()
            );
            logger.info("Finalizó el metodo importarOperaciones");
            mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100125);
            mensajeDTO.setDescripcion("Se han importado las operaciones correctamente");
            return Response.status(Response.Status.OK).entity(mensajeDTO).build();
        } catch (SIA3Exception e) {
            try {
                mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100054);
            } catch (SIA3Exception se1) {
                mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP000003_CODE, MessagesConstants.APP000003);
            }
            logger.error("Error en metodo importarOperaciones");
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en importarOperaciones:" + e.getCause());
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesConstants.APP000003).build();
        }
    }


}