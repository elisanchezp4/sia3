package co.gov.mineducacion.seguridad.modelo.manejadores;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.SqlConstants;
import co.gov.mineducacion.seguridad.modelo.entidades.BitacoraAuditoria;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre
 * la tabla correspondiente a la entidad BitacoraAuditoria.
 *
 * @author jsoto
 */
@Stateless
public class ManejadorBitacoraAuditoria extends ManejadorCrud<BitacoraAuditoria,BigDecimal>{

	// Variables HBT

	/**
	 * Imprimir logs
	 */
	private static final Logger LOG = Logger.getLogger(ManejadorBitacoraAuditoria.class.getName());

	/**
	 * inyección de entitymanager desde el servidor
	 */
	@PersistenceContext(unitName = Constantes.HBT_PERSISTENCE_UNIT)
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	// Fin variables HBT

	public ManejadorBitacoraAuditoria() {
		super(BitacoraAuditoria.class);
	}

	// protected region Use esta region para su implementacion del manejador on begin

	// protected region Use esta region para su implementacion del manejador end

	//Inicio metodos HBT
	/**
	 * Metodo que busca todas los registros de auditoria que coinciden con los criterios de busqueda
	 * @param aplicacionId
	 * @param tipoEvento
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws SIA3Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BitacoraAuditoria> filtrarAuditoria(BigDecimal aplicacionId, BigDecimal tipoEvento, String fechaInicio, String fechaFin, String usuarioId, RangoConsulta rango, String campoActivo){
		LOG.info("Inicio metodo manejador filtrarAuditoria con parametros aplicacionId=>" + aplicacionId+", tipoEvento=>"+tipoEvento+", fechaInicio=>"+fechaInicio+", fechaFin=>"+fechaFin +", usuarioId=>"+usuarioId + ", rango=>"+rango);
		StringBuilder consulta = new StringBuilder(SqlConstants.CONSULTAR_FILTROS_AUDITORIA);
		if (aplicacionId != null) {
			consulta.append(SqlConstants.Y_AUDITORIA_APLICACION_ID);
		}
		if (tipoEvento != null) {
			consulta.append(SqlConstants.Y_AUDITORIA_TIPO_EVENTO);
		}
		if (fechaInicio != null && fechaFin != null) {
			consulta.append(SqlConstants.Y_AUDITORIA_FECHAS);
		}
		if (usuarioId != null) {
			consulta.append(SqlConstants.Y_AUDITORIA_USUARIO);
		}
		if (campoActivo !=null){
			consulta.append(SqlConstants.Y_AUDITORIA_CAMPO_ACTIVO);
		}

		//ordenar registros
		consulta.append(SqlConstants.ORDEN_AUDITORIA);

		Query query = em.createNativeQuery(consulta.toString(), BitacoraAuditoria.class);

		if (aplicacionId != null) {
			query.setParameter(1, aplicacionId);
		}

		if (tipoEvento != null) {
			query.setParameter(2, tipoEvento);
		}

		if (fechaInicio != null && fechaFin != null) {
			LOG.info("Filtra resultados por fechaInicio: " + fechaInicio + " y fechaFin:" + fechaFin);
			query.setParameter(3, fechaInicio);
			query.setParameter(4, fechaFin);
		}
		if (usuarioId != null) {
			query.setParameter(5, usuarioId.trim());
		}

		//configurar rango de registros a devolver
		if (rango != null) {
			query.setFirstResult(rango.getFrom());
			query.setMaxResults(rango.getTo());
		}
		if (campoActivo !=null){
			query.setParameter(6, campoActivo);
		}
		LOG.info("Valor query:" + query);
		LOG.info("Fin metodo filtrarAuditoria");
		return query.getResultList();
	}

	/**
	 * Metodo que obtiene la cantidad de registros de auditoria
	 *
	 * @param aplicacionId
	 * @param tipoEvento
	 * @param fechaInicio
	 * @param fechaFin
	 * @param usuarioId
	 * @return
	 * @throws Exception
	 */
	public Long contarRegistrosAuditoria(BigDecimal aplicacionId, BigDecimal tipoEvento, String fechaInicio, String fechaFin, String usuarioId,String campoActivo) throws SIA3Exception {
		try {
			LOG.info("INICIO Manejador contarRegistrosAuditoria, aplicacionId: "+aplicacionId+", tipoEvento: "+tipoEvento+" , fechaInicio: "+fechaInicio+", fechaFin: "+fechaFin+", usuarioId: "+usuarioId+", campoActivo: "+campoActivo);
			long cantidadRegistros;
			StringBuilder consulta = new StringBuilder(SqlConstants.CONTAR_FILTROS_AUDITORIA);

			if (aplicacionId != null) {
				consulta.append(SqlConstants.Y_AUDITORIA_APLICACION_ID);
			}
			if (tipoEvento != null) {
				consulta.append(SqlConstants.Y_AUDITORIA_TIPO_EVENTO);
			}
			if (fechaInicio != null && fechaFin != null) {
				consulta.append(SqlConstants.Y_AUDITORIA_FECHAS);
			}
			if (usuarioId != null) {
				consulta.append(SqlConstants.Y_AUDITORIA_USUARIO);
			}
			if (campoActivo !=null){
				consulta.append(SqlConstants.Y_AUDITORIA_CAMPO_ACTIVO);
			}
			Query query = em.createNativeQuery(consulta.toString());

			if (aplicacionId != null) {
				query.setParameter(1, aplicacionId);
			}

			if (tipoEvento != null) {
				query.setParameter(2, tipoEvento);
			}

			if (fechaInicio != null && fechaFin != null) {
				LOG.info("Filtra resultados por fechaInicio: " + fechaInicio + " y fechaFin:" + fechaFin);
				query.setParameter(3, fechaInicio);
				query.setParameter(4, fechaFin);
			}
			if (usuarioId != null) {
				query.setParameter(5, usuarioId.trim());
			}
			if (campoActivo !=null){
				query.setParameter(6, campoActivo);
			}
			LOG.info("FIN Manejador contarRegistrosAuditoria: "+query);
			cantidadRegistros = ((BigDecimal) query.getSingleResult()).longValue();
			return cantidadRegistros;

		} catch (Exception e) {
			LOG.error("ERROR en consulta contar auditoria: " + e.getMessage());
			throw new SIA3Exception(e.getMessage());
		}
	}

	//Fin metodos HBT
}

