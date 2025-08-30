package co.gov.mineducacion.seguridad.modelo.dtos;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;

import java.util.ArrayList;
import java.util.List;

public class OperacionesRolDTOTest {

    private OperacionesRolDTO operacionesRolDTO;

    @Before
    public void setUp() {
        operacionesRolDTO = new OperacionesRolDTO();
    }

    @Test
    public void testSetRoles() {
        // Arrange
        Roles roles = mock(Roles.class);

        // Act
        operacionesRolDTO.setRoles(roles);

        // Assert
        assertEquals(roles, operacionesRolDTO.getRoles());
    }

    @Test
    public void testSetOperaciones() {
        // Arrange
        Operaciones operaciones = mock(Operaciones.class);

        // Act
        operacionesRolDTO.setOperaciones(operaciones);

        // Assert
        assertEquals(operaciones, operacionesRolDTO.getOperaciones());
    }

    @Test
    public void testSetRolesList() {
        // Arrange
        List<String> rolesList = new ArrayList<>();
        rolesList.add("Role1");
        rolesList.add("Role2");

        // Act
        operacionesRolDTO.setRolesList(rolesList);

        // Assert
        assertEquals(rolesList, operacionesRolDTO.getRolesList());
    }

    @Test
    public void testSetOperacionesList() {
        // Arrange
        List<OperacionesDTO> operacionesList = new ArrayList<>();
        OperacionesDTO operacion1 = mock(OperacionesDTO.class);
        OperacionesDTO operacion2 = mock(OperacionesDTO.class);
        operacionesList.add(operacion1);
        operacionesList.add(operacion2);

        // Act
        operacionesRolDTO.setOperacionesList(operacionesList);

        // Assert
        assertEquals(operacionesList, operacionesRolDTO.getOperacionesList());
    }

    @Test
    public void testSetUsuario() {
        // Arrange
        UsuariosDTO usuario = mock(UsuariosDTO.class);

        // Act
        operacionesRolDTO.setUsuario(usuario);

        // Assert
        assertEquals(usuario, operacionesRolDTO.getUsuario());
    }
}
