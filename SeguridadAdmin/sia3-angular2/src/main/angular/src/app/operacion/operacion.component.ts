import { Component, ViewChild, OnInit  } from '@angular/core';
import { OperacionService } from './operacion.service';
import { ContextMenuService } from 'app/services/context-menu.service';
import { TooltipModule } from 'primeng/primeng';
import { Tree, TreeNode, MenuItem, Message, ContextMenu, DialogModule, Dialog, ConfirmationService } from 'primeng/primeng';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { Operacion } from 'app/modelo/Operacion';
import { PathConstants } from 'app/util/PathConstants';
import { Estado } from 'app/modelo/Estado';
import { Util } from 'app/util/Util';
import { Mensaje } from 'app/modelo/Mensaje';
import { MensajesService } from 'app/services/mensajes.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ESTADO_ACTIVO, CU007, RESPONSE_OK, ERROR_VALIDACION_CAMPOS_OPERACION, CONFIRMACION_INACTIVAR_OPERACION, APP100115, CARGANDO, ERROR_VALIDACION_CAMPO_ORDEN_VISUALIZACION } from 'app/util/Constants';
import { Usuario } from 'app/modelo/Usuario';
import { LoaderService } from '../spinner/loader.service';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';


@Component({
  selector: 'app-operacion',
  templateUrl: './operacion.component.html',
  styleUrls: ['./operacion.component.css'],
  providers: [OperacionService, AplicacionService, MensajesService, ConfirmationService, ContextMenuService]
})
export class OperacionComponent implements OnInit {
  //mensajes que se muestran en la pantalla
  private msgs: Message[] = [];
  //listado de Aplicaciones
  private aplicaciones: Aplicacion[] = [];
  //listado de Aplicaciones del arbol
  private aplicacionArbol: any[] = [];
  private aplicacionSeleccionada: Aplicacion[] = [];
  //Aplicación
  private aplicacion: Aplicacion = new Aplicacion();
  //Operacion para crear o editar
  private operacion: Operacion = new Operacion();
  //Operacion para crear o editar
  private operacionSeleccionada: Operacion = new Operacion();
  //permite mostrar u ocultar el dialogo
  private mostrarDialogo: boolean = false;
  //mensajes que se muestran en el dialogo
  private msgsDialog: Message[] = [];
  //Estados de la operacion
  private estados: Estado[] = [];
  //Indicador de mostrar o coultar el modal
  display: boolean = false;
  //estado aplicacion
  private estado: string;
  arbolOperaciones: TreeNode[];

  aplicacionHabilitada: boolean = false;
  private campoError: boolean = false;
  private vacio: string = "";
  private filtroApi: boolean;
  nodoSeleccionado: TreeNode;
  menu_contextual: MenuItem[];
  private idAplicacion: string;
  private nombreObjeto: string;
  private nombreAplicaciones: any[] = [];
  private usuario: Usuario;
  private usuarioId: number;
  //inactiva boton guardar
  public disableGuardar: boolean;
  public tieneHijos: boolean;
  public activeGif: boolean;

  public formGroup: FormGroup;
  public submitted: boolean = false;
  public displayExportar: boolean = false;
  public displayImportar: boolean = false;
  public file: File;
  public displayMensajeValidarImportar: boolean = false;

  @ViewChild('cm') cm: any;

  constructor(private loaderService: LoaderService,
    private mensajesService: MensajesService,
    private operacionService: OperacionService,
    private aplicacionService: AplicacionService,
    private confirmationService: ConfirmationService,
    private contextMenuService: ContextMenuService) { }

  ngOnInit() {
    this.disableGuardar = true;
    if (sessionStorage.getItem("usuario")) {
      this.usuario = JSON.parse(sessionStorage.getItem("usuario"));
      this.usuarioId = this.usuario.usuarioId;
      this.consultarAplicacionesPorUsuario();
    }
    this.operacion = new Operacion();;
    this.operacion.tipo = "0";
    this.operacion.aplicacionId = "0";
    this.idAplicacion = "0";
    this.filtroApi = false;
    this.tieneHijos = false;
    this.menu_contextual = [
      { label: 'Agregar', icon: 'fa fa-plus', command: (event) => this.agregarOperacion(this.nodoSeleccionado) },
      { label: 'Modificar', icon: 'fa fa-pencil', command: (event) => this.modificarOperacion(this.nodoSeleccionado) },
      { label: 'Eliminar', icon: 'fa fa-trash-o', command: (event) => this.eliminarOperacion(this.nodoSeleccionado) }
    ];
    this.consultarNombreAplicaciones();
    this.initExportForm();
  }

