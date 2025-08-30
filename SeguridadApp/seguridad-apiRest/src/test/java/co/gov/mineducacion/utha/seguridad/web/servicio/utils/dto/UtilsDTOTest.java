package co.gov.mineducacion.utha.seguridad.web.servicio.utils.dto;

import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.InformacionPermisosDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.RolesPermisosDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.UsuarioDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilsDTOTest {

    @Test
    public void testRolesPermisosDTOobtenerDTO_primerCondicional() {
        OperacionesRolDTO opRolDtoMock = null;
        String token = "token";

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNull(resultado);
    }

    @Test
    public void testRolesPermisosDTOobtenerDTO_setPermisos() {
        OperacionesRolDTO opRolDtoMock = mock(OperacionesRolDTO.class);
        String token = "token";

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNotNull(resultado.getPermisos());
        assertEquals(InformacionPermisosDTO.class, resultado.getPermisos().getClass());
    }

    @Test
    public void testRolesPermisosDTOobtenerDTO_OperacionesListNotNull() {
        OperacionesDTO opDtoMock = mock(OperacionesDTO.class);
        String token = "token";

        when(opDtoMock.getDescripcion()).thenReturn("TestDescripcion");
        when(opDtoMock.getNombreObjeto()).thenReturn("TestNombreObjeto");
        when(opDtoMock.getNombreRolAsociado()).thenReturn("Tester");
        when(opDtoMock.getOpcionId()).thenReturn(new BigDecimal(256));
        when(opDtoMock.getTipo()).thenReturn("TestTipo");
        when(opDtoMock.getUrlImgGif()).thenReturn("TesRutaImage");

        OperacionesRolDTO opRolDtoMock = mock(OperacionesRolDTO.class);

        when(opRolDtoMock.getOperacionesList()).thenReturn(Arrays.asList(opDtoMock));

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNotNull(resultado.getPermisos());
        assertNotNull(resultado.getPermisos().getPermisos());
        assertEquals(1, resultado.getPermisos().getPermisos().size());
        assertEquals("TestNombreObjeto", resultado.getPermisos().getPermisos().get(0).getNombreObjeto());
    }

    @Test
    public void testRolesPermisosDTOobtenerDTO_OperacionesListNull() {
        OperacionesRolDTO opRolDtoMock = mock(OperacionesRolDTO.class);
        String token = "token";

        when(opRolDtoMock.getOperacionesList()).thenReturn(null);

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNotNull(resultado.getPermisos());
        assertNull(resultado.getPermisos().getPermisos());
    }

    @Test
    public void testRolesPermisosDTOobtenerDTO_RolesListNotNull() {
        OperacionesRolDTO opRolDtoMock = mock(OperacionesRolDTO.class);
        String token = "token";

        when(opRolDtoMock.getRolesList()).thenReturn(Arrays.asList("Rol1", "Rol2"));

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNotNull(resultado.getPermisos());
        assertNotNull(resultado.getPermisos().getRoles());
        assertEquals(Arrays.asList("Rol1", "Rol2"), resultado.getPermisos().getRoles());

    }

    @Test
    public void testRolesPermisosDTOobtenerDTO_RolesListIsNull() {
        OperacionesRolDTO opRolDtoMock = mock(OperacionesRolDTO.class);
        String token = "token";

        when(opRolDtoMock.getRolesList()).thenReturn(null);

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNotNull(resultado.getPermisos());
        assertNull(resultado.getPermisos().getRoles());
    }

    @Test
    public void testRolesPermisosDTOobtenerDTO_UsuarioNotNull() {
        OperacionesRolDTO opRolDtoMock = mock(OperacionesRolDTO.class);
        String token = "token";

        UsuariosDTO usuariosDtoMock = new UsuariosDTO();

        when(opRolDtoMock.getUsuario()).thenReturn(usuariosDtoMock);

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNotNull(resultado.getUsuario());
    }

    @Test
    public void testRolesPermisosDTOobtenerDTO_UsuarioNull() {
        OperacionesRolDTO opRolDtoMock = mock(OperacionesRolDTO.class);
        String token = "token";

        when(opRolDtoMock.getUsuario()).thenReturn(null);

        RolesPermisosDTO resultado = UtilsDTO.obtenerDTO(opRolDtoMock, token);

        assertNull(resultado.getUsuario());
    }

}