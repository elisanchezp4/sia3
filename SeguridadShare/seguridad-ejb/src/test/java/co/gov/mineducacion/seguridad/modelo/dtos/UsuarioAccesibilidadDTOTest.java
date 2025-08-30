package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import co.gov.mineducacion.seguridad.modelo.entidades.PropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;

public class UsuarioAccesibilidadDTOTest {

    private UsuarioAccesibilidadDTO usuarioAccesibilidadDTO;

    @Before
    public void setUp() {
        usuarioAccesibilidadDTO = new UsuarioAccesibilidadDTO();
    }

    @Test
    public void testGetSetUsuarioAccId() {
        // Arrange
        Long usuarioAccId = 123L;

        // Act
        usuarioAccesibilidadDTO.setUsuarioAccId(usuarioAccId);

        // Assert
        assertEquals(usuarioAccId, usuarioAccesibilidadDTO.getUsuarioAccId());
    }

    @Test
    public void testGetSetUsuarios() {
        // Arrange
        Usuario usuarioMock = mock(Usuario.class);

        // Act
        usuarioAccesibilidadDTO.setUsuarios(usuarioMock);

        // Assert
        assertEquals(usuarioMock, usuarioAccesibilidadDTO.getUsuarios());
    }

    @Test
    public void testGetSetPropAcc() {
        // Arrange
        PropiedadesAccesibilidad propAccMock = mock(PropiedadesAccesibilidad.class);

        // Act
        usuarioAccesibilidadDTO.setPropAcc(propAccMock);

        // Assert
        assertEquals(propAccMock, usuarioAccesibilidadDTO.getPropAcc());
    }

    @Test
    public void testGetSetValorPropiedad() {
        // Arrange
        String valorPropiedad = "acceso";

        // Act
        usuarioAccesibilidadDTO.setValorPropiedad(valorPropiedad);

        // Assert
        assertEquals(valorPropiedad, usuarioAccesibilidadDTO.getValorPropiedad());
    }

    @Test
    public void testGetSetEstado() {
        // Arrange
        Long estado = 1L;

        // Act
        usuarioAccesibilidadDTO.setEstado(estado);

        // Assert
        assertEquals(estado, usuarioAccesibilidadDTO.getEstado());
    }
}
