package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class UsuariosRolesDto implements Serializable {

    private static final long serialVersionUID = 588273723954533032L;

    transient List<UsuariosDTO> usuarios;

    transient List<RolesDTO> roles;

    transient Date fechaValidacionRnec;
    transient Date fechaExpedicionDocumento;
    transient String estadoValidacion;
    transient String motivoValidacion;


    public void setUsuarios(List<UsuariosDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<UsuariosDTO> getUsuarios() {
        return usuarios;
    }

    public void setRoles(List<RolesDTO> roles) {
        this.roles = roles;
    }

    public List<RolesDTO> getRoles() {
        return roles;
    }

    public UsuariosRolesDto() {
        //Json
    }

    public Date getFechaValidacionRnec() {
        return fechaValidacionRnec;
    }

    public void setFechaValidacionRnec(Date fechaValidacionRnec) {
        this.fechaValidacionRnec = fechaValidacionRnec;
    }

    public Date getFechaExpedicionDocumento() {
        return fechaExpedicionDocumento;
    }

    public void setFechaExpedicionDocumento(Date fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

    public String getEstadoValidacion() {
        return estadoValidacion;
    }

    public void setEstadoValidacion(String estadoValidacion) {
        this.estadoValidacion = estadoValidacion;
    }

    public String getMotivoValidacion() {
        return motivoValidacion;
    }

    public void setMotivoValidacion(String motivoValidacion) {
        this.motivoValidacion = motivoValidacion;
    }
}
