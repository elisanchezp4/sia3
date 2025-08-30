package co.gov.heinsohn.men.parametros.facades;

import java.util.List;

import javax.inject.Inject;

import co.gov.heinsohn.men.parametros.business.IAdministrarParametroBusinessEJB;
import co.gov.heinsohn.men.parametros.dto.ParametroDTO;
import co.gov.heinsohn.men.parametros.utils.exception.PARAMETROSException;

public class AdministrarParametroFacadeEJB implements IAdministrarParametroFacadeEJB {

	@Inject
	private IAdministrarParametroBusinessEJB administrarParametroBusinessEJB;

	@Override
	public List<ParametroDTO> consultaParemetrosFiltros(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException {

		return administrarParametroBusinessEJB.consultaParemetrosFiltros(tipoParametro, nombreApp, dominio, estado);
	}

	@Override
	public List<ParametroDTO> consultaParemetrosNoModificables(String tipoParametro, String nombreApp, String dominio,
			String estado) throws PARAMETROSException {

		return administrarParametroBusinessEJB.consultaParemetrosNoModificables(tipoParametro, nombreApp, dominio,
				estado);
	}

	@Override
	public void adicionarParametro(ParametroDTO parametroDTO) throws PARAMETROSException {

		administrarParametroBusinessEJB.adicionarParametro(parametroDTO);

	}

	@Override
	public void editarParametro(ParametroDTO parametroDTO) throws PARAMETROSException {
		administrarParametroBusinessEJB.editarParametro(parametroDTO);

	}

	@Override
	public void adicionarDependiente(ParametroDTO parametroDTO) throws PARAMETROSException {
		// adiciona Dependiente

	}

	@Override
	public List<ParametroDTO> consultaParemetrosFiltrosDiferentes(String tipoParametro, String nombreApp,
			String dominio, String estado) throws PARAMETROSException {

		return administrarParametroBusinessEJB.consultaParemetrosFiltrosDiferentes(tipoParametro, nombreApp, dominio,
				estado);
	}

	@Override
	public List<ParametroDTO> consultaListasDominio(String nombreApp, String tipoParametro) throws PARAMETROSException {

		return administrarParametroBusinessEJB.consultaListasDominio(nombreApp, tipoParametro);
	}

	@Override
	public List<ParametroDTO> consultaListasTipoParametro(String nombreApp) throws PARAMETROSException {

		return administrarParametroBusinessEJB.consultaListasTipoParametro(nombreApp);
	}

	@Override
	public List<ParametroDTO> consultaListasAplicacion() throws PARAMETROSException {

		return administrarParametroBusinessEJB.consultaListasAplicacion();
	}
	
	@Override
	public List<ParametroDTO> consultaListasDepartamentos() throws PARAMETROSException {

		return administrarParametroBusinessEJB.consultaListasDepartamentos();
	}

}
