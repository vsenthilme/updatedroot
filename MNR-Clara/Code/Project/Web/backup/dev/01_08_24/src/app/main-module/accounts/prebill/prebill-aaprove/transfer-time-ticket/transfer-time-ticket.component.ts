import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { GeneralMatterService } from 'src/app/main-module/matters/case-management/General/general-matter.service';
import { PrebillService } from '../../prebill.service';

@Component({
  selector: 'app-transfer-time-ticket',
  templateUrl: './transfer-time-ticket.component.html',
  styleUrls: ['./transfer-time-ticket.component.scss']
})
export class TransferTimeTicketComponent implements OnInit {
  sub = new Subscription();
  email = new FormControl('', [Validators.required, Validators.email]);


  submitted = false;

  disabled = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  panelOpenState = false;
  newPage = true;


  constructor(
    public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any, private matterService: GeneralMatterService, private service: PrebillService,
    public toastr: ToastrService,
    public datepipe: DatePipe,
    private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private auth: AuthService, private fb: FormBuilder,
    private cs: CommonService
  ) { }
  matter: any;
  btntext = "Save"
  multiMatterList: any[] = [];

  ngOnInit(): void {
    console.log(this.data)
    this.fromMatter = this.data.lines[0].matterNumber;
    this.matterDropdown();
  }

  matterDropdown(){
    this.matterService.SearchNew({classId: [this.data.lines[0].classId]}).subscribe(res => {
      res.forEach((x: any) => this.multiMatterList.push({ value: x.matterNumber, label: x.matterNumber + "-" + x.matterDescription}))
    })
  }

  fromMatter: any;
  toMatter: any;
  expenseCodelist: any;


  isbtntext = true;


  submit() {
    
    this.submitted = true;

    let obj: any = {};
    obj.fromMatterNumber = this.fromMatter;
    obj.toMatterNumber = this.toMatter;
    obj.preBillNumber = this.data.lines[0].referenceField1;
    obj.timeTickets = this.data.lines;
    this.service.transferMatter(obj).subscribe(res => {
      this.toastr.success("Matter Time Tickers transferred successfully","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      this.dialogRef.close("Transferred");
    })
  };



}
