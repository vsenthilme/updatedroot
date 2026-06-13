import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomsInvoiceComponent } from './customs-invoice/customs-invoice.component';
import { CustomsInvoiceCreateComponent } from './customs-invoice/customs-invoice-create/customs-invoice-create.component';
import { CostingSheetComponent } from '../airport/costing-sheet/costing-sheet.component';

const routes: Routes = [
  {path:'costingSheet',component: CostingSheetComponent, data: { title: 'Finance', module: 'Customs Costing' } },
  {path:'invoice',component: CustomsInvoiceComponent, data: { title: 'Finance', module: 'Invoice' } },
  {path:'invoice-new/:code',component: CustomsInvoiceCreateComponent, data: { title: 'Finance', module: 'Invoice-New' } },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FinanceRoutingModule { }
