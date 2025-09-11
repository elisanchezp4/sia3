package co.gov.mineducacion.seguridad.ejb.servicios.strategy.impl;

import co.gov.mineducacion.seguridad.ejb.servicios.strategy.UserCreationStrategy;
import co.gov.mineducacion.seguridad.modelo.dtos.UserDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import co.gov.mineducacion.seguridad.modelo.utils.ValidatorUser;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.sql.Timestamp;

import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ESTADO_ACTIVO_S;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ID_TIPO_USUARIO_INTERNO;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.SI;

@Stateless
@Transactional
public class InternalUserCreationStrategy implements UserCreationStrategy {

    private static final Logger LOG = Logger.getLogger(InternalUserCreationStrategy.class.getName());
    @EJB
    private ManejadorUsuarios manejadorUsuarios;

    @Override
    public Usuario crearUsuario(UserDTO userDTO) throws SIA3Exception {

        new ValidatorUser().validateCommonFields(userDTO);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        Usuario usuario = Usuario
                .builder()
                .ruta(userDTO.getRoute())
                .nuevoPass(SI)
                .tipo(ID_TIPO_USUARIO_INTERNO)
                .estado(ESTADO_ACTIVO_S)
                .fechaCreacion(now)
                .ultimaModificacion(now)
                .logonName(userDTO.getLogonName())
                .build();
        LOG.info("se crear en la entidad");
        manejadorUsuarios.crear(usuario);
        userDTO.setUserId(usuario.getUsuarioId());
        return usuario;
    }
}
