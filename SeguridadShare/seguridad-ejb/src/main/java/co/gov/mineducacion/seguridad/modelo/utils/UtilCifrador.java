package co.gov.mineducacion.seguridad.modelo.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Clase utilitaria para realizar cifrado y decifrado de informacion
 * @author hfabra
 *
 */
public class UtilCifrador {
	
	private SecretKeySpec rc4Key = null; //llave
	protected String textoLlave = "ED7R=&$¿8kmiteyr";
	
	
	public UtilCifrador() {		
		configurarLlave();		
	}
	
	public void configurarLlave() {
		
		try {			
			byte [] textoLlaveBytes = textoLlave.getBytes("ASCII");	
		    rc4Key = new SecretKeySpec(textoLlaveBytes, "RC4");		    
		} catch(Exception e) {
			configurarLlave();
		}
	
	}
	
	/**
	 * Cifra el texto recibido por parametro y retorna el resultado
	 * @param password texto a cifrar
	 * @return texto cifrado
	 */
	public String cifrarTexto(String password) {
		
		String textoACifrar = null;
		byte [] cifradoResultante = null;
		Cipher rc4 = null; //referencia hacia el cifrador
	
		
		
		try {
			
			//solicito cifrador tiop RC4
			rc4 = Cipher.getInstance("RC4");
		    //inicializo el cifrador en modo encriptar
		    rc4.init(Cipher.ENCRYPT_MODE, rc4Key);				
			
			//obtengo texto por teclado a cifrar
			textoACifrar = password;
			
			//cifro el texto ingresado
			cifradoResultante = rc4.update(textoACifrar.getBytes("ASCII"));			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return DatatypeConverter.printHexBinary(cifradoResultante);
	}
	
	/**
	 * Decifra el texto recibido por parametro y retorna el resultado
	 * @param descrifrado texto a decifrar
	 * @return texto decifrado
	 */
	public String descifrar(String descrifrado){
		
		Cipher rc4Decrypt = null;
		byte [] descifrar = null;
		byte [] desencriptado = null;
			
		descifrar =  DatatypeConverter.parseHexBinary(descrifrado);

		try {
			//solicito un nuevo cifrador tiop RC4
			rc4Decrypt = Cipher.getInstance("RC4");
			//inicializo el cifrador en modo desencriptar (utilizo la misma llave)
			rc4Decrypt.init(Cipher.DECRYPT_MODE, rc4Key);
			//desencripto el cifrado que se obtuvo anteriormente
			desencriptado = rc4Decrypt.update(descifrar);
			//imprimo el texto desencriptado
			return new String(desencriptado, "ASCII");
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			e1.printStackTrace();
			return "";
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}			
	}
}
