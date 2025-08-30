package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

public class UsuarioRolDTOTest {

    private UsuarioRolDTO usuarioRolDTO;

    @Before
    public void setUp() {
        // Inicializa los objetos necesarios
        BigDecimal rolId = new BigDecimal(1);
        BigDecimal aplicacionId = new BigDecimal(2);
        String nombre = "Administrador";
        String estadoVinculacion = "Activo";
        usuarioRolDTO = new UsuarioRolDTO(rolId, aplicacionId, nombre, estadoVinculacion);
    }

    @Test
    public void testGetSetRolId() {
        // Arrange
        BigDecimal rolId = new BigDecimal(3);

        // Act
        usuarioRolDTO.setRol_id(rolId);

        // Assert
        assertEquals(rolId, usuarioRolDTO.getRol_id());
    }

    @Test
    public void testGetSetAplicacionId() {
        // Arrange
        BigDecimal aplicacionId = new BigDecimal(4);

        // Act
        usuarioRolDTO.setAplicacion_id(aplicacionId);

        // Assert
        assertEquals(aplicacionId, usuarioRolDTO.getAplicacion_id());
    }

    @Test
    public void testGetSetNombre() {
        // Arrange
        String nombre = "Supervisor";

        // Act
        usuarioRolDTO.setNombre(nombre);

        // Assert
        assertEquals(nombre, usuarioRolDTO.getNombre());
    }

    @Test
    public void testGetSetEstadoVinculacion() {
        // Arrange
        String estadoVinculacion = "Inactivo";

        // Act
        usuarioRolDTO.setEstado_vinculacion(estadoVinculacion);

        // Assert
        assertEquals(estadoVinculacion, usuarioRolDTO.getEstado_vinculacion());
    }

    @Test
    public void testConstructor() {
        // Arrange
        BigDecimal rolId = new BigDecimal(5);
        BigDecimal aplicacionId = new BigDecimal(6);
        String nombre = "Operador";
        String estadoVinculacion = "Pendiente";

        // Act
        UsuarioRolDTO newUsuarioRolDTO = new UsuarioRolDTO(rolId, aplicacionId, nombre, estadoVinculacion);

        // Assert
        assertEquals(rolId, newUsuarioRolDTO.getRol_id());
        assertEquals(aplicacionId, newUsuarioRolDTO.getAplicacion_id());
        assertEquals(nombre, newUsuarioRolDTO.getNombre());
        assertEquals(estadoVinculacion, newUsuarioRolDTO.getEstado_vinculacion());
    }
}
