package co.gov.mineducacion.seguridad.modelo.utils;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;

/***
 * Clase utilitaria de conexiones con el LDAP
 * 
 * @author Michael Murgueitio
 *
 */
public class ConexionLDAP {
	private ConexionLDAP(){/* Recomendacion por sonar */}

	private static final Logger logger = Logger.getLogger(ConexionLDAP.class);
	private static DirContext ldapContextGlobalCatalog;
	private static SearchControls controls;

	private static ResourceBundle mensajes = ResourceBundle.getBundle(Constantes.RUTA_PROPERTIES_LDAP);

	/**
	 * Se conecta al repositorio activo
	 * 
	 * @return
	 * @throws SeguridadException
	 * 
	 */
	public static DirContext getConeccion() throws SeguridadException {
		DirContext ldapContext;
		try {
				Hashtable<String, String> ldapEnv = new Hashtable<>(11);
				ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.INITIAL_CONTEXT_FACTORY));
				ldapEnv.put(Context.PROVIDER_URL,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PROVIDER_URL));
				ldapEnv.put(Context.SECURITY_AUTHENTICATION,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_AUTHENTICATION));
				ldapEnv.put(Context.SECURITY_PRINCIPAL,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_PRINCIPAL));
				ldapEnv.put(Context.SECURITY_CREDENTIALS,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_CREDENTIALS));
				ldapEnv.put("com.sun.jndi.ldap.read.timeout", "5000");
				ldapContext = new InitialDirContext(ldapEnv);
		} catch (Exception errorConexion) {
			logger.error(errorConexion.getMessage(), errorConexion);
			throw new SeguridadException(SeguridadException.ERROR_CONN_LDAP);
		}
		
		return ldapContext;
	}
	
	/**
	 * Se conecta al repositorio activo
	 * 
	 * @return
	 * @throws SeguridadException
	 * 
	 */
	public static DirContext getConeccionSSL() throws SeguridadException {
		DirContext 	ldapContextSSL;
		try {
				Hashtable<String, String> ldapEnv = new Hashtable<>(11);
				ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.INITIAL_CONTEXT_FACTORY));
				ldapEnv.put(Context.PROVIDER_URL,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PROVIDER_URL_SSL));
				ldapEnv.put(Context.SECURITY_AUTHENTICATION,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_AUTHENTICATION));
				ldapEnv.put(Context.SECURITY_PRINCIPAL,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_PRINCIPAL));
				ldapEnv.put(Context.SECURITY_CREDENTIALS,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_CREDENTIALS));
				ldapEnv.put(Context.SECURITY_PROTOCOL, ConstantesLDAP.SSL);
				
				
				System.setProperty("javax.net.ssl.trustStore", UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PATH_KEYSTORE));
				System.setProperty("javax.net.ssl.keyStore",UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PATH_KEYSTORE));
				System.setProperty("javax.net.ssl.trustAnchors", UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PATH_KEYSTORE));
				System.setProperty("javax.net.ssl.trustStorePassword", UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PASSWORD_KEYSTORE));
				System.setProperty("javax.net.ssl.keyStorePassword", UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PASSWORD_KEYSTORE));
				
				ldapContextSSL = new InitialDirContext(ldapEnv);
			} catch (Exception errorConexion) {
				logger.error(errorConexion.getMessage(), errorConexion);
				throw new SeguridadException(SeguridadException.ERROR_CONN_LDAP);
			}
		
		return ldapContextSSL;
	}

	/**
	 * Se conecta al repositorio activo
	 * 
	 * @return
	 * @throws SeguridadException
	 * 
	 */
	public static Boolean getConeccion(String usuario, String password) throws SeguridadException {
		DirContext ldapCtxUsr = null;
		try {
			Hashtable<String, String> ldapEnv = new Hashtable<>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,
					UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.INITIAL_CONTEXT_FACTORY));
			ldapEnv.put(Context.PROVIDER_URL, UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PROVIDER_URL));
			ldapEnv.put(Context.SECURITY_AUTHENTICATION,
					UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_AUTHENTICATION));
			ldapEnv.put(Context.SECURITY_PRINCIPAL, usuario);
			ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
			ldapCtxUsr = new InitialDirContext(ldapEnv);
			return Boolean.TRUE;
		} catch (Exception errorConexion) {
			return Boolean.FALSE;
		} finally{
			try{
				if(ldapCtxUsr != null){
					ldapCtxUsr.close();
				}
			}catch(Exception e){
				logger.error("Imposible cerrar el contexto ldap: " + e.getMessage(), e);
			}
		}

	}

	
	/**
	 * Controles de busqueda
	 * 
	 * @return
	 */
	public static SearchControls getSearchControls() {
		if (controls == null) {
			controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			controls.setCountLimit(ConstantesLDAP.LIMITE_REGISTROS);
			String[] attrIDs = { ConstantesLDAP.mail, ConstantesLDAP.SN, ConstantesLDAP.name,
					ConstantesLDAP.userPassword, ConstantesLDAP.Uid, ConstantesLDAP.sAMAccountName,
					ConstantesLDAP.givenName, ConstantesLDAP.OU, ConstantesLDAP.cn};
			controls.setReturningAttributes(attrIDs);
		}
		return controls;
	}

	
	//Inicio metodos HBT
	/**
	 * Metodo para conectarse al catalogo global del LDAP
	 * 
	 * @return
	 * @throws SeguridadException
	 */
	public static DirContext getConeccionGlobalCatalog() throws SeguridadException {
		try {
			logger.info("Se incia intento conexion");
			
			if (ldapContextGlobalCatalog == null) {
				logger.info("Se verifico el  ldapContextGlobalCatalog");
				Hashtable<String, String> ldapEnv = new Hashtable<>(11);
				ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.INITIAL_CONTEXT_FACTORY));
				ldapEnv.put(Context.PROVIDER_URL,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.PROVIDER_URL_GLOBAL_CATALOG));
				ldapEnv.put(Context.SECURITY_AUTHENTICATION,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_AUTHENTICATION));
				ldapEnv.put(Context.SECURITY_PRINCIPAL,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_PRINCIPAL));
				ldapEnv.put(Context.SECURITY_CREDENTIALS,
						UtilProperties.cargarPropiedad(mensajes, ConstantesLDAP.SECURITY_CREDENTIALS));
				ldapEnv.put("com.sun.jndi.ldap.read.timeout", "5000");
				logger.info("Se asiganaron valores conexion");
				ldapContextGlobalCatalog = new InitialDirContext(ldapEnv);
				
				logger.info("Conexion exitosa ldap" + ldapContextGlobalCatalog);
			}
		} catch (Exception errorConexion) {
			logger.error(errorConexion.getMessage(), errorConexion);
			throw new SeguridadException(SeguridadException.ERROR_CONN_LDAP);
		}
		logger.info("Retorno Conexion exitosa ldap" + ldapContextGlobalCatalog);
		return ldapContextGlobalCatalog;
	}
	//Fin metodos HBT
}
