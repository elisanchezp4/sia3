package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BitacoraAuditoriaTest {

    private BitacoraAuditoria bitacora;

    @Before
    public void setUp() {
        bitacora = new BitacoraAuditoria();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BigDecimal bitacoraId = new BigDecimal(1);
        bitacora.setBitacoraId(bitacoraId);

        Timestamp fechaEvento = new Timestamp(System.currentTimeMillis());
        bitacora.setFechaEvento(fechaEvento);

        BigDecimal tipoEvento = new BigDecimal(1);
        bitacora.setTipoEvento(tipoEvento);

        String usuarioId = "testUser";
        bitacora.setUsuarioId(usuarioId);

        BigDecimal aplicacionId = new BigDecimal(2);
        bitacora.setAplicacionId(aplicacionId);

        // Act
        BigDecimal retrievedId = bitacora.getBitacoraId();
        Timestamp retrievedFechaEvento = bitacora.getFechaEvento();
        BigDecimal retrievedTipoEvento = bitacora.getTipoEvento();
        String retrievedUsuarioId = bitacora.getUsuarioId();
        BigDecimal retrievedAplicacionId = bitacora.getAplicacionId();

        // Assert
        assertEquals(bitacoraId, retrievedId);
        assertEquals(fechaEvento, retrievedFechaEvento);
        assertEquals(tipoEvento, retrievedTipoEvento);
        assertEquals(usuarioId, retrievedUsuarioId);
        assertEquals(aplicacionId, retrievedAplicacionId);
    }

    @Test
    public void testEquals() {
        // Arrange
        BitacoraAuditoria bitacora1 = new BitacoraAuditoria();
        bitacora1.setBitacoraId(new BigDecimal(1));

        BitacoraAuditoria bitacora2 = new BitacoraAuditoria();
        bitacora2.setBitacoraId(new BigDecimal(1));

        // Act
        boolean result = bitacora1.equals(bitacora2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testToString() {
        // Arrange
        BigDecimal bitacoraId = new BigDecimal(1);
        bitacora.setBitacoraId(bitacoraId);

        // Act
        String result = bitacora.toString();

        // Assert
        assertNotNull(result);
    }
}
