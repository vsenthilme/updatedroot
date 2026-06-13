import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { catchError } from 'rxjs/operators';
import { Subscription, forkJoin, of } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from '../../reports/reports.service';
import { InhouseTransferService } from '../../make&change/inhouse-transfer/inhouse-transfer.service';

@Component({
  selector: 'app-update-inventory',
  templateUrl: './update-inventory.component.html',
  styleUrls: ['./update-inventory.component.scss']
})
export class UpdateInventoryComponent implements OnInit {
  itemCode: any;
  barcodeId:any;
  itemCodeFE:any;
  binClassId:any;
  packBarcodes: any;
  specialStockIndicatorId: any;
  stockTypeId: any;
  storageBin: any;
  inventoryUom: any;
  allocatedQuantity: number;
  inventoryQuantity: number;
  description: any;
  mfrPartNo: any;
  showHiddenFields: boolean;
  showHiddenFields1: boolean;
  storageSection: any;
  multilevel:any[]=[];
  multibinclass:any[]=[];
  constructor(private ReportsService: ReportsService,private inventory: InhouseTransferService, private spin: NgxSpinnerService, public cs: CommonService,   public auth: AuthService,
    public toastr: ToastrService,
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
      ];
      this.multilevel = [
        //  {value: 48, label: '48 - Picking'},
          {value: 1, label: '1'},
          {value: 2, label: '2'},
          {value: 3, label: '3'},
          {value:4,label:'4'},
          
      ];
     }
  
  sub = new Subscription();

  ngOnInit(): void {
    this.showHiddenFields = false;
    this.showHiddenFields1 = false;
    //this.dropdownfill();
  }
  
multiselectItemCodeList: any[] = [];
itemCodeList: any[] = [];
multiselectItemCodeList1: any[] = [];
itemCodeList1: any[] = [];
  multiPackBarcodes: any[] = [];
  multispecialStockIndicatorId: any[] = [];
  multistockTypeId: any[] = [];
  multistorageBin: any[] = [];
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
  level:any
  itemCodeChanged(e){
  this.spin.show();
  this.multiPackBarcodes = [];
  this.multispecialStockIndicatorId = [];
  this.multistockTypeId = [];
  this.multistorageBin = [];
  console.log(e);
  let selectedArray: any ;
  let SelectedArray2:any;
    let splittedValue = e.value.split('/')
    selectedArray=(splittedValue[0])
    SelectedArray2=(splittedValue[1])
 this.itemCode=(selectedArray);
this.mfrPartNo=(SelectedArray2);
 
let obj: any = {};
obj.warehouseId = [this.auth.warehouseId];
obj.companyCodeId = [this.auth.companyId];
obj.languageId = [this.auth.languageId];
obj.plantId = [this.auth.plantId];
obj.itemCode= [this.itemCode];
obj.manufacturerName=[this.mfrPartNo];
  
  this.sub.add(this.inventory.GetInventoryv2(obj).subscribe(res => {

    if(res.length == 0){
      this.toastr.error(
        "No Part No found",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      )
      this.spin.hide();
      return;
    }

    if(res.length > 0){
    this.showHiddenFields = true;
    }
        if(res.length ==1){
          this.showHiddenFields =true;
          this.showHiddenFields1 =true;
        }

      res.forEach((element: {packBarcodes: string, specialStockIndicatorId: string, stockTypeId:string, storageBin:string,levelId:string}) => {
      if(res[0].referenceField9){this.mfrPartNo = res[0].referenceField9;}
      if(res[0].referenceField8){this.description = res[0].referenceField8;} 
      if(res[0].barcodeId){this.barcodeId = res[0].barcodeId;} 
      if(res[0].specialStockIndicatorId){this.specialStockIndicatorId = res[0].specialStockIndicatorId;} 
      if(res[0].stockTypeId){this.stockTypeId = res[0].stockTypeId;} 
      if(res[0].storageBin){this.storageBin = res[0].storageBin;} 
      if(res[0].inventoryUom){this.inventoryUom = res[0].inventoryUom;} 
      if(res[0].binClassId){this.binClassId = res[0].binClassId;} 
      if(res[0].packBarcodes){this.packBarcodes =res[0].packBarcodes;}
      if(res[0].levelId){this.level =res[0].levelId;}
       
      if(res[0].barcodeId){this.barcodeId = res[0].barcodeId;}  
      if(res[0].barcodeId){this.barcodeId = res[0].barcodeId;} 


        this.multispecialStockIndicatorId.push({value: element.specialStockIndicatorId, label: element.specialStockIndicatorId});
        this.multispecialStockIndicatorId = this.cs.removeDuplicatesFromArrayNewstatus(this.multispecialStockIndicatorId);

        this.multistockTypeId.push({value: element.stockTypeId, label: element.stockTypeId});
        this.multistockTypeId = this.cs.removeDuplicatesFromArrayNewstatus(this.multistockTypeId);

        this.multistorageBin.push({value: element.storageBin, label: element.storageBin});
        this.multistorageBin = this.cs.removeDuplicatesFromArrayNewstatus(this.multistorageBin);


        this.multilevel.push({value: element.levelId, label: element.levelId});
        this.multilevel = this.cs.removeDuplicatesFromArrayNewstatus(this.multilevel);
       
      });
      this.spin.hide();
  }, err => {
    this.cs.commonerrorNew(err);
    this.spin.hide();
  }));
}



//validateSearch(){
  // if(!this.description || !this.mfrPartNo){
  //   this.toastr.error(
  //     "Please search the Part No to continue",
  //     "Notification", {
  //     timeOut: 2000,
  //     progressBar: false,
  //   }
  //   )
  //   return;
  // }
