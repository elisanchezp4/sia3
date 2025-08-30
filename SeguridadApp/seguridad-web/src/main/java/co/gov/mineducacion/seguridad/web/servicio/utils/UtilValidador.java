package co.gov.mineducacion.seguridad.web.servicio.utils;

import static co.gov.mineducacion.seguridad.web.beans.general.ControladorAbstractoBean.getEtiquetas;
import static co.gov.mineducacion.seguridad.web.beans.general.ControladorAbstractoBean.getResource;
import static co.gov.mineducacion.seguridad.web.servicio.utils.ConstantesWeb.ESTRUCTURA_MENSAJE_GRAL;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import co.gov.mineducacion.seguridad.modelo.utils.UtilProperties;
import co.gov.mineducacion.seguridad.modelo.utils.UtilTexto;

public final class UtilValidador {

	private UtilValidador() {
		// Auto-generated method stub
	}

	private static ResourceBundle mensajes = getResource();
	private static ResourceBundle etiquetas = getEtiquetas();
	private static final int MEN_OBLIGATORIO = 1;
	private static final int MEN_LONGITUD_MAX = 2;
	private static final int MEN_TEXTO_CORREO = 17;
	private static final String MEN_ESPECIFICIO = "_ESP";

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static boolean cargarMensaje(int id, List<String> parametros, String ubicacionFormulario,
			String etiquetaEspecifica) {
		boolean especifico = false;
		if (parametros == null) {
			parametros = new ArrayList<>();
		}
		if (!UtilTexto.estaVacio(etiquetaEspecifica)) {
			parametros.add(0, UtilProperties.cargarPropiedad(etiquetas, etiquetaEspecifica, null));
			especifico = true;
		}
		UtilJsf.mostrarMensaje(ubicacionFormulario, UtilProperties.cargarPropiedad(mensajes,
				ESTRUCTURA_MENSAJE_GRAL + id + (especifico ? MEN_ESPECIFICIO : ""), parametros.toArray()));
		return false;
	}

	public static boolean validar(Long valor, String ubicacionFormulario, boolean obligatorio, int longitud,
			String etiquetaEspecifica) {
		if (obligatorio && valor == null) {
			return cargarMensaje(MEN_OBLIGATORIO, new ArrayList<>(), ubicacionFormulario, etiquetaEspecifica);
		}
		if (valor != null && valor.toString().length() > longitud) {
			List<String> parametros = new ArrayList<>();
			parametros.add(Integer.toString(longitud));
			return cargarMensaje(MEN_LONGITUD_MAX, parametros, ubicacionFormulario, etiquetaEspecifica);
		}
		return true;
	}

	public static boolean validar(String valor, String ubicacionFormulario, boolean obligatorio, int longitud,
			String etiquetaEspecifica) {
		if (obligatorio && UtilTexto.estaVacio(valor)) {
			return cargarMensaje(MEN_OBLIGATORIO, new ArrayList<>(), ubicacionFormulario, etiquetaEspecifica);
		}
		if (valor != null && valor.length() > longitud) {
			List<String> parametros = new ArrayList<>();
			parametros.add(Integer.toString(longitud));
			return cargarMensaje(MEN_LONGITUD_MAX, parametros, ubicacionFormulario, etiquetaEspecifica);
		}
		return true;
	}

	public static boolean validar(String valor, String ubicacionFormulario, boolean obligatorio) {
		String error = "";
		if (obligatorio && (valor == null || valor.isEmpty())) {
			error = "Campo Obligatorio";
			UtilJsf.mostrarMensajeError(ubicacionFormulario, error);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public static boolean validarLongitud(String valor, String ubicacionFormulario, int longitudMaxima) {
		String error = "";
		if (valor.length() > longitudMaxima) {
			error = "Campo muy largo";
			UtilJsf.mostrarMensajeError(ubicacionFormulario, error);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public static boolean validarCorreo(String valor, String ubicacionFormulario, boolean obligatorio, int longitud,
			String etiquetaEspecifica) {
		if (obligatorio && UtilTexto.estaVacio(valor)) {
			return cargarMensaje(MEN_OBLIGATORIO, new ArrayList<>(), ubicacionFormulario, etiquetaEspecifica);
		}
		if (valor != null && valor.length() > longitud) {
			List<String> parametros = new ArrayList<>();
			parametros.add(Integer.toString(longitud));
			return cargarMensaje(MEN_LONGITUD_MAX, parametros, ubicacionFormulario, etiquetaEspecifica);
		}
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		if (!UtilTexto.estaVacio(valor) && !pattern.matcher(valor).matches()) {
			return cargarMensaje(MEN_TEXTO_CORREO, null, ubicacionFormulario, etiquetaEspecifica);
		}
		return true;
	}

	public static boolean validarExtension(String valor, String[] extensiones) {
		if (UtilTexto.estaVacio(valor)) {
			return true;
		}
		for (String extension : extensiones) {
			if (valor.endsWith(extension))
				return true;
		}
		return false;
	}

	public static boolean validar(Timestamp valor, String ubicacionFormulario, boolean obligatorio,
			String etiquetaEspecifica) {
		if (obligatorio && valor == null) {
			return cargarMensaje(MEN_OBLIGATORIO, new ArrayList<>(), ubicacionFormulario, etiquetaEspecifica);
		}
		return true;
	}

	public static boolean validar(Date valor, String ubicacionFormulario, boolean obligatorio,
			String etiquetaEspecifica) {
		if (obligatorio && valor == null) {
			return cargarMensaje(MEN_OBLIGATORIO, new ArrayList<>(), ubicacionFormulario, etiquetaEspecifica);
		}
		return true;
	}

}
