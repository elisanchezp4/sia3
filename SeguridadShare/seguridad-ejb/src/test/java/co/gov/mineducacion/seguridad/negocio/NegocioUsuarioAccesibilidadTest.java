package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.entidades.PropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorPropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarioAccesibilidad;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class NegocioUsuarioAccesibilidadTest {

    PropiedadesAccesibilidad propiedadesAccesibilidad;

    @Mock
    private ManejadorUsuarioAccesibilidad manejadorUsuarioAccesibilidad;

    @Mock
    private ManejadorPropiedadesAccesibilidad manejadorPropiedadesAccesibilidad;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private NegocioUsuarioAccesibilidad negocioUsuarioAccesibilidad = new NegocioUsuarioAccesibilidad();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        propiedadesAccesibilidad = new PropiedadesAccesibilidad();
        propiedadesAccesibilidad.setPropiedadAccId("propId");
    }

    @Test
    public void consultrarPropAccUsuario_DebeRetornarAccesibilidadPorUsuarioExistente() {
        // Arrange
        String usrsId = "USER1";
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usrsId);
        List<UsuarioAccesibilidad> listUsrsAcc = new ArrayList<>();
        UsuarioAccesibilidad usuarioAccesibilidad = new UsuarioAccesibilidad();
        usuarioAccesibilidad.setPropAcc(new PropiedadesAccesibilidad());
        usuarioAccesibilidad.setUsuarios(usuario);
        listUsrsAcc.add(usuarioAccesibilidad);
        when(manejadorUsuarioAccesibilidad.consultarPropAccUsrs(usrsId)).thenReturn(listUsrsAcc);

        // Act
        Map<String, Object> result = negocioUsuarioAccesibilidad.consultrarPropAccUsuario(usrsId);

        // Assert
        Assert.assertFalse(result.isEmpty());
        verify(manejadorUsuarioAccesibilidad, times(1)).consultarPropAccUsrs(usrsId);
    }

    @Test
    public void consultrarPropAccUsuario_DebeCrearPropiedadesPorDefectoCuandoUsuarioNoTiene() {
        // Arrange
        String usrsId = "USER1";
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usrsId);
        UsuarioAccesibilidad propAcc = new UsuarioAccesibilidad();
        propAcc.setValorPropiedad("propVal");
        propAcc.setPropAcc(propiedadesAccesibilidad);
        List<UsuarioAccesibilidad> listUsrsAcc = new ArrayList<>();
        listUsrsAcc.add(propAcc);
        when(manejadorUsuarioAccesibilidad.consultarPropAccUsrs(usrsId)).thenReturn(listUsrsAcc);

        // Act
        Map<String, Object> result = negocioUsuarioAccesibilidad.consultrarPropAccUsuario(usrsId);
        System.err.println(result);

        // Assert
        Assert.assertFalse(result.isEmpty());
        verify(manejadorUsuarioAccesibilidad, times(1)).consultarPropAccUsrs(usrsId);
    }

    @Test
    public void almacenarParamAcces_DebeActualizarPropiedadDeAccesibilidad() throws Exception {
        // Arrange
        String valor = "new_value";
        String propId = "PROP1";
        String userId = "USER1";
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);

        // Act
        negocioUsuarioAccesibilidad.almacenarParamAcces(valor, propId, userId);

        // Assert
        verify(entityManager, times(1)).createQuery(anyString());
        verify(query, times(1)).setParameter("valor", valor);
        verify(query, times(1)).setParameter("id", userId);
        verify(query, times(1)).setParameter("propId", propId);
        verify(query, times(1)).executeUpdate();
    }

}
