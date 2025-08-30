package co.gov.mineducacion.seguridad.modelo.excepciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilProperties;

/**
 * Excepcion para el manejo de errores dentro del sistema SIA3 permite definir
 * la prioridad de los errores y anidarlos uno con otro.
 *
 * @author stilaguy
 * @version 1.0
 * @created 11-nov-2016 09:55:22 a.m.
 */
public class SeguridadException extends Exception {
	private static final long serialVersionUID = -1877514126146793809L;

	// Mensajes de error general
	public static final long ROL_NO_EXISTE = 95;
	public static final long APLICACION_NO_EXISTE = 96;
	public static final long ROL_NO_PERTENECE_APLICACION = 97;
	public static final long NO_CONTROLADA = 98;
	public static final long ERROR_CONN_LDAP = 99;
	public static final long ERROR_ENVIANDO_EMAIL = 100;
	public static final long ID_MSG_ERROR_CONSULTAR = 101;
	public static final long ID_MSG_ERROR_USU_EXISTE = 102L;
	public static final long ID_MSG_ERROR_TIPO_DATO = 103L;

	// Mensajes auditoria Autenticar usuario
	public static final long ID_MSG_ERROR_USU_NO_EXISTE = 8L;
	public static final long ID_MSG_ERROR_USU_INACTIVO = 9L;
	public static final long USER_NO_PERTENECE_APLICACION = 10L;
	public static final long USER_NOT_ANY_APP = 11L;
	public static final long TOKEN_USU_NO_ASOCIADO = 12L;
	public static final long TOKEN_NO_ENCONTRADO = 13L;
	public static final long USUARIO_NO_ENCONTRADO = 14L;
	public static final long ID_MSG_USUARIO_NO_EXTERNO = 15L;
	public static final long USUARIO_NO_ENCONTRADO_LDAP = 16L;
	public static final long ID_MSG_ERROR_USU_NOT_ROLE = 17L;
	public static final long ID_ROLES_ENCONTRADOS = 18L;
	public static final long ID_MSG_UPDATED_TOKEN_DATE = 19L;
	public static final long ID_MSG_SESION_CADUCADA = 41L;
	// Mensajes auditoria Gestionar usuario
	public static final long ID_MSG_ERROR_DATOS_NO_VALIDOS = 21L;
	public static final long ID_MSG_USUARIO_MODIFICADO = 23L;
	public static final long ID_MSG_USUARIO_INACT_EXITO = 24L;
	public static final long ID_MSG_USUARIO_YA_INACTIVO = 25L;
	public static final long ID_MSG_USAURIO_NO_ADMINISTRADOR = 26L;
	public static final long ID_MSG_CODIGO_NO_VIGENTE = 27L;
	public static final long USUARIO_NO_ENCONTRADO_INACTIVAR = 38L;
	public static final long ID_MSG_USUARIO_NO_EXTERNO_SIA3 = 39L;
	public static final long ID_MSG_USUARIO_NO_EXTERNO_LDAP = 40L;

	//HBT - Mensajes auditoria. Autor: jfonseca
	public static final long USER_CREATED = 50L;
	public static final long APP_CREATED = 51L;
	public static final long APP_DELETED = 52L;
	public static final long APP_MODIFIED = 53L;
	public static final long ROLE_CREATED = 54L;
	public static final long ROLE_MODIFIED = 55L;
	public static final long ROLE_DELETED = 56L;
	public static final long OPERATION_CREATED = 57L;
	public static final long OPERATION_DELETED = 58L;
	public static final long OPERATION_MODIFIED = 59L;
	public static final long USER_ASSIGNED_APP = 61L;
	public static final long ROLE_IMPORTED = 63L;
	public static final long ROLE_EXPORTED = 64L;
	public static final long OPERATION_IMPORTED = 65L;
	public static final long OPERATION_EXPORTED = 66L;

	public static final long USER_IMPORT_ROL = 78;

	public static final long USER_IMPORT_OPERATION = 79;

	public static final long USER_EXPORT_ROL = 80;

	public static final long USER_EXPORT_OPERATION = 81;

