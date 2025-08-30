package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;

import java.math.BigDecimal;

public class OperacionesDTOTest {

    private OperacionesDTO operacionesDTO;

    @Before
    public void setUp() {
        operacionesDTO = new OperacionesDTO();
    }

    @Test
    public void testSetOpcionId() {
        // Arrange
        BigDecimal opcionId = BigDecimal.valueOf(1);

        // Act
        operacionesDTO.setOpcionId(opcionId);

        // Assert
        assertEquals(opcionId, operacionesDTO.getOpcionId());
    }

    @Test
    public void testSetAplicacionId() {
        // Arrange
        BigDecimal aplicacionId = BigDecimal.valueOf(2);
        Aplicaciones aplicacionMock = mock(Aplicaciones.class);

        // Act
        operacionesDTO.setAplicaciones(aplicacionMock);
        when(aplicacionMock.getAplicacionId()).thenReturn(aplicacionId);

        // Assert
        assertEquals(aplicacionId, operacionesDTO.getAplicaciones().getAplicacionId());
    }
}
