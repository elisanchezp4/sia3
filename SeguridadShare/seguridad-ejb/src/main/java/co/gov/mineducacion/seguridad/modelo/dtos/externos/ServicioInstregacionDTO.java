package co.gov.mineducacion.seguridad.modelo.dtos.externos;

public class ServicioInstregacionDTO {
    private Long codigoRespuesta;
    private Boolean error;
    private String mensaje;
    private Object data;

    public ServicioInstregacionDTO() {
        //Json
    }

    public ServicioInstregacionDTO(Long codigoRespuesta, Boolean error, String mensaje, Object data) {
        this.codigoRespuesta = codigoRespuesta;
        this.error = error;
        this.mensaje = mensaje;
        this.data = data;
    }
    public Long getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public boolean getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Object getData() {
        return data;
    }

}
