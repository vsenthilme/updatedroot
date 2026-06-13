import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of } from 'rxjs';
import { Location } from "@angular/common";
import { catchError } from 'rxjs/operators';
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PreinboundService } from 'src/app/main-module/Inbound/preinbound/preinbound.service';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { UomService } from 'src/app/main-module/other-setup/uom/uom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';

@Component({
  selector: 'app-addliines',
  templateUrl: './addliines.component.html',
  styleUrls: ['./addliines.component.scss']
})
export class AddliinesComponent implements OnInit {
  constructor(private fb: FormBuilder,
    private auth: AuthService,
    public dialogRef: MatDialogRef<DialogExampleComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute, private router: Router,
    private cs: CommonService,
    private service: PreinboundService,
    private itemService:BasicdataService,
    private reportService: ReportsService,
    private location: Location,
    private cas: CommonApiService,
    private uom: UomService,
    private business: BusinessPartnerService,
  ) { }


  form = this.fb.group({
    countedQty: [],
    cycleCountNo: [],
    frozenQty: [],
    isCancelled: [],
    isCompleted: [],
    itemCode: [,Validators.required],
    itemDescription: [],
    lineNoOfEachItemCode: [],
    manufacturerCode: [,Validators.required],
    manufacturerName: [],
    uom: [],
  });

  cancel() {
    this.dialogRef.close();
  }

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
  ngOnInit(): void {
    if(this.data== 1){
    this.form.controls.lineNoOfEachItemCode.patchValue(this.data);
    }
    
    if(this.data.pageflow == "Edit"){
      this.form.patchValue(this.data.code)
      this.form.controls.itemCode.patchValue(this.data.code.itemCode)
     this.dropdownList();
    }
    this.dropdownList();
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }


  back() {
    this.location.back();
  }


uomList: any[] = [];
partnerList: any[] = [];
dropdownList(){
  let obj: any = {};
  obj.companyCodeId = this.auth.companyId;
  obj.plantId = this.auth.plantId;
  obj.languageId = [this.auth.languageId];
  obj.warehouseId = this.auth.warehouseId;
  this.spin.show();
  this.uom.search(obj).subscribe((res: any[]) => {
    res.forEach(element => {
      this.uomList.push({value: element.uomId, label: element.uomId + '-' + element.description});
       });
    this.spin.hide();
  }, err => {
    this.cs.commonerrorNew(err);
    this.spin.hide();
  });

  
let obj1: any = {};
obj1.companyCodeId = [this.auth.companyId];
 obj1.plantId = [this.auth.plantId];
 obj1.languageId = [this.auth.languageId];
obj1.warehouseId = [this.auth.warehouseId];
this.spin.show();
this.business.searchSpark(obj1).subscribe((res: any[]) => {
  res.forEach(element => {
    this.partnerList.push({value: element.partnerCode, label: element.partnerCode + '-' + element.partnerName});
     });
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
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description, manufacturingName: x.manufacturerName, description: x.description,uomId:x.uomId }))
          }
        });
    }
  }
  showDropdown1: true;
  showDropdown(){
    this.showDropdown1 = true;
  }

  itemCodeChanged(e) {
    this.form.controls.manufacturerCode.patchValue(e.manufacturingName);
    this.form.controls.itemDescription.patchValue(e.description)
    let obj: any = {};
      obj.companyCodeId = [this.auth.companyId]
      obj.languageId = [this.auth.languageId];
      obj.warehouseId = [this.auth.warehouseId];
      obj.plantId=[this.auth.plantId]
      obj.itemCode = [e.value];
      this.itemService.search(obj).subscribe(res => {
        this.spin.hide();
        
       this.form.controls.uom.patchValue(res[0].uomId)
       
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
    this.form.controls.manufacturerName.patchValue(this.form.controls.manufacturerCode.value)
    this.dialogRef.close(this.form.getRawValue());
  }

}


