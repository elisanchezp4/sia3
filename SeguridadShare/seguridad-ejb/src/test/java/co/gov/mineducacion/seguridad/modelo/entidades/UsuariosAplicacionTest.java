package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class UsuariosAplicacionTest {

    private UsuariosAplicacion usuariosAplicacion;

    @Mock
    private Usuario usuario;

    @Mock
    private Aplicaciones aplicacion;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuariosAplicacion = new UsuariosAplicacion();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        UsuariosAplicacionPK usuariosAplicacionPK = new UsuariosAplicacionPK();
        usuariosAplicacionPK.setUsuarioId("1");
        usuariosAplicacionPK.setAplicacionId(BigDecimal.valueOf(100));

        usuariosAplicacion.setUsuariosAplicacionPK(usuariosAplicacionPK);
        usuariosAplicacion.setUsuarios(usuario);
        usuariosAplicacion.setAplicaciones(aplicacion);

        // Act
        UsuariosAplicacionPK retrievedPK = usuariosAplicacion.getUsuariosAplicacionPK();
        Usuario retrievedUsuario = usuariosAplicacion.getUsuarios();
        Aplicaciones retrievedAplicacion = usuariosAplicacion.getAplicaciones();

        // Assert
        assertEquals(usuariosAplicacionPK, retrievedPK);
        assertEquals(usuario, retrievedUsuario);
        assertEquals(aplicacion, retrievedAplicacion);
    }

    @Test
    public void testContieneAtributo() {
        // Act
        boolean contiene = UsuariosAplicacion.contieneAtributo(UsuariosAplicacion.ENTIDAD_USUARIOS_APP_PK_USUARIO_ID);

        // Assert
        assertTrue(contiene);
    }

    @Test
    public void testEquals() {
        // Arrange
        UsuariosAplicacionPK usuariosAplicacionPK = new UsuariosAplicacionPK();
        usuariosAplicacionPK.setUsuarioId("1");
        usuariosAplicacionPK.setAplicacionId(BigDecimal.valueOf(100));

        UsuariosAplicacion otherUsuariosAplicacion = new UsuariosAplicacion();
        otherUsuariosAplicacion.setUsuariosAplicacionPK(usuariosAplicacionPK);
        otherUsuariosAplicacion.setUsuarios(usuario);
        otherUsuariosAplicacion.setAplicaciones(aplicacion);

        usuariosAplicacion.setUsuariosAplicacionPK(usuariosAplicacionPK);
        usuariosAplicacion.setUsuarios(usuario);
        usuariosAplicacion.setAplicaciones(aplicacion);

        // Act
        boolean areEqual = usuariosAplicacion.equals(otherUsuariosAplicacion);

        // Assert
        assertTrue(areEqual);
    }
}