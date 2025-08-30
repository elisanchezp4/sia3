package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class UsuariosRolesDto implements Serializable {

    private static final long serialVersionUID = 588273723954533032L;

    transient List<UsuariosDTO> usuarios;

    transient List<RolesDTO> roles;

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

}
