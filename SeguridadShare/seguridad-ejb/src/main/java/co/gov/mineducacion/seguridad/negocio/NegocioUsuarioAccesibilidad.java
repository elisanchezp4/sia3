package co.gov.mineducacion.seguridad.negocio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import co.gov.mineducacion.seguridad.ejb.servicios.impl.UsuarioAccesibilidadImpl;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuarioAccesibilidadDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.PropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorPropiedadesAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarioAccesibilidad;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;

@Stateless
public class NegocioUsuarioAccesibilidad extends NegocioAbstracto<UsuarioAccesibilidad, UsuarioAccesibilidadDTO> {

	@EJB
	private ManejadorUsuarioAccesibilidad manejadorUsuarioAccesibilidad;

	@EJB
	private ManejadorPropiedadesAccesibilidad manejadorPropiedadesAccesibilidad;

	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	private static final Logger logger = Logger.getLogger(NegocioUsuarioAccesibilidad.class);

	/**
	 * Método encargado de consultar las propiedades de accesiilidad configurados
	 * por el usuario, si no existen crea las propiedades por defecto.
	 * 
	 * @param usrsId
	 */
	public Map<String, Object> /* List<UsuarioAccesibilidadDTO> */ consultrarPropAccUsuario(String usrsId) {
		Map<String, Object> accesibilidad = new HashMap<>();
		String internalCode;
		String exceptionMessage;
		List<UsuarioAccesibilidadDTO> listUsrsAccDTO = new ArrayList<>();
		List<UsuarioAccesibilidad> listUsrsAcc = new ArrayList<>();

		try {
			listUsrsAcc = manejadorUsuarioAccesibilidad.consultarPropAccUsrs(usrsId);

			if (listUsrsAcc.isEmpty()) {
				List<PropiedadesAccesibilidad> propAcc = manejadorPropiedadesAccesibilidad.obtenerPropAcc();
				Usuario usuario = em.find(Usuario.class, usrsId);

				if (usuario != null) {

					for (PropiedadesAccesibilidad propiedadesAccesibilidad : propAcc) {
						UsuarioAccesibilidad usrs = new UsuarioAccesibilidad();
						usrs.setPropAcc(propiedadesAccesibilidad);
						usrs.setUsuarios(usuario);
						usrs.setValorPropiedad(propiedadesAccesibilidad.getValorDefecto());
						usrs.setEstado(1L);
						em.persist(usrs);
						em.flush();

						UsuarioAccesibilidadDTO userAccDTO = new UsuarioAccesibilidadDTO();
						userAccDTO.setEstado(usrs.getEstado());
						userAccDTO.setPropAcc(usrs.getPropAcc());
						userAccDTO.setUsuarioAccId(usrs.getUsuarioAccId());
						userAccDTO.setUsuarios(usrs.getUsuarios());
						userAccDTO.setValorPropiedad(usrs.getValorPropiedad());
						listUsrsAccDTO.add(userAccDTO);
					}
				}
			} else {
				for (UsuarioAccesibilidad userAcc : listUsrsAcc) {
					UsuarioAccesibilidadDTO userAccDTO = new UsuarioAccesibilidadDTO();
					userAccDTO.setEstado(userAcc.getEstado());
					userAccDTO.setPropAcc(userAcc.getPropAcc());
					userAccDTO.setUsuarioAccId(userAcc.getUsuarioAccId());
					userAccDTO.setUsuarios(userAcc.getUsuarios());
					userAccDTO.setValorPropiedad(userAcc.getValorPropiedad());
					listUsrsAccDTO.add(userAccDTO);
				}
			}
		} catch (NullPointerException e) {
			internalCode = "-1";
			exceptionMessage = Constantes.ERROR_USRID;
			accesibilidad.put("ExceptionMessage", exceptionMessage);
			accesibilidad.put("InternalCode", internalCode);
			return accesibilidad;
		} catch (Exception e) {
			internalCode = "-2";
			exceptionMessage = Constantes.ERROR_SIN_DATOS;
			accesibilidad.put("ExceptionMessage", exceptionMessage);
			accesibilidad.put("InternalCode", internalCode);
			return accesibilidad;
		}

		for (UsuarioAccesibilidadDTO propiedades : listUsrsAccDTO) {
			accesibilidad.put(propiedades.getPropAcc().getPropiedadAccId(), propiedades.getValorPropiedad());
		}

		return accesibilidad;

	}

	/**
	 * Método encargado de actualizar las propiedades de accesibilidad configuradas
	 * por el usuario
	 * 
	 * @param valor
	 * @param propId
	 * @param userId
	 * @throws Exception
	 */
	public void almacenarParamAcces(String valor, String propId, String userId){
		try {

			StringBuilder queryNative = new StringBuilder();

			queryNative.append(" update UsuarioAccesibilidad set valorPropiedad = :valor ");
			queryNative.append(" where usuarios.usuarioId = :id  ");
			queryNative.append(" and propAcc.propiedadAccId = :propId ");
			queryNative.append(" and estado = 1 ");

			Query query = em.createQuery(queryNative.toString());
			query.setParameter("valor", valor);
			query.setParameter("id", userId);
			query.setParameter("propId", propId);
			query.executeUpdate();

		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	protected boolean entidadContieneAtributo(String nombreAtributo) {
		return false;
	}

	@Override
	protected Logger getLogger() {
		return null;
	}

	@Override
	protected UsuarioAccesibilidadDTO instanciarDAO() {
		return null;
	}
}
