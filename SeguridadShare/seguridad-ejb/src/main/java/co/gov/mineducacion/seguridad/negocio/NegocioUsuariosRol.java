package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuarioRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.*;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorAplicaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.gov.mineducacion.seguridad.modelo.utils.*;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorRoles;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuariosRol;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;

import java.math.BigDecimal;
import java.util.Optional;

// protected region Incluya importaciones adicionales en esta seccion on begin

// protected region Incluya importaciones adicionales en esta seccion end

/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad UsuariosRol
 * 
 * @author jsoto
 */
@Stateless
public class NegocioUsuariosRol extends NegocioAbstracto<UsuariosRol, UsuariosRolDTO> {

	@EJB
	private ManejadorUsuariosRol manejadorUsuariosRol;

	@PersistenceContext
	private EntityManager em;

	// Inicio variables HBT
	@EJB
	private NegocioRoles negocioRoles;

	@EJB
	private ManejadorUsuarios manejadorUsuarios;
	
	@EJB
	private ManejadorRoles manejadorRoles;

	@EJB
	private ManejadorAplicaciones manejadorAplicaciones;
	@EJB
	private NegocioAplicaciones negocioAplicaciones;
	
	@EJB
	private NegocioBitacoraAuditoria auditoria;
	// Fin variables HBT

	/**
	 * Variable estatica para imprimir logs...
	 */
	private static final Logger logger = Logger.getLogger(NegocioUsuariosRol.class.getName());
	private String mesagge = "actualizar";

	// protected region Declare atributos adicionales en esta seccion on begin

	// protected region Declare atributos adicionales en esta seccion end

	/**
	 * Realiza un consulta en la entidad UsuariosRol aplicando los filtros, el
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
	 *            {@literal usuariosRolId>1&usuariosRolName:LIKE:juan}
	 * @param orderBy
	 *            Cadena de caracteres con los parámetros de ordenamiento. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere ordenar, seguido por el símbolo '$' y posteriormente
	 *            por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
	 *            opcionales ya que si no se especifica por defecto se asume que
	 *            el ordenamiento es de forma Ascendente. Si se coloca más de un
	 *            parámetro debe ir separado por coma : ','. Ej. Una secuencia
	 *            de parámetros de ordenamiento puede ser: usuariosRolId$ASC,
	 *            usuariosRolName$DESC
	 * @param from
	 *            Número de registro inicial que se quiere retornar de la
	 *            consulta realizada. Entero mayor o igual a 0
	 * @param to
	 *            Número de registro final que se quiere retornar de la consulta
	 *            realizada. Entero mayor o igual al parámetro from
	 * @return Una lista de DAOs de los UsuariosRol que se consultaron con los
	 *         parámetros enviados por el cliente
	 * @throws InvalidParameterException
	 *             Excepción lanzada cuando algunos de los parámetros de la url
	 *             tenía un error de sintáxis por lo que no pudo ser procesado
	 *             correctamente
	 */
	public List<UsuariosRolDTO> consultar(String filterBy, String orderBy, Integer from, Integer to)
			throws InvalidParameterException {
		// protected region Modifique el metodo consultar on begin
		logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);

		List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
		List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
		RangoConsulta rango = validarParametrosBloque(from, to);

