package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class UsuariosAplicacionEntityTest {

    private UsuariosAplicacionEntity usuariosAplicacionEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuariosAplicacionEntity = new UsuariosAplicacionEntity();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BigDecimal usuarioId = new BigDecimal(1);
        BigDecimal aplicacionId = new BigDecimal(100);

        usuariosAplicacionEntity.setUsuarioId(usuarioId);
        usuariosAplicacionEntity.setAplicacionId(aplicacionId);

        // Act
        BigDecimal retrievedUsuarioId = usuariosAplicacionEntity.getUsuarioId();
        BigDecimal retrievedAplicacionId = usuariosAplicacionEntity.getAplicacionId();

        // Assert
        assertEquals(usuarioId, retrievedUsuarioId);
        assertEquals(aplicacionId, retrievedAplicacionId);
    }
    
    @Test
    public void testEquals() {
        // Arrange
        BigDecimal usuarioId = new BigDecimal(1);
        BigDecimal aplicacionId = new BigDecimal(100);

        UsuariosAplicacionEntity otherUsuariosAplicacionEntity = new UsuariosAplicacionEntity();
        otherUsuariosAplicacionEntity.setUsuarioId(usuarioId);
        otherUsuariosAplicacionEntity.setAplicacionId(aplicacionId);

        usuariosAplicacionEntity.setUsuarioId(usuarioId);
        usuariosAplicacionEntity.setAplicacionId(aplicacionId);

        // Act
        boolean areEqual = usuariosAplicacionEntity.equals(otherUsuariosAplicacionEntity);

        // Assert
        assertTrue(areEqual);
    }
}