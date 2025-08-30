package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class TokensActivosTest {

    private TokensActivos tokensActivos;

    @Mock
    private Usuario mockUsuario;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tokensActivos = new TokensActivos();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        String tokenId = "token123";
        Timestamp creacion = new Timestamp(new Date().getTime());
        Timestamp vencimiento = new Timestamp(new Date().getTime() + 3600000); // 1 hour later
        BigDecimal tipo = BigDecimal.ONE;
        BigDecimal usuarioId = BigDecimal.valueOf(123);

        tokensActivos.setTokenId(tokenId);
        tokensActivos.setCreacion(creacion);
        tokensActivos.setVencimiento(vencimiento);
        tokensActivos.setTipo(tipo);
        tokensActivos.setUsuarioId(usuarioId);
        tokensActivos.setUsuarios(mockUsuario);

        // Act
        String retrievedTokenId = tokensActivos.getTokenId();
        Timestamp retrievedCreacion = tokensActivos.getCreacion();
        Timestamp retrievedVencimiento = tokensActivos.getVencimiento();
        BigDecimal retrievedTipo = tokensActivos.getTipo();
        BigDecimal retrievedUsuarioId = tokensActivos.getUsuarioId();
        Usuario retrievedUsuario = tokensActivos.getUsuarios();

        // Assert
        assertEquals(tokenId, retrievedTokenId);
        assertEquals(creacion, retrievedCreacion);
        assertEquals(vencimiento, retrievedVencimiento);
        assertEquals(tipo, retrievedTipo);
        assertEquals(usuarioId, retrievedUsuarioId);
        assertEquals(mockUsuario, retrievedUsuario);
    }

    @Test
    public void testEquals() {
        // Arrange
        String tokenId = "token123";
        Timestamp creacion = new Timestamp(new Date().getTime());
        Timestamp vencimiento = new Timestamp(new Date().getTime() + 3600000); // 1 hour later
        BigDecimal tipo = BigDecimal.ONE;
        BigDecimal usuarioId = BigDecimal.valueOf(123);

        tokensActivos.setTokenId(tokenId);
        tokensActivos.setCreacion(creacion);
        tokensActivos.setVencimiento(vencimiento);
        tokensActivos.setTipo(tipo);
        tokensActivos.setUsuarioId(usuarioId);
        tokensActivos.setUsuarios(mockUsuario);

        TokensActivos otherTokensActivos = new TokensActivos();
        otherTokensActivos.setTokenId(tokenId);
        otherTokensActivos.setCreacion(creacion);
        otherTokensActivos.setVencimiento(vencimiento);
        otherTokensActivos.setTipo(tipo);
        otherTokensActivos.setUsuarioId(usuarioId);
        otherTokensActivos.setUsuarios(mockUsuario);

        // Act
        boolean result = tokensActivos.equals(otherTokensActivos);

        // Assert
        assertTrue(result);
    }
}
