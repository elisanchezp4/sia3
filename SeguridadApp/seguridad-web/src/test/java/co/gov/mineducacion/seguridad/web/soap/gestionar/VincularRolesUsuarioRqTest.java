package co.gov.mineducacion.seguridad.web.soap.gestionar;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VincularRolesUsuarioRqTest {

    @Test
    public void testGetUserId() {
        VincularRolesUsuarioRq request = new VincularRolesUsuarioRq();
        request.setUserId("123");

        assertEquals("123", request.getUserId());
    }

    @Test
    public void testGetRoles() {
        VincularRolesUsuarioRq request = new VincularRolesUsuarioRq();
        ArrayOfstring roles = new ArrayOfstring();
        roles.getString().add("Admin");
        roles.getString().add("Usuario");
        request.setRoles(roles);

        assertEquals(2, request.getRoles().getString().size());
        assertTrue(request.getRoles().getString().contains("Admin"));
        assertTrue(request.getRoles().getString().contains("Usuario"));
    }

    @Test
    public void testGetNotificarUsuario() {
        VincularRolesUsuarioRq request = new VincularRolesUsuarioRq();
        request.setNotificarUsuario(true);

        assertTrue(request.getNotificarUsuario());
    }

    @Test
    public void testSetNotificarUsuario() {
        VincularRolesUsuarioRq request = new VincularRolesUsuarioRq();
        request.setNotificarUsuario(true);

        assertTrue(request.getNotificarUsuario());

        VincularRolesUsuarioRq mockRequest = mock(VincularRolesUsuarioRq.class);
        when(mockRequest.getNotificarUsuario()).thenReturn(false);

        assertFalse(mockRequest.getNotificarUsuario());
    }
}