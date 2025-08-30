
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
 *         &lt;element name="informacionUsuario" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}identificadorUsuario" minOccurs="0"/>
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
    "informacionUsuario"
})
@XmlRootElement(name = "inactivarUsuarioRq")
public class InactivarUsuarioRq {

    protected IdentificadorUsuario informacionUsuario;

    /**
     * Gets the value of the informacionUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificadorUsuario }
     *     
     */
    public IdentificadorUsuario getInformacionUsuario() {
        return informacionUsuario;
    }

    /**
     * Sets the value of the informacionUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificadorUsuario }
     *     
     */
    public void setInformacionUsuario(IdentificadorUsuario value) {
        this.informacionUsuario = value;
    }

}
