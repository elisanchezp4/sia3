package co.gov.mineducacion.seguridad.negocio;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.utils.Constantes;

@Stateless
public class NegocioColaAccesibilidad {

	private static final Logger logger = Logger.getLogger(NegocioColaAccesibilidad.class);

	@Resource(mappedName = Constantes.JMS_SIA3_CONNECTION_FACTORY)
	ConnectionFactory conectionfactory;

	@Resource(mappedName = Constantes.JMS_SIA3_QUEUE)
	private Queue queue;

	/**
	 * Método encargado de enviar a la cola que se encuentra en el servidor de aplicaciones weblogic, las propiedades de accesibilidad
	 * configuradas por el usuario.
	 * @param propiedades
	 * @param usrsId
	 */
	public void enviarColaPropAccesibilidad(Map<String, String> propiedades, String usrsId) {
		Connection connection = null;
		Session session = null;
		try {
			propiedades.put("usrsId", usrsId);
			connection = conectionfactory.createConnection();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			MessageProducer productor = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage((Serializable) propiedades);
			productor.send(objectMessage);
			session.close();
			connection.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
