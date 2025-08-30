package co.gov.mineducacion.seguridad.ejb.servicios;

import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.enums.CamposLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.utils.IntegradorDA;
import co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Realiza todas las funciones que tengan que ver con el LDAP
 *
 * @author Michal Murgueitio
 */
public class ServicioLDAP {
    private ServicioLDAP(){/* Recomendacion por sonar */}

    private static final Logger logger = Logger.getLogger(ServicioLDAP.class);

    /**
     * Busca un usuario por el login del usuario
     *
     * @param loginUsuario
     * @param entryDn
     * @return
     * @throws SeguridadException
     */
    public static UsuarioLdap buscarUsuarioPorLogin(String loginUsuario, String entryDn, Properties propiedades) throws SeguridadException {
        UsuarioLdap resultado;
        try {
            resultado = IntegradorDA.buscarUsuarioPorLogin(loginUsuario, entryDn, propiedades);
        } catch (Exception e) {
            logger.error("Se presentó error en el método buscarUsuarioPorLogin:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);
        }
        logger.info("Finaliza buscar usuario por login DAP" + getJson(resultado));
        return resultado;
    }

    public static String getJson(Object object){
        if( object == null)
            return null;
        return new Gson().toJson(object);
    }


    /**
     * Verifica que existe el usuario.
     *
     * @param usuario
     * @param password
     * @throws SeguridadException
     */
    public static void verificarExisteUsuario(String usuario, String password, Properties propiedades) throws SeguridadException {

        try {

            boolean existe = IntegradorDA.validarUsuario(usuario, password, propiedades);
            if (!existe) {
                throw new SeguridadException(SeguridadException.ID_MSG_ERROR_USU_NO_EXISTE);
            }

        } catch (SeguridadException e) {

            throw e;

        } catch (Exception e) {

            logger.error("Se presentó error en el método verificarExisteUsuario:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);

        }

    }


    /**
     * Crea un usuario en el directorio activo con los campos recibidos por el
     * usuario LDAP
     *
     * @param usuario
     * @throws SeguridadException
     */
    public static void crearUsuario(UsuarioLdap usuario, Properties propiedades) throws SeguridadException {
        try {
            LdapValidacionesUtil.validarUsuario(usuario);
            LdapValidacionesUtil.verificarCampo(CamposLdap.PASSWORD, usuario.getUserPassword());
            IntegradorDA.crearUsuario(usuario, propiedades);
        } catch (SeguridadException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Se presentó error en el método crearUsuario:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);
        }
    }

    /**
     * Modifica un usuario en el directorio activo con los campos recibidos por
     * el usuario LDAP
     *
     * @param usuario
     * @throws SeguridadException
     */
    public static void modificarUsuario(UsuarioLdap usuario, Properties propiedades) throws SeguridadException {
        logger.info("Inicia proceso modificar usuario servicio LDAP: " + new Gson().toJson(usuario) + ", propiedades: " + new Gson().toJson(propiedades));
        try {
            LdapValidacionesUtil.validarUsuario(usuario);
            IntegradorDA.modificarUsuario(usuario, propiedades);
            logger.info("Finaliza con exito modificar usuario servicio LDAP");
        }
        catch(SeguridadException e){
            throw e;
        }
        catch (Exception e) {
            logger.error("Se presentó error en el metodo modificarUsuario:  " + new Gson().toJson(e));
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);
        }
    }

