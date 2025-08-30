package com.cifrador;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Cifrador {
    private static final String KEY_PRIVATE = "90d45fd74c63e3fd290d9d68a29e3e3a";

    private static final Logger logger = Logger.getLogger(NegocioOperaciones.class.getName());

    /**
     * Clase principal para correr aplicacion general
     * @param args
     */
    public static void main(String[] args) {
        String encriptada;
        Cifrador cifrador = new Cifrador();
        Scanner lectura = new Scanner(System.in);
        logger.info("Ingresa la cadena a encriptar: ");
        String aEncriptar = lectura.nextline();
        encriptada = cifrador.Encriptar(aEncriptar);
        logger.info("Valor encriptado: " + encriptada);

        try {
            //Ponemos a "Dormir" el programa durante los ms que queremos
            logger.info("Valor encriptado: " + encriptada);
            Thread.sleep(50*1000L);
        } catch (InterruptedException e) {
            logger.info("Error: " + e);
            Thread.currentThread().interrupt();
        }
    }

    /***
     *
     * @param llave llave privada para encriptar los valores
     * @return responde la llave construida
     */
    public SecretKeySpec CrearCalve(String llave) { // NOSONAR
        try { // NOSONAR
            byte[] cadena = llave.getBytes(StandardCharsets.UTF_8); // NOSONAR
            MessageDigest md = MessageDigest.getInstance("SHA-1"); // NOSONAR
            cadena = md.digest(cadena); // NOSONAR
            cadena = Arrays.copyOf(cadena, 16); // NOSONAR
            return new SecretKeySpec(cadena, "AES"); // NOSONAR
        } catch (Exception e) { // NOSONAR
            return null; // NOSONAR
        } // NOSONAR
    } // NOSONAR

    /**
     * @param encriptar parametro ingresado por consulta a encriptar
     * @return responde el valor encriptado pasado por consola
     */
    public String Encriptar(String encriptar) { // NOSONAR
        try { // NOSONAR
            SecretKeySpec secretKeySpec = CrearCalve(KEY_PRIVATE); // NOSONAR
            Cipher cipher = Cipher.getInstance("AES"); // NOSONAR
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); // NOSONAR
            byte[] cadena = encriptar.getBytes(StandardCharsets.UTF_8); // NOSONAR
            byte[] encriptada = cipher.doFinal(cadena); // NOSONAR
            return Base64.encode(encriptada); // NOSONAR
        } catch (Exception e) { // NOSONAR
            return ""; // NOSONAR
        } // NOSONAR
    }

    /**
     * Nota: esta modelo se deja como ejemplo de utilizacion en cualquier parte del sistema
     * @param desencriptar valor a desencriptar con la llave privada
     * @return response el texto plano es puesto
     */
    public String Desencriptar(String desencriptar) { // NOSONAR
        try { // NOSONAR
            SecretKeySpec secretKeySpec = CrearCalve(KEY_PRIVATE); // NOSONAR
            Cipher cipher = Cipher.getInstance("AES"); // NOSONAR
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec); // NOSONAR
            // NOSONAR
            byte[] cadena = Base64.decode(desencriptar); // NOSONAR
            byte[] desencriptacioon = cipher.doFinal(cadena); // NOSONAR
            return new String(desencriptacioon); // NOSONAR
        } catch (Exception e) { // NOSONAR
            return ""; // NOSONAR
        } // NOSONAR

    }

}