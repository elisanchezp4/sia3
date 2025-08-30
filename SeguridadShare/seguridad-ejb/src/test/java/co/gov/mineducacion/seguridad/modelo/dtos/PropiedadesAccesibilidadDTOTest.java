package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class PropiedadesAccesibilidadDTOTest {

    private PropiedadesAccesibilidadDTO propiedadesDTO;

    @Before
    public void setUp() {
        propiedadesDTO = new PropiedadesAccesibilidadDTO();
    }

    @Test
    public void testSetValor() {
        // Arrange
        String valor = "ValorPropiedad";

        // Act
        propiedadesDTO.setValor(valor);

        // Assert
        assertEquals(valor, propiedadesDTO.getValor());
    }

    @Test
    public void testSetNombre() {
        // Arrange
        String nombre = "NombrePropiedad";

        // Act
        propiedadesDTO.setNombre(nombre);

        // Assert
        assertEquals(nombre, propiedadesDTO.getNombre());
    }
}
