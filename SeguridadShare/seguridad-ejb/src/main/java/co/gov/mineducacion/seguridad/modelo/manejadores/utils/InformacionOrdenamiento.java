package co.gov.mineducacion.seguridad.modelo.manejadores.utils;

import co.gov.mineducacion.seguridad.modelo.enums.TipoOrdenamiento;

public class InformacionOrdenamiento {	
	
	public final TipoOrdenamiento tipo;
	public final String campo;
	
	public InformacionOrdenamiento(TipoOrdenamiento tipo, String campo) {
		this.tipo = tipo;
		this.campo = campo;
	}        

}
