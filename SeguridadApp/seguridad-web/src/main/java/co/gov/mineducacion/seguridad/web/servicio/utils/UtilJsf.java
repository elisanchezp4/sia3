package co.gov.mineducacion.seguridad.web.servicio.utils;

import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.primefaces.context.RequestContext;

import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.UtilTexto;

/**
 * @author stilaguy
 * @version 1.0
 * @created 11-nov-2016 09:55:22 a.m.
 */
public class UtilJsf {

	private UtilJsf() {
		// Auto-generated method stub
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	public static void mensajeGlobal(SeguridadException error) {
		mostrarMensaje(null, error);
	}

	/**
	 * 
	 * @param formulario
	 * @param
	 */
	public static void mostrarMensaje(String formulario, SeguridadException error) {
		Severity severidad = null;
		switch (error.getTipo()) {
		case FATAL:
			severidad = FacesMessage.SEVERITY_FATAL;
			break;
		case ERROR:
			severidad = FacesMessage.SEVERITY_ERROR;
			break;
		case ALERTA:
			severidad = FacesMessage.SEVERITY_WARN;
			break;
		default:
			severidad = FacesMessage.SEVERITY_INFO;
			break;
		}
		FacesContext.getCurrentInstance().addMessage(formulario,
				new FacesMessage(severidad, error.getMessage(), error.getDetalle()));

	}

	public static void mostrarMensaje(Exception e) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
	}

	public static void mostrarMensaje(String ubicacionFormulario, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(ubicacionFormulario,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
	}

	public static void mostrarMensajeError(String ubicacionFormulario, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(ubicacionFormulario,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));
	}

	/***
	 * Permite obtener una instancia de un backing bean determinado a traves del
	 * nombre del mismo.
	 * 
	 * @param nombre
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T obtenerBackingBean(String nombre) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(context, expresionEl(nombre), Object.class);
	}

	public static String expresionEl(String texto) {
		if (UtilTexto.estaVacio(texto))
			return "";
		StringBuilder retorno = new StringBuilder("#{");
		retorno.append(texto);
		retorno.append("}");
		return retorno.toString();
	}

	/**
	 * Permite aplicar un valor a un parametro existente en un backingBean
	 * 
	 * @param destino
	 *            : ruta del valor al que se aplicara el parametro.
	 * @param parametro
	 *            : valor a aplicar al parametro.
	 */
	public static void aplicarParametro(String destino, Object parametro) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ValueExpression ve = fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(),
				expresionEl(destino), Object.class);
		ve.setValue(fc.getELContext(), parametro);
	}

	/**
	 * Permite crear una sesion y guardar un parametro dentro de la misma.
	 * 
	 * @param nombre:
	 *            parametro a guardar.
	 * @param valor:
	 *            a guardar asociado a la sesion.
	 * @throws Exception
	 */
	public static <T> void crearSession(String nombre, T valor) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(nombre, valor);
	}

	/**
	 * Permite cerrar la sesion.
	 */
	public static void cerrarSession() {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
	}

	/**
	 * permite navegar dentro del aplicativo haciendo uso de una regla de
	 * navegacion.
	 * 
	 * @param accion
	 */
	public static void navegar(String accion) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getApplication().getNavigationHandler().handleNavigation(fc, null, accion);
	}

	/**
	 * Permite navegar a la aplicacion hacia una ruta sin necesidad de
	 * encontrarse dentro de un backing bean.
	 * 
	 * @param accion
	 * @throws IOException
	 */
	public static void navegarFueraContexto(String accion) throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + accion);
	}

	public static void navegarNuevaPagina(String url) {
		RequestContext.getCurrentInstance().execute("window.open('" + url + "','_blank')");
	}
	
	public static void navigateSameWindow(URI url) {
		RequestContext.getCurrentInstance().execute("window.open('" + url + "','_self')");
	}

	/**
	 * Permite obtener la ruta del contexto del aplicativo.
	 * 
	 * @return
	 */
	public static String getContexto() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    if(request instanceof HttpServletRequest) {
	    	String url = ((HttpServletRequest) request).getRequestURL().toString();
	    	return url.substring(0, url.length() - ((HttpServletRequest) request).getRequestURI().length()) 
	    						+ ((HttpServletRequest) request).getContextPath();
	    } else {
	        return "";
	    }
	}

	public static synchronized String obtenerDescripcion(List<SelectItem> valores, String valor) {
		if (valores == null || valores.isEmpty() || UtilTexto.estaVacio(valor)) {
			return "";
		}
		for (SelectItem elemento : valores) {
			if (valor.equals(elemento.getValue())) {
				return elemento.getLabel();
			}
		}
		return "";
	}

	public static synchronized String obtenerDescripcion(List<SelectItem> valores, Long valor) {
		if (valores == null || valores.isEmpty() || valor == null) {
			return "";
		}
		for (SelectItem elemento : valores) {
			if (valor.equals(elemento.getValue())) {
				return elemento.getLabel();
			}
		}
		return "";
	}

	public static synchronized boolean compararListado(Object value, Object filter, List<SelectItem> valores) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		if (value == null || filter == null) {
			return false;
		}
		if (filter instanceof Long) {
			return UtilJsf.obtenerDescripcion(valores, (Long) filter).equals(value);
		}
		return UtilJsf.obtenerDescripcion(valores, filterText).equals(value);
	}

	public static synchronized boolean contener(Object value, Object filter) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		if (value == null) {
			return false;
		}
		return value.toString().trim().toUpperCase().contains(filterText.trim().toUpperCase());
	}

	public static synchronized boolean filtrarFecha(Object value, Object filter) {
		if (filter == null || filter.toString().trim().equals("")) {
			return true;
		}
		if (value == null) {
			return false;
		}
		return DateUtils.truncatedEquals((Date) value, (Date) filter, Calendar.DATE);
	}

	public static void mostrarMensajeExitoso(String string) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, string, null));

	}

}