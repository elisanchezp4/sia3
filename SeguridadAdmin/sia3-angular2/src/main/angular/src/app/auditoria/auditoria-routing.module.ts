import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditoriaComponent } from 'app/auditoria/auditoria.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_ADMINISTRAR_AUDITORIA, component: AuditoriaComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class AuditoriaRoutingModule { }
