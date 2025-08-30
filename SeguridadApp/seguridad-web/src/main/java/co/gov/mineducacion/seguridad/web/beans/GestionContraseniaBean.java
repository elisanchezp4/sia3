package co.gov.mineducacion.seguridad.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.mineducacion.seguridad.ejb.servicios.IPassword;
import co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioTokensActivos;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import co.gov.mineducacion.seguridad.web.beans.general.ControladorAbstractoBean;
import co.gov.mineducacion.seguridad.web.servicio.utils.UtilJsf;


/**
 * ManageBean para la vista ingresarNuevaContrasenia.xhtml que permite a un usuario ingresar una nueva clave
 * para cambiarla (por olvido o por mantenimiento) y para la vista restablecerContrasenia.xhtml que env�a un correo
 * al usuario con la URL que permite cambiar la contrasenia.
 *
 * @author hfabra
 */
@ManagedBean(name = "recordarContrasenia")
@ViewScoped
public class GestionContraseniaBean extends ControladorAbstractoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private final transient Logger log = LoggerFactory.getLogger(this.getClass());

    private Long idTipoIdentificacion;

    private boolean existeUsuario = false;

    private transient ResourceBundle bundle;

    private int tamanioDoc = 1;

    private String validacion;

    private String correoElectronicoAsociado;

    private Long idTipoIdentificacionAsociado = 0L;

    private String numeroIdentificacionAsociado;

    private String usuarioAsociado;

    private String contraseniaNueva;

    private String contraseniaRepetida;

    private String code;

    private String userId;

    private String appId;

    protected boolean errorCamposObligatorios = false;

    protected boolean errorLongitudCampos = false;

    protected boolean errorFormatoPass = false;

    protected boolean errorPass = false;

    @EJB
    private IPassword servicioPassword;

    @EJB
    private IServicioAutenticacion servicioAutenticacion;

    @EJB
    private NegocioTokensActivos negocioTokenActivos;

    @EJB
    private NegocioAplicaciones negocioAplicaciones;

    @EJB
    private NegocioUsuariosRol negocioUsuariosRol;

    @EJB
    protected NegocioUsuarios negocioUsuarios;

    private boolean inactivoBtnCambio = false;
    private boolean inactivoBtnLimpiar = false;


    public boolean isInactivoBtnCambio() {
        return inactivoBtnCambio;
    }

    public void setInactivoBtnCambio(boolean inactivoBtnCambio) {
        this.inactivoBtnCambio = inactivoBtnCambio;
    }

    public boolean isInactivoBtnLimpiar() {
        return inactivoBtnLimpiar;
    }

    public void setInactivoBtnLimpiar(boolean inactivoBtnLimpiar) {
        this.inactivoBtnLimpiar = inactivoBtnLimpiar;
    }

    /**
     * Metodo que inicializa el ManagedBean
     */
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        inactivoBtnCambio = false;
        inactivoBtnLimpiar = false;
        errorCamposObligatorios = Boolean.FALSE;
        errorPass = Boolean.FALSE;

        try {
            if (request.getParameter("userID") != null) {
                userId = request.getParameter("userID");
                List<UsuariosRolDTO> usersRol = negocioUsuariosRol.buscarUsuarioRolPorUsuario(userId);
                if (usersRol != null && !usersRol.isEmpty()) {
                    appId = usersRol.get(0).getRoles().getAplicacionId().toString();
                } else {
                    appId = null;
                }
            }
            if (request.getParameter("code") != null) {
                code = request.getParameter("code");
                TokensActivosDTO tokenDTO = negocioTokenActivos.buscarToken(code);

                servicioAutenticacion.isTokenValidoVigente(tokenDTO);
                servicioAutenticacion.validarAsociacionUserToken(tokenDTO, userId);
            }

        } catch (SeguridadException e) {
            if (e.getId().equals(SeguridadException.ID_MSG_CODIGO_NO_VIGENTE)) {
                procesarError(new SeguridadException(SeguridadException.ID_MSG_TOKEN_INVALIDO_CAMBIO_CONTRASENIA, TipoExcepcion.ERROR));
            } else {
                procesarError(new SeguridadException(SeguridadException.ID_MSG_ERROR_CAMBIO_CONTRASENIA, TipoExcepcion.ERROR));
            }
        } catch (Exception e) {

            procesarError(new SeguridadException(SeguridadException.ID_MSG_ERROR_CAMBIO_CONTRASENIA, TipoExcepcion.ERROR));
        }

        bundle = context.getApplication().evaluateExpressionGet(context, "#{msg}", ResourceBundle.class);
    }


    /**
     * Enviaa un correo al usuario con una URL que permite cambiar la contrasenia, ya sea por olvido o por administracion
     */
    public void restablecerContrasenia() {
        try {
            this.usuarioAsociado = this.usuarioAsociado.trim();
            validarCampos();
            Usuario usuario = negocioUsuarios.consultarUsuario(usuarioAsociado);
            if(usuario != null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                String idApp = null;
                HttpSession sesHttp = (HttpSession) facesContext.getExternalContext().getSession(false);
                if (sesHttp != null && sesHttp.getAttribute("idAplicacion") != null) {
                    idApp = (String) sesHttp.getAttribute("idAplicacion");
                }else{
                    idApp = Constantes.HBT_ID_APP_SEGURIDAD.toString();
                }
                servicioPassword.procesoRestablecerPassword(usuarioAsociado, UtilJsf.getContexto(), idApp);
                procesarError(new SeguridadException(SeguridadException.ID_MSG_WEB_ENVIO_EXITOSO, TipoExcepcion.INFO));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no encontrado", "El usuario ingresado no existe."));
            }
        } catch (SeguridadException exception) {
            if (exception.getMessage().equals(Constantes.ERROR_USUARIO_NO_EXTERNO)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error usuario no externo", "El usuario no es externo."));

            } else if (exception.getMessage().equals(String.valueOf(Constantes.ESTADO_ACTIVO_N))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error usuario no activo", "El usuario no se encuentra activo en el directorio activo."));

            } else if (exception.getId().equals(98L)) {
                log.error("Error conexion LDAP: " + exception.getMessage());
                procesarError(new SeguridadException(SeguridadException.SE_PERDIO_COMUNICACION_LDAP, TipoExcepcion.ERROR));
            } else {
                log.error("Ingreso procesar error generico: {}", exception.getMessage());
                procesarError(exception);
            }
        } catch (Exception error) {
            log.debug(error.getMessage());
            procesarError(error);
        }

    }


    /**
     * Limpia los campos de la vista
     */
    public void limpiarFormularioPass() {
        contraseniaNueva = "";
        contraseniaRepetida = "";
    }

    private void validarCampos() throws SeguridadException {
        validar(bundle, usuarioAsociado, "form1:usuarioAsociado", true);
        if (errorCamposObligatorios) {
            errorCamposObligatorios = Boolean.FALSE;
            throw new SeguridadException(SeguridadException.ID_MSG_WEB_CAMPO_OBLIGATORIO, TipoExcepcion.ERROR);
        }
    }

    /**
     * Cambia la contrasenia del usuario en el sistema
     *
     * @return
     */
    public void cambiarContrasenia() {
        if (bundle == null) {
            FacesContext contextX = FacesContext.getCurrentInstance();
            bundle = contextX.getApplication().evaluateExpressionGet(contextX, "#{msg}", ResourceBundle.class);
        }
        try {
            validarCamposPass();
            if (!errorPass) {
                //se valida el token
                if (code == null || code.trim().equals(Constantes.CADENA_VACIA)
                        || userId == null || userId.equals(Constantes.CADENA_VACIA)) {

                    throw new SeguridadException(SeguridadException.ID_MSG_CODIGO_NO_VIGENTE, TipoExcepcion.ERROR);
                }
                TokensActivosDTO tokenDTO = negocioTokenActivos.buscarToken(code);
                servicioAutenticacion.isTokenValidoVigente(tokenDTO);
                servicioAutenticacion.validarAsociacionUserToken(tokenDTO, userId);

                servicioAutenticacion.modificarPassword(code, Integer.valueOf(userId), contraseniaNueva, appId, userId);
                inactivoBtnCambio = true;
                inactivoBtnLimpiar = true;
                procesarError(new SeguridadException(SeguridadException.ID_MSG_WEB_PASS_EXITOSO, TipoExcepcion.INFO));

            }
        } catch (SeguridadException exception) {
            if (exception.getId() != null && exception.getId().equals(98L)) {
                log.error("Error conexión LDAP: {}", exception.getMessage());
                procesarError(new SeguridadException(SeguridadException.SE_PERDIO_COMUNICACION_LDAP, TipoExcepcion.ERROR));
            } else if (exception.getId() != null && exception.getId().equals(12L)) {
                log.error("El token no está asociado: {}", exception.getMessage());
                procesarError(new SeguridadException(SeguridadException.TOKEN_USU_NO_ASOCIADO, TipoExcepcion.ERROR));
            }
            else if(exception.getTipo() == null ){
                procesarError(new SeguridadException(exception.getMessage(), TipoExcepcion.ERROR));
            }
            else {
                log.error("Ingreso procesar error generico: {}", exception.getMessage());
                procesarError(exception);
            }
        }
    }
    /**
     * Valida los campos del formulario
     *
     * @throws SeguridadException error estandar
     */
    public void validarCamposPass() throws SeguridadException {
        validar(contraseniaNueva, "form1:contraseniaNueva", true, Constantes.LONGITUD_CLAVE_USUARIO);
        validar(contraseniaRepetida, "form1:contraseniaRepetida", true, Constantes.LONGITUD_CLAVE_USUARIO);

        if (errorCamposObligatorios) {
            errorCamposObligatorios = Boolean.FALSE;
            throw new SeguridadException(SeguridadException.ID_MSG_WEB_CAMPOS_OBLIGATORIOS, TipoExcepcion.ERROR);
        } else {
            errorPass = validarPassCoincide(contraseniaNueva, contraseniaRepetida, "form1:contraseniaRepetida");
        }

        if (this.errorLongitudCampos) {
            throw new SeguridadException(SeguridadException.ID_MSG_ERR_LONGITUD_CAMPOS, TipoExcepcion.ERROR);
        }

        if (this.errorFormatoPass) {
            throw new SeguridadException(215L, TipoExcepcion.ERROR);
        }
    }

    /**
     * Valida que las contrasenias ingresadas coincidan
     *
     * @param passNuevo
     * @param passRepetido
     * @param ubicacionFormulario
     * @return
     */
    public Boolean validarPassCoincide(String passNuevo, String passRepetido, String ubicacionFormulario) {
        SeguridadException error = new SeguridadException(SeguridadException.ID_MSG_WEB_PASS_NO_COINCIDEN, TipoExcepcion.ERROR);
        Boolean fail = false;
        if (!passNuevo.equals(passRepetido)) {
            fail = true;
            procesarMensajeEspecifico(ubicacionFormulario, error);
        }
        return fail;
    }

    public void salir() {
        UtilJsf.navegar("/login.xhtml?faces-redirect=true");
    }

    public void mostrarDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('cancelarDialog').show();");
    }

    public void cerrarDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('cancelarDialog'').close();");
    }

    public void accionDialog(String widgetWar, String accion) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('" + widgetWar + "')." + accion + "();");
    }

    /**
     * Maneja el error en la vista
     */
    public void manejarError(Exception error, Logger log) {
        if (error instanceof SeguridadException) {
            UtilJsf.mostrarMensaje(error);
            log.error(error.getMessage(), error);
        } else {
            UtilJsf.mostrarMensaje(new SeguridadException("Error inesperado"));
            log.error(error.getMessage(), error);
        }
    }

    public void validar(ResourceBundle bundle, String valor, String ubicacionFormulario, boolean obligatorio) {
        SeguridadException error = new SeguridadException(TipoExcepcion.FATAL);
        if (obligatorio && valor == null || valor.isEmpty()) {
            error.setDetalle(bundle.getString("campoObligatorio"));
            errorCamposObligatorios = Boolean.TRUE;
            procesarMensajeEspecifico(ubicacionFormulario, error);
        }
    }

    private void validar(String valor, String ubicacionFormulario, boolean obligatorio, int tamanio) {

        this.errorCamposObligatorios = Boolean.FALSE;
        this.errorLongitudCampos = Boolean.FALSE;
        this.errorFormatoPass = Boolean.FALSE;

        if (obligatorio && (valor == null || valor.isEmpty())) {
            log.error("Ingreso error campo obligatorio");
            SeguridadException error = new SeguridadException(TipoExcepcion.FATAL, "La contraseña no tiene un formato valido: campo obligatorio");
            errorCamposObligatorios = Boolean.TRUE;
            procesarMensajeEspecifico(ubicacionFormulario, error);
        }

        if (valor != null && tamanio != -1 && valor.length() > tamanio) {
            log.error("Ingreso error supera máximo caracteres");
            SeguridadException error = new SeguridadException(TipoExcepcion.FATAL, "La contraseña no tiene un formato valido: Tamaño máximo permitido es de 30 caracteres");
            this.errorLongitudCampos = Boolean.TRUE;
            procesarMensajeEspecifico(ubicacionFormulario, error);
        }

        if (valor != null && !LdapValidacionesUtil.PASSWORD_PATTERN.matcher(valor).matches()) {
            log.error("Ingreso error formato no valida pater");
            SeguridadException error = new SeguridadException(TipoExcepcion.FATAL, "La contraseña no tiene un formato valido: Tamaño mínimo permitido es de 8 caracteres y debe contener caracteres especiales como ($@!#%*-_?&), numeros, mayúsculas y minusculas");
            this.errorFormatoPass = Boolean.TRUE;
            procesarMensajeEspecifico(ubicacionFormulario, error);
        }
    }

    public Long getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(Long idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public boolean isExisteUsuario() {
        return existeUsuario;
    }

    public void setExisteUsuario(boolean existeUsuario) {
        this.existeUsuario = existeUsuario;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public int getTamanioDoc() {
        return tamanioDoc;
    }

    public void setTamanioDoc(int tamanioDoc) {
        this.tamanioDoc = tamanioDoc;
    }

    public String getValidacion() {
        return validacion;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    public String getCorreoElectronicoAsociado() {
        return correoElectronicoAsociado;
    }

    public void setCorreoElectronicoAsociado(String correoElectronicoAsociado) {
        this.correoElectronicoAsociado = correoElectronicoAsociado;
    }

    public Long getIdTipoIdentificacionAsociado() {
        return idTipoIdentificacionAsociado;
    }

    public void setIdTipoIdentificacionAsociado(Long idTipoIdentificacionAsociado) {
        this.idTipoIdentificacionAsociado = idTipoIdentificacionAsociado;
    }

    public String getNumeroIdentificacionAsociado() {
        return numeroIdentificacionAsociado;
    }

    public void setNumeroIdentificacionAsociado(String numeroIdentificacionAsociado) {
        this.numeroIdentificacionAsociado = numeroIdentificacionAsociado;
    }

    public String getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(String usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }


    /* (non-Javadoc)
     * @see co.gov.mineducacion.seguridad.web.beans.general.ControladorAbstractoBean#inicio()
     */
    @Override
    public void inicio() {
        // Auto-generated method stub
    }


    /* (non-Javadoc)
     * @see co.gov.mineducacion.seguridad.web.beans.general.ControladorAbstractoBean#getPermiso()
     */
    @Override
    public String getPermiso() {
        return null;
    }

    /**
     * @return the code
     * @author hfabra
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     * @author hfabra
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the userId
     * @author hfabra
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     * @author hfabra
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the contraseniaNueva
     * @author hfabra
     */
    public String getContraseniaNueva() {
        return contraseniaNueva;
    }

    /**
     * @param contraseniaNueva the contraseniaNueva to set
     * @author hfabra
     */
    public void setContraseniaNueva(String contraseniaNueva) {
        this.contraseniaNueva = contraseniaNueva;
    }

    /**
     * @return the contraseniaRepetida
     * @author hfabra
     */
    public String getContraseniaRepetida() {
        return contraseniaRepetida;
    }

    /**
     * @param contraseniaRepetida the contraseniaRepetida to set
     * @author hfabra
     */
    public void setContraseniaRepetida(String contraseniaRepetida) {
        this.contraseniaRepetida = contraseniaRepetida;
    }

}
