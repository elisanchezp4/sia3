import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ParametrosUnivalorComponent } from 'app/parametros/parametros-univalor/univalor.component';
import { PathConstants } from 'app/util/PathConstants';

const routes: Routes = [
  { path: PathConstants.PATH_PARAMETROS_UNIVALOR, component: ParametrosUnivalorComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class ParametrosRoutingModule { }
