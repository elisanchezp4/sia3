package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AplicacionesDTOTest {

    AplicacionesDTO aplicacionesDTO;

    @Mock
    private AplicacionesDTO mockAplicacionesDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        aplicacionesDTO = new AplicacionesDTO();
        aplicacionesDTO.setAplicacionId(new BigDecimal(1));
        aplicacionesDTO.setNombre("TestApp");
        aplicacionesDTO.setDescripcion("Testing application");
        aplicacionesDTO.setEstado(new BigDecimal(1));
        aplicacionesDTO.setFechaCreacion(null);
        aplicacionesDTO.setUltimaModificacion(null);
        aplicacionesDTO.setUsuarioModificacion(new BigDecimal(1));
        aplicacionesDTO.setUrlInicioExitoso("url");
        aplicacionesDTO.setMinVigTokenActConstrasenia(new BigDecimal(1));
        aplicacionesDTO.setMinutosVigenciaCodigo(new BigDecimal(1));
        aplicacionesDTO.setMinutosVigenciaToken(new BigDecimal(1));
        aplicacionesDTO.setRecibirNotificacion(new BigDecimal(1));
    }

    @Test
    public void testGetterAndSetter() {
        when(mockAplicacionesDTO.getAplicacionId()).thenReturn(aplicacionesDTO.getAplicacionId());
        when(mockAplicacionesDTO.getNombre()).thenReturn(aplicacionesDTO.getNombre());
        when(mockAplicacionesDTO.getDescripcion()).thenReturn(aplicacionesDTO.getDescripcion());
        when(mockAplicacionesDTO.getEstado()).thenReturn(aplicacionesDTO.getEstado());
        when(mockAplicacionesDTO.getFechaCreacion()).thenReturn(aplicacionesDTO.getFechaCreacion());
        when(mockAplicacionesDTO.getUltimaModificacion()).thenReturn(aplicacionesDTO.getUltimaModificacion());
        when(mockAplicacionesDTO.getUsuarioModificacion()).thenReturn(aplicacionesDTO.getUsuarioModificacion());
        when(mockAplicacionesDTO.getUrlInicioExitoso()).thenReturn(aplicacionesDTO.getUrlInicioExitoso());
        when(mockAplicacionesDTO.getMinVigTokenActConstrasenia()).thenReturn(aplicacionesDTO.getMinVigTokenActConstrasenia());
        when(mockAplicacionesDTO.getMinutosVigenciaCodigo()).thenReturn(aplicacionesDTO.getMinutosVigenciaCodigo());
        when(mockAplicacionesDTO.getMinutosVigenciaToken()).thenReturn(aplicacionesDTO.getMinutosVigenciaToken());
        when(mockAplicacionesDTO.getRecibirNotificacion()).thenReturn(aplicacionesDTO.getRecibirNotificacion());

        assertEquals(aplicacionesDTO.getAplicacionId(), mockAplicacionesDTO.getAplicacionId());
        assertEquals(aplicacionesDTO.getNombre(), mockAplicacionesDTO.getNombre());
        assertEquals(aplicacionesDTO.getDescripcion(), mockAplicacionesDTO.getDescripcion());
        assertEquals(aplicacionesDTO.getEstado(), mockAplicacionesDTO.getEstado());
        assertEquals(aplicacionesDTO.getFechaCreacion(), mockAplicacionesDTO.getFechaCreacion());
        assertEquals(aplicacionesDTO.getUltimaModificacion(), mockAplicacionesDTO.getUltimaModificacion());
        assertEquals(aplicacionesDTO.getUsuarioModificacion(), mockAplicacionesDTO.getUsuarioModificacion());
        assertEquals(aplicacionesDTO.getUrlInicioExitoso(), mockAplicacionesDTO.getUrlInicioExitoso());
        assertEquals(aplicacionesDTO.getUrlInicioExitoso(), mockAplicacionesDTO.getUrlInicioExitoso());
        assertEquals(aplicacionesDTO.getMinVigTokenActConstrasenia(), mockAplicacionesDTO.getMinVigTokenActConstrasenia());
        assertEquals(aplicacionesDTO.getMinutosVigenciaToken(), mockAplicacionesDTO.getMinutosVigenciaToken());
        assertEquals(aplicacionesDTO.getRecibirNotificacion(), mockAplicacionesDTO.getRecibirNotificacion());
    }
}