    /**
     * Modifica el password del usuario cuyo identificador se recibe por parametro
     *
     * @param password  nueva clave a asignar
     * @param logonName identificador (login) del usuario al que se le cambiará la clave
     * @throws SeguridadException
     */
    public static void modificarPassword(String password, String logonName, Properties propiedades) throws SeguridadException {
        logger.info("Inicia comando para modificar contrasena en LDAP: " + logonName);
        try {
            LdapValidacionesUtil.verificarCampo(CamposLdap.PASSWORD, password);
            IntegradorDA.modificarPassword(logonName, password, propiedades);
            logger.info("Finaliza proceso modificacion contrasena LDAP");
        }
        catch(SeguridadException ex){
            throw ex;
        }
        catch (Exception e) {
            logger.error("Se presentó error en el método modificarPassword:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);
        }
    }

    /**
     * Valida que el usuario que se recibe por parámetro exista en el directorio activo.
     *
     * @param loginUsuario identificador del usuario a consultar
     * @param entryDN      si contiene 'Usuario Externos' se consulta en la rama de usuarios externos,
     *                     sino se consulta en el arbol general
     * @throws SeguridadException
     */
    public static void validarUsuarioExisteLDAP(String loginUsuario, String entryDN, Properties propiedades) throws SeguridadException {
        try {
            logger.info("Inicia proceso en LDAP para validar si existe un usuario: " + loginUsuario);
            UsuarioLdap usr;
            usr = IntegradorDA.buscarUsuarioPorLogin(loginUsuario, entryDN, propiedades);
            if (usr == null) {
                throw new SeguridadException("El usuario no existe en el sistema o no está activo.", TipoExcepcion.ERROR);
            }
        } catch (SeguridadException e) {
            logger.error("Se presentó error en el método modificarPassword:  " + e.getMessage(), e);
            throw new SeguridadException("El usuario no existe en el sistema o no está activo.", TipoExcepcion.ERROR);
        }
    }


    // Inicio metodos HBT

    /**
     * Metodo que lista usuarios del LDAP por mas de un criterio de busqueda
     *
     * @param logonName
     * @param tipoUsuario
     * @param nombres
     * @param apellidos
     * @param email
     * @return
     * @throws SeguridadException
     */
    public static List<UsuarioLdap> listarUsuariosPorFiltros(String logonName, String nombres,
                                                             String apellidos, String email, String entryDn, Properties propiedades) throws SeguridadException {
        logger.info("Inicio metodo en servicio consultarUsuarioPorFiltros HBT con parametros logonName:" + logonName
                + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:"
                + email + "entryDn: " + entryDn);

        List<UsuarioLdap> usuarioLdapList = new ArrayList<>();
        try {


            usuarioLdapList = IntegradorDA.listarUsuariosPorFiltros(logonName, nombres, apellidos, email, entryDn, propiedades);

        } catch (SeguridadException e) {

            throw e;

        } catch (Exception e) {

            logger.error("Se presentó error en el método listarUsuariosPorFiltros:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);

        }

        return usuarioLdapList;

    }

    public static List<UsuarioLdap> listaUserInactivos(Properties propiedades) throws SeguridadException {

        logger.info("Entro Al metodo de servicio Inactivos");

        List<UsuarioLdap> usuarioLdapList = new ArrayList<>();

        try {

            usuarioLdapList = IntegradorDA.listarUsuariosActivosInactivos(Boolean.FALSE, propiedades);

        } catch (SeguridadException e) {

            throw e;

        } catch (Exception e) {

            logger.error("Se presentó error en el método listaUserInactivos:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);

        }

        return usuarioLdapList;
    }

    public static List<UsuarioLdap> listaUserActivos(Properties propiedades) throws SeguridadException {

        logger.info("Entro al metodo de Usuarios Activos");

        List<UsuarioLdap> usuarioLdapList = new ArrayList<>();

        try {

            usuarioLdapList = IntegradorDA.listarUsuariosActivosInactivos(Boolean.TRUE, propiedades);

        } catch (SeguridadException e) {

            throw e;

        } catch (Exception e) {

            logger.error("Se presentó error en el método listaUserActivos:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);

        }

        return usuarioLdapList;

    }

    public static void activarUser(UsuarioLdap usuario, Properties propiedades) throws SeguridadException {

        logger.info("Activar User");

        try {

            IntegradorDA.activarUsuario(usuario.getsAMAccountName(), propiedades);

        } catch (SeguridadException e) {

            throw e;

        } catch (Exception e) {

            logger.error("Se presentó error en el método listaUserActivos:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);

        }

    }

    public static void desactivarUser(UsuarioLdap usuario, Properties propiedades) throws SeguridadException {

        logger.info("Desactivar User");

        try {

            IntegradorDA.inactivarUsuario(usuario.getsAMAccountName(), propiedades);

        } catch (SeguridadException e) {

            throw e;

        } catch (Exception e) {

            logger.error("Se presentó error en el método listaUserActivos:  " + e.getMessage(), e);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA);

        }
    }


    // Fin metodos HBT
}
