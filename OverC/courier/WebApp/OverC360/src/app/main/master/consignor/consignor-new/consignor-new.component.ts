import { Component } from '@angular/core';
import { ConsignorService } from '../consignor.service';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { CustomerService } from '../../customer/customer.service';
import { ProductService } from '../../../id-masters/product/product.service';
import { SubProductService } from '../../../id-masters/sub-product/sub-product.service';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { MatDialog } from '@angular/material/dialog';
import { ConsignorValueComponent } from '../consignor-value/consignor-value.component';

@Component({
  selector: 'app-consignor-new',
  templateUrl: './consignor-new.component.html',
  styleUrl: './consignor-new.component.scss'
})
export class ConsignorNewComponent {

  active: number | undefined = 0;
  status: any[] = []

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsignorService,
    private subProductService: SubProductService,
    private numberRangeService: NumberrangeService,
    private productService: ProductService,
    private customerService: CustomerService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private dialog: MatDialog,
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  numCondition: any;
  pageToken: any;

  // form builder initialize
  form = this.fb.group({
    languageId: [this.auth.languageId],
    languageDescription: [],
    companyId: [this.auth.companyId],
    companyName: [],
    subProductId: [],
    subProductName: [],
    subProductValue: [],
    productId: [],
    productName: [],
    customerId: [],
    customerName: [],
    consignorId: [],
    consignorName: [, Validators.required],
    remark: [],
    agingCount: [],
    statusId: ["16",],
    statusDescription: [],
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

    const dataToSend = ['Master', 'Consignor', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.subProductId.disable();
      this.form.controls.productId.disable();
      this.form.controls.customerId.disable();
      this.form.controls.consignorId.disable();
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
    obj.numberRangeObject = ['CONSIGNOR'];
    this.numberRangeService.search(obj).subscribe({
      next: (res: any) => {
        if (res.length > 0) {
          this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
          this.form.controls.consignorId.patchValue(this.nextNumber);
          this.numCondition = 'true';
          this.form.controls.referenceField10.patchValue(this.numCondition);
          this.form.controls.consignorId.disable();
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
  customerIdList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.subProduct.url,
      this.cas.dropdownlist.setup.product.url,
      this.cas.dropdownlist.setup.customer.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.subProductIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.subProduct.key);
        this.productIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.product.key);
        this.customerIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.customer.key);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  consignorArray: any[] = [];

  add() {
    const dialogRef = this.dialog.open(ConsignorValueComponent, {
      disableClose: true,
      width: '70%',
      height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: this.consignorArray.length + 1,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.consignorArray.push(result);
      }
    });
  }

  removeItem(index: number) {
    this.consignorArray.splice(index, 1);
  }


  fill(line: any) {
    this.form.patchValue(line);
    this.spin.show();
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.consignorId = [line.consignorId];

    this.service.search(obj).subscribe({
      next: (res: any) => {
        console.log(res);
        this.consignorArray = res;
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
    if (this.consignorArray.length == 0) {
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
      this.consignorArray.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.consignorId = this.form.controls.consignorId.value;
        x.consignorName = this.form.controls.consignorName.value;
        x.statusId = this.form.controls.statusId.value;
        x.remark = this.form.controls.remark.value;
      });
      this.service.UpdateBulk(this.consignorArray).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].consignorId + ' has been updated successfully',
          });
          this.router.navigate(['/main/master/consignor']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.consignorArray.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.consignorId = this.form.controls.consignorId.value;
        x.consignorName = this.form.controls.consignorName.value;
        x.statusId = this.form.controls.statusId.value;
        x.remark = this.form.controls.remark.value;
      });
      this.service.CreateBulk(this.consignorArray).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res[0].consignorId + ' has been created successfully',
            });
            this.router.navigate(['/main/master/consignor']);
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
    const dialogRef = this.dialog.open(ConsignorValueComponent, {
      disableClose: true,
      width: '70%',
      height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { pageflow: data, code: this.consignorArray[i] },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.consignorArray.splice(i, 0);
        this.consignorArray.splice(i, 1, result);
        console.log(result);
        //this.form.patchValue(result);
        this.consignorArray = [...this.consignorArray]

      }
    });
  }

  subProductChanged() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.subProductId = [this.form.controls.subProductId.value];

    this.productIdList = [];
    this.spin.show();
    this.productService.search(obj).subscribe({
      next: (result) => {
        this.productIdList = this.cas.foreachlist(result, { key: 'productId', value: 'productName' });
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  productChanged() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.subProductId = [this.form.controls.subProductId.value]
    obj.productId = [this.form.controls.productId.value];

    this.customerIdList = [];
    this.spin.show();
    this.customerService.search(obj).subscribe({
      next: (result) => {
        this.customerIdList = this.cas.foreachlist(result, { key: 'customerId', value: 'customerName' });
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

}
