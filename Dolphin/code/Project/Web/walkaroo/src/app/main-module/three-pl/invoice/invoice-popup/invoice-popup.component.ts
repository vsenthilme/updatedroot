import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { MasterService } from 'src/app/shared/master.service';

@Component({
  selector: 'app-invoice-popup',
  templateUrl: './invoice-popup.component.html',
  styleUrls: ['./invoice-popup.component.scss']
})
export class InvoicePopupComponent implements OnInit {

  constructor(    private fb: FormBuilder, private auth: AuthService,  private masterService: MasterService, private toastr: ToastrService,
    public dialogRef: MatDialogRef<any>,private cs: CommonService,private reportService: ReportsService,
    @Inject(MAT_DIALOG_DATA) public data: any,) { }


  form = this.fb.group({
    languageId: new FormControl(this.auth.languageId),
  companyCodeId: new FormControl(this.auth.companyId),
  plantId: new FormControl(this.auth.plantId),
  warehouseId:new FormControl(this.auth.warehouseId),
  moduleId: new FormControl(),
  priceListId: new FormControl(),
  totalValue: new FormControl(),
  serviceTypeId: new FormControl(),
  chargeRangeId: new FormControl(),
  fromPeriod: new FormControl(),
  toPeriod: new FormControl(),
  chargeRangeFrom: new FormControl(),
  chargeRangeTo: new FormControl(),
  chargeUnit: new FormControl(),
  currency: new FormControl(),
  pricePerChargeUnit: new FormControl(),
  priceUnit: new FormControl(),
  minMonthlyPrice:new FormControl(),
  statusId: new FormControl(1),
  companyIdAndDescription: new FormControl(),
  plantIdAndDescription: new FormControl(),
  warehouseIdAndDescription: new FormControl(),
  moduleIdAndDescription: new FormControl(),
  serviceTypeIdAndDescription: new FormControl(),
  description: new FormControl(),
  deletionIndicator: new FormControl(),
  referenceField1: new FormControl(),
  referenceField2: new FormControl(),
  referenceField3:new FormControl(),
  referenceField4: new FormControl(),
  referenceField5: new FormControl(),
  referenceField6: new FormControl(),
  referenceField7: new FormControl(),
  itemCodeFE:new FormControl(),
itemCode:new FormControl(),
  referenceField8: new FormControl(),
  referenceField9: new FormControl(),
  referenceField10: new FormControl(),
  billQuantity:new FormControl(),
  createdBy: new FormControl(),
  createdOn: new FormControl(),
  rateUnit: new FormControl(),
  serviceType:new FormControl(),
  updatedBy: new FormControl(),
  updatedOn: new FormControl(),
  });
  moduleList:any[]=[];
  uomList:any[]=[];
  currencyList:any[]=[];
  ngOnInit(): void {
;
    this.masterService.searchserviceType({
      warehouseId: this.auth.warehouseId,
      companyCodeId: this.auth.companyId,
      plantId: this.auth.plantId,
      languageId: [this.auth.languageId],
    }).subscribe(res => {
      this.serviceTypeList = [];
      res.forEach(element => {
        this.serviceTypeList.push({
          value: element.serviceTypeId,
          label: element.serviceTypeId + '-' + element.serviceTypeDescription,
          serviceTypeDescription:element.serviceTypeDescription,

        });
      })
    });
    this.masterService.searchModule({companyCodeId: this.auth.companyId, plantId:this.auth.plantId, warehouseId: this.auth.warehouseId, languageId: [this.auth.languageId]}).subscribe(res => {
      this.moduleList = [];
      res.forEach(element => {
        if(element.moduleDescription != null){
          this.moduleList.push({value: element.moduleId, label: element.moduleId + '-' + element.moduleDescription,moduleDescription:element.moduleDescription});
        }
      });
      this.moduleList = this.cs.removeDuplicatesFromArrayNewstatus(this.moduleList);
    });
    this.masterService.searchuom({
      warehouseId: this.auth.warehouseId,
      companyCodeId: this.auth.companyId,
      plantId: this.auth.plantId,
      languageId: [this.auth.languageId],
    }).subscribe(res => {
      this.uomList = [];
      res.forEach(element => {
        this.uomList.push({
          value: element.uomId,
          label: element.uomId + '-' + element.description
        });
      })
    });
    this.masterService.searchcurrency({companyCodeId: [this.auth.companyId], plantId:[this.auth.plantId], warehouseId: [this.auth.warehouseId], languageId: [this.auth.languageId]}).subscribe(res => {
      this.currencyList = [];
      res.forEach(element => {
        this.currencyList.push({value: element.currencyId, label: element.currencyId + '-' + element.currencyDescription,currencyDescription:element.currencyDescription});
        
      });
    
    });
  }

