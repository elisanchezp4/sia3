package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesPorRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesRolDTO;

import java.util.List;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;

import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRol;
import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRolPK;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperacionesRol;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;

import java.math.BigDecimal;

// protected region Incluya importaciones adicionales en esta seccion on begin

// protected region Incluya importaciones adicionales en esta seccion end

/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad OperacionesRol
 * 
 * @author jsoto
 */
@Stateless
public class NegocioOperacionesRol extends NegocioAbstracto<OperacionesRol, OperacionesRolDTO> {

	@EJB
	private ManejadorOperacionesRol manejadorOperacionesRol;

	// Inicio variables HBT
	@EJB
	private NegocioRoles negocioRol;

	@EJB
	private NegocioOperaciones negocioOperacion;
	
	@EJB
	private ManejadorOperaciones manejadorOperaciones;
	// Fin variables HBT

	/**
	 * Variable estatica para imprimir logs...
	 */
	private static final Logger logger = Logger.getLogger(NegocioOperacionesRol.class.getName());

	// protected region Declare atributos adicionales en esta seccion on begin

	// protected region Declare atributos adicionales en esta seccion end

	/**
	 * Realiza un consulta en la entidad OperacionesRol aplicando los filtros,
	 * el ordenamiento, y el rango (from y to) que se pasan como parámetro. Los
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
	 *            {@literal operacionesRolId>1&operacionesRolName:LIKE:juan}
	 * @param orderBy
	 *            Cadena de caracteres con los parámetros de ordenamiento. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere ordenar, seguido por el símbolo '$' y posteriormente
	 *            por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
	 *            opcionales ya que si no se especifica por defecto se asume que
	 *            el ordenamiento es de forma Ascendente. Si se coloca más de un
	 *            parámetro debe ir separado por coma : ','. Ej. Una secuencia
	 *            de parámetros de ordenamiento puede ser: operacionesRolId$ASC,
	 *            operacionesRolName$DESC
	 * @param from
	 *            Número de registro inicial que se quiere retornar de la
	 *            consulta realizada. Entero mayor o igual a 0
	 * @param to
	 *            Número de registro final que se quiere retornar de la consulta
	 *            realizada. Entero mayor o igual al parámetro from
	 * @return Una lista de DAOs de los OperacionesRol que se consultaron con
	 *         los parámetros enviados por el cliente
	 * @throws InvalidParameterException
	 *             Excepción lanzada cuando algunos de los parámetros de la url
	 *             tenía un error de sintáxis por lo que no pudo ser procesado
	 *             correctamente
	 */
	public List<OperacionesRolDTO> consultar(String filterBy, String orderBy, Integer from, Integer to)
			throws InvalidParameterException{
		// protected region Modifique el metodo consultar on begin
		logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);

		List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
		List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
		RangoConsulta rango = validarParametrosBloque(from, to);

