import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { OperacionComponent } from './operacion.component'; 
import { OperacionService } from './operacion.service'; 
import {
  InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule, ContextMenuModule,GrowlModule, 
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule, TreeModule, TreeNode
} from 'primeng/primeng';
import { OperacionRoutingModule } from 'app/operacion/operacion-routing.module';
@NgModule({
  declarations: [OperacionComponent],
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
    OperacionRoutingModule,
    ReactiveFormsModule    
  ],
   
  providers: [OperacionService],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA
  ]
})
export class OperacionModule { 


}