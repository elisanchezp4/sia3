package co.gov.mineducacion.seguridad.modelo.dtos.externos;

import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;

public class UsuarioResponseDTO {
    private String samAccountName;
    private String givenName;
    private String surname;
    private String displayName;
    private String mail;
    private String name;
    private String password;
    private String description;
    private Boolean enabled;
    private String distinguishedName;


    public UsuarioResponseDTO(UsuarioLdap usuarioLdap) {
        this.samAccountName = usuarioLdap.getsAMAccountName();
        this.givenName = usuarioLdap.getGivenName();
        this.surname = usuarioLdap.getSn();
        this.displayName = usuarioLdap.getDisplayName();
        this.mail = usuarioLdap.getMail();
        this.name = usuarioLdap.getName();
        this.password = usuarioLdap.getUserPassword();
        this.description = usuarioLdap.getDescription();
        this.distinguishedName = usuarioLdap.getDistinguishedName();
        this.enabled = true;
    }

    public UsuarioResponseDTO(String samAccountName, Boolean enabled) {
        this.samAccountName = samAccountName;
        this.enabled = enabled;
    }

    public UsuarioResponseDTO(String samAccountName, String password) {
        this.samAccountName = samAccountName;
        this.password = password;
    }

    public String getSamAccountName() {
        return samAccountName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getSurname() {
        return surname;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }
}
