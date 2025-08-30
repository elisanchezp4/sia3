package co.gov.mindecuacion.sdk.servicio;

/**
 * Excepcion para el manejo de errores dentro del sistema Seguridad permite
 * retornar los errores a la aplicacion cliente.
 * 
 * @author Michael Murgueitio
 * @version 1.0
 * @created 11-nov-2016 09:55:22 a.m.
 */
public class SeguridadException extends Exception {

	private String idError;

	private static final long serialVersionUID = -1877514126146793809L;

	public SeguridadException(String mensaje) {
		super(mensaje);
	}

	public SeguridadException(String mensaje, String codigo) {
		super(mensaje);
		this.idError = codigo;
	}

	public String getIdError() {
		return idError;
	}

	public void setIdError(String idError) {
		this.idError = idError;
	}

}
