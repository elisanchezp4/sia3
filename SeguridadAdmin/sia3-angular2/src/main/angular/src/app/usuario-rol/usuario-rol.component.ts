import { Component, OnInit } from '@angular/core';
import { Aplicacion } from '../../app/modelo/Aplicacion';
import { AplicacionService } from '../../app/aplicacion/aplicacion.service';
import { RESPONSE_OK, CU008MEN17, CATALOGO_TIPO_USUARIO, CU003MEN11, EXP_REGULAR_EMAIL, APP100115, CARGANDO } from 'app/util/Constants';
import { Mensaje } from '../../app/modelo/Mensaje';
import { MensajesService } from '../../app/services/mensajes.service';
import { Message, DialogModule, Dialog, ConfirmationService } from 'primeng/primeng';
import { Util } from '../../app/util/Util';
import { RolService } from '../../app/rol/rol.service';
import { Rol } from '../../app/modelo/Rol';
import { Usuario } from '../../app/modelo/Usuario';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { PathConstants } from '../../app/util/PathConstants';
import { Estado } from '../../app/modelo/Estado';
import { UsuarioService } from '../usuario/usuario.service';
import { UsuarioRolService } from '../usuario-rol/usuario-rol.service';
import { LoaderService } from '../spinner/loader.service';

@Component({
  selector: 'app-usuario-rol',
  templateUrl: './usuario-rol.component.html',
  styleUrls: ['./usuario-rol.component.css'],
  providers: [MensajesService, AplicacionService, RolService, UsuarioService, UsuarioRolService, ConfirmationService]
})
export class UsuarioRolComponent implements OnInit {

  display: boolean = false;
  private aplicaciones: Aplicacion[];
  private nombreAplicaciones: any[] = [];
  private nombreRoles: any[] = [];
  private usuarios: Usuario[] = [];
  private usuariosPorRol: Usuario[] = [];
  private selectedUsuarioRol: Usuario[] = [];
  //mensajes que se muestran en la pantalla
  private msgs: Message[] = [];
  //mensajes que se muestran en el dialogo
  private msgsDialog: Message[] = [];
  //mensaje de carga aplicacion
  private msgsCargar: Message[] = [];
  private idAplicacion: string;
  private idRol: string;
  private campoError: boolean = false;
  //listado de Roles
  private roles: Rol[] = [];
  selectedUsuario: Array<Usuario>;
  public disableFiltrar: boolean;


  private nombreUsuario: string = '';
  private tipoUsuario: string = '';
  private nombres: string;
  private apellidos: string;
  private tipoUsuarioList: Estado[] = [];
  //estado aplicacion
  private estado: string;
  private nombreTipoUsuario: any[] = [];
  private email: string;
  private campoEmail: boolean = false;
  private usuario: Usuario;
  private usuarioId: number;

  constructor(private loaderService: LoaderService, private aplicacionService: AplicacionService, private mensajesService: MensajesService, private rolService: RolService, private router: Router, private usuarioService: UsuarioService, private usuarioRolService: UsuarioRolService, private confirmationService: ConfirmationService) {
    this.selectedUsuarioRol = [];
  }

  ngOnInit() {
    this.disableFiltrar = true;
    if (sessionStorage.getItem("usuario")) {
      this.usuario = JSON.parse(sessionStorage.getItem("usuario"));
      this.usuarioId = this.usuario.usuarioId;
      this.consultarAplicacionesPorUsuario();
      this.consultarNombreAplicaciones();
    }
    this.idAplicacion = "0";
    this.tipoUsuario = "0";
    this.idRol = "0";
    this.consultarTipoUsuario();
    this.consultarNombreTipoUsuario();
  }

  public asignarRol() {
    if (this.idAplicacion == "0" || this.idRol == "0") {
      this.mostrarMensaje(CU008MEN17);
      this.campoError = true;
    } else {
      this.msgs = [];
      this.display = true;
    }

  }

  //limpiar espacios en blanco
  public limpiarNombresApellidos(nombre: string, apellido : string){
    if(this.nombres !== null && this.nombres !== '' && this.nombres !== undefined) {
      this.nombres = nombre.replace(/\s{2,}/g, ' ').trim();
    }
    if(this.apellidos !== null && this.apellidos !== '' && this.apellidos !== undefined) {
      this.apellidos = apellido.replace(/\s{2,}/g, ' ').trim();
    }
  }

