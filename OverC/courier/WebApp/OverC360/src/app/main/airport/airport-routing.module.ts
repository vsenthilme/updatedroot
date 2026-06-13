import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PreAlertManifestComponent } from './pre-alert-manifest/pre-alert-manifest.component';
import { ConsoleComponent } from './console/console.component';
import { BondedManifestComponent } from './bonded-manifest/bonded-manifest.component';
import { CcrComponent } from './ccr/ccr.component';
import { PreAlertNewComponent } from './pre-alert-manifest/pre-alert-new/pre-alert-new.component';
import { BondedManifestNewComponent } from './bonded-manifest/bonded-manifest-new/bonded-manifest-new.component';
import { CcrNewComponent } from './ccr/ccr-new/ccr-new.component';
import { ConsoleNewComponent } from './console/console-new/console-new.component';
import { ConsoleEditComponent } from './console/console-edit/console-edit.component';
import { CcrEditComponent } from './ccr/ccr-edit/ccr-edit.component';
import { PreAlertUpdateComponent } from './pre-alert-manifest/pre-alert-update/pre-alert-update.component';
import { ConsoleLocationComponent } from './console/console-location/console-location.component';
import { CostingSheetComponent } from './costing-sheet/costing-sheet.component';
import { CostingSheetNewComponent } from './costing-sheet/costing-sheet-new/costing-sheet-new.component';
import { DduInvoiceComponent } from './ddu-invoice/ddu-invoice.component';

const routes: Routes = [
  { path: 'preAlertManifest', component: PreAlertManifestComponent, data: { title: 'Mid-Mile', module: 'Pre-Alert Manifest' } },
  { path: 'preAlertManifest-new/:code', component: PreAlertNewComponent, data: { title: 'Mid-Mile', module: 'Pre-Alert Manifest New' } },
  { path: 'preAlertManifest-update/:code', component: PreAlertUpdateComponent, data: { title: 'Mid-Mile', module: 'Pre-Alert Manifest Update' } },



  { path: 'console', component: ConsoleComponent, data: { title: 'Mid-Mile', module: 'Console' } },
  { path: 'console-new/:code', component: ConsoleNewComponent, data: { title: 'Mid-Mile', module: 'Console' } },
  { path: 'console-edit/:code', component: ConsoleEditComponent, data: { title: 'Mid-Mile', module: 'Console' } },

  { path: 'bondedManifest', component: BondedManifestComponent, data: { title: 'Mid-Mile', module: 'Bonded Manifest' } },
  { path: 'bondedManifest-new/:code', component: BondedManifestNewComponent, data: { title: 'Mid-Mile', module: 'Bonded Manifest New' } },

  { path: 'ccr', component: CcrComponent, data: { title: 'Mid-Mile', module: 'CCR' } },
  { path: 'ccr-new/:code', component: CcrNewComponent, data: { title: 'Mid-Mile', module: 'CCR New' } },
  { path: 'ccr-edit/:code', component: CcrEditComponent, data: { title: 'Mid-Mile', module: 'CCR New' } },

  { path: 'consoleLocation/:code', component: ConsoleLocationComponent, data: { title: 'Mid-Mile', module: 'Console Location' } },

  { path: 'costingSheet', component: CostingSheetComponent, data: { title: 'Mid-Mile', module: 'Expense' } },
  { path: 'costingSheet-new/:code', component: CostingSheetNewComponent, data: { title: 'Mid-Mile', module: 'Expense' } },
  
  { path: 'dduInvoice', component: DduInvoiceComponent, data: { title: 'Mid-Mile', module: 'DDU Invoice' } },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AirportRoutingModule { }
