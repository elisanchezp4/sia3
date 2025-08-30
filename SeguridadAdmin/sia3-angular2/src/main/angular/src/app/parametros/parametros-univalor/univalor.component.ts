import { Component, OnInit } from '@angular/core';
import { Message, ConfirmationService, DialogModule, Dialog } from 'primeng/primeng';
import { Tree, TreeNode } from 'primeng/primeng';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Util } from 'app/util/Util';
import { Mensaje } from 'app/modelo/Mensaje';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { RolService } from 'app/rol/rol.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Rol } from 'app/modelo/Rol';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { ParametrosService } from 'app/parametros/parametros.service';
import { PathConstants } from 'app/util/PathConstants';
import { Estado } from 'app/modelo/Estado';
import { Parametro } from 'app/modelo/Parametro';
import { MensajesService } from 'app/services/mensajes.service';
import { RESPONSE_OK, ERROR_VALIDACION_CAMPO_NOMBRE_ROL, ERROR_VALIDACION_CAMPO_ESTADO_ROL, ERROR_VALIDACION_CAMPOS_APLICACION, CATALOGO_ESTADO, CU003MEN01, CU003MEN11, UNIVALOR, MULTIVALOR, DEPENDIENTE, PARAMETROSAPP01, PARAMETROSAPP02, CU008MEN17, CARGANDO, DUPLICADOS, PARAMETROSAPP03 } from 'app/util/Constants';
import { LoaderService } from '../../spinner/loader.service';

@Component({
    selector: 'app-parametros-univalor',
    templateUrl: './univalor.component.html',
    styleUrls: ['./univalor.component.css'],
    providers: [AplicacionService, ConfirmationService, ParametrosService, MensajesService]
})
export class ParametrosUnivalorComponent implements OnInit {

    //listado de Aplicaciones
    private aplicaciones: Aplicacion[] = [];
    //mensajes que se muestran en la pantalla
    private msgs: Message[] = [];
    //mensajes que se muestran en el dialogo
    private msgsDialog: Message[] = [];
    //mensajes de cargando
    private msgsCargar: Message[] = [];

    private verFormularioUnivalor: boolean;
    private verFormularioMultivalor: boolean;
    private verFormularioDependiente: boolean;
    private verFormularioEditar: boolean;
    private verFormularioCrear: boolean;
    private verFormularioEditarDependiente: boolean;

    //Estados de la aplicación
    private estados: Estado[] = [];
    private nombreEstados: any[] = [];
    private estado: string;
    private estadoFiltro: string;
    private dominio: string;
    private dominioFiltro: string;
    private parametros: Parametro[] = [];
    private dominios: any[] = [];
    private tipoParametros: any[] = [];
    private tipoParametrosTemp: any[] = [];
    private departamentos: any[] = [];
    private nombreApp: string;
    private nombreAppFiltro: string;
    private tipoParametro: string;
    private tipoParametroFiltro: string;
    private valorParametro: string;
    private parametrosUnivalor: Parametro;
    private parametrosMultivalor: Parametro;
    private parametrosMultivalorEditar: Parametro;
    private parametrosDependienteEditar: Parametro;
    private nuevoParametroMultivalor: Parametro;
    private parametrosDependientes: Parametro[];
    private parametrosDependientesGuardar: Parametro;
    private departamento: string;
    private nuevoParametroDependiente: Parametro;
    private datosDependientes: Parametro;
    private campoError: boolean = false;
    private vacio: string = "";
    private nombreDepartamentos: any[] = [];
    private codigoDepartamentos: any[] = [];
    private agregando: boolean = false;

    constructor(private loaderService: LoaderService, private aplicacionService: AplicacionService, private router: Router, private confirmationService: ConfirmationService, private parametrosService: ParametrosService, private mensajesService: MensajesService) { }

    ngOnInit() {
        this.consultarNombreEstado();
        this.consultarNombreDepartamento();
        this.consultarAplicaciones();
        this.consultaEstados();
        this.nombreApp = "0";
        this.dominio = "0";
        this.tipoParametro = "0";
        this.estado = "0";
        this.nombreAppFiltro = "0";
        this.dominioFiltro = "0";
        this.tipoParametroFiltro = "0";
        this.estadoFiltro = "0";
        this.departamento = "0";

        this.verFormularioUnivalor = false;
        this.verFormularioMultivalor = false;
        this.verFormularioDependiente = false;
        this.verFormularioEditar = false;
        this.verFormularioCrear = false;
        this.verFormularioEditarDependiente = false;
    }

