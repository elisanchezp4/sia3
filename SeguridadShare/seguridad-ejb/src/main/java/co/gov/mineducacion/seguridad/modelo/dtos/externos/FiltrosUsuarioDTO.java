package co.gov.mineducacion.seguridad.modelo.dtos.externos;

public class FiltrosUsuarioDTO {
    private String accountName;
    private String firstName;
    private String lastName;
    private String email;
    private String enabled;

    public FiltrosUsuarioDTO(String firstName, String lastName, String email, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountName = login;
    }

    public FiltrosUsuarioDTO() {
        //Json
    }

    public FiltrosUsuarioDTO constructorFiltroEstado(Boolean estado) {
        this.enabled = String.valueOf(estado);
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getEnabled() {
        return enabled;
    }

}
