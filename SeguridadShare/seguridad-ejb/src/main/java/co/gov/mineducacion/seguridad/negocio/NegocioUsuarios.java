package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.ejb.servicios.ServicioLDAP;
import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRol;
import co.gov.mineducacion.seguridad.modelo.entidades.Roles;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioExterno;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRolPK;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperacionesRol;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorRoles;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuariosRol;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import co.gov.mineducacion.seguridad.modelo.utils.TipoExcepcion;
import co.gov.mineducacion.seguridad.modelo.utils.UtilEmail;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;
import co.gov.mineducacion.seguridad.modelo.utils.UtilToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

// protected region Incluya importaciones adicionales en esta seccion on begin

// protected region Incluya importaciones adicionales en esta seccion end

/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad Usuarios
 *
 * @author jsoto
 */
@Stateless
@Transactional
public class NegocioUsuarios extends NegocioAbstracto<Usuario, UsuariosDTO> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ManejadorUsuarios manejadorUsuarios;

    @EJB
    private ParametrosSng parametrosSng;

    // Inicio variables HTB
    @EJB
    private NegocioAplicaciones negocioAplicaciones;

    @EJB
    private NegocioBitacoraAuditoria auditoria;
    @EJB
    private ManejadorRoles roles;

    @EJB
    private NegocioRoles negocioRoles;
    @EJB
    private ManejadorUsuariosRol usuariosRol;
    @EJB
    private NegocioUsuariosRol negocioUsuariosRol;
    @EJB
    private ManejadorOperacionesRol manejadorOperacionesRol;
    // Fin variables HBT

    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(NegocioUsuarios.class.getName());
    private String mesagge = "fin metodo getUsuariosPorAppRol. Retorna lista: ";
    private String mesaggeError = "Error en metodo getUsuariosPorAppRol: ";
    private String mesaggeApp = "y aplicacionId: ";

    // protected region Declare atributos adicionales en esta seccion on begin

    // protected region Declare atributos adicionales en esta seccion end

    /**
     * Realiza un consulta en la entidad Usuarios aplicando los filtros, el
     * ordenamiento, y el rango (from y to) que se pasan como parámetro. Los
     * parámetros filterBy y orderBy pueden ser nulos. El parámetro from y to
     * están relacionados. Si from es diferente de nulo to puedo ser nulo, pero
     * no al revés. Ambos pueden ser nulos, en cuyo caso no se aplica una
     * restricción de rango a la consulta.
     *
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere filtrar, seguido por un operador de comparación que
     *                 puede tomar los valores
     *                 {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
     *                 por último el valor por el que se quiere filtrar. Los filtros
     *                 se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     *                 Ej. Una secuencia de parámetros de filtrado puede ser
     *                 {@literal usuariosId>1&usuariosName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser: usuariosId$ASC,
     *                 usuariosName$DESC
     * @param from     Número de registro inicial que se quiere retornar de la
     *                 consulta realizada. Entero mayor o igual a 0
     * @param to       Número de registro final que se quiere retornar de la consulta
     *                 realizada. Entero mayor o igual al parámetro from
     * @return Una lista de DAOs de los Usuarios que se consultaron con los
     * parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url
     *                                   tenía un error de sintáxis por lo que no pudo ser procesado
     *                                   correctamente
     */
    public List<UsuariosDTO> consultar(String filterBy, String orderBy, Integer from, Integer to)
            throws InvalidParameterException {
        // protected region Modifique el metodo consultar on begin
        logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
        RangoConsulta rango = validarParametrosBloque(from, to);

        return convertirListaEntidadesADao(manejadorUsuarios.consultar(filtros, ordenamiento, rango));
        // protected region Modifique el metodo consultar end
    }

    /**
     * Crea el usuarios que se pasa como parámetro en la base de datos.
     *
     * @param usuariosDTO El DAO de la entidad Usuarios a crear. Este se envía en el
     *                    cuerpo de la solicitud POST como un objeto JSON. HBT agrega
     *                    validaciones para dto de usuario
     * @return La insntancia de Usuarios recién creado
     * @throws SIA3Exception
     * @Hbt se modfica metodo para poder obtener cuando se crear usuario externo
     * el nombre de usuario del directorio activo y para generar los insert
     * que faltaban en la tabla USUARIOS_ROL Lineas afectadas 171 - 234
     */
    public UsuariosDTO crear(UsuariosDTO usuariosDTO) throws SIA3Exception {
        // protected region Modifique el metodo crear on begin
        try {
            logService(this.getClass().getName(), "crear", usuariosDTO);
            validarUsuarios(usuariosDTO);
            Usuario usuarios = new Usuario();
            copiarPropiedades(usuarios, usuariosDTO);

            // Inicio sentencias HBT
            // Setear fecha de creacion y modificacion
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            usuarios.setFechaCreacion(timestampActual);
            usuarios.setUltimaModificacion(timestampActual);
            // Fin sentencias HBT
            logger.info("se crear en la entidad");
            manejadorUsuarios.crear(usuarios);
            usuariosDTO.setUsuarioId(usuarios.getUsuarioId());
            logger.info("CREO en la entidad");
            if (Constantes.ID_TIPO_USUARIO_EXTERNO.equals(usuariosDTO.getTipo())) {
                // se consulta el usuario en el directorio activo para obtener el nombre usuario
                UsuarioLdap usuarioLdap = armarUsuarioLdap(usuariosDTO);

                if (usuarioLdap.getsAMAccountName() != null && !"".equals(usuarioLdap.getsAMAccountName())) {

                    Usuario usuarioNuevo = manejadorUsuarios.getUsuarioPorNombre(usuarioLdap.getsAMAccountName());
                    usuariosDTO.setNombreUsuario(usuarioLdap.getsAMAccountName().toUpperCase());
                    if (usuarioNuevo != null) {
                        usuariosDTO.setUsuarioId(usuarioNuevo.getUsuarioId());
                    } else {
                        logger.error("Error no encontro  resultados para el Usuario en el directorio activo");
                        throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR);
                    }

                    // Crear relacion en tabla USUARIOS_ROL para rol enviado
                    // externo
                    List<Roles> idRolExterno = roles.buscarRolPorNombre(usuariosDTO.getNombreRol());
                    if (idRolExterno != null && !idRolExterno.isEmpty()) {
                        usuariosRol.crear(armarUsuariosRol(usuarios, idRolExterno.get(0).getRolId()));
                    } else {

                        logger.error("Error no encontro  resultados para el rol enviado");
                        throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR);
                    }

                    // Crear relacion en tabla USUARIOS_ROL para GESTIONADOR
                    List<Roles> idGestionador = roles.buscarRolPorNombre(Constantes.ROL_GESTIONADOR);
                    if (idGestionador != null && !idGestionador.isEmpty()) {
                        usuariosRol.crear(armarUsuariosRol(usuarios, idGestionador.get(0).getRolId()));
                    } else {
                        logger.error("Error no encontro  resultados para el rol enviado");
                        throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR);
                    }

                    // Crear relacion en tabla USUARIOS_ROL para AUTENTICADOR
                    List<Roles> idAutenticador = roles.buscarRolPorNombre(Constantes.ROL_AUTENTICADOR);
                    if (idAutenticador != null && !idAutenticador.isEmpty()) {
                        usuariosRol.crear(armarUsuariosRol(usuarios, idAutenticador.get(0).getRolId()));
                    } else {
                        logger.error("Error no encontro  resultados para el rol enviado");
                        throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR);
                    }

                } else {

                    logger.error(
                            "Error en metodo consultarUsuarioPorNombre: No se encontraron usuarios en el LDAP con parametros nombres:"
                                    + usuariosDTO.getNombres() + ". apellidos:" + usuariosDTO.getApellidosUsuario());
                    throw new SIA3Exception(MessagesConstants.APP100097);
                }

            } else {

                usuariosDTO.setUsuarioId(usuarios.getUsuarioId());
            }
            logger.info("Retorna usuariosDTO " + usuariosDTO.getLogonName() + usuariosDTO.getUsuarioId());
            return usuariosDTO;
        } catch (SIA3Exception se) {
            logger.error("Error al validar usuarioDTO al crear Usuario->" + se.getCause());
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de crear Usuario->" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    public UsuarioExterno createExternalUser(UsuarioExterno usuarioExterno) throws SIA3Exception {
        //validarUsuarios();
        return null;
    }

    /**
     * Se arma usuarioRol para insertar en tabla intermedia
     *
     * @param usuarios
     * @param rolId
     * @return
     * @throws SeguridadException
     */
    public UsuariosRol armarUsuariosRol(Usuario usuarios, BigDecimal rolId){

        UsuariosRol rol = new UsuariosRol();
        Roles rolIdUsuario = new Roles();

        rolIdUsuario.setRolId(rolId);
        rol.setUsuarios(usuarios);
        rol.setRoles(rolIdUsuario);
        UsuariosRolPK userPkAutenticador = new UsuariosRolPK();

        userPkAutenticador.setRolId(rolId);
        userPkAutenticador.setUsuarioId(usuarios.getUsuarioId());

        rol.setUsuariosRolPK(userPkAutenticador);

        rol.setFechaVinculacion(new Date(UtilToken.obtenerFechaActual().getTime()));
        rol.setEstadoVinculacion(Constantes.ESTADO_VINCULADO);

        return rol;
    }

    /**
     * Crea el usuario (cuya información se almacena en un objeto tipo UsuariosDTO) recibido por parametro en el sistema
     *
     * @param usuariosDTO contiene la información del usuario a crear
     * @return
     * @throws SeguridadException
     */
    public Usuario crearManual(UsuariosDTO usuariosDTO) throws SeguridadException {
        logService(this.getClass().getName(), "crear", usuariosDTO);
        try {
            Usuario usuarios = new Usuario();
            copiarPropiedades(usuarios, usuariosDTO);
            em.persist(usuarios);
            em.flush();

            return usuarios;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.ERROR);
        }
    }

    /**
     * Crea el usuario (cuya información se almacena en un objeto tipo Usuario) recibido por parametro en el sistema
     *
     * @param usuarios contiene la información del usuario a crear
     * @return
     * @throws SeguridadException
     */
    public Usuario crearManual(Usuario usuarios) throws SeguridadException {
        logService(this.getClass().getName(), "crear", usuarios);
        try {
            em.persist(usuarios);
            em.flush();

            return usuarios;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.ERROR);
        }
    }

    /**
     * Crea el usuario recibido por parameetro y lo relaciona con el rol recibido por parametor
     *
     * @param usuarios
     * @param rolBD
     * @return
     * @throws SeguridadException
     */
    public Usuario crearCompleto(Usuario usuarios, Roles rolBD) throws SeguridadException {
        logService(this.getClass().getName(), "crear", usuarios);
        try {
            em.persist(usuarios);
            em.flush();

            UsuariosRol usuariorRol = new UsuariosRol();

            usuariorRol.setRoles(rolBD);
            usuariorRol.setUsuarios(usuarios);

            UsuariosRolPK userPk = new UsuariosRolPK();
            userPk.setRolId(rolBD.getRolId());
            userPk.setUsuarioId(usuarios.getUsuarioId());
            usuariorRol.setUsuariosRolPK(userPk);

            ArrayList<UsuariosRol> arreglo = new ArrayList<>();
            arreglo.add(usuariorRol);
            usuarios.setUsuariosRolList(arreglo);

            em.merge(usuarios);
            em.flush();

            return usuarios;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SeguridadException(SeguridadException.NO_CONTROLADA, TipoExcepcion.ERROR);
        }
    }

    /**
     * Actualiza en la base de datos el usuarios que se pasa como parámetro.
     *
     * @param usuariosDTO El DAO de la entidad Usuarios a actualizar. Este se envía en
     *                    el cuerpo de la solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad Usuarios que ha sido actualizado
     */
    public UsuariosDTO actualizar(UsuariosDTO usuariosDTO, Usuario usuarios) {
        // protected region Modifique el metodo actualizar on begin
        try {
            logService(this.getClass().getName(), "actualizar", usuariosDTO);

            copiarPropiedades(usuarios, usuariosDTO);

            manejadorUsuarios.actualizar(usuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuariosDTO;
        // protected region Modifique el metodo actualizar end
    }

    /**
     * Actualiza en la base de datos el usuarios que se pasa como parámetro.
     *
     * @param usuariosDTO El DAO de la entidad Usuarios a actualizar. Este se envía en
     *                    el cuerpo de la solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad Usuarios que ha sido actualizado
     */
    public UsuariosDTO actualizar(UsuariosDTO usuariosDTO) {
        // protected region Modifique el metodo actualizar on begin
        try {
            logger.info("Inicia comando para actualizar usuario: " + new Gson().toJson(usuariosDTO));
            Usuario usuarios = manejadorUsuarios.buscar(usuariosDTO.getUsuarioId());
            copiarPropiedades(usuarios, usuariosDTO);

            manejadorUsuarios.actualizar(usuarios);
            logger.info("Finaliza actualizacion usuario");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return usuariosDTO;
        // protected region Modifique el metodo actualizar end
    }

    /**
     * Elimina el usuarios con el identificador que se pasa como parámetro.
     *
     * @param usuarioId Valor del atributo del identificador de la instancia de la
     *                  entidad usuarios a eliminar
     * @return El identificador del usuarios que ha sido eliminado
     */
    public String eliminar(@QueryParam("usuarioId") String usuarioId) {
        // protected region Modifique el metodo eliminar on begin

        logService(this.getClass().getName(), "eliminar", usuarioId);
        manejadorUsuarios.eliminarPorId(usuarioId);

        StringBuilder valores = new StringBuilder();
        valores.append(String.valueOf(usuarioId));
        return valores.toString();
        // protected region Modifique el metodo eliminar end
    }

    /**
     * Cuenta la cantidad de registros que devuelve la consulta a la tabla de
     * aplicando los filtros o rangos que se pasen como parámetro. Estos pueden
     * ser nulos, en cuyo caso a la consulta no se le realiza ningún tipo de
     * filtrado.
     *
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere filtrar, seguido por un operador de comparación que
     *                 puede tomar los valores
     *                 {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
     *                 por último el valor por el que se quiere filtrar. Los filtros
     *                 se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     *                 Ej. Una secuencia de parámetros de filtrado puede ser
     *                 {@literal usuariosId>1&usuariosName:LIKE:juan}
     * @param from     Número de registro inicial que se quiere retornar de la
     *                 consulta realizada. Entero mayor o igual a 0
     * @param to       Número de registro final que se quiere retornar de la consulta
     *                 realizada. Entero mayor o igual al parámetro from
     * @return El número de registros contados a partir de los parámetros
     * enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url
     *                                   tenía un error de sintáxis por lo que no pudo ser procesado
     *                                   correctamente
     */
    public String contar(String filterBy, Integer from, Integer to) throws InvalidParameterException {
        // protected region Modifique el metodo contar on begin

        logService(this.getClass().getName(), "contar", filterBy);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        RangoConsulta rango = validarParametrosBloque(from, to);

        return String.valueOf(manejadorUsuarios.consultarTotalRegistros(filtros, rango));
        // protected region Modifique el metodo contar end
    }

    /**
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere filtrar, seguido por un operador de comparación que
     *                 puede tomar los valores
     *                 {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y
     *                 por último el valor por el que se quiere filtrar. Los filtros
     *                 se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     *                 Ej. Una secuencia de parámetros de filtrado puede ser
     *                 {@literal usuariosId>1&usuariosName:LIKE:juan}
     * @param orderBy  Cadena de caracteres con los parámetros de ordenamiento. Cada
     *                 parámetro está compuesto por el nombre del campo por el que se
     *                 quiere ordenar, seguido por el símbolo '$' y posteriormente
     *                 por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son
     *                 opcionales ya que si no se especifica por defecto se asume que
     *                 el ordenamiento es de forma Ascendente. Si se coloca más de un
     *                 parámetro debe ir separado por coma : ','. Ej. Una secuencia
     *                 de parámetros de ordenamiento puede ser: usuariosId$ASC,
     *                 usuariosName$DESC
     * @param atributo Nombre del atributo de la entidad Usuarios del cual se quieren
     *                 obtener los diferentes valores.
     * @return Una lista con los diferentes valores que se encuentran en la
     * columna de la tabla asociada al atributo.
     * @throws InvalidParameterException Si el atributo no existe en la entidad o si los filtros y el
     *                                   ordenamiento contienen atributos de la entidad que no
     *                                   existen.
     */
    public List<String> consultarLista(String filterBy, String orderBy, String atributo)
            throws InvalidParameterException {
        // protected region Modifique el metodo consultarLista on begin

        logService(this.getClass().getName(), "contar", filterBy, orderBy, atributo);

        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);
        InformacionAgrupamiento infoAgrupamiento = decodificarInformacionAgrupamiento(atributo);

        return UtilOperaciones.convertirListaObjetosAString(
                manejadorUsuarios.consultarLista(filtros, ordenamiento, infoAgrupamiento));
        // protected region Modifique el metodo consultarLista end
    }

    /**
     * {@inheritDoc}
     *
     * @param nombreAtributo {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean entidadContieneAtributo(String nombreAtributo) {
        return Usuario.contieneAtributo(nombreAtributo);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    protected UsuariosDTO instanciarDAO() {
        return new UsuariosDTO();
    }

    public Usuario consultarUsuario(String nombreUsuario) throws SeguridadException {
        Usuario respuesta;
        try {
            logger.info("consultarUsuario  consulta ==>" + nombreUsuario);
            TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByNombreUsuario", Usuario.class);
            query.setParameter("nombreUsuario", nombreUsuario);
            logger.info("query  consulta ==>" + query.toString());
            respuesta = query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR);
        }
        return respuesta;
    }

    /**
     * Construye un objeto de tipo Usuario con la información del objeto de tipo UsuarioDTO recibido por parámetro
     * y retorna el resultado
     *
     * @param usuarioDto
     * @return
     */
    public Usuario copiarPropiedades(UsuariosDTO usuarioDto) {
        Usuario respuesta = new Usuario();
        try {
            copiarPropiedades(respuesta, usuarioDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Construye un objeto de tipo UsuarioDTO con la información del objeto de tipo Usuario recibido por parámetro
     * y retorna el resultado
     *
     * @param usuarioDto
     * @return
     */
    public UsuariosDTO copiarPropiedades(Usuario usuario) {
        UsuariosDTO respuesta = new UsuariosDTO();
        try {
            copiarPropiedades(respuesta, usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Retorna el usuario (en un objeto tipo UsuariosDTO) cuyo identificador se recibe por parametro. Retorna null si el
     * usuario no es encontrado
     *
     * @param userID
     * @return
     */
    public UsuariosDTO buscarUsuario(String userID) {
        UsuariosDTO userDTO = instanciarDAO();
        Usuario result = manejadorUsuarios.buscar(userID);
        if (result != null) {
            copiarPropiedades(userDTO, result);
        } else {
            userDTO = null;
        }
        return userDTO;

    }

    /**
     * Retorna el usuario (en un objeto tipo Usuario) cuyo identificador se recibe por parametro. Retorna null si el
     * usuario no es encontrado
     *
     * @param idUsuario
     * @return
     * @throws SeguridadException
     */
    public Usuario consultarUsuarioIdUsuario(String idUsuario) throws SeguridadException {
        Usuario respuesta;
        try {
            TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findUsuariosByIdUsuario", Usuario.class);
            query.setParameter("idUsuario", idUsuario);
            respuesta = query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR, TipoExcepcion.ERROR);
        }
        return respuesta;
    }

    /**
     * Retorna los identificadores de los usuarios que pertenece al rol cuyo identificador se recibe por parámetro
     *
     * @param idRol
     * @return
     * @throws SeguridadException
     */
    public List<BigDecimal> buscarIdUsuariosPorRol(BigDecimal idRol) throws SeguridadException {
        List<BigDecimal> respuesta = new ArrayList<>();
        try {
            TypedQuery<BigDecimal> query = em.createNamedQuery("UsuarioRolEntity.findUsuariosByIdRol",
                    BigDecimal.class);
            query.setParameter("idRol", idRol);
            respuesta = query.getResultList();
        } catch (NoResultException ex) {
            return respuesta;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SeguridadException(SeguridadException.ID_MSG_ERROR_CONSULTAR, TipoExcepcion.ERROR);
        }
        return respuesta;
    }

    // Inicio metodos HBT

    /**
     * Metodo que obitene un usuario segun el nombre de usuario pasado por
     * parametro
     *
     * @param nombreUsuario
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public UsuariosDTO consultarUsuarioPorNombre(String nombreUsuario) throws SIA3Exception, SeguridadException {
        try {
            logger.info("Inicio metodo consultarUsuarioPorNombre con nombreUsuario:" + nombreUsuario);
            UsuariosDTO usuarioDTO = new UsuariosDTO();
            // buscar usuario en rama usuarios externos
            logger.info("Buscar usuario en la rama de externos");
            usuarioDTO = consultarRamaLDAPLogin(nombreUsuario,
                    "OU=" + Constantes.HBT_OU_EXTERNOS + "," + Constantes.RUTA_ARBOL_LDAT);
            logger.info("UsuarioDTO obtenido de rama externos->" + usuarioDTO);
            if (usuarioDTO == null) {
                logger.info("Buscar usuario en la rama de internos");
                // buscar usuario en rama usuarios internos
                usuarioDTO = consultarRamaLDAPLogin(nombreUsuario, Constantes.RUTA_ARBOL_LDAT);
                logger.info("UsuarioDTO obtenido de rama internos->" + usuarioDTO);
                if (usuarioDTO == null) {
                    logger.error(
                            "Error en metodo consultarUsuarioPorNombre: No hay usuarios en el direcotrio activo con nombre: "
                                    + nombreUsuario);
                    throw new SIA3Exception(MessagesConstants.APP100095);
                }
            }
            logger.info("Fin metodo negocio consultarUsuarioPorNombre. retorna UsuarioDTO->" + usuarioDTO);
            // retornar usuario encontrado
            return usuarioDTO;
        } catch (SIA3Exception se) {
            logger.error("Error en metodo consultarUsuarioPorNombre:" + se);
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado en metodo consultarUsuarioPorNombre:" + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que complementa la información del DTO de usuario con la que viene
     * del directorio activo segun su loginName
     *
     * @param nombreUsuario
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public UsuariosDTO completarInformacionUsuario(String nombreUsuario) throws SIA3Exception, SeguridadException {
        try {
            logger.info("Inicio metodo completarInformacionUsuario con nombreUsuario:" + nombreUsuario);
            UsuariosDTO usuarioDTO = new UsuariosDTO();
            // buscar usuario en rama usuarios externos
            logger.info("Buscar usuario en la rama de externos");
            usuarioDTO = consultarRamaLDAPLogin(nombreUsuario,
                    "OU=" + Constantes.HBT_OU_EXTERNOS + "," + Constantes.RUTA_ARBOL_LDAT);
            logger.info("UsuarioDTO obtenido de rama externos->" + usuarioDTO);
            if (usuarioDTO == null) {
                logger.info("Buscar usuario en la rama de internos");
                // buscar usuario en rama usuarios internos
                usuarioDTO = consultarRamaLDAPLogin(nombreUsuario, Constantes.RUTA_ARBOL_LDAT);
                logger.info("UsuarioDTO obtenido de rama internos->" + usuarioDTO);
                if (usuarioDTO == null) {
                    return null;
                }
            }
            logger.info("Fin metodo negocio completarInformacionUsuario. retorna UsuarioDTO->" + usuarioDTO);
            // retornar usuario encontrado
            return usuarioDTO;
        } catch (SIA3Exception se) {
            logger.error("Error en metodo completarInformacionUsuario:" + se);
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado en metodo completarInformacionUsuario:" + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que obitene un usuario segun el nombre de usuario pasado por
     * parametro Usa el valor de usuarioSesionId para el registro de eventos de
     * auditoria
     *
     * @param nombreUsuario
     * @param usuarioSesionId
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public UsuariosDTO consultarUsuarioPorNombre(String nombreUsuario, String usuarioSesionId)
            throws SIA3Exception, SeguridadException {
        try {
            logger.info("Inicio metodo consultarUsuarioPorNombre con nombreUsuario:" + nombreUsuario
                    + " y usuarioSesionId:" + usuarioSesionId);
            UsuariosDTO usuarioDTO = new UsuariosDTO();
            // buscar usuario en rama usuarios externos
            logger.info("Buscar usuario en la rama");
            usuarioDTO = consultarRamaLDAPLogin(nombreUsuario,
                    "OU=" + Constantes.HBT_OU_EXTERNOS + "," + Constantes.RUTA_ARBOL_LDAT, usuarioSesionId);
            logger.info("UsuarioDTO obtenido ->" + usuarioDTO);
            if (usuarioDTO == null) {
                logger.info("Buscar usuario en la rama de internos");
                // buscar usuario en rama usuarios internos
                usuarioDTO = consultarRamaLDAPLogin(nombreUsuario, Constantes.RUTA_ARBOL_LDAT, usuarioSesionId);
                logger.info("UsuarioDTO obtenido de rama internos->" + usuarioDTO);
                if (usuarioDTO == null) {
                    logger.error(
                            "Error en metodo consultarUsuarioPorNombre: No hay usuarios en el direcotrio activo con nombre: "
                                    + nombreUsuario);
                    throw new SIA3Exception(MessagesConstants.APP100095);
                }
            }
            logger.info("Fin metodo negocio consultarUsuarioPorNombre. retorna UsuarioDTO->" + usuarioDTO);
            // retornar usuario encontrado
            return usuarioDTO;
        } catch (SIA3Exception se) {
            logger.error("Error en metodo consultarUsuarioPorNombre:" + se);
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado en metodo consultarUsuarioPorNombre:" + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que busca por usuario por Login en la rama del LDAP especificada
     * por parametro
     *
     * @param nombreUsuario
     * @param entryDn
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public UsuariosDTO consultarRamaLDAPLogin(String nombreUsuario, String entryDn)
            throws SIA3Exception, SeguridadException {
        try {
            logger.info(
                    "Inicio metodo consultarRamaLDAPLogin con nombreUsuario:" + nombreUsuario + ", entryDn:" + entryDn);
            // Llama al servicio que consulta usuario en el LDAP
            UsuarioLdap usuarioLDAP = ServicioLDAP.buscarUsuarioPorLogin(nombreUsuario, entryDn, parametrosSng.obtenerParametros());
            UsuariosDTO usuarioDTO = new UsuariosDTO();
            if (usuarioLDAP == null) {
                logger.error("metodo consultarRamaLDAPLogin: No hay usuarios en la rama " + entryDn + " con nombre: "
                        + nombreUsuario);
                return null;
            } else {
                // datos que vienen del ldap
                usuarioDTO.setApellidosUsuario(usuarioLDAP.getSn());
                usuarioDTO.setEmailUsuario(usuarioLDAP.getMail());
                usuarioDTO.setNombres(usuarioLDAP.getGivenName());
                usuarioDTO.setLogonName(nombreUsuario);
                // datos para la base de seguridad
                // Setear fecha de creacion y modificacion
                Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
                usuarioDTO.setFechaCreacion(timestampActual);
                usuarioDTO.setLoginUsuario(nombreUsuario);
                usuarioDTO.setNuevoPass(usuarioLDAP.getUserPassword());
                usuarioDTO.setNumeroDocumento(usuarioLDAP.getUid());
                usuarioDTO.setPassword(usuarioLDAP.getUserPassword());
                usuarioDTO.setRuta(Constantes.RUTA_ARBOL_LDAT);
                usuarioDTO.setUltimaModificacion(timestampActual);
                usuarioDTO.setUsuarioModificacion("1");
                usuarioDTO.setNumeroDocumento(usuarioLDAP.getDescription());
                // Asignar tipo usuario dependiendo la rama donde se busca

                usuarioDTO.setTipo(isOuExterno(usuarioLDAP.getDistinguishedName()));

                // Se crea usuario si no exsite en la base de seguridad
                List<Usuario> usuarioList = manejadorUsuarios.listarUsuariosPorNombre(nombreUsuario);
                if (usuarioList.isEmpty()) {
                    usuarioDTO.setEstado(Constantes.ESTADO_ACTIVO_S);
                    logger.info(
                            "No existe usuario en BD seguridad, por lo tanto se crea con estos datos: " + usuarioDTO);
                    crearUsuarioEnBD(usuarioDTO);
                    // Registrar en auditoria el evento
                    logger.info("Inicio registro auditoria evento USER_CREATED");
                    // Validar que venga el usuario que realiza la operacion
                    if (usuarioDTO.getUsuarioModificacion() == null) {
                        logger.error(
                                "Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
                        throw new SIA3Exception(MessagesConstants.APP100120);
                    }
                    auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_CREATED),
                            usuarioDTO.getUsuarioModificacion(), Constantes.HBT_ID_APP_SEGURIDAD.toString(),
                            "Crea usuario con nombre usuario:" + usuarioDTO.getLogonName() + ", nombres:"
                                    + usuarioDTO.getNombres() + ", apellidos:" + usuarioDTO.getApellidosUsuario());
                    logger.info("Fin registro auditoria evento USER_CREATED");
                }

                // obtener id del usuario almacenado en la BD
                Usuario usuario = manejadorUsuarios.getUsuarioPorNombre(nombreUsuario);
                usuarioDTO.setUsuarioId(usuario.getUsuarioId());
                usuarioDTO.setEstado(usuario.getEstado());
            }
            logger.info("Fin metodo negocio consultarRamaLDAPLogin");
            // retornar datos de usuario encontrado
            return usuarioDTO;
        } catch (PersistenceException e) {
            logger.error("Error en metodo consultarRamaLDAPLogin:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    private BigDecimal isOuExterno(String distinguishedName) {
        if(distinguishedName == null){
            logger.info("Se configura el usuario como externo");
            return Constantes.ID_TIPO_USUARIO_EXTERNO;
        }
        if (distinguishedName.toUpperCase().contains(Constantes.TIPO_USUARIO_EXTERNO.toUpperCase())){
            logger.info("Se configura el usuario como externo");
            return Constantes.ID_TIPO_USUARIO_EXTERNO;
        }else{
            logger.info("Se configura el usuario como interno");
            return Constantes.ID_TIPO_USUARIO_INTERNO;
        }
    }

    /**
     * Metodo que busca por usuario por Login en la rama del LDAP especificada
     * por parametro. Registra eventos de auditoria con el id recibido de
     * usuarioSesionId
     *
     * @param nombreUsuario
     * @param entryDn
     * @param usuarioSesionId
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public UsuariosDTO consultarRamaLDAPLogin(String nombreUsuario, String entryDn, String usuarioSesionId)
            throws SIA3Exception, SeguridadException {
        try {
            logger.info("Inicio metodo consultarRamaLDAPLogin con nombreUsuario:" + nombreUsuario + ", usuarioSesionID:" + usuarioSesionId);
            // validar que venga el id del usuario de sesion
            if (usuarioSesionId == null) {
                logger.error("El campo usuarioSesionId es obligatorio para el registro de auditoria. Valor recibido: "
                        + usuarioSesionId);
                throw new SIA3Exception(MessagesConstants.APP100120);
            }
            // Llama al servicio que consulta usuario en el LDAP
            UsuarioLdap usuarioLDAP = ServicioLDAP.buscarUsuarioPorLogin(nombreUsuario, entryDn, parametrosSng.obtenerParametros());
            UsuariosDTO usuarioDTO = new UsuariosDTO();
            if (usuarioLDAP == null) {
                logger.error("metodo consultarRamaLDAPLogin: No hay usuarios en la rama con nombre: "
                        + nombreUsuario);
                return null;
            } else {
                // datos que vienen del ldap
                usuarioDTO.setApellidosUsuario(usuarioLDAP.getSn());
                usuarioDTO.setEmailUsuario(usuarioLDAP.getMail());
                usuarioDTO.setNombres(usuarioLDAP.getGivenName());
                usuarioDTO.setLogonName(nombreUsuario);
                // datos para la base de seguridad
                // Setear fecha de creacion y modificacion
                Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
                usuarioDTO.setFechaCreacion(timestampActual);
                usuarioDTO.setLoginUsuario(nombreUsuario);
                usuarioDTO.setNuevoPass(usuarioLDAP.getUserPassword());
                usuarioDTO.setNumeroDocumento(usuarioLDAP.getUid());
                usuarioDTO.setPassword(usuarioLDAP.getUserPassword());
                usuarioDTO.setRuta(Constantes.RUTA_ARBOL_LDAT);
                usuarioDTO.setUltimaModificacion(timestampActual);
                usuarioDTO.setUsuarioModificacion(usuarioSesionId);
                // Asignar tipo usuario dependiendo la rama donde se busca
                logger.info("Verificar tipo usuario para usuarioLdap con entryDn-->" + entryDn);
                usuarioDTO.setTipo(isOuExterno(usuarioLDAP.getDistinguishedName()));
                // Se crea usuario si no exsite en la base de seguridad
                List<Usuario> usuarioList = manejadorUsuarios.listarUsuariosPorNombre(nombreUsuario);
                if (usuarioList.isEmpty()) {
                    usuarioDTO.setEstado(Constantes.ESTADO_ACTIVO_S);
                    logger.info(
                            "No existe usuario en BD seguridad, por lo tanto se crea con estos datos: " + usuarioDTO);
                    crearUsuarioEnBD(usuarioDTO);
                    // Registrar en auditoria el evento
                    logger.info("Inicio registro auditoria evento USER_CREATED");
                    // Validar que venga el usuario que realiza la operacion
                    if (usuarioDTO.getUsuarioModificacion() == null) {
                        logger.error(
                                "Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
                        throw new SIA3Exception(MessagesConstants.APP100120);
                    }
                    auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_CREATED),
                            usuarioDTO.getUsuarioModificacion(), Constantes.HBT_ID_APP_SEGURIDAD.toString(),
                            "Crea usuario con nombre usuario:" + usuarioDTO.getLogonName() + ", nombres:"
                                    + usuarioDTO.getNombres() + ", apellidos:" + usuarioDTO.getApellidosUsuario());
                    logger.info("Fin registro auditoria evento USER_CREATED");
                }
                // obtener id del usuario almacenado en la BD
                Usuario usuario = manejadorUsuarios.getUsuarioPorNombre(nombreUsuario);
                usuarioDTO.setUsuarioId(usuario.getUsuarioId());
                usuarioDTO.setEstado(usuario.getEstado());
            }
            logger.info("Fin metodo negocio consultarRamaLDAPLogin");
            // retornar datos de usuario encontrado
            return usuarioDTO;
        } catch (PersistenceException e) {
            logger.error("Error en metodo consultarRamaLDAPLogin:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que lista usuarios del LDAP de acuerdo a varios criterios de
     * busqueda
     *
     * @param logonName
     * @param tipoUsuario
     * @param nombres
     * @param apellidos
     * @param email
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public List<UsuariosDTO> consultarUsuarioPorFiltros(String logonName, BigDecimal tipoUsuario, String nombres,
                                                        String apellidos, String email) throws SIA3Exception, SeguridadException {
        logger.info("Inicio metodo consultarUsuarioPorFiltros con parametros logonName:" + logonName + ". tipoUsuario: "
                + tipoUsuario + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email);
        // Validar que haya al menos un filtro por nombres y apellidos
        if (nombres == null || apellidos == null) {
            logger.error(
                    "Error en metodo consultarUsuarioPorFiltros. Debe haber al menos un filtro de busqueda por nombres y apellidos.");
            throw new SIA3Exception(MessagesConstants.APP100105);
        }
        List<UsuariosDTO> usuariosLdapList = new ArrayList<>();
        List<UsuariosDTO> usuariosLdapExternosList = new ArrayList<>();
        List<UsuariosDTO> usuariosLdapInternosList = new ArrayList<>();

        // seleccionar tipo de usuario
        if (tipoUsuario != null) {
            if (tipoUsuario.compareTo(Constantes.ID_TIPO_USUARIO_EXTERNO) == 0) {
                // si se selecciona externo buscar por la rama de Usuarios
                // Externos
                usuariosLdapExternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                        "OU=" + Constantes.HBT_OU_EXTERNOS + "," + Constantes.RUTA_ARBOL_LDAT);
            }
            if (tipoUsuario.compareTo(Constantes.ID_TIPO_USUARIO_INTERNO) == 0) {
                // si se selecciona interno buscar por la rama de Usuarios
                // Internos
                usuariosLdapInternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                        Constantes.RUTA_ARBOL_LDAT);
            }
        } else {
            // si no viene ningun tipo seleccionado, buscar en todas las ramas
            // 1. Usuarios externos
            usuariosLdapExternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                    "OU=" + Constantes.HBT_OU_EXTERNOS + "," + Constantes.RUTA_ARBOL_LDAT);
            // 2. Usuarios internos
            if (usuariosLdapExternosList.isEmpty()) {
                usuariosLdapInternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                        Constantes.RUTA_ARBOL_LDAT);
            }

        }
        // Unir los resultados de las ramas de internos y externos
        usuariosLdapList.addAll(usuariosLdapExternosList);
        usuariosLdapList.addAll(usuariosLdapInternosList);
        // retornar lista completa
        if (usuariosLdapList.isEmpty()) {
            logger.error(
                    "Error en metodo consultarUsuarioPorFiltros: No se encontraron usuarios en el LDAP con parametros logonName:"
                            + logonName + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email);
            throw new SIA3Exception(MessagesConstants.APP100097);
        } else {
            return usuariosLdapList;
        }
    }

    /**
     * Metodo que lista usuarios del LDAP de acuerdo a varios criterios de
     * busqueda. Usa el campo usuarioSesionId para el registro de eventos de
     * auditoria
     *
     * @param logonName
     * @param tipoUsuario
     * @param nombres
     * @param apellidos
     * @param email
     * @param usuarioSesionId
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public List<UsuariosDTO> consultarUsuarioPorFiltros(String logonName, BigDecimal tipoUsuario, String nombres,
                                                        String apellidos, String email, String usuarioSesionId) throws SIA3Exception, SeguridadException {
        logger.info("Inicio metodo consultarUsuarioPorFiltros con parametros logonName:" + logonName + ". tipoUsuario: "
                + tipoUsuario + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email
                + ", usuarioSesionId:" + usuarioSesionId);
        List<UsuariosDTO> usuariosLdapList = new ArrayList<>();
        List<UsuariosDTO> usuariosLdapExternosList = new ArrayList<>();
        List<UsuariosDTO> usuariosLdapInternosList = new ArrayList<>();

        // seleccionar tipo de usuario
        if (tipoUsuario != null) {
            if (tipoUsuario.compareTo(Constantes.ID_TIPO_USUARIO_EXTERNO) == 0) {
                // si se selecciona externo buscar por la rama de Usuarios
                // Externos
                usuariosLdapExternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                        "OU=" + Constantes.HBT_OU_EXTERNOS + "," + Constantes.RUTA_ARBOL_LDAT, usuarioSesionId);
            }
            if (tipoUsuario.compareTo(Constantes.ID_TIPO_USUARIO_INTERNO) == 0) {
                // si se selecciona interno buscar por la rama de Usuarios
                // Internos
                usuariosLdapInternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                        Constantes.RUTA_ARBOL_LDAT, usuarioSesionId);
            }
        } else {
            // si no viene ningun tipo seleccionado, buscar en todas las ramas
            // 1. Usuarios externos
            usuariosLdapExternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                    "OU=" + Constantes.HBT_OU_EXTERNOS + "," + Constantes.RUTA_ARBOL_LDAT, usuarioSesionId);
            // 2. Usuarios internos
            if (usuariosLdapExternosList.isEmpty()) {
                usuariosLdapInternosList = consultarRamaLDAPFiltros(logonName, nombres, apellidos, email,
                        Constantes.RUTA_ARBOL_LDAT, usuarioSesionId);
            }

        }
        // Unir los resultados de las ramas de internos y externos
        usuariosLdapList.addAll(usuariosLdapExternosList);
        usuariosLdapList.addAll(usuariosLdapInternosList);
        // retornar lista completa
        if (usuariosLdapList.isEmpty()) {
            logger.error(
                    "Error en metodo consultarUsuarioPorFiltros: No se encontraron usuarios en el LDAP con parametros logonName:"
                            + logonName + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email);
            throw new SIA3Exception(MessagesConstants.APP100097);
        } else {
            return usuariosLdapList;
        }
    }

    /**
     * Metodo que consulta los usuarios en la rama del directorio especificada
     * por parametro
     *
     * @param logonName
     * @param nombres
     * @param apellidos
     * @param email
     * @param entryDn
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public List<UsuariosDTO> consultarRamaLDAPFiltros(String logonName, String nombres, String apellidos, String email,
                                                      String entryDn) throws SIA3Exception, SeguridadException {
        logger.info("Inicio metodo consultarRamaLDAPFiltros con parametros logonName:" + logonName + ". nombres:"
                + nombres + ". apellidos:" + apellidos + ". email:" + email);

        List<UsuarioLdap> usuarioLDAPList = ServicioLDAP.listarUsuariosPorFiltros(logonName, nombres, apellidos, email,
                entryDn, parametrosSng.obtenerParametros());
        List<UsuariosDTO> usuariosDTOList = new ArrayList<>();
        if (usuarioLDAPList.isEmpty()) {
            logger.error(
                    "metodo consultarRamaLDAPFiltros: No se encontraron usuarios en el LDAP con parametros logonName:"
                            + logonName + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email);
        } else {
            // Recorrer lista para armar DTO de usuarios con base en la info
            // recibida del LDAP
            logger.info("Registros LDAP encontrados en lista: " + usuarioLDAPList);
            for (UsuarioLdap usuarioLdapLista : usuarioLDAPList) {
                UsuariosDTO usuarioDTO = new UsuariosDTO();
                // datos que vienen del ldap
                usuarioDTO.setApellidosUsuario(usuarioLdapLista.getSn());
                usuarioDTO.setEmailUsuario(usuarioLdapLista.getMail());
                usuarioDTO.setNombres(usuarioLdapLista.getGivenName());
                usuarioDTO.setLogonName(usuarioLdapLista.getsAMAccountName());
                logger.info("LogonName encontrado:" + usuarioLdapLista.getsAMAccountName());
                // datos para la base de seguridad
                // Asignar fecha de creacion y modificacion
                Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
                usuarioDTO.setFechaCreacion(timestampActual);
                usuarioDTO.setLoginUsuario(usuarioLdapLista.getUid());
                usuarioDTO.setNuevoPass(usuarioLdapLista.getUserPassword());
                usuarioDTO.setNumeroDocumento(usuarioLdapLista.getUid());
                usuarioDTO.setPassword(usuarioLdapLista.getUserPassword());
                usuarioDTO.setRuta(Constantes.RUTA_ARBOL_LDAT);

                // Asignar tipo usuario dependiendo la rama donde se busca
                logger.info("Verificar tipo usuario para usuarioLdap con entryDn-->" + entryDn);
                usuarioDTO.setTipo(isOuExterno(usuarioLdapLista.getDistinguishedName()));

                usuarioDTO.setUltimaModificacion(timestampActual);
                usuarioDTO.setUsuarioModificacion("1");
                // Se crea usuario si no exsite en la base de seguridad
                List<Usuario> usuarioList = manejadorUsuarios.listarUsuariosPorNombre(usuarioDTO.getLogonName());
                if (usuarioList.isEmpty()) {
                    usuarioDTO.setEstado(Constantes.ESTADO_ACTIVO_S);
                    logger.info(
                            "No existe usuario en BD seguridad, por lo tanto se crea con estos datos: " + usuarioDTO);
                    crearUsuarioEnBD(usuarioDTO);
                }
                // obtener id del usuario almacenado en la BD
                Usuario usuario = manejadorUsuarios.getUsuarioPorNombre(usuarioDTO.getLogonName());
                usuarioDTO.setUsuarioId(usuario.getUsuarioId());
                usuarioDTO.setEstado(usuario.getEstado());
                usuariosDTOList.add(usuarioDTO);
            }

        }
        logger.info("Fin metodo negocio consultarRamaLDAPFiltros");
        // Retornar lista de DTO de usuarios encontrados
        return usuariosDTOList;
    }

    /**
     * Metodo que consulta los usuarios en la rama del directorio especificada
     * por parametro. Registra eventos de auditoria con el id recibido de
     * usuarioSesionId
     *
     * @param logonName
     * @param nombres
     * @param apellidos
     * @param email
     * @param entryDn
     * @param usuarioSesionId
     * @return
     * @throws SIA3Exception
     * @throws SeguridadException
     */
    public List<UsuariosDTO> consultarRamaLDAPFiltros(String logonName, String nombres, String apellidos, String email,
                                                      String entryDn, String usuarioSesionId) throws SIA3Exception, SeguridadException {
        logger.info("Inicio metodo consultarRamaLDAPFiltros con parametros logonName:" + logonName + ". nombres:"
                + nombres + ". apellidos:" + apellidos + ". email:" + email + ", usuarioSesionId:" + usuarioSesionId);
        // validar que venga el id del usuario de sesion
        if (usuarioSesionId == null) {
            logger.error("El campo usuarioSesionId es obligatorio para el registro de auditoria. Valor recibido: "
                    + usuarioSesionId);
            throw new SIA3Exception(MessagesConstants.APP100120);
        }
        List<UsuarioLdap> usuarioLDAPList = ServicioLDAP.listarUsuariosPorFiltros(logonName, nombres, apellidos, email,
                entryDn, parametrosSng.obtenerParametros());
        List<UsuariosDTO> usuariosDTOList = new ArrayList<>();
        if (usuarioLDAPList.isEmpty()) {
            logger.error(
                    "metodo consultarRamaLDAPFiltros: No se encontraron usuarios en el LDAP con parametros logonName:"
                            + logonName + ". nombres:" + nombres + ". apellidos:" + apellidos + ". email:" + email);
        } else {
            // Recorrer lista para armar DTO de usuarios con base en la info
            // recibida del LDAP
            logger.info("Registros LDAP encontrados en lista: " + usuarioLDAPList);
            for (UsuarioLdap usuarioLdapLista : usuarioLDAPList) {
                UsuariosDTO usuarioDTO = new UsuariosDTO();
                // datos que vienen del ldap
                usuarioDTO.setApellidosUsuario(usuarioLdapLista.getSn());
                usuarioDTO.setEmailUsuario(usuarioLdapLista.getMail());
                usuarioDTO.setNombres(usuarioLdapLista.getGivenName());
                usuarioDTO.setLogonName(usuarioLdapLista.getsAMAccountName());
                logger.info("LogonName encontrado:" + usuarioLdapLista.getsAMAccountName());
                // datos para la base de seguridad
                // Asignar fecha de creacion y modificacion
                Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
                usuarioDTO.setFechaCreacion(timestampActual);
                usuarioDTO.setLoginUsuario(usuarioLdapLista.getUid());
                usuarioDTO.setNuevoPass(usuarioLdapLista.getUserPassword());
                usuarioDTO.setNumeroDocumento(usuarioLdapLista.getUid());
                usuarioDTO.setPassword(usuarioLdapLista.getUserPassword());
                usuarioDTO.setRuta(Constantes.RUTA_ARBOL_LDAT);

                // Asignar tipo usuario dependiendo la rama donde se busca
                logger.info("Verificar tipo usuario para usuarioLdap con entryDn-->" + entryDn);
                usuarioDTO.setTipo(isOuExterno(usuarioLdapLista.getDistinguishedName()));

                usuarioDTO.setUltimaModificacion(timestampActual);
                usuarioDTO.setUsuarioModificacion(usuarioSesionId);
                // Se crea usuario si no exsite en la base de seguridad
                List<Usuario> usuarioList = manejadorUsuarios.listarUsuariosPorNombre(usuarioDTO.getLogonName());
                if (usuarioList.isEmpty()) {
                    usuarioDTO.setEstado(Constantes.ESTADO_ACTIVO_S);
                    logger.info(
                            "No existe usuario en BD seguridad, por lo tanto se crea con estos datos: " + usuarioDTO);
                    crearUsuarioEnBD(usuarioDTO);
                    // Registrar en auditoria el evento
                    logger.info("Inicio registro auditoria evento USER_CREATED");
                    // Validar que venga el usuario que realiza la operacion
                    if (usuarioDTO.getUsuarioModificacion() == null) {
                        logger.error(
                                "Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
                        throw new SIA3Exception(MessagesConstants.APP100120);
                    }
                    auditoria.gestionarAuditoriaDetalle(new BigDecimal(SeguridadException.USER_CREATED),
                            usuarioDTO.getUsuarioModificacion(), Constantes.HBT_ID_APP_SEGURIDAD.toString(),
                            "Crea usuario con nombre usuario:" + usuarioDTO.getLogonName() + ", nombres:"
                                    + usuarioDTO.getNombres() + ", apellidos:" + usuarioDTO.getApellidosUsuario());
                    logger.info("Fin registro auditoria evento USER_CREATED");
                }
                // obtener id del usuario almacenado en la BD
                Usuario usuario = manejadorUsuarios.getUsuarioPorNombre(usuarioDTO.getLogonName());
                usuarioDTO.setUsuarioId(usuario.getUsuarioId());
                usuarioDTO.setEstado(usuario.getEstado());
                usuariosDTOList.add(usuarioDTO);
            }

        }
        logger.info("Fin metodo negocio consultarRamaLDAPFiltros");
        // Retornar lista de DTO de usuarios encontrados
        return usuariosDTOList;
    }

    /**
     * Metodo que activa un usuario
     *
     * @param usuarioDTO
     * @throws SIA3Exception
     */
    public void activarUsuario(UsuariosDTO usuarioDTO, String context) throws SIA3Exception {
        logger.info("Inicio metodo activarUsuario con usuarioDTO =>" + usuarioDTO);
        try {
            if (usuarioDTO == null) {
                logger.error("Error en metodo activarUsuario: Debe ingresar id de usuario");
                throw new SIA3Exception(MessagesConstants.APP100085);
            }
            // Verificar que usuario exista
            List<Usuario> usuariosExistentes = manejadorUsuarios
                    .listarUsuariosPorId(new BigDecimal(usuarioDTO.getUsuarioId()));
            if (usuariosExistentes == null || usuariosExistentes.isEmpty()) {
                logger.error(
                        "Error en metodo activarUsuario: NO EXISTE usuario con el id:" + usuarioDTO.getUsuarioId());
                throw new SIA3Exception(MessagesConstants.APP100072);
            }

            logger.info("Usuario DTO Ruta =>" + usuarioDTO.getRuta());

            UsuarioLdap usuarioLdap = armarUsuarioLdap(usuarioDTO);

            ServicioLDAP.activarUser(usuarioLdap,
                    parametrosSng.obtenerParametros());
            logger.info("Se Activa el usuario LDAP");

            Usuario usuario = consultarUsuarioIdUsuario(usuarioDTO.getUsuarioId());
            copiarPropiedades(usuario);
            usuarioDTO.setEstado(Constantes.ESTADO_ACTIVO_S);
            usuarioDTO.setUsuarioModificacion(usuarioDTO.getUsuarioModificacion());
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            usuarioDTO.setUltimaModificacion(timestampActual);
            actualizar(usuarioDTO, usuario);
            logger.info("Finalizó el metodo activarUsuario con usuarioDTO => " + usuarioDTO);

            // Genera el token de sesion y lo ingresa en base de datos
            String code = UtilOperaciones.randomString(Constantes.LONGITUD_CODIGO);

            String enlace = armarEnlace(context, code, usuarioDTO.getUsuarioId());
            UtilEmail.enviarPasswordEmail(usuarioDTO, enlace, true, parametrosSng.obtenerParametros());

        } catch (SIA3Exception se) {
            logger.error("Error en metodo activarUsuario:" + se);
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de activar Usuario");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    // Armar Usuario LDAP
    private UsuarioLdap armarUsuarioLdap(UsuariosDTO usuario) {
        UsuarioLdap usuarioLdap = new UsuarioLdap();
        usuarioLdap.setDescription(usuario.getNumeroDocumento());
        usuarioLdap.setSn(usuario.getApellidosUsuario());
        usuarioLdap.setMail(usuario.getEmailUsuario());
        usuarioLdap.setCn(usuario.getNombres() + " " + usuario.getApellidosUsuario());
        usuarioLdap.setDistinguishedName(usuario.getRuta());
        usuarioLdap.setDisplayName(usuario.getNombres() + " " + usuario.getApellidosUsuario());
        usuarioLdap.setGivenName(usuario.getNombres());
        usuarioLdap.setsAMAccountName(usuario.getLogonName());
        usuarioLdap.setUserPassword(usuario.getPassword());
        return usuarioLdap;
    }

    /**
     * Metodo para inactivar usuario
     *
     * @param usuarioDTO
     * @throws SIA3Exception
     */
    public void inactivarUsuario(UsuariosDTO usuarioDTO) throws SIA3Exception {
        logger.info("Inicio metodo inactivarUsuario con usuarioDTO =>" + usuarioDTO);
        try {
            if (usuarioDTO == null) {
                logger.error("Error en metodo inactivarUsuario: Debe ingresar id de usuario");
                throw new SIA3Exception(MessagesConstants.APP100085);
            }
            // Verificar que usuario exista
            List<Usuario> usuariosExistentes = manejadorUsuarios
                    .listarUsuariosPorId(new BigDecimal(usuarioDTO.getUsuarioId()));
            if (usuariosExistentes == null || usuariosExistentes.isEmpty()) {
                logger.error(
                        "Error en metodo activarUsuario: NO EXISTE usuario con el id:" + usuarioDTO.getUsuarioId());
                throw new SIA3Exception(MessagesConstants.APP100081);
            }

            logger.info("Usuario DTO Ruta =>" + usuarioDTO.getRuta());

            UsuarioLdap usuarioLdap = armarUsuarioLdap(usuarioDTO);

            ServicioLDAP.desactivarUser(usuarioLdap,
                    parametrosSng.obtenerParametros());
            logger.info("Se desactiva el usuario LDAP");

            Usuario usuario = consultarUsuarioIdUsuario(usuarioDTO.getUsuarioId());
            copiarPropiedades(usuario);
            usuarioDTO.setEstado(Constantes.ESTADO_ACTIVO_N);
            usuarioDTO.setUsuarioModificacion(usuarioDTO.getUsuarioModificacion());
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            usuarioDTO.setUltimaModificacion(timestampActual);
            actualizar(usuarioDTO, usuario);
            logger.info("Finalizó el metodo inactivarUsuario con usuarioDTO => " + usuarioDTO);

        } catch (SIA3Exception se) {
            logger.error("Error en metodo inactivarUsuario:" + se);
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de inactivar Usuario");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que valida la informacion contenida en un DTO de usuario
     *
     * @param usuarioDTO
     * @throws SIA3Exception
     */
    private void validarUsuarios(UsuariosDTO usuarioDTO) throws SIA3Exception {
        // validar campos obligatorios
        if (usuarioDTO.getEstado() == null) {
            logger.error("Error en metodo validarUsuarios: Campo estado no puede ser  nulo");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }
        if (usuarioDTO.getEstado() != null && usuarioDTO.getEstado().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarUsuarios: Campo estado no puede ser  nulo");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }

        if (usuarioDTO.getLogonName().equals("") || usuarioDTO.getLogonName() == null) {
            logger.error("Error en metodo validarUsuarios: Campo ruta no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100013);
        }

        if (usuarioDTO.getRuta().equals("") || usuarioDTO.getRuta() == null) {
            logger.error("Error en metodo validarUsuarios: Campo ruta no puede ser vacio o nulo");
            throw new SIA3Exception(MessagesConstants.APP100013);
        }

        if (usuarioDTO.getTipo() == null) {
            logger.error("Error en metodo validarUsuarios: Campo tipo no puede ser  nulo");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }
        if (usuarioDTO.getTipo() != null && usuarioDTO.getTipo().equals(new BigDecimal(0))) {
            logger.error("Error en metodo validarUsuarios: Campo tipo no puede ser  nulo");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }

        // validar nombre unico
        List<Usuario> usuariosExistentes = manejadorUsuarios.listarUsuariosPorNombre(usuarioDTO.getLogonName());
        for (Usuario usuarioLista : usuariosExistentes) {
            if (!usuarioLista.getLogonName().equals(usuarioDTO.getLogonName())) {
                logger.error("Error en metodo validarUsuarios: Ya existe usuario con el nombre:"
                        + usuarioDTO.getLogonName());
                throw new SIA3Exception(MessagesConstants.APP100096);
            }
        }
    }

    /**
     * Metodo que lista usuarios relacionados una aplicacion y rol indicados
     *
     * @param opcionId
     * @param rolId
     * @return
     * @throws SIA3Exception
     */
    public List<UsuariosDTO> getUsuariosPorAppRol(BigDecimal opcionId, BigDecimal rolId)
            throws SIA3Exception, SeguridadException {
        try {
            logger.info("Inicio metodo negocio getUsuariosPorAppRol con opcionId:" + opcionId + " y rolId:" + rolId);
            // Validar que hayan operaciones relacionadas con el rol
            // seleccionado
            List<OperacionesRol> listaOperacionesRol = manejadorOperacionesRol.buscarOperacionesRolXRol(rolId);
            if (listaOperacionesRol.isEmpty()) {
                throw new SIA3Exception(MessagesConstants.APP100116);
            }

            // obtener lista de usuarios por aplicacion y rol
            List<UsuariosDTO> usuariosBDList = manejadorUsuarios.listarUsuariosPorAppRol(opcionId, rolId);
            if (usuariosBDList.isEmpty()) {
                logger.error("Error en metodo getUsuariosPorAppRol: No hay usuarios relacionados con opcionId:"
                        + opcionId + " y rolId:" + rolId);
                throw new SIA3Exception(MessagesConstants.APP100098);
            }

            // recorrer DTO para actualizar datos que vienen del LDAP
            List<UsuariosDTO> usuarioDTOList = new ArrayList<>();
            for (UsuariosDTO usuarioDTO : usuariosBDList) {

                        UsuariosDTO usuarioDTOTemp = new UsuariosDTO();
                        usuarioDTOTemp.setFechaVinculacion(usuarioDTO.getFechaVinculacion());
                        usuarioDTOTemp.setEstadoVinculacion(
                                usuarioDTO.getEstadoVinculacion() != null ? usuarioDTO.getEstadoVinculacion() : "");
                logger.info("Buscar informacion en LDAP para logonName: " + usuarioDTO.getLogonName());
                usuarioDTO = completarInformacionUsuario(usuarioDTO.getLogonName());
                if (usuarioDTO != null) {
                    usuarioDTO.setFechaVinculacion(usuarioDTOTemp.getFechaVinculacion());
                    usuarioDTO.setEstadoVinculacion(
                                        usuarioDTOTemp.getEstadoVinculacion() != null ? usuarioDTOTemp.getEstadoVinculacion() : "");
                    usuarioDTOList.add(usuarioDTO);
                            }
                        }

            logger.info(mesagge + usuarioDTOList);
            return usuarioDTOList;
        } catch (PersistenceException e) {
            logger.error(mesaggeError + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que lista usuarios relacionados una aplicacion y rol
     *
     * @param opcionId
     * @return
     * @throws SIA3Exception
     */
    public List<UsuariosDTO> getUsuariosPorApp(BigDecimal opcionId, BigDecimal aplicacionId)
            throws SIA3Exception, SeguridadException {
        List<UsuariosDTO> usuariosBDList = null;
        try {
            logger.info("Inicio metodo negocio getUsuariosPorApp con opcionId:" + opcionId + mesaggeApp
                    + aplicacionId);
            usuariosBDList = BuilderDTO.toUsuarioDTOList(manejadorUsuarios.listarUsuariosPorApp(opcionId, aplicacionId));
            if (usuariosBDList.isEmpty()) {
                logger.error("Error en metodo getUsuariosPorApp: No hay usuarios relacionados con opcionId: " + opcionId
                        + mesaggeApp + aplicacionId);
                return usuariosBDList;
            } else {
                // recorrer DTO para actualizar datos que vienen del LDAP
                List<UsuariosDTO> usuarioDTOList = new ArrayList<>();
                for (UsuariosDTO usuarioDTO : usuariosBDList) {
                    logger.info("Buscar informacion en LDAP para logonName: " + usuarioDTO.getLogonName());
                    UsuariosDTO datosExternoUsuario = completarInformacionUsuario(usuarioDTO.getLogonName());
                    if (datosExternoUsuario != null) {
                        datosExternoUsuario.setFechaCreacion(usuarioDTO.getFechaCreacion());
                        datosExternoUsuario.setFechaVinculacion(usuarioDTO.getFechaVinculacion());
                        datosExternoUsuario.setEstado(usuarioDTO.getEstado());
                        datosExternoUsuario.setFechaDesvinculacion(usuarioDTO.getFechaDesvinculacion());
                        datosExternoUsuario.setEstado(usuarioDTO.getEstado());
                        usuarioDTOList.add(datosExternoUsuario);
                    }
                }
                logger.info(mesagge + usuarioDTOList);

                return usuarioDTOList;
            }
        } catch (PersistenceException e) {
            logger.error(mesaggeError + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    public List<UsuariosDTO> getUsuarioPorAppExiste(BigDecimal usuarioId, String aplicacionId)
            throws SIA3Exception{
        List<UsuariosDTO> usuariosBDList = null;
        try {
            logger.info("Inicio metodo negocio getUsuariosPorApp con aplicacionId:" + aplicacionId + mesaggeApp
                    + aplicacionId);
            usuariosBDList = BuilderDTO.toUsuarioDTOList(manejadorUsuarios.getUsuarioPorAppExiste(usuarioId, aplicacionId));
            if (usuariosBDList.isEmpty()) {
                logger.error("Error en metodo getUsuariosPorApp: No hay usuarios relacionados con aplicacionId: " + aplicacionId
                        + mesaggeApp + aplicacionId);
                return usuariosBDList;
            }else {
                logger.info("fin metodo getUsuarioPorAppExiste. Retorna lista: " + usuariosBDList);
                return usuariosBDList;
            }
        } catch (PersistenceException e) {
            logger.error(mesaggeError + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    static class ThreadException extends  RuntimeException {
        ThreadException(Exception e){
            super(e);
        }
    }

    /**
     * Metodo que lista usuarios relacionados una aplicacion y rol
     *
     * @param opcionId
     * @return
     * @throws SIA3Exception
     */
    public List<UsuariosDTO> getUsuariosPorApp(BigDecimal aplicacionId)
            throws SIA3Exception {
        List<UsuariosDTO> usuariosBDList = null;
        try {
            logger.info("Inicio metodo negocio getUsuariosPorApp con opcionId:" + aplicacionId + mesaggeApp
                    + aplicacionId);
            usuariosBDList = BuilderDTO
                    .toUsuarioDTOList(manejadorUsuarios.listarUsuariosPorApp(aplicacionId));
            if (usuariosBDList.isEmpty()) {
                logger.error("Error en metodo getUsuariosPorApp: No hay usuarios relacionados con opcionId: " + aplicacionId
                        + mesaggeApp + aplicacionId);
                return usuariosBDList;
            } else {
                final int NUM_OF_THREADS = 10;

                ExecutorService executor = Executors.newFixedThreadPool(
                        Math.min(usuariosBDList.size(), NUM_OF_THREADS));

                List<CompletableFuture<UsuariosDTO>> futures = usuariosBDList.stream()
                        .map(usuarioDTO -> CompletableFuture.supplyAsync(() -> {
                            UsuariosDTO usr;
                            try {
                                usr = completarInformacionUsuario(usuarioDTO.getLogonName());
                                if (usr != null) {
                                    List<RolesDTO> rolesDTOS = negocioRoles.getRolesPorUsuarioIdAplicacionId(Long.parseLong(usuarioDTO.getUsuarioId()), aplicacionId);
                                    usr.setRoles(rolesDTOS);
                                    usr.setFechaCreacion(usuarioDTO.getFechaCreacion());
                                    usr.setFechaVinculacion(usuarioDTO.getFechaCreacion());
                                    usr.setEstado(usuarioDTO.getEstado());
                                    usr.setFechaDesvinculacion(null);
                                }

                            } catch (SIA3Exception | SeguridadException e) {
                                throw new ThreadException(e);
                            }
                            return usr;
                        }, executor))
                        .collect(Collectors.toList());

                List<UsuariosDTO> usuarioDTOList = futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                logger.info(mesagge + usuarioDTOList);
                return usuarioDTOList;

            }
        } catch (PersistenceException e) {
            logger.error(mesaggeError + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que desvincula un usuario a rol
     *
     * @param opcionId
     * @param rolId
     * @return
     * @throws SIA3Exception
     */
    public void desvincularUsuarios(String rolId, List<UsuariosDTO> listaUsuarios)
            throws SIA3Exception {
        try {
            logger.info("Inicio metodo negocio desvincularUsuarios " + listaUsuarios);

            for (UsuariosDTO usuariosDTO : listaUsuarios) {
                UsuariosRolDTO usuariosRolDTO = new UsuariosRolDTO();
                UsuariosRolPK usuariosRolPK = new UsuariosRolPK();
                usuariosRolPK.setRolId(new BigDecimal(rolId));
                usuariosRolPK.setUsuarioId(usuariosDTO.getUsuarioId());
                usuariosRolDTO.setUsuariosRolPK(usuariosRolPK);

                manejadorUsuarios.desvincularUsuarios(usuariosRolDTO);

                // Registrar en auditoria el evento
                logger.info("Inicio registro auditoria evento USER_UNASSIGNED");
                // obtener nombre del rol
                Roles rolDesvinculado = roles.getRolPorId(usuariosRolPK.getRolId());
                // Validar que venga el usuario que realiza la operacion
                if (usuariosDTO.getUsuarioModificacion() == null) {
                    logger.error(
                            "Error al tratar de registrar auditoria, el campo usuario modificacion es obligatorio");
                    throw new SIA3Exception(MessagesConstants.APP100120);
                }

                String nombreRol = rolDesvinculado != null ? rolDesvinculado.getNombre() : "";

                JsonObject detalle = new JsonObject();
                detalle.addProperty("descripcion", "Desvincula usuario:");
                detalle.addProperty("usuario", usuariosDTO.getLogonName());
                detalle.addProperty("rol", nombreRol);

                auditoria.gestionarAuditoriaDetalle(Constantes.EVT_USER_UNASSIGNED, usuariosDTO.getUsuarioModificacion(), Constantes.HBT_ID_APP_SEGURIDAD.toString(), detalle.toString());
                logger.info("Fin registro auditoria evento USER_UNASSIGNED");

            }
            logger.info("fin metodo desvincularUsuarios.");

        } catch (PersistenceException e) {
            logger.error("Error en metodo desvincularUsuarios: " + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que obtiene la lista de todos los usuarios
     *
     * @param estado
     * @return
     * @throws SIA3Exception
     */
    public List<UsuariosDTO> getAllUsuarios(Long estado) throws SIA3Exception {
        try {
            logger.info("Inicio metodo getAllUsuarios. Filtro estado:" + estado);
            List<UsuariosDTO> usuarioDTOList = BuilderDTO.toUsuarioDTOList(manejadorUsuarios.getAllUsuarios(estado));
            if (usuarioDTOList.isEmpty()) {
                logger.error("Error en metodo consultar: No hay usuarios.");
                throw new SIA3Exception(MessagesConstants.APP100097);
            }
            logger.info("fin metodo getAllUsuarios");
            return usuarioDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getAllUsuarios:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de consultar todos los usuarios");
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

    }

    /**
     * Metodo que obtiene la lista de todos los usuarios que pertenecen a
     * Seguridad
     *
     * @param estado
     * @return
     * @throws SIA3Exception
     */
    public List<UsuariosDTO> getAllUsuariosSeguridad(Long estado) throws SIA3Exception {
        try {
            logger.info("Inicio metodo getAllUsuariosSeguridad. Filtro estado:" + estado);
            List<UsuariosDTO> usuarioDTOList = BuilderDTO
                    .toUsuarioDTOList(manejadorUsuarios.getAllUsuariosSeguridad(estado));
            if (usuarioDTOList.isEmpty()) {
                logger.error("Error en metodo consultar: No hay usuarios de seguridad.");
                throw new SIA3Exception(MessagesConstants.APP100097);
            }
            logger.info("fin metodo getAllUsuariosSeguridad");
            return usuarioDTOList;
        } catch (PersistenceException e) {
            logger.error("Error en metodo getAllUsuariosSeguridad:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de consultar todos los usuarios de seguridad " + e.getMessage());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }

    }
    public List<UsuariosDTO> getAllUsuariosPorApp(Long aplicacionId, String usuario) throws SIA3Exception{
        try {
            logger.info("Inicio metodo getAllUsuariosPorApp con aplicacionId:" + aplicacionId);
            List<UsuariosDTO> usuarioDTOList = BuilderDTO
                    .toUsuarioDTOList(manejadorUsuarios.getAllUsuariosPorApp(aplicacionId, usuario));
            if (usuarioDTOList.isEmpty()) {
                logger.error("Error en metodo consultar: No hay usuarios para la app con id:" + aplicacionId);
                throw new SIA3Exception(MessagesConstants.APP100097);
            }
            logger.info("fin metodo getAllUsuariosPorApp");
            return usuarioDTOList;
        } catch (PersistenceException e){
            logger.error("Error en metodo getAllUsuariosPorApp:" + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    /**
     * Metodo que se encarga de crear usuario si no existe en la BD al momento
     * de buscar usuario en el LDAP
     *
     * @param usuariosDTO
     * @return
     * @throws SIA3Exception
     */
    public UsuariosDTO crearUsuarioEnBD(UsuariosDTO usuariosDTO) throws SIA3Exception {
        try {
            logService(this.getClass().getName(), "crear", usuariosDTO);
            validarUsuarios(usuariosDTO);
            Usuario usuarios = new Usuario();
            copiarPropiedades(usuarios, usuariosDTO);
            // Setear fecha de creacion y modificacion
            Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
            usuarios.setFechaCreacion(timestampActual);
            usuarios.setUltimaModificacion(timestampActual);
            logger.info("Crear entidad usuario");
            manejadorUsuarios.crear(usuarios);
            logger.info("Fin metodo crearUsuarioEnBD. Retorna usuariosDTO " + usuariosDTO.getLogonName()
                    + usuariosDTO.getUsuarioId());
            return usuariosDTO;
        } catch (SIA3Exception se) {
            logger.error("Error al validar creacion de Usuario en metodo crearUsuarioEnBD->" + se);
            throw new SIA3Exception(se.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al tratar de crear Usuario en metodo crearUsuarioEnBD->" + e);
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }
    // Fin metodos HBT

    /**
     * @param context
     * @param code
     * @return
     * @author hfabra
     * @since 24/03/2017
     */
    private String armarEnlace(String context, String code, String userId) {
        StringBuilder enlace = new StringBuilder(context).append("/ingresarNuevaContrasenia.jsf?code=").append(code)
                .append("&userID=" + userId);
        return enlace.toString();
    }

    /**
     * Metodo que lista usuarios relacionados una aplicación y rol indicados
     *
     * @param opcionId
     * @param rolId
     * @return
     * @throws SIA3Exception
     */
    public List<UsuariosDTO> getUsuariosPorAppNombreRol(BigDecimal opcionId, String rol, Long estado, Long idUsuario, Integer pagInicio, Integer pagFin)
            throws SIA3Exception, SeguridadException {
        try {
            logger.info("Inicio metodo negocio getUsuariosPorAppNombreRol con opcionId:" + opcionId + " y rol:" + rol);
            List<UsuariosDTO> usuariosBDList = manejadorUsuarios.getUsuariosPorAppNombreRol(opcionId, rol, estado, idUsuario, pagInicio, pagFin);
            if (usuariosBDList.isEmpty()) {
                logger.error("Error en metodo getUsuariosPorAppRol: No hay usuarios relacionados con opcionId:"
                        + opcionId + " y rol:" + rol);
                throw new SIA3Exception(MessagesConstants.APP100098);
            }

            //recorrer DTO para actualizar datos que vienen del LDAP
            List<UsuariosDTO> usuarioDTOList = new ArrayList<>();
            for (UsuariosDTO usuarioDTO : usuariosBDList) {

                UsuariosDTO usuarioDTOTemp = new UsuariosDTO();
                usuarioDTOTemp.setFechaVinculacion(
                        usuarioDTO.getFechaVinculacion() != null ? usuarioDTO.getFechaVinculacion() : null);
                usuarioDTOTemp.setEstadoVinculacion(
                        usuarioDTO.getEstadoVinculacion() != null ? usuarioDTO.getEstadoVinculacion() : "");
                logger.info("Buscar informacion en LDAP para logonName: " + usuarioDTO.getLogonName());
                usuarioDTO = completarInformacionUsuario(usuarioDTO.getLogonName());


                if (usuarioDTO != null) {
                    usuarioDTO.setFechaVinculacion(
                            usuarioDTOTemp.getFechaVinculacion() != null ? usuarioDTOTemp.getFechaVinculacion() : null);
                    usuarioDTO.setEstadoVinculacion(
                            usuarioDTOTemp.getEstadoVinculacion() != null ? usuarioDTOTemp.getEstadoVinculacion() : "");
                    usuarioDTO.setNombres(usuarioDTO.getNombres() + " " + usuarioDTO.getApellidosUsuario());
                    usuarioDTOList.add(usuarioDTO);

                }


            }

            logger.info(mesagge + usuarioDTOList);
            return usuarioDTOList;
        } catch (PersistenceException e) {
            logger.error(mesaggeError + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

    public Long getCountUsuariosPorAppNombreRol(BigDecimal opcionId, String rol, Long estado, Long idUsuario)
            throws SIA3Exception{
        try {
            logger.info("Inicio metodo negocio getCountUsuariosPorAppNombreRol con opcionId:" + opcionId + " y rol:" + rol);

            Long countUsuarios = manejadorUsuarios.getCountUsuariosPorAppNombreRol(opcionId, rol, estado, idUsuario);

            logger.info("fin metodo getCountUsuariosPorAppNombreRol. Retorna" + countUsuarios);
            return countUsuarios;
        } catch (PersistenceException e) {
            logger.error(mesaggeError + e.getCause());
            throw new SIA3Exception(MessagesConstants.APP000003);
        }
    }

}
