/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.mineducacion.seguridad.modelo.excepciones;

import javax.ejb.ApplicationException;

/**
 * Excepcion para el manejo de errores dentro del sistema SIA3
 * @author heinsohn
 */
@ApplicationException(rollback = true)
public class SIA3Exception extends Exception{

	private static final long serialVersionUID = 1L;

	public SIA3Exception(String message) {
		super(message);
	}

	public SIA3Exception(String message, Throwable cause) {
		super(message, cause);
	}

}
