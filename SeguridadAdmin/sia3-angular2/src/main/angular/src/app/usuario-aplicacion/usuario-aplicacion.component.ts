import { Component, OnInit } from '@angular/core';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import {
  RESPONSE_OK,
  CU008MEN17,
  CATALOGO_TIPO_USUARIO,
  CU003MEN11,
  EXP_REGULAR_EMAIL,
  APP100113,
  PARAMETROSAPP04, APPSIA3
} from 'app/util/Constants';
import { Mensaje } from 'app/modelo/Mensaje';
import { MensajesService } from 'app/services/mensajes.service';
import { Message, DialogModule, Dialog, ConfirmationService } from 'primeng/primeng';
import { Util } from 'app/util/Util';
import { RolService } from 'app/rol/rol.service';
import { Rol } from 'app/modelo/Rol';
import { Usuario } from 'app/modelo/Usuario';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { PathConstants } from 'app/util/PathConstants';
import { Estado } from 'app/modelo/Estado';
import { UsuarioService } from '../usuario/usuario.service';
import { UsuarioAplicacionService } from '../usuario-aplicacion/usuario-aplicacion.service';
import { LoaderService } from '../spinner/loader.service';

@Component({
  selector: 'app-usuario-aplicacion',
  templateUrl: './usuario-aplicacion.component.html',
  styleUrls: ['./usuario-aplicacion.component.css'],
  providers: [MensajesService, AplicacionService, RolService, UsuarioService, UsuarioAplicacionService, ConfirmationService]
})
export class UsuarioAplicacionComponent implements OnInit {

  display: boolean = false;
  private aplicaciones: Aplicacion[];
  private nombreAplicaciones: any[] = [];
  private usuarios: Usuario[] = [];
  private usuariosPorRol: Usuario[] = [];
  //mensajes que se muestran en la pantalla
  private msgs: Message[] = [];
  //mensajes que se muestran en el dialogo
  private msgsDialog: Message[] = [];
  private aplicacionId: string;

  public filtrar: boolean;

  private campoError: boolean = false;
  private listaError: boolean = false;
  //listado de Roles
  private roles: Rol[] = [];
  //estado aplicacion
  private estado: string;
  // lista de usuarios del sistema
  usuariosList: Array<Usuario>;
  usuarioLista: Array<Usuario>;
  // lista que almacena los usuario seleccionados en el datapicker
  usuariosListSelected: Array<Usuario>;
  private aplicacionesSugeridas: Aplicacion[];
  nombreAplicacion: string;

  constructor(private loaderService: LoaderService, private aplicacionService: AplicacionService, private mensajesService: MensajesService, private rolService: RolService, private router: Router, private usuarioService: UsuarioService, private usuarioAppService: UsuarioAplicacionService, private confirmationService: ConfirmationService) { }

  ngOnInit() {
    this.filtrar = true;
    this.aplicacionId = "0";
    this.consultarNombreAplicaciones();
  }

