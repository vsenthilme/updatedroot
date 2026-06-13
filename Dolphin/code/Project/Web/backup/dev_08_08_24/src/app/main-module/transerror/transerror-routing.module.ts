import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TranserrorComponent } from './transerror/transerror.component';
import { AxinterfaceComponent } from './axinterface/axinterface.component';

const routes: Routes = [
  {
    path: 'error',
    component: TranserrorComponent,
  },
  {
    path: 'axinterface',
    component: AxinterfaceComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TranserrorRoutingModule { }
