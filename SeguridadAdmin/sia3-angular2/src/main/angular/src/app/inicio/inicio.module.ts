import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {
  InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule,
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule
} from 'primeng/primeng';
import { InicioComponent } from './inicio.component';
import { InicioRoutingModule } from './inicio-routing.module';
@NgModule({
  declarations: [InicioComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    SharedModule,
    DataTableModule,
    ConfirmDialogModule,
    MessagesModule,
    InputMaskModule,
    DialogModule,
    RadioButtonModule,
    PickListModule,
    CalendarModule,
    InicioRoutingModule
  ],
  providers: []
})
export class InicioModule { }
