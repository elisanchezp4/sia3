package co.gov.mineducacion.utha.seguridad.web.servicio.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;

public class ConstantesServiciosTest {

    @Test(expected = IllegalAccessException.class)
    public void testConstructorPrivado() throws Exception {
        Constructor<ConstantesServicios> constructor = ConstantesServicios.class.getDeclaredConstructor();

        assertTrue("El constructor debe ser privado", isPrivate(constructor));

        constructor.setAccessible(false);
        constructor.newInstance();
    }

    private boolean isPrivate(Constructor<?> constructor) {
        return (constructor.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0;
    }
}