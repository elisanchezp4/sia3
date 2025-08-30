import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioComponent } from 'app/usuario/usuario.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_ADMINISTRAR_USUARIO, component: UsuarioComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class UsuarioRoutingModule { }