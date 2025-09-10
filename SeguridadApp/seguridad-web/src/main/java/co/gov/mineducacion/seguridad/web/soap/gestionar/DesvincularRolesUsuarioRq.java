
package co.gov.mineducacion.seguridad.web.soap.gestionar;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for desvincularRolesUsuarioRq complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="desvincularRolesUsuarioRq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreUsuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="correoElectronico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://www.w3.org/2001/XMLSchema}ArrayOfstring" minOccurs="0"/>
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
@XmlType(name = "desvincularRolesUsuarioRq", propOrder = {
        "userId",
        "nombreUsuario",
        "correoElectronico",
        "roles",
        "notificarUsuario"
})
public class DesvincularRolesUsuarioRq {

    protected String userId;
    protected String nombreUsuario;
    protected String correoElectronico;
    protected ArrayOfstring roles;
    protected Boolean notificarUsuario;

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


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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
     *    {@link ArrayOfstring }
     *     
     */
    public void setRoles(ArrayOfstring value) {
        this.roles = value;
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
