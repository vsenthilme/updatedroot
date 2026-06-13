import { Component } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { ProductService } from '../product.service';
import { AuthService } from '../../../../core/Auth/auth.service';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { NumberrangeService } from '../../numberrange/numberrange.service';
import { MatDialog } from '@angular/material/dialog';
import { ProductValueComponent } from '../product-value/product-value.component';

@Component({
  selector: 'app-product-new',
  templateUrl: './product-new.component.html',
  styleUrl: './product-new.component.scss',
})
export class ProductNewComponent {
  active: number | undefined = 0;
  status: any[] = [];

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ProductService,
    private numberRangeService: NumberrangeService,
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
    productName: [, Validators.required],
    productValue: [],
    remark: [],
    statusId: ["16", ],
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

    const dataToSend = ['Setup', 'Product', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.subProductId.disable();
      this.form.controls.productId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
    else {
      this.checkNumberRange();
    }
  }

  checkNumberRange(){
    this.spin.show();
    let obj: any = {};
    obj.numberRangeObject = ['PRODUCT'];
    this.numberRangeService.search(obj).subscribe({
      next: (res: any) => {
        if (res.length > 0) {
          this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
          this.form.controls.productId.patchValue(this.nextNumber);
          this.numCondition = 'true';
          this.form.controls.referenceField10.patchValue(this.numCondition);
          this.form.controls.productId.disable();
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

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  productArray: any[] = [];

  add() {
    const dialogRef = this.dialog.open(ProductValueComponent, {
      disableClose: true,
      width: '70%',
      height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: this.productArray.length + 1,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.productArray.push(result);
        console.log(this.productArray)
      }
    });
  }

  removeItem(index: number) {
    this.productArray.splice(index, 1);

    // this.service.Delete(this.productArray[0].productId, this.productArray[0].subProductId).subscribe({
    //   next: (res) => {
    //     this.messageService.add({
    //       severity: 'success',
    //       summary: 'Deleted',
    //       key: 'br',
    //       detail: this.productArray[0].productId + ' Deleted successfully',
    //     });
    //     this.spin.hide();
    //   },
    //   error: (err) => {
    //     this.cs.commonerrorNew(err);
    //     this.spin.hide();
    //   },
    // });
  }

  save() {
    this.submitted = true;
    if (this.productArray.length == 0) {
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
      this.productArray.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.productId = this.form.controls.productId.value;
        x.productName = this.form.controls.productName.value;
        x.statusId = this.form.controls.statusId.value;
        x.remark = this.form.controls.remark.value;
      });
      this.service.UpdateBulk(this.productArray).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].productId + ' has been updated successfully',
          });
          this.router.navigate(['/main/idMaster/product']);
          this.spin.hide();
        },
        error: (err) => { 
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.productArray.forEach((x: any) => {
        x.languageId = this.auth.languageId;
        x.companyId = this.auth.companyId;
        x.productId = this.form.controls.productId.value;
        x.productName = this.form.controls.productName.value;
        x.statusId = this.form.controls.statusId.value;
        x.remark = this.form.controls.remark.value;
      });
      this.service.CreateBulk(this.productArray).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res[0].productId + ' has been created successfully',
            });
            this.router.navigate(['/main/idMaster/product']);
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

  fill(line: any) {
    this.form.patchValue(line);
    this.spin.show();
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.productId = [line.productId];

    this.service.search(obj).subscribe({
      next: (res: any) => {
        console.log(res);
        this.productArray = res;
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

  

  editItem(data: any,i: any): void {
    const dialogRef = this.dialog.open(ProductValueComponent, {
      disableClose: true,
      width: '70%',
      height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: {pageflow: data,code:this.productArray[i]},
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.productArray.splice(i,0);
        this.productArray.splice(i, 1, result);
        console.log(result);
      //this.form.patchValue(result);
      this.productArray = [...this.productArray]
  
  }});
  }
}
