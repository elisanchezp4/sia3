package co.gov.mineducacion.seguridad.modelo.manejadores.utils;

import co.gov.mineducacion.seguridad.modelo.enums.TipoFiltro;
import co.gov.mineducacion.seguridad.modelo.enums.TipoOperador;

/**
 * Clase que representa una condición de filtrado para un consulta JPQL. La condición
 * de define con un campo, un operador de comparación (=,!=,{@literal <}, etc), un valor y un 
 * tipo de operador de concatenación (OR o AND) para unir esta condicion con
 * otra condición.
 * 
 * @author jsoto
 */
public class InformacionFiltro {

	public final String campo;
	public final TipoFiltro tipo;
	public final Object valor;
	public final TipoOperador operador;

        /**
         * Por defecto se selecciona un tipo de operador AND.
         * @param tipo Tipo del filtro
         * @param campo Nombre del campo 
         * @param valor Valor por el cual filtrar el campo
         */
	public InformacionFiltro(TipoFiltro tipo, String campo, Object valor) {
		this.tipo = tipo;
		this.campo = campo;
		this.valor = valor;
		this.operador = TipoOperador.AND;
	}

        /**
         * 
         * @param tipo Tipo del filtro
         * @param campo Nombre del campo
         * @param valor Valor por el cual filtrar el campo
         * @param tipoOperador Operador de concatenación
         */
	public InformacionFiltro(TipoFiltro tipo, String campo, Object valor,
			TipoOperador tipoOperador) {
		this.tipo = tipo;
		this.campo = campo;
		this.valor = valor;
		this.operador = tipoOperador;
	}
        

}