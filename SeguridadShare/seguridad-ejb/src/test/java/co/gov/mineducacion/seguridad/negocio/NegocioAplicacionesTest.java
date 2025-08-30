package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorAplicaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NegocioAplicacionesTest {

    @Mock
    private ManejadorAplicaciones manejadorAplicaciones = new ManejadorAplicaciones();

    @Mock
    private ManejadorUsuarios manejadorUsuarios = new ManejadorUsuarios();

    @InjectMocks
    private NegocioAplicaciones negocioAplicaciones = new NegocioAplicaciones();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAplicacionesPorUsuario_DebeRetornarListaAplicacionesDTO_CuandoUsuarioEsAdmin() throws SIA3Exception {
        // Arrange
        Long estado = 1L;
        BigDecimal usuarioId = BigDecimal.valueOf(123);

        List<Aplicaciones> aplicacionesList = new ArrayList<>();
        aplicacionesList.add(new Aplicaciones());
        aplicacionesList.add(new Aplicaciones());

        when(manejadorUsuarios.userIsAdmin(usuarioId)).thenReturn(true);
        when(manejadorAplicaciones.getAllAplicaciones(estado)).thenReturn(aplicacionesList);

        // Act
        List<AplicacionesDTO> result = negocioAplicaciones.getAplicacionesPorUsuario(estado, usuarioId);

        // Assert
        assertEquals(aplicacionesList.size(), result.size());
        verify(manejadorAplicaciones).getAllAplicaciones(estado);
        verify(manejadorUsuarios).userIsAdmin(usuarioId);
    }

    @Test
    public void getAplicacionesPorUsuario_DebeRetornarListaAplicacionesDTO_CuandoUsuarioNoEsAdmin() throws SIA3Exception {
        // Arrange
        Long estado = 1L;
        BigDecimal usuarioId = BigDecimal.valueOf(123);

        List<Aplicaciones> aplicacionesList = new ArrayList<>();
        aplicacionesList.add(new Aplicaciones());
        aplicacionesList.add(new Aplicaciones());

        when(manejadorUsuarios.userIsAdmin(usuarioId)).thenReturn(false);
        when(manejadorAplicaciones.getAplicacionesPorUsuario(estado, usuarioId)).thenReturn(aplicacionesList);

        // Act
        List<AplicacionesDTO> result = negocioAplicaciones.getAplicacionesPorUsuario(estado, usuarioId);

        // Assert
        assertEquals(aplicacionesList.size(), result.size());
        verify(manejadorAplicaciones).getAplicacionesPorUsuario(estado, usuarioId);
        verify(manejadorUsuarios).userIsAdmin(usuarioId);
    }

    @Test(expected = SIA3Exception.class)
    public void getAplicacionesPorUsuario_DebeLanzarExcepcion_CuandoPersistenceExceptionOcurre() throws SIA3Exception {
        // Arrange
        Long estado = 1L;
        BigDecimal usuarioId = BigDecimal.valueOf(123);

        when(manejadorUsuarios.userIsAdmin(usuarioId)).thenReturn(false);
        when(manejadorAplicaciones.getAplicacionesPorUsuario(estado, usuarioId)).thenThrow(new RuntimeException());

        // Act
        negocioAplicaciones.getAplicacionesPorUsuario(estado, usuarioId);

        // Assert
        // Excepción esperada
    }

    @Test(expected = SIA3Exception.class)
    public void getAplicacionesPorUsuario_DebeLanzarExcepcion_CuandoOcurreOtroError() throws SIA3Exception {
        // Arrange
        Long estado = 1L;
        BigDecimal usuarioId = BigDecimal.valueOf(123);

        when(manejadorUsuarios.userIsAdmin(usuarioId)).thenReturn(false);
        when(manejadorAplicaciones.getAplicacionesPorUsuario(estado, usuarioId)).thenThrow(new PersistenceException());

        // Act
        negocioAplicaciones.getAplicacionesPorUsuario(estado, usuarioId);

        // Assert
        // Excepción esperada
    }

    @Test
    public void consultarListadoDirectorioActivo_DebeRetornarListaCamposActivos() {
        // Arrange
        List<String> expectedCamposActivos = new ArrayList<>();
        expectedCamposActivos.add("usuarioId");
        expectedCamposActivos.add("nuevoPass");
        expectedCamposActivos.add("ruta");
        expectedCamposActivos.add("tipo");
        expectedCamposActivos.add("estado");
        expectedCamposActivos.add("fechaCreacion");
        expectedCamposActivos.add("ultimaModificacion");
        expectedCamposActivos.add("usuarioModificacion");
        expectedCamposActivos.add("loginUsuario");
        expectedCamposActivos.add("loginUsuario");
        expectedCamposActivos.add("apellidosUsuario");
        expectedCamposActivos.add("emailUsuario");
        expectedCamposActivos.add("nombres");
        expectedCamposActivos.add("password");
        expectedCamposActivos.add("logonName");
        expectedCamposActivos.add("nombreUsuario");
        expectedCamposActivos.add("numeroDocumento");
        expectedCamposActivos.add("nombreRol");
        expectedCamposActivos.add("roles");
        expectedCamposActivos.add("adressLocal");
        expectedCamposActivos.add("portLocal");
        expectedCamposActivos.add("fechaVinculacion");
        expectedCamposActivos.add("fechaDesvinculacion");
        expectedCamposActivos.add("estadoVinculacion");

        // Act
        List<String> result = negocioAplicaciones.consultarListadoDirectorioActivo();

        // Assert
        assertEquals(expectedCamposActivos.size(), result.size());
        assertEquals(expectedCamposActivos, result);
    }
}
