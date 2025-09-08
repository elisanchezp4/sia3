package co.gov.mineducacion.seguridad.modelo.utils;

import java.math.BigDecimal;

/**
 *
 * Clase que parametriza constantes del sistema
 * HBT agrega al final apartado de constantes exclusivas de app seguridad
 *
 */

public class Constantes {
	private Constantes(){/* Recomendacion por sonar */}

	public static final String RUTA_PROPERTIES_LDAP = "resources.configuracionLdap";
	public static final String RUTA_PROPERTIES_MENSAJES = "mensajes_es";
	public static final String RUTA_PROPERTIES_ETIQUETAS = "resources.etiquetas_es";
	public static final String ESTRUCTURA_MENSAJE = "MSJ_MEN_WS_";
	public static final String CADENA_VACIA = "";
	public static final String PATH_CONTEXT_MAIL = "/seguridad-web-pub";

	public static final String UTF_16LE = "UTF-16LE";

	public static final String RUTA_ARBOL_LDAT = "DC=tsi DC=domain, DC=sia3";

	public static final BigDecimal ID_SIA3 = new BigDecimal(35);

	public static final String LDAP_LOGIN = "CN=";
	public static final String LDAP_FILTER_INACTIVE = "(&(objectClass=user)(objectCategory=person)(!(useraccountcontrol:1.2.840.113556.1.4.803:=2)))";
	public static final String ESTADO_ACTIVO = "A";
	public static final String RUTA_EMAIL_PROPERTIES = "resources.email";

	public static final String TIPO_TOKEN_AUTORIZACION = "AUTH";
	public static final String TIPO_TOKEN_ACCESO = "SESS";

	public static final String TIPO_USUARIO_EXTERNO = "Externo";
	public static final String TIPO_USUARIO_INTERNO = "Interno";

	public static final BigDecimal TIPO_USUARIO_EXTERNO_N = new BigDecimal(5);
	public static final BigDecimal TIPO_USUARIO_INTERNO_N = new BigDecimal(6);

	public static final BigDecimal ESTADO_ACTIVO_S = new BigDecimal(1);
	public static final BigDecimal ESTADO_ACTIVO_N = new BigDecimal(2);

	public static final BigDecimal ID_TIPO_TOKEN_AUTORIZACION = new BigDecimal(3);
	public static final BigDecimal ID_TIPO_TOKEN_ACCESO = new BigDecimal(4);

	public static final BigDecimal ID_TIPO_USUARIO_EXTERNO = new BigDecimal(5);
	public static final BigDecimal ID_TIPO_USUARIO_INTERNO = new BigDecimal(6);

	public static final String ROL_GESTIONADOR = "GESTIONADOR";
	public static final String ROL_AUTENTICADOR = "AUTENTICADOR";
	public static final BigDecimal ROL_SIA3_ADMIN_ID = new BigDecimal(108);
	public static final BigDecimal ESTADO_VINCULADO_ID = new BigDecimal(1);
	public static final BigDecimal MINUTOS_VIGENCIA_CAMBIO_CLAVE_DEFECTO = new BigDecimal(180);

	public static final String AUDITAR_ROL = "AUDITAR_ROL";
	public static final String AUDITAR_OPERACION = "AUDITAR_OPERACION";

	public static final String SI = "S";
	public static final String NO = "N";

	public static final Integer SECONDS = 60;
	public static final Long MILISECONDS = 1000L;
	public static final int LONGITUD_CODIGO = 16;

	public static final int LONGITUD_CODIGO_USUARIO = 50;
	public static final int LONGITUD_CLAVE_USUARIO = 30;
	public static final int LONGITUD_CAPTCHA = 6;

	public static final int UNO = 1;
	public static final int CERO = 0;
	public static final int INTENTOS_GENERACION_TOKEN = 5;
	public static final int INTENTOS_GENERACION_CLAVE = 3;

