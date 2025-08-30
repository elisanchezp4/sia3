package co.gov.mineducacion.seguridad.modelo.manejadores;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.gov.mineducacion.seguridad.modelo.entidades.PropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;

@Stateless
public class ManejadorPropiedadesAccesibilidad extends ManejadorCrud<PropiedadesAccesibilidad, Long> {

	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {

		return em;
	}

	public ManejadorPropiedadesAccesibilidad() {
		super(PropiedadesAccesibilidad.class);
	}

	public List<PropiedadesAccesibilidad> obtenerPropAcc() {

		List<PropiedadesAccesibilidad> listResult = null;
		TypedQuery<PropiedadesAccesibilidad> q = em.createQuery(" select pa from PropiedadesAccesibilidad pa where pa.estado = 1 order by pa.indice asc ",PropiedadesAccesibilidad.class);
		listResult = q.getResultList();
		return listResult;

	}
}
