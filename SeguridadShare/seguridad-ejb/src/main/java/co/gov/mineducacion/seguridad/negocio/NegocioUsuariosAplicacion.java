package co.gov.mineducacion.seguridad.negocio;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosAplicacionDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosAplicacion;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosAplicacionEntity;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosAplicacionPK;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorAplicaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuariosAplicacion;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;


@Stateless
public class NegocioUsuariosAplicacion extends NegocioAbstracto<UsuariosAplicacion, UsuariosAplicacionDTO> {

	@EJB
	private ManejadorUsuariosAplicacion manejadorUsuariosAplicacion;

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ManejadorUsuarios manejadorUsuarios;

	@EJB
	private ManejadorAplicaciones manejadorAplicaciones;

	@EJB
	private NegocioAplicaciones negocioAplicaciones;

	@EJB
	private NegocioBitacoraAuditoria auditoria;
	
	@EJB
	private NegocioUsuarios negocioUsuario;

	/**
	 * Variable estatica para imprimir logs...
	 */
	private static final Logger logger = Logger.getLogger(NegocioUsuariosAplicacion.class.getName());


	/**
	 * Elimina usuarioAplicacion con el identificador que se pasa como parámetro.
	 * 
	 * @param aplicacionId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad usuariosAplicacion a eliminar
	 * @param usuarioId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad usuariosRol a eliminar
	 * @return El identificador del usuariosAplicacion que ha sido eliminado
	 */
	public String eliminar(@QueryParam("aplicacionId") BigDecimal aplicacionId, @QueryParam("usuarioId") String usuarioId) {
		// protected region Modifique el metodo eliminar on begin

		logService(this.getClass().getName(), "eliminar", aplicacionId, usuarioId);
		UsuariosAplicacionPK usuariosAplicacionPK = new UsuariosAplicacionPK();
		usuariosAplicacionPK.setAplicacionId(aplicacionId);
		usuariosAplicacionPK.setUsuarioId(usuarioId);
		manejadorUsuariosAplicacion.eliminarPorId(usuariosAplicacionPK);

		StringBuilder valores = new StringBuilder();
		valores.append(String.valueOf(aplicacionId));
		valores.append(String.valueOf(usuarioId));
		return valores.toString();
		// protected region Modifique el metodo eliminar end
	}


