import {Component, OnInit} from '@angular/core';
import {Message, ConfirmationService} from 'primeng/primeng';
import {PathConstants} from 'app/util/PathConstants';
import {AplicacionService} from '../aplicacion.service';
import {Aplicacion} from 'app/modelo/Aplicacion';
import {Estado} from 'app/modelo/Estado';
import {Util} from 'app/util/Util';
import {Mensaje} from 'app/modelo/Mensaje';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {MensajesService} from 'app/services/mensajes.service';
import {Usuario} from 'app/modelo/Usuario';
import {
  RESPONSE_OK,
  ERROR_VALIDACION_CAMPOS_APLICACION,
  ERROR_VALIDACION_CAMPO_DESCRIPCION,
  ERROR_VALIDACION_CAMPO_URLEXITOSA,
  ERROR_VALIDACION_CAMPO_MINUTOCODIGO,
  ERROR_VALIDACION_CAMPO_MINUTOTOKEN,
  ERROR_VALIDACION_CAMPO_MINUTOTOKEN_NUMERO,
  ERROR_VALIDACION_CAMPO_MINUTOCODIGO_NUMERO,
  ERROR_VALIDACION_CAMPO_ESTADO,
  CATALOGO_ESTADO,
  CU003MEN11
} from 'app/util/Constants';
import {LoaderService} from '../../spinner/loader.service';

@Component({
  selector: 'app-crear-editar-aplicacion',
  templateUrl: './crear-editar-aplicacion.component.html',
  styleUrls: ['./crear-editar-aplicacion.component.css'],
  providers: [ConfirmationService, MensajesService]
})
export class CrearEditarAplicacionComponent implements OnInit {

  //Aplicación a editar o crear
  private aplicacion: Aplicacion = new Aplicacion();
  //Estados de la aplicación
  private estados: Estado[] = [];

  private notificacion : Estado[] = [];
  private estado: string;
  //mensajes que se muestran en el dialogo
  private msgsDialog: Message[] = [];
  //mensajes que se muestran en la pantalla
  private msgs: Message[] = [];
  //permite mostrar u ocultar el dialogo
  private mostrarDialogo: boolean = false;
  private campoError: boolean = false;
  private vacio: string = "";
  private usuario: Usuario;
  private usuarioId: number;
  //inactiva boton guardar
  public disableGuardar: boolean;
  public fechaCreacionTmp: Date;


  constructor(private loaderService: LoaderService, private aplicacionService: AplicacionService, private router: Router, private mensajesService: MensajesService, private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.disableGuardar = true;
    if (sessionStorage.getItem("usuario")) {
      this.usuario = JSON.parse(sessionStorage.getItem("usuario"));
      this.usuarioId = this.usuario.usuarioId;
    }

    this.consultaEstados();

    this.aplicacion.estado = "0";
    this.aplicacion.recibirNotificacion = 0;

    if (Util.existeItemSession("aplicacion")) {
      let mensaje: Mensaje = JSON.parse(sessionStorage.getItem("aplicacion"));
      this.aplicacion = JSON.parse(sessionStorage.getItem("aplicacion"));
    }

  }


