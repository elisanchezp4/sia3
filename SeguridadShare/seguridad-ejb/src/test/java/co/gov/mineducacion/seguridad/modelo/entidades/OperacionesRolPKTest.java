package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacionesRolPKTest {

    private OperacionesRolPK operacionesRolPK;

    @Before
    public void setUp() {
        operacionesRolPK = new OperacionesRolPK();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BigDecimal rolId = BigDecimal.valueOf(1L);
        String opcionId = "opcion-1";
        operacionesRolPK.setRolId(rolId);
        operacionesRolPK.setOpcionId(opcionId);

        // Act
        BigDecimal retrievedRolId = operacionesRolPK.getRolId();
        String retrievedOpcionId = operacionesRolPK.getOpcionId();

        // Assert
        assertEquals(rolId, retrievedRolId);
        assertEquals(opcionId, retrievedOpcionId);
    }

    @Test
    public void testEquals() {
        // Arrange
        BigDecimal rolId1 = BigDecimal.valueOf(1L);
        String opcionId1 = "opcion-1";

        BigDecimal rolId2 = BigDecimal.valueOf(1L);
        String opcionId2 = "opcion-1";

        // Act
        operacionesRolPK.setRolId(rolId1);
        operacionesRolPK.setOpcionId(opcionId1);
        OperacionesRolPK otherOperacionesRolPK = new OperacionesRolPK(rolId2, opcionId2);

        boolean result = operacionesRolPK.equals(otherOperacionesRolPK);

        // Assert
        assertTrue(result);
    }
}
