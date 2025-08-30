package co.gov.mineducacion.seguridad.rest.dtos;

import co.gov.mineducacion.seguridad.modelo.dtos.RolesDTO;

import java.io.Serializable;
import java.math.BigDecimal;

public class RolDTO implements Serializable  {
    private String nombre;
    private BigDecimal idAplicacion;
    private String rutaDirectorioActivo;
    private BigDecimal idUsuario;

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(BigDecimal idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public String getRutaDirectorioActivo() {
        return rutaDirectorioActivo;
    }

    public void setRutaDirectorioActivo(String rutaDirectorioActivo) {
        this.rutaDirectorioActivo = rutaDirectorioActivo;
    }

    public RolesDTO aRolesDTO() {
        RolesDTO rolesDTO = new RolesDTO();
        rolesDTO.setAplicacionId(idAplicacion);
        rolesDTO.setNombre(nombre);
        rolesDTO.setPath(rutaDirectorioActivo);
        rolesDTO.setEstado(BigDecimal.ONE);
        rolesDTO.setUsuarioModificacion(idUsuario);
        return rolesDTO;
    }
}
