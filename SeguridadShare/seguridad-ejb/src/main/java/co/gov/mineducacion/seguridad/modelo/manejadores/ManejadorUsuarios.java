package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre la
 * tabla correspondiente a la entidad Usuarios. HBT agrega metodos para manejo
 * de modulo seguridad
 * 
 * @author jsoto
 */
@Stateless
public class ManejadorUsuarios extends ManejadorCrud<Usuario, String> {

	// Inicio Variables HBT
	@EJB
	private ManejadorUsuariosRol manejadorUsuariosRol;

	@EJB
	private ManejadorRoles manejadorRoles;
	/**
	 * Imprimir logs
	 */
	private static final Logger logger = Logger.getLogger(ManejadorUsuarios.class.getName());
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

	public ManejadorUsuarios() {
		super(Usuario.class);
	}

	// protected region Use esta region para su implementacion del manejador on
	// begin

	// Inicio metodos HBT

	/**
	 * Buscar un usuario que coincida con el nombre pasado por parametro
	 * 
	 * @param nombreUsuario
	 * @return
	 */
	public Usuario getUsuarioPorNombre(String nombreUsuario) {
		logger.info("Inicio metodo getUsuarioPorNombre con nombreUsuario: " + nombreUsuario);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_NOMBRE, Usuario.class);
		query.setParameter(1, nombreUsuario);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Usuario> resultList = query.getResultList();
		logger.info("Fin metodo getUsuarioPorNombre en manejador");
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	public Usuario buscarUsuarioPorCorreoElectronico(String correoElectronico){
		logger.info("Inicio metodo buscarUsuarioPorCorreoElectronico con correoElectronico: " + correoElectronico);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_CORREO_ELECTRONICO, Usuario.class);
		query.setParameter(1, correoElectronico);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Usuario> resultList = query.getResultList();
		logger.info("Fin metodo buscarUsuarioPorCorreoElectronico en manejador");
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	/**
	 * Obtiene una lista de usuarios que coincidan con el nombre pasado por
	 * parametro
	 * 
	 * @param nombre
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosPorNombre(String nombre) {
		logger.info("Inicio metodo listarUsuariosPorNombre con parametros nombre: " + nombre);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_NOMBRE, Usuario.class);
		query.setParameter(1, nombre);
		logger.info("Fin metodo listarUsuariosPorNombre");
		return query.getResultList();
	}

	/**
	 * Obtiene usuario que coincida con el id dado
	 * 
	 * @param usuarioId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosPorId(BigDecimal usuarioId) {
		logger.info("Inicio metodo listarUsuariosPorId con parametros usuarioId: " + usuarioId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_ID, Usuario.class);
		query.setParameter(1, usuarioId);
		logger.info("Fin metodo listarUsuariosPorId");
		return query.getResultList();
	}

	/**
	 * Metodo que busca los usuarios relacionados con una aplicacion y rol
	 * indicados
	 * 
	 * @param opcionId
	 * @param rolId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UsuariosDTO> listarUsuariosPorAppRol(BigDecimal opcionId, BigDecimal rolId) {
		logger.info("Inicio metodo listarUsuariosPorAppRol con parametros opcionId: " + opcionId + " y rolId: " + rolId);
		List<UsuariosDTO> listaUsuariosDTO = new ArrayList<>();

		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_APP_Y_ROL);
		query.setParameter(1, opcionId);
		query.setParameter(2, rolId);
		query.setParameter(3, Constantes.ESTADO_VINCULADO);
		logger.info("La consulta va asi: " + query);
		List<Object[]> listaUsuariosObj = query.getResultList();

		for (Object[] objects : listaUsuariosObj) {
			listaUsuariosDTO.add(getUsuariosDTO(objects));
		}

		logger.info("Fin metodo listarUsuariosPorAppRol: " + listaUsuariosDTO);
		return listaUsuariosDTO;

	}

	private String toString(Object valor){
		if (valor != null){
			return valor.toString();
		}
		return "";
	}

	private UsuariosDTO getUsuariosDTO(Object[] objects){
		UsuariosDTO usuariosDTO = new UsuariosDTO();
		usuariosDTO.setUsuarioId(toString(objects[0]));
		usuariosDTO.setRuta(toString(objects[1]));
		usuariosDTO.setTipo(objects[2] != null ? ((BigDecimal) objects[2]) : null);
		usuariosDTO.setEstado(objects[3] != null ? ((BigDecimal) objects[3]) : null);
		usuariosDTO.setFechaCreacion(objects[4] != null ? ((Date) objects[4]) : null);
		usuariosDTO.setUltimaModificacion(objects[5] != null ? ((Date) objects[5]) : null);
		usuariosDTO.setUsuarioModificacion(toString(objects[6]));
		usuariosDTO.setLogonName(toString(objects[7]));
		usuariosDTO.setNuevoPass(toString(objects[8]));
		usuariosDTO.setFechaVinculacion(objects[9] != null ? ((Date) objects[9]) : null);
		usuariosDTO.setEstadoVinculacion(toString(objects[10]));
		return usuariosDTO;
	}

	/**
	 * Metodo que busca los usuarios relacionados con una aplicacion y rol
	 *
	 * @param aplicacionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosPorApp(BigDecimal aplicacionId) {
		Query query = null;
		logger.info("Inicio metodo listarUsuariosPorApp con parametros opcionId " + aplicacionId);

		query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_APP, Usuario.class);
		query.setParameter(1, aplicacionId);

		logger.info("Fin metodo listarUsuariosPorApp");
		return query.getResultList();
	}

	/**
	 * Metodo que busca los usuarios relacionados con una aplicacion y rol
	 * 
	 * @param opcionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosPorApp(BigDecimal opcionId, BigDecimal aplicacionId) {
		Query query = null;
		logger.info("Inicio metodo listarUsuariosPorApp con parametros opcionId: " + opcionId + " y aplicacionId: " + aplicacionId);

		// aqui consulta para traer todos los usuarios  de sia3
		if (aplicacionId == null) {
			query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_APP, Usuario.class);
			query.setParameter(1, opcionId);

			// Aquí trae los usuarios ya asignados
		} else {
			query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_APP_SEGURIDAD, Usuario.class);
			query.setParameter(1, opcionId);
			query.setParameter(2, aplicacionId);

		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuarioPorAppExiste(BigDecimal usuarioId, String aplicacionId) {
		Query query = null;
		logger.info("Inicio metodo getUsuarioPorAppExiste con parametros aplicacionId " + aplicacionId);

		// aqui consulta para traer todos los usuarios  de sia3
			query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_POR_APP_EXISTE, Usuario.class);
			query.setParameter(1, aplicacionId);
			query.setParameter(2, usuarioId);

			// Aquí trae los usuarios ya asignados
		return query.getResultList();
	}
	
	/**
	 * metodo para determinar si un usuario es admin
	 * 
	 * @param usuarioId
	 */
	public Boolean userIsAdmin(BigDecimal usuarioId){
		List<Roles> usuarioRoles = manejadorRoles.getRolesPorUsuario(usuarioId.longValue());

		Boolean isAdmin = false;

		for (Roles rol : usuarioRoles) {
			if (Constantes.ROL_SIA3_ADMIN_ID.equals(rol.getRolId())
					&& "Administrador".equalsIgnoreCase(rol.getNombre())
					&& Constantes.ESTADO_VINCULADO_ID.equals(rol.getEstado())
					&& Constantes.ID_SIA3.equals(rol.getAplicacionId())){

					isAdmin = true;

			}
		}
		return isAdmin;
	}

