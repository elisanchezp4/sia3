package co.gov.mineducacion.seguridad.modelo.excepciones;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ReflectionExceptionTest {

    @Test
    public void testReflectionExceptionWithMessage() {
        String errorMessage = "Error en la reflexión";
        ReflectionException exception = new ReflectionException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testReflectionExceptionWithCause() {
        Throwable cause = mock(Throwable.class);
        ReflectionException exception = new ReflectionException(cause);

        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testReflectionExceptionWithMessageAndCause() {
        String errorMessage = "Error en la reflexión";
        Throwable cause = mock(Throwable.class);
        ReflectionException exception = new ReflectionException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}