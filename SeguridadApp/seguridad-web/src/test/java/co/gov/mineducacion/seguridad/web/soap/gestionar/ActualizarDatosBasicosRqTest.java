package co.gov.mineducacion.seguridad.web.soap.gestionar;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertNull;

public final class ActualizarDatosBasicosRqTest {

    @InjectMocks
    private ActualizarDatosBasicosRq actualizarDatosBasicosRq = new ActualizarDatosBasicosRq();

    @Test
    public void testGetApellidos_ReturnApellidos() {
        String actual = actualizarDatosBasicosRq.getApellidos();
        assertNull(actual);
    }

    @Test
    public void testGetNombres_ReturnNombres() {
        String actual = actualizarDatosBasicosRq.getNombres();
        assertNull(actual);
    }

    @Test
    public void testGetNotificarUsuario_ReturnNotificarUsuario() {
        Boolean actual = actualizarDatosBasicosRq.getNotificarUsuario();
        assertNull(actual);
    }

    @Test
    public void testGetNumeroDocumento_ReturnNumeroDocumento() {
        String actual = actualizarDatosBasicosRq.getNumeroDocumento();
        assertNull(actual);
    }

    @Test
    public void testGetRutaDirectorio_ReturnRutaDirectorio() {
        String actual = actualizarDatosBasicosRq.getRutaDirectorio();
        assertNull(actual);
    }

    @Test
    public void testGetUserId_ReturnUserId() {
        String actual = actualizarDatosBasicosRq.getUserId();
        assertNull(actual);
    }

    @Test
    public void testSetActualizarDatosBasicosRq() {
        actualizarDatosBasicosRq.setApellidos(null);
        actualizarDatosBasicosRq.setNombres(null);
        actualizarDatosBasicosRq.setNotificarUsuario(null);
        actualizarDatosBasicosRq.setNumeroDocumento(null);
        actualizarDatosBasicosRq.setRutaDirectorio(null);
        actualizarDatosBasicosRq.setUserId(null);
    }
}
