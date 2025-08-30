package co.gov.mineducacion.seguridad.modelo.dtos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PropiedadesAccesibilidadDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String valor;
	private String nombre;

	public PropiedadesAccesibilidadDTO(){/* Recomendacion por sonar */}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
