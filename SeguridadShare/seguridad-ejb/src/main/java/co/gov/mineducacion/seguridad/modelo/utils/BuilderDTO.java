package co.gov.mineducacion.seguridad.modelo.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import co.gov.mineducacion.seguridad.modelo.entidades.*;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;
import co.gov.mineducacion.seguridad.modelo.dtos.*;

/**
 * Agregada por HBT Clase encargada de realizar las conversion de un pojo a una
 * entidad en particular
 *
 * @author Heinsohn
 *
 */
public class BuilderDTO {
	private BuilderDTO(){/* Recomendacion por sonar */}

	// log de la aplicacion para la clase BuilderDTO
	private static final Logger LOG = Logger.getLogger(NegocioMensaje.class.getName());

	/**
	 * Metodo que convierte una entidad de usuario a dto usuario
	 * 
	 * @param usuario
	 * @return
	 */
	public static UsuariosDTO toUsuarioDTO(Usuario usuario) {
		UsuariosDTO usuarioDTO = new UsuariosDTO();
		if (usuario != null) {
			usuarioDTO.setEstado(usuario.getEstado());
			usuarioDTO.setFechaCreacion(usuario.getFechaCreacion());
			usuarioDTO.setLogonName(usuario.getLogonName());
			usuarioDTO.setNuevoPass(usuario.getNuevoPass());
			usuarioDTO.setRuta(usuario.getRuta());
			usuarioDTO.setTipo(usuario.getTipo());
			usuarioDTO.setUltimaModificacion(usuario.getUltimaModificacion());
			usuarioDTO.setUsuarioId(usuario.getUsuarioId());
			usuarioDTO.setUsuarioModificacion(usuario.getUsuarioModificacion());
		}

		return usuarioDTO;

	}
	
