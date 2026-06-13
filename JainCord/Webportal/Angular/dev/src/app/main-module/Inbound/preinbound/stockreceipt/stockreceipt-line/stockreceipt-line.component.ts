import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { Location } from "@angular/common";
import { forkJoin, of } from 'rxjs';
import { PreinboundService } from '../../preinbound.service';
import { catchError } from 'rxjs/operators';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { UomService } from 'src/app/main-module/other-setup/uom/uom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';

@Component({
  selector: 'app-stockreceipt-line',
  templateUrl: './stockreceipt-line.component.html',
  styleUrls: ['./stockreceipt-line.component.scss']
})
export class StockreceiptLineComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private auth: AuthService,
    public dialogRef: MatDialogRef<StockreceiptLineComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute, private router: Router,
    private cs: CommonService,
    private service : PreinboundService,
    private itemService:BasicdataService,
    private location: Location,
    private uom: UomService,
    private business: BusinessPartnerService,
    private reportService: ReportsService,
    ) { }


  form = this.fb.group({
    branchCode: [[this.auth.plantId]],
  companyCode: [[this.auth.companyId]],
  expectedDate1: [ ],
  itemCode: [],
  itemDescription: [],
  lineNoForEachItem: [],
  manufacturerCode: [],
  manufacturerFullName: [],
  manufacturerShortName: [],
  receiptDate: [],
  receiptNo: [],
  receiptQty: [],
  supplierCode: [],
  supplierName: [],
  supplierPartNo: [],
  unitOfMeasure: [],

  });

 cancel(){
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
      this.form.controls.lineNoForEachItem.patchValue(this.data);
      }
      
      if(this.data.pageflow == "Edit"){
        this.form.patchValue(this.data.code)
        this.form.controls.itemCode.patchValue(this.data.code.itemCode)
      
      }
    this.form.controls.branchCode.patchValue(this.auth.plantId);
    this.form.controls.companyCode.patchValue(this.auth.companyId);
    this.form.controls.branchCode.disable();
    this.form.controls.companyCode.disable();
    this.dropdownList();
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
  obj1.businessPartnerType=[2];
  this.spin.show();
  this.business.searchSpark(obj1).subscribe((res: any[]) => {
    res.forEach(element => {
      this.partnerList.push({value: element.partnerCode, label: element.partnerCode + '-' + element.partnerName});
       });
  })
  }
  onItemSelect(item: any) {
    console.log(item);
  }
  showDropdown1: true;
  showDropdown(){
    this.showDropdown1 = true;
  }
  showDropdown2: true;
  showDropdown4(){
    this.showDropdown2 = true;
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

  itemCodeChanged(e) {
    this.form.controls.manufacturerCode.patchValue(e.manufacturingName);
   
  
    let obj: any = {};
      obj.companyCodeId = [this.auth.companyId]
      obj.languageId = [this.auth.languageId];
      obj.warehouseId = [this.auth.warehouseId];
      obj.plantId=[this.auth.plantId]
      obj.itemCode = [e.value];
      this.itemService.search(obj).subscribe(res => {
        this.spin.hide();
        
        this.form.controls.itemDescription.patchValue(res[0].description)
       this.form.controls.unitOfMeasure.patchValue(res[0].uomId)
       
      }, err => {
     
        this.cs.commonerrorNew(err);
        this.spin.hide();
     
      }); 
  }
  onSelectAll(items: any) {
    console.log(items);
  }

  // inputChange(value){
  //    this.form.controls.receiptDate.patchValue(this.cs.dateddMMYY(this.form.controls.receiptDate1.value))
  //  }
   back() {
    this.location.back();
  }

submit(){
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
  obj.data=this.form.value;
  this.form.controls.supplierCode.patchValue(this.form.controls.supplierCode.value)
  this.form.controls.receiptDate.patchValue(this.cs.day_callapi(this.form.controls.receiptDate.value))
  this.form.controls.manufacturerShortName.patchValue(this.form.controls.manufacturerCode.value)
  this.dialogRef.close(this.form.getRawValue());
}

}






