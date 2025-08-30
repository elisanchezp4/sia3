
package co.gov.mineducacion.sdk.web.soap.gestionar;

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
 *         &lt;element name="usuarios" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}ArrayOfinformacionUsuario" minOccurs="0"/>
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
        "usuarios"
})
@XmlRootElement(name = "vincularRolesUsuarioRs")
public class VincularRolesUsuarioRs {

    protected ArrayOfinformacionUsuario usuarios;

    /**
     * Gets the value of the usuarios property.
     *
     * @return
     *     possible object is
     *     {@link ArrayOfinformacionUsuario }
     *
     */
    public ArrayOfinformacionUsuario getUsuarios() {
        return usuarios;
    }

    /**
     * Sets the value of the usuarios property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayOfinformacionUsuario }
     *
     */
    public void setUsuarios(ArrayOfinformacionUsuario value) {
        this.usuarios = value;
    }

}
