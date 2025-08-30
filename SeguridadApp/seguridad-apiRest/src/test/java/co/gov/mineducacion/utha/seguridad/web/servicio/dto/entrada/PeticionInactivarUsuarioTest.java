package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class PeticionInactivarUsuarioTest {

    @Mock
    PeticionInactivarUsuario mockPeticionInactivarUsuario = new PeticionInactivarUsuario();

    @Test
    public void testGetterAndSetter(){
        HeaderDTO header = new HeaderDTO();
        String ipHost = "IpTest";
        header.setIpHost(ipHost);
        Integer userIdInactivar = 666;

        mockPeticionInactivarUsuario.setHeader(header);
        mockPeticionInactivarUsuario.setUserIdInactivar(userIdInactivar);

        assertEquals(header, mockPeticionInactivarUsuario.getHeader());
        assertEquals(userIdInactivar, mockPeticionInactivarUsuario.getUserIdInactivar());
    }
}