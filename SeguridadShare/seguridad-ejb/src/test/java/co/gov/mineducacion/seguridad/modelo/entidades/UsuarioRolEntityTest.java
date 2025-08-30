package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class UsuarioRolEntityTest {

    private UsuarioRolEntity usuarioRolEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioRolEntity = new UsuarioRolEntity();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BigDecimal rolId = new BigDecimal(1);
        BigDecimal usuarioId = new BigDecimal(100);
        Date fechaVinculacion = new Date();
        Date fechaDesvinculacion = new Date();
        String estadoVinculacion = "VINCULADO";

        usuarioRolEntity.setRolId(rolId);
        usuarioRolEntity.setUsuarioId(usuarioId);
        usuarioRolEntity.setFechaVinculacion(fechaVinculacion);
        usuarioRolEntity.setFechaDesvinculacion(fechaDesvinculacion);
        usuarioRolEntity.setEstadoVinculacion(estadoVinculacion);

        // Act
        BigDecimal retrievedRolId = usuarioRolEntity.getRolId();
        BigDecimal retrievedUsuarioId = usuarioRolEntity.getUsuarioId();
        Date retrievedFechaVinculacion = usuarioRolEntity.getFechaVinculacion();
        Date retrievedFechaDesvinculacion = usuarioRolEntity.getFechaDesvinculacion();
        String retrievedEstadoVinculacion = usuarioRolEntity.getEstadoVinculacion();

        // Assert
        assertEquals(rolId, retrievedRolId);
        assertEquals(usuarioId, retrievedUsuarioId);
        assertEquals(fechaVinculacion, retrievedFechaVinculacion);
        assertEquals(fechaDesvinculacion, retrievedFechaDesvinculacion);
        assertEquals(estadoVinculacion, retrievedEstadoVinculacion);
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        BigDecimal rolId = new BigDecimal(1);
        BigDecimal usuarioId = new BigDecimal(100);
        Date fechaVinculacion = new Date();
        Date fechaDesvinculacion = new Date();
        String estadoVinculacion = "VINCULADO";

        usuarioRolEntity.setRolId(rolId);
        usuarioRolEntity.setUsuarioId(usuarioId);
        usuarioRolEntity.setFechaVinculacion(fechaVinculacion);
        usuarioRolEntity.setFechaDesvinculacion(fechaDesvinculacion);
        usuarioRolEntity.setEstadoVinculacion(estadoVinculacion);

        UsuarioRolEntity otherEntity = new UsuarioRolEntity();
        otherEntity.setRolId(rolId);
        otherEntity.setUsuarioId(usuarioId);
        otherEntity.setFechaVinculacion(fechaVinculacion);
        otherEntity.setFechaDesvinculacion(fechaDesvinculacion);
        otherEntity.setEstadoVinculacion(estadoVinculacion);

        // Act
        boolean areEqual = usuarioRolEntity.equals(otherEntity);
        int hashCode1 = usuarioRolEntity.hashCode();
        int hashCode2 = otherEntity.hashCode();

        // Assert
        assertTrue(areEqual);
        assertEquals(hashCode1, hashCode2);
    }
}
