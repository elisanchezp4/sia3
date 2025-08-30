package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;

public class RolesPermisosDTOTest {

    @InjectMocks
    private RolesPermisosDTO mockRolesPermisosDTO = new RolesPermisosDTO();

    @Mock
    private List<String> mockRoles;

    @Test
    public void testGetterAndSetter(){
        InformacionPermisosDTO permisos =  new InformacionPermisosDTO();
        List<String> roles = mockRoles;
        UsuarioDTO usuario =  new UsuarioDTO();
        Integer userId = 28;
        String tokenAcceso = "56kifyteLKDYHFK69";

        mockRolesPermisosDTO.setPermisos(permisos);
        mockRolesPermisosDTO.setUsuario(usuario);
        mockRolesPermisosDTO.setTokenAcceso(tokenAcceso);

        assertEquals(permisos, mockRolesPermisosDTO.getPermisos());
        assertEquals(usuario, mockRolesPermisosDTO.getUsuario());
        assertEquals(tokenAcceso, mockRolesPermisosDTO.getTokenAcceso());
    }
}