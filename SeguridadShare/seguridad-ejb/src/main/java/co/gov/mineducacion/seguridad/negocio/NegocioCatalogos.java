package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.dtos.CatalogosDTO;

import java.util.List;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;

import co.gov.mineducacion.seguridad.modelo.entidades.Catalogos;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorCatalogos;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;


import java.math.BigDecimal;

// protected region Incluya importaciones adicionales en esta seccion on begin


// protected region Incluya importaciones adicionales en esta seccion end


/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad Catalogos
 * @author jsoto
 */
@Stateless
public class NegocioCatalogos extends NegocioAbstracto<Catalogos,CatalogosDTO> {

    @EJB
    private ManejadorCatalogos manejadorCatalogos;

    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(NegocioCatalogos.class.getName());
    
    // protected region Declare atributos adicionales en esta seccion on begin
    
    // protected region Declare atributos adicionales en esta seccion end
    

    /**
     * Realiza un consulta en la entidad Catalogos aplicando los filtros, el ordenamiento,
     * y el rango (from y to) que se pasan como parámetro. Los parámetros filterBy y orderBy
     * pueden ser nulos. El parámetro from y to están relacionados. Si from es diferente de nulo
     * to puedo ser nulo, pero no al revés. Ambos pueden ser nulos, en cuyo caso no se aplica una
     * restricción de rango a la consulta.
     * 
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere filtrar, seguido por un operador
     * de comparación que puede tomar los valores {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y por último el valor
     * por el que se quiere filtrar. Los filtros se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal aplicacionesId>1&aplicacionesName:LIKE:juan}
     * @param orderBy Cadena de caracteres con los parámetros de ordenamiento. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere ordenar, seguido por el símbolo '$' y 
     * posteriormente por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son opcionales ya que si
     * no se especifica por defecto se asume que el ordenamiento es de forma Ascendente. Si se coloca más de un
     * parámetro debe ir separado por coma : ','.
     * Ej. Una secuencia de parámetros de ordenamiento puede ser: aplicacionesId$ASC, aplicacionesName$DESC
     * @param from Número de registro inicial que se quiere retornar de la consulta realizada. Entero mayor o igual a 0
     * @param to Número de registro final que se quiere retornar de la consulta realizada. Entero mayor o igual al parámetro from
     * @return Una lista de DAOs de los Catalogos que se consultaron con los parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url tenía un error de sintáxis por lo que no pudo ser procesado correctamente
     */
    public List<CatalogosDTO> consultar(String filterBy, 
                String orderBy, Integer from,
                Integer to) 
            throws InvalidParameterException {
        // protected region Modifique el metodo consultar on begin
        logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);
        
        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);           
        RangoConsulta rango = validarParametrosBloque(from, to);       
                
        return convertirListaEntidadesADao(manejadorCatalogos.consultar(filtros, ordenamiento, rango));
        // protected region Modifique el metodo consultar end
    }

    /**
     * Crea el catalogo que se pasa como parámetro en la base de datos.
     * 
     * @param catalogosDTO El DAO de la entidad Catalogos a crear. Este se envía en el cuerpo de la
     * solicitud POST como un objeto JSON.
     * @return La insntancia de Catalogos recién creado
     */
    public CatalogosDTO crear(CatalogosDTO catalogosDTO) {
    	// protected region Modifique el metodo crear on begin
    	
        logService(this.getClass().getName(), "crear", catalogosDTO);

        Catalogos catalogos = new Catalogos();
        copiarPropiedades(catalogos, catalogosDTO);
        
        manejadorCatalogos.crear(catalogos);

        return catalogosDTO;
        // protected region Modifique el metodo crear end
    }

    /**
     * Actualiza en la base de datos el aplicaciones que se pasa como parámetro.
     * 
     * @param catalogosDTO El DAO de la entidad Catalogos a actualizar. Este se envía en el cuerpo de la
     * solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad Catalogos que ha sido actualizado
     */
    public CatalogosDTO actualizar(CatalogosDTO catalogosDTO){
        // protected region Modifique el metodo actualizar on begin
    
        logService(this.getClass().getName(), "actualizar", catalogosDTO);

        Catalogos catalogos = manejadorCatalogos.buscar(catalogosDTO.getCatalogoId());                
        copiarPropiedades(catalogos, catalogosDTO);
        
        manejadorCatalogos.actualizar(catalogos);
				
        return catalogosDTO;
        // protected region Modifique el metodo actualizar end
    }

    /**
     * Elimina el aplicaciones con el identificador que se pasa como parámetro.
     * 
     * @param aplicacionId Valor del atributo del identificador de la instancia de la entidad  aplicaciones a eliminar
     * @return El identificador del aplicaciones que ha sido eliminado
     */
    public String eliminar(@QueryParam("aplicacionId") BigDecimal aplicacionId) {
        // protected region Modifique el metodo eliminar on begin

        logService(this.getClass().getName(), "eliminar", aplicacionId);
        manejadorCatalogos.eliminarPorId(aplicacionId);
        
		
		StringBuilder valores = new StringBuilder();
		valores.append(String.valueOf(aplicacionId));
        return valores.toString();
        // protected region Modifique el metodo eliminar end
    }

    /**
     * Cuenta la cantidad de registros que devuelve la consulta a la tabla de 
     * aplicando los filtros o rangos que se pasen como parámetro. Estos 
     * pueden ser nulos, en cuyo caso a la consulta no se le realiza ningún tipo de
     * filtrado.
     * 
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere filtrar, seguido por un operador
     * de comparación que puede tomar los valores {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y por último el valor
     * por el que se quiere filtrar. Los filtros se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal aplicacionesId>1&aplicacionesName:LIKE:juan}
     * @param from Número de registro inicial que se quiere retornar de la consulta realizada. Entero mayor o igual a 0
     * @param to Número de registro final que se quiere retornar de la consulta realizada. Entero mayor o igual al parámetro from
     * @return El número de registros contados a partir de los parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url tenía un error de sintáxis por lo que no pudo ser procesado correctamente
     */
    public String contar(String filterBy,
             Integer from,
             Integer to) throws InvalidParameterException {
        // protected region Modifique el metodo contar on begin
        
        logService(this.getClass().getName(), "contar", filterBy);        
        
        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        RangoConsulta rango = validarParametrosBloque(from, to);       
        
        return String.valueOf(manejadorCatalogos.consultarTotalRegistros(filtros, 
                    rango));
		// protected region Modifique el metodo contar end
    }    
    
    /**
     * 
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere filtrar, seguido por un operador
     * de comparación que puede tomar los valores {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y por último el valor
     * por el que se quiere filtrar. Los filtros se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal aplicacionesId>1&aplicacionesName:LIKE:juan}
     * @param orderBy Cadena de caracteres con los parámetros de ordenamiento. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere ordenar, seguido por el símbolo '$' y 
     * posteriormente por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son opcionales ya que si
     * no se especifica por defecto se asume que el ordenamiento es de forma Ascendente. Si se coloca más de un
     * parámetro debe ir separado por coma : ','.
     * Ej. Una secuencia de parámetros de ordenamiento puede ser: aplicacionesId$ASC, aplicacionesName$DESC
     * @param atributo Nombre del atributo de la entidad Catalogos del cual se quieren obtener los diferentes valores.
     * @return Una lista con los diferentes valores que se encuentran en la columna de la tabla asociada al atributo.
     * @throws InvalidParameterException Si el atributo no existe en la entidad o si los filtros y el ordenamiento 
     * contienen atributos de la entidad que no existen.
     */
    public List<String> consultarLista(String filterBy,
             String orderBy, String atributo) throws InvalidParameterException{
        // protected region Modifique el metodo consultarLista on begin

        logService(this.getClass().getName(), "contar", filterBy, orderBy, atributo);  
        
        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);    
        InformacionAgrupamiento infoAgrupamiento = decodificarInformacionAgrupamiento(atributo);
                
        return UtilOperaciones.convertirListaObjetosAString(manejadorCatalogos.consultarLista(filtros, ordenamiento, infoAgrupamiento));
        // protected region Modifique el metodo consultarLista end
    }

    /**
     * {@inheritDoc}
     * @param nombreAtributo {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean entidadContieneAtributo(String nombreAtributo) {
        return Catalogos.contieneAtributo(nombreAtributo);
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
    protected CatalogosDTO instanciarDAO() {
        return new CatalogosDTO();
    }
    
    public CatalogosDTO buscarCatalogo(String appId) {
    	BigDecimal big = new BigDecimal(appId);
    	CatalogosDTO app = instanciarDAO();
    	copiarPropiedades(app, manejadorCatalogos.buscar(big));
    	return app;
    	
    }
    
    // protected region Use esta region para su implementacion de otros metodos on begin
    
    
    // protected region Use esta region para su implementacion de otros metodos end
    
    //Inicio metodos HBT
    /**
     * Metodo que lista los catalogos segun un tipo de catalogo 
     * @param tipoCatalogo
     * @return
     * @throws SIA3Exception
     */
    public List<CatalogosDTO> listarCatalogoPorTipo(String tipoCatalogo) throws SIA3Exception {
		try {
			logger.info("Inicio metodo negocio listarCatalogoPorTipo con tipoCatalogo => "+tipoCatalogo);
			List<CatalogosDTO> catalogoDTOList = BuilderDTO
					.toCatalogoDTOList(manejadorCatalogos.listarCatalogoPorTipo(tipoCatalogo));
			if (catalogoDTOList.isEmpty()) {
				logger.error("Error en metodo listarCatalogoPorTipo: No hay catalogos por tipoCatalogo:"+tipoCatalogo);
				
				throw new SIA3Exception(MessagesConstants.APP100037);
			}
			logger.info("fin metodo listarCatalogoPorTipo");
			return catalogoDTOList;
		} catch (SIA3Exception e) {
			logger.error("Error en metodo listarCatalogoPorTipo:" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP100038);
		}

	}
    
    /**
     * Metodo que lista los catalogos segun un tipo de catalogo
     * que pertenecen a la aplciacion de seguridad
     * @param tipoCatalogo
     * @return
     * @throws SIA3Exception
     */
    public List<CatalogosDTO> listarCatalogoPorTipoSeguridad(String tipoCatalogo) throws SIA3Exception {
		try {
			logger.info("Inicio metodo negocio listarCatalogoPorTipoSeguridad con tipoCatalogo => "+tipoCatalogo);
			List<CatalogosDTO> catalogoDTOList = BuilderDTO
					.toCatalogoDTOList(manejadorCatalogos.listarCatalogoPorTipoSeguridad(tipoCatalogo));
			if (catalogoDTOList.isEmpty()) {
				logger.error("Error en metodo listarCatalogoPorTipoSeguridad: No hay catalogos por tipoCatalogo:"+tipoCatalogo);
				
				throw new SIA3Exception(MessagesConstants.APP100037);
			}
			logger.info("fin metodo listarCatalogoPorTipoSeguridad");
			return catalogoDTOList;
		} catch (SIA3Exception e) {
			logger.error("Error en metodo listarCatalogoPorTipoSeguridad:" + e.getCause());
			throw new SIA3Exception(MessagesConstants.APP100038);
		}

	}
    //Fin metodos HBT

}
