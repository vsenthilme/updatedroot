import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { PathNameService } from '../../../../common-service/path-name.service';
import { SubProductService } from '../sub-product.service';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';
import { FormBuilder, Validators, FormControl, FormArray, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-sub-products-values',
  templateUrl: './sub-products-values.component.html',
  styleUrl: './sub-products-values.component.scss'
})
export class SubProductsValuesComponent {

  status: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<SubProductsValuesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: SubProductService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
  ) { 
    this.status = [
      { value: '2', label: 'Inactive' },
      { value: '1', label: 'Active' }
    ];
  }

  form = this.fb.group({
    languageId: [this.auth.languageId],
    languageDescription: [],
    companyId: [this.auth.companyId],
    companyName: [],
    subProductId: [],
    subProductName: [, Validators.required],
    subProductValue: [, Validators.required],
    remark: [],
    statusId: ['1',],
    statusDescription: [],
    referenceField1: [, Validators.required],
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


  ngOnInit(): void {
    
    this.form.controls.subProductValue.patchValue(this.data);

    if(this.data.pageflow == "Edit"){
      this.form.patchValue(this.data.code)
    }

  }

  save() {
      this.dialogRef.close(this.form.value);
  }
  
}
