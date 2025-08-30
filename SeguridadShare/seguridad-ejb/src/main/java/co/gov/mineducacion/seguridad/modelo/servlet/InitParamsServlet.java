package co.gov.mineducacion.seguridad.modelo.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitParamsServlet extends HttpServlet{

    private static String rutaArchivoMensajes;

    @Override
    public void init(ServletConfig config) throws ServletException {
        setRutaArchivoMensajes(config.getInitParameter("rutaArchivoMensajes"));
    }

    public static String getRutaArchivoMensajes() {
        return rutaArchivoMensajes;
    }

    public static void setRutaArchivoMensajes(String rutaArchivoMensajes) {
        InitParamsServlet.rutaArchivoMensajes = rutaArchivoMensajes;
    }
}
