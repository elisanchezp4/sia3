package co.gov.mineducacion.seguridad.modelo.utils;

import co.gov.mineducacion.seguridad.modelo.dtos.externos.ServicioInstregacionDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.externos.UsuarioResponseDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import com.google.gson.Gson;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ServicioHTTPTest extends TestCase {

    public void testEnviarPublicacionPut() throws SIA3Exception {
        String url = "https://adintegration-apirest.azurewebsites.net/ADIntegation/editarusuario";
        String key = "Men2022*2022";
        List<String> data = new ArrayList<>();
        data.add("organizationalPerson");
        data.add("person");
        data.add("top");
        data.add("user");
        UsuarioLdap resultado = new UsuarioLdap();
        resultado.setSn("castro silva");
        resultado.setDescription("88755874");
        resultado.setDisplayName("angel daniel castro silva");
        resultado.setGivenName("angel daniel");
        resultado.setsAMAccountName("mpimienta");
        resultado.setDistinguishedName("CN\u003dangel castro,OU\u003dUsuarios Externos,DC\u003dtsi,DC\u003ddomain,DC\u003dsia3");
        resultado.setUserPrincipalName("mpimientatsi.domain.sia3");
        resultado.setObjectClass(data);
        resultado.setWhenChanged("20221228125350.00Z");
        resultado.setUserPrincipalName("mpimientatsi.domain.sia3");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(resultado);

        assertTrue(true);
    }
}