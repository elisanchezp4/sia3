
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
 *         &lt;element name="informacionToken" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionToken" minOccurs="0"/>
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
    "informacionToken"
})
@XmlRootElement(name = "actualizarFechaVencimientoTokenRs")
public class ActualizarFechaVencimientoTokenRs {

    protected InformacionToken informacionToken;

    /**
     * Gets the value of the informacionToken property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionToken }
     *     
     */
    public InformacionToken getInformacionToken() {
        return informacionToken;
    }

    /**
     * Sets the value of the informacionToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionToken }
     *     
     */
    public void setInformacionToken(InformacionToken value) {
        this.informacionToken = value;
    }

}
