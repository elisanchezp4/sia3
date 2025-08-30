import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from 'app/app-routing.module';
import { AppComponent } from './app.component';
import { AplicacionModule } from './aplicacion/aplicacion.module';
import { UsuarioModule } from './usuario/usuario.module';
import { UsuarioComponent } from './usuario/usuario.component';
import { RolComponent } from './rol/rol.component';
import { RolModule } from './rol/rol.module';
import { NodeService } from './rol/arbol.service';
import { OperacionService } from './operacion/operacion.service';
import { OperacionComponent } from './operacion/operacion.component';
import { OperacionModule } from './operacion/operacion.module';
import { UsuarioRolModule } from './usuario-rol/usuario-rol.module';
import { AuditoriaModule } from './auditoria/auditoria.module';
import { InicioModule } from './inicio/inicio.module';
import { ParametrosModule } from './parametros/parametros.module';
import { SafeHtml } from "./util/CaptalizePipe";
import { UsuarioAplicacionModule } from './usuario-aplicacion/usuario-aplicacion.module';
import {
  InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule, ContextMenuModule, GrowlModule,
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule, TreeModule, TreeNode, AutoCompleteModule
} from 'primeng/primeng';
import { SpinnerComponent } from './spinner/spinner.component';
import { LoaderService } from './spinner/loader.service';
@NgModule({
  declarations: [
    AppComponent,
    UsuarioComponent,
    SafeHtml, SpinnerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AplicacionModule,
    AppRoutingModule,
    RolModule,
    OperacionModule,
    UsuarioRolModule,
    AuditoriaModule,
    ConfirmDialogModule, DialogModule,
    MessagesModule,
    InicioModule,
    ParametrosModule,
    UsuarioAplicacionModule,
    PickListModule,
    DataTableModule,
    AutoCompleteModule
  ],
  providers: [LoaderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
