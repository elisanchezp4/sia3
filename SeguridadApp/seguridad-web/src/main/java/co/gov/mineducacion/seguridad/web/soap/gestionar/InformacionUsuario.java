
package co.gov.mineducacion.seguridad.web.soap.gestionar;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for informacionUsuario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="informacionUsuario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumeroIdentificacion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="TipoIdentificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreUsuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}TipoUsuario" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://soap.mineducacion.gov.co/11/seguridad/sia3}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "informacionUsuario", propOrder = {
    "numeroIdentificacion",
    "tipoIdentificacion",
    "apellidos",
    "email",
    "nombreUsuario",
    "nombres",
    "tipo",
    "userId"
})
public class InformacionUsuario {

    @XmlElement(name = "NumeroIdentificacion")
    protected Long numeroIdentificacion;
    @XmlElementRef(name = "TipoIdentificacion", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tipoIdentificacion;
    @XmlElementRef(name = "apellidos", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> apellidos;
    @XmlElementRef(name = "email", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> email;
    @XmlElementRef(name = "nombreUsuario", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreUsuario;
    @XmlElementRef(name = "nombres", namespace = "http://soap.mineducacion.gov.co/11/seguridad/sia3", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombres;
    protected TipoUsuario tipo;
    @XmlElement(name = "userId", required = false)
    protected Integer userId;

    /**
     * Gets the value of the numeroIdentificacion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Sets the value of the numeroIdentificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeroIdentificacion(Long value) {
        this.numeroIdentificacion = value;
    }

    /**
     * Gets the value of the tipoIdentificacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Sets the value of the tipoIdentificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoIdentificacion(JAXBElement<String> value) {
        this.tipoIdentificacion = value;
    }

    /**
     * Gets the value of the apellidos property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApellidos() {
        return apellidos;
    }

    /**
     * Sets the value of the apellidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApellidos(JAXBElement<String> value) {
        this.apellidos = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEmail(JAXBElement<String> value) {
        this.email = value;
    }

    /**
     * Gets the value of the nombreUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Sets the value of the nombreUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreUsuario(JAXBElement<String> value) {
        this.nombreUsuario = value;
    }

    /**
     * Gets the value of the nombres property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombres() {
        return nombres;
    }

    /**
     * Sets the value of the nombres property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombres(JAXBElement<String> value) {
        this.nombres = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoUsuario }
     *     
     */
    public TipoUsuario getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoUsuario }
     *     
     */
    public void setTipo(TipoUsuario value) {
        this.tipo = value;
    }
    
    
    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUserId(Integer value) {
        this.userId = value;
    }

}
