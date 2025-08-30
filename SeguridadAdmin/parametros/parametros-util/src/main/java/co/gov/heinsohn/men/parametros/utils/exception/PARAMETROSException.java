package co.gov.heinsohn.men.parametros.utils.exception;

public class PARAMETROSException extends Exception{

	private static final long serialVersionUID = 1L;

	public PARAMETROSException(String message) {
		super(message);
	}

	public PARAMETROSException(String message, Throwable cause) {
		super(message, cause);
	}
}
