package co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HeaderDTOTest {

    @InjectMocks
    private HeaderDTO mockHeaderDTO = new HeaderDTO();

    @Test
    public void testGetterAndSetter() {
        Date fechaActual = new Date();

        String ipHost = "IpTest";
        Timestamp fechaPeticion = new Timestamp(fechaActual.getTime());
        String apiKey = "TestTest4";
        Integer userId = 22;
        Integer userToModify = 23;
        String email = "crazytest@gtesting.com";
        String numeroDocumento = "123456789";
        String apellidos = "FinalTest";
        String nombres = "InitTest";
        String nombreCompleto = nombres + " " + apellidos;
        String rutaDirectorio = "Obtener&%Pruebas57";
        String password = "NoEsUnTest";
        Boolean notificarUsuario = false;

        mockHeaderDTO.setIpHost(ipHost);
        mockHeaderDTO.setFechaPeticion(fechaPeticion);
        mockHeaderDTO.setApiKey(apiKey);
        mockHeaderDTO.setUserId(userId);
        mockHeaderDTO.setUserToModify(userToModify);
        mockHeaderDTO.setEmail(email);
        mockHeaderDTO.setNumeroDocumento(numeroDocumento);
        mockHeaderDTO.setApellidos(apellidos);
        mockHeaderDTO.setNombres(nombres);
        mockHeaderDTO.setNombreCompleto(nombreCompleto);
        mockHeaderDTO.setRutaDirectorio(rutaDirectorio);
        mockHeaderDTO.setPassword(password);
        mockHeaderDTO.setNotificarUsuario(notificarUsuario);

        assertEquals(ipHost, mockHeaderDTO.getIpHost());
        assertEquals(fechaPeticion, mockHeaderDTO.getFechaPeticion());
        assertEquals(apiKey, mockHeaderDTO.getApiKey());
        assertEquals(userId, mockHeaderDTO.getUserId());
        assertEquals(userToModify, mockHeaderDTO.getUserToModify());
        assertEquals(email, mockHeaderDTO.getEmail());
        assertEquals(numeroDocumento, mockHeaderDTO.getNumeroDocumento());
        assertEquals(apellidos, mockHeaderDTO.getApellidos());
        assertEquals(nombres, mockHeaderDTO.getNombres());
        assertEquals(nombreCompleto, mockHeaderDTO.getNombreCompleto());
        assertEquals(rutaDirectorio, mockHeaderDTO.getRutaDirectorio());
        assertEquals(password, mockHeaderDTO.getPassword());
        assertEquals(notificarUsuario, mockHeaderDTO.getNotificarUsuario());
    }
}