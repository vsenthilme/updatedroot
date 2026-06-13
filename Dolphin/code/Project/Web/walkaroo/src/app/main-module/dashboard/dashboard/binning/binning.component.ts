import {
  Component,
  OnInit
} from '@angular/core';
import { Router } from '@angular/router';
import * as Highcharts from 'highcharts';
import Drilldown from 'highcharts/modules/drilldown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { HhtuserService } from 'src/app/main-module/userman/hhtuser/hhtuser.service';
import { UserprofileService } from 'src/app/main-module/userman/userprofile/userprofile.service';
Drilldown(Highcharts);

@Component({
  selector: 'app-binning',
  templateUrl: './binning.component.html',
  styleUrls: ['./binning.component.scss']
})
export class BinningComponent implements OnInit {

    constructor(
        private router: Router,
        private hhtUser: HhtuserService,
        private user: UserprofileService,
        public toastr: ToastrService,
        private spin: NgxSpinnerService,
        public cs: CommonService,
        private auth: AuthService,
        private report: ReportsService,
        //  private route: ActivatedRoute
    ) { }

    Highcharts = Highcharts;
    chartOptions = {};

    array: any[] = [
        {
            name: "03-10-2024",
            y: 7,
        },
        {
            name: "02-10-2024 ",
            y: 6,
        },
        {
            name: "01-10-2024",
            y: 5
        },
        {
            name: "30-09-2024",
            y: 5,
        },
        {
            name: "29-09-2024",
            y: 4,
        }
    ]


    outputArray: any[] = [];
    drilldownData:any[] = [];

    transformData(result: any) {
        const dateMap: { [key: string]: { total: number; items: any[] } } = {};
    
        result.forEach(item => {
            // Check if confirmedOn is null or undefined, and skip if so
            if (!item.confirmedOn) return;
    
            const date = new Date(item.confirmedOn).toISOString().split('T')[0]; // YYYY-MM-DD
            const formattedDate = date.split('-').reverse().join('-'); // DD-MM-YYYY
            
            // Initialize if not already present
            if (!dateMap[formattedDate]) {
                dateMap[formattedDate] = { total: 0, items: [] };
            }
    
            // Safely add putawayConfirmedQty, ensure it's a number
            const confirmedQty = Number(item.putawayConfirmedQty) || 0;
            dateMap[formattedDate].total += confirmedQty;
    
            // Store each item's details for drilldown, including ref_doc_no
            dateMap[formattedDate].items.push({
                refDocNumber: item.refDocNumber, // Accessing ref_doc_no correctly
                y: item.leadTime
            });
        });
    
        // Prepare main series and drilldown series
        this.outputArray = Object.keys(dateMap).map(date => ({
            name: date,
            y: dateMap[date].total,
            drilldown: date // Use the date as a unique ID for drilldown
        }));
    
        this.drilldownData = Object.keys(dateMap).map(date => ({
            id: date, // Corresponds to the drilldown ID
            data: dateMap[date].items.map(item => [item.refDocNumber, item.y]) // Convert items to array format for drilldown
        }));
    
        this.bindChart(this.outputArray, this.drilldownData);
        console.log(this.outputArray);
        console.log(this.drilldownData);
    }
    
    ngOnInit() {
        this.getPicker();
        this.bindChart(this.array, this.array);
    }

    userList:any[] = [];

    userSelected:any;
    getPicker(){
        let obj: any = {};
        obj.companyCodeId = [this.auth.companyId];
        obj.plantId = [this.auth.plantId];
        obj.languageId = [this.auth.languageId];
        obj.warehouseId = [this.auth.warehouseId];
        obj.userTypeId = [5];

    
        this.spin.show();
       this.user.search(obj).subscribe((res: any[]) => {
          
          if (res) {
            res.forEach(element => {
                this.userList.push({value: element.userName, label: element.userName});
              });
        this.pickerChoosed(null);
        this.userSelected = this.userList[0].value;
          } this.spin.hide();
        }, err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        });
    }


    pickerChoosed(event:any){
        let obj: any = {};
        obj.companyCodeId = [this.auth.companyId];
        obj.plantId = [this.auth.plantId];
        obj.languageId = [this.auth.languageId];
        obj.warehouseId = [this.auth.warehouseId];
        if(event != null){
            obj.binner = [event.value];
        }else{
            obj.binner = [this.userList[0].value];
        }
    
        this.spin.show();
       this.report.biningProductivity(obj).subscribe((res: any[]) => {
          if (res) {
           this.transformData(res);
          } this.spin.hide();
        }, err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        });
    }

    bindChart(array: any, drilldown:any) {
        this.chartOptions = {
            credits: false,
            chart: {
                type: 'column'
            },
            title: {
                text: 'Binning Productivity'
            },
            accessibility: {
                announceNewData: {
                    enabled: true
                }
            },
            xAxis: {
                type: 'category',
                title: {
                    text: 'Current Week'
                }
            },
            yAxis: {
                title: {
                    text: 'Average Lead Time'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.1f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
            },

            series: [
                {
                    name: "Average Lead Time",
                    colorByPoint: true,
                    data: array,
                    // point: {
                    //     events: {
                    //       click: (event) => { // Arrow function
                    //         this.router.navigate(['/main/dashboard/walkaroo']);
                    //       },
                    //     },
                    //   },
                }
            ],
            drilldown: {
                breadcrumbs: {
                    position: {
                        align: 'right'
                    }
                },
                series: drilldown
            }
        };
    }

}











// transformData(result: any) {
//     const dateMap: { [key: string]: number } = {};

//     result.forEach(item => {
//         const date = new Date(item.confirmedOn).toISOString().split('T')[0]; // YYYY-MM-DD
//         const formattedDate = date.split('-').reverse().join('-'); // DD-MM-YYYY
        
//         // Initialize if not already present
//         if (!dateMap[formattedDate]) {
//             dateMap[formattedDate] = 0; 
//         }

//         // Safely add confirmedQty, ensure it's a number
//         const confirmedQty = Number(item.putawayConfirmedQty) || 0;
//         dateMap[formattedDate] += confirmedQty;
//     });

//     this.outputArray = Object.keys(dateMap).map(date => ({
//         name: date,
//         y: dateMap[date]
//     }));
//     this.bindChart(this.outputArray);
//     console.log(this.outputArray);
// }



//to get the average


// transformData(result: any) {
//     const dateMap: { [key: string]: { total: number; count: number } } = {};

//     result.forEach(item => {
//         const date = new Date(item.confirmedOn).toISOString().split('T')[0]; // YYYY-MM-DD
//         const formattedDate = date.split('-').reverse().join('-'); // DD-MM-YYYY
        
//         // Initialize if not already present
//         if (!dateMap[formattedDate]) {
//             dateMap[formattedDate] = { total: 0, count: 0 };
//         }

//         // Safely add confirmedQty, ensure it's a number
//         const confirmedQty = Number(item.confirmedQty) || 0;
//         dateMap[formattedDate].total += confirmedQty;
//         dateMap[formattedDate].count += 1; // Increment count
//     });

//     // Calculate average for each date
//     this.outputArray = Object.keys(dateMap).map(date => ({
//         name: date,
//         y: dateMap[date].total / dateMap[date].count // Calculate average
//     }));

//     this.bindChart(this.outputArray);
//     console.log(this.outputArray);
// }
