
package co.gov.mineducacion.sdk.web.soap.autenticar;

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
 *         &lt;element name="informacionSesion" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionSesion" minOccurs="0"/>
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
    "informacionSesion"
})
@XmlRootElement(name = "finalizarSesionRq")
public class FinalizarSesionRq {

    protected InformacionSesion informacionSesion;

    /**
     * Gets the value of the informacionSesion property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionSesion }
     *     
     */
    public InformacionSesion getInformacionSesion() {
        return informacionSesion;
    }

    /**
     * Sets the value of the informacionSesion property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionSesion }
     *     
     */
    public void setInformacionSesion(InformacionSesion value) {
        this.informacionSesion = value;
    }

}
