import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Location } from "@angular/common";
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { PutawayAddLinesComponent } from '../../Goods-receipt/putawaycreation-create/putaway-add-lines/putaway-add-lines.component';
import { PutawayDetailsComponent } from '../../Goods-receipt/putawaycreation-create/putaway-details/putaway-details.component';
import { QualityService } from '../quality.service';
import { QualityPopupComponent } from '../quality-popup/quality-popup.component';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';

@Component({
  selector: 'app-quality-line',
  templateUrl: './quality-line.component.html',
  styleUrls: ['./quality-line.component.scss']
})
export class QualityLineComponent implements OnInit {

  screenid = 3052;
  qualityLine: any[] = [];
  selectedQualityLine: any[] = [];
  @ViewChild('qualityLineTag') qualityLineTag: Table | any;
  statusIdList: any[] = [];
  filterstoragesectionList: any[] = [];

  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
  toggleFloat() {
    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more'
    }
    this.showFloatingButtons = !this.showFloatingButtons;
    ;
  }

  email = new FormControl('', [Validators.required, Validators.email, Validators.pattern("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}")]);
  form = this.fb.group({
    analysis: [],
    batchSerialNumber: [],
    companyCodeId: [],
    companyDescription: [],
    createdBy: [],
    createdOn: [],
    deletionIndicator: [],
    impurities: [],
    inboundQualityNumber: [],
    itemCode: [],
    itemDescription: [],
    languageId: [],
    lineNo: [],
    plantDescription: [],
    plantId: [],
    preInboundNo: [],
    receivedQuantity: [],
    refDocNumber: [],
    referenceField1: [],
    referenceField2: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    referenceField10: [],
    remarks: [],
    sampleQuantity: [],
    statusDescription: [],
    statusId: [],
    storageSectionId: [],
    updatedBy: [],
    updatedOn: [],
    warehouseDescription: [],
    warehouseId: [],
  });

  isShown: boolean = false; // hidden by default
  toggleShow() { this.isShown = !this.isShown; }
  animal: string | undefined;
  name: string | undefined;

  constructor(private fb: FormBuilder,
    private auth: AuthService,
    private qualityService: QualityService,
    private location: Location,
    public toastr: ToastrService,
    private dialog: MatDialog,
    private basicDataService: BasicdataService,
    private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private cs: CommonService,
    private reportService: ReportsService) {
    this.statusIdList = [
      { value: '94', label: 'Approved' },
      { value: '95', label: 'Rejected' },
      { value: '96', label: 'Conditionally Approved' }
    ];
  }

  sub = new Subscription();

  subscreen(): void {
    const dialogRef = this.dialog.open(PutawayDetailsComponent, {
      disableClose: true,
      width: '100%',
      maxWidth: '90%',
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.animal = result;
    });
  }

  isbtntext = true;
  code: any;

  ngOnInit(): void {
    this.form.disable();
    this.dropdownlist();
    let code = this.route.snapshot.params.code;
    if (code != 'new') {
      let js = this.cs.decrypt(code);
      this.fill(js);
      this.code = js.code;
    }

  }
  qualityLineclosed1:any[]=[];
  assignQuality=false;
  assigninvoice() {
    const dialogRef = this.dialog.open(QualityPopupComponent, {
      // width: '60%', height: '70%',
      width: '70%',
      maxWidth: '80%',
      position: {
        top: '6.7%',
      },

      data: { title: "Quality", body: "Invoice Number" },
    });
    dialogRef.afterClosed().subscribe(result => {
      
      if (result != null) {
        for(let i=0;i<this.selectedQualityLine.length;i++){
       this.selectedQualityLine[i].impurities = result.impurities;
       this.selectedQualityLine[i].analysis=result.analysis;
       this.selectedQualityLine[i].statusId=result.statusId;
       this.selectedQualityLine[i].storageSectionId=result.storageSectionId;
       this.selectedQualityLine[i].sampleQuantity=Number(result.sampleQuantity);
       this.assignQuality=true;
        }
      }
    });
  
  }
  StorageBinList: any[] = [];
  btntext = "Save";
  pageflow = "New";
  elementdata: any;
  selectedItems: any[] = [];
  multiSelectbinList: any[] = [];
  multibinList: any[] = [];

  dropdownSettings: IDropdownSettings = {
    singleSelection: true,
    idField: 'item_id',
    textField: 'item_text',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };

  fill(data: any) {
    if (data.pageflow != 'New') {
      this.pageflow = data.pageflow;
      this.btntext = 'Update';
      this.form.controls.preInboundNo.disable();
      this.form.controls.warehouseId.disable();
      console.log(data.code)
      this.form.patchValue(data.code, { emitEvent: false });
      this.form.controls.statusId.patchValue(this.form.controls.statusDescription.value)

      this.spin.show();
      if (data.pageflow == 'Display') {
        this.form.disable();
        this.isbtntext = false;

        this.sub.add(this.qualityService.searchHeader({ refDocNumber: [data.code.refDocNumber],preInboundNo:[data.code.preInboundNo], }).subscribe(res => {
          this.elementdata = JSON.parse(JSON.stringify(res));
          this.spin.hide();
          this.qualityLine = res;
          
          console.log(this.qualityLine);
        }, err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }));
      }
      else {
        this.sub.add(this.qualityService.searchHeader({ refDocNumber: [data.code.refDocNumber],preInboundNo:[data.code.preInboundNo] }).subscribe(res => {
          ;

          this.spin.hide();
          this.qualityLine = res.filter(x => x.statusId == 17);
          this.selectedQualityLine=this.qualityLine;
         this.qualityLineclosed1 = res.filter(x => x.statusId != 17);
          this.sub.add(this.qualityService.searchQualityLines({ refDocNumber: [res[0].refDocNumber],preInboundNo:[res[0].preInboundNo] }).subscribe(res => {
            ;
  
            this.spin.hide();
          
            this.qualityLineclosed = res.filter(x => x.statusId );

          }, err => {
            this.cs.commonerrorNew(err);
            this.spin.hide();
          }));
        }, err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }));
      }
    }
  }
  qualityLineclosed:any[]=[];