//}
binLocationChange(e) {
  let obj: any = {}
  obj.itemCode = [this.itemCode];
  obj.storageBin = [e.value];
  obj.manufacturerName = [this.mfrPartNo]
  this.spin.show();
  this.sub.add(this.inventory.GetInventoryv2(obj).subscribe(res => {
    this.multiPackBarcodes = [];
    this.multispecialStockIndicatorId = [];
    this.multistockTypeId = [];
    //this.multistorageBin = [];

    res.forEach((element: {
      //packBarcodes: string,
      specialStockIndicatorId: string,
      stockTypeId: string,
      storageBin: string,
      referenceField10: string,
      inventoryQuantity: number,
      barcodeId:string,
    }) => {
      this.multiPackBarcodes.push({
        value: element.barcodeId,
        label: element.barcodeId
      });
      this.multiPackBarcodes = this.cs.removeDuplicatesFromArrayNewstatus(this.multiPackBarcodes);
      // if(this.multiPackBarcodes.length == 1){
      //   this.packBarcodes = element.packBarcodes;
      // }
      this.multispecialStockIndicatorId.push({
        value: element.specialStockIndicatorId,
        label: element.specialStockIndicatorId
      });
      this.multispecialStockIndicatorId = this.cs.removeDuplicatesFromArrayNewstatus(this.multispecialStockIndicatorId);
      if (this.multispecialStockIndicatorId.length == 1) {
        this.specialStockIndicatorId = element.specialStockIndicatorId;
      }

      this.multistockTypeId.push({
        value: element.stockTypeId,
        label: element.stockTypeId
      });
      this.multistockTypeId = this.cs.removeDuplicatesFromArrayNewstatus(this.multistockTypeId);
      if (this.multistockTypeId.length == 1) {
        this.stockTypeId = element.stockTypeId;
      }

      //  this.multistorageBin.push({
      //   value: element.referenceField10,   
      //   label: element.referenceField10
      // });
      // this.multistorageBin = this.cs.removeDuplicatesFromArrayNewstatus(this.multistorageBin);
      // if (this.multistorageBin.length == 1) {
      //   this.storageSection = element.referenceField10;
      // }
      if (res.length > 0) {
        this.showHiddenFields1 = true;
      }

    }, err => {
      this.cs.commonerror(err);
      this.spin.hide();
    });
    this.spin.hide();
  }))
}
palletidChanged(e) {
  console.log(e)
  let obj: any = {}
  obj.itemCode = [this.itemCode];
  obj.storageBin = [this.storageBin];
  obj.storageSectionId = [this.storageSection];
  obj.stockTypeId = [this.stockTypeId];
  obj.specialStockIndicatorId = [this.specialStockIndicatorId]
  obj.packBarcodes = [e.value];
  this.spin.show();
  this.sub.add(this.inventory.GetInventoryv2(obj).subscribe(res => {
    this.inventoryQuantity = res[0].inventoryQuantity;
    this.allocatedQuantity = res[0].allocatedQuantity;
    this.spin.hide();
  }, err => {
    this.cs.commonerror(err);
    this.spin.hide();
  }))
}
  submit() {

      if(!this.itemCode){
    this.toastr.error(
      "Please enter Part No to continue",
      "Notification", {
      timeOut: 2000,
      progressBar: false,
    }
    )
    return;
  }

    let obj: any = {};

    obj.allocatedQuantity = Number;
    obj.inventoryQuantity = Number;
    if (this.allocatedQuantity != null) {
      obj.allocatedQuantity = Number(this.allocatedQuantity);
    }
    if (this.inventoryQuantity != null) {
      obj.inventoryQuantity = Number(this.inventoryQuantity);
    }

    this.sub.add(this.inventory.inventoryUpdatev2(this.stockTypeId, this.itemCode, this.packBarcodes, this.specialStockIndicatorId,this.storageBin,this.mfrPartNo, this.auth.warehouseId,this.auth.languageId,this.auth.companyId,this.auth.plantId, obj).subscribe(res => {
      this.toastr.success("Inventory updated successfully!","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
      if (this.inventoryQuantity != null && this.inventoryQuantity != 0) {
      let obj1: any = {};
        obj1.balanceOHQty = this.inventoryQuantity != null ? Number(this.inventoryQuantity) : 0;
        obj1.batchSerialNumber = 1;
        obj1.companyCodeId = this.auth.companyId;
        obj1.description = this.description != null ? this.description : '';
        obj1.inventoryUom = this.inventoryUom != null ? this.inventoryUom : '';;
        obj1.itemCode = this.itemCode != null ? this.itemCode : '';
        obj1.languageId = this.auth.languageId;
        obj1.movementDocumentNo = 1;
        obj1.movementQty = this.inventoryQuantity != null ? this.inventoryQuantity : 0;
        obj1.movementType = 4;
        obj1.packBarcodes = this.packBarcodes != null ? this.packBarcodes : '';
        obj1.plantId = this.auth.plantId;
        obj1.refDocNumber = 1;
        obj1.specialStockIndicator = this.specialStockIndicatorId != null ? this.specialStockIndicatorId : '';
        obj1.stockTypeId = this.stockTypeId != null ? this.stockTypeId : 0;
        obj1.storageBin = this.storageBin != null ? this.storageBin : '';
        obj1.submovementType = 1;
        obj1.warehouseId = this.auth.warehouseId;

      this.sub.add(this.inventory.createInventoryMovement(obj1).subscribe(res => {

      }));
      }
      this.itemCodeFE = null;
      this.description = null;
      this.mfrPartNo = null;
      this.barcodeId = null;
      this.binClassId=null;
      this.specialStockIndicatorId = null;
      this.stockTypeId = null;
      this.storageBin = null;
      this.allocatedQuantity=0;
      this.inventoryQuantity = 0;
     // this.dialogRef.close();
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));

  }

}
