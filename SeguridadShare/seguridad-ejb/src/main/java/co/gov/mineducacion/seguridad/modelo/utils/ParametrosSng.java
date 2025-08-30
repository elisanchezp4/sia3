package co.gov.mineducacion.seguridad.modelo.utils;


import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;


import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.modelo.entidades.Parametro;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorParametro;

/**
 * Singleton que contiene los parametros del sistema dentro de un objeto Properties,
 * estos parametros se encuentran en la tabla PARAMETROS
 *
 * @author Asesoftware - Javier Estévez
 */
@Singleton
public class ParametrosSng {

    private static final Logger logger = Logger.getLogger(ParametrosSng.class);

    private Properties propiedades;

    @EJB
    private ManejadorParametro manejadorParametro;

    @PostConstruct
    public void init() {
        logger.info("Inicia consulta parametros global");
        propiedades = new Properties();
        List<Parametro> lstParametros = manejadorParametro.consultarParametros();
        if (lstParametros != null) {
            for (Parametro p : lstParametros) {
                propiedades.setProperty(p.getNombre(), p.getValor());
            }
        }


    }

    public Properties obtenerParametros() {

        return propiedades;

    }
}
