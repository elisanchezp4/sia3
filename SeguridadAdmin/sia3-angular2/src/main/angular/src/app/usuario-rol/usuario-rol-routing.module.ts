import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioRolComponent } from 'app/usuario-rol/usuario-rol.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_ADMINISTRAR_USUARIO_ROL, component: UsuarioRolComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class UsuarioRolRoutingModule { }
