package co.gov.mineducacion.seguridad.ejb.servicios.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import co.gov.mineducacion.seguridad.ejb.servicios.ServicioLDAP;
import co.gov.mineducacion.seguridad.ejb.servicios.ServiciosCommons;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioRolEntity;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRolPK;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.ConstantesLDAP;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilEmail;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;
import co.gov.mineducacion.seguridad.modelo.utils.UtilToken;
import co.gov.mineducacion.seguridad.negocio.NegocioBitacoraAuditoria;
import co.gov.mineducacion.seguridad.negocio.NegocioRoles;
import co.gov.mineducacion.seguridad.negocio.NegocioTokensActivos;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;

/**
 * Implementacion encargada de administrar los usuarios
 * <p>
 * ID_MSG_ERROR_DATOS_REQUERIDOS: auditoria de error en la validacion de datos
 * ID_MSG_ERROR_ROL_APP_NOT_FOUND : auditoria de rol y aplicacion no encontrados
 * ID_MSG_USUARIO_MODIFICADO : auditoria de usuario modificado
 * ID_MSG_USUARIO_NO_EXTERNO: auditoria de usuario no es externo
 * USUARIO_NO_ENCONTRADO_LDAP : auditoria de usuario no encontrado en LDAP
 * ID_MSG_USUARIO_INACT_EXITO : usuario inactivado exitosamente
 * ID_MSG_USUARIO_YA_INACTIVO : usuario ya inactivo ID_MSG_CONSULTAR_EXITOSO :
 * consulta exitosa
 *
 * @author Michael Muegueitio
 * @version 02/2017 (1.0)
 */
@Stateless
public class UsuariosImpl extends ServiciosCommons implements IUsuarios {
    private static final Logger logger = Logger.getLogger(UsuariosImpl.class.getName());
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @EJB
    private NegocioUsuarios negocioUsuarios;

    @EJB
    private NegocioBitacoraAuditoria auditoria;

    @EJB
    private NegocioRoles negocioRoles;

    @EJB
    private NegocioUsuariosRol negocioUsuariosRol;

    @EJB
    protected NegocioTokensActivos negocioTokensActivos;

    @EJB
    private ParametrosSng parametrosSng;


