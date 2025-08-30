package co.gov.mineducacion.seguridad.web.soap.gestionar;

import org.junit.Test;
import org.mockito.InjectMocks;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.utbot.runtime.utils.java.UtUtils.createInstance;
import static org.utbot.runtime.utils.java.UtUtils.setField;
import static org.junit.Assert.assertNull;

public final class IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessageTest {

    @InjectMocks
    private IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage mockIGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage;

    @Test
    public void testGetFaultInfo_ReturnFaultInfo() throws Exception {
        mockIGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage = ((IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage) createInstance("co.gov.mineducacion.seguridad.web.soap.gestionar.IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage"));
        setField(mockIGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage, "co.gov.mineducacion.seguridad.web.soap.gestionar.IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage", "faultInfo", null);

        MensajeFault actual = mockIGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage.getFaultInfo();

        assertNull(actual);
    }

    @Test
    public void testConstructorWithMessageAndFaultInfo() {
        MensajeFault mensajeFault = new MensajeFault();
        QName qName = new QName("http://example.com", "miElemento", "ns1");
        JAXBElement<String> mensajeMock = new JAXBElement<>(qName, String.class, "Error en la actualización");
        mensajeFault.setMensaje(mensajeMock);

        IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage exception =
                new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage("Error general", mensajeFault);

        assertEquals("Error general", exception.getMessage());
        assertEquals(mensajeFault, exception.getFaultInfo());
    }

    @Test
    public void testConstructorWithMessageFaultInfoAndCause() {
        MensajeFault mensajeFault = new MensajeFault();
        QName qName = new QName("http://example.com", "miElemento", "ns1");
        JAXBElement<String> mensajeMock = new JAXBElement<>(qName, String.class, "Error en la actualización");
        mensajeFault.setMensaje(mensajeMock);

        Throwable cause = mock(Throwable.class);

        IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage exception =
                new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage("Error general", mensajeFault, cause);

        assertEquals("Error general", exception.getMessage());
        assertEquals(mensajeFault, exception.getFaultInfo());
        assertEquals(cause, exception.getCause());
    }
}
