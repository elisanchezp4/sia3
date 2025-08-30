package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperaciones;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class NegocioOperacionesTest {

    @Mock
    private ManejadorOperaciones manejadorOperaciones;

    @InjectMocks
    private NegocioOperaciones negocioOperaciones = new NegocioOperaciones();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImportar() {
        // Preparación
        List<Operaciones> operacionesExistentes = new ArrayList<>();
        Operaciones operacionExistente = new Operaciones();
        operacionExistente.setNombreObjeto("operacion1");
        operacionesExistentes.add(operacionExistente);

        when(manejadorOperaciones.buscarOperacionesPorNombre(anyString(), anyString())).thenReturn(operacionesExistentes);

    }

    @Test
    public void testBuscarOperacionesPorNombreYAplicacion() {
        // Preparación
        String nombre = "operacion1";
        long aplicacionId = 1L;

        List<Operaciones> operacionesEncontradas = new ArrayList<>();
        Operaciones operacionEncontrada = new Operaciones();
        operacionEncontrada.setNombreObjeto("operacion1");
        operacionEncontrada.setAplicacionId(BigDecimal.valueOf(aplicacionId));
        operacionesEncontradas.add(operacionEncontrada);

        when(manejadorOperaciones.buscarOperacionesPorNombre(anyString(), anyString())).thenReturn(operacionesEncontradas);

        // Ejecución
        when(negocioOperaciones.buscarOperacionesPorNombreYAplicacion(nombre, Long.toString(aplicacionId))).thenReturn(operacionEncontrada);
        // Verificar que el resultado no sea nulo
        assertNotNull(operacionEncontrada);
        assertEquals(nombre, operacionEncontrada.getNombreObjeto());
        assertEquals(BigDecimal.valueOf(aplicacionId), operacionEncontrada.getAplicacionId());
    }
}
