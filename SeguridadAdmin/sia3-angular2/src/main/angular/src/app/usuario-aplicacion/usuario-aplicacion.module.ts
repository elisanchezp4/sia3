import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { UsuarioAplicacionComponent } from './usuario-aplicacion.component';
 
import {
   InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule, ContextMenuModule,GrowlModule, 
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule, TreeModule, TreeNode, AutoCompleteModule
} from 'primeng/primeng';
import { UsuarioAplicacionRoutingModule } from 'app/usuario-aplicacion/usuario-aplicacion-routing.module';

@NgModule({
  declarations: [UsuarioAplicacionComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    DataTableModule,
    TreeModule,
    ContextMenuModule,
    GrowlModule,
    DialogModule,
    MessagesModule, 
    ConfirmDialogModule,
    UsuarioAplicacionRoutingModule,
    PickListModule, 
    AutoCompleteModule
  ],
   
  providers: []
})
export class UsuarioAplicacionModule { }