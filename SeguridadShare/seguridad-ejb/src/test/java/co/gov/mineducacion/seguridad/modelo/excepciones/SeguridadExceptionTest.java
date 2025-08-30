package co.gov.mineducacion.seguridad.modelo.excepciones;

import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class SeguridadExceptionTest {

    @Test
    public void testSeguridadExceptionWithMessageAndCause() {
        String message = "Error en la seguridad";
        Throwable cause = mock(Throwable.class);
        SeguridadException exception = new SeguridadException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testAgregarError() {
        SeguridadException masterException = new SeguridadException("Error maestro");
        SeguridadException nestedException = new SeguridadException("Error anidado");

        masterException.agregarError(nestedException);

        assertEquals(1, masterException.getErrores().size());
        assertEquals(nestedException, masterException.getErrores().get(0));
    }

    @Test
    public void testGetMensajes() {
        SeguridadException exception = new SeguridadException(TipoExcepcion.INFO);
        assertEquals("es", exception.getMensajes().getLocale().getLanguage());
    }
}
