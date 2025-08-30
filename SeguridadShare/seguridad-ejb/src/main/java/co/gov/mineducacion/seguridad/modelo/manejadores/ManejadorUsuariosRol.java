package co.gov.mineducacion.seguridad.modelo.manejadores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRol;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuariosRolPK;
import co.gov.mineducacion.seguridad.modelo.manejadores.utils.ManejadorCrud;

/**
 * Manejador que define las operaciones CRUD y de negocio a realizar sobre la
 * tabla correspondiente a la entidad UsuariosRol.
 * 
 * @author jsoto
 */
@Stateless
public class ManejadorUsuariosRol extends ManejadorCrud<UsuariosRol, UsuariosRolPK> {

	public ManejadorUsuariosRol() {
		super(UsuariosRol.class);
	}

	public List<UsuariosRol> buscarUsuariosRolXUsuarioApp(String usuarioId, BigDecimal clientID) {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(usuarioId);
		parameters.add(clientID);
		parameters.add(Constantes.ESTADO_ACTIVO_S);
    	parameters.add(Constantes.ESTADO_ACTIVO_S);
    	parameters.add(Constantes.ESTADO_VINCULADO);
		return (List<UsuariosRol>) createNamedQuery(parameters, "UsuariosRol.findRolUserByUserApp");
	}

	//buscar usuario desvinculado
	public List<UsuariosRol> buscarUsuariosRolXUsuarioAppD(String usuarioId, BigDecimal clientID) {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(usuarioId);
		parameters.add(clientID);
		parameters.add(Constantes.ESTADO_ACTIVO_S);
		parameters.add(Constantes.ESTADO_ACTIVO_S);
		parameters.add(Constantes.ESTADO_DESVINCULADO);
		return createNamedQuery(parameters, "UsuariosRol.findRolUserByUserApp");
	}

	public List<UsuariosRol> buscarUsuariosRolXUsuario(String usuarioId) {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(usuarioId);
		return (List<UsuariosRol>) createNamedQuery(parameters, "UsuariosRol.findRolUserByUser");
	}
	// protected region Use esta region para su implementacion del manejador on
	// begin

	// Inicio metodos HBT
	/**
	 * Metodo que busca usuariosRol para un rol dado
	 * 
	 * @param rolId
	 * @return
	 */
	public List<UsuariosRol> buscarUsuariosRolXRol(BigDecimal rolId) {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(rolId);
		return (List<UsuariosRol>) createNamedQuery(parameters, "UsuariosRol.findRolUserByRol");
	}

	// Fin metodos HBT

	// protected region Use esta region para su implementacion del manejador end
}