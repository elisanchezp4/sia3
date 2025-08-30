package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorPersistencia;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pruebas de los métodos expuestos por el ManejadorUsuariosRol
 *
 * @author jsoto
 */
public class ManejadorUsuariosRolTest {

    @Mock
    private EntityManager em;

    @Mock
    private ManejadorPersistencia<UsuariosRol> mp;

    @InjectMocks
    private ManejadorUsuariosRol manejadorUsuariosRol;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarUsuariosRolXUsuarioApp() {
        Query query = mock(Query.class);
        when(mp.createNamedQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new UsuariosRol()));

        List<UsuariosRol> result = manejadorUsuariosRol.buscarUsuariosRolXUsuarioApp("user123", BigDecimal.valueOf(1L));
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarUsuariosRolXUsuarioAppD() {
        Query query = mock(Query.class);
        when(mp.createNamedQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new UsuariosRol()));

        List<UsuariosRol> result = manejadorUsuariosRol.buscarUsuariosRolXUsuarioAppD("user123", BigDecimal.valueOf(1L));
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarUsuariosRolXUsuario() {
        Query query = mock(Query.class);
        when(mp.createNamedQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new UsuariosRol()));

        List<UsuariosRol> result = manejadorUsuariosRol.buscarUsuariosRolXUsuario("user123");
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testBuscarUsuariosRolXRol() {
        Query query = mock(Query.class);
        when(mp.createNamedQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new UsuariosRol()));

        List<UsuariosRol> result = manejadorUsuariosRol.buscarUsuariosRolXRol(BigDecimal.valueOf(1L));
        Assert.assertEquals(1, result.size());
    }
}

