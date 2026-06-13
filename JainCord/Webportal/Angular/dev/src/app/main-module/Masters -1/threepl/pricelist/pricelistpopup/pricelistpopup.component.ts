import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { seriesType } from 'highcharts';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { MasterService } from 'src/app/shared/master.service';
import { BusinessPartnerService } from '../../../other-masters/business-partner/business-partner.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-pricelistpopup',
  templateUrl: './pricelistpopup.component.html',
  styleUrls: ['./pricelistpopup.component.scss']
})
export class PricelistpopupComponent implements OnInit {

  constructor(private fb: FormBuilder, private auth: AuthService, private masterService: MasterService, private toastr: ToastrService,
    public dialogRef: MatDialogRef<any>, private cs: CommonService, private reportService: ReportsService, private BusinessPartnerService: BusinessPartnerService, private spin: NgxSpinnerService,
    @Inject(MAT_DIALOG_DATA) public data: any, private datePipe: DatePipe) {
  }

  typeList: any[] = [
    { value: "CBM BASED", label: 'CBM BASED' },
    { value: "QUANTITY BASED", label: 'QUANTITY BASED' },
    { value: "WEIGHT BASED", label: 'WEIGHT BASED' },
    { value: "PALLET BASED", label: 'PALLET BASED' },
    { value: "SEASONAL BASED", label: 'SEASONAL BASED' },
    { value: "ITEM BASED", label: 'ITEM BASED' },
    { value: "CUSTOMER BASED", label: 'CUSTOMER BASED' }
  ];

  
  multiselectItemCodeList: any[] = [
    { value: 'Frosted Figs', label: 'Frosted Figs' },
    { value: 'Chilly Cashews', label: 'Chilly Cashews' },
    { value: 'Cool Harvest', label: 'Cool Harvest' },
    { value: 'IceNut Preserve', label: 'IceNut Preserve' },
    { value: 'FreshFreeze Figs', label: 'FreshFreeze Figs' },
    { value: 'NutriFreeze Cashews', label: 'NutriFreeze Cashews' },
    { value: 'Arctic Figs', label: 'Arctic Figs' },
    { value: 'CoolNut Treasures', label: 'CoolNut Treasures' },
    { value: 'FreezeFresh Figs', label: 'FreezeFresh Figs' },
    { value: 'Frosted Bites', label: 'Frosted Bites' },
    { value: 'PureFreeze Cashews', label: 'PureFreeze Cashews' },
    { value: 'Glacier Nuts', label: 'Glacier Nuts' },
    { value: 'ChillSnack Cashews', label: 'ChillSnack Cashews' },
    { value: 'Snowy Figs', label: 'Snowy Figs' },
    { value: 'NuttyChill', label: 'NuttyChill' },
    { value: 'Frosty Delights', label: 'Frosty Delights' },
    { value: 'IceBound Figs', label: 'IceBound Figs' },
    { value: 'Polar Nut Packs', label: 'Polar Nut Packs' },
    { value: 'Frozen Figs & Nuts', label: 'Frozen Figs & Nuts' },
    { value: 'ChillSafe Snacks', label: 'ChillSafe Snacks' }
  ];


  customerList: any[] = [
    { value: 'Arctic Storage', label: 'Arctic Storage' },
    { value: 'Polar Cold Logistics', label: 'Polar Cold Logistics' },
    { value: 'ChillPoint Solutions', label: 'ChillPoint Solutions' },
    { value: 'FrostGuard Inc.', label: 'FrostGuard Inc.' },
    { value: 'CoolFleet Services', label: 'CoolFleet Services' },
    { value: 'CryoWarehousing', label: 'CryoWarehousing' },
    { value: 'Glacier Cold Storage', label: 'Glacier Cold Storage' },
    { value: 'SnowPeak Logistics', label: 'SnowPeak Logistics' },
    { value: 'IceBox Enterprises', label: 'IceBox Enterprises' },
    { value: 'SubZero Storage', label: 'SubZero Storage' },
    { value: 'FreezeFlow Systems', label: 'FreezeFlow Systems' },
    { value: 'ChillMaster Storage', label: 'ChillMaster Storage' },
    { value: 'FrozenSphere Logistics', label: 'FrozenSphere Logistics' },
    { value: 'FrostLine Solutions', label: 'FrostLine Solutions' },
    { value: 'IcePoint Cold Storage', label: 'IcePoint Cold Storage' },
    { value: 'TempGuard Cold Storage', label: 'TempGuard Cold Storage' },
    { value: 'CryoFlex Logistics', label: 'CryoFlex Logistics' },
    { value: 'Permafrost Warehousing', label: 'Permafrost Warehousing' },
    { value: 'CoolReach Services', label: 'CoolReach Services' },
    { value: 'ArcticChill Storage', label: 'ArcticChill Storage' }
  ];

