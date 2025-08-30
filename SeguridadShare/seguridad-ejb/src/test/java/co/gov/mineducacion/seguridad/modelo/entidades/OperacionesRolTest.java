package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacionesRolTest {

    private OperacionesRol operacionesRol;

    @Before
    public void setUp() {
        operacionesRol = new OperacionesRol();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        OperacionesRolPK operacionesRolPK = new OperacionesRolPK();
        operacionesRolPK.setOpcionId("1");
        operacionesRolPK.setRolId(BigDecimal.valueOf(2));
        operacionesRol.setOperacionesRolPK(operacionesRolPK);

        Roles roles = mock(Roles.class);
        operacionesRol.setRoles(roles);

        Operaciones operaciones = mock(Operaciones.class);
        operacionesRol.setOperaciones(operaciones);

        // Act
        OperacionesRolPK retrievedOperacionesRolPK = operacionesRol.getOperacionesRolPK();
        Roles retrievedRoles = operacionesRol.getRoles();
        Operaciones retrievedOperaciones = operacionesRol.getOperaciones();

        // Assert
        assertEquals(operacionesRolPK, retrievedOperacionesRolPK);
        assertEquals(roles, retrievedRoles);
        assertEquals(operaciones, retrievedOperaciones);
    }

    @Test
    public void testEquals() {
        // Arrange
        OperacionesRolPK operacionesRolPK1 = new OperacionesRolPK();
        operacionesRolPK1.setOpcionId("1");
        operacionesRolPK1.setRolId(BigDecimal.valueOf(2L));

        OperacionesRolPK operacionesRolPK2 = new OperacionesRolPK();
        operacionesRolPK2.setOpcionId("1");
        operacionesRolPK2.setRolId(BigDecimal.valueOf(2L));

        // Act
        operacionesRol.setOperacionesRolPK(operacionesRolPK1);
        OperacionesRol otherOperacionesRol = new OperacionesRol();
        otherOperacionesRol.setOperacionesRolPK(operacionesRolPK2);

        boolean result = operacionesRol.equals(otherOperacionesRol);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testToString() {
        // Arrange
        Roles roles = mock(Roles.class);
        Operaciones operaciones = mock(Operaciones.class);
        operacionesRol.setRoles(roles);
        operacionesRol.setOperaciones(operaciones);

        // Act
        String result = operacionesRol.toString();

        // Assert
        assertNotNull(result);
    }
}
