package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;

public class UsuarioPropAccesibilidadDTOTest {

    private UsuarioPropAccesibilidadDTO usuarioPropAccesibilidadDTO;

    @Before
    public void setUp() {
        usuarioPropAccesibilidadDTO = new UsuarioPropAccesibilidadDTO();
    }

    @Test
    public void testGetSetUsuarioAccId() {
        // Arrange
        Long usuarioAccId = 123L;

        // Act
        usuarioPropAccesibilidadDTO.setUsuarioAccId(usuarioAccId);

        // Assert
        assertEquals(usuarioAccId, usuarioPropAccesibilidadDTO.getUsuarioAccId());
    }

    @Test
    public void testGetSetUsuarios() {
        // Arrange
        Usuario usuarioMock = mock(Usuario.class);

        // Act
        usuarioPropAccesibilidadDTO.setUsuarios(usuarioMock);

        // Assert
        assertEquals(usuarioMock, usuarioPropAccesibilidadDTO.getUsuarios());
    }

    @Test
    public void testGetSetPropAcc() {
        // Arrange
        PropiedadesAccesibilidadDTO propAccMock = mock(PropiedadesAccesibilidadDTO.class);

        // Act
        usuarioPropAccesibilidadDTO.setPropAcc(propAccMock);

        // Assert
        assertEquals(propAccMock, usuarioPropAccesibilidadDTO.getPropAcc());
    }

    @Test
    public void testGetSetValorPropiedad() {
        // Arrange
        String valorPropiedad = "acceso";

        // Act
        usuarioPropAccesibilidadDTO.setValorPropiedad(valorPropiedad);

        // Assert
        assertEquals(valorPropiedad, usuarioPropAccesibilidadDTO.getValorPropiedad());
    }

    @Test
    public void testGetSetEstado() {
        // Arrange
        Long estado = 1L;

        // Act
        usuarioPropAccesibilidadDTO.setEstado(estado);

        // Assert
        assertEquals(estado, usuarioPropAccesibilidadDTO.getEstado());
    }
}
