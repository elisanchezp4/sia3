import { Message } from 'primeng/primeng';
import { Mensaje } from '../modelo/Mensaje';
import { SEPARADOR_FORMATO_DD_MM_ANIO, SEPARADOR_FORMATO_DD_MM_ANIO_2, ERROR_EN_PETICION } from './Constants';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
import { Response } from '@angular/http';

export class Util {

    constructor() {

    }

    /*
    permite mostrar mensajes en la pantalla
    msg: arreglo de mensajes de primeng
    mensaje: mensaje que se desea mostrar
    limpiar: indica si debe o no borrar los mensajes anteriores
    */
    public static mostrarMensaje(msgs: Message[], mensaje: Mensaje, limpiar: boolean) {

        if (limpiar) {
            let tasm = msgs.length - 1;
            msgs.splice(0, tasm);

        }

        //--->condicional agregado para solucionar el problema del mensaje que no desaparece
        if(mensaje.descripcion!==undefined){
          msgs.push({ severity: mensaje.tipoMensaje, summary: '', detail: mensaje.descripcion });
        }

    }

    public static removerItemSession(item: string) {
        if (sessionStorage.length > 0) {
            for (var index = 0; index < sessionStorage.length; index++) {
                if (sessionStorage.key(index) == item) {
                    sessionStorage.removeItem(item);
                }
            }
        }
    }

    public static existeItemSession(item: string) {
        if (sessionStorage.length > 0) {
            for (var index = 0; index < sessionStorage.length; index++) {
                if (sessionStorage.key(index) == item) {
                    return true;
                }
            }
        }

        return false;
    }

    public static validarEstado(objeto: any[]) {
        objeto.forEach(element => {
            if (element.estado == 1) {
                element.nombreEstado = "Activo";
            } else {
                element.nombreEstado = "Inactivo";
            }
        });
    }

    public static getCalendarLenguaje() {
        let es = {
            firstDayOfWeek: 0,
            dayNames: ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado"],
            dayNamesShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vi", "Sab"],
            dayNamesMin: ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
            monthNames: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
            monthNamesShort: ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"]
        };
        return es;
    }

    public static toDate(cadena: string): Date {
        let fecha: Date = null;
        let partes = cadena.split(SEPARADOR_FORMATO_DD_MM_ANIO);
        fecha = new Date();
        fecha.setFullYear(parseInt(partes[2]));
        fecha.setMonth(parseInt(partes[1]) - 1);
        fecha.setDate(parseInt(partes[0]));
        return fecha;;
    }

    public static toString(fecha: Date): string {
        let cadena: string = "";
        if (fecha != null) {
            let dia: string = fecha.getDate() < 10 ? "0" + fecha.getDate() : fecha.getDate() + "";
            let month: number = fecha.getMonth() + 1;
            let mes: string = month < 10 ? "0" + month : month + "";

            cadena = dia + "/" + mes + "/" + fecha.getFullYear();
        }
        return cadena;
    }

    /**metodo encargado de ordenar una lista*/
    public static ordenarObjectsCodigo(lista: any) {
        for (var pass = 1; pass < lista.length; pass++) {
            for (var left = 0; left < (lista.length - pass); left++) {
                var right = left + 1;
                if (parseInt(lista[left].aplicacionId) > parseInt(lista[right].aplicacionId)) {
                    var tmpVal = lista[left];
                    lista[left] = lista[right];
                    lista[right] = tmpVal;
                }
            }
        }
        return lista;
    }

    /**metodo encargado de convertir una fecha en formato json a dd/MM/yyyy  juagomez*/
    public static toDateofTodate(fechaJson: string) {
        let fecha: Date = new Date(fechaJson);
        let month = fecha.getMonth() + 1;
        let day = fecha.getDate();
        let year = fecha.getFullYear();
        let date = day + "/" + month + "/" + year;
        let res: Date = new Date(year, month, day)
        return res;
    }

    public static toDateGrego(cadena: string): Date {
        let fecha: Date = null;
        let partes = cadena.split(SEPARADOR_FORMATO_DD_MM_ANIO_2);
        fecha = new Date();
        fecha.setFullYear(parseInt(partes[0]));
        fecha.setMonth(parseInt(partes[1]) - 1);
        fecha.setDate(parseInt(partes[2]));
        return fecha;;
    }

    /**metodo encargado de convertir una fecha en formato json a dd/MM/yyyy juagomez*/
    public static toDateofJSON(fechaJson: string) {
        let fecha: Date = new Date(fechaJson);
        fecha.setMilliseconds(100000000);
        let month = fecha.getMonth() < 10 ? "0" + (fecha.getMonth() + 1) : (fecha.getMonth() + 1);
        let day = fecha.getDate() < 10 ? "0" + (fecha.getDate() - 1) : fecha.getDate() - 1 + "";
        let hour = fecha.getHours() < 10 ? "0" + fecha.getHours() : fecha.getHours();
        let minutes = fecha.getMinutes() < 10 ? "0" + fecha.getMinutes() : fecha.getMinutes();
        let seconds = fecha.getSeconds() < 10 ? "0" + fecha.getSeconds() : fecha.getSeconds();
        let year = fecha.getFullYear();
        let date = day + "/" + month + "/" + year + " " + hour + ":" + minutes + ":" + seconds;
        return date;
    }


    public  static  handleError(error:  Response  |  any) {
        let  response;
        if  (error  instanceof  Response) {
            switch  (error.status) {
                case  400:
                    response  =  error  ||  '';
                    break;
                case  401:

                    let  uri  =  localStorage.getItem('redirect');
                    this.clearSession();
                    window.location.href = uri;
                    break;
                default:
                    response  =  error;
            }
        }  else  {
            response  =  error;
        }
        return  Observable.throw(response);
    }

    private static clearSession(){
      sessionStorage.clear();
    }


}
