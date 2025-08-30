package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * DAO que contiene la información de la entidad OperacionesDTO que se transmite
 * por los servicios REST. Solo se transmiten los atributos simples, es decir,
 * se omiten aquellos atributos que definen relaciones con otras entidades.
 *
 * @author jsoto
 */
@XmlRootElement
public class OperacionesDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4395940355824065889L;

    private BigDecimal opcionId;
    private BigDecimal aplicacionId;
    private String descripcion;
    private String nombreObjeto;
    private BigDecimal opcionPadre;
    private String tipo;
    private BigDecimal estado;
    private Timestamp fechaCreacion;
    private Timestamp ultimaModificacion;
    private BigDecimal usuarioModificacion;
    private String nombreRolAsociado;

    private List<OperacionesDTO> listadoOperaciones;
    private BigDecimal ordenVisualizacion;

    private Aplicaciones aplicacion;

    private String urlImgGif;

    // protected region atributos adicionales on begin
    // Escriba en esta sección sus modificaciones

    // protected region atributos adicionales end

    public OperacionesDTO() {
        //Json
    }

    public OperacionesDTO(OperacionesDTO actual, OperacionesDTO actualizar) {
        this.opcionId = actual.getOpcionId();
        this.aplicacionId = actualizar.getAplicacionId();
        this.descripcion = actualizar.getDescripcion();
        this.nombreObjeto = actualizar.getNombreObjeto();
        this.opcionPadre = actual.getOpcionPadre();
        this.tipo = actualizar.getTipo();
        this.estado = actualizar.getEstado();
        this.fechaCreacion = Timestamp.from(Instant.now());
        this.ultimaModificacion = Timestamp.from(Instant.now());
        this.usuarioModificacion = actualizar.getUsuarioModificacion();
        this.nombreRolAsociado = actualizar.getNombreRolAsociado();
        this.ordenVisualizacion = actualizar.getOrdenVisualizacion();
        this.aplicacion = actual.getAplicaciones();
        this.urlImgGif = actualizar.getUrlImgGif();
    }

    public BigDecimal getOrdenVisualizacion() {
        return ordenVisualizacion;
    }

    public void setOrdenVisualizacion(BigDecimal ordenVisualizacion) {
        this.ordenVisualizacion = ordenVisualizacion;
    }

    public BigDecimal getOpcionId() {
        return this.opcionId;
    }

    public void setOpcionId(BigDecimal opcionId) {
        this.opcionId = opcionId;
    }

    public BigDecimal getAplicacionId() {
        return this.aplicacionId;
    }

    public void setAplicacionId(BigDecimal aplicacionId) {
        this.aplicacionId = aplicacionId;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreObjeto() {
        return this.nombreObjeto;
    }

    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }

    public BigDecimal getOpcionPadre() {
        return this.opcionPadre;
    }

    public void setOpcionPadre(BigDecimal opcionPadre) {
        this.opcionPadre = opcionPadre;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getEstado() {
        return this.estado;
    }

    public void setEstado(BigDecimal estado) {
        this.estado = estado;
    }

    public Timestamp getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getUltimaModificacion() {
        return this.ultimaModificacion;
    }

    public void setUltimaModificacion(Timestamp ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public BigDecimal getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public void setUsuarioModificacion(BigDecimal usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Aplicaciones getAplicaciones() {
        return aplicacion;
    }

    public void setAplicaciones(Aplicaciones aplicaciones) {
        this.aplicacion = aplicaciones;
    }

    public String getNombreRolAsociado() {
        return nombreRolAsociado;
    }

    public void setNombreRolAsociado(String nombreRolAsociado) {
        this.nombreRolAsociado = nombreRolAsociado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aplicacionId == null) ? 0 : aplicacionId.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
        result = prime * result + ((nombreObjeto == null) ? 0 : nombreObjeto.hashCode());
        result = prime * result + ((opcionId == null) ? 0 : opcionId.hashCode());
        result = prime * result + ((opcionPadre == null) ? 0 : opcionPadre.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((ultimaModificacion == null) ? 0 : ultimaModificacion.hashCode());
        result = prime * result + ((usuarioModificacion == null) ? 0 : usuarioModificacion.hashCode());
        result = prime * result + ((nombreRolAsociado == null) ? 0 : nombreRolAsociado.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OperacionesDTO other = (OperacionesDTO) obj;
        if (aplicacionId == null) {
            if (other.aplicacionId != null)
                return false;
        } else if (!aplicacionId.equals(other.aplicacionId))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (fechaCreacion == null) {
            if (other.fechaCreacion != null)
                return false;
        } else if (!fechaCreacion.equals(other.fechaCreacion))
            return false;
        if (nombreObjeto == null) {
            if (other.nombreObjeto != null)
                return false;
        } else if (!nombreObjeto.equals(other.nombreObjeto))
            return false;
        if (opcionId == null) {
            if (other.opcionId != null)
                return false;
        } else if (!opcionId.equals(other.opcionId))
            return false;
        if (opcionPadre == null) {
            if (other.opcionPadre != null)
                return false;
        } else if (!opcionPadre.equals(other.opcionPadre))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (ultimaModificacion == null) {
            if (other.ultimaModificacion != null)
                return false;
        } else if (!ultimaModificacion.equals(other.ultimaModificacion))
            return false;
        if (usuarioModificacion == null) {
            if (other.usuarioModificacion != null)
                return false;
        } else if (!usuarioModificacion.equals(other.usuarioModificacion))
            return false;
        if (nombreRolAsociado == null) {
            if (other.nombreRolAsociado != null)
                return false;
        } else if (!nombreRolAsociado.equals(other.nombreRolAsociado))
            return false;
        return true;
    }

    /**
     * @return the listadoOperaciones
     * @author hfabra
     */
    public List<OperacionesDTO> getListadoOperaciones() {
        return listadoOperaciones;
    }

    /**
     * @param listadoOperaciones the listadoOperaciones to set
     * @author hfabra
     */
    public void setListadoOperaciones(List<OperacionesDTO> listadoOperaciones) {
        this.listadoOperaciones = listadoOperaciones;
    }

    @Override
    public String toString() {
        return "OperacionesDTO [opcionId=" + opcionId + ", aplicacionId=" + aplicacionId + ", descripcion=" + descripcion
                + ", nombreObjeto=" + nombreObjeto + ", opcionPadre=" + opcionPadre
                + ", tipo=" + tipo + ", estado=" + estado
                + ", fechaCreacion=" + fechaCreacion + ", ultimaModificacion=" + ultimaModificacion
                + ", usuarioModificacion=" + usuarioModificacion + "]";
    }


    public String getUrlImgGif() {
        return urlImgGif;
    }

    public void setUrlImgGif(String urlImgGif) {
        this.urlImgGif = urlImgGif;
    }


}
