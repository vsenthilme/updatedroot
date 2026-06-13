import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ThreePlRoutingModule } from './three-pl-routing.module';
import { AngularMultiSelectModule } from 'angular2-multiselect-dropdown';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { ProformaComponent } from './proforma/proforma.component';
import { CommonFieldModule } from 'src/app/common-field/common-field.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ProformaNewComponent } from './proforma/proforma-new/proforma-new.component';
import { InvoiceComponent } from './invoice/invoice.component';
import { InvoiceNewComponent } from './invoice/invoice-new/invoice-new.component';
import { ThreeplTransferComponent } from './threepl-transfer/threepl-transfer.component';
import { InvoiceTabComponent } from './invoice-tab/invoice-tab.component';
import { ProformaPopupComponent } from './proforma/proforma-popup/proforma-popup.component';
import { InvoicePopupComponent } from './invoice/invoice-popup/invoice-popup.component';


@NgModule({
  declarations: [
    ProformaComponent,
    ProformaNewComponent,
    InvoiceComponent,
    InvoiceNewComponent,
    ThreeplTransferComponent,
    InvoiceTabComponent,
    ProformaPopupComponent,
    InvoicePopupComponent
  ],
  imports: [
    CommonModule,
    ThreePlRoutingModule,
    NgMultiSelectDropDownModule.forRoot(),
    AngularMultiSelectModule,
    SharedModule,
    CommonFieldModule,
  ],
  exports: [
    ProformaComponent
  ]
})
export class ThreePlModule { }
