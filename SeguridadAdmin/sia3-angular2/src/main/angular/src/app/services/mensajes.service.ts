import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { URL_BASE, USER_ID, TOKEN, APLICACION_ID } from '../util/Constants';
import 'rxjs/add/operator/catch';

/*
 * Este servicio provee los metodos para conectarse con el servicio rest de la
 * parametrización de tarifas
 */
@Injectable()
export class MensajesService {

    private static MENSAJE_RESOURCE = "/mensaje";


    private static MENSAJE_AYUDA = "/buscarPorCodigo";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);
    /*
    * constructor al que se inyecta el servicio de http
    * para realizar peticiones al servidor
    */
    constructor(private http: Http) {
    }

    /*
     * permite obtener un mensaje
     */
    public getMensaje(codigo: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (codigo != null && codigo != "") {
            params.set("codigo", codigo);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + MensajesService.MENSAJE_RESOURCE, options).catch(this.handleError);
    }


    /*
   * permite obtener un mensaje de ayuda
   */
    public getMensajeAyuda(codigo: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (codigo != null && codigo != "") {
            params.set("codigo", codigo);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + MensajesService.MENSAJE_RESOURCE + MensajesService.MENSAJE_AYUDA, options).catch(this.handleError);
    }

    private handleError(error: Response | any) {
        let response;
        if (error instanceof Response) {
            switch (error.status) {
                case 400:
                    response = error.json() || '';
                    break;
                case 401:
                    window.location.href = localStorage.getItem('redirect');
                    break;
                default:
                    response = error;
            }
        } else {
            response = error;
        }
        return Observable.throw(response);
    }

}
