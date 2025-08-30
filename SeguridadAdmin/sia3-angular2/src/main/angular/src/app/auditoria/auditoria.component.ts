import { Component, OnInit } from '@angular/core';
import { Calendar, CalendarModule, Message, LazyLoadEvent } from 'primeng/primeng';
import { Util } from '../util/Util';
import { Mensaje } from '../modelo/Mensaje';
import { MensajesService } from '../services/mensajes.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { AuditoriaService } from 'app/auditoria/auditoria.service';
import { RESPONSE_OK, CATALOGO_TIPO_EVENTO, APPSIA3, CARGANDO, APP100114 } from 'app/util/Constants';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { Estado } from 'app/modelo/Estado';
import { Usuario } from "app/modelo/Usuario";
import { Auditoria } from 'app/modelo/Auditoria';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { PathConstants } from 'app/util/PathConstants';
import { UsuarioService } from "app/usuario/usuario.service";
import { LoaderService } from '../spinner/loader.service';


@Component({
  selector: 'app-auditoria',
  templateUrl: './auditoria.component.html',
  styleUrls: ['./auditoria.component.css'],
  providers: [AplicacionService, MensajesService, AuditoriaService, UsuarioService]

})
export class AuditoriaComponent implements OnInit {

  private fechaInicio: Date;
  private fechaFin: Date;
  es: any;
  private aplicaciones: Aplicacion[] = [];
  private aplicacionesTemp: Aplicacion[] = [];
  private msgs: Message[] = [];
  private msgsDialog: Message[] = [];
  //mensajes de cargando
  private msgsCargar: Message[] = [];
  private idAplicacion: string;
  private tipoEventos: Estado[] = [];
  private idEvento: string;
  private usuarios: Usuario[] = [];
  private usuariosApp: Usuario[] = [];
  private usuariosFiltrados: any[] = [];
  private terminoBusqueda: string = '';
  private idUsuario: string;
  private nombreUsuario: string;
  private auditoria: Auditoria[] = [];
  private nombreAplicaciones: any[] = [];
  private nombreEvento: any[] = [];
  private camposDirectorioActivo: string[]=[];
  //estado aplicacion
  private estado: string;
  private campoError: boolean = false;

  //variables paginacion
  private primerRegistro: number = 0;
  private cantidadFilas: number = 10;
  private totalRegistros: number = 0;
  private buscando: boolean = false;

  private statSend: boolean = false;
  private aplicacionId: string;
  private campoActivo: string;
  private hovering:boolean = false;

 private displayModal: boolean = false;
  private disableFiltrar: boolean;
  private nombreUsuarioModal: string;
  private selectedUsuario: any;
  private buscarHabilitado: boolean = false;

  constructor(private loaderService: LoaderService, private mensajesService: MensajesService, private router: Router, private aplicacionService: AplicacionService, private auditoriaService: AuditoriaService, private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.es = Util.getCalendarLenguaje();
    this.consultarAplicaciones();
    this.idEvento = "0";
    this.idUsuario = "0";
    this.nombreUsuario ="";
    this.consultarTipoEvento();
    this.consultarNombreAplicaciones();
    this.consultarNombreEvento();
    this.consultarCamposDirectorioActivos();
    this.fechaFin= new Date();
    this.fechaInicio = new Date();
    this.aplicacionId = "0";
    this.campoActivo = "0";
    this.usuariosFiltrados = null;
  }

