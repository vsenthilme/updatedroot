import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BehaviorSubject, Subscribable, Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/Auth/auth.service';
import { WebSocketAPIService } from 'src/app/WebSocketAPIService';
import { PrebillService } from '../../accounts/prebill/prebill.service';
import { HeaderComponent } from '../header.component';
import { MatDialog } from '@angular/material/dialog';
import { InquiryUpdate3Component } from '../../crm/inquiries/inquiry-update3/inquiry-update3.component';
import { InquiresService } from '../../crm/inquiries/inquires.service';
import { IntakePopupComponent } from '../../crm/inquiries/intake-popup/intake-popup.component';
import { IntakeService } from '../../crm/intake-snap-main/intake.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TaskMatterService } from '../../matters/case-management/task/task-matter.service';
import { TaskNewComponent } from '../../matters/case-management/task/task-new/task-new.component';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  notificationData: any[] = [];
  subscription: Subscription;
  username = this.auth.firstName; 
  @Output() notificationRead = new EventEmitter<boolean>();
  
  constructor(private headerComponent: HeaderComponent, private auth: AuthService, private service: PrebillService,
    public dialog: MatDialog, private inquiryService: InquiresService, private intakeService: IntakeService, private cs: CommonService,
    private router: Router, public toastr: ToastrService, private taskService: TaskMatterService, private prebillService: PrebillService
  ) { }

  ngOnInit(): void {
    this.subscription = this.headerComponent.notificationArray.subscribe((message: any[])  => {
    (message.sort((a, b) => (a.createdOn > b.createdOn) ? -1 : 1))
      if (message != null && message.length != 0) {
        this.notificationData = message;
        console.log(this.notificationData);
      } else {
        this.notificationData = [];
      }
    });
  }

  readAllNotification(){
    this.notificationRead.emit(true);
  }

  

  delete(e){
    this.service.readOnenotification(e.id).subscribe(res=>{
      this.headerComponent.getNotificationCount();
    });
  }

  view(element) {
    if (element.topic == 'Inquiry Assign' || element.topic == 'Inquiry') {
      this.inquiryService.Get(element.inquiryNo).subscribe(res => {
        const dialogRef = this.dialog.open(InquiryUpdate3Component, {
          disableClose: true,
          width: '50%',
          maxWidth: '80%',
          // position: { top: '6.5%' },
          data: {code: res.inquiryNumber, pageflow: 'Inquiry Validation'}
        });

        dialogRef.afterClosed().subscribe(result => {
          this.delete(element);

        });
      })
    }

    if (element.topic == 'Intake') {
      this.intakeService.GetIntake(element.documentNumber).subscribe(res => {
        let formname = this.cs.customerformname(element.itFormID);
        if (formname == '') {
          this.toastr.error(
            "Select from is invalid.",
            ""
          );
          this.cs.notifyOther(true);
          return;
        }
        this.cs.notifyOther(false);
        res.pageflow = 'validate';
        this.router.navigate(['/main/crm/' + formname + '/' + this.cs.encrypt(res)]);
        this.delete(element);
      })
    }


    if(element.topic == 'Matter Task'){
      this.taskService.Get(element.documentNumber).subscribe(res => {
        if (res.statusId == 32) {
          this.toastr.error("Task is already completed.", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });
          return;
        }

        let statusList = [30, 36];
        if (statusList.includes(res.statusId)) {
          this.toastr.error("Task can't be edited.", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });
          return;
        }

        const dialogRef = this.dialog.open(TaskNewComponent, {
          disableClose: true,
          width: '70%',
          maxWidth: '80%',
          position: { top: '6.5%' },
          data: { pageflow: 'Edit', matter: element.matterNo, matterdesc: element.matterDesc, code: element.documentNumber }
        });
        this.delete(element);
      })
    }

    if (element.topic == 'Prebill Create') {
      this.prebillService.GetPrebill(element.documentNumber).subscribe(res => {
        let paramdata = this.cs.encrypt({ code: res, pageflow: res });
        this.router.navigate(['/main/accounts/prebill-approve/' + paramdata]);
        this.delete(element);
      })
    }

    if (element.topic == 'Prebill Approve') {
      // this.prebillService.GetPrebill(element.documentNumber).subscribe(res => {
      //   let paramdata = "";      
      //   paramdata = this.cs.encrypt({ code: res, pageflow: 'Display' });
      //   this.router.navigate(['/main/accounts/prebill-display/' + paramdata]);       
      //  this.delete(element);
      // })
      this.router.navigate(['/main/accounts/prebilllist']);
      this.delete(element);
    }
  }
}
