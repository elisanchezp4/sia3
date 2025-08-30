package co.gov.mineducacion.seguridad.web.servicio.utils;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ArrayOfstringTest {

    @InjectMocks
    private ArrayOfstring mockArrayOfstring = new ArrayOfstring();

    @Test
    public void testGetString_NullList() {
        List<String> resultado = mockArrayOfstring.getString();

        assertNotNull(resultado);
        assertNotNull(mockArrayOfstring.getString());
        assertTrue(resultado instanceof ArrayList);
    }

}