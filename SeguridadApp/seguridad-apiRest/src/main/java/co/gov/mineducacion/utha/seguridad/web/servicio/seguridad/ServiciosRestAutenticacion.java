package co.gov.mineducacion.utha.seguridad.web.servicio.seguridad;

import co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion;
import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import co.gov.mineducacion.seguridad.modelo.enums.CamposLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuariosRol;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import co.gov.mineducacion.seguridad.modelo.utils.UtilEmail;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.negocio.NegocioRoles;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.InformacionTokenDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.UsuariosRolesDto;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.PeticionAutenticacionDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.salida.Respuesta;
import co.gov.mineducacion.utha.seguridad.web.servicio.utils.dto.UtilsDTO;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ERROR_DATOS_NULOS;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ERROR_SOLO_UN_VALOR;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ID_ERROR_DATOS_NULOS;
import static co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil.PASSWORD_PATTERN;
import static co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil.validarMaximoCampo;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


/**
 * Expone los servicios REST disponibles para realizar operaciones de autenticación de usuarios sobre el sistema de seguridad
 *
 * @author Asesoftware - Javier Estévez
 */
@Stateless
@Path("servicios/autenticacion")
public class ServiciosRestAutenticacion {

    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(ServiciosRestAutenticacion.class.getName());
    private static final String MSG_USUARIO_NO_VALIDO = "Usuario no existe o no es valido. ";
    private static final String APP000003 = "APP000003";
    public static final String ERROR_NOT_ALLOWED_APP = "Error: El usuario no esta relacionado con la aplicación.";

    @EJB
    private NegocioUsuarios negocioUsuario;

    @EJB
    private NegocioMensaje negocioMensaje;

    @EJB
    private NegocioRoles negocioRoles;

    @EJB
    private IUsuarios servicioUsuarios;

    @EJB
    private NegocioUsuariosRol negocioUsuariosRol;

    @EJB
    private IServicioAutenticacion servicioAutenticacion;

    @EJB
    private ManejadorUsuariosRol manejadorUsuariosRol;

    @EJB
    private ParametrosSng parametrosSng;

    @EJB
    protected NegocioAplicaciones negocioAplicacion;


    private MensajeDTO obtenerMensajeDTOFromException(Exception e) {
        try {
            return negocioMensaje.mensajePorCodigo(e.getMessage());
        } catch (SIA3Exception se1) {
            MensajeDTO mensajeDTO = new MensajeDTO();
            mensajeDTO.setCodigo(APP000003);
            mensajeDTO.setTipoMensaje(MessagesConstants.TIPO_MENSAJE_ERROR);
            mensajeDTO.setDescripcion(MessagesConstants.APP000003);
            return mensajeDTO;
        }
    }


    /**
     * Retorna el token con el cual un usuario identifica la sesión en el sistema de seguridad.
     *
     * @param codigoAutorizacion token inicial retornado al autenticarse
     * @param clientId           identificador de la aplicación en el sistema de seguridad
     * @param userId             identificador del usuario en el sistema de seguridad
     * @return
     * @throws SeguridadException
     */
    @GET
    @Produces({APPLICATION_JSON})
    @Path("obtenertoken")
    public InformacionTokenDTO obtenerToken(@QueryParam("codigo_autorizacion") String codigoAutorizacion,
                                            @QueryParam("client_id") String clientId, @QueryParam("user_id") String userId) throws SeguridadException {
        logger.info("ObtenerToken--->");
        if (codigoAutorizacion == null
                || codigoAutorizacion.trim().isEmpty()
                || clientId == null
                || clientId.trim().isEmpty()
                || userId == null
                || userId.trim().isEmpty()) {

            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
        }

        TokensActivosDTO tokenAcceso = null;
        InformacionTokenDTO resultado = null;


        servicioUsuarios.verificarUsuarioAdministrador(Integer.valueOf(userId), Constantes.ROL_AUTENTICADOR);

        tokenAcceso = servicioAutenticacion.obtenerToken(codigoAutorizacion, Integer.valueOf(userId), clientId);
        logger.info("TokenAcceso--->");

        resultado = new InformacionTokenDTO();
        resultado.setFechaExpiracion(tokenAcceso.getVencimiento());
        resultado.setTokenAcceso(tokenAcceso.getTokenId());

        return resultado;

    }


    /**
     * Finaliza la sesión de un usuario en el sistema de seguridad
     *
     * @param peticion
     * @throws SeguridadException
     */
    @POST
    @Consumes({APPLICATION_JSON})
    @Path("finalizarsesion")
    public void finalizarSesion(PeticionAutenticacionDTO peticion) throws co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException {

        UtilsDTO.validarPeticionAutenticacion(peticion);

        servicioUsuarios.verificarUsuarioAdministrador(peticion.getHeader().getUserId(), Constantes.ROL_AUTENTICADOR);
        servicioAutenticacion.finalizarSesion(peticion.getTokenAcceso(), peticion.getHeader().getUserId(), peticion.getHeader().getApiKey());

    }

