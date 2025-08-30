/**
 * 
 */
package co.gov.mineducacion.seguridad.modelo.utils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;

import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;

/**
 * Clase que realiza la generacion de codigo
 * 
 * @author hfabra
 * 
 */
public class UtilToken {
	private UtilToken(){/* Recomendacion por sonar */}

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMEROS = "0123456789";
	private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	private static final String CARACTERES_ESPECIALES = "@$#!%*";
	private static SecureRandom rnd = new SecureRandom();
	private static final int INDICE_FINAL_NUMEROS = 9;
	private static final int INDICE_FINAL_MAYUSCULAS = 35;


	public static String generateRandomPassword(int length) {
		String randomSpecialCharacter = String.valueOf(CARACTERES_ESPECIALES.charAt(rnd.nextInt(CARACTERES_ESPECIALES.length())));
		return  randomString(length) + randomSpecialCharacter;
	}
	
	/**
	 * Retorna el token generado
	 * @param length
	 * @return el token generado
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public static String randomString(int length) {
	   
	   int cantMayusculas;
	   int cantMinusculas;
	   int cantNumeros;
	   int registroCaracter;
	   
	   for(int intentos = Constantes.UNO; intentos <= Constantes.INTENTOS_GENERACION_CLAVE; intentos++){
		   
		   cantMayusculas = Constantes.CERO;
		   cantMinusculas = Constantes.CERO;
		   cantNumeros = Constantes.CERO;
		   StringBuilder sb = new StringBuilder(length);
		   
		   for( int i = 0; i < length; i++ ){
		       
			   int index = rnd.nextInt(AB.length());
		       
		       if(index <= UtilToken.INDICE_FINAL_NUMEROS){
		    	   
		    	   cantNumeros++;
		    	   
		       } else if (index <= UtilToken.INDICE_FINAL_MAYUSCULAS){
		    	   
		    	   cantMayusculas++;
		    	   
		       } else {
		    	   
		    	   cantMinusculas++;
		    	   
		       }
		       
		       sb.append(AB.charAt(index));
			   
		   }
		   
		   if(cantNumeros > Constantes.CERO
				   && cantMayusculas > Constantes.CERO
				   && cantMinusculas > Constantes.CERO){
		   
			   return sb.toString();
		   } 
	   
	   }
	   
	   StringBuilder sb = new StringBuilder(length);
	   registroCaracter = Constantes.UNO;
	   
	   for( int i = 0; i < length; i++ ){
	       
		   int index;
	       
		   switch(registroCaracter){
		   case 1:
			   index = rnd.nextInt(MINUSCULAS.length());
		       sb.append(MINUSCULAS.charAt(index));
		       break;
		   case 2:
			   index = rnd.nextInt(NUMEROS.length());
		       sb.append(NUMEROS.charAt(index));
		       break;
		   case 3:
			   index = rnd.nextInt(MAYUSCULAS.length());
		       sb.append(MAYUSCULAS.charAt(index));
		       break;
			   default:
				   break;
		   }
		   
	       registroCaracter++;
	       
	       if(registroCaracter == 4){
	    	   registroCaracter = Constantes.UNO;
	       }
	   }

	   return sb.toString();
	}
	
	/**
	 * Retorna el tiempo actual en milisegundos, para luego verificar 
	 * si el token sigue activo
	 * @return el tiempo actual en milisegundos
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public static Timestamp obtenerFechaActual() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * M&eacute;toddo que arma el objeto a insertar en el token
	 * @param code
	 * @param tokenType
	 * @param usuario
	 * @param minutes
	 * @return
	 * @author hfabra
	 * @since 9/02/2017
	 */
	public static TokensActivosDTO armarTokenDTO(String code,
			BigDecimal tokenType, BigDecimal usuario, BigDecimal minutes) {
		TokensActivosDTO token = new TokensActivosDTO();
		token.setTokenId(code);
		token.setTipo(tokenType);
		token.setUsuarioId(usuario);
		token.setCreacion(obtenerFechaActual());
		token.setVencimiento(new Timestamp(token.getCreacion().getTime() + (minutes.intValue() * Constantes.SECONDS * Constantes.MILISECONDS)));
		return token;
	}
}
