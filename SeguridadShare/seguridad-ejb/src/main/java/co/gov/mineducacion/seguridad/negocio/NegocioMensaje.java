package co.gov.mineducacion.seguridad.negocio;

import java.util.List;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

import co.gov.mineducacion.seguridad.modelo.entidades.Mensaje;
import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorMensaje;

/**
 * Agregada por HBT para el manejo de mensajes en la aplicacion SIA3
 * @author jfonseca
 *
 */
@Stateless
public class NegocioMensaje {
	// log de la aplicacion para la clase
	private static final Logger LOG = Logger.getLogger(NegocioMensaje.class.getName());
	
	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = "SeguridadDS")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}


	@EJB
	private ManejadorMensaje manejadorMensaje;

	public NegocioMensaje(){/* Recomendacion por sonar */}

	/**
	 * Metodo para obtener la lista de mensajes
	 * @return
	 * @throws SIA3Exception
	 */
	public List<MensajeDTO> obtenerMensajes() throws SIA3Exception {
		try {
			LOG.info("Inicio metodo obtenerMensajes");
			Query query = em.createNativeQuery(SqlConstants.CONSULTAR_MENSAJES, Mensaje.class);
			LOG.info("Valor query: " + query);
			
			@SuppressWarnings("unchecked")
			List<MensajeDTO> resultList = query.getResultList();
			LOG.info("Fin metodo obtenerMensajes en manejador");
			return resultList;
			
		}catch (Exception e) {
			LOG.error("Fallo en la operacion de obtencion de mensajes"+ "Mensaje: "+ e.getMessage());
			throw new SIA3Exception(MessagesConstants.APP000000 +"Mensaje: "+ e.getCause());
		}
		
	}

	
	/**
	 * Metodo que obtiene un dto de mensaje segun un codigo ingresado
	 * @param codigo
	 * @param parametros
	 * @return
	 * @throws SIA3Exception
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MensajeDTO mensajePorCodigo(String codigo, String... parametros) throws SIA3Exception {
		LOG.info("Inicio mensajePorCodigo en NegocioMensaje con parametros codigo:"+codigo);
		try {
			MensajeDTO mensajeDTO=BuilderDTO.toMensajeDTO(manejadorMensaje.findByCode(codigo));
			if (mensajeDTO == null) {
				LOG.error("Error al consultar mensajePorCodigo, mensajeDTO es nulo");				
				throw new SIA3Exception(MessagesConstants.APP000001);
			}
			
			String mensaje=mensajeDTO.getDescripcion();
			for (int i = 0; i < parametros.length; i++) {
				String parametro = parametros[i];
				mensaje = mensaje.replaceFirst("\\?", parametro);
			}
			mensajeDTO.setDescripcion(mensaje);

			return mensajeDTO;
		} catch (SIA3Exception e) {
			LOG.error("Se capturó la exepción propia lanzada en el try", e.getCause());

			throw e;
		} catch (Exception e) {
			LOG.error("Fallo consultando el mensaje" + " Mensaje: " +  e.getCause());
			throw new SIA3Exception(MessagesConstants.APP000001);
		}
	}

	public MensajeDTO getMensajeDTOError(String codigo, String descripcion){
		MensajeDTO mensajeDTO = new MensajeDTO();
		mensajeDTO.setCodigo(codigo);
		mensajeDTO.setTipoMensaje(MessagesConstants.TIPO_MENSAJE_ERROR);
		mensajeDTO.setDescripcion(descripcion);
		return mensajeDTO;
	}

	public Response getResponseException(Exception e){
		MensajeDTO mensajeDTO = getMensajeDTOError(MessagesConstants.APP000003_CODE, MessagesConstants.APP000003);
		LOG.error("Ocurrio un error inesperado en servicio");
		LOG.error(e.getClass().getName() + " --> " + e);
		if (e instanceof SIA3Exception){
			try {
				mensajeDTO = mensajePorCodigo(e.getMessage());
			}catch (SIA3Exception se1) {
				LOG.error("Error obteniendo mensaje por codigo. " + se1);
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
	}

	public Response getResponseException(Exception e, String codigo){
		MensajeDTO mensajeDTO = getMensajeDTOError(MessagesConstants.APP000003_CODE, MessagesConstants.APP000003);
		LOG.error("Ocurrio un error inesperado en servicio");
		LOG.error(e.getClass().getName() + " --> " + e);
		if (e instanceof SIA3Exception){
			try {
				mensajeDTO = mensajePorCodigo(codigo);
			}catch (SIA3Exception se1) {
				LOG.error("Error obteniendo mensaje por codigo. " + se1);
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(mensajeDTO).build();
	}

}
