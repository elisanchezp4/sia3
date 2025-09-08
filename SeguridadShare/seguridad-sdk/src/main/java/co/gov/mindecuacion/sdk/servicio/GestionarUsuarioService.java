package co.gov.mindecuacion.sdk.servicio;

import co.gov.mineducacion.sdk.web.soap.gestionar.ActualizarDatosBasicosRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.ActualizarEmailRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.ActualizarPasswordRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.Aplicacion;
import co.gov.mineducacion.sdk.web.soap.gestionar.ArrayOfstring;
import co.gov.mineducacion.sdk.web.soap.gestionar.ConsultarUsuariosRolRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.ConsultarUsuariosRolRs;
import co.gov.mineducacion.sdk.web.soap.gestionar.CrearUsuarioExternoRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.CrearUsuarioExternoRs;
import co.gov.mineducacion.sdk.web.soap.gestionar.DesvincularRolesUsuarioRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.EncabezadoSeguridad;
import co.gov.mineducacion.sdk.web.soap.gestionar.IdentificadorUsuario;
import co.gov.mineducacion.sdk.web.soap.gestionar.InactivarUsuarioRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.InformacionRol;
import co.gov.mineducacion.sdk.web.soap.gestionar.InformacionUsuario;
import co.gov.mineducacion.sdk.web.soap.gestionar.MensajeFault;
import co.gov.mineducacion.sdk.web.soap.gestionar.ModificarUsuarioExternoRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.VincularRolesUsuarioRq;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.GestionarUsuariosImplementsService;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuario;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioCrearUsuarioExternoMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioInactivarUsuarioMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioModificarUsuarioExternoMensajeFaultFaultFaultMessage;
import co.gov.mineducacion.sdk.web.soap.gestionar.servicio.IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * SDK que permite la comunicacion con el servicio de gestionar usuarios.
 * 
 * @author Michael Murgueitio
 * @version 02/2017 (1.0)
 * 
 */
public class GestionarUsuarioService {
	private GestionarUsuarioService(){/* Recomendacion por sonar */}

