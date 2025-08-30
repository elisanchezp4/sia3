package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

public class InformacionUsuarioSalidaDTOTest {

    @InjectMocks
    private InformacionUsuarioSalidaDTO mockInformacionUsuarioSalidaDTO = new InformacionUsuarioSalidaDTO();

    @Test
    public void testGetterAndSetter(){
        Integer userId = 632;
        String nombreUsuario = "MidTestLoggin";

        mockInformacionUsuarioSalidaDTO.setUserId(userId);
        mockInformacionUsuarioSalidaDTO.setNombreUsuario(nombreUsuario);

        assertEquals(userId, mockInformacionUsuarioSalidaDTO.getUserId());
        assertEquals(nombreUsuario, mockInformacionUsuarioSalidaDTO.getNombreUsuario());
    }
}