  /**
    * metodo encargado de cargar el listado de usuarios
    */
  public cargarUsuarios() {
    this.msgs = [];
    this.usuariosList = null;
    this.usuariosListSelected = [];
    if(this.aplicacionId == undefined ){
      this.aplicacionId = "0";
    }
    this.loaderService.display(true);
    this.usuariosListSelected = [];
    this.msgs = [];
    if (this.aplicacionId != "0" && this.aplicacionId != null) {
      this.usuarioAppService.consultarUsuarioPorApp(APPSIA3, undefined).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.usuariosList = data.json();
            this.loaderService.display(false);
          }
          if (this.aplicacionId != "0") {
            this.usuarioAppService.consultarUsuarioPorApp(APPSIA3, this.aplicacionId).subscribe(
              data => {
                if (data.status == RESPONSE_OK) {
                  this.usuarioLista = data.json();

                  for (var j = 0; j < this.usuarioLista.length; j++) {
                    for (var i = 0; i < this.usuariosList.length; i++) {
                      //Se carga la lista de los seleccionados.
                      if (this.usuarioLista[j].usuarioId == this.usuariosList[i].usuarioId) {
                        this.usuariosListSelected.push(this.usuariosList[i]);
                        this.usuariosList.splice(i, 1);
                        i--;
                      }
                    }
                  }
                  this.loaderService.display(false);
                }
              },
              error => {
                this.msgs = [];
                let mensaje: Mensaje = error.json();
                Util.mostrarMensaje(this.msgs, mensaje, true);
                this.loaderService.display(false);
              }
            );
          }
        },
        error => {
          this.msgs = [];
          let mensaje: Mensaje = error.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.loaderService.display(false);
        });
    } else {
      this.mostrarMensaje(PARAMETROSAPP04);
      this.campoError = true;
      this.loaderService.display(false);
    }
  }

  public asignarUsuarios() {
    this.loaderService.display(true);
   if(this.aplicacionId == undefined){
      this.aplicacionId = "0";
    }
    if (this.aplicacionId == "0") {
      this.mostrarMensaje(CU008MEN17);
      this.campoError = true;
      this.loaderService.display(false);
    } else {
      if (this.usuariosListSelected.length == 0) {
        this.mostrarMensaje(APP100113);
        this.listaError = true;
        this.loaderService.display(false);
      } else {
        this.usuarioAppService.asignarUsuarioApp(this.usuariosListSelected, this.aplicacionId).subscribe(
          data => {
            if (data.status == RESPONSE_OK) {
              this.msgs = [];
              let mensaje: Mensaje = new Mensaje();
              mensaje = data.json();
              Util.mostrarMensaje(this.msgs, mensaje, true);
              this.loaderService.display(false);
            }
          },
          error => {
            this.msgs = [];
            let mensaje: Mensaje = new Mensaje();
            mensaje = error.json();
            Util.mostrarMensaje(this.msgsDialog, mensaje, true);
            this.loaderService.display(false);
          });
      }
    }

  }


  public consultarAplicaciones() {
    this.loaderService.display(true);
    this.estado = "1";
    this.aplicacionService.consultar(this.estado).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.aplicaciones = data.json();
          this.loaderService.display(false);
        }
      },
      error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  public cancelar() {
    this.mensajesService.getMensaje(CU003MEN11).subscribe(
      data => {
        let mensaje: Mensaje = data.json();
        this.confirmationService.confirm({
          message: mensaje.descripcion,
          accept: () => {
            this.usuariosListSelected = [];
            this.aplicaciones = [];
            this.router.navigate([PathConstants.PATH_INICIO]);
          }
        });
      }, error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });

  }

  private mostrarMensaje(mensaje: string) {
    this.mensajesService.getMensaje(mensaje).subscribe(data => {
      this.msgs = [];
      let mensaje: Mensaje = data.json();
      Util.mostrarMensaje(this.msgs, mensaje, true);
    }, error => {
      this.msgs = [];
      let mensaje: Mensaje = error.json();
      Util.mostrarMensaje(this.msgs, mensaje, true);
    });
  }

  private consultarNombreAplicaciones() {
    this.loaderService.display(true);
    this.estado = "1";
    this.aplicacionService.consultar(this.estado).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          let list = data.json();
          for (let a of list) {
            this.nombreAplicaciones[a.aplicacionId] = a.nombre
          }
          this.loaderService.display(false);
        } else {
          this.msgs = [];
          let mensaje: Mensaje = data.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.loaderService.display(false);
          this.filtrar = true;
        }
      },
      error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });

  }

  public autocompletarAplicacion(event: any) {
    this.loaderService.display(true);
    this.estado = "1";
    this.aplicacionService.consultarAplicacionesSugeridadas(this.estado, event.query).subscribe(
      data => {
        this.aplicacionesSugeridas = data.json();
        this.loaderService.display(false);
      },
      error => {
        this.usuariosListSelected = [];
        this.usuariosList = null;
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      }
    );
  }

  public seleccionarAplicacion(aplicacion) {
    if (aplicacion.nombre != null && aplicacion.nombre != undefined) {
      this.aplicacionId = aplicacion.aplicacionId;
      this.nombreAplicacion = aplicacion.nombre;
    }
  }
  /**
   * Metodo que se activa con cada cambio en los cmpos del formulario
   * @param event
   */
  formChange(event) {
    if (event.nombre && !this.nombreAplicaciones[event.nombre]) {
      this.filtrar = false;
    } else {
      this.aplicacionId = "0";
      this.filtrar = true;
    }
  }
}
