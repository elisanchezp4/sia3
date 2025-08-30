package co.gov.mineducacion.sdk.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.HttpHeaders;

import co.gov.mindecuacion.sdk.servicio.AutorizacionService;
import co.gov.mindecuacion.sdk.servicio.SeguridadException;
import co.gov.mineducacion.sdk.filter.TokenFilter;
import co.gov.mineducacion.sdk.filter.TokenFilterAngular;

/**
 * Contiene métodos estáticos que permiten configurar el SDK. Contiene métodos estáticos para determinar si con los encabezados recibidos
 * se superan los filtros de seguridad y para actualizar la fecha de vencimiento, éstos pueden ser utilizados por la aplicación cliente
 * al procesar URLs desprotegidas que no se tuvieron en cuenta por los filtros TokenFilter y TokenFilterAngular. 
 * @author Asesoftware - Javier Estévez
 *
 */
public class FiltroSeguridad {
	private FiltroSeguridad(){/* Recomendacion por sonar */}
	
	/**
	 * Variable estática que determina si el SDK se encuentra configurado, si su valor es falso
	 * se retornará siempre un error por cada petición interceptada por los diferentes filtros.
	 */
	private static boolean configurado =  false;
	
	
	private static final String STR_NULL = "null";
	private static Map<String, Date> cacheTokens = CacheTokens.CACHE_TOKENS;
	private static int segundosDiferenciaCache;
	private static int msDiferenciaCache;
	
	/**
	 * contiene las propiedades que configuran el SDK leidas desde un fichero externo
	 */
	private static Properties propiedades;
	
	private static final String ACCESS_TOKEN = "access_token";
	private static final String CLIENT_ID = "client_id";
	private static final String USER_ID = "user_id";
	
