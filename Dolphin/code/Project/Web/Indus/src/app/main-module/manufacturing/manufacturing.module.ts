import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManufacturingRoutingModule } from './manufacturing-routing.module';
import { ManufacturTabComponent } from './manufactur-tab/manufactur-tab.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { CommonFieldModule } from 'src/app/common-field/common-field.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { BomDialogComponent } from './order-details/bom-dialog/bom-dialog.component';
import { OrderDetailsNewComponent } from './order-details/order-details-new/order-details-new.component';
import { RoutingComponent } from './order-details/routing/routing.component';
import { CuttingMainComponent } from './cutting/cutting-main/cutting-main.component';
import { CuttingNewComponent } from './cutting/cutting-new/cutting-new.component';
import { MoldingMainComponent } from './molding/molding-main/molding-main.component';
import { MoldingNewComponent } from './molding/molding-new/molding-new.component';
import { FabricationMainComponent } from './fabrication/fabrication-main/fabrication-main.component';
import { FabricationNewComponent } from './fabrication/fabrication-new/fabrication-new.component';
import { AssemblyMainComponent } from './assembly/assembly-main/assembly-main.component';
import { AssemblyNewComponent } from './assembly/assembly-new/assembly-new.component';
import { DeliveryMainComponent } from './delivery/delivery-main/delivery-main.component';
import { DeliveryNewComponent } from './delivery/delivery-new/delivery-new.component';
import { ProductionOrderComponent } from './indusFoods/production-order/production-order.component';
import { ProductionOrderNewComponent } from './indusFoods/production-order-new/production-order-new.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { ProductionOrderConfirmComponent } from './indusFoods/production-order-confirm/production-order-confirm.component';
import { OperationFieldsComponent } from './indusFoods/production-order-confirm/operation-fields/operation-fields.component';


@NgModule({
  declarations: [
    ManufacturTabComponent,
    OrderDetailsComponent,
    BomDialogComponent,
    OrderDetailsNewComponent,
    RoutingComponent,
    CuttingMainComponent,
    CuttingNewComponent,
    MoldingMainComponent,
    MoldingNewComponent,
    FabricationMainComponent,
    FabricationNewComponent,
    AssemblyMainComponent,
    AssemblyNewComponent,
    DeliveryMainComponent,
    DeliveryNewComponent,
    ProductionOrderComponent,
    ProductionOrderNewComponent,
    ProductionOrderConfirmComponent,
    OperationFieldsComponent
  ],
  imports: [
    CommonModule,
    ManufacturingRoutingModule,
    SharedModule,
    CommonFieldModule,
    OwlDateTimeModule, 
    OwlNativeDateTimeModule,
  ]
})
export class ManufacturingModule { }
