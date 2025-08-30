import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OperacionComponent } from 'app/operacion/operacion.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_ADMINISTRAR_OPERACION, component: OperacionComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class OperacionRoutingModule { }