  /*Permite consultar todos los estados de una aplicación*/
  private consultaEstados() {
    this.loaderService.display(true);
    let tipo = CATALOGO_ESTADO;
    this.aplicacionService.consultarCatalogo(tipo).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.estados = data.json();
          this.notificacion = data.json();
          this.loaderService.display(false);
        }
      },
      error => {
        this.loaderService.display(false);
        this.msgs = [];
        let mensaje: Mensaje = new Mensaje();
        mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
      });
  }

  /*Permite guardar una aplicación*/
  private guardar() {
    this.msgs = [];
    this.loaderService.display(true);
    if (this.validarGuardar()) {
      this.aplicacion.usuarioModificacion = this.usuarioId;
      this.aplicacion.nombre = this.aplicacion.nombre.trim();
      this.aplicacion.urlInicioExitoso = this.aplicacion.urlInicioExitoso.trim();
      this.aplicacion.recibirNotificacion = this.aplicacion.recibirNotificacion.valueOf();
      this.aplicacionService.guardar(this.aplicacion).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.limpiarFormulario();
            let mensaje: Mensaje = new Mensaje();
            this.msgs = [];
            mensaje = data.json();
            sessionStorage.setItem("mensaje", JSON.stringify(mensaje));
            this.router.navigate([PathConstants.PATH_ADMINISTRAR_APLICACION]);
          }
        },
        error => {
          let mensaje: Mensaje = new Mensaje();
          this.msgs = [];
          mensaje = error.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.loaderService.display(false);
        });

    } else {
      this.loaderService.display(false);
    }
  }

  /*Permite guardar una aplicación*/
  private modificar() {
    this.loaderService.display(true);
    if (this.validarModificar() ) {
      this.aplicacion.usuarioModificacion = this.usuarioId;
      this.aplicacion.nombre = this.aplicacion.nombre.trim();
      this.aplicacion.urlInicioExitoso = this.aplicacion.urlInicioExitoso.trim();
      this.aplicacion.fechaCreacion = Util.toDateGrego(this.aplicacion.fechaCreacion.toString());
      this.aplicacion.recibirNotificacion = this.aplicacion.recibirNotificacion.valueOf();
      this.aplicacionService.modificar(this.aplicacion).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.limpiarFormulario();
            let mensaje: Mensaje = new Mensaje();
            this.msgs = [];
            mensaje = data.json();
            sessionStorage.setItem("mensaje", JSON.stringify(mensaje));
            this.router.navigate([PathConstants.PATH_ADMINISTRAR_APLICACION]);
          }
        },
        error => {
          let mensaje: Mensaje = new Mensaje();
          this.msgs = [];
          mensaje = error.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.loaderService.display(false);
        });
    } else {
      this.loaderService.display(false);
    }
  }

  /*Permite volver a la lista de aplicaciones*/
  private volver() {
    this.mensajesService.getMensaje(CU003MEN11).subscribe(
      data => {
        let mensaje: Mensaje = new Mensaje();
        this.msgs = [];
        mensaje = data.json();
        this.confirmationService.confirm({
          message: mensaje.descripcion,
          accept: () => {
            this.limpiarFormulario();
            this.router.navigate([PathConstants.PATH_ADMINISTRAR_APLICACION]);
            if (Util.existeItemSession("aplicacion")) {
              Util.removerItemSession("aplicacion");
            }
          }
        });
      }, error => {
        let mensaje: Mensaje = new Mensaje();
        this.msgs = [];
        mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
      });
  }

  /*Permite validar los campos del formulario*/
  private validarGuardar() {
    let mensaje: Mensaje = new Mensaje();
    this.msgs = [];
    mensaje.tipoMensaje = Mensaje.ERROR;
    if (this.aplicacion.nombre == undefined) {
      this.aplicacion.nombre = "";
    }
    if (this.aplicacion.urlInicioExitoso == undefined) {
      this.aplicacion.urlInicioExitoso = "";
    }
    if (this.aplicacion.minutosVigenciaToken == undefined) {
      this.aplicacion.minutosVigenciaToken = "";
    }
    if (this.aplicacion.minutosVigenciaCodigo == undefined) {
      this.aplicacion.minutosVigenciaCodigo = "";
    }
    if (this.aplicacion.minutosVigenciaToken == "0") {
      this.aplicacion.minutosVigenciaToken = "";
    }
    if (this.aplicacion.minutosVigenciaCodigo == "0") {
      this.aplicacion.minutosVigenciaCodigo = "";
    }
    if (this.aplicacion.minVigTokenActConstrasenia == undefined) {
      this.aplicacion.minVigTokenActConstrasenia = "";
    }
    if (this.aplicacion.minVigTokenActConstrasenia == "0") {
      this.aplicacion.minVigTokenActConstrasenia = "";
    }


    if (this.aplicacion.nombre.trim() == null || this.aplicacion.nombre.trim() == "" || this.aplicacion.urlInicioExitoso.trim() == null || this.aplicacion.urlInicioExitoso.trim() == ""
      || this.aplicacion.estado == "0" || this.aplicacion.minutosVigenciaToken == null || this.aplicacion.minutosVigenciaToken == "" || this.aplicacion.minutosVigenciaToken == "0"
      || this.aplicacion.minutosVigenciaCodigo == null || this.aplicacion.minutosVigenciaCodigo == "" || this.aplicacion.minutosVigenciaCodigo == "0" || this.aplicacion.minVigTokenActConstrasenia == "" || this.aplicacion.minVigTokenActConstrasenia == "0") {
      mensaje.descripcion = ERROR_VALIDACION_CAMPOS_APLICACION;
      this.campoError = true;
      Util.mostrarMensaje(this.msgs, mensaje, true);
      return false;
    }


    return true;
  }

  /*Permite validar los campos del formulario*/
  private validarModificar() {
    let mensaje: Mensaje = new Mensaje();
    this.msgs = [];
    mensaje.tipoMensaje = Mensaje.ERROR;
    if (this.aplicacion.nombre == undefined) {
      this.aplicacion.nombre = "";
    }
    if (this.aplicacion.urlInicioExitoso == undefined) {
      this.aplicacion.urlInicioExitoso = "";
    }
    if (this.aplicacion.minutosVigenciaToken == undefined) {
      this.aplicacion.minutosVigenciaToken = "";
    }
    if (this.aplicacion.minutosVigenciaCodigo == undefined) {
      this.aplicacion.minutosVigenciaCodigo = "";
    }
    if (this.aplicacion.minutosVigenciaToken == "0") {
      this.aplicacion.minutosVigenciaToken = "";
    }
    if (this.aplicacion.minutosVigenciaCodigo == "0") {
      this.aplicacion.minutosVigenciaCodigo = "";
    }
    if (this.aplicacion.minVigTokenActConstrasenia == undefined) {
      this.aplicacion.minVigTokenActConstrasenia = "";
    }
    if (this.aplicacion.minVigTokenActConstrasenia == "0") {
      this.aplicacion.minVigTokenActConstrasenia = "";
    }

    if (this.aplicacion.nombre.trim() == null || this.aplicacion.nombre.trim() == "" || this.aplicacion.urlInicioExitoso.trim() == null || this.aplicacion.urlInicioExitoso.trim() == ""
      || this.aplicacion.estado == "0" || this.aplicacion.minutosVigenciaToken == null || this.aplicacion.minutosVigenciaToken == "" || this.aplicacion.minutosVigenciaToken == "0"
      || this.aplicacion.minutosVigenciaCodigo == null || this.aplicacion.minutosVigenciaCodigo == "" || this.aplicacion.minutosVigenciaCodigo == "0" || this.aplicacion.minVigTokenActConstrasenia == "" || this.aplicacion.minVigTokenActConstrasenia == "0") {
      mensaje.descripcion = ERROR_VALIDACION_CAMPOS_APLICACION;
      this.campoError = true;
      Util.mostrarMensaje(this.msgs, mensaje, true);
      return false;
    }
    return true;
  }



  public verificarNotificacion(){
    if(this.aplicacion.recibirNotificacion == null){
      this.aplicacion.recibirNotificacion == null;
    }else {
      this.aplicacion.recibirNotificacion = this.aplicacion.recibirNotificacion.valueOf();
    }
  }


  public limpiarFormulario() {
    this.aplicacion = new Aplicacion();

  }

  public validarCampoVacio(campo) {
    if (campo != null) {
      let cadena = campo.trim();
      if (cadena == "") {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  /**
   * Metodo que se activa con cada cambio en los cmpos del formulario
   * @param event
   */
  formChange(event) {
    this.disableGuardar = false;
  }
}
