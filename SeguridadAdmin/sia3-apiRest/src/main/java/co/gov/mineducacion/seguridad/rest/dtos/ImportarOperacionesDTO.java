package co.gov.mineducacion.seguridad.rest.dtos;

import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;

import java.io.Serializable;
import java.util.List;

public class ImportarOperacionesDTO implements Serializable {
    private static final long serialVersionUID = 5395940355824065889L;
    private Long idAplicacion;
    private List<OperacionesDTO> operacionesList;

    public Long getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(Long idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public List<OperacionesDTO> getOperacionesList() {
        return operacionesList;
    }

    public void setOperacionesList(List<OperacionesDTO> operacionesList) {
        this.operacionesList = operacionesList;
    }

}
