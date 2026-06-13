import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder ,FormControl,Validators} from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PreoutboundService } from '../../preoutbound.service';
import { forkJoin, of } from 'rxjs';
import { Location } from "@angular/common";
import { catchError } from 'rxjs/operators';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { UomService } from 'src/app/main-module/other-setup/uom/uom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.scss']
})
export class PopupComponent implements OnInit {

 
  constructor(private fb: FormBuilder,
    private auth: AuthService,
    public dialogRef: MatDialogRef<DialogExampleComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute, private router: Router,
    private cs: CommonService,
    private service : PreoutboundService,
    private location: Location,
    private cas: CommonApiService,
    private reportService: ReportsService,
    private uom: UomService,
    private business: BusinessPartnerService,
    private itemService:BasicdataService,
    ) { }


    form = this.fb.group({
      brand: [],
      countryOfOrigin: [],
      expectedQty: [],
      fromCompanyCode: [this.auth.companyId, Validators.required],
      lineReference: [],
      manufacturerCode: [, Validators.required],
      manufacturerName: [],
      middlewareHeaderId: [],
      middlewareId: [],
      middlewareTable: [],
      orderType: [],
      orderedQty: [, Validators.required],
      origin: [],
      packQty: [],
      sku: [, Validators.required],
      skuDescription: [, Validators.required],
      sourceBranchCode: [this.auth.plantId, Validators.required],
      storeID: [],
      supplierName: [],
      transferOrderNumber: [],
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
      this.form.patchValue(this.data);
      if(this.data== 1){
        this.form.controls.lineReference.patchValue(this.data);
        }
        
        if(this.data.pageflow == "Edit"){
          this.form.patchValue(this.data.code)
          this.form.controls.sku.patchValue(this.data.code.sku)
        
        }

      this.dropdownList();
    }
  
    onItemSelect(item: any) {
      console.log(item);
    }
  
    onSelectAll(items: any) {
      console.log(items);
    }
  
    inputChange(value) {
      this.form.controls.expectedDate.patchValue(this.cs.dateddMMYY(this.form.controls.expectedDate1.value))
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
  obj1.businessPartnerType=[1];
  this.spin.show();
  this.business.searchSpark(obj1).subscribe((res: any[]) => {
    res.forEach(element => {
      this.partnerList.push({value: element.partnerCode, label: element.partnerCode + '-' + element.partnerName});
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
              this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description, manufacturingName: x.manufacturerName, description: x.description,uomId:x.uomId }))
            }
          });
      }
    }
    showDropdown1: true;
  showDropdown(){
    this.showDropdown1 = true;
  }
  showDropdown2: true;
  showDropdown4(){
    this.showDropdown2 = true;
  }
    itemCodeChanged(e) {
      console.log(e)
      this.form.controls.manufacturerCode.patchValue(e.manufacturingName);
      this.form.controls.manufacturerName.patchValue(e.manufacturingName);
      this.form.controls.skuDescription.patchValue(e.description);
      this.form.controls.skuDescription.patchValue(e.description);
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
  
  
  