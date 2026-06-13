import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CustomerComponent } from './customer/customer.component';
import { CustomerTabComponent } from './customer-tab/customer-tab.component';


@NgModule({
  declarations: [
    CustomerComponent,
    CustomerTabComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    SharedModule
    
  ]
})
export class CustomerModule { }
