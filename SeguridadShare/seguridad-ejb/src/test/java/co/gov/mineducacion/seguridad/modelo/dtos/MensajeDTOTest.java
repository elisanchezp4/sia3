package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MensajeDTOTest {

    private MensajeDTO mensajeDTO;

    @Before
    public void setUp() {
        mensajeDTO = new MensajeDTO();
    }

    @Test
    public void testGetterAndSetter() {
        String id = "1";
        String codigo = "MSG001";
        String nombre = "Mensaje de prueba";
        String descripcion = "Descripción del mensaje";
        String tipoMensaje = "INFO";
        String estado = "ACTIVO";

        mensajeDTO.setId(id);
        mensajeDTO.setCodigo(codigo);
        mensajeDTO.setNombre(nombre);
        mensajeDTO.setDescripcion(descripcion);
        mensajeDTO.setTipoMensaje(tipoMensaje);
        mensajeDTO.setEstado(estado);

        assertEquals(id, mensajeDTO.getId());
        assertEquals(codigo, mensajeDTO.getCodigo());
        assertEquals(nombre, mensajeDTO.getNombre());
        assertEquals(descripcion, mensajeDTO.getDescripcion());
        assertEquals(tipoMensaje, mensajeDTO.getTipoMensaje());
        assertEquals(estado, mensajeDTO.getEstado());
    }

    @Test
    public void testToString() {
        String expectedString = "MensajeDTO [id=1, codigo=MSG001, nombre=Mensaje de prueba, descripcion=Descripción del mensaje, tipoMensaje=INFO, estado=ACTIVO]";

        mensajeDTO.setId("1");
        mensajeDTO.setCodigo("MSG001");
        mensajeDTO.setNombre("Mensaje de prueba");
        mensajeDTO.setDescripcion("Descripción del mensaje");
        mensajeDTO.setTipoMensaje("INFO");
        mensajeDTO.setEstado("ACTIVO");

        assertEquals(expectedString, mensajeDTO.toString());
    }
}
