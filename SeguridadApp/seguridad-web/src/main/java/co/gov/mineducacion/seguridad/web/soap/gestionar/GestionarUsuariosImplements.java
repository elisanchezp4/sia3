package co.gov.mineducacion.seguridad.web.soap.gestionar;

import co.gov.mineducacion.seguridad.ejb.servicios.IServicioAutenticacion;
import co.gov.mineducacion.seguridad.ejb.servicios.IUsuarios;
import co.gov.mineducacion.seguridad.ejb.servicios.ServiciosCommons;
import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import co.gov.mineducacion.seguridad.modelo.enums.CamposLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuariosRol;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import co.gov.mineducacion.seguridad.modelo.utils.UtilEmail;
import co.gov.mineducacion.seguridad.negocio.NegocioAplicaciones;
import co.gov.mineducacion.seguridad.negocio.NegocioRoles;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuarios;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import co.gov.mineducacion.seguridad.web.servicio.utils.ObjectFactoryUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.WebServiceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ERROR_DATOS_NULOS;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ERROR_SOLO_UN_VALOR;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ID_ERROR_DATOS_NULOS;
import static co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil.PASSWORD_PATTERN;
import static co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil.validarMaximoCampo;

@WebService(endpointInterface = "co.gov.mineducacion.seguridad.web.soap.gestionar.IGestionarUsuario")
public class GestionarUsuariosImplements extends ServiciosCommons implements IGestionarUsuario {
	private static final Logger logger = Logger.getLogger(GestionarUsuariosImplements.class.getName());
	private static final String ERROR = "Error";
	private static final String USER_ID = "userId";
	private static final String NOMBRE_USUARIO = "nombreUsuario";
	private static final String CORREO_ELECTRONICO_USUARIO = "correoElectronico";
	private static final String API_KEY = "apiKey";
	private static final String ROLES = "roles";
	private static final String NOTIFICAR_USUARIO = "notificarUsuario";
	private static final String CORREO = "correo";
	private static final String MSG_USUARIO_NO_VALIDO = "Usuario no existe o no es valido. ";
	public static final String APP_NOT_ALLOWED = "El usuario no esta relacionado con la aplicación";

	@EJB
	IUsuarios servicioUsuarios;

	@EJB
	private NegocioUsuarios negocioUsuario;

	@EJB
	private NegocioRoles negocioRoles;

	@EJB
	private NegocioUsuariosRol negocioUsuariosRol;

	@EJB
	private ParametrosSng parametrosSng;

	@Resource
	private WebServiceContext wsCntxt;

	@EJB
	protected NegocioAplicaciones negocioAplicacion;

	@EJB
	private ManejadorUsuariosRol manejadorUsuariosRol;

	@EJB
	private IServicioAutenticacion servicioAutenticacion;

	/**
	 * @Hbt Se modifica ya que ahora el crearUsuario Retorna un UsuariosDTO
	 * Lineas afectadas 34,39,44
	 *
	 */
	@Override
	public CrearUsuarioExternoRs crearUsuarioExterno(CrearUsuarioExternoRq parameters, Aplicacion aplicacion,
													 EncabezadoSeguridad encabezadoSeguridadRq)
			throws IGestionarUsuarioCrearUsuarioExternoMensajeFaultFaultFaultMessage {
		String apiKey = aplicacion.getApiKey().getValue();
		String rol = setearRol(parameters.getInformacionRol());

		UsuariosDTO usuario;
		try {
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			validarCampos(aplicacion.getUserId());
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_GESTIONADOR);
			usuario = servicioUsuarios.crearUsuario(armarUsuarioDto(parameters.getInformacionUsuario().getValue()),
					apiKey, rol, aplicacion.getUserId());
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioCrearUsuarioExternoMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}
		logger.info("Retorno crearUsuarioExterno: " +usuario);

