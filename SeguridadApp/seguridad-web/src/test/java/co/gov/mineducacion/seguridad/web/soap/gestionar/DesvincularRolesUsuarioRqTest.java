package co.gov.mineducacion.seguridad.web.soap.gestionar;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertNull;

public final class DesvincularRolesUsuarioRqTest {

    @InjectMocks
    private DesvincularRolesUsuarioRq desvincularRolesUsuarioRq = new DesvincularRolesUsuarioRq();

    @Test
    public void testGetNotificarUsuario_ReturnNotificarUsuario() {
        Boolean actual = desvincularRolesUsuarioRq.getNotificarUsuario();
        assertNull(actual);
    }

    @Test
    public void testGetRoles_ReturnRoles() {
        ArrayOfstring actual = desvincularRolesUsuarioRq.getRoles();
        assertNull(actual);
    }

    @Test
    public void testGetUserId_ReturnUserId() {
        String actual = desvincularRolesUsuarioRq.getUserId();
        assertNull(actual);
    }

    @Test
    public void testSetDesvincularRolesUsuarioRqTest() {
        desvincularRolesUsuarioRq.setNotificarUsuario(null);
        desvincularRolesUsuarioRq.setRoles(null);
        desvincularRolesUsuarioRq.setUserId(null);
    }
}
