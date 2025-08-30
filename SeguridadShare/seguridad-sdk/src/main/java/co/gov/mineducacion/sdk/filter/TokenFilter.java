package co.gov.mineducacion.sdk.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.gov.mindecuacion.sdk.servicio.AutorizacionService;
import co.gov.mindecuacion.sdk.servicio.SeguridadException;
import co.gov.mineducacion.sdk.util.CacheTokens;
import co.gov.mineducacion.sdk.util.FiltroSeguridad;

/**
 * Filtro encargado de interceptar todas las peticiones a recursos web utlizados por una aplicación JSF 
 * @author hfabra
 * @since 22/02/2017
 */
public class TokenFilter implements Filter {

	private static final String STR_NULL = "null";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String CODIGO_AUTORIZACION = "codigo_autorizacion";
	private static final String CLIENT_ID = "client_id";
	private static final String USER_ID = "user_id";

	
	private ServletContext context;
	
	private static final Map<String, Date> cacheTokens = CacheTokens.getCacheTokens();
	
	private static Properties propiedades;
	
	private static int segundosDiferenciaCache;
	private static int msDiferenciaCache;
	private static String idAplicacion;
	private static List<String> lstUrlsExcluidas;
	
	public static void configurar(Properties propConf){
		
		propiedades = propConf;
		idAplicacion = propiedades.getProperty("id_aplicacion_en_seguridad");
		try{
			segundosDiferenciaCache = Integer.parseInt(propiedades.getProperty("segundos_diferencia_cache"));
			msDiferenciaCache = segundosDiferenciaCache * 1000;
		}catch(Exception e){
			msDiferenciaCache = 3000;
		}
		
		try {
			
			lstUrlsExcluidas = new ArrayList<>();
			
			String urlsExcluidas = propiedades.getProperty("excluir_urls_seguridad");
			
			if(urlsExcluidas != null && !urlsExcluidas.trim().equals("")){
				
				String[] vct = urlsExcluidas.split(",");
				for(int i=0; i<vct.length; i++){
					
					lstUrlsExcluidas.add(vct[i].trim());
				}
				
			}
			
			
		}catch (Exception e){
			lstUrlsExcluidas = new ArrayList<>();
		}

		
	}
	
  
	public void init(FilterConfig filterConfig) throws ServletException {
		this.context = filterConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
		
		String rutaArchivoConfiguracion = filterConfig.getInitParameter("rutaArchivoConfiguracion");
		
		
		if(rutaArchivoConfiguracion != null && !rutaArchivoConfiguracion.isEmpty()){
			FiltroSeguridad.configurar(rutaArchivoConfiguracion);
		}
	}  
	      
	/**
	 * Captura las peticiones y valida que el token se reciba y se encuentre activo, si no se recibe o no se encuentra activo
	 * se retorna el error 401 a ángular para su gestión.      
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if(!FiltroSeguridad.estaConfigurado()){
			Logger.getLogger(TokenFilter.class.getName()).log(Level.SEVERE, "ERROR:  El SDK no está configurado, Use FiltroSeguridad.configurar(String rutaArchivoConfiguracion)");
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		
		if(TokenFilter.lstUrlsExcluidas != null &&
				TokenFilter.lstUrlsExcluidas.contains(uri)){
			
			chain.doFilter(request, response);
			
			return;
		}

		String token = (String)req.getSession().getAttribute(TokenFilter.ACCESS_TOKEN) == null ? (String)req.getSession().getAttribute(TokenFilter.CODIGO_AUTORIZACION) : (String)req.getSession().getAttribute(TokenFilter.ACCESS_TOKEN);
		String appId = (String)req.getSession().getAttribute(TokenFilter.CLIENT_ID);
		String userId = (String)req.getSession().getAttribute(TokenFilter.USER_ID);
		
		
		// Validar token cache
		if(token != null  && !token.isEmpty() && !token.equals(TokenFilter.STR_NULL)){

			if (cacheTokens.containsKey(token) && (new Date().getTime() < cacheTokens.get(token).getTime() - (TokenFilter.msDiferenciaCache))) {
					chain.doFilter(request, response);
					return;
			}
				
			actualizarFechaVencimiento(token, appId, userId, req, res, chain, request, response);
			
		} else {
			
			String idAppUsar; //identificador de la aplicación a usar en el redirect
			
			if(appId == null  || appId.isEmpty() || appId.equals(TokenFilter.STR_NULL)){
				 
				idAppUsar = TokenFilter.idAplicacion;
				
			} else {
				
				idAppUsar = appId;
			}
			
			
			res.sendRedirect(propiedades.getProperty("urlLogin")+idAppUsar);
			
		}
		
	}

	/**
	 * Retorna el error err recibido por parámetro en la respuesta representada por el objeto res
	 * @param res
	 * @param err
	 */
	private void redireccionaPaginaLogin(HttpServletResponse res, String appId){
		
		try {
			
			res.sendRedirect(propiedades.getProperty("urlLogin")+appId);
			
		} catch (IOException e) {
			
			Logger.getLogger(TokenFilterAngular.class.getName()).log(Level.SEVERE, "IOException: " +e.getMessage(), e);
			
		}
	}
	