  form = this.fb.group({
    languageId: new FormControl(this.auth.languageId),
    companyCodeId: new FormControl(this.auth.companyId),
    plantId: new FormControl(this.auth.plantId),
    warehouseId: new FormControl(this.auth.warehouseId),
    moduleId: new FormControl('', [Validators.required]),
    priceListId: new FormControl(),
    serviceTypeId: new FormControl(''),
    chargeRangeId: new FormControl('', [Validators.required]),
    fromPeriod: new FormControl(),
    toPeriod: new FormControl(),
    chargeRangeFrom: new FormControl(),
    chargeRangeTo: new FormControl(),
    chargeRangeTo1: new FormControl(),
    chargeRangeFrom1: new FormControl(),
    chargeUnit: new FormControl(),
    pricePerChargeUnit: new FormControl(),
    priceUnit: new FormControl(),
    minMonthlyPrice: new FormControl(),
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
    referenceField3: new FormControl(),
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
  moduleList: any[] = [];
  uomList: any[] = [];
  currencyList: any[] = [];
  ngOnInit(): void {

    if (this.data.pageflow == 'Edit') {

      this.fill();
    }
    this.masterService.searchModule({ companyCodeId: this.auth.companyId, plantId: this.auth.plantId, warehouseId: this.auth.warehouseId, languageId: [this.auth.languageId] }).subscribe(res => {
      this.moduleList = [];
      res.forEach(element => {
        if (element.moduleDescription != null) {
          this.moduleList.push({ value: element.moduleId, label: element.moduleId + '-' + element.moduleDescription, moduleDescription: element.moduleDescription });
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
    this.masterService.searchcurrency({ companyCodeId: [this.auth.companyId], plantId: [this.auth.plantId], warehouseId: [this.auth.warehouseId], languageId: [this.auth.languageId] }).subscribe(res => {
      this.currencyList = [];
      res.forEach(element => {
        this.currencyList.push({ value: element.currencyId, label: element.currencyId + '-' + element.currencyDescription, currencyDescription: element.currencyDescription });

      });

    });
  }

  fill() {

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
          serviceTypeDescription: element.serviceTypeDescription,
        });
      })

      this.form.patchValue(this.data.code, { emitEvent: false });

      this.form.controls.chargeRangeFrom1.patchValue(this.convertToDate(this.form.controls.chargeRangeFrom.value));
            this.form.controls.chargeRangeTo1.patchValue(this.convertToDate(this.form.controls.chargeRangeTo.value));

      this.form.controls.chargeUnit.patchValue(this.data.code.chargeUnit != null ? Number(this.data.code.chargeUnit) : '');
    });

  }
  serviceTypeList: any[] = [];
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
          serviceTypeDescription: element.serviceTypeDescription,

        });
      })
    });
  }
  manufacturerCode: any;
  manufacturerName: any;
  onservetypeChange(value) {
    console.log(value);
    const serviceType = this.serviceTypeList.find(serviceType => serviceType.value === value);

    console.log(serviceType);

    if (serviceType) {

      this.form.controls.referenceField10.patchValue(serviceType.serviceTypeDescription);
    } else {

      console.error('module not found');
    }

  }
  currencychange(value) {
    console.log(value);
    const currency = this.currencyList.find(currency => currency.value === value);

    console.log(currency);

    if (currency) {

      this.form.controls.referenceField4.patchValue(currency.currencyDescription);
    } else {

      console.error('module not found');
    }

  }



  submit() {
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


  ontypeListChange(value) {
    this.form.controls.chargeRangeFrom.reset();
    this.form.controls.chargeRangeTo.reset();
  }

  itemCodeChange(){
    if (this.form.controls.referenceField1.value == 'CUSTOMER BASED' || this.form.controls.referenceField1.value == 'ITEM BASED') {
        this.form.controls.chargeRangeFrom.patchValue(this.form.controls.chargeRangeTo.value);
    }
  }

  dateChange(){
    if(this.form.controls.chargeRangeTo1.value){
      this.form.controls.chargeRangeTo.patchValue(this.datePipe.transform(this.form.controls.chargeRangeTo1.value, 'MMMM YYYY'))
    }
    if(this.form.controls.chargeRangeFrom1.value){
      this.form.controls.chargeRangeFrom.patchValue(this.datePipe.transform(this.form.controls.chargeRangeFrom1.value, 'MMMM YYYY'))
    }
  }

  convertToDate(monthYearString: string): Date {
    const [monthName, year] = monthYearString.split(' ');

    const monthIndex = new Date(Date.parse(monthName + " 1, 2022")).getMonth();

    return new Date(+year, monthIndex, 1);
  }
}

