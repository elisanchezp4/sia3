package co.gov.mineducacion.seguridad.modelo.dtos;

import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRolPK;

import java.sql.Timestamp;
import java.util.Date;

public class UsuariosRolDTOTest {

    private UsuariosRolDTO usuariosRolDTO;

    @Before
    public void setUp() {
        // Inicializa los objetos necesarios
        usuariosRolDTO = new UsuariosRolDTO();
        usuariosRolDTO.setRol_id("testRolId");
        usuariosRolDTO.setUsuario_id("testUserId");
        usuariosRolDTO.setEstado_vinculacion("testEstado");
        usuariosRolDTO.setFecha_desvinculacion(new Timestamp(System.currentTimeMillis()));
        usuariosRolDTO.setFecha_vinculacion(new Timestamp(System.currentTimeMillis()));
        usuariosRolDTO.setEstadoVinculacion("testEstadoVinc");
        usuariosRolDTO.setFechaVinculacion(new Date());
        usuariosRolDTO.setUsuariosRolPK(new UsuariosRolPK());

        usuariosRolDTO.setRoles(mock(Roles.class));
        usuariosRolDTO.setUsuarios(mock(Usuario.class));
    }

    @Test
    public void testGettersAndSetters() {
        // Assert
        assertEquals("testRolId", usuariosRolDTO.getRol_id());
        assertEquals("testUserId", usuariosRolDTO.getUsuario_id());
        assertEquals("testEstado", usuariosRolDTO.getEstado_vinculacion());
        assertNotNull(usuariosRolDTO.getFecha_desvinculacion());
        assertNotNull(usuariosRolDTO.getFecha_vinculacion());
        assertEquals("testEstadoVinc", usuariosRolDTO.getEstadoVinculacion());
        assertNotNull(usuariosRolDTO.getFechaVinculacion());
        assertNotNull(usuariosRolDTO.getUsuariosRolPK());
        assertNotNull(usuariosRolDTO.getRoles());
        assertNotNull(usuariosRolDTO.getUsuarios());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UsuariosRolDTO otherDTO = new UsuariosRolDTO();
        otherDTO.setRol_id("testRolId");
        otherDTO.setUsuario_id("testUserId");

        // Assert
        assertEquals(otherDTO, usuariosRolDTO);
        assertEquals(usuariosRolDTO.hashCode(), otherDTO.hashCode());
    }

    @Test
    public void testToString() {
        // Act
        String result = usuariosRolDTO.toString();

        // Assert
        assertNotNull(result);
    }
}
