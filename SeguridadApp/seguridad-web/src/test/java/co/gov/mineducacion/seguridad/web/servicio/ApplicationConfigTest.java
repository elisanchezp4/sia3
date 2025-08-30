package co.gov.mineducacion.seguridad.web.servicio;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Set;

import static org.junit.Assert.*;

public class ApplicationConfigTest {

    @InjectMocks
    private ApplicationConfig mockApplicationConfig = new ApplicationConfig();

    @Test
    public void testGetClassesReturnsEmptySet() {
        Set<Class<?>> result = mockApplicationConfig.getClasses();

        assert result != null;
        assert result.isEmpty();
    }

}