package co.gov.mineducacion.seguridad.negocio;


import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.utils.LdapValidacionesUtil;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Singleton
@Startup
public class NegocioPropiedadesLdapValidacion {

    private static final Logger logger = Logger.getLogger(NegocioPropiedadesLdapValidacion.class);

    @PostConstruct
    public void init(){
        try {
            logger.info("Iniciaciondo clase para validaciones de campos del ldap");
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            final String rutaArchivo = (String) env.lookup("validaciones-ldap-ruta");
            logger.info("rutaArchivo------>"+rutaArchivo);
            LdapValidacionesUtil.inizializar(rutaArchivo);
        } catch (NamingException | SIA3Exception e) {
            logger.error("No se encuentra el archivo de configuración para el archivo de propiedades");
        }
    }

}
