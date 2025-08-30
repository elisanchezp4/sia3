
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
 *         &lt;element name="tokenAcceso" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionToken" minOccurs="0"/>
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
    "tokenAcceso"
})
@XmlRootElement(name = "obtenerTokenRs")
public class ObtenerTokenRs {

    protected InformacionToken tokenAcceso;

    /**
     * Gets the value of the tokenAcceso property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionToken }
     *     
     */
    public InformacionToken getTokenAcceso() {
        return tokenAcceso;
    }

    /**
     * Sets the value of the tokenAcceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionToken }
     *     
     */
    public void setTokenAcceso(InformacionToken value) {
        this.tokenAcceso = value;
    }

}
