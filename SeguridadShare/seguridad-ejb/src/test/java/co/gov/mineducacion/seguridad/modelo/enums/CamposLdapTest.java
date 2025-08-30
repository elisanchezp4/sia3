package co.gov.mineducacion.seguridad.modelo.enums;

import org.junit.Test;
import static org.junit.Assert.*;

public class CamposLdapTest {

    @Test
    public void testGetId() {
        assertEquals("description", CamposLdap.DESCRIPTION.getId());
        assertEquals("displayName", CamposLdap.DISPLAY_NAME.getId());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Descripcion", CamposLdap.DESCRIPTION.getDescripcion());
        assertEquals("Display name", CamposLdap.DISPLAY_NAME.getDescripcion());
    }

    @Test
    public void testFromString() {
        assertEquals(CamposLdap.DESCRIPTION, CamposLdap.fromString("description"));
        assertEquals(CamposLdap.DISPLAY_NAME, CamposLdap.fromString("displayName"));
        assertEquals(CamposLdap.DISTINGUISHED_NAME, CamposLdap.fromString("distinguishedName"));

        assertNull(CamposLdap.fromString("invalidField"));
    }
}
