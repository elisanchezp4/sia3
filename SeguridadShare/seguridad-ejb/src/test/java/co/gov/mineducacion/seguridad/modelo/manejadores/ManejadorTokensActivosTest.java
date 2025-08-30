package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.entidades.TokensActivos;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorPersistencia;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.Query;
import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas de los métodos expuestos por el ManejadorTokensActivos
 *
 * @author jsoto
 */
public class ManejadorTokensActivosTest {

    @Mock
    private ManejadorPersistencia<TokensActivos> mp;

    @InjectMocks
    private ManejadorTokensActivos manejadorTokensActivos;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testeliminarTokensVencidos() {
        Query query = mock(Query.class);
        when(mp.createNamedQuery(anyString())).thenReturn(query);
        Timestamp timestamp = mock(Timestamp.class);
        manejadorTokensActivos.eliminarTokensVencidos(timestamp);
        verify(query, times(1)).executeUpdate();

    }

}

