import { Component, OnInit } from '@angular/core';
import { UsuarioService } from './usuario.service';
import { Usuario } from '../../app/modelo/Usuario';
import { Observable } from 'rxjs/Observable';
import { RESPONSE_OK, CU003MEN01, CU003MEN42, CU003MEN10, CU003MEN11, CATALOGO_TIPO_USUARIO, CATALOGO_ESTADO } from '../../app/util/Constants';
import { TooltipModule } from 'primeng/primeng';
import { Message, DialogModule, Dialog, ConfirmationService, SelectItem } from 'primeng/primeng';
import { AplicacionService } from '../../app/aplicacion/aplicacion.service';
import { Aplicacion } from '../../app/modelo/Aplicacion';
import { Operacion } from '../../app/modelo/Operacion';
import { PathConstants } from '../../app/util/PathConstants';
import { Estado } from '../../app/modelo/Estado';
import { Util } from '../../app/util/Util';
import { Mensaje } from '../../app/modelo/Mensaje';
import { MensajesService } from '../../app/services/mensajes.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Rol } from '../../app/modelo/Rol';
import { RolesAsociados } from '../../app/modelo/RolesAsociados';
import { LoaderService } from '../spinner/loader.service';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css'],
  providers: [UsuarioService, ConfirmationService, MensajesService]
})
export class UsuarioComponent implements OnInit {

  //es el listado de Usuarios
  // private usuario: Usuario[] = [];
  private usuario: Usuario = new Usuario;
  private logonName: string;
  private msgs: Message[] = [];
  private msgsD: Message[] = [];
  private vacio: string = "";
  private valido: boolean = false;
  private rol: Rol = new Rol;
  private listaApp: RolesAsociados[];
  private listaRol: SelectItem[];
  private nombreAplicaciones: any[] = [];
  private estados: any[] = [];
  private listaLdap: Usuario[] = [];
  private nombreTipoUsuario: any[] = [];
  //estado aplicacion
  private estado: string;
  //datos sesion usuario
  private usuarioSesion: Usuario;
  private usuarioSesionId: number;
  private statSend: boolean = false;
  display: boolean = false;

  constructor(private loaderService: LoaderService, private usuarioService: UsuarioService, private mensajesService: MensajesService, private router: Router, private confirmationService: ConfirmationService, private aplicacionService: AplicacionService) {
    this.listaApp = [];
    this.listaRol = [];
    this.display = false;
  }

  ngOnInit() {
    if (sessionStorage.getItem("usuario")) {
      this.usuarioSesion = JSON.parse(sessionStorage.getItem("usuario"));
      this.usuarioSesionId = this.usuarioSesion.usuarioId;
    }
    this.consultarNombreAplicaciones();
    this.consultarTipoUsuario();
    this.consultaEstados();
  }