		return convertirListaEntidadesADao(manejadorUsuariosRol.consultar(filtros, ordenamiento, rango));
		// protected region Modifique el metodo consultar end
	}

	/**
	 * Crea el usuariosRol que se pasa como parámetro en la base de datos.
	 * 
	 * @param usuariosRolDTO
	 *            El DAO de la entidad UsuariosRol a crear. Este se envía en el
	 *            cuerpo de la solicitud POST como un objeto JSON.
	 * @return La insntancia de UsuariosRol recién creado
	 */
	public UsuariosRolDTO crear(UsuariosRolDTO usuariosRolDTO) {
		// protected region Modifique el metodo crear on begin

		logService(this.getClass().getName(), "crear", usuariosRolDTO);

		UsuariosRol usuariosRol = new UsuariosRol();
		copiarPropiedades(usuariosRol, usuariosRolDTO);

		manejadorUsuariosRol.crear(usuariosRol);

		return usuariosRolDTO;
		// protected region Modifique el metodo crear end
	}

	/**
	 * Actualiza en la base de datos el usuariosRol que se pasa como parámetro.
	 * 
	 * @param usuariosRolDTO
	 *            El DAO de la entidad UsuariosRol a actualizar. Este se envía
	 *            en el cuerpo de la solicitud PUT como un objeto JSON.
	 * @return La instancia de la entidad UsuariosRol que ha sido actualizado
	 */
	public UsuariosRolDTO actualizar(UsuariosRolDTO usuariosRolDTO) {
		// protected region Modifique el metodo actualizar on begin

		logService(this.getClass().getName(), mesagge, usuariosRolDTO);

		UsuariosRol usuariosRol = manejadorUsuariosRol.buscar(usuariosRolDTO.getUsuariosRolPK());
		copiarPropiedades(usuariosRol, usuariosRolDTO);

		manejadorUsuariosRol.actualizar(usuariosRol);

		return usuariosRolDTO;
		// protected region Modifique el metodo actualizar end
	}

	/**
	 * Elimina el usuariosRol con el identificador que se pasa como parámetro.
	 * 
	 * @param rolId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad usuariosRol a eliminar
	 * @param usuarioId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad usuariosRol a eliminar
	 * @return El identificador del usuariosRol que ha sido eliminado
	 */
	public String eliminar(@QueryParam("rolId") BigDecimal rolId, @QueryParam("usuarioId") String usuarioId) {
		// protected region Modifique el metodo eliminar on begin

		logService(this.getClass().getName(), "eliminar", rolId, usuarioId);
		UsuariosRolPK usuariosRolPK = new UsuariosRolPK();
		usuariosRolPK.setRolId(rolId);
		usuariosRolPK.setUsuarioId(usuarioId);
		manejadorUsuariosRol.eliminarPorId(usuariosRolPK);

		StringBuilder valores = new StringBuilder();
		valores.append(String.valueOf(rolId));
		valores.append(String.valueOf(usuarioId));
		return valores.toString();
		// protected region Modifique el metodo eliminar end
	}

	public void desvincularUsuarioRol(@QueryParam("rolId") BigDecimal rolId, @QueryParam("usuarioId") String usuarioId, @QueryParam("aplicacionId") String aplicacionId, @QueryParam("motivoDesvinculacion") String motivoDesvinculacion){
		logService(this.getClass().getName(), mesagge, rolId, usuarioId);
		BigDecimal aplicacionIdDecimal = new BigDecimal(aplicacionId);
		// Buscar el UsuariosRol correspondiente a los parámetros de desvinculación
		List<UsuariosRol> usuariosRoles = manejadorUsuariosRol.buscarUsuariosRolXUsuarioApp(usuarioId, aplicacionIdDecimal);

		// Iterar sobre los UsuariosRol encontrados
		for (UsuariosRol usuariosRol : usuariosRoles) {
			// Verificar si el rol coincide con el rol a desvincular
			if (usuariosRol.getUsuariosRolPK().getRolId().equals(rolId)) {
				// Realizar la desvinculación
				usuariosRol.setEstadoVinculacion(Constantes.ESTADO_DESVINCULADO);
				usuariosRol.setFechaDesvinculacion(new Date());
				usuariosRol.setMotivoDesvinculacion(motivoDesvinculacion != null ? motivoDesvinculacion : "");
				manejadorUsuariosRol.actualizar(usuariosRol);
				break;
			}
		}
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
	 *            {@literal usuariosRolId>1&usuariosRolName:LIKE:juan}
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

		return String.valueOf(manejadorUsuariosRol.consultarTotalRegistros(filtros, rango));
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
	 *            {@literal usuariosRolId>1&usuariosRolName:LIKE:juan}
	 * @param orderBy
	 *            Cadena de caracteres con los parámetros de ordenamiento. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere ordenar, seguido por el símbolo '$' y posteriormente
	 *            por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
	 *            opcionales ya que si no se especifica por defecto se asume que
	 *            el ordenamiento es de forma Ascendente. Si se coloca más de un
	 *            parámetro debe ir separado por coma : ','. Ej. Una secuencia
	 *            de parámetros de ordenamiento puede ser: usuariosRolId$ASC,
	 *            usuariosRolName$DESC
	 * @param atributo
	 *            Nombre del atributo de la entidad UsuariosRol del cual se
	 *            quieren obtener los diferentes valores.
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

		return UtilOperaciones.convertirListaObjetosAString(
				manejadorUsuariosRol.consultarLista(filtros, ordenamiento, infoAgrupamiento));
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
		return UsuariosRol.contieneAtributo(nombreAtributo);
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
	protected UsuariosRolDTO instanciarDAO() {
		return new UsuariosRolDTO();
	}

	// protected region Use esta region para su implementacion de otros metodos
	// on begin

	// protected region Use esta region para su implementacion de otros metodos
	// end

	public List<UsuariosRolDTO> buscarUsuarioRolPorUsuarioApp(String userID, String clientID) {
		// protected region Modifique el metodo actualizar on begin

		List<UsuariosRolDTO> usuariosRolDTO = new ArrayList<>();
		logService(this.getClass().getName(), mesagge, userID);

		List<UsuariosRol> usuariosRol = manejadorUsuariosRol.buscarUsuariosRolXUsuarioApp(userID,
				new BigDecimal(clientID));

		for (UsuariosRol userRole : usuariosRol) {
			UsuariosRolDTO userRoleDTO = instanciarDAO();
			copiarPropiedades(userRoleDTO, userRole);
			usuariosRolDTO.add(userRoleDTO);
		}

		return usuariosRolDTO;
		// protected region Modifique el metodo actualizar end
	}

	public void crearManual(UsuarioRolEntity usuarioRolEntity) throws SeguridadException {
		logService(this.getClass().getName(), "crear", usuarioRolEntity);
		try {
			em.persist(usuarioRolEntity);
			em.flush();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.ERROR);
		}
	}

	public void crearManual(UsuariosRol usuarioRolEntity) throws SeguridadException {
		logService(this.getClass().getName(), "crear", usuarioRolEntity);
		try {
			em.persist(usuarioRolEntity);
			em.flush();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.ERROR);
		}
	}

	public void actualizarUsuarioRol(UsuarioRolEntity usuarioRolEntity) throws SeguridadException {
		logService(this.getClass().getName(), mesagge, usuarioRolEntity);
		try {
			em.merge(usuarioRolEntity);
			em.flush();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.ERROR);
		}
	}
	
	public List<UsuariosRolDTO> buscarUsuarioRolPorUsuario(String userID) {
		// protected region Modifique el metodo actualizar on begin
		logger.info("Inicia consulta usuario rol por usuario: "+userID);
		List<UsuariosRolDTO> usuariosRolDTO = new ArrayList<>();
		logService(this.getClass().getName(), mesagge, userID);

		List<UsuariosRol> usuariosRol = manejadorUsuariosRol.buscarUsuariosRolXUsuario(userID);
		for (UsuariosRol userRole : usuariosRol) {
			UsuariosRolDTO userRoleDTO = instanciarDAO();
			copiarPropiedades(userRoleDTO, userRole);
			usuariosRolDTO.add(userRoleDTO);
		}

		return usuariosRolDTO;
		// protected region Modifique el metodo actualizar end
	}

	// Inicio metodos HBT

	/**
	 * Metodo para agregar usuarios a un rol
	 * 
	 * @param usuariosDTOList
	 *            : Lista de usuarios a asignar rol
	 * @param rolId
	 *            : id del rol que se asignara a los usuarios
	 * @throws SIA3Exception
	 * @throws SeguridadException
	 */
	public void agregarUsuariosARol(List<UsuariosDTO> usuariosDTOList, BigDecimal rolId, String usuarioPeticion)
			throws SIA3Exception, SeguridadException {
		try {
			BigDecimal idAplicacion = BigDecimal.valueOf(0);
			String nombreRol = "";
			// recorrer lista de usuarios para asignar roles
			for (UsuariosDTO usuariosDTO : usuariosDTOList) {
				logger.info("Inicio metodo negocio agregarUsuariosARol con usuariosDTO =>" + usuariosDTO + " y rolId =>"
						+ rolId);
				validarUsuarioValido(usuariosDTO);
				if (rolId == null) {// Si el rol viene vacio
					logger.error("Debe indicar rol a asignar a los usuarios");
					throw new SIA3Exception(MessagesConstants.APP100087);
				}
				// Verificar que el rol exista
				List<Roles> rolesExistentes = manejadorRoles.buscarRolPorId(rolId);
				if ( isEmpty(rolesExistentes) ) {
					logger.error("Error en metodo eliminarAplicacion: NO EXISTE rol con el id:" + usuariosDTO.getUsuarioId());
					throw new SIA3Exception(MessagesConstants.APP100047);
				}
				for(Roles rol:rolesExistentes){
					nombreRol = rol.getNombre();
					idAplicacion = rol.getAplicacionId();
				}
				//Inicio Cambio solicitado despues de certificacion usuario
				//Fin Cambio solicitado despues de certificacion usuario
				UsuarioRolEntity usuariorRol = entidadRolAgregar(usuariosDTO);
				logger.info("Crear manual usuariorRol =>" + usuariorRol);
				// crear la asignacion usuario-rol
				agregarRolesUsuario(usuariorRol, nombreRol, idAplicacion);
				usuariorRol = entidadRolAgregar(usuariosDTO);
				agregarRolesUsuario(usuariorRol, Constantes.ROL_AUTENTICADOR, Constantes.ID_SIA3);
				usuariorRol = entidadRolAgregar(usuariosDTO);
				agregarRolesUsuario(usuariorRol, Constantes.ROL_GESTIONADOR, Constantes.ID_SIA3);
				//Registrar en auditoria el evento
				logger.info("Inicio registro auditoria evento USER_ASSIGNED");
				//Validar que venga el usuario que realiza la operacion
				if(usuariosDTO.getUsuarioModificacion()==null){
					logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
					throw new SIA3Exception(MessagesConstants.APP100120);
				}
				JsonObject detalle = new JsonObject();
				detalle.addProperty("descripcion", "Asigna rol");
				detalle.addProperty("rol", nombreRol);
				detalle.addProperty("usuario", usuariosDTO.getLogonName());
				auditoria.gestionarAuditoriaDetalle(Constantes.EVT_USER_ASSIGNED, usuarioPeticion, idAplicacion.toString(), detalle.toString());
				auditoria.gestionarAuditoriaConDetalleYCampoActivo(Constantes.EVT_USER_ADDED_ROL, usuarioPeticion, idAplicacion.toString(), detalle.toString(),null);
				logger.info("Fin registro auditoria evento USER_ASSIGNED");
			}
		} catch (SIA3Exception se) {
			logger.error("Error al agregar Usuarios A Rol. Verifique usuarios y rol suminstrados");
			throw new SIA3Exception(MessagesConstants.APP100088);
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de agregar Usuarios A Rol");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}

	}

	private void validarUsuarioValido(UsuariosDTO usuariosDTO) throws SIA3Exception {
		if (usuariosDTO == null) {// Si el DTO viene vacio
			logger.error("Debe indicar los usuarios a quienes asignar rol");
			throw new SIA3Exception(MessagesConstants.APP100086);
		}
		if (usuariosDTO.getUsuarioId() != null) {
			// Si el usuario viene diligenciado
			// Verificar que el usuario exista
			List<Usuario> usuariosExistentes = manejadorUsuarios
					.listarUsuariosPorId(new BigDecimal(usuariosDTO.getUsuarioId()));
			if (isEmpty(usuariosExistentes)) {
				logger.error("Error en metodo eliminarAplicacion: NO EXISTE usuario con el id:"
						+ usuariosDTO.getUsuarioId());
				throw new SIA3Exception(MessagesConstants.APP100072);
			}

		} else {// Si dentro del DTO el id de usuario viene vacio
			logger.error("El campo id de usuario es requerido para asignarle rol");
			throw new SIA3Exception(MessagesConstants.APP100090);
		}
	}

	private boolean isEmpty(List<?> list){
		return list == null || list.isEmpty();
	}

	public UsuarioRolEntity entidadRolAgregar(UsuariosDTO usuariosDTO){
		UsuarioRolEntity usuariorRol = new UsuarioRolEntity();
		usuariorRol.setUsuarioId(new BigDecimal(usuariosDTO.getUsuarioId()));
		usuariorRol.setEstadoVinculacion(Constantes.ESTADO_VINCULADO);
		usuariorRol.setFechaVinculacion(new Date());
		return usuariorRol;
	}

	public BigDecimal idRolNombre(String nombreRol){
		BigDecimal idRolXNombre = null;
		List<Roles> idRolAgregar = manejadorRoles.buscarRolPorNombre(nombreRol);
		for (Roles rol : idRolAgregar) {
			idRolXNombre = rol.getRolId();
		}
		return idRolXNombre;
	}

	public void agregarRolesUsuario(UsuarioRolEntity usuarioRolEntity, String nombreRol, BigDecimal aplicacionId) throws SeguridadException {
			boolean rolEncontrado = false;
			List<UsuarioRolDTO> usuarioRoles = manejadorRoles.getRolesUsuario(usuarioRolEntity.getUsuarioId().longValue());
			if (!usuarioRoles.isEmpty()){
				if (usuarioRoles.stream().anyMatch(rolesDTO -> rolesDTO.getNombre().equals(nombreRol) && rolesDTO.getAplicacion_id().equals(aplicacionId))) {
					rolEncontrado = true;
				}
				Optional<UsuarioRolDTO> rolExiste = usuarioRoles.stream()
						.filter(rolesDTO -> rolesDTO.getNombre().equals(nombreRol)
								&& rolesDTO.getAplicacion_id().equals(aplicacionId)
								&& rolesDTO.getEstado_vinculacion().equals(Constantes.ESTADO_DESVINCULADO))
						.findFirst();
				if (rolExiste.isPresent()) {
					usuarioRolEntity.setRolId(rolExiste.get().getRol_id());
					actualizarUsuarioRol(usuarioRolEntity);
				}
			}
			if (!rolEncontrado) {
				List<Roles> idRolAgregar = manejadorRoles.buscarRolPorNombre(nombreRol);
				for (Roles rol : idRolAgregar) {
					if(rol.getAplicacionId().equals(aplicacionId)){
						usuarioRolEntity.setRolId(rol.getRolId());
						break; // Sale del ciclo después de establecer el ID del primer rol encontrado
					}
				}
				crearManual(usuarioRolEntity);
			}
	}

	/**
	 * Metodo que elimina los usuariosRol relacionados a un rol dado
	 * 
	 * @param rolId
	 * @throws SIA3Exception
	 */
	public void eliminarUsuariosRolXRol(BigDecimal rolId) throws SIA3Exception {
		try {
			logger.info("Inicio metodo eliminarUsuariosRolXRol con rolId => " + rolId);
			//buscar usuariosRol que coincidan con el id rol dado
			List<UsuariosRol> listaUsuariosRol = manejadorUsuariosRol.buscarUsuariosRolXRol(rolId);
			logger.info("*****Recorrer listaUsuariosRol ----"+listaUsuariosRol);
			for (UsuariosRol usuariosRol : listaUsuariosRol) {
				logger.info("ciclo eliminar usuariosRol--->"+usuariosRol);
				eliminar(usuariosRol.getRoles().getRolId(),
						usuariosRol.getUsuarios().getUsuarioId()
						);
			}
			logger.info("Fin metodo eliminarUsuariosRolXRol");
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de eliminar UsuariosRol relacionadas con el rol id:"
					+ rolId + ". Mensaje:" + e);
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}
	
	/**
	 * Metodo que valida los usuariosRol relacionados a un rol dado
	 * 
	 * @param rolId
	 * @throws SIA3Exception
	 */
	public void validarUsuariosRolXRol(BigDecimal rolId) throws SIA3Exception {
		try {
			logger.info("Inicio metodo validarUsuariosRolXRol con rolId => " + rolId);
			//buscar usuariosRol que coincidan con el id rol dado
			List<UsuariosRol> listaUsuariosRol = manejadorUsuariosRol.buscarUsuariosRolXRol(rolId);
			logger.info("*****Recorrer listaUsuariosRol ----"+listaUsuariosRol);
			if(!listaUsuariosRol.isEmpty()){
				throw new SIA3Exception(MessagesConstants.APP100118);
			}
			logger.info("Fin metodo validarUsuariosRolXRol");
		}catch (SIA3Exception s3) {
			throw new SIA3Exception(MessagesConstants.APP100118);
	
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de validar UsuariosRol relacionadas con el rol id:"
					+ rolId + ". Mensaje:" + e);
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	// Fin metodos HBT
	
}
