package co.gov.mineducacion.seguridad.web.soap.gestionar;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;

import static org.utbot.runtime.utils.java.UtUtils.deepEquals;
import static org.junit.Assert.assertTrue;

public final class ArrayOfinformacionUsuarioTest {

    @InjectMocks
    private ArrayOfinformacionUsuario arrayOfinformacionUsuario = new ArrayOfinformacionUsuario();

    @Test
    public void testGetInformacionUsuario_InformacionUsuarioNotEqualsNull() {
        ArrayList arrayList = new ArrayList();
        arrayOfinformacionUsuario.informacionUsuario = arrayList;

        ArrayList actual = ((ArrayList) arrayOfinformacionUsuario.getInformacionUsuario());

        assertTrue(deepEquals(arrayList, actual));
    }

    @Test
    public void testGetInformacionUsuario() {
        ArrayList actual = ((ArrayList) arrayOfinformacionUsuario.getInformacionUsuario());

        ArrayList expected = new ArrayList();
        assertTrue(deepEquals(expected, actual));
    }
}
