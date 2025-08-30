package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

public class PeticionAutenticacionDTOTest {

    @InjectMocks
    private PeticionAutenticacionDTO mockPeticionAutenticacionDTO = new PeticionAutenticacionDTO();

    @Test
    public void testGetterAndSetter(){
        HeaderDTO header = new HeaderDTO();
        String ipHost = "IpTest";
        header.setIpHost(ipHost);
        String tokenAcceso = "56kifyteLKDYHFK69";

        mockPeticionAutenticacionDTO.setHeader(header);
        mockPeticionAutenticacionDTO.setTokenAcceso(tokenAcceso);

        assertEquals(header,mockPeticionAutenticacionDTO.getHeader());
        assertEquals(tokenAcceso, mockPeticionAutenticacionDTO.getTokenAcceso());
    }
}