  public consultarAplicaciones() {
    this.loaderService.display(true);
    this.estado = "1";
    this.aplicacionService.consultar(this.estado).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.aplicacionesTemp = data.json();
          for (var i = 0; i < this.aplicacionesTemp.length; i++) {
            if (this.aplicacionesTemp[i].nombre == APPSIA3) {
              this.aplicaciones[0] = this.aplicacionesTemp[i];
              this.idAplicacion = this.aplicaciones[0].aplicacionId.toString();
            }
          }
          this.loaderService.display(false);
        }
      },
      error => {
        let mensaje: Mensaje = error.json();
        this.msgs = [];
        Util.mostrarMensaje(this.msgs, mensaje, true);
        this.loaderService.display(false);
      });
  }

  /*Permite consultar todos los estados de una aplicación*/
  private consultarTipoEvento() {
    this.loaderService.display(true);
    let tipo = CATALOGO_TIPO_EVENTO;
    this.aplicacionService.consultarCatalogoSeguridad(tipo).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.tipoEventos = data.json();
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

  private consultarUsuarioApp(idApp: string) {
    if (this.aplicacionId && this.nombreUsuarioModal) {
      this.loaderService.display(true);
      this.usuarioService.consultarUsuarioApp(this.aplicacionId, this.nombreUsuarioModal).subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.msgsDialog = [];
            this.usuarios = data.json();
            this.loaderService.display(false);
          }
        },
        error => {
          this.msgsDialog  = [];
          let mensaje: Mensaje = error.json();
          Util.mostrarMensaje(this.msgsDialog , mensaje, true);
          this.loaderService.display(false);
        }
      );
    }
  }
  seleccionarUsuario(selectedUsuario: any) {
    if (!this.selectedUsuario) {
      // Mostrar mensaje de alerta
      this.msgsDialog = [{
        severity: 'warn',
        summary: 'Alerta',
        detail: 'Por favor, selecciona un usuario.'
      }];
    }else{
    this.idUsuario = selectedUsuario.usuarioId;
    this.nombreUsuario = selectedUsuario.logonName;
    this.displayModal = false;
  }
  }

  openModal() {
    this.displayModal = true;
  }
  public limpiarModalUsuario(){
    this.nombreUsuarioModal = null;
    this.msgsDialog = [];
    this.selectedUsuario = null;
    this.usuarios = [];
  }
  public cancelarFiltro() {
    this.displayModal = false;
    this.selectedUsuario = null;
    this.limpiarModalUsuario();
    this.msgsDialog = [];
  }
  formChange(event) {
    if (this.nombreUsuarioModal !== null && this.nombreUsuarioModal.trim() !== '' && this.nombreUsuarioModal !== undefined) {
      this.disableFiltrar = false;
    } else {
      this.disableFiltrar = true;
    }

  }
  actualizarBuscarHabilitado() {
    this.buscarHabilitado = Boolean(this.aplicacionId);
  }


  public limpiar() {
    this.idEvento = "0";
    this.idUsuario = "0";
    this.fechaInicio = null;
    this.fechaFin = null;
    this.auditoria = null;
    this.totalRegistros = 0;
    this.msgs = [];
    this.aplicacionId = "0";
    this.campoActivo = "0";
    this.nombreUsuario = "";
    this.usuariosFiltrados = null;
    this.buscarHabilitado = false;


  }

  public consultarAuditoria() {
    this.msgs = [];
    this.auditoria = null;
    if (this.idEvento == "0" || this.fechaInicio == null || this.fechaFin == null) {
      this.mostrarMensaje(APP100114);
      this.campoError = true;
    } else {
      this.msgs = [];
      this.buscando = true;
      this.buscar(0, this.cantidadFilas);
    }

  }

  public buscar(pagInicio: number, cantRegistros: number) {
    this.loaderService.display(true);
    this.msgs = [];
    if (!this.buscando) {
      this.msgs = [];
      return;
    }

    this.auditoriaService.contarConsultarAuditoria(this.aplicacionId, this.idEvento, this.fechaInicio, this.fechaFin, this.idUsuario,this.campoActivo).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.msgsCargar = [];
          this.totalRegistros = parseInt(data.text());
          this.auditoriaService.consultarAuditoria(this.aplicacionId, this.idEvento, this.fechaInicio, this.fechaFin, this.idUsuario,this.campoActivo, pagInicio, cantRegistros).subscribe(
            data => {
              if (data.status == RESPONSE_OK) {
                this.msgsCargar = [];
                this.auditoria = data.json();
                for (var i = 0; i < this.auditoria.length; i++) {
                  if (this.auditoria[i].fechaEvento) {
                    let cadenaFecha: string;
                    cadenaFecha = this.auditoria[i].fechaEvento.toString();
                    cadenaFecha = cadenaFecha.replace("T", " ");
                    cadenaFecha = cadenaFecha.replace(".0Z", "");
                    this.auditoria[i].fechaEvento = cadenaFecha;
                  }
                  this.auditoria[i].tipoEvento = this.nombreEvento[this.auditoria[i].tipoEvento];
                  this.auditoria[i].aplicacionId = this.nombreAplicaciones[this.auditoria[i].aplicacionId];
                  this.msgsCargar = [];
                  this.statSend = false;
                }
                this.loaderService.display(false);
              }
            }, error => {
              this.msgsCargar = [];
              this.msgs = [];
              this.statSend = false;
              let mensaje: Mensaje = error.json();
              Util.mostrarMensaje(this.msgs, mensaje, false);
              this.loaderService.display(false);
            }
          );
        } else {
          this.msgsCargar = [];
          this.msgs = [];
          let mensaje: Mensaje = data.json();
          Util.mostrarMensaje(this.msgs, mensaje, false);
          this.statSend = false;
          this.loaderService.display(false);
        }
      }, error => {
        this.msgsCargar = [];
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, false);
        this.statSend = false;
        this.loaderService.display(false);
      }
    );
  }


  public cambiarPagina(event: LazyLoadEvent) {
    this.msgsCargar = [];
    this.primerRegistro = event.first;
    this.buscar(this.primerRegistro, this.cantidadFilas);
  }

  public generarReporteExcel(tipo: string) {
    this.auditoriaService.consultarAuditoria(this.aplicacionId, this.idEvento, this.fechaInicio, this.fechaFin, this.idUsuario,this.campoActivo, 0, 10).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          this.msgs = [];
          this.auditoriaService.imprimirReporteAuditoria(this.aplicacionId, this.idEvento, this.fechaInicio, this.fechaFin, this.idUsuario,this.campoActivo, 0, 0, tipo, tipo).subscribe(response => {
            var blob = new Blob([response._body], { type: 'application/octet-stream' });
            saveAs(blob, "consultaAuditoria.xlsx");
          });
        } else {
          this.msgs = [];
          let mensaje: Mensaje = data.json();
          Util.mostrarMensaje(this.msgs, mensaje, true);
        }
      },
      error => {
        this.msgs = [];
        let mensaje: Mensaje = error.json();
        Util.mostrarMensaje(this.msgs, mensaje, true);
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
  public consultarNombreEvento() {
    this.loaderService.display(true);
    let tipo = CATALOGO_TIPO_EVENTO;
    this.aplicacionService.consultarCatalogoSeguridad(tipo).subscribe(
      data => {
        if (data.status == RESPONSE_OK) {
          let lista = data.json();
          for (let e of lista) {
            this.nombreEvento[e.catalogoId] = e.descripcion
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

  validarEnviando() {
    if (!this.statSend) {
      this.statSend = true;
      this.consultarAuditoria();
      return true;
    } else {
      alert("Consulta en proceso...");
      return false;
    }
  }

    /*Permite consultar los campos de directorio activo*/
    private consultarCamposDirectorioActivos() {
      this.loaderService.display(true);
      this.aplicacionService.consultarCamposDirectorioActivos().subscribe(
        data => {
          if (data.status == RESPONSE_OK) {
            this.camposDirectorioActivo = data.json();
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
