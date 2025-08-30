import { Component, OnInit } from '@angular/core';
import { OperacionService } from 'app/operacion/operacion.service';
import { ContextMenuService } from 'app/services/context-menu.service';
import { TooltipModule } from 'primeng/primeng';
import { Tree, TreeNode, MenuItem, Message, ContextMenu, DialogModule, Dialog, ConfirmationService } from 'primeng/primeng';
import { Operacion } from 'app/modelo/Operacion';
import { RolService } from 'app/rol/rol.service';
import { PathConstants } from 'app/util/PathConstants';
import { Estado } from 'app/modelo/Estado';
import { OperacionRol } from 'app/modelo/OperacionRol';
import { Rol } from 'app/modelo/Rol';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { Util } from 'app/util/Util';
import { Mensaje } from 'app/modelo/Mensaje';
import { RESPONSE_OK, CU003MEN11 } from 'app/util/Constants';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { MensajesService } from 'app/services/mensajes.service';
import { LoaderService } from '../../spinner/loader.service';

@Component({
  selector: 'app-crear-editar-operaciones',
  templateUrl: './crear-editar-operaciones.component.html',
  styleUrls: ['./crear-editar-operaciones.component.css'],
  providers: [OperacionService, AplicacionService, MensajesService, ConfirmationService, RolService]
})
export class CrearEditarOperacionesComponent implements OnInit {

  //mensajes que se muestran en la pantalla
  private msgs: Message[] = [];
  //listado de Aplicaciones
  private aplicaciones: Aplicacion[] = [];
  private aplicacionesFinal: any[] = [];
  private operacionRolGuardar: OperacionRol[] = [];
  private operacionRolArbol: OperacionRol[] = [];
  private operacionRolSinBase: OperacionRol[] = [];
  private operacionRol: OperacionRol;
  //listado de operaciones
  arbolOperaciones: TreeNode[];
  //listado operaciones seleccionadas
  nodosSeleccionados: TreeNode;
  nodosMarcados: TreeNode[];
  //Rol al que se le asignan las operaciones
  private rol: Rol = new Rol();
  operacionesRol: OperacionRol[] = [];
  private rolId: string;
  private idAplicacion: string;
  private nombreObjeto: string;
  private idValido: boolean = false;
  private cont: number = 0;
  //estado aplicacion
  private estado: string;
  //inactiva boton guardar
  public disableGuardar: boolean;


  constructor(private loaderService: LoaderService, private operacionService: OperacionService, private aplicacionService: AplicacionService, private router: Router, private rolService: RolService, private mensajesService: MensajesService, private confirmationService: ConfirmationService) { }

