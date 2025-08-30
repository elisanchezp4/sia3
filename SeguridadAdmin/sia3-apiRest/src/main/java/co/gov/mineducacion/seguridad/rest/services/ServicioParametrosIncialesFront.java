package co.gov.mineducacion.seguridad.rest.services;



import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.gov.mineducacion.seguridad.modelo.dtos.PropiedadDTO;
import co.gov.mineducacion.seguridad.rest.config.DatosIniciaslesFront;




/**
 * Clase que controla los servicios REST de los datos iniciales del front
 * @author Edixon Giovanny Toca
 *
 */
@Stateless
@Path("/servicios/configuracion/inicial")
public class ServicioParametrosIncialesFront {
    
    
    
    
    
    /**
     * 
     * Metodo encargado de listar los parametros por id
     * @author Edixon Giovanny Toca <etoca@heinsohn.com.co>
     * 
     * @param id
     * @return
     * @throws SIUCEException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response getParametrosIniciales() { 
        
        
        
        List<PropiedadDTO> lstResultado = new ArrayList<PropiedadDTO>();
        
        for (Enumeration e = DatosIniciaslesFront.propiedadesFront.keys(); e.hasMoreElements() ; ) {
            
            PropiedadDTO propiedad = new PropiedadDTO();
            Object obj = e.nextElement();
            
            propiedad.setNombre(obj.toString());
            propiedad.setValor(DatosIniciaslesFront.propiedadesFront.getProperty(obj.toString()));
            
            lstResultado.add(propiedad);
            
        }
        return Response.ok(lstResultado)
                .build();
        
    }
    
    
}
