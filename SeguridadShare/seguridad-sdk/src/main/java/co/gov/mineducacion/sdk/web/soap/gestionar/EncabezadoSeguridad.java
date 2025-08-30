
package co.gov.mineducacion.sdk.web.soap.gestionar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for encabezadoSeguridad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="encabezadoSeguridad">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaPeticion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ipHost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "encabezadoSeguridad", propOrder = {
    "fechaPeticion",
    "ipHost"
})
public class EncabezadoSeguridad {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaPeticion;
    protected String ipHost;

    /**
     * Gets the value of the fechaPeticion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaPeticion() {
        return fechaPeticion;
    }

    /**
     * Sets the value of the fechaPeticion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaPeticion(XMLGregorianCalendar value) {
        this.fechaPeticion = value;
    }

    /**
     * Gets the value of the ipHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpHost() {
        return ipHost;
    }

    /**
     * Sets the value of the ipHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpHost(String value) {
        this.ipHost = value;
    }

}
