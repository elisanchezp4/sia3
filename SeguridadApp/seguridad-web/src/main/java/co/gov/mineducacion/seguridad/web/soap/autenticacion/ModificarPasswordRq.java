
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
 *         &lt;element name="informacionSesion" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionNuevaClave" minOccurs="0"/>
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
@XmlRootElement(name = "modificarPasswordRq")
public class ModificarPasswordRq {

    @XmlElementRef(name = "informacionSesion", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<InformacionNuevaClave> informacionSesion;

    /**
     * Gets the value of the informacionSesion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link InformacionNuevaClave }{@code >}
     *     
     */
    public JAXBElement<InformacionNuevaClave> getInformacionSesion() {
        return informacionSesion;
    }

    /**
     * Sets the value of the informacionSesion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link InformacionNuevaClave }{@code >}
     *     
     */
    public void setInformacionSesion(JAXBElement<InformacionNuevaClave> value) {
        this.informacionSesion = value;
    }

}
