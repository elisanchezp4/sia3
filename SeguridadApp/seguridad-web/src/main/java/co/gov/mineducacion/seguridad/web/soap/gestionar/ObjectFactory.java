
package co.gov.mineducacion.seguridad.web.soap.gestionar;

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

    private static final QName _InformacionUsuarioEmail_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "email"); // NOSONAR
    private static final QName _InformacionUsuarioApellidos_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "apellidos"); // NOSONAR
    private static final QName _InformacionUsuarioTipoIdentificacion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "TipoIdentificacion"); // NOSONAR
    private static final QName _InformacionUsuarioNombres_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "nombres"); // NOSONAR
    private static final QName _InformacionUsuarioNombreUsuario_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "nombreUsuario"); // NOSONAR
    private static final QName _ConsultarUsuariosRolRqInformacionRol_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionRol"); // NOSONAR
    private static final QName _InformacionUsuario_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "informacionUsuario"); // NOSONAR
    private static final QName _Aplicacion_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "aplicacion"); // NOSONAR
    private static final QName _IdentificadorUsuario_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "identificadorUsuario"); // NOSONAR
    private static final QName _ArrayOfinformacionUsuario_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "ArrayOfinformacionUsuario"); // NOSONAR
    private static final QName _EncabezadoSeguridad_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "encabezadoSeguridad"); // NOSONAR
    private static final QName _EncabezadoSeguridadRq_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "encabezadoSeguridadRq"); // NOSONAR
    private static final QName _TipoUsuario_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "TipoUsuario"); // NOSONAR
    private static final QName _MensajeFault_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "mensajeFault"); // NOSONAR
    private static final QName _InformacionRolRol_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "rol"); // NOSONAR
    private static final QName _MensajeFaultTrace_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "trace"); // NOSONAR
    private static final QName _MensajeFaultCodigo_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "codigo"); // NOSONAR
    private static final QName _MensajeFaultMensaje_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "mensaje"); // NOSONAR
    private static final QName _AplicacionApiKey_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "apiKey"); // NOSONAR
    private static final QName _ConsultarUsuariosRolRsUsuarios_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "usuarios"); // NOSONAR
    private static final QName _EncabezadoSeguridadIpHost_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "ipHost"); // NOSONAR
    private static final QName _DesvincularRolesUsuariosUserId_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "userId"); // NOSONAR
    private static final QName _DesvincularRolesUsuariosRoles_QNAME = new QName("http://soap.mineducacion.gov.co/11/seguridad/sia3", "roles"); // NOSONAR

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.gov.mineducacion.soap._11.seguridad.sia3
     * 
     */
    public ObjectFactory() {
        // Auto-generated method stub
    }

    /**
     * Create an instance of {@link InformacionUsuario }
     * 
     */
    public InformacionUsuario createInformacionUsuario() {
        return new InformacionUsuario();
    }

    /**
     * Create an instance of {@link CrearUsuarioExternoRs }
     * 
     */
    public CrearUsuarioExternoRs createCrearUsuarioExternoRs() {
        return new CrearUsuarioExternoRs();
    }

    /**
     * Create an instance of {@link IdentificadorUsuario }
     * 
     */
    public IdentificadorUsuario createIdentificadorUsuario() {
        return new IdentificadorUsuario();
    }

    /**
     * Create an instance of {@link ModificarUsuarioExternoRq }
     * 
     */
    public ModificarUsuarioExternoRq createModificarUsuarioExternoRq() {
        return new ModificarUsuarioExternoRq();
    }

    /**
     * Create an instance of {@link ConsultarUsuariosRolRq }
     * 
     */
    public ConsultarUsuariosRolRq createConsultarUsuariosRolRq() {
        return new ConsultarUsuariosRolRq();
    }

    /**
     * Create an instance of {@link InformacionRol }
     * 
     */
    public InformacionRol createInformacionRol() {
        return new InformacionRol();
    }

    /**
     * Create an instance of {@link ConsultarUsuariosRolRs }
     * 
     */
    public ConsultarUsuariosRolRs createConsultarUsuariosRolRs() {
        return new ConsultarUsuariosRolRs();
    }

    /**
     * Create an instance of {@link ArrayOfinformacionUsuario }
     * 
     */
    public ArrayOfinformacionUsuario createArrayOfinformacionUsuario() {
        return new ArrayOfinformacionUsuario();
    }

    /**
     * Create an instance of {@link EncabezadoSeguridad }
     * 
     */
    public EncabezadoSeguridad createEncabezadoSeguridad() {
        return new EncabezadoSeguridad();
    }

    /**
     * Create an instance of {@link CrearUsuarioExternoRq }
     * 
     */
    public CrearUsuarioExternoRq createCrearUsuarioExternoRq() {
        return new CrearUsuarioExternoRq();
    }

    /**
     * Create an instance of {@link InactivarUsuarioRq }
     * 
     */
    public InactivarUsuarioRq createInactivarUsuarioRq() {
        return new InactivarUsuarioRq();
    }

    /**
     * Create an instance of {@link Aplicacion }
     * 
     */
    public Aplicacion createAplicacion() {
        return new Aplicacion();
    }

    /**
     * Create an instance of {@link MensajeFault }
     * 
     */
    public MensajeFault createMensajeFault() {
        return new MensajeFault();
    }

    /**
     * Create an instance of {@link DesvincularRolesUsuarioRq }
     *
     */
    public DesvincularRolesUsuarioRq createDesvincularRolesUsuariosRq() {
        return new DesvincularRolesUsuarioRq();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "email", scope = InformacionUsuario.class)
    public JAXBElement<String> createInformacionUsuarioEmail(String value) {
        return new JAXBElement<>(_InformacionUsuarioEmail_QNAME, String.class, InformacionUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "apellidos", scope = InformacionUsuario.class)
    public JAXBElement<String> createInformacionUsuarioApellidos(String value) {
        return new JAXBElement<>(_InformacionUsuarioApellidos_QNAME, String.class, InformacionUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "TipoIdentificacion", scope = InformacionUsuario.class)
    public JAXBElement<String> createInformacionUsuarioTipoIdentificacion(String value) {
        return new JAXBElement<>(_InformacionUsuarioTipoIdentificacion_QNAME, String.class, InformacionUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "nombres", scope = InformacionUsuario.class)
    public JAXBElement<String> createInformacionUsuarioNombres(String value) {
        return new JAXBElement<>(_InformacionUsuarioNombres_QNAME, String.class, InformacionUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "nombreUsuario", scope = InformacionUsuario.class)
    public JAXBElement<String> createInformacionUsuarioNombreUsuario(String value) {
        return new JAXBElement<>(_InformacionUsuarioNombreUsuario_QNAME, String.class, InformacionUsuario.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionRol }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionRol", scope = ConsultarUsuariosRolRq.class)
    public JAXBElement<InformacionRol> createConsultarUsuariosRolRqInformacionRol(InformacionRol value) {
        return new JAXBElement<>(_ConsultarUsuariosRolRqInformacionRol_QNAME, InformacionRol.class, ConsultarUsuariosRolRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionUsuario")
    public JAXBElement<InformacionUsuario> createInformacionUsuario(InformacionUsuario value) {
        return new JAXBElement<>(_InformacionUsuario_QNAME, InformacionUsuario.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificadorUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "identificadorUsuario")
    public JAXBElement<IdentificadorUsuario> createIdentificadorUsuario(IdentificadorUsuario value) {
        return new JAXBElement<>(_IdentificadorUsuario_QNAME, IdentificadorUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfinformacionUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "ArrayOfinformacionUsuario")
    public JAXBElement<ArrayOfinformacionUsuario> createArrayOfinformacionUsuario(ArrayOfinformacionUsuario value) {
        return new JAXBElement<>(_ArrayOfinformacionUsuario_QNAME, ArrayOfinformacionUsuario.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionRol }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionRol")
    public JAXBElement<InformacionRol> createInformacionRol(InformacionRol value) {
        return new JAXBElement<>(_ConsultarUsuariosRolRqInformacionRol_QNAME, InformacionRol.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "TipoUsuario")
    public JAXBElement<TipoUsuario> createTipoUsuario(TipoUsuario value) {
        return new JAXBElement<>(_TipoUsuario_QNAME, TipoUsuario.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "rol", scope = InformacionRol.class)
    public JAXBElement<String> createInformacionRolRol(String value) {
        return new JAXBElement<>(_InformacionRolRol_QNAME, String.class, InformacionRol.class, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "apiKey", scope = Aplicacion.class)
    public JAXBElement<String> createAplicacionApiKey(String value) {
        return new JAXBElement<>(_AplicacionApiKey_QNAME, String.class, Aplicacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificadorUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionUsuario", scope = InactivarUsuarioRq.class)
    public JAXBElement<IdentificadorUsuario> createInactivarUsuarioRqInformacionUsuario(IdentificadorUsuario value) {
        return new JAXBElement<>(_InformacionUsuario_QNAME, IdentificadorUsuario.class, InactivarUsuarioRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificadorUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionUsuario", scope = CrearUsuarioExternoRs.class)
    public JAXBElement<IdentificadorUsuario> createCrearUsuarioExternoRsInformacionUsuario(IdentificadorUsuario value) {
        return new JAXBElement<>(_InformacionUsuario_QNAME, IdentificadorUsuario.class, CrearUsuarioExternoRs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionUsuario", scope = CrearUsuarioExternoRq.class)
    public JAXBElement<InformacionUsuario> createCrearUsuarioExternoRqInformacionUsuario(InformacionUsuario value) {
        return new JAXBElement<>(_InformacionUsuario_QNAME, InformacionUsuario.class, CrearUsuarioExternoRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionRol }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionRol", scope = CrearUsuarioExternoRq.class)
    public JAXBElement<InformacionRol> createCrearUsuarioExternoRqInformacionRol(InformacionRol value) {
        return new JAXBElement<>(_ConsultarUsuariosRolRqInformacionRol_QNAME, InformacionRol.class, CrearUsuarioExternoRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfinformacionUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "usuarios", scope = ConsultarUsuariosRolRs.class)
    public JAXBElement<ArrayOfinformacionUsuario> createConsultarUsuariosRolRsUsuarios(ArrayOfinformacionUsuario value) {
        return new JAXBElement<>(_ConsultarUsuariosRolRsUsuarios_QNAME, ArrayOfinformacionUsuario.class, ConsultarUsuariosRolRs.class, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link InformacionUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "informacionUsuario", scope = ModificarUsuarioExternoRq.class)
    public JAXBElement<InformacionUsuario> createModificarUsuarioExternoRqInformacionUsuario(InformacionUsuario value) {
        return new JAXBElement<>(_InformacionUsuario_QNAME, InformacionUsuario.class, ModificarUsuarioExternoRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "userId", scope = DesvincularRolesUsuarioRq.class)
    public JAXBElement<String> createDesvincularRolesUsuariosUserId(String value) {
        return new JAXBElement<>(_DesvincularRolesUsuariosUserId_QNAME, String.class, DesvincularRolesUsuarioRq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", name = "roles", scope = DesvincularRolesUsuarioRq.class)
    public JAXBElement<ArrayOfstring> createDesvincularRolesUsuariosRoles(ArrayOfstring value) {
        return new JAXBElement<>(_DesvincularRolesUsuariosRoles_QNAME, ArrayOfstring.class, DesvincularRolesUsuarioRq.class, value);
    }
}
