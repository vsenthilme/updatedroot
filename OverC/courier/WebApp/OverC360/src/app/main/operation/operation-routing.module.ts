import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsignmentComponent } from './consignment/consignment.component';
import { ConsignmentNewComponent } from './consignment/consignment-new/consignment-new.component';
import { AssignmentComponent } from './assignment/assignment.component';

const routes: Routes = [
  {path:'consignment',component: ConsignmentComponent, data: { title: 'Operations', module: 'Consignment' } },
  {path:'consignment-new/:code',component: ConsignmentNewComponent, data: { title: 'Operations', module: 'Consignment' } },
  {path:'assignment',component: AssignmentComponent, data: { title: 'Operations', module: 'Assignment' } },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperationRoutingModule { }
