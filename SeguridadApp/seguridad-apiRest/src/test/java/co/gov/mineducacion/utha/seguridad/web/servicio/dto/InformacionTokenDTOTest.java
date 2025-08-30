package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class InformacionTokenDTOTest {

    @InjectMocks
    private InformacionTokenDTO mockInformacionTokenDTO = new InformacionTokenDTO();

    @Test
    public void testGetterAndSetter() {
        Date fechaActual = new Date();

        Timestamp fechaExpiracion = new Timestamp(fechaActual.getTime());
        String tokenAcceso = "56iipriPISIOU69";

        mockInformacionTokenDTO.setFechaExpiracion(fechaExpiracion);
        mockInformacionTokenDTO.setTokenAcceso(tokenAcceso);

        assertEquals(fechaExpiracion, mockInformacionTokenDTO.getFechaExpiracion());
        assertEquals(tokenAcceso, mockInformacionTokenDTO.getTokenAcceso());
    }

}