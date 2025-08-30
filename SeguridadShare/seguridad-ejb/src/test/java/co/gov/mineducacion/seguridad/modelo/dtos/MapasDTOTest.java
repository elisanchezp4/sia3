package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.TreeMap;

public class MapasDTOTest {

    @Mock
    private TreeMap<BigDecimal, BigDecimal> mockMapaPadreHijo;

    @Mock
    private TreeMap<String, OperacionesDTO> mockMapaCompleto;

    private MapasDTO mapasDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mapasDTO = new MapasDTO(mockMapaPadreHijo, mockMapaCompleto);
    }

    @Test
    public void testGetterAndSetter() {
        TreeMap<BigDecimal, BigDecimal> mapaPadreHijo = new TreeMap<>();
        mapaPadreHijo.put(new BigDecimal(1), new BigDecimal(2));

        TreeMap<String, OperacionesDTO> mapaCompleto = new TreeMap<>();
        OperacionesDTO operacionesDTO = new OperacionesDTO();
        mapaCompleto.put("key", operacionesDTO);

        mapasDTO.setMapaPadreHijo(mapaPadreHijo);
        mapasDTO.setMapaCompleto(mapaCompleto);

        assertEquals(mapaPadreHijo, mapasDTO.getMapaPadreHijo());
        assertEquals(mapaCompleto, mapasDTO.getMapaCompleto());
    }

    @Test
    public void testConstructor() {
        MapasDTO mapasDTO = new MapasDTO(mockMapaPadreHijo, mockMapaCompleto);

        assertEquals(mockMapaPadreHijo, mapasDTO.getMapaPadreHijo());
        assertEquals(mockMapaCompleto, mapasDTO.getMapaCompleto());
    }
}
