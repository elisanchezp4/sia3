package co.gov.mineducacion.seguridad.modelo.utils;

import co.gov.mineducacion.seguridad.ejb.servicios.ServicioLDAP;
import co.gov.mineducacion.seguridad.modelo.dtos.UsuariosDTO;
import co.gov.mineducacion.seguridad.modelo.entidades.UsuarioLdap;
import co.gov.mineducacion.seguridad.modelo.excepciones.SeguridadException;
import co.gov.mineducacion.seguridad.modelo.servlet.InitParamsServlet;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.util.Properties;

import static co.gov.mineducacion.seguridad.ejb.servicios.ServiciosCommons.procesarError;

/**
 * Clase que contiene métodos estáticos utilitarios para el envío de correo electrónico
 *
 * @author hfabra
 */
public class UtilEmail {
    private UtilEmail(){/* Recomendacion por sonar */}
    private static final Logger logger = Logger.getLogger(UtilEmail.class);
    static final String MESAGGE = "Error enviando email: ";
    static final String DEFAULT_SUBJECT = "Actualización de datos exitosa";
    static final String DEFAULT_CONTENT = "Los datos se actualizaron correctamente usando la aplicación ${nombre_app} - SIA3.";
    static final String START_TD_JUSTIFY = "<tr><td colspan=\"3\" style=\"text-align: justify;\">";
    static final String START_TD_EMPTY = "<tr><td colspan=\"3\">&nbsp;</td></tr>";
    static final String END_TD_TR = "</td></tr>";

    /**
     * Envía correo electrónico a un usuario para establecer o restablecer la contraseña
     *
     * @param usuariosDTO  contiene información del usuario al cual se le envía el correo (como nombres, apellidos y buzón de correo)
     * @param randomString Url que permite al usuario restablecer o establecer la contraseña y que viaja dentro del correo
     * @param nuevo        verdadero indica que el usuario va a establecer la contraseña por primera vez, falso indica que el usuario va a
     *                     cambiar su contraseña.
     * @throws SeguridadException
     */
    public static void enviarPasswordEmail(UsuariosDTO usuariosDTO, String randomString, boolean nuevo, Properties propiedades) throws SeguridadException {
        logger.info("Inicia proceso para enviar email: " + new Gson().toJson(usuariosDTO) + ", parametros: " + new Gson().toJson(propiedades));
        try {
            String subject = "Recordatorio de usuario y contraseña usuario";
            String body = armarMensaje(usuariosDTO.getNombres() + " " + usuariosDTO.getApellidosUsuario(), randomString, nuevo);
            send(usuariosDTO, subject, body,  propiedades);
        } catch (Exception e) {
            logger.error(MESAGGE + new Gson().toJson(e));
            throw new SeguridadException(SeguridadException.ERROR_ENVIANDO_EMAIL);
        }
    }

    /**
     * Crea el cuerpo del mensaje a enviar en el correo
     *
     * @param nombreUsuario
     * @param password
     * @param nuevo
     * @return
     */
    private static String armarMensaje(String nombreUsuario, String password, boolean nuevo) {
        StringBuilder msj = new StringBuilder();
        msj.append(getHeader(nombreUsuario));

        msj.append(START_TD_JUSTIFY);
        if (nuevo) {
            msj.append("Le informamos que puede ingresar al siguiente enlace para establecer su contraseña: ");
        } else {
            msj.append("Le informamos que puede ingresar al siguiente enlace para restablecer su contraseña: ");
        }
        msj.append(END_TD_TR);
        msj.append(START_TD_EMPTY);
        msj.append(START_TD_JUSTIFY);
        msj.append("<a href=\"").append(password).append("\">").append(password).append("</a>");
        msj.append(END_TD_TR);

        msj.append(getFooter());
        return msj.toString();
    }

    /**
     * Envía correo electrónico a un usuario para notificar actualizacion de datos
     *
     * @param notificarUsuario  Boolean que indica si se envia o no correo de notifiacion al usuario (true-false)
     * @param usuariosDTO       contiene información del usuario al cual se le envía el correo (como nombres, apellidos y buzón de correo)
     * @param nombreAplicacion  Nombre de aplicacion
     * @param properties        Objeto que contiene las propiedades para el envio de correo (correoOrigen, host, protocolo...)
     * @throws SeguridadException
     */
    public static void enviarEmail(boolean notificarUsuario, UsuariosDTO usuariosDTO, String nombreAplicacion, Properties properties) throws SeguridadException {
        if (notificarUsuario){
            logger.info("Inicia proceso para enviar email: " + new Gson().toJson(usuariosDTO) + ", parametros: " + new Gson().toJson(properties));
            try {
                UsuarioLdap userLdap = ServicioLDAP.buscarUsuarioPorLogin(usuariosDTO.getLogonName(), usuariosDTO.getRuta(), properties);
                if (userLdap == null) {
                    throw new SeguridadException("El usuario no existe en el sistema o no está activo.", TipoExcepcion.ERROR);
                }
                usuariosDTO.setEmailUsuario(userLdap.getMail());
                usuariosDTO.setNombres(userLdap.getDisplayName());

                Properties messages = UtilProperties.loadProperties(InitParamsServlet.getRutaArchivoMensajes());
                String subject = messages.getProperty("subject") == null ? DEFAULT_SUBJECT : messages.getProperty("subject");
                String content = messages.getProperty("content") == null ? DEFAULT_CONTENT : messages.getProperty("content");
                content = content.replace("${nombre_app}", nombreAplicacion);

                send(usuariosDTO,  subject, armarMensaje(usuariosDTO.getNombres(), content),  properties);
            } catch (Exception e) {
                logger.error(MESAGGE + new Gson().toJson(e));
                throw new SeguridadException(SeguridadException.ERROR_ENVIANDO_EMAIL);
            }
        }
    }