		return armarRespuestaCracion(usuario);
	}

	/**
	 * Modifica un usario en el directorio activo
	 */
	@Override
	public void modificarUsuarioExterno(ModificarUsuarioExternoRq parameters, Aplicacion aplicacion,
										EncabezadoSeguridad encabezadoSeguridadRq)
			throws IGestionarUsuarioModificarUsuarioExternoMensajeFaultFaultFaultMessage {
		String apiKey = aplicacion.getApiKey().getValue();
		try {
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			validarCampos(aplicacion.getUserId());
			validarCampos(Integer.parseInt(parameters.getInformacionUsuario().getValue().getUserId().toString()));
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_GESTIONADOR);
			servicioUsuarios.modificarUsuario(armarUsuarioDto(parameters.getInformacionUsuario().getValue()), apiKey,
					aplicacion.getUserId());
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioModificarUsuarioExternoMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}

	}

	/***
	 * Consulta usuario por logon_name
	 */
	@Override
	public ConsultarUsuariosRolRs consultarUsuarioLogon(ConsultarUsuariosRolRq parameters, Aplicacion aplicacion,
														EncabezadoSeguridad encabezadoSeguridadRq)
			throws IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage {
		ConsultarUsuariosRolRs respuesta = new ConsultarUsuariosRolRs();
		try {
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			validarCampos(aplicacion.getUserId());

			List<UsuariosDTO> listaUsuarios;
			JAXBElement<String> infoRol = parameters.getInformacionRol().getValue().getRol();
			listaUsuarios = consultarUsuarioLogon(obtenerValor(infoRol), obtenerValor(aplicacion.getApiKey()),
					aplicacion.getUserId());

			JAXBElement<ArrayOfinformacionUsuario> listaInfoUsuariosJax;
			ObjectFactory factory = new ObjectFactory();

			ArrayOfinformacionUsuario infoUsuario = llenarInfoUsuarios(listaUsuarios);
			listaInfoUsuariosJax = factory.createArrayOfinformacionUsuario(infoUsuario);

			respuesta.setUsuarios(listaInfoUsuariosJax);
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}
		return respuesta;
	}

	/**
	 * Consulta los usuarios por rol
	 */
	@Override
	public ConsultarUsuariosRolRs consultarUsuariosRol(ConsultarUsuariosRolRq parameters, Aplicacion aplicacion,
													   EncabezadoSeguridad encabezadoSeguridadRq)
			throws IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage {
		ConsultarUsuariosRolRs respuesta = new ConsultarUsuariosRolRs();
		try {
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			validarCampos(aplicacion.getUserId());

			List<UsuariosDTO> listaUsuarios;
			JAXBElement<String> infoRol = parameters.getInformacionRol().getValue().getRol();
			listaUsuarios = consultarRolesXUSuario(obtenerValor(infoRol), obtenerValor(aplicacion.getApiKey()),
					aplicacion.getUserId());

			JAXBElement<ArrayOfinformacionUsuario> listaInfoUsuariosJax;
			ObjectFactory factory = new ObjectFactory();

			ArrayOfinformacionUsuario infoUsuario = llenarInfoUsuarios(listaUsuarios);
			listaInfoUsuariosJax = factory.createArrayOfinformacionUsuario(infoUsuario);

			respuesta.setUsuarios(listaInfoUsuariosJax);
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}
		return respuesta;
	}

	/**
	 * devuelve la lista de usuarios DTO consultadas a partir de un rol
	 *
	 * @param rol
	 * @param app
	 * @param userModifi
	 * @return
	 * @throws IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage
	 */
	private List<UsuariosDTO> consultarRolesXUSuario(String rol, String app, Integer userModifi)
			throws IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage {
		List<UsuariosDTO> listaUsuarios;
		try {
			servicioUsuarios.verificarUsuarioAdministrador(userModifi, Constantes.ROL_GESTIONADOR);

			listaUsuarios = servicioUsuarios.consultarRolesXUSuario(rol, app, userModifi);
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}
		return listaUsuarios;
	}

	/**
	 * devuelve la lista de usuarios DTO consultadas a partir de un rol
	 *
	 * @param rol
	 * @param app
	 * @param userModifi
	 * @return
	 * @throws IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage
	 */
	private List<UsuariosDTO> consultarUsuarioLogon(String logon, String app, Integer userModifi)
			throws IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage {
		List<UsuariosDTO> listaUsuarios;
		try {
			servicioUsuarios.verificarUsuarioAdministrador(userModifi, Constantes.ROL_GESTIONADOR);

			listaUsuarios = servicioUsuarios.consultarUsuarioLogon(logon, app, userModifi);
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioConsultarUsuariosRolMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}
		return listaUsuarios;
	}

	/**
	 * Inactiva un usuario
	 */
	@Override
	public void inactivarUsuario(InactivarUsuarioRq parameters, Aplicacion aplicacion,
								 EncabezadoSeguridad encabezadoSeguridadRq)
			throws IGestionarUsuarioInactivarUsuarioMensajeFaultFaultFaultMessage {
		try {
			validarCampos(aplicacion.getUserId());
			validarCampos(encabezadoSeguridadRq.getIpHost().getValue(), 15L);
			servicioUsuarios.verificarUsuarioAdministrador(aplicacion.getUserId(), Constantes.ROL_GESTIONADOR);
			servicioUsuarios.inactivarUsuario(parameters.getInformacionUsuario().getValue().getUserId(),
					aplicacion.getApiKey().getValue(), aplicacion.getUserId());
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioInactivarUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}

	}

	/***
	 * Desvincular roles usuario
	 */
	@Override
	public void desvincularRolesUsuario(DesvincularRolesUsuarioRq parameters, Aplicacion aplicacion, EncabezadoSeguridad encabezadoSeguridadRq) throws IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage {
		logger.info("Inicia desvincularRolesUsuario roles");
		try {
			List<String> msnError = new ArrayList<>();

			msnError.add(validadorCamposNulos(API_KEY, aplicacion.getApiKey().getValue()));
			msnError.add(validadorCamposNulos(ROLES, parameters.getRoles()));
			msnError.add(validadorCamposNulos(NOTIFICAR_USUARIO, parameters.getNotificarUsuario()));

			String mensajeError = msnError.stream().filter(Objects::nonNull).findFirst().orElse(null);
			if (mensajeError != null){
				throw new IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(mensajeError, "400"));
			}

			UsuariosDTO usuariosDTO = buscarUsuario(parameters.getUserId(), parameters.getNombreUsuario(), parameters.getCorreoElectronico());

			if (usuariosDTO == null) {
				logger.error(MSG_USUARIO_NO_VALIDO + " Criterios: ID=" + parameters.userId + ", Nombre=" + parameters.nombreUsuario + ", Email=" + parameters.correoElectronico);
				throw new IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(MSG_USUARIO_NO_VALIDO, "400"));
			}

			List<String> listMsnValidacion = new ArrayList<>();

			List<RolesDTO> rolesDTOS = negocioRoles.getRolesPorUsuario(Long.valueOf(parameters.getUserId()));
			parameters.getRoles().getString().forEach(rolBase -> {
				if (rolesDTOS.stream().noneMatch(rolesDTO -> rolesDTO.getNombre().equals(rolBase))) {
					listMsnValidacion.add("El rol: " + rolBase + " no esta asociado al usuario: " + parameters.getUserId());
				}
			});

			if (listMsnValidacion.size() == parameters.getRoles().getString().size()) {
				throw new IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(String.join(", ", listMsnValidacion), "400"));
			}

			listMsnValidacion.clear();
			String apiKeyValue = aplicacion.getApiKey().getValue();
			BigDecimal aplicacionId = new BigDecimal(apiKeyValue);
			List<String> rolesFiltrados = new ArrayList<>();
			for (String rol : parameters.getRoles().getString()) {
				Roles existe = negocioRoles.consultarRolePorNombreYAplicacion(rol, aplicacionId);
				logger.info("Validacion de rol respuesta: " + existe + ", rol: " + rol);

				if (existe == null) {
					String rolNoexiste = "El rol " + rol + " no existe para la aplicación " + aplicacionId;
					logger.warn(rolNoexiste);
					listMsnValidacion.add(rolNoexiste);
				} else{
					List<UsuariosRol> usuariosRoles = manejadorUsuariosRol.buscarUsuariosRolXUsuarioAppD(parameters.getUserId(), aplicacionId);
					boolean rolDesvinculado = usuariosRoles.stream()
							.anyMatch(usuarioRol -> usuarioRol.getUsuariosRolPK().getRolId().equals(existe.getRolId()));
					if (rolDesvinculado) {
						String rolYaVinculado = "El rol " + rol + " ya se encuentra desvinculado para el usuario " + parameters.getUserId() + " en la aplicación " + aplicacionId;
						logger.warn(rolYaVinculado);
						listMsnValidacion.add(rolYaVinculado);
					} else {
						rolesFiltrados.add(rol);
					}
				}
			}
			if (listMsnValidacion.size() == parameters.getRoles().getString().size()) {
				throw new IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(String.join(", ", listMsnValidacion), "400"));
			}

			servicioUsuarios.desvincularRolesUsuario(rolesFiltrados, parameters.getUserId(),aplicacion.getApiKey().getValue(), String.valueOf(aplicacion.getUserId().intValue()));

			AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(aplicacion.getApiKey().getValue());
			UtilEmail.enviarEmail(parameters.getNotificarUsuario(), usuariosDTO, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
		} catch (SeguridadException | SIA3Exception e) {
			logger.error("Error desvinculando usuario rol: " + e);
			throw new IGestionarUsuarioDesvincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault("Error desvinculando usuario rol: " + e, "500"));
		}
	}

	/***
	 * Vincular roles usuario
	 */
	@Override
	public void vincularRolesUsuario(VincularRolesUsuarioRq parameters, Aplicacion aplicacion, EncabezadoSeguridad encabezadoSeguridadRq) throws IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage {

		logger.info("Inicia vincular roles");
		List<String> msnError = new ArrayList<>();

		try {
			List<String> msnErrores = new ArrayList<>();
			msnErrores.add(validadorCamposNulos(ROLES, parameters.getRoles()));
			msnErrores.add(validadorCamposNulos(NOTIFICAR_USUARIO, parameters.getNotificarUsuario()));
			msnErrores.add(validadorCamposNulos(API_KEY, aplicacion.getApiKey().getValue()));

			String mensajeError = msnErrores.stream().filter(Objects::nonNull).findFirst().orElse(null);
			if (mensajeError != null){
				throw new IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(mensajeError, "400"));
			}

			UsuariosDTO usuariosDTO = buscarUsuario(parameters.getUserId(), parameters.getNombreUsuario(), parameters.getCorreoElectronico());

			if (usuariosDTO == null) {
				logger.error(MSG_USUARIO_NO_VALIDO + " Criterios: ID=" + parameters.usuarioId + ", Nombre=" + parameters.nombreUsuario + ", Email=" + parameters.correoElectronico);
				throw new IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(MSG_USUARIO_NO_VALIDO, "400"));
			}

			String apiKeyValue = aplicacion.getApiKey().getValue();
			BigDecimal aplicacionId = new BigDecimal(apiKeyValue);
			for (String rol : parameters.getRoles().getString()) {
				Roles existe = negocioRoles.consultarRolePorNombreYAplicacion(rol, aplicacionId);
				logger.info("Validacion de rol respuesta: " + existe + " ,rol: " + rol);

				if (existe != null && existe.getEstado().equals(new BigDecimal(1))) {
					List<UsuariosDTO> usuariosDTOS = new ArrayList<>();
					usuariosDTOS.add(usuariosDTO);

					List<UsuariosRol> usuariosRoles = manejadorUsuariosRol.buscarUsuariosRolXUsuarioApp(parameters.getUserId(), aplicacionId);
					boolean rolVinculado = usuariosRoles.stream()
							.anyMatch(usuarioRol -> usuarioRol.getUsuariosRolPK().getRolId().equals(existe.getRolId()));
					if (rolVinculado) {
						logger.warn("Rol ya esta asociado al usuario en la aplicación");
						msnError.add("El rol: " + rol + " ya esta asociado al usuario: " + usuariosDTO.getUsuarioId() + " en la aplicación: " + aplicacionId);
						continue;
					}

					// Obtener el rolid correcto de la variable "existe"
					BigDecimal rolId = existe.getRolId();

					negocioUsuariosRol.agregarUsuariosARol(usuariosDTOS, rolId, aplicacionId.toString());
				} else {
					logger.warn("No se puede vincular por que rol no existe: " + rol);
					msnError.add("No se puede vincular por que rol no existe: " + rol);
				}
			}
			if (msnError.size() == parameters.getRoles().getString().size()) {
				throw new IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(String.join(", ", msnError), "400"));
			}
			AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(aplicacion.getApiKey().getValue());
			UtilEmail.enviarEmail(parameters.getNotificarUsuario(), usuariosDTO, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		} catch (SIA3Exception e) {
			logger.error("Error vinculando usuario rol: " + e);
			throw new IGestionarUsuarioVincularRolesUsuarioMensajeFaultFaultFaultMessage(ERROR, messageFault("Error vinculando usuario rol: " + e, "500"));
		}
	}

	/***
	 * Actualizar email usuario
	 */
	@Override
	public void actualizarEmail(ActualizarEmailRq parameters, Aplicacion aplicacion, EncabezadoSeguridad encabezadoSeguridadRq) throws IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage {
		logger.info("Inicia actualizar email ");

		try {
			List<String> msnError = new ArrayList<>();
			msnError.add(validadorCamposMaxLdap(CORREO, parameters.getEmail(), CamposLdap.DESCRIPTION, Constantes.VAL_EMAIL_MAX));
			msnError.add(validadorCamposNulos(NOTIFICAR_USUARIO, parameters.getNotificarUsuario()));

			String mensajeError = msnError.stream().filter(Objects::nonNull).findFirst().orElse(null);
			if (mensajeError != null){
				throw new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage(ERROR, messageFault(mensajeError, "400"));
			}

			Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); // NOSONAR
			Matcher mather = pattern.matcher(parameters.getEmail());
			if (!mather.find()) {
				throw new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage(ERROR, messageFault("Error: El correo ingresado no tiene el formato adecuado", "400"));
			}

			UsuariosDTO usuariosDTO = negocioUsuario.buscarUsuario(parameters.getUserId());
			if (usuariosDTO == null) {
				logger.warn(MSG_USUARIO_NO_VALIDO + parameters.getUserId());
				throw new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage(ERROR, messageFault(MSG_USUARIO_NO_VALIDO, "400"));
			}

			List<UsuariosDTO> usuariosBDList = negocioUsuario.getUsuarioPorAppExiste(new BigDecimal(parameters.getUserId()), aplicacion.getApiKey().getValue());
			if (usuariosBDList.isEmpty()) {
				throw new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage(ERROR, messageFault(APP_NOT_ALLOWED, "400"));
			}

			servicioUsuarios.actualizarEmail(String.valueOf(parameters.getUserId()), parameters.getEmail(), aplicacion.getApiKey().getValue(), String.valueOf(aplicacion.getUserId().intValue()));
			AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(aplicacion.getApiKey().getValue());
			UtilEmail.enviarEmail(parameters.getNotificarUsuario(), usuariosDTO, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		} catch (SIA3Exception e) {
			throw new IGestionarUsuarioActualizarEmailMensajeFaultFaultFaultMessage(ERROR, messageFault(new SeguridadException(e.getMessage())));
        }
    }

	@Override
	public void actualizarDatosBasicos(ActualizarDatosBasicosRq parameters, Aplicacion aplicacion, EncabezadoSeguridad encabezadoSeguridadRq) throws IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage {
		logger.info("Inicia proceso para actualizar datos basicos");

		List<String> msnError = new ArrayList<>();
		msnError.add(validadorCamposNulos("usuarioPeticion", aplicacion.getUserId()));
		msnError.add(validadorCamposNulos(USER_ID, parameters.getUserId()));
		msnError.add(validadorCamposNulos(NOTIFICAR_USUARIO, parameters.getNotificarUsuario()));
		msnError.add(validadorCamposNulos(API_KEY, aplicacion.getApiKey().getValue()));
		msnError.add(validadorCamposMaxLdap("apellido", parameters.getApellidos(), CamposLdap.SURNAME, Constantes.VAL_SN_GIVENAME_MAX));
		msnError.add(validadorCamposMaxLdap("nombre", parameters.getNombres(), CamposLdap.GIVE_NAME, Constantes.VAL_SN_GIVENAME_MAX));
		msnError.add(validadorCamposMaxLdap("numero de documento", parameters.getNumeroDocumento(), CamposLdap.DESCRIPTION, Constantes.VAL_DESCRIPTION_MAX));
		msnError.add(validadorCamposMaxLdap("ruta del directorio", parameters.getRutaDirectorio(), CamposLdap.DESCRIPTION, Constantes.VAL_DESCRIPTION_MAX));

		String mensajeError = msnError.stream().filter(Objects::nonNull).findFirst().orElse(null);
		if (mensajeError != null){
			throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault(mensajeError, "400"));
		}

		UsuariosDTO usuario = new UsuariosDTO();
		usuario.setApellidosUsuario(parameters.getApellidos());
		usuario.setNombres(parameters.getNombres());
		usuario.setNumeroDocumento(parameters.getNumeroDocumento());
		usuario.setRuta(parameters.getRutaDirectorio());

		UsuariosDTO usuariosDTO = negocioUsuario.buscarUsuario(String.valueOf(aplicacion.getUserId().intValue()));
		if (usuariosDTO == null) {
			logger.warn("actualizardatosbasicos Usuario enviando no existe: usuarioPeticion: " + aplicacion.getUserId());
			throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault("El usuario no existe. Usuario: " + aplicacion.getUserId(), "400"));
		}

		UsuariosDTO usuariosModificarDTO = negocioUsuario.buscarUsuario(parameters.getUserId());
		if (usuariosModificarDTO == null) {
			logger.warn("actualizardatosbasicos Usuario enviando no existe: uerId: " + parameters.getUserId());
			throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault("El usuario no existe. Usuario: " + parameters.getUserId(), "400"));
		}

		try {
			List<UsuariosDTO> usuariosBDList = negocioUsuario.getUsuarioPorAppExiste(BigDecimal.valueOf(Long.parseLong(parameters.getUserId())), aplicacion.getApiKey().getValue());
			if (usuariosBDList.isEmpty()) {
				throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault(APP_NOT_ALLOWED, "400"));
			}
		} catch (SIA3Exception e) {
			throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault(APP_NOT_ALLOWED, "400"));
		}

		try {
			List<RolesDTO> rolDTOList = negocioRoles.getRolesPorUsuarioIdAplicacionId(Long.valueOf(parameters.getUserId()), new BigDecimal(aplicacion.getApiKey().getValue()));
			boolean rolVinculado = rolDTOList.stream().anyMatch(usuarioRol -> usuarioRol.getEstado().toString().equals("1"));
			if(!rolVinculado) {
				throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault("Usuario no está vinculado a la aplicación cliente", "400"));
			}
		}catch (SIA3Exception e) {
			throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault("Usuario no está vinculado a la aplicación cliente", "400"));
		}

		try {
			servicioUsuarios.actualizarDatosBasicos(usuario, aplicacion.getApiKey().getValue(), parameters.getUserId(), aplicacion.getUserId().toString());
			AplicacionesDTO aplicacionesDTO = negocioAplicacion.buscarAplicacion(aplicacion.getApiKey().getValue());
			UtilEmail.enviarEmail(parameters.getNotificarUsuario(), usuariosModificarDTO, aplicacionesDTO.getNombre(), parametrosSng.obtenerParametros());
		} catch (SeguridadException e) {
			logger.error("Error actualizando datos basicos: " + e);
			throw new IGestionarUsuarioActualizarDatosBasicosMensajeFaultFaultFaultMessage(ERROR, messageFault("Error actualizando datos basicos: " + e, "500"));
		}

	}


	/**
	 * Arma la respuesta para el servicio de creacion
	 *
	 * @param usuario
	 * @return
	 */
	private CrearUsuarioExternoRs armarRespuestaCracion(UsuariosDTO usuario) {
		CrearUsuarioExternoRs crearResponse = new CrearUsuarioExternoRs();
		IdentificadorUsuario identificador = new IdentificadorUsuario();
		ObjectFactory factory = new ObjectFactory();
		identificador.setUserId(Integer.valueOf(usuario.getUsuarioId()));
		identificador.setNombreUsuario(usuario.getLogonName());
		crearResponse.setInformacionUsuario(factory.createCrearUsuarioExternoRsInformacionUsuario(identificador));
		return crearResponse;
	}

	/**
	 * Setea un rol en caso que no sea nulo
	 *
	 * @param jaxbElement
	 * @return
	 */
	private String setearRol(JAXBElement<InformacionRol> jaxbElement) {
		if ((jaxbElement != null) &&
				(jaxbElement.getValue() != null) &&
				(jaxbElement.getValue().getRol() != null) &&
				(jaxbElement.getValue().getRol().getValue() != null)) {
			return jaxbElement.getValue().getRol().getValue();
		}
		return null;
	}

	/**
	 * Obtiene el valor de un JAXBElement
	 *
	 * @param campo
	 * @return
	 */
	private String obtenerValor(JAXBElement<String> campo) {
		if (campo == null) {
			return null;
		} else {
			return campo.getValue();
		}
	}

	/**
	 * Arma la respuesta de un MessageFault
	 *
	 * @param e
	 * @return
	 */
	private MensajeFault messageFault(SeguridadException e) {
		MensajeFault messageFault = new MensajeFault();
		messageFault.setMensaje(setearCampoJax(e.getMessage()));
		if(e.getId() != null){
			messageFault.setCodigo(setearCampoJax(e.getId().toString()));
		}

		return messageFault;
	}

	/**
	 * Arma la respuesta de un MessageFault con un String
	 *
	 * @param mensaje, codigo
	 * @return
	 */
	private MensajeFault messageFault(String mensaje, String codigo) {
		MensajeFault messageFault = new MensajeFault();
		messageFault.setMensaje(setearCampoJax(mensaje));
		messageFault.setCodigo(setearCampoJax(codigo));
		return messageFault;
	}

	/**
	 * Lenna la informacion del usuario
	 *
	 * @param listaUsuarios
	 * @return
	 */
	private ArrayOfinformacionUsuario llenarInfoUsuarios(List<UsuariosDTO> listaUsuarios) {
		ArrayOfinformacionUsuario infoUsuario = new ArrayOfinformacionUsuario();

		for (UsuariosDTO usuario : listaUsuarios) {
			InformacionUsuario infoUsuarioTemp = new InformacionUsuario();
			infoUsuarioTemp.setApellidos(setearCampoJax(usuario.getApellidosUsuario()));
			infoUsuarioTemp.setEmail(setearCampoJax(usuario.getEmailUsuario()));
			infoUsuarioTemp.setNombres(setearCampoJax(usuario.getNombres()));
			infoUsuarioTemp.setNombreUsuario(setearCampoJax(usuario.getNombreUsuario()));
			infoUsuarioTemp.setTipo(verificarTipoUsuario(usuario.getTipo()));
			infoUsuarioTemp.setUserId(Integer.parseInt(usuario.getUsuarioId()));
			infoUsuario.getInformacionUsuario().add(infoUsuarioTemp);

		}
		return infoUsuario;
	}

	/**
	 * Verifica los diferentes tipos de usuario
	 *
	 * @param tipoUsuario
	 * @return
	 */
	private TipoUsuario verificarTipoUsuario(BigDecimal tipoUsuario) {
		if (Constantes.TIPO_USUARIO_EXTERNO_N.equals(tipoUsuario)) {
			return TipoUsuario.EXTERNO;
		} else {
			return TipoUsuario.INTERNO;
		}
	}

	/**
	 * setea un campo JAXBElement
	 *
	 * @param campo
	 * @return
	 */
	private JAXBElement<String> setearCampoJax(String campo) {
		ObjectFactoryUtils factory = new ObjectFactoryUtils();
		JAXBElement<String> campoReturn = null;
		if (campo != null) {
			campoReturn = factory.createString(campo);
		}
		return campoReturn;
	}

	/**
	 * Arma un usuario DTO apartir de la data recibida
	 *
	 * @param infoUsuario
	 * @return
	 */
	private UsuariosDTO armarUsuarioDto(InformacionUsuario infoUsuario) {
		UsuariosDTO usuarioDto = new UsuariosDTO();
		usuarioDto.setApellidosUsuario(setearCampo(infoUsuario.getApellidos()));
		usuarioDto.setEmailUsuario(setearCampo(infoUsuario.getEmail()));
		usuarioDto.setLoginUsuario(setearCampo(infoUsuario.getNombreUsuario()));
		usuarioDto.setNombres(setearCampo(infoUsuario.getNombres()));
		usuarioDto.setNombreUsuario(setearCampo(infoUsuario.getNombreUsuario()));
		usuarioDto.setUsuarioId(infoUsuario.getUserId()==null?null:infoUsuario.getUserId()+"");
		if (infoUsuario.getTipo() != null)
			usuarioDto.setTipo(setearTipoUsuario(infoUsuario.getTipo()));
		String tipoIdentificacion = setearCampo(infoUsuario.getTipoIdentificacion());

		if (((tipoIdentificacion != null) && (!tipoIdentificacion.equals("")))
				&& ((infoUsuario.getNumeroIdentificacion() != null)
				&& (!infoUsuario.getNumeroIdentificacion().toString().equals("")))) {
			usuarioDto.setLogonName(tipoIdentificacion + infoUsuario.getNumeroIdentificacion().toString());
			usuarioDto.setNumeroDocumento(tipoIdentificacion + " " + infoUsuario.getNumeroIdentificacion().toString());
		}
		return usuarioDto;
	}

	/**
	 * Setea el tipo de usuario a la respuesta
	 *
	 * @param tipo
	 * @return
	 */
	private BigDecimal setearTipoUsuario(TipoUsuario tipo) {
		if (Constantes.TIPO_USUARIO_EXTERNO.equals(tipo.value())) {
			return Constantes.TIPO_USUARIO_EXTERNO_N;
		} else {
			return Constantes.TIPO_USUARIO_INTERNO_N;
		}
	}

	/**
	 * setea un String a partir de un JAXBElement
	 *
	 * @param campo
	 * @return
	 */
	private String setearCampo(JAXBElement<String> campo) {
		if (campo != null && campo.getValue() != null) {
			return campo.getValue();
		}
		return null;
	}

	/**
	 *
	 * @param campo
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 3/04/2017
	 */
	private void validarCampos(Integer campo) throws SeguridadException {
		if ((campo == null) || (campo == 0)) {
			throw procesarErrorRe(new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS));
		}
	}

	/**
	 *
	 * @param campo
	 * @param longitud
	 * @throws SeguridadException
	 * @author hfabra
	 * @since 3/04/2017
	 */
	private void validarCampos(String campo, Long longitud) throws SeguridadException {
		if ((campo != null) && (!campo.equals("")) && (campo.length() > longitud)) {
			throw procesarErrorRe(new SeguridadException(SeguridadException.ID_MSG_ERROR_DATOS_NO_VALIDOS));
		}
	}

	@Override
	public void actualizarPassword(ActualizarPasswordRq parameters, Aplicacion aplicacion, EncabezadoSeguridad encabezadoSeguridadRq) throws IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage {
		logger.info("Inicia actualizar password ");
		try {
			List<String> msnError = new ArrayList<>();
			msnError.add(validadorCamposNulos(API_KEY, aplicacion.getApiKey().getValue()));
			msnError.add(validadorCamposNulos(USER_ID, parameters.getUserId()));
			msnError.add(validadorCamposNulos("UsuarioPeticion", aplicacion.getUserId()));
			msnError.add(validadorCamposNulos("Password", parameters.getPassword()));
			msnError.add(validadorCamposNulos(NOTIFICAR_USUARIO, parameters.getNotificarUsuario()));

			String mensajeError = msnError.stream().filter(Objects::nonNull).findFirst().orElse(null);
			if (mensajeError != null){
				throw new IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage(ERROR, messageFault(mensajeError, "400"));
			}

			Matcher mather = PASSWORD_PATTERN.matcher(parameters.getPassword());
			if (!mather.find()) {
				throw new IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage(ERROR, messageFault("Error: La clave ingresado no tiene el formato adecuado", "400"));
			}

			UsuariosDTO usuariosDTO = negocioUsuario.buscarUsuario(aplicacion.getUserId().toString());
			if (usuariosDTO == null) {
				logger.warn("Usuario Peticion no existe o no es valido: " + parameters.getUserId());
				throw new IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage(ERROR, messageFault("Usuario no existe o no es valido", "400"));
			}

			UsuariosDTO usuariosModificarDTO = negocioUsuario.buscarUsuario(aplicacion.getUserId().toString());
			if (usuariosModificarDTO == null) {
				logger.warn("Usuario Modificar no existe o no es valido: " + parameters.getUserId());
				throw new IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage(ERROR, messageFault("Usuario Modificar no existe o no es valido", "400"));
			}
			servicioAutenticacion.modificarPassword(Integer.valueOf(parameters.getUserId()), parameters.getPassword(), aplicacion.getApiKey().getValue(), aplicacion.getUserId().toString());
		} catch (SeguridadException e) {
			throw new IGestionarUsuarioActualizarPasswordMensajeFaultFaultFaultMessage(ERROR, messageFault(e));
		}
	}

	private String validadorCamposNulos(String nombreCampo, Object valorCampo) {
		logger.warn("El " + nombreCampo + " enviando es: " + valorCampo);
		if (valorCampo == null) {
			return "El "+nombreCampo+" no puede ser nulo";
		}
		String strValor = valorCampo.toString();
		if (strValor.equals("0") || strValor.isEmpty()) {
			return "El "+nombreCampo+" no puede ser vacio o 0";
		}
		return null;
	}

	private String validadorCamposMaxLdap(String nombreCampo, String valorCampo, CamposLdap campoLdap, Integer maximoDefecto) {
		String error = validadorCamposNulos(nombreCampo, valorCampo);
		if (error != null){
			return error;
		}
		boolean isValid = validarMaximoCampo(campoLdap, valorCampo, maximoDefecto);
		if(isValid){
			return "El "+nombreCampo+" ingresado supera el número máximo de caractéres permitidos";
		}
		return null;
	}

	private UsuariosDTO buscarUsuario(String usuarioId, String nombreUsuario, String correoElectronico) {

		int contador = 0;

		if (usuarioId != null && !usuarioId.trim().isEmpty()) {
			contador++;
		}
		if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
			contador++;
		}
		if (correoElectronico != null && !correoElectronico.trim().isEmpty()) {
			contador++;
		}

		if (contador == 0) {
			logger.error(ERROR_DATOS_NULOS);
			throw new IllegalArgumentException(ID_ERROR_DATOS_NULOS.toString().concat(ERROR_DATOS_NULOS));
		}

		if (contador > 1) {
			logger.error(ERROR_SOLO_UN_VALOR);
			throw new IllegalArgumentException(ERROR_SOLO_UN_VALOR);
		}

		UsuariosDTO usuariosDTO;

		if (usuarioId != null && !usuarioId.trim().isEmpty()) {
			usuariosDTO = negocioUsuario.buscarUsuario(usuarioId);
		} else if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
			usuariosDTO = negocioUsuario.buscarUsuarioPorNombre(nombreUsuario);
		} else {
			usuariosDTO = negocioUsuario.buscarUsuarioPorCorreoElectronico(correoElectronico);
		}
		return usuariosDTO;
	}
}
