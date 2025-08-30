package co.gov.mineducacion.seguridad.modelo.entidades;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery; 
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.JoinColumn;

import uk.co.jemos.podam.annotations.PodamExclude;

import java.sql.Timestamp;

/**
 * The persistent class for the TOKENS_ACTIVOS database table.
 *
 */
@Entity
@Table(name="TOKENS_ACTIVOS")
@NamedQueries({
	@NamedQuery(name = "TokensActivos.findAll", query = "SELECT p FROM TokensActivos p"),
	@NamedQuery(name = "TokensActivos.borrarVencidos", query = "DELETE FROM TokensActivos t  WHERE t.vencimiento <= :fechaVto")
})
public class TokensActivos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Definicion de atributos de la entidad (Exceptuando relaciones)
	public static final String ENTIDAD_TOKENS_ACTIVOS_PK = "tokenId";
	public static final String ENTIDAD_TOKENS_ACTIVOS_CREACION = "creacion";
	public static final String ENTIDAD_TOKENS_ACTIVOS_VENCIMIENTO = "vencimiento";
	public static final String ENTIDAD_TOKENS_ACTIVOS_TIPO = "tipo";
	public static final String ENTIDAD_TOKENS_ACTIVOS_USUARIO_ID = "usuarioId";
    private static final String[] ATRIBUTOS_ENTIDAD_TOKENS_ACTIVOS
            = {ENTIDAD_TOKENS_ACTIVOS_PK, ENTIDAD_TOKENS_ACTIVOS_TIPO, ENTIDAD_TOKENS_ACTIVOS_USUARIO_ID, ENTIDAD_TOKENS_ACTIVOS_CREACION, ENTIDAD_TOKENS_ACTIVOS_VENCIMIENTO};

	@Id
    @Column(name="TOKEN_ID")
	private String tokenId;

    
	@Column(name="CREACION")
	private Timestamp creacion;		
    
	@Column(name="VENCIMIENTO")
	private Timestamp vencimiento;		
    
	@Column(name="TIPO")
	private BigDecimal tipo;		
    
    @PodamExclude
	@Column(name="USUARIO_ID")
	private BigDecimal usuarioId;		

	@ManyToOne
	@JoinColumn(name="USUARIO_ID", referencedColumnName="USUARIO_ID", insertable = false, updatable = false)
	@PodamExclude
    private Usuario usuarios;
		

	// protected region atributos adicionales on begin
	// Escriba en esta sección sus modificaciones

	// protected region atributos adicionales end
	
    public TokensActivos(){
		// protected region procedimientos adicionales de inicialización on begin
		// Escriba en esta sección sus modificaciones

		// protected region procedimientos adicionales de inicialización end
    }


	public String getTokenId(){
		return this.tokenId;
	}
	
	public void setTokenId(String tokenId){
		this.tokenId = tokenId;
	}
	
	public Timestamp getCreacion(){
		return this.creacion;
	}
	
	public void setCreacion(Timestamp creacion){
		this.creacion = creacion;
	}
		
	public Timestamp getVencimiento(){
		return this.vencimiento;
	}
	
	public void setVencimiento(Timestamp vencimiento){
		this.vencimiento = vencimiento;
	}
		
	public BigDecimal getTipo(){
		return this.tipo;
	}
	
	public void setTipo(BigDecimal tipo){
		this.tipo = tipo;
	}
		
	public BigDecimal getUsuarioId(){
		return this.usuarioId;
	}
	
	public void setUsuarioId(BigDecimal usuarioId){
		this.usuarioId = usuarioId;
	}
		

    public Usuario getUsuarios(){
		return this.usuarios; 
	}
	
	public void setUsuarios(Usuario usuarios){
		this.usuarios = usuarios;
	}

	/**
     * Verifica si la entidad contiene el atributo que se pasa como parámetro
     *
     * @param atributo Nombre del atributo a validar
     * @return Verdadero si la entidad contiene al atributo.
     */
    public static boolean contieneAtributo(String atributo) {
		
		boolean contiene = false;
        for (final String atr : ATRIBUTOS_ENTIDAD_TOKENS_ACTIVOS) {
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
		TokensActivos other = (TokensActivos) obj;
		if (creacion == null) {
			if (other.creacion != null)
				return false;
		} else if (!creacion.equals(other.creacion))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (tokenId == null) {
			if (other.tokenId != null)
				return false;
		} else if (!tokenId.equals(other.tokenId))
			return false;
		if (usuarioId == null) {
			if (other.usuarioId != null)
				return false;
		} else if (!usuarioId.equals(other.usuarioId))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		if (vencimiento == null) {
			if (other.vencimiento != null)
				return false;
		} else if (!vencimiento.equals(other.vencimiento))
			return false;
		return true;
	}

}
