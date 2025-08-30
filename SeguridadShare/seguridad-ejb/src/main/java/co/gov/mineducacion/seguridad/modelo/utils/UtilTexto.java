package co.gov.mineducacion.seguridad.modelo.utils;

/**
 * Esta clase permite proveer validaciones estandar para el procesamiento de
 * cadenas de caracteres.
 * 
 * @author stilaguy
 * @version 1.0
 * @created 11-nov-2016 09:55:22 a.m.
 */
public final class UtilTexto {
	private UtilTexto(){/* Recomendacion por sonar */}

	/**
	 * Permite determinar sin un texto se encuentra vacio.
	 * 
	 * @param texto:
	 *            a validar.
	 */
	public static synchronized boolean estaVacio(String texto) {
		return texto == null || texto.isEmpty();
	}

	/**
	 * Permite remover la puntuacion y los signos en un texto.
	 * 
	 * @param valor
	 */
	public static synchronized String removerPuntuacion(String valor) {
		if (estaVacio(valor))
			return valor;
		return valor.replaceAll("\\p{Punct}", "");
	}

}