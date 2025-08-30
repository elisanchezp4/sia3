package co.gov.mineducacion.seguridad.modelo.dtos;

import javax.xml.bind.annotation.XmlRootElement;

import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.entidades.Catalogos;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DAO que contiene la información de la entidad BitacoraAuditoriaDTO que se
 * transmite por los servicios REST. Solo se transmiten los atributos simples,
 * es decir, se omiten aquellos atributos que definen relaciones con otras
 * entidades.
 *
 * @author jsoto
 */
@XmlRootElement
public class BitacoraAuditoriaDTO implements Serializable {

    /**
     * @author hfabra
     */
    private static final long serialVersionUID = 1L;

    private BigDecimal bitacoraId;

    private Timestamp fechaEvento;
    private BigDecimal tipoEvento;
    private Catalogos catalogos;
    private String usuarioId;
    private String detalle;
    private Usuario usuarios;
    private Aplicaciones aplicaciones;
    private String nombreUsuario;

    private BigDecimal aplicacionId;

    private String campoDirectorio;



    private String logonName;


    // protected region atributos adicionales on begin
    // Escriba en esta sección sus modificaciones

    // protected region atributos adicionales end

    public BitacoraAuditoriaDTO() {
        // protected region procedimientos adicionales de inicialización on
        // begin
        // Escriba en esta sección sus modificaciones

        // protected region procedimientos adicionales de inicialización end
    }

    /**
     * Constructor para llenar el objeto
     */
    public BitacoraAuditoriaDTO(Timestamp fechaEvento, String usuarioId, BigDecimal aplicacionId) {
        super();
        this.bitacoraId = new BigDecimal(0);
        this.fechaEvento = fechaEvento;
        this.usuarioId = usuarioId;
        this.aplicacionId = aplicacionId;
    }

    /**
     *
     */
    public BitacoraAuditoriaDTO(BigDecimal tipoEvento, Timestamp fechaEvento, String usuario, BigDecimal appId) {
        super();
        this.bitacoraId = new BigDecimal(0);
        this.tipoEvento = tipoEvento;
        this.fechaEvento = fechaEvento;
        this.usuarioId = usuario;
        this.aplicacionId = appId;
    }

    /**
     * Metodo que inicia la bitacora añadiendo el detalle
     *
     * @param tipoEvento
     * @param fechaEvento
     * @param usuario
     * @param appId
     * @param detalle
     */
    public BitacoraAuditoriaDTO(BigDecimal tipoEvento, Timestamp fechaEvento, String usuario, BigDecimal appId, String detalle) {
        super();
        this.bitacoraId = new BigDecimal(0);
        this.tipoEvento = tipoEvento;
        this.fechaEvento = fechaEvento;
        this.usuarioId = usuario;
        this.aplicacionId = appId;
        this.detalle = detalle;
    }

    public BitacoraAuditoriaDTO(BigDecimal tipoEvento, Timestamp fechaEvento, String usuario, BigDecimal appId, String detalle, String campoDirectorio) {
        super();
        this.bitacoraId = new BigDecimal(0);
        this.tipoEvento = tipoEvento;
        this.fechaEvento = fechaEvento;
        this.usuarioId = usuario;
        this.aplicacionId = appId;
        this.detalle = detalle;
        this.campoDirectorio = campoDirectorio;
    }

    public BigDecimal getBitacoraId() {
        return this.bitacoraId;
    }

    public void setBitacoraId(BigDecimal bitacoraId) {
        this.bitacoraId = bitacoraId;
    }

    public Timestamp getFechaEvento() {
        return this.fechaEvento;
    }

    public void setFechaEvento(Timestamp fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public BigDecimal getTipoEvento() {
        return this.tipoEvento;
    }

    public void setTipoEvento(BigDecimal tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Catalogos getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(Catalogos catalogos) {
        this.catalogos = catalogos;
    }

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getAplicacionId() {
        return this.aplicacionId;
    }

    public void setAplicacionId(BigDecimal aplicacionId) {
        this.aplicacionId = aplicacionId;
    }

    public Aplicaciones getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(Aplicaciones aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuario) {
        this.usuarios = usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCampoDirectorio() {
        return campoDirectorio;
    }

    public void setCampoDirectorio(String campoDirectorio) {
        this.campoDirectorio = campoDirectorio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitacoraAuditoriaDTO other = (BitacoraAuditoriaDTO) obj;
        if (aplicacionId == null) {
            if (other.aplicacionId != null)
                return false;
        } else if (!aplicacionId.equals(other.aplicacionId))
            return false;
        if (bitacoraId == null) {
            if (other.bitacoraId != null)
                return false;
        } else if (!bitacoraId.equals(other.bitacoraId))
            return false;
        if (fechaEvento == null) {
            if (other.fechaEvento != null)
                return false;
        } else if (!fechaEvento.equals(other.fechaEvento))
            return false;
        if (tipoEvento == null) {
            if (other.tipoEvento != null)
                return false;
        } else if (!tipoEvento.equals(other.tipoEvento))
            return false;
        if (usuarioId == null) {
            if (other.usuarioId != null)
                return false;
        } else if (!usuarioId.equals(other.usuarioId))
            return false;
        if (detalle == null) {
            if (other.detalle != null)
                return false;
        } else if (!detalle.equals(other.detalle))
            return false;
        return true;
    }

    //Inicio metodos HBT

    /**
     * Metodo usado en el datasource que genera reporte auditoria
     *
     * @param contador
     * @return
     */
    public BitacoraAuditoriaDTO get(int contador) {
        return null;
    }
    //Fin metodos HBT

    public String getLogonName() {
        return logonName;
    }

    public void setLogonName(String logonName) {
        this.logonName = logonName;
    }
}
