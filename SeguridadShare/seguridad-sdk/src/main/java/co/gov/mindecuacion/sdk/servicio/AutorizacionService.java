package co.gov.mindecuacion.sdk.servicio;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import co.gov.mineducacion.sdk.util.FiltroSeguridad;
import co.gov.mineducacion.sdk.util.UtilCifrador;
import co.gov.mineducacion.sdk.web.soap.autenticar.*;
import co.gov.mineducacion.sdk.web.soap.autenticar.servicio.*;


/**
 * SDK que permite la comunicacion con el servicio de autorizaci&oacute;n.
 * 
 * @author hfabra
 * @version 02/2017 (1.0)
 * 
 */
public class AutorizacionService {

	private static final Logger logger = Logger.getLogger(FiltroSeguridad.class.getName());
	
	/**
	 * Permite obtener comunicandose con el sistema de seguridad
	 * 
	 * @param codigoAutorizacion
	 * @param idAplicacion
	 * @param rol
	 * @param usuarioPeticion
	 * @return la informacion completa del token
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 21/02/2017
	 */
	public static String obtenerToken(String codigoAutorizacion, String idAplicacion, String rol,
			Integer usuarioPeticion) throws SeguridadException {
		
		ObtenerTokenRq parameters = new ObtenerTokenRq();
		Aplicacion aplicacion = null;
		Autorizacion autorizacion = null;
		InformacionToken token = null;
		
		try {
			AutenticarImplementsService service = new AutenticarImplementsService();
			IAutenticacion auth = service.getAutenticarImplementsPort();
			autorizacion = new Autorizacion();
			aplicacion = new Aplicacion();
			token = new InformacionToken();
			
			autorizacion.setCodigoAutorizacion(codigoAutorizacion);
			parameters.setCodigoAutorizacion(autorizacion);
			
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			ObtenerTokenRs respuesta = new ObtenerTokenRs();
			Holder<ObtenerTokenRs> respuestaHolder = new Holder<>(respuesta);
			auth.obtenerToken(parameters, aplicacion, armarEncabezadoSeguridad(), respuestaHolder);
			token = respuestaHolder.value.getTokenAcceso();
		} catch (IAutenticacionObtenerTokenMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
		return token.getTokenAcceso();
	}

	/**
	 * Permite finalizar sesion comunicandose con el sistema de seguridad
	 * 
	 * @param token
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 21/02/2017
	 */
	public static void finalizarSesion(String token, String idAplicacion, Integer usuarioPeticion)
			throws SeguridadException {
		FinalizarSesionRq parameters = new FinalizarSesionRq();
		Aplicacion aplicacion = null;
		InformacionSesion sesion = null;
		
		try {
			AutenticarImplementsService service = new AutenticarImplementsService();
			IAutenticacion auth = service.getAutenticarImplementsPort();
			sesion = new InformacionSesion();
			aplicacion = new Aplicacion();
			
			sesion.setTokenAcceso(token);
			parameters.setInformacionSesion(sesion);
			
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			auth.finalizarSesion(parameters, aplicacion, armarEncabezadoSeguridad());
		} catch (IAutenticacionFinalizarSesionMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}

	/**
	 * Permite modificar password comunicandose con el sistema de seguridad
	 * 
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @param rol
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 21/02/2017
	 */
	public static void modificarPassword(String tokenAcceso, String password, String idAplicacion, Integer usuarioPeticion)
			throws SeguridadException {
		ModificarPasswordRq parameters = new ModificarPasswordRq();
		Aplicacion aplicacion = null;
		InformacionNuevaClave credentials = null;
		UtilCifrador cifrador = new UtilCifrador();
		
		try {
			AutenticarImplementsService service = new AutenticarImplementsService();
			IAutenticacion auth = service.getAutenticarImplementsPort();
			credentials = new InformacionNuevaClave();
			aplicacion = new Aplicacion();
			
			credentials.setTokenAcceso(tokenAcceso);
			credentials.setNuevaClave(cifrador.cifrarTexto(password));
			parameters.setInformacionSesion(credentials);
			
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			auth.modificarPassword(parameters, aplicacion, armarEncabezadoSeguridad());
		} catch (IAutenticacionModificarPasswordMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
	}

	/**
	 *  Permite obtener roles y permisos comunicandose con el sistema de seguridad
	 *  
	 * @param tokenAcceso
	 * @param idAplicacion
	 * @param usuarioPeticion
	 * @return los permisos y roles
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 21/02/2017
	 */
	public static ObtenerRolesPermisosRs obtenerRolesPermisos(String tokenAcceso, String idAplicacion, Integer usuarioPeticion)
			throws SeguridadException {
		ObtenerRolesPermisosRq parameters = new ObtenerRolesPermisosRq();
		Aplicacion aplicacion = null;
		InformacionSesion session = null;
		ObtenerRolesPermisosRs permisos = null;
		
		try {
			AutenticarImplementsService service = new AutenticarImplementsService();
			IAutenticacion auth = service.getAutenticarImplementsPort();
			aplicacion = new Aplicacion();
			session = new InformacionSesion();
			permisos = new ObtenerRolesPermisosRs();
			
			session.setTokenAcceso(tokenAcceso);
			parameters.setInformacionSesion(session);
			
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			ObtenerRolesPermisosRs respuesta = new ObtenerRolesPermisosRs();
			Holder<ObtenerRolesPermisosRs> respuestaHolder = new Holder<>(respuesta);
			auth.obtenerRolesPermisos(parameters, aplicacion, armarEncabezadoSeguridad(), respuestaHolder);
			permisos = respuestaHolder.value;
		} catch (IAutenticacionObtenerRolesPermisosMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
		return permisos;
	}
	
	public static Date actualizarFechaVencimiento(String tokenAcceso, String idAplicacion, Integer usuarioPeticion) throws SeguridadException {

		logger.info("Ejecuta actualizarFechaVencimiento: idAplicacion: " + idAplicacion +" tokenAcceso: " + tokenAcceso + " usuarioPeticion: " +   usuarioPeticion);

		ActualizarFechaVencimientoTokenRq parameters = new ActualizarFechaVencimientoTokenRq();
		Aplicacion aplicacion = null;
		InformacionSesion session = null;
		ActualizarFechaVencimientoTokenRs respuesta = null;
		Date fechaVencimiento = null;
		
		try {
			AutenticarImplementsService service = new AutenticarImplementsService();

			logger.info("Paso AutenticarImplementsService Puerto: {}" + service.getAutenticarImplementsPort());

			IAutenticacion auth = service.getAutenticarImplementsPort();
			respuesta = new ActualizarFechaVencimientoTokenRs();
			aplicacion = new Aplicacion();
			session = new InformacionSesion();
			fechaVencimiento = new Date();
			
			session.setTokenAcceso(tokenAcceso);
			parameters.setInformacionToken(session);
			
			aplicacion = armarAplicacion(idAplicacion, usuarioPeticion);
			
			Holder<ActualizarFechaVencimientoTokenRs> respuestaHolder = new Holder<>(respuesta);
			auth.actualizarFechaVencimientoToken(parameters, aplicacion, armarEncabezadoSeguridad(), respuestaHolder);
			fechaVencimiento = convertirADate(respuestaHolder.value.getInformacionToken().getFechaExpriracion());
			
		} catch (IAutenticacionActualizarFechaVencimientoTokenMensajeFaultFaultFaultMessage error) {
			MensajeFault message = error.getFaultInfo();
			throw new SeguridadException(message.getMensaje(), message.getCodigo());
		} catch (Exception e) {
			throw new SeguridadException(e.getMessage());
		}
		return fechaVencimiento;
	}
	
	
	/*
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
    private static Date convertirADate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
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
		InetAddress IP = InetAddress.getLocalHost();
		encabezado.setFechaPeticion(fecha);
		encabezado.setIpHost(IP.getHostAddress());
		return encabezado;
	}

}
