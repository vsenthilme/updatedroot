import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { PathNameService } from '../../../../common-service/path-name.service';
import { ProductService } from '../product.service'; 
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';
import { FormBuilder, Validators, FormControl, FormArray, FormGroup } from '@angular/forms';
import { SubProductService } from '../../sub-product/sub-product.service';


@Component({
  selector: 'app-product-value',
  templateUrl: './product-value.component.html',
  styleUrl: './product-value.component.scss'
})
export class ProductValueComponent {

  status: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ProductValueComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ProductService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private subProductService: SubProductService,
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
  
  ngOnInit(): void {
    
    // this.form.controls.referenceField1.disable();
    
    if(this.data.pageflow == "Edit"){
      this.form.patchValue(this.data.code)
    }

    this.subProductDropdown();

    // this.form.controls.referenceField1.disable();

  }

  save() {
      this.submitted = true;
      console.log(this.form.value);
      this.dialogRef.close(this.form.value);
  }

  subProductIdList: any[] = [];
  subProductValueList: any[] = [];

  subProductDropdown() {

    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];

    this.subProductIdList = [];
    this.spin.show();
    this.subProductService.search(obj).subscribe({next: (result) => {
      this.subProductIdList = this.cas.foreachlist(result, {key: 'subProductId', value: 'subProductName'});
      this.subProductIdList = this.cs.removeDuplicatesFromArrayList(this.subProductIdList, 'value')

      if(this.data.pageflow == 'Edit') {
        this.subProductValueList = this.cas.foreachlist(result, {key: 'subProductValue', value: 'subProductValue'});
      }
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
    this.subProductService.search(obj).subscribe({next: (result) => {
      this.subProductValueList = this.cas.foreachlist(result, {key: 'subProductValue', value: 'referenceField1'});
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
    this.subProductService.search(obj).subscribe({next: (result) => {
      this.form.patchValue(result[0]);
      this.spin.hide();
    }, error: (err) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    } 
  })
  }
  
}
