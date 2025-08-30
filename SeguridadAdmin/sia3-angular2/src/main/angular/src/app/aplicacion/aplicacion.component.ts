import { Component, OnInit } from '@angular/core';
import { Message, ConfirmationService } from 'primeng/primeng';
import { AplicacionService } from './aplicacion.service';
import { Aplicacion } from '../modelo/Aplicacion';
import { PathConstants } from '../util/PathConstants';
import { Estado } from '../modelo/Estado';
import { Util } from '../util/Util';
import { Mensaje } from '../modelo/Mensaje';
import { MensajesService } from './../services/mensajes.service';
import { RESPONSE_OK, CONFIRMACION_INACTIVAR_APLICACION, CARGANDO } from '../util/Constants';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { LoaderService } from '../spinner/loader.service';

@Component({
  providers: [AplicacionService, MensajesService, ConfirmationService],
  selector: 'app-aplicacion',
  templateUrl: './aplicacion.component.html',
  styleUrls: ['./aplicacion.component.css']
})

export class AplicacionComponent implements OnInit {
  public isRequesting: boolean;
  //mensajes que se muestran en la pantalla 
  private msgs: Message[] = [];
  //listado de Aplicaciones
  private aplicaciones: Aplicacion[] = [];
  //Aplicación
  private aplicacion: Aplicacion = new Aplicacion();
  //permite mostrar u ocultar el dialogo
  private mostrarDialogo: boolean = false;
  //mensajes que se muestran en el dialogo
  private msgsDialog: Message[] = [];
  //mensajes de cargando
  private msgsCargar: Message[] = [];

  //estado aplicacion
  private estado: string;

  constructor(private loaderService: LoaderService, private mensajesService: MensajesService, private aplicacionService: AplicacionService, private router: Router, private confirmationService: ConfirmationService) { }

  ngOnInit() {
    this.loaderService.display(true);
    sessionStorage.removeItem('aplicacion');
    let mensaje: Mensaje = JSON.parse(sessionStorage.getItem("mensaje"));
    if (mensaje != null) {
      sessionStorage.removeItem("mensaje");
      Util.mostrarMensaje(this.msgs, mensaje, true);
    }
    this.consultarAplicaciones();
  }
  private mostrarMensaje(mensaje: string) {
    this.mensajesService.getMensaje(mensaje).subscribe(data => {
      let mensaje: Mensaje = new Mensaje();
      this.msgs = [];
      mensaje = data.json();
      Util.mostrarMensaje(this.msgs, mensaje, true);
    }, error => {
      let mensaje: Mensaje = new Mensaje();
      this.msgs = [];
      mensaje = error.json();
      Util.mostrarMensaje(this.msgs, mensaje, true);
      this.loaderService.display(false);
    });
  }

  /*Permite consultar todas las aplicaciones*/
  private consultarAplicaciones() {
    this.loaderService.display(true);
    this.estado = "";
    this.isRequesting = true;
    this.aplicacionService.consultar(this.estado).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.aplicaciones = data.json();
          for (let i of this.aplicaciones) {
            i.fechaCreacion = Util.toDateGrego(i.fechaCreacion.toString());
            i.ultimaModificacion = Util.toDateGrego(i.ultimaModificacion).toString();
          }
          this.stopRefreshing();
          Util.validarEstado(this.aplicaciones);
          this.validarMostrarIconoInactivar();
          this.loaderService.display(false);
        }
      },
      error => {
        let mensaje: Mensaje = new Mensaje();
        this.msgs = [];
        mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  private stopRefreshing() {
    this.isRequesting = false;
  }

  /* Permite validar si debe mostrar el ícono de inactivar aplicación, de acuerdo al estado*/
  private validarMostrarIconoInactivar() {
    this.aplicaciones.forEach(element => {
      if (element.estado == "2") {
        element.aplicacionActiva = false;
      }
    });
  }

  private nuevo() {
    this.router.navigate([PathConstants.PATH_CREAR_EDITAR_APLICACION]);
  }

  /*Permite seleccionar una aplicación de la tabla de aplicaciones */
  private seleccionarAplicacion(aplicacionId) {
    this.aplicaciones.forEach(element => {
      if (element.aplicacionId == aplicacionId) {
        this.aplicacion = element;
      }
      sessionStorage.setItem('aplicacion', JSON.stringify(this.aplicacion));
      this.router.navigate([PathConstants.PATH_CREAR_EDITAR_APLICACION]);
    });

  }

  private inactivarAplicacion(aplicacionId) {
    this.aplicaciones.forEach(element => {
      if (element.aplicacionId == aplicacionId) {
        this.aplicacion = element;
      }
    });
    let mensaje: Mensaje = new Mensaje();
    this.confirmationService.confirm({
      message: CONFIRMACION_INACTIVAR_APLICACION,
      accept: () => {
    this.loaderService.display(true);
        this.aplicacionService.inactivar(this.aplicacion).subscribe(
          data => {
            if (data.status == RESPONSE_OK) {
              this.consultarAplicaciones();
              let mensaje: Mensaje = new Mensaje();
              this.msgs = [];
              mensaje = data.json();
              Util.mostrarMensaje(this.msgs, mensaje, true);
              this.loaderService.display(false);
            }
          },
          error => {
            this.loaderService.display(false);
            let mensaje: Mensaje = new Mensaje();
            this.msgs = [];
            mensaje = error.json();
            Util.mostrarMensaje(this.msgs, mensaje, true);
          });
      }
    });
  }

  private mostrarMensajeCargando(mensaje: string) {
    this.mensajesService.getMensaje(mensaje).subscribe(data => {
      let mensaje: Mensaje = new Mensaje();
      this.msgs = [];
      mensaje = data.json();
      Util.mostrarMensaje(this.msgsCargar, mensaje, true);
    }, error => {
      let mensaje: Mensaje = new Mensaje();
      this.msgs = [];
      mensaje = error.json();
      Util.mostrarMensaje(this.msgsCargar, mensaje, true);
    });
  }

} 