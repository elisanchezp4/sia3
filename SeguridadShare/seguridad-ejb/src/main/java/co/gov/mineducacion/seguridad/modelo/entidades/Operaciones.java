package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;

import javax.persistence.NamedQuery; 
import javax.persistence.Table;
import javax.persistence.OneToMany;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import uk.co.jemos.podam.annotations.PodamExclude;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the CATEGORIES database table.
 *
 */
@Entity
@Table(name="OPERACIONES")
@NamedQueries({	@NamedQuery(name = "Operaciones.findAll", query = "SELECT p FROM Operaciones p"),
	@NamedQuery(name = "Operaciones.findRolOperacionesByUser", 
		query = "SELECT op FROM OperacionesRol opr INNER JOIN opr.operaciones op WHERE opr.operacionesRolPK.rolId IN ?1 "
				+ "AND op.aplicacionId = ?2 order by op.ordenVisualizacion"),
	@NamedQuery(name = "Operaciones.findOperacionesByUser", 
	query = "SELECT op FROM OperacionesRol opr "
			+ " INNER JOIN opr.operaciones op "
			+ " INNER JOIN opr.roles.usuariosRolList urlist "
			+ " WHERE op.aplicacionId = ?1 "
			+ " AND urlist.usuariosRolPK.usuarioId = ?2"
			+ " order by op.ordenVisualizacion"),
	})
