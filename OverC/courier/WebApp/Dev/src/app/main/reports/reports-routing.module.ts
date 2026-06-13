import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsoleTrackingComponent } from './console-tracking/console-tracking.component';
import { InventoryScanningComponent } from './inventory-scanning/inventory-scanning.component';
import { CustomsCalculationReportComponent } from './customs-calculation-report/customs-calculation-report.component';
import { CustomsCalculationReportLinesComponent } from './customs-calculation-report/customs-calculation-report-lines/customs-calculation-report-lines.component';
import { CustomsInvoiceComponent } from './finance/customs-invoice/customs-invoice.component';
import { CustomsCostingComponent } from './finance/customs-costing/customs-costing.component';

const routes: Routes = [
  { path: 'consoleTracking', component: ConsoleTrackingComponent, data: { title: 'Reports', module: 'Console Tracking Report' } },
  { path: 'inventoryScanning', component: InventoryScanningComponent, data: { title: 'Reports', module: 'Inventory Scan' } },
  { path: 'pendingCustoms', component: InventoryScanningComponent, data: { title: 'Reports', module: 'Pending Customs' } },
  { path: 'customsCalculations', component: CustomsCalculationReportComponent, data: { title: 'Reports', module: 'Customs Calculations Report' } },
  { path: 'customsCalculationsline/:code', component: CustomsCalculationReportLinesComponent, data: { title: 'Mid-Mile', module: 'Customs Calculations Report' } },
  { path: 'expenseSheet', component: CustomsInvoiceComponent, data: { title: 'Reports', module: 'Customs Fees' } },
  { path: 'costSheet', component: CustomsCostingComponent, data: { title: 'Mid-Mile', module: 'Customs Costing' } },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportsRoutingModule { }
