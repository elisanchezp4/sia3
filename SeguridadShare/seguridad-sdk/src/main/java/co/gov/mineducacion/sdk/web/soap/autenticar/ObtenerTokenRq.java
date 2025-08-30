
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
 *         &lt;element name="codigoAutorizacion" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}autorizacion" minOccurs="0"/>
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
    "codigoAutorizacion"
})
@XmlRootElement(name = "obtenerTokenRq")
public class ObtenerTokenRq {

    protected Autorizacion codigoAutorizacion;

    /**
     * Gets the value of the codigoAutorizacion property.
     * 
     * @return
     *     possible object is
     *     {@link Autorizacion }
     *     
     */
    public Autorizacion getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    /**
     * Sets the value of the codigoAutorizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Autorizacion }
     *     
     */
    public void setCodigoAutorizacion(Autorizacion value) {
        this.codigoAutorizacion = value;
    }

}
