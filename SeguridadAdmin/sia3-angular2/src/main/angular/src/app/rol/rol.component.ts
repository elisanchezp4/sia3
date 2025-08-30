import { Component, OnInit } from '@angular/core';
import { Message, ConfirmationService } from 'primeng/primeng';
import { NodeService } from './arbol.service';
import { Util } from 'app/util/Util';
import { Mensaje } from 'app/modelo/Mensaje';
import { AplicacionService } from '../aplicacion/aplicacion.service';
import { RolService } from 'app/rol/rol.service';
import { MensajesService } from 'app/services/mensajes.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Rol } from 'app/modelo/Rol';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { PathConstants } from 'app/util/PathConstants';
import { CONFIRMACION_INACTIVAR_ROL, RESPONSE_OK, ERROR_VALIDACION_CAMPOS_OPERACION, APP100115, CARGANDO } from 'app/util/Constants';
import { Usuario } from 'app/modelo/Usuario';
import { LoaderService } from '../spinner/loader.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { OperacionService } from 'app/operacion/operacion.service';
import { Response } from '@angular/http';

@Component({
  selector: 'app-rol',
  templateUrl: './rol.component.html',
  styleUrls: ['./rol.component.css'],
  providers: [NodeService, MensajesService, ConfirmationService, AplicacionService, RolService]
})
export class RolComponent implements OnInit {
  //listado de Roles
  private roles: Rol[] = [];
  //listado de Aplicaciones
  private aplicaciones: Aplicacion[] = [];
  //
  private aplicacion: Aplicacion = new Aplicacion();
  //Rol seleccionado para editar
  private rol: Rol = new Rol();
  //mensajes que se muestran en la pantalla
  private msgs: Message[] = [];
  //mensajes de cargando
  private msgsCargar: Message[] = [];
  private idAplicacion: string;
  private campoError: boolean = false;
  //estado aplicacion
  private estado: string;
  private usuario: Usuario;
  private usuarioId: number;

  public formGroup: FormGroup;
  public submitted: boolean = false;
  public display: boolean = false;
  public displayImportar: boolean = false;
  public file: File;

  public datosRol: Rol;

  // files: TreeNode[];

  // selectedFiles: TreeNode[];

  constructor(private loaderService: LoaderService,
    private nodeService: NodeService,
    private aplicacionService: AplicacionService,
    private rolService: RolService,
    private mensajesService: MensajesService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private operacionService: OperacionService) { }

  ngOnInit() {
    this.msgs = [];
    sessionStorage.removeItem('rol');
    if (sessionStorage.getItem("usuario")) {
      this.usuario = JSON.parse(sessionStorage.getItem("usuario"));
      this.usuarioId = this.usuario.usuarioId;
      this.consultarAplicacionesPorUsuario();
    }
    //this.files = this.nodeService.getFiles();
    this.idAplicacion = "0";
    if (sessionStorage.getItem("idAplicacion") != null && sessionStorage.getItem("idAplicacion") != "0") {
      this.idAplicacion = sessionStorage.getItem("idAplicacion");
      sessionStorage.removeItem("idAplicacion");
      this.consultarRoles();
    }
    let mensaje: Mensaje = JSON.parse(sessionStorage.getItem("mensaje"));
    if (mensaje != null) {
      sessionStorage.removeItem("mensaje");
      Util.mostrarMensaje(this.msgs, mensaje, true);
    }
    this.initExportForm();
  }

  private nuevo() {
    sessionStorage.setItem('idAplicacion', this.idAplicacion)
    this.router.navigate([PathConstants.PATH_CREAR_EDITAR_ROL]);
  }