qualityLine2:any[]=[];
  submit() {
    console.log(this.assignQuality)
    console.log(this.selectedQualityLine)
    if(this.assignQuality === false){
      let sampleQtyNull = false;
      let statusNull = false;
      let storageSecgtionNull = false;
      let checkEqual = false;
      this.qualityLine=this.selectedQualityLine;
      this.qualityLine.forEach((x: any) => {
        if (x.sampleQuantity == null) sampleQtyNull = true;
        if (x.statusId == null) statusNull = true;
        if (x.storageSectionId == null) storageSecgtionNull = true;
      });
  
      if (sampleQtyNull) {
        this.toastr.error(
          "Please fill Sample Qty to continue",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        })
        return;
      }
      if (statusNull) {
        this.toastr.error(
          "Please fill Status ID to continue",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        })
        return;
      }
      if (storageSecgtionNull) {
        this.toastr.error(
          "Please fill Zone to continue",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        })
        return;
      }
  
      if (checkEqual) {
        this.toastr.error(
          "Sample Qty must be the same as Received Qty",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        })
        return;
      }
  
      this.qualityLine.forEach((x: any) => {
        //x.inboundQualityNumber = this.form.controls.inboundQualityNumber.value;
        x.companyCodeId = this.auth.companyId;
       x.lineNo=Number(x.referenceField9);
      });
  
      this.spin.show();
      this.sub.add(this.qualityService.createLines(this.qualityLine).subscribe(res => {
        this.toastr.success(res.refDocNumber + " Confirmed", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.spin.hide();
        this.location.back();
  
      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
    }
   
    if(this.assignQuality == true){
    let sampleQtyNull = false;
    let statusNull = false;
    let storageSecgtionNull = false;
    let checkEqual = false;
this.qualityLine2=[];

this.qualityLine2=this.selectedQualityLine;
    this.qualityLine2.forEach((x: any) => {
      if (x.sampleQuantity == null) sampleQtyNull = true;
      if (x.statusId == null) statusNull = true;
      if (x.storageSectionId == null) storageSecgtionNull = true;
    });
    console.log(this.selectedQualityLine)
    if (sampleQtyNull) {
      this.toastr.error(
        "Please fill Sample Qty to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      })
      return;
    }
    if (statusNull) {
      this.toastr.error(
        "Please fill Status ID to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      })
      return;
    }
    if (storageSecgtionNull) {
      this.toastr.error(
        "Please fill Zone to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      })
      return;
    }

    if (checkEqual) {
      this.toastr.error(
        "Sample Qty must be the same as Received Qty",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      })
      return;
    }

    this.qualityLine2.forEach((x: any) => {
      x.inboundQualityNumber = this.form.controls.inboundQualityNumber.value;
      x.companyCodeId = this.auth.companyId;
     x.lineNo=Number(x.referenceField9);
    });

    this.spin.show();
    this.sub.add(this.qualityService.createLines(this.qualityLine2).subscribe(res => {
      this.toastr.success(this.code.itemCode + " Confirmed", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      this.spin.hide();
      this.location.back();

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  }

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

  multiselectStorageList: any[] = [];
  storageBinList1: any[] = [];
  selectedStorageBin: any[] = [];
  onStorageType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        storageList: this.reportService.getStorageDropDown2(searchVal.trim(), this.auth.companyId,
          this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ storageList }) => {
          if (storageList != null && storageList.length > 0) {
            this.multiselectStorageList = [];
            this.storageBinList1 = storageList;
            this.storageBinList1.forEach(x => this.multiselectStorageList.push({ value: x.storageBin, label: x.storageBin }))
          }
        }
        );
    }
  }

  onDeleteRow(rowIndex: any): void {
    this.qualityLine.splice(rowIndex, 1);
    this.qualityLine = [...this.qualityLine];

  }

  showDropdown1: true;
  showDropdown() {
    this.showDropdown1 = true;
  }

  openDialog(data: any, rowIndex): void {
    const dialogRef = this.dialog.open(PutawayAddLinesComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      data: this.qualityLine[rowIndex],
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (this.pageflow == 'New') {
          this.qualityLine.push(result);
        }
        if (result != null) {
          if (this.pageflow == 'Edit') {
            if (result.length == 1) {
              this.qualityLine.splice(rowIndex, 0);
              this.qualityLine.splice(0, 1, result);
              this.form.patchValue(result);
              this.qualityLine = [...this.qualityLine]
            }
            if (result.length != 1) {
              this.qualityLine.splice(rowIndex, 0);
              this.qualityLine.splice(rowIndex, 1, result);
              
              this.form.patchValue(result);
              this.qualityLine = [...this.qualityLine]
            }
          }
          this.form.patchValue(this.qualityLine);
        }
      }
    });
  }

  storagesectionList: any = [];
  multistoragelist: any[] = [];
  multiSelectstorageList: any[] = [];

  dropdownlist() {
    this.spin.show();
    let obj: any = {};
    obj.warehouseId = [this.auth.warehouseId];
    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];
    this.sub.add(this.basicDataService.searchStorage(obj).subscribe(res => {
      res.forEach(x => this.multistoragelist.push({ value: x.storageSectionId, label: x.storageSection }))
      this.multiSelectstorageList = this.multistoragelist;
      this.multiSelectstorageList = this.cs.removeDuplicatesFromArrayNewstatus(this.multiSelectstorageList)
      this.spin.hide();
    },
      err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    ));
  }

}
