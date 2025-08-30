package co.gov.mineducacion.seguridad.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ws.rs.QueryParam;

import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesPorRolDTO;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorRoles;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;

// protected region Incluya importaciones adicionales en esta seccion on begin

// protected region Incluya importaciones adicionales en esta seccion end

/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad Roles
 * 
 * @author jsoto
 */
@Stateless
public class NegocioRoles extends NegocioAbstracto<Roles, RolesDTO> {

	@EJB
	private ManejadorRoles manejadorRoles;

	@PersistenceContext
	private EntityManager em;

	// Inicio variables HBT
	@EJB
	private NegocioOperacionesRol negocioOperacionesRol;

	@EJB
	private NegocioUsuariosRol negocioUsuariosRol;

	@EJB
	private NegocioAplicaciones negocioAplicaciones;

	@EJB
	private NegocioBitacoraAuditoria auditoria;
	// Fin variables HBT

	@EJB
	private NegocioOperaciones negocioOperaciones;

	/**
	 * Variable estatica para imprimir logs...
	 */
	private static final Logger logger = Logger.getLogger(NegocioRoles.class.getName());
	private String mesagge = "Inicio metodo getRolesPorUsuario con usuarioId:";

	// protected region Declare atributos adicionales en esta seccion on begin

	// protected region Declare atributos adicionales en esta seccion end

	/**
	 * Realiza un consulta en la entidad Roles aplicando los filtros, el
	 * ordenamiento, y el rango (from y to) que se pasan como parámetro. Los
	 * parámetros filterBy y orderBy pueden ser nulos. El parámetro from y to
	 * están relacionados. Si from es diferente de nulo to puedo ser nulo, pero
	 * no al revés. Ambos pueden ser nulos, en cuyo caso no se aplica una
	 * restricción de rango a la consulta.
	 * 
	 * @param filterBy
	 *            Cadena de caracteres con los parámetros de filtrado. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere filtrar, seguido por un operador de comparación que
	 *            puede tomar los valores
	 *            {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
	 *            por último el valor por el que se quiere filtrar. Los filtros
	 *            se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
	 *            Ej. Una secuencia de parámetros de filtrado puede ser
	 *            {@literal rolesId>1&rolesName:LIKE:juan}
	 * @param orderBy
	 *            Cadena de caracteres con los parámetros de ordenamiento. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere ordenar, seguido por el símbolo '$' y posteriormente
	 *            por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
	 *            opcionales ya que si no se especifica por defecto se asume que
	 *            el ordenamiento es de forma Ascendente. Si se coloca más de un
	 *            parámetro debe ir separado por coma : ','. Ej. Una secuencia
	 *            de parámetros de ordenamiento puede ser: rolesId$ASC,
	 *            rolesName$DESC
	 * @param from
	 *            Número de registro inicial que se quiere retornar de la
	 *            consulta realizada. Entero mayor o igual a 0
	 * @param to
	 *            Número de registro final que se quiere retornar de la consulta
	 *            realizada. Entero mayor o igual al parámetro from
	 * @return Una lista de DAOs de los Roles que se consultaron con los
	 *         parámetros enviados por el cliente
	 * @throws InvalidParameterException
	 *             Excepción lanzada cuando algunos de los parámetros de la url
	 *             tenía un error de sintáxis por lo que no pudo ser procesado
	 *             correctamente
	 */
	public List<RolesDTO> consultar(String filterBy, String orderBy, Integer from, Integer to)
			throws InvalidParameterException {
		// protected region Modifique el metodo consultar on begin
		logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);

		List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
		List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
		RangoConsulta rango = validarParametrosBloque(from, to);

