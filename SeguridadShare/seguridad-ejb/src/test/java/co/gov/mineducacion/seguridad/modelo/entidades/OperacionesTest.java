package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacionesTest {

    private Operaciones operaciones;

    @Before
    public void setUp() {
        operaciones = new Operaciones();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BigDecimal opcionId = BigDecimal.valueOf(1L);
        operaciones.setOpcionId(opcionId);

        BigDecimal aplicacionId = BigDecimal.valueOf(2L);
        operaciones.setAplicacionId(aplicacionId);

        String descripcion = "Descripción de operación";
        operaciones.setDescripcion(descripcion);

        String nombreObjeto = "Objeto de operación";
        operaciones.setNombreObjeto(nombreObjeto);

        BigDecimal opcionPadre = BigDecimal.valueOf(3L);
        operaciones.setOpcionPadre(opcionPadre);

        String tipo = "Tipo de operación";
        operaciones.setTipo(tipo);

        BigDecimal estado = BigDecimal.valueOf(4L);
        operaciones.setEstado(estado);

        // ... continue setting other attributes

        // Act
        BigDecimal retrievedOpcionId = operaciones.getOpcionId();
        BigDecimal retrievedAplicacionId = operaciones.getAplicacionId();
        String retrievedDescripcion = operaciones.getDescripcion();
        String retrievedNombreObjeto = operaciones.getNombreObjeto();
        BigDecimal retrievedOpcionPadre = operaciones.getOpcionPadre();
        String retrievedTipo = operaciones.getTipo();
        BigDecimal retrievedEstado = operaciones.getEstado();

        // ... continue retrieving other attributes

        // Assert
        assertEquals(opcionId, retrievedOpcionId);
        assertEquals(aplicacionId, retrievedAplicacionId);
        assertEquals(descripcion, retrievedDescripcion);
        assertEquals(nombreObjeto, retrievedNombreObjeto);
        assertEquals(opcionPadre, retrievedOpcionPadre);
        assertEquals(tipo, retrievedTipo);
        assertEquals(estado, retrievedEstado);

        // ... continue asserting other attributes
    }

    @Test
    public void testEquals() {
        // Arrange
        Operaciones operaciones1 = new Operaciones();
        operaciones1.setOpcionId(BigDecimal.valueOf(1L));

        Operaciones operaciones2 = new Operaciones();
        operaciones2.setOpcionId(BigDecimal.valueOf(1L));

        // Act
        boolean result = operaciones1.equals(operaciones2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testToString() {
        // Arrange
        operaciones.setOpcionId(BigDecimal.valueOf(1L));
        operaciones.setNombreObjeto("Objeto de operación");

        // Act
        String result = operaciones.toString();

        // Assert
        assertNotNull(result);
    }
}
