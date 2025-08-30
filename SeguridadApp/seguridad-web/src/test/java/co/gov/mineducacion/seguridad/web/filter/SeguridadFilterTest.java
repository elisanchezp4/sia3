package co.gov.mineducacion.seguridad.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import static org.junit.Assert.*;

public class SeguridadFilterTest {

    @InjectMocks
    private SeguridadFilter mockSeguridadFilter = new SeguridadFilter();
    @Mock
    private FilterConfig mockFilterConfig;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInit() {
        try {
            mockSeguridadFilter.init(mockFilterConfig);
        } catch (ServletException e) {
            fail("init() lanzó una excepción: " + e.getMessage());
        }
    }

    @Test
    public void testDestroy() {
        try {
            mockSeguridadFilter.destroy();
        } catch (Exception e) {
            fail("destroy() lanzó una excepción: " + e.getMessage());
        }
    }


}