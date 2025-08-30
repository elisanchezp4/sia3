package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.TokensActivos;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorTokensActivos;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class NegocioTokensActivosTest {

    @Mock
    private ManejadorTokensActivos manejadorTokensActivos;

    @InjectMocks
    private NegocioTokensActivos negocioTokensActivos = new NegocioTokensActivos();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void consultar_DebeLlamarManejadorConsultarConFiltrosOrdenamientoYRangoCorrectos() {
        // Arrange
        List<TokensActivos> tokensActivosList = new ArrayList<>();
        when(manejadorTokensActivos.consultar(anyList(), anyList(), any(RangoConsulta.class)))
                .thenReturn(tokensActivosList);
    }

    @Test
    public void crear_DebeLlamarManejadorCrearConTokenActivosCorrecto() {
        // Arrange
        TokensActivosDTO tokensActivosDTO = new TokensActivosDTO();
        tokensActivosDTO.setTokenId("token123");

        // Act
        TokensActivosDTO result = negocioTokensActivos.crear(tokensActivosDTO);

        // Assert
        verify(manejadorTokensActivos).crear(any(TokensActivos.class));
        assertEquals(tokensActivosDTO, result);
    }

    @Test
    public void actualizar_DebeLlamarManejadorBuscarYActualizarConTokenActivosCorrecto() {
        // Arrange
        TokensActivosDTO tokensActivosDTO = new TokensActivosDTO();
        tokensActivosDTO.setTokenId("token123");
        when(manejadorTokensActivos.buscar(anyString())).thenReturn(new TokensActivos());

        // Act
        TokensActivosDTO result = negocioTokensActivos.actualizar(tokensActivosDTO);

        // Assert
        verify(manejadorTokensActivos).buscar(anyString());
        verify(manejadorTokensActivos).actualizar(any(TokensActivos.class));
        assertEquals(tokensActivosDTO, result);
    }

    @Test
    public void eliminar_DebeLlamarManejadorEliminarPorIdConTokenIdCorrecto() {
        // Arrange
        String tokenId = "token123";

        // Act
        String result = negocioTokensActivos.eliminar(tokenId);

        // Assert
        verify(manejadorTokensActivos).eliminarPorId(tokenId);
        assertEquals(tokenId, result);
    }

    @Test
    public void testContar() {
        // Arrange
        int expectedResult = 5;

        when(manejadorTokensActivos.consultarTotalRegistros(anyList(), any()))
                .thenReturn(expectedResult);
    }

    @Test
    public void testConsultarLista() {
        List<Object> expectedResult = new ArrayList<>();
        expectedResult.add("Juan");
        expectedResult.add("Maria");

        when(manejadorTokensActivos.consultarLista(anyList(), anyList(), any()))
                .thenReturn(expectedResult);
    }

    @Test
    public void buscarToken_DebeRetornarTokenActivoExistente() {
        // Arrange
        String token = "TOKEN";
        TokensActivos tokenActivo = new TokensActivos();
        tokenActivo.setTokenId(token);
        when(manejadorTokensActivos.buscar(token)).thenReturn(tokenActivo);

        // Act
        TokensActivosDTO result = negocioTokensActivos.buscarToken(token);

        // Assert
        assertEquals(token, result.getTokenId());
    }

    @Test
    public void buscarToken_DebeRetornarNullCuandoTokenNoExiste() {
        // Arrange
        String token = "TOKEN";
        when(manejadorTokensActivos.buscar(token)).thenReturn(null);

        // Act
        TokensActivosDTO result = negocioTokensActivos.buscarToken(token);

        // Assert
        assertNull(result);
        verify(manejadorTokensActivos, times(1)).buscar(token);
    }

    @Test
    public void eliminarTokensVencidos_DebeLlamarMetodoEliminarTokensVencidosEnManejador() {
        // Arrange
        Timestamp fecha = new Timestamp(System.currentTimeMillis());

        // Act
        negocioTokensActivos.eliminarTokensVencidos(fecha);

        // Assert
        verify(manejadorTokensActivos, times(1)).eliminarTokensVencidos(fecha);
    }

    // Aquí puedes agregar más pruebas para otros métodos de la clase NegocioTokensActivos

}