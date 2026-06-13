import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { LandingPageComponent } from "./landing-page/landing-page.component";
import { WalkarooLayoutComponent } from "./dashboard/warehouseLayout/walkaroo-layout/walkaroo-layout.component";
import { ProductivityDashboardComponent } from "./dashboard/productivity/productivity-dashboard/productivity-dashboard.component";
import { ColdChainLayoutComponent } from "./dashboard/coldStorage/cold-chain-layout/cold-chain-layout.component";

const routes: Routes = [{
  path: '',
  component: DashboardComponent,
  pathMatch: 'full'

},

{ path: 'aerialView',component: WalkarooLayoutComponent},
{ path: 'coldChain',component: ColdChainLayoutComponent},
{ path: 'productivity',component: ProductivityDashboardComponent},


{path: 'warehousemonitor/:code', component: DashboardComponent},


{path: 'landingPage', component: LandingPageComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
