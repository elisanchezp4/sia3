package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorRoles;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NegocioRolesTest {

    @Mock
    private ManejadorRoles manejadorRolesMock;

    @Mock
    private NegocioOperaciones negocioOperacionesMock;

    @InjectMocks
    private NegocioRoles negocioRoles = new NegocioRoles();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRolesPorUsuarioId_ValidUsuarioId_ReturnsRolesDTOList() throws SIA3Exception {
        // Preparación
        Long usuarioId = 1L;
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(new Roles());
        when(manejadorRolesMock.getRolesPorUsuario(usuarioId)).thenReturn(rolesList);

        // Ejecución
        List<RolesDTO> result = negocioRoles.getRolesPorUsuarioId(usuarioId);

        // Verificación
        assertEquals(rolesList.size(), result.size());
        verify(manejadorRolesMock).getRolesPorUsuario(usuarioId);
        // Agrega más verificaciones según el comportamiento esperado
    }

    @Test(expected = SIA3Exception.class)
    public void testGetRolesPorUsuarioId_EmptyRolesList_ThrowsSIA3Exception() throws SIA3Exception {
        // Preparación
        Long usuarioId = 1L;
        List<Roles> rolesList = new ArrayList<>();
        when(manejadorRolesMock.getRolesPorUsuario(usuarioId)).thenReturn(rolesList);

        // Ejecución
        negocioRoles.getRolesPorUsuarioId(usuarioId);

    }

    @Test(expected = SIA3Exception.class)
    public void testGetRolesPorUsuarioId_PersistenceException_ThrowsSIA3Exception() throws SIA3Exception {
        // Preparación
        Long usuarioId = 1L;
        when(manejadorRolesMock.getRolesPorUsuario(usuarioId)).thenThrow(new PersistenceException());

        // Ejecución
        negocioRoles.getRolesPorUsuarioId(usuarioId);

    }

    @Test
    public void testImportar_ValidRolesDTO_CreatesRolesAndOperacionesPorRol() throws SIA3Exception {
        // Preparación
        RolesDTO rolesDTO = new RolesDTO();
        when(negocioOperacionesMock.getOperacionesPorAplicacion(BigDecimal.valueOf(anyLong()))).thenReturn(new ArrayList<>());
    }

    @Test(expected = SIA3Exception.class)
    public void testImportar_EmptyOperacionesList_ThrowsSIA3Exception() throws SIA3Exception {
        // Preparación
        RolesDTO rolesDTO = new RolesDTO();
        when(negocioOperacionesMock.getOperacionesPorAplicacion(new BigDecimal(anyLong()))).thenReturn(new ArrayList<>());

        // Ejecución
        negocioRoles.importar(rolesDTO);

        // Verificación: Se espera que se lance una excepción SIA3Exception
    }

    @Test(expected = SIA3Exception.class)
    public void testImportar_UnexpectedException_ThrowsSIA3Exception() throws SIA3Exception {
        // Preparación
        RolesDTO rolesDTO = new RolesDTO();
        when(negocioOperacionesMock.getOperacionesPorAplicacion(BigDecimal.valueOf(anyLong()))).thenReturn(new ArrayList<>());
        doThrow(new RuntimeException()).when(manejadorRolesMock).crear(any(Roles.class));

        // Ejecución
        negocioRoles.importar(rolesDTO);
    }

}