  private consultarUsuario() {
    this.loaderService.display(true);
    if (this.logonName == null || this.logonName == this.vacio) {
      this.loaderService.display(false);
      this.valido = true;
      this.mostrarMensaje(CU003MEN01);
      return;
    }
    this.usuarioService.consultarConSesion(this.logonName, this.usuarioSesionId.toString()).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.loaderService.display(false);
          this.usuario = data.json();
          this.rolesPorUsuario();
          this.statSend = false;
        }
      },
      error => {
        this.loaderService.display(false);
        this.usuario = new Usuario()
        this.rol = new Rol()
        this.listaApp = [];
        this.listaRol = [];
        this.statSend = false;
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
      });
  }

  public activarUsuario() {
    this.mensajesService.getMensaje(CU003MEN42).subscribe(
      data => {
        let mensaje: Mensaje = data.json();
        this.confirmationService.confirm({
          message: mensaje.descripcion,
          accept: () => {
            this.loaderService.display(true);
            this.usuarioService.activarUsuario(this.usuario).subscribe(
              data => {
                if (data.status == RESPONSE_OK) {
                  this.msgs = [];
                  let mensaje: Mensaje = data.json();
                  this.consultarUsuario();
                  Util.mostrarMensaje(this.msgs, mensaje, true);
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
        });
      }, error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }


  public inactivarUsuario() {
    this.mensajesService.getMensaje(CU003MEN10).subscribe(
      data => {
        let mensaje: Mensaje = data.json();
        this.confirmationService.confirm({
          message: mensaje.descripcion,
          accept: () => {
            this.loaderService.display(true);
            this.usuarioService.inactivarUsuario(this.usuario).subscribe(
              data => {
                this.consultarUsuario();
                this.msgs = [];
                let mensaje: Mensaje = data.json();
                Util.mostrarMensaje(this.msgs, mensaje, true);
                this.loaderService.display(false);
              },
              error => {
                this.msgs = [];
                let mensaje: Mensaje = error.json();
                Util.mostrarMensaje(this.msgs, mensaje, true);
                this.loaderService.display(false);
              });
          }
        });
      }, error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  public cancelar() {
    this.msgs = [];
    this.usuario = new Usuario();
    this.router.navigate([PathConstants.PATH_INICIO]);
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

  public rolesPorUsuario() {
    this.loaderService.display(true);
    this.listaApp = [];
    this.listaRol = [];
    this.usuarioService.consultarRolPorUsuario(this.usuario.usuarioId.toString()).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          let rol = data.json();
          let rolcopia = data.json();
          let lista = Util.ordenarObjectsCodigo(rol);
          let roles = [];
          let h = 0;
          let j = 0;
          for (let v of lista) {
            roles[v.rolId] = v.nombre;
            this.listaRol.push({ label: v.nombre, value: v.rolId });
          }
          // se recorre la lista de copia para obtener la posicion en donde trae los usuariosRol
          for (let v of rolcopia) {
            //if la posicion en la que estamos en null suma h
            if (v.usuariosRolDTOs == null) {
              h = h + 1;
            } else {
              //cuando la posicion en la que estamos no es null le asigna el valor de h a j y sale del for
              j = h;
              break;
            }
          }
          let k = 0;
          for (let v of rolcopia[j].usuariosRolDTOs) {
            this.listaApp.push(new RolesAsociados(this.nombreAplicaciones[rolcopia[k].aplicacionId], roles[v[0]], v[2], v[4] != null ? Util.toDateGrego(v[4]).toString() : null, v[3] != null ? Util.toDateGrego(v[3]).toString() : null));
            k = k + 1;
          }
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

  private consultarNombreAplicaciones() {
    this.loaderService.display(true);
    this.estado = "";
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
        }
      },
      error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }
  /*Permite consultar el tipo de usuario*/
  private consultarTipoUsuario() {
    this.loaderService.display(true);
    let tipo = CATALOGO_TIPO_USUARIO;
    this.aplicacionService.consultarCatalogo(tipo).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          let list = data.json();
          for (let t of list) {
            this.nombreTipoUsuario[t.catalogoId] = t.descripcion
          }
          this.loaderService.display(false);
        } else {
          this.msgs = [];
          let mensaje: Mensaje = data.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
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

  /*Permite consultar todos los estados de una aplicación*/
  private consultaEstados() {
    this.loaderService.display(true);
    let tipo = CATALOGO_ESTADO;
    this.aplicacionService.consultarCatalogo(tipo).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          let est = data.json();
          for (let t of est) {
            this.estados[t.catalogoId] = t.descripcion
          }
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

  validarEnviando() {
    this.msgs = [];
    this.loaderService.display(true);
    if (!this.statSend) {
      this.statSend = true;
      this.consultarUsuario();
      this.loaderService.display(false);
      return true;
    } else {
      this.loaderService.display(true);
      return false;
    }
  }

  cargarListaLdap() {
    this.loaderService.display(true);
    this.usuarioService.consultarinactivos().subscribe(
      data => {
        this.listaLdap = data.json();
        this.loaderService.display(false);
        this.display = true;
      }, error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      }
    );
  }

  /**
   * metodo encargado de activar los usuarios de la lista eldap (popup)
   * @param usr valor seleccionado en pantalla
   */
  activarUsrLdap(usr) {
    this.usuarioService.consultarConSesion(usr.sAMAccountName, this.usuarioSesionId.toString()).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.loaderService.display(false);
          let usua = data.json();
          this.usuarioService.activarUsuario(usua).subscribe(
            data => {
              if (data.status == RESPONSE_OK) {
                this.msgs = [];
                let mensaje: Mensaje = data.json();
                this.display = false;
                Util.mostrarMensaje(this.msgs, mensaje, true);
                this.loaderService.display(false);
              }
            },
            error => {
              this.msgsD = [];
              let mensaje: Mensaje = error.json();
              Util.mostrarMensaje(this.msgsD, mensaje, true);
              this.loaderService.display(false);
            });
        }
      },
      error => {
        this.loaderService.display(false);
        this.msgsD = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgsD, mensaje, true);
      });
  }
}
