package co.gov.mineducacion.seguridad.modelo.manejadores;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.entidades.Mensaje;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;


/**
 * Agregada por HBT para manejo de mensajes
 * @author jfonseca
 *
 */
@Stateless
public class ManejadorMensaje extends ManejadorCrud<Mensaje, BigDecimal> {
	/**
	 * Imprimir logs
	 */
	private static final Logger LOG = Logger.getLogger(ManejadorMensaje.class.getName());


	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public ManejadorMensaje() {
		super(Mensaje.class);
	}
	
	/**
	 * Metodo que obtiene un mensaje segun un codigo de mensaje dado
	 * @param codigo
	 * @return
	 */
	public Mensaje findByCode(String codigo) {
		LOG.info("Inicio metodo findByCode");
		LOG.info("Codigo del mensaje : " + codigo);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_MENSAJES_POR_CODIGO, Mensaje.class);
		query.setParameter(1, codigo);
		@SuppressWarnings("unchecked")
		List<Mensaje> resultList = query.getResultList();
		LOG.info("Fin metodo findByCode");
		return resultList.isEmpty() ? null : resultList.get(0);
	}

}
