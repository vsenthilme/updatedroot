import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { HighchartsChartModule } from 'highcharts-angular';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { PickProductivityComponent } from './pick-productivity/pick-productivity.component';

@NgModule({
  declarations: [
    DashboardComponent,
    LandingPageComponent,
    PickProductivityComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    DashboardRoutingModule,
    HighchartsChartModule,
  ],
  exports: []
})
export class DashboardModule { }