		return convertirListaEntidadesADao(manejadorOperacionesRol.consultar(filtros, ordenamiento, rango));
		// protected region Modifique el metodo consultar end
	}

	/**
	 * Crea el operacionesRol que se pasa como parámetro en la base de datos.
	 * 
	 * @param operacionesRolDTO
	 *            El DAO de la entidad OperacionesRol a crear. Este se envía en
	 *            el cuerpo de la solicitud POST como un objeto JSON.
	 * @return La insntancia de OperacionesRol recién creado
	 */
	public OperacionesRolDTO crear(OperacionesRolDTO operacionesRolDTO) {
		// protected region Modifique el metodo crear on begin
		logService(this.getClass().getName(), "crear", operacionesRolDTO);
		OperacionesRol operacionesRol = new OperacionesRol();
		copiarPropiedades(operacionesRol, operacionesRolDTO);
		manejadorOperacionesRol.crear(operacionesRol);
		return operacionesRolDTO;
		// protected region Modifique el metodo crear end
	}

	/**
	 * Actualiza en la base de datos el operacionesRol que se pasa como
	 * parámetro.
	 * 
	 * @param operacionesRolDTO
	 *            El DAO de la entidad OperacionesRol a actualizar. Este se
	 *            envía en el cuerpo de la solicitud PUT como un objeto JSON.
	 * @return La instancia de la entidad OperacionesRol que ha sido actualizado
	 */
	public OperacionesRolDTO actualizar(OperacionesRolDTO operacionesRolDTO) {
		// protected region Modifique el metodo actualizar on begin

		logService(this.getClass().getName(), "actualizar", operacionesRolDTO);

		OperacionesRol operacionesRol = manejadorOperacionesRol.buscar(operacionesRolDTO.getOperacionesRolPK());
		copiarPropiedades(operacionesRol, operacionesRolDTO);

		manejadorOperacionesRol.actualizar(operacionesRol);

		return operacionesRolDTO;
		// protected region Modifique el metodo actualizar end
	}

	/**
	 * Elimina el operacionesRol con el identificador que se pasa como
	 * parámetro.
	 * 
	 * @param rolId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad operacionesRol a eliminar
	 * @param opcionId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad operacionesRol a eliminar
	 * @return El identificador del operacionesRol que ha sido eliminado
	 */
	public String eliminar(@QueryParam("rolId") BigDecimal rolId, @QueryParam("opcionId") String opcionId) {
		// protected region Modifique el metodo eliminar on begin
		logger.info("entra a metodo eliminar");
		logService(this.getClass().getName(), "eliminar", rolId, opcionId);
		OperacionesRolPK operacionesRolPK = new OperacionesRolPK();
		operacionesRolPK.setRolId(rolId);
		operacionesRolPK.setOpcionId(opcionId);
		manejadorOperacionesRol.eliminarPorId(operacionesRolPK);

		StringBuilder valores = new StringBuilder();
		valores.append(String.valueOf(rolId));
		valores.append(String.valueOf(opcionId));
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
	 *            {@literal operacionesRolId>1&operacionesRolName:LIKE:juan}
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

		return String.valueOf(manejadorOperacionesRol.consultarTotalRegistros(filtros, rango));
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
	 *            {@literal operacionesRolId>1&operacionesRolName:LIKE:juan}
	 * @param orderBy
	 *            Cadena de caracteres con los parámetros de ordenamiento. Cada
	 *            parámetro está compuesto por el nombre del campo por el que se
	 *            quiere ordenar, seguido por el símbolo '$' y posteriormente
	 *            por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
	 *            opcionales ya que si no se especifica por defecto se asume que
	 *            el ordenamiento es de forma Ascendente. Si se coloca más de un
	 *            parámetro debe ir separado por coma : ','. Ej. Una secuencia
	 *            de parámetros de ordenamiento puede ser: operacionesRolId$ASC,
	 *            operacionesRolName$DESC
	 * @param atributo
	 *            Nombre del atributo de la entidad OperacionesRol del cual se
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
				manejadorOperacionesRol.consultarLista(filtros, ordenamiento, infoAgrupamiento));
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
		return OperacionesRol.contieneAtributo(nombreAtributo);
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
	protected OperacionesRolDTO instanciarDAO() {
		return new OperacionesRolDTO();
	}

	// protected region Use esta region para su implementacion de otros metodos
	// on begin

	// protected region Use esta region para su implementacion de otros metodos
	// end

	// Inicio metodos HBT
	/**
	 * Metodo que convierte la lista de idOperacion y rol que viene de la vista
	 * a un DTO completo de OperacionesRolDTO
	 * 
	 * @param operacionesPorRolDTO
	 * @return
	 * @throws SIA3Exception
	 */
	public OperacionesRolDTO convertirOperacionPorRolDTO(OperacionesPorRolDTO operacionesPorRolDTO)
			throws SIA3Exception {
		try {
			logger.info("Inicio convertirOperacionPorRolDTO ");
			OperacionesRolDTO operacionesRolDTO = new OperacionesRolDTO();
			if (operacionesPorRolDTO != null) {
				//validar que no venga vacio el id del rol
				if(operacionesPorRolDTO.getRolId()==null){
					logger.error("El id del rol no puede venir vacio");//*
					throw new SIA3Exception(MessagesConstants.APP100108);
				}
				//validar que no venga vacio el id de la operacion
				if(operacionesPorRolDTO.getOperacionId()==null){
					logger.error("El id de la operacion no puede venir vacio");//*
					throw new SIA3Exception(MessagesConstants.APP100109);
				}
				operacionesRolDTO.setRoles(negocioRol.consultarRolPorId(operacionesPorRolDTO.getRolId()));
				if (operacionesRolDTO.getRoles()==null) {
					logger.error("No existe rol con id: " + operacionesPorRolDTO.getRolId());
					throw new SIA3Exception(MessagesConstants.APP100047);
				}
				operacionesRolDTO.setOperaciones(
						negocioOperacion.consultarOperacionPorId(operacionesPorRolDTO.getOperacionId()));
				if (operacionesRolDTO.getOperaciones()==null) {
					logger.error("No existe operacion con id: " + operacionesPorRolDTO.getOperacionId());
					throw new SIA3Exception(MessagesConstants.APP100048);
				}
				OperacionesRolPK operacionesRolPK = new OperacionesRolPK();
				operacionesRolPK.setOpcionId(operacionesRolDTO.getOperaciones().getOpcionId().toString());
				operacionesRolPK.setRolId(operacionesRolDTO.getRoles().getRolId());
				operacionesRolDTO.setOperacionesRolPK(operacionesRolPK);

			}else{
				logger.error("El DTO de OperacionesRol no tiene datos. Verifique que venga con informacion.");
				throw new SIA3Exception(MessagesConstants.APP100110);//*
			}
			logger.info("Fin convertirOperacionPorRolDTO. Devuelve DTO: " + operacionesRolDTO);
			return operacionesRolDTO;
		} catch (SIA3Exception se) {
			logger.error("Error al convertir convertirOperacionPorRolDTO -->" + se.getMessage());
			throw new SIA3Exception(se.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado en metodo convertirOperacionPorRolDTO:" + e);
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metodo que asocia las operaciones a un rol segun la lista
	 * idOperacion-idRol recibida del servicio
	 * 
	 * @param operacionesPorRolDTOList
	 * @throws SIA3Exception
	 */
	public void crearOperacionPorRol(List<OperacionesPorRolDTO> operacionesPorRolDTOList) throws SIA3Exception {
		try {
			logger.info("Inicio crearOperacionPorRol con lista operacionesPorRolDTO=> " + operacionesPorRolDTOList);
			// antes de crear elimina los permisos del rol
			borrarlistaOperaciones(operacionesPorRolDTOList);
			//Recorrer lista para crear operacionesRol
			for (OperacionesPorRolDTO operacionesPorRolDTO : operacionesPorRolDTOList) {
				OperacionesRolDTO operacionesRolDTO = new OperacionesRolDTO();
				// convierte la lista recibida en un DTO operacionesRol
				operacionesRolDTO = convertirOperacionPorRolDTO(operacionesPorRolDTO);
				logger.info("empieza a crear operacionesrol con operacionesRol  opcionid: " + " "
						+ operacionesRolDTO.getOperaciones().getOpcionId() + " y RolId: "
						+ operacionesRolDTO.getRoles().getRolId());
				logger.info("Envia DTO para crear operacionesRolDTO->" + operacionesRolDTO);				
				//crear las operaciones con sus padres
				logger.info("Llamar a metodo que busca operaciones padre");
				List<Operaciones> listaOperacionesPadre = manejadorOperaciones.buscarOperacionesPadres(operacionesRolDTO.getOperaciones().getOpcionId());
				logger.info("Recorrer Lista de  operaciones padre ->" + listaOperacionesPadre);
				for(Operaciones operacion : listaOperacionesPadre){
					logger.info("Recorre operacion -> " + operacion);
					//verificar que no exista la relacion antes
					if(buscarOperacionRolPorOperacionAndRol(operacion.getOpcionId().toString(), operacionesRolDTO.getRoles().getRolId().toString()).isEmpty()){
						logger.info("*****Crear nuevo registro OperacionRol para opcionId-----" + operacion.getOpcionId() + " con rolId " + operacionesRolDTO.getRoles().getRolId());	
						//si no existe se crea operacionRol
						OperacionesRolDTO operacionesRolDTOPadre = new OperacionesRolDTO();
						operacionesRolDTOPadre.setOperaciones(operacion);
						operacionesRolDTOPadre.setRoles(operacionesRolDTO.getRoles());
						logger.info("Nuevo operacionesDTO padre ->" + operacionesRolDTOPadre);
						OperacionesRolPK operacionesRolPKPadre = new OperacionesRolPK();
						operacionesRolPKPadre.setOpcionId(operacionesRolDTOPadre.getOperaciones().getOpcionId().toString());
						operacionesRolPKPadre.setRolId(operacionesRolDTOPadre.getRoles().getRolId());
						operacionesRolDTOPadre.setOperacionesRolPK(operacionesRolPKPadre);
						crear(operacionesRolDTOPadre);
					}					
				}
			}
			logger.info("Fin crearOperacionPorRol ");
		} catch (SIA3Exception e) {
			logger.error("Error al crear OperacionRol --> "+e.getMessage());
			throw new SIA3Exception(e.getMessage());
		} catch (Exception e) {
			logger.error("Error inesperado en metodo crearOperacionPorRol:" + e);
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	/**
	 * Metodo que busca las relaciones Operacion-Rol existentes para una
	 * operacion dada
	 * 
	 * @param operacionId
	 * @return
	 * @throws Exception
	 */
	public List<OperacionesRol> buscarOperacionRolPorOperacion(String operacionId) throws Exception {
		try {
			logger.info("Inicio buscarOperacionRolPorOperacion con operacionId:" + operacionId);
			List<OperacionesRol> operacionesRol = manejadorOperacionesRol.buscarOperacionesRolXOperacion(operacionId);
			logger.info("Fin buscarOperacionRolPorOperacion. Devuelve => " + operacionesRol);
			return operacionesRol;
		} catch (Exception e) {
			logger.error("Error inesperado en metodo buscarOperacionRolPorOperacion:" + e);
			throw new SIA3Exception(MessagesConstants.APP100084);
		}
	}

	/**
	 * Metodo que elimina operacionesRol recibidas por parametro
	 * 
	 * @param operacionesRolList
	 * @throws SIA3Exception
	 */
	public void eliminarOperacionRolPorOperacion(List<OperacionesRol> operacionesRolList) throws SIA3Exception {
		try {
			logger.info("Inicio eliminarOperacionRolPorOperacion con lista operacionesRolList=> " + operacionesRolList);
			for (OperacionesRol operacionesRol : operacionesRolList) {
				logger.info("se elimina operacionesRol con rolId" + operacionesRol.getRoles().getRolId()
						+ " y opcionId:" + operacionesRol.getOperaciones().getOpcionId());
				eliminar(operacionesRol.getRoles().getRolId(),
						operacionesRol.getOperaciones().getOpcionId().toString());
			}
			logger.info("Fin eliminarOperacionRolPorOperacion ");
		} catch (Exception e) {
			logger.error("Error inesperado en metodo eliminarOperacionRolPorOperacion:" + e.getMessage());
			throw new SIA3Exception(MessagesConstants.APP100058);
		}
	}
	
	/**
	 * Metodo que elimina OperacionesRol de un rol dado
	 * 
	 * @param rolId
	 * @throws SIA3Exception
	 */
	public void eliminarOperacionesRolXRol(BigDecimal rolId) throws SIA3Exception {
		try {
			logger.info("Inicio metodo eliminarOperacionesRolXRol con rolId => " + rolId);
			//buscar las operaciones que coincidan con la aplicacion dada
			List<OperacionesRol> listaOperacionesRol = manejadorOperacionesRol.buscarOperacionesRolXRol(rolId);
			logger.info("*****Recorrer listaOperacionesRol ----"+listaOperacionesRol);
			for (OperacionesRol operacionesRol : listaOperacionesRol) {
				logger.info("ciclo eliminar operacionRol--->"+operacionesRol);
				eliminar(operacionesRol.getRoles().getRolId(),
						operacionesRol.getOperaciones().getOpcionId().toString());
			}
			logger.info("Fin metodo eliminarOperacionesRolXRol");
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de eliminar OperacionesRol relacionadas con el rol id:"
					+ rolId + ". Mensaje:" + e);
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}
	
	/**
	 * Metodo que valida OperacionesRol de un rol dado
	 * 
	 * @param rolId
	 * @throws SIA3Exception
	 */
	public void validarOperacionesRolXRol(BigDecimal rolId) throws SIA3Exception {
		try {
			logger.info("Inicio metodo validarOperacionesRolXRol con rolId => " + rolId);
			//buscar las operaciones que coincidan con la aplicacion dada
			List<OperacionesRol> listaOperacionesRol = manejadorOperacionesRol.buscarOperacionesRolXRol(rolId);
			logger.info("*****Recorrer listaOperacionesRol ----"+listaOperacionesRol);
			if(!listaOperacionesRol.isEmpty()){
				throw new SIA3Exception(MessagesConstants.APP100118);
			}
			logger.info("Fin metodo validarOperacionesRolXRol");
		}catch (SIA3Exception s3) {
			throw new SIA3Exception(MessagesConstants.APP100118);
		} 
		catch (Exception e) {
			logger.error("Error inesperado al tratar de validar OperacionesRol relacionadas con el rol id:"
					+ rolId + ". Mensaje:" + e);
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}
	
	/**
	 * Metodo que obtiene operacionesRol que coincida con id opcion y rol dado
	 * @param operacionId
	 * @param rolId
	 * @return
	 * @throws Exception
	 */
	public List<OperacionesRol> buscarOperacionRolPorOperacionAndRol(String operacionId, String rolId) throws Exception {
		try {
			logger.info("Inicio buscarOperacionRolPorOperacionAndRol con operacionId:" + operacionId +" y rolId" + rolId);
			List<OperacionesRol> operacionesRol = manejadorOperacionesRol.buscarOperacionesRolXOperacionAndRol(operacionId, rolId);
			logger.info("Fin buscarOperacionRolPorOperacionAndRol. Devuelve => " + operacionesRol);
			return operacionesRol;
		} catch (Exception e) {
			logger.error("Error inesperado en metodo buscarOperacionRolPorOperacionAndRol:" + e);
			throw new SIA3Exception(MessagesConstants.APP100084);
		}
	}
	
	/**
	 * Metodo que borra todo un listado de operaciones asociadas a un rol
	 * @param operacionesPorRolDTOList
	 * @throws SIA3Exception
	 */
	public void borrarlistaOperaciones(List<OperacionesPorRolDTO> operacionesPorRolDTOList) throws SIA3Exception{
		try{
			logger.info("Inicio metodo borrarlistaOperaciones con operacionesPorRolDTOList -> " + operacionesPorRolDTOList);
			for(OperacionesPorRolDTO operacionesPorRolDTOBorrar : operacionesPorRolDTOList){
				eliminarOperacionesRolXRol(operacionesPorRolDTOBorrar.getRolId());
			}
			logger.info("Fin metodo borrarlistaOperaciones.");
		}catch(SIA3Exception se){
			logger.error("Error al borrar listaOperaciones:" + se);
			throw new SIA3Exception(MessagesConstants.APP100112);
		}catch (Exception e) {
			logger.error("Error inesperado en metodo buscarOperacionRolPorOperacionAndRol:" + e);
			throw new SIA3Exception(MessagesConstants.APP100003);
		}
	}

	// Fin metodos HBT

}
