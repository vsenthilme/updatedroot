import { Component, OnInit, ViewChild } from '@angular/core';
import { CustomerService } from '../customer.service';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { Calendar } from 'primeng/calendar';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit {


  @ViewChild('boutiqaat') boutiqaat: Table | undefined;
  jntOrders: any[] = [];
  selectedjntOrders: any[] = [];
  advanceFilterShow: boolean;

  multicustomer_code: any[];

  constructor(public dialog: MatDialog, private service: CustomerService, private spin: NgxSpinnerService, private cs: CommonService,
    private messageService: MessageService, private fb: FormBuilder) {

    this.multicustomer_code = [
      { value: 'BOQ', label: 'Boutiqaat' },
      { value: 'PDO', label: 'PayDo' },
      { value: 'EPO', label: 'Emirates Post' },
      { value: 'GNM', label: 'Ali Al-Ghanim' },
      { value: 'AJX', label: 'Ajex' },
    ]
  }
  sub = new Subscription();


  searhform = this.fb.group({
    startDate: [],
    endDate: [],
    customerCodeFE: ["BOQ", ],
    fullDate: [],
    customerCode: [["BOQ"],],
  });

  ngOnInit(): void {
    let currentDate = new Date();
    let currentMonthStartDate = new Date();
    currentMonthStartDate.setDate(currentDate.getDate() - 31);

    this.searhform.controls.fullDate.patchValue([currentMonthStartDate, new Date()]);
    this.searhform.controls.endDate.patchValue(new Date());
    this.searhform.controls.startDate.patchValue(currentMonthStartDate); //currentMonthStartDate

    this.sparkCall();
  }

  filterResult: any[] = [];
  multiscanType: any[] = [];
  multireference_number: any[] = [];


  sparkCall() {
    console.log(this.searhform.getRawValue())
    this.spin.show();
    this.jntOrders = [];
    this.service.findConsignment(this.searhform.getRawValue()).subscribe(res => { //this.searhform.getRawValue()
      this.jntOrders = res;
      this.selectedjntOrders = [];
      this.spin.hide();
    }, err => {
      this.spin.hide();
      this.cs.commonerror(err);
    });

  }

  advanceFilter() {
    this.advanceFilterShow = !this.advanceFilterShow;
  }


  onChange() {
    const choosen = this.selectedjntOrders[this.selectedjntOrders.length - 1];
    this.selectedjntOrders.length = 0;
    this.selectedjntOrders.push(choosen);
  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.boutiqaat!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  value: Date;
  

  @ViewChild('myStartCalendar') startCalendar: Calendar;
  filter(type: any) {
    if (type == 'customer') {
      this.searhform.controls.customerCode.patchValue([this.searhform.controls.customerCodeFE.value]);
    }
    if (type == 'date') {
      if (this.searhform.controls.fullDate.value[1]) { // If second date is selected
        this.startCalendar.overlayVisible = false;
      }
      if (this.searhform.controls.fullDate.value.length <= 0) {
        this.cs.notifyOther(true);
        return;
      }
    }

    this.jntOrders = [];
    this.filterResult = [];

    if (this.searhform.controls.fullDate.value != null) {
      this.searhform.controls.startDate.patchValue(this.cs.day_callapi_UTC(this.searhform.controls.fullDate.value[0]));
      this.searhform.controls.endDate.patchValue(this.cs.day_callapi_UTC(this.searhform.controls.fullDate.value[1]));

      if (this.searhform.controls.endDate.value == null) {
        this.cs.notifyOther(true);
        return;
      }
    }


    this.spin.show();
    this.service.findConsignment(this.searhform.getRawValue()).subscribe(res => {
      this.jntOrders = res;
      this.selectedjntOrders = [];

      this.spin.hide();
    }, err => {
      this.spin.hide();
      this.cs.commonerror(err);
    })
  }

  reset() {
    this.searhform.reset();
    // this.searhform.controls.orderType.patchValue([1]);
    this.searhform.controls.hubCode.patchValue(["QATARPOST"]);
    // this.searhform.controls.customer_code.patchValue(["BOQ"]);
    this.filter('reset');
  }

  downloadExcel() {
    var res: any = [];
    this.jntOrders.forEach(x => {
      res.push({
        "Shipsy AWB No": x.reference_number,
        "Customer Code ": x.customer_code,
        "Hub Code ": 'Qatar Post',
        "Willingness": x.ajx_willingness,
        "Integration Status ": x.boutiqaat_push_status,
        "JNT Bill Code ": x.awb_3rd_Party,
        "Created On": this.cs.dateapi(x.created_at),
        "Event": x.item_action_name,
        "Event Time": this.cs.dateapi(x.action_time),
      });

    })
    this.cs.exportAsExcel(res, "JNT Orders");
  }

}
