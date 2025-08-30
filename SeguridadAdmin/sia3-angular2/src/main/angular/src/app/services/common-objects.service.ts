import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { URL_PARAMETROS } from '../util/Constants';



/*
 * Este servicio provee los metodos para conectarse con el servicio rest de la 
 * parametrización de tarifas
 */
@Injectable()
export class CommonObjectsService {

  private static URL_BASE_VALORES_SIMPLE = sessionStorage.getItem(URL_PARAMETROS) + "rest/ConsultaItemListaValores/ValoresSimple";
  private static URL_RESOURCE = "?nombreApp=SIA3&dominio=URL_SEGURIDAD";
  /*
   * constructor al que se inyecta el servicio de http 
   * para realizar peticiones al servidor 
   */
  constructor(private http: Http) {
  }

  /*
  * permite  obtener la url para la redireccion
  */

}
