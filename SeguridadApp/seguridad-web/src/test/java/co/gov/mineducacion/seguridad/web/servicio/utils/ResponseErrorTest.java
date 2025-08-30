package co.gov.mineducacion.seguridad.web.servicio.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseErrorTest {

    @Test
    public void testConstructor() {
        ResponseError error = new ResponseError();

        assertNotNull(error);
    }
}