package co.gov.mineducacion.seguridad.ejb.servicios;

import java.util.ResourceBundle;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilProperties;
import co.gov.mineducacion.seguridad.modelo.utils.UtilTexto;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;

public class ServiciosCommons {

	@EJB
	protected NegocioUsuarios negocioUsuarios;

	private static final Logger logger = Logger.getLogger(ServiciosCommons.class);

	private static ResourceBundle mensajes = ResourceBundle.getBundle(Constantes.RUTA_PROPERTIES_MENSAJES);

	/**
	 * Procesa el error y responde al servicio
	 * 
	 * @param error
	 * @throws SeguridadException
	 * @throws SIGAAExcepcion
	 * 
	 *             TODDO Enviar respuesta al servicio SOAP
	 */
	public static void procesarError(Exception error) throws SeguridadException {
		SeguridadException errorSeguridad;
		if (!(error instanceof SeguridadException)) {
			logger.error(error.getMessage(), error);
			errorSeguridad = new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
		} else {
			errorSeguridad = (SeguridadException) error;
			logger.error(errorSeguridad.getId(), error);
		}
		errorSeguridad = procesarMensaje(errorSeguridad);
		throw new SeguridadException(errorSeguridad.getMessage(), errorSeguridad.getId());

	}

	/**
	 * Procesa el error y responde al servicio
	 * 
	 * @param error
	 * @throws SeguridadException
	 * @throws SIGAAExcepcion
	 * 
	 *             TODDO Enviar respuesta al servicio SOAP
	 */
	public SeguridadException procesarErrorRe(Exception error)  {
		SeguridadException errorSeguridad;
		if (!(error instanceof SeguridadException)) {
			logger.error(error.getMessage(), error);
			errorSeguridad = new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.FATAL);
		} else {
			errorSeguridad = (SeguridadException) error;
			logger.error(errorSeguridad.getId(), error);
		}
		errorSeguridad = procesarMensaje(errorSeguridad);
		return errorSeguridad;

	}

	/**
	 * Busca el mensaje en el archivo de propiedades
	 * 
	 * @param error
	 * @return
	 */
	private static SeguridadException procesarMensaje(SeguridadException error) {
		if (error.getId() != null && UtilTexto.estaVacio(error.getMessage())) {
			String textoMensaje = UtilProperties.cargarPropiedad(mensajes,
					Constantes.ESTRUCTURA_MENSAJE + error.getId(), error.getParametros());
			return new SeguridadException(error.getId(), error.getTipo(), textoMensaje, error.getParametros());
		}
		return error;
	}

}
