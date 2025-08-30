package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRolPK;

import java.math.BigDecimal;

public class OperacionesPorRolDTOTest {

    private OperacionesPorRolDTO operacionesPorRolDTO;

    @Before
    public void setUp() {
        operacionesPorRolDTO = new OperacionesPorRolDTO();
    }

    @Test
    public void testSetRolId() {
        // Arrange
        BigDecimal rolId = BigDecimal.valueOf(1);

        // Act
        operacionesPorRolDTO.setRolId(rolId);

        // Assert
        assertEquals(rolId, operacionesPorRolDTO.getRolId());
    }

    @Test
    public void testSetOperacionId() {
        // Arrange
        BigDecimal operacionId = BigDecimal.valueOf(2);

        // Act
        operacionesPorRolDTO.setOperacionId(operacionId);

        // Assert
        assertEquals(operacionId, operacionesPorRolDTO.getOperacionId());
    }

    @Test
    public void testSetOperacionesRolPK() {
        // Arrange
        OperacionesRolPK operacionesRolPK = mock(OperacionesRolPK.class);

        // Act
        operacionesPorRolDTO.setOperacionesRolPK(operacionesRolPK);

        // Assert
        assertEquals(operacionesRolPK, operacionesPorRolDTO.getOperacionesRolPK());
    }
}
