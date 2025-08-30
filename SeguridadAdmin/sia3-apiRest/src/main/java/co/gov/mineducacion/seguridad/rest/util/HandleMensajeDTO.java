package co.gov.mineducacion.seguridad.rest.util;

import co.gov.mineducacion.seguridad.modelo.dtos.MensajeDTO;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.utils.MessagesConstants;
import co.gov.mineducacion.seguridad.negocio.NegocioMensaje;

public class HandleMensajeDTO {

    private HandleMensajeDTO(){
        //utility class
    }
    public static MensajeDTO obtenerMensajeDTOFromException(NegocioMensaje negocioMensaje, Exception e) {
        MensajeDTO mensajeDTO = new MensajeDTO();
        mensajeDTO.setCodigo("APP000003");
        mensajeDTO.setTipoMensaje(MessagesConstants.TIPO_MENSAJE_ERROR);
        mensajeDTO.setDescripcion(MessagesConstants.APP000003);

        if (e instanceof SIA3Exception) {
            try {
                mensajeDTO = negocioMensaje.mensajePorCodigo(e.getMessage());
            } catch (SIA3Exception se1) {
                // Si no encuentra mensaje devolver mensaje error generico
            }
        }

        return mensajeDTO;
    }
}
