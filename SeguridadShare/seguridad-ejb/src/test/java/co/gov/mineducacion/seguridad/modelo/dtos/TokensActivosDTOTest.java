package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TokensActivosDTOTest {

    private TokensActivosDTO tokensActivosDTO;

    @Before
    public void setUp() {
        tokensActivosDTO = new TokensActivosDTO();
    }

    @Test
    public void testGetSetTokenId() {
        // Arrange
        String tokenId = "abc123";

        // Act
        tokensActivosDTO.setTokenId(tokenId);

        // Assert
        assertEquals(tokenId, tokensActivosDTO.getTokenId());
    }

    @Test
    public void testGetSetCreacion() {
        // Arrange
        Timestamp creacion = new Timestamp(System.currentTimeMillis());

        // Act
        tokensActivosDTO.setCreacion(creacion);

        // Assert
        assertEquals(creacion, tokensActivosDTO.getCreacion());
    }

    @Test
    public void testGetSetVencimiento() {
        // Arrange
        Timestamp vencimiento = new Timestamp(System.currentTimeMillis());

        // Act
        tokensActivosDTO.setVencimiento(vencimiento);

        // Assert
        assertEquals(vencimiento, tokensActivosDTO.getVencimiento());
    }

    @Test
    public void testGetSetTipo() {
        // Arrange
        BigDecimal tipo = new BigDecimal(1);

        // Act
        tokensActivosDTO.setTipo(tipo);

        // Assert
        assertEquals(tipo, tokensActivosDTO.getTipo());
    }

    @Test
    public void testGetSetUsuarioId() {
        // Arrange
        BigDecimal usuarioId = new BigDecimal(2);

        // Act
        tokensActivosDTO.setUsuarioId(usuarioId);

        // Assert
        assertEquals(usuarioId, tokensActivosDTO.getUsuarioId());
    }

    @Test
    public void testEqualsAndHashCode() {
        TokensActivosDTO tokens1 = new TokensActivosDTO();
        TokensActivosDTO tokens2 = new TokensActivosDTO();

        tokens1.setTokenId("abc123");
        tokens2.setTokenId("abc123");

        assertEquals(tokens1, tokens2);
        assertEquals(tokens1.hashCode(), tokens2.hashCode());
    }
}
