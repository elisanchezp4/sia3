package co.gov.mineducacion.seguridad.modelo.utils;

/**
 * Esta clase define los tipos de error que se pueden presentar en el aplicativo
 * y el nivel de prioridad de los mismos.
 * 
 * @author Michael Murgueitio
 * @version 1.0
 * @created 11-nov-2016 09:55:22 a.m.
 */
public enum TipoExcepcion {
	FATAL(1), ERROR(2), ALERTA(3), INFO(4);

	private int prioridad;

	TipoExcepcion(int prioridad) {
		this.prioridad = prioridad;
	}

	public int getPrioridad() {
		return prioridad;
	}

}