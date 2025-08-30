package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RespuestaImportarOperacionesDTOTest {

    private RespuestaImportarOperacionesDTO respuestaImportarOperacionesDTO;

    @Before
    public void setUp() {
        List<OperacionesDTO> operacionesCreadas = new ArrayList<>();
        List<OperacionesDTO> operacionesActualizadas = new ArrayList<>();
        respuestaImportarOperacionesDTO = new RespuestaImportarOperacionesDTO(operacionesCreadas, operacionesActualizadas);
    }

    @Test
    public void testGetOperacionesCreadas() {
        // Arrange
        List<OperacionesDTO> operacionesCreadas = new ArrayList<>();
        operacionesCreadas.add(new OperacionesDTO());

        // Act
        respuestaImportarOperacionesDTO = new RespuestaImportarOperacionesDTO(operacionesCreadas, new ArrayList<>());

        // Assert
        assertEquals(operacionesCreadas, respuestaImportarOperacionesDTO.getOperacionesCreadas());
    }

    @Test
    public void testGetOperacionesActualizadas() {
        // Arrange
        List<OperacionesDTO> operacionesActualizadas = new ArrayList<>();
        operacionesActualizadas.add(new OperacionesDTO());

        // Act
        respuestaImportarOperacionesDTO = new RespuestaImportarOperacionesDTO(new ArrayList<>(), operacionesActualizadas);

        // Assert
        assertEquals(operacionesActualizadas, respuestaImportarOperacionesDTO.getOperacionesActualizadas());
    }
}
