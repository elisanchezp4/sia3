package co.gov.mineducacion.utha.seguridad.web.servicio.utils.dto;

import java.util.ArrayList;

import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.InformacionPermisosDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.OpcionDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.RolesPermisosDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.UsuarioDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.HeaderDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.InformacionUsuarioDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.PeticionAutenticacionDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.PeticionConsultaUsuarioRol;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.PeticionCrearUsuarioExternoDTO;
import co.gov.mineducacion.utha.seguridad.web.servicio.dto.entrada.PeticionInactivarUsuario;

/**
 * Contiene utilidades para convertir un objeto recibido+
 * de los servicio web a objetos DTOs.
 * @author Asesoftware - Javier Estévez
 *
 */
public class UtilsDTO {
	
	private static void validarHeader(HeaderDTO header)throws SeguridadException{
		if(header == null 
		|| header.getApiKey() == null
		|| header.getApiKey().trim().isEmpty()
		|| header.getUserId() == null){
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
		
	}
	
	private static void validarNull(Object object)throws SeguridadException{
		if(object == null){
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
	
	private static void validarInformacionUsuario(InformacionUsuarioDTO infUsr)throws SeguridadException{
		if(infUsr == null 
				|| infUsr.getApellidos() == null
				|| infUsr.getApellidos().trim().isEmpty()
				|| infUsr.getNombres() == null
				|| infUsr.getNombres().trim().isEmpty()
				|| infUsr.getNumeroIdentificacion() == null
				|| infUsr.getTipo() == null
				|| infUsr.getTipo().trim().isEmpty()
				|| infUsr.getTipoIdentificacion() == null
				|| infUsr.getTipoIdentificacion().trim().isEmpty()
				|| infUsr.getEmail() == null
				|| infUsr.getEmail().trim().isEmpty()
				|| infUsr.getNombreUsuario() == null
				|| infUsr.getNombreUsuario().trim().isEmpty()){
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
		
		if(!infUsr.getTipo().equals(Constantes.TIPO_USUARIO_EXTERNO) ){
			throw new SeguridadException(SeguridadException.ID_MSG_USUARIO_NO_EXTERNO_SIA3);
		}
	}
	
	public static void validarPeticionAutenticacion(PeticionAutenticacionDTO peticion)throws SeguridadException{
		
		validarNull(peticion);
		validarHeader(peticion.getHeader());
		if( peticion.getTokenAcceso() == null
				|| peticion.getTokenAcceso().trim().isEmpty()){
			
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
	
	public static void validarPeticionCrearUsuario(PeticionCrearUsuarioExternoDTO peticion)throws SeguridadException{
		validarNull(peticion);
		validarHeader(peticion.getHeader());
		validarInformacionUsuario(peticion.getInformacionUsuario());
		
		if(peticion.getRol() == null
				|| peticion.getRol().trim().isEmpty()){
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
	
	public static void validarPeticionConsultaUsuarioRol(PeticionConsultaUsuarioRol peticion)throws SeguridadException{
		validarNull(peticion);
		validarHeader(peticion.getHeader());
		
		if(peticion.getRol() == null
				|| peticion.getRol().trim().isEmpty()){
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
	
	public static void validarPeticionInactivarUsuario(PeticionInactivarUsuario peticion)throws SeguridadException{
		validarNull(peticion);
		validarHeader(peticion.getHeader());
		
		if(peticion.getUserIdInactivar() == null){
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
	
	
	
	
	public static UsuariosDTO obtenerDTO(InformacionUsuarioDTO infoUsuario){
		
		UsuariosDTO usuario = new UsuariosDTO();
		
		if(infoUsuario == null){
			return null;
		}
		
		usuario.setApellidosUsuario(infoUsuario.getApellidos());
		usuario.setEmailUsuario(infoUsuario.getEmail());
		usuario.setLoginUsuario(infoUsuario.getNombreUsuario());
		usuario.setNombres(infoUsuario.getNombres());
		usuario.setNombreUsuario(infoUsuario.getNombreUsuario());
		usuario.setUsuarioId(infoUsuario.getUserId() != null? infoUsuario.getUserId().toString():null);
		usuario.setTipo(infoUsuario.getTipo().equals(Constantes.TIPO_USUARIO_EXTERNO)?Constantes.TIPO_USUARIO_EXTERNO_N:Constantes.TIPO_USUARIO_INTERNO_N);
		usuario.setLogonName(infoUsuario.getTipoIdentificacion()+infoUsuario.getNumeroIdentificacion());
		usuario.setNumeroDocumento(infoUsuario.getNumeroIdentificacion().toString());
		return usuario;
		
	}
	
	private static UsuarioDTO obtenerDTO(UsuariosDTO usuarios){
		
		UsuarioDTO resultado = new UsuarioDTO();
		if(usuarios == null){
			return null;
		}
		
		resultado.setApellidos(usuarios.getApellidosUsuario());
		resultado.setNombres(usuarios.getNombres());
		resultado.setUserId(usuarios.getUsuarioId() != null? Integer.valueOf(usuarios.getUsuarioId()): null);
		resultado.setLogonName(usuarios.getLogonName());
		resultado.setNombreUsuario(usuarios.getNombreUsuario());
		resultado.setNumeroDocumento(usuarios.getNumeroDocumento());
		resultado.setEmailUsuario(usuarios.getEmailUsuario());
		resultado.setLoginUsuario(usuarios.getLoginUsuario());
		resultado.setTipo(usuarios.getTipo());
		resultado.setFechaCreacion(usuarios.getFechaCreacion());
		resultado.setUltimaModificacion(usuarios.getUltimaModificacion());

		return resultado;
		
	}
	
	private static OpcionDTO obtenerDTO(OperacionesDTO opDto){
		
		OpcionDTO resultado = new OpcionDTO();
		
		if(opDto == null){
			return null;
		}
		
		resultado.setDescripcion(opDto.getDescripcion());
		resultado.setNombreObjeto(opDto.getNombreObjeto());
		resultado.setNombreRolAsociado(opDto.getNombreRolAsociado());
		resultado.setOpcionId(opDto.getOpcionId().intValue());
		resultado.setTipo(opDto.getTipo());
		resultado.setUrlImgGif(opDto.getUrlImgGif());
		
		if(opDto.getListadoOperaciones() != null){
			resultado.setHijosOpcion(new ArrayList<>());
			
			for(OperacionesDTO opHija: opDto.getListadoOperaciones()){
				resultado.getHijosOpcion().add(UtilsDTO.obtenerDTO(opHija));
			}
			
			
		}
		
		return resultado;
		
	}
	
	public static RolesPermisosDTO obtenerDTO(OperacionesRolDTO opRolDto,String token){
		RolesPermisosDTO resultado = new RolesPermisosDTO();
		
		if(opRolDto == null){
			return null;
		}
		
		resultado.setPermisos(new InformacionPermisosDTO());
		
		if(opRolDto.getOperacionesList() != null){
			resultado.getPermisos().setPermisos(new ArrayList<>());
			for(OperacionesDTO opDto : opRolDto.getOperacionesList()){
				resultado.getPermisos().getPermisos().add(UtilsDTO.obtenerDTO(opDto));
			}
		}
		
		if(opRolDto.getRolesList() != null){
			resultado.getPermisos().setRoles(opRolDto.getRolesList() );
		}
		
		if(opRolDto.getUsuario() != null){
			resultado.setUsuario(UtilsDTO.obtenerDTO(opRolDto.getUsuario()));
		}
		
		resultado.setTokenAcceso(token);
		
		return resultado;
	}
	
	/**
	 * Constructor privado ya que no se espera crear un instancia de esta clase.
	 */
	private UtilsDTO(){
		
	}

}
