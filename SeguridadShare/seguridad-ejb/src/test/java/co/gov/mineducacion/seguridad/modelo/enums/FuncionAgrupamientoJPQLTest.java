package co.gov.mineducacion.seguridad.modelo.enums;

import org.junit.Test;
import static org.junit.Assert.*;

public class FuncionAgrupamientoJPQLTest {

    @Test
    public void testEnumValues() {
        assertEquals(3, FuncionAgrupamientoJPQL.values().length);
        assertArrayEquals(new FuncionAgrupamientoJPQL[] { FuncionAgrupamientoJPQL.COUNT, FuncionAgrupamientoJPQL.DISTINCT, FuncionAgrupamientoJPQL.NINGUNA },
                FuncionAgrupamientoJPQL.values());
    }

    @Test
    public void testEnumMethods() {
        assertEquals("COUNT", FuncionAgrupamientoJPQL.COUNT.name());
        assertEquals("DISTINCT", FuncionAgrupamientoJPQL.DISTINCT.name());
        assertEquals("NINGUNA", FuncionAgrupamientoJPQL.NINGUNA.name());

        assertEquals(0, FuncionAgrupamientoJPQL.COUNT.ordinal());
        assertEquals(1, FuncionAgrupamientoJPQL.DISTINCT.ordinal());
        assertEquals(2, FuncionAgrupamientoJPQL.NINGUNA.ordinal());
    }

    @Test
    public void testToString() {
        assertEquals("COUNT", FuncionAgrupamientoJPQL.COUNT.toString());
        assertEquals("DISTINCT", FuncionAgrupamientoJPQL.DISTINCT.toString());
        assertEquals("NINGUNA", FuncionAgrupamientoJPQL.NINGUNA.toString());
    }

    @Test
    public void testFromString() {
        assertEquals(FuncionAgrupamientoJPQL.COUNT, FuncionAgrupamientoJPQL.COUNT);
        assertEquals(FuncionAgrupamientoJPQL.DISTINCT, FuncionAgrupamientoJPQL.DISTINCT);
        assertEquals(FuncionAgrupamientoJPQL.NINGUNA, FuncionAgrupamientoJPQL.NINGUNA);
    }
}
