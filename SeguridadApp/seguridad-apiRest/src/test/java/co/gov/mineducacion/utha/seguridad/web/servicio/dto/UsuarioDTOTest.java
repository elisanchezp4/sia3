package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class UsuarioDTOTest {

    @InjectMocks
    private UsuarioDTO mockUsuarioDTO = new UsuarioDTO();

    @Test
    public void testGetterAndSetter(){
        String apellidos = "FinalTest";
        String nombres = "InitTest";
        Integer userId = 22;
        String logonName = "TestLogon";
        String nombreUsuario = "MidTest";
        String numeroDocumento = "123456789";
        String emailUsuario="crazytest@gtesting.com";
        String loginUsuario = "MidTestLoggin";
        BigDecimal tipo = new BigDecimal(6);
        Date fechaCreacion = new Date(1234567890000L);
        Date ultimaModificacion = new Date(122333444455555L);

        mockUsuarioDTO.setApellidos(apellidos);
        mockUsuarioDTO.setNombres(nombres);
        mockUsuarioDTO.setUserId(userId);
        mockUsuarioDTO.setLogonName(logonName);
        mockUsuarioDTO.setNombreUsuario(nombreUsuario);
        mockUsuarioDTO.setNumeroDocumento(numeroDocumento);
        mockUsuarioDTO.setEmailUsuario(emailUsuario);
        mockUsuarioDTO.setLoginUsuario(loginUsuario);
        mockUsuarioDTO.setTipo(tipo);
        mockUsuarioDTO.setFechaCreacion(fechaCreacion);
        mockUsuarioDTO.setUltimaModificacion(ultimaModificacion);

        assertEquals(apellidos,mockUsuarioDTO.getApellidos());
        assertEquals(nombres,mockUsuarioDTO.getNombres());
        assertEquals(userId,mockUsuarioDTO.getUserId());
        assertEquals(logonName,mockUsuarioDTO.getLogonName());
        assertEquals(nombreUsuario,mockUsuarioDTO.getNombreUsuario());
        assertEquals(numeroDocumento,mockUsuarioDTO.getNumeroDocumento());
        assertEquals(emailUsuario,mockUsuarioDTO.getEmailUsuario());
        assertEquals(loginUsuario,mockUsuarioDTO.getLoginUsuario());
        assertEquals(tipo,mockUsuarioDTO.getTipo());
        assertEquals(fechaCreacion,mockUsuarioDTO.getFechaCreacion());
        assertEquals(ultimaModificacion,mockUsuarioDTO.getUltimaModificacion());
    }
}