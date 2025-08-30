package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.entidades.TokensActivos;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre
 * la tabla correspondiente a la entidad TokensActivos.
 * 
 * @author jsoto
 */
@Stateless
public class ManejadorTokensActivos extends ManejadorCrud<TokensActivos,String>{

    private static final Logger logger = Logger.getLogger(ManejadorTokensActivos.class);

    public ManejadorTokensActivos() {
        super(TokensActivos.class);
    }   
    
    /**
     * Elimina los tokens cuya fecha de vencimiento sea
     * menor o igual a la fecha recibida por parametro
     * @param fecha
     */
    public void eliminarTokensVencidos(Timestamp fecha){
    	
    	Query query = this.mp.createNamedQuery("TokensActivos.borrarVencidos");
        logger.info("Ejecuta eliminacion de tokens vencidos--->" + query);
        logger.info("Parametro fechaVto--->" + fecha);
        query.setParameter("fechaVto", fecha);
    	
    	query.executeUpdate();
    	
    	
    }
    
    // protected region Use esta region para su implementacion del manejador on begin 
    
    // protected region Use esta region para su implementacion del manejador end        
}

