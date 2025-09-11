package co.gov.mineducacion.seguridad.modelo.utils;

import co.gov.mineducacion.seguridad.modelo.dtos.UserDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;

@Stateless
@Slf4j
public class ValidatorUser {

    @EJB
    private ManejadorUsuarios manejadorUsuarios;
    public void validateCommonFields(UserDTO userDTO) throws SIA3Exception {

        if (userDTO.getStatus() == null || userDTO.getStatus().equals(new BigDecimal(0))) {
            log.error("Error en metodo validarUsuarios: Campo estado no puede ser nulo o cero.");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }

        if (userDTO.getLogonName() == null || userDTO.getLogonName().trim().isEmpty()) {
            log.error("Error en metodo validarUsuarios: Campo logonName no puede ser vacio o nulo.");
            throw new SIA3Exception(MessagesConstants.APP100013);
        }

        if (userDTO.getRoute() == null || userDTO.getRoute().trim().isEmpty()) {
            log.error("Error en metodo validarUsuarios: Campo ruta no puede ser vacio o nulo.");
            throw new SIA3Exception(MessagesConstants.APP100013);
        }

        if (userDTO.getUserType() == null || userDTO.getUserType().equals(new BigDecimal(0))) {
            log.error("Error en metodo validarUsuarios: Campo tipo de usuario no puede ser nulo o cero.");
            throw new SIA3Exception(MessagesConstants.APP100014);
        }

        List<Usuario> existingUsers = manejadorUsuarios.listarUsuariosPorNombre(userDTO.getLogonName());
        if (existingUsers != null && !existingUsers.isEmpty()) {
            log.error("Error en metodo validarUsuarios: Ya existe usuario con el nombre:" + userDTO.getLogonName());
            throw new SIA3Exception(MessagesConstants.APP100096);
        }
    }
}
