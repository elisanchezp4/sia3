package co.gov.mineducacion.seguridad.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(value = "/*", initParams =
@WebInitParam(name = CharacterEncodingFilter.ENCODING_INIT_PARAM,
        value = CharacterEncodingFilter.DEFAULT_ENCODING))
public class CharacterEncodingFilter implements Filter{

    public static final String ENCODING_INIT_PARAM = "encoding";

    public static final String DEFAULT_ENCODING = "UTF-8";

    private String encoding = DEFAULT_ENCODING;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //No se debe hacer nada al destruir
    }


    @Override
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter(ENCODING_INIT_PARAM);
    }
}
