
package co.gov.mineducacion.sdk.web.soap.autenticar;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOpcion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOpcion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Opcion" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}Opcion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOpcion", propOrder = {
    "opcion"
})
public class ArrayOfOpcion {

    @XmlElement(name = "Opcion", nillable = true)
    protected List<Opcion> opcion;

    /**
     * Gets the value of the opcion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the opcion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOpcion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Opcion }
     * 
     * 
     */
    public List<Opcion> getOpcion() {
        if (opcion == null) {
            opcion = new ArrayList<>();
        }
        return this.opcion;
    }

}