    private consultarParametros() {
        this.loaderService.display(true);
        this.msgs = [];
        if (this.nombreAppFiltro == "0" || this.tipoParametroFiltro == "0") {
            this.mostrarMensaje(CU003MEN01);
            this.campoError = true;
            this.loaderService.display(false);
        } else {
            this.parametrosService.consultarParametros(this.tipoParametroFiltro, this.nombreAppFiltro, this.dominioFiltro, this.estadoFiltro).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.msgsCargar = [];
                        this.parametros = data.json();
                    }
                    this.loaderService.display(false);
                },
                error => {
                    if (error.status == 500 || error.status == 400) {
                        this.mostrarMensaje(PARAMETROSAPP03);
                        this.loaderService.display(false);
                    } else {
                        this.msgs = [];
                        let mensaje: Mensaje = error.json();
                        Util.mostrarMensaje(this.msgs, mensaje, true);
                        this.loaderService.display(false);
                    }
                });
        }

    }
    /*Permite consultar todas las aplicaciones*/
    private consultarAplicaciones() {
        this.loaderService.display(true);
        this.parametrosService.consultarAplicaciones().subscribe(
            data => {
                if (data.status == RESPONSE_OK) {
                    this.aplicaciones = data.json();
                    this.consultarDepartamentos();
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

    public cargarDatosParametros(parametro: Parametro) {
        this.loaderService.display(true);
        if (parametro.tipoParametro == UNIVALOR) {
            this.parametrosService.consultarParametrosFiltro(parametro.tipoParametro, parametro.aplicacion, parametro.dominio, parametro.estado).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.parametrosUnivalor = data.json();
                        let param = this.parametrosUnivalor[0];
                        this.parametrosUnivalor = param;
                        this.estado = this.parametrosUnivalor.estado;
                        this.dominio = this.parametrosUnivalor.dominio;
                        this.valorParametro = this.parametrosUnivalor.nombre;
                        this.mostrarFormularioUnivalor();
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
        if (parametro.tipoParametro == MULTIVALOR) {
            this.parametrosService.consultarParametrosFiltro(parametro.tipoParametro, parametro.aplicacion, parametro.dominio, parametro.estado).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.parametrosMultivalor = data.json();
                        this.mostrarFormularioMultivalor();
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
        if (parametro.tipoParametro == DEPENDIENTE) {
            this.parametrosService.consultarParametrosFiltro(parametro.tipoParametro, parametro.aplicacion, parametro.dominio, parametro.estado).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.parametrosDependientes = data.json();
                        for (let f of this.parametrosDependientes) {
                            f.codigoPadre = this.nombreDepartamentos[f.codigoPadre];
                            f.codigoDep = this.codigoDepartamentos[f.codigoPadre];
                        }
                        this.mostrarFormularioDependiente();
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

    public mostrarFormularioUnivalor() {
        this.verFormularioUnivalor = true;
        this.consultarAplicaciones();
        this.msgs = [];
    }

    public mostrarFormularioMultivalor() {
        this.verFormularioMultivalor = true;
        this.msgs = [];
    }

    public mostrarFormularioDependiente() {
        this.verFormularioDependiente = true;
        this.consultarAplicaciones();
        this.msgs = [];
    }

    public cancelar() {
        this.verFormularioMultivalor = false;
        this.verFormularioUnivalor = false;
        this.verFormularioDependiente = false;
        this.verFormularioEditar = false;
        this.msgsDialog = [];
        this.msgs = [];
        this.loaderService.display(false);
    }

    public salir() {
        this.verFormularioMultivalor = false;
        this.verFormularioDependiente = false;
        this.msgsDialog = [];
    }

    public validarValor(valor): string {
        if (valor == null || valor == "") {
            return 'red';
        } else {
            return 'initial';
        }
    }

    public limpiarFormulario() {
        this.msgs = [];
        this.msgsDialog = [];
        this.estadoFiltro = "0";
        this.nombreAppFiltro = "0";
        this.dominioFiltro = "0";
        this.tipoParametroFiltro = "0";
        this.parametros = [];
        this.campoError = false;
        this.loaderService.display(false);
    }
    private actualizarApp() {
        if (this.nombreAppFiltro == "0") {
            this.dominios = [];
            this.dominioFiltro = "0";
            this.tipoParametros = [];
            this.tipoParametroFiltro = "0";
            this.estadoFiltro = "0"
            this.parametros = [];
        }
        this.dominios = [];
        this.dominioFiltro = "0";
        this.tipoParametroFiltro = "0";
        this.estadoFiltro = "0";
        this.parametros = [];
    }

    /*Permite consultar todos los estados de una aplicación*/
    private consultaEstados() {
        this.loaderService.display(true);
        let tipo = CATALOGO_ESTADO;
        this.aplicacionService.consultarCatalogo(tipo).subscribe(
            data => {
                if (data.status == RESPONSE_OK) {
                    this.estados = data.json();
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

    public cargarMultivalor(paramMultiValor: Parametro) {
        this.agregando = false;
        this.parametrosMultivalorEditar = new Parametro();
        this.estado = paramMultiValor.estado;
        this.dominio = paramMultiValor.dominio;
        this.valorParametro = paramMultiValor.nombre;
        this.parametrosMultivalorEditar.aplicacion = paramMultiValor.aplicacion;
        this.parametrosMultivalorEditar.codigo = paramMultiValor.codigo;
        this.parametrosMultivalorEditar.codigoPadre = paramMultiValor.codigoPadre;
        this.parametrosMultivalorEditar.dominioPadre = paramMultiValor.dominioPadre;
        this.parametrosMultivalorEditar.id = paramMultiValor.id;
        this.parametrosMultivalorEditar.modificable = paramMultiValor.modificable;
        this.parametrosMultivalorEditar.tipoParametro = paramMultiValor.tipoParametro;
        this.verFormularioEditar = true;
    }
    public cancelarEditar() {
        this.verFormularioEditar = false;
        this.verFormularioEditarDependiente = false;
        this.msgs = [];
        this.estado = "0";
        this.valorParametro = null;
        this.departamento = "0";
        this.msgsDialog = [];
    }

    public cargarDependiente(paramDependiente: Parametro) {
        this.agregando = false;
        this.datosDependientes = paramDependiente;
        this.parametrosDependienteEditar = new Parametro();
        this.estado = paramDependiente.estado;
        this.dominio = paramDependiente.dominio;
        this.valorParametro = paramDependiente.nombre;
        this.departamento = paramDependiente.codigoDep;
        this.parametrosDependienteEditar.aplicacion = paramDependiente.aplicacion;
        this.parametrosDependienteEditar.codigo = paramDependiente.id;
        this.parametrosDependienteEditar.dominioPadre = paramDependiente.dominioPadre;
        this.parametrosDependienteEditar.id = paramDependiente.id;
        this.parametrosDependienteEditar.modificable = paramDependiente.modificable;
        this.parametrosDependienteEditar.tipoParametro = paramDependiente.tipoParametro;
        this.verFormularioEditarDependiente = true;
    }

    public consultaListaDominios(nombreApp: string, tipoParametro: string) {
        this.loaderService.display(true);
        this.parametrosService.consultarListaDominios(nombreApp, tipoParametro).subscribe(
            data => {
                if (data.status == RESPONSE_OK) {
                    this.dominios = data.json();
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

    public consultaListaTipoParametro(appNombre: string) {
        this.loaderService.display(true);
        this.parametrosService.consultarListaTipoParametro(appNombre).subscribe(
            data => {
                if (data.status == RESPONSE_OK) {
                    this.tipoParametrosTemp = data.json();
                    for (var i = 0; i < this.tipoParametrosTemp.length; i++) {
                        if (this.tipoParametrosTemp[i].tipoParametro != "") {
                            let listaSinVacio = this.tipoParametrosTemp[i];
                            this.tipoParametros[i] = listaSinVacio;
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
            });
    }

    private consultarNombreEstado() {
        this.loaderService.display(true);
        let tipo = CATALOGO_ESTADO;
        this.aplicacionService.consultarCatalogo(tipo).subscribe(
            data => {
                if (data.status == RESPONSE_OK) {
                    let list = data.json();
                    for (let e of list) {
                        this.nombreEstados[e.catalogoId] = e.descripcion
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

    private editarParametros() {
        this.loaderService.display(true);
        if (this.estado == "0" || this.valorParametro.trim() == null || this.valorParametro.trim() == "") {
            this.campoError = true;
            if (this.parametrosUnivalor.nombre != null || this.parametrosUnivalor.nombre != "") {
                this.mostrarMensaje(CU008MEN17);
                this.loaderService.display(false);
            } else {
                this.mostrarMensajeDialog(CU008MEN17);
                this.loaderService.display(false);
            }
        } else {
            this.parametrosUnivalor.estado = this.estado;
            this.parametrosUnivalor.dominio = this.dominio;
            this.parametrosUnivalor.nombre = this.valorParametro.trim();
            this.parametrosService.editarParametros(this.parametrosUnivalor).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.limpiarFormulario();
                        this.mostrarMensaje(PARAMETROSAPP01);
                        this.verFormularioUnivalor = false;
                        this.loaderService.display(false);
                        this.actualizarApp();
                    }
                },
                error => {
                    if (error.status == 500 || error.status == 400) {
                        this.mostrarMensajeDialog(DUPLICADOS);
                        this.loaderService.display(false);
                    } else {
                        this.msgs = [];
                        let mensaje: Mensaje = error.json();
                        Util.mostrarMensaje(this.msgsDialog, mensaje, true);
                        this.loaderService.display(false);
                    }

                });
        }
    }

    private editarParametrosMultivalor() {
      this.loaderService.display(true);
      if (this.estado == "0" || this.valorParametro.trim() == null || this.valorParametro.trim() == "") {
        this.campoError = true;
        this.mostrarMensajeDialog(CU008MEN17);
        this.loaderService.display(false);
      } else {
        this.parametrosMultivalorEditar.estado = this.estado;
        this.parametrosMultivalorEditar.dominio = this.dominio;
        this.parametrosMultivalorEditar.nombre = this.valorParametro.trim();
        this.parametrosService.editarParametros(this.parametrosMultivalorEditar).subscribe(
          data => {
            if (data.status == RESPONSE_OK) {
              this.limpiarFormulario();
              this.mostrarMensaje(PARAMETROSAPP01);
              this.verFormularioMultivalor = false;
              this.verFormularioEditar = false;
              this.actualizarApp();
              this.loaderService.display(false);
            }
          },
          error => {
            if (error.status == 500 || error.status == 400) {
              this.mostrarMensajeDialog(DUPLICADOS);
              this.loaderService.display(false);
            } else {
              this.msgs = [];
              let mensaje: Mensaje = error.json();
              Util.mostrarMensaje(this.msgsDialog, mensaje, true);
              this.loaderService.display(false);
            }

          });
      }
    }

  private editarParametrosDependiente() {
        this.loaderService.display(true);
        if (this.estado == "0" || this.valorParametro.trim() == null || this.valorParametro.trim() == "" || this.departamento == "0") {
            this.campoError = true;
            this.mostrarMensajeDialog(CU008MEN17);
            this.loaderService.display(false);
        } else {
            this.parametrosDependienteEditar.estado = this.estado;
            this.parametrosDependienteEditar.dominio = this.dominio;
            this.parametrosDependienteEditar.nombre = this.valorParametro.trim();
            this.parametrosDependienteEditar.codigoPadre = this.departamento;
            this.parametrosService.editarParametros(this.parametrosDependienteEditar).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.limpiarFormulario();
                        this.mostrarMensaje(PARAMETROSAPP01);
                        this.verFormularioDependiente = false;
                        this.verFormularioEditarDependiente = false;
                        this.actualizarApp();
                        this.loaderService.display(false);
                    }
                },
                error => {
                    if (error.status == 500 || error.status == 400) {
                        this.mostrarMensajeDialog(DUPLICADOS);
                        this.loaderService.display(false);
                    } else {
                        this.msgs = [];
                        let mensaje: Mensaje = error.json();
                        Util.mostrarMensaje(this.msgsDialog, mensaje, true);
                        this.loaderService.display(false);
                    }

                });
        }
    }

    private adicionarParametrosMultivalor() {
        this.loaderService.display(true);
        if (this.estado == "0" || this.valorParametro.trim() == null || this.valorParametro.trim() == "") {
            this.campoError = true;
            this.mostrarMensajeDialog(CU008MEN17);
            this.loaderService.display(false);
        } else {
            this.nuevoParametroMultivalor = new Parametro();
            let paramsMulti = this.parametrosMultivalor[0];
            this.nuevoParametroMultivalor.aplicacion = paramsMulti.aplicacion;
            this.nuevoParametroMultivalor.codigo = paramsMulti.codigo;
            this.nuevoParametroMultivalor.codigoPadre = paramsMulti.codigoPadre;
            this.nuevoParametroMultivalor.dominioPadre = paramsMulti.dominioPadre;
            this.nuevoParametroMultivalor.modificable = paramsMulti.modificable;
            this.nuevoParametroMultivalor.tipoParametro = paramsMulti.tipoParametro;
            this.nuevoParametroMultivalor.estado = this.estado;
            this.nuevoParametroMultivalor.dominio = paramsMulti.dominio;
            this.nuevoParametroMultivalor.nombre = this.valorParametro.trim();
            this.parametrosService.adicionarParametro(this.nuevoParametroMultivalor).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.limpiarFormulario();
                        this.mostrarMensaje(PARAMETROSAPP02);
                        this.verFormularioMultivalor = false;
                        this.verFormularioEditar = false;
                        this.actualizarApp();
                        this.loaderService.display(false);
                    }
                },
                error => {
                    if (error.status == 500) {
                        this.mostrarMensajeDialog(DUPLICADOS);
                        this.loaderService.display(false);
                    } else {
                        this.msgs = [];
                        let mensaje: Mensaje = error.json();
                        Util.mostrarMensaje(this.msgsDialog, mensaje, true);
                        this.loaderService.display(false);
                    }

                });
        }
    }

    private adicionarNuevoDependiente() {
        this.loaderService.display(true);
        if (this.estado == "0" || this.valorParametro.trim() == null || this.valorParametro.trim() == "" || this.departamento == "0") {
            this.campoError = true;
            this.mostrarMensajeDialog(CU008MEN17);
            this.loaderService.display(false);
        } else {
            this.nuevoParametroDependiente = new Parametro();
            let datosDependientes = this.parametrosDependientes[0];
            this.nuevoParametroDependiente.aplicacion = datosDependientes.aplicacion;
            this.nuevoParametroDependiente.codigo = datosDependientes.id;
            this.nuevoParametroDependiente.codigoPadre = this.departamento;
            this.nuevoParametroDependiente.dominioPadre = datosDependientes.dominioPadre;
            this.nuevoParametroDependiente.modificable = datosDependientes.modificable;
            this.nuevoParametroDependiente.tipoParametro = datosDependientes.tipoParametro;
            this.nuevoParametroDependiente.estado = this.estado;
            this.nuevoParametroDependiente.dominio = datosDependientes.dominio;
            this.nuevoParametroDependiente.nombre = this.valorParametro.trim();
            this.parametrosService.adicionarParametro(this.nuevoParametroDependiente).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.limpiarFormulario();
                        this.mostrarMensaje(PARAMETROSAPP02);
                        this.verFormularioDependiente = false;
                        this.verFormularioEditarDependiente = false;
                        this.actualizarApp();
                        this.loaderService.display(false);
                    }
                },
                error => {
                    if (error.status == 500) {
                        this.mostrarMensajeDialog(DUPLICADOS);
                        this.loaderService.display(false);
                    } else {
                        this.msgs = [];
                        let mensaje: Mensaje = error.json();
                        Util.mostrarMensaje(this.msgsDialog, mensaje, true);
                        this.loaderService.display(false);
                    }

                });
        }
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
            this.msgs = [];
            let mensaje: Mensaje = data.json();
            Util.mostrarMensaje(this.msgsDialog, mensaje, true);
        }, error => {
            this.msgs = [];
            let mensaje: Mensaje = error.json();
            Util.mostrarMensaje(this.msgsDialog, mensaje, true);
        });
    }

    public adicionar() {
        this.agregando = true;
        this.verFormularioEditar = true;
        this.estado = "0";
        this.valorParametro = null;
        this.dominio = null;
    }

    public adicionarParametroDependiente() {
        this.verFormularioEditarDependiente = true;
        this.agregando = true;
        this.estado = "0";
        this.valorParametro = null;
        this.dominio = null;
        this.departamento = "0";
    }

    public consultarDepartamentos() {
        this.loaderService.display(true);
        this.parametrosService.consultarDepartamentos().subscribe(
            data => {
                if (data.status == RESPONSE_OK) {
                    this.departamentos = data.json();
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

    private consultarNombreDepartamento() {
        this.loaderService.display(true);
        this.parametrosService.consultarDepartamentos().subscribe(
            data => {
                if (data.status == RESPONSE_OK) {
                    let list = data.json();
                    for (let d of list) {
                        this.nombreDepartamentos[d.codigo] = d.nombre;
                        this.codigoDepartamentos[d.nombre] = d.codigo;
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
}
