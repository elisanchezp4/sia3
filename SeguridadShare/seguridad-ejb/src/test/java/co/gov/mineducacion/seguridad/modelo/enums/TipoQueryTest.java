package co.gov.mineducacion.seguridad.modelo.enums;

import org.junit.Test;
import static org.junit.Assert.*;

public class TipoQueryTest {

    @Test
    public void testEnumValues() {
        TipoQuery[] expectedValues = {TipoQuery.SELECT, TipoQuery.DELETE, TipoQuery.UPDATE};
        TipoQuery[] actualValues = TipoQuery.values();

        assertArrayEquals(expectedValues, actualValues);
    }
}
