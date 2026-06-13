import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { InterwarehouselinesComponent } from '../../interwarehousecreate/interwarehouselines/interwarehouselines.component';
import { PreoutboundService } from '../../preoutbound.service';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { UomService } from 'src/app/main-module/other-setup/uom/uom.service';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';

@Component({
  selector: 'app-salesorderlines',
  templateUrl: './salesorderlines.component.html',
  styleUrls: ['./salesorderlines.component.scss']
})
export class SalesorderlinesComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private auth: AuthService,
    public dialogRef: MatDialogRef<InterwarehouselinesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private reportService: ReportsService,
    private route: ActivatedRoute, private router: Router,
    private cs: CommonService,
    private uom: UomService,
    private itemService:BasicdataService,
    private service : PreoutboundService,
    ) { }


  form = this.fb.group({
    brand: [],
  lineReference: [],
  manufacturerCode: [],
  manufacturerName: [],
  orderedQty: [],
  sku: [],
  skuDescription: [],
  uom: [],
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
  uomList:any[]=[];
  ngOnInit(): void {
    
    if(typeof(this.data) =="object"){
      this.form.patchValue(this.data.code);
      this.form.controls.sku.patchValue(this.data.code.sku);
    }
    else{
      this.form.controls.lineReference.patchValue(this.data);
    }
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
  }
  showDropdown1: true;
  showDropdown(){
    this.showDropdown1 = true;
  }
  showDropdown2: true;
  showDropdown4(){
    this.showDropdown2 = true;
  }

  onItemSelect(item: any) {
    console.log(item);
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
          if(this.data.pageflow == 'Edit'){
            this.form.controls.sku.patchValue(this.data.code.sku)
          }
        });
    }
  }

  itemCodeChanged(e) {
    this.form.controls.manufacturerCode.patchValue(e.manufacturingName);
    this.form.controls.manufacturerName.patchValue(e.manufacturingName);

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

  onSelectAll(items: any) {
    console.log(items);
  }

  inputChange(value){
    console.log((this.form.controls.expectedDate.patchValue(this.cs.dateddMMYY(this.form.controls.expectedDate1.value))))
 
     this.form.controls.expectedDate.patchValue(this.cs.dateddMMYY(this.form.controls.expectedDate1.value))
   }
submit(){
  
  let obj: any = {};
  console.log(this.form.value);
  obj.data=this.form.value;
  console.log(obj.data)
  this.dialogRef.close(this.form.getRawValue());
  console.log(this.form.getRawValue());
}
cancel(){
  this.dialogRef.close();
}
}




