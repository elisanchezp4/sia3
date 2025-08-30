import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AplicacionComponent } from 'app/aplicacion/aplicacion.component';
import { CrearEditarAplicacionComponent } from 'app/aplicacion/crear-editar-aplicacion/crear-editar-aplicacion.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_ADMINISTRAR_APLICACION, component: AplicacionComponent},
  {  path: PathConstants.PATH_CREAR_EDITAR_APLICACION, component: CrearEditarAplicacionComponent}
       
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class AplicacionRoutingModule { }
