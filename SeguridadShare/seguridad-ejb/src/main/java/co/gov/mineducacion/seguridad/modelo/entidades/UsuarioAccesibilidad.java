package co.gov.mineducacion.seguridad.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO_ACCESIBILIDAD")
@NamedQueries({
	@NamedQuery(name = "UsuarioAccesibilidad.obtenerAccUs", query = "select ua from UsuarioAccesibilidad ua where ua.usuarios.usuarioId = ?1 ")	 
	})
public class UsuarioAccesibilidad implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ")
    @SequenceGenerator(sequenceName = "SEQ_USUARIO_ACCESIBILIDAD", allocationSize = 1, name = "ID_SEQ")
	@Id
	@Column(name = "USUARIOACC_ID")
	private Long usuarioAccId;
	
	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
	private Usuario usuarios;
	
	@ManyToOne
	@JoinColumn(name = "PROPIEDADACC_ID", referencedColumnName = "PROPIEDADACC_ID")
	private PropiedadesAccesibilidad propAcc;
	
	@Column(name = "VALOR_PROPIEDAD")
	private String valorPropiedad;
	
	@Column(name = "ESTADO")
	private Long estado;

	public Long getUsuarioAccId() {
		return usuarioAccId;
	}

	public void setUsuarioAccId(Long usuarioAccId) {
		this.usuarioAccId = usuarioAccId;
	}

	public Usuario getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}

	public PropiedadesAccesibilidad getPropAcc() {
		return propAcc;
	}

	public void setPropAcc(PropiedadesAccesibilidad propAcc) {
		this.propAcc = propAcc;
	}

	public String getValorPropiedad() {
		return valorPropiedad;
	}

	public void setValorPropiedad(String valorPropiedad) {
		this.valorPropiedad = valorPropiedad;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	
}