	/**
	 * Permite Crear usurios comunicandose con el sistema de seguridad
	 * 
	 * @Hbt Se modifica el metodo para que retorne InformacionUsuario 
	 * ya que va a retornar el idUsuario, nombre usaurio se agregar el nuevo dato de retorno
	 * lineas afectadas 56, 61, 70,71, 78
	 * 
	 * @param infoUsuario
	 * @param idAplicacion
	 * @param rol
	 * @param usuarioPeticion
	 * @return
	 * @throws SeguridadException
	 */
	public static InformacionUsuario crearUsuario(InformacionUsuario infoUsuario, String idAplicacion, String rol,
			Integer usuarioPeticion) throws SeguridadException {
		CrearUsuarioExternoRq crearRq = new CrearUsuarioExternoRq();
		GestionarUsuariosImplementsService gestionarServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		InformacionUsuario usuarioRespuesta = new InformacionUsuario();
		try {
			IGestionarUsuario gestionar = gestionarServ.getGestionarUsuariosImplementsPort();
			crearRq.setInformacionRol(armarInfoRol(rol));
			crearRq.setInformacionUsuario(infoUsuario);
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			CrearUsuarioExternoRs respuesta = new CrearUsuarioExternoRs();
			Holder<CrearUsuarioExternoRs> respuestaHolder = new Holder<>(respuesta);
			gestionar.crearUsuarioExterno(crearRq, aplicacion, armarEncabezadoSeguridad(), respuestaHolder);
			usuarioRespuesta.setUserId(respuestaHolder.value.getInformacionUsuario().getUserId());
			usuarioRespuesta.setNombreUsuario(respuestaHolder.value.getInformacionUsuario().getNombreUsuario());
		} catch (IGestionarUsuarioCrearUsuarioExternoMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
		return usuarioRespuesta;
	}

	/**
	 * Permite Modificar usurios comunicandose con el sistema de seguridad
	 * 
	 * @param infoUsuario
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 */
	public static void modificarUsuario(InformacionUsuario infoUsuario, String idAplicacion, Integer usuarioPeticion)
			throws SeguridadException {
		ModificarUsuarioExternoRq modUsuarioRq = new ModificarUsuarioExternoRq();
		GestionarUsuariosImplementsService gestionarServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		try {
			IGestionarUsuario gestionar = gestionarServ.getGestionarUsuariosImplementsPort();
			modUsuarioRq.setInformacionUsuario(infoUsuario);
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			gestionar.modificarUsuarioExterno(modUsuarioRq, aplicacion, armarEncabezadoSeguridad());
		} catch (IGestionarUsuarioModificarUsuarioExternoMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}

	/**
	 * Permite Inactivar usurios comunicandose con el sistema de seguridad
	 * 
	 * @param infoUsuario
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 */
	public static List<InformacionUsuario> consultarUsuario(String idAplicacion, Integer usuarioPeticion, String rol)
			throws SeguridadException {
		ConsultarUsuariosRolRq consultarRQ = new ConsultarUsuariosRolRq();
		GestionarUsuariosImplementsService gestionarServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		try {
			IGestionarUsuario gestionar = gestionarServ.getGestionarUsuariosImplementsPort();
			consultarRQ.setInformacionRol(armarInfoRol(rol));
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			ConsultarUsuariosRolRs respuesta = new ConsultarUsuariosRolRs();
			Holder<ConsultarUsuariosRolRs> respuestaHolder = new Holder<>(respuesta);
			gestionar.consultarUsuariosRol(consultarRQ, aplicacion, armarEncabezadoSeguridad(), respuestaHolder);
			return respuestaHolder.value.getUsuarios().getInformacionUsuario();
		} catch (IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}

	/**
	 * Permite Desvincular roles a usurios comunicandose con el sistema de seguridad
	 *
	 * @param roles, usuarioId
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 */
	public static void desvincularRolesUsuario(List<String> roles, String usuarioId, String idAplicacion,
											   Integer usuarioPeticion, Boolean notificarUsuario) throws SeguridadException {

		DesvincularRolesUsuarioRq parameters = new DesvincularRolesUsuarioRq();
		GestionarUsuariosImplementsService gServ = new GestionarUsuariosImplementsService();
		try{
			validaRoles(roles);
			IGestionarUsuario gestionarUsuario = gServ.getGestionarUsuariosImplementsPort();
			Aplicacion aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			ArrayOfstring rolesList = new ArrayOfstring();
			rolesList.getString().addAll(roles);
			parameters.setRoles(rolesList);
			parameters.setUserId(usuarioId);
			parameters.setNotificarUsuario(notificarUsuario);
			gestionarUsuario.desvincularRolesUsuario(parameters,aplicacion,armarEncabezadoSeguridad());
		}catch (IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage error){
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		}catch (Exception e){
			throw new SeguridadException(e.getMessage());
		}
	}

	/**
	 * Permite Vincular roles a usurios comunicandose con el sistema de seguridad
	 *
	 * @param roles, usuarioId
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 */
	public static void vincularRolesUsuario(List<String> roles, String usuarioId, String idAplicacion, Integer usuarioPeticion, Boolean notificarUsuario) throws SeguridadException {

		VincularRolesUsuarioRq parameters = new VincularRolesUsuarioRq();
		GestionarUsuariosImplementsService gServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		ArrayOfstring rolesList = null;
		try{
			validaRoles(roles);
			IGestionarUsuario gestionarUsuario = gServ.getGestionarUsuariosImplementsPort();
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			rolesList = new ArrayOfstring();
			rolesList.getString().addAll(roles);
			parameters.setRoles(rolesList);
			parameters.setUserId(usuarioId);
			parameters.setNotificarUsuario(notificarUsuario);
			gestionarUsuario.vincularRolesUsuario(parameters,aplicacion,armarEncabezadoSeguridad());
		}catch (IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage error){
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		}catch (Exception e){
			throw new SeguridadException(e.getMessage());
		}
	}

	/**
	 * Permite Modificar email de un usuario comunicandose con el sistema de seguridad
	 *
	 * @param infoUsuario
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 */
	public static void actualizarEmail(String email, String userId, String idAplicacion, Integer usuarioPeticion, Boolean notificarUsuario) throws SeguridadException {
		ActualizarEmailRq parameters = new ActualizarEmailRq();
		GestionarUsuariosImplementsService gServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		try {
			IGestionarUsuario gestionarUsuario = gServ.getGestionarUsuariosImplementsPort();
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			parameters.setEmail(email);
			parameters.setUserId(userId);
			parameters.setNotificarUsuario(notificarUsuario);
			gestionarUsuario.actualizarEmail(parameters,aplicacion,armarEncabezadoSeguridad());
		} catch (IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}

	/**
	 * Permite Actualizar los Datos basicos de un usuario comunicandose con el sistema de seguridad
	 *
	 * @param nombres
	 * @param apellidos
	 * @param numeroDocumento
	 * @param rutaDirectorio
	 * @param userId
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @param notificarUsuario
	 * @throws SeguridadException
	 */
	public static void actualizarDatosBasicos(String nombres, String apellidos, String numeroDocumento, String rutaDirectorio, String userId, String idAplicacion, Integer usuarioPeticion, Boolean notificarUsuario) throws SeguridadException {
		ActualizarDatosBasicosRq parameters = new ActualizarDatosBasicosRq();
		GestionarUsuariosImplementsService gServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		try {
			IGestionarUsuario gestionarUsuario = gServ.getGestionarUsuariosImplementsPort();
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			parameters.setNombres(nombres);
			parameters.setApellidos(apellidos);
			parameters.setNumeroDocumento(numeroDocumento);
			parameters.setRutaDirectorio(rutaDirectorio);
			parameters.setUserId(userId);
			parameters.setNotificarUsuario(notificarUsuario);
			gestionarUsuario.actualizarDatosBasicos(parameters,aplicacion,armarEncabezadoSeguridad());
		} catch (IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}


	/**
	 * Permite Inactivar usurios comunicandose con el sistema de seguridad
	 * 
	 * @param infoUsuario
	 * @param idAplicacion
	 * @param rol
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 */
	public static void inactivarUsuario(Integer idUsuarioInact, String idAplicacion, Integer usuarioPeticion)
			throws SeguridadException {
		InactivarUsuarioRq inactivarUsuario = new InactivarUsuarioRq();
		IdentificadorUsuario identificadorUsuario = new IdentificadorUsuario();
		identificadorUsuario.setUserId(idUsuarioInact);
		GestionarUsuariosImplementsService gestionarServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		try {
			IGestionarUsuario gestionar = gestionarServ.getGestionarUsuariosImplementsPort();
			inactivarUsuario.setInformacionUsuario(identificadorUsuario);
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			gestionar.inactivarUsuario(inactivarUsuario, aplicacion, armarEncabezadoSeguridad());
		} catch (IGestionarUsuarioInactivarUsuarioMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}

	//valida si el los roles a vincular o desvincular son nulos
	private static void validaRoles(List<String> roles) throws SeguridadException {
		if(roles == null  || roles.isEmpty()) {
			throw new SeguridadException("Roles no puede ser nulo", "400");
		}
	}

	/**
	 * Arma la informacion de un rol
	 * 
	 * @param rol
	 * @return
	 */
	private static InformacionRol armarInfoRol(String rol) {
		InformacionRol infoRol = new InformacionRol();
		infoRol.setRol(rol);
		return infoRol;
	}

	/**
	 * Arma la informacion de la aplicacion
	 * 
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @return
	 */
	private static Aplicacion armarAplicacion(String idAplicacion, Integer usuarioPeticion) {
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.setApiKey(idAplicacion);
		aplicacion.setUserId(usuarioPeticion);
		return aplicacion;
	}

	/**
	 * Arma el encabezado de entrada
	 * 
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws UnknownHostException
	 */
	private static EncabezadoSeguridad armarEncabezadoSeguridad()
			throws DatatypeConfigurationException, UnknownHostException {
		EncabezadoSeguridad encabezado = new EncabezadoSeguridad();
		GregorianCalendar ahora = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		XMLGregorianCalendar fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(ahora);
		InetAddress ip = InetAddress.getLocalHost();
		encabezado.setFechaPeticion(fecha);
		encabezado.setIpHost(ip.getHostAddress());
		return encabezado;
	}

	/**
	 * Permite Actualizar la contraseña de un usuario comunicandose con el sistema de seguridad
	 *
	 * @param password
	 * @param userId
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @param notificarUsuario
	 * @throws SeguridadException
	 */
	public static void actualizarPassword(String password, String userId, String idAplicacion, Integer usuarioPeticion, Boolean notificarUsuario) throws SeguridadException {
		ActualizarPasswordRq parameters = new ActualizarPasswordRq();
		GestionarUsuariosImplementsService gServ = new GestionarUsuariosImplementsService();
		Aplicacion aplicacion;
		try {
			IGestionarUsuario gestionarUsuario = gServ.getGestionarUsuariosImplementsPort();
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			parameters.setPassword(password);
			parameters.setUserId(userId);
			parameters.setNotificarUsuario(notificarUsuario);
			gestionarUsuario.actualizarPassword(parameters,aplicacion,armarEncabezadoSeguridad());
		} catch (IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}

}
