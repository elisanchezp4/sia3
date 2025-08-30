package co.gov.mineducacion.seguridad.web.beans.general;

import static co.gov.mineducacion.seguridad.web.servicio.utils.ConstantesWeb.ESTRUCTURA_MENSAJE;
import static co.gov.mineducacion.seguridad.web.servicio.utils.ConstantesWeb.RUTA_PROPERTIES_ETIQUETAS;
import static co.gov.mineducacion.seguridad.web.servicio.utils.ConstantesWeb.RUTA_PROPERTIES_MENSAJES;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.faces.application.StateManager;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.UtilProperties;
import co.gov.mineducacion.seguridad.modelo.utils.UtilTexto;
import co.gov.mineducacion.seguridad.web.servicio.utils.UtilJsf;

/**
 * Clase abstracta de la cual heredan todas los controladores para las pantalla
 * de sistema. Provee a todas las pantallas de funcionalidad para el control de
 * permisos y el tratamiento de errores.
 * 
 * @author stilaguy
 * @version 1.0
 * @created 11-nov-2016 09:55:17 a.m.
 */
@SuppressWarnings("serial")
public abstract class ControladorAbstractoBean implements Serializable {

	private static final Logger logger = Logger.getLogger(ControladorAbstractoBean.class);

	private static ResourceBundle mensajes = ResourceBundle.getBundle(RUTA_PROPERTIES_MENSAJES);
	private static ResourceBundle etiquetas = ResourceBundle.getBundle(RUTA_PROPERTIES_ETIQUETAS);

	/***
	 * Este metodo se debe implementar en todos los backingBeans con la
	 * notacion @postcontruct. en este metodo se debe definir las operaciones
	 * iniciales cuando la pantalla inicia.
	 */
	public abstract void inicio();

	/**
	 * Permite definir el nombre del permiso asociado a la pantalla del
	 * backingbean, este corresponde al campo NOMBRE_PERMISO, debe ser igual al
	 * valor del campo en base de datos, sin espacios y en mayusculas. Si este
	 * valor se define como NULL implicara que la opcion no necesita permisos
	 * especificos por usuario para ingresar a la misma. No necesariamente que
	 * la opcion sea publica y se pueda ingresar sin autenticacion.
	 * 
	 * @return nombre del permiso asociado a la pantalla del backingbean
	 */
	public abstract String getPermiso();

	/**
	 * Permite obtener el archivo properties con el listado de los mensajes de
	 * error.
	 * 
	 * @return properties con los mensajes de error.
	 */
	public static ResourceBundle getResource() {
		return mensajes;
	}

	/**
	 * Permite obtener el archivo properties con el listado de las etiquetas
	 * usadas en los textos..
	 * 
	 * @return properties con las etiquetas usadas en las pantallas..
	 */
	public static ResourceBundle getEtiquetas() {
		return etiquetas;
	}

	public static String valorEtiqueta(String etiqueta) {
		if (!UtilTexto.estaVacio(etiqueta)) {
			return UtilProperties.cargarPropiedad(etiquetas, etiqueta, null);
		}
		return "";
	}

	public static String valorEtiqueta(String etiqueta, String[] parametros) {
		if (!UtilTexto.estaVacio(etiqueta)) {
			return UtilProperties.cargarPropiedad(etiquetas, etiqueta, parametros);
		}
		return "";
	}

	/***
	 * Permite mostrar un mensaje de error en un lugar especifico de la
	 * pantalla.
	 * 
	 * @param form:
	 *            ubicacion en el formulario donde se mostrara el error,
	 *            corresponde al id del componente al que esta asociado el
	 *            message. se debe definir para el mismo la ruta completa.
	 * @param error:
	 *            Contiene la infromacion del error a mostrar: tipo, mensaje,
	 *            codigo de la excepcion.
	 */
	public static void procesarMensajeEspecifico(String form, SeguridadException error) {
		UtilJsf.mostrarMensaje(form, procesarMensaje(error));
	}

	private static SeguridadException procesarMensaje(SeguridadException error) {
		if (error.getId() != null && UtilTexto.estaVacio(error.getMessage())) {
			String textoMensaje = UtilProperties.cargarPropiedad(mensajes, ESTRUCTURA_MENSAJE + error.getId(),
					error.getParametros());
			return new SeguridadException(error.getId(), error.getTipo(), textoMensaje, error.getParametros());
		}
		return error;
	}

	/**
	 * Procesa un error para visualizarlo correctamente al usuario
	 * 
	 * @param error
	 * @throws SeguridadException
	 */
	public static void procesarError(Exception error) {
		SeguridadException errorSeguridad = null;
		if (!(error instanceof SeguridadException)) {
			logger.error(error, error);
			errorSeguridad = new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
		} else {
			errorSeguridad = (SeguridadException) error;
			logger.error(errorSeguridad.getId(), error);
		}
		errorSeguridad = procesarMensaje(errorSeguridad);
		UtilJsf.mensajeGlobal(errorSeguridad);
		procesarAnidados(null, errorSeguridad);
	}

	public static void procesarError(String form, Exception error) {
		SeguridadException errorSeguridad = null;
		if (!(error instanceof SeguridadException)) {
			logger.error(error, error);
			errorSeguridad = new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
		} else {
			errorSeguridad = (SeguridadException) error;
			logger.error(errorSeguridad.getId(), error);
		}
		errorSeguridad = procesarMensaje(errorSeguridad);
		UtilJsf.mostrarMensaje(form, errorSeguridad);
		procesarAnidados(form, errorSeguridad);
	}

	private static void procesarAnidados(String form, SeguridadException errorSeguridad) {
		if (errorSeguridad.getErrores() == null || errorSeguridad.getErrores().isEmpty()) {
			return;
		}
		for (SeguridadException detalle : errorSeguridad.getErrores()) {
			detalle = procesarMensaje(detalle);
			UtilJsf.mostrarMensaje(form, detalle);
		}
	}

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	public Object getSessionObject(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
	}
	
	public void activarMsgParaRedireccionar(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Flash flash = externalContext.getFlash();
		flash.setKeepMessages(true);
	}
	
	/**
	 * @author mamora
	 * @param file - Archivo para visualizar volcando su contenido al browser
	 * Metodo encargado de visualizarArchivo en el navegador web volcando su contenido en el mismo  
	 */
	public boolean generarArchivoNavegador(File file){
		boolean resultado = false;
		FacesContext faces = FacesContext.getCurrentInstance();
		try {
			Path path = Paths.get(file.getAbsolutePath());
			byte[] bytes = Files.readAllBytes(path);
			if (bytes == null || bytes.length<= 0){
				return false;
			}
			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			response.setHeader("Window-target:","_blank"); 
			response.setHeader("Content-disposition", "inline");
			response.setHeader("Cache-Control", "cache, must-revalidate");
			response.setHeader("Pragma", "public");
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			resultado = true;
		} catch (Exception e) {
			procesarError(e);
		}
		StateManager stateManager = faces.getApplication().getStateManager();
		stateManager.saveSerializedView(faces);
		faces.responseComplete();
		return resultado;
	}
	
	
}