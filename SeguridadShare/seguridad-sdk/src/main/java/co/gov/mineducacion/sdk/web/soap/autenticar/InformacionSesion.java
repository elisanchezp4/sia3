
package co.gov.mineducacion.sdk.web.soap.autenticar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for informacionSesion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="informacionSesion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tokenAcceso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "informacionSesion", propOrder = {
    "tokenAcceso"
})
public class InformacionSesion {

    protected String tokenAcceso;

    /**
     * Gets the value of the tokenAcceso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenAcceso() {
        return tokenAcceso;
    }

    /**
     * Sets the value of the tokenAcceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenAcceso(String value) {
        this.tokenAcceso = value;
    }

}
