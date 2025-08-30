
package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="informacionToken" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionSesion" minOccurs="0"/>
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
@XmlRootElement(name = "actualizarFechaVencimientoTokenRq")
public class ActualizarFechaVencimientoTokenRq {

    @XmlElementRef(name = "informacionToken", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<InformacionSesion> informacionToken;

    /**
     * Gets the value of the informacionToken property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link InformacionSesion }{@code >}
     *     
     */
    public JAXBElement<InformacionSesion> getInformacionToken() {
        return informacionToken;
    }

    /**
     * Sets the value of the informacionToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link InformacionSesion }{@code >}
     *     
     */
    public void setInformacionToken(JAXBElement<InformacionSesion> value) {
        this.informacionToken = value;
    }

}
