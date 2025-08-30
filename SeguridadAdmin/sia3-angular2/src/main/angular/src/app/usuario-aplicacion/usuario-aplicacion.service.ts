import { Injectable } from '@angular/core';
import { URL_BASE, APLICACION_ID, USER_ID, TOKEN } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Usuario } from 'app/modelo/Usuario';
import { Util } from '../util/Util';
import 'rxjs/add/operator/catch';

@Injectable()
export class UsuarioAplicacionService {

    private static CONSULTAR_USUARIOS_APP = "/usuario/getUsuariosPorApp";
    private static ASIGNAR_USUARIOS_APP = "/usuarioAplicacion";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);

    constructor(private http: Http) { }

    public consultarUsuarioPorApp(opcionId: string, aplicacionId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (opcionId != undefined && opcionId != "0") {
            params.set("opcionId", opcionId);
        }
        if (aplicacionId != undefined && aplicacionId != "0") {
            params.set("aplicacionId", aplicacionId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + UsuarioAplicacionService.CONSULTAR_USUARIOS_APP, options).catch(Util.handleError);
    }

    public asignarUsuarioApp(usuario: Usuario[], aplicacionId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (aplicacionId != null) {
            params.set("aplicacionId", aplicacionId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.post(URL_BASE + UsuarioAplicacionService.ASIGNAR_USUARIOS_APP + '?' + params.toString(), usuario, options).catch(Util.handleError);
    }

}