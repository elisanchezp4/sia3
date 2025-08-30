package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.entidades.BitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Pruebas de los métodos expuestos por el ManejadorBitacoraAuditoria
 *
 * @author jsoto
 */
public class ManejadorBitacoraAuditoriaTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ManejadorBitacoraAuditoria manejadorBitacoraAuditoria;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFiltrarAuditoria() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new BitacoraAuditoria()));

        List<BitacoraAuditoria> result = manejadorBitacoraAuditoria.filtrarAuditoria(
                BigDecimal.valueOf(1L), BigDecimal.valueOf(2L),
                "2023-01-01", "2023-12-31", "user123",
                new RangoConsulta(0, 10), "campoActivo");


        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testContarRegistrosAuditoria() throws Exception {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(BigDecimal.valueOf(5L));

        Long result = manejadorBitacoraAuditoria.contarRegistrosAuditoria(
                BigDecimal.valueOf(1L), BigDecimal.valueOf(2L),
                "2023-01-01", "2023-12-31", "user123", "campoActivo");

        Assert.assertNotNull(result);
    }

}

