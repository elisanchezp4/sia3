package co.gov.mineducacion.seguridad.modelo.enums;

import org.junit.Test;
import static org.junit.Assert.*;

public class TipoOrdenamientoTest {

    @Test
    public void testEnumValues() {
        TipoOrdenamiento[] expectedValues = {TipoOrdenamiento.ASCENDENTE, TipoOrdenamiento.DESCENDENTE, TipoOrdenamiento.SIN_ORDENAR};
        TipoOrdenamiento[] actualValues = TipoOrdenamiento.values();

        assertArrayEquals(expectedValues, actualValues);
    }

    @Test
    public void testGetIdReducido() {
        assertEquals("ASC", TipoOrdenamiento.ASCENDENTE.getIdReducido());
        assertEquals("DESC", TipoOrdenamiento.DESCENDENTE.getIdReducido());
        assertEquals("SIN", TipoOrdenamiento.SIN_ORDENAR.getIdReducido());
    }

    @Test
    public void testObtenerTipoOrdenamiento() {
        assertEquals(TipoOrdenamiento.ASCENDENTE, TipoOrdenamiento.obtenerTipoOrdenamiento("ASC"));
        assertEquals(TipoOrdenamiento.DESCENDENTE, TipoOrdenamiento.obtenerTipoOrdenamiento("DESC"));
        assertEquals(TipoOrdenamiento.SIN_ORDENAR, TipoOrdenamiento.obtenerTipoOrdenamiento("SIN"));

        try {
            // Debe lanzar NullPointerException cuando se pasa null
            TipoOrdenamiento.obtenerTipoOrdenamiento(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected
        }

        try {
            // Debe lanzar IllegalArgumentException cuando se pasa un valor inválido
            TipoOrdenamiento.obtenerTipoOrdenamiento("INVALID");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
