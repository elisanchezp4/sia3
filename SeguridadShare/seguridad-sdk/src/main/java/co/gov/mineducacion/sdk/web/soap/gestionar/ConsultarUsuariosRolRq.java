
package co.gov.mineducacion.sdk.web.soap.gestionar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="informacionRol" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionRol" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "informacionRol"
})
@XmlRootElement(name = "consultarUsuariosRolRq")
public class ConsultarUsuariosRolRq {

    protected InformacionRol informacionRol;

    /**
     * Gets the value of the informacionRol property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionRol }
     *     
     */
    public InformacionRol getInformacionRol() {
        return informacionRol;
    }

    /**
     * Sets the value of the informacionRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionRol }
     *     
     */
    public void setInformacionRol(InformacionRol value) {
        this.informacionRol = value;
    }

}
