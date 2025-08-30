package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.Assert.*;

public class ArrayOfstringTest {

    @InjectMocks
    private ArrayOfstring mockArrayOfstring = new ArrayOfstring();

    @Test
    public void testGetStringWhenListIsNull() {
        List<String> result = mockArrayOfstring.getString();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}