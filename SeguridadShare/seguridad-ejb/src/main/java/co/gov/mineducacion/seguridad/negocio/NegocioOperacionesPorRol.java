package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesPorRolDTO;

import java.util.List;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import co.gov.mineducacion.seguridad.modelo.entidades.Operaciones;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperaciones;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorOperacionesPorRol;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;

import javax.ejb.EJB;


import java.math.BigDecimal;

// protected region Incluya importaciones adicionales en esta seccion on begin


// protected region Incluya importaciones adicionales en esta seccion end


/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad Operaciones
 * @author jsoto
 */
@Stateless
public class NegocioOperacionesPorRol extends NegocioAbstracto<Operaciones,OperacionesPorRolDTO> {

    @EJB
    private ManejadorOperaciones manejadorOperaciones;

    @EJB
    private ManejadorOperacionesPorRol manejadorOperacionesPorRol;
    
    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(NegocioOperacionesPorRol.class.getName());
    
    // protected region Declare atributos adicionales en esta seccion on begin
    
    // protected region Declare atributos adicionales en esta seccion end
    /**
     * {@inheritDoc}
     * @param nombreAtributo {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean entidadContieneAtributo(String nombreAtributo) {
        return Operaciones.contieneAtributo(nombreAtributo);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc} 
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * {@inheritDoc}
     * @return  {@inheritDoc}
     */
    @Override
    protected OperacionesPorRolDTO instanciarDAO() {
        return new OperacionesPorRolDTO();
    }       
    
    // protected region Use esta region para su implementacion de otros metodos on begin
    
    
    // protected region Use esta region para su implementacion de otros metodos end
        
  //Inicio metodos HBT
  	
  	/**
  	 * Metodo que obtiene las operaciones para un rol especifico
  	 * 
  	 * @param rolId
  	 * @param aplicacionId
  	 * @return
  	 * @throws SIA3Exception
  	 */
  	public List<OperacionesPorRolDTO> getOperacionesPorRol(BigDecimal rolId, BigDecimal aplicacionId) throws SIA3Exception {
  		try {
  			logger.info("Inicio metodo getOperacionesPorRol con rolId:"+rolId);
  			List<OperacionesPorRolDTO> rolDTOList = BuilderDTO
  					.toOperacionPorRolDTOList(manejadorOperacionesPorRol.getOperacionesPorRol(rolId,aplicacionId), rolId);
  			if (rolDTOList.isEmpty()) {
  				logger.error("Error en metodo consultar: No hay Operaciones por rol.");
  				throw new SIA3Exception(MessagesConstants.APP100041);
  			}
  			logger.info("fin metodo getOperacionesPorRol");
  			return rolDTOList;
  		} catch (PersistenceException e) {
  			logger.error("Error en metodo getOperacionesPorRol:" + e.getCause());
  			throw new SIA3Exception(MessagesConstants.APP000003);
  		}
  	}
  	//Fin metodos HBT

}
