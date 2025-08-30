package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CatalogosDTOTest {

    private CatalogosDTO catalogosDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        catalogosDTO = new CatalogosDTO();
    }

    @Test
    public void testGetterAndSetter() {
        BigDecimal catalogoId = new BigDecimal(1);
        String descripcion = "TestDescription";
        String textoAyuda = "TestHelpText";

        catalogosDTO.setCatalogoId(catalogoId);
        catalogosDTO.setDescripcion(descripcion);
        catalogosDTO.setTextoAyuda(textoAyuda);

        assertEquals(catalogoId, catalogosDTO.getCatalogoId());
        assertEquals(descripcion, catalogosDTO.getDescripcion());
        assertEquals(textoAyuda, catalogosDTO.getTextoAyuda());
    }

    @Test
    public void testEquals() {
        CatalogosDTO other = new CatalogosDTO();
        other.setCatalogoId(catalogosDTO.getCatalogoId());
        other.setDescripcion(catalogosDTO.getDescripcion());
        other.setTextoAyuda(catalogosDTO.getTextoAyuda());

        assertTrue(catalogosDTO.equals(other));
    }
}