    /**
     * Realiza los procesos de validacion para crear un usuario y si toddo sale
     * correcto realiza la creacion en el directorio activo y en la base de
     * datos del sistema
     *
     * @throws SeguridadException
     * @Hbt Se modifica para que retorne el UsuariosDTO
     * Lineas afectadas 93,121,134
     */
    @Override
    public UsuariosDTO crearUsuario(UsuariosDTO usuariosDTO, String aplicacion, String rol, Integer usuarioMod)
            throws SeguridadException {
        logger.info("UsuariosImpl:crearUsuario:se crearUsuario armar " + usuariosDTO);
        Usuario user = new Usuario();
        UsuariosDTO usuarioRetorna = new UsuariosDTO();
        String code = null;
        BigDecimal minutosVigencia;
        String context = parametrosSng.obtenerParametros().getProperty("url_seguridad_publica");
        try {
            usuariosDTO.setNombreRol(rol);
            validarTipoDato(aplicacion, usuarioMod);
            validarCamposObligatorios(aplicacion, rol);
            validarCampos(aplicacion, 50L);
            validarCamposUsuario(usuariosDTO);
            String password = UtilToken.generateRandomPassword(12);
            usuariosDTO.setEstado(Constantes.ESTADO_ACTIVO_S);
            usuariosDTO.setFechaCreacion(new Date());
            usuariosDTO.setUltimaModificacion(new Date());
            usuariosDTO.setUsuarioModificacion(usuarioMod.toString());
            usuariosDTO.setPassword(password);
            usuariosDTO.setNuevoPass(Constantes.SI);
            usuariosDTO.setPassword(password);
            user.setNuevoPass(Constantes.SI);
            Roles rolBD = validarAplicacionYRol(new BigDecimal(aplicacion), rol);
            String entryDN = Constantes.LDAP_LOGIN + usuariosDTO.getNombres() + " " + usuariosDTO.getApellidosUsuario()
                    + "," + rolBD.getPath();
            usuariosDTO.setRuta(entryDN);
            UsuarioLdap usuarioLdap = armarUsuarioLdap(usuariosDTO);
            usuarioLdap.setWhenCreated(armarFecha(new Date()));
            usuarioLdap.setAccountExpires("0"); //Never
            validarUsuarioEsExterno(usuariosDTO.getTipo());
            validarUsuarioExistente(usuariosDTO.getLogonName());
            validacionesLDAP(usuarioLdap.getsAMAccountName(), Constantes.HBT_OU_GENERAL);

            // Transaccion en Base de Datos
            logger.info("UsuariosImpl:crearUsuario:negocioUsuarios.crear:" + usuariosDTO);
            usuarioRetorna = negocioUsuarios.crear(usuariosDTO);
            logger.info("UsuariosImpl:crearUsuario:Termina negocioUsuarios.crear:" + usuarioRetorna);

            // Se crea el codigo de autorizacion
            int intentos = 1;
            boolean existeToken = Boolean.TRUE;

            logger.info("UsuariosImpl:crearUsuario:GeracionToken:INICIO");
            do {

                // Genera el token de sesion y lo ingresa en base de datos
                code = UtilOperaciones.randomString(Constantes.LONGITUD_CODIGO);
                logger.info("UsuariosImpl:crearUsuario:Intento:" + intentos + ":Token:" + code + ":Usuario:" + usuariosDTO);

                TokensActivosDTO tokenEnBd = negocioTokensActivos.buscarToken(code);

                if (tokenEnBd == null) {
                    existeToken = Boolean.FALSE;
                }

                intentos++;

            } while (intentos <= Constantes.INTENTOS_GENERACION_TOKEN
                    && existeToken);

            if (existeToken) {
                throw new SeguridadException(SeguridadException.ID_MSG_ERR_IMP_GENERAR_TOKEN, TipoExcepcion.ERROR);
            }

            String enlace = armarEnlace(context, code, usuarioRetorna.getUsuarioId());

            minutosVigencia = rolBD.getAplicaciones() != null && rolBD.getAplicaciones().getMinutosVigenciaToken() != null ?
                    rolBD.getAplicaciones().getMinVigTokenActConstrasenia() :
                    Constantes.MINUTOS_VIGENCIA_CAMBIO_CLAVE_DEFECTO;

            negocioTokensActivos.crear(UtilToken.armarTokenDTO(code, Constantes.ID_TIPO_TOKEN_AUTORIZACION,
                    new BigDecimal(usuarioRetorna.getUsuarioId()), minutosVigencia));

            auditoria.gestionarAuditoriaDetalle(Constantes.EVT_USER_CREATED, usuarioMod.toString(), aplicacion, "Usuario creado: " + usuariosDTO.getLogonName());

            logger.info("UsuariosImpl:crearUsuario:GeracionToken:FIN");

            // Enviar correo de la creacion del usuario
            logger.info("UsuariosImpl:crearUsuario:EnvioCorreo:INICIO");
            UtilEmail.enviarPasswordEmail(usuariosDTO, enlace, true, parametrosSng.obtenerParametros());
            logger.info("UsuariosImpl:crearUsuario:EnvioCorreo:FIN");

            // Transaccion en LDAP
            logger.info("UsuariosImpl:crearUsuario:se crearUsuario armar ldap " + usuariosDTO);
            ServicioLDAP.crearUsuario(usuarioLdap, parametrosSng.obtenerParametros());
            logger.info("UsuariosImpl:crearUsuario:termino  crearUsuario armar ldap " + usuariosDTO);

            // Consultar el usuario en el LDAP despues de crearlo
            logger.info("UsuariosImpl:ServicioLDAP.buscarUsuarioPorLogin:INICIO:" + usuariosDTO);
            UsuarioLdap usuarioLdapValida = ServicioLDAP.buscarUsuarioPorLogin(usuariosDTO.getLogonName(), usuariosDTO.getRuta(), parametrosSng.obtenerParametros());
            logger.info("UsuariosImpl:ServicioLDAP.buscarUsuarioPorLogin:FIN:" + usuariosDTO);

            if (usuarioLdapValida == null || usuarioLdapValida.getsAMAccountName() == null || "".equals(usuarioLdapValida.getsAMAccountName())) {
                throw new SIA3Exception(MessagesConstants.APP100097);
            }

        } catch (SeguridadException e) {

            logger.error("UsuariosImpl:crearUsuario:SeguridadException:" + e.getMessage(), e);
            auditoria.registrarEventoAuditoria(e.getId(), usuarioMod.toString(), aplicacion);
            procesarError(e);

        } catch (Exception e) {

            logger.error("UsuariosImpl:crearUsuario:Exception:" + e.getMessage(), e);
            procesarError(e);
        }
        logger.info("UsuariosImpl:crearUsuario:Retorno UsuariosImpl: " + usuarioRetorna);
        return usuarioRetorna;
    }

