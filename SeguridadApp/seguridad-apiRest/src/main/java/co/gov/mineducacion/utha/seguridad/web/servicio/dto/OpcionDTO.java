package co.gov.mineducacion.utha.seguridad.web.servicio.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa normalmente una opcion de menú (a la cual un usuario tiene acceso) pero también puede representar
 * un componente visual (boton por ejemplo) o un permiso especial (asignado a un usuario).
 * @author Asesoftware - Javier Est�vez
 *
 */
@XmlRootElement
public class OpcionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8493456475796666476L;
	/**
	 * descripción de la opcion de menú, componente visual o permiso especial
	 */
	private String descripcion;
	
	/**
	 * si el objeto representa una opcion de menú en este objeto se encuentran las opciones del sub-menú
	 */
	private List<OpcionDTO> hijosOpcion;
	
	/**
	 * nombre que se visualizará en pantalla
	 */
	private String nombreObjeto;
	
	private Integer opcionId;
	
	private String urlImgGif;
	
	/**
	 * permite identificar si el objeto representa una opción, un componentes visual o un permiso.
	 */
	private String tipo;
	private String nombreRolAsociado;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<OpcionDTO> getHijosOpcion() {
		return hijosOpcion;
	}
	public void setHijosOpcion(List<OpcionDTO> hijosOpcion) {
		this.hijosOpcion = hijosOpcion;
	}
	public String getNombreObjeto() {
		return nombreObjeto;
	}
	public void setNombreObjeto(String nombreObjeto) {
		this.nombreObjeto = nombreObjeto;
	}
	public Integer getOpcionId() {
		return opcionId;
	}
	public void setOpcionId(Integer opcionId) {
		this.opcionId = opcionId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombreRolAsociado() {
		return nombreRolAsociado;
	}
	public void setNombreRolAsociado(String nombreRolAsociado) {
		this.nombreRolAsociado = nombreRolAsociado;
	}
	public String getUrlImgGif() {
		return urlImgGif;
	}
	public void setUrlImgGif(String urlImgGif) {
		this.urlImgGif = urlImgGif;
	}
    
    

}
