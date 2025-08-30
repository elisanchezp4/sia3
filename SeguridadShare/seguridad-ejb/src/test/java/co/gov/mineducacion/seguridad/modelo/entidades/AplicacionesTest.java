package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AplicacionesTest {

    private Aplicaciones aplicacion;

    @Before
    public void setUp() {
        aplicacion = new Aplicaciones();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BigDecimal aplicacionId = new BigDecimal(1);
        aplicacion.setAplicacionId(aplicacionId);

        String nombre = "Test App";
        aplicacion.setNombre(nombre);

        BigDecimal estado = new BigDecimal(1);
        aplicacion.setEstado(estado);

        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
        aplicacion.setFechaCreacion(fechaCreacion);

        Timestamp ultimaModificacion = new Timestamp(System.currentTimeMillis());
        aplicacion.setUltimaModificacion(ultimaModificacion);

        BigDecimal usuarioModificacion = new BigDecimal(123);
        aplicacion.setUsuarioModificacion(usuarioModificacion);

        BigDecimal minVigTokenActConstrasenia = new BigDecimal(15);
        aplicacion.setMinVigTokenActConstrasenia(minVigTokenActConstrasenia);

        BigDecimal minutosVigenciaCodigo = new BigDecimal(60);
        aplicacion.setMinutosVigenciaCodigo(minutosVigenciaCodigo);

        BigDecimal minutosVigenciaToken = new BigDecimal(30);
        aplicacion.setMinutosVigenciaToken(minutosVigenciaToken);

        BigDecimal recibirNotificacion = new BigDecimal(1);
        aplicacion.setRecibirNotificacion(recibirNotificacion);

        // Act
        BigDecimal retrievedId = aplicacion.getAplicacionId();
        String retrievedNombre = aplicacion.getNombre();
        BigDecimal retrievedEstado = aplicacion.getEstado();
        Timestamp retrievedFechaCreacion = aplicacion.getFechaCreacion();
        Timestamp retrievedUltimaModificacion = aplicacion.getUltimaModificacion();
        BigDecimal retrievedUsuarioModificacion = aplicacion.getUsuarioModificacion();
        BigDecimal retrievedMinVigTokenActConstrasenia = aplicacion.getMinVigTokenActConstrasenia();
        BigDecimal retrievedMinutosVigenciaCodigo = aplicacion.getMinutosVigenciaCodigo();
        BigDecimal retrievedMinutosVigenciaToken = aplicacion.getMinutosVigenciaToken();
        BigDecimal retrievedRecibirNotificacion = aplicacion.getRecibirNotificacion();

        // Assert
        assertEquals(aplicacionId, retrievedId);
        assertEquals(nombre, retrievedNombre);
        assertEquals(estado, retrievedEstado);
        assertEquals(fechaCreacion, retrievedFechaCreacion);
        assertEquals(ultimaModificacion, retrievedUltimaModificacion);
        assertEquals(usuarioModificacion, retrievedUsuarioModificacion);
        assertEquals(minVigTokenActConstrasenia, retrievedMinVigTokenActConstrasenia);
        assertEquals(minutosVigenciaCodigo, retrievedMinutosVigenciaCodigo);
        assertEquals(minutosVigenciaToken, retrievedMinutosVigenciaToken);
        assertEquals(recibirNotificacion, retrievedRecibirNotificacion);
    }

    @Test
    public void testToString() {
        // Arrange
        BigDecimal aplicacionId = new BigDecimal(1);
        aplicacion.setAplicacionId(aplicacionId);

        String nombre = "Test App";
        aplicacion.setNombre(nombre);

        BigDecimal estado = new BigDecimal(1);
        aplicacion.setEstado(estado);

        // Act
        String result = aplicacion.toString();

        // Assert
        assertNotNull(result);
    }
}
