package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.Assert.*;

public class UsuariosRolesDtoTest {

    @InjectMocks
    private UsuariosRolesDto mockUsuariosRolesDto = new UsuariosRolesDto();

    transient List<UsuariosDTO> mockUsuarios;

    transient List<RolesDTO> mockRoles;

    @Test
    public void testGetterAndSetter() {
        mockUsuariosRolesDto.setUsuarios(mockUsuarios);
        mockUsuariosRolesDto.setRoles(mockRoles);

        assertEquals(mockUsuarios, mockUsuariosRolesDto.getUsuarios());
        assertEquals(mockRoles, mockUsuariosRolesDto.getRoles());
    }
}