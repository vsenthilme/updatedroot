

import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';// import highcharts3d from "highcharts/highcharts-3d";
import cylinder from "highcharts/modules/cylinder";
import highcharts3d from "highcharts/highcharts-3d";
highcharts3d(Highcharts);
cylinder(Highcharts);
import lollipop from 'highcharts/modules/lollipop.js';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { HhtuserService } from 'src/app/main-module/userman/hhtuser/hhtuser.service';
import { UserprofileService } from 'src/app/main-module/userman/userprofile/userprofile.service';
//lollipop(Highcharts);


//dumbbell(Highcharts)

//let indicators = require('highcharts/modules/lollipop');

// import item from 'highcharts/modules/item-series.js';
// item(Highcharts);


// import highcharts3d from "highcharts/highcharts-3d";
// import cylinder from "highcharts/modules/cylinder";

// highcharts3d(Highcharts);
// cylinder(Highcharts);


@Component({
  selector: 'app-putaway-productivity',
  templateUrl: './putaway-productivity.component.html',
  styleUrls: ['./putaway-productivity.component.scss']
})
export class PutawayProductivityComponent implements OnInit {

  Highcharts = Highcharts;
  chartOptions = {};

  chartData = {
    series: [
      {
        name: 'Afsal',
        dashStyle: 'ShortDot',
        data: [6],
      }
    ],
    categories: [
      '06-10-2024'
    ],
  };



  constructor(
    private router: Router,
    private hhtUser: HhtuserService,
    private user: UserprofileService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public cs: CommonService,
    private auth: AuthService,
    private report: ReportsService,
  ) { }


  typeOfFilter: any[] = [
    { value: "Lead Time", label: 'Lead Time (Minutes)' },
    { value: "Quantity", label: 'Quantity' },
    { value: "Avg Lead Time", label: 'Avg Lead Time (Minutes)' },
    { value: "Productivity", label: 'Productivity (Qty/hr)' },
  ];

  ngOnInit() {

    const today = new Date();
    this.toCreatedOnFE = today; // Today’s date
    this.fromCreatedOnFE = new Date(today);
    this.fromCreatedOnFE.setDate(today.getDate() - 30);

    this.dateChoosed();
    this.bindChart(this.chartData);

  }


  fromCreatedOnFE: any;
  toCreatedOnFE: any;
  filter = 'Lead Time';

  dateChoosed(event: any = null) {
    if (this.toCreatedOnFE == null) {
      return
    }
    let obj: any = {};
    obj.startDate = this.cs.dateNewFormat1(this.fromCreatedOnFE);
    obj.endDate = this.cs.dateNewFormat1(this.toCreatedOnFE);
    obj.leadTime = this.filter == 'Lead Time' ? 1 : 0;
    obj.quantity = this.filter == 'Quantity' ? 1 : 0;
    obj.averageLeadTime = this.filter == 'Avg Lead Time' ? 1 : 0;
    obj.productivity = this.filter == 'Productivity' ? 1 : 0;

    this.spin.show();
    this.report.binningReportDashboard(obj).subscribe((res: any[]) => {
      if (res) {
        this.bindChart(res[0]);
      } this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    });
  }

  bindChart(chartData) {
    this.chartOptions = {
      credits: false,
      chart: {
       type: 'column'
      },
      title: {
        text: 'Binning Productivity'
      },
      xAxis: {
        categories: chartData.categories
      },
      yAxis: {
        title: {
          text: this.filter
        }
      },    
      plotOptions: {
        column: {
            pointWidth: 30,
            dataLabels: {
                enabled: true,
                format: '{point.y}',
                style: {
                    fontWeight: 'bold',
                    color: 'black'
                }
            }
        }
    },
      series: chartData.series     
    
    };
    Highcharts.chart('container', this.chartOptions); 
  }
}
