import { Injectable } from '@angular/core';
import { URL_BASE, USER_ID, TOKEN, APLICACION_ID } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Usuario } from 'app/modelo/Usuario';
import { Util } from '../util/Util';
import 'rxjs/add/operator/catch';
import { catchError, map } from 'rxjs/operators';


@Injectable()
export class UsuarioService {

    private static CONSULTAR = "/usuario/getUsuarioPorNombre";
    private static CONSULTAR_ALL = "/usuario/getAllUsuarios";
    private static CONSULTAR_USUARIOS_SEGURIDAD = "/usuario/getAllUsuariosSeguridad";
    private static CONSULTAR_USUARIOS_APLICACION = "/usuario/getAllUsuariosPorApp";
    private static CONSULTAR_TOTAL_USUARIOS_APP = "/usuario/getTotalUsuariosPorApp";
    private static ACTIVAR = "/usuario/activarUsuario";
    private static INACTIVAR = "/usuario/inactivarUsuario";
    private static CONSULTAR_ROL_USUARIO = "/rol/getRolPorUsuario";
    private static CONSULTAR_USUARIOS = "/usuario/getUsuariosPorFiltros";
    private static CONSULTAR_USUARIOS_ROL_APP = "/usuario/getUsuariosPorAppRol";
    private static CONSULTAR_USUARIOS_INACTIVOS_LDAP = "/usuario/obtenerlistDesactivados";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);
    constructor(private http: Http) { }

    public consultar(userName: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (userName != undefined && userName != null && userName != "") {
            params.set("nombreUsuario", userName);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR, options).catch(Util.handleError);
    }

    public consultarConSesion(userName: string, usuarioSesionId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (userName != undefined && userName != null && userName != "") {
            params.set("nombreUsuario", userName);
            params.set("usuarioSesionId", usuarioSesionId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR, options).catch(Util.handleError);
    }

    public consultarAll(): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        //return this.http.get(URL_BASE + UsuarioService.CONSULTAR_ALL, {
        // });
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR_ALL, options).catch(Util.handleError);
    }

    public consultarUsuariosSeguridad(): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        //return this.http.get(URL_BASE + UsuarioService.CONSULTAR_USUARIOS_SEGURIDAD, {
        //});
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR_USUARIOS_SEGURIDAD, options).catch(Util.handleError);
    }


  public consultarUsuarioApp(aplicacionId: string, usuario: string): Observable<Response> {
    let params: URLSearchParams = new URLSearchParams();
    params.set("aplicacionId", aplicacionId);
    params.set("usuario", usuario);
    let headers = new Headers({
      'Content-Type': 'application/json; charset=UTF-8',
      'access_token': sessionStorage.getItem(TOKEN),
      'client_id': this.CLIENT_ID,
      'user_id': sessionStorage.getItem(USER_ID)
    });
    let options = new RequestOptions({ headers: headers, search: params });
    return this.http.get(URL_BASE + UsuarioService.CONSULTAR_USUARIOS_APLICACION, options).catch(Util.handleError);
    }

    public consultarTotalUsuarioApp(aplicacionId: string){
      let params: URLSearchParams = new URLSearchParams();
      params.set("aplicacionId", aplicacionId);
      let headers = new Headers({
        'Content-Type': 'application/json; charset=UTF-8',
        'access_token': sessionStorage.getItem(TOKEN),
        'client_id': this.CLIENT_ID,
        'user_id': sessionStorage.getItem(USER_ID)
      });
      let options = new RequestOptions({ headers: headers, search: params });
      return this.http.get(URL_BASE + UsuarioService.CONSULTAR_TOTAL_USUARIOS_APP, options).catch(Util.handleError);
    }

    public inactivarUsuario(usuario: Usuario): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.put(URL_BASE + UsuarioService.INACTIVAR, usuario, options).catch(Util.handleError);
    }

    public activarUsuario(usuario: Usuario): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        usuario.adressLocal = location.hostname;
        usuario.portLocal = sessionStorage.getItem('port');
        let options = new RequestOptions({ headers: headers });
        return this.http.put(URL_BASE + UsuarioService.ACTIVAR, usuario, options).catch(Util.handleError);
    }

    public consultarRolPorUsuario(usuarioId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (usuarioId != undefined && usuarioId != null && usuarioId != "") {
            params.set("usuarioId", usuarioId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR_ROL_USUARIO, options).catch(Util.handleError);
    }

    public consultarUsuariosPorFiltro(tipoUsuario: string, nombreUsuario: string, nombres: string, apellidos: string, email: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (tipoUsuario != undefined && tipoUsuario != "0") {
            params.set("tipoUsuario", tipoUsuario);
        }
        if (nombreUsuario != undefined && nombreUsuario != null && nombreUsuario != "") {
            params.set("logonName", nombreUsuario);
        }
        if (nombres != undefined && nombres != null && nombres != "") {
            params.set("nombres", nombres);
        }
        if (apellidos != undefined && apellidos != null && apellidos != "") {
            params.set("apellidos", apellidos);
        }
        if (email != undefined && email != null && email != "") {
            params.set("email", email);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR_USUARIOS, options).catch(Util.handleError);
    }

    public consultarUsuariosPorFiltroConSesion(tipoUsuario: string, nombreUsuario: string, nombres: string, apellidos: string, email: string, usuarioSesionId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (tipoUsuario != undefined && tipoUsuario != "0") {
            params.set("tipoUsuario", tipoUsuario);
        }
        if (nombreUsuario != undefined && nombreUsuario != null && nombreUsuario != "") {
            params.set("logonName", nombreUsuario);
        }
        if (nombres != undefined && nombres != null && nombres != "") {
            params.set("nombres", nombres);
        }
        if (apellidos != undefined && apellidos != null && apellidos != "") {
            params.set("apellidos", apellidos);
        }
        if (email != undefined && email != null && email != "") {
            params.set("email", email);
        }
        if (usuarioSesionId != undefined && usuarioSesionId != null && usuarioSesionId != "") {
            params.set("usuarioSesionId", usuarioSesionId);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR_USUARIOS, options).catch(Util.handleError);
    }

    public consultarUsuarioRolApp(opcionId: string, rolId: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (opcionId != undefined && opcionId != "0") {
            params.set("opcionId", opcionId);
        }
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
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR_USUARIOS_ROL_APP, options).pipe(map(res=>res),catchError(Util.handleError));
    }

    //Se agrego el servicio Consultar Usuarios Inactivos
    public consultarinactivos(): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        params.set("url", "OU=Usuarios Externos,DC=minedu,DC=gov,DC=co");
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + UsuarioService.CONSULTAR_USUARIOS_INACTIVOS_LDAP, options).catch(Util.handleError);
    }
}
