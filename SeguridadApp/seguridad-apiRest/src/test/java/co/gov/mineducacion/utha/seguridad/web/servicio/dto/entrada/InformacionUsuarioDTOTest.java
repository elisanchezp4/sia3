package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

public class InformacionUsuarioDTOTest {

    @InjectMocks
    private InformacionUsuarioDTO mockInformacionUsuarioDTO = new InformacionUsuarioDTO();

    @Test
    public void testGetterAndSetter() {
        Long numeroIdentificacion = 1L;
        String tipoIdentificacion = "CC";
        String apellidos = "FinalTest";
        String email = "crazytest@gtesting.com";
        String nombreUsuario = "MidTest";
        String nombres = "InitTest";
        String tipo = "Metal";
        Integer userId = 25;

        mockInformacionUsuarioDTO.setNumeroIdentificacion(numeroIdentificacion);
        mockInformacionUsuarioDTO.setTipoIdentificacion(tipoIdentificacion);
        mockInformacionUsuarioDTO.setApellidos(apellidos);
        mockInformacionUsuarioDTO.setEmail(email);
        mockInformacionUsuarioDTO.setNombreUsuario(nombreUsuario);
        mockInformacionUsuarioDTO.setNombres(nombres);
        mockInformacionUsuarioDTO.setTipo(tipo);
        mockInformacionUsuarioDTO.setUserId(userId);

        assertEquals(numeroIdentificacion, mockInformacionUsuarioDTO.getNumeroIdentificacion());
        assertEquals(tipoIdentificacion, mockInformacionUsuarioDTO.getTipoIdentificacion());
        assertEquals(apellidos, mockInformacionUsuarioDTO.getApellidos());
        assertEquals(email, mockInformacionUsuarioDTO.getEmail());
        assertEquals(nombreUsuario, mockInformacionUsuarioDTO.getNombreUsuario());
        assertEquals(nombres, mockInformacionUsuarioDTO.getNombres());
        assertEquals(tipo, mockInformacionUsuarioDTO.getTipo());
        assertEquals(userId, mockInformacionUsuarioDTO.getUserId());
    }
}