	/**
	 * Si aún se encuentra activo el token, se actualiza su fecha de vencimiento en el caché, de lo
	 * contrario se retorna un error 401 para iformar a ángular del error y que redireccione al sistema
	 * de seguridad
	 * @param token
	 * @param appId
	 * @param userId
	 * @param req
	 * @param res
	 * @param chain
	 * @param request
	 * @param response
	 */
	private void actualizarFechaVencimiento(String token, String appId,
			String userId, HttpServletRequest req, HttpServletResponse res,
			FilterChain chain, ServletRequest request, ServletResponse response) {
		
		Date fechaVencimiento;
		
		String idAppUsar;
		
		if(appId == null  || appId.isEmpty() || appId.equals(TokenFilter.STR_NULL)){
			 
			idAppUsar = TokenFilter.idAplicacion;
		} else {
			
			idAppUsar = appId;
		}
		
		try {
			fechaVencimiento = AutorizacionService.actualizarFechaVencimiento(token, appId, new Integer(userId));
			
		
			if (!fechaVencimiento.after(new Date())) {
				
				AutorizacionService.finalizarSesion(token, appId, new Integer(userId));
				
				res.sendRedirect(propiedades.getProperty("urlLogin")+idAppUsar);

			} else {
				cacheTokens.remove(token);
				cacheTokens.put(token, fechaVencimiento);
				chain.doFilter(request, response);
			}
			
		} catch (NumberFormatException e) {
			
			Logger.getLogger(TokenFilterAngular.class.getName()).log(Level.SEVERE, "NumberFormatException: " +e.getMessage(), e);
			this.redireccionaPaginaLogin(res, idAppUsar);
			
		} catch (SeguridadException e) {

			Logger.getLogger(TokenFilterAngular.class.getName()).log(Level.SEVERE, "SeguridadException: " +e.getMessage(), e);
			this.redireccionaPaginaLogin(res, idAppUsar);
			
		} catch (IOException e) {

			Logger.getLogger(TokenFilterAngular.class.getName()).log(Level.SEVERE, "IOException: " +e.getMessage(), e);
			this.redireccionaPaginaLogin(res, idAppUsar);
			
		} catch (ServletException e) {
			
			Logger.getLogger(TokenFilterAngular.class.getName()).log(Level.SEVERE, "ServletException: " +e.getMessage(), e);
			this.redireccionaPaginaLogin(res, idAppUsar);

		} catch (Exception e) {
			
			Logger.getLogger(TokenFilterAngular.class.getName()).log(Level.SEVERE, "Exception: " +e.getMessage(), e);
			this.redireccionaPaginaLogin(res, idAppUsar);

		}
	}

	@Override
    public void destroy() {/* Comentario sugerido por sonar*/}

}


