import { Response, ResponseOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';

export class Dummy {

    constructor() { }

   public static getDummyRequest(succesJson:string,errorJson:string,success:boolean):Observable<Response>{
      return Observable.create(observer => {
      if (success) {
        var options = new ResponseOptions({
          body: succesJson
        });
        let response: Response = new Response(options);
        response.status=200;
        observer.next(response);
        observer.complete();
      } else {
        var options = new ResponseOptions({
          body: errorJson
        });
        let response: Response = new Response(options);
        response.status=404;
        observer.error(response);
      }
    });
   }
}