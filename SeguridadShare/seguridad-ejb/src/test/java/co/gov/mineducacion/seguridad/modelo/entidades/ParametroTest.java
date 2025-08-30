package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ParametroTest {

    private Parametro parametro;

    @Before
    public void setUp() {
        parametro = new Parametro();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        String nombre = "parametro1";
        String valor = "valor1";
        String descripcion = "descripcion1";
        String tipo = "tipo1";

        parametro.setNombre(nombre);
        parametro.setValor(valor);
        parametro.setDescripcion(descripcion);
        parametro.setTipo(tipo);

        // Act
        String retrievedNombre = parametro.getNombre();
        String retrievedValor = parametro.getValor();
        String retrievedDescripcion = parametro.getDescripcion();
        String retrievedTipo = parametro.getTipo();

        // Assert
        assertEquals(nombre, retrievedNombre);
        assertEquals(valor, retrievedValor);
        assertEquals(descripcion, retrievedDescripcion);
        assertEquals(tipo, retrievedTipo);
    }

    @Test
    public void testEquals() {
        // Arrange
        String nombre = "parametro1";
        String valor = "valor1";
        String descripcion = "descripcion1";
        String tipo = "tipo1";

        parametro.setNombre(nombre);
        parametro.setValor(valor);
        parametro.setDescripcion(descripcion);
        parametro.setTipo(tipo);

        Parametro otherParametro = new Parametro();
        otherParametro.setNombre(nombre);
        otherParametro.setValor(valor);
        otherParametro.setDescripcion(descripcion);
        otherParametro.setTipo(tipo);

        // Act
        boolean result = parametro.equals(otherParametro);

        // Assert
        assertTrue(result);
    }
}
