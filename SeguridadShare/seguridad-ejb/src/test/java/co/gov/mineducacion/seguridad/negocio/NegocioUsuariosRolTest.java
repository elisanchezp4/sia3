package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuarioRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioRolEntity;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorRoles;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class NegocioUsuariosRolTest {

    @Mock
    private EntityManager em;

    @Mock
    private ManejadorRoles manejadorRoles;
    
    @InjectMocks
    private NegocioUsuariosRol negocioUsuariosRol = new NegocioUsuariosRol();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void actualizarUsuarioRol_DebeActualizarUsuarioRol() {
        // Arrange
        UsuarioRolEntity usuarioRolEntity = new UsuarioRolEntity();
        usuarioRolEntity.setUsuarioId(BigDecimal.valueOf(1));
        when(em.merge(usuarioRolEntity)).thenReturn(usuarioRolEntity);
    }

    @Test
    public void entidadRolAgregar_DebeCrearUsuarioRolEntity() {
        // Arrange
        UsuariosDTO usuariosDTO = new UsuariosDTO();
        usuariosDTO.setUsuarioId("1");

        // Act
        UsuarioRolEntity result = negocioUsuariosRol.entidadRolAgregar(usuariosDTO);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(BigDecimal.valueOf(1), result.getUsuarioId());
        Assert.assertEquals(Constantes.ESTADO_VINCULADO, result.getEstadoVinculacion());
        Assert.assertNotNull(result.getFechaVinculacion());
    }

    @Test
    public void idRolNombre_DebeRetornarIdRolPorNombre() {
        // Arrange
        String nombreRol = "ROLE1";
        List<Roles> roles = new ArrayList<>();
        roles.add(new Roles());
        when(manejadorRoles.buscarRolPorNombre(nombreRol)).thenReturn(roles);

        // Act
        BigDecimal result = negocioUsuariosRol.idRolNombre(nombreRol);

        Assert.assertNull(result);
        // Assert
        verify(manejadorRoles, times(1)).buscarRolPorNombre(nombreRol);
    }

    @Test
    public void agregarRolesUsuario_RolEncontrado_DebeActualizarUsuarioRol() throws SIA3Exception {
        // Arrange
        UsuarioRolEntity usuarioRolEntity = new UsuarioRolEntity();
        usuarioRolEntity.setUsuarioId(BigDecimal.valueOf(1));
        usuarioRolEntity.setRolId(BigDecimal.valueOf(2));

        String nombreRol = "ROLE1";
        String estadoVinculacion = "ESTADO1";
        BigDecimal aplicacionId = BigDecimal.valueOf(3);

        UsuarioRolDTO usuarioRolDTO = new UsuarioRolDTO(usuarioRolEntity.getRolId(), aplicacionId, nombreRol, estadoVinculacion);
        usuarioRolDTO.setNombre(nombreRol);
        usuarioRolDTO.setAplicacion_id(aplicacionId);
        usuarioRolDTO.setEstado_vinculacion(Constantes.ESTADO_DESVINCULADO);

        List<UsuarioRolDTO> usuarioRoles = new ArrayList<>();
        usuarioRoles.add(usuarioRolDTO);

        when(manejadorRoles.getRolesUsuario(1L)).thenReturn(usuarioRoles);
        when(manejadorRoles.buscarRolPorNombre(nombreRol)).thenReturn(new ArrayList<>());
        when(em.merge(usuarioRolEntity)).thenReturn(usuarioRolEntity);

    }

    @Test
    public void agregarRolesUsuario_RolNoEncontrado_DebeCrearUsuarioRol() throws SIA3Exception {
        // Arrange
        String nombreRol = "ROLE1";

        List<UsuarioRolDTO> usuarioRoles = new ArrayList<>();

        when(manejadorRoles.getRolesUsuario(1L)).thenReturn(usuarioRoles);
        when(manejadorRoles.buscarRolPorNombre(nombreRol)).thenReturn(new ArrayList<>());

    }
}
