import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { BehaviorSubject, Subscription, timer } from 'rxjs';
import { map, share, take } from 'rxjs/operators';
import { MenuService } from 'src/app/common-service/menu.service';
import { AuthService } from 'src/app/core/core';
import { environment } from 'src/environments/environment';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { MatDialog } from '@angular/material/dialog';
import { BarcodePrintComponent } from './barcode-print/barcode-print.component';
import { PreoutboundService } from '../Outbound/preoutbound/preoutbound.service';
import { UpdateInventoryComponent } from './update-inventory/update-inventory.component';
import { InhouseTransferService } from '../make&change/inhouse-transfer/inhouse-transfer.service';
import { UpdateOutboundLineComponent } from './update-outbound-line/update-outbound-line.component';
import { CronJob } from 'cron';
import { ShipmentSummaryComponent } from '../reports/shipment-summary/shipment-summary.component';
import { MenuNewService } from 'src/app/common-service/menu-new.service';
import { MenuNew1Service } from 'src/app/common-service/menu-new1.service';
import { HttpClient } from '@angular/common/http';
import { LoginUsersComponent } from './login-users/login-users.component';
import { CreateInventoryComponent } from './create-inventory/create-inventory.component';
import { CommonService } from 'src/app/common-service/common-service.service';

import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { NotificationService } from './notification/notification.service';
import { error } from 'console';
import { WebSocketAPIService } from 'src/app/WebSocketAPIService';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  animations: [
    // Each unique animation requires its own trigger. The first argument of the trigger function is the name
    trigger('rotatedState', [
      state('default', style({ transform: 'rotate(0)' })),
      state('rotated', style({ transform: 'rotate(-180deg)' })),
      transition('rotated => default', animate('400ms ease-out')),
      transition('default => rotated', animate('400ms ease-in'))
    ])
  ]
})
export class HomeComponent implements OnInit {


  public icon = 'menu';
  showFloatingButtons: any;
  toggle = true;
  state: string = 'default';
  toggleFloat() {
    this.toggle = !this.toggle;
    this.state = (this.state === 'default' ? 'rotated' : 'default');
    if (this.icon === 'menu') {
      this.icon = 'arrow_back_ios_new';
    } else {
      this.icon = 'menu'
    }
  }


  message: any = null;

  username = this.auth.userID;
  usertype = this.auth.userTypeId;
  currentdate = new Date();
  // default list of  menu items
  menulist: any;

  sub = new Subscription();
  cronJob: CronJob;
  constructor(private router: Router, public dialog: MatDialog, private ms: MenuService, public auth: AuthService, private service: PreoutboundService,
    private uploadService: ShipmentSummaryComponent, private menuNew: MenuNew1Service, private http: HttpClient, private cs: CommonService,
    private notification: NotificationService,   
    private webSocketAPIService: WebSocketAPIService,    
    public toastr: ToastrService,) {
    this.currentEnv = environment.name;

    //     var cron = require('cron');
    // var cronJob = cron.job("35 15 * * *", 
    // this.idmastser.Getall().subscribe(res => {
    //   console.log('Job Completed')
    // })
    // this.cronJob = new CronJob('*/5 * * * *', async () => {
    //   try {
    //     await this.bar();
    //   } catch (e) {
    //     console.error(e);
    //   }
    // });
    // if (!this.cronJob.running) {
    //   this.cronJob.start();
    // }
    this.webSocketAPIService._connect();
  }
  // async bar(): Promise<void> {
  //   console.log('job completed')
  //   this.uploadService.scheduleSearch()
  // }
  showFiller = false;

  currentEnv: string;
  prod: boolean;
  navItems: any[] = [];
  navItems1: any[] = [];
  subscription: Subscription;
  ngOnInit(): void {
   // this.listen();
    //this.requestPermission();
  //  this.getMessage();
    var username = this.auth.username;
    this.navItems = this.menuNew.getMeuList();

    this.navItems.filter(x => x.id == 8100 ? this.navItems1 = (x.children) : '');
    let menu = JSON.parse("[" + sessionStorage.getItem('menu') + "]");
    this.menulist = this.ms.getMeuList('save').filter(x => menu.includes(x.id));
    this.sub = timer(0, 10000)
      .pipe(
        map(() => new Date()),
        share()
      )
      .subscribe(time => {
        this.currentdate = time;
      });

    if (this.currentEnv == 'prod') {
      this.prod = true;
    } else {
      this.prod = false;
    }


    
    this.getNotificationCountFromWebsocket();
    this.subscription = this.webSocketAPIService.currentMessage.subscribe((message:any) => {
      if (message != "") {
        this.getNotificationCountFromWebsocket();
        this.toastr.success(
          message.text,
          "Notification",{
            timeOut: 5000,
            progressBar: false,
          }
        )
      }
    });
  }

  toasterClickedHandler(){
    this.router.navigate(['/main/dashboard/walkaroo']);
  }

