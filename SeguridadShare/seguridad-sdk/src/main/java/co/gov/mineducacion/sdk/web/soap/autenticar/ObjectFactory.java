
package co.gov.mineducacion.sdk.web.soap.autenticar;

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

    private static final QName _InformacionNuevaClave_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionNuevaClave"); // NOSONAR
    private static final QName _Usuario_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "usuario"); // NOSONAR
    private static final QName _InformacionPermisos_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionPermisos"); // NOSONAR
    private static final QName _Aplicacion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "aplicacion"); // NOSONAR
    private static final QName _TipoOpcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "TipoOpcion"); // NOSONAR
    private static final QName _Opcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "Opcion"); // NOSONAR
    private static final QName _ArrayOfOpcion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "ArrayOfOpcion"); // NOSONAR
    private static final QName _InformacionSesion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionSesion"); // NOSONAR
    private static final QName _EncabezadoSeguridadRq_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "encabezadoSeguridadRq"); // NOSONAR
    private static final QName _Autorizacion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "autorizacion"); // NOSONAR
    private static final QName _MensajeFault_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "mensajeFault"); // NOSONAR
    private static final QName _InformacionToken_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionToken"); // NOSONAR
    private static final QName _EncabezadoSeguridad_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "encabezadoSeguridad"); // NOSONAR

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.gov.mineducacion.soap._11.seguridad.sia3
     * 
     */
    public ObjectFactory(){/* Recomendacion por sonar */}

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
     * Create an instance of {@link ObtenerTokenRs }
     * 
     */
    public ObtenerTokenRs createObtenerTokenRs() {
        return new ObtenerTokenRs();
    }

    /**
     * Create an instance of {@link ActualizarFechaVencimientoTokenRs }
     * 
     */
    public ActualizarFechaVencimientoTokenRs createActualizarFechaVencimientoTokenRs() {
        return new ActualizarFechaVencimientoTokenRs();
    }

    /**
     * Create an instance of {@link ArrayOfstring }
     * 
     */
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Aplicacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "aplicacion")
    public JAXBElement<Aplicacion> createAplicacion(Aplicacion value) {
        return new JAXBElement<>(_Aplicacion_QNAME, Aplicacion.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Opcion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "Opcion")
    public JAXBElement<Opcion> createOpcion(Opcion value) {
        return new JAXBElement<>(_Opcion_QNAME, Opcion.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionSesion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionSesion")
    public JAXBElement<InformacionSesion> createInformacionSesion(InformacionSesion value) {
        return new JAXBElement<>(_InformacionSesion_QNAME, InformacionSesion.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Autorizacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "autorizacion")
    public JAXBElement<Autorizacion> createAutorizacion(Autorizacion value) {
        return new JAXBElement<>(_Autorizacion_QNAME, Autorizacion.class, null, value);
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

}
