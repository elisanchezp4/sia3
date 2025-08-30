package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropiedadDTOTest {

    private PropiedadDTO propiedadDTO;

    @Before
    public void setUp() {
        propiedadDTO = new PropiedadDTO();
    }

    @Test
    public void testSetNombre() {
        // Arrange
        String nombre = "NombrePropiedad";

        // Act
        propiedadDTO.setNombre(nombre);

        // Assert
        assertEquals(nombre, propiedadDTO.getNombre());
    }

    @Test
    public void testSetValor() {
        // Arrange
        String valor = "ValorPropiedad";

        // Act
        propiedadDTO.setValor(valor);

        // Assert
        assertEquals(valor, propiedadDTO.getValor());
    }
}
