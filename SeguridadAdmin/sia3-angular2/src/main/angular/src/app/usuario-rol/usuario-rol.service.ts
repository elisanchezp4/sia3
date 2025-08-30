import { Injectable } from '@angular/core';
import { URL_BASE, APLICACION_ID, USER_ID, TOKEN } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Usuario } from 'app/modelo/Usuario';
import { Util } from '../util/Util';
import 'rxjs/add/operator/catch';


@Injectable()
export class UsuarioRolService {

    private static ASIGNAR_USUARIO_ROL = "/usuarioRol";
    private static DESVINCULAR_USUARIO = "/usuario/desvincularUsuarios";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);

    constructor(private http: Http) { }

    public agregarUsuariosARol(usuario: Usuario[], rolId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (rolId != null) {
            params.set("rolId", rolId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.post(URL_BASE + UsuarioRolService.ASIGNAR_USUARIO_ROL + '?' + params.toString(), usuario, options).catch(Util.handleError);
    }

    public desvincularUsuarios(usuario: Usuario[], rolId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (rolId != null) {
            params.set("rolId", rolId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.post(URL_BASE + UsuarioRolService.DESVINCULAR_USUARIO + '?' + params.toString(), usuario, options).catch(Util.handleError);
    }

}