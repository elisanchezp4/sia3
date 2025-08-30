package co.gov.mineducacion.seguridad.modelo.utils;

import co.gov.mineducacion.seguridad.modelo.dtos.externos.ServicioInstregacionDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IntegradorDATest {

    Properties properties;
    ServicioInstregacionDTO responseOk;
    ServicioInstregacionDTO responseUsuarioLdap;
    ServicioInstregacionDTO responseNok;
    UsuarioLdap usuarioLdap;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        responseOk = new ServicioInstregacionDTO(0L, false, "Message", "Exitoso");
        responseNok = new ServicioInstregacionDTO(1L, false, "Message", "Error");
        usuarioLdap = getUser();
        responseUsuarioLdap = new ServicioInstregacionDTO(0L, false, "Message", usuarioLdap);


        properties = new Properties();
        properties.put("URL_SERVICIO_NET", "localhost");
    }

    private UsuarioLdap getUser() {
        UsuarioLdap usuarioLdap = new UsuarioLdap();
        usuarioLdap.setDisplayName("DisplayName");
        usuarioLdap.setDescription("Description");
        usuarioLdap.setDistinguishedName("DistinguishedName");
        usuarioLdap.setGivenName("GivenName");
        usuarioLdap.setMail("Mail");
        usuarioLdap.setName("Name");
        usuarioLdap.setsAMAccountName("sAMAccountName");
        return usuarioLdap;
    }

    @Test
    public void validarUsuarioExistente() throws SeguridadException, SIA3Exception {
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPost(anyString(), any(), any(), any())).thenReturn(responseOk);

            String userNameTest = "fmarcano";
            String passwordTest = "Sia3-123456789012";
            boolean datos = IntegradorDA.validarUsuario(userNameTest, passwordTest, properties);
            assertTrue("Validar que el usuario exista", datos);
        }
    }

    @Test(expected = SeguridadException.class)
    public void validarUsuarioConError() throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPost(anyString(), any(), any(), any())).thenReturn(responseNok);

            String userNameTest = "drgffss";
            String passwordTest = "asdee";
            boolean datos = IntegradorDA.validarUsuario(userNameTest, passwordTest, properties);
        }
    }

    @Test
    public void crearUsuarioExitoso() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPost(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.crearUsuario(usuarioLdap, properties);
            utilServicioHTTP.verify(
                    () ->  ServicioHTTP.enviarPublicacionPost(anyString(), any(), any(), any()), times(1));
        }
    }

    @Test(expected = SeguridadException.class)
    public void crearUsuarioException() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPost(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.crearUsuario(usuarioLdap, properties);
        }
    }


    @Test
    public void modificarUsuarioExitoso() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.modificarUsuario(usuarioLdap, properties);
            utilServicioHTTP.verify(
                    () ->  ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any()), times(1));
        }
    }


    @Test(expected = SeguridadException.class)
    public void modificarUsuarioException() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.modificarUsuario(usuarioLdap, properties);
        }
    }




    @Test
    public void buscarUsuarioPorLoginExitoso() throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        UsuarioLdap usuarioLdap1 = getUser();
        doReturn(usuarioLdap1).when(responseNok).getData();
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGet(anyString(), any(), anyString())).thenReturn(responseNok);

            String usuario = "mpimienta";
            UsuarioLdap response = IntegradorDA.buscarUsuarioPorLogin(usuario, null, properties);
            assertNotNull(response);
            assertEquals(usuarioLdap1.getDisplayName(), response.getDisplayName());
            assertEquals(usuarioLdap1.getDescription(), response.getDescription());
            assertEquals(usuarioLdap1.getDistinguishedName(), response.getDistinguishedName());
            assertEquals(usuarioLdap1.getGivenName(), response.getGivenName());
            assertEquals(usuarioLdap1.getMail(), response.getMail());
            assertEquals(usuarioLdap1.getName(), response.getName());
        }
    }

    @Test
    public void buscarUsuarioPorLoginNoEncontrado()  throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn("UsuarioNoExiste").when(responseNok).getData();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGet(anyString(), any(), anyString())).thenReturn(responseNok);

            String usuario = "mpimienta";
            UsuarioLdap response = IntegradorDA.buscarUsuarioPorLogin(usuario, null, properties);
            assertNull(response);
        }
    }

    @Test(expected = SeguridadException.class)
    public void buscarUsuarioPorLoginException()  throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGet(anyString(), any(), anyString())).thenReturn(responseNok);

            String usuario = "mpimienta";
            IntegradorDA.buscarUsuarioPorLogin(usuario, null, properties);
        }
    }


    @Test
    public void modificarPasswordExitoso() throws SIA3Exception, SeguridadException {
        String usuario = "fmarcano";
        String newPassword = "Sia3-123456789012";
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.modificarPassword(usuario, newPassword, properties);
            utilServicioHTTP.verify(
                    () ->  ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any()), times(1));
        }
    }

    @Test(expected = SeguridadException.class)
    public void modificarPasswordNoExitoso() throws SIA3Exception, SeguridadException {
        String usuario = "fmarcano";
        String newPassword = "Sia3-123456789012";
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.modificarPassword(usuario, newPassword, properties);
        }
    }

    @Test
    public void listarUsuariosActivosInactivosExitosoPorActivos()  throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);

        List<UsuarioLdap> usuarioLdap1 = Collections.singletonList(getUser());
        doReturn(usuarioLdap1).when(responseNok).getData();
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGetConBody(anyString(), any(), anyString(), anyString())).thenReturn(responseNok);
            List<UsuarioLdap> response = IntegradorDA.listarUsuariosActivosInactivos(true, properties);
            assertNotNull(response);
            assertEquals(1, response.size());
        }
    }

    @Test(expected = SeguridadException.class)
    public void listarUsuariosActivosInactivosExitosoPorActivosNoExitoso()  throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGetConBody(anyString(), any(), anyString(), anyString())).thenReturn(responseNok);
            IntegradorDA.listarUsuariosActivosInactivos(true, properties);
        }
    }

    @Test
    public void listarUsuariosPorFiltrosExitoso()  throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        String logonName = "fmarcano";
        String nombres = "aa";
        String apellidos = "aa";
        String email = "aa@aa.com";
        String entryDN = "ou";
        List<UsuarioLdap> usuarioLdap1 = Collections.singletonList(getUser());
        doReturn(usuarioLdap1).when(responseNok).getData();
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGetConBody(anyString(), any(), anyString(), anyString())).thenReturn(responseNok);
            List<UsuarioLdap> response = IntegradorDA.listarUsuariosPorFiltros(logonName, nombres, apellidos, email, entryDN, properties);
            assertNotNull(response);
            assertEquals(1, response.size());
        }
    }

    @Test(expected = SeguridadException.class)
    public void listarUsuariosPorFiltrosNoExitoso()  throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        String logonName = "fmarcano";
        String nombres = "";
        String apellidos = "";
        String email = "";
        String entryDN = "";
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGetConBody(anyString(), any(), anyString(), anyString())).thenReturn(responseNok);
            IntegradorDA.listarUsuariosPorFiltros(logonName, nombres, apellidos, email, entryDN, properties);
        }
    }


    @Test
    public void listarUsuariosPorFiltrosNoExitosoResponseFour()  throws SeguridadException, SIA3Exception {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        String logonName = "fmarcano";
        String nombres = "";
        String apellidos = "";
        String email = "";
        String entryDN = "";
        doReturn(false).when(responseNok).getError();
        doReturn(4L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPeticionGetConBody(anyString(), any(), anyString(), anyString())).thenReturn(responseNok);
            List<UsuarioLdap> response = IntegradorDA.listarUsuariosPorFiltros(logonName, nombres, apellidos, email, entryDN, properties);
            assertNotNull(response);
            assertEquals(0, response.size());
        }
    }


    @Test
    public void activarUsuarioExitoso() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.activarUsuario("usuario", properties);
            utilServicioHTTP.verify(
                    () ->  ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any()), times(1));
        }
    }

    @Test(expected = SeguridadException.class)
    public void activarUsuarioNoExitoso() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.activarUsuario("usuario", properties);
            utilServicioHTTP.verify(
                    () ->  ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any()), times(1));
        }
    }


    @Test
    public void inactivarUsuarioExitoso() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(false).when(responseNok).getError();
        doReturn(0L).when(responseNok).getCodigoRespuesta();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.inactivarUsuario("usuario", properties);
            utilServicioHTTP.verify(
                    () ->  ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any()), times(1));
        }
    }
    @Test(expected = SeguridadException.class)
    public void inactivarUsuarioNoExitoso() throws SIA3Exception, SeguridadException {
        ServicioInstregacionDTO responseNok = mock(ServicioInstregacionDTO.class);
        doReturn(true).when(responseNok).getError();
        try (MockedStatic<ServicioHTTP> utilServicioHTTP = Mockito.mockStatic(ServicioHTTP.class)) {
            utilServicioHTTP.when(ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any())).thenReturn(responseNok);
            IntegradorDA.inactivarUsuario("usuario", properties);
            utilServicioHTTP.verify(
                    () ->  ServicioHTTP.enviarPublicacionPut(anyString(), any(), any(), any()), times(1));
        }
    }

}
