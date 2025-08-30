package co.gov.mineducacion.sdk.web.soap.gestionar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for actualizarDatosBasicosRq complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="actualizarDatosBasicosRq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}String" minOccurs="0"/>
 *         &lt;element name="apellidos" type="{http://www.w3.org/2001/XMLSchema}String" minOccurs="0"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}String" minOccurs="0"/>
 *         &lt;element name="rutaDirectorio" type="{http://www.w3.org/2001/XMLSchema}String" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}String" minOccurs="0"/>
 *         &lt;element name="notificarUsuario" type="{http://www.w3.org/2001/XMLSchema}Boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actualizarDatosBasicosRq",propOrder = {
        "nombres",
        "apellidos",
        "numeroDocumento",
        "rutaDirectorio",
        "userId",
        "notificarUsuario"
})
public class ActualizarDatosBasicosRq {

    protected String nombres;
    protected String apellidos;
    protected String numeroDocumento;
    protected String rutaDirectorio;

    protected String userId;
    protected Boolean notificarUsuario;

    /**
     * Gets the value of the nombres property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Sets the value of the nombres property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNombres(String value) {
        this.nombres = value;
    }

    /**
     * Gets the value of the apellidos property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Sets the value of the apellidos property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setApellidos(String value) {
        this.apellidos = value;
    }

    /**
     * Gets the value of the numeroDocumento property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Sets the value of the numeroDocumento property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    /**
     * Gets the value of the rutaDirectorio property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRutaDirectorio() {
        return rutaDirectorio;
    }

    /**
     * Sets the value of the numeroDocumento property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRutaDirectorio(String value) {
        this.rutaDirectorio = value;
    }

    /**
     * Gets the value of the userId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the notificarUsuario property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getNotificarUsuario() {
        return notificarUsuario;
    }

    /**
     * Sets the value of the notificarUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setNotificarUsuario(Boolean value) {
        this.notificarUsuario = value;
    }
}
