import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { TOKEN, USER_ID, URL_PARAMETROS, APLICACION_ID } from '../util/Constants';
import { Util } from '../util/Util';
import 'rxjs/add/operator/catch';


@Injectable()
export class SesionService {

  private static TOKEN_RESOURCE = "/obtenertoken";
  private static ROLES_PERMISOS_RESOURCE = "/obtenerrolespermisos";
  private static LOGOUT_RESOURCE = "/finalizarsesion";
  private static AUTENTICACION_RESOURCE = "recursoswebseguridad/servicios/autenticacion";
  private URL_BASE_SEGURIDAD: string = "";
  private static URL_RESOURCE = "?nombreApp=TODAS&dominio=URL_BASE_SEGURIDAD";
  private static URL_BASE_VALORES_SIMPLE = sessionStorage.getItem(URL_PARAMETROS) + "rest/ConsultaItemListaValores/ValoresSimple";
  CLIENT_ID = null;
  constructor(private http: Http) {
    this.CLIENT_ID = sessionStorage.getItem(APLICACION_ID);  
  }


  /**metodo encargado de obtener el token para llamar los servicios */
  public obtenerToken(autorizacion: string, userId: string): Observable<Response> {
    //client_id equivale al id del aplicativo riel en el modulo de seguridad, en este caso el 37
    if (this.CLIENT_ID == null){
      this.CLIENT_ID = sessionStorage.getItem(APLICACION_ID);
    }
    return this.http.get(sessionStorage.getItem('url') + SesionService.AUTENTICACION_RESOURCE + SesionService.TOKEN_RESOURCE + "?codigo_autorizacion=" + autorizacion + "&client_id=" + this.CLIENT_ID + "&user_id=" + userId);
  }

  public getUrlSeguridad(): Observable<Response> {
    // se obtiene el valor de la url base desde la BD
    return this.http.get(SesionService.URL_BASE_VALORES_SIMPLE + SesionService.URL_RESOURCE);
  }

  /**metodo encargado de obtener los roles permisos y menu */
  public obtenerPermisos(): Observable<Response> {
    let ip = location.host;
    let body = "{\"header\":{\"ipHost\" : \"" + ip + "\", \"apiKey\": \"" + this.CLIENT_ID + "\", \"userId\": " + sessionStorage.getItem(USER_ID) + " }, \"tokenAcceso\":\"" + sessionStorage.getItem(TOKEN) + "\"}";
    let headers = new Headers({
      'Content-Type': 'application/json; charset=UTF-8',
      'access_token': sessionStorage.getItem(TOKEN),
      'client_id': this.CLIENT_ID,
      'user_id': sessionStorage.getItem(USER_ID)
    });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(sessionStorage.getItem('url') + SesionService.AUTENTICACION_RESOURCE + SesionService.ROLES_PERMISOS_RESOURCE, body, options).catch(Util.handleError);
  }

  /**metodo encargado de cerrar la sesion en seguridad */
  public cerrarSesion(): Observable<Response> {
    let ip = location.host;
    let body = "{\"header\":{\"ipHost\" : \"" + ip + "\", \"apiKey\": \"" + this.CLIENT_ID + "\", \"userId\": " + sessionStorage.getItem(USER_ID) + " }, \"tokenAcceso\":\"" + sessionStorage.getItem(TOKEN) + "\"}";
    let headers = new Headers({
      'Content-Type': 'application/json; charset=UTF-8',
      'access_token': sessionStorage.getItem(TOKEN),
      'client_id': this.CLIENT_ID,
      'user_id': sessionStorage.getItem(USER_ID)
    });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(sessionStorage.getItem('url') + SesionService.AUTENTICACION_RESOURCE + SesionService.LOGOUT_RESOURCE, body, options).catch(Util.handleError);
  }


}
