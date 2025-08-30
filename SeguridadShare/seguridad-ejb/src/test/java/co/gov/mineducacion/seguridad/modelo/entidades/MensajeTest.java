package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MensajeTest {

    private Mensaje mensaje;

    @Before
    public void setUp() {
        mensaje = new Mensaje();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Long id = 1L;
        mensaje.setId(id);

        String nombre = "Mensaje de prueba";
        mensaje.setNombre(nombre);

        String descripcion = "Descripción del mensaje";
        mensaje.setDescripcion(descripcion);

        String tipoMensaje = "Tipo de mensaje";
        mensaje.setTipoMensaje(tipoMensaje);

        String estado = "Activo";
        mensaje.setEstado(estado);

        String codigo = "MSG001";
        mensaje.setCodigo(codigo);

        // Act
        Long retrievedId = mensaje.getId();
        String retrievedNombre = mensaje.getNombre();
        String retrievedDescripcion = mensaje.getDescripcion();
        String retrievedTipoMensaje = mensaje.getTipoMensaje();
        String retrievedEstado = mensaje.getEstado();
        String retrievedCodigo = mensaje.getCodigo();

        // Assert
        assertEquals(id, retrievedId);
        assertEquals(nombre, retrievedNombre);
        assertEquals(descripcion, retrievedDescripcion);
        assertEquals(tipoMensaje, retrievedTipoMensaje);
        assertEquals(estado, retrievedEstado);
        assertEquals(codigo, retrievedCodigo);
    }

    @Test
    public void testEquals() {
        // Arrange
        Mensaje mensaje1 = new Mensaje();
        mensaje1.setId(1L);

        Mensaje mensaje2 = new Mensaje();
        mensaje2.setId(1L);

        // Act
        boolean result = mensaje1.equals(mensaje2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testToString() {
        // Arrange
        mensaje.setId(1L);
        mensaje.setNombre("Mensaje de prueba");

        // Act
        String result = mensaje.toString();

        // Assert
        assertNotNull(result);
    }
}
