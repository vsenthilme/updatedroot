import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, FormArray } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { MasterService } from 'src/app/shared/master.service';
import { BOMService } from '../../bom/bom.service';
import { MasterchildComponent } from '../../masterrecipe/masterrecipe-new/masterchild/masterchild.component';
import { MasteropoerationLinesComponent } from './masteropoeration-lines/masteropoeration-lines.component';

@Component({
  selector: 'app-masteroperation-new',
  templateUrl: './masteroperation-new.component.html',
  styleUrls: ['./masteroperation-new.component.scss']
})
export class MasteroperationNewComponent implements OnInit {
  operationDescription:any;
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
    private reportService: ReportsService,
    private service : BOMService,
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
  
  referenceField8:any;
  plantSelection:any;
  statusId:any;
  companySelection:any;
  description:any;
  itemTypeList:any[]=[];
  js: any = {}
  ngOnInit(): void {
     let code = this.route.snapshot.params.code;
     this.js = this.cs.decrypt(code);
 
    this.plantSelection=this.auth.plantId;
    this.companySelection=this.auth.companyId;
   this.parentItemQuantity=1;

      //this.dropdownlist();
   
      this.createForm();
       
       
    if (this.js.pageflow != 'New') {
     
      if (this.js.pageflow == 'Display')
        this.form.disable();
       this.fill();
    }
  }
  resultTable2:any;
  sub = new Subscription();
  fill(){
    this.spin.show();
    let obj: any = {};
    obj.companyCode= [this.auth.companyId];
    obj.plantId =[ this.auth.plantId];
  obj.languageId = [this.auth.languageId];
   obj.warehouseId = [this.auth.warehouseId];
   obj.operationNumber=[this.js.code];
    this.spin.show();
    this.sub.add(this.service.searchMasterOperation(obj).subscribe((res:any) => {
    
    this.resultTable=res;
   this.operationDescription=res[0].operationDescription;
   this.statusId = (res[0].statusId).toString();
   console.log(this.statusId)
      res.forEach((element, index) => {
        if (index != res.length - 1) {
          this.addNewRow()
        }
       
      });

      console.log(this.resultTable);
      this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
   //this.dropdownlist();
    this.spin.hide();
  }
  openDialog(data: any = 'New',rowIndex:any): void {
    const dialogRef = this.dialog.open(MasteropoerationLinesComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: {pageflow:data,code:this.resultTable[rowIndex]},
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.resultTable.splice(rowIndex,0);
        this.resultTable.splice(rowIndex, 1, result);
        
      //this.form.patchValue(result);
      this.resultTable = [...this.resultTable]
  
  }});
  
  }
  form: FormGroup;
  pricelist:any;
  parentItemCode:any;
  parentItemQuantity:any;
  private createTableRow(): FormGroup {
    return this.fb.group({
      companyCodeId: new FormControl(),
  companyDescription: new FormControl(),
  createdBy: new FormControl(),
  createdOn: new FormControl(),
  deletionIndicator: new FormControl(),
  languageId: new FormControl(),
  leadTime: new FormControl(),
  loadingTime: new FormControl(),
  machineTime: new FormControl(),
  operationDescription: new FormControl(),
  operationNumber: new FormControl(),
  phaseNumber: new FormControl(),
  plantDescription: new FormControl(),
  plantId: new FormControl(),
  referenceField1: new FormControl(),
  referenceField10: new FormControl(),
  referenceField2: new FormControl(),
  referenceField3: new FormControl(),
  referenceField4: new FormControl(),
  referenceField5: new FormControl(),
  referenceField6: new FormControl(),
  referenceField7: new FormControl(),
  referenceField8: new FormControl(),
  referenceField9: new FormControl(),
  remarks: new FormControl(),
  setupTime: new FormControl(),
  statusDescription: new FormControl(),
  statusId: new FormControl(),
  timeUnit: new FormControl(),
  updatedBy: new FormControl(),
  updatedOn: new FormControl(),
  warehouseDescription: new FormControl(),
  warehouseId: new FormControl(),
  workCenterId: new FormControl(),
  workCenterName: new FormControl(),
    });
  }

  get tableRowArray(): FormArray {
    return this.form.get('tableRowArray') as FormArray;
  }

  
  add() {
    const dialogRef = this.dialog.open(MasteropoerationLinesComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      data: this.resultTable.length + 1
    });

    dialogRef.afterClosed().subscribe(result => {
     console.log(result.length);
     if(result){
      this.resultTable
     
      if (result.length > 0) {
        this.resultTable.push(result);
      }

      this.resultTable.push(result);
    }
     this.resultTable=[...this.resultTable];
    });
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
//     dropdownlist(){
//       this.spin.show();
//       this.cas.getalldropdownlist([
//         this.cas.dropdownlist.setup.warehouseid.url,
//         this.cas.dropdownlist.setup.floorid.url,

//         this.cas.dropdownlist.setup.paymenttermid.url,
//        this.cas.dropdownlist.setup.moduleid.url,
//        this.cas.dropdownlist.setup.billingmodeid.url,
//       this.cas.dropdownlist.setup.billingfrequencyid.url,
//       this.cas.dropdownlist.setup.servicetypeid.url,
//       this.cas.dropdownlist.setup.companyid.url,
//       this.cas.dropdownlist.setup.plantid.url,
//       this.cas.dropdownlist.setup.languageid.url,
//     this.cas.dropdownlist.setup.currency.url,
  
//       ]).subscribe((results) => {
//         this.warehouseIdList=this.cas.forLanguageFilter(results[0],this.cas.dropdownlist.setup.warehouseid.key);
//    this.floorList=this.cas.forLanguageFilter(results[1],this.cas.dropdownlist.setup.floorid.key);
//    this.paymenttermList=this.cas.forLanguageFilter(results[2],this.cas.dropdownlist.setup.paymenttermid.key);
//  //this.moduleList=this.cas.forLanguageFilter(results[3],this.cas.dropdownlist.setup.moduleid.key);
//  this.billingmodeList=this.cas.forLanguageFilter(results[4],this.cas.dropdownlist.setup.billingmodeid.key);
//  this.billingfrequencyList=this.cas.forLanguageFilter(results[5],this.cas.dropdownlist.setup.billingfrequencyid.key);
//  this.serviceTypeList=this.cas.forLanguageFilter(results[6],this.cas.dropdownlist.setup.servicetypeid.key);
//  this.companyList = this.cas.forLanguageFilter(results[7], this.cas.dropdownlist.setup.companyid.key);
//  this.plantList = this.cas.forLanguageFilter(results[8], this.cas.dropdownlist.setup.plantid.key);
//  this.languageList=this.cas.foreachlist2(results[9],this.cas.dropdownlist.setup.languageid.key);
//  this.masterService.searchcurrency({companyCodeId: [this.auth.companyId], plantId:[this.auth.plantId], warehouseId: [this.auth.warehouseId], languageId: [this.auth.languageId]}).subscribe(res => {
//   this.currencyList = [];
//   res.forEach(element => {
//     this.currencyList.push({value: element.currencyId, label: element.currencyId + '-' + element.currencyDescription});
    
//   });

// });
// this.masterService.searchuom({companyCodeId: this.auth.companyId, plantId:this.auth.plantId, warehouseId: this.auth.warehouseId, languageId: [this.auth.languageId]}).subscribe(res => {
//   this.uomList = [];
//   res.forEach(element => {
//     this.uomList.push({value: element.uomId, label: element.uomId + '-' + element.description});
//   });

// });
  
//     this.masterService.searchModule({companyCodeId: this.auth.companyId, plantId:this.auth.plantId, warehouseId: this.auth.warehouseId, languageId: [this.auth.languageId]}).subscribe(res => {
//       this.moduleList = [];
//       res.forEach(element => {
//         if(element.moduleDescription != null){
//           this.moduleList.push({value: element.moduleId, label: element.moduleId + '-' + element.moduleDescription});
//         }
//       });
//       this.moduleList = this.cs.removeDuplicatesFromArrayNewstatus(this.moduleList);
//     });
  
//       });
    
//       this.spin.hide();
//     }
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
     itemCode:any;
  submit() {
    if(this.resultTable == null){
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

  
  this.cs.notifyOther(false);
  this.spin.show();

  if (this.js.code) {
      this.resultTable.forEach(x => {
        x.operationDescription = this.operationDescription;
        x.operationNumber=this.js.code;
        x.statusId=1;
        x.companyCodeId=this.auth.companyId;
        x.plantId=this.auth.plantId;
        x.warehouseId=this.auth.warehouseId;
        x.languageId=this.auth.languageId;
        x.phaseDescription=x.referenceField1;
    
      });
     this.sub.add(this.service.UpdateMasterOperation(this.resultTable,this.auth.warehouseId,this.auth.plantId,this.auth.companyId,this.auth.languageId).subscribe(res => {
      this.toastr.success(res[0].operationNumber + " updated successfully!","Notification",{
       timeOut: 2000,
        progressBar: false,
     });
       this.router.navigate(['/main/other-masters/masteroperation']);

       this.spin.hide();
  
     }, err => {
  
       this.cs.commonerrorNew(err);
       this.spin.hide();
  
     }));
  }
  else{
    this.resultTable.forEach(x => {
      x.operationDescription = this.operationDescription;
      x.statusId=1;
      x.companyCodeId=this.auth.companyId;
      x.plantId=this.auth.plantId;
      x.warehouseId=this.auth.warehouseId;
      x.languageId=this.auth.languageId
  
    });
    console.log(this.resultTable)
    let obj: any;
   obj = this.resultTable;
   this.resultTable.forEach(x => {
    x.operationDescription = this.operationDescription;
    x.operationNumber=this.js.code;
    x.statusId=1;
    x.companyCodeId=this.auth.companyId;
    x.plantId=this.auth.plantId;
    x.warehouseId=this.auth.warehouseId;
    x.languageId=this.auth.languageId
   x.phaseDescription=x.referenceField1;
  });
    this.sub.add(this.service.CreateMasterOperations(obj).subscribe(res => {
      this.toastr.success(res[0].operationNumber + " Saved Successfully!","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      this.router.navigate(['/main/other-masters/masteroperation']);
      this.spin.hide();
  
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
  
    }));
   }
  
  }

}




