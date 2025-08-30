/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.mineducacion.seguridad.web.servicio.utils;

/**
 * Constantes utilizadas en los servicios.
 * 
 * @author jsoto
 */
public class ConstantesWeb {

	private ConstantesWeb() {
		// Constructor agregado por sugerencia de SONAR
	}

	// Tipos de datos consumidos o producidos por los servicios
	public static final String APPLICATION_JSON = "application/json";
	public static final String TEXT_PLAIN = "text/plain";
	
	// Constantes utilizadas en las etiquetas
	
	public static final String RUTA_PROPERTIES_MENSAJES="resources.mensajes_es";
	public static final String RUTA_PROPERTIES_ETIQUETAS="resources.etiquetas_es";
	public static final String ESTRUCTURA_MENSAJE="MSJ_MEN_WS_";
	public static final String ESTRUCTURA_MENSAJE_GRAL="MSJ_GRL_";
	public static final String CREDENCIAL="CREDENCIAL";
}