	/**
	 * Crea  la relación entre el usuario y la aplicación que se recibe por parámetro
	 * @param usuariosAplicacionEntity
	 * @throws SeguridadException
	 */
	public void crearManual(UsuariosAplicacionEntity usuariosAplicacionEntity) throws SeguridadException {
		logService(this.getClass().getName(), "crear", usuariosAplicacionEntity);
		try {
			em.persist(usuariosAplicacionEntity);
			em.flush();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.ERROR);
		}
	}
	/**
	 * Metodo para agregar usuarios a una aplicacion
	 * 
	 * @param usuariosDTOList
	 *            : Lista de usuarios a asignar a la aplicacion
	 * @param rolId
	 *            : id de la aplicacion
	 * @throws SIA3Exception
	 * @throws SeguridadException
	 */
	public void agregarUsuariosApp(List<UsuariosDTO> usuariosDTOList, BigDecimal aplicacionId)
			throws SIA3Exception, SeguridadException {
		List<UsuariosDTO> usuarioDTOList = null;
		try {
			if(usuariosDTOList.isEmpty()){
				throw new SIA3Exception(MessagesConstants.APP100113);
			}else{
				usuarioDTOList = negocioUsuario.getUsuariosPorApp((Constantes.HBT_ID_APP_SEGURIDAD), aplicacionId);
				if(!usuarioDTOList.isEmpty()){
					for(int i=0; i<usuarioDTOList.size(); i++){
						// eliminar asignacion de la app antes de crearla
						eliminar(aplicacionId, usuarioDTOList.get(i).getUsuarioId());
					}
				}
			}
			
			
			// recorrer lista de usuarios para asignar aplicaciones
			for (UsuariosDTO usuariosDTO : usuariosDTOList) {
				String nombreAplicacion = "";
				logger.info("Inicio metodo negocio agregarUsuariosApp con usuariosDTO =>" + usuariosDTO + " y aplicacionId =>"
						+ aplicacionId);
				UsuariosAplicacionEntity usuariosAplicacion = new UsuariosAplicacionEntity();
				if (usuariosDTO == null) {// Si el DTO viene vacio
					logger.error("Debe indicar los usuarios a quienes asignar la aplicacion");
					throw new SIA3Exception(MessagesConstants.APP100086);
				} else if (usuariosDTO.getUsuarioId() != null) {
					// Si el usuario viene diligenciado
					// Verificar que el usuario exista
					List<Usuario> usuariosExistentes = manejadorUsuarios
							.listarUsuariosPorId(new BigDecimal(usuariosDTO.getUsuarioId()));
					if (usuariosExistentes == null || usuariosExistentes.isEmpty()) {
						logger.error("Error en metodo eliminarAplicacion: NO EXISTE usuario con el id:"
								+ usuariosDTO.getUsuarioId());
						throw new SIA3Exception(MessagesConstants.APP100072);
					}

				} else {// Si dentro del DTO el id de usuario viene vacio
					logger.error("El campo id de usuario es requerido para asignar la aplicacion");
					throw new SIA3Exception(MessagesConstants.APP100090);
				}
				if (aplicacionId == null) {// Si el rol viene vacio
					logger.error("Debe indicar la aplicacion asignar a los usuarios");
					throw new SIA3Exception(MessagesConstants.APP100087);
				}else{
					// Verificar que la app exista
					List<Aplicaciones> appExistentes = manejadorAplicaciones.buscarAplicacionPorId(aplicacionId);
					if (appExistentes == null || appExistentes.isEmpty()) {
						logger.error("Error en metodo eliminarAplicacion: NO EXISTE aplicacion con el id:"
								+ usuariosDTO.getUsuarioId());
						throw new SIA3Exception(MessagesConstants.APP100047);
					}else{
						for(Aplicaciones aplicacion:appExistentes){
							nombreAplicacion = aplicacion.getNombre();
						}
					}
				}
				usuariosAplicacion.setAplicacionId(aplicacionId);
				usuariosAplicacion.setUsuarioId(new BigDecimal(usuariosDTO.getUsuarioId()));
				// crear la asignacion usuario-aplicacion
				logger.info("Crear manual usuariorApp =>" + usuariosAplicacion);
				crearManual(usuariosAplicacion);
				//Registrar en auditoria el evento
				logger.info("Inicio registro auditoria evento USER_ASSIGNED_APP");
				//Validar que venga el usuario que realiza la operacion
				if(usuariosDTO.getUsuarioModificacion()==null){
					logger.error("Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
					throw new SIA3Exception(MessagesConstants.APP100120);
				}
				auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_ASSIGNED_APP), usuariosDTO.getUsuarioModificacion(), Constantes.HBT_ID_APP_SEGURIDAD.toString(), "Asigna usuario:" + usuariosDTO.getLogonName() + " a aplicacion:" + nombreAplicacion);
				logger.info("Fin registro auditoria evento USER_ASSIGNED_APP");
			}
		} catch (SIA3Exception se) {
			if(!se.getMessage().equals(MessagesConstants.APP100098)){
				logger.error("Error al agregar Usuarios A Rol. Verifique usuarios y rol suminstrados");
				throw new SIA3Exception(MessagesConstants.APP100088);
			}
			
		} catch (Exception e) {
			logger.error("Error inesperado al tratar de agregar Usuarios A Rol");
			throw new SIA3Exception(MessagesConstants.APP000003);
		}
	}

	@Override
	protected boolean entidadContieneAtributo(String nombreAtributo) {
		return UsuariosAplicacion.contieneAtributo(nombreAtributo);
	}


	@Override
	protected Logger getLogger() {
		return logger;
	}


	@Override
	protected UsuariosAplicacionDTO instanciarDAO() {
		return new UsuariosAplicacionDTO();
	}
	
	/**
	 * Metodo para eliminar usuarios de una aplicacion dada
	 * @param aplicacionId
	 * @throws SIA3Exception
	 */
	public void validarUsuarioXAplicacion(BigDecimal aplicacionId) throws SIA3Exception{
		logger.info("Inicio metodo validarUsuarioXAplicacion con aplicacionId =>" + aplicacionId);
		try{
			//obtener usuarios relacionados con la aplicacion			
			List<UsuariosAplicacion> usuariosList = manejadorUsuariosAplicacion.getUsuariosPorAplicacion(aplicacionId);
			logger.info("obtiene lista de usuarios relacionados, UsuariosList ->"+ usuariosList);
			if(!usuariosList.isEmpty()){
				throw new SIA3Exception(MessagesConstants.APP100119); 
			}
			
		}catch (SIA3Exception e) {
			logger.error("Error en metodo validarUsuarioXAplicacion:" + e);
			throw new SIA3Exception(MessagesConstants.APP100033);
		}catch(Exception e){
    		logger.error("Error inesperado al tratar de eliminar usuario en metodo validarUsuarioXAplicacion");
			 throw new SIA3Exception(MessagesConstants.APP000003); 
    	}
	}
	
	
	/**
	 * Elimina el usuarios con el identificador que se pasa como parámetro.
	 * 
	 * @param aplicacionId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad usuarios a eliminar
	 * @param usuarioId
	 *            Valor del atributo del identificador de la instancia de la
	 *            entidad usuariosRol a eliminar
	 * @return El identificador del usuario que ha sido eliminado
	 */
	public String eliminarUsuario(@QueryParam("aplicacionId") BigDecimal aplicacionId, @QueryParam("usuarioId") String usuarioId) {
		// protected region Modifique el metodo eliminar on begin

		logService(this.getClass().getName(), "eliminar", aplicacionId, usuarioId);
		UsuariosAplicacionPK usuariosAplicacionPK = new UsuariosAplicacionPK();
		usuariosAplicacionPK.setAplicacionId(aplicacionId);
		usuariosAplicacionPK.setUsuarioId(usuarioId);
		manejadorUsuariosAplicacion.eliminarPorId(usuariosAplicacionPK);

		StringBuilder valores = new StringBuilder();
		valores.append(String.valueOf(aplicacionId));
		valores.append(String.valueOf(usuarioId));
		return valores.toString();
		// protected region Modifique el metodo eliminar end
	}
}