	// Mensajes pantalla
	public static final long ID_MSG_WEB_USUARIO_NO_ENCONTRADO = 28L;
	public static final long ID_MSG_WEB_USUARIO_NO_EXTERNO = 29L;
	public static final long ID_MSG_WEB_USU_INACTIVO = 30L;
	public static final long ID_MSG_WEB_ERROR_ENVIANDO_EMAIL = 31L;
	public static final long ID_MSG_WEB_ENVIO_EXITOSO = 32L;
	public static final long ID_MSG_WEB_CAMPOS_OBLIGATORIOS = 33L;
	public static final long ID_MSG_WEB_PASS_NO_COINCIDEN = 34L;
	public static final long ID_MSG_WEB_PASS_EXITOSO = 35L;
	public static final long ID_MSG_WEB_CAPTCHA_NO_COINCIDE = 36L;
	public static final long ID_MSG_WEB_ERROR_USU_INACTIVO = 37L;
	public static final long APP_INACTIVA = 200L;
	public static final long ID_MSG_WEB_CAMPO_OBLIGATORIO = 201L;
	public static final long ID_MSG_ERR_LONGITUD_CAMPOS = 202L;
	public static final long ID_MSG_ERR_APP_NO_ENCONTRADA = 203L;
	public static final long ID_MSG_ERR_IMP_COM_LDAP = 204L;
	public static final long ID_MSG_ERR_IMP_GENERAR_TOKEN = 205L;
	public static final long ID_MSG_ERR_CLAVE_DEBIL = 206L;
	public static final long ID_MSG_USR_NO_ROLES_ACTIVOS = 207L;
	public static final long ID_MSG_USR_NO_OPERACIONES = 208L;
	public static final long ID_MSG_TOKEN_INVALIDO_CAMBIO_CONTRASENIA = 209L;
	public static final long ID_MSG_ERROR_CAMBIO_CONTRASENIA = 210L;
	public static final long ID_MSG_ERROR_USUARIO_EXISTE_LDAP = 211L;
	public static final long ID_MSG_ERROR_DLL_NO_CARGADA = 212L;
	public static final long ID_MSG_ERROR_CAMPO_LDAP_INVALIDO = 213L;

	public static final long USUARIO_SIN_EMAIL = 213L;
	public static final long SE_PERDIO_COMUNICACION_LDAP = 9520L;

	private transient ResourceBundle mensajes; // NOSONAR
	private List<SeguridadException> errores;
	private String[] parametros;
	private TipoExcepcion tipo;
	private Long id;
	private String detalle;

	public SeguridadException(TipoExcepcion tipo) {
		this.tipo = tipo == null ? TipoExcepcion.INFO : tipo;
		errores = new ArrayList<>();
	}

	public SeguridadException(String mensaje) {
		super(mensaje);
		this.detalle = mensaje;
		this.tipo = TipoExcepcion.ERROR;
		errores = new ArrayList<>();
	}

	public SeguridadException(long id, TipoExcepcion tipo) {
		UtilProperties.cargarPropiedad(getMensajes(), Constantes.ESTRUCTURA_MENSAJE + id, null);
		this.tipo = tipo == null ? TipoExcepcion.INFO : tipo;
		this.id = id;
		errores = new ArrayList<>();
	}

	public SeguridadException(long id) {
		this.detalle=UtilProperties.cargarPropiedad(getMensajes(), Constantes.ESTRUCTURA_MENSAJE + id, null);
		this.tipo = TipoExcepcion.ERROR;
		this.id = id;
		errores = new ArrayList<>();
	}

	public SeguridadException(long id, String[] parametros, TipoExcepcion tipo) {
		UtilProperties.cargarPropiedad(getMensajes(), Constantes.ESTRUCTURA_MENSAJE + id, parametros);
		this.tipo = tipo == null ? TipoExcepcion.INFO : tipo;
		this.id = id;
		this.parametros = parametros;
		errores = new ArrayList<>();
	}

	public SeguridadException(long id, String mensaje, String[] parametros) {
		super(mensaje);
		this.tipo = TipoExcepcion.INFO;
		this.id = id;
		errores = new ArrayList<>();
		this.parametros = parametros;
	}

	public SeguridadException(long id, TipoExcepcion tipo, String mensaje, String[] parametros) {
		super(mensaje);
		this.tipo = tipo == null ? TipoExcepcion.INFO : tipo;
		this.id = id;
		errores = new ArrayList<>();
		this.parametros = parametros;
	}

	public SeguridadException(TipoExcepcion tipo, String mensaje) {
		super(mensaje);
		this.tipo = tipo;
		errores = new ArrayList<>();
	}

	public SeguridadException(String message, Long id) {
		super(message);
		this.id = id;
		this.tipo = TipoExcepcion.ERROR;
		errores = new ArrayList<>();
	}
	public SeguridadException(String mensaje, TipoExcepcion tipoExcepcion) {
		super(mensaje);
		this.tipo = tipoExcepcion;
	}
	public SeguridadException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Permite anidar una excepcion a la excepcion maestra.
	 *
	 * @param error
	 *            : error a adicionar.
	 */
	public void agregarError(SeguridadException error) {
		errores.add(error);
	}

	public List<SeguridadException> getErrores() {
		return errores;
	}

	public TipoExcepcion getTipo() {
		return tipo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Long getId() {
		return id;
	}

	public String[] getParametros() {
		return parametros;
	}

	public ResourceBundle getMensajes() {
		if (mensajes == null) {
			mensajes = ResourceBundle.getBundle("mensajes", new Locale("es"));
		}
		return mensajes;
	}

	public void setMensajes(ResourceBundle mensajes) {
		this.mensajes = mensajes;
	}

}
