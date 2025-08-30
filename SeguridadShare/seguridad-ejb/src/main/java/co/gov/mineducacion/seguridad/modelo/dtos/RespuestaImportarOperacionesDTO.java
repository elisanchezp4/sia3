package co.gov.mineducacion.seguridad.modelo.dtos;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class RespuestaImportarOperacionesDTO implements Serializable {
    private final List<OperacionesDTO> operacionesCreadas;
    private final List<OperacionesDTO> operacionesActualizadas;

    public RespuestaImportarOperacionesDTO(List<OperacionesDTO> operacionesCreadas, List<OperacionesDTO> operacionesActualizadas) {
        this.operacionesCreadas = operacionesCreadas;
        this.operacionesActualizadas = operacionesActualizadas;
    }

    public List<OperacionesDTO> getOperacionesCreadas() {
        return operacionesCreadas;
    }

    public List<OperacionesDTO> getOperacionesActualizadas() {
        return operacionesActualizadas;
    }
}
