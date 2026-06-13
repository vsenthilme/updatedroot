import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main.component';

const routes: Routes = [{ 
  path: '', component: MainComponent,
  children: [ 
  { path: 'idMaster', loadChildren: () => import('../main/id-masters/id-masters.module').then(m => m.IdMastersModule) },
  { path: 'master', loadChildren: () => import('../main/master/master.module').then(m => m.MasterModule) },
  { path: 'airport', loadChildren: () => import('../main/airport/airport.module').then(m => m.AirportModule) },
  { path: 'operation', loadChildren: () => import('../main/operation/operation.module').then(m => m.OperationModule) },
  { path: 'reports', loadChildren: () => import('../main/reports/reports.module').then(m => m.ReportsModule) },
  { path: 'finance', loadChildren: () => import('../main/finance/finance.module').then(m => m.FinanceModule) },
  { path: 'lastMile', loadChildren: () => import('../main/last-mile/last-mile.module').then(m => m.LastMileModule) },
  ]
 }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
