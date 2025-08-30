import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Util } from '../util/Util';
import { URL_BASE } from '../util/Constants';

@Injectable()
export class ConfiguracionInicialFrontService {

  constructor(private http: Http) { }

  /*
  * permite obtener la configuracion inicial de SIUCE
 */
  public getConfiguracionInicial(): Observable<Response> {
    const headers = new Headers({
      'Content-Type': 'application/json; charset=UTF-8'
    });
    const options = new RequestOptions({ headers: headers });
    return this.http.get(URL_BASE + '/configuracion/inicial', options).catch(Util.handleError);
  }

}
