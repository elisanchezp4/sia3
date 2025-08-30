import { Injectable } from '@angular/core';
import { URL_BASE, TOKEN, APLICACION_ID, USER_ID } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/catch';
import { Util } from '../util/Util';

@Injectable()
export class AuditoriaService {

    private static CONSULTAR = "/auditoria/filtrarAuditoria";
    private static IMPRIMIR_REPORTE_RESOURCE = "/auditoria/reporteAuditoria"
    private static CONTAR_CONSULTA = "/auditoria/contarRegistrosAuditoria";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);
    constructor(private http: Http) { }

    public consultarAuditoria(aplicacionId: string, tipoEvento: string, fechaInicio: Date, fechaFin: Date, usuarioId: string,campoActivo:string, paginaInicio: number, paginaFin: number): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (aplicacionId != undefined && aplicacionId != "0") {
            params.set("aplicacionId", aplicacionId);
        }
        if (tipoEvento != undefined && tipoEvento != "0") {
            params.set("tipoEvento", tipoEvento);
        }
        if (fechaInicio != null) {
            params.set("fechaInicio", fechaInicio.getTime().toString());
        }
        if (fechaFin != null) {
            params.set("fechaFin", fechaFin.getTime().toString());
        }
        if (usuarioId != undefined && usuarioId != "0" && usuarioId != null) {
            params.set("usuarioId", usuarioId);
        }
        if (paginaInicio != null) {
            params.set("paginaInicio", paginaInicio.toString());
        }
        if (paginaFin != null) {
            params.set("paginaFin", paginaFin.toString());
        }
        if(campoActivo!=null  && campoActivo != "0"){
            params.set("campoActivo",campoActivo);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + AuditoriaService.CONSULTAR, options).catch(Util.handleError);
    }

    public imprimirReporteAuditoria(idAplicacion: string, idEvento: string, fechaInicio: Date, fechaFin: Date, usuarioId: string,campoActivo: string, paginaInicio: number, paginaFin: number, tipo: string, formato: string): Observable<any> {
        let params: URLSearchParams = new URLSearchParams();
        if (idAplicacion != null && idAplicacion != "0") {
            params.set("aplicacionId", idAplicacion);
        }
        if (idEvento != null && idEvento != "0") {
            params.set("tipoEvento", idEvento);
        }
        if (fechaInicio != null) {
            params.set("fechaInicio", fechaInicio.getTime().toString());
        }
        if (fechaFin != null) {
            params.set("fechaFin", fechaFin.getTime().toString());
        }
        if (usuarioId != undefined && usuarioId != "0") {
            params.set("usuarioId", usuarioId);
        }
        if (paginaInicio != null) {
            params.set("paginaInicio", paginaInicio.toString());
        }
        if (paginaFin != null) {
            params.set("paginaFin", paginaFin.toString());
        }
        if (formato != null) {
            params.set("formato", formato);
        }
        if(campoActivo !=null  && campoActivo != "0"){
            params.set("campoActivo",campoActivo);
        }
        let cadena = URL_BASE + AuditoriaService.IMPRIMIR_REPORTE_RESOURCE + '?' + params.toString();
        let headers = new Headers({
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params, responseType: ResponseContentType.Blob });
        return this.http.get(cadena, options).catch(Util.handleError);
    }

    public contarConsultarAuditoria(aplicacionId: string, tipoEvento: string, fechaInicio: Date, fechaFin: Date, usuarioId: string,campoActivo:string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (aplicacionId != undefined && aplicacionId != "0") {
            params.set("aplicacionId", aplicacionId);
        }
        if (tipoEvento != undefined && tipoEvento != "0") {
            params.set("tipoEvento", tipoEvento);
        }
        if (fechaInicio != null) {
            params.set("fechaInicio", fechaInicio.getTime().toString());
        }
        if (fechaFin != null) {
            params.set("fechaFin", fechaFin.getTime().toString());
        }
        if (usuarioId != undefined && usuarioId != "0") {
            params.set("usuarioId", usuarioId);
        }
        if(campoActivo!=null  && campoActivo != "0"){
            params.set("campoActivo",campoActivo);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + AuditoriaService.CONTAR_CONSULTA, options).catch(Util.handleError);
    }

}