  logout() {
    // this.http.patch<any>(`/wms-idmaster-service/usermanagement/${this.auth.userID}?companyCode=${this.auth.companyId}&languageId=${this.auth.languageId}&plantId=${this.auth.plantId}&warehouseId=${this.auth.warehouseId}&userRoleId=${this.auth.userRoleId}`, { portalLoggedIn: false, companyCode: this.auth.companyId, plantId: this.auth.plantId, languageId: this.auth.languageId, userRoleId: this.auth.userRoleId }).subscribe(userres => {
    //   //this.auth.logout();
    //   this.router.navigate(['']);
    //   sessionStorage.clear();
    // });
    this.router.navigate(["/"]);
    window.sessionStorage.clear();
    sessionStorage.clear();
  }
  createInventory() {
    const dialogRef = this.dialog.open(CreateInventoryComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '6.5%' },
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }
  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    if (!url) {
      let menu =
        JSON.parse("[" + sessionStorage.getItem('menu') + "]");
      url = this.ms.getMeuList('save').filter(x => x.id == Number(id))[0].children?.filter(x => menu.includes(x.id))[0].url;
    }
    this.router.navigate([url]);
  }
  OpenBarcodePrint() {
    const dialogRef = this.dialog.open(BarcodePrintComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '9%', },
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }
  ///side pannel

  @ViewChild('sidenav') sidenav: MatSidenav | undefined;
  isExpanded = false;
  showSubmenu: boolean = false;
  isShowing = false;
  showSubSubMenu: boolean = false;



  updateInventory() {
    const dialogRef = this.dialog.open(UpdateInventoryComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '6.5%' },
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  updateOutbound() {
    const dialogRef = this.dialog.open(UpdateOutboundLineComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '6.5%' },
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  updateLogin() {
    const dialogRef = this.dialog.open(LoginUsersComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '6.5%' },
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }
  failedorder() {
    this.router.navigate(["/main/inbound/failedorder"]);
  }

  validateToggle() {
    if (this.toggle == false) {
      this.toggle = true;
      this.isExpanded = false;
      this.state = (this.state === 'default' ? 'rotated' : 'default');
      if (this.icon === 'menu') {
        this.icon = 'arrow_back_ios_new';
      } else {
        this.icon = 'menu'
      }
    }

  }



  checkModule(id) {
    if (id == 1000) {       //id == 1000 || 
      return false
    }

    let fileterdata = this.auth.MenuData.filter((x: any) => x.moduleId == id && (x.view || x.delete || x.createUpdate));
    if (fileterdata.length > 0) {
      return false;
    }
    return true;
  }

  checkModule1(id) {
    let fileterdata = this.auth.MenuData.filter((x: any) => x.moduleId == id && (x.view || x.delete || x.createUpdate));

    if (fileterdata.length > 0) {
      return false;
    }
    return true;
  }
  routerToDashboard() {
    let paramdata = this.cs.encrypt({ code: 'bin', type: 'type', line: 's' });
    this.router.navigate(['/main/dashboard/warehousemonitor/' + paramdata]);
  }

  requestPermission() {
    const messaging = getMessaging();
    getToken(messaging, { vapidKey: environment.firebase.vapidKey }).then((currentToken) => {
      if (currentToken) {
        console.log("Hurraaa!!! we got the token.....")
        console.log(currentToken);
      } else {
        // Show permission request UI
        console.log('No registration token available. Request permission to generate one.');
        // ...
      }
    }).catch((err) => {
      console.log(err);
      // ...
    });

  }
  countOfNotification = 0;
  listen() {
    const messaging = getMessaging();
    onMessage(messaging, (payload) => {
      console.log('Message received. ', payload.notification);
      this.message = payload.notification;
      setTimeout(() => {
        this.getMessage();
      }, 1000);

    });
  }

  private notificationDataFromDB = new BehaviorSubject([]);
  notificationArray = this.notificationDataFromDB.asObservable();
  getMessage() {
    this.notification.find({ userId: [this.auth.userID] }).subscribe(res => { //clientUserId: [this.auth.userID] 
      if (res) {
        this.countOfNotification = res.length;
        this.notificationDataFromDB.next(res);
      }
    }, error => {
      this.cs.commonerrorNew(error);
    })
  }

  readAllNotifications(event) {
    console.log(event)
    //update notifications as read
    if (event == true) {
      console.log(this.notificationDataFromDB)
      //line.tab = false;
      //  this.notification.update([line]).subscribe(res => { //clientUserId: [this.auth.userID] 
      this.countOfNotification = 0;
      this.notificationDataFromDB.next([]);
      //    }, error => {
      //     this.cs.commonerrorNew(error);
      //   })

    }
  }
  onMenuClosed() {
    this.notificationDataFromDB.value.forEach((x: any) => { x.tab = true });
    this.notification.update(this.notificationDataFromDB.value).subscribe(res => {

    }, error => {
      this.cs.commonerrorNew(error);
    })
  }

  getNotificationCountFromWebsocket(){
    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.languageId = this.auth.languageId;
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;
    this.notification.find({}).subscribe(res => {
      if(res != null && res.length != 0) {
        this.countOfNotification = res.length;
      } else {
        this.countOfNotification = 0;
      }
      this.notificationDataFromDB.next(res);
    },error=>{
      this.countOfNotification = 0;
      this.notificationDataFromDB.next([]);
    });
  }
}
