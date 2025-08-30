
package co.gov.mineducacion.sdk.web.soap.autenticar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for informacionPermisos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="informacionPermisos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="permisos" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}ArrayOfOpcion" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}ArrayOfstring" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "informacionPermisos", propOrder = {
    "permisos",
    "roles"
})
public class InformacionPermisos {

    protected ArrayOfOpcion permisos;
    protected ArrayOfstring roles;

    /**
     * Gets the value of the permisos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOpcion }
     *     
     */
    public ArrayOfOpcion getPermisos() {
        return permisos;
    }

    /**
     * Sets the value of the permisos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOpcion }
     *     
     */
    public void setPermisos(ArrayOfOpcion value) {
        this.permisos = value;
    }

    /**
     * Gets the value of the roles property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfstring }
     *     
     */
    public ArrayOfstring getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfstring }
     *     
     */
    public void setRoles(ArrayOfstring value) {
        this.roles = value;
    }

}
