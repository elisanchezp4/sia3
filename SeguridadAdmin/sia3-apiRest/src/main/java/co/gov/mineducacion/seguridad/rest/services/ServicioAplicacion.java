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

import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;

/**
 * Servicios REST para la entidad Aplicacion
 *
 * @author gvalverde
 */
@Stateless
@Path("/servicios/aplicacion")
public class ServicioAplicacion {
    /**
     * Imprimir logs
     */
    private static final Logger logger = Logger.getLogger(ServicioAplicacion.class.getName());

    @Inject
    private NegocioAplicaciones negocioAplicacion;

    @Inject
    private NegocioMensaje negocioMensaje;

    /**
     * Metodo para consultar todas las entidades Aplicacion registradas en la
     * base de datos
     *
     * @return Response OK o Error(BAD_REQUEST)
     */


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

    @GET
    @Path("/getAllAplicaciones")
    @Produces({APPLICATION_JSON})
    public Response getAllAplicaciones(@QueryParam("estado") Long estado) {
        try {
            logger.info("Inicio servicio metodo getAllAplicaciones. Filtro estado:" + estado);
            List<AplicacionesDTO> aplicaciones = negocioAplicacion.getAllAplicaciones(estado);
            return Response.ok(aplicaciones).build();
        } catch (SIA3Exception se) {
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(se);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en getAllAplicaciones:" + e.getCause());
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    /**
     * Servico REST para crear una nueva aplicacion
     *
     * @param aplicacionDTO
     * @return Response OK o Error(BAD_REQUEST)
     * @throws SIA3Exception
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response crearAplicacion(AplicacionesDTO aplicacionDTO) {
        logger.info("Inicio metodo crearAplicacion con aplicacionDTO =>" + aplicacionDTO);
        MensajeDTO mensajeDTO = new MensajeDTO();
        try {
            negocioAplicacion.crear(aplicacionDTO);
            logger.info("Finalizó el metodo crearAplicacion");
            mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100001);
            return Response.status(Response.Status.OK).entity(mensajeDTO).build();
        } catch (SIA3Exception se) {
            mensajeDTO = obtenerMensajeDTOFromException(se);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en crearAplicacion:" + e.getCause());
            mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    /**
     * Servicio REST para editar una aplicacion existente
     *
     * @param aplicacionDTO
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response editarAplicacion(AplicacionesDTO aplicacionDTO) {
        logger.info("Inicio metodo editarAplicacion con aplicacionDTO =>" + aplicacionDTO);
        MensajeDTO mensajeDTO = new MensajeDTO();
        try {
            mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100004);
            negocioAplicacion.actualizar(aplicacionDTO);
            logger.info("Finalizó el metodo editarAplicacion");
            return Response.status(Response.Status.OK).entity(mensajeDTO).build();
        }catch (SIA3Exception se) {
            mensajeDTO = obtenerMensajeDTOFromException(se);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en editarAplicacion:" + e.getCause());
            mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    /**
     * Servicio REST para eliminar una aplicacion existente
     *
     * @param aplicacionId
     * @param usuarioId
     * @return
     */
    @DELETE
    @Path("/eliminarAplicacion")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response eliminarAplicacion(@QueryParam("aplicacionId") BigDecimal aplicacionId, @QueryParam("usuarioId") String usuarioId) {
        logger.info("Inicio metodo eliminarAplicacion con aplicacionId =>" + aplicacionId);
        MensajeDTO mensajeDTO = new MensajeDTO();
        try {
            mensajeDTO = negocioMensaje.mensajePorCodigo(MessagesConstants.APP100006);
            negocioAplicacion.eliminarAplicacion(aplicacionId, usuarioId);
            logger.info("Finalizó el metodo eliminarAplicacion");
            return Response.status(Response.Status.OK).entity(mensajeDTO).build();
        } catch (SIA3Exception e) {
            mensajeDTO = obtenerMensajeDTOFromException(e);
            if (e.getMessage().equals(MessagesConstants.APP100119)) {
                mensajeDTO = negocioMensaje.getMensajeDTOError(MessagesConstants.APP100119_CODE, MessagesConstants.APP100119);
            }
            logger.error("Error en metodo eliminarAplicacion");
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en eliminarAplicacion:" + e.getCause());
            mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    /**
     * Servicio para contar las aplicaciones registradas en Base Datos
     *
     * @return
     * @throws SIA3Exception
     */
    @GET
    @Path("/contarAplicaciones")
    @Produces({APPLICATION_JSON})
    public Response contarAplicaciones() throws SIA3Exception {
        try {
            logger.info("Inicio metodo contarAplicaciones");
            Long cantidadAplicaciones = negocioAplicacion.contarAplicaciones();
            if (cantidadAplicaciones.equals(0)) {
                logger.error("Error en metodo contarAplicaciones: No hay aplicaciones");
                throw new SIA3Exception(MessagesConstants.APP100012);
            }
            return Response.ok(cantidadAplicaciones).build();
        } catch (SIA3Exception se) {
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(se);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en servicio getUsuarioPorApp:" + e);
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    /**
     * Metodo para consultar todas las entidades Aplicacion registradas en la
     * base de datos con un usuario especifico
     *
     * @return Response OK o Error(BAD_REQUEST)
     */
    @GET
    @Path("/getAplicacionesPorUsuario")
    @Produces({APPLICATION_JSON})
    public Response getAplicacionesPorUsuario(@QueryParam("estado") Long estado, @QueryParam("usuarioId") BigDecimal usuarioId) {
        try {
            logger.info("Inicio servicio metodo getAplicacionesPorUsuario con el usuario: " + usuarioId);
            List<AplicacionesDTO> aplicaciones = negocioAplicacion.getAplicacionesPorUsuario(estado, usuarioId);
            return Response.ok(aplicaciones).build();
        } catch (SIA3Exception se) {
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(se);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en getAllAplicaciones:" + e.getCause());
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    /**
     * Metodo para consultar todas las entidades Aplicacion registradas en la
     * base de datos por nombre
     *
     * @return Response OK o Error(BAD_REQUEST)
     */
    @GET
    @Path("/getAplicacionesNombre")
    @Produces({APPLICATION_JSON})
    public Response getAplicacionesNombre(@QueryParam("estado") Long estado, @QueryParam("nombre") String nombre) {
        try {
            logger.info("Inicio servicio metodo getAplicacionesNombre. Filtro estado: " + estado + " " + "Filtro estado: " + nombre);
            List<AplicacionesDTO> aplicaciones = negocioAplicacion.getAplicacionesNombre(estado, nombre);
            return Response.ok(aplicaciones).build();
        }  catch (SIA3Exception se) {
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(se);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en getAplicacionesNombre:" + e.getCause());
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    @GET()
    @Produces({APPLICATION_JSON})
    @Path("/getAllCamposActivos")
    public Response getAllCamposActivos() {
        try {
            List<String> camposActivos = negocioAplicacion.consultarListadoDirectorioActivo();
            return Response.ok(camposActivos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(MessagesConstants.APP000003).build();
        }
    }
}