package co.gov.mineducacion.utha.seguridad.web.servicio.utils;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

public class ResponseErrorTest {

    @InjectMocks
    private ResponseError mockResponseError = new ResponseError();

    @Test
    public void testGetterAndSetter(){
        String errorClass = "ErrorTest";
        String message = "MensaggeTest";

        mockResponseError.setErrorClass(errorClass);
        mockResponseError.setMessage(message);

        assertEquals(errorClass, mockResponseError.getErrorClass());
        assertEquals(message, mockResponseError.getMessage());
    }

    @Test
    public void testConstructorVacio() {
          assertNotNull(mockResponseError);
    }
}