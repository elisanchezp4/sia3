/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.mineducacion.seguridad.modelo.excepciones;

/**
 * Excepción lanzada cuando se produce en error en alguno de los tests automatizados.
 * 
 * @author jsoto
 */
public class TestException extends RuntimeException {

    /**
	 * 
	 * @author hfabra
	 */
	private static final long serialVersionUID = 1L;

	public TestException (String message) {
        super (message);
    }

    public TestException (Throwable cause) {
        super (cause);
    }

    public TestException (String message, Throwable cause) {
        super (message, cause);
    }
}
