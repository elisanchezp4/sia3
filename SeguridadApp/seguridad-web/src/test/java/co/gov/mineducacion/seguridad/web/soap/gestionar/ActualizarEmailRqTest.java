package co.gov.mineducacion.seguridad.web.soap.gestionar;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertNull;

public final class ActualizarEmailRqTest {

    @InjectMocks
    private ActualizarEmailRq actualizarEmailRq = new ActualizarEmailRq();

    @Test
    public void testGetEmail_ReturnEmail() {
        String actual = actualizarEmailRq.getEmail();
        assertNull(actual);
    }

    @Test
    public void testGetNotificarUsuario_ReturnNotificarUsuario() {
        Boolean actual = actualizarEmailRq.getNotificarUsuario();
        assertNull(actual);
    }

    @Test
    public void testGetUserId_ReturnUserId() {
        String actual = actualizarEmailRq.getUserId();
        assertNull(actual);
    }

    @Test
    public void testSetActualizarEmailRq() {
        actualizarEmailRq.setEmail(null);
        actualizarEmailRq.setNotificarUsuario(null);
        actualizarEmailRq.setUserId(null);
    }

}
