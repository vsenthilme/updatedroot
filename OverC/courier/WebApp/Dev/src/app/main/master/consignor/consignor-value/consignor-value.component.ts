import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { PathNameService } from '../../../../common-service/path-name.service';
import { CustomerService } from '../../customer/customer.service';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';
import { FormBuilder, Validators, FormControl, FormArray, FormGroup } from '@angular/forms';
import { SubProductService } from '../../../id-masters/sub-product/sub-product.service';
import { ProductService } from '../../../id-masters/product/product.service';
import { ConsignorService } from '../consignor.service';

@Component({
  selector: 'app-consignor-value',
  templateUrl: './consignor-value.component.html',
  styleUrl: './consignor-value.component.scss'
})
export class ConsignorValueComponent {

  status: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ConsignorValueComponent>,
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
    private consignorService: ConsignorService,
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

  ngOnInit(): void {

    this.form.controls.customerId.patchValue(this.data);
    // this.form.controls.referenceField1.disable();
    if (this.data.pageflow == "Edit") {
      this.form.patchValue(this.data.code)
    }

    this.customerDropdown();

    // this.form.controls.referenceField1.disable();
  }

  save() {
    this.submitted = true;
    this.dialogRef.close(this.form.value);
  }

  customerIdList: any[] = [];

  customerDropdown() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];

    this.customerIdList = [];
    this.spin.show();
    this.customerService.search(obj).subscribe({
      next: (result) => {
        this.customerIdList = this.cas.foreachlist(result, { key: 'customerId', value: 'customerName' });
        this.customerIdList =  this.cs.removeDuplicatesFromArrayList(this.customerIdList, 'value')
        
        if(this.data.pageflow == 'Edit') {
          this.productIdList = this.cas.foreachlist(result, { key: 'productId', value: 'productName' });
          this.subProductIdList = this.cas.foreachlist(result, { key: 'subProductId', value: 'subProductName' });
          // this.subProductValueList = this.cas.foreachlist(result, { key: 'subProductValue', value: 'subProductValue' });
        }

        console.log(this.customerIdList);
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }

  productIdList: any[] = [];
  subProductIdList: any[] = [];
  subProductValueList: any[] = [];

  customerChanged() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.customerId = [this.form.controls.customerId.value];

    this.subProductIdList = [];
    this.spin.show();
    this.customerService.search(obj).subscribe({
      next: (result) => {
        this.form.patchValue(result[0]);
        this.productIdList = this.cas.foreachlistWithoutKey(result, { key: 'productId', value: 'productName' });
        this.subProductIdList = this.cas.foreachlistWithoutKey(result, { key: 'subProductId', value: 'subProductName' });
        this.subProductValueList = this.cas.foreachlistWithoutKey(result, { key: 'subProductValue', value: 'subProductValue' });
        this.productIdList = this.cs.removeDuplicatesFromArrayList(this.productIdList, 'value');
        this.subProductIdList = this.cs.removeDuplicatesFromArrayList(this.subProductIdList, 'value');

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

    this.subProductValueList = [];
    this.spin.show();
    this.subProductService.search(obj).subscribe({
      next: (result) => {
        this.subProductValueList = this.cas.foreachlist(result, { key: 'subProductValue', value: 'referenceField1' });
        this.subProductValueList =  this.cs.removeDuplicatesFromArrayList(this.subProductValueList, 'value')
        // this.form.patchValue(result[0]);
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
