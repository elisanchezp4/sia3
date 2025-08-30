package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


import javax.ejb.Stateless;
import javax.persistence.*;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre la
 * tabla correspondiente a la entidad Operaciones.
 * HBT agrega metodos para manejo de modulo de seguridad
 *
 * @author jsoto
 */
@Stateless
public class ManejadorOperaciones extends ManejadorCrud<Operaciones, BigDecimal> {

	// Inicio variables HBT

	private static final Logger logger = Logger.getLogger(ManejadorOperaciones.class.getName());


	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	// Fin variables HBT

	public ManejadorOperaciones() {
		super(Operaciones.class);
	}

	public List<Operaciones> buscarOperacionesRolXUsuario(List<BigDecimal> rolId, BigDecimal clientID) {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(rolId);
		parameters.add(clientID);
		return (List<Operaciones>) createNamedQuery(parameters, "Operaciones.findRolOperacionesByUser");
	}

	/**
	 *
	 * @param rolId
	 * @param clientID
	 * @return
	 */
	public List<Operaciones> buscarOperacionesPorsuario(String idUsuario, BigDecimal appId) {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(appId);
		parameters.add(idUsuario);
		return (List<Operaciones>) createNamedQuery(parameters, "Operaciones.findOperacionesByUser");
	}

	// Inicio metodos HBT

