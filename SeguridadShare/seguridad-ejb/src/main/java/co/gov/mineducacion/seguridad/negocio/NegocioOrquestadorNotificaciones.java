package co.gov.mineducacion.seguridad.negocio;

import co.gov.mineducacion.seguridad.modelo.dtos.AplicacionesDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.BitacoraAuditoriaDTO;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.Aplicaciones;
import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.rabbitmq.Send;
import co.gov.mineducacion.seguridad.modelo.utils.BuilderDTO;
import co.gov.mineducacion.seguridad.modelo.utils.Constantes;
import co.gov.mineducacion.seguridad.modelo.utils.ParametrosSng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

@Stateless
public class NegocioOrquestadorNotificaciones {
    private static final Logger logger = Logger.getLogger(NegocioOrquestadorNotificaciones.class.getName());
    @EJB
    protected NegocioAplicaciones negocioAplicacion;
    @EJB
    protected ParametrosSng parametrosSng;
    @EJB
    private NegocioUsuarios negocioUsuario;

    public void orquestarNotificacionesAuditoriaPorAplicaciones(BitacoraAuditoriaDTO auditoriaDTO) {
        logger.info("Inicia orquestador notificaciones auditoria por aplicaciones: " + new Gson().toJson(auditoriaDTO));
        if (auditoriaDTO.getAplicacionId() != null &&
                (Objects.equals(auditoriaDTO.getTipoEvento(), new BigDecimal(42)) ||
                Objects.equals(auditoriaDTO.getTipoEvento(), new BigDecimal(67)) ||
                Objects.equals(auditoriaDTO.getTipoEvento(), new BigDecimal(70)) ||
                Objects.equals(auditoriaDTO.getTipoEvento(), new BigDecimal(68)) ||
                Objects.equals(auditoriaDTO.getTipoEvento(), new BigDecimal(69)) ||
                Objects.equals(auditoriaDTO.getTipoEvento(), new BigDecimal(62)) )) {
            try {
                List<AplicacionesDTO> aplicacionesList = negocioAplicacion.getAllAplicacionesNotificaciones();

                for (AplicacionesDTO appDto : aplicacionesList) {
                    Aplicaciones aplicacion = BuilderDTO.toAplicacion(appDto);
                    auditoriaDTO.setAplicacionId(aplicacion.getAplicacionId());
                    logger.info("Se realiza consulta de aplicacion desde orquestador de cola: " + new Gson().toJson(aplicacion));
                        armarNotificacion(auditoriaDTO);
                }
            } catch (Exception e) {
                logger.error("Error consultando aplicacion en auditoria: " + e.getMessage());
            }
        } else {
            logger.info("No se puede enviar cola de auditoria por que la aplicacion es nula o el evento no genera cola en RabbitMQ: " + new Gson().toJson(auditoriaDTO));
        }
        logger.info("Finaliza orquestador de notificaciones...");
    }

    private void armarNotificacion(BitacoraAuditoriaDTO auditoriaDTO){
        try {
            Properties properties = parametrosSng.obtenerParametros();
            String cola = Constantes.QUEUE_APPLICATIONS + auditoriaDTO.getAplicacionId() + Constantes.QUEUE_APPLICATIONS_MESSAGE;
            Send.enviarMensajeEnCola(properties, cola, toStringJson(auditoriaDTO));
        } catch (IOException | TimeoutException | SIA3Exception e) {
            logger.error(new Throwable("error enviando mensaje rabbitMqt, error" + e + " data: " + new Gson().toJson(auditoriaDTO)));
            try {
                enviarEmailExcepcion(auditoriaDTO.getAplicacionId().toString());
            } catch (SeguridadException | SIA3Exception ex) {
                logger.error("Error al enviar correo electronico " + ex);
            }
        }
    }

    private String toStringJson(BitacoraAuditoriaDTO auditoriaDTO){
        Gson gson = new Gson();
        String jsonString = gson.toJson(auditoriaDTO);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        try {
            JsonObject jsonDetalle = gson.fromJson(auditoriaDTO.getDetalle(), JsonObject.class);
            jsonObject.remove("detalle");
            jsonObject.add("detalle", jsonDetalle);
        } catch (JsonSyntaxException e){
            logger.warn("Error al obtener json de campo detalle de auditoria, se usará el valor original");
        }
        return jsonObject.toString();
    }

    private void enviarEmailExcepcion(String aplicacion) throws SeguridadException, SIA3Exception {
        logger.info("Inicia metodo para enviar email a todos los los roles administradores");
        Properties properties = parametrosSng.obtenerParametros();
        Date sent = new Date();
        //Buscar Administradores de SIA3
        List<UsuariosDTO> usuarioDTOList = negocioUsuario.getUsuariosPorAppRol(Constantes.ID_SIA3, Constantes.ROL_SIA3_ADMIN_ID);
        logger.info("Listado de administradores: " + usuarioDTOList);
        //Recorro la lista y se envian emails
        for (UsuariosDTO usuariosDTO : usuarioDTOList) {
            if (usuariosDTO.getEmailUsuario() != null || !usuariosDTO.getEmailUsuario().isEmpty()) {
                Properties props = new Properties();
                props.setProperty("mail.smtp.starttls.enable", properties.getProperty("enable"));
                props.setProperty("mail.smtp.host", properties.getProperty("host"));
                props.setProperty("mail.smtp.user", properties.getProperty("user"));
                props.setProperty("mail.smtp.password", properties.getProperty("pwd"));
                props.setProperty("mail.smtp.port", properties.getProperty("port"));
                props.setProperty("mail.smtp.auth", properties.getProperty("auth"));
                props.setProperty("mail.smtp.secure", properties.getProperty("secure"));
                props.setProperty("mail.smtp.ssl.trust", properties.getProperty("host"));

                Session session = Session.getDefaultInstance(props, null);

                MimeMessage message = new MimeMessage(session);
                try {
                    message.setFrom(new InternetAddress(properties.getProperty("user")));
                    message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(usuariosDTO.getEmailUsuario())));
                    message.setSubject("Error en SIA3");
                    Date fechaActual = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    message.setText("Error al notificar a la aplicación cliente " + aplicacion + " los datos de actualización de datos." + "\nFecha: " + dateFormat.format(fechaActual));
                    message.setSentDate(sent);

                    Transport transport = session.getTransport(properties.getProperty("protocol"));
                    transport.connect(properties.getProperty("host"), properties.getProperty("user"), properties.getProperty("pwd"));
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                } catch (AddressException exa) {
                    logger.error("Error al enviar correo electronico, esto es un AddressException " + exa);
                } catch (MessagingException exm) {
                    logger.error("Error al enviar correo electronico, esto es un MessagingException " + exm);
                }
            }else{
                logger.error("El Usuario: " + usuariosDTO.getLogonName() + " no tiene correo electronico");
            }
        }
        logger.info("Finalizacion del metodo: enviarEmailExcepcion()");
    }
}
