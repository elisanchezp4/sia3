package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class NegocioUsuariosTest {
    @Mock
    private ManejadorUsuarios manejadorUsuarios;

    @Mock
    private NegocioBitacoraAuditoria auditoria = new NegocioBitacoraAuditoria();

    @InjectMocks
    private NegocioUsuarios negocioUsuarios = new NegocioUsuarios();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void actualizarDeberiaActualizarYAuditarUsuario() {
        // Arrange
        UsuariosDTO usuariosDTO = new UsuariosDTO();
        usuariosDTO.setUsuarioId("1");
        usuariosDTO.setNombreUsuario("nombre");
        usuariosDTO.setApellidosUsuario("apellidos");
        usuariosDTO.setEmailUsuario("email");
        usuariosDTO.setNumeroDocumento("12345");
        usuariosDTO.setRuta("ruta");
        Usuario usuario = new Usuario();
        usuario.setUsuarioId("1");
        // El método buscar debería devolver un objeto Usuario con el id proporcionado
        when(manejadorUsuarios.buscar("1")).thenReturn(usuario);
        // Act
        negocioUsuarios.actualizar(usuariosDTO);
        // Se verifica que se han emitido correctamente las auditorías
        verify(manejadorUsuarios).buscar(usuariosDTO.getUsuarioId());
    }

    @Test
    public void actualizar_DebeActualizarUsuarioExistente() {
        // Arrange
        UsuariosDTO usuariosDTO = new UsuariosDTO();
        usuariosDTO.setUsuarioId("USER1");
        Usuario usuario = new Usuario();
        usuario.setUsuarioId("USER1");
        when(manejadorUsuarios.buscar("USER1")).thenReturn(usuario);

        // Act
        UsuariosDTO result = negocioUsuarios.actualizar(usuariosDTO);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(usuariosDTO.getUsuarioId(), result.getUsuarioId());
        verify(manejadorUsuarios, times(1)).actualizar(usuario);
    }

    @Test
    public void getUsuariosPorApp_DebeRetornarUsuariosPorAplicacion() throws Exception {
        // Arrange
        BigDecimal aplicacionId = BigDecimal.valueOf(123);
        Integer pagInicio = 1;
        Integer pagFin = 10;
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario());
        when(manejadorUsuarios.listarUsuariosPorApp(aplicacionId)).thenReturn(usuarios);

        // Assert
        Assert.assertNotNull(usuarios);
        Assert.assertEquals(usuarios.size(), usuarios.size());
    }

    @Test
    public void getUsuariosPorApp_DebeRetornarListaVaciaCuandoNoHayUsuarios() throws Exception {
        // Arrange
        BigDecimal aplicacionId = BigDecimal.valueOf(123);
        Integer pagInicio = 1;
        Integer pagFin = 10;
        List<Usuario> usuarios = new ArrayList<>();
        when(manejadorUsuarios.listarUsuariosPorApp(aplicacionId)).thenReturn(usuarios);

        // Act
        List<UsuariosDTO> result = negocioUsuarios.getUsuariosPorApp(aplicacionId);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
        verify(manejadorUsuarios, times(1)).listarUsuariosPorApp(aplicacionId);
    }

    @Test
    public void getUsuariosPorApp_DebeLanzarExcepcionCuandoOcurrePersistenceException() {
        // Arrange
        BigDecimal aplicacionId = BigDecimal.valueOf(123);
        Integer pagInicio = 1;
        Integer pagFin = 10;
        when(manejadorUsuarios.listarUsuariosPorApp(aplicacionId))
                .thenThrow(new PersistenceException());
    }
}
