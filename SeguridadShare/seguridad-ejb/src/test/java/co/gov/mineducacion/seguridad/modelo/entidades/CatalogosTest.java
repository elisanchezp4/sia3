package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CatalogosTest {

    private Catalogos catalogo;

    @Before
    public void setUp() {
        catalogo = new Catalogos();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BigDecimal catalogoId = new BigDecimal(1);
        catalogo.setCatalogoIdId(catalogoId);

        String tipoCatalogo = "Tipo de Catalogo";
        catalogo.setNombre(tipoCatalogo);

        String descripcion = "Descripción del catálogo";
        catalogo.setDescripcion(descripcion);

        String textoAyuda = "Texto de ayuda";
        catalogo.setTextoAyuda(textoAyuda);

        // Act
        BigDecimal retrievedId = catalogo.getCatalogoIdId();
        String retrievedTipoCatalogo = catalogo.getNombre();
        String retrievedDescripcion = catalogo.getDescripcion();
        String retrievedTextoAyuda = catalogo.getTextoAyuda();

        // Assert
        assertEquals(catalogoId, retrievedId);
        assertEquals(tipoCatalogo, retrievedTipoCatalogo);
        assertEquals(descripcion, retrievedDescripcion);
        assertEquals(textoAyuda, retrievedTextoAyuda);
    }

    @Test
    public void testEquals() {
        // Arrange
        Catalogos catalogo1 = new Catalogos();
        catalogo1.setCatalogoIdId(new BigDecimal(1));

        Catalogos catalogo2 = new Catalogos();
        catalogo2.setCatalogoIdId(new BigDecimal(1));

        // Act
        boolean result = catalogo1.equals(catalogo2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testToString() {
        // Arrange
        BigDecimal catalogoId = new BigDecimal(1);
        catalogo.setCatalogoIdId(catalogoId);

        // Act
        String result = catalogo.toString();

        // Assert
        assertNotNull(result);
    }
}
