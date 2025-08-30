package co.gov.mineducacion.utha.seguridad.web.servicio.accesibilidad;

import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarioAccesibilidad;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;


import java.util.HashMap;
import java.util.Map;

public class ServicioPropiedadesAccUserTest {

    @InjectMocks
    private ServicioPropiedadesAccUser mockServicioPropiedadesAccUser = new ServicioPropiedadesAccUser();

    @Mock
    private IUsuarioAccesibilidad mockIUsuarioAccesibilidad;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockServicioPropiedadesAccUser.servicio = mockIUsuarioAccesibilidad;
    }

    @Test
    public void testObtenerPropAccesibilidadConExcepcion() {
        when(mockIUsuarioAccesibilidad.consultaPropAcceUsuario(anyString())).thenThrow(new RuntimeException("Mocked exception"));

        Response response = mockServicioPropiedadesAccUser.obtenerPropAccesibilidad("TestUser1");

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        String responseEntity = response.getEntity().toString();
        assertTrue(responseEntity.contains("ExceptionMessage"));
        assertTrue(responseEntity.contains("InternalCode"));
    }

    @Test
    public void testObtenerPropAccesibilidadSinExcepcion() {
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("Properties1", "Valor1");
        when(mockIUsuarioAccesibilidad.consultaPropAcceUsuario(eq("TestUser2"))).thenReturn(propiedades);

        Response response = mockServicioPropiedadesAccUser.obtenerPropAccesibilidad("TestUser2");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String responseEntity = response.getEntity().toString();
        assertTrue(responseEntity.contains("accesibilidad"));
        assertTrue(responseEntity.contains("Properties1"));
        assertTrue(responseEntity.contains("Valor1"));
    }

    @Test
    public void testObtenerPropAccesibilidadConInternalCode() {
        // Configurar el mock para simular la presencia de "InternalCode"
        Map<String, Object> accesibilidad = new HashMap<>();
        accesibilidad.put("InternalCode", "error123");
        when(mockIUsuarioAccesibilidad.consultaPropAcceUsuario(anyString())).thenReturn(accesibilidad);

        // Ejecutar el método bajo prueba
        Response response = mockServicioPropiedadesAccUser.obtenerPropAccesibilidad("TestUser1");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String responseEntity = response.getEntity().toString();
        assertTrue(responseEntity.contains("InternalCode"));
    }
}