import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FinanceRoutingModule } from './finance-routing.module';
import { CustomsInvoiceComponent } from './customs-invoice/customs-invoice.component';
import { SharedModule } from '../../shared/shared.module';
import { CostingSheetBulkupdateComponent } from '../airport/costing-sheet/costing-sheet-bulkupdate/costing-sheet-bulkupdate.component';
import { CustomsInvoiceCreateComponent } from './customs-invoice/customs-invoice-create/customs-invoice-create.component';
import { TrimDirective } from '../../trim.directive';


@NgModule({
  declarations: [
    CustomsInvoiceComponent,
    CostingSheetBulkupdateComponent,
    CustomsInvoiceCreateComponent,
    
  ],
  imports: [
    CommonModule,
    FinanceRoutingModule,
    SharedModule
  ]
})
export class FinanceModule { }
