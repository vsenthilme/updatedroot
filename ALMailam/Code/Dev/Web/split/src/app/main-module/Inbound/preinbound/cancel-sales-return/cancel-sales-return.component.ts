import { Component, Inject, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { CancelSupplierInvoiceComponent } from '../cancel-supplier-invoice/cancel-supplier-invoice.component';
import { PreinboundService } from '../preinbound.service';

@Component({
  selector: 'app-cancel-sales-return',
  templateUrl: './cancel-sales-return.component.html',
  styleUrls: ['./cancel-sales-return.component.scss']
})
export class CancelSalesReturnComponent implements OnInit {

  disabled = false;
  step = 0;
  //dialogRef: any;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }
  form = this.fb.group({
    refDocNumber: [, Validators.required],
    remarks: [, Validators.required],

  });
  panelOpenState = false;
  multiRemarks: any[] = [];

  constructor(
    private router: Router,
    private service: PreinboundService,
    public dialogRef: MatDialogRef<CancelSupplierInvoiceComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,
    private cas: CommonApiService,
  ) { 
    this.multiRemarks = [
      {value: 'Damage Parts - During Delivery', label: 'Damage Parts - During Delivery'},
      {value: 'Physical Stock Not Available ', label: 'Physical Stock Not Available '},
      {value: 'Quality Complaints', label: 'Quality Complaints'},
      {value: 'Wrong Invoice', label: 'Wrong Invoice'},
      {value: 'Wrong Parts - Customer', label: 'Wrong Parts - Customer'},
      {value: 'Wrong Parts - Salesman', label: 'Wrong Parts - Salesman'}
  ];

  }
  ngOnInit(): void {
    this.form.controls.refDocNumber.disable();
    this.form.controls.refDocNumber.patchValue(this.data.code.refDocNumber);
  }
  sub = new Subscription();
  submitted = false;

  submit() {
    this.submitted = true;
    if (this.form.invalid) {
      this.toastr.error(
        "Please fill required fields to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );
      this.cs.notifyOther(true);
      return;
    }

    this.spin.show();
    let obj: any = {};
    obj.languageId = this.auth.languageId;
    obj.companyCodeId = this.auth.companyId;
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;
    obj.refDocNumber = this.data.code.refDocNumber;
    obj.preInboundNo = this.data.code.preInboundNo;
    obj.remarks = this.form.controls.remarks.value;

    this.sub.add(this.service.cancelSalesReturn(obj).subscribe(res => {
      this.data.code.referenceField1 = this.form.controls.remarks.value;
      this.toastr.success("Order Cancelled Sucessfully!", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      this.dialogRef.close();
      this.spin.hide();

    }, err => {
      this.cs.commonerrorNew(err);
      this.dialogRef.close();
      this.spin.hide();
    }));


  }
  email = new FormControl('', [Validators.required, Validators.email]);
  public errorHandling = (control: string, error: string = "required") => {
    return this.form.controls[control].hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
}