  /*openContextMenu(event: MouseEvent) {
    event.preventDefault();
    const target = event.target as HTMLElement;
    const rect = target.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    this.cm.show(event, 'right', { top: y + 'px', left: x + 'px' });
  }*/


  /*Permite mostra el menu contextual al hacer clic derecho sobre cualquier operación*/
  private onRightClick(event: MouseEvent) {
    this.contextMenuService.show.next({ event: event, obj: this.menu_contextual });
    event.preventDefault();
  }

  /*Permite agregar una operación desde el formulario modal*/
  private agregarOperacion(operacionSeleccionada) {
    this.msgsDialog = [];
    this.tieneHijos = false;
    this.operacionSeleccionada = operacionSeleccionada;
    this.operacion.aplicacionId = this.operacionSeleccionada.aplicacionId;
    this.aplicacionHabilitada = true;
    this.validarTipo();
    this.isActiveGif();
  }

  public isActiveGif(){
    if(this.operacion.tipo == "MENU" || this.operacion.tipo == "SUBMENU"){
      this.activeGif = true;
    }else{
      this.activeGif = false;
    }
  }

  private validarTipo() {
    if (this.nodoSeleccionado.data == "aplicacion") {
      this.display = false;
    } else {
      this.display = true;
    }
  }

  /*Permite editar una operación desde el formulario modal*/
  private modificarOperacion(operacionSeleccionada) {

    this.isActiveGif();
    this.msgs = [];
    this.msgsDialog = [];
    this.tieneHijos = false;
    if (operacionSeleccionada.tipo != "APLICACION") {
      if (operacionSeleccionada.children) {
        this.tieneHijos = true;
      }
      this.operacion = operacionSeleccionada;
      this.aplicacionHabilitada = false;
      this.validarTipo();
    } else {
      let mensaje: Mensaje = new Mensaje;
      mensaje.codigo = "01";
      mensaje.tipoMensaje = "error";
      mensaje.descripcion = "No es posible modificar una aplicación";      Util.mostrarMensaje(this.msgs, mensaje, true);
      return null;
    }
  }

  /*Permite eliminar una operacion*/
  private eliminarOperacion(operacionSeleccionada) {
    this.msgs = [];
    this.msgsDialog = [];
    let i = operacionSeleccionada.parent;
    if (i == undefined) {
      let mensaje: Mensaje = new Mensaje;
      mensaje.codigo = "01";
      mensaje.tipoMensaje = "error";
      mensaje.descripcion = "La carpeta raiz de aplicación no se puede eliminar";
      Util.mostrarMensaje(this.msgs, mensaje, true);
      return null;
    }
    let tam = 0;
    for (let f of i.listadoOperaciones) {
      if (f.tipo == "MENU") {
        tam = tam + 1;
      }
    }
    if (tam <= 1 && operacionSeleccionada.data == "MENU") {
      let mensaje: Mensaje = new Mensaje;
      mensaje.codigo = "01";
      mensaje.tipoMensaje = "error";
      mensaje.descripcion = "No se puede eliminar la operación seleccionada. La aplicación siempre debe tener por lo menos una operación tipo menú creada.";
      Util.mostrarMensaje(this.msgs, mensaje, true);
      return null;
    }
    this.confirmationService.confirm({
      message: CONFIRMACION_INACTIVAR_OPERACION,
      accept: () => {
        this.loaderService.display(true);
        this.operacionSeleccionada = operacionSeleccionada;
        if (this.nodoSeleccionado.data != "aplicacion") {
          this.operacion.opcionId = this.operacionSeleccionada.opcionId;
          this.operacion.usuarioModificacion = this.usuarioId;
          this.operacionService.eliminar(this.operacion).subscribe(
            data => {
              this.operacion.opcionId = undefined;
              let mensaje: Mensaje = data.json();
              Util.mostrarMensaje(this.msgs, mensaje, true);
              this.filtrarOperacion();
              this.loaderService.display(false);
            },
            error => {
              let mensaje: Mensaje = error.json();
              Util.mostrarMensaje(this.msgs, mensaje, true);
              this.loaderService.display(false);
            });
        }
      }
    });

  }

