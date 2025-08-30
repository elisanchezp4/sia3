package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RolesDTOTest {

    private RolesDTO rolesDTO;

    @Before
    public void setUp() {
        rolesDTO = new RolesDTO();
    }

    @Test
    public void testGetSetRolId() {
        // Arrange
        BigDecimal rolId = new BigDecimal(1);

        // Act
        rolesDTO.setRolId(rolId);

        // Assert
        assertEquals(rolId, rolesDTO.getRolId());
    }

    @Test
    public void testGetSetNombre() {
        // Arrange
        String nombre = "Admin";

        // Act
        rolesDTO.setNombre(nombre);

        // Assert
        assertEquals(nombre, rolesDTO.getNombre());
    }

    @Test
    public void testGetSetEstado() {
        // Arrange
        BigDecimal estado = new BigDecimal(1);

        // Act
        rolesDTO.setEstado(estado);

        // Assert
        assertEquals(estado, rolesDTO.getEstado());
    }

    @Test
    public void testGetSetAplicacionId() {
        // Arrange
        BigDecimal aplicacionId = new BigDecimal(2);

        // Act
        rolesDTO.setAplicacionId(aplicacionId);

        // Assert
        assertEquals(aplicacionId, rolesDTO.getAplicacionId());
    }

    @Test
    public void testGetSetFechaCreacion() {
        // Arrange
        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());

        // Act
        rolesDTO.setFechaCreacion(fechaCreacion);

        // Assert
        assertEquals(fechaCreacion, rolesDTO.getFechaCreacion());
    }

    @Test
    public void testGetSetUltimaModificacion() {
        // Arrange
        Timestamp ultimaModificacion = new Timestamp(System.currentTimeMillis());

        // Act
        rolesDTO.setUltimaModificacion(ultimaModificacion);

        // Assert
        assertEquals(ultimaModificacion, rolesDTO.getUltimaModificacion());
    }

    @Test
    public void testGetSetUsuarioModificacion() {
        // Arrange
        BigDecimal usuarioModificacion = new BigDecimal(3);

        // Act
        rolesDTO.setUsuarioModificacion(usuarioModificacion);

        // Assert
        assertEquals(usuarioModificacion, rolesDTO.getUsuarioModificacion());
    }

    @Test
    public void testGetSetPath() {
        // Arrange
        String path = "/admin";

        // Act
        rolesDTO.setPath(path);

        // Assert
        assertEquals(path, rolesDTO.getPath());
    }

    @Test
    public void testGetSetUsuariosRolDTOs() {
        // Arrange
        List<UsuariosRolDTO> usuariosRolDTOs = new ArrayList<>();
        usuariosRolDTOs.add(mock(UsuariosRolDTO.class));

        // Act
        rolesDTO.setUsuariosRolDTOs(usuariosRolDTOs);

        // Assert
        assertEquals(usuariosRolDTOs, rolesDTO.getUsuariosRolDTOs());
    }
}