	/**
	 * metodo para desvincular usuarios
	 *
	 * @param usuariosRolDTO
	 */
	public void desvincularUsuarios(UsuariosRolDTO usuariosRolDTO) {

		logger.info("Inicio metodo usuariosRolDTO: " + usuariosRolDTO);

		UsuariosRol usuariosRol = manejadorUsuariosRol.buscar(usuariosRolDTO.getUsuariosRolPK());
		usuariosRol.setEstadoVinculacion(Constantes.ESTADO_DESVINCULADO);
		usuariosRol.setFechaDesvinculacion(new Date());
		logger.info("Finaliza update: " + usuariosRol);
		manejadorUsuariosRol.actualizar(usuariosRol);

	}

	
	/**
	 * Metodo que busca todos los usuarios registrados
	 * @param estado
	 * @return
	 * @throws SIA3Exception
	 */
	public List<Usuario> getAllUsuarios(Long estado){
		logger.info("Inicio metodo getAllUsuarios con filtro estado " + estado);
		StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_USUARIOS_ALL);
		//filtrar solo por usuarios que tengan registro de auditoria
		consulta.append(SqlConstants.Y_USUARIO_AUDITORIA);
		//filtrar por estado si viene
		if (estado != null) {
			consulta.append(SqlConstants.Y_ESTADO_USER);
		}
		//ordenar registros
		consulta.append(SqlConstants.ORDEN_USUARIO);
		
