package co.gov.mineducacion.seguridad.ejb.servicios.strategy;

import co.gov.mineducacion.seguridad.modelo.dtos.UserDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;

public interface UserCreationStrategy {

    Usuario crearUsuario(UserDTO userDTO) throws SIA3Exception;
}
