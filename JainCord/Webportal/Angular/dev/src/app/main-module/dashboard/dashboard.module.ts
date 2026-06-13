import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { HighchartsChartModule } from 'highcharts-angular';
import { PiechartComponent } from './dashboard/operation1/piechart/piechart.component';
import { StackChartComponent } from './dashboard/operation1/stack-chart/stack-chart.component';
import { StackBarComponent } from './dashboard/operation1/stack-bar/stack-bar.component';
import { BarChartComponent } from './dashboard/operation1/bar-chart/bar-chart.component';
import { SemiPieComponent } from './dashboard/operation1/semi-pie/semi-pie.component';
import { SkuchartComponent } from './dashboard/operation2/skuchart/skuchart.component';
import { ReceiptBarComponent } from './dashboard/operation2/receipt-bar/receipt-bar.component';
import { BinChartComponent } from './dashboard/operation2/bin-chart/bin-chart.component';
import { FloorChartComponent } from './dashboard/floor chart/floor-chart/floor-chart.component';
import { BoxdetailsComponent } from './dashboard/floor chart/floor-chart/boxdetails/boxdetails.component';
import { BoxDetails2Component } from './dashboard/floor chart/floor-chart/box-details2/box-details2.component';
import { TemperatureDetailsComponent } from './dashboard/floor chart/floor-chart/temperature-details/temperature-details.component';
import { Storage2Component } from './dashboard/coldStorage/storage2/storage2.component';
import { FloorChart1Component } from './dashboard/floor chart/floor-chart/floor-chart1/floor-chart1.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { D3CollapsibleComponent } from './dashboard/d3-collapsible/d3-collapsible.component';
import { PickProductivityComponent } from './pick-productivity/pick-productivity.component';
import { FastMovingPanelComponent } from './dashboard/floor chart/floorChartpopup/fast-moving-panel/fast-moving-panel.component';
import { WalkarooLayoutComponent } from './dashboard/warehouseLayout/walkaroo-layout/walkaroo-layout.component';
import { PutawayProductivityComponent } from './dashboard/productivity/putaway-productivity/putaway-productivity.component';
import { PickingProductivityComponent } from './dashboard/productivity/picking-productivity/picking-productivity.component';
import { ProductivityDashboardComponent } from './dashboard/productivity/productivity-dashboard/productivity-dashboard.component';
import { ColdChainLayoutComponent } from './dashboard/coldStorage/cold-chain-layout/cold-chain-layout.component';
import { Zone1Component } from './dashboard/temperature/zone1/zone1.component';
import { Zone2Component } from './dashboard/temperature/zone2/zone2.component';


@NgModule({
  declarations: [
    DashboardComponent,
    PiechartComponent,
    StackChartComponent,
    StackBarComponent,
    BarChartComponent,
    SemiPieComponent,
    SkuchartComponent,
    ReceiptBarComponent,
    BinChartComponent,
    FloorChartComponent,
    BoxdetailsComponent,
    BoxDetails2Component,
    TemperatureDetailsComponent,
    Storage2Component,
    FloorChart1Component,
    LandingPageComponent,
    D3CollapsibleComponent,
    PickProductivityComponent,
    FastMovingPanelComponent,
    WalkarooLayoutComponent,
    ColdChainLayoutComponent,
    PutawayProductivityComponent,
    PickingProductivityComponent,
    ProductivityDashboardComponent,
    Zone2Component,
    Zone1Component,
    
  
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
