package co.gov.mineducacion.seguridad.web.servicio;

import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosRolDTO;
import java.util.List;


import javax.ejb.Stateless;

import co.gov.mineducacion.seguridad.modelo.excepciones.InvalidParameterException;
import co.gov.mineducacion.seguridad.negocio.NegocioUsuariosRol;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.math.BigDecimal;

/**
 * Servicios REST para operaciones CRUD y de negocio sobre la entidad UsuariosRol
 * @author jsoto
 */
@Stateless
@Path("servicios/usuariosrol")
public class ServicioUsuariosRol {

    @EJB
    private NegocioUsuariosRol negocioUsuariosRol;

    /**
     * Variable estatica para imprimir logs...
     */

    /**
     * Realiza un consulta en la entidad UsuariosRol aplicando los filtros, el ordenamiento,
     * y el rango (from y to) que se pasan como parámetro. Los parámetros filterBy y orderBy
     * pueden ser nulos. El parámetro from y to están relacionados. Si from es diferente de nulo
     * to puedo ser nulo, pero no al revés. Ambos pueden ser nulos, en cuyo caso no se aplica una
     * restricción de rango a la consulta.
     * 
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere filtrar, seguido por un operador
     * de comparación que puede tomar los valores {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y por último el valor
     * por el que se quiere filtrar. Los filtros se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal usuariosRolId>1&usuariosRolName:LIKE:juan}
     * @param orderBy Cadena de caracteres con los parámetros de ordenamiento. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere ordenar, seguido por el símbolo '$' y 
     * posteriormente por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son opcionales ya que si
     * no se especifica por defecto se asume que el ordenamiento es de forma Ascendente. Si se coloca más de un
     * parámetro debe ir separado por coma : ','.
     * Ej. Una secuencia de parámetros de ordenamiento puede ser: usuariosRolId$ASC, usuariosRolName$DESC
     * @param from Número de registro inicial que se quiere retornar de la consulta realizada. Entero mayor o igual a 0
     * @param to Número de registro final que se quiere retornar de la consulta realizada. Entero mayor o igual al parámetro from
     * @return Una lista de DAOs de los UsuariosRol que se consultaron con los parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url tenía un error de sintáxis por lo que no pudo ser procesado correctamente
     */
    @GET
    @Produces({APPLICATION_JSON})
    public List<UsuariosRolDTO> consultar(@QueryParam("filterBy") String filterBy, 
                @QueryParam("orderBy") String orderBy, @QueryParam("from") Integer from,
                @QueryParam("to") Integer to) 
            throws InvalidParameterException {
        // protected region Use esta region para su implementacion on begin
        
        return negocioUsuariosRol.consultar(filterBy, orderBy, from, to);
        // protected region Use esta region para su implementacion end 
    }

    /**
     * Crea el usuariosRol que se pasa como parámetro en la base de datos.
     * 
     * @param usuariosRolDTO El DAO de la entidad UsuariosRol a crear. Este se envía en el cuerpo de la
     * solicitud POST como un objeto JSON.
     * @return El identificador de la insntancia de UsuariosRol recién creado
     */
    @POST
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    public UsuariosRolDTO crear(UsuariosRolDTO usuariosRolDTO) {
        // protected region Use esta region para su implementacion on begin

        return negocioUsuariosRol.crear(usuariosRolDTO);
        // protected region Use esta region para su implementacion end 
        
    }

    /**
     * Actualiza en la base de datos el usuariosRol que se pasa como parámetro.
     * 
     * @param usuariosRolDTO El DAO de la entidad UsuariosRol a actualizar. Este se envía en el cuerpo de la
     * solicitud PUT como un objeto JSON.
     * @return El identificador de la instancia de la entidad UsuariosRol que ha sido actualizado
     */
    @PUT
    @Consumes({APPLICATION_JSON})
    @Produces({APPLICATION_JSON})
    public UsuariosRolDTO actualizar(UsuariosRolDTO usuariosRolDTO){
        // protected region Use esta region para su implementacion on begin

        return negocioUsuariosRol.actualizar(usuariosRolDTO);
        // protected region Use esta region para su implementacion end
    }

