import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RolComponent } from 'app/rol/rol.component';
import { CrearEditarRolComponent } from 'app/rol/crear-editar-rol/crear-editar-rol.component';
import { CrearEditarOperacionesComponent } from 'app/rol/crear-editar-operaciones/crear-editar-operaciones.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_ADMINISTRAR_ROL, component: RolComponent},
  {  path: PathConstants.PATH_CREAR_EDITAR_ROL, component: CrearEditarRolComponent},
  {  path: PathConstants.PATH_CREAR_EDITAR_OPERACIONES, component: CrearEditarOperacionesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class RolRoutingModule { }
