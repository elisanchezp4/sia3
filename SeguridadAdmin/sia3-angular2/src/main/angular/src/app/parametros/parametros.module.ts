import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {
  InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule,
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule, TreeModule,TreeNode
} from 'primeng/primeng';
import { ParametrosUnivalorComponent } from './parametros-univalor/univalor.component';
import { ParametrosRoutingModule } from 'app/parametros/parametros-routing.module';
@NgModule({
  declarations: [ParametrosUnivalorComponent],
  imports: [
    BrowserModule,
    FormsModule, 
    HttpModule, 
    DataTableModule,
    TreeModule,
    MessagesModule,
    ConfirmDialogModule,
    ParametrosRoutingModule,
    DialogModule
  ],
   
  providers: []
})
export class ParametrosModule { 


}