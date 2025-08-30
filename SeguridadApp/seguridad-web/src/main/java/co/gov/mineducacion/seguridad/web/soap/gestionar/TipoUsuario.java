
package co.gov.mineducacion.seguridad.web.soap.gestionar;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoUsuario.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoUsuario">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Interno"/>
 *     &lt;enumeration value="Externo"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoUsuario")
@XmlEnum
public enum TipoUsuario {

    @XmlEnumValue("Interno")
    INTERNO("Interno"),
    @XmlEnumValue("Externo")
    EXTERNO("Externo");
    private final String value;

    TipoUsuario(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoUsuario fromValue(String v) {
        for (TipoUsuario c: TipoUsuario.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