public class Operaciones implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_OPERACIONES_PK = "opcionId";
	public static final String ENTIDAD_OPERACIONES_APLICACION_ID = "aplicacionId";
	public static final String ENTIDAD_OPERACIONES_DESCRIPCION = "descripcion";
	public static final String ENTIDAD_OPERACIONES_NOMBRE_OBJETO = "nombreObjeto";
	public static final String ENTIDAD_OPERACIONES_OPCION_PADRE = "opcionPadre";
	public static final String ENTIDAD_OPERACIONES_TIPO = "tipo";
	public static final String ENTIDAD_OPERACIONES_ESTADO = "estado";
	public static final String ENTIDAD_OPERACIONES_FECHA_CREACION = "fechaCreacion";
	public static final String ENTIDAD_OPERACIONES_ULTIMA_MODIFICACION = "ultimaModificacion";
	public static final String ENTIDAD_OPERACIONES_USUARIO_MODIFICACION = "usuarioModificacion";
	public static final String ENTIDAD_OPERACIONES_ORDEN_VISULAIZACION = "ordenVisualizacion";
    private static final String[] ATRIBUTOS_ENTIDAD_OPERACIONES = {ENTIDAD_OPERACIONES_NOMBRE_OBJETO, ENTIDAD_OPERACIONES_ULTIMA_MODIFICACION, ENTIDAD_OPERACIONES_APLICACION_ID, ENTIDAD_OPERACIONES_TIPO, ENTIDAD_OPERACIONES_FECHA_CREACION, ENTIDAD_OPERACIONES_USUARIO_MODIFICACION, ENTIDAD_OPERACIONES_DESCRIPCION, ENTIDAD_OPERACIONES_ESTADO, ENTIDAD_OPERACIONES_PK, ENTIDAD_OPERACIONES_OPCION_PADRE,ENTIDAD_OPERACIONES_ORDEN_VISULAIZACION};

	@Id
    @Column(name="OPCION_ID")
	private BigDecimal opcionId;

    
    @PodamExclude
	@Column(name="APLICACION_ID")
	private BigDecimal aplicacionId;		
    
	@Column(name="DESCRIPCION")
	private String descripcion;		
    
	@Column(name="NOMBRE_OBJETO")
	private String nombreObjeto;		
    
    @PodamExclude
	@Column(name="OPCION_PADRE")
	private BigDecimal opcionPadre;		
    
	@Column(name="TIPO")
	private String tipo;		
    
	@Column(name="ESTADO")
	private BigDecimal estado;		
    
	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;		
    
	@Column(name="ULTIMA_MODIFICACION")
	private Timestamp ultimaModificacion;		
    
	@Column(name="USUARIO_MODIFICACION")
	private BigDecimal usuarioModificacion;		
	
	@Column(name="ORDEN_VISUALIZACION")
	private BigDecimal ordenVisualizacion;

	@ManyToOne
	@JoinColumn(name="APLICACION_ID", referencedColumnName="APLICACION_ID", insertable = false, updatable = false)
	@PodamExclude
    private Aplicaciones aplicaciones;
		
	@ManyToOne
	@JoinColumn(name="OPCION_PADRE", referencedColumnName="OPCION_ID", insertable = false, updatable = false)
	@PodamExclude
    private Operaciones operaciones;
		
	@OneToMany(mappedBy="operaciones")
	@PodamExclude
    private List<Operaciones> operacionesList;

	@Column(name="URLIMGLENGUASENAS")
	private String urlImgGif;
	
	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end
	
  

	public Operaciones(){
		// protected region procedimientos adicionales de inicialización on begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
    }

	public BigDecimal getOrdenVisualizacion() {
		return ordenVisualizacion;
	}


	public void setOrdenVisualizacion(BigDecimal ordenVisualizacion) {
		this.ordenVisualizacion = ordenVisualizacion;
	}


	public BigDecimal getOpcionId(){
		return this.opcionId;
	}
	
	public void setOpcionId(BigDecimal opcionId){
		this.opcionId = opcionId;
	}
	
	public BigDecimal getAplicacionId(){
		return this.aplicacionId;
	}
	
	public void setAplicacionId(BigDecimal aplicacionId){
		this.aplicacionId = aplicacionId;
	}
		
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
		
	public String getNombreObjeto(){
		return this.nombreObjeto;
	}
	
	public void setNombreObjeto(String nombreObjeto){
		this.nombreObjeto = nombreObjeto;
	}
		
	public BigDecimal getOpcionPadre(){
		return this.opcionPadre;
	}
	
	public void setOpcionPadre(BigDecimal opcionPadre){
		this.opcionPadre = opcionPadre;
	}
		
	public String getTipo(){
		return this.tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
		
	public BigDecimal getEstado(){
		return this.estado;
	}
	
	public void setEstado(BigDecimal estado){
		this.estado = estado;
	}
		
	public Timestamp getFechaCreacion(){
		return this.fechaCreacion;
	}
	
	public void setFechaCreacion(Timestamp fechaCreacion){
		this.fechaCreacion = fechaCreacion;
	}
		
	public Timestamp getUltimaModificacion(){
		return this.ultimaModificacion;
	}
	
	public void setUltimaModificacion(Timestamp ultimaModificacion){
		this.ultimaModificacion = ultimaModificacion;
	}
		
	public BigDecimal getUsuarioModificacion(){
		return this.usuarioModificacion;
	}
	
	public void setUsuarioModificacion(BigDecimal usuarioModificacion){
		this.usuarioModificacion = usuarioModificacion;
	}
		

    public List<Operaciones> getOperacionesList(){
		return this.operacionesList;
	}
	
	public void setOperacionesList(List<Operaciones> operacionesList){
		this.operacionesList = operacionesList;
	}
			
    public Aplicaciones getAplicaciones(){
		return this.aplicaciones; 
	}
	
	public void setAplicaciones(Aplicaciones aplicaciones){
		this.aplicaciones = aplicaciones;
	}
    public Operaciones getOperaciones(){
		return this.operaciones; 
	}
	
	public void setOperaciones(Operaciones operaciones){
		this.operaciones = operaciones;
	}
	
	public String getUrlImgGif() {
		return urlImgGif;
	}
	
	public void setUrlImgGif(String urlImgGif) {
		this.urlImgGif = urlImgGif;
	}

	/**
     * Verifica si la entidad contiene el atributo que se pasa como parámetro
     *
     * @param atributo Nombre del atributo a validar
     * @return Verdadero si la entidad contiene al atributo.
     */
    public static boolean contieneAtributo(String atributo) {
		
		boolean contiene = false;
        for (final String atr : ATRIBUTOS_ENTIDAD_OPERACIONES) {
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
		Operaciones other = (Operaciones) obj;
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
		if (operaciones == null) {
			if (other.operaciones != null)
				return false;
		} else if (!operaciones.equals(other.operaciones))
			return false;
		if (operacionesList == null) {
			if (other.operacionesList != null)
				return false;
		} else if (!operacionesList.equals(other.operacionesList))
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
		return true;
	}

	@Override
	public String toString() {
		return "Operaciones [opcionId=" + opcionId + ", aplicacionId=" + aplicacionId + ", descripcion=" + descripcion
				+ ", nombreObjeto=" + nombreObjeto + ", opcionPadre=" + opcionPadre + ", tipo=" + tipo + ", estado="
				+ estado + ", fechaCreacion=" + fechaCreacion + ", ultimaModificacion=" + ultimaModificacion
				+ ", usuarioModificacion=" + usuarioModificacion 
				+ ", ordenVisualizacion=" +  ordenVisualizacion+"]";
	}
}
