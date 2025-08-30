package co.gov.mineducacion.seguridad.modelo.entidades;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class UsuarioAccesibilidadTest {

    private UsuarioAccesibilidad usuarioAccesibilidad;

    @Mock
    private Usuario mockUsuario;
    @Mock
    private PropiedadesAccesibilidad mockPropiedadesAccesibilidad;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioAccesibilidad = new UsuarioAccesibilidad();
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Long usuarioAccId = 1L;
        String valorPropiedad = "valor";
        Long estado = 1L;

        usuarioAccesibilidad.setUsuarioAccId(usuarioAccId);
        usuarioAccesibilidad.setUsuarios(mockUsuario);
        usuarioAccesibilidad.setPropAcc(mockPropiedadesAccesibilidad);
        usuarioAccesibilidad.setValorPropiedad(valorPropiedad);
        usuarioAccesibilidad.setEstado(estado);

        // Act
        Long retrievedUsuarioAccId = usuarioAccesibilidad.getUsuarioAccId();
        Usuario retrievedUsuarios = usuarioAccesibilidad.getUsuarios();
        PropiedadesAccesibilidad retrievedPropAcc = usuarioAccesibilidad.getPropAcc();
        String retrievedValorPropiedad = usuarioAccesibilidad.getValorPropiedad();
        Long retrievedEstado = usuarioAccesibilidad.getEstado();

        // Assert
        assertEquals(usuarioAccId, retrievedUsuarioAccId);
        assertEquals(mockUsuario, retrievedUsuarios);
        assertEquals(mockPropiedadesAccesibilidad, retrievedPropAcc);
        assertEquals(valorPropiedad, retrievedValorPropiedad);
        assertEquals(estado, retrievedEstado);
    }
}
