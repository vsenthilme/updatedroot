import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { Location } from "@angular/common";
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { PreoutboundService } from 'src/app/main-module/Outbound/preoutbound/preoutbound.service';
import { UomService } from 'src/app/main-module/other-setup/uom/uom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { MasterService } from 'src/app/shared/master.service';

@Component({
  selector: 'app-masteropoeration-lines',
  templateUrl: './masteropoeration-lines.component.html',
  styleUrls: ['./masteropoeration-lines.component.scss']
})
export class MasteropoerationLinesComponent implements OnInit {

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
    private itemService:BasicdataService,
    private masterService: MasterService
    ) { }


    form = this.fb.group({
      companyCodeId: [],
      companyDescription: [],
      createdBy: [],
      createdOn: [],
      deletionIndicator: [],
      languageId: [],
      leadTime: [],
      loadingTime: [],
      machineTime: [],
      operationDescription: [],
      operationNumber: [],
      phaseNumber: [],
      plantDescription: [],
      plantId: [],
      referenceField1: [],
      referenceField10: [],
      referenceField2: [],
      referenceField3: [],
      referenceField4: [],
      referenceField5: [],
      referenceField6: [],
      referenceField7: [],
      referenceField8: [],
      referenceField9: [],
      remarks: [],
      loadingTimeFE:[],
      setupTimeFE: [],
      machineTimeFE: [],
      setupTime: [],
      statusDescription: [],
      statusId: [],
      timeUnit: [],
      updatedBy: [],
      updatedOn: [],
      warehouseDescription: [],
      warehouseId: [],
      workCenterId: [],
      workCenterName: [],
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
    itemTypeList:any[]=[];
    workcenterList:any[]=[];
    ngOnInit(): void {
     
      let obj1: any = {};
   obj1.companyCodeId = this.auth.companyId;
    obj1.plantId = this.auth.plantId;
    obj1.languageId = [this.auth.languageId];
   obj1.warehouseId = this.auth.warehouseId;
   obj1.uomType=['TIME']
   this.spin.show();
   this.masterService.searchuom(obj1).subscribe((res: any[]) => {
     res.forEach(element => {
       this.itemTypeList.push({value: element.uomId, label: element.uomId + '-' + element.description,itemType:element.descriptionS

       });
        });
        this.spin.hide();
   })
   let obj2: any = {};
   obj2.companyCodeId = this.auth.companyId;
    obj2.plantId = this.auth.plantId;
    obj2.languageId = [this.auth.languageId];
   obj2.warehouseId = this.auth.warehouseId;
   this.spin.show();
   this.masterService.searchworkcenter(obj1).subscribe((res: any[]) => {
     res.forEach(element => {
       this.workcenterList.push({value: element.workCenterId, label: element.workCenterId + '-' + element.workCenterDescription,workCenterDescription:element.workCenterDescription

       });
        });
        this.spin.hide();
   })
      if(this.data.pageFlow!= 'Edit'){
       // this.form.controls.referenceField2.patchValue(this.data);
        }
       
        
        this.form.controls.phaseNumber.patchValue(this.data.toString());
        if(this.data.pageflow == "Edit"){
          this.form.patchValue(this.data.code)
         
        
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
  
    onservetypeChange(value){
      console.log(value);
      const serviceType = this.itemTypeList.find(serviceType => serviceType.value === value);
    
      console.log(serviceType); 
   
      if (serviceType) {
         
          this.form.controls.referenceField10.patchValue(serviceType.itemType);
      } else {
          
          console.error('module not found');
      }
  
    }
    onservetypeChange1(value){
      console.log(value);
      const serviceType = this.workcenterList.find(serviceType => serviceType.value === value);
    
      console.log(serviceType); 
   
      if (serviceType) {
         
          this.form.controls.workCenterName.patchValue(serviceType.workCenterDescription);
      } else {
          
          console.error('module not found');
      }
  
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
    
      this.form.controls.referenceField6.patchValue(e.description);
      let obj: any = {};
      obj.companyCodeId = [this.auth.companyId]
      obj.languageId = [this.auth.languageId];
      obj.warehouseId = [this.auth.warehouseId];
      obj.plantId=[this.auth.plantId]
      obj.itemCode = [e.value];
      this.itemService.search(obj).subscribe(res => {
        this.spin.hide();
        
       this.form.controls.referenceField7.patchValue(res[0].uomId)
       
      }, err => {
     
        this.cs.commonerrorNew(err);
        this.spin.hide();
     
      });
    }
    CalculateLeadtime(event){
    

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
    // if((this.form.controls.loadingTimeFE.value != null)||(this.form.controls.machineTimeFE.value != null)||(this.form.controls.setupTimeFE.value!= null) ){
    //   this.form.controls.loadingTime.patchValue(this.cs.timeFormatD(this.form.controls.loadingTimeFE.value));
    //   this.form.controls.setupTime.patchValue(this.cs.timeFormatD(this.form.controls.setupTimeFE.value));
    //   this.form.controls.machineTime.patchValue(this.cs.timeFormatD(this.form.controls.machineTimeFE.value));
    //   this.form.controls.leadTime.patchValue(this.cs.getTotalTime(this.form.controls.loadingTime.value,this.form.controls.setupTime.value,this.form.controls.machineTime.value))
    // }
    const Leadtime=Number(this.form.controls.setupTime.value)+Number(this.form.controls.loadingTime.value)+Number(this.form.controls.machineTime.value)
    this.form.controls.leadTime.patchValue(Leadtime)
      console.log(this.form.getRawValue())
      this.dialogRef.close(this.form.getRawValue());
    }
  
  }
  
  
  