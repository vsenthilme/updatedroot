import { Component, Inject, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators
} from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PricelistService } from '../pricelist.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { MasterService } from 'src/app/shared/master.service';
import { PricelistpopupComponent } from '../pricelistpopup/pricelistpopup.component';

@Component({
  selector: 'app-pricelist-new',
  templateUrl: './pricelist-new.component.html',
  styleUrls: ['./pricelist-new.component.scss']
})
export class PricelistNewComponent implements OnInit {

 
  warehouseidDropdown: any;
  isLinear = false;
  constructor(private fb: FormBuilder,
    public auth: AuthService,
    public dialog: MatDialog,
    public toastr: ToastrService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute, private router: Router,
    private cs: CommonService,
    private cas: CommonApiService,
    private service : PricelistService,
    private masterService: MasterService
    ) { }




  filterpartnercodeList: any[] = [];
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

  toggleFloat() {

    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;

  }
    
  disabled = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  panelOpenState = false;
  submitted = false;
  
  
  plantSelection:any;
  companySelection:any;
  description:any;
  js: any = {}
  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    this.js = this.cs.decrypt(code);
    this.service.search({companyCodeId: this.auth.companyId, plantId:this.auth.plantId, warehouseId:this.auth.warehouseId, languageId: [this.auth.languageId]}).subscribe(res => {
      console.log(res.length);
      if (res.length > 0) {
          const maxPriceListId = Math.max(...res.map(item => item.priceListId));
          this.pricelist = maxPriceListId + 1;
          this.form.controls.priceListId.patchValue(this.pricelist);
      } else {
          this.pricelist = 1;
          this.form.controls.priceListId.patchValue(this.pricelist);
      }
      
    });
   
    this.plantSelection=this.auth.plantId;
    this.companySelection=this.auth.companyId;
   
      this.dropdownlist();
   
