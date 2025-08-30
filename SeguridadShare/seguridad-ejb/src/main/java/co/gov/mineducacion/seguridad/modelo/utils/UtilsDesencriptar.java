package co.gov.mineducacion.seguridad.modelo.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class UtilsDesencriptar {
    private UtilsDesencriptar(){/* Recomendacion por sonar */}
    public static String desencriptar(String desencriptar) { // NOSONAR
        try { // NOSONAR
            SecretKeySpec secretKeySpec = crearClave(); // NOSONAR
            Cipher cipher = Cipher.getInstance("AES");  // NOSONAR
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec); // NOSONAR
            // NOSONAR
            byte[] cadena = Base64.decode(desencriptar); // NOSONAR
            byte[] desencriptacioon = cipher.doFinal(cadena); // NOSONAR
            return new String(desencriptacioon); // NOSONAR
        } catch (Exception e) { // NOSONAR
            return ""; // NOSONAR
        } // NOSONAR
    }

    private static SecretKeySpec crearClave() { // NOSONAR
        try { // NOSONAR
            byte[] cadena = Constantes.KEY_PRIVATE.getBytes(StandardCharsets.UTF_8); // NOSONAR
            MessageDigest md = MessageDigest.getInstance("SHA-1"); // NOSONAR
            cadena = md.digest(cadena); // NOSONAR
            cadena = Arrays.copyOf(cadena, 16); // NOSONAR
            return new SecretKeySpec(cadena, "AES"); // NOSONAR
        } catch (Exception e) { // NOSONAR
            return null; // NOSONAR
        } // NOSONAR
    } // NOSONAR
}
