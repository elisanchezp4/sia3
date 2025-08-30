package co.gov.mineducacion.seguridad.web.servicio.utils;

import java.io.Serializable;

/**
 * Clase que reprensenta una excepción como un objeto serializable
 * que será lanzado como parte de la respuesta cuando se presente una excepción
 * @author Asesoftware
 */
public class ResponseError implements Serializable {
        
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private String errorClass;

        private String message;

        public ResponseError(){
            // Auto-generated method stub
        }

    public String getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

        
}
