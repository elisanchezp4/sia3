
package co.gov.mineducacion.seguridad.web.soap.autenticacion;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoOpcion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoOpcion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Menu"/>
 *     &lt;enumeration value="pestaña"/>
 *     &lt;enumeration value="Boton"/>
 *     &lt;enumeration value="Vinculo"/>
 *     &lt;enumeration value="Submenu"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoOpcion")
@XmlEnum
public enum TipoOpcion {

    @XmlEnumValue("Menu")
    MENU("Menu"),
    @XmlEnumValue("Pesta\u00f1a")
    PESTANA("Pesta\u00f1a"),
    @XmlEnumValue("Boton")
    BOTON("Boton"),
    @XmlEnumValue("Vinculo")
    VINCULO("Vinculo"),
    @XmlEnumValue("Submenu")
    SUBMENU("Submenu");
    private final String value;

    TipoOpcion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoOpcion fromValue(String v) {
        for (TipoOpcion c: TipoOpcion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