  fill(){
   
    this.masterService.searchserviceType({
      warehouseId: this.auth.warehouseId,
      companyCodeId: this.auth.companyId,
      plantId: this.auth.plantId,
      languageId: [this.auth.languageId],
     
    }).subscribe(res => {
      this.serviceTypeList = [];
      res.forEach(element => {
        this.serviceTypeList.push({
          value: element.serviceTypeId,
          label: element.serviceTypeId + '-' + element.serviceTypeDescription,
          serviceTypeDescription:element.serviceTypeDescription,
        });
      })
  
      this.form.patchValue(this.data.code, { emitEvent: false });
      this.form.controls.chargeUnit.patchValue(this.data.code.chargeUnit != null ? Number(this.data.code.chargeUnit) : '');
    });
    
  }
  serviceTypeList:any[]=[];
  partnercodeList: any[] = [];
  onpartnertytpeChange(value) {
   console.log(value)
   const module = this.moduleList.find(module => module.value === value);
  
   console.log(module); 

   if (module) {
      
       this.form.controls.referenceField1.patchValue(module.moduleDescription);

   } else {
       
       console.error('module not found');
   }
    this.masterService.searchserviceType({
      warehouseId: this.auth.warehouseId,
      companyCodeId: this.auth.companyId,
      plantId: this.auth.plantId,
      languageId: [this.auth.languageId],
      moduleId: [value]
    }).subscribe(res => {
      this.serviceTypeList = [];
      res.forEach(element => {
        this.serviceTypeList.push({
          value: element.serviceTypeId,
          label: element.serviceTypeId + '-' + element.serviceTypeDescription,
          serviceTypeDescription:element.serviceTypeDescription,

        });
      })
    });
  }
  manufacturerCode:any;
  manufacturerName:any;
  onservicetypeChange(value){
    console.log(value);
    const serviceType = this.serviceTypeList.find(serviceType => serviceType.value === value);
  
    console.log(serviceType); 
 
    if (serviceType) {
       
        this.form.controls.serviceType.patchValue(serviceType.label);
    } else {
        
        console.error('module not found');
    }

  }

  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ itemList }) => {
          if (itemList != null && itemList.length > 0) {
            this.multiselectItemCodeList = [];
            this.itemCodeList = itemList;
            this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode + '/' + x.manufacturerName, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description,description:x.description,itemCode:x.itemCode })); //+x.manufacturerName
          }
        });
    }
  }
  
  itemCodeChanged(e){
    console.log(e);
    this.form.controls.itemCode.patchValue(e.itemCode);
    let selectedArray: any ;
    let SelectedArray2:any;
      console.log(selectedArray);
      console.log(SelectedArray2);
      SelectedArray2=e.description;
      this.form.controls.referenceField5.patchValue(SelectedArray2

      );
    
  }
  currencychange(value){
    console.log(value);
    const currency = this.currencyList.find(currency => currency.value === value);
  
    console.log(currency); 
 
    if (currency) {
       
        this.form.controls.currency.patchValue(currency.currencyDescription);
        console.log(this.form.controls.currency.value);
    } else {
        
        console.error('module not found');
    }

  }

  submit(){
    if (this.form.invalid) {
      this.toastr.error(
        "Please fill required fields to continue",
        "Notification", {
          timeOut: 2000,
          progressBar: false,
        }
      );

      return;
    }

    let obj: any = {};
    console.log(this.form.value)
    obj.billQuantity=(this.form.controls.billQuantity.value),
    obj.billUnit=(this.form.controls.chargeUnit.value),
    obj.referenceField5=(this.form.controls.referenceField5.value),
   obj.referenceField4=(this.form.controls.serviceType.value),
   obj.priceUnit=(this.form.controls.rateUnit.value),
   obj.billQuantity=(this.form.controls.totalValue.value),
   obj.referenceField2=(this.form.controls.referenceField2.value),
obj.referenceField1=(this.form.controls.referenceField1.value),
obj.referenceField3=(this.form.controls.itemCode.value),
obj.referenceField5=(this.form.controls.referenceField5.value),
obj.currency=(this.form.controls.currency.value),
this.form.controls.referenceField8.patchValue(this.cs.dateMMYY(this.form.controls.referenceField8.value));
obj.referenceField8=(this.form.controls.referenceField8.value),
obj.referenceField6=(this.form.controls.referenceField6.value),
obj.referenceField7=(this.form.controls.referenceField7.value),
obj.referenceField10=(this.form.controls.currency.value),
obj.languageId=this.auth.languageId,
obj.companyCodeId=this.auth.companyId,
obj.plantId=this.auth.plantId,
obj.warehouseId=this.auth.warehouseId,
obj.invoiceDate=new Date(),
obj.customer=(this.data);
  // invoiceDate:this.cs.dateNewFormat(x.transactionDate),
  
    console.log(obj);
    this.dialogRef.close(obj);
  }
}



