package co.gov.mineducacion.seguridad.modelo.enums;

import java.util.Arrays;

public enum TipoValidacion {

    REQUIRED("required", 217L),
    MAX("max", 219L),
    MIN("min", 219L),
    ALPHANUMERIC("alphanumeric", 214L),
    NUMERIC("numeric", 214L),
    ALPHABETIC("alphabetic", 214L),
    EMAIL("email", 216L),
    PATTERN("pattern", 214L),
    MAX_LENGTH("maxLength", 213L),
    MIN_LENGTH("minLength", 213L),
    PASSWORD("password", 215L);

    private TipoValidacion(String descripcion, Long idMensaje) {
        this.descripcion = descripcion;
        this.idMensaje = idMensaje;
    }

    private final String descripcion;
    private final Long idMensaje;

    public String getDescripcion() {
        return descripcion;
    }

    public Long getIdMensaje() {
        return idMensaje;
    }

    public static TipoValidacion fromString(String value) {
        return Arrays.stream(TipoValidacion.values())
                .filter(ct -> (value).equals(ct.getDescripcion()))
                .findFirst()
                .orElse(null);
    }

}
