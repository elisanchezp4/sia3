package co.gov.mineducacion.seguridad.ejb.servicios.strategy.impl;

import co.gov.mineducacion.seguridad.ejb.servicios.strategy.UserCreationStrategy;
import co.gov.mineducacion.seguridad.modelo.dtos.UserDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.InformacionAdicionalUsuario;
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
public class ExternalUserCreationStrategy implements UserCreationStrategy {

    @EJB
    private ManejadorUsuarios manejadorUsuarios;

    @Override
    public Usuario crearUsuario(UserDTO userDTO) throws SIA3Exception {


        Usuario usuario = new Usuario();

        // Copiar propiedades comunes
        usuario.setLogonName(userDTO.getLogonName());
        usuario.setRuta(userDTO.getRoute());
        usuario.setEstado(Constantes.ESTADO_ACTIVO_S);
        usuario.setTipo(Constantes.ID_TIPO_USUARIO_EXTERNO);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        usuario.setFechaCreacion(now);
        usuario.setUltimaModificacion(now);

        // Crear y vincular la información adicional del usuario
        InformacionAdicionalUsuario infoAdicional = new InformacionAdicionalUsuario();
        infoAdicional.setIdentificationNumber(userDTO.getIdentificationNumber());
        infoAdicional.setFirstName(userDTO.getFirstName());
        infoAdicional.setLastName(userDTO.getLastName());
        infoAdicional.setEmail(userDTO.getEmail());
        // ... setear otras propiedades de InformacionAdicionalUsuario

        // Establecer la relación bidireccional
        usuario.setInformacionAdicional(infoAdicional);
        infoAdicional.setUsuario(usuario);

        // Persistir el usuario (debido a CascadeType.ALL, esto también persiste la infoAdicional)
        manejadorUsuarios.crear(usuario);

        // Lógica adicional, como la asignación de roles predeterminados para usuarios externos

        return usuario;
    }
}
