import { Component, OnInit } from '@angular/core';
import { Message, ConfirmationService } from 'primeng/primeng';
import { Tree, TreeNode } from 'primeng/primeng';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Util } from 'app/util/Util';
import { Mensaje } from 'app/modelo/Mensaje';
import { AplicacionService } from 'app/aplicacion/aplicacion.service';
import { RolService } from 'app/rol/rol.service';
import { MensajesService } from 'app/services/mensajes.service';
import { Router, ActivatedRoute, Params } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Rol } from 'app/modelo/Rol';
import { Aplicacion } from 'app/modelo/Aplicacion';
import { PathConstants } from 'app/util/PathConstants';
import { Estado } from 'app/modelo/Estado';
import { Usuario } from 'app/modelo/Usuario';
import { RESPONSE_OK, ERROR_VALIDACION_CAMPO_NOMBRE_ROL, ERROR_VALIDACION_CAMPO_ESTADO_ROL, ERROR_VALIDACION_CAMPOS_APLICACION, CATALOGO_ESTADO, CU003MEN11, APP100115 } from 'app/util/Constants';
import { LoaderService } from '../../spinner/loader.service';

@Component({
    selector: 'app-crear-editar-rol',
    templateUrl: './crear-editar-rol.component.html',
    styleUrls: ['./crear-editar-rol.component.css'],
    providers: [MensajesService, ConfirmationService, AplicacionService, RolService]
})
export class CrearEditarRolComponent implements OnInit {

    //listado de Roles
    private roles: Rol[] = [];
    //Rol a editar o crear
    private rol: Rol = new Rol();
    private rolCopia: Rol = new Rol();
    //listado de Aplicaciones
    private aplicaciones: Aplicacion[] = [];
    //Aplicación
    private aplicacion: Aplicacion = new Aplicacion();
    //Estados de la aplicación
    private estados: Estado[] = [];
    //mensajes que se muestran en la pantalla
    private msgs: Message[] = [];
    private campoError: boolean = false;
    private aplicacionId: string;
    //estado aplicacion
    private estado: string;
    private usuario: Usuario;
    private usuarioId: number;
    //inactiva boton guardar
    public disableGuardar: boolean;

    constructor(private loaderService: LoaderService, private aplicacionService: AplicacionService, private rolService: RolService, private mensajesService: MensajesService, private router: Router, private confirmationService: ConfirmationService) { }

    ngOnInit() {
        this.disableGuardar = true;
        if (sessionStorage.getItem("usuario")) {
            this.usuario = JSON.parse(sessionStorage.getItem("usuario"));
            this.usuarioId = this.usuario.usuarioId;
            this.consultarAplicacionesPorUsuario();
        }
        this.consultaEstados();
        this.rol.estado = "0";
        this.rol.aplicacionId = "0";
        if (Util.existeItemSession("rol")) {
            this.rol = JSON.parse(sessionStorage.getItem("rol"));
            this.rolCopia = JSON.parse(sessionStorage.getItem("rol"));
            sessionStorage.removeItem('rol');
        } else {
            this.rol.aplicacionId = sessionStorage.getItem('idAplicacion');
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


    private guardar() {
        if (this.validar()) {
            this.loaderService.display(true);
            this.rol.usuarioModificacion = this.usuarioId;
            this.rol.nombre = this.rol.nombre.trim();
            this.rol.path = this.rol.path.trim();
            this.rolService.guardar(this.rol).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.limpiarFormulario();
                        let mensaje: Mensaje = new Mensaje();
                        mensaje = data.json();
                        sessionStorage.setItem("mensaje", JSON.stringify(mensaje));
                        this.router.navigate([PathConstants.PATH_ADMINISTRAR_ROL]);
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
    }

    private modificar() {
        if (this.validarModificar()) {
            this.loaderService.display(true);
            this.rol.usuarioModificacion = this.usuarioId;
            this.rol.nombre = this.rol.nombre.trim();
            this.rol.path = this.rol.path.trim();
            this.rolService.modificar(this.rol).subscribe(
                data => {
                    if (data.status == RESPONSE_OK) {
                        this.limpiarFormulario();
                        let mensaje: Mensaje = new Mensaje();
                        mensaje = data.json();
                        sessionStorage.setItem("mensaje", JSON.stringify(mensaje));
                        this.router.navigate([PathConstants.PATH_ADMINISTRAR_ROL]);
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
    }

    /*Permite volver a la lista de aplicaciones*/
    private volver() {
        this.mensajesService.getMensaje(CU003MEN11).subscribe(
            data => {
                let mensaje: Mensaje = data.json();
                this.confirmationService.confirm({
                    message: mensaje.descripcion,
                    accept: () => {
                        this.router.navigate([PathConstants.PATH_ADMINISTRAR_ROL]);
                        if (Util.existeItemSession("rol")) {
                            Util.removerItemSession("rol");
                        }
                    }
                });
            }, error => {
                this.msgs = [];
                let mensaje: Mensaje = error.json();
                Util.mostrarMensaje(this.msgs, mensaje, true);
            });
    }



    private validar() {
        this.msgs = [];
        let mensaje: Mensaje = new Mensaje();
        mensaje.tipoMensaje = Mensaje.ERROR;

        if (this.rol.nombre == undefined) {
            this.rol.nombre = "";
        }
        if (this.rol.path == undefined) {
            this.rol.path = "";
        }

        if (this.aplicacionId == "0" || this.rol.nombre.trim() == null || this.rol.nombre.trim() == ""
            || this.rol.estado == "0" || this.rol.path.trim() == null || this.rol.path.trim() == "") {
            mensaje.descripcion = ERROR_VALIDACION_CAMPOS_APLICACION;
            Util.mostrarMensaje(this.msgs, mensaje, true);
            this.campoError = true;
            return false;
        }
        return true;
    }

    private validarModificar() {
        this.msgs = [];
        let mensaje: Mensaje = new Mensaje();
        mensaje.tipoMensaje = Mensaje.ERROR;


        if (this.aplicacionId == "0" || this.rol.nombre.trim() == null || this.rol.nombre.trim() == ""
            || this.rol.estado == "0" || this.rol.path.trim() == null || this.rol.path.trim() == "") {
            mensaje.descripcion = ERROR_VALIDACION_CAMPOS_APLICACION;
            Util.mostrarMensaje(this.msgs, mensaje, true);
            this.campoError = true;
            return false;
        }
        return true;
    }

    public limpiarFormulario() {
        this.rol = new Rol();
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
    /**
     * Metodo que se activa con cada cambio en los cmpos del formulario
     * @param event
     */
    formChange(event) {
        this.disableGuardar = false;
    }
}
