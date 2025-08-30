import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { UsuarioComponent } from './usuario.component';
import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioService } from './usuario.service';
import {
  InputMaskModule, DataTableModule, SharedModule, DialogModule, ConfirmDialogModule, ContextMenuModule, GrowlModule,
  MessagesModule, ConfirmationService, RadioButtonModule, PickListModule, CalendarModule, TreeModule, TreeNode, TreeTableModule
} from 'primeng/primeng';
@NgModule({
  declarations: [UsuarioComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    DataTableModule, SharedModule,
    TreeModule,
    MessagesModule,
    DialogModule,
    ConfirmDialogModule,
    ContextMenuModule,
    GrowlModule
  ],

  providers: [UsuarioService]
})
export class UsuarioModule { }