package co.gov.mineducacion.seguridad.modelo.enums;

import org.junit.Test;
import static org.junit.Assert.*;

public class TipoFiltroTest {

    @Test
    public void testEnumValues() {
        assertEquals(8, TipoFiltro.values().length);
        assertArrayEquals(new TipoFiltro[] { TipoFiltro.LIKE, TipoFiltro.EXACTO, TipoFiltro.NOT_LIKE,
                        TipoFiltro.MAYOR, TipoFiltro.MENOR, TipoFiltro.MAYOR_O_IGUAL,
                        TipoFiltro.MENOR_O_IGUAL, TipoFiltro.DIFERENTE },
                TipoFiltro.values());
    }

    @Test
    public void testEnumMethods() {
        assertEquals("LIKE", TipoFiltro.LIKE.name());
        assertEquals("EXACTO", TipoFiltro.EXACTO.name());
        assertEquals("NOT_LIKE", TipoFiltro.NOT_LIKE.name());
        assertEquals("MAYOR", TipoFiltro.MAYOR.name());
        assertEquals("MENOR", TipoFiltro.MENOR.name());
        assertEquals("MAYOR_O_IGUAL", TipoFiltro.MAYOR_O_IGUAL.name());
        assertEquals("MENOR_O_IGUAL", TipoFiltro.MENOR_O_IGUAL.name());
        assertEquals("DIFERENTE", TipoFiltro.DIFERENTE.name());

        assertEquals(0, TipoFiltro.LIKE.ordinal());
        assertEquals(1, TipoFiltro.EXACTO.ordinal());
        assertEquals(2, TipoFiltro.NOT_LIKE.ordinal());
        assertEquals(3, TipoFiltro.MAYOR.ordinal());
        assertEquals(4, TipoFiltro.MENOR.ordinal());
        assertEquals(5, TipoFiltro.MAYOR_O_IGUAL.ordinal());
        assertEquals(6, TipoFiltro.MENOR_O_IGUAL.ordinal());
        assertEquals(7, TipoFiltro.DIFERENTE.ordinal());
    }

    @Test
    public void testGetIdReducido() {
        assertEquals(":LIKE:", TipoFiltro.LIKE.getIdReducido());
        assertEquals("=", TipoFiltro.EXACTO.getIdReducido());
        assertEquals(":NOTLIKE:", TipoFiltro.NOT_LIKE.getIdReducido());
        assertEquals(">", TipoFiltro.MAYOR.getIdReducido());
        assertEquals("<", TipoFiltro.MENOR.getIdReducido());
        assertEquals(">=", TipoFiltro.MAYOR_O_IGUAL.getIdReducido());
        assertEquals("<=", TipoFiltro.MENOR_O_IGUAL.getIdReducido());
        assertEquals("!=", TipoFiltro.DIFERENTE.getIdReducido());
    }

    @Test
    public void testObtenerTipoFiltro() {
        // Prueba la función obtenerTipoFiltro utilizando una instancia del enum
        assertEquals(TipoFiltro.LIKE, TipoFiltro.LIKE.obtenerTipoFiltro(":LIKE:"));
        assertEquals(TipoFiltro.EXACTO, TipoFiltro.EXACTO.obtenerTipoFiltro("="));
        assertEquals(TipoFiltro.NOT_LIKE, TipoFiltro.NOT_LIKE.obtenerTipoFiltro(":NOTLIKE:"));
        assertEquals(TipoFiltro.MAYOR, TipoFiltro.MAYOR.obtenerTipoFiltro(">"));
        assertEquals(TipoFiltro.MENOR, TipoFiltro.MENOR.obtenerTipoFiltro("<"));
        assertEquals(TipoFiltro.MAYOR_O_IGUAL, TipoFiltro.MAYOR_O_IGUAL.obtenerTipoFiltro(">="));
        assertEquals(TipoFiltro.MENOR_O_IGUAL, TipoFiltro.MENOR_O_IGUAL.obtenerTipoFiltro("<="));
        assertEquals(TipoFiltro.DIFERENTE, TipoFiltro.DIFERENTE.obtenerTipoFiltro("!="));

        try {
            // Debe lanzar NullPointerException cuando se pasa null
            TipoFiltro.LIKE.obtenerTipoFiltro(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected
        }

        try {
            // Debe lanzar IllegalArgumentException cuando se pasa un valor inválido
            TipoFiltro.LIKE.obtenerTipoFiltro("INVALID");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}