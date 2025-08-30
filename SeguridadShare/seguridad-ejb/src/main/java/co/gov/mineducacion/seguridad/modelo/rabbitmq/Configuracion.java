package co.gov.mineducacion.seguridad.modelo.rabbitmq;

import co.gov.mineducacion.seguridad.modelo.utils.UtilsDesencriptar;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.HOST_RABBIT_MQ;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.PASS_RABBIT_MQ;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.PORT_RABBIT_MQ;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.USER_RABBIT_MQ;

public class Configuracion {
    private Configuracion(){/* Recomendacion por sonar */}
    private static final Logger logger = Logger.getLogger(Configuracion.class.getName());

    public static Connection conectarRabbitMQ(Properties propiedades) throws IOException, TimeoutException {
        logger.info("Inicia conexion rabbitMQ");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(propiedades.getProperty(HOST_RABBIT_MQ));
        factory.setPort(Integer.parseInt(propiedades.getProperty(PORT_RABBIT_MQ)));
        factory.setUsername(consultarDatosOcultos(propiedades.getProperty(USER_RABBIT_MQ)));
        factory.setPassword(consultarDatosOcultos(propiedades.getProperty(PASS_RABBIT_MQ)));
        return factory.newConnection();
    }

    private static String consultarDatosOcultos(String llave) {
        return UtilsDesencriptar.desencriptar(llave);
    }
}