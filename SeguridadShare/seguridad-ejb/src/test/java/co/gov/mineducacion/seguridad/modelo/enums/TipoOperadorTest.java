package co.gov.mineducacion.seguridad.modelo.enums;

import org.junit.Test;
import static org.junit.Assert.*;
public class TipoOperadorTest {

    @Test
    public void testEnumValues() {
        TipoOperador[] expectedValues = {TipoOperador.OR, TipoOperador.AND};
        TipoOperador[] actualValues = TipoOperador.values();

        assertArrayEquals(expectedValues, actualValues);
    }

    @Test
    public void testGetIdReducido() {
        assertEquals("|", TipoOperador.OR.getIdReducido());
        assertEquals("&", TipoOperador.AND.getIdReducido());
    }

    @Test
    public void testObtenerTipoOperador() {
        assertEquals(TipoOperador.OR, TipoOperador.obtenerTipoOperador("|"));
        assertEquals(TipoOperador.AND, TipoOperador.obtenerTipoOperador("&"));

        try {
            // Debe lanzar NullPointerException cuando se pasa null
            TipoOperador.obtenerTipoOperador(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected
        }

        try {
            // Debe lanzar IllegalArgumentException cuando se pasa un valor inválido
            TipoOperador.obtenerTipoOperador("INVALID");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