  ngOnInit() {
     this.disableGuardar = true;
    if (Util.existeItemSession("idAplicacion")) {
      this.idAplicacion = sessionStorage.getItem('idAplicacion');
    }
    if (Util.existeItemSession('rolId')) {
      this.rolId = sessionStorage.getItem('rolId');
    }
    this.consultarAplicaciones();

    setTimeout(()=> {
      this.filtrarOperacion();
    }, 1000);

    this.operacionRolGuardar = [];
    this.operacionRolArbol = [];
    this.operacionRolSinBase = [];


  }
  public agregarArray(element: string) {
    let operacionRol: OperacionRol = new OperacionRol();
    operacionRol.operacionId = element;
    operacionRol.rolId = this.rolId;
    this.operacionRolGuardar.push(operacionRol);
  }
  public agregarArrayArbol(element: string) {
    let operacionRol: OperacionRol = new OperacionRol();
    operacionRol.operacionId = element;
    operacionRol.rolId = this.rolId;
    this.operacionRolArbol.push(operacionRol);
  }
  /**Permite consultar las operaciones asignadas al rol*/
  private consultarOperacionesRol() {
    this.loaderService.display(true);
    this.rolService.consultarOperacionesRol(this.rolId, this.idAplicacion).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.operacionesRol = data.json();
          for (var i = 0; i < this.operacionesRol.length; i++) {
            this.arbolOperaciones.forEach(element => {
              element.partialSelected = true;
              let nodosHijos = element.children;
              for (var j = 0; j < nodosHijos.length; j++) {
                if (nodosHijos[j].children) {
                  if (nodosHijos[j]['opcionId'] == this.operacionesRol[i].operacionId) {
                    nodosHijos[j].expanded = true;
                    nodosHijos[j].partialSelected = true;
                    this.agregarArray(nodosHijos[j]['opcionId']);
                  }
                  let nodosHijos2 = nodosHijos[j].children;
                  for (var q = 0; q < nodosHijos2.length; q++) {
                    if (nodosHijos2[q].children) {
                      if (nodosHijos2[q]['opcionId'] == this.operacionesRol[i].operacionId) {
                        nodosHijos2[q].expanded = true;
                        nodosHijos2[q].partialSelected = true;
                        this.agregarArray(nodosHijos2[q]['opcionId']);
                      }
                      let nodosHijos3 = nodosHijos2[q].children;
                      for (var k = 0; k < nodosHijos3.length; k++) {
                        if (nodosHijos3[k].children) {
                          if (nodosHijos3[k]['opcionId'] == this.operacionesRol[i].operacionId) {
                            nodosHijos3[k].expanded = true;
                            nodosHijos3[k].partialSelected = true;
                            this.agregarArray(nodosHijos3[k]['opcionId']);
                          }
                          let nodosHijos4 = nodosHijos3[k].children;
                          for (var z = 0; z < nodosHijos4.length; z++) {
                            if (nodosHijos4[z]['opcionId'] == this.operacionesRol[i].operacionId) {
                              nodosHijos4[z].expanded = true;
                              nodosHijos4[z].partialSelected = true;
                              this.agregarArray(nodosHijos4[z]['opcionId']);
                            }
                          }
                        } else {
                          if (nodosHijos3[k]['opcionId'] == this.operacionesRol[i].operacionId) {
                            nodosHijos3[k].expanded = true;
                            nodosHijos3[k].partialSelected = true;
                            this.agregarArray(nodosHijos3[k]['opcionId']);
                          }
                        }
                      }
                    } else {
                      if (nodosHijos2[q]['opcionId'] == this.operacionesRol[i].operacionId) {
                        nodosHijos2[q].expanded = true;
                        nodosHijos2[q].partialSelected = true;
                        this.agregarArray(nodosHijos2[q]['opcionId']);
                      }
                    }
                  }
                } else {
                  if (nodosHijos[j]['opcionId'] == this.operacionesRol[i].operacionId) {
                    nodosHijos[j].expanded = true;
                    nodosHijos[j].partialSelected = true;
                    this.agregarArray(nodosHijos[j]['opcionId']);
                  }
                }
              }
            }
            )
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


  /** Permite guardar las operaciones asociadas al rol*/
  private guardar() {
    this.loaderService.display(true);
    if (Util.existeItemSession("rolId")) {
      this.armarNuevoArbol();
      let operacionRolJson: OperacionRol[] = this.convertirNodosToJSON(this.nodosSeleccionados);
      if (operacionRolJson.length == 0) {
        this.rolService.eliminarOperacionesRol(this.rolId).subscribe(
          data => {
            if (data.status == RESPONSE_OK) {
              this.operacionRolGuardar = [];
              let mensaje: Mensaje = data.json();
              sessionStorage.setItem("mensaje", JSON.stringify(mensaje));
              this.router.navigate([PathConstants.PATH_ADMINISTRAR_ROL]);
              this.loaderService.display(false);
            }
          },
          error => {
            this.msgs = [];
            let mensaje: Mensaje = error.json();
            Util.mostrarMensaje(this.msgs, mensaje, true);
            this.loaderService.display(false);
          });
      } else {
        for (var i = 0; i < operacionRolJson.length; i++) {
          if (operacionRolJson[i].operacionId != null) {
            this.operacionRolSinBase.push(operacionRolJson[i]);
          }
        }
        let operacionSinBase = this.operacionRolSinBase;
        this.rolService.asignarOperacionesRol(operacionSinBase).subscribe(
          data => {
            if (data.status == RESPONSE_OK) {
              this.operacionRolGuardar = [];
              let mensaje: Mensaje = data.json();
              sessionStorage.setItem("mensaje", JSON.stringify(mensaje));
              this.router.navigate([PathConstants.PATH_ADMINISTRAR_ROL]);
              this.loaderService.display(false);
            }
          },
          error => {
            this.convertirNodosToJSON(this.nodosSeleccionados);
            this.msgs = [];
            let mensaje: Mensaje = error.json();
            Util.mostrarMensaje(this.msgs, mensaje, true);
            this.loaderService.display(false);
          });
      }

    }

  }

  public armarNuevoArbol() {
    this.loaderService.display(true);
    this.arbolOperaciones.forEach(element => {
      element.partialSelected = true;
      let nodosHijos = element.children;
      for (var j = 0; j < nodosHijos.length; j++) {
        if (nodosHijos[j].children) {
          if (nodosHijos[j].partialSelected == true) {
            this.agregarArrayArbol(nodosHijos[j]['opcionId']);
          }
          let nodosHijos2 = nodosHijos[j].children;
          for (var q = 0; q < nodosHijos2.length; q++) {
            if (nodosHijos2[q].children) {
              if (nodosHijos2[q].partialSelected == true) {
                this.agregarArrayArbol(nodosHijos2[q]['opcionId']);
              }
              let nodosHijos3 = nodosHijos2[q].children;
              for (var k = 0; k < nodosHijos3.length; k++) {
                if (nodosHijos3[k].children) {
                  if (nodosHijos3[k].partialSelected == true) {
                    this.agregarArrayArbol(nodosHijos3[k]['opcionId']);
                  }
                  let nodosHijos4 = nodosHijos3[k].children;
                  for (var z = 0; z < nodosHijos4.length; z++) {
                    if (nodosHijos4[z].partialSelected == true) {
                      this.agregarArrayArbol(nodosHijos4[z]['opcionId']);
                    }
                  }
                } else {
                  if (nodosHijos3[k].partialSelected == true) {
                    this.agregarArrayArbol(nodosHijos3[k]['opcionId']);
                  }
                }
              }
            } else {
              if (nodosHijos2[q].partialSelected == true) {
                this.agregarArrayArbol(nodosHijos2[q]['opcionId']);
              }
            }
          }
        } else {
          if (nodosHijos[j].partialSelected == true) {
            this.agregarArrayArbol(nodosHijos[j]['opcionId']);
          }
        }
      }
    });
    this.loaderService.display(false);
  }


  /**Permite cnvertiir los nodos seleccionados a un array json de tipo OperacionRol */
  private convertirNodosToJSON(nodosSeleccionados) {
    let operacionRolJson: OperacionRol[] = [];
    nodosSeleccionados.forEach(element => {
      let operacionRol: OperacionRol = new OperacionRol();
      operacionRol.operacionId = element.opcionId;
      operacionRol.rolId = this.rolId;
      operacionRolJson.push(operacionRol);
      if (element.children != null && element.children != undefined) {
        this.convertirNodosToJSON(element.children);
      }
    });
    for (var i = 0; i < this.operacionRolArbol.length; i++) {
      operacionRolJson.push(this.operacionRolArbol[i]);
    }
    return operacionRolJson;
  }

  /** Permite volver al formulario de roles*/
  private volver() {
    this.mensajesService.getMensaje(CU003MEN11).subscribe(
      data => {
        let mensaje: Mensaje = data.json();
        this.confirmationService.confirm({
          message: mensaje.descripcion,
          accept: () => {
            this.router.navigate([PathConstants.PATH_ADMINISTRAR_ROL]);
          }
        });
      }, error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
      });
  }

  public consultarAplicaciones() {
    this.loaderService.display(true);
    this.estado = "";
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

  public filtrarOperacion() {
    this.loaderService.display(true);
    this.operacionService.consultarOperaciones(this.idAplicacion, this.nombreObjeto).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.arbolOperaciones = this.getTree(data.json());
          this.consultarOperacionesRol();
        }
      },
      error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  /* Permite convertir el json de operaciones a un arreglo de tipo TreeNode */
  getTree(json): TreeNode[] {
    this.aplicaciones.forEach(element => {
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
      if (this.idAplicacion == element.aplicacionId.toString()) {
        element['listadoOperaciones'] = json.filter(operacion => operacion.aplicacionId == element.aplicacionId);
      }
    });


    this.aplicaciones.forEach(element => {
      if (this.idAplicacion == element.aplicacionId.toString()) {
        this.crearNodo(element);
      }
    });

    this.aplicaciones.forEach(element => {
      if (this.idAplicacion == element.aplicacionId.toString()) {
        this.aplicacionesFinal.push(element)
      }
    });

    return this.aplicacionesFinal;

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
  /**
     * Metodo que se activa con cada cambio en los cmpos del formulario
     * @param event
     */
  formChange(event) {
    this.disableGuardar = false;
  }
}
