package co.gov.mineducacion.seguridad.modelo.utils;

import co.gov.mineducacion.seguridad.modelo.excepciones.SIA3Exception;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ServicioHTTP {

    public static final String X_API_KEY = "x-api-key";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";

    private ServicioHTTP(){/* Recomendacion por sonar */}
    private static final Logger logger = Logger.getLogger(ServicioHTTP.class);
    private static final Gson mapper = new Gson();
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();

    /**
     * @param url   url servicio externo
     * @param clase clase que retorna trasnformada
     * @param key   clave unica para servicio externo
     * @param <T>   objeto global estandar
     * @return retorna la clase esperada
     * @throws SIA3Exception retorna el error con su detalle para clientes externos
     */
    public static <T> T enviarPeticionGet(String url, Class<T> clase, String key) throws SIA3Exception {
        logger.info("Inicia peticion rest get: " + url + " ; clase: " + clase.getName());
        try {
            Request request = new Request.Builder()
              .url(url)
              .method("GET", null)
              .addHeader(X_API_KEY, key)
              .build();
            Response response = client.newCall(request).execute();
            return validarRespuesta(response, clase);
        } catch (IOException e) {
            logger.error("Error en consulta get: {}", new Throwable(e));
            throw new SIA3Exception(e.getMessage(), e);
        }
    }

    /**
     * @param serviceUrl url servicio externo
     * @param content    cuerpo de la solicitud http
     * @param clase      clase que retorna trasnformada
     * @param key        clave unica para servicio externo
     * @param <T>        objeto global estandar
     * @return retorna la clase esperada
     * @throws SIA3Exception retorna el error con su detalle para clientes externos
     */
    public static <T> T enviarPublicacionPost(String serviceUrl, String content, Class<T> clase, String key) throws SIA3Exception {
        logService("Inicia peticion rest post: " + serviceUrl + " ;body: " + mapper.toJson(content) + " clase: " + clase.getName());
        try {
            MediaType mediaType = MediaType.parse(APPLICATION_JSON);
            RequestBody body = RequestBody.create(mediaType, content);
            Request request = new Request.Builder()
              .url(serviceUrl)
              .method("POST", body)
              .addHeader(CONTENT_TYPE, APPLICATION_JSON)
              .addHeader(X_API_KEY, key)
              .build();
            Response response = client.newCall(request).execute();
            return validarRespuesta(response, clase);
        } catch (IOException e) {
            logger.error("Error en consulta post: {}", new Throwable(e));
            throw new SIA3Exception(e.getMessage(), e);
        }
    }

    private static void logService(String log){
        if(log != null && !log.contains("password")){
            logger.info(log);
        }
    }

    /**
     * @param serviceUrl url servicio externo
     * @param content    cuerpo de la solicitud http
     * @param clase      clase que retorna trasnformada
     * @param key        clave unica para servicio externo
     * @param <T>        objeto global estandar
     * @return retorna la clase esperada
     * @throws SIA3Exception retorna el error con su detalle para clientes externos
     */
    public static <T> T enviarPublicacionPut(String serviceUrl, String content, Class<T> clase, String key) throws SIA3Exception {
        logService("Inicia peticion rest put: " + serviceUrl + " body: " + content + " clase: " + clase.getName());
        try {
            MediaType mediaType = MediaType.parse(APPLICATION_JSON);
            RequestBody body = RequestBody.create(mediaType, content);
            Request request = new Request.Builder()
              .url(serviceUrl)
              .method("PUT", body)
              .addHeader(X_API_KEY, key)
              .addHeader(CONTENT_TYPE, APPLICATION_JSON)
              .build();
            Response response = client.newCall(request).execute();
            return validarRespuesta(response, clase);
        } catch (IOException e) {
            logger.error("Error en consulta put: {}", new Throwable(e));
            throw new SIA3Exception(e.getMessage(), e);
        }
    }

    /**
     * /**
     *
     * @param url     url servicio externo
     * @param content cuerpo de la solicitud http
     * @param clase   clase que retorna trasnformada
     * @param key     clave unica para servicio externo
     * @param <T>     objeto global estandar
     * @return retorna la clase esperada
     * @throws SIA3Exception retorna el error con su detalle para clientes externos
     */
    public static <T> T enviarPeticionGetConBody(String url, Class<T> clase, String content, String key) throws SIA3Exception {
        logger.info("Inicia peticion rest get: " + url + " body: " + mapper.toJson(content) + "clase: " + clase.getName());
        try {
            MediaType mediaType = MediaType.parse(APPLICATION_JSON);
            RequestBody body = RequestBody.create(mediaType, content);
            Request request = new Request.Builder()
              .url(url)
              .method("get", body)
              .addHeader(X_API_KEY, key)
              .addHeader(CONTENT_TYPE, APPLICATION_JSON)
              .build();
            Response response = client.newCall(request).execute();
            return validarRespuesta(response, clase);
        } catch (IOException e) {
            logger.error("Error en consulta get: {}", new Throwable(e));
            throw new SIA3Exception(e.getMessage(), e);
        }
    }

    /**
     * @param response objeto completo de respuesta libreria okhttp3
     * @param clase modelo al cual se transforma la respuesta
     * @param <T>     objeto global estandar
     * @return retorna la clase esperada
     */
    private static <T> T validarRespuesta(Response response, Class<T> clase) {
        assert response.body() != null;
        return mapper.fromJson(response.body().charStream(), clase);
    }
}
