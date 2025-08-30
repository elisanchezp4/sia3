package co.gov.mineducacion.sdk.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;

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
 * Filtro encargado de interceptar todas las peticiones a servicios Rest utlizados por una aplicación ángular 
 * @author Asesoftware - Javier Estévez
 *
 */
public class TokenFilterAngular implements Filter {

	private static final Logger logger = Logger.getLogger(TokenFilterAngular.class.getName());

	private ServletContext context;
	
	private static final String STR_NULL = "null";
	private static final int ERR_NO_AUTORIZADO = 401;
	private static final String ACCESS_TOKEN = "access_token";
	private static final String CLIENT_ID = "client_id";
	private static final String USER_ID = "user_id";
	private static final String CODIGO_AUTORIZACION = "codigo_autorizacion";
	private static final String METHOD_OPTIONS = "OPTIONS";
	
	private static final Map<String, Date> cacheTokens = CacheTokens.getCacheTokens();
	
	private static Properties propiedades;
	private static int segundosDiferenciaCache;
	private static int msDiferenciaCache;
	private static List<String> lstUrlsExcluidas;
	
	public static void configurar(Properties propConfig){
		
		propiedades = propConfig;
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

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//VAlidacion que excluye las peticiones OPTION  de los clientes angular instanciados en otro servidor 
		if(METHOD_OPTIONS.equalsIgnoreCase(req.getMethod())) {
			chain.doFilter(request, response);
			return;
		}
		
		
		if(!FiltroSeguridad.estaConfigurado()){
			logger.warn("ERROR:  El SDK no está configurado, Use FiltroSeguridad.configurar(String rutaArchivoConfiguracion)");
			res.sendError(TokenFilterAngular.ERR_NO_AUTORIZADO);
			return;
		}
		
		
		String uri = req.getRequestURI();

		if(TokenFilterAngular.lstUrlsExcluidas != null &&
				TokenFilterAngular.lstUrlsExcluidas.contains(uri)){
			
			chain.doFilter(request, response);
			
			return;
		}

		String token = req.getHeader(TokenFilterAngular.ACCESS_TOKEN) == null ? req.getHeader(TokenFilterAngular.CODIGO_AUTORIZACION) : req.getHeader(TokenFilterAngular.ACCESS_TOKEN);
		String appId = req.getHeader(TokenFilterAngular.CLIENT_ID);
		String userId = req.getHeader(TokenFilterAngular.USER_ID);
		
		
		// Validar token cache
		if(token != null  && !token.isEmpty() && !token.equalsIgnoreCase(TokenFilterAngular.STR_NULL)){

			if (cacheTokens.containsKey(token)) {
				
				
				if (new Date().getTime() < cacheTokens.get(token).getTime() - (TokenFilterAngular.msDiferenciaCache)) {
					chain.doFilter(request, response);
				} else {
					actualizarFechaVencimiento(token, appId, userId, req, res, chain, request, response);
				}
				
			} else {
				
				actualizarFechaVencimiento(token, appId, userId, req, res, chain, request, response);
			}
			
		} else {
			
			
			res.sendError(TokenFilterAngular.ERR_NO_AUTORIZADO,"Token invalido");
			
		}
		
	}
	
	/**
	 * Retorna el error err recibido por parámetro en la respuesta representada por el objeto res
	 * @param res
	 * @param err
	 */
	private void retornarErrorEnRespuesta(HttpServletResponse res, int err, String msn){
		try {
			
			res.sendError(err,msn);
			
		} catch (IOException e) {
			logger.warn("IOException: " + e.getMessage(), e);
			
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
		
		try {
			logger.info("Inicio AutorizacionService.actualizarFechaVencimiento, token: "+token+", appId: "+appId+" ,userId: "+userId+" ,res: "+res);
				fechaVencimiento = AutorizacionService.actualizarFechaVencimiento(token, appId, Integer.valueOf(userId));

				if (!fechaVencimiento.after(new Date())) {

					AutorizacionService.finalizarSesion(token, appId, Integer.valueOf(userId));

					res.sendError(TokenFilterAngular.ERR_NO_AUTORIZADO,"Token vencido o no puede ser procesado");

				} else {
					logger.info("Inicio AutorizacionService.actualizarFechaVencimiento: == " + token + ' ' + fechaVencimiento.getTime());
					cacheTokens.put(token, fechaVencimiento);

					chain.doFilter(request, response);
				}
		} catch (NumberFormatException e) {

			logger.warn("NumberFormatException: " + e.getMessage(), e);
			this.retornarErrorEnRespuesta(res, TokenFilterAngular.ERR_NO_AUTORIZADO,"Usuario no existe");
			
		} catch (SeguridadException e) {

			logger.warn("SeguridadException: " + e.getMessage(), e);
			this.retornarErrorEnRespuesta(res, TokenFilterAngular.ERR_NO_AUTORIZADO,e.getMessage());
			
		} catch (IOException e) {

			logger.warn("IOException: " + e.getMessage(), e);
			this.retornarErrorEnRespuesta(res, TokenFilterAngular.ERR_NO_AUTORIZADO,e.getMessage());
			
		} catch (ServletException e) {

			logger.warn("ServletException: " + e.getMessage(), e);
			this.retornarErrorEnRespuesta(res, TokenFilterAngular.ERR_NO_AUTORIZADO,e.getMessage());
			
		} catch (Exception e) {

			logger.warn("Exception: " + e.getMessage(), e);
			this.retornarErrorEnRespuesta(res, TokenFilterAngular.ERR_NO_AUTORIZADO,e.getMessage());
			
		}
	}

	@Override
	public void destroy(){/* Comentario sugerido por sonar*/}

}  


