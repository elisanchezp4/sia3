package co.gov.mineducacion.seguridad.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import co.gov.mineducacion.seguridad.ejb.servicios.IAplicacion;
import co.gov.mineducacion.seguridad.ejb.servicios.ISesion;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SessionUtils;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import co.gov.mineducacion.seguridad.web.beans.general.ControladorAbstractoBean;
import co.gov.mineducacion.seguridad.web.servicio.utils.UtilJsf;
import co.gov.mineducacion.seguridad.web.servicio.utils.UtilValidador;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * ManageBean para la vista login.xhtml que permite a un usuario autenticarse y crear una sesión
 * en el sistema de seguridad
 * 
 * @author hfabra
 * @version 1.0
 * @created 11-nov-2016 09:55:19 a.m.
 */
@ManagedBean
@SessionScoped
public class LoginBean extends ControladorAbstractoBean implements Serializable {

	private static final long serialVersionUID = -2262128937158226450L;
	
	private static final Logger logger = Logger.getLogger(LoginBean.class);
	private static final String ID_APLICACION = "idAplicacion";
	private static final String LABEL_REGISTRO = "labelRegistro";
	private static final String NOMBRE_APLICACION = "nombreAplicacion";
	private static final String URL_REGISTRO = "urlRegistro";
	private static final String ES_ACTIVO_REGISTRO = "esActivoRegistro";

	private String nombreAplicacion;
	private String urlRegistro;
	private String labelRegistro;
	private Boolean esActivoRegistro;
	private String usuario;
	private String contrasenia;
	private String captchaInput;
	private String clientId;
	private AplicacionesDTO appDto;

	@EJB
	private IAplicacion app;

	@EJB
	private ISesion sesionLogin;
	
	@EJB
	protected NegocioUsuarios negocioUsuarios;
	
	@EJB
	protected NegocioUsuariosRol negocioUsuariosRol;

	transient OAuthAuthzRequest oauthRequest = null;

	/**
	 * Inicializa el ManagedBean
	 */
	@PostConstruct
	public void init() {
		String urlReg;
		String labelReg;
		try {
			HttpServletRequest request = SessionUtils.getRequest();

			String session = request.getParameter("session_out")+"";
			
			if (session.equals("true")) {
				clientId = request.getParameter("client_id")+"";
				appDto = app.buscarAplicacion(clientId);
				this.setNombreAplicacion(appDto.getNombre());
				procesarError(new SeguridadException(SeguridadException.ID_MSG_SESION_CADUCADA, TipoExcepcion.ERROR));
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Su sesión ha caducado. Ingrese nuevamente.", "Su sesión ha caducado. Ingrese nuevamente."));
			} else {
				oauthRequest = new OAuthAuthzRequest(request);
				clientId = oauthRequest.getParam(OAuth.OAUTH_CLIENT_ID);
				appDto = app.buscarAplicacion(clientId);
				this.setNombreAplicacion(appDto.getNombre());
			}			
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession sesHttp = (HttpSession) facesContext.getExternalContext().getSession(true);
			sesHttp.setAttribute(ID_APLICACION, clientId);
			sesHttp.setAttribute(NOMBRE_APLICACION, this.nombreAplicacion);

			urlReg =  request.getParameter(URL_REGISTRO);
			if(urlReg != null && !urlReg.trim().equals(Constantes.CADENA_VACIA)){
				this.urlRegistro = urlReg;
				sesHttp.setAttribute(URL_REGISTRO, urlReg);
			}

			labelReg =  request.getParameter(LABEL_REGISTRO);
			if(labelReg != null && !labelReg.trim().equals(Constantes.CADENA_VACIA)){
				this.labelRegistro = labelReg;
				sesHttp.setAttribute(LABEL_REGISTRO, labelReg);
			}

			if(urlReg != null && !urlReg.trim().equals(Constantes.CADENA_VACIA)
					&& labelReg != null && !labelReg.trim().equals(Constantes.CADENA_VACIA)){

				this.esActivoRegistro = true;
				sesHttp.setAttribute(ES_ACTIVO_REGISTRO, Boolean.TRUE);

			} else {

				this.esActivoRegistro = false;
			}

			sesHttp.setAttribute(ID_APLICACION, clientId);
			sesHttp.setAttribute(NOMBRE_APLICACION, this.nombreAplicacion);


		} catch (OAuthProblemException | OAuthSystemException e) {

			logger.info(e.getMessage());

		} catch(Exception e){

			logger.error("Exception: " + e.getMessage(), e);
		}

	}

	public void inicio() {
		// Auto-generated method stub
	}

