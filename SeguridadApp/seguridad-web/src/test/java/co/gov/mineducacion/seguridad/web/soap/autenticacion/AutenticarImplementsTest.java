package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion;
import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class AutenticarImplementsTest {
/*
    @InjectMocks
    private AutenticarImplements autenticarService = new AutenticarImplements();

    @Mock
    private IUsuarios servicioUsuarios;
    @Mock
    private IServicioAutenticacion servicioAutenticacion;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        autenticarService.servicioUsuarios = servicioUsuarios;
        autenticarService.servicioAutenticacion = servicioAutenticacion;
    }

    @Test
    public void testModificarPassword() throws Exception {
        ModificarPasswordRq modificarPasswordRq = new ModificarPasswordRq();
        Aplicacion aplicacion = new Aplicacion();
        EncabezadoSeguridad encabezadoSeguridadRq = new EncabezadoSeguridad();

        Object tObject = new Object();

        doNothing().when(servicioUsuarios.verificarUsuarioAdministrador(anyInt(), anyString())).thenReturn(true);

        autenticarService.modificarPassword(modificarPasswordRq, aplicacion, encabezadoSeguridadRq);

        verify(servicioUsuarios).verificarUsuarioAdministrador(any(), anyString());
        verify(servicioAutenticacion).modificarPassword(anyString(), any(), anyString(), anyString(), anyString());
    }*/

}