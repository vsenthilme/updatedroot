import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ReportsService } from 'src/app/main-module/reports/reports.service';

@Component({
  selector: 'app-proforma-popup',
  templateUrl: './proforma-popup.component.html',
  styleUrls: ['./proforma-popup.component.scss']
})
export class ProformaPopupComponent implements OnInit {

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
  pricePerChargeUnit: new FormControl(),
  priceUnit: new FormControl(),
  minMonthlyPrice:new FormControl(),
  statusId: new FormControl(1),
  currency:new FormControl(),
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
    this.masterService.searchcurrency({companyCodeId: [this.auth.companyId], plantId:[this.auth.plantId], warehouseId: [this.auth.warehouseId], languageId: [this.auth.languageId]}).subscribe(res => {
      this.currencyList = [];
      res.forEach(element => {
        this.currencyList.push({value: element.currencyId, label: element.currencyId + '-' + element.currencyDescription,currencyDescription:element.currencyDescription});
        
      });
    
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
  currencychange(value){
    console.log(value);
    const currency = this.currencyList.find(currency => currency.value === value);
  
    console.log(currency); 
 
    if (currency) {
       
        this.form.controls.currency.patchValue(currency.currencyDescription);
    } else {
        
        console.error('module not found');
    }

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
    
    obj.transactionQty=(this.form.controls.billQuantity.value),
    obj.chargeUnit=(this.form.controls.chargeUnit.value),
    obj.description=(this.form.controls.referenceField5.value),
   obj.serviceType=(this.form.controls.serviceType.value),
   obj.rateUnit=(this.form.controls.rateUnit.value),
   obj.totalValue=(this.form.controls.totalValue.value),
   obj.orderNo=(this.form.controls.referenceField2.value),
obj.module=(this.form.controls.referenceField1.value),
obj.sku=(this.form.controls.itemCode.value),
obj.currency=(this.form.controls.currency.value),
obj.description=(this.form.controls.referenceField5.value),
this.form.controls.referenceField8.patchValue(this.cs.dateMMYY(this.form.controls.referenceField8.value));
obj.transactionDate=(this.form.controls.referenceField8.value),
obj.chargeValue=(this.form.controls.referenceField6.value),
obj.cbmQty=(this.form.controls.referenceField7.value),
obj.customer=(this.data);
  // invoiceDate:this.cs.dateNewFormat(x.transactionDate),
  
    console.log(obj);
    if(obj != null){
    this.dialogRef.close(obj);
    }
    else{
      this.dialogRef.close();
    }
  }
}


