package co.gov.mineducacion.seguridad.rest.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;




public class DatosIniciaslesFront implements Filter {
    
    
    public static Properties propiedadesFront = null;
    private ServletContext context;
    
    
    
    
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
        String rutaArchivoConfiguracion = filterConfig.getInitParameter("rutaArchivoConfiguracion");
        cargarPropiedades(rutaArchivoConfiguracion);
    }
    
    
    /**
     * Captura las peticiones y valida que el token se reciba y se encuentre activo, si no se recibe o no se encuentra activo
     * se retorna el error 401 a ángular para su gestión.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Auto-generated method stub
    }
    
    
    @Override
    public void destroy() {
        // Auto-generated method stub
    }
    
    private static void cargarPropiedades(String rutaArchivo){
        InputStream is = null;
        try{
            propiedadesFront = new Properties();
            is = new FileInputStream(rutaArchivo);
            propiedadesFront.load(is);
        }catch(IOException e) {
            propiedadesFront = null;
        }finally{
            try{
                if(is != null){
                    is.close();
                }
            }catch(IOException e){
            }
        }
    }
}


