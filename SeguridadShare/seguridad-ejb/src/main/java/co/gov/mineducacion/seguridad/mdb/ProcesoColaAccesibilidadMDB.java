package co.gov.mineducacion.seguridad.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarioAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;


@MessageDriven( mappedName = Constantes.JMS_SIA3_QUEUE, 
activationConfig = {
        @ActivationConfigProperty(propertyName = Constantes.DESTINATION_TYPE, propertyValue =Constantes.JAVAX_JMS_QUEUE),
        @ActivationConfigProperty(propertyName = Constantes.CONNECTION_FACTORY_JNDI_NAME, propertyValue = Constantes.JMS_SIA3_CONNECTION_FACTORY),
        @ActivationConfigProperty(propertyName = Constantes.DESTINATION_NAME, propertyValue = Constantes.JMS_SIA3_QUEUE)
}
)
public class ProcesoColaAccesibilidadMDB implements  MessageListener{


	@EJB
	IUsuarioAccesibilidad servicioUsrsAcce;
	
	private static final Logger logger = Logger.getLogger(ProcesoColaAccesibilidadMDB.class);
	
	/**
	 * Método encargado de procesar las colas localizadas en el servidor de aplicaciones weblogic 
	 * @author nramirezj
	 * @param message
	 */
	
	@Override
	public void onMessage(Message message)  {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			Object objMsj = objectMessage.getObject();
			servicioUsrsAcce.almacenarPropAcc(objMsj);
		} catch (Throwable t) {
			logger.error("Exception en onMessage():" + t.getMessage());
        }
		
	}

	 

}
