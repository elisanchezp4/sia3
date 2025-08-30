package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Agregada por HBT Manejador que define las operaciones CRUD y de negocio a
 * realizar sobre la tabla correspondiente a la entidad Aplicaciones.
 * 
 * @author jfonseca
 */
@Stateless
public class ManejadorAplicaciones extends ManejadorCrud<Aplicaciones, BigDecimal> {

	// Variables HBT

	/**
	 * Imprimir logs
	 */
	private static final Logger LOG = Logger.getLogger(ManejadorAplicaciones.class.getName());

	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	// Fin variables HBT

	public ManejadorAplicaciones() {
		super(Aplicaciones.class);
	}

	// protected region Use esta region para su implementacion del manejador on
	// begin

	// protected region Use esta region para su implementacion del manejador end

	// HBT -- metodos para pantallas de administracion

	/**
	 * Metodo que consulta todas las aplicaciones en el estado que 
	 * se recibe por parametro
	 * @param estado estado de las aplicaciones a retornar
	 * @return
	 */
	public List<Aplicaciones> getAllAplicaciones(Long estado){
		LOG.info("Inicio metodo getAllAplicaciones con filtro estado:"+estado);
		StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_APLICACIONES);
		if (estado != null) {
			consulta.append(SqlConstants.Y_ESTADO_APP);
		}	
		consulta.append(SqlConstants.ORDENAR_FECHA_CREACION);
		
		Query query = em.createNativeQuery(consulta.toString(), Aplicaciones.class);
		if(estado != null){
			query.setParameter(1, estado);
		}		
		LOG.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<Aplicaciones> resultList = query.getResultList();
		return resultList;
	}


	public List<Aplicaciones> getAllAplicacionesNotificaciones(){
		LOG.info("Inicio metodo getAllAplicacionesNotificaciones ");
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_APLICACIONES_NOTIFICACIONES, Aplicaciones.class);
		@SuppressWarnings("unchecked")
		List<Aplicaciones> resultList = query.getResultList();
		LOG.info("Fin metodo getAllAplicaciones en manejador" +resultList);
		return resultList;
	}

	/**
	 * Metodo que obtiene una lista de aplicaciones segun un nombre
	 * 
	 * @param nombre
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Aplicaciones> buscarAplicacionPorNombre(String nombre) {
		LOG.info("Inicio metodo buscarAplicacionPorNombre con parametros nombre=>" + nombre);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_APLICACION_POR_NOMBRE, Aplicaciones.class);

		query.setParameter(1, nombre);
		LOG.info("Fin metodo buscarAplicacionPorNombre");
		return query.getResultList();
	}

	/**
	 * Metodo que obtiene la aplicacion que coincida con el id dado por
	 * parametro
	 * 
	 * @param aplicacionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Aplicaciones> buscarAplicacionPorId(BigDecimal aplicacionId) {
		LOG.info("Inicio metodo buscarAplicacionPorId con parametros aplicacionId=>" + aplicacionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_APLICACION_POR_ID, Aplicaciones.class);

		query.setParameter(1, aplicacionId);
		LOG.info("Fin metodo buscarAplicacionPorId");
		return query.getResultList();
	}

	/**
	 * Metodo que cuenta la cantidad de aplicaciones que hay registradas en BD
	 * 
	 * @return
	 */
	public Long contarAplicaciones() {
		LOG.info("Inicio metodo contarAplicaciones");
		Query query = em.createNativeQuery(SqlConstants.CONTAR_APLICACIONES, Aplicaciones.class);
		return (Long) query.getResultList().get(0);
	}

	/**
	 * Metodo que consulta todas las aplicaciones
	 * 
	 * @return
	 */
	public List<Aplicaciones> getAplicacionesPorUsuario(Long estado, BigDecimal usuarioId){
		LOG.info("Inicio metodo getAplicacionesPorUsuario con filtro estado:"+estado+ "usuarioId: "+ usuarioId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_APLICACIONES_POR_USUARIO, Aplicaciones.class);
		query.setParameter(1, estado);
		query.setParameter(2, usuarioId);
				
		LOG.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<Aplicaciones> resultList = query.getResultList();
		LOG.info("Fin metodo getAplicacionesPorUsuario en manejador");
		return resultList;
	}
	
	/**
	 * Metodo que consulta todas las aplicaciones
	 * 
	 * @return
	 */
	public List<Aplicaciones> getAplicacionesNombre(Long estado, String nombre){
		LOG.info("Inicio metodo getAplicacionesNombre con filtro estado:"+estado);
		StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_APLICACIONES);
		if (estado != null) {
			consulta.append(SqlConstants.Y_ESTADO_APP);
		}	
		if(nombre != null){
			consulta.append(SqlConstants.Y_NOMBRE);
		}
		consulta.append(SqlConstants.ORDENAR_FECHA_CREACION);
		
		Query query = em.createNativeQuery(consulta.toString(), Aplicaciones.class);
		if(estado != null){
			query.setParameter(1, estado);
		}	
		if(nombre != null){
			query.setParameter(2, "%" + nombre.toUpperCase() + "%");
		}	
		LOG.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<Aplicaciones> resultList = query.getResultList();
		LOG.info("Fin metodo getAplicacionesNombre en manejador");
		return resultList;
	}
	// Fin métodos HBT
}
