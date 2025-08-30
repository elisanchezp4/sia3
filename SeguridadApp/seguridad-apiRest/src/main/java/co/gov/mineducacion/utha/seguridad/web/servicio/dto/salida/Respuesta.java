package co.gov.mineducacion.utha.seguridad.web.servicio.dto.salida;

import java.io.Serializable;

public class Respuesta implements Serializable {
    private final Integer codigo;
    private final transient Object mensaje;

    public Respuesta(Integer codigo, Object mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Object getMensaje() {
        return mensaje;
    }

}
