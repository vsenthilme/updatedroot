import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of, Subscription } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Location } from "@angular/common";
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { PreoutboundService } from 'src/app/main-module/Outbound/preoutbound/preoutbound.service';
import { UomService } from 'src/app/main-module/other-setup/uom/uom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { MasterService } from 'src/app/shared/master.service';

@Component({
  selector: 'app-masterchild',
  templateUrl: './masterchild.component.html',
  styleUrls: ['./masterchild.component.scss']
})
export class MasterchildComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private auth: AuthService,
    public dialogRef: MatDialogRef<DialogExampleComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute, private router: Router,
    private cs: CommonService,
    private service: PreoutboundService,
    private location: Location,
    private cas: CommonApiService,
    private reportService: ReportsService,
    private uom: UomService,
    private itemService: BasicdataService,
    private masterService: MasterService
  ) { }


  form = this.fb.group({
    bomNumber: [],
    childItemCode: [],
    childItemQuantity: [],
    companyCode: [this.auth.companyId],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    languageId: [this.auth.languageId],
    plantId: [this.auth.plantId],
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
    sequenceNo: [],
    statusId: [],
    updatedBy: [],
    updatedOn: [],
    warehouseId: [this.auth.warehouseId],
  });

  panelOpenState = false;
  submitted = false;
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

  itemTypeList: any[] = [];
  sub = new Subscription();
  ngOnInit(): void {
    let obj1: any = {};
    obj1.companyCodeId = this.auth.companyId;
    obj1.plantId = this.auth.plantId;
    obj1.languageId = [this.auth.languageId];
    obj1.warehouseId = this.auth.warehouseId;

    this.spin.show();
    this.masterService.searchitemtype(obj1).subscribe((res: any[]) => {
      res.forEach(element => {
        this.itemTypeList.push({
          value:(element.itemTypeId).toString(), label: element.itemTypeId + '-' + element.itemType, itemType: element.itemType
        });
        //this.form.controls.referenceField10.patchValue(element.itemType);
      });
      this.spin.hide();
    })
    if (this.data.pageFlow != 'Edit') {
      this.form.controls.referenceField2.patchValue(this.data);
    }

    if (this.data.pageflow == "Edit") {
      this.form.patchValue(this.data.code)
      console.log(this.data.code)
      this.dropdwonlist1();
      this.form.controls.referenceField8.patchValue(this.data.code.referenceField8) 
      let obj1: any = {};
      obj1.companyCode = [this.auth.companyId];
      obj1.plantId = [this.auth.plantId];
      obj1.languageId = [this.auth.languageId];
      obj1.warehouseId = [this.auth.warehouseId];
      obj1.itemCode = [this.data.code.childItemCode];
      this.sub.add(this.itemService.search(obj1).subscribe((res: any) => {
       this.form.controls.referenceField8.patchValue(res[0].itemType.toString())
        this.spin.hide();
  
      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));  
      }
    this.dropdownList();
  }

  onservetypeChange(value) {
    const serviceType = this.itemTypeList.find(serviceType => serviceType.value === value);
    if (serviceType) {
      this.form.controls.referenceField10.patchValue(serviceType.itemType);
    } else {
      console.error('module not found');
    }
  }

  uomList: any[] = [];
  partnerList: any[] = [];
  dropdownList() {
    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.plantId = this.auth.plantId;
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = this.auth.warehouseId;
    this.spin.show();
    this.uom.search(obj).subscribe((res: any[]) => {
      res.forEach(element => {
        this.uomList.push({ value: element.uomId, label: element.uomId + '-' + element.description });
      });
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    });
    this.spin.show();
  }
dropdwonlist1(){
  let obj1: any = {};
  obj1.companyCodeId = this.auth.companyId;
  obj1.plantId = this.auth.plantId;
  obj1.languageId = [this.auth.languageId];
  obj1.warehouseId = this.auth.warehouseId;
 

  this.spin.show();
  this.masterService.searchitemtype(obj1).subscribe((res: any[]) => {
    res.forEach(element => {
      this.itemTypeList.push({
        value:(element.itemTypeId).toString(), label: element.itemTypeId + '-' + element.itemType, itemType: element.itemType
      });
     
    });
    this.spin.hide();
  })
}

  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ itemList }) => {
          if (itemList != null && itemList.length > 0) {
            this.multiselectItemCodeList = [];
            this.itemCodeList = itemList;
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description, manufacturingName: x.manufacturerName, description: x.description, uomId: x.uomId }))
          }
        });
    }
  }

  showDropdown1: true;
  showDropdown() {
    this.showDropdown1 = true;
  }

  showDropdown2: true;
  showDropdown4() {
    this.showDropdown2 = true;
  }

  itemCodeChanged(e) {
    this.form.controls.referenceField6.patchValue(e.description);
    let obj: any = {};
    obj.companyCodeId = [this.auth.companyId]
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.plantId = [this.auth.plantId]
    obj.itemCode = [e.value];
    this.itemService.searchDecription(obj).subscribe(res => {
      this.spin.hide();
      
      this.form.controls.referenceField7.patchValue(res[0].uomId)
    this.form.controls.referenceField8.patchValue(res[0].itemType.toString())
    this.form.controls.referenceField10.patchValue(res[0].itemTypeDescription)
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    });
  }

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

    let obj: any = {};
    obj.data = this.form.value;
    this.form.controls.referenceField4.patchValue(this.auth.userID);
    this.form.controls.referenceField5.patchValue(new Date())
    this.dialogRef.close(this.form.getRawValue());
  }

}


