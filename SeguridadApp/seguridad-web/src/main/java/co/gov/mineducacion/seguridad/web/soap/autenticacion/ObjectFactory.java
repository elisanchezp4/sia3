
package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.gov.mineducacion.soap._11.seguridad.sia3 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _OpcionHijosOpcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3",  "hijosOpcion"); // NOSONAR
    private static final QName _OpcionNombreObjeto_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "nombreObjeto"); // NOSONAR
    private static final QName _OpcionNombreRolAsociado_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "nombreRolAsociado"); // NOSONAR
    private static final QName _OpcionDescripcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "descripcion"); // NOSONAR
    private static final QName _AplicacionApiKey_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "apiKey"); // NOSONAR
    private static final QName _Opcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "Opcion"); // NOSONAR
    private static final QName _InformacionSesion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionSesion"); // NOSONAR
    private static final QName _InformacionNuevaClave_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionNuevaClave"); // NOSONAR
    private static final QName _Aplicacion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "aplicacion"); // NOSONAR
    private static final QName _Usuario_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "usuario"); // NOSONAR
    private static final QName _InformacionPermisos_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionPermisos"); // NOSONAR
    private static final QName _ArrayOfOpcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "ArrayOfOpcion"); // NOSONAR
    private static final QName _InformacionToken_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionToken"); // NOSONAR
    private static final QName _EncabezadoSeguridad_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "encabezadoSeguridad"); // NOSONAR
    private static final QName _TipoOpcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "TipoOpcion"); // NOSONAR
    private static final QName _EncabezadoSeguridadRq_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "encabezadoSeguridadRq"); // NOSONAR
    private static final QName _MensajeFault_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "mensajeFault"); // NOSONAR
    private static final QName _Autorizacion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "autorizacion"); // NOSONAR
    private static final QName _UsuarioApellidos_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "apellidos"); // NOSONAR
    private static final QName _UsuarioNombres_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "nombres"); // NOSONAR
    private static final QName _ObtenerTokenRqCodigoAutorizacion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "codigoAutorizacion"); // NOSONAR
    private static final QName _InformacionTokenTokenAcceso_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "tokenAcceso"); // NOSONAR
    private static final QName _MensajeFaultTrace_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "trace"); // NOSONAR
    private static final QName _MensajeFaultCodigo_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "codigo"); // NOSONAR
    private static final QName _MensajeFaultMensaje_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "mensaje"); // NOSONAR
    private static final QName _InformacionNuevaClaveNuevaClave_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "nuevaClave"); // NOSONAR
    private static final QName _EncabezadoSeguridadIpHost_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "ipHost"); // NOSONAR
    private static final QName _InformacionPermisosRoles_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "roles"); // NOSONAR
    private static final QName _InformacionPermisosPermisos_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "permisos"); // NOSONAR

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.gov.mineducacion.soap._11.seguridad.sia3
     * 
     */
    public ObjectFactory() {
        // Auto-generated method stub
    }

    /**
     * Create an instance of {@link Opcion }
     * 
     */
    public Opcion createOpcion() {
        return new Opcion();
    }

    /**
     * Create an instance of {@link InformacionPermisos }
     * 
     */
    public InformacionPermisos createInformacionPermisos() {
        return new InformacionPermisos();
    }

    /**
     * Create an instance of {@link Usuario }
     * 
     */
    public Usuario createUsuario() {
        return new Usuario();
    }

    /**
     * Create an instance of {@link FinalizarSesionRq }
     * 
     */
    public FinalizarSesionRq createFinalizarSesionRq() {
        return new FinalizarSesionRq();
    }

    /**
     * Create an instance of {@link InformacionSesion }
     * 
     */
    public InformacionSesion createInformacionSesion() {
        return new InformacionSesion();
    }

    /**
     * Create an instance of {@link EncabezadoSeguridad }
     * 
     */
    public EncabezadoSeguridad createEncabezadoSeguridad() {
        return new EncabezadoSeguridad();
    }

    /**
     * Create an instance of {@link InformacionToken }
     * 
     */
    public InformacionToken createInformacionToken() {
        return new InformacionToken();
    }

    /**
     * Create an instance of {@link ArrayOfOpcion }
     * 
     */
    public ArrayOfOpcion createArrayOfOpcion() {
        return new ArrayOfOpcion();
    }

    /**
     * Create an instance of {@link ObtenerRolesPermisosRq }
     * 
     */
    public ObtenerRolesPermisosRq createObtenerRolesPermisosRq() {
        return new ObtenerRolesPermisosRq();
    }

    /**
     * Create an instance of {@link Aplicacion }
     * 
     */
    public Aplicacion createAplicacion() {
        return new Aplicacion();
    }

    /**
     * Create an instance of {@link ObtenerRolesPermisosRs }
     * 
     */
    public ObtenerRolesPermisosRs createObtenerRolesPermisosRs() {
        return new ObtenerRolesPermisosRs();
    }

    /**
     * Create an instance of {@link InformacionNuevaClave }
     * 
     */
    public InformacionNuevaClave createInformacionNuevaClave() {
        return new InformacionNuevaClave();
    }

    /**
     * Create an instance of {@link ActualizarFechaVencimientoTokenRq }
     * 
     */
    public ActualizarFechaVencimientoTokenRq createActualizarFechaVencimientoTokenRq() {
        return new ActualizarFechaVencimientoTokenRq();
    }

    /**
     * Create an instance of {@link ObtenerTokenRq }
     * 
     */
    public ObtenerTokenRq createObtenerTokenRq() {
        return new ObtenerTokenRq();
    }

    /**
     * Create an instance of {@link Autorizacion }
     * 
     */
    public Autorizacion createAutorizacion() {
        return new Autorizacion();
    }

    /**
     * Create an instance of {@link MensajeFault }
     * 
     */
    public MensajeFault createMensajeFault() {
        return new MensajeFault();
    }

    /**
     * Create an instance of {@link ModificarPasswordRq }
     * 
     */
    public ModificarPasswordRq createModificarPasswordRq() {
        return new ModificarPasswordRq();
    }

    /**
     * Create an instance of {@link ActualizarFechaVencimientoTokenRs }
     * 
     */
    public ActualizarFechaVencimientoTokenRs createActualizarFechaVencimientoTokenRs() {
        return new ActualizarFechaVencimientoTokenRs();
    }

    /**
     * Create an instance of {@link ObtenerTokenRs }
     * 
     */
    public ObtenerTokenRs createObtenerTokenRs() {
        return new ObtenerTokenRs();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOpcion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "hijosOpcion", scope = Opcion.class)
    public JAXBElement<ArrayOfOpcion> createOpcionHijosOpcion(ArrayOfOpcion value) {
        return new JAXBElement<>(_OpcionHijosOpcion_QNAME, ArrayOfOpcion.class, Opcion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "nombreObjeto", scope = Opcion.class)
    public JAXBElement<String> createOpcionNombreObjeto(String value) {
        return new JAXBElement<>(_OpcionNombreObjeto_QNAME, String.class, Opcion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "nombreRolAsociado", scope = Opcion.class)
    public JAXBElement<String> createOpcionNombreRolAsociado(String value) {
        return new JAXBElement<>(_OpcionNombreRolAsociado_QNAME, String.class, Opcion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "descripcion", scope = Opcion.class)
    public JAXBElement<String> createOpcionDescripcion(String value) {
        return new JAXBElement<>(_OpcionDescripcion_QNAME, String.class, Opcion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "apiKey", scope = Aplicacion.class)
    public JAXBElement<String> createAplicacionApiKey(String value) {
        return new JAXBElement<>(_AplicacionApiKey_QNAME, String.class, Aplicacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Opcion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "Opcion")
    public JAXBElement<Opcion> createOpcion(Opcion value) {
        return new JAXBElement<>(_Opcion_QNAME, Opcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionSesion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionSesion")
    public JAXBElement<InformacionSesion> createInformacionSesion(InformacionSesion value) {
        return new JAXBElement<>(_InformacionSesion_QNAME, InformacionSesion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionNuevaClave }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionNuevaClave")
    public JAXBElement<InformacionNuevaClave> createInformacionNuevaClave(InformacionNuevaClave value) {
        return new JAXBElement<>(_InformacionNuevaClave_QNAME, InformacionNuevaClave.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Aplicacion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "aplicacion")
    public JAXBElement<Aplicacion> createAplicacion(Aplicacion value) {
        return new JAXBElement<>(_Aplicacion_QNAME, Aplicacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "usuario")
    public JAXBElement<Usuario> createUsuario(Usuario value) {
        return new JAXBElement<>(_Usuario_QNAME, Usuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionPermisos }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionPermisos")
    public JAXBElement<InformacionPermisos> createInformacionPermisos(InformacionPermisos value) {
        return new JAXBElement<>(_InformacionPermisos_QNAME, InformacionPermisos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOpcion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "ArrayOfOpcion")
    public JAXBElement<ArrayOfOpcion> createArrayOfOpcion(ArrayOfOpcion value) {
        return new JAXBElement<>(_ArrayOfOpcion_QNAME, ArrayOfOpcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionToken }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionToken")
    public JAXBElement<InformacionToken> createInformacionToken(InformacionToken value) {
        return new JAXBElement<>(_InformacionToken_QNAME, InformacionToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EncabezadoSeguridad }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "encabezadoSeguridad")
    public JAXBElement<EncabezadoSeguridad> createEncabezadoSeguridad(EncabezadoSeguridad value) {
        return new JAXBElement<>(_EncabezadoSeguridad_QNAME, EncabezadoSeguridad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoOpcion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "TipoOpcion")
    public JAXBElement<TipoOpcion> createTipoOpcion(TipoOpcion value) {
        return new JAXBElement<>(_TipoOpcion_QNAME, TipoOpcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EncabezadoSeguridad }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "encabezadoSeguridadRq")
    public JAXBElement<EncabezadoSeguridad> createEncabezadoSeguridadRq(EncabezadoSeguridad value) {
        return new JAXBElement<>(_EncabezadoSeguridadRq_QNAME, EncabezadoSeguridad.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MensajeFault }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "mensajeFault")
    public JAXBElement<MensajeFault> createMensajeFault(MensajeFault value) {
        return new JAXBElement<>(_MensajeFault_QNAME, MensajeFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Autorizacion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "autorizacion")
    public JAXBElement<Autorizacion> createAutorizacion(Autorizacion value) {
        return new JAXBElement<>(_Autorizacion_QNAME, Autorizacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionToken }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionToken", scope = ActualizarFechaVencimientoTokenRs.class)
    public JAXBElement<InformacionToken> createActualizarFechaVencimientoTokenRsInformacionToken(InformacionToken value) {
        return new JAXBElement<>(_InformacionToken_QNAME, InformacionToken.class, ActualizarFechaVencimientoTokenRs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "apellidos", scope = Usuario.class)
    public JAXBElement<String> createUsuarioApellidos(String value) {
        return new JAXBElement<>(_UsuarioApellidos_QNAME, String.class, Usuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "nombres", scope = Usuario.class)
    public JAXBElement<String> createUsuarioNombres(String value) {
        return new JAXBElement<>(_UsuarioNombres_QNAME, String.class, Usuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Autorizacion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "codigoAutorizacion", scope = ObtenerTokenRq.class)
    public JAXBElement<Autorizacion> createObtenerTokenRqCodigoAutorizacion(Autorizacion value) {
        return new JAXBElement<>(_ObtenerTokenRqCodigoAutorizacion_QNAME, Autorizacion.class, ObtenerTokenRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "tokenAcceso", scope = InformacionToken.class)
    public JAXBElement<String> createInformacionTokenTokenAcceso(String value) {
        return new JAXBElement<>(_InformacionTokenTokenAcceso_QNAME, String.class, InformacionToken.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionSesion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionSesion", scope = FinalizarSesionRq.class)
    public JAXBElement<InformacionSesion> createFinalizarSesionRqInformacionSesion(InformacionSesion value) {
        return new JAXBElement<>(_InformacionSesion_QNAME, InformacionSesion.class, FinalizarSesionRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionToken }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "tokenAcceso", scope = ObtenerTokenRs.class)
    public JAXBElement<InformacionToken> createObtenerTokenRsTokenAcceso(InformacionToken value) {
        return new JAXBElement<>(_InformacionTokenTokenAcceso_QNAME, InformacionToken.class, ObtenerTokenRs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "trace", scope = MensajeFault.class)
    public JAXBElement<ArrayOfstring> createMensajeFaultTrace(ArrayOfstring value) {
        return new JAXBElement<>(_MensajeFaultTrace_QNAME, ArrayOfstring.class, MensajeFault.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "codigo", scope = MensajeFault.class)
    public JAXBElement<String> createMensajeFaultCodigo(String value) {
        return new JAXBElement<>(_MensajeFaultCodigo_QNAME, String.class, MensajeFault.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "mensaje", scope = MensajeFault.class)
    public JAXBElement<String> createMensajeFaultMensaje(String value) {
        return new JAXBElement<>(_MensajeFaultMensaje_QNAME, String.class, MensajeFault.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionNuevaClave }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionSesion", scope = ModificarPasswordRq.class)
    public JAXBElement<InformacionNuevaClave> createModificarPasswordRqInformacionSesion(InformacionNuevaClave value) {
        return new JAXBElement<>(_InformacionSesion_QNAME, InformacionNuevaClave.class, ModificarPasswordRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "tokenAcceso", scope = InformacionNuevaClave.class)
    public JAXBElement<String> createInformacionNuevaClaveTokenAcceso(String value) {
        return new JAXBElement<>(_InformacionTokenTokenAcceso_QNAME, String.class, InformacionNuevaClave.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "nuevaClave", scope = InformacionNuevaClave.class)
    public JAXBElement<String> createInformacionNuevaClaveNuevaClave(String value) {
        return new JAXBElement<>(_InformacionNuevaClaveNuevaClave_QNAME, String.class, InformacionNuevaClave.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "ipHost", scope = EncabezadoSeguridad.class)
    public JAXBElement<String> createEncabezadoSeguridadIpHost(String value) {
        return new JAXBElement<>(_EncabezadoSeguridadIpHost_QNAME, String.class, EncabezadoSeguridad.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionSesion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionToken", scope = ActualizarFechaVencimientoTokenRq.class)
    public JAXBElement<InformacionSesion> createActualizarFechaVencimientoTokenRqInformacionToken(InformacionSesion value) {
        return new JAXBElement<>(_InformacionToken_QNAME, InformacionSesion.class, ActualizarFechaVencimientoTokenRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "roles", scope = InformacionPermisos.class)
    public JAXBElement<ArrayOfstring> createInformacionPermisosRoles(ArrayOfstring value) {
        return new JAXBElement<>(_InformacionPermisosRoles_QNAME, ArrayOfstring.class, InformacionPermisos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOpcion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "permisos", scope = InformacionPermisos.class)
    public JAXBElement<ArrayOfOpcion> createInformacionPermisosPermisos(ArrayOfOpcion value) {
        return new JAXBElement<>(_InformacionPermisosPermisos_QNAME, ArrayOfOpcion.class, InformacionPermisos.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "codigoAutorizacion", scope = Autorizacion.class)
    public JAXBElement<String> createAutorizacionCodigoAutorizacion(String value) {
        return new JAXBElement<>(_ObtenerTokenRqCodigoAutorizacion_QNAME, String.class, Autorizacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionSesion }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionSesion", scope = ObtenerRolesPermisosRq.class)
    public JAXBElement<InformacionSesion> createObtenerRolesPermisosRqInformacionSesion(InformacionSesion value) {
        return new JAXBElement<>(_InformacionSesion_QNAME, InformacionSesion.class, ObtenerRolesPermisosRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Usuario }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "usuario", scope = ObtenerRolesPermisosRs.class)
    public JAXBElement<Usuario> createObtenerRolesPermisosRsUsuario(Usuario value) {
        return new JAXBElement<>(_Usuario_QNAME, Usuario.class, ObtenerRolesPermisosRs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionPermisos }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "permisos", scope = ObtenerRolesPermisosRs.class)
    public JAXBElement<InformacionPermisos> createObtenerRolesPermisosRsPermisos(InformacionPermisos value) {
        return new JAXBElement<>(_InformacionPermisosPermisos_QNAME, InformacionPermisos.class, ObtenerRolesPermisosRs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "tokenAcceso", scope = InformacionSesion.class)
    public JAXBElement<String> createInformacionSesionTokenAcceso(String value) {
        return new JAXBElement<>(_InformacionTokenTokenAcceso_QNAME, String.class, InformacionSesion.class, value);
    }

}
