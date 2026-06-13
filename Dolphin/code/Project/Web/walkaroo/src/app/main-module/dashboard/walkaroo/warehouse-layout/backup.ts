import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatAccordion } from '@angular/material/expansion';
import { NgxSpinnerService } from 'ngx-spinner';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { NotificationService } from 'src/app/main-module/home/notification/notification.service';
import { WalkarooService } from './walkaroo.service';

@Component({
  selector: 'app-warehouse-layout',
  templateUrl: './warehouse-layout.component.html',
  styleUrls: ['./warehouse-layout.component.scss']
})
export class WarehouseLayoutComponent implements OnInit {
  step = 0;
  @ViewChild(MatAccordion) accordion: MatAccordion;
  setStep(index: number) {
    this.step = index;
  }
  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  zoneList: any[] = [];
  constructor(private notification: NotificationService, private auth: AuthService, 
    private spin: NgxSpinnerService, private cs: CommonService, private service: WalkarooService, private cdr: ChangeDetectorRef
  ) {
    this.zoneList = [
      { value: "H", label: 'H - Single' },
      { value: "A", label: 'A - Boys' },
      { value: "D", label: 'D - Girls' },
      { value: "G", label: 'G - Other Brands' },
      { value: "E", label: 'E - Male Kids' },
      { value: "C", label: 'C - Ladies' },
      { value: "F", label: 'F - Fast Moving' },
      { value: "G", label: 'G - Gents' },
    ];
  }
  // binList = [
  //   { "storageBin": 'A-01-01', "stockCount": 7, "status": "Available", "statusId": 10 },
  //   { "storageBin": 'A-01-02', "stockCount": 22, "status": "Filled", "statusId": 12 },
  //   { "storageBin": 'A-01-03', "stockCount": 15, "status": "Filled", "statusId": 12 },
  //   { "storageBin": 'A-01-04', "stockCount": 30, "status": "Available", "statusId": 10 },
  //   { "storageBin": 'C-01-01', "stockCount": 19, "status": "Filled", "statusId": 12 },
  //   { "storageBin": 'C-01-02', "stockCount": 5, "status": "Available", "statusId": 10 },
  //   { "storageBin": 'C-01-03', "stockCount": 28, "status": "Filled", "statusId": 12 },
  //   { "storageBin": 'C-01-04', "stockCount": 11, "status": "Available", "statusId": 10 },
  //   { "storageBin": 'B-01-01', "stockCount": 26, "status": "Filled", "statusId": 12 },
  //   { "storageBin": 'D-01-01', "stockCount": 8, "status": "Filled", "statusId": 12 },
  //   { "storageBin": 'E-01-01', "stockCount": 34, "status": "Filled", "statusId": 12 },
  //   { "storageBin": 'E-02-02', "stockCount": 2, "status": "Available", "statusId": 10 },
  // ]

  notificationList1 = [
    {title: 'Inbound', storageBin: 'A-01-01',  message: 'Has been created', createdOn: '30-09-2024', newCreated: true},
    {title: 'Inbound', storageBin: 'A-01-03',  message: 'Has been created', createdOn: '30-09-2024', newCreated: true},
    {title: 'Inbound', storageBin: 'C-05-01',  message: 'Has been created', createdOn: '30-09-2024', newCreated: false},
    {title: 'Inbound', storageBin: 'D-04-01',  message: 'Has been created', createdOn: '30-09-2024', newCreated: false},
    {title: 'Inbound', storageBin: 'E-03-01',  message: 'Has been created', createdOn: '30-09-2024', newCreated: false},
  ]



  // TransactionList = [
  //   {
  //     type: 'New',
  //     lines: [{
  //       itemCode: '32323',
  //       huNo: 'TDS3241',
  //       date: '30-09-2024',
  //       qty: '23',
  //     },
  //     {
  //       itemCode: '32323',
  //       huNo: 'TDS3242',
  //       date: '30-09-2024',
  //       qty: '21',
  //     }],
  //   },
  //   {
  //     type: 'Existing',
  //     lines: [{
  //       itemCode: '32323',
  //       huNo: 'ADS3231',
  //       date: '24-09-2024',
  //       qty: '33',
  //     },
  //     {
  //       itemCode: '32323',
  //       huNo: 'ADS3232',
  //       date: '24-09-2024',
  //       qty: '20',
  //     },
  //     {
  //       itemCode: '32323',
  //       huNo: 'ADS3233',
  //       date: '24-09-2024',
  //       qty: '33',
  //     },
  //     {
  //       itemCode: '32323',
  //       huNo: 'ADS3234',
  //       date: '24-09-2024',
  //       qty: '20',
  //     }
  //     ],
  //   }
  // ]

  notificationList: any[] = [];
  TransactionList: any[] = [];
  binList: any[] = [];

  ngOnInit(): void {
    this.getAllBin();
  }

  filterBins:any = [];
  zoneChange(event:any){
    let obj:any= {};
    obj.companyCodeId = this.auth.companyId;
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;

    this.spin.show();
    this.service.find(obj).subscribe(res => {
      if (res) {
        if(event.value){
        this.filterBins = [];
        this.filterBins = res;
        }
        this.spin.hide();
      }
    }, error => {
      this.spin.hide();
      this.cs.commonerrorNew(error);
    });
  }

  getClass(storageBin: string){
      let fileterdata1 = this.filterBins.find(bin => bin.storageBin == storageBin && bin.storageBin.startsWith(2));
      if (fileterdata1) {
        return 'emptysssBin';
      }
      
    let fileterdata = this.notificationList.find((x: any) => x.storageBin == storageBin && x.newCreated);
    if (fileterdata) {
      return 'blink';
    }
    let bin = this.binList.find(b => b.storageBin === storageBin);

    if (bin) {
      if (bin.statusDescription === 'Occupied') {
        return 'blue';
      } else if (bin.statusDescription === 'Empty') {
        return 'emptyBin';
      } else if (bin.statusDescription === 'Filter') {
        return 'emptysssBin';
      }
    }
    return 'emptyBin';
  }

  getNotificationCountFromWebsocket() {
    this.spin.show();
    this.notification.find({ userId: [this.auth.userID] }).subscribe(res => {
      if (res) {
        this.notificationList = res;
        this.spin.hide();
      }
    }, error => {
      this.spin.hide();
      this.cs.commonerrorNew(error);
    });
  }

  getStorageDetails(){
    let obj:any= {};
    obj.companyId = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.userId = [this.auth.userID]
    obj.warehouseId = [this.auth.warehouseId];
    this.spin.show();
    this.service.find(obj).subscribe(res => {
      if (res) {
        this.TransactionList = [];
        this.TransactionList = res;
        this.spin.hide();
      }
    }, error => {
      this.spin.hide();
      this.cs.commonerrorNew(error);
    });
  }

  getAllBin(){
    let obj:any= {};
    obj.companyCodeId = this.auth.companyId;
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;
    this.spin.show();
    this.service.find(obj).subscribe(res => {
      if (res) {
        this.binList = [];
        this.binList = res;
        this.spin.hide();
      }
    }, error => {
      this.spin.hide();
      this.cs.commonerrorNew(error);
    });
  }
}
