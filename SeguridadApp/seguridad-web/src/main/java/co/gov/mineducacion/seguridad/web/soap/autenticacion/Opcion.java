
package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Opcion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Opcion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hijosOpcion" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}ArrayOfOpcion" minOccurs="0"/>
 *         &lt;element name="nombreObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="opcionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}TipoOpcion" minOccurs="0"/>
 *         &lt;element name="nombreRolAsociado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Opcion", propOrder = {
    "descripcion",
    "hijosOpcion",
    "nombreObjeto",
    "opcionId",
    "tipo",
    "nombreRolAsociado"
})
public class Opcion {

    @XmlElementRef(name = "descripcion", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descripcion;
    @XmlElementRef(name = "hijosOpcion", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfOpcion> hijosOpcion;
    @XmlElementRef(name = "nombreObjeto", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreObjeto;
    protected Integer opcionId;
    protected TipoOpcion tipo;
    @XmlElementRef(name = "nombreRolAsociado", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreRolAsociado;

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescripcion(JAXBElement<String> value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the hijosOpcion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfOpcion }{@code >}
     *     
     */
    public JAXBElement<ArrayOfOpcion> getHijosOpcion() {
        return hijosOpcion;
    }

    /**
     * Sets the value of the hijosOpcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfOpcion }{@code >}
     *     
     */
    public void setHijosOpcion(JAXBElement<ArrayOfOpcion> value) {
        this.hijosOpcion = value;
    }

    /**
     * Gets the value of the nombreObjeto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreObjeto() {
        return nombreObjeto;
    }

    /**
     * Sets the value of the nombreObjeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreObjeto(JAXBElement<String> value) {
        this.nombreObjeto = value;
    }

    /**
     * Gets the value of the opcionId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOpcionId() {
        return opcionId;
    }

    /**
     * Sets the value of the opcionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOpcionId(Integer value) {
        this.opcionId = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoOpcion }
     *     
     */
    public TipoOpcion getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoOpcion }
     *     
     */
    public void setTipo(TipoOpcion value) {
        this.tipo = value;
    }
    
    /**
     * Gets the value of the nombreRolAsociado property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreRolAsociado() {
        return nombreRolAsociado;
    }

    /**
     * Sets the value of the nombreRolAsociado property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreRolAsociado(JAXBElement<String> value) {
        this.nombreRolAsociado = value;
    }

}