      this.createForm();
       
       
    if (this.js.pageflow != 'New') {
     
      if (this.js.pageflow == 'Display')
        this.form.disable();
       this.fill();
    }
  }
  sub = new Subscription();
  warehouseSelection: any;
  languageSelection: any;
  createdOn: any;
  createdBy:any;
  updatedBy:any;
  updatedOn:any;
  createdOnFE:any;
  updatedOnFE:any;
  fill(){
    this.spin.show();
    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.plantId = this.auth.plantId;
  obj.languageId = [this.auth.languageId];
   obj.warehouseId = this.auth.warehouseId;
   obj.priceListId=[this.js.code];
    this.spin.show();
    this.sub.add(this.service.search(obj).subscribe((res: any[]) => {

   this.pricelist=res[0].priceListId;
   this.description=res[0].description;
      res.forEach((element, index) => {
        if (index != res.length - 1) {
          this.addNewRow()
        }
      
      });

      this.tableRowArray.patchValue(res);
      this.resultTable = res;
      console.log(this.resultTable);
console.log(this.tableRowArray.value)
this.warehouseSelection = res[0].warehouseId;
this.companySelection = res[0].companyCodeId;
this.plantSelection = res[0].plantId;
this.createdOn=res[0].createdOn;
this.createdBy=res[0].createdBy;
this.updatedOn=res[0].updatedOn;
this.languageSelection=res[0].languageId;
this.createdOnFE=this.cs.dateTimeApi(res[0].createdOn);
this.updatedOnFE=this.cs.dateTimeApi(res[0].updatedOn);
this.updatedBy=res[0].updatedBy;


      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
   this.dropdownlist();
    this.spin.hide();
  }
  openDialog(data: any = 'New', element: any = null, index: any = null): void {
    const dialogRef = this.dialog.open(PricelistpopupComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: {
        pageflow: data,
        code: data != 'New' ? element : null
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result.pageflow == 'New') {
        this.resultTable.push(result.data);
      }
      if (result.pageflow == 'Edit') {
        this.resultTable.splice(index, 1);
        this.resultTable.splice(2, 0, result.data);
      }
    });
  
  }
  form: FormGroup;
  pricelist:any;

  private createTableRow(): FormGroup {
    return this.fb.group({
      languageId: new FormControl(this.auth.languageId),
  companyCodeId: new FormControl(this.auth.companyId),
  plantId: new FormControl(this.auth.plantId),
  warehouseId:new FormControl(this.auth.warehouseId),
  moduleId: new FormControl(),
  priceListId: new FormControl(),
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
  }

  get tableRowArray(): FormArray {
    return this.form.get('tableRowArray') as FormArray;
  }

  onDeleteRow(rowIndex: number): void {
    this.tableRowArray.removeAt(rowIndex);
  }

  delete(i) {
    //this.resultTable = this.resultTable.filter(val => val.description !== i.description );
    this.resultTable.splice(i, 1);
    //(this.js.deletionIndicator)=1;
  }


  addNewRow(): void {
    this.tableRowArray.push(this.createTableRow());
  }





  createForm(): void {
    this.form = this.fb.group({
      tableRowArray: this.fb.array([
        this.createTableRow()
      ])
    })
  }

  search = true;
  resultTable:any[]=[];
  floorList: any[] = [];
  moduleList:any[]=[];
  paymenttermList: any[] = [];
  itemgroupList: any[] = [];
  warehouseIdList: any[] = [];
    partnercodeList: any[] = [];
    billingmodeList:any[]=[];
    billingfrequencyList:any[]=[];
    serviceTypeList:any[]=[];
    companyList: any[] = [];
    currencyList:any[]=[]
    plantList: any[]=[];;
    languageList: any[]=[];
    uomList:any[]=[];
    dropdownlist(){
      this.spin.show();
      this.cas.getalldropdownlist([
        this.cas.dropdownlist.setup.warehouseid.url,
        this.cas.dropdownlist.setup.floorid.url,

        this.cas.dropdownlist.setup.paymenttermid.url,
       this.cas.dropdownlist.setup.moduleid.url,
       this.cas.dropdownlist.setup.billingmodeid.url,
      this.cas.dropdownlist.setup.billingfrequencyid.url,
      this.cas.dropdownlist.setup.servicetypeid.url,
      this.cas.dropdownlist.setup.companyid.url,
      this.cas.dropdownlist.setup.plantid.url,
      this.cas.dropdownlist.setup.languageid.url,
    this.cas.dropdownlist.setup.currency.url,
  
      ]).subscribe((results) => {
        this.warehouseIdList=this.cas.forLanguageFilter(results[0],this.cas.dropdownlist.setup.warehouseid.key);
   this.floorList=this.cas.forLanguageFilter(results[1],this.cas.dropdownlist.setup.floorid.key);
   this.paymenttermList=this.cas.forLanguageFilter(results[2],this.cas.dropdownlist.setup.paymenttermid.key);
 //this.moduleList=this.cas.forLanguageFilter(results[3],this.cas.dropdownlist.setup.moduleid.key);
 this.billingmodeList=this.cas.forLanguageFilter(results[4],this.cas.dropdownlist.setup.billingmodeid.key);
 this.billingfrequencyList=this.cas.forLanguageFilter(results[5],this.cas.dropdownlist.setup.billingfrequencyid.key);
 this.serviceTypeList=this.cas.forLanguageFilter(results[6],this.cas.dropdownlist.setup.servicetypeid.key);
 this.companyList = this.cas.forLanguageFilter(results[7], this.cas.dropdownlist.setup.companyid.key);
 this.plantList = this.cas.forLanguageFilter(results[8], this.cas.dropdownlist.setup.plantid.key);
 this.languageList=this.cas.foreachlist2(results[9],this.cas.dropdownlist.setup.languageid.key);
 this.masterService.searchcurrency({companyCodeId: [this.auth.companyId], plantId:[this.auth.plantId], warehouseId: [this.auth.warehouseId], languageId: [this.auth.languageId]}).subscribe(res => {
  this.currencyList = [];
  res.forEach(element => {
    this.currencyList.push({value: element.currencyId, label: element.currencyId + '-' + element.currencyDescription});
    
  });

});
this.masterService.searchuom({companyCodeId: this.auth.companyId, plantId:this.auth.plantId, warehouseId: this.auth.warehouseId, languageId: [this.auth.languageId]}).subscribe(res => {
  this.uomList = [];
  res.forEach(element => {
    this.uomList.push({value: element.uomId, label: element.uomId + '-' + element.description});
  });

});
  
    this.masterService.searchModule({companyCodeId: this.auth.companyId, plantId:this.auth.plantId, warehouseId: this.auth.warehouseId, languageId: [this.auth.languageId]}).subscribe(res => {
      this.moduleList = [];
      res.forEach(element => {
        if(element.moduleDescription != null){
          this.moduleList.push({value: element.moduleId, label: element.moduleId + '-' + element.moduleDescription});
        }
      });
      this.moduleList = this.cs.removeDuplicatesFromArrayNewstatus(this.moduleList);
    });
   
    this.warehouseSelection = this.auth.warehouseId;
    this.languageSelection = this.auth.languageId; 
    this.plantSelection = this.auth.plantId;
    this.companySelection = this.auth.companyId;
      });
    
      this.spin.hide();
    }
  onserviceTypeChange(value){
    this.service.search({companyCodeId: this.form.controls.companyCodeId.value, plantId:this.form.controls.plantId.value, warehouseId:this.form.controls.warehouseId.value, languageId: [this.form.controls.languageId.value],moduleId:[this.form.controls.moduleId.value],serviceTypeId:[this.form.controls.serviceTypeId.value]}).subscribe(res => {
      console.log(res.length);
      if(res.length >0){
      this.pricelist=res.length +1;
      this.form.controls.priceListId.patchValue(this.pricelist);
      }else{
        this.pricelist=1;
        this.form.controls.priceListId.patchValue(this.pricelist);
      }
    });
  }
  deleteDialog(){
    console.log("Hello");
  }
     onWarehouseChange(value){
      console.log(value);
     this.form.controls.companyCodeId.patchValue(value.companyCodeId);
     this.form.controls.languageId.patchValue(value.languageId);
      this.form.controls.plantId.patchValue(value.plantId);
      
      
    
        this.form.controls.languageId.patchValue(this.auth.languageId);
        this.form.controls.plantId.patchValue(this.auth.plantId);
        this.form.controls.companyCodeId.patchValue(this.auth.companyId);
       
      
      this.masterService.searchModule({companyCodeId: this.form.controls.companyCodeId.value, plantId:this.form.controls.plantId.value, warehouseId: value.value, languageId: [this.form.controls.languageId.value]}).subscribe(res => {
        this.moduleList = [];
           res.forEach(element => {
        if(element.moduleDescription != null){
          this.moduleList.push({value: element.moduleId, label: element.moduleId + '-' + element.moduleDescription});
        }
      });
      this.moduleList = this.cs.removeDuplicatesFromArrayNewstatus(this.moduleList);
      });
      this.masterService.searchserviceType({companyCodeId: this.form.controls.companyCodeId.value, plantId:this.form.controls.plantId.value, warehouseId: value.value, languageId: [this.form.controls.languageId.value],moduleId:[this.form.controls.moduleId.value]}).subscribe(res => {
        this.moduleList = [];
        res.forEach(element => {
          if(element.moduleDescription != null){
          this.moduleList.push({value: element.serviceTypeId, label: element.serviceTypeId + '-' + element.serviceTypeDescription});
          }
        });
        this.moduleList = this.cs.removeDuplicatesFromArrayNewstatus(this.moduleList);
      });
  
     }
     onmoduleChange(value){
    
      this.masterService.searchserviceType({companyCodeId: this.form.controls.companyCodeId.value, plantId:this.form.controls.plantId.value, warehouseId: this.form.controls.warehouseId.value, languageId: [this.form.controls.languageId.value],moduleId:[value.value]}).subscribe(res => {
        this.serviceTypeList = [];
        res.forEach(element => {
          this.serviceTypeList.push({value: element.serviceTypeId, label: element.serviceTypeId + '-' + element.serviceTypeDescription});
        });
      });
     
  
     }
  
  submit() {
    if(this.pricelist == null){
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
    console.log(this.pricelist)
    this.resultTable.forEach(x => {
      x.priceListId = this.pricelist;
      x.description=this.description;
      x.id = x.id ? x.id : null;
    });
    console.log(this.resultTable)
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

     this.form.controls.tableRowArray.value.forEach(element => {
       element.warehouseId = this.auth.warehouseId;
       element.languageId = this.auth.languageId;
       element.companyCodeId = this.auth.companyId;
       element.plantId = this.auth.plantId;
       element.priceListId = this.pricelist;
        element.description=this.description;
     })

    this.resultTable.forEach(x => {
      x.priceListId = this.pricelist;
      x.description=this.description;
      x.id = x.id ? x.id : null;
    });
  this.cs.notifyOther(false);
  this.spin.show();

  if (this.js.code) {
    this.sub.add(this.service.UpdateList(this.resultTable).subscribe(res => {
      this.toastr.success(res[0].priceListId + " updated successfully!","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      this.router.navigate(['/main/threePLmaster/pricelist']);

      this.spin.hide();
  
    }, err => {
  
      this.cs.commonerrorNew(err);
      this.spin.hide();
  
    }));
  }else{
    this.sub.add(this.service.CreateList(this.resultTable).subscribe(res => {
      this.toastr.success(res[0].priceListId + " Saved Successfully!","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      this.router.navigate(['/main/threePLmaster/pricelist']);
      this.spin.hide();
  
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
  
    }));
   }
  
  }

}



