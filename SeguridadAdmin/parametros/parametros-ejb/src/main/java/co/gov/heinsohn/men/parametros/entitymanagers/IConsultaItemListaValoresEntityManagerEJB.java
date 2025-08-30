package co.gov.heinsohn.men.parametros.entitymanagers;

import java.util.List;

import javax.ejb.Local;

import co.gov.heinsohn.men.parametros.entities.Parametro;

/**
 * Interfaz de los metodos para parametrizar los Parametros
 * 
 * @author Jhonnatan Orozco Duque <jorozco@heinsohn.com.co>
 *
 */
@Local
public interface IConsultaItemListaValoresEntityManagerEJB {

	/**
	 * permite obtener un listado de Valores
	 * 
	 * @param nombreApp
	 *            dominio
	 * 
	 * @return List<Parametro>
	 */
	public List<Parametro> consultaListaValoresSimple(String nombreApp, String dominio);

	/**
	 * permite obtener un listado de Parametros
	 * 
	 * @param nombreApp
	 *            dominio
	 * @return Parametro
	 */
	public Parametro consultarParametroSimple(String nombreApp, String dominio);

	/**
	 * permite obtener un listado de Valores multiples
	 * 
	 * @param nombreApp
	 *            dominio dominioPadre codigoPadre
	 * @return List<Parametro>
	 */
	public List<Parametro> consultarListaValoresMultiple(String nombreApp, String dominio, String dominioPadre,
			String codigoPadre);

}
