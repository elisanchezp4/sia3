package co.gov.mineducacion.seguridad.modelo.manejadores;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosAplicacion;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosAplicacionPK;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre la
 * tabla correspondiente a la entidad UsuariosAplicacion.
 * 
 * @author jsoto
 */
@Stateless
public class ManejadorUsuariosAplicacion extends ManejadorCrud<UsuariosAplicacion, UsuariosAplicacionPK> {

	/**
	 * Imprimir logs
	 */
	private static final Logger LOG = Logger.getLogger(ManejadorUsuariosAplicacion.class.getName());
	
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}
	
	public ManejadorUsuariosAplicacion() {
		super(UsuariosAplicacion.class);
	}
	
	 /**
     * Metodo que obtiene lista de usuarios que coinciden para una app
     * que se pasa por parametro
     * @param idAplicacion
     * @return
     * @throws SIA3Exception
     */
    public List<UsuariosAplicacion> getUsuariosPorAplicacion(BigDecimal aplicacionId){
		LOG.info("Inicio metodo getUsuariosPorAplicacion con aplicacionId: " + aplicacionId);
		Query query = em.createNativeQuery(SqlConstants.CONSULTAR_USUARIO_APLICACION, UsuariosAplicacion.class);
		query.setParameter(1, aplicacionId);
		LOG.info("Valor query: " + query);
		
		@SuppressWarnings("unchecked")
		List<UsuariosAplicacion> resultList = query.getResultList();
		LOG.info("Fin metodo getUsuariosPorAplicacion en manejador");
		return resultList;
	}


}
