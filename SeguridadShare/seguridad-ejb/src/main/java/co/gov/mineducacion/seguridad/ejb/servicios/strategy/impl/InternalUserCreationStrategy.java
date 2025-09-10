package co.gov.mineducacion.seguridad.ejb.servicios.strategy.impl;

import co.gov.mineducacion.seguridad.ejb.servicios.strategy.UserCreationStrategy;
import co.gov.mineducacion.seguridad.modelo.dtos.UserDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@Stateless
@Transactional
public class InternalUserCreationStrategy implements UserCreationStrategy {

    @EJB
    private ManejadorUsuarios manejadorUsuarios;

    @Override
    public Usuario crearUsuario(UserDTO userDTO) throws SIA3Exception {

        Timestamp now = new Timestamp(System.currentTimeMillis());

        Usuario usuario = new Usuario()
                .toBuilder()
                .logonName(userDTO.getLogonName())
                .ruta(userDTO.getRoute())
                .estado(Constantes.ESTADO_ACTIVO_S)
                .tipo(Constantes.ID_TIPO_USUARIO_INTERNO)
                .fechaCreacion(now)
                .ultimaModificacion(now)
                .build();

        // Copiar propiedades comunes

        manejadorUsuarios.crear(usuario);

        // Lógica adicional para usuarios internos si la hubiera

        return usuario;
    }
}
