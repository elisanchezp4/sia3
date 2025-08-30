package co.gov.mineducacion.seguridad.ejb.servicios;

import java.util.List;

import javax.ejb.Local;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;

/**
 * Interfaz administrativa de usuarios
 *
 * @author Michael Murgueitio
 *
 * @Hbt Se modifica para que el metodo crearUsuario raetorne UsuariosDTO
 */
@Local
public interface IUsuarios {

	/**
	 * Crea un usuario en el directorio activo y en la base de datos de
	 * seguridad
	 *
	 * @param usuario
	 * @param apiKey
	 * @param rol
	 * @return
	 * @throws SeguridadException
	 */
	UsuariosDTO crearUsuario(UsuariosDTO usuario, String apiKey, String rol, Integer usuarioMod) throws SeguridadException;

	/**
	 * Modifica un usuario en el directorio activo y en la base de datos de
	 * seguridad
	 *
	 * @param usuariosDTO
	 * @param aplicacion
	 * @throws SeguridadException
	 */
	void modificarUsuario(UsuariosDTO usuariosDTO, String aplicacion, Integer usuarioMod) throws SeguridadException;

	void
	actualizarEmail(String logonusuario,String nuevoEmail,String aplicacionId, String usuarioPeticion) throws SeguridadException;

	void actualizarDatosBasicos(UsuariosDTO usuario,String aplicacionId,String usuarioMod, String usuarioPeticion)  throws SeguridadException;

	void vincularRolesUsuario(List<String> roles,String usuarioId,String aplicacionId) throws SeguridadException, SIA3Exception;

	void desvincularRolesUsuario(List<String> roles,String usuarioId,String aplicacionId, String usuarioPeticion) throws SeguridadException;

	/**
	 * Inactiva un usuario en el directorio activo y en la base de datos de
	 * seguridad
	 *
	 * @param loginUsuario
	 * @param appKey
	 * @throws SeguridadException
	 */
	void inactivarUsuario(Integer loginUsuario, String appKey, Integer usuarioMod) throws SeguridadException;

	/**
	 * Consulta todos los usuarios que tenga un rol
	 *
	 * @param rol
	 * @param infoAPP
	 * @return
	 * @throws SeguridadException
	 */
	List<UsuariosDTO> consultarRolesXUSuario(String rol, String infoAPP, Integer usuarioMod) throws SeguridadException;

	List<UsuariosDTO> consultarUsuarioLogon(String logon, String infoAPP, Integer usuarioMod) throws SeguridadException;

	/**
	 *
	 * @throws SeguridadException
	 */
	void verificarUsuarioAdministrador(Integer usuarioAdministrador, String rol) throws SeguridadException;

	/**
	 * Retorna un listado de objetos tipo UsuariosDTO construida con base en la lista objetos tipo Usuario
	 * recibida por parametro
	 * @param listaUsuarios
	 * @return
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 24/03/2017
	 */
	public List<UsuariosDTO> armarListaUsuariosDTO(List<Usuario> listaUsuarios) throws SeguridadException;

}
