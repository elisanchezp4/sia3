package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.manejadores.utils.RangoConsulta;
import co.gov.mineducacion.seguridad.modelo.dtos.TokensActivosDTO;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;

import co.gov.mineducacion.seguridad.modelo.entidades.TokensActivos;
import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionFiltro;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionOrdenamiento;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorTokensActivos;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.InformacionAgrupamiento;
import co.gov.mineducacion.seguridad.modelo.utils.UtilOperaciones;

import javax.ejb.EJB;
import javax.ws.rs.QueryParam;


// protected region Incluya importaciones adicionales en esta seccion on begin


// protected region Incluya importaciones adicionales en esta seccion end


/**
 * Servicios para operaciones CRUD y de negocio sobre la entidad TokensActivos
 * @author jsoto
 */
@Stateless
public class NegocioTokensActivos extends NegocioAbstracto<TokensActivos,TokensActivosDTO> {

    @EJB
    private ManejadorTokensActivos manejadorTokensActivos;

    /**
     * Variable estatica para imprimir logs...
     */
    private static final Logger logger = Logger.getLogger(NegocioTokensActivos.class.getName());
    
    // protected region Declare atributos adicionales en esta seccion on begin
    
    // protected region Declare atributos adicionales en esta seccion end
    

    /**
     * Realiza un consulta en la entidad TokensActivos aplicando los filtros, el ordenamiento,
     * y el rango (from y to) que se pasan como parámetro. Los parámetros filterBy y orderBy
     * pueden ser nulos. El parámetro from y to están relacionados. Si from es diferente de nulo
     * to puedo ser nulo, pero no al revés. Ambos pueden ser nulos, en cuyo caso no se aplica una
     * restricción de rango a la consulta.
     * 
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere filtrar, seguido por un operador
     * de comparación que puede tomar los valores {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y por último el valor
     * por el que se quiere filtrar. Los filtros se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal tokensActivosId>1&tokensActivosName:LIKE:juan}
     * @param orderBy Cadena de caracteres con los parámetros de ordenamiento. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere ordenar, seguido por el símbolo '$' y 
     * posteriormente por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son opcionales ya que si
     * no se especifica por defecto se asume que el ordenamiento es de forma Ascendente. Si se coloca más de un
     * parámetro debe ir separado por coma : ','.
     * Ej. Una secuencia de parámetros de ordenamiento puede ser: tokensActivosId$ASC, tokensActivosName$DESC
     * @param from Número de registro inicial que se quiere retornar de la consulta realizada. Entero mayor o igual a 0
     * @param to Número de registro final que se quiere retornar de la consulta realizada. Entero mayor o igual al parámetro from
     * @return Una lista de DAOs de los TokensActivos que se consultaron con los parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url tenía un error de sintáxis por lo que no pudo ser procesado correctamente
     */
    public List<TokensActivosDTO> consultar(String filterBy, 
                String orderBy, Integer from,
                Integer to) 
            throws InvalidParameterException {
        // protected region Modifique el metodo consultar on begin
        logService(this.getClass().getName(), "consultar", filterBy, orderBy, from, to);
        
        List<InformacionFiltro> filtros = invocarDecodificacionFiltro(filterBy);
        List<InformacionOrdenamiento> ordenamiento = invocarDecodificacionOrdenamiento(orderBy);           
        RangoConsulta rango = validarParametrosBloque(from, to);       
                
        return convertirListaEntidadesADao(manejadorTokensActivos.consultar(filtros, ordenamiento, rango));
        // protected region Modifique el metodo consultar end
    }

    /**
     * Crea el tokensActivos que se pasa como parámetro en la base de datos.
     * 
     * @param tokensActivosDTO El DAO de la entidad TokensActivos a crear. Este se envía en el cuerpo de la
     * solicitud POST como un objeto JSON.
     * @return La insntancia de TokensActivos recién creado
     */
    public TokensActivosDTO crear(TokensActivosDTO tokensActivosDTO) {
    	// protected region Modifique el metodo crear on begin
        logger.info("crear---> " + tokensActivosDTO);

        TokensActivos tokensActivos = new TokensActivos();
        copiarPropiedades(tokensActivos, tokensActivosDTO);
        
        manejadorTokensActivos.crear(tokensActivos);

        return tokensActivosDTO;
        // protected region Modifique el metodo crear end
    }

