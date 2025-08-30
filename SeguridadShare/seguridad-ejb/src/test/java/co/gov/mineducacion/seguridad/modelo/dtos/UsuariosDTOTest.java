package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuariosDTOTest {

    private UsuariosDTO usuariosDTO;

    @Before
    public void setUp() {
        // Inicializa los objetos necesarios
        usuariosDTO = new UsuariosDTO();
        usuariosDTO.setUsuarioId("testId");
        usuariosDTO.setNuevoPass("newPass");
        usuariosDTO.setRuta("testRuta");
        usuariosDTO.setTipo(BigDecimal.ONE);
        usuariosDTO.setEstado(BigDecimal.ZERO);
        usuariosDTO.setFechaCreacion(new Date());
        usuariosDTO.setUltimaModificacion(new Date());
        usuariosDTO.setUsuarioModificacion("testUserMod");
        usuariosDTO.setLoginUsuario("testLogin");
        usuariosDTO.setApellidosUsuario("testApellidos");
        usuariosDTO.setEmailUsuario("testEmail");
        usuariosDTO.setNombres("testNombres");
        usuariosDTO.setPassword("testPass");
        usuariosDTO.setLogonName("testLogon");
        usuariosDTO.setNombreUsuario("testNombre");
        usuariosDTO.setNumeroDocumento("testDoc");
        usuariosDTO.setNombreRol("testRol");
        usuariosDTO.setFechaVinculacion(new Date());
        usuariosDTO.setFechaDesvinculacion(new Date());
        usuariosDTO.setEstadoVinculacion("testEstado");

        List<RolesDTO> roles = new ArrayList<>();
        roles.add(mock(RolesDTO.class));
        usuariosDTO.setRoles(roles);
    }

    @Test
    public void testGettersAndSetters() {
        // Assert
        assertEquals("testId", usuariosDTO.getUsuarioId());
        assertEquals("newPass", usuariosDTO.getNuevoPass());
        assertEquals("testRuta", usuariosDTO.getRuta());
        assertEquals(BigDecimal.ONE, usuariosDTO.getTipo());
        assertEquals(BigDecimal.ZERO, usuariosDTO.getEstado());
        assertNotNull(usuariosDTO.getFechaCreacion());
        assertNotNull(usuariosDTO.getUltimaModificacion());
        assertEquals("testUserMod", usuariosDTO.getUsuarioModificacion());
        assertEquals("testLogin", usuariosDTO.getLoginUsuario());
        assertEquals("testApellidos", usuariosDTO.getApellidosUsuario());
        assertEquals("testEmail", usuariosDTO.getEmailUsuario());
        assertEquals("testNombres", usuariosDTO.getNombres());
        assertEquals("testPass", usuariosDTO.getPassword());
        assertEquals("testLogon", usuariosDTO.getLogonName());
        assertEquals("testNombre", usuariosDTO.getNombreUsuario());
        assertEquals("testDoc", usuariosDTO.getNumeroDocumento());
        assertEquals("testRol", usuariosDTO.getNombreRol());
        assertNotNull(usuariosDTO.getFechaVinculacion());
        assertNotNull(usuariosDTO.getFechaDesvinculacion());
        assertEquals("testEstado", usuariosDTO.getEstadoVinculacion());
    }

    @Test
    public void testToString() {
        // Act
        String result = usuariosDTO.toString();

        // Assert
        assertNotNull(result);
    }
}