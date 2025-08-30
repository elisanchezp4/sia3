package co.gov.mineducacion.seguridad.modelo.manejadores;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.gov.mineducacion.seguridad.modelo.entidades.Parametro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;


/**
 * Manejador de la entidad Parametro
 * @author jfonseca
 *
 */
@Stateless
public class ManejadorParametro extends ManejadorCrud<Parametro, String> {
	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public ManejadorParametro() {
		super(Parametro.class);
	}
	
	/**
	 * retorna el listado de parametros 
	 * @return
	 */
	public List<Parametro> consultarParametros(){
		
		return this.em.createNamedQuery("Parametro.findAll").getResultList();
		
	}

}