	// Eventos Auditoria
	public static final BigDecimal EVT_LOGIN_OK = new BigDecimal(7);
	public static final BigDecimal EVT_UPDATE_PASSWORD_FAIL = new BigDecimal(15);
	public static final BigDecimal EVT_LOGOUT_FAIL = new BigDecimal(16);
	public static final BigDecimal EVT_LOGIN_FAIL = new BigDecimal(17);
	public static final BigDecimal EVT_USER_INACTIVE = new BigDecimal(18);
	public static final BigDecimal EVT_USER_NOT_ANY_APP = new BigDecimal(20);
	public static final BigDecimal EVT_TOKEN_NOT_USER = new BigDecimal(21);
	public static final BigDecimal EVT_TOKEN_NOT_FOUND = new BigDecimal(22);
	public static final BigDecimal EVT_USER_NOT_FOUND = new BigDecimal(23);
	public static final BigDecimal EVT_USER_NOT_EXTERNAL = new BigDecimal(24);
	public static final BigDecimal EVT_USER_NOT_REGISTERED_LDAP = new BigDecimal(25);
	public static final BigDecimal EVT_USER_NOT_ROLE = new BigDecimal(26);
	public static final BigDecimal EVT_CODIGO_NO_VIGENTE = new BigDecimal(27);
	public static final BigDecimal EVT_UPDATED_TOKEN_DATE = new BigDecimal(28);
	public static final BigDecimal EVT_USER_REGISTERED = new BigDecimal(29);
	public static final BigDecimal EVT_ROL_APP_NOT_FOUND = new BigDecimal(31);
	public static final BigDecimal EVT_USER_MODIFIED = new BigDecimal(32);
	public static final BigDecimal EVT_USER_INACTIVE_SUCCESS = new BigDecimal(33);
	public static final BigDecimal EVT_CONSULTA_EXITOSA = new BigDecimal(35);
	public static final BigDecimal EVT_USER_CREATED = new BigDecimal(50);
	public static final BigDecimal EVT_USER_CHANGED_ATTRIBUTE = new BigDecimal(42);
	public static final BigDecimal EVT_USER_REMOVED_ROL = new BigDecimal(67);
	public static final BigDecimal EVT_USER_ASSIGNED = new BigDecimal(60);
	public static final BigDecimal EVT_USER_ADDED_ROL = new BigDecimal(70);
	public static final BigDecimal EVT_USER_CHANGED_PASSWORD = new BigDecimal(68);
	public static final BigDecimal EVT_USER_FORGET_PASSWORD = new BigDecimal(69);
	public static final BigDecimal EVT_USER_UNASSIGNED = new BigDecimal(62);

	// Inicio constantes HBT

	// Constantes tipo catalogo
	public static final String HBT_PERSISTENCE_UNIT = "SeguridadDS";
	// constante para id app seguridad
	public static final BigDecimal HBT_ID_APP_SEGURIDAD = new BigDecimal(35);
	// constantes para manejo de intervalos minutos vigencia token
	public static final BigDecimal HBT_MINIMO_MINUTOS = new BigDecimal(1);
	public static final BigDecimal HBT_MAXIMO_MINUTOS = new BigDecimal(99);
	public static final BigDecimal HBT_MINIMO_MINUTOS_PASSWORD = new BigDecimal(1);
	public static final BigDecimal HBT_MAXIMO_MINUTOS_PASSWORD = new BigDecimal(999);
	// constantes para nombres OU de ramas del LDAP
	public static final String HBT_OU_GENERAL = "GENERAL";
	public static final String HBT_OU_INACTIVOS = "INACTIVOS";
	public static final String HBT_OU_EXTERNOS = "Usuarios Externos";
	public static final String ESTADO_DESVINCULADO = "Desvinculado";
	public static final String ESTADO_VINCULADO = "Vinculado";
	// Fin constantes HBT

	// Constantes de Cola Weblogic
	public static final String JMS_SIA3_QUEUE = "jms/SIA3Queue";
	public static final String JMS_SIA3_CONNECTION_FACTORY = "jms/SIA3ConnectionFactory";
	public static final String DESTINATION_NAME = "destinationName";
	public static final String JAVAX_JMS_QUEUE = "javax.jms.Queue";
	public static final String CONNECTION_FACTORY_JNDI_NAME = "connectionFactoryJndiName";
	public static final String DESTINATION_TYPE = "destinationType";