  private consultarRoles() {
    this.roles = null;
    if (this.validarId()) {
      this.loaderService.display(true);
      this.msgs = [];
      this.rolService.consultarRolesPorApp(this.idAplicacion).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.roles = data.json();
            Util.validarEstado(this.roles);
            //sessionStorage.setItem("idAplicacion", this.idAplicacion);
            this.validarMostrarIconoInactivar();
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
  }


  /* Permite validar si debe mostrar el ícono de inactivar aplicación, de acuerdo al estado*/
  private validarMostrarIconoInactivar() {
    this.roles.forEach(element => {
      if (element.estado == "2") {
        element.rolActivo = false;
      }

    });

  }

  /*Permite seleccionar un Rol de la tabla de roles */
  private seleccionarRol(rolId) {
    this.roles.forEach(element => {
      if (element.rolId == rolId) {
        this.rol = element;
      }
      sessionStorage.setItem('rol', JSON.stringify(this.rol));
      sessionStorage.setItem("idAplicacion", this.idAplicacion);
      this.router.navigate([PathConstants.PATH_CREAR_EDITAR_ROL]);

    });

  }

  /*Permite inactivar un Rol */
  private inactivarRol(rolId) {
    this.roles.forEach(element => {
      if (element.rolId == rolId) {
        this.rol = element;
      }
    });
    let mensaje: Mensaje = new Mensaje();
    this.confirmationService.confirm({
      message: CONFIRMACION_INACTIVAR_ROL,
      accept: () => {
        this.loaderService.display(true);
        this.rolService.inactivar(this.rol).subscribe(
          data => {
            if (data.status == RESPONSE_OK) {
              this.consultarRoles();
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
    });
  }


  private agregarOperaciones(rolId) {
    this.roles.forEach(element => {
      if (element.rolId == rolId) {
        this.rol = element;
      }
    });
    sessionStorage.setItem('rolId', this.rol.rolId);
    sessionStorage.setItem("idAplicacion", this.idAplicacion);
    this.router.navigate([PathConstants.PATH_CREAR_EDITAR_OPERACIONES]);
  }

  /*Permite validar los campos del formulario*/
  private validarId() {
    this.msgs = [];
    let mensaje: Mensaje = new Mensaje();
    mensaje.tipoMensaje = Mensaje.ERROR;
    if (this.idAplicacion == "0") {
      this.campoError = true;
      mensaje.descripcion = ERROR_VALIDACION_CAMPOS_OPERACION;
      Util.mostrarMensaje(this.msgs, mensaje, true);
      return false;
    }
    return true;
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

  private mostrarMensajeCargando(mensaje: string) {
    this.mensajesService.getMensaje(mensaje).subscribe(data => {
      this.msgs = [];
      let mensaje: Mensaje = data.json();
      Util.mostrarMensaje(this.msgsCargar, mensaje, true);
    }, error => {
      this.msgs = [];
      let mensaje: Mensaje = error.json();
      Util.mostrarMensaje(this.msgsCargar, mensaje, true);
    });
  }

  private initExportForm() {
    this.formGroup = new FormGroup({
      id: new FormControl(null, Validators.required),
      nombre: new FormControl('', Validators.required),
      idAplicacion: new FormControl(null, Validators.required),
      rutaDirectorioActivo: new FormControl(null, [Validators.required]),
      idAplicacionOriginal: new FormControl(null, Validators.required),
    });
  }

  public get controls() {
    return this.formGroup.controls;
  }

  public abrirDialogoExportar(idRol) {
    if (this.validarId()) {
      this.msgs = [];
      this.formGroup.get('id').setValue(idRol);
      this.formGroup.get('idAplicacionOriginal').setValue(this.idAplicacion);
      this.display = true;
      this.submitted = false;
    }
  }

  public cancelarDialog() {
    this.formGroup.reset();
    this.display = false;
    this.loaderService.display(false);
    this.submitted = false;
    this.msgs = [];
  }

  public exportar() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      return;
    }
    this.display = false;
    this.loaderService.display(true);
    const rol = this.formGroup.getRawValue();
    this.rolService.auditarExportar(rol.idAplicacion).subscribe();
    rol['idUsuario'] = this.usuarioId;
    const sJson = JSON.stringify(rol);
    const element = document.createElement('a');
    element.setAttribute('href', "data:text/json;charset=UTF-8," + encodeURIComponent(sJson));
    element.setAttribute('download', "Rol.json");
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
    this.loaderService.display(false);
    const mensaje = new Mensaje();
    mensaje.descripcion = "Se ha exportado el archivo correctamente";
    mensaje.tipoMensaje = 'success';
    Util.mostrarMensaje(this.msgs, mensaje, true);
    this.display = false;
  }



  public abrirDialogoImportar() {
    this.msgs = [];
    this.displayImportar = true;
  }

  public cancelarDialogImportar() {
    this.displayImportar = false;
    this.file = null;
    this.loaderService.display(false);
    this.msgs = [];
  }

  public importar() {
    this.msgs = [];
    this.displayImportar = false;
    this.loaderService.display(true);
    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      const rol = JSON.parse(fileReader.result as string);
      this.rolService.importar(rol).subscribe(resp => {
        if (resp.status == RESPONSE_OK) {
          this.loaderService.display(false);
          const mensaje = resp.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.displayImportar = false;
        }
      },
        error => {
          let mensaje = error;
          if (error instanceof Response) {
            mensaje = error.json();
          }
          this.msgs = [];
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.loaderService.display(false);
        });
    }
    fileReader.readAsText(this.file, "UTF-8");
  }

  fileChange(event) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.file = fileList[0];
    }
  }

  selectItem(item: Rol){
    this.formGroup.patchValue({
      nombre: item.nombre,
      idAplicacion: item.aplicacionId,
      rutaDirectorioActivo: item.path
    });
  }

}