    /**
     * Actualiza en la base de datos el tokensActivos que se pasa como parámetro.
     * 
     * @param tokensActivosDTO El DAO de la entidad TokensActivos a actualizar. Este se envía en el cuerpo de la
     * solicitud PUT como un objeto JSON.
     * @return La instancia de la entidad TokensActivos que ha sido actualizado
     */
    public TokensActivosDTO actualizar(TokensActivosDTO tokensActivosDTO){
        // protected region Modifique el metodo actualizar on begin
    
        logService(this.getClass().getName(), "actualizar", tokensActivosDTO);

        TokensActivos tokensActivos = manejadorTokensActivos.buscar(tokensActivosDTO.getTokenId());                
        copiarPropiedades(tokensActivos, tokensActivosDTO);
        
        manejadorTokensActivos.actualizar(tokensActivos);
				
        return tokensActivosDTO;
        // protected region Modifique el metodo actualizar end
    }

    /**
     * Elimina el tokensActivos con el identificador que se pasa como parámetro.
     * 
     * @param tokenId Valor del atributo del identificador de la instancia de la entidad  tokensActivos a eliminar
     * @return El identificador del tokensActivos que ha sido eliminado
     */
    public String eliminar(@QueryParam("tokenId") String tokenId) {
        // protected region Modifique el metodo eliminar on begin

        logger.info("tokenId---> " + tokenId);
        manejadorTokensActivos.eliminarPorId(tokenId);
        
		
		StringBuilder valores = new StringBuilder();
		valores.append(String.valueOf(tokenId));

        logger.info("valores String.valueOf(tokenId)---> " + valores.toString());
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
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal tokensActivosId>1&tokensActivosName:LIKE:juan}
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
        
        return String.valueOf(manejadorTokensActivos.consultarTotalRegistros(filtros, 
                    rango));
		// protected region Modifique el metodo contar end
    }    
    
    /**
     * 
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere filtrar, seguido por un operador
     * de comparación que puede tomar los valores {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y por último el valor
     * por el que se quiere filtrar. Los filtros se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal tokensActivosId>1&tokensActivosName:LIKE:juan}
     * @param orderBy Cadena de caracteres con los parámetros de ordenamiento. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere ordenar, seguido por el símbolo '$' y 
     * posteriormente por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son opcionales ya que si
     * no se especifica por defecto se asume que el ordenamiento es de forma Ascendente. Si se coloca más de un
     * parámetro debe ir separado por coma : ','.
     * Ej. Una secuencia de parámetros de ordenamiento puede ser: tokensActivosId$ASC, tokensActivosName$DESC
     * @param atributo Nombre del atributo de la entidad TokensActivos del cual se quieren obtener los diferentes valores.
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
                
        return UtilOperaciones.convertirListaObjetosAString(manejadorTokensActivos.consultarLista(filtros, ordenamiento, infoAgrupamiento));
        // protected region Modifique el metodo consultarLista end
    }

    /**
     * {@inheritDoc}
     * @param nombreAtributo {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean entidadContieneAtributo(String nombreAtributo) {
        return TokensActivos.contieneAtributo(nombreAtributo);
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
    protected TokensActivosDTO instanciarDAO() {
        return new TokensActivosDTO();
    }
    
    /**
     * Metodo que consulta el token generado
     * @param token
     * @return
     * @author hfabra
     * @since 9/02/2017
     */
    public TokensActivosDTO buscarToken(String token) {
    	TokensActivosDTO activeToken = instanciarDAO();
    	if (manejadorTokensActivos.buscar(token) != null) {
    		copiarPropiedades(activeToken, manejadorTokensActivos.buscar(token));
    	} else {
    		activeToken = null;
    	} 	
    	return activeToken;
    	
    }
    
    /**
     * Elimina los tokens cuya fecha de vencimiento sea
     * menor o igual a la fecha recibida por parametro
     * @param fecha
     */
    public void eliminarTokensVencidos(Timestamp fecha){
    	
    	this.manejadorTokensActivos.eliminarTokensVencidos(fecha);
    	
    }
    
    // protected region Use esta region para su implementacion de otros metodos on begin
    
    
    // protected region Use esta region para su implementacion de otros metodos end

}
