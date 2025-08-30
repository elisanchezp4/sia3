
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

    protected InformacionPermisos permisos;
    protected Usuario usuario;

    /**
     * Gets the value of the permisos property.
     * 
     * @return
     *     possible object is
     *     {@link InformacionPermisos }
     *     
     */
    public InformacionPermisos getPermisos() {
        return permisos;
    }

    /**
     * Sets the value of the permisos property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformacionPermisos }
     *     
     */
    public void setPermisos(InformacionPermisos value) {
        this.permisos = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link Usuario }
     *     
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Usuario }
     *     
     */
    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

}