    /**
     * Elimina el usuariosRol con el identificador que se pasa como parámetro.
     * 
     * @param rolId Valor del atributo del identificador de la instancia de la entidad  usuariosRol a eliminar
     * @param usuarioId Valor del atributo del identificador de la instancia de la entidad  usuariosRol a eliminar
     * @return El identificador del usuariosRol que ha sido eliminado
     */
    @DELETE
    public String eliminar(@QueryParam("rolId") BigDecimal rolId, @QueryParam("usuarioId") String usuarioId) {
        // protected region Use esta region para su implementacion on begin

        return negocioUsuariosRol.eliminar(rolId, usuarioId);
        // protected region Use esta region para su implementacion end
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
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal usuariosRolId>1&usuariosRolName:LIKE:juan}
     * @param from Número de registro inicial que se quiere retornar de la consulta realizada. Entero mayor o igual a 0
     * @param to Número de registro final que se quiere retornar de la consulta realizada. Entero mayor o igual al parámetro from
     * @return El número de registros contados a partir de los parámetros enviados por el cliente
     * @throws InvalidParameterException Excepción lanzada cuando algunos de los parámetros de la url tenía un error de sintáxis por lo que no pudo ser procesado correctamente
     */
    @GET
    @Path("contar")
    @Produces(TEXT_PLAIN)
    public String contar(@QueryParam("filterBy") String filterBy,
             @QueryParam("from") Integer from,
                @QueryParam("to") Integer to) throws InvalidParameterException {        
        // protected region Use esta region para su implementacion on begin

        return negocioUsuariosRol.contar(filterBy, from, to);
        // protected region Use esta region para su implementacion end
    }    
    
    /**
     * 
     * @param filterBy Cadena de caracteres con los parámetros de filtrado. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere filtrar, seguido por un operador
     * de comparación que puede tomar los valores {@literal '=', '<', '<=', '>', '>=', ':NOTLIKE:', ':LIKE:'}, y por último el valor
     * por el que se quiere filtrar. Los filtros se concatenan por el símbolo {@literal '&' (AND) o '|' (OR)}.
     * Ej. Una secuencia de parámetros de filtrado puede ser {@literal usuariosRolId>1&usuariosRolName:LIKE:juan}
     * @param orderBy Cadena de caracteres con los parámetros de ordenamiento. Cada parámetro
     * está compuesto por el nombre del campo por el que se quiere ordenar, seguido por el símbolo '$' y 
     * posteriormente por los valores 'ASC' o 'DESC'. Estos dos ultimos valores son opcionales ya que si
     * no se especifica por defecto se asume que el ordenamiento es de forma Ascendente. Si se coloca más de un
     * parámetro debe ir separado por coma : ','.
     * Ej. Una secuencia de parámetros de ordenamiento puede ser: usuariosRolId$ASC, usuariosRolName$DESC
     * @param atributo Nombre del atributo de la entidad UsuariosRol del cual se quieren obtener los diferentes valores.
     * @return Una lista con los diferentes valores que se encuentran en la columna de la tabla asociada al atributo.
     * @throws InvalidParameterException Si el atributo no existe en la entidad o si los filtros y el ordenamiento 
     * contienen atributos de la entidad que no existen.
     */
    @GET
    @Path("lista")
    @Produces({APPLICATION_JSON})
    public List<String> consultarLista(@QueryParam("filterBy") String filterBy,
             @QueryParam("orderBy") String orderBy, @QueryParam("atributo") String atributo) throws InvalidParameterException{
        // protected region Use esta region para su implementacion on begin
        return negocioUsuariosRol.consultarLista(filterBy, orderBy, atributo);
        // protected region Use esta region para su implementacion end
    }     
    
    // protected region Use esta region para su implementacion de otros servicios on begin
    
    // protected region Use esta region para su implementacion de otros servicios end

}