	public static List<UsuariosDTO> toUsuarioDTOList(List<Usuario> usuarios) {
		List<UsuariosDTO> dtoList = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			dtoList.add(BuilderDTO.toUsuarioDTO(usuario));
		}
		return dtoList;
	}
	
	//aplicaciones

	/**
	 * Metodo que convierte una entidad de aplicacion a dto aplicacion
	 * 
	 * @param aplicacion
	 * @return
	 */
	public static AplicacionesDTO AplicacionDTO(Aplicaciones aplicacion) {
		AplicacionesDTO dto = new AplicacionesDTO();
		if (aplicacion != null) {
			dto.setAplicacionId(aplicacion.getAplicacionId());
			dto.setEstado(aplicacion.getEstado());
			dto.setFechaCreacion(aplicacion.getFechaCreacion());
			dto.setNombre(aplicacion.getNombre());
			dto.setUrlInicioExitoso(aplicacion.getUrlInicioExitoso());
			dto.setMinVigTokenActConstrasenia(aplicacion.getMinVigTokenActConstrasenia());
			dto.setMinutosVigenciaCodigo(aplicacion.getMinutosVigenciaCodigo());
			dto.setMinutosVigenciaToken(aplicacion.getMinutosVigenciaToken());
			dto.setUltimaModificacion(aplicacion.getUltimaModificacion());
			dto.setUsuarioModificacion(aplicacion.getUsuarioModificacion());
			dto.setRecibirNotificacion(aplicacion.getRecibirNotificacion());
		}

		return dto;

	}

	/**
	 * Metodo que cnvierte una lista de aplicaciones a dto de aplicaciones
	 * 
	 * @param aplicaciones
	 * @return
	 */
	public static List<AplicacionesDTO> AplicacionDTOList(List<Aplicaciones> aplicaciones) {
		List<AplicacionesDTO> dtoList = new ArrayList<>();
		for (Aplicaciones app : aplicaciones) {
			dtoList.add(BuilderDTO.AplicacionDTO(app));
		}

		return dtoList;

	}

	/**
	 * Metodo que convierte una lista de dto de aplicaciones a lista entidad
	 * aplicaciones
	 * 
	 * @param aplicacionDTO
	 * @return
	 */
	public static Aplicaciones toAplicacion(AplicacionesDTO aplicacionDTO) {
		Aplicaciones aplicacion = null;
		if (aplicacionDTO != null) {
			aplicacion = new Aplicaciones();
			aplicacion.setAplicacionId(aplicacionDTO.getAplicacionId());
			aplicacion.setEstado(aplicacionDTO.getEstado());
			aplicacion.setFechaCreacion(aplicacionDTO.getFechaCreacion());
			aplicacion.setNombre(aplicacionDTO.getNombre());
			aplicacion.setUrlInicioExitoso(aplicacionDTO.getUrlInicioExitoso());
			aplicacion.setMinVigTokenActConstrasenia(aplicacionDTO.getMinVigTokenActConstrasenia());
			aplicacion.setMinutosVigenciaCodigo(aplicacionDTO.getMinutosVigenciaCodigo());
			aplicacion.setMinutosVigenciaToken(aplicacionDTO.getMinutosVigenciaToken());
			aplicacion.setUltimaModificacion(aplicacionDTO.getUltimaModificacion());
			aplicacion.setUsuarioModificacion(aplicacionDTO.getUsuarioModificacion());
			aplicacion.setRecibirNotificacion(aplicacionDTO.getRecibirNotificacion());
		}
		return aplicacion;
	}

	/**
	 * metodo encargado de convertir una entidad en un pojo MensajeDTO
	 *
	 * @param mensaje
	 *            entidad de entrada
	 * @return MensajeDTO
	 */
	public static MensajeDTO toMensajeDTO(Mensaje mensaje) {
		LOG.info("Inicio toMensajeDTO con la entidad mensaje: " + mensaje);
		MensajeDTO msn = new MensajeDTO();
		if (mensaje != null) {
			msn.setId(mensaje.getId() != null ? mensaje.getId().toString() : "");
			String descripcion = (mensaje.getDescripcion() != null ? mensaje.getDescripcion() : "");
			msn.setCodigo(mensaje.getCodigo() != null ? mensaje.getCodigo() : "");
			msn.setDescripcion(descripcion);
			msn.setEstado(mensaje.getEstado() != null ? mensaje.getEstado() : "");
			msn.setNombre(mensaje.getNombre() != null ? mensaje.getNombre() : "");
			msn.setTipoMensaje(mensaje.getTipoMensaje() != null ? mensaje.getTipoMensaje() : "");
		}
		LOG.info("Fin toMensajeDTO con MensajeDTO: " + msn.toString());
		return msn;
	}

	/**
	 * metodo encargado de convertir un pojo MensajeDTO a una entidad MEnsaje
	 *
	 * @param dto
	 *            pojo MensajeDTO
	 * @return Entidad Mensaje
	 */
	public static Mensaje toMensajeEntity(MensajeDTO dto) {
		LOG.info("Inicio toMensajeEntity con el pojo mensajeDTO: " + dto);
		Mensaje msn = new Mensaje();
		if (dto != null) {
			msn.setId(dto.getId() != null ? Long.parseLong(dto.getId()) : null);
			msn.setCodigo(dto.getCodigo() != null ? dto.getCodigo() : null);
			msn.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion() : "");
			msn.setEstado(dto.getEstado() != null ? dto.getEstado() : "");
			msn.setNombre(dto.getNombre() != null ? dto.getNombre() : "");
			msn.setTipoMensaje(dto.getTipoMensaje() != null ? dto.getTipoMensaje() : "");
		}
		LOG.info("Fin toMensajeDTO con Mensaje: " + msn.toString());
		return msn;
	}

	/**
	 * metodo encargado de convertir una lista de tipo entidad Mensaje a una
	 * lista de tipo MensajeDTO
	 *
	 * @param mensajes
	 * @return
	 */
	public static List<MensajeDTO> toMensajeDTOList(List<Mensaje> mensajes) {
		LOG.info("Inicio toMensajeDTOList con lista de mensajes: " + mensajes.size());
		List<MensajeDTO> mensajesList = new ArrayList<>();
		for (Mensaje msnjs : mensajes) {
			mensajesList.add(BuilderDTO.toMensajeDTO(msnjs));
		}
		LOG.info("Fin toMensajeDTOList con lista de MensajeDTO: " + mensajesList.size());
		return mensajesList;
	}

	public static CatalogosDTO toCatalogoDTO(Catalogos catalogo) {
		LOG.info("Inicio toCatalogoDTO con la entidad catalogo: " + catalogo);
		CatalogosDTO catalogoDTO = new CatalogosDTO();
		if (catalogo != null) {
			catalogoDTO.setCatalogoId(catalogo.getCatalogoIdId() != null ? catalogo.getCatalogoIdId() : null);
			catalogoDTO.setDescripcion(catalogo.getDescripcion() != null ? catalogo.getDescripcion() : "");
			catalogoDTO.setNombre(catalogo.getNombre() != null ? catalogo.getNombre() : "");
			catalogoDTO.setTextoAyuda(catalogo.getTextoAyuda() != null ? catalogo.getTextoAyuda() : "");

		}
		LOG.info("Fin toCatalogoDTO con CatalogoDTO: " + catalogoDTO.toString());
		return catalogoDTO;
	}

	public static List<CatalogosDTO> toCatalogoDTOList(List<Catalogos> catalogos) {
		LOG.info("Inicio toCatalogoDTOList con lista de catalogos: " + catalogos.size());
		List<CatalogosDTO> catalogoDTOList = new ArrayList<>();
		for (Catalogos catalogo : catalogos) {
			catalogoDTOList.add(BuilderDTO.toCatalogoDTO(catalogo));
		}
		LOG.info("Fin toCatalogoDTOList con lista de CatalogoDTO: " + catalogoDTOList.size());
		return catalogoDTOList;
	}

	// Metodos para roles
	/**
	 * Metodo que convierte una entidad rol en un DTO rol
	 * 
	 * @param rol
	 * @return
	 */
	public static RolesDTO toRolDTO(Roles rol) {
		RolesDTO rolDTO = new RolesDTO();
		if (rol != null) {
			rolDTO.setRolId(rol.getRolId());
			rolDTO.setAplicacionId(rol.getAplicacionId());
			rolDTO.setEstado(rol.getEstado());
			rolDTO.setFechaCreacion(rol.getFechaCreacion());
			rolDTO.setNombre(rol.getNombre());
			rolDTO.setUltimaModificacion(rol.getUltimaModificacion());
			rolDTO.setUsuarioModificacion(rol.getUsuarioModificacion());
			rolDTO.setPath(rol.getPath());
		}
		return rolDTO;
	}

	/**
	 * Metodo que convierte una lista de roles en una lista DTO roles
	 * 
	 * @param roles
	 * @return
	 */
	public static List<RolesDTO> toRolDTOList(List<Roles> roles) {
		List<RolesDTO> dtoList = new ArrayList<>();
		for (Roles rol : roles) {
			dtoList.add(BuilderDTO.toRolDTO(rol));
		}
		return dtoList;
	}

	/**
	 * Metodo que convierte un dto de rol a entidad rol
	 * 
	 * @param rolDTO
	 * @return
	 */
	public static Roles toRol(RolesDTO rolDTO) {
		Roles rol = null;
		if (rolDTO != null) {
			rol = new Roles();
			rol.setRolId(rolDTO.getRolId());
			rol.setNombre(rolDTO.getNombre());
			rol.setEstado(rolDTO.getEstado());
			rol.setAplicacionId(rolDTO.getAplicacionId());
			rol.setFechaCreacion(rolDTO.getFechaCreacion());
			rol.setUltimaModificacion(rolDTO.getUltimaModificacion());
			rol.setUsuarioModificacion(rolDTO.getUsuarioModificacion());
			rol.setPath(rolDTO.getPath());
		}
		return rol;
	}

	// Metodos para operaciones
	/**
	 * Metodo que convierte una entidad operacion en un DTO operacion
	 * 
	 * @param operacion
	 * @return
	 */
	public static OperacionesDTO toOperacionDTO(Operaciones operacion) {
		OperacionesDTO operacionDTO = new OperacionesDTO();
		if (operacion != null) {
			operacionDTO.setAplicacionId(operacion.getAplicacionId());
			operacionDTO.setDescripcion(operacion.getDescripcion());
			operacionDTO.setEstado(operacion.getEstado());
			operacionDTO.setFechaCreacion(operacion.getFechaCreacion());
			operacionDTO.setNombreObjeto(operacion.getNombreObjeto());
			operacionDTO.setOpcionId(operacion.getOpcionId());
			operacionDTO.setOpcionPadre(operacion.getOpcionPadre());
			operacionDTO.setTipo(operacion.getTipo());
			operacionDTO.setUltimaModificacion(operacion.getUltimaModificacion());
			operacionDTO.setUsuarioModificacion(operacion.getUsuarioModificacion());
			operacionDTO.setListadoOperaciones(toOperacionDTOList(operacion.getOperacionesList()));
			operacionDTO.setAplicaciones(operacion.getAplicaciones());
		}
		return operacionDTO;
	}

	public static OperacionesDTO toOperacionPOrRolDTO(Operaciones operacion) {
		OperacionesDTO operacionDTO = new OperacionesDTO();
		if (operacion != null) {
			operacionDTO.setAplicacionId(operacion.getAplicacionId());
			operacionDTO.setOpcionId(operacion.getOpcionId());
			operacionDTO.setOpcionPadre(operacion.getOpcionPadre());
		}
		return operacionDTO;
	}

	/**
	 * Metodo que convierte una lista de operaciones en una lista DTO
	 * operaciones
	 * 
	 * @param operaciones
	 * @return
	 */
	public static List<OperacionesDTO> toOperacionDTOList(List<Operaciones> operaciones) {
		List<OperacionesDTO> dtoList = new ArrayList<>();
		for (Operaciones operacion : operaciones) {
			dtoList.add(BuilderDTO.toOperacionDTO(operacion));

		}

		return dtoList;
	}

	public static List<Operaciones> toOperacionList(List<OperacionesDTO> operacionesDTO) {
		List<Operaciones> dtoList = new ArrayList<>();
		for (OperacionesDTO operacionDTO : operacionesDTO) {
			dtoList.add(BuilderDTO.toOperacion(operacionDTO));
		}
		return dtoList;
	}

	/**
	 * Metodo que convierte un dto de operacion a entidad operacion
	 * 
	 * @param operacionDTO
	 * @return
	 */
	public static Operaciones toOperacion(OperacionesDTO operacionDTO) {
		Operaciones operacion = null;
		if (operacionDTO != null) {
			operacion = new Operaciones();
			LOG.info("setAplicacionId****");
			operacion.setAplicacionId(operacionDTO.getAplicacionId());
			LOG.info("setDescripcion****");
			operacion.setDescripcion(operacionDTO.getDescripcion());
			LOG.info("setEstado****");
			operacion.setEstado(operacionDTO.getEstado());
			LOG.info("setFechaCreacion****");
			operacion.setFechaCreacion(operacionDTO.getFechaCreacion());
			LOG.info("setNombreObjeto****");
			operacion.setNombreObjeto(operacionDTO.getNombreObjeto());
			LOG.info("setOpcionId****");
			operacion.setOpcionId(operacionDTO.getOpcionId());
			LOG.info("setOpcionPadre****");
			operacion.setOpcionPadre(operacionDTO.getOpcionPadre());
			LOG.info("setTipo****");
			operacion.setTipo(operacionDTO.getTipo());
			LOG.info("setUltimaModificacion****");
			operacion.setUltimaModificacion(operacionDTO.getUltimaModificacion());
			LOG.info("setUsuarioModificacion****");
			operacion.setUsuarioModificacion(operacionDTO.getUsuarioModificacion());
			LOG.info("setOperacionesList****");
			operacion.setOperacionesList(toOperacionList(operacionDTO.getListadoOperaciones()));
			LOG.info("setAplicaciones****");
			operacion.setAplicaciones(operacionDTO.getAplicaciones());
		}
		LOG.info("retorna****"+operacion);
		return operacion;
	}

	// Metodos para operaciones por rol
	/**
	 * Convierte una lista de Operaciones a una lista DTO de OperacionesPorRol
	 * @param operaciones
	 * @param idRol
	 * @return
	 */
	public static List<OperacionesPorRolDTO> toOperacionPorRolDTOList(List<Operaciones> operaciones, BigDecimal idRol) {
		List<OperacionesPorRolDTO> dtoList = new ArrayList<>();
		for (Operaciones operacion : operaciones) {
			dtoList.add(BuilderDTO.toOperacionPorRolDTO(operacion, idRol));

		}

		return dtoList;
	}

	/**
	 * Convierte una operacion en un DTO de operacion por rol
	 * @param operacion
	 * @param idRol
	 * @return
	 */
	public static OperacionesPorRolDTO toOperacionPorRolDTO(Operaciones operacion, BigDecimal idRol) {
		OperacionesPorRolDTO operacionPorRolDTO = new OperacionesPorRolDTO();
		if (operacion != null) {
			operacionPorRolDTO.setOperacionId(operacion.getOpcionId());
			operacionPorRolDTO.setRolId(idRol);
		}
		return operacionPorRolDTO;
	}
	
	//Metodos para auditoria
	public static BitacoraAuditoriaDTO toBitacoraAuditoriaDTO(BitacoraAuditoria bitacoraAuditoria) {
		BitacoraAuditoriaDTO bitacoraAuditoriaDTO = new BitacoraAuditoriaDTO();
		if (bitacoraAuditoria != null) {
			bitacoraAuditoriaDTO.setAplicacionId(bitacoraAuditoria.getAplicacionId());
			bitacoraAuditoriaDTO.setBitacoraId(bitacoraAuditoria.getBitacoraId());
			bitacoraAuditoriaDTO.setFechaEvento(bitacoraAuditoria.getFechaEvento());
			bitacoraAuditoriaDTO.setTipoEvento(bitacoraAuditoria.getTipoEvento());
			bitacoraAuditoriaDTO.setUsuarioId(bitacoraAuditoria.getUsuarioId());
			bitacoraAuditoriaDTO.setUsuarios(bitacoraAuditoria.getUsuarios());
			bitacoraAuditoriaDTO.setAplicaciones(bitacoraAuditoria.getAplicaciones());
			bitacoraAuditoriaDTO.setNombreUsuario(bitacoraAuditoria.getUsuarios().getLogonName());
			bitacoraAuditoriaDTO.setCatalogos(bitacoraAuditoria.getCatalogos());
			bitacoraAuditoriaDTO.setDetalle(bitacoraAuditoria.getDetalle());
			bitacoraAuditoriaDTO.setCampoDirectorio(bitacoraAuditoria.getCampoDirectorio());
		}
		return bitacoraAuditoriaDTO;
	}
	
	public static List<BitacoraAuditoriaDTO> toBitacoraAuditoriaDTOList(List<BitacoraAuditoria> bitacoraAuditoriaList) {
		List<BitacoraAuditoriaDTO> bitacoraAuditoriaDTOList = new ArrayList<>();
		for (BitacoraAuditoria bitacoraAuditoria : bitacoraAuditoriaList) {
			bitacoraAuditoriaDTOList.add(BuilderDTO.toBitacoraAuditoriaDTO(bitacoraAuditoria));
		}
		return bitacoraAuditoriaDTOList;
	}

}