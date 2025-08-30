import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  RESPONSE_OK, MENU, VINCULO, LI_MENU_ABRE, LI_MENU_ABRE2, LI_VINCULO_MEDIO2,
  LI_MENU_CIERRA, LI_VINCULO_ABRE, LI_VINCULO_ABRE2, LI_VINCULO_MEDIO, LI_VINCULO_CIERRA, UL_ABRE, UL_CIERRA, A_CIERRA, TOKEN, USER_ID, APLICACION_ID, URL_PARAMETROS
} from './util/Constants';
import { Util } from 'app/util/Util';
import { Mensaje } from 'app/modelo/Mensaje';
import { Message } from 'primeng/primeng';
import { Usuario } from 'app/modelo/Usuario';
import { SesionService } from './services/sesion.service'
import { CommonObjectsService } from './services/common-objects.service';
import { ConfiguracionInicialFrontService } from './services/configuracion-inicial-front.service'
import { LoaderService } from './spinner/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [SesionService, CommonObjectsService, ConfiguracionInicialFrontService]

})
export class AppComponent implements OnInit {
  private msgs: Message[] = [];
  private menuDinamico: string = null;
  private usuario: Usuario;

  constructor(private router: Router, private sesionService: SesionService, private loaderService: LoaderService, private commonObjectsService: CommonObjectsService, private configuracionInicialFrontService: ConfiguracionInicialFrontService) {
    this.usuario = new Usuario();
  }

  getUrl(redirect: string) {
    try {
      if (this.usuario == null) {
        window.location.href = redirect;
      }
    } catch (e) {
      this.showError('Falló consultando la url.')
    }
  }

  ngOnInit() {
    this.loaderService.display(false);
    var url = '';
    var idAplicacion = '';
    var redirect = '';
    var urlParametros = '';
    this.configuracionInicialFrontService.getConfiguracionInicial().subscribe(
      data => {
        if (data.status === RESPONSE_OK) {
          var listaCongiguracion = data.json();
          url = listaCongiguracion.filter(x => x.nombre == "URL_BASE")[0].valor;
          idAplicacion = listaCongiguracion.filter(x => x.nombre == "ID_APLICACION")[0].valor;
          redirect = listaCongiguracion.filter(x => x.nombre == "URL_LOGIN")[0].valor;
          urlParametros = listaCongiguracion.filter(x => x.nombre == "URL_PARAMETROS")[0].valor;
          sessionStorage.setItem('url', url);
          sessionStorage.setItem(APLICACION_ID, idAplicacion);
          sessionStorage.setItem('redirect', redirect);
          localStorage.setItem('redirect', redirect);
          sessionStorage.setItem(URL_PARAMETROS, urlParametros);
        }

        // se obtiene la cadena despues del hash
        let code = location.hash;
        this.usuario = JSON.parse(sessionStorage.getItem("usuario"));
        //se valida que hay sesión creada o que vienen los parametros de logueo
        if (code.includes('#/?code') && this.usuario == null) {
          let cadena = code.replace('#/?', '');
          let parametros = cadena.split('&');
          let parms = parametros[0].split('=');
          let parms2 = parametros[1].split('=');
          let codigo = parms[1];
          let userId = parms2[1];
          // se guarda en session el user_id
          sessionStorage.setItem(USER_ID, userId);
          sessionStorage.setItem("code_auto", codigo);
          sessionStorage.setItem("code_auto", codigo);
          let split = url.split(':');
          sessionStorage.setItem('port', split[2]);
          // se llama al servicio de obtencion del token
          this.token(codigo, userId);

        } else if (this.usuario != null) {
          this.getUrl(redirect);
          this.menuDinamico = sessionStorage.getItem('menu');
          this.router.navigate(['/inicio/home']);
        }
        else {
          this.getUrl(redirect);
        }

        let men = sessionStorage.getItem('menu');
        if (men != null) {
          this.menuDinamico = men;
        }
        if (this.usuario != null && sessionStorage.getItem(APLICACION_ID) != null) {
          this.router.navigate(['/inicio/home']);
        }
      },
      error => {
        this.showError("Error inesperado al tratar de consultar la configuración del sistema.");
      }
    );
  }

