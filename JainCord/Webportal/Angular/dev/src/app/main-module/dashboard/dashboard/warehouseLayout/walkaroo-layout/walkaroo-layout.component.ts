import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { WalkarooLayoutService } from '../walkaroo-layout.service';
import { MatAccordion } from '@angular/material/expansion';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { NotificationService } from 'src/app/main-module/home/notification/notification.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { initializeApp } from 'firebase/app';
import { getMessaging, onMessage } from 'firebase/messaging';
import { environment } from 'src/environments/environment';
//import { WebSocketAPIService } from 'src/app/WebSocketAPIService';

@Component({
  selector: 'app-walkaroo-layout',
  templateUrl: './walkaroo-layout.component.html',
  styleUrls: ['./walkaroo-layout.component.scss']
})
export class WalkarooLayoutComponent implements OnInit {

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
  warehouseList: any[] = [];

  constructor(private notification: NotificationService, private auth: AuthService,
    private spin: NgxSpinnerService, private cs: CommonService, private service: WalkarooLayoutService, private cdr: ChangeDetectorRef,
    // private webSocketAPIService: WebSocketAPIService, 
    private toastr: ToastrService, private inventory: ReportsService
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

    this.warehouseList = [
      { value: "Chennai", label: 'Chennai' },
    ]
  }

  notificationList1 = [
    { title: 'Inbound', storageBin: 'A-01-01', message: 'Has been created', createdOn: '30-09-2024', newCreated: true },
    { title: 'Inbound', storageBin: 'A-01-03', message: 'Has been created', createdOn: '30-09-2024', newCreated: true },
    { title: 'Inbound', storageBin: 'C-05-01', message: 'Has been created', createdOn: '30-09-2024', newCreated: false },
    { title: 'Inbound', storageBin: 'D-04-01', message: 'Has been created', createdOn: '30-09-2024', newCreated: false },
    { title: 'Inbound', storageBin: 'E-03-01', message: 'Has been created', createdOn: '30-09-2024', newCreated: false },
  ]

  notificationList: any[] = [];
  TransactionList: any[] = [];
  inventoryList: any[] = [];
  binList: any[] = [];


  subscription: Subscription
  ngOnInit(): void {
    this.getAllBin();
    this.listen();
    this.getNotificationCountFromWebsocket();
    // this.subscription = this.webSocketAPIService.currentMessage.subscribe((message: any) => {
    //   if (message != "") {
    //     this.getNotificationCountFromWebsocket();
    //     this.getAllBin();
    //   }
    // });

  }

  listen() {
    const firebaseConfig = {
      apiKey: "AIzaSyCrdO9_MMIvKVaIHT4E1yoUGP3U7xH0FUE",
      authDomain: "almailem-29a5e.firebaseapp.com",
      projectId: "almailem-29a5e",
      storageBucket: "almailem-29a5e.appspot.com",
      messagingSenderId: "930706163709",
      appId: "1:930706163709:web:7d399a955c6d1716d93b94",
      measurementId: "G-29P8C25FRN",
      vapidKey: 'BLy2-JA9Xo99YgTCTRz6T9sM-Wb7PZs0TH29pk91F1DIKyezeorGOsAMopBs5FZYEdJPQydPlsTAEGwb0QDPdrk'
    };

    // Initialize Firebase
    const app = initializeApp(environment.firebase);
    const messaging = getMessaging(app);

    console.log(messaging);

    onMessage(messaging, (payload: any) => {
      console.log('Message received. ', payload);

      // Define a title and options for the notification
      const notificationTitle = payload.notification.title || 'Default Title';
      const notificationOptions = {
        body: payload.notification.body || 'Default body text',
        // You can add more options here, like icon, etc.
      };

      // Display the notification
      new Notification(notificationTitle, notificationOptions);

      this.getNotificationCountFromWebsocket();
      this.getAllBin();
    });
  }


  filterBins: any = [];
  zoneChange(event: any) {
    this.binList.forEach(x => {
      if (x.storageBin.startsWith(event.value)) {
        x.statusDescription = 'Filter';
        this.getClass(x.storageBin)
      } else {
        x.statusDescription = x.quantity > 0 ? 'Occupied' : 'Empty';
      }
    })
  }

  getClass(storageBin: string) {
    let fileterdata = this.notificationList.find((x: any) => x.storageBin == storageBin && x.newCreated);
    if (fileterdata && fileterdata.title == 'Inbound Create') {
      return 'blinkGreen font15  bi bi-caret-right-fill';
    } else if (fileterdata && fileterdata.title == 'Outbound Create') {
      return 'blinkRed font15  bi bi-caret-left-fill';
    } else if (fileterdata && fileterdata.title == ' Make & Change') {
      return 'blinkBlue  font15  bi bi-arrow-left-right';
    }
    let bin = this.binList.find(b => b.storageBin === storageBin);
    if (bin) {
      if (bin.statusDescription === 'Filter') {
        return 'bg-success';
      }
      else if (bin.statusDescription === 'Occupied') {
        return 'blue';
      } else if (bin.statusDescription === 'Empty') {
        return 'emptyBin';
      }
    }
    return 'emptyBin';
  }

  getNotificationCountFromWebsocket() {
    this.spin.show();
    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.languageId = this.auth.languageId;
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;
    this.notification.find({}).subscribe(res => {
      if (res) {
        this.notificationList = res;
        this.spin.hide();
      }
    }, error => {
      this.spin.hide();
      this.cs.commonerrorNew(error);
    });
  }

  selectedStorageBin: any;
  getStorageDetails(binNumber: any) {
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.storageBin = [binNumber];

    this.spin.show();
    this.inventory.findInventory2(obj).subscribe(res => {
      if (res) {
        this.inventoryList = [];
        this.selectedStorageBin = null;
        this.selectedStorageBin = binNumber;
        this.inventoryList = res;
        this.updateNewCreatedOn();
        this.spin.hide();
      }
    }, error => {
      this.spin.hide();
      this.cs.commonerrorNew(error);
    });

    // this.spin.show();
    // this.service.find(obj).subscribe(res => {
    //   if (res) {
    //     this.TransactionList = [];
    //     this.TransactionList = res;
    //     this.spin.hide();
    //   }
    // }, error => {
    //   this.spin.hide();
    //   this.cs.commonerrorNew(error);
    // });
  }

  getAllBin() {
    let obj: any = {};
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

  updateNewCreatedOn() {
    this.service.updateNewCreate().subscribe(res => {
      this.getAllBin();
      this.getNotificationCountFromWebsocket();
    }, error => {
      this.spin.hide();
      this.cs.commonerrorNew(error);
    });
  }

  ngOnDestroy() {
    this.updateNewCreatedOn();
  }
}
