package co.gov.mineducacion.seguridad.modelo.rabbitmq;

import co.gov.mineducacion.seguridad.modelo.dtos.BitacoraAuditoriaDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class SendTest {
    Properties properties;
    BitacoraAuditoriaDTO bitacoraAuditoriaDTO;
    private static final String EXCHANGE_NAME = "QUEUE_TEST_MSN";

    @Mock
    Connection connection;

    @Mock
    Channel channel;

    @Before
    public void setUp() throws IOException, TimeoutException {
        MockitoAnnotations.initMocks(this);

        bitacoraAuditoriaDTO = new BitacoraAuditoriaDTO(Timestamp.from(Instant.now()), UUID.randomUUID().toString(), new BigDecimal(10));

        properties = new Properties();
        properties.setProperty("HOST_RABBIT_MQ","localhost");
        properties.setProperty("PUERTO_RABBIT_MQ","123");
        properties.setProperty("USUARIO_RABBIT_MQ","user");
        properties.setProperty("PASS_RABBIT_MQ","pass");
    }

    @Test
    public void testEnviarMensajeEnCola() throws IOException, TimeoutException, SIA3Exception {
        try (MockedStatic<Configuracion> utilConfiguracion = Mockito.mockStatic(Configuracion.class)) {
            utilConfiguracion.when((MockedStatic.Verification) Configuracion.conectarRabbitMQ(any())).thenReturn(connection);
            when(connection.createChannel()).thenReturn(channel);
            Send.enviarMensajeEnCola(properties, EXCHANGE_NAME, new Gson().toJson(bitacoraAuditoriaDTO));

            verify(connection,times(1)).createChannel();
        }
    }

    @Test(expected = Exception.class)
    public void testEnviarMensajeEnColaCatch() throws IOException, TimeoutException, SIA3Exception {
        try (MockedStatic<Configuracion> utilConfiguracion = Mockito.mockStatic(Configuracion.class)) {
            utilConfiguracion.when((MockedStatic.Verification) Configuracion.conectarRabbitMQ(any())).thenThrow(new IOException());
            Send.enviarMensajeEnCola(properties, EXCHANGE_NAME, new Gson().toJson(bitacoraAuditoriaDTO));
        }
    }
}