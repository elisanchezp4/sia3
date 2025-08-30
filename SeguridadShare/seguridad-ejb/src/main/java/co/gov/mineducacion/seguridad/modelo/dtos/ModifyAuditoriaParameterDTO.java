package co.gov.mineducacion.seguridad.modelo.dtos;


public class ModifyAuditoriaParameterDTO {
    String antes;
    String despues;
    String usuario;
    String campoActivo;
    String aplicacionId;
    String logonName;

    public String getAntes() {
        return antes;
    }

    public String getDespues() {
        return despues;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCampoActivo() {
        return campoActivo;
    }

    public String getAplicacionId() {
        return aplicacionId;
    }

    public String getLogonName() {
        return logonName;
    }

    public ModifyAuditoriaParameterDTO(String antes, String despues, String usuario, String campoActivo, String aplicacionId) {
        this.antes = antes;
        this.despues = despues;
        this.usuario = usuario;
        this.campoActivo = campoActivo;
        this.aplicacionId = aplicacionId;
    }

    public ModifyAuditoriaParameterDTO logonName(String logonName){
        this.logonName = logonName;
        return this;
    }
}
