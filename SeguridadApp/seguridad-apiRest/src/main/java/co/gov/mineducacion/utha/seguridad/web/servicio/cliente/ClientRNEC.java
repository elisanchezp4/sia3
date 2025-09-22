package co.gov.mineducacion.utha.seguridad.web.servicio.cliente;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Cliente para consumir los servicios externos de la Registraduría Nacional del Estado Civil (RNEC).
 */
@Slf4j
public abstract class ClientRNEC {

    private static final String TOKEN_URL = "https://apicert.mineducacion.gov.co:8443/SINCRONIZADOR/SIA3/v1/seguridad/token";
    private static final String DOCUMENT_URL = "https://apicert.mineducacion.gov.co:8443/SINCRONIZADOR/SIA3/v1/consulta/documento";
    private static final String AUTH_BASIC_HEADER = "Basic xxxxxxxxxxxxxxxx"; // Reemplaza con tus credenciales reales


    /**
     * Consulta la información de un documento en el servicio de RNEC usando un token.
     *
     * @param numeroDocumento El número de documento a consultar.
     * @param tipoDocumento   El tipo de documento (ej. "CC").
     * @return Un objeto JsonObject con la información del documento.
     * @throws Exception si la petición falla.
     */
    public static JsonObject getDocumentInfo(String numeroDocumento, String tipoDocumento) throws Exception {

        String token = getToken();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder uriBuilder = new URIBuilder(DOCUMENT_URL)
                    .addParameter("numeroDocumento", numeroDocumento)
                    .addParameter("tipoDocumento", tipoDocumento);

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            httpPost.setHeader("Authorization", "Bearer " + token);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    return new Gson().fromJson(responseBody, JsonObject.class);
                } else {
                    String errorBody = EntityUtils.toString(response.getEntity());
                    log.error("Error al consultar documento. Código: " + response.getStatusLine().getStatusCode() + ", Respuesta: " + errorBody);
                    throw new RuntimeException("Fallo al consultar la información del documento.");
                }
            }
        }
    }

    /**
     * Obtiene un token de autorización del servicio de seguridad de RNEC.
     *
     * @return El token de autorización como String.
     * @throws Exception si la petición falla.
     */
    private static String getToken() throws Exception {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(TOKEN_URL);
            httpGet.setHeader("Authorization", AUTH_BASIC_HEADER);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
                    return jsonResponse.get("token").getAsString();
                } else {
                    String errorBody = EntityUtils.toString(response.getEntity());
                    log.error("Error al obtener el token. Código: " + response.getStatusLine().getStatusCode() + ", Respuesta: " + errorBody);
                    throw new RuntimeException("Fallo al obtener el token de autenticación.");
                }
            }
        }
    }
}
