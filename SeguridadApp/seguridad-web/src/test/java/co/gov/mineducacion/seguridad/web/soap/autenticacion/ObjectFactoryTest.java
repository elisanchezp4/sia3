package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import org.junit.Test;
import org.mockito.InjectMocks;

import javax.xml.bind.JAXBElement;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

import static org.junit.Assert.assertNull;
import static org.utbot.runtime.utils.java.UtUtils.createInstance;

public final class ObjectFactoryTest {

    @InjectMocks
    private ObjectFactory objectFactory = new ObjectFactory();

    @Test
    public void testCreateActualizarFechaVencimientoTokenRq_Return() {
        ActualizarFechaVencimientoTokenRq actual = objectFactory.createActualizarFechaVencimientoTokenRq();

        ActualizarFechaVencimientoTokenRq expected = new ActualizarFechaVencimientoTokenRq();
        JAXBElement actualInformacionToken = actual.informacionToken;
        assertNull(actualInformacionToken);

    }

    @Test
    public void testCreateActualizarFechaVencimientoTokenRqInformacionToken() throws Exception {
        InformacionSesion value = new InformacionSesion();

        JAXBElement actual = objectFactory.createActualizarFechaVencimientoTokenRqInformacionToken(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateActualizarFechaVencimientoTokenRs_Return() {
        ActualizarFechaVencimientoTokenRs actual = objectFactory.createActualizarFechaVencimientoTokenRs();

        ActualizarFechaVencimientoTokenRs expected = new ActualizarFechaVencimientoTokenRs();
        JAXBElement actualInformacionToken = actual.informacionToken;
        assertNull(actualInformacionToken);

    }

    @Test
    public void testCreateActualizarFechaVencimientoTokenRsInformacionToken() throws Exception {
        InformacionToken value = new InformacionToken();

        JAXBElement actual = objectFactory.createActualizarFechaVencimientoTokenRsInformacionToken(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateAplicacion_Return() {
        Aplicacion actual = objectFactory.createAplicacion();

        Aplicacion expected = new Aplicacion();
        JAXBElement actualApiKey = actual.apiKey;
        assertNull(actualApiKey);

        Integer actualUserId = actual.userId;
        assertNull(actualUserId);

    }

    @Test
    public void testCreateAplicacion() throws Exception {
        Aplicacion value = new Aplicacion();

        JAXBElement actual = objectFactory.createAplicacion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateAplicacionApiKeyWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createAplicacionApiKey("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateArrayOfOpcion_Return() {
        ArrayOfOpcion actual = objectFactory.createArrayOfOpcion();

        ArrayOfOpcion expected = new ArrayOfOpcion();
        List actualOpcion = actual.opcion;
        assertNull(actualOpcion);

    }

    @Test
    public void testCreateArrayOfOpcion() throws Exception {
        ArrayOfOpcion value = new ArrayOfOpcion();

        JAXBElement actual = objectFactory.createArrayOfOpcion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateAutorizacion_Return() {
        Autorizacion actual = objectFactory.createAutorizacion();

        Autorizacion expected = new Autorizacion();
        JAXBElement actualCodigoAutorizacion = actual.codigoAutorizacion;
        assertNull(actualCodigoAutorizacion);

    }

    @Test
    public void testCreateAutorizacion() throws Exception {
        Autorizacion value = new Autorizacion();

        JAXBElement actual = objectFactory.createAutorizacion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateAutorizacionCodigoAutorizacionWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createAutorizacionCodigoAutorizacion("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateEncabezadoSeguridad_Return() {
        EncabezadoSeguridad actual = objectFactory.createEncabezadoSeguridad();

        EncabezadoSeguridad expected = new EncabezadoSeguridad();
        XMLGregorianCalendar actualFechaPeticion = actual.fechaPeticion;
        assertNull(actualFechaPeticion);

        JAXBElement actualIpHost = actual.ipHost;
        assertNull(actualIpHost);

    }

    @Test
    public void testCreateEncabezadoSeguridad() throws Exception {
        EncabezadoSeguridad value = new EncabezadoSeguridad();

        JAXBElement actual = objectFactory.createEncabezadoSeguridad(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateEncabezadoSeguridadIpHostWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createEncabezadoSeguridadIpHost("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateEncabezadoSeguridadRq() throws Exception {
        EncabezadoSeguridad value = new EncabezadoSeguridad();

        JAXBElement actual = objectFactory.createEncabezadoSeguridadRq(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateFinalizarSesionRq_Return() {
        FinalizarSesionRq actual = objectFactory.createFinalizarSesionRq();

        FinalizarSesionRq expected = new FinalizarSesionRq();
        JAXBElement actualInformacionSesion = actual.informacionSesion;
        assertNull(actualInformacionSesion);

    }

    @Test
    public void testCreateFinalizarSesionRqInformacionSesion() throws Exception {
        InformacionSesion value = new InformacionSesion();

        JAXBElement actual = objectFactory.createFinalizarSesionRqInformacionSesion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionNuevaClave_Return() {
        InformacionNuevaClave actual = objectFactory.createInformacionNuevaClave();

        InformacionNuevaClave expected = new InformacionNuevaClave();
        JAXBElement actualNuevaClave = actual.nuevaClave;
        assertNull(actualNuevaClave);

        JAXBElement actualTokenAcceso = actual.tokenAcceso;
        assertNull(actualTokenAcceso);

    }

    @Test
    public void testCreateInformacionNuevaClave() throws Exception {
        InformacionNuevaClave value = new InformacionNuevaClave();

        JAXBElement actual = objectFactory.createInformacionNuevaClave(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionNuevaClaveNuevaClave1() throws Exception {
        JAXBElement actual = objectFactory.createInformacionNuevaClaveNuevaClave(null);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionNuevaClaveTokenAcceso1() throws Exception {
        JAXBElement actual = objectFactory.createInformacionNuevaClaveTokenAcceso(null);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionPermisos_Return() {
        InformacionPermisos actual = objectFactory.createInformacionPermisos();

        InformacionPermisos expected = new InformacionPermisos();
        JAXBElement actualPermisos = actual.permisos;
        assertNull(actualPermisos);

        JAXBElement actualRoles = actual.roles;
        assertNull(actualRoles);

    }

    @Test
    public void testCreateInformacionPermisos() throws Exception {
        InformacionPermisos value = new InformacionPermisos();

        JAXBElement actual = objectFactory.createInformacionPermisos(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionPermisosPermisos1() throws Exception {
        JAXBElement actual = objectFactory.createInformacionPermisosPermisos(null);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionPermisosRoles() throws Exception {
        ArrayOfstring value = new ArrayOfstring();

        JAXBElement actual = objectFactory.createInformacionPermisosRoles(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionSesion_Return() {
        InformacionSesion actual = objectFactory.createInformacionSesion();

        InformacionSesion expected = new InformacionSesion();
        JAXBElement actualTokenAcceso = actual.tokenAcceso;
        assertNull(actualTokenAcceso);

    }

    @Test
    public void testCreateInformacionSesion() throws Exception {
        InformacionSesion value = new InformacionSesion();

        JAXBElement actual = objectFactory.createInformacionSesion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionSesionTokenAccesoWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createInformacionSesionTokenAcceso("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionToken_Return() {
        InformacionToken actual = objectFactory.createInformacionToken();

        InformacionToken expected = new InformacionToken();
        XMLGregorianCalendar actualFechaExpriracion = actual.fechaExpriracion;
        assertNull(actualFechaExpriracion);

        JAXBElement actualTokenAcceso = actual.tokenAcceso;
        assertNull(actualTokenAcceso);

    }

    @Test
    public void testCreateInformacionToken() throws Exception {
        InformacionToken value = new InformacionToken();

        JAXBElement actual = objectFactory.createInformacionToken(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateInformacionTokenTokenAccesoWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createInformacionTokenTokenAcceso("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateMensajeFault_Return() {
        MensajeFault actual = objectFactory.createMensajeFault();

        MensajeFault expected = new MensajeFault();
        JAXBElement actualCodigo = actual.codigo;
        assertNull(actualCodigo);

        JAXBElement actualMensaje = actual.mensaje;
        assertNull(actualMensaje);

        JAXBElement actualTrace = actual.trace;
        assertNull(actualTrace);

    }

    @Test
    public void testCreateMensajeFault() throws Exception {
        MensajeFault value = new MensajeFault();
        value.setTrace(null);
        value.setMensaje(null);

        JAXBElement actual = objectFactory.createMensajeFault(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateMensajeFaultCodigoWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createMensajeFaultCodigo("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateMensajeFaultMensajeWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createMensajeFaultMensaje("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateMensajeFaultTrace() throws Exception {
        ArrayOfstring value = new ArrayOfstring();

        JAXBElement actual = objectFactory.createMensajeFaultTrace(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateModificarPasswordRq_Return() {
        ModificarPasswordRq actual = objectFactory.createModificarPasswordRq();

        ModificarPasswordRq expected = new ModificarPasswordRq();
        JAXBElement actualInformacionSesion = actual.informacionSesion;
        assertNull(actualInformacionSesion);

    }

    @Test
    public void testCreateModificarPasswordRqInformacionSesion() throws Exception {
        InformacionNuevaClave value = new InformacionNuevaClave();

        JAXBElement actual = objectFactory.createModificarPasswordRqInformacionSesion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateObtenerRolesPermisosRq_Return() {
        ObtenerRolesPermisosRq actual = objectFactory.createObtenerRolesPermisosRq();

        ObtenerRolesPermisosRq expected = new ObtenerRolesPermisosRq();
        JAXBElement actualInformacionSesion = actual.informacionSesion;
        assertNull(actualInformacionSesion);

    }

    @Test
    public void testCreateObtenerRolesPermisosRqInformacionSesion() throws Exception {
        InformacionSesion value = new InformacionSesion();

        JAXBElement actual = objectFactory.createObtenerRolesPermisosRqInformacionSesion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateObtenerRolesPermisosRs_Return() {
        ObtenerRolesPermisosRs actual = objectFactory.createObtenerRolesPermisosRs();

        ObtenerRolesPermisosRs expected = new ObtenerRolesPermisosRs();
        JAXBElement actualPermisos = actual.permisos;
        assertNull(actualPermisos);

        JAXBElement actualUsuario = actual.usuario;
        assertNull(actualUsuario);

    }

    @Test
    public void testCreateObtenerRolesPermisosRsPermisos() throws Exception {
        InformacionPermisos value = new InformacionPermisos();

        JAXBElement actual = objectFactory.createObtenerRolesPermisosRsPermisos(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateObtenerRolesPermisosRsUsuario() throws Exception {
        Usuario value = new Usuario();
        value.setUserId(-1);
        value.setNombres(null);

        JAXBElement actual = objectFactory.createObtenerRolesPermisosRsUsuario(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateObtenerTokenRq_Return() {
        ObtenerTokenRq actual = objectFactory.createObtenerTokenRq();

        ObtenerTokenRq expected = new ObtenerTokenRq();
        JAXBElement actualCodigoAutorizacion = actual.codigoAutorizacion;
        assertNull(actualCodigoAutorizacion);

    }

    @Test
    public void testCreateObtenerTokenRqCodigoAutorizacion() throws Exception {
        Autorizacion value = new Autorizacion();

        JAXBElement actual = objectFactory.createObtenerTokenRqCodigoAutorizacion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateObtenerTokenRs_Return() {
        ObtenerTokenRs actual = objectFactory.createObtenerTokenRs();

        ObtenerTokenRs expected = new ObtenerTokenRs();
        JAXBElement actualTokenAcceso = actual.tokenAcceso;
        assertNull(actualTokenAcceso);

    }

    @Test
    public void testCreateObtenerTokenRsTokenAcceso() throws Exception {
        InformacionToken value = new InformacionToken();

        JAXBElement actual = objectFactory.createObtenerTokenRsTokenAcceso(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateOpcion_Return() {
        Opcion actual = objectFactory.createOpcion();

        Opcion expected = new Opcion();
        JAXBElement actualDescripcion = actual.descripcion;
        assertNull(actualDescripcion);

        JAXBElement actualHijosOpcion = actual.hijosOpcion;
        assertNull(actualHijosOpcion);

        JAXBElement actualNombreObjeto = actual.nombreObjeto;
        assertNull(actualNombreObjeto);

        Integer actualOpcionId = actual.opcionId;
        assertNull(actualOpcionId);

        TipoOpcion actualTipo = actual.tipo;
        assertNull(actualTipo);

        JAXBElement actualNombreRolAsociado = actual.nombreRolAsociado;
        assertNull(actualNombreRolAsociado);

    }

    @Test
    public void testCreateOpcion() throws Exception {
        Opcion value = new Opcion();
        value.setHijosOpcion(null);
        value.setOpcionId(-1);
        value.setNombreRolAsociado(null);
        TipoOpcion tipoOpcion = TipoOpcion.SUBMENU;
        value.setTipo(tipoOpcion);
        value.setNombreObjeto(null);
        value.setDescripcion(null);

        JAXBElement actual = objectFactory.createOpcion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateOpcionDescripcionWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createOpcionDescripcion("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateOpcionHijosOpcion() throws Exception {
        ArrayOfOpcion value = new ArrayOfOpcion();

        JAXBElement actual = objectFactory.createOpcionHijosOpcion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateOpcionNombreObjetoWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createOpcionNombreObjeto("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateOpcionNombreRolAsociadoWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createOpcionNombreRolAsociado("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateTipoOpcion() throws Exception {
        TipoOpcion value = TipoOpcion.VINCULO;

        JAXBElement actual = objectFactory.createTipoOpcion(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateUsuario() {
        Usuario actual = objectFactory.createUsuario();

        Usuario expected = new Usuario();
        JAXBElement actualApellidos = actual.apellidos;
        assertNull(actualApellidos);

        JAXBElement actualNombres = actual.nombres;
        assertNull(actualNombres);

        Integer actualUserId = actual.userId;
        assertNull(actualUserId);

    }

    @Test
    public void testCreateUsuario1() throws Exception {
        Usuario value = new Usuario();
        value.setUserId(-1);
        value.setNombres(null);

        JAXBElement actual = objectFactory.createUsuario(value);

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateUsuarioApellidosWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createUsuarioApellidos("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

    @Test
    public void testCreateUsuarioNombresWithEmptyString() throws Exception {
        JAXBElement actual = objectFactory.createUsuarioNombres("");

        JAXBElement expected = ((JAXBElement) createInstance("javax.xml.bind.JAXBElement"));
    }

}