		Query query = em.createNativeQuery(consulta.toString(), Usuario.class);
		if(estado != null){
			query.setParameter(1, estado);
		}
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Usuario> resultList = query.getResultList();
		logger.info("Fin metodo getAllUsuarios en manejador");
		return resultList;
	}
	
	/**
	 * Metodo que busca todos los usuarios que pertenezcan al sistema de seguridad
	 * 
	 * @param estado
	 * @return
	 */
	public List<Usuario> getAllUsuariosSeguridad(Long estado) {
		logger.info("Inicio metodo getAllUsuariosSeguridad con filtro estado: " + estado);
		StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_USUARIOS_ALL);
		//filtrar solo por usuarios que pertenezcan a seguridad
		consulta.append(SqlConstants.FILTRO_USUARIOS_SEGURIDAD);
		//filtrar solo por usuarios que tengan registro de auditoria
		consulta.append(SqlConstants.Y_USUARIO_AUDITORIA);
		//filtrar por estado si viene
		if (estado != null) {
			consulta.append(SqlConstants.Y_ESTADO_USER);
		}
		//ordenar registros
		consulta.append(SqlConstants.ORDEN_USUARIO);
		
		Query query = em.createNativeQuery(consulta.toString(), Usuario.class);
		//Filtrar resultados por estado si viene diligenciado
		if(estado != null){
			query.setParameter(1, estado);
		}		
		//Filtrar resultados por él, id de la aplicacion de seguridad
		query.setParameter(2, Constantes.HBT_ID_APP_SEGURIDAD);
		logger.info(mesagge + query);

		@SuppressWarnings("unchecked")
		List<Usuario> resultList = query.getResultList();
		logger.info("Fin metodo getAllUsuariosSeguridad en manejador");
		return resultList;
	}



	public List<Usuario> getAllUsuariosPorApp(Long aplicacionId, String usuario){
		logger.info("Inicio metodo getAllUsuariosPorApp con aplicacionId: " + aplicacionId);
		Query query = em.createNativeQuery(SqlConstants.USUARIOS_POR_APLICACION_Y_NOMBRE, Usuario.class);
		query.setParameter(1, aplicacionId);
		query.setParameter(2, usuario );
		logger.info(mesagge + query);
		@SuppressWarnings("unchecked")
		List<Usuario> resultList = query.getResultList();
		logger.info("Fin metodo getAllUsuariosPorApp en manejador");
		return resultList;
	}


	// Fin metodos HBT

	// protected region Use esta region para su implementacion del manejador end
	
	/**
	 * Metodo que busca los usuarios vinculados o desvinculados relacionados con una aplicacion y nombre rol
	 * indicados
	 * 
	 * @param opcionId
	 * @param rolId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UsuariosDTO> getUsuariosPorAppNombreRol(BigDecimal opcionId, String rol,Long estado,Long idUsuario,  Integer pagInicio, Integer pagFin) {
		logger.info("Inicio metodo getUsuariosPorAppNombreRol con parametros opcionId: " + opcionId + " y  rol: " + rol);
		List<UsuariosDTO> listaUsuariosDTO = new ArrayList<>();
		
		StringBuilder queryConsultarFiscalizadores = new StringBuilder(SqlConstants.CONSULTAR_USUARIO_POR_APP_Y_NOMBRE_ROL);
	
		if (estado != null) {
			queryConsultarFiscalizadores.append(SqlConstants.Y_ESTADO_USUARIO);
		} 
		if (idUsuario != null) {
			queryConsultarFiscalizadores.append(SqlConstants.Y_ID_USUARIO);
		}
	
		Query query = em.createNativeQuery(queryConsultarFiscalizadores.toString());
		query.setParameter(1, opcionId);
		query.setParameter(2, rol);
		
		if (idUsuario != null) {
			query.setParameter(3, idUsuario);
		} 
		if (estado != null) {
			query.setParameter(4, estado);
		}
			
		if (pagInicio != null && pagFin != null) {
			query.setFirstResult(pagInicio);
			query.setMaxResults(pagFin);
		}

		logger.info("La consulta va asi: " + query);
		List<Object[]> listaUsuariosObj = query.getResultList();

		for (Object[] objects : listaUsuariosObj) {
			listaUsuariosDTO.add(getUsuariosDTO(objects));
		}

		logger.info("Fin metodo getUsuariosPorAppNombreRol: " + listaUsuariosDTO);
		return listaUsuariosDTO;

	}

	/**
	 * Metodo que busca los usuarios vinculados o desvinculados relacionados con una aplicacion y nombre rol
	 * indicados
	 * 
	 * @param opcionId
	 * @param rolId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Long getCountUsuariosPorAppNombreRol(BigDecimal opcionId, String rol,Long estado,Long idUsuario) {
		logger.info("Inicio metodo getCountUsuariosPorAppNombreRol con parametros opcionId: " + opcionId + " y  rol: " + rol);
		StringBuilder queryConsultarFiscalizadores = new StringBuilder(SqlConstants.COUNT_USUARIO_POR_APP_Y_NOMBRE_ROL);
	
		if (estado != null) {
			queryConsultarFiscalizadores.append(SqlConstants.Y_ESTADO_USUARIO);
		} 
		if (idUsuario != null) {
			queryConsultarFiscalizadores.append(SqlConstants.Y_ID_USUARIO);
		}
	
		Query query = em.createNativeQuery(queryConsultarFiscalizadores.toString());
		query.setParameter(1, opcionId);
		query.setParameter(2, rol);
		
		if (idUsuario != null) {
			query.setParameter(3, idUsuario);
		} 
		if (estado != null) {
			query.setParameter(4, estado);
		}
		Long countUsuarios = ((BigDecimal) query.getSingleResult()).longValue();
		logger.info("Fin metodo getCountUsuariosPorAppNombreRol: " + countUsuarios);
		return countUsuarios;

	}

}