	public void check(){
		try {
			autenticar();

		} catch (Exception e2) {
			logger.error(e2);
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Código correcto",null));
	}




	/**
	 * boton de ingresar. valida la informacion de usuario e inicia la sesion.
	 *
	 * @throws IOException
	 */
	public Response autenticar() {
		HttpServletRequest request = SessionUtils.getRequest();
		try {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession sesHttp = (HttpSession) facesContext.getExternalContext().getSession(false);
			if(clientId == null || clientId.trim().equals("")){

				if(sesHttp.getAttribute(ID_APLICACION) != null){

					clientId = (String)sesHttp.getAttribute(ID_APLICACION);

				} else {

					throw new SeguridadException(SeguridadException.ID_MSG_ERR_APP_NO_ENCONTRADA, TipoExcepcion.ALERTA);
				}
			}

			this.usuario = this.usuario.trim();
			validar();

			appDto = app.buscarAplicacion(clientId);

			if(appDto == null){

				throw new SeguridadException(SeguridadException.ID_MSG_ERR_APP_NO_ENCONTRADA, TipoExcepcion.ALERTA);

			}

			String code = sesionLogin.iniciarSesion(usuario, contrasenia, clientId);
			Usuario userActive = negocioUsuarios.consultarUsuario(usuario);


			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
					HttpServletResponse.SC_FOUND);
			// Agrega el codigo de autorizacion retornada
			builder.setCode(code);
			builder.setParam("user_id", userActive.getUsuarioId());


			String redirectURI = appDto.getUrlInicioExitoso();

			// Agrega a la url de la aplicacion cliente el codigo de
			// autorizacion
			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
			URI url = new URI(response.getLocationUri());
			logger.info("LoginBean--->"+response.getLocationUri());
			sesHttp.invalidate();

			UtilJsf.navigateSameWindow(url);

			return Response.status(response.getResponseStatus()).location(url).build();

			// llamado servio
			// redirect con codigo autorizacion
		} catch (Exception e) {
			procesarError(e);
		}
		return null;
	}

	private void validar() throws SeguridadException {
		boolean user;
		boolean password;
		user = UtilValidador.validar(usuario, "loginForm:usuario", true);
		password = UtilValidador.validar(contrasenia, "loginForm:contrasenia", true);
		if (!user || !password) {
			throw new SeguridadException(SeguridadException.ID_MSG_WEB_CAMPOS_OBLIGATORIOS, TipoExcepcion.ERROR);
		}

		user = UtilValidador.validarLongitud(usuario, "loginForm:usuario", Constantes.LONGITUD_CODIGO_USUARIO);
		password = UtilValidador.validarLongitud(contrasenia, "loginForm:contrasenia", Constantes.LONGITUD_CLAVE_USUARIO);
		if (!user || !password) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERR_LONGITUD_CAMPOS, TipoExcepcion.ERROR);
		}

	}

	/**
	 * Getters y setters.
	 *
	 * @return
	 */
	public String getNombreAplicacion() {

		if(nombreAplicacion == null || nombreAplicacion.trim().equals(Constantes.CADENA_VACIA)){

			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession sesHttp = (HttpSession) facesContext.getExternalContext().getSession(false);
			if(sesHttp != null && sesHttp.getAttribute(NOMBRE_APLICACION) != null){

				nombreAplicacion = (String)sesHttp.getAttribute(NOMBRE_APLICACION);

			}
		}

		return nombreAplicacion;
	}

	public String getUrlRegistro() {

		if(urlRegistro == null || urlRegistro.trim().equals(Constantes.CADENA_VACIA)){

			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession sesHttp = (HttpSession) facesContext.getExternalContext().getSession(false);
			if(sesHttp != null && sesHttp.getAttribute(URL_REGISTRO) != null){

				urlRegistro = (String)sesHttp.getAttribute(URL_REGISTRO);

			}
		}

		return urlRegistro;
	}

	public void setUrlRegistro(String urlRegistro) {
		this.urlRegistro = urlRegistro;
	}

	public String getLabelRegistro() {

		if(labelRegistro == null || labelRegistro.trim().equals(Constantes.CADENA_VACIA)){

			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession sesHttp = (HttpSession) facesContext.getExternalContext().getSession(false);
			if(sesHttp != null && sesHttp.getAttribute(LABEL_REGISTRO) != null){

				labelRegistro = (String)sesHttp.getAttribute(LABEL_REGISTRO);

			} 
		}
		return labelRegistro;
	}

	public void setLabelRegistro(String labelRegistro) {
		this.labelRegistro = labelRegistro;
	}

	public Boolean getEsActivoRegistro() {
		
		if(esActivoRegistro == null){
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession sesHttp = (HttpSession) facesContext.getExternalContext().getSession(false);
			if(sesHttp != null && sesHttp.getAttribute(ES_ACTIVO_REGISTRO) != null){
			
				esActivoRegistro = (Boolean)sesHttp.getAttribute(ES_ACTIVO_REGISTRO);

			} else {
				
				esActivoRegistro = Boolean.FALSE;
			}
		}
		
		return esActivoRegistro;
	}

	public void setEsActivoRegistro(Boolean esActivoRegistro) {
		this.esActivoRegistro = esActivoRegistro;
	}

	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}
	
	public void irUrlRegistro(){
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    try {
	    	
	    	externalContext.redirect(this.getUrlRegistro());
	    	
		} catch (IOException e) {
			 
			logger.error("IOException: "+e.getMessage() + e);
		}
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public AplicacionesDTO getAppDto() {
		return appDto;
	}

	public void setAppDto(AplicacionesDTO appDto) {
		this.appDto = appDto;
	}

	@Override
	public String getPermiso() {
		return null;
	}

	/**
	 * @author hfabra
	 * @return the capchaInput
	 */
	public String getCaptchaInput() {
		return captchaInput;
	}

	/**
	 * @author hfabra
	 * @param capchaInput the capchaInput to set
	 */
	public void setCaptchaInput(String captchaInput) {
		this.captchaInput = captchaInput;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public IAplicacion getApp() {
		return app;
	}

	public void setApp(IAplicacion app) {
		this.app = app;
	}
	
}
