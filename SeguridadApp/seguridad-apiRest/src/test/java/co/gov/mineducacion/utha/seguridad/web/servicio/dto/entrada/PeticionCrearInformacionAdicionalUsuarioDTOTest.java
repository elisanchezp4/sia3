package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

public class PeticionCrearInformacionAdicionalUsuarioDTOTest {

    @InjectMocks
    private PeticionCrearUsuarioExternoDTO mockPeticionCrearUsuarioExternoDTO = new PeticionCrearUsuarioExternoDTO();

    @Test
    public void testGetterAndSetter() {
        HeaderDTO header = new HeaderDTO();
        String ipHost = "IpTest";
        header.setIpHost(ipHost);
        String rol = "Tester";
        InformacionUsuarioDTO informacionUsuario = new InformacionUsuarioDTO();
        Long numeroIdentificacion = 10L;
        informacionUsuario.setNumeroIdentificacion(numeroIdentificacion);

        mockPeticionCrearUsuarioExternoDTO.setHeader(header);
        mockPeticionCrearUsuarioExternoDTO.setRol(rol);
        mockPeticionCrearUsuarioExternoDTO.setInformacionUsuario(informacionUsuario);

        assertEquals(header, mockPeticionCrearUsuarioExternoDTO.getHeader());
        assertEquals(rol, mockPeticionCrearUsuarioExternoDTO.getRol());
        assertEquals(informacionUsuario, mockPeticionCrearUsuarioExternoDTO.getInformacionUsuario());
    }
}