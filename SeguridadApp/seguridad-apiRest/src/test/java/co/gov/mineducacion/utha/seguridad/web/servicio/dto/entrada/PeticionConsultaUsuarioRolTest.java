package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

public class PeticionConsultaUsuarioRolTest {

    @InjectMocks
    private PeticionConsultaUsuarioRol mockPeticionConsultaUsuarioRol = new PeticionConsultaUsuarioRol();

    @Test
    public void testGetterAndSetter() {
        HeaderDTO header = new HeaderDTO();
        String ipHost = "IpTest";
        header.setIpHost(ipHost);
        String rol = "Tester";

        mockPeticionConsultaUsuarioRol.setHeader(header);
        mockPeticionConsultaUsuarioRol.setRol(rol);

        assertEquals(header, mockPeticionConsultaUsuarioRol.getHeader());
        assertEquals(rol, mockPeticionConsultaUsuarioRol.getRol());
    }
}