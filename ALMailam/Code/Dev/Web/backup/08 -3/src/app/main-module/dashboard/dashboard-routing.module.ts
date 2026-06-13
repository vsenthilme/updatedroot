import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { LandingPageComponent } from "./landing-page/landing-page.component";

const routes: Routes = [{
  path: '',
  component: DashboardComponent,
  pathMatch: 'full'

},


// { path: 'bin-full',component: BinFullComponent},



{path: 'landingPage', component: LandingPageComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
