package co.gov.mineducacion.seguridad.modelo.excepciones;

/**
 * Excepción lanzada cuando se produce en error en alguno de los métodos de la
 * aplicación que hacen uso de reflection.
 * 
 * @author jsoto
 */
public class ReflectionException extends RuntimeException {

    /**
	 * 
	 * @author hfabra
	 */
	private static final long serialVersionUID = 1L;

	public ReflectionException (String message) {
        super (message);
    }

    public ReflectionException (Throwable cause) {
        super (cause);
    }

    public ReflectionException (String message, Throwable cause) {
        super (message, cause);
    }
}
