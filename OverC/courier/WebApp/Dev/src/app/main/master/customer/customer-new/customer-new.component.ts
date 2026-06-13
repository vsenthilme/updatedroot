import { Component } from '@angular/core';
import { CustomerService } from '../customer.service';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { SubProductService } from '../../../id-masters/sub-product/sub-product.service';
import { ProductService } from '../../../id-masters/product/product.service';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { MatDialog } from '@angular/material/dialog';
import { CustomerValueComponent } from '../customer-value/customer-value.component';

@Component({
  selector: 'app-customer-new',
  templateUrl: './customer-new.component.html',
  styleUrl: './customer-new.component.scss'
})
export class CustomerNewComponent {

  active: number | undefined = 0;
  status: any[] = []

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: CustomerService,
    private subProductService: SubProductService,
    private productService: ProductService,
    private numberRangeService: NumberrangeService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    public dialog: MatDialog,
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  pageToken: any;
  numCondition: any;

  // form builder initialize
  form = this.fb.group({
    languageId: [this.auth.languageId],
    languageDescription: [],
    companyId: [this.auth.companyId],
    companyName: [],
    subProductId: [],
    subProductName: [],
    productId: [],
    productName: [],
    customerId: [],
    customerName: [, Validators.required],
    statusId: ["16",],
    statusDescription: [],
    agingCount: [],
    remark: [],
    referenceField1: [],
    referenceField10: [],
    referenceField2: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    createdOn: ['',],
    createdBy: [],
    updatedOn: ['',],
    updatedBy: [],
  });

  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = 'required') {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  nextNumber: any;
  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Master', 'Customer', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.subProductId.disable();
      this.form.controls.productId.disable();
      this.form.controls.customerId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
    else {
      this.checkNumberRange();
    }
  }

  checkNumberRange() {
    this.spin.show();
    let obj: any = {};
    obj.numberRangeObject = ['CUSTOMER'];
    this.numberRangeService.search(obj).subscribe({
      next: (res: any) => {
        if (res.length > 0) {
          this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
          this.form.controls.customerId.patchValue(this.nextNumber);
          this.numCondition = 'true';
          this.form.controls.referenceField10.patchValue(this.numCondition);
          this.form.controls.customerId.disable();
        }
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  subProductIdList: any[] = [];
  productIdList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.subProduct.url,
      this.cas.dropdownlist.setup.product.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.subProductIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.subProduct.key);
        this.productIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.product.key);
        console.log(this.productIdList);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  customerArray: any[] = [];

  add() {
    const dialogRef = this.dialog.open(CustomerValueComponent, {
      disableClose: true,
      width: '70%',
      height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: this.customerArray.length + 1,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.customerArray.push(result);
        console.log(this.customerArray)
      }
    });
  }

  removeItem(index: number) {
    this.customerArray.splice(index, 1);
  }

  fill(line: any) {
    this.form.patchValue(line);
    this.spin.show();
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.customerId = [line.customerId];

    this.service.search(obj).subscribe({
      next: (res: any) => {
        console.log(res);
        this.customerArray = res;
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
  }

  save() {

    this.submitted = true;
    if (this.customerArray.length == 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }

    if (this.pageToken.pageflow != 'New') {
      this.spin.show();
      this.customerArray.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.customerId = this.form.controls.customerId.value;
        x.customerName = this.form.controls.customerName.value;
        x.statusId = this.form.controls.statusId.value;
        x.remark = this.form.controls.remark.value;
      });
      this.service.UpdateBulk(this.customerArray).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].customerId + ' has been updated successfully',
          });
          this.router.navigate(['/main/master/customer']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.customerArray.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.customerId = this.form.controls.customerId.value;
        x.customerName = this.form.controls.customerName.value;
        x.statusId = this.form.controls.statusId.value;
        x.remark = this.form.controls.remark.value;
      });
      console.log(this.customerArray)
      this.service.CreateBulk(this.customerArray).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res[0].customerId + ' has been created successfully',
            });
            this.router.navigate(['/main/master/customer']);
            this.spin.hide();
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  editItem(data: any, i: any): void {
    const dialogRef = this.dialog.open(CustomerValueComponent, {
      disableClose: true,
      width: '70%',
      height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { pageflow: data, code: this.customerArray[i] },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.customerArray.splice(i, 0);
        this.customerArray.splice(i, 1, result);
        console.log(result);
        //this.form.patchValue(result);
        this.customerArray = [...this.customerArray]

      }
    });
  }

}