    @Override
    public void actualizarDatosBasicos(UsuariosDTO usuario, String aplicacionId, String usuarioMod, String usuarioPeticion) throws SeguridadException {
        try {
            logger.info("Inicia procesador actualizar datos basicos: " + new Gson().toJson(usuario) + ", aplicacionId: " + aplicacionId + " , usuarioMod: " + usuarioMod);
            UsuariosDTO usuariosDTO = negocioUsuarios.buscarUsuario(usuarioMod);
            UsuarioLdap datosUsuarioExistente = validarUsuarioExisteLDAP(usuariosDTO.getLogonName(), Constantes.HBT_OU_GENERAL);

            usuariosDTO.setApellidosUsuario(usuario.getApellidosUsuario());
            usuariosDTO.setNombres(usuario.getNombres());
            usuariosDTO.setNumeroDocumento(usuario.getNumeroDocumento());
            usuariosDTO.setRuta(usuario.getRuta());
            usuariosDTO.setEmailUsuario(datosUsuarioExistente.getMail());
            usuario.setUsuarioId(usuariosDTO.getUsuarioId());
            usuario.setLogonName(usuariosDTO.getLogonName());

            UsuarioLdap usuarioLdap = armarUsuarioLdap(usuariosDTO);

            usuarioLdap.setWhenChanged(armarFecha(new Date()));

            ServicioLDAP.modificarUsuario(usuarioLdap, parametrosSng.obtenerParametros());

            auditoria.construirAuditoriaDatosBasicosConColas(usuario,datosUsuarioExistente,usuarioPeticion, aplicacionId);

        } catch (SeguridadException e) {
            logger.error("Error actualizando datos: {}" , e);
            auditoria.registrarEventoAuditoria(e.getId(), usuarioPeticion, aplicacionId);
            procesarError(e);
        }
    }


	@Override
	public void actualizarEmail(String userId,String nuevoEmail,String aplicacionId, String usuarioPeticion) throws SeguridadException {
		try {

			UsuariosDTO usuariosDTO = negocioUsuarios.buscarUsuario(userId);
            UsuarioLdap datosUsuarioExistente = validarUsuarioExisteLDAP(usuariosDTO.getLogonName(), Constantes.HBT_OU_GENERAL);

            usuariosDTO.setApellidosUsuario(datosUsuarioExistente.getSn());
            usuariosDTO.setNombres(datosUsuarioExistente.getGivenName());
            usuariosDTO.setNumeroDocumento(datosUsuarioExistente.getDescription());
            usuariosDTO.setRuta(datosUsuarioExistente.getDistinguishedName());
			usuariosDTO.setEmailUsuario(nuevoEmail);

			UsuarioLdap usuarioLdap = armarUsuarioLdap(usuariosDTO);
			usuarioLdap.setWhenChanged(armarFecha(new Date()));
			validacionesLDAPMod(usuarioLdap.getsAMAccountName(), Constantes.HBT_OU_GENERAL);


			ServicioLDAP.modificarUsuario(usuarioLdap, parametrosSng.obtenerParametros());
			usuariosDTO.setUsuarioId(usuariosDTO.getUsuarioId());
			usuariosDTO = negocioUsuarios.actualizar(usuariosDTO);

            auditoria.construirAuditoriaDatosBasicosConColas(usuariosDTO,datosUsuarioExistente,usuarioPeticion, aplicacionId);

        } catch (SeguridadException e) {
			auditoria.registrarEventoAuditoria(e.getId(), userId, aplicacionId);
			procesarError(e);
		}
	}


	@Override
	public void vincularRolesUsuario(List<String> roles, String usuarioId, String aplicacionId) throws SeguridadException, SIA3Exception {
		UsuariosDTO usuarioBD = obtenerUsuarioExistente(usuarioId);
		List<UsuariosDTO> listUsuario = new ArrayList<>();
		listUsuario.add(usuarioBD);
		Usuario usuario = negocioUsuarios.consultarUsuarioIdUsuario(usuarioId);

		if(usuario==null){
			throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO);
		}

		for (String rol : roles) {
			negocioUsuariosRol.agregarUsuariosARol(listUsuario, new BigDecimal(String.valueOf(rol)), usuarioId);
		}

	}

    @Override
    public void desvincularRolesUsuario(List<String> roles, String usuarioId, String aplicacionId, String usuarioPeticion) throws SeguridadException {
        logger.info("Inicia proceso desvincular roles usuario: " + usuarioId + " , roles: " + new Gson().toJson(roles) + "Aplicacion" + aplicacionId);
        UsuariosDTO usuarioBD = obtenerUsuarioExistente(usuarioId);

        List<UsuariosDTO> listUsuario = new ArrayList<>();
        listUsuario.add(usuarioBD);
        Usuario usuario = negocioUsuarios.consultarUsuarioIdUsuario(usuarioId);

        if (usuario == null) {
            throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO);
        }

        List<Boolean> errores = new ArrayList<>();
        for (String rol : roles) {
            logger.info("Ciclo para eliminar rol usuario: " + rol);
            try {
                usuario.getUsuariosRolList().stream()
                    .filter(dato -> dato.getRoles().getNombre().equals(rol) && dato.getRoles().getEstado().equals(new BigDecimal(1)) && dato.getRoles().getAplicacionId().equals(new BigDecimal(aplicacionId)))
                    .forEach(usuariosRol -> {
                        logger.info("Inicia proceso negocio eliminar rolId: " + usuariosRol.getRoles().getRolId() + " , nombre: " + usuariosRol.getRoles().getNombre());
                        negocioUsuariosRol.desvincularUsuarioRol(usuariosRol.getRoles().getRolId(), usuarioId, aplicacionId);

                        JsonObject detalle = new JsonObject();
                        detalle.addProperty("descripcion", "Elimina rol");
                        detalle.addProperty("rol", rol);
                        detalle.addProperty("usuario", usuario.getLogonName());
                        auditoria.gestionarAuditoriaConDetalleYCampoActivo(Constantes.EVT_USER_REMOVED_ROL, usuarioPeticion, aplicacionId, detalle.toString(), null);
                    });
            } catch (Exception e) {
                errores.add(false);
                logger.error("Error desvinculando usuario rol: " + rol);
            }
        }

        if (errores.size() == roles.size()){
            throw new SeguridadException(SeguridadException.ID_ROLES_ENCONTRADOS);
        }
    }

    /**
     * Realiza los procesos de validacion para modificar un usuario y si toddo
     * sale correcto realiza la creacion en el directorio activo y en la base de
     * datos del sistema
     *
     * @param usuariosDTO
     * @param aplicacion
     * @param usuarioMod
     * @throws SeguridadException
     */
    @Override
    public void modificarUsuario(UsuariosDTO usuariosDTO, String aplicacion, Integer usuarioMod) throws SeguridadException {
        logger.info("Inicia proceso modificar usuario imp: " + new Gson().toJson(usuariosDTO) + ", aplicacion: " + aplicacion);
        try {
            validarTipoDato(aplicacion, usuarioMod);
            validarCampos(aplicacion, 50L);
            validarCamposUsuario(usuariosDTO);
            UsuariosDTO usuarioBD = obtenerUsuarioExistente(usuariosDTO.getUsuarioId());
            usuariosDTO.setEstado(usuarioBD.getEstado());
            usuariosDTO.setRuta(usuarioBD.getRuta());
            usuariosDTO.setTipo(usuarioBD.getTipo());
            usuariosDTO.setFechaCreacion(usuarioBD.getFechaCreacion());
            usuariosDTO.setUltimaModificacion(new Date());
            usuariosDTO.setUsuarioModificacion(usuarioMod.toString());
            usuariosDTO.setNuevoPass(usuarioBD.getNuevoPass());
            UsuarioLdap usuarioLdap = armarUsuarioLdap(usuariosDTO);
            usuarioLdap.setWhenChanged(armarFecha(new Date()));
            validarUsuarioEsExterno(usuariosDTO.getTipo());
            validacionesLDAPMod(usuarioLdap.getsAMAccountName(), Constantes.HBT_OU_GENERAL);
            ServicioLDAP.modificarUsuario(usuarioLdap, parametrosSng.obtenerParametros());
            usuariosDTO.setUsuarioId(usuarioBD.getUsuarioId());
            usuariosDTO = negocioUsuarios.actualizar(usuariosDTO);
            auditoria.gestionarAuditoriaDetalle(Constantes.EVT_USER_MODIFIED, usuarioMod.toString(), aplicacion, "Usuario modificado: " + usuariosDTO.getLogonName(), usuariosDTO.getLogonName());
        } catch (SeguridadException e) {
            logger.error("Error actualizando usuario: " + e.getMessage());
            auditoria.registrarEventoAuditoria(e.getId(), usuarioMod.toString(), aplicacion);
            procesarError(e);
        }
    }


    /**
     * Inactiva un usuario en el directorio activo y en la base de datos de
     * seguridad
     *
     * @param loginUsuario
     * @param appKey
     * @throws SeguridadException
     */
    @Override
    public void inactivarUsuario(Integer idUsuario, String appKey, Integer usuarioMod) throws SeguridadException {
        try {
            validarCamposObligatorios(idUsuario, appKey);
            validarTipoDato(appKey, usuarioMod);
            validarCampos(appKey, 50L);
            validarCampos(idUsuario);
            Usuario usuarioBD = validarEstadoUsuario(idUsuario.toString());
            validarUsuarioEsExterno(usuarioBD.getTipo());
            ServicioLDAP.validarUsuarioExisteLDAP(usuarioBD.getLogonName(), Constantes.HBT_OU_GENERAL, parametrosSng.obtenerParametros());
            validarUsuarioInactivoLDAP(usuarioBD.getLogonName(), Constantes.HBT_OU_GENERAL);
            validarUsuarioExternoLDAP(usuarioBD.getLogonName(), Constantes.HBT_OU_EXTERNOS);
            UsuariosDTO usuariosDTO = negocioUsuarios.copiarPropiedades(usuarioBD);
            usuariosDTO.setEstado(Constantes.ESTADO_ACTIVO_N);
            usuariosDTO.setUsuarioModificacion(usuarioMod.toString());
            usuariosDTO.setUltimaModificacion(new Date());
            eliminarRolesAsociados(idUsuario);
            guardarUsuario(usuariosDTO.getLogonName(), usuariosDTO);
            auditoria.gestionarAuditoriaDetalle(Constantes.EVT_USER_INACTIVE_SUCCESS, usuarioMod.toString(), appKey, "Usuario inactivo: " + usuarioBD.getLogonName());
        } catch (SeguridadException e) {

            auditoria.registrarEventoAuditoria(e.getId(), usuarioMod.toString(), appKey);
            procesarError(e);
        }
    }

    /**
     * Valida si el usuario es externo si no es asi se lanza la excepcion
     *
     * @param logonName identificador del usuario
     * @param entryDn   si contiene 'Usuarios Externos' se consulta en la rama de Usuarios Externos, si no es asi se
     *                  consulta en toddo el arbol
     * @throws SeguridadException lanzada cuando el usuario no es externo
     * @author hfabra
     * @since 4/04/2017
     */
    private void validarUsuarioExternoLDAP(String usuario, String entryDn) throws SeguridadException {
        if (ServicioLDAP.buscarUsuarioPorLogin(usuario, entryDn, parametrosSng.obtenerParametros()) == null) {
            throw new SeguridadException(SeguridadException.ID_MSG_USUARIO_NO_EXTERNO_LDAP);
        }

    }

    /**
     * Valida si el usuario se encuentra inactivo en el directorio activo
     *
     * @param logonName identificador del usuario
     * @param entryDn   si contiene 'Usuarios Externos' se consulta en la rama de Usuarios Externos, si no es asi se
     *                  consulta en toddo el arbol
     * @throws SeguridadException
     * @author hfabra
     * @since 4/04/2017
     */
    private void validarUsuarioInactivoLDAP(String usuario, String entryDn) throws SeguridadException {
        if (ServicioLDAP.buscarUsuarioPorLogin(usuario, entryDn, parametrosSng.obtenerParametros()) != null) {
            throw new SeguridadException(SeguridadException.ID_MSG_USUARIO_YA_INACTIVO);
        }
    }

    /**
     * Consulta todos los usuarios que tenga un rol
     *
     * @param rol sobre el cual se realiza la consulta
     * @param app identificador de la aplicación sobre la cual se realiza la consutlta
     * @return
     * @throws SeguridadException
     */
    @Override
    public List<UsuariosDTO> consultarRolesXUSuario(String rol, String app, Integer usuarioMod)
            throws SeguridadException {
        try {
            validarCamposObligatorios(rol, app);
            validarCampos(app, 50L);
            validarCampos(rol, 50L);
            validarTipoDato(app, usuarioMod);
            Roles rolBD = validarAplicacionYRol(new BigDecimal(app), rol);
            auditoria.gestionarAuditoria(Constantes.EVT_CONSULTA_EXITOSA, usuarioMod.toString(), app);
            return armarListaUsuariosDTO(armarListaUsuarios(rolBD.getRolId()));
        } catch (SeguridadException e) {
            auditoria.registrarEventoAuditoria(e.getId(), usuarioMod.toString(), app);
            procesarError(e);
            return null;
        }
    }

    @Override
    public List<UsuariosDTO> consultarUsuarioLogon(String logon, String app, Integer usuarioMod)
            throws SeguridadException {
        try {
            validarCamposObligatorios(logon, app);
            validarCampos(app, 50L);
            validarCampos(logon, 50L);
            validarTipoDato(app, usuarioMod);
            auditoria.gestionarAuditoria(Constantes.EVT_CONSULTA_EXITOSA, usuarioMod.toString(), app);
            return armarListaUsuariosDTO(armarListaUsuarioLogon(logon));
        } catch (SeguridadException e) {
            auditoria.registrarEventoAuditoria(e.getId(), usuarioMod.toString(), app);
            procesarError(e);
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene un usuario por el nombre de usuario
     *
     * @param logonName
     * @return
     * @throws SeguridadException
     */
    private UsuariosDTO obtenerUsuarioExistente(String logonName) throws SeguridadException {
        UsuariosDTO usuarioBd = negocioUsuarios.buscarUsuario(logonName);
        if (usuarioBd == null) {
            throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO_INACTIVAR);
        }
        return usuarioBd;
    }

    /**
     * Elimina los roles asociados que tenga un usuario
     *
     * @param idUsuario
     * @throws SeguridadException
     */
    private void eliminarRolesAsociados(Integer idUsuario) throws SeguridadException {
        try {
            List<UsuariosRolDTO> listaUsuariosRol = negocioUsuariosRol.buscarUsuarioRolPorUsuario(idUsuario.toString());
            for (UsuariosRolDTO usuRolDto : listaUsuariosRol) {
                negocioUsuariosRol.eliminar(usuRolDto.getRoles().getRolId(), usuRolDto.getUsuarios().getUsuarioId());
            }
        } catch (Exception e) {
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);
        }
    }

    /**
     * Valida el tipo de dato de un app y del usuario para evitar errores
     * posteriores
     *
     * @param appKey
     * @param usuarioMod
     * @throws SeguridadException
     */
    private void validarTipoDato(String appKey, Integer usuarioMod) throws SeguridadException {
        try {
            new BigDecimal(appKey);
            logger.info(usuarioMod.toString());
        } catch (Exception e) {
            logger.error("Error validando tipo dato: " + e.getMessage());
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
        }
    }

    /**
     * @param campoA
     * @param campoB
     * @throws SeguridadException
     */
    private void validarCamposObligatorios(Object campoA, String campoB) throws SeguridadException {
        if (((campoA == null) || ((campoA != null) && (campoA.equals(""))))
                || ((campoB == null) || ((campoB != null) && (campoB.equals(""))))) {
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
        }
    }

    /**
     * Valida los campos del usuario esten completos
     *
     * @param usuariosDTO
     * @throws SeguridadException
     */
    private void validarCamposUsuario(UsuariosDTO usuariosDTO) throws SeguridadException {
        validarCampos(usuariosDTO.getApellidosUsuario(), 50L);
        validarCampos(usuariosDTO.getNombres(), 50L);
        validarCampos(usuariosDTO.getLogonName(), 25L);
        validarCampos(usuariosDTO.getTipo().toString(), 7L);
        validarEmail(usuariosDTO.getEmailUsuario(), 100L);
    }

    /***
     * Arma la lista de usuarios a partir de un rol
     *
     * @param idRol
     * @return
     * @throws SeguridadException
     */
    private List<Usuario> armarListaUsuarios(BigDecimal idRol) throws SeguridadException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        List<BigDecimal> listaIdUsuarios = negocioUsuarios.buscarIdUsuariosPorRol(idRol);
        for (BigDecimal idUsuario : listaIdUsuarios) {
            listaUsuarios.add(negocioUsuarios.consultarUsuarioIdUsuario(idUsuario.toString()));
        }
        return listaUsuarios;

    }

    /***
     * Arma la lista de usuarios a del logon
     *
     * @param idRol
     * @return
     * @throws SeguridadException
     */
    private List<Usuario> armarListaUsuarioLogon(String logon) throws SeguridadException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(negocioUsuarios.consultarUsuario(logon));
        return listaUsuarios;
    }

    /**
     * Arma un listado de usuarios DTO para retornar
     *
     * @param listaUsuarios
     * @return
     * @throws SeguridadException
     */
    public List<UsuariosDTO> armarListaUsuariosDTO(List<Usuario> listaUsuarios) throws SeguridadException {
        List<UsuariosDTO> listaUsuariosDTO = new ArrayList<>();
        if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
            for (Usuario usuario : listaUsuarios) {
                if (usuario != null) {
                    UsuariosDTO usuarioDTO = new UsuariosDTO();
                    UsuarioLdap usuarioLDAP = ServicioLDAP.buscarUsuarioPorLogin(usuario.getLogonName(), usuario.getRuta(), parametrosSng.obtenerParametros());

                    if (usuarioLDAP != null) {
                        usuarioDTO.setApellidosUsuario(usuarioLDAP.getSn());
                        usuarioDTO.setEmailUsuario(usuarioLDAP.getMail());
                        usuarioDTO.setNombres(usuarioLDAP.getGivenName());
                        usuarioDTO.setNombreUsuario(usuarioLDAP.getsAMAccountName());
                        usuarioDTO.setTipo(usuario.getTipo());
                        usuarioDTO.setUsuarioId(usuario.getUsuarioId());
                        listaUsuariosDTO.add(usuarioDTO);
                    }

                }
            }
        }
        return listaUsuariosDTO;
    }

    private Usuario validarEstadoUsuario(String idUsuario) throws SeguridadException {
        Usuario usuario = negocioUsuarios.consultarUsuarioIdUsuario(idUsuario);
        if (usuario == null) {
            throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO_INACTIVAR);
        } else {
            if (!Constantes.ESTADO_ACTIVO_S.equals(usuario.getEstado())) {
                throw new SeguridadException(SeguridadException.ID_MSG_USUARIO_YA_INACTIVO);
            }
        }
        return usuario;

    }

    /**
     * Arma un usuario Ldap con los datos recibidos del servicio
     *
     * @param usuario
     * @return
     */
    private UsuarioLdap armarUsuarioLdap(UsuariosDTO usuario) {
        UsuarioLdap usuarioLdap = new UsuarioLdap();
        usuarioLdap.setDescription(usuario.getNumeroDocumento());
        usuarioLdap.setSn(usuario.getApellidosUsuario());
        usuarioLdap.setMail(usuario.getEmailUsuario());
        usuarioLdap.setCn(usuario.getNombres() + " " + usuario.getApellidosUsuario());
        usuarioLdap.setDistinguishedName(usuario.getRuta());
        usuarioLdap.setDisplayName(usuario.getNombres() + " " + usuario.getApellidosUsuario());
        usuarioLdap.setGivenName(usuario.getNombres());
        usuarioLdap.setsAMAccountName(usuario.getLogonName());
        usuarioLdap.setUserPrincipalName(usuario.getLogonName() + obtenerDominio());
        usuarioLdap.setUserPassword(usuario.getPassword());
        usuarioLdap.setObjectClass(armarListaClases());
        return usuarioLdap;
    }

    /**
     * @param date
     * @return
     * @author hfabra
     * @since 4/04/2017
     */
    private String armarFecha(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = formatter.format(date);
        return s + ".00Z";
    }

    private String obtenerDominio(){
        return parametrosSng.obtenerParametros().getProperty(ConstantesLDAP.DOMINIO);
    }

    /**
     * Consulta si el usuario existe en el directorio activo
     *
     * @param usuario
     * @param entryDN
     * @throws SeguridadException
     */
    private void validacionesLDAP(String usuario, String entryDn) throws SeguridadException {
        if (ServicioLDAP.buscarUsuarioPorLogin(usuario, entryDn, parametrosSng.obtenerParametros()) != null) {
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_USU_EXISTE);
        }
    }

    /**
     * Consulta si el usuario existe en el directorio activo
     *
     * @param usuario
     * @param entryDN
     * @throws SeguridadException
     */
    private void validacionesLDAPMod(String login, String entryDn) throws SeguridadException {
        logger.info("Inicia proceso validacionesLDAPMod " + login);
        if (ServicioLDAP.buscarUsuarioPorLogin(login, entryDn, parametrosSng.obtenerParametros()) == null) {
            throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO_LDAP);
        }
    }

    /**
     *
     * @param login loginUsuario
     * @param entryDn parametros default
     * @return UsuarioLdap
     * @throws SeguridadException retorna error si no existe el usuario
     */
    private UsuarioLdap validarUsuarioExisteLDAP(String login, String entryDn) throws SeguridadException {
        logger.info("Inicia proceso validarUsuarioExisteLDAP " + login);
        UsuarioLdap usuarioLdap = ServicioLDAP.buscarUsuarioPorLogin(login, entryDn, parametrosSng.obtenerParametros());
        if (usuarioLdap == null) {
            throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO_LDAP);
        }
        return usuarioLdap;
    }

    /**
     * arma la lista de clases a las que debe quedar relacionado el usuario
     *
     * @return
     */
    private List<String> armarListaClases() {
        List<String> listaClases = new ArrayList<>();
        listaClases.add("organizationalPerson");
        listaClases.add("person");
        listaClases.add("top");
        listaClases.add("user");
        return listaClases;

    }

    /**
     * Verifica si el usuario existe en el sistema si se crea no debe existir el
     * usuario si se modifica lo debe crear
     *
     * @param nombreUsuario
     * @throws SeguridadException
     */
    private void validarUsuarioExistente(String nombreUsuario) throws SeguridadException {
        Usuario usuario = negocioUsuarios.consultarUsuario(nombreUsuario);
        if (usuario != null) {
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_USU_EXISTE);
        }
    }

    /**
     * Verifica si el usuario existe en el sistema si se crea no debe existir el
     * usuario si se modifica lo debe crear
     *
     * @param nombreUsuario
     * @throws SeguridadException
     */
    private void guardarUsuario(String nombreUsuario, UsuariosDTO usuarioDto) throws SeguridadException {
        Usuario usuario = negocioUsuarios.consultarUsuario(nombreUsuario);
        try {
            if (usuario == null) {
                negocioUsuarios.crear(usuarioDto);
            } else {
                usuarioDto.setUsuarioId(usuario.getUsuarioId());
                negocioUsuarios.actualizar(usuarioDto, usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Valida que el rol y la aplicacion correspondan a una relacion existente
     * en el sistema
     *
     * @param aplicacion
     * @param nombreRol
     * @throws SeguridadException
     */
    private Roles validarAplicacionYRol(BigDecimal aplicacion, String nombreRol) throws SeguridadException {
        Roles rolBD = negocioRoles.consultarRolePorNombreYAplicacion(nombreRol, aplicacion);
        if (rolBD == null) {
            List<Roles> rolesBD = negocioRoles.consultarRolesPorNombre(nombreRol);
            if (rolesBD == null || rolesBD.isEmpty()) {
                throw new SeguridadException(SeguridadException.ROL_NO_EXISTE);
            } else {
                throw new SeguridadException(SeguridadException.ROL_NO_PERTENECE_APLICACION);
            }
        }
        return rolBD;
    }

    /**
     * @param usuariosDTO
     * @param rolBD
     * @return
     */
    private UsuarioRolEntity armarUsuarioRolDTO(Usuario usuariosDTO, Roles rolBD) {
        UsuarioRolEntity usuariorRol = new UsuarioRolEntity();
        usuariorRol.setRolId(rolBD.getRolId());
        usuariorRol.setUsuarioId(new BigDecimal(usuariosDTO.getUsuarioId()));
        return usuariorRol;
    }

    /**
     * @param usuariosDTO
     * @param rolBD
     * @return
     */
    private UsuariosRol armarUsuarioRol(Usuario usuariosDTO, Roles rolBD) {
        UsuariosRol usuariorRol = new UsuariosRol();

        usuariorRol.setRoles(rolBD);
        usuariorRol.setUsuarios(usuariosDTO);

        UsuariosRolPK userPk = new UsuariosRolPK();
        userPk.setRolId(rolBD.getRolId());
        userPk.setUsuarioId(usuariosDTO.getUsuarioId());
        usuariorRol.setUsuariosRolPK(userPk);
        return usuariorRol;
    }

    /**
     * @param usuariosDTO
     * @param rolBD
     * @return
     */
    private UsuariosRolDTO armarUsuarioRolDTO2(Usuario usuariosDTO, Roles rolBD) {
        UsuariosRolDTO usuarioRol = new UsuariosRolDTO();
        try {
            usuarioRol.setRoles(rolBD);
            Usuario user = new Usuario();
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

            BeanUtils.copyProperties(user, usuariosDTO);

            usuarioRol.setUsuarios(user);
            UsuariosRolPK userPk = new UsuariosRolPK();
            userPk.setRolId(rolBD.getRolId());
            userPk.setUsuarioId(usuariosDTO.getUsuarioId());

            usuarioRol.setUsuariosRolPK(userPk);

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return usuarioRol;
    }

    /**
     * Verifica si el usuario quien realizo la peticion es un usuario administrador
     */
    @Override
    public void verificarUsuarioAdministrador(Integer usuarioAdministrador, String rol) throws SeguridadException {

        logger.info("verificarUsuarioAdministrador-->" + usuarioAdministrador + ":" + rol);
        try {
            verificarObligatoriedad(usuarioAdministrador);
            verificarRolGestionador(obtenerUsuarioPorId(usuarioAdministrador), rol);
        } catch (Exception e) {
            procesarError(e);
        }
    }

    /**
     * Verifica que la firma contenga el campo usuario administrador
     *
     * @param usuarioAdministrador
     * @throws SeguridadException
     */
    private void verificarObligatoriedad(Integer usuarioAdministrador) throws SeguridadException {
        if (usuarioAdministrador == null) {
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
        }
    }

    /**
     * Verifica que el usuario exista
     *
     * @param usuarioAdministrador
     * @return
     * @throws SeguridadException
     */
    private Usuario obtenerUsuarioPorId(Integer usuarioAdministrador) throws SeguridadException {
        Usuario usuario = negocioUsuarios.consultarUsuarioIdUsuario(usuarioAdministrador.toString());
        if (usuario == null) {
            throw new SeguridadException(SeguridadException.USUARIO_NO_ENCONTRADO_INACTIVAR);
        }
        return usuario;
    }

    private void validarUsuarioEsExterno(BigDecimal tipo) throws SeguridadException {
        logger.info("Inicia proceso para validar si es un usuario externo: " + tipo);
        if (!Constantes.ID_TIPO_USUARIO_EXTERNO.equals(tipo)) {
            throw new SeguridadException(SeguridadException.ID_MSG_USUARIO_NO_EXTERNO_SIA3);
        }
    }

    /**
     * Verifica que entre los roles el usuario tenga el rol Gestionador
     *
     * @param usuario
     * @param rol
     * @throws SeguridadException
     */
    private void verificarRolGestionador(Usuario usuario, String rol) throws SeguridadException {
        boolean contieneRolGestionador = false;

        for (UsuariosRol usuarioRol : usuario.getUsuariosRolList()) {
            if (rol.equals(usuarioRol.getRoles().getNombre())) {
                contieneRolGestionador = true;
                break;
            }
        }

        if (!contieneRolGestionador) {
            throw new SeguridadException(SeguridadException.ID_MSG_USAURIO_NO_ADMINISTRADOR);
        }
    }

    /**
     * @param campo
     * @param longitud
     * @throws SeguridadException
     * @author hfabra
     * @since 3/04/2017
     */
    private void validarCampos(String campo, Long longitud) throws SeguridadException {
        if ((campo == null)
                || ((campo != null) && (campo.equals("")))
                || ((campo != null) && (!campo.equals(""))
                && (campo.length() > longitud))) {
            logger.error("Error validando campos: " + campo + " " + longitud);
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
        }

    }

    /**
     * @param campo
     * @throws SeguridadException
     * @author hfabra
     * @since 3/04/2017
     */
    private void validarCampos(Integer campo) throws SeguridadException {
        if ((campo == null) || ((campo != null) && (campo.intValue() == 0))) {
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
        }
    }

    /**
     * @param campoEmail
     * @param longitud
     * @throws SeguridadException
     * @author hfabra
     * @since 3/04/2017
     */
    private void validarEmail(String campoEmail, Long longitud) throws SeguridadException {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        if ((campoEmail == null)
                || ((campoEmail != null) && (campoEmail.equals("")))
                || ((campoEmail != null) && (!campoEmail.equals(""))
                && (campoEmail.length() > longitud))
                || !pattern.matcher(campoEmail).matches()) {
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
        }

    }


    /**
     * @param context
     * @param code
     * @return
     * @author hfabra
     * @since 24/03/2017
     */
    private String armarEnlace(String context, String code, String userId) {
        StringBuilder enlace = new StringBuilder(context)
                .append("/ingresarNuevaContrasenia.jsf?code=")
                .append(code)
                .append("&userID=" + userId);
        return enlace.toString();
    }
}
