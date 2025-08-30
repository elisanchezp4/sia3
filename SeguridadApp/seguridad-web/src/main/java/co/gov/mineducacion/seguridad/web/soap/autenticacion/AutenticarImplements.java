package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;

import co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion;
import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import co.gov.mineducacion.seguridad.ejb.servicios.ServiciosCommons;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesRolDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.UtilCifrador;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;

@WebService(endpointInterface = "co.gov.mineducacion.seguridad.web.soap.autenticacion.IAutenticacion")
public class AutenticarImplements extends ServiciosCommons implements IAutenticacion {

	@EJB
	IUsuarios servicioUsuarios;

	@EJB
	IServicioAutenticacion servicioAutenticacion;

	@Override
	public ObtenerTokenRs obtenerToken(ObtenerTokenRq parameters, Aplicacion aplicacion,
			EncabezadoSeguridad encabezadoSeguridadRq) throws IAutenticacionObtenerTokenMensajeFaultFaultFaultMessage {
		ObtenerTokenRs responseService = null;
		TokensActivosDTO tokenAcceso = null;

		InformacionToken tokenInfo = null;

		ObjectFactory objectFactory = null;
		JAXBElement<String> valueToken = null;
		JAXBElement<InformacionToken> tokenJAXB = null;

		try {
			// Se inicializan los objetos a utilizar en la operacion
			objectFactory = new ObjectFactory();
			tokenInfo = objectFactory.createInformacionToken();
			responseService = objectFactory.createObtenerTokenRs();
			
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_AUTENTICADOR);
			
			tokenAcceso = servicioAutenticacion.obtenerToken(parameters
					.getCodigoAutorizacion().getValue().getCodigoAutorizacion()
					.getValue(), aplicacion.getUserId(), aplicacion.getApiKey().getValue());
			
			valueToken = objectFactory.createInformacionTokenTokenAcceso(tokenAcceso.getTokenId());

			tokenInfo.setTokenAcceso(valueToken);
			tokenInfo
					.setFechaExpriracion(UtilOperaciones.convertirTimestampAXMLGregorian(tokenAcceso.getVencimiento()));

			tokenJAXB = objectFactory.createInformacionToken(tokenInfo);

			responseService.setTokenAcceso(tokenJAXB);

		} catch (Exception e) {
			MensajeFault fault = cargarMensajeFault(e, objectFactory);
			throw new IAutenticacionObtenerTokenMensajeFaultFaultFaultMessage(fault.getMensaje().getValue(), fault);
		}
		return responseService;
	}

	@Override
	public void finalizarSesion(FinalizarSesionRq parameters, Aplicacion aplicacion,
			EncabezadoSeguridad encabezadoSeguridadRq)
			throws IAutenticacionFinalizarSesionMensajeFaultFaultFaultMessage {
		ObjectFactory objectFactory = null;
		try {
			objectFactory = new ObjectFactory();
			
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);

			JAXBElement<InformacionSesion> informacionSesion = parameters.getInformacionSesion();
			JAXBElement<String> tokenAcceso = informacionSesion.getValue().getTokenAcceso();
			
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_AUTENTICADOR);

			servicioAutenticacion.finalizarSesion(tokenAcceso.getValue(), aplicacion.getUserId(), aplicacion.getApiKey().getValue());
		} catch (Exception e) {
			MensajeFault fault = cargarMensajeFault(e, objectFactory);
			throw new IAutenticacionFinalizarSesionMensajeFaultFaultFaultMessage(fault.getMensaje().getValue(), fault);
		}

	}

	@Override
	public void modificarPassword(ModificarPasswordRq parameters, Aplicacion aplicacion,
			EncabezadoSeguridad encabezadoSeguridadRq)
			throws IAutenticacionModificarPasswordMensajeFaultFaultFaultMessage {
		ObjectFactory objectFactory = null;
		UtilCifrador cifrador = new UtilCifrador();
		try {
			objectFactory = new ObjectFactory();
			
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			validarCampos(aplicacion.getUserId());			

			JAXBElement<InformacionNuevaClave> informacionSesion = parameters.getInformacionSesion();
			JAXBElement<String> tokenAcceso = informacionSesion.getValue().getTokenAcceso();
			JAXBElement<String> nuevaClave = informacionSesion.getValue().getNuevaClave();
			
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_AUTENTICADOR);

			servicioAutenticacion.modificarPassword(tokenAcceso.getValue(), aplicacion.getUserId(), cifrador.descifrar(nuevaClave.getValue()), aplicacion.getApiKey().getValue(), String.valueOf(aplicacion.getUserId()));
		} catch (Exception e) {
			MensajeFault fault = cargarMensajeFault(e, objectFactory);
			throw new IAutenticacionModificarPasswordMensajeFaultFaultFaultMessage(fault.getMensaje().getValue(),
					fault);
		}

	}

	@Override
	public ObtenerRolesPermisosRs obtenerRolesPermisos(ObtenerRolesPermisosRq parameters, Aplicacion aplicacion,
			EncabezadoSeguridad encabezadoSeguridadRq)
			throws IAutenticacionObtenerRolesPermisosMensajeFaultFaultFaultMessage {
		ObjectFactory objectFactory = null;
		ObtenerRolesPermisosRs responseService = null;

		try {
			objectFactory = new ObjectFactory();
			
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			validarCamposObligatorio(aplicacion.getApiKey().getValue(), 50L);
			validarCampos(aplicacion.getUserId());
			
			responseService = objectFactory.createObtenerRolesPermisosRs();
			
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_AUTENTICADOR);
			
			OperacionesRolDTO rolesPermisosWS = servicioAutenticacion.obtenerRolesYPermisos(aplicacion.getUserId(), 
																					aplicacion.getApiKey().getValue(), 
																					parameters.getInformacionSesion().getValue().getTokenAcceso().getValue());
			
			ArrayOfstring rolesList = new ArrayOfstring();			
			rolesList.getString().addAll(rolesPermisosWS.getRolesList());
			
			
			JAXBElement<ArrayOfOpcion> permisosList = crearArrayOfOpcion(rolesPermisosWS.getOperacionesList(),objectFactory);
		    JAXBElement<ArrayOfstring> roles = objectFactory.createInformacionPermisosRoles(rolesList);
			
		    InformacionPermisos infoPermisos = objectFactory.createInformacionPermisos();
		    
		    infoPermisos.setPermisos(permisosList);
		    infoPermisos.setRoles(roles);
		    
		    Usuario userWS = objectFactory.createUsuario();
		    userWS.setUserId(aplicacion.getUserId());
		    userWS.setNombres(objectFactory.createUsuarioNombres(rolesPermisosWS.getUsuario().getNombres()));
		    userWS.setApellidos(objectFactory.createUsuarioApellidos(rolesPermisosWS.getUsuario().getApellidosUsuario()));
			
			JAXBElement<InformacionPermisos> permisos = objectFactory.createInformacionPermisos(infoPermisos);
			
			JAXBElement<Usuario> usuario = objectFactory.createObtenerRolesPermisosRsUsuario(userWS);
			
			
			responseService.setPermisos(permisos);
			responseService.setUsuario(usuario);
			
		} catch (Exception e) {
			MensajeFault fault = cargarMensajeFault(e, objectFactory);
			throw new IAutenticacionObtenerRolesPermisosMensajeFaultFaultFaultMessage(fault.getMensaje().getValue(),
					fault);
		}
		return responseService;
	}

	/**
	 * Servicio que arma recursivamente el listado de opciones para retornar en 
	 * el servicio web
	 * @param operacionesList
	 * @return
	 * @author hfabra
	 * @since 19/02/2017
	 */
	private JAXBElement<ArrayOfOpcion> crearArrayOfOpcion(List<OperacionesDTO> operacionesList, ObjectFactory objectFactory) {
		
		ArrayOfOpcion opciones = objectFactory.createArrayOfOpcion();
		
		if (operacionesList != null) {
			for (OperacionesDTO operaciones: operacionesList) {
				Opcion opcion = objectFactory.createOpcion();
				opcion.setDescripcion(objectFactory.createOpcionDescripcion(operaciones.getDescripcion()));
				opcion.setNombreObjeto(objectFactory.createOpcionNombreObjeto(operaciones.getNombreObjeto()));
				opcion.setTipo(TipoOpcion.fromValue(operaciones.getTipo()
						.substring(0, 1)
						+ operaciones.getTipo().substring(1).toLowerCase()));
				opcion.setOpcionId(operaciones.getOpcionId().intValue());
				opcion.setHijosOpcion(crearArrayOfOpcion(operaciones.getListadoOperaciones(), objectFactory));
				opcion.setNombreRolAsociado(objectFactory.createOpcionNombreRolAsociado(operaciones.getNombreRolAsociado()));
				opciones.getOpcion().add(opcion);
			}
		}

		return objectFactory.createArrayOfOpcion(opciones);
	}

	@Override
	public ActualizarFechaVencimientoTokenRs actualizarFechaVencimientoToken(
			ActualizarFechaVencimientoTokenRq parameters, Aplicacion aplicacion,
			EncabezadoSeguridad encabezadoSeguridadRq)
			throws IAutenticacionActualizarFechaVencimientoTokenMensajeFaultFaultFaultMessage {

		ObjectFactory objectFactory = null;
		ActualizarFechaVencimientoTokenRs responseService = null;
		Timestamp tokenFecha = null;
		try {
			objectFactory = new ObjectFactory();
			
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			validarCampos(aplicacion.getUserId());
			responseService = objectFactory.createActualizarFechaVencimientoTokenRs();
			
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_AUTENTICADOR);

			JAXBElement<InformacionSesion> informacionSesion = parameters.getInformacionToken();
			JAXBElement<String> tokenAcceso = informacionSesion.getValue().getTokenAcceso();

			tokenFecha = servicioAutenticacion.actualizarFechaVencimientoToken(tokenAcceso.getValue(), aplicacion.getUserId(),
					aplicacion.getApiKey().getValue());

			InformacionToken tokenInfo = objectFactory.createInformacionToken();

			tokenInfo.setTokenAcceso(tokenAcceso);
			tokenInfo.setFechaExpriracion(UtilOperaciones.convertirTimestampAXMLGregorian(tokenFecha));

			JAXBElement<InformacionToken> informacionToken = objectFactory.createInformacionToken(tokenInfo);

			responseService.setInformacionToken(informacionToken);
		} catch (Exception e) {
			MensajeFault fault = cargarMensajeFault(e, objectFactory);
			throw new IAutenticacionActualizarFechaVencimientoTokenMensajeFaultFaultFaultMessage(
					fault.getMensaje().getValue(), fault);
		}
		return responseService;
	}

	private MensajeFault cargarMensajeFault(Exception e, ObjectFactory factory) {
		MensajeFault fault = factory.createMensajeFault();

		SeguridadException exceptionSeguridad = procesarErrorRe(e);

		JAXBElement<String> codigo = factory.createMensajeFaultCodigo(exceptionSeguridad.getId().toString());
		JAXBElement<String> mensaje = factory.createMensajeFaultCodigo(exceptionSeguridad.getMessage());

		fault.setCodigo(codigo);
		fault.setMensaje(mensaje);

		return fault;
	}
	
	private void validarCampos(String campo, Long longitud) throws SeguridadException {
		if ((campo != null) && (!campo.equals("")) && (campo.length() > longitud)) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
	
	private void validarCamposObligatorio(String campo, Long longitud) throws SeguridadException {
		if ((campo == null) || ((campo != null) && (campo.equals("")))
				|| ((campo != null) && (!campo.equals("")) && (campo.length() > longitud))) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
	
	private void validarCampos(Integer campo) throws SeguridadException {
		if ((campo == null) || ((campo != null) && (campo.intValue() == 0))) {
			throw new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS);
		}
	}
}
