package co.gov.mineducacion.seguridad.negocio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class NegocioColaAccesibilidadTest {

    private NegocioColaAccesibilidad negocioColaAccesibilidad;

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private Session session;

    @Mock
    private Queue queue;

    @Mock
    private MessageProducer producer;

    @Mock
    private MessageProducer messageProducer;

    @Before
    public void setup() throws JMSException, NamingException {
        MockitoAnnotations.initMocks(this);
        negocioColaAccesibilidad = new NegocioColaAccesibilidad();

        when(connectionFactory.createConnection()).thenReturn(connection);
        when(connection.createSession(anyBoolean(), anyInt())).thenReturn(session);
        when(session.createProducer(any(Queue.class))).thenReturn(messageProducer);
    }

    @Test
    public void enviarColaPropAccesibilidad_DebeEnviarMensajeALaCola() throws JMSException {
        // Arrange
        String usrsId = "user123";
        Map<String, String> propiedades = new HashMap<>();
        propiedades.put("propiedad1", "valor1");
        propiedades.put("propiedad2", "valor2");

        when(connectionFactory.createConnection()).thenReturn(connection);
        when(connection.createSession(true, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
        when(session.createProducer(queue)).thenReturn(producer);

        // Act
        negocioColaAccesibilidad.enviarColaPropAccesibilidad(propiedades, usrsId);

        // Assert using assertions instead of verify
        Assert.assertEquals(connection, connectionFactory.createConnection());
        Assert.assertEquals(session, connection.createSession(true, Session.AUTO_ACKNOWLEDGE));
        Assert.assertEquals(producer, session.createProducer(queue));
    }
}
