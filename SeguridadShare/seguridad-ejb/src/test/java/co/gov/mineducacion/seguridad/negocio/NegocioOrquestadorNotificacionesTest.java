package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.BitacoraAuditoriaDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.*;

public class NegocioOrquestadorNotificacionesTest {

    List<AplicacionesDTO> listAplicaciones;
    AplicacionesDTO aplicacionesDTO;

    @Mock
    private NegocioAplicaciones negocioAplicacionMock;

    @Mock
    private ParametrosSng parametrosSngMock;

    @InjectMocks
    private NegocioOrquestadorNotificaciones negocioOrquestadorNotificaciones = new NegocioOrquestadorNotificaciones();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        aplicacionesDTO = new AplicacionesDTO();
        aplicacionesDTO.setRecibirNotificacion(new BigDecimal(1));

        listAplicaciones = new ArrayList<>();
        listAplicaciones.add(aplicacionesDTO);
    }

    @Test
    public void testOrquestarNotificacionesAuditoriaPorAplicaciones_ValidData_SendMessage() throws SIA3Exception {
        // Preparación
        BitacoraAuditoriaDTO auditoriaDTO = new BitacoraAuditoriaDTO();
        auditoriaDTO.setAplicacionId(BigDecimal.valueOf(1L));
        auditoriaDTO.setTipoEvento(new BigDecimal(42));
        when(parametrosSngMock.obtenerParametros()).thenReturn(new Properties());
        when(negocioAplicacionMock.getAllAplicacionesNotificaciones()).thenReturn(listAplicaciones);


        // Ejecución
        negocioOrquestadorNotificaciones.orquestarNotificacionesAuditoriaPorAplicaciones(auditoriaDTO);

        // Verificación
        // Aquí puedes agregar aserciones adicionales para verificar que se llame a los métodos adecuados o se realicen las acciones esperadas.
        verify(parametrosSngMock).obtenerParametros();
        verify(negocioAplicacionMock).getAllAplicacionesNotificaciones();
    }

    @Test
    public void testOrquestarNotificacionesAuditoriaPorAplicaciones_NoMessageSent() {
        // Preparación
        BitacoraAuditoriaDTO auditoriaDTO = new BitacoraAuditoriaDTO();
        auditoriaDTO.setAplicacionId(BigDecimal.valueOf(1L));

        // Ejecución
        negocioOrquestadorNotificaciones.orquestarNotificacionesAuditoriaPorAplicaciones(auditoriaDTO);

        // Verificación
        // Verifica que no se llame a otros métodos o se realicen acciones no deseadas
        verifyNoMoreInteractions(negocioAplicacionMock, parametrosSngMock);
    }

    @Test
    public void testOrquestarNotificacionesAuditoriaPorAplicaciones_NoRecibirNotificacion() throws SIA3Exception {
        // Preparación
        BitacoraAuditoriaDTO auditoriaDTO = new BitacoraAuditoriaDTO();
        auditoriaDTO.setAplicacionId(BigDecimal.valueOf(1L));
        auditoriaDTO.setTipoEvento(new BigDecimal(62));
        listAplicaciones.get(0).setRecibirNotificacion(new BigDecimal(0));
        when(negocioAplicacionMock.getAllAplicacionesNotificaciones()).thenReturn(listAplicaciones);

        // Ejecución
        negocioOrquestadorNotificaciones.orquestarNotificacionesAuditoriaPorAplicaciones(auditoriaDTO);

        // Verificación
        // Aquí puedes agregar aserciones adicionales para verificar que se llame a los métodos adecuados o se realicen las acciones esperadas.
        verify(negocioAplicacionMock).getAllAplicacionesNotificaciones();
    }
}
