package co.gov.heinsohn.men.parametros.entitymanagers;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.gov.heinsohn.men.parametros.entities.Parametro;
import co.gov.heinsohn.men.parametros.utils.Constants;

/**
 * Implemantación de los metodos para parametrizar los Parametros
 * 
 * @author Jhonnatan Orozco Duque <jorozco@heinsohn.com.co>
 *
 */
@Stateless
public class ConsultaItemListaValoresEntityManagerEJB extends AbstractEntityManagerEJB<Parametro>
		implements IConsultaItemListaValoresEntityManagerEJB {

	private static final String NOMBRE_APP = "nombreApp";
	private static final String DOMINIO = "dominio";
	private static final String ESTADO = "estado";
	
	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT)
	private EntityManager em;

	public ConsultaItemListaValoresEntityManagerEJB() {
		super(Parametro.class);
	}

	@Override
	protected EntityManager getEntityManager() {

		return em;

	}

	/**
	 * permite obtener un listado de Valores
	 * 
	 * @param nombreApp
	 *            dominio
	 * 
	 * @return List<Parametro>
	 */
	public List<Parametro> consultaListaValoresSimple(String nombreApp, String dominio) {
		System.out.println("Ingresa consultaListaValoresSimple" + nombreApp + " " + dominio);
		Query query = getEntityManager().createNamedQuery(Parametro.BUSCAR_POR_VALORES_SIMPLE, Parametro.class);
		query.setParameter(NOMBRE_APP, nombreApp);
		query.setParameter(DOMINIO, dominio);
		query.setParameter(ESTADO, Constants.NUMERO_UNO);
		System.out.println(query);
		return query.getResultList();
	}

	/**
	 * permite obtener un listado de Parametros
	 * 
	 * @param nombreApp
	 *            dominio
	 * @return Parametro
	 */
	public Parametro consultarParametroSimple(String nombreApp, String dominio) {

		Query query = getEntityManager().createNamedQuery(Parametro.BUSCAR_POR_VALORES_SIMPLE, Parametro.class);
		query.setParameter(NOMBRE_APP, nombreApp);
		query.setParameter(DOMINIO, dominio);
		query.setParameter(ESTADO, Constants.NUMERO_UNO);

		return null;
	}

	/**
	 * permite obtener un listado de Valores multiples
	 * 
	 * @param nombreApp
	 *            dominio dominioPadre codigoPadre
	 * @return List<Parametro>
	 */
	public List<Parametro> consultarListaValoresMultiple(String nombreApp, String dominio, String dominioPadre,
			String codigoPadre) {
		Query query = getEntityManager().createNamedQuery(Parametro.BUSCAR_POR_VALORES_MULTIPLE, Parametro.class);
		query.setParameter(NOMBRE_APP, nombreApp);
		query.setParameter(DOMINIO, dominio);
		query.setParameter("dominioPadre", dominioPadre);
		query.setParameter("codigoPadre", codigoPadre);
		query.setParameter(ESTADO, Constants.NUMERO_UNO);
		return query.getResultList();

	}

}
