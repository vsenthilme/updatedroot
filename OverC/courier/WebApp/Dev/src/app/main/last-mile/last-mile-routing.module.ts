import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PickupNewComponent } from './pickup/pickup-new/pickup-new.component';
import { PickupComponent } from './pickup/pickup.component';
import { DeliveryComponent } from './delivery/delivery.component';
import { DeliveryNewComponent } from './delivery/delivery-new/delivery-new.component';

const routes: Routes = [
  { path: 'pickup', component: PickupComponent, data: { title: 'LastMile', module: 'Pickup' } },
  { path: 'pickup-new/:code', component: PickupNewComponent, data: { title: 'LastMile', module: 'Pickup - Add New' } },
  { path: 'delivery', component: DeliveryComponent, data: { title: 'LastMile', module: 'Delivery' } },
  { path: 'delivery-new/:code', component: DeliveryNewComponent, data: { title: 'LastMile', module: 'Delivery - Add New' } },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LastMileRoutingModule { }
