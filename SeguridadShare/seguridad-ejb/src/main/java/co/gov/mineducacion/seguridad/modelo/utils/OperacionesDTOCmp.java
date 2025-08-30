package co.gov.mineducacion.seguridad.modelo.utils;

import java.math.BigDecimal;
import java.util.Comparator;

import co.gov.mineducacion.seguridad.modelo.dtos.OperacionesDTO;

/**
 * Clase que permite comparar dos operaciones segun su orden de visualizacion, utilizada
 * para ordenar listados de operaciones
 * @author Asesoftware - Javier Estevez
 *
 */
public class OperacionesDTOCmp implements Comparator<OperacionesDTO>{
	
	/**
	 * obtiene el campo ordenVisualizacion de cada parametro y retorna la comparacion entre estos dos campos,
	 * si los campos ordenVisualizacion son vacios se retorna la comparacion entre los campos opcionId de cad 
	 * objeto.
	 */
	@Override
	public int compare(OperacionesDTO op1, OperacionesDTO op2) {
		
		BigDecimal orden1;
		BigDecimal orden2;
		if(op1.getOrdenVisualizacion() == null &&
				op2.getOrdenVisualizacion() == null){
			orden1 = op1.getOpcionId();
			orden2 = op2.getOpcionId();
		} else {
			orden1 = op1.getOrdenVisualizacion() == null?BigDecimal.ZERO:op1.getOrdenVisualizacion();
			orden2 = op2.getOrdenVisualizacion() == null?BigDecimal.ZERO:op2.getOrdenVisualizacion();
		}
		
		return orden1.compareTo(orden2);
	}  
}