	/**
	 * Metodo que obtiene todas las operaciones
	 *
	 * @return
	 * @throws SIA3Exception
	 */
	public List<Operaciones> getAllOperaciones() {
		logger.info("Inicio metodo getAllOperaciones");
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACIONES, Operaciones.class);
		logger.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<Operaciones> resultList = query.getResultList();
		logger.info("Fin metodo getAllOperaciones en manejador");
		return resultList;
	}

	/**
	 * Metodo que busca todas las operaciones relacionadas a un rol dado por
	 * parametro
	 *
	 * @param idRol
	 * @return
	 */
	public List<Operaciones> getOperacionesPorRol(Long idRol) {
		logger.info("Inicio metodo getOperacionesPorRol");
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACIONES_POR_ROL, Operaciones.class);
		query.setParameter(1, idRol);
		logger.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<Operaciones> resultList = query.getResultList();
		logger.info("Fin metodo getOperacionesPorRol en manejador");
		return resultList;
	}

	/**
	 * Metodo que busca una operacion que coincida con el id que se pasa por
	 * parametro
	 *
	 * @param idOperacion
	 * @return
	 */
	public Operaciones getOperacionPorId(BigDecimal idOperacion) {
		logger.info("Inicio metodo getOperacionPorId");
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACION_POR_ID, Operaciones.class);
		query.setParameter(1, idOperacion);
		logger.info("Valor query: " + query);
		Operaciones respuesta = (Operaciones) query.getSingleResult();
		logger.info("Fin metodo getOperacionesPorRol en manejador");
		return respuesta;
	}

    /**
     * Metodo que obtiene lista de todas las operaciones que coincidan con el
     * nombre pasado por parametro
     *
     * @param nombre
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Operaciones> buscarOperacionesPorNombre(String nombre, String aplicacionId) {
        logger.info("Inicio metodo buscarOperacionesPorNombre con parametros nombre => " + nombre + " y aplicacionId =>" + aplicacionId);

        StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_OPERACION_POR_NOMBRE_OBJETO);
        if (aplicacionId != null) {
            consulta.append(SqlConstants.Y_OP_APLICACION_ID);
        }
        logger.info("Inicia consulta: " + consulta);
        Query query = em.createNativeQuery(consulta.toString(), Operaciones.class);
        query.setParameter(1, nombre);
        if (aplicacionId != null) {
            query.setParameter(2, aplicacionId);
        }
        logger.info("Fin metodo buscarOperacionesPorNombre");
        return query.getResultList();
    }

	/**
	 * Metodo que busca todas las operaciones que coincidan con la aplicacion y
	 * nombre de operacion indicada
	 *
	 * @param aplicacionId
	 * @param nombreObjeto
	 * @return
	 */
	public List<Operaciones> getOperacionesPorFiltro(BigDecimal aplicacionId, String nombreObjeto) {
		logger.info("Inicio metodo getOperacionesPorFiltro");
		// obtener lista de operaciones padres y operaciones hijas
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACIONES_PADRES_HIJOS, Operaciones.class);
		query.setParameter(1, aplicacionId);
		// Verificar si se incluye filtro por nombre de operacion
		if (nombreObjeto == null) {
			query.setParameter(2, "%%");
		} else {
			query.setParameter(2, "%" + nombreObjeto.trim() + "%");
		}

		logger.info("Valor query: " + query);
		// Devolver lista con resultados encontrados
		@SuppressWarnings("unchecked")
		List<Operaciones> resultList = query.getResultList();
		logger.info("Fin metodo getOperacionesPorFiltro en manejador");
		return resultList;

	}

	/**
	 * Metodo ue busca la operacion que coincida con el id pasado por parametro
	 *
	 * @param opcionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Operaciones> buscarOperacionesPorId(BigDecimal opcionId) {
		logger.info("Inicio metodo buscarOperacionesPorId con parametros opcionId=>" + opcionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACION_POR_ID, Operaciones.class);
		query.setParameter(1, opcionId);
		logger.info("Fin metodo buscarOperacionesPorId");
		return query.getResultList();
	}

	/**
	 * Metodo que busca todas las operaciones que descienden de la operacion con
	 * id dado por parametro
	 *
	 * @param opcionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Operaciones> buscarOperacionesHijas(BigDecimal opcionId) {
		logger.info("Inicio metodo buscarOperacionesHijas con parametros opcionId=>" + opcionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACIONES_HIJAS, Operaciones.class);
		query.setParameter(1, opcionId);
		logger.info("Fin metodo buscarOperacionesHijas");
		return query.getResultList();
	}

	/**
	 * Metodo que busca todas las operaciones relacionadas con una aplicacion
	 * @param aplicacionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Operaciones> buscarOperacionesPorAplicacion(BigDecimal aplicacionId) {
		logger.info("Inicio metodo buscarOperacionesPorAplicacion con parametros aplicacionId=>" + aplicacionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACION_POR_APLICACION, Operaciones.class);
		query.setParameter(1, aplicacionId);
		logger.info("Fin metodo buscarOperacionesPorAplicacion");
		return query.getResultList();
	}

	/**
	 * Metodo que busca todas las operaciones padre de una operacion dad
	 * @param opcionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Operaciones> buscarOperacionesPadres(BigDecimal opcionId) {
		logger.info("Inicio metodo buscarOperacionesPadres con parametros opcionId=>" + opcionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACIONES_PADRES, Operaciones.class);
		query.setParameter(1, opcionId);
		logger.info("Fin metodo buscarOperacionesPadres");
		return query.getResultList();
	}
	// Fin metodos HBT

	// protected region Use esta region para su implementacion del manejador on
	// begin

	// protected region Use esta region para su implementacion del manejador end

	public Operaciones buscarOperacionesPorNombreYAplicacion(String nombre, String aplicacionId) {
		Operaciones resultadoConsulta;
		logger.info("Inicio metodo buscarOperacionesPorNombreYAplicacion con parametros nombre: " + nombre + "y aplicacionId: " + aplicacionId);
		StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_OPERACION_POR_NOMBRE_OBJETO);
		consulta.append(SqlConstants.Y_OP_APLICACION_ID);
		Query query = em.createNativeQuery(consulta.toString(), Operaciones.class);
		query.setParameter(1, nombre);
		query.setParameter(2, aplicacionId);
		try {
			resultadoConsulta = (Operaciones) query.getSingleResult();
		}catch (NoResultException | NonUniqueResultException e){
			resultadoConsulta = null;
		}
		logger.info("Fin metodo buscarOperacionesPorNombreYAplicacion");
		return resultadoConsulta;
	}

	public void eliminarOperacionesHijas(BigDecimal opcionPadreId) {
		logger.info("Inicio metodo eliminarOperacionesHijas con parametros opcionPadreId: " + opcionPadreId);
		Query query = em.createNativeQuery(SqlConstants.DELETE_OPERACIONES_POR_PADRE_ID);
		query.setParameter(1, opcionPadreId);
		logger.info("Fin metodo eliminarOperacionesHijas");
		query.executeUpdate();
	}


}
