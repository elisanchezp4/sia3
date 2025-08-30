package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UsuarioTest {

    private Usuario usuario;

    @Mock
    private BitacoraAuditoria mockBitacoraAuditoria;
    @Mock
    private TokensActivos mockTokensActivos;
    @Mock
    private UsuariosRol mockUsuariosRol;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuario = new Usuario();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        String usuarioId = "user123";
        String ruta = "/users";
        String nuevoPass = "newpass123";
        BigDecimal tipo = BigDecimal.ONE;
        BigDecimal estado = BigDecimal.ZERO;
        Date fechaCreacion = new Date();
        Date ultimaModificacion = new Date();
        String usuarioModificacion = "admin";
        String logonName = "user123";

        usuario.setUsuarioId(usuarioId);
        usuario.setRuta(ruta);
        usuario.setNuevoPass(nuevoPass);
        usuario.setTipo(tipo);
        usuario.setEstado(estado);
        usuario.setFechaCreacion(fechaCreacion);
        usuario.setUltimaModificacion(ultimaModificacion);
        usuario.setUsuarioModificacion(usuarioModificacion);
        usuario.setLogonName(logonName);
        usuario.setBitacoraAuditoriaList(Arrays.asList(mockBitacoraAuditoria));
        usuario.setTokensActivosList(Arrays.asList(mockTokensActivos));
        usuario.setUsuariosRolList(Arrays.asList(mockUsuariosRol));

        // Act
        String retrievedUsuarioId = usuario.getUsuarioId();
        String retrievedRuta = usuario.getRuta();
        String retrievedNuevoPass = usuario.getNuevoPass();
        BigDecimal retrievedTipo = usuario.getTipo();
        BigDecimal retrievedEstado = usuario.getEstado();
        Date retrievedFechaCreacion = usuario.getFechaCreacion();
        Date retrievedUltimaModificacion = usuario.getUltimaModificacion();
        String retrievedUsuarioModificacion = usuario.getUsuarioModificacion();
        String retrievedLogonName = usuario.getLogonName();
        List<BitacoraAuditoria> retrievedBitacoraAuditoriaList = usuario.getBitacoraAuditoriaList();
        List<TokensActivos> retrievedTokensActivosList = usuario.getTokensActivosList();
        List<UsuariosRol> retrievedUsuariosRolList = usuario.getUsuariosRolList();

        // Assert
        assertEquals(usuarioId, retrievedUsuarioId);
        assertEquals(ruta, retrievedRuta);
        assertEquals(nuevoPass, retrievedNuevoPass);
        assertEquals(tipo, retrievedTipo);
        assertEquals(estado, retrievedEstado);
        assertEquals(fechaCreacion, retrievedFechaCreacion);
        assertEquals(ultimaModificacion, retrievedUltimaModificacion);
        assertEquals(usuarioModificacion, retrievedUsuarioModificacion);
        assertEquals(logonName, retrievedLogonName);
        assertEquals(mockBitacoraAuditoria, retrievedBitacoraAuditoriaList.get(0));
        assertEquals(mockTokensActivos, retrievedTokensActivosList.get(0));
        assertEquals(mockUsuariosRol, retrievedUsuariosRolList.get(0));
    }

    @Test
    public void testEquals() {
        // Arrange
        String usuarioId = "user123";
        String ruta = "/users";
        String nuevoPass = "newpass123";
        BigDecimal tipo = BigDecimal.ONE;
        BigDecimal estado = BigDecimal.ZERO;
        Date fechaCreacion = new Date();
        Date ultimaModificacion = new Date();
        String usuarioModificacion = "admin";
        String logonName = "user123";

        usuario.setUsuarioId(usuarioId);
        usuario.setRuta(ruta);
        usuario.setNuevoPass(nuevoPass);
        usuario.setTipo(tipo);
        usuario.setEstado(estado);
        usuario.setFechaCreacion(fechaCreacion);
        usuario.setUltimaModificacion(ultimaModificacion);
        usuario.setUsuarioModificacion(usuarioModificacion);
        usuario.setLogonName(logonName);
        usuario.setBitacoraAuditoriaList(Arrays.asList(mockBitacoraAuditoria));
        usuario.setTokensActivosList(Arrays.asList(mockTokensActivos));
        usuario.setUsuariosRolList(Arrays.asList(mockUsuariosRol));

        Usuario otherUsuario = new Usuario();
        otherUsuario.setUsuarioId(usuarioId);
        otherUsuario.setRuta(ruta);
        otherUsuario.setNuevoPass(nuevoPass);
        otherUsuario.setTipo(tipo);
        otherUsuario.setEstado(estado);
        otherUsuario.setFechaCreacion(fechaCreacion);
        otherUsuario.setUltimaModificacion(ultimaModificacion);
        otherUsuario.setUsuarioModificacion(usuarioModificacion);
        otherUsuario.setLogonName(logonName);
        otherUsuario.setBitacoraAuditoriaList(Arrays.asList(mockBitacoraAuditoria));
        otherUsuario.setTokensActivosList(Arrays.asList(mockTokensActivos));
        otherUsuario.setUsuariosRolList(Arrays.asList(mockUsuariosRol));

        // Act
        boolean result = usuario.equals(otherUsuario);

        // Assert
        assertTrue(result);
    }
}
