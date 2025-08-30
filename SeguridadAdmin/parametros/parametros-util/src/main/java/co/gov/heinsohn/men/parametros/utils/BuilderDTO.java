package co.gov.heinsohn.men.parametros.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.entities.Parametro;

/**
 * Clase encargada de realizar las conversion de un DTO a una Entidad o viceversa
 *
 * @author Jhonnatan Orozco Duque <jorozco@heinsohn.com.co>
 *
 */
public class BuilderDTO {

	private BuilderDTO(){
		// Auto-generated method stub
	}
	// log de la aplicacion para la clase BuilderDTO

	private static final Logger LOG = Logger.getLogger(BuilderDTO.class.getName());

	/**
	 * metodo encargado de convertir una entidad Parametro en un ParametroDTO
	 *
	 * @param parametro
	 *            entidad de entrada
	 * @return ParametroDTO
	 */
	public static ParametroDTO toParametroDTO(Parametro parametro) {
		LOG.info("Inicio toParametroDTO con la entidad Parametro: " + parametro);
		ParametroDTO parametroDTO = new ParametroDTO();
		if (parametro != null) {
			parametroDTO.setId(parametro.getId());
			parametroDTO.setAplicacion(toString(parametro.getAplicacion()));
			parametroDTO.setCodigoPadre(toString(parametro.getCodigoPadre()));
			parametroDTO.setCodigo(toString(parametro.getCodigo()));
			parametroDTO.setDominioPadre(toString(parametro.getDominioPadre()));
			parametroDTO.setDominio(toString(parametro.getDominio()));
			parametroDTO.setNombre(toString(parametro.getNombre()));
			parametroDTO.setEstado(parametro.getEstado());
			parametroDTO.setModificable(toString(parametro.getModificable()));
			parametroDTO.setTipoParametro(toString(parametro.getTipoParametro()));
		}
		LOG.info("Fin toParametroDTO con ParametroDTO: " + parametroDTO);
		return parametroDTO;
	}

	private static String toString(Object valor){
		return valor != null ? valor.toString() : "";
	}

	/**
	 * metodo encargado de convertir un ParametroDTO Parametro en una entidad
	 *
	 * @param parametroDTO
	 *            DTO de entrada
	 * @return Parametro
	 */
	public static Parametro toParametro(ParametroDTO parametroDTO) {
		LOG.info("Inicio toParametro con ParametroDTO: " + parametroDTO);
		Parametro param = new Parametro();
		if (parametroDTO != null) {
			param.setId(parametroDTO.getId());
			param.setAplicacion(toString(parametroDTO.getAplicacion()));
			param.setCodigo(toString(parametroDTO.getCodigo()));
			param.setCodigoPadre(toString(parametroDTO.getCodigoPadre()));
			param.setDominio(toString(parametroDTO.getDominio()));
			param.setDominioPadre(toString(parametroDTO.getDominioPadre()));
			param.setNombre(toString(parametroDTO.getNombre()));
			param.setEstado(parametroDTO.getEstado());
			param.setTipoParametro(toString(parametroDTO.getTipoParametro()));
			param.setModificable(toString(parametroDTO.getModificable()));
		}
		LOG.info("Fin toParametro con Parametro: " + param);
		return param;
	}

	/**
	 * Convierte las Entidades en DTO
	 * 
	 * @param parametros
	 * @return List<ParametroDTO>
	 */
	public static List<ParametroDTO> toParametroDTOList(List<Parametro> parametros) {
		LOG.info("Inicio toParametroDTOList con el listado de Parametro");
		List<ParametroDTO> parametroDTO = new ArrayList<>();
		if (parametros != null) {
			/*
			 * se recorre la lista de entrada y cada parametro se convierte a
			 * DTO
			 */
			for (Parametro param : parametros) {
				// agrega cada Parametro convertido a la lista de DTOs
				parametroDTO.add(toParametroDTO(param));
			}
		}
		LOG.info("Fin toParametroDTOList");
		return parametroDTO;
	}

}