  public consultarUsuariosPorFiltro() {
    this.limpiarNombresApellidos(this.nombres, this.apellidos);
    this.campoEmail = false;
    this.loaderService.display(true);
    this.usuarios = null;
    if (this.email != null && this.email != "") {
      if (this.validarEmail(this.email)) {
        this.campoEmail = true;
        this.loaderService.display(false);
      }
    }
    if (this.idAplicacion == "0" || this.idRol == "0") {
      this.mostrarMensaje(CU008MEN17);
      this.campoError = true;
      this.loaderService.display(false);
    } else {
      this.msgs = [];
      this.mostrarMensajeDialog(CARGANDO);
      this.usuarioService.consultarUsuariosPorFiltroConSesion(this.tipoUsuario, this.nombreUsuario, this.nombres, this.apellidos, this.email, this.usuarioId.toString()).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.msgsDialog = [];
            this.usuarios = data.json();
            this.loaderService.display(false);
          }
        },
        error => {
          this.msgs = [];
          this.msgsDialog = [];
          let mensaje: Mensaje = error.json();
          Util.mostrarMensaje(this.msgsDialog, mensaje, true);
          this.loaderService.display(false);
        });
    }
  }
  public consultarUsuarios() {
    this.loaderService.display(true);
    this.usuariosPorRol = [];
    if (this.idAplicacion == "0" || this.idRol == "0") {
      this.mostrarMensaje(CU008MEN17);
      this.campoError = true;
      this.loaderService.display(false);
    } else {
      this.mostrarMensajeCargando(CARGANDO);
      this.usuarioService.consultarUsuarioRolApp(this.idAplicacion, this.idRol).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.msgsCargar = [];
            this.usuariosPorRol = data.json();
            for (let i of this.usuariosPorRol) {
              if(i.fechaVinculacion !=null){
                i.fechaVinculacion = Util.toDateGrego(i.fechaVinculacion).toString();
              }
            }
            this.loaderService.display(false);
          }
        },
        error => {
          this.msgsCargar = [];
          this.msgs = [];
          let mensaje: Mensaje = error.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.loaderService.display(false);
        });
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

  private consultarRoles(idApp: string) {
    this.loaderService.display(true);
    this.rolService.consultarRolesPorApp(idApp).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.roles = data.json();
          Util.validarEstado(this.roles);
          this.consultarNombreRol(idApp);
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
  public agregarUsuariosARol() {
    this.loaderService.display(true);
    this.usuarioRolService.agregarUsuariosARol(this.selectedUsuario, this.idRol).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.msgs = [];
          let mensaje: Mensaje = new Mensaje();
          mensaje = data.json();
          this.limpiar();
          this.display = false;
          this.consultarUsuarios();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.loaderService.display(false);
        }
      },
      error => {
        this.msgs = [];
        let mensaje: Mensaje = new Mensaje();
        mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  public desvincularUsuarios() {
    this.confirmationService.confirm({
      message: "¿Esta seguro que desea desvincular el usuario al rol?",
      accept: () => {
        this.loaderService.display(true);
        this.usuarioRolService.desvincularUsuarios(this.selectedUsuarioRol, this.idRol).subscribe(
          data => {
            if (data.status == RESPONSE_OK) {
              this.msgs = [];
              let mensaje: Mensaje = new Mensaje();
              mensaje = data.json();
              this.limpiar();
              Util.mostrarMensaje(this.msgs, mensaje, true);
              this.display = false;
              this.consultarUsuarios();
              this.loaderService.display(false);

            }
          },
          error => {
            this.msgs = [];
            let mensaje: Mensaje = new Mensaje();
            mensaje = error.json();
            Util.mostrarMensaje(this.msgs, mensaje, true);
            this.loaderService.display(false);
          });
      }
    });

  }

  public cancelar() {
    this.selectedUsuario = null;
    this.aplicaciones = [];
    this.router.navigate([PathConstants.PATH_INICIO]);
  }

  public limpiar() {
    this.nombreUsuario = '';
    this.tipoUsuario = "0";
    this.nombres = null;
    this.apellidos = null;
    this.usuarios = null;
    this.email = null;
    this.msgsDialog = [];
    this.selectedUsuarioRol = [];
    this.selectedUsuario = null;
    this.disableFiltrar = true;

  }

  public cancelarFiltro() {
    this.display = false;
    this.selectedUsuario = null;
    this.limpiar();
    this.msgsDialog = [];
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
  private mostrarMensajeDialog(mensaje: string) {
    this.mensajesService.getMensaje(mensaje).subscribe(data => {
      this.msgsDialog = [];
      let mensaje: Mensaje = data.json();
      Util.mostrarMensaje(this.msgsDialog, mensaje, true);
    }, error => {
      this.msgsDialog = [];
      let mensaje: Mensaje = error.json();
      Util.mostrarMensaje(this.msgsDialog, mensaje, true);
    });
  }
  private mostrarMensajeCargando(mensaje: string) {
    this.mensajesService.getMensaje(mensaje).subscribe(data => {
      this.msgsCargar = [];
      let mensaje: Mensaje = data.json();
      Util.mostrarMensaje(this.msgsCargar, mensaje, true);
    }, error => {
      this.msgsCargar = [];
      let mensaje: Mensaje = error.json();
      Util.mostrarMensaje(this.msgsCargar, mensaje, true);
    });
  }

  /*Permite consultar el tipo de usuario*/
  private consultarTipoUsuario() {
    this.loaderService.display(true);
    let tipo = CATALOGO_TIPO_USUARIO;
    this.aplicacionService.consultarCatalogo(tipo).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.tipoUsuarioList = data.json();
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

  private consultarNombreRol(idApp: string) {
    this.loaderService.display(true);
    this.rolService.consultarRolesPorApp(idApp).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          let list = data.json();
          for (let r of list) {
            this.nombreRoles[r.rolId] = r.nombre
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
  private consultarNombreTipoUsuario() {
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

  public validarEmail(valor: string) {
    var regex = new RegExp(EXP_REGULAR_EMAIL);
    if (regex.test(valor)) {
      return false;
    } else {
      return true;
    }
  }

  public consultarAplicacionesPorUsuario() {
    this.loaderService.display(true);
    this.estado = "1";
    this.aplicacionService.consultarPorUsuario(this.estado, this.usuarioId).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.aplicaciones = data.json();
          if (this.aplicaciones.length == 0) {
            this.mostrarMensaje(APP100115);
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
    this.estado = "1";
    this.aplicacionService.consultarPorUsuario(this.estado, this.usuarioId).subscribe(
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

  /**
   * Metodo que se activa con cada cambio en los campos del formulario
   * @param event
   */
  formChange() {
    const tipoUsuarioValue = this.tipoUsuario !== undefined ? this.tipoUsuario : '';
    const nombreUsuarioValue = this.nombreUsuario !== undefined ? this.nombreUsuario.trim() : '';

    let camposCompletos = false;

    if (tipoUsuarioValue !== '0' && nombreUsuarioValue !== undefined && nombreUsuarioValue !== '') {
      camposCompletos = true
    }
    this.disableFiltrar = !camposCompletos;
  }

}