	//Id Mensajes de respuesta validacion
	public static final Integer ID_ERROR_USER_NO_EXISTS = 1;
	public static final Integer ID_ERROR_ROL_INVALIDO = 2;
	public static final Integer ID_ERROR_DATOS_NO_VALIDOS = 3;
	public static final Integer ID_ERROR_USUARIO_NO_VINCULADO = 4;
	public static final Integer ID_ERROR_USER_NULL = 5;
	public static final Integer ID_ERROR_NOMBRE_USUARIO_NULL = 13;
	public static final Integer ID_ERROR_CORREO_ELECTRONICO_USUARIO_NULL = 14;
	public static final Integer ID_ERROR_CONTRASENA_NO_VALIDA = 6;
	public static final Integer ID_ERROR_USUARIO_NO_EXTERNO = 7;
	public static final Integer ID_ERROR_ROLES_NULL = 8;
	public static final Integer ID_ERROR_APLICACION_NULL = 9;
	public static final Integer ID_ERROR_YA_VINCULADO = 10;
	public static final Integer ID_ERROR_YA_DESVINCULADO = 11;
	public static final Integer ID_ERROR_NOTIFICA_USUARIO = 12;
	public static final Integer ID_OPERACION_EXITOSA =100;
	public static final Integer ID_OPERACION_ERROR = 101;

	//Mensajes de respuesta servicios Accesibilidad
	public static final String MENSAJE_OK = "Ok";
	public static final String ERROR_USRID = "El usuario no existe en el sistema";
	public static final String ERROR_SIN_DATOS = "No se pudo obtener la información solicitada";
	public static final String ERROR_PROPIEDAD_NO_EXISTE = "Una de las propiedades no existe en base de datos";
	public static final String ERROR_ESTRUCTURA_INCORRECTA = "Estructura de registros incorrecta";
	public static final String HEADER_PARAM_USERID = "user_id";
	public static final String HEADER_PARAM_TOKEN = "access_token";
	public static final String ERROR_USER_NO_EXISTS = "El usuario no existe";
	public static final String ERROR_DATOS_NO_VALIDOS = "Datos inválidos";
	public static final String ERROR_USUARIO_NO_VINCULADO = "Usuario no está vinculado a la aplicación cliente";
	public static final String ERROR_USER_NULL = "El id usuario no puede se nulo";
	public static final String ERROR_NOMBRE_USUARIO_NULL = "El nombre del usuario no puede se nulo";
	public static final String ERROR_CORREO_ELECTRONICO_USUARIO_NULL = "El correo electronico del usuario no puede se nulo";
	public static final String ERROR_CONTRASENA_NO_VALIDA = "Contraseña inválida o no cumple con el formato";
	public static final String ERROR_USUARIO_NO_EXTERNO = "Usuario no es externo";
	public static final String ERROR_ROLES_NULL = "Los roles no pueden ser nulos";
	public static final String ERROR_APLICACION_NULL = "El id aplicacion no puede ser nulo";
	public static final String ERROR_YA_VINCULADO = "El rol enviado ya se encuentra vinculado para el usuario en la aplicación indicada. ";
	public static final String ERROR_YA_DESVINCULADO = "El rol enviado ya se encuentra desvinculado para el usuario en la aplicación indicada. ";
	public static final String ERROR_NOTIFICA_USUARIO = "notificarUsuario no puede ser nulo";
	public static final String ERROR_ROL_INVALIDO = "Los roles son inválidos, Roles: ";
	public static final String OPERACION_EXITOSA = "Operación exitosa";
	public static final String OPERACION_ERROR = "Operación con error";

	public static final String URL_BASE_SERVICIO_INTEGRACION="https://adintegration-apirest.azurewebsites.net/ADIntegation/";
	public static final String URL_CONSULTAR_TODOS_LOS_USUARIOS = "listarusuarios";
	public static final String URL_ACTUALIZAR_USUARIO= "editarusuario";
	public static final String QUEUE_APPLICATIONS = "app_";
	public static final String QUEUE_APPLICATIONS_MESSAGE = "_user_update_data";
	public static final String URL_BASE_INTEGRACION = "URL_SERVICIO_NET";
	public static final String LLAVE_INTEGRACION = "API_KEY_SERVICIO_NET";
	public static final String HOST_RABBIT_MQ = "HOST_RABBIT_MQ";
	public static final String PORT_RABBIT_MQ = "PUERTO_RABBIT_MQ";
	public static final String USER_RABBIT_MQ = "USUARIO_RABBIT_MQ";
	public static final String PASS_RABBIT_MQ = "PASS_RABBIT_MQ";
	public static final String KEY_PRIVATE = "90d45fd74c63e3fd290d9d68a29e3e3a";

	public static final Integer VAL_SN_GIVENAME_MAX = 65;
	public static final Integer VAL_DESCRIPTION_MAX = 1024;
	public static final Integer VAL_EMAIL_MAX = 256;
}
