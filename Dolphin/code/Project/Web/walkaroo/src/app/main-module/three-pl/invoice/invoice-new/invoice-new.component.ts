import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { variant } from 'src/app/main-module/cycle-count/variant-analysis/variant-analysis.component';
import { InvoiceService } from '../invoice.service';
import { AuthService } from 'src/app/core/Auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { ProformaPopupComponent } from '../../proforma/proforma-popup/proforma-popup.component';
import { InvoicePopupComponent } from '../invoice-popup/invoice-popup.component';

@Component({
  selector: 'app-invoice-new',
  templateUrl: './invoice-new.component.html',
  styleUrls: ['./invoice-new.component.scss']
})
export class InvoiceNewComponent implements OnInit {

  advanceFilterShow: boolean;
  @ViewChild('Setupinvoice') Setupinvoice: Table | undefined;
  invoice: any;
  selectedinvoice: any;

  form = this.fb.group({
    billDateFrom: [],
    billDateTo: [],
    billQuantity: [],
    billamount:[],
    billUnit: [],
    companyCodeId: [],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    invoiceAmount: [],
    invoiceDate: [],
    invoiceNumber: [],
    languageId: [],
    partnerCode: [],
    paymentAmount: [],
    plantId: [],
    priceUnit: [],
    proformaBillNo: [],
    statusId: [],
    updatedBy: [],
    updatedOn: [],
    warehouseId: [],
  });
  
  isShowDiv = false;
  table = true;
  fullscreen = false;
  search = true;
  step = 0;
  back = false;
  div1Function() {
    this.table = !this.table;
  }
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  toggleFloat() {

    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;

  }
  showFiller = false;
  sub = new Subscription();
  constructor(
    private route: ActivatedRoute, private router: Router, public toastr: ToastrService, private spin: NgxSpinnerService, private fb: FormBuilder, private cs: CommonService,
    private service: InvoiceService, private auth: AuthService, public dialog: MatDialog,
  ) { }
  js: any = {};
  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    this.js = this.cs.decrypt(code);
console.log(this.js)
    if(this.js.pageflow != 'Invoice'){
        this.fill()
    }else{
      this.fillForNew()
    }

  }
total:any;
showresult:any;
  fillForNew(){
    console.log(this.js.code.proformaBillNo)
    this.service.GetProfomaHeader(this.js.code.proformaBillNo,this.auth.companyId,this.auth.languageId,this.js.code.partnerCode,this.auth.warehouseId,this.auth.plantId).subscribe(res => {
      if(res){
        this.form.patchValue(res, { emitEvent: false });
        this.form.controls.billamount.patchValue(res.billQuantity)
        let obj: any = {};
        obj.companyCodeId = [this.auth.companyId];
        obj.plantId = [this.auth.plantId];
        obj.languageId = [this.auth.languageId];
            obj.warehouseId = [this.auth.warehouseId];
            obj.proformaHeaderId=[this.js.code.proformaBillNo];
            obj.partnerCode=[res.partnerCode]
            
        this.service.searchProformaline(obj).subscribe(res => {
          this.invoice = res;
          console.log(this.invoice)
          this.selectedinvoice=res;
          this.showresult=true;
          this.total.patchValue(this.getInvQty());
        })
      }
     })
  }
  addLine(){
    const dialogRef = this.dialog.open(InvoicePopupComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
     data:this.form.controls.partnerCode.value,
    });
  
    dialogRef.afterClosed().subscribe(result => {
      this.spin.show();
      if(result){
  if (this.showresult == true){
    console.log(this.selectedinvoice.length);
     console.log(result)
     result.partnerCode=this.form.controls.partnerCode.value;
     result.proformaBillNo=this.form.controls.proformaBillNo.value;
     result.lineNumber=(this.selectedinvoice.length)+1;
          this.invoice.unshift(result); 
        this.invoice = this.invoice.slice();
        this.selectedinvoice = this.invoice;
      
        console.log(this.invoice)
        console.log(this.selectedinvoice.length);
  }
}
    this.spin.hide();
    });
  }
fill(){
  this.service.GetProfomaHeader(this.js.code.proformaBillNo,this.auth.companyId,this.auth.languageId,this.js.code.partnerCode,this.auth.warehouseId,this.auth.plantId).subscribe(res => {
  this.service.GetProfomaLine(this.js.code.proformaBillNo,this.auth.companyId,this.auth.languageId,this.js.code.partnerCode,this.auth.warehouseId,this.auth.plantId).subscribe(result => {
  this.form.patchValue(res, { emitEvent: false });
  this.invoice=(result);
  this.selectedinvoice=this.invoice;
  this.form.controls.billamount.patchValue(res.billamount);
  })
 })
}
getInvQty() {
  let total = 0;
  this.invoice.forEach(x => {
    total = total + (x.billQuantity != null ? x.billQuantity : 0)
  })
  return Math.round(total * 100 / 100)
}
  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
  }
  Submit() {
    this.service.CreateHeader(this.form.getRawValue()).subscribe(res => {
      this.invoice.forEach(line => {
        line.invoiceHeaderId = res.invoiceNumber;
    });
      if(res){
        this.service.CreateLine(this.invoice).subscribe(res => {
          this.toastr.success("Invoice Created successfully", "Notification", {
            timeOut: 2000,
             progressBar: false,
           });
           this.router.navigate(['/main/threePL/invoice/']);
        })
      }
    })
  }
}


