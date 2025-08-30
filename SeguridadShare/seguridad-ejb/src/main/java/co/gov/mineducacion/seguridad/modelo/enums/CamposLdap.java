package co.gov.mineducacion.seguridad.modelo.enums;

import java.util.Arrays;

public enum CamposLdap {
    DESCRIPTION("description", "Descripcion"),
    DISPLAY_NAME("displayName", "Display name"),
    DISTINGUISHED_NAME("distinguishedName", "distinguishedName"),
    GIVE_NAME("givenName", "givenName"),
    MAIL("mail", "Mail"),
    NAME("name", "Name"),
    PASSWORD("userPassword", "Password"),
    SAM_ACCOUNT_NAME("sAMAccountName", "SamAccountName"),
    SURNAME("SN", "Surname");

    private final String id;
    private final String descripcion;

     CamposLdap(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static CamposLdap fromString(String value) {
        return Arrays.stream(CamposLdap.values())
                .filter(ct -> (ct.getId()).equals(value))
                .findFirst()
                .orElse(null);
    }

}
