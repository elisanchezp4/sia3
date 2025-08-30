package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosAplicacionPK;

public class UsuariosAplicacionDTOTest {

    private UsuariosAplicacionDTO usuariosAplicacionDTO;

    @Before
    public void setUp() {
        // Inicializa los objetos necesarios
        usuariosAplicacionDTO = new UsuariosAplicacionDTO();
        UsuariosAplicacionPK usuariosAplicacionPK = new UsuariosAplicacionPK();
        usuariosAplicacionDTO.setUsuariosAplicacionPK(usuariosAplicacionPK);

        Usuario usuario = mock(Usuario.class);
        Aplicaciones aplicaciones = mock(Aplicaciones.class);

        usuariosAplicacionDTO.setUsuarios(usuario);
        usuariosAplicacionDTO.setAplicaciones(aplicaciones);
    }

    @Test
    public void testGetSetUsuariosAplicacionPK() {
        // Arrange
        UsuariosAplicacionPK newUsuariosAplicacionPK = new UsuariosAplicacionPK();

        // Act
        usuariosAplicacionDTO.setUsuariosAplicacionPK(newUsuariosAplicacionPK);

        // Assert
        assertEquals(newUsuariosAplicacionPK, usuariosAplicacionDTO.getUsuariosAplicacionPK());
    }

    @Test
    public void testGetSetUsuarios() {
        // Arrange
        Usuario newUsuario = mock(Usuario.class);

        // Act
        usuariosAplicacionDTO.setUsuarios(newUsuario);

        // Assert
        assertEquals(newUsuario, usuariosAplicacionDTO.getUsuarios());
    }

    @Test
    public void testGetSetAplicaciones() {
        // Arrange
        Aplicaciones newAplicaciones = mock(Aplicaciones.class);

        // Act
        usuariosAplicacionDTO.setAplicaciones(newAplicaciones);

        // Assert
        assertEquals(newAplicaciones, usuariosAplicacionDTO.getAplicaciones());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        UsuariosAplicacionDTO otherDTO = new UsuariosAplicacionDTO();
        UsuariosAplicacionPK otherPK = new UsuariosAplicacionPK();
        otherDTO.setUsuariosAplicacionPK(otherPK);

        // Assert
        assertEquals(otherDTO, usuariosAplicacionDTO);
        assertEquals(usuariosAplicacionDTO.hashCode(), otherDTO.hashCode());
    }

    @Test
    public void testToString() {
        // Act
        String result = usuariosAplicacionDTO.toString();

        // Assert
        assertNotNull(result);
    }
}
