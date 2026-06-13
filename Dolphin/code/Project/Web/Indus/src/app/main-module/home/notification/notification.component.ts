import { Component, Input, OnInit } from '@angular/core';
import { NotificationService } from './notification.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {
  @Input() data: any;
  constructor(private notification: NotificationService, private cs: CommonService, private auth: AuthService) { } 

  ngOnInit(): void {
    //this.getMessage()

  }

  messaageList:any;
  count:any;
  getMessage() {
    this.notification.find({userId: [this.auth.userID]}).subscribe(res => { //clientUserId: [this.auth.userID] 
      if (res) {
        this.messaageList = res;
        this.count = res.length;
      }
    }, error => {
      this.cs.commonerrorNew(error);
    })
  }

  update(line){
    line.tab = false;
    this.notification.update([line]).subscribe(res => { //clientUserId: [this.auth.userID] 
     this.getMessage();
    }, error => {
      this.cs.commonerrorNew(error);
    })
  }
}
