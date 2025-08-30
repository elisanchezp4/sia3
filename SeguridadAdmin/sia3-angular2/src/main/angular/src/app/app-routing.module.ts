import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioComponent } from '../app/usuario/usuario.component';
import { AplicacionComponent } from '../app/aplicacion/aplicacion.component';
import { RolComponent } from '../app/rol/rol.component';
import { OperacionComponent } from '../app/operacion/operacion.component';
import { UsuarioRolComponent } from '../app/usuario-rol/usuario-rol.component';
import { AuditoriaComponent } from '../app/auditoria/auditoria.component';
import { AppComponent } from './app.component';
import { PathConstants } from '../app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_ADMINISTRAR_USUARIO, component: UsuarioComponent },
  { path: PathConstants.PATH_ADMINISTRAR_APLICACION, component: AplicacionComponent },
  { path: PathConstants.PATH_ADMINISTRAR_ROL, component: RolComponent },
  { path: PathConstants.PATH_ADMINISTRAR_OPERACION, component: OperacionComponent },
  { path: PathConstants.PATH_ADMINISTRAR_USUARIO_ROL, component: UsuarioRolComponent },
  { path: PathConstants.PATH_ADMINISTRAR_AUDITORIA, component: AuditoriaComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule { }