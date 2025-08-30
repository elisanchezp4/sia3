import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InicioComponent } from './inicio.component';
import { PathConstants } from '../util/PathConstants';
const routes: Routes = [
  { path: PathConstants.PATH_INICIO, component: InicioComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: []
})
export class InicioRoutingModule { }
