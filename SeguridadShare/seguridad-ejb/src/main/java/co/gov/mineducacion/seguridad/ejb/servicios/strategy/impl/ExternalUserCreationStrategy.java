package co.gov.mineducacion.seguridad.ejb.servicios.strategy.impl;

import co.gov.mineducacion.seguridad.ejb.servicios.strategy.UserCreationStrategy;
import co.gov.mineducacion.seguridad.modelo.dtos.UserDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.InformacionAdicionalUsuario;
import co.gov.mineducacion.seguridad.modelo.entidades.Usuario;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.manejadores.ManejadorUsuarios;
import co.gov.mineducacion.seguridad.modelo.utils.ValidatorUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.sql.Timestamp;

import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ESTADO_ACTIVO_S;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.ID_TIPO_USUARIO_EXTERNO;
import static co.gov.mineducacion.seguridad.modelo.utils.Constantes.SI;

@Stateless
@Transactional
public class ExternalUserCreationStrategy implements UserCreationStrategy {

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
                .tipo(ID_TIPO_USUARIO_EXTERNO)
                .estado(ESTADO_ACTIVO_S)
                .fechaCreacion(now)
                .ultimaModificacion(now)
                .logonName(userDTO.getLogonName())
                .build();


        InformacionAdicionalUsuario informacionAdicionalUsuario = InformacionAdicionalUsuario
                .builder()
                .identificationNumber(userDTO.getIdentificationNumber())
                .firstName(userDTO.getFirstName())
                .secondName(userDTO.getSecondName())
                .lastName(userDTO.getLastName())
                .secondLastName(userDTO.getSecondLastName())
                .email(userDTO.getEmail())
                .usuario(usuario)
                .build();

        usuario.setInformacionAdicional(informacionAdicionalUsuario);

        manejadorUsuarios.crear(usuario);
        return usuario;
    }
}
