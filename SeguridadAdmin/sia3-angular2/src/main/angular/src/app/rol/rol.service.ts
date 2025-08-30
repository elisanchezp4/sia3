import { Injectable } from '@angular/core';
import { URL_BASE, APLICACION_ID, USER_ID, TOKEN } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Rol } from 'app/modelo/Rol';
import { OperacionRol } from 'app/modelo/OperacionRol';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { Util } from '../util/Util';
import 'rxjs/add/operator/catch';

@Injectable()
export class RolService {


    private static CONSULTAR_POR_APP = "/rol/getRolPorApp";
    private static CONSULTAR_OPERACIONES = "/operacion/getOperacionesPorRol";
    private static ASIGNAR_OPERACIONES = "/operacionRol";
    private static GUARDAR = "/rol";
    private static INACTIVAR = "/rol/eliminarRol";
    private static CONSULTAR_ESTADOS = "/catalogo/listarCatalogoEstado";
    private static ELIMINAR_OPERACIONES = "/operacionRol/eliminarOperacionPorRol";
    private static IMPORTAR = "/rol/importar";
  private static AUDITAR_EXPORTAR = "/auditoria/auditarExportar";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);

    constructor(private http: Http) { }

    public consultarRolesPorApp(aplicacionId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        params.set("aplicacionId", aplicacionId);
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + RolService.CONSULTAR_POR_APP, options).catch(Util.handleError);
    }

    public consultarEstados(): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.get(URL_BASE + RolService.CONSULTAR_ESTADOS, options).catch(Util.handleError);
    }

    public guardar(rol: Rol): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(URL_BASE + RolService.GUARDAR, rol, options).catch(Util.handleError);
    }

    public modificar(rol: Rol): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.put(URL_BASE + RolService.GUARDAR, rol, options).catch(Util.handleError);
    }

    public inactivar(rol: Rol): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (rol.rolId != undefined && rol.rolId != "0") {
            params.set("rolId", rol.rolId);
            params.set("usuarioId", rol.usuarioModificacion.toString());
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.delete(URL_BASE + RolService.INACTIVAR, options).catch(Util.handleError);
    }

    public consultarOperacionesRol(rolId: string, idAplicacion: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (rolId != undefined && rolId != "0") {
            params.set("rolId", rolId);
        }
        if (idAplicacion != undefined && idAplicacion != "0") {
            params.set("aplicacionId", idAplicacion);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + RolService.CONSULTAR_OPERACIONES, options).catch(Util.handleError);
    }

    public asignarOperacionesRol(operacionesRol: OperacionRol[]): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(URL_BASE + RolService.ASIGNAR_OPERACIONES, operacionesRol, options).catch(Util.handleError);
    }

    public eliminarOperacionesRol(rolId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (rolId != undefined && rolId != "0") {
            params.set("rolId", rolId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.post(URL_BASE + RolService.ELIMINAR_OPERACIONES + '?' + params.toString(), options).catch(Util.handleError);
    }

    public importar(rol: any): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(URL_BASE + RolService.IMPORTAR, rol, options).catch(Util.handleError);
    }

  public auditarExportar(idAplication: string) {
    let headers = new Headers({
      'Content-Type': 'application/json; charset=UTF-8',
      'access_token': sessionStorage.getItem(TOKEN),
      'client_id': this.CLIENT_ID,
      'user_id': sessionStorage.getItem(USER_ID)
    });
    let params: URLSearchParams = new URLSearchParams();
    params.set('aplicacionId', idAplication);
    params.set('auditarRolOperacion', 'AUDITAR_ROL');
    params.set('usuarioId', sessionStorage.getItem(USER_ID));

    let options = new RequestOptions({ headers: headers , search: params});
    return this.http.post(URL_BASE + RolService.AUDITAR_EXPORTAR, null, options);
  }
}
