package co.gov.mineducacion.seguridad.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Filtro implementado para incluir las cabeceras de seguridad necesarias
 * en las respuestas del sitio web
 * Servlet Filter implementation class SeguridadFilter
 */
@WebFilter("/SeguridadFilter")
public class SeguridadFilter implements Filter {

    
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse)response;
		resp.setHeader("X-Frame-Options", "DENY");	
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Auto-generated method stub
	}

	@Override
	public void destroy() {
		// Auto-generated method stub
	}

	

}
