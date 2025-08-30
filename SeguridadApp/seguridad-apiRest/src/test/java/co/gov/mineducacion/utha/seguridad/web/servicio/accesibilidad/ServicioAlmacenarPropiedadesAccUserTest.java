package co.gov.mineducacion.utha.seguridad.web.servicio.accesibilidad;

import co.gov.mineducacion.seguridad.negocio.NegocioColaAccesibilidad;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public final class ServicioAlmacenarPropiedadesAccUserTest {
    @InjectMocks
    private ServicioAlmacenarPropiedadesAccUser mockServicioAlmacenarPropiedadesAccUser = new ServicioAlmacenarPropiedadesAccUser();

    @Mock
    private NegocioColaAccesibilidad mockNegocioColaAccesibilidad;
    @Mock
    private EntityManager mockEntityManager;

    @Before
    public void setUp() throws Exception {
        mockNegocioColaAccesibilidad = mock(NegocioColaAccesibilidad.class);

        java.lang.reflect.Field field = mockServicioAlmacenarPropiedadesAccUser.getClass().getDeclaredField("negocioColaAccesibilidad");
        field.setAccessible(true);
        field.set(mockServicioAlmacenarPropiedadesAccUser, mockNegocioColaAccesibilidad);
    }

    @Test
    public void testAlmacenarPropAccesSuccess() {
        String usrsId = "user123";
        Map<String, String> props = new HashMap<>();
        props.put("propiedad1", "valor1");
        props.put("propiedad2", "valor2");

        mockNegocioColaAccesibilidad.enviarColaPropAccesibilidad(props, usrsId);

        Map<String, String> propiedades = new HashMap<>();
        String userId = "123";
        String token = "token_value";

        Response response = mockServicioAlmacenarPropiedadesAccUser.almacenarPropAcces(propiedades, userId, token);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAlmacenarPropAcces_ExceptionHandling() {
        Map<String, String> propiedades = new HashMap<>();
        String userId = "user123";

        doThrow(new RuntimeException("Simulated exception")).when(mockNegocioColaAccesibilidad).enviarColaPropAccesibilidad(any(), any());

        Response response = mockServicioAlmacenarPropiedadesAccUser.almacenarPropAcces(propiedades, userId, "token");

        verify(mockNegocioColaAccesibilidad).enviarColaPropAccesibilidad(propiedades, userId);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("No se pudo obtener la información solicitada", entity.get("mensaje"));
        assertEquals("-1", entity.get("codigo"));
    }

}
