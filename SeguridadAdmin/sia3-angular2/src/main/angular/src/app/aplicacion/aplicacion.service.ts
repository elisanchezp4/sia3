import { Injectable } from '@angular/core';
import { URL_BASE, TOKEN, APLICACION_ID, USER_ID } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Util } from '../util/Util';
import { Observable } from 'rxjs/Observable';
import { Aplicacion } from 'app/modelo/Aplicacion';
import 'rxjs/add/operator/catch';



@Injectable()
export class AplicacionService {
    private static CONSULTAR = "/aplicacion/getAllAplicaciones";
    private static CONSULTAR_CATALOGO = "/catalogo/listarCatalogoTipo";
    private static CONSULTAR_CATALOGO_SEGURIDAD = "/catalogo/listarCatalogoTipoSeguridad";
    private static GUARDAR = "/aplicacion/";
    private static INACTIVAR = "/aplicacion/eliminarAplicacion";
    private static CONSULTAR_POR_USUARIO = "/aplicacion/getAplicacionesPorUsuario";
    private static CONSULTAR_APPS_SUGERIDAS = "/aplicacion/getAplicacionesNombre";
    private static CONSULTAR_CAMPOS_DIRECTORIO_ACTIVO ="/aplicacion/getAllCamposActivos";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);

    constructor(private http: Http) { }
    public consultar(estado): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (estado != null || estado != "") {
            params.set("estado", estado);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + AplicacionService.CONSULTAR, options).catch(Util.handleError);
    }

    public consultarCatalogo(tipo: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (tipo != null || tipo != "") {
            params.set("tipoCatalogo", tipo);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + AplicacionService.CONSULTAR_CATALOGO, options).catch(Util.handleError);
    }

    public consultarCatalogoSeguridad(tipo: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (tipo != null || tipo != "") {
            params.set("tipoCatalogo", tipo);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + AplicacionService.CONSULTAR_CATALOGO_SEGURIDAD, options).catch(Util.handleError);
    }

    public guardar(aplicacion: Aplicacion): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.post(URL_BASE + AplicacionService.GUARDAR, aplicacion, options).catch(Util.handleError);
    }

    public modificar(aplicacion: Aplicacion): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.put(URL_BASE + AplicacionService.GUARDAR, aplicacion, options).catch(Util.handleError);
    }

    public inactivar(aplicacion: Aplicacion): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (aplicacion.aplicacionId != null) {
            params.set("aplicacionId", aplicacion.aplicacionId.toString());
            params.set("usuarioId", aplicacion.usuarioModificacion.toString());
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.delete(URL_BASE + AplicacionService.INACTIVAR, options).catch(Util.handleError);
    }

    public consultarPorUsuario(estado, usuarioId): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (estado != null || estado != "") {
            params.set("estado", estado);
        }
        if (usuarioId != null || usuarioId != "") {
            params.set("usuarioId", usuarioId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + AplicacionService.CONSULTAR_POR_USUARIO, options).catch(Util.handleError);
    }

    public consultarAplicacionesSugeridadas(estado, nombre): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (estado != undefined && estado != "") {
            params.set("estado", estado);
        } if (nombre != undefined && nombre != "") {
            params.set("nombre", nombre);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + AplicacionService.CONSULTAR_APPS_SUGERIDAS, options).catch(Util.handleError);
    }

    public consultarCamposDirectorioActivos(): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers});
        return this.http.get(URL_BASE + AplicacionService.CONSULTAR_CAMPOS_DIRECTORIO_ACTIVO, options).catch(Util.handleError);
    }

}