		return convertirListaEntidadesADao(manejadorRoles.consultar(filtros, ordenamiento, rango));
		// protected region Modifique el metodo consultar end
	}

	/**
	 * Crea el roles que se pasa como parámetro en la base de datos. HBT agrega
	 * excepcion SIA3Exception
	 * 
	 * @param rolesDTO
	 *            El DAO de la entidad Roles a crear. Este se envía en el cuerpo
	 *            de la solicitud POST como un objeto JSON.
	 * @return La insntancia de Roles recién creado
	 */
	public void crear(RolesDTO rolesDTO) throws SIA3Exception {
		// protected region Modifique el metodo crear on begin
		// HBT agrega metodo para validacion antes de la creacion
		try {
			validarRoles(rolesDTO);
			// verificar que nombre y path vengan sin espacios
			rolesDTO.setNombre(rolesDTO.getNombre().trim());
			rolesDTO.setPath(rolesDTO.getPath().trim());
			logService(this.getClass().getName(), "crear", rolesDTO);
			Roles roles = new Roles();
			copiarPropiedades(roles, rolesDTO);
			// Inicio sentencias HBT
			// Setear fecha de creacion y modificacion
			Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
			roles.setFechaCreacion(timestampActual);
			roles.setUltimaModificacion(timestampActual);
			// Fin sentencias HBT
			manejadorRoles.crear(roles);
			// Registrar en auditoria el evento
			logger.info("Inicio registro auditoria evento ROLE_CREATED");
			// Validar que venga el usuario que realiza la operacion
			if (rolesDTO.getUsuarioModificacion() == null) {
				logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
				throw new SIA3Exception(MessagesConstants.APP100120);
			}
			auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.ROLE_CREATED),
					rolesDTO.getUsuarioModificacion().toString(), Constantes.HBT_ID_APP_SEGURIDAD.toString(),
					"Crea rol con nombre:" + rolesDTO.getNombre());
			logger.info("Fin registro auditoria evento ROLE_CREATED");
		} catch (SIA3Exception se) {
			logger.error("Error al validar rolDTO al crear rol->" + se.getMessage());
			throw new SIA3Exception(se.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de crear Rol");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
		// protected region Modifique el metodo crear end
	}

	/**
	 * Actualiza en la base de datos el roles que se pasa como parámetro. HBT
	 * Agrega excepcion SIA3Exception
	 * 
	 * @param rolesDTO
	 *            El DAO de la entidad Roles a actualizar. Este se envía en el
	 *            cuerpo de la solicitud PUT como un objeto JSON.
	 * @return La instancia de la entidad Roles que ha sido actualizado
	 */
	public void actualizar(RolesDTO rolesDTO) throws SIA3Exception {
		// protected region Modifique el metodo actualizar on begin

		logService(this.getClass().getName(), "actualizar", rolesDTO);

		// HBT agrega metodo para validacion antes de la creacion
		try {
			validarRoles(rolesDTO);
			// verificar que nombre y path vengan sin espacios
			rolesDTO.setNombre(rolesDTO.getNombre().trim());
			rolesDTO.setPath(rolesDTO.getPath().trim());
			Roles roles = manejadorRoles.buscar(rolesDTO.getRolId());
			copiarPropiedades(roles, rolesDTO);
			// Inicio sentencias HBT
			// Setear fecha de creacion y modificacion
			Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
			roles.setFechaCreacion(timestampActual);
			roles.setUltimaModificacion(timestampActual);
			// Fin sentencias HBT
			manejadorRoles.actualizar(roles);
			// Registrar en auditoria el evento
			logger.info("Inicio registro auditoria evento ROLE_MODIFIED");
			// Validar que venga el usuario que realiza la operacion
			if (rolesDTO.getUsuarioModificacion() == null) {
				logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
				throw new SIA3Exception(MessagesConstants.APP100120);
			}
			auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.ROLE_MODIFIED),
					rolesDTO.getUsuarioModificacion().toString(), Constantes.HBT_ID_APP_SEGURIDAD.toString(),
					"Modifica rol, asigna nombre: " + rolesDTO.getNombre() + ", path: " + rolesDTO.getPath());
			logger.info("Fin registro auditoria evento ROLE_MODIFIED");
		} catch (SIA3Exception se) {
			logger.error("Error al validar rolDTO al editar rol->" + se.getMessage());
			throw new SIA3Exception(se.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de editar Rol");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
		// protected region Modifique el metodo actualizar end
	}

	/**
	 * Elimina el roles con el identificador que se pasa como parámetro.
	 * 
	 * @param rolId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad roles a eliminar
	 * @return El identificador del roles que ha sido eliminado
	 */
	public String eliminar(@QueryParam("rolId") BigDecimal rolId) {
		// protected region Modifique el metodo eliminar on begin

		logService(this.getClass().getName(), "eliminar", rolId);
		manejadorRoles.eliminarPorId(rolId);

		StringBuilder valores = new StringBuilder();
		valores.append(String.valueOf(rolId));
		return valores.toString();
		// protected region Modifique el metodo eliminar end
	}

	/**
	 * Cuenta la cantidad de registros que devuelve la consulta a la tabla de
	 * aplicando los filtros o rangos que se pasen como parámetro. Estos pueden
	 * ser nulos, en cuyo caso a la consulta no se le realiza ningún tipo de
	 * filtrado.
	 * 
	 * @param filterBy
	 *            Cadena de caracteres con los parámetros de filtrado. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere filtrar, seguido por un operador de comparación que
	 *            puede tomar los valores
	 *            {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
	 *            por último el valor por el que se quiere filtrar. Los filtros
	 *            se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
	 *            Ej. Una secuencia de parámetros de filtrado puede ser
	 *            {@literal rolesId>1&rolesName:LIKE:juan}
	 * @param from
	 *            Número de registro inicial que se quiere retornar de la
	 *            consulta realizada. Entero mayor o igual a 0
	 * @param to
	 *            Número de registro final que se quiere retornar de la consulta
	 *            realizada. Entero mayor o igual al parámetro from
	 * @return El número de registros contados a partir de los parámetros
	 *         enviados por el cliente
	 * @throws InvalidParameterException
	 *             Excepción lanzada cuando algunos de los parámetros de la url
	 *             tenía un error de sintáxis por lo que no pudo ser procesado
	 *             correctamente
	 */
	public String contar(String filterBy, Integer from, Integer to) throws InvalidParameterException {
		// protected region Modifique el metodo contar on begin

		logService(this.getClass().getName(), "contar", filterBy);

		List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
		RangoConsulta rango = validarParametrosBloque(from, to);

		return String.valueOf(manejadorRoles.consultarTotalRegistros(filtros, rango));
		// protected region Modifique el metodo contar end
	}

	/**
	 * 
	 * @param filterBy
	 *            Cadena de caracteres con los parámetros de filtrado. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere filtrar, seguido por un operador de comparación que
	 *            puede tomar los valores
	 *            {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
	 *            por último el valor por el que se quiere filtrar. Los filtros
	 *            se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
	 *            Ej. Una secuencia de parámetros de filtrado puede ser
	 *            {@literal rolesId>1&rolesName:LIKE:juan}
	 * @param orderBy
	 *            Cadena de caracteres con los parámetros de ordenamiento. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere ordenar, seguido por el símbolo '$' y posteriormente
	 *            por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
	 *            opcionales ya que si no se especifica por defecto se asume que
	 *            el ordenamiento es de forma Ascendente. Si se coloca más de un
	 *            parámetro debe ir separado por coma : ','. Ej. Una secuencia
	 *            de parámetros de ordenamiento puede ser: rolesId$ASC,
	 *            rolesName$DESC
	 * @param atributo
	 *            Nombre del atributo de la entidad Roles del cual se quieren
	 *            obtener los diferentes valores.
	 * @return Una lista con los diferentes valores que se encuentran en la
	 *         columna de la tabla asociada al atributo.
	 * @throws InvalidParameterException
	 *             Si el atributo no existe en la entidad o si los filtros y el
	 *             ordenamiento contienen atributos de la entidad que no
	 *             existen.
	 */
	public List<String> consultarLista(String filterBy, String orderBy, String atributo)
			throws InvalidParameterException {
		// protected region Modifique el metodo consultarLista on begin

		logService(this.getClass().getName(), "contar", filterBy, orderBy, atributo);

		List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
		List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
		InformacionAgrupamiento infoAgrupamiento = decodificarInformacionAgrupamiento(atributo);

		return UtilOperaciones
				.convertirListaObjetosAString(manejadorRoles.consultarLista(filtros, ordenamiento, infoAgrupamiento));
		// protected region Modifique el metodo consultarLista end
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param nombreAtributo
	 *            {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	protected boolean entidadContieneAtributo(String nombreAtributo) {
		return Roles.contieneAtributo(nombreAtributo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	protected Logger getLogger() {
		return logger;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	protected RolesDTO instanciarDAO() {
		return new RolesDTO();
	}

	/**
	 * Consulta un rol por nombre
	 * 
	 * @param nombreRol
	 * @return
	 * @throws SeguridadException
	 */
	public Roles consultarRolePorNombre(String nombreRol) throws SeguridadException {
		Roles respuesta;
		try {
			TypedQuery<Roles> query = em.createNamedQuery("Roles.findByNombreRol", Roles.class);
			query.setParameter("nombre", nombreRol);
			respuesta = query.getResultList().get(0);
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR, TipoExcepcion.ERROR);
		}
		return respuesta;
	}

	/**
	 * Consulta rol por nombre y aplicacion
	 * @param nombreRol nombre del rol a consultar
	 * @param aplicacion identificador de la aplicacion a la que pertenece el rol
	 * @return
	 * @throws SeguridadException
	 */
	public Roles consultarRolePorNombreYAplicacion(String nombreRol, BigDecimal aplicacion) throws SeguridadException {
		Roles respuesta;
		try {
			TypedQuery<Roles> query = em.createNamedQuery("Roles.findByNombreRolYAplicacion", Roles.class);
			query.setParameter("nombre", nombreRol);
			query.setParameter("aplicacionId", aplicacion);
			respuesta = query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR, TipoExcepcion.ERROR);
		}
		return respuesta;
	}

	/**
	 * Retorna aquellos roles cuyo nombre sea igual al recibido por parametro
	 * @param nombreRol nombre de rol consultar
	 * @return
	 * @throws SeguridadException
	 */
	public List<Roles> consultarRolesPorNombre(String nombreRol) throws SeguridadException {
		List<Roles> respuesta = new ArrayList<>();
		try {
			TypedQuery<Roles> query = em.createNamedQuery("Roles.findByNombreRol", Roles.class);
			query.setParameter("nombre", nombreRol);
			respuesta = query.getResultList();
		} catch (NoResultException ex) {
			return respuesta;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR, TipoExcepcion.ERROR);
		}
		return respuesta;
	}

	// Inicio metodos HBT
	/**
	 * Metodo para obtener todos los roles
	 * 
	 * @return
	 * @throws SIA3Exception
	 */
	public List<RolesDTO> getAllRoles() throws SIA3Exception {
		try {
			logger.info("Inicio metodo getAllRoles");
			List<RolesDTO> rolDTOList = BuilderDTO.toRolDTOList(manejadorRoles.getAllRoles());
			if (rolDTOList.isEmpty()) {
				logger.error("No hay roles registrados.");
				throw new SIA3Exception(MessagesConstants.APP100021);
			}
			logger.info("fin metodo getAllRoles");
			return rolDTOList;
		} catch (PersistenceException e) {
			logger.error("Error en metodo getAllRoles:" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metodo para obtener roles de una aplicacion id dado
	 * 
	 * @param aplicacionId
	 * @return
	 * @throws SIA3Exception
	 */
	public List<RolesDTO> getRolesPorAplicacion(Long aplicacionId) throws SIA3Exception {
		try {
			logger.info("Inicio metodo getRolesPorAplicacion con aplicacionId:" + aplicacionId);
			List<RolesDTO> rolDTOList = BuilderDTO.toRolDTOList(manejadorRoles.getRolesPorAplicacion(aplicacionId));
			if (rolDTOList.isEmpty()) {
				logger.error("Error en metodo consultar: No hay roles para la app con id:" + aplicacionId);
				throw new SIA3Exception(MessagesConstants.APP100022);
			}
			logger.info("fin metodo getRolesPorAplicacion");
			return rolDTOList;
		} catch (PersistenceException e) {
			logger.error("Error en metodo getRolesPorAplicacion:" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metodo para validar los datos que vienen en un dto de rol
	 * 
	 * @param rolDTO
	 * @throws SIA3Exception
	 */
	private void validarRoles(RolesDTO rolDTO) throws SIA3Exception {
		// obligatoriedad
		if (rolDTO.getNombre().trim().equals("") || rolDTO.getNombre() == null) {
			logger.error("Error en metodo validarRoles: Campo nombre no puede ser nulo");
			throw new SIA3Exception(MessagesConstants.APP100027);
		}
		if (rolDTO.getEstado() == null) {
			logger.error("Error en metodo validarRoles: Campo estado no puede ser  nulo");
			throw new SIA3Exception(MessagesConstants.APP100028);
		}
		if (rolDTO.getEstado() != null && rolDTO.getEstado().equals(new BigDecimal(0))) {
			logger.error("Error en metodo validarRoles: Campo estado no puede ser  nulo");
			throw new SIA3Exception(MessagesConstants.APP100028);
		}

		if (rolDTO.getAplicacionId() == null) {
			logger.error("Error en metodo validarRoles: Campo aplicacionId no puede ser  nulo");
			throw new SIA3Exception(MessagesConstants.APP100029);
		}
		if (rolDTO.getAplicacionId() != null && rolDTO.getAplicacionId().equals(new BigDecimal(0))) {
			logger.error("Error en metodo validarRoles: Campo aplicacionId no puede ser  nulo");
			throw new SIA3Exception(MessagesConstants.APP100029);
		}

		if (rolDTO.getUsuarioModificacion() == null) {
			logger.error("Error en metodo validarRoles: Campo usuario modificacion no puede ser vacio o nulo");
			throw new SIA3Exception(MessagesConstants.APP100030);
		}
		if (rolDTO.getUsuarioModificacion() != null && rolDTO.getUsuarioModificacion().equals(new BigDecimal(0))) {
			logger.error("Error en metodo validarRoles: Campo usuario modificacion no puede ser  nulo");
			throw new SIA3Exception(MessagesConstants.APP100030);
		}
		if (rolDTO.getPath().trim().equals("") || rolDTO.getPath() == null) {
			logger.error("Error en metodo validarRoles: Campo path no puede ser nulo");
			throw new SIA3Exception(MessagesConstants.APP100031);
		}

		// nombre unico
		List<Roles> rolesExistentes = manejadorRoles.buscarRolPorNombre(rolDTO.getNombre());

		for (Roles rolLista : rolesExistentes) {
			if (!rolLista.getRolId().equals(rolDTO.getRolId())
					&& rolLista.getAplicacionId().equals(rolDTO.getAplicacionId())) {
				logger.error("Error en metodo validarRoles: Ya existe un rol con el nombre:" + rolDTO.getNombre());
				throw new SIA3Exception(MessagesConstants.APP100039);
			}
		}

	}

	/**
	 * Metodo que elimina un rol del sistema
	 * 
	 * @param rolDTO
	 * @throws SIA3Exception
	 */
	public void eliminarRol(BigDecimal rolId, String usuarioId) throws SIA3Exception {
		logger.info("Inicio metodo eliminarRol con rolId =>" + rolId);
		try {
			String nombreRol = "";
			// Verificar que el rol exista
			List<Roles> rolesExistentes = manejadorRoles.buscarRolPorId(rolId);
			if (rolesExistentes == null || rolesExistentes.isEmpty()) {
				logger.error("Error en metodo eliminarRol: NO EXISTE rol con id:" + rolId);
				throw new SIA3Exception(MessagesConstants.APP100032);
			} else {
				for (Roles rol : rolesExistentes) {
					nombreRol = rol.getNombre();
				}
			}
			// eliminar operacionesRol relacionadas
			negocioOperacionesRol.validarOperacionesRolXRol(rolId);
			// eliminar UsuariosRol relacionados
			negocioUsuariosRol.validarUsuariosRolXRol(rolId);
			// eliminar rol del sistema
			eliminar(rolId);
			logger.info("Finalizó el metodo eliminarRol con rolId => " + rolId);
			// Registrar en auditoria el evento
			logger.info("Inicio registro auditoria evento ROLE_DELETED");
			// Validar que venga el usuario que realiza la operacion
			if (usuarioId == null || usuarioId.equals("")) {
				logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
				throw new SIA3Exception(MessagesConstants.APP100120);
			}
			auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.ROLE_DELETED), usuarioId,
					Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Borra rol nombre:" + nombreRol);
			logger.info("Fin registro auditoria evento ROLE_DELETED");
		} catch (SIA3Exception e) {
			logger.error("Error en metodo eliminarRol:" + e.getCause());
			throw new SIA3Exception(e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de eliminar Rol");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metodo para eliminar roles de una aplicacion dada
	 * 
	 * @param aplicacionId
	 * @throws SIA3Exception
	 */
	public void eliminarRolXAplicacion(Long aplicacionId) throws SIA3Exception {
		logger.info("Inicio metodo eliminarRolXAplicacion con aplicacionId =>" + aplicacionId);
		try {
			// obtener roles relacionados con la aplicacion
			List<RolesDTO> rolesDTOList = BuilderDTO.toRolDTOList(manejadorRoles.getRolesPorAplicacion(aplicacionId));
			logger.info("obtiene lista de usuarios relacionados, rolesDTOList ->" + rolesDTOList);
			if (!rolesDTOList.isEmpty()) {
				for (RolesDTO rolDTO : rolesDTOList) {
					// eliminar rol
					logger.info("Dentro de  lista rolesDTOList, borrar rol -> " + rolDTO);
					eliminarRol(rolDTO.getRolId(), rolDTO.getUsuarioModificacion().toString());
				}
			}

		} catch (SIA3Exception e) {
			logger.error("Error en metodo eliminarRolXAplicacion:" + e);
			throw new SIA3Exception(MessagesConstants.APP100033);
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de eliminar Rol en metodo eliminarRolXAplicacion");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metodo para validar roles de una aplicacion dada
	 * 
	 * @param aplicacionId
	 * @throws SIA3Exception
	 */
	public void validarRolXAplicacion(Long aplicacionId) throws SIA3Exception {
		logger.info("Inicio metodo validarRolXAplicacion con aplicacionId =>" + aplicacionId);
		try {
			// obtener roles relacionados con la aplicacion
			List<RolesDTO> rolesDTOList = BuilderDTO.toRolDTOList(manejadorRoles.getRolesPorAplicacion(aplicacionId));
			logger.info("obtiene lista de usuarios relacionados, rolesDTOList ->" + rolesDTOList);
			if (!rolesDTOList.isEmpty()) {
				throw new SIA3Exception(MessagesConstants.APP100119);
			}

		} catch (SIA3Exception e) {
			logger.error("Error en metodo validarRolXAplicacion:" + e);
			throw new SIA3Exception(MessagesConstants.APP100033);
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de eliminar Rol en metodo validarRolXAplicacion");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metdo que consulta el rol que coincida con el id que se pasa por
	 * parametro
	 * 
	 * @param idRol
	 * @return
	 * @throws SIA3Exception
	 */
	public Roles consultarRolPorId(BigDecimal idRol) throws SIA3Exception {
		try {
			logger.info("Inicio metodo consultarRolPorId con idRol:" + idRol);
			Roles rol = manejadorRoles.getRolPorId(idRol);
			if (rol == null) {
				logger.error("Error en metodo consultar: No hay roles con id:" + idRol);
				throw new SIA3Exception(MessagesConstants.APP100047);
			}
			logger.info("fin metodo consultarRolPorId");
			return rol;
		} catch (SIA3Exception e) {
			logger.error("Error en metodo consultarRolPorId");
			throw new SIA3Exception(MessagesConstants.APP100051);
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de consultar Rol" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metodo que obtiene lista de todos los usuarios relacionados a un rol
	 * especifico
	 * 
	 * @param usuarioId
	 * @return
	 * @throws SIA3Exception
	 */
	public List<RolesDTO> getRolesPorUsuario(Long usuarioId) throws SIA3Exception {
		try {
			logger.info(mesagge + usuarioId);
			List<RolesDTO> rolDTOList = BuilderDTO.toRolDTOList(manejadorRoles.getRolesPorUsuario(usuarioId));
			if (rolDTOList.isEmpty()) {
				logger.error("Error en metodo getRolesPorUsuario: No hay roles para usuario con id:" + usuarioId);
				throw new SIA3Exception(MessagesConstants.APP100094);
			} else {
				List<UsuariosRolDTO> usuarioRoles = manejadorRoles.getUsuarioRoles(usuarioId);
				rolDTOList.get(0).setUsuariosRolDTOs(usuarioRoles);
			}
			logger.info("fin metodo getRolesPorUsuario");
			return rolDTOList;
		} catch (PersistenceException e) {
			logger.error("Error en metodo getRolesPorUsuario:" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	// Fin metodos HBT


	public List<RolesDTO> getRolesPorUsuarioId(Long usuarioId) throws SIA3Exception {
		try {
			logger.info(mesagge + usuarioId);
			List<RolesDTO> rolDTOList = BuilderDTO.toRolDTOList(manejadorRoles.getRolesPorUsuario(usuarioId));
			for(RolesDTO r : rolDTOList){
				logger.info("Roles del usuario----->" + r.getNombre());
			}
			if (rolDTOList.isEmpty()) {
				logger.error("Error en metodo getRolesPorUsuario: No hay roles para usuario con id:" + usuarioId);
				throw new SIA3Exception(MessagesConstants.APP100094);
			}
			logger.info("fin metodo getRolesPorUsuario");
			return rolDTOList;
		} catch (PersistenceException e) {
			logger.error("Error en metodo getRolesPorUsuario:" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	public List<RolesDTO> getRolesPorUsuarioIdAplicacionId(Long usuarioId, BigDecimal aplicacionId) throws SIA3Exception {
		try {
			logger.info(mesagge + usuarioId + "y aplicacionId:" + aplicacionId);
			List<RolesDTO> rolDTOList = BuilderDTO.toRolDTOList(manejadorRoles.getRolesPorUsuarioAplicacion(usuarioId, aplicacionId));
			for(RolesDTO r : rolDTOList){
				logger.info("Roles del usuario----->" + r.getNombre());
			}
			if (rolDTOList.isEmpty()) {
				logger.error("Error en metodo getRolesPorUsuarioIdAplicacionId: No hay roles para usuario con id:" + usuarioId + "y aplicacion con id:" +aplicacionId );
				throw new SIA3Exception(MessagesConstants.APP100094);
			}
			logger.info("fin metodo getRolesPorUsuarioIdAplicacionId");
			return rolDTOList;
		} catch (PersistenceException e) {
			logger.error("Error en metodo getRolesPorUsuarioIdAplicacionId:" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	public void importar(RolesDTO rolesDTO) throws SIA3Exception {

		try {
			validarRoles(rolesDTO);

			List<OperacionesDTO> listaOperacionesGuardadas = negocioOperaciones.getOperacionesPorAplicacion(rolesDTO.getAplicacionId());
			if(listaOperacionesGuardadas.isEmpty()) {
				throw new SIA3Exception(MessagesConstants.APP100126);
			}

			rolesDTO.setNombre(rolesDTO.getNombre().trim());
			rolesDTO.setPath(rolesDTO.getPath().trim());
			logService(this.getClass().getName(), "crear", rolesDTO);
			Roles roles = new Roles();
			copiarPropiedades(roles, rolesDTO);
			Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
			roles.setFechaCreacion(timestampActual);
			roles.setUltimaModificacion(timestampActual);
			manejadorRoles.crear(roles);
			Roles rolGuardado = consultarRolePorNombre(roles.getNombre());
			List<OperacionesPorRolDTO> operacionPorRolList = new ArrayList<>();
			listaOperacionesGuardadas.forEach( operacionesDTO ->  {
				OperacionesPorRolDTO operacionesPorRolDTO = new OperacionesPorRolDTO();
				operacionesPorRolDTO.setRolId(rolGuardado.getRolId());
				operacionesPorRolDTO.setOperacionId(operacionesDTO.getOpcionId());
				operacionPorRolList.add(operacionesPorRolDTO);
			});
			negocioOperacionesRol.crearOperacionPorRol(operacionPorRolList);
			logger.info("Inicio registro auditoria evento ROLE_IMPORTED");
			auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_IMPORT_ROL),
					rolesDTO.getUsuarioModificacion().toString(), Constantes.HBT_ID_APP_SEGURIDAD.toString(),
					"Importa rol con nombre:" + rolesDTO.getNombre());
			logger.info("Fin registro auditoria evento ROLE_IMPORTED");
		} catch (SIA3Exception se) {
			logger.error("Error al validar rolDTO al importar rol->" + se.getMessage());
			throw new SIA3Exception(se.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de importar Rol");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

}
