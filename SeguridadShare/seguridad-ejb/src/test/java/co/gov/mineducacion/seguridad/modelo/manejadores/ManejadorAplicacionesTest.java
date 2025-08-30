package co.gov.mineducacion.seguridad.modelo.manejadores;


import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Pruebas de los métodos expuestos por el ManejadorAplicaciones
 *
 * @author jsoto
 */
public class ManejadorAplicacionesTest {
    @Mock
    private EntityManager em;

    @Mock
    private Query query;

    @InjectMocks
    private ManejadorAplicaciones manejadorAplicaciones;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAplicaciones() {
        when(em.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Aplicaciones()));

        List<Aplicaciones> result = manejadorAplicaciones.getAllAplicaciones(1L);

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarAplicacionPorNombre() {
        when(em.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Aplicaciones()));

        List<Aplicaciones> result = manejadorAplicaciones.buscarAplicacionPorNombre("App Name");

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarAplicacionPorId() {
        when(em.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Aplicaciones()));

        List<Aplicaciones> result = manejadorAplicaciones.buscarAplicacionPorId(BigDecimal.valueOf(1L));

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testContarAplicaciones() {
        when(em.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(10L));

        Long result = manejadorAplicaciones.contarAplicaciones();
        Assert.assertNotNull(result);
        // Add assertions to verify the behavior
    }

    @Test
    public void testGetAplicacionesPorUsuario() {
        when(em.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Aplicaciones()));

        List<Aplicaciones> result = manejadorAplicaciones.getAplicacionesPorUsuario(1L, BigDecimal.valueOf(1L));

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetAplicacionesNombre() {
        when(em.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Aplicaciones()));

        List<Aplicaciones> result = manejadorAplicaciones.getAplicacionesNombre(1L, "App Name");

        Assert.assertEquals(1, result.size());
    }

    // Add more test methods if needed
}

