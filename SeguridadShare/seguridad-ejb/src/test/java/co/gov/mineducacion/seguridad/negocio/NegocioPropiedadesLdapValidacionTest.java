package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.mockito.Mockito.*;

public class NegocioPropiedadesLdapValidacionTest {

    @Mock
    private InitialContext initialContextMock;

    @Mock
    private Context contextMock;

    @InjectMocks
    private NegocioPropiedadesLdapValidacion negocioPropiedadesLdapValidacion = new NegocioPropiedadesLdapValidacion();

    @Before
    public void setUp() throws NamingException {
        MockitoAnnotations.initMocks(this);
        when(initialContextMock.lookup("java:comp/env")).thenReturn(contextMock);
    }

    @Test
    public void testInit_ValidFile_InitializeLdapValidacionesUtil() throws NamingException, SIA3Exception {
        // Preparación
        final String rutaArchivo = "ruta/validaciones-ldap.properties";
        when(contextMock.lookup("validaciones-ldap-ruta")).thenReturn(rutaArchivo);

        // Ejecución
        negocioPropiedadesLdapValidacion.init();

        // Verifica que se inicialice LdapValidacionesUtil con la ruta del archivo esperada
        LdapValidacionesUtil.inizializar(rutaArchivo);
    }
}