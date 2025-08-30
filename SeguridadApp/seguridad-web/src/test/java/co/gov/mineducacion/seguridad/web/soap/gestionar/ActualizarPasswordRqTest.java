package co.gov.mineducacion.seguridad.web.soap.gestionar;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertNull;

public final class ActualizarPasswordRqTest {

    @InjectMocks
    private ActualizarPasswordRq actualizarPasswordRq = new ActualizarPasswordRq();

    @Test
    public void testGetNotificarUsuario_ReturnNotificarUsuario() {
        Boolean actual = actualizarPasswordRq.getNotificarUsuario();
        assertNull(actual);
    }

    @Test
    public void testGetPassword_ReturnPassword() {
        String actual = actualizarPasswordRq.getPassword();
        assertNull(actual);
    }

    @Test
    public void testGetUserId_ReturnUserId() {
        String actual = actualizarPasswordRq.getUserId();
        assertNull(actual);
    }

    @Test
    public void testSetActualizarPasswordRq() {
        actualizarPasswordRq.setNotificarUsuario(null);
        actualizarPasswordRq.setPassword(null);
        actualizarPasswordRq.setUserId(null);
    }
}
