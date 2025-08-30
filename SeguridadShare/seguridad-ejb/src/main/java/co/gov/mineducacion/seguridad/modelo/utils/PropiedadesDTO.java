package co.gov.mineducacion.seguridad.modelo.utils;

import java.io.Serializable;

public class PropiedadesDTO implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String contrasteDefault;
	String tamanioLetra;
	String tiempoMensajes;
	String lenguajeSenias;
	String tecladoVirtual;
	String tiempoReversibilidad;

	public String getContrasteDefault() {
		return contrasteDefault;
	}
	public void setContrasteDefault(String contrasteDefault) {
		this.contrasteDefault = contrasteDefault;
	}
	public String getTamanioLetra() {
		return tamanioLetra;
	}
	public void setTamanioLetra(String tamanioLetra) {
		this.tamanioLetra = tamanioLetra;
	}
	public String getTiempoMensajes() {
		return tiempoMensajes;
	}
	public void setTiempoMensajes(String tiempoMensajes) {
		this.tiempoMensajes = tiempoMensajes;
	}
	public String getLenguajeSenias() {
		return lenguajeSenias;
	}
	public void setLenguajeSenias(String lenguajeSenias) {
		this.lenguajeSenias = lenguajeSenias;
	}
	public String getTecladoVirtual() {
		return tecladoVirtual;
	}
	public void setTecladoVirtual(String tecladoVirtual) {
		this.tecladoVirtual = tecladoVirtual;
	}
	public String getTiempoReversibilidad() {
		return tiempoReversibilidad;
	}
	public void setTiempoReversibilidad(String tiempoReversibilidad) {
		this.tiempoReversibilidad = tiempoReversibilidad;
	}

	
}