    /**
     * Crea el cuerpo del mensaje a enviar en el correo con mensaje basico
     *
     * @param nombreUsuario Nombre del usuario al que se le enviará el correo
     * @param content       Mensaje del correo
     * @return
     */
    private static String armarMensaje(String nombreUsuario, String content) {
        StringBuilder msj = new StringBuilder();
        msj.append(getHeader(nombreUsuario));
        msj.append(START_TD_JUSTIFY);
        msj.append(content);
        msj.append(END_TD_TR);
        msj.append(getFooter());
        return msj.toString();
    }

    /**
     * Construye el correo y lo envia
     *
     */
    private static void send(UsuariosDTO usuariosDTO, String subject, String body, Properties properties) throws SeguridadException {
        try {
            if (usuariosDTO.getEmailUsuario() == null) {
                logger.info("Usuario sin email en directorio activo------>");
                procesarError(new SeguridadException(SeguridadException.USUARIO_SIN_EMAIL, TipoExcepcion.ALERTA));
            }

            Session session = Session.getDefaultInstance(armarProperties(properties), null);

            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html");
            mimeMultipart.addBodyPart(bodyPart);

            InputStream inputStream = UtilEmail.class.getResourceAsStream("/images/channels-977_logo.png");
            ByteArrayDataSource bads = null;
            if (inputStream != null) {
                bads = new ByteArrayDataSource(inputStream, "image/png");
                MimeBodyPart bodyPartImg = new MimeBodyPart();
                bodyPartImg.setDataHandler(new DataHandler(bads));
                bodyPartImg.setHeader("Content-ID", "<image>");
                bodyPartImg.setFileName("channels-977_logo.png");
                mimeMultipart.addBodyPart(bodyPartImg);
            }

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("user")));
            message.addRecipients(Message.RecipientType.TO, usuariosDTO.getEmailUsuario());
            message.setSubject(subject);
            message.setContent(mimeMultipart);

            Transport transport = session.getTransport(properties.getProperty("protocol"));
            transport.connect(properties.getProperty("host"), properties.getProperty("user"), properties.getProperty("pwd"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            logger.error(MESAGGE + new Gson().toJson(e));
            throw new SeguridadException(SeguridadException.ERROR_ENVIANDO_EMAIL);
        }
    }

    /**
     * Establece las propiedades del servicio de correos
     *
     * @return
     */
    private static Properties armarProperties(Properties propiedades) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", propiedades.getProperty("enable"));
        props.put("mail.smtp.host", propiedades.getProperty("host"));
        props.put("mail.smtp.user", propiedades.getProperty("user"));
        props.put("mail.smtp.password", propiedades.getProperty("pwd"));
        props.put("mail.smtp.port", propiedades.getProperty("port"));
        props.put("mail.smtp.auth", propiedades.getProperty("auth"));
        props.put("mail.smtp.secure", propiedades.getProperty("secure"));
        props.put("mail.smtp.ssl.trust", propiedades.getProperty("host"));
        logger.info("Arma propiedades: " + new Gson().toJson(props));
        return props;
    }

    /**
     * Crea el header del cuerpo del mensaje a enviar en el correo
     *
     * @param nombreUsuario
     * @return
     */
    private static String getHeader(String nombreUsuario) {
        StringBuilder sbHeader = new StringBuilder();
        sbHeader.append("<body><table align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" ");
        sbHeader.append("style=\"width: 100%;\"><tbody><tr>");
        sbHeader.append("<td colspan=\"3\" style=\"text-align: center;\"><img alt=\"\" ");
        sbHeader.append("src=\"cid:image\" ");
        sbHeader.append("style=\"height: 95px;\" /></td>");
        sbHeader.append("</tr><tr><td colspan=\"3\" style=\"text-align: center;\"><strong>");
        sbHeader.append("<span style=\"text-align: center;\">SEGURIDAD<br />NOTIFICACIONES<br />");
        sbHeader.append("MINISTERIO DE EDUCACI&Oacute;N NACIONAL</span></strong></td></tr>");
        sbHeader.append("<tr><td colspan=\"3\">&nbsp;</td></tr><tr><td colspan=\"3\">");
        sbHeader.append("Estimado usuario ");
        sbHeader.append(nombreUsuario);
        sbHeader.append("</strong></td>");
        sbHeader.append(START_TD_EMPTY);
        return sbHeader.toString();
    }

    /**
     * Crea el footer del cuerpo del mensaje a enviar en el correo
     *
     * @return
     */
    private static String getFooter() {
        StringBuilder sbFooter = new StringBuilder();
        sbFooter.append(START_TD_EMPTY);
        sbFooter.append("<tr><td colspan=\"3\">");
        sbFooter.append("Este correo es solamente informativo por favor no lo responda.");
        sbFooter.append(END_TD_TR);
        sbFooter.append(START_TD_EMPTY);
        sbFooter.append("<tr><td colspan=\"3\">");
        sbFooter.append("Gracias.");
        sbFooter.append(END_TD_TR);
        sbFooter.append("</tbody></table></body></html>");
        return sbFooter.toString();
    }
}
