package co.gov.mineducacion.seguridad.modelo.enums;

import org.junit.Test;
import static org.junit.Assert.*;

public class TipoValidacionTest {

    @Test
    public void testEnumValues() {
        TipoValidacion[] expectedValues = {
                TipoValidacion.REQUIRED, TipoValidacion.MAX, TipoValidacion.MIN,
                TipoValidacion.ALPHANUMERIC, TipoValidacion.NUMERIC, TipoValidacion.ALPHABETIC,
                TipoValidacion.EMAIL, TipoValidacion.PATTERN, TipoValidacion.MAX_LENGTH,
                TipoValidacion.MIN_LENGTH, TipoValidacion.PASSWORD
        };
        TipoValidacion[] actualValues = TipoValidacion.values();

        assertArrayEquals(expectedValues, actualValues);
    }

    @Test
    public void testFromString() {
        assertEquals(TipoValidacion.REQUIRED, TipoValidacion.fromString("required"));
        assertEquals(TipoValidacion.MAX, TipoValidacion.fromString("max"));
        assertEquals(TipoValidacion.MIN, TipoValidacion.fromString("min"));
        assertEquals(TipoValidacion.ALPHANUMERIC, TipoValidacion.fromString("alphanumeric"));
        assertEquals(TipoValidacion.NUMERIC, TipoValidacion.fromString("numeric"));
        assertEquals(TipoValidacion.ALPHABETIC, TipoValidacion.fromString("alphabetic"));
        assertEquals(TipoValidacion.EMAIL, TipoValidacion.fromString("email"));
        assertEquals(TipoValidacion.PATTERN, TipoValidacion.fromString("pattern"));
        assertEquals(TipoValidacion.MAX_LENGTH, TipoValidacion.fromString("maxLength"));
        assertEquals(TipoValidacion.MIN_LENGTH, TipoValidacion.fromString("minLength"));
        assertEquals(TipoValidacion.PASSWORD, TipoValidacion.fromString("password"));
    }
}
