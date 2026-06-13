import { Component, Input, OnInit } from '@angular/core';
import { NotificationService } from './notification.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { Subscription } from 'rxjs';
import { HomeComponent } from '../home.component';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {
  @Input() data: any;
  constructor(private notification: NotificationService, private cs: CommonService, private auth: AuthService,
    private home: HomeComponent,
  ) { }

  subscription: Subscription;
  ngOnInit(): void {
    this.subscription = this.home.notificationArray.subscribe((message: any[]) => {
      (message.sort((a, b) => (a.createdOn > b.createdOn) ? -1 : 1))
      if (message != null && message.length != 0) {
        this.messaageList = message;
        this.count = message.length;
      } else {
        this.messaageList = [];
      }
    })
  }

  messaageList: any;
  count= 0;
  getMessage() {

    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.languageId = this.auth.languageId;
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;
    this.notification.find({}).subscribe(res => {
      if (res) {
        this.messaageList = res;
        this.count = res.length;
      }
    }, error => {
      this.cs.commonerrorNew(error);
    })
  }


  update(line) {
    line.tab = false;
    this.notification.update([line]).subscribe(res => { //clientUserId: [this.auth.userID] 
      this.home.getMessage();
    }, error => {
      this.cs.commonerrorNew(error);
    })
  }

  delete(list){
    this.notification.delete(list).subscribe(res => { //clientUserId: [this.auth.userID] 
      this.home.getMessage();
    }, error => {
      this.cs.commonerrorNew(error);
    })
  }

  deleteall(list){
    this.notification.deleteall(list).subscribe(res => { //clientUserId: [this.auth.userID] 
      this.home.getMessage();
    }, error => {
      this.cs.commonerrorNew(error);
    })
  }


  readAllNotifications(){
    this.notification.markNotificationsAsRead().subscribe(res=>{
      this.count = 0;
      this.home.getMessage(); 
    });
}

deleteRecord(e){
this.notification.readOnenotification(e.id).subscribe(res=>{
  this.home.getNotificationCountFromWebsocket();
});
}
}
