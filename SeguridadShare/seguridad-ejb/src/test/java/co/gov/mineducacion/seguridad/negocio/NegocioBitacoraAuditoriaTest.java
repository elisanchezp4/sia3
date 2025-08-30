package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.BitacoraAuditoriaDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.ModifyAuditoriaParameterDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.BitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorBitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
public class NegocioBitacoraAuditoriaTest {

    @Mock
    private ManejadorBitacoraAuditoria manejadorBitacoraAuditoria = new ManejadorBitacoraAuditoria();

    @Mock
    private NegocioOrquestadorNotificaciones negocioOrquestadorNotificaciones = new NegocioOrquestadorNotificaciones();

    @InjectMocks
    private NegocioBitacoraAuditoria negocioBitacoraAuditoria = new NegocioBitacoraAuditoria();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void gestionarAuditoriaConDetalleYCampoActivo_DebeCrearBitacoraAuditoriaYGestionarAuditoria() {
        // Arrange
        BigDecimal errorId = BigDecimal.valueOf(123);
        BigDecimal appID = BigDecimal.valueOf(123);
        String userId = "user123";
        String clientId = "123";
        String detalle = "Detalle de la auditoria";
        String campoActivo = "campo1";
        Timestamp fechaEvento = new Timestamp(System.currentTimeMillis());
        BitacoraAuditoriaDTO bitacoraAuditoriaDTO = new BitacoraAuditoriaDTO(errorId, fechaEvento, userId, appID, detalle, campoActivo);
        BitacoraAuditoria bitacoraAuditoria = new BitacoraAuditoria();
        negocioBitacoraAuditoria.copiarPropiedades(bitacoraAuditoria, bitacoraAuditoriaDTO);
        // Configurar el comportamiento del mock manejadorBitacoraAuditoria
        Mockito.doNothing().when(manejadorBitacoraAuditoria).crear(bitacoraAuditoria);

        // Act
        negocioBitacoraAuditoria.gestionarAuditoriaConDetalleYCampoActivo(errorId, userId, clientId, detalle, campoActivo);
        Mockito.verify(negocioOrquestadorNotificaciones, Mockito.times(1)).orquestarNotificacionesAuditoriaPorAplicaciones(any());
    }

    @Test
    public void emitirAuditoriaActualizacionDatos_DebeLlamarOrquestarNotificacionesAuditoriaPorAplicaciones() {
        // Arrange
        String antes = "valorAnterior";
        String despues = "valorNuevo";
        String usuario = "usuario123";
        String campoActivo = "campo1";
        String aplicacionId = "72";

        // Capturar el argumento pasado al método
        ArgumentCaptor<BitacoraAuditoriaDTO> captor = ArgumentCaptor.forClass(BitacoraAuditoriaDTO.class);

        // Act
        negocioBitacoraAuditoria.emitirAuditoriaActualizacionDatosAplicacion(new ModifyAuditoriaParameterDTO(antes, despues, usuario, campoActivo, aplicacionId).logonName("name") );

        // Verificar que se haya llamado al método con el argumento esperado
        verify(negocioOrquestadorNotificaciones).orquestarNotificacionesAuditoriaPorAplicaciones(captor.capture());

        // Obtener el argumento capturado
        BitacoraAuditoriaDTO bitacoraAuditoriaDTO = captor.getValue();

        // Assert
        // Realizar los asserts necesarios sobre el objeto bitacoraAuditoriaDTO
        // Puedes utilizar assertEquals u otras aserciones según la estructura del objeto y los campos que desees verificar
        Assert.assertEquals("campo1", bitacoraAuditoriaDTO.getCampoDirectorio());
    }

    @Test
    public void emitirAuditoriaActualizacionDatos_DebeLlamarMetodosCorrectos() {
        // Arrange
        String antes = "Ruta Anterior";
        String despues = "Ruta Actualizada";
        String usuario = "user123";
        String campoActivo = "ruta";
        BigDecimal errorId = Constantes.EVT_USER_CHANGED_ATTRIBUTE;
        String clientId = Constantes.HBT_ID_APP_SEGURIDAD.toString();
        String detalle = "Se actualizalo un valor del usuario, detalle: " + " dato actual: " + antes + " dato nuevo: " + despues;
        // Act
        negocioBitacoraAuditoria.emitirAuditoriaActualizacionDatos(new ModifyAuditoriaParameterDTO (antes, despues, usuario, campoActivo , null) );

        // Assert
        negocioBitacoraAuditoria.gestionarAuditoriaConDetalleYCampoActivo(errorId, usuario, clientId, detalle, campoActivo);


        Mockito.verify(negocioOrquestadorNotificaciones, Mockito.times(2)).orquestarNotificacionesAuditoriaPorAplicaciones(any());
    }
    @Test
    public void gestionarAuditoriaConDetalleYCampoActivo_DebeLlamarMetodosCorrectos() {
        // Arrange
        BigDecimal errorId = Constantes.EVT_USER_CHANGED_ATTRIBUTE;
        String userId = "user123";
        String clientId = Constantes.HBT_ID_APP_SEGURIDAD.toString();
        String detalle = "detalle de auditoria";
        String campoActivo = "campo activo";
        Timestamp fechaEvento = Timestamp.valueOf(LocalDateTime.now());
        BitacoraAuditoriaDTO audit = new BitacoraAuditoriaDTO(errorId, fechaEvento, userId, new BigDecimal(clientId), detalle, campoActivo);

        // Act
        negocioBitacoraAuditoria.gestionarAuditoriaConDetalleYCampoActivo(errorId, userId, clientId, detalle, campoActivo);

        // Assert
        Mockito.doNothing().when(negocioOrquestadorNotificaciones).orquestarNotificacionesAuditoriaPorAplicaciones(audit);
        Mockito.verify(negocioOrquestadorNotificaciones, Mockito.times(1)).orquestarNotificacionesAuditoriaPorAplicaciones(any());
    }
}
