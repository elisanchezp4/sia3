package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorPersistencia;
import junit.framework.Assert;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pruebas de los métodos expuestos por el ManejadorOperaciones
 *
 * @author jsoto
 */
public class ManejadorOperacionesTest {

    @Mock
    private EntityManager em;

    @Mock
    private ManejadorPersistencia<Operaciones> mp;

    @InjectMocks
    private ManejadorOperaciones manejadorOperaciones;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarOperacionesRolXUsuario() {
        Query query2 = mock(Query.class);
        when(mp.createNamedQuery(anyString())).thenReturn(query2);
        when(query2.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.buscarOperacionesRolXUsuario(Collections.singletonList(BigDecimal.valueOf(1L)), BigDecimal.valueOf(2L));
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarOperacionesPorsuario() {
        Query query = mock(Query.class);
        when(mp.createNamedQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.buscarOperacionesPorsuario("user123", BigDecimal.valueOf(1L));
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetAllOperaciones() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.getAllOperaciones();

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetOperacionesPorRol() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.getOperacionesPorRol(123L);

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetOperacionPorId() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Operaciones());

        Operaciones result = manejadorOperaciones.getOperacionPorId(BigDecimal.valueOf(1L));

        Assert.assertNotNull(result);
    }

    @Test
    public void testBuscarOperacionesPorNombre() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.buscarOperacionesPorNombre("operation", "app123");

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetOperacionesPorFiltro() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.getOperacionesPorFiltro(BigDecimal.valueOf(1L), "operation");

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarOperacionesPorId() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.buscarOperacionesPorId(BigDecimal.valueOf(1L));

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarOperacionesHijas() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.buscarOperacionesHijas(BigDecimal.valueOf(1L));

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarOperacionesPorAplicacion() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.buscarOperacionesPorAplicacion(BigDecimal.valueOf(1L));

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarOperacionesPadres() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Operaciones.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Operaciones()));

        List<Operaciones> result = manejadorOperaciones.buscarOperacionesPadres(BigDecimal.valueOf(1L));

        Assert.assertEquals(1, result.size());
    }
}

