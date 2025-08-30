package co.gov.mineducacion.seguridad.rest.services;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.negocio.NegocioRoles;
import co.gov.mineducacion.seguridad.rest.dtos.RolDTO;
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
@Path("/servicios/rol")
public class ServicioImportarRol {

    private static final Logger logger = Logger.getLogger(ServicioImportarRol.class.getName());

    @Inject
    private NegocioRoles negocioRol;

    @Inject
    private NegocioMensaje negocioMensaje;


    @POST
    @Path("/importar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response importarRol(RolDTO rolDTO) {
        logger.info("Inicio servicio importar con rolDTO =>" + rolDTO);
        MensajeDTO mensajeDTO;
        try {
            negocioRol.importar(rolDTO.aRolesDTO());
            mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100124);
            logger.info("Finalizó el metodo importar basico");
            return Response.status(Response.Status.OK).entity(mensajeDTO).build();
        } catch (SIA3Exception e) {
            try {
                if(e.getMessage().equals(MessagesConstants.APP100125)){
                    mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP100126_CODE, MessagesConstants.APP100125);
                }else {
                    logger.error("SIA3Exception e->" + e);
                    mensajeDTO = negocioMensaje.mensajePorCodigo(e.getMessage());
                }
            } catch (SIA3Exception se1) {
                mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP000003_CODE, MessagesConstants.APP000003);
            }
            logger.error("Error en metodo importarRol");
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en importarRol:" + e.getCause());
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesConstants.APP000003).build();
        }
    }

}
