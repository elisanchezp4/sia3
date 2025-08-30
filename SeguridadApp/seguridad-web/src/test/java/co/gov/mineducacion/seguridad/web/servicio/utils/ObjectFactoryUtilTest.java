package co.gov.mineducacion.seguridad.web.servicio.utils;

import org.junit.Test;
import org.mockito.InjectMocks;

import javax.xml.bind.JAXBElement;

import static org.junit.Assert.*;

public class ObjectFactoryUtilTest {

    @InjectMocks
    private ObjectFactoryUtil mockObjectFactoryUtil = new ObjectFactoryUtil();

    @Test
    public void testCreateArrayOfstringElement() {
        ArrayOfstring array = new ArrayOfstring();

        JAXBElement<ArrayOfstring> result = mockObjectFactoryUtil.createArrayOfstring(array);

        assertNotNull(result);
        assertEquals(array, result.getValue());
    }

}