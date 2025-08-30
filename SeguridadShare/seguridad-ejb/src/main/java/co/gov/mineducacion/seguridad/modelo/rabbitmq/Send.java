package co.gov.mineducacion.seguridad.modelo.rabbitmq;

import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class Send {
    private Send(){/* Recomendacion por sonar */}
    private static final Logger logger = Logger.getLogger(Send.class.getName());

    public static void enviarMensajeEnCola(Properties propiedades,String cola, String message) throws IOException, TimeoutException, SIA3Exception {
        try {
            Map<String, Object> args = new HashMap<>();
            args.put("x-message-ttl", 3600000);
            Channel channel = Configuracion.conectarRabbitMQ(propiedades).createChannel();
            channel.queueDeclare(cola, false, false, false, args);
            AMQP.BasicProperties.Builder propsBuilder = new AMQP.BasicProperties.Builder()
                    .expiration( "3600000" );
            channel.basicPublish("", cola, propsBuilder.build(), message.getBytes());
            logger.info("Sent msn: " + message + " cola: " + cola);
        } catch (IOException | TimeoutException e) {
            logger.error("Error publicando topic: " + cola + " error: " + e);
            throw new SIA3Exception(e.getMessage(), e);
        }
    }
}