import { Injectable } from '@angular/core';
import { URL_BASE, URL_BASE_PARAMETROS, URL_PARAMETROS } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Parametro } from 'app/modelo/Parametro';


@Injectable()
export class ParametrosService {


    private static CONSULTAR_PARAMETROS = "/consultaParemetrosFiltrosDiferentes";
    private static EDITAR_PARAMETRO = "/editarParametro";
    private static CONSULTAR_LISTA_DOMINIOS = "/consultaListasDominio";
    private static CONSULTAR_LISTA_TIPO_PARAMETRO = "/consultaListasTipoParametro";
    private static CONSULTAR_LISTA_APLICACIONES = "/consultaListasAplicacion";
    private static CONSULTAR_PARAMETROS_FILTROS = "/consultaParemetrosFiltros"
    private static ADICIONAR_PARAMETRO = "/adicionarParametro"
    private static CONSULTAR_LISTA_DEPARTAMENTOS = "/consultaListasDepartamentos";
    URL_PARAMETROS = sessionStorage.getItem(URL_PARAMETROS) + URL_BASE_PARAMETROS;


    constructor(private http: Http) { }

    public consultarParametros(tipoParametro: string, nombreApp: string, dominio: string, estado: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (tipoParametro != undefined && tipoParametro != "0") {
            params.set("tipoParametro", tipoParametro);
        }
        if (nombreApp != undefined && nombreApp != "") {
            params.set("nombreApp", nombreApp);
        }
        if (dominio != undefined && dominio != "0") {
            params.set("dominio", dominio);
        }
        if (estado != undefined && estado != "0") {
            params.set("estado", estado);
        }
        return this.http.get(this.URL_PARAMETROS + ParametrosService.CONSULTAR_PARAMETROS, {
            search: params
        });
    }

    public editarParametros(parametros: Parametro): Observable<Response> {
        let headers = new Headers({ 'Content-Type': 'application/json; charset=UTF-8' });
        let options = new RequestOptions({ headers: headers });
        return this.http.put(this.URL_PARAMETROS + ParametrosService.EDITAR_PARAMETRO, parametros, options);
    }

    public consultarListaDominios(nombreApp: string, tipoParametro: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (nombreApp != undefined && nombreApp != "") {
            params.set("nombreApp", nombreApp);
        }
        if (tipoParametro != undefined && tipoParametro != "") {
            params.set("tipoParametro", tipoParametro);
        }
        return this.http.get(this.URL_PARAMETROS + ParametrosService.CONSULTAR_LISTA_DOMINIOS, {
            search: params
        });
    }

    public consultarListaTipoParametro(nombreApp: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (nombreApp != undefined && nombreApp != "") {
            params.set("nombreApp", nombreApp);
        }
        return this.http.get(this.URL_PARAMETROS + ParametrosService.CONSULTAR_LISTA_TIPO_PARAMETRO, {
            search: params
        });
    }

    public consultarAplicaciones(): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        return this.http.get(this.URL_PARAMETROS + ParametrosService.CONSULTAR_LISTA_APLICACIONES);
    }

    public consultarParametrosFiltro(tipoParametro: string, nombreApp: string, dominio: string, estado: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (tipoParametro != undefined && tipoParametro != "0") {
            params.set("tipoParametro", tipoParametro);
        }
        if (nombreApp != undefined && nombreApp != "") {
            params.set("nombreApp", nombreApp);
        }
        if (dominio != undefined && dominio != "0") {
            params.set("dominio", dominio);
        }
        if (estado != undefined && estado != "0") {
            params.set("estado", estado);
        }
        return this.http.get(this.URL_PARAMETROS + ParametrosService.CONSULTAR_PARAMETROS_FILTROS, {
            search: params
        });
    }

    public adicionarParametro(parametros: Parametro): Observable<Response> {
        let headers = new Headers({ 'Content-Type': 'application/json; charset=UTF-8' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(this.URL_PARAMETROS + ParametrosService.ADICIONAR_PARAMETRO, parametros, options);
    }

    public consultarDepartamentos(): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        return this.http.get(this.URL_PARAMETROS + ParametrosService.CONSULTAR_LISTA_DEPARTAMENTOS);
    }
}