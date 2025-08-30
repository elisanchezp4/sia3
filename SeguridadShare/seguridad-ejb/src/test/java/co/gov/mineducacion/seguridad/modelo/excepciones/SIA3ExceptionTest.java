package co.gov.mineducacion.seguridad.modelo.excepciones;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SIA3ExceptionTest {

    @Test
    public void testSIA3ExceptionWithMessage() {
        String message = "Error en el sistema SIA3";
        SIA3Exception exception = new SIA3Exception(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testSIA3ExceptionWithMessageAndCause() {
        String message = "Error en el sistema SIA3";
        Throwable cause = new RuntimeException("Causa del error");
        SIA3Exception exception = new SIA3Exception(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
