package co.gov.mineducacion.seguridad.modelo.manejadores;


import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRol;
import co.gov.mineducacion.seguridad.modelo.entidades.OperacionesRolPK;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre
 * la tabla correspondiente a la entidad OperacionesRol.
 * 
 * @author hfabra
 */
@Stateless
public class ManejadorOperacionesRol extends ManejadorCrud<OperacionesRol,OperacionesRolPK>{

	// Inicio variables HBT

	private static final Logger LOG = Logger.getLogger(ManejadorOperacionesRol.class.getName());
	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	// Fin variables HBT

    public ManejadorOperacionesRol() {
        super(OperacionesRol.class);
    }
    
    public List<OperacionesRol> buscarOperacionesRolXRol(List<BigDecimal> rolId, BigDecimal clientID) {
    	ArrayList<Object> parameters = new ArrayList<>();
    	parameters.add(rolId);
    	parameters.add(clientID);
    	return (List<OperacionesRol>) createNamedQuery(parameters, "OperacionesRol.findRolOperacionesByRol");
    }
    // protected region Use esta region para su implementacion del manejador on begin 
    
    // protected region Use esta region para su implementacion del manejador end
    
    //Inicio metodos HBT
    /**
     * Metodo que lista operacionesRol de una operacion dada
     * 
     * @param operacionId
     * @return
     */
    public List<OperacionesRol> buscarOperacionesRolXOperacion(String operacionId) {
    	ArrayList<Object> parameters = new ArrayList<>();
    	parameters.add(operacionId);
    	return (List<OperacionesRol>) createNamedQuery(parameters, "OperacionesRol.findOperacionRolByOperacion");
    }
    
    /**
     * Metodo que lista operacionesRol de un rol dada
     * 
     * @param rolId
     * @return
     */
    public List<OperacionesRol> buscarOperacionesRolXRol(BigDecimal rolId) {
    	ArrayList<Object> parameters = new ArrayList<>();
    	parameters.add(rolId);
    	return (List<OperacionesRol>) createNamedQuery(parameters, "OperacionesRol.findOperacionRolByRol");
    }
    
    /**
     * Metodo que obtiene la operacion que coincida con operacion y rol dado
     * @param operacionId
     * @param roldId
     * @return
     */
    public List<OperacionesRol> buscarOperacionesRolXOperacionAndRol(String operacionId, String rolId) {
    	LOG.info("Inicio metodo buscarOperacionesRolXOperacionAndRol con operacionId: " + operacionId + " y rolId:" + rolId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_OPERACIONES_POR_OPCION_Y_ROL, OperacionesRol.class);
		query.setParameter(1, operacionId);
		query.setParameter(2, rolId);
		LOG.info("Valor query: " + query);

		@SuppressWarnings("unchecked")
		List<OperacionesRol> resultList = query.getResultList();
		LOG.info("Fin metodo buscarOperacionesRolXOperacionAndRol en manejador");
		return resultList;   	
    }
    
    //Fin metodos HBT

}

