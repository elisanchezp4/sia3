/**
 * 
 * @author hfabra
 * @since 19/02/2017
 */
package co.gov.mindecuacion.sdk.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import co.gov.mineducacion.sdk.web.soap.autenticar.ObtenerRolesPermisosRs;

/**
 * @author hfabra
 *
 */
public class PruebaSeguridadWeb {

	private static final String KEY_PRIVATE = "90d45fd74c63e3fd290d9d68a29e3e3a";

	private static final int DESVINCULAR_ROLES_USUARIO = 1;
	private static final int VINCULAR_ROLES_USUARIO = 2;
	private static final int ACTUALIZAR_EMAIL = 3;
	private static final int ACTUALIZAR_DATOS_BASICOS = 4;
	private static final int ACTUALIZAR_PASSWORD = 5;

	private static final int CONSULTAR_USUARIOS = 6;

	/**
	 * @param args
	 * @author hfabra
	 * @since 19/02/2017
	 */
	public static void main(String[] args)  {

//		String encripcion = Encriptar("publicadorsia3");
//		System.out.println(encripcion);

//		String desencripcion = Desencriptar("lGdr5OTeLRsmnZJghz99gA==");
//		System.out.println(desencripcion);

		try {
//			serviciosAutenticar(1);

//			serviciosGestionar(DESVINCULAR_ROLES_USUARIO);
//			serviciosGestionar(VINCULAR_ROLES_USUARIO);
//			serviciosGestionar(ACTUALIZAR_EMAIL);
//			serviciosGestionar(ACTUALIZAR_DATOS_BASICOS);
			serviciosGestionar(CONSULTAR_USUARIOS);

		} catch (SeguridadException e) {
			throw new RuntimeException(e);
		}

	}

	public static void serviciosAutenticar(Integer servicio) throws SeguridadException {
		if(servicio.equals(1)) {
			ObtenerRolesPermisosRs permisos = AutorizacionService.obtenerRolesPermisos("eeYhKddojCRuZUw5", "35", 20975);
			System.out.println(permisos.getPermisos().getRoles().getString().get(0));
			System.out.println(permisos.getUsuario().getUserId());
		}
	}

	public static void serviciosGestionar(Integer servicio) throws SeguridadException {
		List<String> roles = new ArrayList<>();
		int idUsuarioPeticion 		 = 20975;
		String idUsuarioModificacion = "1718";
		String idAplicacion 		 = "35";
		boolean notificarUsuario 	 = false;

		if(servicio.equals(DESVINCULAR_ROLES_USUARIO)) {
			System.out.println("desvincularRolesUsuario");
			roles.add("ROLEX");
			GestionarUsuarioService.desvincularRolesUsuario(roles, idUsuarioModificacion, idAplicacion, idUsuarioPeticion, notificarUsuario);

		} else if (servicio.equals(VINCULAR_ROLES_USUARIO)) {
			System.out.println("vincularRolesUsuario");
			roles.add("ROLEX");
			GestionarUsuarioService.vincularRolesUsuario(roles, idUsuarioModificacion, idAplicacion, idUsuarioPeticion, notificarUsuario);

		} else if (servicio.equals(ACTUALIZAR_EMAIL)) {
			System.out.println("actualizarEmail");
			String email = "lsGrajales@gmail.com";
			GestionarUsuarioService.actualizarEmail(email, idUsuarioModificacion, idAplicacion, idUsuarioPeticion, notificarUsuario);

		} else if (servicio.equals(ACTUALIZAR_DATOS_BASICOS)) {
			System.out.println("actualizarDatosBasicos");
			String nombres = "Andres Felipe";
			String apellidos = "Torres Isaza";
			String numeroDocumento = "12345678";
			String rutaDirectorio = "DC=tsi,DC=domain,DC=sia3";
			GestionarUsuarioService.actualizarDatosBasicos(nombres, apellidos, numeroDocumento, rutaDirectorio, idUsuarioModificacion, idAplicacion, idUsuarioPeticion, notificarUsuario);

		} else if (servicio.equals(ACTUALIZAR_PASSWORD)) {
			System.out.println("actualizarPassword");
			String password = "Sia-123456";
			GestionarUsuarioService.actualizarPassword(password, idUsuarioModificacion, idAplicacion, idUsuarioPeticion, notificarUsuario);
		}  else if (servicio.equals(CONSULTAR_USUARIOS)) {
			System.out.println("consultarUsuario");
			GestionarUsuarioService.consultarUsuario("48", 7, "Ventanilla");
		}
	}

	/**
	 * @param encriptar parametro ingresado por consulta a encriptar
	 * @return responde el valor encriptado pasado por consola
	 */
	public static String Encriptar(String encriptar) {
		try {
			SecretKeySpec secretKeySpec = crearClave(KEY_PRIVATE);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] cadena = encriptar.getBytes(StandardCharsets.UTF_8);
			byte[] encriptada = cipher.doFinal(cadena);
			return Base64.encode(encriptada);
		} catch (Exception e) {
			return "";
		}
	}

	/***
	 *
	 * @param llave llave privada para encriptar los valores
	 * @return responde la llave construida
	 */
	public static SecretKeySpec crearClave(String llave) {
		try {
			byte[] cadena = llave.getBytes(StandardCharsets.UTF_8);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			cadena = md.digest(cadena);
			cadena = Arrays.copyOf(cadena, 16);
			return new SecretKeySpec(cadena, "AES");
		} catch (Exception e) {
			return null;
		}
	}

	public static String desencriptar(String desencriptar) {
		try {
			SecretKeySpec secretKeySpec = crearClave(KEY_PRIVATE);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			byte[] cadena = Base64.decode(desencriptar);
			byte[] desencriptacioon = cipher.doFinal(cadena);
			return new String(desencriptacioon);
		} catch (Exception e) {
			return "";
		}

	}
}
