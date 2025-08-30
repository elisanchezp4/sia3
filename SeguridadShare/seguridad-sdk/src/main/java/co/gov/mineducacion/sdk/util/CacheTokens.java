package co.gov.mineducacion.sdk.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Contiene los tokens almacenados en cache
 * @author Asesoftware - Javier Estévez 
 *
 */
public class CacheTokens {
	private CacheTokens(){/* Recomendacion por sonar */}

	protected static final Map<String, Date> CACHE_TOKENS = new HashMap<>();

	public static  Map<String, Date> getCacheTokens(){
		return CACHE_TOKENS;
	}
}
