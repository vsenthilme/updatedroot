import { Component } from '@angular/core';
import { Router, NavigationStart, NavigationEnd, NavigationCancel } from '@angular/router';
import { CronJob } from 'cron';
import { NgxSpinnerService } from 'ngx-spinner';
import { ShipmentDispatchComponent } from './main-module/reports/shipment-dispatch/shipment-dispatch.component';
import { ShipmentSummaryComponent } from './main-module/reports/shipment-summary/shipment-summary.component';
import { ScheduleReportComponent } from './main-module/reports/schedule-report/schedule-report.component';
import { AuthService } from './core/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PrimeNGConfig } from 'primeng/api';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  
  title = 'Classic WMS';
  cronJob: CronJob;
  constructor(
    private router: Router,
    private spinner: NgxSpinnerService,
    private shipmentService: ShipmentSummaryComponent,
    private dispatchService: ShipmentDispatchComponent,
    private schedule: ScheduleReportComponent,
    private auth: AuthService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private http: HttpClient,
    private primengConfig: PrimeNGConfig,
  ) { 
  }
  ngAfterViewInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.spinner.show();
      } else if (
        event instanceof NavigationEnd ||
        event instanceof NavigationCancel
      ) {
        this.spinner.hide();
      }
    }); 

    this.primengConfig.setTranslation({
      accept: 'Accept',
      reject: 'Cancel',
      //translations
  });
  }
  
}
