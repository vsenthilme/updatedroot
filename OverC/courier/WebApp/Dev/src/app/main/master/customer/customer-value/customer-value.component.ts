import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { PathNameService } from '../../../../common-service/path-name.service';
import { CustomerService } from '../customer.service';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';
import { FormBuilder, Validators, FormControl, FormArray, FormGroup } from '@angular/forms';
import { SubProductService } from '../../../id-masters/sub-product/sub-product.service';
import { ProductService } from '../../../id-masters/product/product.service';


@Component({
  selector: 'app-customer-value',
  templateUrl: './customer-value.component.html',
  styleUrl: './customer-value.component.scss'
})
export class CustomerValueComponent {

  status: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<CustomerValueComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private productService: ProductService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private subProductService: SubProductService,
    private customerService: CustomerService,
  ) {
    this.status = [
      { value: '2', label: 'Inactive' },
      { value: '1', label: 'Active' }
    ];
  }

  // form builder initialize
  form = this.fb.group({
    languageId: [this.auth.languageId],
    languageDescription: [],
    companyId: [this.auth.companyId],
    companyName: [],
    subProductId: [, Validators.required],
    subProductName: [],
    subProductValue: [],
    productId: [, Validators.required],
    productName: [],
    customerId: [],
    customerName: [, Validators.required],
    statusId: ["16",],
    statusDescription: [],
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

  ngOnInit(): void {

    this.form.controls.customerId.patchValue(this.data);
    // this.form.controls.referenceField1.disable();
    if (this.data.pageflow == "Edit") {
      this.form.patchValue(this.data.code)
    }

    this.productDropdown();

    // this.form.controls.referenceField1.disable();
  }

  save() {
    this.submitted = true;
    this.dialogRef.close(this.form.value);
  }

  productIdList: any[] = [];

  productDropdown() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];

    this.productIdList = [];
    this.spin.show();
    this.productService.search(obj).subscribe({
      next: (result) => {
        this.productIdList = this.cas.foreachlist(result, { key: 'productId', value: 'productName' });
        this.productIdList = this.cs.removeDuplicatesFromArrayList(this.productIdList, 'value')

        if(this.data.pageflow == 'Edit') {
          this.subProductIdList = this.cas.foreachlist(result, { key: 'subProductId', value: 'subProductName' });
          this.subProductIdList = this.cs.removeDuplicatesFromArrayList(this.subProductIdList, 'value')
          this.subProductValueList = this.cas.foreachlist(result, { key: 'subProductValue', value: 'subProductValue' });
        }
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  subProductIdList: any[] = [];
  subProductValueList: any[] = [];

  productChanged() {  // subProduct dropdown

    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.productId = [this.form.controls.productId.value];

    this.subProductIdList = [];
    this.spin.show();
    this.productService.search(obj).subscribe({
      next: (result) => {
        this.subProductIdList = this.cas.foreachlistWithoutKey(result, { key: 'subProductId', value: 'subProductName' });
        this.subProductIdList = this.cs.removeDuplicatesFromArrayList(this.subProductIdList, 'value')
        this.subProductValueList = this.cas.foreachlistWithoutKey(result, { key: 'subProductValue', value: 'subProductValue' });
        this.subProductValueList = this.cs.removeDuplicatesFromArrayList(this.subProductValueList, 'value')
        this.form.patchValue(result[0]);
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  subProductChanged() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.subProductId = [this.form.controls.subProductId.value];
    // obj.subProductValue = [this.form.controls.subProductValue.value];
    
    this.subProductValueList = [];
    this.spin.show();
    this.subProductService.search(obj).subscribe({next: (result) => {
      this.subProductValueList = this.cas.foreachlist(result, {key: 'subProductValue', value: 'referenceField1'});
      this.subProductValueList = this.cs.removeDuplicatesFromArrayList(this.subProductValueList, 'value')
      this.form.patchValue(result[0]);
      this.spin.hide();
    }, error: (err) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }
  })
  }

  subProductValueChanged() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.subProductId = [this.form.controls.subProductId.value];
    obj.subProductValue = [this.form.controls.subProductValue.value];

    this.spin.show();
    this.subProductService.search(obj).subscribe({
      next: (result) => {
        this.form.patchValue(result[0]);
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

}