  public guardarNodoBase() {
    this.loaderService.display(true);
    this.msgsDialog = [];
    this.operacion.aplicacionId = this.idAplicacion;
    this.operacion.descripcion = "Inicio por Default";
    this.operacion.nombreObjeto = "Inicio " + this.nombreAplicaciones[this.idAplicacion];
    this.operacion.opcionPadre = null;
    this.operacion.tipo = "MENU";
    this.operacion.estado = ESTADO_ACTIVO;
    this.operacion.usuarioModificacion = this.usuarioId;
    this.operacion.ordenVisualizacion = 0;
    this.operacionService.guardar(this.operacion).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.operacion = null;
          this.cancelar();
          let mensaje: Mensaje = data.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.filtrarOperacion();
        }
      },
      error => {
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgsDialog, mensaje, true);
        this.loaderService.display(false);
      });
  }

  /*Permite guardar una operacion*/
  private guardar() {
     this.msgs = [];
    this.loaderService.display(true);
    this.msgsDialog = [];
    if (this.validar()) {
      this.operacion.usuarioModificacion = this.usuarioId;
      switch (this.operacion.opcionId) {
        case undefined://Guardar
          if (this.operacionSeleccionada.tipo == "APLICACION") {
            this.operacion.opcionPadre = null;
          } else {
            this.operacion.opcionPadre = this.operacionSeleccionada.opcionId;
          }
          this.operacion.aplicacionId = this.operacionSeleccionada.aplicacionId;
          this.operacion.estado = ESTADO_ACTIVO;
          this.operacion.nombreObjeto = this.operacion.nombreObjeto.trim();
      this.operacion.descripcion = this.operacion.descripcion.trim();
      if(this.operacion.urlImgGif != undefined){
        this.operacion.urlImgGif = this.operacion.urlImgGif.trim();
      }else{
        this.operacion.urlImgGif = null;

      }


          this.operacionService.guardar(this.operacion).subscribe(
            data => {
              if (data.status == RESPONSE_OK) {
                //this.operacion = null;
                this.cancelar();
                let mensaje: Mensaje = data.json();
                Util.mostrarMensaje(this.msgs, mensaje, true);
                this.filtrarOperacion();
              }
            },
            error => {
              let mensaje: Mensaje = error.json();
              Util.mostrarMensaje(this.msgsDialog, mensaje, true);
              this.loaderService.display(false);
            });
          break;

        default://Modificar
          this.operacionSeleccionada.aplicacionId = this.operacion.aplicacionId;
          this.operacionSeleccionada.descripcion = this.operacion.descripcion.trim();
          this.operacionSeleccionada.nombreObjeto = this.operacion.nombreObjeto.trim();
          this.operacionSeleccionada.opcionId = this.operacion.opcionId;
          this.operacionSeleccionada.opcionPadre = this.operacion.opcionPadre;
          this.operacionSeleccionada.tipo = this.operacion.tipo;
          this.operacionSeleccionada.estado = ESTADO_ACTIVO;
          this.operacionSeleccionada.ordenVisualizacion = this.operacion.ordenVisualizacion;
		      this.operacionSeleccionada.usuarioModificacion = this.aplicacion.usuarioModificacion = this.usuarioId;
		      this.operacionSeleccionada.urlImgGif = this.operacion.urlImgGif;
          this.operacionService.modificar(this.operacionSeleccionada).subscribe(
            data => {
              this.operacion = null;
              let mensaje: Mensaje = data.json();
              Util.mostrarMensaje(this.msgs, mensaje, true);
              this.cancelar();
              this.filtrarOperacion();
            },
            error => {
              let mensaje: Mensaje = error.json();
              Util.mostrarMensaje(this.msgsDialog, mensaje, true);
              this.loaderService.display(false);
            });
          break;
      }
    }
  }
  /*Permite validar los campos del formulario*/
  private validar() {
    let mensaje: Mensaje = new Mensaje();
    mensaje.tipoMensaje = Mensaje.ERROR;

    if (this.operacion.tipo == null || this.operacion.tipo == "0" || this.operacion.nombreObjeto == null || this.operacion.nombreObjeto == ""
      || this.operacion.descripcion == null || this.operacion.descripcion == "" || this.operacion.ordenVisualizacion == null || this.operacion.ordenVisualizacion == 0 || this.operacion.ordenVisualizacion < 0) {
      this.campoError = true;
      mensaje.descripcion = ERROR_VALIDACION_CAMPOS_OPERACION;
      Util.mostrarMensaje(this.msgsDialog, mensaje, true);
      return false;
    }
    if (this.operacion.ordenVisualizacion > 999) {
      this.campoError = true;
      mensaje.descripcion = ERROR_VALIDACION_CAMPO_ORDEN_VISUALIZACION;
      Util.mostrarMensaje(this.msgsDialog, mensaje, true);
      return false;
    }
    return true;
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
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  private cancelarDialog() {
    this.confirmationService.confirm({
      message: CU007,
      accept: () => {
        this.cancelar();
      }
    });
  }

  private deleteOperacion() {
    this.confirmationService.confirm({
      message: CONFIRMACION_INACTIVAR_OPERACION,
      accept: () => {
        this.cancelar();
      }
    });
  }
  public cancelar() {
    this.msgsDialog = [];
    this.operacion = new Operacion();
    this.operacionSeleccionada = new Operacion();
    this.operacion.tipo = "0";
    this.campoError = false;
    this.display = false;
    this.loaderService.display(false);
  }

  /* Permite convertir el json de operaciones a un arreglo de tipo TreeNode */
  getTree(json): TreeNode[] {
    this.aplicacionArbol = [];
    this.aplicaciones.forEach(element => {
      if (element['children']) {
        delete element['children'];
        delete element['collapsedIcon'];
        delete element['expandedIcon'];
        delete element['label'];
      }
      if (this.idAplicacion == element.aplicacionId.toString()) {
        delete element['minutosVigenciaCodigo'];
        delete element['minutosVigenciaToken'];
        delete element['ultimaModificacion'];
        delete element['urlInicioExitoso'];
        // delete element['urlInicioFallido'];
        delete element['usuarioModificacion'];
        delete element['descripcion'];
        element['nombreObjeto'] = element.nombre;
        element['tipo'] = "APLICACION"
      }
      else {
        delete element[0];
      }
    });

    /** se remueven los items duplicados del arreglo de operaciones */
    var seenNames = {};
    let array = json.filter(function (currentObject) {
      if (currentObject.name in seenNames) {
        return false;
      } else {
        seenNames[currentObject.name] = true;
        return true;
      }
    });

    this.aplicaciones.forEach(element => {
      element['listadoOperaciones'] = json.filter(operacion => operacion.aplicacionId == element.aplicacionId);
    });

    this.aplicaciones.forEach(element => {
      if (this.idAplicacion == element.aplicacionId.toString()) {
        this.crearNodo(element);
      }
    });
    for (var k = 0; k < this.aplicaciones.length; k++) {
      if (this.aplicaciones[k]['children']) {
        this.aplicacionArbol.push(this.aplicaciones[k]);
      }
    }
    return this.aplicacionArbol;
  }

  /* Permite convertir un nodo de operación a un nodo de tipo TreeNode*/
  private crearNodo(item) {

    delete item['aplicaciones'];
    delete item['fechaCreacion'];
    delete item['ultimaModificacion'];
    delete item['parent'];
    delete item['usuarioModificacion'];
    item['label'] = item.nombreObjeto;
    if (item.listadoOperaciones != null) {
      this.crearIcono(item);
      if (item.listadoOperaciones.length > 0) {
        item['children'] = [];
        item.listadoOperaciones.forEach(element => {
          item.children.push(element);
          this.crearIcono(item);
          this.crearNodo(element);
        });
      }
    } else {
      this.crearIcono(item);
    }
  }

  /** Permite crear un ícono dependiendo del tipo de operación */
  private crearIcono(item) {

    switch (item.tipo) {
      case "APLICACION":
        item['data'] = "APLICACION";
        item['expandedIcon'] = "fa-folder-open";
        item['collapsedIcon'] = "fa-folder";
        break;
      case "MENU":
        item['data'] = "MENU";
        item['expandedIcon'] = "fa fa-caret-square-o-down";
        item['collapsedIcon'] = "fa fa-caret-square-o-right";
        break;
      case "SUBMENU":
        item['data'] = "SUBMENU";
        item['expandedIcon'] = "fa fa-chevron-down";
        item['collapsedIcon'] = "fa fa-chevron-right";
        break;
      case "VINCULO":
        item['data'] = "VINCULO";
        item['expandedIcon'] = "fa fa-long-arrow-down";
        item['collapsedIcon'] = "fa fa-long-arrow-right";
        break;
      case "BOTON":
        item['data'] = "BOTON";
        item['icon'] = "fa fa-dot-circle-o";
        break;
      default:
        item['data'] = "SINTIPO";
        item['icon'] = "fa fa-file-o";
        break;
    }
  }

  public filtrarOperacion() {
    this.loaderService.display(true);
    if (this.validarId()) {
      this.operacionService.consultarOperaciones(this.idAplicacion, this.nombreObjeto).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.filtroApi = true;
            this.arbolOperaciones = this.getTree(data.json());
            this.loaderService.display(false);
          }
        },
        error => {
          let mensaje: Mensaje = error.json();
          this.filtroApi = false;
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.guardarNodoBase();
          this.loaderService.display(false);
        });
    }
    this.loaderService.display(false);
  }

  public limpiar() {
    this.idAplicacion = "0";
    this.nombreObjeto = null;
    this.filtroApi = false;
    this.msgs = [];
    this.campoError = false;
    this.loaderService.display(false);
  }

  /*Permite validar los campos del formulario*/
  private validarId() {
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
        }
        this.loaderService.display(false);
      },
      error => {
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
        } else {
          let mensaje: Mensaje = data.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
        }
        this.loaderService.display(false);
      },
      error => {
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  private mostrarMensaje(mensaje: string) {
    this.mensajesService.getMensaje(mensaje).subscribe(data => {
      let mensaje: Mensaje = data.json();
      Util.mostrarMensaje(this.msgs, mensaje, true);
    }, error => {
      let mensaje: Mensaje = error.json();
      Util.mostrarMensaje(this.msgs, mensaje, true);
    });
  }
  /**
     * Metodo que se activa con cada cambio en los cmpos del formulario
     * @param event
     */
  formChange(event) {
    this.disableGuardar = false;
    this.isActiveGif();
  }

  private initExportForm() {
    this.formGroup = new FormGroup({
      idAplicacion: new FormControl(null, Validators.required),
      idAplicacionOriginal: new FormControl(null),
    });
  }

  public get controls() {
    return this.formGroup.controls;
  }

  public abrirDialogoExportar() {
    if (this.validarId()) {
      this.msgs = [];
      this.formGroup.reset();
      this.formGroup.get('idAplicacionOriginal').setValue(this.idAplicacion);
      this.displayExportar = true;
      this.submitted = false;
    }
  }

  public cancelarDialogoExportar() {
    this.formGroup.reset();
    this.displayExportar = false;
    this.loaderService.display(false);
    this.submitted = false;
  }

  public exportar() {
    this.displayExportar = false;
    this.submitted = true;
    if (this.formGroup.invalid) {
      return;
    }
    this.loaderService.display(true);
    const objeto = this.formGroup.getRawValue();
    this.operacionService.auditarExportar(objeto.idAplicacion).subscribe();
    this.operacionService.consultarOperacioneParaImportar(objeto.idAplicacionOriginal, objeto.idAplicacion).subscribe(reps => {
        objeto['operacionesList'] = reps;
        const sJson = JSON.stringify(objeto);
        const element = document.createElement('a');
        element.setAttribute('href', "data:text/json;charset=UTF-8," + encodeURIComponent(sJson));
        element.setAttribute('download', "Operaciones.json");
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
        this.loaderService.display(false);
        const mensaje = new Mensaje();
        mensaje.descripcion = "Se ha exportado el archivo correctamente";
        mensaje.tipoMensaje = 'success';
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.displayExportar = false;
    },
      error => {
        let mensaje = error;
        if  (error  instanceof  Response) {
          mensaje = error.json();
        }
        this.msgs = [];
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  public abrirDialogoImportar() {
    this.msgs = [];
    this.displayImportar = true;
  }

  public cancelarDialogoImportar() {
    this.displayImportar = false;
    this.file = null;
    this.displayMensajeValidarImportar = false;
    this.loaderService.display(false);
  }

  public mostrarMensajeAntesDeImportar(){
      this.displayMensajeValidarImportar = true;
      this.msgs = [];
  }

  public cancelarDialogoMensajeImportar() {
    this.displayMensajeValidarImportar = false;
    this.loaderService.display(false);
  }

  public importar() {
    this.displayImportar = false;
    this.displayMensajeValidarImportar = false;
    let fileReader = new FileReader();
    this.loaderService.display(true);
    fileReader.onload = (e) => {
      const operacionesAImportar = JSON.parse(fileReader.result as string);
      this.operacionService.importar(operacionesAImportar).subscribe(resp => {
        if (resp.status == RESPONSE_OK) {
          this.loaderService.display(false);
          const mensaje = resp.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.displayImportar = false;
          this.displayMensajeValidarImportar = false;
        }
      },
        error => {
          let mensaje = error;
          if  (error  instanceof  Response) {
            mensaje = error.json();
          }
          this.msgs = [];
          Util.mostrarMensaje(this.msgs, mensaje, true);
          this.displayMensajeValidarImportar = false;
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


}
