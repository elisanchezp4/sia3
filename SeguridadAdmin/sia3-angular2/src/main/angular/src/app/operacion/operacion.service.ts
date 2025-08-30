import { Injectable } from '@angular/core';
import { URL_BASE, APLICACION_ID, USER_ID, TOKEN } from 'app/util/Constants';
import { Http, Response, RequestOptions, Headers, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { TreeNode } from "primeng/primeng";
import { Operacion } from 'app/modelo/Operacion';
import { Util } from '../util/Util';
import 'rxjs/add/operator/catch';
import { OperacionAImportar } from 'app/modelo/OperacionAImportar';
import { map } from 'rxjs/operators'


@Injectable()
export class OperacionService {

    private static CONSULTAR = "/operacion/getArbolOperaciones";
    private static CONSULTAR_ESTADOS = "/catalogo/listarCatalogoEstado";
    private static GUARDAR = "/operacion";
    private static ELIMINAR = "/operacion/eliminarOperacion";
    private static EDITAR = "/operacion";
    private static CONSULTAR_POR_APLICACION = "/operacion/getOperacionesPorAplicacion";
    private static IMPORTAR = "/operacion/importar";
    private static AUDITAR_EXPORTAR = "/auditoria/auditarExportar";

    CLIENT_ID = sessionStorage.getItem(APLICACION_ID);

    constructor(private http: Http) { }


    public consultarOperaciones(aplicacionId: string, nombreObjeto: string): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (aplicacionId != undefined && aplicacionId != "0") {
            params.set("aplicacionId", aplicacionId);
        } if (nombreObjeto != undefined && nombreObjeto != "") {
            params.set("nombreObjeto", nombreObjeto);
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + OperacionService.CONSULTAR, options).catch(Util.handleError);
    }

    public consultar() {

        let json = [
            {
                "nombreObjeto": "Módulo de seguridad",
                "descripcion": " Aplicación Módulo de seguridad",
                "opcionId": "0",
                "tipo": "aplicacion",
                "aplicacionId": "24",
                "estado": "1",
                "children": [

                    {
                        "opcionId": "1",
                        "tipo": "menu",
                        "nombreObjeto": "Administración",
                        "descripcion": " Aplicación Módulo de seguridad",
                        "aplicacionId": "24",
                        "estado": "1",
                        "children": [
                            {
                                "opcionId": "2",
                                "tipo": "submenu",
                                "nombreObjeto": "Usuarios",
                                "descripcion": " Aplicación Módulo de seguridad",
                                "aplicacionId": "24",
                                "estado": "1",
                                "children": [{
                                    "opcionId": "2",
                                    "tipo": "submenu",
                                    "nombreObjeto": "Usuarios 1",
                                    "descripcion": " Aplicación Módulo de seguridad",
                                    "aplicacionId": "24",
                                    "estado": "1"
                                }]
                            },

                            {
                                "opcionId": "3",
                                "tipo": "submenu",
                                "nombreObjeto": "Aplicaciones",
                                "descripcion": " Aplicación Módulo de seguridad",
                                "aplicacionId": "24",
                                "estado": "1",
                                "children": [
                                    {
                                        "opcionId": "4",
                                        "tipo": "boton",
                                        "nombreObjeto": "Nueva Aplicación",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1",

                                    },
                                    {
                                        "opcionId": "5",
                                        "tipo": "boton",
                                        "nombreObjeto": "Modificar Aplicación",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1",
                                    },
                                    {
                                        "opcionId": "6",
                                        "tipo": "boton",
                                        "nombreObjeto": "Inactivar Aplicación",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    },
                                    {
                                        "opcionId": "7",
                                        "tipo": "boton",
                                        "nombreObjeto": "Guardar",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    },
                                    {
                                        "opcionId": "8",
                                        "tipo": "boton",
                                        "nombreObjeto": "Volver",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    }
                                ]
                            },
                            {
                                "opcionId": "9",
                                "tipo": "submenu",
                                "nombreObjeto": "Roles",
                                "descripcion": " Aplicación Módulo de seguridad",
                                "aplicacionId": "24",
                                "estado": "1",
                                "children": [
                                    {
                                        "opcionId": "10",
                                        "tipo": "boton",
                                        "nombreObjeto": "Nuevo Rol",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    },
                                    {
                                        "opcionId": "11",
                                        "tipo": "boton",
                                        "nombreObjeto": "Modificar Rol",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    },
                                    {
                                        "opcionId": "12",
                                        "tipo": "boton",
                                        "nombreObjeto": "Inactivar Rol",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    },
                                    {
                                        "opcionId": "13",
                                        "tipo": "boton",
                                        "nombreObjeto": "Guardar",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    },
                                    {
                                        "opcionId": "14",
                                        "tipo": "boton",
                                        "nombreObjeto": "Volver",
                                        "descripcion": " Aplicación Módulo de seguridad",
                                        "aplicacionId": "24",
                                        "estado": "1"
                                    }
                                ]
                            },
                            {
                                "opcionId": "15",
                                "tipo": "submenu",
                                "nombreObjeto": "Operaciones",
                                "descripcion": " Aplicación Módulo de seguridad",
                                "aplicacionId": "24",
                                "estado": "1"
                            },
                            {
                                "opcionId": "16",
                                "tipo": "submenu",
                                "nombreObjeto": "Usuarios/Rol",
                                "descripcion": " Aplicación Módulo de seguridad",
                                "aplicacionId": "24",
                                "estado": "1"
                            }
                        ]
                    },

                    {
                        "opcionId": "17",
                        "tipo": "menu",
                        "nombreObjeto": "Auditoría",
                        "descripcion": " Aplicación Módulo de seguridad",
                        "aplicacionId": "24",
                        "estado": "1",
                        "children": [
                            {
                                "opcionId": "18",
                                "tipo": "submenu",
                                "nombreObjeto": "Consultar",
                                "descripcion": " Aplicación Módulo de seguridad",
                                "aplicacionId": "24",
                                "estado": "1"
                            }
                        ]
                    },
                    {
                        "opcionId": "19",
                        "tipo": "vinculo",
                        "nombreObjeto": "Cerrar Sesión",
                        "descripcion": " Aplicación Módulo de seguridad",
                        "aplicacionId": "24",
                        "estado": "1"

                    }
                ]

            }


        ];

        return this.getTree(json);

    }



    /* Permite convertir el json de operaciones a un arreglo de tipo TreeNode */
    getTree(json): TreeNode[] {

        json.forEach(element => {
            this.crearNodo(element);
        });

        return json;


    }

    /* Permite convertir un nodo de operación a un nodo de tipo TreeNode*/
    private crearNodo(item) {
        item['label'] = item.nombreObjeto;
        if (item.children != null) {
            this.crearIcono(item);
            if (Object.keys(item.children).length > 0) {
                item.children.forEach(element => {
                    this.crearIcono(item);
                    this.crearNodo(element);
                });
            }
        } else {
            this.crearIcono(item);
        }
    }

    /** Permite crear un ícono dependiendo del tipo de operación */
    private crearIcono(item) {

        switch (item.tipo) {
            case "aplicacion":
                item['data'] = "aplicacion";
                item['expandedIcon'] = "fa-folder-open";
                item['collapsedIcon'] = "fa-folder";
                break;
            case "menu":
                item['data'] = "menu";
                item['expandedIcon'] = "fa fa-caret-square-o-down";
                item['collapsedIcon'] = "fa fa-caret-square-o-right";
                break;
            case "submenu":
                item['data'] = "submenu";
                item['expandedIcon'] = "fa fa-chevron-down";
                item['collapsedIcon'] = "fa fa-chevron-right";
                break;
            case "vinculo":
                item['data'] = "vinculo";
                item['expandedIcon'] = "fa fa-long-arrow-down";
                item['collapsedIcon'] = "fa fa-long-arrow-right";
                break;
            case "boton":
                item['data'] = "boton";
                item['icon'] = "fa fa-dot-circle-o";
                break;
            default:
                item['data'] = "sintipo";
                item['icon'] = "fa fa-file-o";
                break;
        }

    }



    getFiles(): TreeNode[] {




        return [
            {
                "label": "SIGCE",
                "data": "Documents Folder",
                "expandedIcon": "fa-folder-open",
                "collapsedIcon": "fa-folder",
                "children": [{
                    "label": "PMI",
                    "data": "Work Folder",
                    "expandedIcon": "fa-folder-open",
                    "collapsedIcon": "fa-folder",
                    "children": [{ "label": "Mejora tus resultados", "icon": "fa-file-code-o", "data": "Expenses Document" }, { "label": "Seguimiento PMI", "icon": "fa-file-code-o", "data": "Resume Document" }]
                },
                {
                    "label": "PAM",
                    "data": "Home Folder",
                    "expandedIcon": "fa-folder-open",
                    "collapsedIcon": "fa-folder",
                    "children": [{ "label": "Operación", "icon": "fa-file-code-o", "data": "Invoices for this month" }]
                }]
            },
            {
                "label": "SIGAA",
                "data": "Pictures Folder",
                "expandedIcon": "fa-folder-open",
                "collapsedIcon": "fa-folder",
                "children": [
                    { "label": "Operación", "icon": "fa-file-code-o", "data": "Barcelona Photo" },
                    { "label": "Operación", "icon": "fa-file-code-o", "data": "PrimeFaces Logo" },
                    { "label": "Operación", "icon": "fa-file-code-o", "data": "PrimeUI Logo" }]
            },
            {
                "label": "RIEL",
                "data": "Home Folder",
                "expandedIcon": "fa-folder-open",
                "collapsedIcon": "fa-folder",
                "children": [
                    { "label": "Operación", "icon": "fa-file-code-o", "data": "Barcelona Photo" },
                    { "label": "Operación", "icon": "fa-file-code-o", "data": "PrimeFaces Logo" },
                    { "label": "Operación", "icon": "fa-file-code-o", "data": "PrimeUI Logo" }
                ]
            }


        ];
    }


    public consultarEstados(): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.get(URL_BASE + OperacionService.CONSULTAR_ESTADOS, options).catch(Util.handleError);
    }

    public guardar(operacion: Operacion): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(URL_BASE + OperacionService.GUARDAR, operacion, options).catch(Util.handleError);
    }

    public modificar(operacion: Operacion): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.put(URL_BASE + OperacionService.EDITAR, operacion, options).catch(Util.handleError);
    }

    public eliminar(operacion: Operacion): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        if (operacion != null) {
            params.set("operacionId", operacion.opcionId.toString());
            params.set("usuarioId", operacion.usuarioModificacion.toString());
        }
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.delete(URL_BASE + OperacionService.ELIMINAR, options).catch(Util.handleError);
    }

    public consultarPorAplicacion(aplicacionId, aplicacionIdAImportar): Observable<any> {
        let params: URLSearchParams = new URLSearchParams();
        params.set("aplicacionId", aplicacionId);
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + OperacionService.CONSULTAR_POR_APLICACION, options)
            .pipe(
                map((resp: Response) => {
                    const data = resp.json();
                    return data.map(elemento => {
                        this.setNullData(elemento, aplicacionIdAImportar);
                        return elemento;
                    });
                })
            )
            .catch(Util.handleError);
    }

    public importar(operacionesAImportar: OperacionAImportar): Observable<Response> {
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(URL_BASE + OperacionService.IMPORTAR, operacionesAImportar, options).catch(Util.handleError);
    }

    private setNullData(elemento: any, aplicacionId) {
        elemento.aplicaciones = null;
        elemento.fechaCreacion = null;
        elemento.aplicacionId = aplicacionId;
        elemento.ultimaModificacion = null;
        if( elemento.opcionPadre != 0 && elemento.opcionPadre != '0') {
            elemento.opcionPadre = null;
        }
        if(elemento.listadoOperaciones && elemento.listadoOperaciones.length > 0) {
            elemento.listadoOperaciones.forEach(elementInterno => {
                this.setNullData(elementInterno, aplicacionId);
            });
        }
    }

    public consultarOperacioneParaImportar(aplicacionId: string, aplicacionIdAImportar): Observable<Response> {
        let params: URLSearchParams = new URLSearchParams();
        params.set("aplicacionId", aplicacionId);
        let headers = new Headers({
            'Content-Type': 'application/json; charset=UTF-8',
            'access_token': sessionStorage.getItem(TOKEN),
            'client_id': this.CLIENT_ID,
            'user_id': sessionStorage.getItem(USER_ID)
        });
        let options = new RequestOptions({ headers: headers, search: params });
        return this.http.get(URL_BASE + OperacionService.CONSULTAR, options)
        .pipe(
            map((resp: Response) => {
                const data = resp.json();
                return data.map(elemento => {
                    this.setNullData(elemento, aplicacionIdAImportar);
                    return elemento;
                });
            })
        )
        .catch(Util.handleError);
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
    params.set('auditarRolOperacion', 'AUDITAR_OPERACION');
    params.set('usuarioId', sessionStorage.getItem(USER_ID));

    let options = new RequestOptions({ headers: headers , search: params});
    return this.http.post(URL_BASE + OperacionService.AUDITAR_EXPORTAR, null, options);
  }
}
