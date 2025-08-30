package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.Assert.*;

public class ArrayOfOpcionTest {

    @InjectMocks
    private ArrayOfOpcion mockArrayOfOpcion = new ArrayOfOpcion();

    @Test
    public void testGetOpcionWhenListIsNull() {
        List<Opcion> result = mockArrayOfOpcion.getOpcion();

        assertEquals(0, result.size());
    }
}