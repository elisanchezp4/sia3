import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AuditoriaComponent } from './auditoria.component';
import { Routes, RouterModule } from '@angular/router';
import {
  InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule, ContextMenuModule,
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule, TreeModule, TreeNode
} from 'primeng/primeng';
import { AuditoriaRoutingModule } from 'app/auditoria/auditoria-routing.module';
@NgModule({
  declarations: [AuditoriaComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    DataTableModule,
    CalendarModule,
    AuditoriaRoutingModule,
    MessagesModule,
    DialogModule,
    TreeModule,
    ConfirmDialogModule

  ],

  providers: []
})
export class AuditoriaModule { }
