import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportsRoutingModule } from './reports-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { ConsoleTrackingComponent } from './console-tracking/console-tracking.component';
import { InventoryScanningComponent } from './inventory-scanning/inventory-scanning.component';
import { CustomsCalculationReportComponent } from './customs-calculation-report/customs-calculation-report.component';
import { CustomsCalculationReportLinesComponent } from './customs-calculation-report/customs-calculation-report-lines/customs-calculation-report-lines.component';
import { CustomsInvoiceComponent } from './finance/customs-invoice/customs-invoice.component';
import { CustomsCostingComponent } from './finance/customs-costing/customs-costing.component';


@NgModule({
  declarations: [
    ConsoleTrackingComponent,
    InventoryScanningComponent,
    CustomsCalculationReportComponent,
    CustomsCalculationReportLinesComponent,
    CustomsInvoiceComponent,
    CustomsCostingComponent
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    SharedModule
  ]
})
export class ReportsModule { }