    @POST
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("actualizaremail")
    public Response actualizarEmail(PeticionAutenticacionDTO peticion) {
        List<String> msgError = new ArrayList<>();
        msgError.add(validadorCamposMaxLdap("correo", peticion.getHeader().getEmail(), CamposLdap.MAIL, Constantes.VAL_EMAIL_MAX));
        if (peticion.getHeader().getNotificarUsuario() == null) {
            msgError.add("Error: " + Constantes.ERROR_NOTIFICA_USUARIO);
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); // NOSONAR
        Matcher mather = pattern.matcher(peticion.getHeader().getEmail());
        if (!mather.find()) {
            msgError.add("Error: El correo ingresado no tiene el formato adecuado");
        }

        List<String> errores = msgError.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (!errores.isEmpty()){
            return Response.ok(new Respuesta(Constantes.ID_ERROR_DATOS_NO_VALIDOS, Constantes.ERROR_DATOS_NO_VALIDOS + ": " + errores)).status(422).build();
        }

        UsuariosDTO usuariosDTO = negocioUsuario.buscarUsuario(String.valueOf(peticion.getHeader().getUserId()));
        if (usuariosDTO == null) {
            logger.warn(MSG_USUARIO_NO_VALIDO + peticion.getHeader().getUserId());
            return Response.ok(new Respuesta(400, MSG_USUARIO_NO_VALIDO)).status(400).build();
        }

        try {
            servicioAutenticacion.actualizarFechaVencimientoToken(peticion.getTokenAcceso(), peticion.getHeader().getUserId(), peticion.getHeader().getApiKey());
            List<UsuariosDTO> usuariosBDList = negocioUsuario.getUsuarioPorAppExiste(BigDecimal.valueOf(peticion.getHeader().getUserToModify()), peticion.getHeader().getApiKey());
            if (usuariosBDList.isEmpty()) {
                return Response.ok(new Respuesta(400, ERROR_NOT_ALLOWED_APP)).status(400).build();
            }
            //BUG 15: Se cambia el usuario a modificar es el parametro de userToModify no el que tiene asignado el Token
            servicioUsuarios.actualizarEmail(String.valueOf(peticion.getHeader().getUserToModify()), peticion.getHeader().getEmail(), peticion.getHeader().getApiKey(), String.valueOf(peticion.getHeader().getUserId()));

            UsuariosDTO userToModify = negocioUsuario.buscarUsuario(peticion.getHeader().getUserToModify().toString());
            AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(peticion.getHeader().getApiKey());
            UtilEmail.enviarEmail(peticion.getHeader().getNotificarUsuario(), userToModify, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
            return Response.ok(true).build();
        } catch (SeguridadException e) {
            return Response.ok(new Respuesta(401, "Token no valido o no puede ser procesado")).status(401).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al actualizar el correo").build();
        }
    }

    @POST
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("actualizarpassword")
    public Response actualizarPassword(PeticionAutenticacionDTO peticion,
                                       @HeaderParam("access_token") String headerToken, @HeaderParam("client_id") String clientId, @HeaderParam("user_id") Integer userId) throws SeguridadException {


        if (peticion.getTokenAcceso() != null && !peticion.getTokenAcceso().isEmpty()) {
            return Response.ok(new Respuesta(400, "No es necesario enviar el token en el cuerpo de la solicitud")).status(400).build();
        }

        String token = headerToken;

        boolean contrasenaNoValida = false;
        List<String> errores = new ArrayList<>();

        logger.info("Inicia proceso para actualizar contraseña");
        if (peticion.getHeader().getUserId() == null || peticion.getHeader().getUserId().equals(0)) {
            return Response.ok(new Respuesta(400, "UsuarioId no puede ser nulo o cero")).status(400).build();
        }

        UsuariosDTO usuario = negocioUsuario.buscarUsuario(peticion.getHeader().getUserId().toString());
        if (usuario == null) {
            logger.warn("actualizarpassword Usuario enviando no existe: " + peticion.getHeader().getUserId().toString());
            return Response.ok(new Respuesta(Constantes.ID_ERROR_USER_NO_EXISTS, Constantes.ERROR_USER_NO_EXISTS)).status(422).build();
        }

        UsuariosDTO usuariosModificarDTO = negocioUsuario.buscarUsuario(peticion.getHeader().getUserToModify().toString());
        if (usuariosModificarDTO == null) {
            logger.warn("actualizarpassword Usuario enviando no existe: userToModify: " + peticion.getHeader().getUserId().toString());
            return Response.ok(new Respuesta(Constantes.ID_ERROR_USER_NO_EXISTS, Constantes.ERROR_USER_NO_EXISTS)).status(422).build();
        }

        if (peticion.getHeader().getApiKey() == null) {
            return Response.ok(new Respuesta(400, "ApiKey no puede ser nulo o vacio")).status(400).build();
        }
        Matcher mather = PASSWORD_PATTERN.matcher(peticion.getHeader().getPassword());
        if(!mather.find()){
            errores.add("Error: La contraseña no tiene un formato valido, tamaño mínimo permitido es de 8 caracteres y debe contener caracteres especiales ($@!#%*-_?&), números, mayúsculas y minusculas");
            contrasenaNoValida = true;
        }
        if(peticion.getHeader().getPassword() == null || peticion.getHeader().getPassword().isEmpty()){
            errores.add("Error: La contaseña ingresada no debe ser nula o vacia");
            contrasenaNoValida = true;
        }
        if(contrasenaNoValida) {
            return Response.ok(new Respuesta(Constantes.ID_ERROR_CONTRASENA_NO_VALIDA, Constantes.ERROR_CONTRASENA_NO_VALIDA + ": " + errores)).status(422).build();
        }

        servicioAutenticacion.actualizarFechaVencimientoToken(token, userId, clientId);

        try {
            servicioAutenticacion.modificarPassword(token, peticion.getHeader().getUserToModify(), peticion.getHeader().getPassword(), peticion.getHeader().getApiKey(), String.valueOf(userId));
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_EXITOSA, Constantes.OPERACION_EXITOSA)).status(200).build();
        } catch (SeguridadException e) {

            if(e.getMessage().equals(Constantes.ERROR_USUARIO_NO_EXTERNO)){
                return Response.ok(new Respuesta(Constantes.ID_ERROR_USUARIO_NO_EXTERNO, e.getMessage())).status(422).build();
            }
            logger.error("Error al actualizar password: " + e.getMessage());
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_ERROR, Constantes.OPERACION_ERROR + " " + e.getDetalle())).status(500).build();
        } catch (Exception e) {
            logger.error("Error al actualizar password: " + e.getMessage());
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_ERROR, Constantes.OPERACION_ERROR + " " + e.getMessage())).status(500).build();
        }
    }

    @POST
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("actualizardatosbasicos")
    public Response actualizarDatosBasicos(PeticionAutenticacionDTO peticion,
                                           @HeaderParam("access_token") String token, @HeaderParam("client_id") String clientId, @HeaderParam("user_id") Integer userId) throws SeguridadException, SIA3Exception {
        logger.info("Inicia proceso para actualizar datos basicos: " + new Gson().toJson(peticion));

        List<Respuesta> respError = new ArrayList<>();
        respError.add(validadorCamposNulos(peticion.getHeader().getUserId(), Constantes.ID_ERROR_USER_NULL, Constantes.ERROR_USER_NULL));
        respError.add(validadorCamposNulos(peticion.getHeader().getUserToModify(), Constantes.ID_ERROR_USER_NULL, Constantes.ERROR_USER_NULL));
        respError.add(validadorCamposNulos(peticion.getHeader().getNotificarUsuario(), Constantes.ID_ERROR_NOTIFICA_USUARIO, Constantes.ERROR_NOTIFICA_USUARIO));
        respError.add(validadorCamposNulos(peticion.getHeader().getApiKey(), Constantes.ID_ERROR_APLICACION_NULL, Constantes.ERROR_APLICACION_NULL));
        Respuesta respuestaError = respError.stream().filter(Objects::nonNull).findFirst().orElse(null);
        if (respuestaError != null){
            return Response.ok(new Respuesta(respuestaError.getCodigo(), respuestaError.getMensaje())).status(422).build();
        }

        List<String> msgError = new ArrayList<>();
        msgError.add(validadorCamposMaxLdap("apellido", peticion.getHeader().getApellidos(), CamposLdap.SURNAME, Constantes.VAL_SN_GIVENAME_MAX));
        msgError.add(validadorCamposMaxLdap("nombre", peticion.getHeader().getNombres(), CamposLdap.GIVE_NAME, Constantes.VAL_SN_GIVENAME_MAX));
        msgError.add(validadorCamposMaxLdap("numero de documento", peticion.getHeader().getNumeroDocumento(), CamposLdap.DESCRIPTION, Constantes.VAL_DESCRIPTION_MAX));
        msgError.add(validadorCamposMaxLdap("ruta del directorio", peticion.getHeader().getRutaDirectorio(), CamposLdap.DESCRIPTION, Constantes.VAL_DESCRIPTION_MAX));
        List<String> errores = msgError.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (!errores.isEmpty()){
            return Response.ok(new Respuesta(Constantes.ID_ERROR_DATOS_NO_VALIDOS, Constantes.ERROR_DATOS_NO_VALIDOS + ": " + errores)).status(422).build();
        }

        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setApellidosUsuario(peticion.getHeader().getApellidos());
        usuario.setNombres(peticion.getHeader().getNombres());
        usuario.setNumeroDocumento(peticion.getHeader().getNumeroDocumento());
        usuario.setRuta(peticion.getHeader().getRutaDirectorio());

        UsuariosDTO usuariosDTO = negocioUsuario.buscarUsuario(peticion.getHeader().getUserId().toString());
        if (usuariosDTO == null) {
            logger.warn("actualizardatosbasicos Usuario enviando no existe: userId: " + peticion.getHeader().getUserId().toString());
            return Response.ok(new Respuesta(Constantes.ID_ERROR_USER_NO_EXISTS, Constantes.ERROR_USER_NO_EXISTS)).status(422).build();
        }

        UsuariosDTO usuariosModificarDTO = negocioUsuario.buscarUsuario(peticion.getHeader().getUserToModify().toString());
        if (usuariosModificarDTO == null) {
            logger.warn("actualizardatosbasicos Usuario enviando no existe: userToModify: " + peticion.getHeader().getUserId().toString());
            return Response.ok(new Respuesta(Constantes.ID_ERROR_USER_NO_EXISTS, Constantes.ERROR_USER_NO_EXISTS)).status(422).build();
        }

        List<UsuariosDTO> usuariosBDList = negocioUsuario.getUsuarioPorAppExiste(BigDecimal.valueOf(peticion.getHeader().getUserToModify()), peticion.getHeader().getApiKey());
        if (usuariosBDList.isEmpty()) {
            return Response.ok(new Respuesta(400, ERROR_NOT_ALLOWED_APP)).status(400).build();
        }

        try {
            List<RolesDTO> rolDTOList = negocioRoles.getRolesPorUsuarioIdAplicacionId(Long.valueOf(peticion.getHeader().getUserToModify()), new BigDecimal(peticion.getHeader().getApiKey()));
            boolean rolVinculado = rolDTOList.stream().anyMatch(usuarioRol -> usuarioRol.getEstado().toString().equals("1"));

            if(!rolVinculado) {
                return Response.ok(new Respuesta(Constantes.ID_ERROR_USUARIO_NO_VINCULADO, Constantes.ERROR_USUARIO_NO_VINCULADO)).status(422).build();
            }
        } catch (SIA3Exception e) {
            return Response.ok(new Respuesta(Constantes.ID_ERROR_USUARIO_NO_VINCULADO, Constantes.ERROR_USUARIO_NO_VINCULADO)).status(422).build();
        }

        servicioAutenticacion.actualizarFechaVencimientoToken(token, userId, clientId);
        try {
            servicioUsuarios.actualizarDatosBasicos(usuario, peticion.getHeader().getApiKey(), peticion.getHeader().getUserToModify().toString(), String.valueOf(userId));
            AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(peticion.getHeader().getApiKey());
            UtilEmail.enviarEmail(peticion.getHeader().getNotificarUsuario(), usuariosModificarDTO, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_EXITOSA, Constantes.OPERACION_EXITOSA)).status(200).build();
        } catch (Exception e) {
            logger.error("Error actualizando datos basicos: " + new Gson().toJson(e));
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_ERROR, Constantes.OPERACION_ERROR + " " + e.getMessage())).status(500).build();
        }
    }

    @GET
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("vincularroles")
    public Response vincularRoles(@QueryParam("aplicacionid") BigDecimal aplicacionid, @QueryParam("usuarioid") Long usuarioId,
                                  @QueryParam("nombreUsuario") String nombreUsuario, @QueryParam("correoElectronico") String correoElectronico,
                                  @QueryParam("roles") List<String> roles, @QueryParam("notificarUsuario") Boolean notificarUsuario,
                                  @HeaderParam("access_token") String token, @HeaderParam("client_id") String clientId, @HeaderParam("user_id") Integer userId) throws SeguridadException {
        logger.info("Inicia vincular roles");
        List<String> msnError = new ArrayList<>();

        List<Respuesta> respError = new ArrayList<>();
        respError.add(validadorCamposNulos(aplicacionid, Constantes.ID_ERROR_APLICACION_NULL, Constantes.ERROR_APLICACION_NULL));
        respError.add(validadorCamposNulos(notificarUsuario, Constantes.ID_ERROR_NOTIFICA_USUARIO, Constantes.ERROR_NOTIFICA_USUARIO));
        respError.add(validadorCamposNulos(roles, Constantes.ID_ERROR_ROLES_NULL, Constantes.ERROR_ROLES_NULL));

        Respuesta respuestaError = respError.stream().filter(Objects::nonNull).findFirst().orElse(null);
        if (respuestaError != null){
            return Response.ok(new Respuesta(respuestaError.getCodigo(), respuestaError.getMensaje())).status(422).build();
        }

        servicioAutenticacion.actualizarFechaVencimientoToken(token, userId, clientId);

        UsuariosDTO usuariosDTO = buscarUsuario(String.valueOf(usuarioId), nombreUsuario, correoElectronico);

        if (usuariosDTO == null) {
            logger.error(MSG_USUARIO_NO_VALIDO + " Criterios: ID=" + usuarioId + ", Nombre=" + nombreUsuario + ", Email=" + correoElectronico);
            return Response.ok(new Respuesta(Constantes.ID_ERROR_USER_NO_EXISTS, Constantes.ERROR_USER_NO_EXISTS)).status(422).build();
        }

        try {
            List<UsuariosRol> usuariosRoles = manejadorUsuariosRol.buscarUsuariosRolXUsuarioApp(usuarioId.toString(), aplicacionid);
            for (String rol : roles) {
                Roles existe = negocioRoles.consultarRolePorNombreYAplicacion(rol, aplicacionid);
                logger.info("Validacion de rol respuesta: " + existe + " ,rol: " + rol);

                if (existe != null && existe.getEstado().equals(new BigDecimal(1))) {

                    boolean rolVinculado = usuariosRoles.stream()
                            .anyMatch(usuarioRol -> usuarioRol.getUsuariosRolPK().getRolId().equals(existe.getRolId()));
                    if (rolVinculado) {
                        logger.warn(Constantes.ERROR_YA_VINCULADO + "rol: " + rol + " usuario: " + usuarioId + " aplicación: " + aplicacionid);
                        msnError.add(rol);
                        continue;
                    }

                    BigDecimal rolId = existe.getRolId();

                    List<UsuariosDTO> usuariosDTOS = new ArrayList<>();
                    usuariosDTOS.add(usuariosDTO);
                logger.info("parametros desde el controlador " + usuariosDTOS + " rol: " + existe.getRolId());
                    negocioUsuariosRol.agregarUsuariosARol(usuariosDTOS, rolId, String.valueOf(userId));
                } else {
                    logger.warn("No se puede vincular por que rol no existe o no esta asociado a la aplicacion rol: " + rol + " aplicacion: " + aplicacionid);
                    msnError.add(rol);
                }
            }

            if (msnError.size() == roles.size()) {
                return Response.ok(new Respuesta(Constantes.ID_ERROR_ROL_INVALIDO, Constantes.ERROR_ROL_INVALIDO + msnError)).status(422).build();
            }
            AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(aplicacionid.toString());
            UtilEmail.enviarEmail(notificarUsuario, usuariosDTO, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_EXITOSA, Constantes.OPERACION_EXITOSA)).status(200).build();
        } catch (SeguridadException e) {
            logger.error("Error vinculando usuario rol: " + e);
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_ERROR, Constantes.OPERACION_ERROR + " " + e.getDetalle())).status(500).build();
        } catch (SIA3Exception e) {
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_ERROR, Constantes.OPERACION_ERROR + " " + e.getMessage())).status(500).build();
        }
    }

    @GET
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("desvincularroles")
    public Response desvincularRoles(@QueryParam("aplicacionid") BigDecimal aplicacionid, @QueryParam("usuarioid") Long usuarioId,
                                     @QueryParam("nombreUsuario") String nombreUsuario, @QueryParam("correoElectronico") String correoElectronico,
                                     @QueryParam("roles") List<String> roles, @QueryParam("notificarUsuario") Boolean notificarUsuario,
                                     @QueryParam("motivoDesvinculacion") String motivoDesvinculacion,
                                     @HeaderParam("access_token") String token, @HeaderParam("client_id") String clientId, @HeaderParam("user_id") Integer userId) throws SeguridadException {
        logger.info("Inicia comando para desvincular roles: " + roles);

        List<Respuesta> msnError = new ArrayList<>();
        msnError.add(validadorCamposNulos(roles, Constantes.ID_ERROR_ROLES_NULL, Constantes.ERROR_ROLES_NULL));
        msnError.add(validadorCamposNulos(aplicacionid, Constantes.ID_ERROR_APLICACION_NULL, Constantes.ERROR_APLICACION_NULL));
        msnError.add(validadorCamposNulos(notificarUsuario, Constantes.ID_ERROR_NOTIFICA_USUARIO, Constantes.ERROR_NOTIFICA_USUARIO));

        Respuesta respuestaError = msnError.stream().filter(Objects::nonNull).findFirst().orElse(null);
        if (respuestaError != null){
            return Response.ok(new Respuesta(respuestaError.getCodigo(), respuestaError.getMensaje())).status(422).build();
        }

        UsuariosDTO usuariosDTO = buscarUsuario(String.valueOf(usuarioId), nombreUsuario, correoElectronico);

        if (usuariosDTO == null) {
            logger.error(MSG_USUARIO_NO_VALIDO + " Criterios: ID=" + usuarioId + ", Nombre=" + nombreUsuario + ", Email=" + correoElectronico);
            return Response.ok(new Respuesta(Constantes.ID_ERROR_USER_NO_EXISTS, Constantes.ERROR_USER_NO_EXISTS)).status(422).build();
        }

        servicioAutenticacion.actualizarFechaVencimientoToken(token, userId, clientId);

        List<String> listMsnValidacion = new ArrayList<>();
        List<String> rolesDesvincular = new ArrayList<>();

        try {
            List<RolesDTO> rolesDTOS = negocioRoles.getRolesPorUsuario(usuarioId);
            for (String rol : roles) {
                Roles existe = negocioRoles.consultarRolePorNombreYAplicacion(rol, aplicacionid);
                logger.info("Validacion de rol respuesta: " + existe + " ,rol: " + rol);
                boolean error = false;
                if (existe != null && existe.getEstado().equals(new BigDecimal(0))) {
                    logger.info(Constantes.ERROR_YA_DESVINCULADO + "rol: " + rol + " usuario: " + usuarioId + " aplicación: " + aplicacionid);
                    listMsnValidacion.add(rol);
                    error = true;
                }
                if (!error && rolesDTOS.stream().noneMatch(rolesDTO -> isValidRol(rol, rolesDTO))) {
                    logger.warn("No se puede desvincular por que rol no existe o no esta asociado a la aplicacion rol: " + rol + " aplicacion: " + aplicacionid);
                    listMsnValidacion.add(rol);
                    error = true;
                }
                if(error)
                    continue;
                rolesDesvincular.add(rol);
            }
            if (listMsnValidacion.size() == roles.size()) {
                return Response.ok(new Respuesta(Constantes.ID_ERROR_ROL_INVALIDO, Constantes.ERROR_ROL_INVALIDO + listMsnValidacion)).status(422).build();
            }
            servicioUsuarios.desvincularRolesUsuario(rolesDesvincular, String.valueOf(usuarioId), String.valueOf(aplicacionid), String.valueOf(userId), motivoDesvinculacion);
            AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(aplicacionid != null ? aplicacionid.toString() : null);
            UtilEmail.enviarEmail(Boolean.TRUE.equals(notificarUsuario), usuariosDTO, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_EXITOSA, Constantes.OPERACION_EXITOSA)).status(200).build();
        } catch (SeguridadException e) {
            logger.error("Error desvinculando usuario rol: " + e);
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_ERROR, Constantes.OPERACION_ERROR + " " + e.getDetalle())).status(500).build();
        } catch (SIA3Exception e) {
            return Response.ok(new Respuesta(Constantes.ID_OPERACION_ERROR, Constantes.OPERACION_ERROR + " " + e.getMessage())).status(500).build();
        }
    }

    private static boolean isValidRol(String rol, RolesDTO rolesDTO) {
        return rolesDTO.getNombre().contains(rol) && rolesDTO.getEstado().equals(new BigDecimal(1));
    }

    /**
     * Retorna los permisos que tiene el usuario (que realiza la solicitud) sobre la aplicación (normalmente las opciones de menú).
     *
     * @param peticion
     * @return
     * @throws SeguridadException
     */
    @POST
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("obtenerrolespermisos")
    public Response obtenerRolesPermisos(PeticionAutenticacionDTO peticion) {
        if (peticion.getHeader().getUserId() == null) {
            return Response.ok(new Respuesta(400, "UsuarioId no puede ser nulo")).status(400).build();
        }
        if (peticion.getHeader().getApiKey() == null) {
            return Response.ok(new Respuesta(400, "ApiKey no puede ser nulo")).status(400).build();
        }
        UsuariosDTO usuariosDTO = negocioUsuario.buscarUsuario((peticion.getHeader().getUserId().toString()));
        if (usuariosDTO == null) {
            return Response.ok(new Respuesta(400, MSG_USUARIO_NO_VALIDO)).status(400).build();
        }
        try {
            servicioAutenticacion.actualizarFechaVencimientoToken(peticion.getTokenAcceso(), peticion.getHeader().getUserId(), peticion.getHeader().getApiKey());
        } catch (SeguridadException e) {
            return Response.ok(new Respuesta(401, "Token invalido o no puede ser procesado")).status(401).build();
        }
        try {
            UtilsDTO.validarPeticionAutenticacion(peticion);

            servicioUsuarios.verificarUsuarioAdministrador(peticion.getHeader().getUserId(), Constantes.ROL_AUTENTICADOR);

            OperacionesRolDTO rolesPermisosWS = servicioAutenticacion.obtenerRolesYPermisos(peticion.getHeader().getUserId(), peticion.getHeader().getApiKey(), peticion.getTokenAcceso());

            return Response.ok(UtilsDTO.obtenerDTO(rolesPermisosWS, peticion.getTokenAcceso())).build();
        } catch (SeguridadException e) {
            return Response.ok(new Respuesta(500, e.getMessage())).status(401).build();
        }
    }

    @GET
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    @Path("pruebaServicio")
    public String prueba() {
        return "BIEN Friend";
    }

    @GET
    @Produces({APPLICATION_JSON})
    @Consumes({APPLICATION_JSON})
    @Path("getUsuariosYRolesPorApp")
    public Response getUsuariosYRolesPorApp(@QueryParam("idAplicacion") BigDecimal idAplicacion,
                                            @QueryParam("pagInicio") Integer pagInicio,
                                            @QueryParam("pagFin") Integer pagFin,
                                            @QueryParam("records") List<String> records,
                                            @HeaderParam("access_token") String token,
                                            @HeaderParam("client_id") String clientId,
                                            @HeaderParam("user_id") Integer userId) {
        logger.info("Inicia consulta usuarios y roles por aplicacionId: " + idAplicacion + " ,records: " + new Gson().toJson(records));
        logger.info("token: " + token + "  cliente_id: " + clientId + " userId: " + userId);
        if (token == null) {
            return Response.ok(new Respuesta(401, "token no puede ser nulo")).status(401).build();
        }
        if (clientId == null || clientId.isEmpty()) {
            return Response.ok(new Respuesta(401, "clienteId no puede ser nulo")).status(401).build();
        }
        if (userId == null) {
            return Response.ok(new Respuesta(401, "userId no puede ser nulo")).status(401).build();
        }
        if (idAplicacion == null) {
            return Response.ok(new Respuesta(400, "idAplicacion no puede ser nulo")).status(400).build();
        }
        if (records.isEmpty()) {
            return Response.ok(new Respuesta(400, "records no puede ser nulo")).status(400).build();
        }
        UsuariosDTO usuariosDTO = negocioUsuario.buscarUsuario(String.valueOf(userId));
        if (usuariosDTO == null) {
            return Response.ok(new Respuesta(400, MSG_USUARIO_NO_VALIDO)).status(400).build();
        }
        try {
            servicioAutenticacion.actualizarFechaVencimientoToken(token, userId, clientId);
        } catch (Exception e) {
            logger.error("Error validando seguridad: " + e);
            return Response.ok(new Respuesta(401, "Token invalido o no puede ser procesado")).status(401).build();
        }
        try {
            UsuariosRolesDto usuariosRolesDto = new UsuariosRolesDto();
            if (records.contains("users")) {
                logger.info("Ingresa por la opcion users");
                List<UsuariosDTO> usuarios = negocioUsuario.getUsuariosPorApp(idAplicacion);
                usuariosRolesDto.setUsuarios(usuarios);
            }
            if (records.contains("all_roles")) {
                logger.info("Ingresa por la opcion all_roles");
                List<RolesDTO> roles = negocioRoles.getRolesPorAplicacion(idAplicacion.longValue());
                usuariosRolesDto.setRoles(roles);
            }
            return Response.ok(usuariosRolesDto).build();
        } catch (Exception e) {
            logger.error("Error consultando usuarios y roles por app: " + e.getMessage());
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/getUsuariosPorAppNombreRol")
    @Produces({APPLICATION_JSON})
    public Response getUsuariosPorAppNombreRol(@QueryParam("opcionId") BigDecimal opcionId,
                                               @QueryParam("rol") String rol,
                                               @QueryParam("nombres") String nombres, @QueryParam("estado") Long estado,
                                               @QueryParam("idUsuario") Long idUsuario, @QueryParam("pagInicio") Integer pagInicio,
                                               @QueryParam("pagFin") Integer pagFin) {
        try {
            logger.info("Inicio metodo getUsuariosPorAppNombreRol con parametro opcionId:" + opcionId + " y rol:" + rol);
            List<UsuariosDTO> usuariosList = negocioUsuario.getUsuariosPorAppNombreRol(opcionId, rol, estado, idUsuario, pagInicio, pagFin);

            if (nombres != null) {
                List<UsuariosDTO> usuariosEncontrados = new ArrayList<>();
                for (UsuariosDTO usuarioFiltro : usuariosList) {
                    if (usuarioFiltro.getNombres().contains(nombres)) {
                        setEmail(usuarioFiltro, usuarioFiltro.getLogonName());
                        usuariosEncontrados.add(usuarioFiltro);
                    }
                }
                usuariosList = usuariosEncontrados;
            } else {
                for (UsuariosDTO usuarioDTO : usuariosList) {
                    setEmail(usuarioDTO, usuarioDTO.getLogonName());
                }
            }
            return Response.ok(usuariosList).build();
        } catch (SIA3Exception e) {
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Error inesperado en servicio getOperacionesPorAplicacion");
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    private void setEmail(UsuariosDTO usuarioDTO, String logonName) throws SeguridadException, SIA3Exception {
        UsuariosDTO usuarioAD = negocioUsuario.completarInformacionUsuario(logonName);
        if (usuarioAD != null) {
            usuarioDTO.setEmailUsuario(usuarioAD.getEmailUsuario() == null ? "" : usuarioAD.getEmailUsuario());
        }
    }

    @GET
    @Path("/getCountUsuariosPorAppNombreRol")
    @Produces({APPLICATION_JSON})
    public Response getCountUsuariosPorAppNombreRol(@QueryParam("opcionId") BigDecimal opcionId,
                                                    @QueryParam("rol") String rol,
                                                    @QueryParam("nombres") String nombres, @QueryParam("estado") Long estado,
                                                    @QueryParam("idUsuario") Long idUsuario) {
        try {
            logger.info("Inicio metodo getCountUsuariosPorAppNombreRol con parametro opcionId:" + opcionId + " y rol:" + rol);
            Long countUsuarios = negocioUsuario.getCountUsuariosPorAppNombreRol(opcionId, rol, estado, idUsuario);

            return Response.ok(countUsuarios).build();
        } catch (SIA3Exception e) {
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        } catch (Exception e) {
            logger.error("Error inesperado en servicio getOperacionesPorAplicacion");
            MensajeDTO mensajeDTO = obtenerMensajeDTOFromException(e);
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
        }
    }

    private Respuesta validadorCamposNulos(Object valorCampo, Integer codigo, Object mensaje) {
        if (valorCampo == null) {
            return new Respuesta(codigo, mensaje);
        }
        String strValor = valorCampo.toString();
        if (strValor.equals("0") || strValor.isEmpty()) {
            return new Respuesta(codigo, mensaje);
        }
        return null;
    }

    private String validadorCamposMaxLdap(String nombreCampo, String valorCampo, CamposLdap campoLdap, Integer maximoDefecto) {
        Respuesta error = validadorCamposNulos(valorCampo, null, null);
        if (error != null){
            return "El "+nombreCampo+" no puede ser nulo, vacio o 0";
        }
        boolean isValid = validarMaximoCampo(campoLdap, valorCampo, maximoDefecto);
        if(isValid){
            return "El "+nombreCampo+" ingresado supera el número máximo de caractéres permitidos";
        }
        return null;
    }

    private UsuariosDTO buscarUsuario(String usuarioId, String nombreUsuario, String correoElectronico) {

        int contador = 0;

        if (usuarioId != null && !usuarioId.trim().isEmpty()) {
            contador++;
        }
        if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
            contador++;
        }
        if (correoElectronico != null && !correoElectronico.trim().isEmpty()) {
            contador++;
        }

        if (contador == 0) {
            logger.error(ERROR_DATOS_NULOS);
            throw new IllegalArgumentException(ID_ERROR_DATOS_NULOS.toString().concat(ERROR_DATOS_NULOS));
        }

        if (contador > 1) {
            logger.error(ERROR_SOLO_UN_VALOR);
            throw new IllegalArgumentException(ERROR_SOLO_UN_VALOR);
        }

        UsuariosDTO usuariosDTO;

        if (usuarioId != null && !usuarioId.trim().isEmpty()) {
            usuariosDTO = negocioUsuario.buscarUsuario(usuarioId);
        } else if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
            usuariosDTO = negocioUsuario.buscarUsuarioPorNombre(nombreUsuario);
        } else {
            usuariosDTO = negocioUsuario.buscarUsuarioPorCorreoElectronico(correoElectronico);
        }
        return usuariosDTO;
    }
}
