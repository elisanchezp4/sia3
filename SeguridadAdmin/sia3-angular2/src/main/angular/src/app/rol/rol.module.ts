import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RolComponent } from './rol.component';  
import { NodeService } from './arbol.service'; 
import {
  InputMaskModule, DataTableModule, SharedModule, ConfirmDialogModule,
  MessagesModule, ConfirmationService, DialogModule, RadioButtonModule, PickListModule, CalendarModule, TreeModule,TreeNode, TreeTableModule
} from 'primeng/primeng';
import { CrearEditarRolComponent } from './crear-editar-rol/crear-editar-rol.component';
import { RolRoutingModule } from 'app/rol/rol-routing.module';
import { CrearEditarOperacionesComponent } from './crear-editar-operaciones/crear-editar-operaciones.component';
@NgModule({
  declarations: [RolComponent, CrearEditarRolComponent, CrearEditarOperacionesComponent],
  imports: [
    BrowserModule,
    FormsModule, 
    HttpModule, 
    DataTableModule,
    TreeModule,
    MessagesModule,
    ConfirmDialogModule,
    RolRoutingModule,
    TreeTableModule,
    ReactiveFormsModule,
    DialogModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA
  ],
  providers: [NodeService]
})
export class RolModule { 


}