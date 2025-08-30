
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
 *         &lt;element name="permisos" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}informacionPermisos" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}usuario" minOccurs="0"/>
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
    "permisos",
    "usuario"
})
@XmlRootElement(name = "obtenerRolesPermisosRs")
public class ObtenerRolesPermisosRs {

    @XmlElementRef(name = "permisos", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<InformacionPermisos> permisos;
    @XmlElementRef(name = "usuario", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<Usuario> usuario;

    /**
     * Gets the value of the permisos property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link InformacionPermisos }{@code >}
     *     
     */
    public JAXBElement<InformacionPermisos> getPermisos() {
        return permisos;
    }

    /**
     * Sets the value of the permisos property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link InformacionPermisos }{@code >}
     *     
     */
    public void setPermisos(JAXBElement<InformacionPermisos> value) {
        this.permisos = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Usuario }{@code >}
     *     
     */
    public JAXBElement<Usuario> getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Usuario }{@code >}
     *     
     */
    public void setUsuario(JAXBElement<Usuario> value) {
        this.usuario = value;
    }

}
