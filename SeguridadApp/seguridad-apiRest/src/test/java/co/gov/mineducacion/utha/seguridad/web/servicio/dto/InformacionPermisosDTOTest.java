package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;

public class InformacionPermisosDTOTest {

    @InjectMocks
    private InformacionPermisosDTO mockInformacionPermisosDTO = new InformacionPermisosDTO();

    @Mock
    private List<OpcionDTO> mockPermisos;

    @Mock
    private List<String> mockRoles;

    @Test
    public void testGetterAndSetter() {
        mockInformacionPermisosDTO.setPermisos(mockPermisos);
        mockInformacionPermisosDTO.setRoles(mockRoles);

        assertEquals(mockPermisos, mockInformacionPermisosDTO.getPermisos());
        assertEquals(mockRoles, mockInformacionPermisosDTO.getRoles());
    }
}