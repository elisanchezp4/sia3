import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioAplicacionComponent } from 'app/usuario-aplicacion/usuario-aplicacion.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_USUARIO_APLICACION, component: UsuarioAplicacionComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class UsuarioAplicacionRoutingModule { }
