
package co.gov.mineducacion.seguridad.web.soap.gestionar;

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
 *         &lt;element name="informacionRol" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionRol" minOccurs="0"/>
 *         &lt;element name="informacionUsuario" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionUsuario" minOccurs="0"/>
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
    "informacionRol",
    "informacionUsuario"
})
@XmlRootElement(name = "crearUsuarioExternoRq")
public class CrearUsuarioExternoRq {

    @XmlElementRef(name = "informacionRol", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<InformacionRol> informacionRol;
    @XmlElementRef(name = "informacionUsuario", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<InformacionUsuario> informacionUsuario;

    /**
     * Gets the value of the informacionRol property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link InformacionRol }{@code >}
     *     
     */
    public JAXBElement<InformacionRol> getInformacionRol() {
        return informacionRol;
    }

    /**
     * Sets the value of the informacionRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link InformacionRol }{@code >}
     *     
     */
    public void setInformacionRol(JAXBElement<InformacionRol> value) {
        this.informacionRol = value;
    }

    /**
     * Gets the value of the informacionUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link InformacionUsuario }{@code >}
     *     
     */
    public JAXBElement<InformacionUsuario> getInformacionUsuario() {
        return informacionUsuario;
    }

    /**
     * Sets the value of the informacionUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link InformacionUsuario }{@code >}
     *     
     */
    public void setInformacionUsuario(JAXBElement<InformacionUsuario> value) {
        this.informacionUsuario = value;
    }

}
