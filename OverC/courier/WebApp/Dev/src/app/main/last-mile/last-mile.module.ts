import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LastMileRoutingModule } from './last-mile-routing.module';
import { PickupComponent } from './pickup/pickup.component';
import { PickupNewComponent } from './pickup/pickup-new/pickup-new.component';
import { SharedModule } from '../../shared/shared.module';
import { DeliveryComponent } from './delivery/delivery.component';
import { DeliveryNewComponent } from './delivery/delivery-new/delivery-new.component';


@NgModule({
  declarations: [
    PickupComponent,
    PickupNewComponent,
    DeliveryComponent,
    DeliveryNewComponent
  ],
  imports: [
    CommonModule,
    LastMileRoutingModule,
    SharedModule
  ]
})
export class LastMileModule { }
