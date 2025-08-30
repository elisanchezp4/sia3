package co.gov.mineducacion.seguridad.modelo.utils;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;

import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

/**
 * esta clase permite ofrecer utilidad para el procesamiento de archivos de
 * propiedades.
 * 
 * @author stilaguy
 *
 */
public final class UtilProperties {
	private UtilProperties(){/* Recomendacion por sonar */}

	private static final Logger logger = Logger.getLogger(UtilProperties.class);

	public static synchronized Properties leerProperties(Class<?> contexto) throws SeguridadException {
		Properties archivo = new Properties();
		try {
			archivo.load(contexto.getResourceAsStream("resources/mensajes"));
		} catch (Exception e) {
			throw new SeguridadException("No se puede leer el archivo de propiedades.");
		}
		return archivo;
	}

	/**
	 * Retorna valor de la propiedad formateada
	 * @param archivo archivo donde se encuentra la propiedad
	 * @param id nombre de la propiedad
	 * @param parametros parametros a remplazar dentro del texto de la propiedad
	 * @return
	 */
	public static String cargarPropiedad(ResourceBundle archivo, String id, Object[] parametros) {
		return MessageFormat.format(archivo.getString(id), parametros);
	}

	/**
	 * Retorna valor de la propiedad
	 * @param archivo archivo donde se encuentra la propiedad
	 * @param id nombre de la propiedad
	 * @return
	 * @throws SeguridadException
	 */
	public static String cargarPropiedad(ResourceBundle archivo, String id) throws SeguridadException {
		try {
			return archivo.getString(id);
		} catch (Exception e) {
			throw new SeguridadException("No se puede leer el archivo de propiedades.");
		}
	}

	public static synchronized Properties loadProperties(String ruta){
		Properties properties = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream(ruta)) {
			properties.load(fileInputStream);
		} catch (Exception e) {
			logger.error("No se puede leer el archivo de propiedades: " + new Gson().toJson(e));
		}
		return properties;
	}

}
