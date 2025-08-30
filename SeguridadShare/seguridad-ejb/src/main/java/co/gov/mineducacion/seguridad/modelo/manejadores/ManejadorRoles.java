package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuarioRolDTO;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import org.eclipse.persistence.config.QueryHints;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre la
 * tabla correspondiente a la entidad Roles.
 * 
 * @author jsoto
 */
@Stateless
public class ManejadorRoles extends ManejadorCrud<Roles, BigDecimal> {

	// Variables HBT

	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ManejadorRoles.class.getName());

	private String mesagge = "Valor query: ";

	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	// Fin variables HBT

	public ManejadorRoles() {
		super(Roles.class);
	}

	// protected region Use esta region para su implementacion del manejador on
	// begin

	// protected region Use esta region para su implementacion del manejador end

	// Inicio metodos HBT
	/**
	 * Metodo que obtiene todos los roles
	 * 
	 * @return
	 * @throws SIA3Exception
	 */
	public List<Roles> getAllRoles(){
		logger.info("Inicio metodo getAllRoles");
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROLES, Roles.class);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Roles> resultList = query.getResultList();
		logger.info("Fin metodo getAllRoles en manejador");
		return resultList;
	}

	/**
	 * Metodo que obtiene lista de roles que coinciden para una app que se pasa
	 * por parametro
	 * 
	 * @param idAplicacion
	 * @return
	 * @throws SIA3Exception
	 */
	public List<Roles> getRolesPorAplicacion(Long aplicacionId){
		logger.info("Inicio metodo getRolesPorAplicacion con aplicacionId+" + aplicacionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROLES_POR_APP, Roles.class);
		query.setParameter(1, aplicacionId);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Roles> resultList = query.getResultList();
		logger.info("Fin metodo getRolesPorAplicacion en manejador");
		return resultList;
	}

	/**
	 * Metodo que busca un rol segun un nombre dado
	 * 
	 * @param nombre
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> buscarRolPorNombre(String nombre) {
		logger.info("Inicio metodo buscarRolPorNombre con parametros nombre=>" + nombre);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROL_POR_NOMBRE, Roles.class);

		query.setParameter(1, nombre);
		logger.info("Fin metodo buscarRolPorNombre");
		return query.getResultList();
	}

	/**
	 * Busca un rol que coincida con el id pasado por parametro
	 * 
	 * @param idRol
	 * @return
	 */
	public Roles getRolPorId(BigDecimal idRol) {
		logger.info("Inicio metodo getRolPorId");
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROL_POR_ID, Roles.class);
		query.setParameter(1, idRol);
		logger.info(mesagge + query);

		Roles respuesta = (Roles) query.getSingleResult();
		logger.info("Fin metodo getRolPorId en manejador");
		return respuesta;
	}

	/**
	 * Metodo que obtiene una lista de roles de acuerdo a un id dado
	 * 
	 * @param rolId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> buscarRolPorId(BigDecimal rolId) {
		logger.info("Inicio metodo buscarRolPorId con parametros rolId=>" + rolId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROL_POR_ID, Roles.class);

		query.setParameter(1, rolId);
		logger.info("Fin metodo buscarRolPorId");
		return query.getResultList();
	}

	/**
	 * Metodo que busca todos los usuarios relacionados a un usuario especifico
	 * 
	 * @param usuarioId
	 * @return
	 * @throws SIA3Exception
	 */
	public List<Roles> getRolesPorUsuario(Long usuarioId){
		logger.info("Inicio metodo getRolesPorUsuario con usuarioId:+" + usuarioId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROLES_POR_USUARIO, Roles.class).setHint(QueryHints.REFRESH, true);
		query.setParameter(1, usuarioId);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Roles> resultList = query.getResultList();
		logger.info("Fin metodo getRolesPorUsuario en manejador");
		return resultList;
	}

	public List<Roles> getRolesPorUsuarioAplicacion(Long usuarioId, BigDecimal aplicacionId){
		logger.info("Inicio metodo getRolesPorUsuario con usuarioId: " + usuarioId + " y aplicacionId: " + aplicacionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROLES_POR_USUARIO_APLICACION, Roles.class).setHint(QueryHints.REFRESH, true);
		query.setParameter(1, usuarioId);
		query.setParameter(2, aplicacionId);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Roles> resultList = query.getResultList();
		logger.info("Fin metodo getRolesPorUsuarioAplicacion en manejador");
		return resultList;
	}

	public List<UsuariosRolDTO> getUsuarioRoles(Long usuarioId){
		logger.info("Inicio metodo getUsuarioRoles con usuarioId:+" + usuarioId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_ROLES);
		query.setParameter(1, usuarioId);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<UsuariosRolDTO> resultList = query.getResultList();
		logger.info("Fin metodo getUsuarioRoles en manejador");
		return resultList;
	}

	public List<UsuarioRolDTO> getRolesUsuario(Long usuarioId){
		logger.info("Inicio metodo getUsuarioRoles con usuarioId: " + usuarioId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_ROLES_USUARIO);
		query.setParameter(1, usuarioId);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();
		List<UsuarioRolDTO> usuarioRolDTOs = new ArrayList<>();
		for (Object[] row : resultList) {
			UsuarioRolDTO usuarioRolDTO = new UsuarioRolDTO(
					(BigDecimal) row[0],
					(BigDecimal) row[1],
					(String) row[2],
					(String) row[3]);
			usuarioRolDTOs.add(usuarioRolDTO);
		}
		if (usuarioRolDTOs.stream().anyMatch(rolesDTO -> rolesDTO.getNombre().contains("Administrador") && rolesDTO.getAplicacion_id().equals(Constantes.ID_SIA3))) {
			logger.info("Exito");
		}
		logger.info("Fin metodo getUsuarioRoles en manejador");
		return usuarioRolDTOs;
	}

	// fin metodos HBT

}
