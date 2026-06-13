import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { seriesType } from 'highcharts';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';

@Component({
  selector: 'app-pricelistpopup',
  templateUrl: './pricelistpopup.component.html',
  styleUrls: ['./pricelistpopup.component.scss']
})
export class PricelistpopupComponent implements OnInit {

  constructor(    private fb: FormBuilder, private auth: AuthService,  private masterService: MasterService, private toastr: ToastrService,
    public dialogRef: MatDialogRef<any>,private cs: CommonService,
    @Inject(MAT_DIALOG_DATA) public data: any,) { }


  form = this.fb.group({
    languageId: new FormControl(this.auth.languageId),
  companyCodeId: new FormControl(this.auth.companyId),
  plantId: new FormControl(this.auth.plantId),
  warehouseId:new FormControl(this.auth.warehouseId),
  moduleId: new FormControl('', [Validators.required]),
  priceListId: new FormControl(),
  serviceTypeId: new FormControl('', [Validators.required]),
  chargeRangeId: new FormControl('', [Validators.required]),
  fromPeriod: new FormControl(),
  toPeriod: new FormControl(),
  chargeRangeFrom: new FormControl(),
  chargeRangeTo: new FormControl(),
  chargeUnit: new FormControl(),
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
  referenceField8: new FormControl(),
  referenceField9: new FormControl(),
  referenceField10: new FormControl(),
  createdBy: new FormControl(),
  createdOn: new FormControl(),
  updatedBy: new FormControl(),
  updatedOn: new FormControl(),
  });
  moduleList:any[]=[];
  uomList:any[]=[];
  currencyList:any[]=[];
  ngOnInit(): void {

    if(this.data.pageflow == 'Edit'){

      this.fill();
    }
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
      
       this.form.controls.referenceField9.patchValue(module.moduleDescription);
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
  onservetypeChange(value){
    console.log(value);
    const serviceType = this.serviceTypeList.find(serviceType => serviceType.value === value);
  
    console.log(serviceType); 
 
    if (serviceType) {
       
        this.form.controls.referenceField10.patchValue(serviceType.serviceTypeDescription);
    } else {
        
        console.error('module not found');
    }

  }
  currencychange(value){
    console.log(value);
    const currency = this.currencyList.find(currency => currency.value === value);
  
    console.log(currency); 
 
    if (currency) {
       
        this.form.controls.referenceField4.patchValue(currency.currencyDescription);
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
    obj.data = this.form.value;
    obj.pageflow = this.data.pageflow
    console.log(obj);
    this.dialogRef.close(obj);
  }
}

