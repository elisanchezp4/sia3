/**
 * PropiedadDTO.java
 */
package co.gov.mineducacion.seguridad.modelo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * <b>Descripción:<b> Clase que determina
 * <b>Caso de Uso:<b>
 * @author etoca Edixon Giovanny Toca
 * @version
 */


@XmlRootElement
public class PropiedadDTO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 3464786164452916920L;
    
    
    /**
     * nombre de la propiedad
     */
    private String nombre;
    
    /**
     * Valor de la propiedad
     */
    private String valor;
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    
}

