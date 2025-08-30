package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuarioRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Pruebas de los métodos expuestos por el ManejadorRoles
 *
 * @author jsoto
 */

public class ManejadorRolesTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ManejadorRoles manejadorRoles;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRoles() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Roles.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Roles()));
        List<Roles> result = manejadorRoles.getAllRoles();
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetRolesPorAplicacion() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Roles.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Roles()));
        List<Roles> result = manejadorRoles.getRolesPorAplicacion(123L);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarRolPorNombre() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Roles.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Roles()));
        List<Roles> result = manejadorRoles.buscarRolPorNombre("admin");
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetRolPorId() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Roles.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Roles());
        Roles result = manejadorRoles.getRolPorId(BigDecimal.valueOf(1L));
        Assert.assertNotNull(result);
    }

    @Test
    public void testBuscarRolPorId() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), eq(Roles.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Roles()));
        List<Roles> result = manejadorRoles.buscarRolPorId(BigDecimal.valueOf(1L));
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetRolesPorUsuario() {
        Query query = mock(Query.class);
        when(query.setHint(any(),any())).thenReturn(query);
        when(em.createNativeQuery(anyString(), eq(Roles.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Roles()));
        List<Roles> result = manejadorRoles.getRolesPorUsuario(123L);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetRolesPorUsuarioAplicacion() {
        Query query = mock(Query.class);
        when(query.setHint(any(),any())).thenReturn(query);
        when(em.createNativeQuery(anyString(), eq(Roles.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Roles()));
        List<Roles> result = manejadorRoles.getRolesPorUsuarioAplicacion(123L, BigDecimal.valueOf(1L));
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetUsuarioRoles() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Object[] {}));

        List<UsuariosRolDTO> result = manejadorRoles.getUsuarioRoles(123L);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetRolesUsuario() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Object[] {BigDecimal.ONE,BigDecimal.ONE,"",""}));

        List<UsuarioRolDTO> result = manejadorRoles.getRolesUsuario(123L);
        Assert.assertEquals(1, result.size());

    }

}

