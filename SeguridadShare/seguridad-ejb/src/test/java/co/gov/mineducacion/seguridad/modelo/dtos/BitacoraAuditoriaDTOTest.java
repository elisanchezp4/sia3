package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Catalogos;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class BitacoraAuditoriaDTOTest {

    @Mock
    private Catalogos mockCatalogos;

    @Mock
    private Usuario mockUsuario;

    @Mock
    private Aplicaciones mockAplicaciones;

    private BitacoraAuditoriaDTO bitacoraAuditoriaDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        bitacoraAuditoriaDTO = new BitacoraAuditoriaDTO();
        bitacoraAuditoriaDTO.setCatalogos(mockCatalogos);
        bitacoraAuditoriaDTO.setUsuarios(mockUsuario);
        bitacoraAuditoriaDTO.setAplicaciones(mockAplicaciones);
    }

    @Test
    public void testGetterAndSetter() {
        BigDecimal bitacoraId = new BigDecimal(1);
        Timestamp fechaEvento = new Timestamp(System.currentTimeMillis());
        BigDecimal tipoEvento = new BigDecimal(1);
        String usuarioId = "testUser";
        String detalle = "Test detail";
        BigDecimal aplicacionId = new BigDecimal(2);
        String campoDirectorio = "testDirectory";

        bitacoraAuditoriaDTO.setBitacoraId(bitacoraId);
        bitacoraAuditoriaDTO.setFechaEvento(fechaEvento);
        bitacoraAuditoriaDTO.setTipoEvento(tipoEvento);
        bitacoraAuditoriaDTO.setUsuarioId(usuarioId);
        bitacoraAuditoriaDTO.setDetalle(detalle);
        bitacoraAuditoriaDTO.setAplicacionId(aplicacionId);
        bitacoraAuditoriaDTO.setCampoDirectorio(campoDirectorio);

        assertEquals(bitacoraId, bitacoraAuditoriaDTO.getBitacoraId());
        assertEquals(fechaEvento, bitacoraAuditoriaDTO.getFechaEvento());
        assertEquals(tipoEvento, bitacoraAuditoriaDTO.getTipoEvento());
        assertEquals(mockCatalogos, bitacoraAuditoriaDTO.getCatalogos());
        assertEquals(usuarioId, bitacoraAuditoriaDTO.getUsuarioId());
        assertEquals(detalle, bitacoraAuditoriaDTO.getDetalle());
        assertEquals(mockAplicaciones, bitacoraAuditoriaDTO.getAplicaciones());
        assertEquals(aplicacionId, bitacoraAuditoriaDTO.getAplicacionId());
        assertEquals(mockUsuario, bitacoraAuditoriaDTO.getUsuarios());
        assertEquals(campoDirectorio, bitacoraAuditoriaDTO.getCampoDirectorio());
    }

    @Test
    public void testEquals() {
        BitacoraAuditoriaDTO other = new BitacoraAuditoriaDTO();
        other.setBitacoraId(bitacoraAuditoriaDTO.getBitacoraId());
        other.setFechaEvento(bitacoraAuditoriaDTO.getFechaEvento());
        other.setTipoEvento(bitacoraAuditoriaDTO.getTipoEvento());
        other.setCatalogos(bitacoraAuditoriaDTO.getCatalogos());
        other.setUsuarioId(bitacoraAuditoriaDTO.getUsuarioId());
        other.setDetalle(bitacoraAuditoriaDTO.getDetalle());
        other.setAplicaciones(bitacoraAuditoriaDTO.getAplicaciones());
        other.setAplicacionId(bitacoraAuditoriaDTO.getAplicacionId());
        other.setUsuarios(bitacoraAuditoriaDTO.getUsuarios());
        other.setCampoDirectorio(bitacoraAuditoriaDTO.getCampoDirectorio());

        assertTrue(bitacoraAuditoriaDTO.equals(other));
    }
}
