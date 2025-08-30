package co.gov.mineducacion.seguridad.modelo.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Clase que proporciona operaciones para administrar la sesion (Http) del usuario conectado a la aplicacion
 * @author hfabra
 *
 */
public class SessionUtils {
	private SessionUtils(){/* Recomendacion por sonar */}

	/**
	 * Retorna la sesion HTTP del usuario conectado a la aplicacion
	 * @return
	 */
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	/**
	 * Retorna el objeto Request asociado a la peticion realizada por el usuario (web) de la aplicacion
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * Retorna el atributo 'username' asociado a la sesion http del usuario conectado a la aplicacion
	 * @return
	 */
	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	/**
	 * Retorna el atributo 'userid' asociado a la sesion http del usuario conectado a la aplicacion 
	 * @return
	 */
	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}
}