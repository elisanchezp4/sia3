package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre la
 * tabla correspondiente a la entidad Operaciones.
 * 
 * @author jsoto
 */
@Stateless
public class ManejadorOperacionesPorRol extends ManejadorCrud<Operaciones, BigDecimal> {

	// Inicio variables HBT

	private static final Logger LOG = Logger.getLogger(ManejadorOperacionesPorRol.class.getName());

	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	// Fin variables HBT

	public ManejadorOperacionesPorRol() {
		super(Operaciones.class);
	}

	public List<Operaciones> buscarOperacionesRolXUsuario(List<BigDecimal> rolId, BigDecimal clientID) {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(rolId);
		parameters.add(clientID);
		return (List<Operaciones>) createNamedQuery(parameters, "Operaciones.findRolOperacionesByUser");
	}

	// Inicio metodos HBT

	/**
	 * Metodo que obtiene todas las operaciones
	 * 
	 * @return
	 * @throws SIA3Exception
	 */
	public List<Operaciones> getAllOperaciones() {
		LOG.info("Inicio metodo getAllOperaciones");
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACIONES, Operaciones.class);
		LOG.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<Operaciones> resultList = query.getResultList();
		LOG.info("Fin metodo getAllOperaciones en manejador");
		return resultList;
	}

	/**
	 * Metodo que busca todas las operaciones relacionadas a un rol y aplicacion
	 * dadas por parametro
	 * 
	 * @param rolId
	 * @param aplicacionId
	 * @return
	 */
	public List<Operaciones> getOperacionesPorRol(BigDecimal rolId, BigDecimal aplicacionId){
		LOG.info("Inicio metodo getOperacionesPorRol");

		StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_OPERACIONES_POR_ROL);
		//Si viene parametro de aplicacion incluirlo al query
		if (aplicacionId != null) {
			consulta.append(SqlConstants.Y_OPERACION_APLICACION_ID);
		}
		//Realizar la consulta con los parametros indicados
		Query query = em.createNativeQuery(consulta.toString(), Operaciones.class);
		query.setParameter(1, rolId);
		if (aplicacionId != null) {
			query.setParameter(2, aplicacionId);
		}
		LOG.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<Operaciones> resultList = query.getResultList();
		LOG.info("Fin metodo getOperacionesPorRol en manejador");

		return resultList;
	}
	// Fin metodos HBT

	// protected region Use esta region para su implementacion del manejador on
	// begin

	// protected region Use esta region para su implementacion del manejador end
}