  logout() {
    sessionStorage.clear();
    this.sesionService.cerrarSesion();
    location.reload();
  }

  /**
    * metodo encargado de hacer el llamado al servicio de obtencion de token
    * @param autorizacion codigo de autorizacion de acceso
    * @param userId id del usuario en la libreta de usuarios
    */
  token(autorizacion: string, userId: string) {
    this.sesionService.obtenerToken(autorizacion, userId).subscribe(
      data => {
        let dat = data.json();
        if (data.status == RESPONSE_OK) {
          sessionStorage.setItem(TOKEN, dat.tokenAcceso);
          // se llama al servicio de obtencion de roles y permisos
          this.loaderService.display(false);
          this.rolesYPermisos();
        } else {
          this.loaderService.display(false);
          let mensaje = data.json();
          this.showError(mensaje.message);
        }
      }, error => {
        this.loaderService.display(false);
        let mensaje = error.json();
        this.showError(mensaje.message);
      }
    );
  }

  /**
   * metodo encargado de llamar al servicio de obtencion de roles y permisos
   */
  rolesYPermisos() {
    this.sesionService.obtenerPermisos().subscribe(
      data => {
        let dat = data.json();
        if (data.status == RESPONSE_OK) {
          let usr: Usuario = new Usuario();
          usr = dat.usuario;
          usr.usuarioId = usr.userId;
          usr.roles = dat.permisos.roles;
          sessionStorage.setItem("usuario", JSON.stringify(usr));
          // llamado al metodo de creacion de menu
          this.loaderService.display(false);
          this.crearMenu(dat.permisos);
        } else {
          this.loaderService.display(false);
          let mensaje = data.json();
          this.showError(mensaje.message);
        }
      }, error => {
        this.loaderService.display(false);
        let mensaje = error.json();
        this.showError(mensaje.message);
      }
    );
  }

  /**
   * metodo encargado de construir el menu dinamico de RIEL
   */
  public crearMenu(listaMenu) {
    // cadena donde se formara el meni
    let menu: string = "";
    let id: number = 0;
    // se recorre la lista devuelta por el servicio
    for (let m of listaMenu.permisos) {
      // se valida si el elemento es de tipo menu o vinculo
      if (m.tipo == MENU) {
        menu = menu + LI_MENU_ABRE + id + LI_MENU_ABRE2 + m.nombreObjeto + A_CIERRA;
        id++;
        if (m.hijosOpcion.length > 0) {
          menu = menu + UL_ABRE;
          for (let mi of m.hijosOpcion) {
            menu = menu + LI_VINCULO_ABRE + id + LI_VINCULO_ABRE2 + LI_VINCULO_MEDIO + mi.nombreObjeto + LI_VINCULO_MEDIO2 + mi.descripcion + LI_VINCULO_CIERRA;
            id++;
          }
          menu = menu + UL_CIERRA + LI_MENU_CIERRA;
        } else {
          menu = menu + LI_MENU_CIERRA;
        }
      }// se ejecuta cuando es un elemento sin padre y sin hijos
      else {
        menu = menu + LI_VINCULO_ABRE + id + LI_VINCULO_ABRE2 + LI_VINCULO_MEDIO + m.nombreObjeto + LI_VINCULO_MEDIO2 + m.descripcion + LI_VINCULO_CIERRA;
      }
    }
    this.menuDinamico = menu;
    sessionStorage.setItem('menu', menu);
    location.reload();
  }

  /**
   * metodo encargado de dar funcionalidad al menu creado dinamicamente, ya que al ser creado de esta forma pierde las propiedades y los estilos complejos.
   * Esto es un evento que detecta el cambio en la url atravez del hash '#'; se activa y redirige a la nueva ruta de cambio.
   * juagomez@heinsohn.com.co
   */
  routerLink() {
    window.addEventListener("hashchange", () => { this.router.navigate([location.hash.replace('#', '')]) }, false);
  }
  /*
   * Muestra un mensaje de error
   */
  private showError(mensaje: string) {
    this.msgs = [];

    if (mensaje == null || mensaje === ""){
      mensaje = "Error en la autenticación. SIA3 no está disponible. Por favor, intente más tarde.";
    }

    this.msgs.push({ severity: 'error', summary: 'ERROR:', detail: mensaje });
  }
}

