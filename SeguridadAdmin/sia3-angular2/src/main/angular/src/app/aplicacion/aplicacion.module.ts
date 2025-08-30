import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AplicacionComponent } from './aplicacion.component';
import { AplicacionRoutingModule } from './aplicacion-routing.module';
import { AplicacionService } from './aplicacion.service';
import {
  InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule,
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule
} from 'primeng/primeng';
import { CrearEditarAplicacionComponent } from './crear-editar-aplicacion/crear-editar-aplicacion.component';
@NgModule({
  declarations: [AplicacionComponent, CrearEditarAplicacionComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AplicacionRoutingModule,
    DataTableModule,
    MessagesModule,
    ConfirmDialogModule
  ],
  providers: [AplicacionService],
})
export class AplicacionModule { }