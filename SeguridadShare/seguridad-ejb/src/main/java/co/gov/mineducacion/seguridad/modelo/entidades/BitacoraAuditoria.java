package co.gov.mineducacion.seguridad.modelo.entidades;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import uk.co.jemos.podam.annotations.PodamExclude;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the CATEGORIES database table.
 */
@Entity
@Table(name = "BITACORA_AUDITORIA")
@NamedQuery(name = "BitacoraAuditoria.findAll", query = "SELECT p FROM BitacoraAuditoria p")
public class BitacoraAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    //Definicion de atributos de la entidad (Exceptuando relaciones)
    public static final String ENTIDAD_BITACORA_AUDITORIA_PK = "bitacoraId";
    public static final String ENTIDAD_BITACORA_AUDITORIA_FECHA_EVENTO = "fechaEvento";
    public static final String ENTIDAD_BITACORA_AUDITORIA_TIPO_EVENTO = "tipoEvento";
    public static final String ENTIDAD_BITACORA_AUDITORIA_USUARIO_ID = "usuarioId";
    public static final String ENTIDAD_BITACORA_AUDITORIA_APLICACION_ID = "aplicacionId";
    public static final String ENTIDAD_BITACORA_AUDITORIA_DETALLE = "detalle";
    private static final String[] ATRIBUTOS_ENTIDAD_BITACORA_AUDITORIA
            = {ENTIDAD_BITACORA_AUDITORIA_USUARIO_ID, ENTIDAD_BITACORA_AUDITORIA_PK, ENTIDAD_BITACORA_AUDITORIA_FECHA_EVENTO,
            ENTIDAD_BITACORA_AUDITORIA_APLICACION_ID, ENTIDAD_BITACORA_AUDITORIA_TIPO_EVENTO, ENTIDAD_BITACORA_AUDITORIA_DETALLE};

    @Id
    @GeneratedValue(generator = "SEC_BIT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_BIT", sequenceName = "SEQ_BIT_AUDIT_BIT_ID", allocationSize = 1)
    @Column(name = "BITACORA_ID")
    private BigDecimal bitacoraId;


    @Column(name = "FECHA_EVENTO")
    private Timestamp fechaEvento;

    @Column(name = "TIPO_EVENTO")
    private BigDecimal tipoEvento;

    @PodamExclude
    @Column(name = "USUARIO_ID")
    private String usuarioId;

    @PodamExclude
    @Column(name = "DETALLE")
    private String detalle;

    @PodamExclude
    @Column(name = "APLICACION_ID")
    private BigDecimal aplicacionId;

    @ManyToOne
    @JoinColumn(name = "APLICACION_ID", referencedColumnName = "APLICACION_ID", insertable = false, updatable = false)
    @PodamExclude
    private Aplicaciones aplicaciones;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID", insertable = false, updatable = false)
    @PodamExclude
    private Usuario usuarios;

    @ManyToOne
    @JoinColumn(name = "TIPO_EVENTO", referencedColumnName = "CATALOGO_ID", insertable = false, updatable = false)
    @PodamExclude
    private Catalogos catalogos;

    @PodamExclude
    @Column(name = "CAMPO_DIRECTORIO")
    private String campoDirectorio;

    // protected region atributos adicionales on begin
    // Escriba en esta sección sus modificaciones

    // protected region atributos adicionales end

    public BitacoraAuditoria() {
        // protected region procedimientos adicionales de inicialización on begin
        // Escriba en esta sección sus modificaciones

        // protected region procedimientos adicionales de inicialización end
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

    public String getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getAplicacionId() {
        return this.aplicacionId;
    }

    public void setAplicacionId(BigDecimal aplicacionId) {
        this.aplicacionId = aplicacionId;
    }


    public Aplicaciones getAplicaciones() {
        return this.aplicaciones;
    }

    public void setAplicaciones(Aplicaciones aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public Usuario getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public Catalogos getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(Catalogos catalogos) {
        this.catalogos = catalogos;
    }


    public String getDetalle() {
        return detalle;
    }


    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCampoDirectorio() {
        return campoDirectorio;
    }

    public void setCampoDirectorio(String campoDirectorio) {
        this.campoDirectorio = campoDirectorio;
    }

    /**
     * Verifica si la entidad contiene el atributo que se pasa como parámetro
     *
     * @param atributo Nombre del atributo a validar
     * @return Verdadero si la entidad contiene al atributo.
     */
    public static boolean contieneAtributo(String atributo) {

        boolean contiene = false;
        for (final String atr : ATRIBUTOS_ENTIDAD_BITACORA_AUDITORIA) {
            if (atr.equals(atributo)) {
                contiene = true;
            }
        }

        return contiene;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitacoraAuditoria other = (BitacoraAuditoria) obj;
        if (aplicacionId == null) {
            if (other.aplicacionId != null)
                return false;
        } else if (!aplicacionId.equals(other.aplicacionId))
            return false;
        if (aplicaciones == null) {
            if (other.aplicaciones != null)
                return false;
        } else if (!aplicaciones.equals(other.aplicaciones))
            return false;
        if (catalogos == null) {
            if (other.catalogos != null)
                return false;
        } else if (!catalogos.equals(other.catalogos))
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
        if (usuarios == null) {
            if (other.usuarios != null)
                return false;
        } else if (!usuarios.equals(other.usuarios))
            return false;
        return true;
    }


} 

