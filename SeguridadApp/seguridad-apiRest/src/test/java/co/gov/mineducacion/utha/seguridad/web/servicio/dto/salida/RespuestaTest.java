package co.gov.mineducacion.utha.seguridad.web.servicio.dto.salida;

import org.junit.Test;

import static org.junit.Assert.*;

public class RespuestaTest {

    @Test
    public void testGetCodigo(){
        int codigoEsperado = 200;
        Respuesta respuesta = new Respuesta(codigoEsperado, "Éxito");

        int codigoObtenido = respuesta.getCodigo();

        assertEquals(codigoEsperado, codigoObtenido);
    }

    @Test
    public void testGetMensaje() {
        String mensajeEsperado = "Mensaje de prueba";
        Respuesta respuesta = new Respuesta(404, mensajeEsperado);

        Object mensajeObtenido = respuesta.getMensaje();

        assertEquals(mensajeEsperado, mensajeObtenido);
    }
}