	/**
	 * Método que configura el SDK, inicializa todos los filtros y configura la lectura de los parámetros
	 * del SDK, si no se presenta ningún error establece la propiedad 'configurado' en verdadero.
	 */
	public static void configurar(String rutaArchivoConfiguracion){
		
		InputStream is = null;
		
		
		try{
			FiltroSeguridad.propiedades = new Properties();
			is = new FileInputStream(rutaArchivoConfiguracion);
			FiltroSeguridad.propiedades.load(is);
			
						
			segundosDiferenciaCache = Integer.parseInt(FiltroSeguridad.propiedades.getProperty("segundos_diferencia_cache"));
			msDiferenciaCache = segundosDiferenciaCache * 1000;
			
			TokenFilterAngular.configurar(FiltroSeguridad.propiedades);
			TokenFilter.configurar(FiltroSeguridad.propiedades);
			
			FiltroSeguridad.configurado = true;
			
		}catch(IOException e) {
			
			propiedades = null;
			FiltroSeguridad.configurado = false;
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "ERROR: Imposible configurar el SDK. IOException: " +e.getMessage(), e);
			
		}catch(Exception e){
			
			propiedades = null;
			FiltroSeguridad.configurado = false;
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "ERROR: Imposible configurar el SDK. Exception: " +e.getMessage(), e);
			
		}finally{
			
			try{
				if(is != null){
					is.close();
				}
				
			}catch(IOException e){
				
				Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "ERROR al cerrar archivo de configuración. IOException: " +e.getMessage(), e);
			}
		}
		
		

	}
	
	/**
	 * retonra el valor de la propiedad estática 'configurado' la cual indica si el SDK ha sido configurado
	 * satisfactoriamente
	 * @return
	 */
	public static boolean estaConfigurado(){
		return FiltroSeguridad.configurado;
	}
	
	/**
	 * Retorna las propiedades de configuración del SDK
	 * @return
	 */
	public static Properties obtenerPropiedades(){
		return propiedades;
	}
	
	/**
	 * Retorna verdadero si se superan los filtros de seguridad, de lo contrario retorna falso
	 * @param token
	 * @param appId
	 * @param userId
	 * @return
	 */
	public static boolean superaFiltrosSeguridad(String token, String appId, String userId){
		
		if(!FiltroSeguridad.configurado){
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "ERROR:  El SDK no está configurado, Use FiltroSeguridad.configurar(String rutaArchivoConfiguracion)");
			return Boolean.FALSE;
		}
		
		try{
			filtrarSeguridad(token, appId, userId);
			return Boolean.TRUE;
		} catch (SeguridadException e){
			return Boolean.FALSE;
		}
	}
	
	public static boolean superaFiltrosSeguridad(HttpHeaders headers){
		
		if(!FiltroSeguridad.configurado){
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "ERROR:  El SDK no está configurado, Use FiltroSeguridad.configurar(String rutaArchivoConfiguracion)");
			return Boolean.FALSE;
		}
		
		try{
	    	String token = headers.getRequestHeader(FiltroSeguridad.ACCESS_TOKEN).get(0);
			String userId = headers.getRequestHeader(FiltroSeguridad.USER_ID).get(0);
			String appId = headers.getRequestHeader(FiltroSeguridad.CLIENT_ID).get(0);
			
			filtrarSeguridad(token, appId, userId);
			return Boolean.TRUE;
		} catch (SeguridadException e){
			return Boolean.FALSE;
		}
	}
	
	/**
	 * Valida que el token se reciba y se encuentre activo, si no se recibe o no se encuentra activo
	 * se retorna el error SDK_FS_001     
	 * @throws SeguridadException 
	 */
	public static void filtrarSeguridad(HttpHeaders headers) throws SeguridadException{
		String token = headers.getRequestHeader(FiltroSeguridad.ACCESS_TOKEN).get(0);
		String userId = headers.getRequestHeader(FiltroSeguridad.USER_ID).get(0);
		String appId = headers.getRequestHeader(FiltroSeguridad.CLIENT_ID).get(0);
		filtrarSeguridad(token, appId, userId);
	}
	/**
	 * Valida que el token se reciba y se encuentre activo, si no se recibe o no se encuentra activo
	 * se retorna el error SDK_FS_001     
	 * @throws SeguridadException 
	 */
	public static void filtrarSeguridad(String token, String appId, String userId) throws SeguridadException{
		
		if(!FiltroSeguridad.configurado){
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "ERROR:  El SDK no está configurado, Use FiltroSeguridad.configurar(String rutaArchivoConfiguracion)");
			throw new SeguridadException("SDK_FS_005","SDK_FS_005");
		}
		
		if(token != null  && !token.isEmpty() && !token.equalsIgnoreCase(FiltroSeguridad.STR_NULL)){
			if (cacheTokens.containsKey(token)) {			
				if (new Date().getTime() < cacheTokens.get(token).getTime() - (FiltroSeguridad.msDiferenciaCache)) {
					Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.INFO, "Confirmacion de filtrarSeguridad correcta!!");
				} else {
					actualizarFechaVencimiento(token, appId, userId);
				}		
			} else {		
				actualizarFechaVencimiento(token, appId, userId);
			}			
		} else {
			throw new SeguridadException("SDK_FS_001","SDK_FS_001");
			
		}
	}
	

	/**
	 * Si se encuentra activo el token, se actualiza su fecha de vencimiento en el caché, de lo
	 * contrario se retorna el respectivo error
	 */
	private static void actualizarFechaVencimiento(String token, String appId, String userId) throws SeguridadException {
		Date fechaVencimiento;
		try {
			fechaVencimiento = AutorizacionService.actualizarFechaVencimiento(token, appId, new Integer(userId));			
			if (!fechaVencimiento.after(new Date())) {
				AutorizacionService.finalizarSesion(token, appId, new Integer(userId));
				throw new SeguridadException("SDK_FS_002","SDK_FS_002");
			} else {
				cacheTokens.put(token, fechaVencimiento);
			}
			
		} catch (NumberFormatException e) {
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "NumberFormatException: " +e.getMessage(), e);
			throw new SeguridadException(e.getMessage(),"SDK_FS_003");
			
		} catch (SeguridadException e) {
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "SeguridadException: " +e.getMessage(), e);
			throw e;
			
		} catch (Exception e) {
			Logger.getLogger(FiltroSeguridad.class.getName()).log(Level.SEVERE, "Exception: " +e.getMessage(), e);
			throw new SeguridadException(e.getMessage(),"SDK_FS_004");
		}
	}
}
