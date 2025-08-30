import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { UsuarioRolComponent } from './usuario-rol.component';
 
import {
   InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule, ContextMenuModule,GrowlModule, 
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule, TreeModule, TreeNode
} from 'primeng/primeng';
import { UsuarioRolRoutingModule } from 'app/usuario-rol/usuario-rol-routing.module';

@NgModule({
  declarations: [UsuarioRolComponent],
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
    UsuarioRolRoutingModule
  ],
   
  providers: []
})
export class UsuarioRolModule { }