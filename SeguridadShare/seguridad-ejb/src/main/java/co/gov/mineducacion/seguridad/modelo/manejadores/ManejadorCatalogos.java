package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.negocio.NegocioCatalogos;
import co.gov.mineducacion.seguridad.modelo.entidades.Catalogos;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre
 * la tabla correspondiente a la entidad Aplicaciones.
 * 
 * @author jsoto
 */
@Stateless
public class ManejadorCatalogos extends ManejadorCrud<Catalogos,BigDecimal>{
	
	//Inicio variables HBT
	
    private static final Logger LOG = Logger.getLogger(ManejadorCatalogos.class.getName());
	
    /**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}
    
	//Fin variables HBT
	
    public ManejadorCatalogos() {
        super(Catalogos.class);
    }   
    
    // protected region Use esta region para su implementacion del manejador on begin 
    
    // protected region Use esta region para su implementacion del manejador end
    
    //Inicio metodos HBT
    
    /**
     * Metodo que obtiene una lista de catalogos segun un tipo de catalogo ingresado
     * @param tipoCatalogo
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Catalogos> listarCatalogoPorTipo(String tipoCatalogo) {
			LOG.info("Inicio metodo listarCatalogoPorTipo");
			Query query = em.createNativeQuery(SqlConstants.CONSULTAR_CATALOGO_POR_TIPO, Catalogos.class);			
			query.setParameter(1, tipoCatalogo);
			LOG.info("Valor query: " + query);
			List<Catalogos> listaResultado = query.getResultList();
			LOG.info("Fin metodo listarCatalogoPorTipo");
			return listaResultado;
	}
    
    /**
     * Metodo que obtiene una lista de catalogos segun un tipo de catalogo ingresado
     * y que pertenecen solo a la aplicacion de seguridad
     * @param tipoCatalogo
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Catalogos> listarCatalogoPorTipoSeguridad(String tipoCatalogo) {
			LOG.info("Inicio metodo listarCatalogoPorTipoSeguridad");
			
			StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_CATALOGO_POR_TIPO);
			consulta.append(SqlConstants.FILTRO_CATALOGO_SEGURIDAD);
			Query query = em.createNativeQuery(consulta.toString(), Catalogos.class);
			query.setParameter(1, tipoCatalogo);
			//Filtrar resultados por el id de la aplicacion de seguridad
			query.setParameter(2, Constantes.HBT_ID_APP_SEGURIDAD);
			LOG.info("Valor query: " + query);
			List<Catalogos> listaResultado = query.getResultList();
			LOG.info("Fin metodo listarCatalogoPorTipoSeguridad");
			return listaResultado;
	}
    
    //Fin metodos HBT

}

