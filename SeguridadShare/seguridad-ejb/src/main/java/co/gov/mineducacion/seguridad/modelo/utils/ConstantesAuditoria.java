package co.gov.mineducacion.seguridad.modelo.utils;

public class ConstantesAuditoria {
	private ConstantesAuditoria(){/* Recomendacion por sonar */}

	public static final String LOGIN_OK = "LOGIN_OK"; // Login autorizado
	public static final String LOGIN_FAIL = "LOGIN_FAIL"; // Login fallido
	public static final String USER_INACTIVE = "USER_INACTIVE"; // Usuario inactivo
	public static final String USER_NOT_APP = "USER_NOT_APP"; // Usuario no asociado a aplicacion
	public static final String USER_NOT_ANY_APP = "USER_NOT_ANY_APP"; // Usuario no asociado a alguna aplicacion
	public static final String TOKEN_NOT_USER = "TOKEN_NOT_USER"; // Token no asociado al usuario
	public static final String TOKEN_NOT_FOUND = "TOKEN_NOT_FOUND"; // Token no encontrado
	public static final String USER_NOT_FOUND = "USER_NOT_FOUND"; // Usuario no encontrado en la aplicacion
	public static final String USER_NOT_EXTERNAL = "USER_NOT_EXTERNAL"; // Usuario no es de tipo externo
	public static final String USER_NOT_REGISTERED_LDAP = "USER_NOT_REGISTERED_LDAP"; // Usuario  no registrado en LDAP
	public static final String USER_NOT_ROLE = "USER_NOT_ROLE"; // Usuario no asociado al rol
	public static final String ROLE_FOUND = "ROLE_FOUND"; // Roles encontrados
	public static final String UPDATED_TOKEN_DATE = "UPDATE_TOKEN_DATE"; // Usuario no asociado al rol
	
	public static final String USER_REGISTERED = "USER_REGISTERED"; // Usuario registrado
	public static final String USER_REQUIRED_DATES = "USER_REQUIRED_DATES"; // Datos requeridos en creacion de usuario
	public static final String ROL_APP_NOT_FOUND = "ROL_APP_NOT_FOUND"; // Rol y aplicacion no encontrados en bd
	public static final String USER_MODIFIED = "USER_MODIFIED"; // Usuario modificado
	public static final String USER_INACTIVE_SUCCESS = "USER_INACTIVE_SUCCESS"; // Usuario inactivado con éxito
	public static final String USU_ALREADY_INACTIVE = "USU_ALREADY_INACTIVE"; // Usuario ya inactivo
	public static final String CONSULTA_EXITOSA = "CONSULTA_EXITOSA"; // Usuario ya inactivo

}
