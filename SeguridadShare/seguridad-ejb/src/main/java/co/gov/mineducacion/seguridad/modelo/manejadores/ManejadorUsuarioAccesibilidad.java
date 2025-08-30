package co.gov.mineducacion.seguridad.modelo.manejadores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;

@Stateless
public class ManejadorUsuarioAccesibilidad extends ManejadorCrud<UsuarioAccesibilidad, Long> {

	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {

		return em;
	}

	public ManejadorUsuarioAccesibilidad() {
		super(UsuarioAccesibilidad.class);
	}

	public List<UsuarioAccesibilidad> consultarPropAccUsrs(String usrsId) {
		List<UsuarioAccesibilidad> usuarioAccesibilidad = new ArrayList<>();
		try {
			TypedQuery<UsuarioAccesibilidad> q = em.createQuery(" select ua from UsuarioAccesibilidad ua where ua.usuarios.usuarioId = :id ",UsuarioAccesibilidad.class);
			q.setParameter("id", usrsId);
			usuarioAccesibilidad = q.getResultList();
			return usuarioAccesibilidad;
		} catch (Exception e) {
			usuarioAccesibilidad = null;
			return usuarioAccesibilidad;
		}
	}
	
	

}
