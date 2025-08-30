package co.gov.mineducacion.seguridad.modelo.excepciones;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InvalidParameterExceptionTest {

    @Test
    public void testInvalidParameterExceptionWithMessage() {
        String errorMessage = "Parámetro inválido";
        InvalidParameterException exception = new InvalidParameterException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testInvalidParameterExceptionWithCause() {
        Throwable cause = mock(Throwable.class);
        InvalidParameterException exception = new InvalidParameterException(cause);

        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testInvalidParameterExceptionWithMessageAndCause() {
        String errorMessage = "Parámetro inválido";
        Throwable cause = mock(Throwable.class);
        InvalidParameterException exception = new InvalidParameterException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}