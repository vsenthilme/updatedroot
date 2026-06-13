import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { InhouseTransferService } from '../../make&change/inhouse-transfer/inhouse-transfer.service';
import { UpdateInventoryComponent } from '../update-inventory/update-inventory.component';
import { ReportsService } from '../../reports/reports.service';
import { Basicdata1Service } from '../../Masters -1/masters/basic-data1/basicdata1.service';
import { PutawayService } from '../../Inbound/putaway/putaway.service';
import { BasicdataService } from '../../Masters -1/masternew/basicdata/basicdata.service';


@Component({
  selector: 'app-create-inventory',
  templateUrl: './create-inventory.component.html',
  styleUrls: ['./create-inventory.component.scss']
})
export class CreateInventoryComponent implements OnInit {
  multilevel:any[]=[];
  multibinclass:any[]=[];
  constructor(private ReportsService: ReportsService,private inventory: InhouseTransferService, private spin: NgxSpinnerService, private fb: FormBuilder, public cs: CommonService,   public auth: AuthService,
    private Basicdata1Service: BasicdataService, private toastr: ToastrService, private PutawayService : PutawayService,
    public dialogRef: MatDialogRef<UpdateInventoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    ) {
      this.multibinclass = [
        //  {value: 48, label: '48 - Picking'},
          {value: 1, label: 'LIVELOCATION'},
          {value: 2, label: 'RESERVE'},
          {value: 3, label: 'RECEVING STAGING'},
          {value:4,label:'QUATITY STAGING'},
          {value:5,label:'DELIVERY STAGING'},
          {value:6,label:'STOCKCOUNT INTERM'},
          {value:7,label:'RETURNBIN'},
      ],
      this.multilevel = [
        //  {value: 48, label: '48 - Picking'},
          {value: 1, label: '1'},
          {value: 2, label: '2'},
          {value: 3, label: '3'},
          {value:4,label:'4'},
          
      ];
     }
    form = this.fb.group({
      allocatedQuantity: [],
  barcodeId: [],
  batchSerialNumber: [],
  binClassId: [],
  brand: [],
  caseCode: [],
  cbm: [],
  cbmPerQuantity: [],
  cbmUnit: [],
  companyCodeId: [this.auth.companyId],
  companyDescription: [],
  createdBy: [],
  createdOn: [],
  deletionIndicator: [],
  description: [],
  expiryDate: [],
  inventoryId: [],
  inventoryQuantity: [],
  inventoryUom: [],
  itemCode: [],
  itemCodeFE:[],
  languageId: [this.auth.languageId],
  levelId: [],
  manufacturerCode: [],
  manufacturerDate: [],
  manufacturerPartNo:[],
  manufacturerName: [],
  origin: [],
  packBarcodes: ["99999"],
  palletCode: [],
  plantDescription: [],
  plantId: [this.auth.plantId],
  referenceDocumentNo: [],
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
  referenceOrderNo: [],
  screatedOn: [],
  specialStockIndicatorId: [1,],
  stockTypeDescription: ["On Hand"],
  stockTypeId: [1,],
  storageBin: [],
  storageMethod: [],
  storageSectionId:[],
  variantCode: [],
  variantSubCode: [],
  warehouseDescription: [],
  warehouseId: [this.auth.warehouseId],
    });
  
    ngOnInit(): void {
      this.form.controls.referenceField9.disable();
      this.form.controls.referenceField8.disable();
    }

multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  multiselectItemCodeList1: any[] = [];
itemCodeList1: any[] = [];
onItemType(searchKey) {
  let searchVal = searchKey?.filter;
  if (searchVal !== '' && searchVal !== null) {
    forkJoin({
      itemList: this.ReportsService.getItemCodeDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId).pipe(catchError(err => of(err))),
    })
      .subscribe(({ itemList }) => {
        if (itemList != null && itemList.length > 0) {
          this.multiselectItemCodeList = [];
          this.itemCodeList = itemList;
          this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode + '/' + x.manufacturerName, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description })); //+x.manufacturerName
        }
      });
  }
}
  multiselectStorageList: any[] = [];
  storageBinList1: any[] = [];
  selectedStorageBin: any[] = [];
  onStorageType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        storageList: this.ReportsService.getStorageDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ storageList }) => {
          if (storageList != null && storageList.length > 0) {
            console.log(3)
            this.multiselectStorageList = [];
            this.storageBinList1 = storageList;
            this.storageBinList1.forEach(x => this.multiselectStorageList.push({ value: x.storageBin, label: x.storageBin}))
          }
        });
    }
  }
  sub = new Subscription();
  showHiddenFields = false;
  showHiddenFields1 = false;
  itemCodeChanged(e){
    this.spin.show();
    console.log(e);
    let Item: any;
    let mfrname:any=[];
      let splittedValue = e.value.split('/')
      Item=(splittedValue[0])
      mfrname=(splittedValue[1])
   this.form.controls.itemCode.patchValue(Item);
  this.form.controls.manufacturerName.patchValue(mfrname);
  let warehouseDescription=this.auth.warehouseIdAndDescription.split('-');
  this.form.controls.warehouseDescription.patchValue(warehouseDescription[1]);
  let plantDescription=this.auth.plantIdAndDescription.split('-');
  this.form.controls.plantDescription.patchValue(plantDescription[1]);
  let companyDescription=this.auth.companyIdAndDescription.split('-');
  this.form.controls.companyDescription.patchValue(companyDescription[1]);
    this.multiselectStorageList = [];
    this.Basicdata1Service.search1({itemCode: [this.form.controls.itemCode.value], warehouseId: [this.auth.warehouseId],manufacturerPartNo:[this.form.controls.manufacturerName.value],companyCodeId:[this.auth.companyId],plantId:[this.auth.plantId],langaugeId:[this.auth.languageId]}).subscribe(res => {
      this.form.controls.referenceField9.patchValue(res[0].manufacturerPartNo);
      this.form.controls.referenceField8.patchValue(res[0].description);
      this.form.controls.description.patchValue(res[0].description);
      this.form.controls.manufacturerCode.patchValue(res[0].manufacturerPartNo);
      this.form.controls.manufacturerPartNo.patchValue(res[0].manufacturerPartNo);
      this.showHiddenFields = true;
     this.spin.hide();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    });
  }
  multiStorageSectionId: any[] = [];
  storageBinchange(e){
    this.spin.show();
    let obj: any = {};
    obj.companyCodeId = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.storageBin=[e.value];
    this.PutawayService.findStorageBinnew(obj).subscribe(res => {
     res.forEach( x => {
      this.multiStorageSectionId.push({
        value: x.storageSectionId,
        label: x.storageSectionId
      });
     this.multiStorageSectionId = this.cs.removeDuplicatesFromArrayNewstatus(this.multiStorageSectionId);
     if(this.multiStorageSectionId.length == 1){
      this.form.controls.referenceField10.patchValue(x.storageSectionId);
      this.form.controls.storageSectionId.patchValue(x.storageSectionId);
      this.form.controls.levelId.patchValue(x.levelId);
     }
     })
     this.showHiddenFields1 = true;
     this.spin.hide();
    }, err => {
      this.spin.hide();
      this.cs.commonerror(err)
    });
  }

  submit(){
    if ((this.form.invalid)) {
      this.spin.show();
      this.toastr.error("Please fill the required fields to continue", "", {
        timeOut: 2000,
        progressBar: false,
      });
      this.spin.hide();
      return;
    }
    this.spin.show();
  const sum=this.form.controls.inventoryQuantity.value +this.form.controls.allocatedQuantity.value;
  this.form.controls.referenceField4.patchValue(sum);
  this.form.controls.stockTypeDescription.patchValue("On Hand")
    this.inventory.createInventoryv2(this.form.getRawValue()).subscribe(res => {
      this.toastr.success("Inventory Saved Successfully!","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      this.spin.hide();
      this.dialogRef.close();
      }, err => {
        this.spin.hide();
        this.cs.commonerror(err)
      });
  }
}
