import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { ItemReceiptService } from '../../item-receipt.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { catchError } from 'rxjs/operators';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { BarcodetypeidService } from 'src/app/main-module/other-setup/barcodetypeid/barcodetypeid.service';
import { CustomdialogComponent } from 'src/app/common-field/customdialog/customdialog.component';
import { AuthService } from 'src/app/core/Auth/auth.service';
import { PartnerService } from 'src/app/main-module/Masters -1/masternew/partner/partner.service';
import { GoodsReceiptService } from '../../../Goods-receipt/goods-receipt.service';
import { PricelistService } from 'src/app/main-module/Masters -1/threepl/pricelist/pricelist.service';
import { PricelistassignmentService } from 'src/app/main-module/Masters -1/threepl/pricelistassignment/pricelistassignment.service';
import { MasterService } from 'src/app/shared/master.service';
import { Customdialog2Component } from 'src/app/common-field/customdialog2/customdialog2.component';
import { ImbatchserialService } from 'src/app/main-module/Masters -1/masternew/imbatchserial/imbatchserial.service';
import { tree } from 'd3';

@Component({
  selector: 'app-label-generate',
  templateUrl: './label-generate.component.html',
  styleUrls: ['./label-generate.component.scss']
})
export class LabelGenerateComponent implements OnInit {
  maintainanceList:any[]=[];
  storageMethodList:any[]=[];
  constructor(public dialogRef: MatDialogRef<any>, public auth: AuthService, private service: ItemReceiptService, private partnerservice: PartnerService, @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService, private barcodeService: BarcodetypeidService, private dialog: MatDialog, private goodsReceiptService: GoodsReceiptService,private priceList:PricelistassignmentService,private reportservice : ReportsService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService, private basicdataService: BasicdataService,private masterService: MasterService,private batchService:ImbatchserialService,
    private cs: CommonService, private reportService: ReportsService) {   this.storageMethodList = [
      //  {value: 48, label: '48 - Picking'},
        {value: 'Batch', label: 'Batch'},
        {value: 'Serial', label: 'Serial'},
        {value: 'Not Applicable', label: 'Not Applicable'},
       
    ];
    this.maintainanceList = [
      //  {value: 48, label: '48 - Picking'},
        {value: 'Internal', label: 'Internal'},
        {value: 'External', label: 'External'},
      
       
    ];}
unloaderName:any;
receiverName:any;
    showwieghtCBMVALUE=false;
  printLabel = false;
  showvolumeCBMVALUE=false;
  showCBMVALUE=false;
  showElseCbm=false;
  show3pl=true;
  ngOnInit(): void {
    if(this.data.pageflow != 'Print' && this.auth.companyId == 1001){
       this.show3pl=false;
    }
    this.data.line.acceptedQty =  this.data.line.orderQty;
    this.findDimensions()
    this.findUOM()
    this.findbatch()
   // this.findbatchserial()
    // if (this.data.line.partner_item_barcode == null || this.data.line.partner_item_barcode == '') {
    //   this.searchBarcode();
    // } else {
    //   this.data.line['partner_item_barcode'] = this.data.line.partner_item_barcode;
    // }
    this.barcodeService.search({ companyCodeId: this.data.line.companyCode, plantId: this.data.line.plantId, languageId: [this.data.line.languageId], warehouseId: this.data.line.warehouseId }).subscribe(res => {
      this.data.line['barCodeType'] = res[0].barcodeTypeId;
    })
    if (this.data.pageflow == 'Print' && this.auth.companyId == 1001) {
      this.selectedIndex = 2;
      this.printLabel = true;
      this.show3pl=false;
    }
    if (this.data.pageflow == 'Print') {
      this.selectedIndex = 2;
      this.printLabel = true;
    }
  }
  onMaintenanceChange(event:any){
    console.log(event)
    if((event.value  == 'Internal')&& this.data.line['maintainence']!='Not Applicable' && this.data.line['batchSerialNumber'] ==null){
    this.data.line['maintainence']=(event.value);
    this.sub.add(this.reportservice.getBatchgenerate(this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId,this.data.line.storageMethod).subscribe(res => {
      this.data.line['batchSerialNumber']=(res);
      this.toastr.success(res + " Created successfully!","Notification",{
        timeOut: 2000,
        progressBar: false,
      });
    


    
  }
));
}
else{
const dialogRef = this.dialog.open(Customdialog2Component, {
  // width: '60%', height: '70%',
  width: '25%',
  maxWidth: '30%',
  position: {
    top: '6.7%',
  },

  data: { title: "Enter Number" ,body: "Current No" },
});
dialogRef.afterClosed().subscribe(result => {
  
  if (result != null) {
  this.data.line['batchSerialNumber']=(result.remarks)
  }
});
}
  }
  uomList:any[]=[];
  findUOM() {
    let obj: any = {};
    obj.companyCodeId = this.auth.companyId;
    obj.languageId = [this.auth.languageId];
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;
    obj.moduleId = ["4000"];
    obj.partnerCode = [this.data.line.businessPartnerCode];
  
    this.priceList.search(obj).subscribe(res => {
      if (res != null && res.length > 0) { // Check if res is not null and has at least one element
        let obj1: any = {};
        obj1.companyCodeId = this.auth.companyId;
        obj1.languageId = [this.auth.languageId];
        obj1.plantId = this.auth.plantId;
        obj1.warehouseId = this.auth.warehouseId;
        obj1.moduleId = ["4000"];
        obj1.priceListId = [res[0].priceListId];
  
        this.priceList.searchPRICE(obj1).subscribe(result => {
          
          console.log(result[0].chargeUnit)
          this.data.line['threePLUom'] = result[0].chargeUnit;
          console.log(this.data.line.threePLUom);
  
          if (this.data.line.threePLUom == "CBM") {
            this.showCBMVALUE = true;
            console.log(this.showCBMVALUE);
          }
  
          else if (this.data.line.threePLUom == "Weight") {
            this.showwieghtCBMVALUE = true;
            this.data.line['weight'] = 1;
            console.log(this.showCBMVALUE);
          }
  
        else  if (this.data.line['threePLUom'] == 'ECH') {
            this.data.line['qty'] = 1;
            this.data.line['totalqty'] = this.data.line.acceptedQty;
            this.showvolumeCBMVALUE = true;
            console.log(this.showvolumeCBMVALUE);
          }
          else {
            console.log(2);
            this.data.line['qty'] = 1;
            this.data.line['totalqty'] = this.data.line.acceptedQty;
            this.showElseCbm = true;
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
              });
            });
          }
        });
      } else {
        this.showElseCbm = true;
        this.data.line['qty'] = 1;
        this.data.line['totalqty'] = this.data.line.acceptedQty;
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
          });
        });
      }
    });
  }
  
  gettotalweight(type){
    if(type != null){

      this.data.line['totalweight']=(this.data.line['weight']*this.data.line.acceptedQty)
    }
  }
  gettotalqty(type){
    if(type != null){
     
    }
  }
  getheight(type){
    if(type=='accepetd'){
      this.data.line['cbmQty']=(Number(this.data.line.length ? this.data.line.length : 0) * Number(this.data.line.width ? this.data.line.width : 0) * Number(this.data.line.height ? this.data.line.height : 0) )
      console.log(this.data.line['cbmQty']);
    
    }
  }
  showremarks=false;
  getVariance(type) {

    if (type == 'rejected' && this.data.line.damageQty < 0) {
      this.toastr.error(
        "Damaged Qty should not be greater than 1",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );
      this.data.line.damageQty = null;
    }
    if(this.data.line.damageQty >0){
      console.log(1)
      this.showremarks=true;
   
    }
    if (type == 'accepted' && this.data.line.acceptedQty < 0) {
      this.toastr.error(
        "Released Qty should not be greater than 1",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );
      this.data.line.acceptedQty = null;
    }
    let num: number = (Number(this.data.line.acceptedQty ? this.data.line.acceptedQty : 0) + Number(this.data.line.damageQty ? this.data.line.damageQty : 0)
      + Number(this.data.line.rec_accept_qty ? this.data.line.rec_accept_qty : 0) + Number(this.data.line.rec_damage_qty ? this.data.line.rec_damage_qty : 0));

    this.data.line.varianceQty = Number(this.data.line.orderQty) as number - num;
    let varianceQty1 = this.data.line.varianceQty;
    if (this.data.line.varianceQty < 0) {
      if (type == 'accepted') {
        this.toastr.error(
          "Accepted Qty should not be more than Order Qty",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        }
        );
        this.data.line.acceptedQty = null;
        this.data.line.varianceQty = Number(this.data.line.orderQty) as number - (Number(this.data.line.rec_accept_qty) + Number(this.data.line.rec_damage_qty));
      
      }
      if (type == 'rejected') {
        this.toastr.error(
          "Damaged Qty should not be more than Order Qty",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        }
        );
        this.data.line.damageQty = null;
        this.data.line.varianceQty = Number(this.data.line.orderQty) as number - (Number(this.data.line.rec_accept_qty) + Number(this.data.line.rec_damage_qty));

      }
    }
if(this.data.line['threePLUom'] == 'CBM'){
    this.data.line['acceptedCbmQty'] = (Number(this.data.line.length ? this.data.line.length : 0) * Number(this.data.line.width ? this.data.line.width : 0) * Number(this.data.line.height ? this.data.line.height : 0) * Number(this.data.line.acceptedQty ? this.data.line.acceptedQty : 0))
    this.data.line['rejectedCbmQty'] = (Number(this.data.line.length ? this.data.line.length : 0) * Number(this.data.line.width ? this.data.line.width : 0) * Number(this.data.line.height ? this.data.line.height : 0) * Number(this.data.line.damageQty ? this.data.line.damageQty : 0))
}
    if(this.data.line['threePLUom'] == 'ECH' || this.showElseCbm == true){
    if(this.data.line['qty']== 1){
      this.data.line['totalqty']=(this.data.line.acceptedQty)
      console.log(this.data.line['totalqty'])
    }
    else{
    this.data.line['totalqty']=(this.data.line['qty']*this.data.line.acceptedQty)
    }
  }
  if(this.data.line['threePLUom']== 'Weight'){
    if(this.data.line['weight'] ==1){
      this.data.line['totalweight']=(this.data.line.acceptedQty)
      console.log(this.data.line['totalweight'])
    }
    else{
    this.data.line['totalweight']=(this.data.line['weight']*this.data.line.acceptedQty)
    
    }
  }
  }

  sub = new Subscription();

  remarks(data) {
    if(this.showremarks){
    const dialogRef = this.dialog.open(Customdialog2Component, {
      // width: '60%', height: '70%',
      width: '25%',
      maxWidth: '30%',
      position: {
        top: '6.7%',
      },

      data: { title: "Remarks", body: "Remarks", value: this.data.line.referenceField5 },
    });
    dialogRef.afterClosed().subscribe(result => {
      
      if (result != null) {
        this.data.line.referenceField5 = result.remarks;
      }
    });
  }
  }

  printed = false;
  createPartner = false;
  submit() {
  console.log(this.data.line['referenceField6'])
  if(this.showbatchdetails == true && this.data.line['acceptedQty'] != null){
    this.selectedIndex=1
  }
  if((this.data.line['storageMethod'] !='Not Applicable')  &&(this.data.line['maintenance'] =="Internal" ) &&((this.data.line['manufacturerDate'] == null)||(this.data.line['expiryDate'] == null) ) ){
    
    this.toastr.error(
      "Please fill Required Manufactured Date and Expiry Date to continue",
      "Notification", {
      timeOut: 2000,
      progressBar: false,
    }
  );

  this.cs.notifyOther(true);
  return;
}
if(this.data.line['threePLUom'] == "CBM"){
  this.data.line.threePLCbm=this.data.line['acceptedCbmQty']
  this.data.line.cbmQuantity=this.data.line.cbmQty;
}
if(this.data.line['threePLUom'] == "Weight"){
this.data.line.threePLCbm=this.data.line['totalweight'];
this.data.line.cbmQuantity=this.data.line.cbm;
}
if(this.data.line['threePLUom']=='ECH'  || this.showElseCbm == true){
  this.data.line.threePLCbm=this.data.line['totalqty'];
  this.data.line.cbmQuantity=this.data.line.qty;
}

    if (this.data.line.acceptedQty == null || this.data.line.acceptedQty == '' || this.data.line.acceptedQty == 0) {
      this.toastr.error(
        "Please fill Release qty to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );

      this.cs.notifyOther(true);
      return;
    }
    if (this.data.line.damageQty == null || this.data.line.damageQty == '') {
      this.data.line.damageQty = 0;
    }
    if (this.data.line.packBarcodes.length > 0) {
      this.data.line.packBarcodes = [];
    }

    if (this.data.line.partner_item_barcode == null || this.data.line.partner_item_barcode == '') {
      // this.toastr.error(
      //   "Please fill barcode field to continue",
      //   "Notification",{
      //     timeOut: 2000,
      //     progressBar: false,
      //   }
      // );

      // this.cs.notifyOther(true);
      // return;

      const dialogRef = this.dialog.open(CustomdialogComponent, {
        // width: '60%', height: '70%',
        width: '50%',
        maxWidth: '80%',
        position: {
          top: '6.7%',
        },

        data: { title: "Generate Barcode", body: "The selected part no doesn't have barcode id, Do you want to generate barcode" },
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result == 'Yes') {
          if (this.data.line.packBarcodes.length < 0) {
            this.data.line.packBarcodes = [];

          }
          this.spin.show();
          this.sub.add(this.service.packBarcodeV2(this.data.line.acceptedQty, this.data.line.damageQty, this.data.line.warehouseId, this.data.line.companyCode, this.data.line.plantId, this.data.line.languageId).subscribe(res => {
            // this.data.line.packBarcodes = res;
            this.data.line.packBarcodes = res;
            if (res.length >= 0) {
              res.forEach(element => {
                element.cbmQuantity = this.data.line.cbmQty;
                
                element.cbm = element.quantityType == 'A' ? this.data.line.acceptedCbmQty : element.quantityType == 'D' ? this.data.line.rejectedCbmQty : '';
              });
            }
            
            this.data.line['barcodeId'] = this.data.line.partner_item_barcode;
            this.data.line.partner_item_barcode = this.data.line.partner_item_barcode;
            this.data.line['goodReceiptQty'] = 1;
            this.data.line['noOfDamageLabel'] = this.data.line.damageQty; //this.data.line.damageQty; 
            this.data.line['noOfAcceptedLabel'] = this.data.line.acceptedQty; //this.data.line.acceptedQty; 
            this.data.line['totalLabel'] = this.data.line.noOfDamageLabel + this.data.line.noOfAcceptedLabel;
            this.data.line['barCodeType'] = this.data.line.barCodeType;
            this.data.line['packBarcodesState'] = true;
            this.data.line['referenceField6']=this.data.line.referenceField6;
            this.data.line['referenceField7']=this.data.line.referenceField7;
            this.data.line['remarks'] = 'test';
            this.spin.hide();
          }, err => {
            this.cs.commonerrorNew(err);
            this.spin.hide();
          }));
          this.createPartner = true;
          this.printLabel = true;
          this.selectedIndex = 2;
          const concatenatedValue = this.data.line.manufacturerName + this.data.line.itemCode;
          this.data.line['partner_item_barcode'] = concatenatedValue;
          
          console.log(this.data.line);
        
        }
      });


      this.cs.notifyOther(true);
      return;
    }



    this.spin.show();
    this.sub.add(this.service.packBarcodeV2(this.data.line.acceptedQty, this.data.line.damageQty, this.data.line.warehouseId, this.data.line.companyCode, this.data.line.plantId, this.data.line.languageId).subscribe(res => {

      this.data.line.packBarcodes = res;
      if (res.length >= 0) {
        res.forEach(element => {
          element.cbmQuantity = this.data.line.cbmQty;
          element.cbm = element.quantityType == 'A' ? this.data.line.acceptedCbmQty : element.quantityType == 'D' ? this.data.line.rejectedCbmQty : '';
        });
      }
      this.data.line['barcodeId'] = this.data.line.partner_item_barcode;
      this.data.line.partner_item_barcode = this.data.line.partner_item_barcode;
      this.data.line['goodReceiptQty'] = 1;
      this.data.line['noOfDamageLabel'] = this.data.line.damageQty; //this.data.line.damageQty; 
      this.data.line['noOfAcceptedLabel'] = this.data.line.acceptedQty; //this.data.line.acceptedQty; 
      this.data.line['totalLabel'] = this.data.line.noOfDamageLabel + this.data.line.noOfAcceptedLabel;
      this.data.line['barCodeType'] = this.data.line.barCodeType;
      this.data.line['packBarcodesState'] = true;
      this.data.line['remarks'] = 'test';
      this.data.line['referenceField6']=this.data.line.referenceField6;
      this.data.line['referenceField7']=this.data.line.referenceField7;
      this.spin.hide();
      this.dialogRef.close(this.data);
      ;
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));

    this.goodsReceiptService.updateLine1(this.data.line).subscribe(res => {

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    })

  }

  submitNew() {
    this.spin.show();
    console.log(this.printed);
    if (this.createPartner == true) {
      let obj: any = {};
      obj.languageId = this.auth.languageId;
      obj.companyCodeId = this.auth.companyId;
      obj.plantId = this.auth.plantId;
      obj.languageId = this.auth.languageId;
      obj.warehouseId = this.auth.warehouseId;
      obj.businessPartnerCode = this.data.line.manufacturerName,
        obj.businessPartnerType = "1";
      obj.itemCode = this.data.line.itemCode;
      obj.partnerItemBarcode = this.data.line.partner_item_barcode;
      obj.manufacturerCode = this.data.line.manufacturerCode;
      obj.manufacturerName = this.data.line.manufacturerName;
      obj.brand = this.data.line.brand;
      this.sub.add(this.partnerservice.Create([obj]).subscribe(res => {
        this.toastr.success(res[0].businessPartnerCode + " Created Successfully!", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
      }
      ))
    }
    if (this.printed == false) {
      this.toastr.error(
        "Please print the barcode to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );
      this.spin.hide();
      this.cs.notifyOther(true);
      return;
    }
    this.goodsReceiptService.updateLine1(this.data.line).subscribe(res => {

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    })

    this.printed = false;
    this.data.line['remarks'] = 'test';
    this.data.line['packBarcodesState'] = true;
    this.data.line.barcodeId = this.data.line.partner_item_barcode;
    this.dialogRef.close(this.data);
    this.spin.hide();
  }



  printLabel1(action) {
    
    let obj: any = {};
    let dataa: any[] = [];
    //    for (let j = 0; j < this.data.line.noOfAcceptedLabel; j++) {
    obj.barcodeId = this.data.line.partner_item_barcode != null ? this.data.line.partner_item_barcode : this.data.line.itemCode;
    obj.partner_item_barcode = this.data.line.partner_item_barcode != null ? this.data.line.partner_item_barcode : this.data.line.itemCode;
    obj.goodReceiptQty = 1;
    obj.noOfDamageLabel = this.data.line.noOfDamageLabel;
    obj.noOfAcceptedLabel = this.data.line.noOfAcceptedLabel;
    obj.totalLabel = this.data.line.noOfDamageLabel + this.data.line.noOfAcceptedLabel;
    obj.barCodeType = this.data.line.barCodeType;
    obj.manufacturerName = this.data.line.manufacturerName;
    obj.origin = this.data.line.origin;
    obj.itemCode = this.data.line.itemCode;
    obj.itemDescription = this.data.line.itemDescription;

    obj.qty = this.data.line.noOfAcceptedLabel;
    console.log(obj)
    dataa.push(obj);
    //    }
    // let obj2: any = {};
    // for (let i = 0; i < this.data.line.noOfDamageLabel; i++) {
    //   obj2.barcodeId = this.data.line.partner_item_barcode != null ? this.data.line.partner_item_barcode : this.data.line.itemCode;
    //   obj2.partner_item_barcode = this.data.line.partner_item_barcode != null ? this.data.line.partner_item_barcode : this.data.line.itemCode;
    //   obj2.goodReceiptQty = 1;
    //   obj2.noOfDamageLabel = this.data.line.noOfDamageLabel;
    //   obj2.noOfAcceptedLabel = this.data.line.noOfAcceptedLabel;
    //   obj2.totalLabel = this.data.line.noOfDamageLabel + this.data.line.noOfAcceptedLabel;
    //   obj2.barCodeType = this.data.line.barCodeType;
    //   obj2.manufacturerName = this.data.line.manufacturerName;
    //   obj2.origin = this.data.line.origin;
    //   obj2.itemCode = this.data.line.itemCode;
    //   obj2.itemDescription = this.data.line.itemDescription;

    //   obj2.qty = this.data.line.noOfDamageLabel;
    //   dataa.push(obj2);
    // }
    sessionStorage.setItem(
      'barcode',
      JSON.stringify({ BCfor: "barcode", list: dataa })
    );
    if (action == 'preview') {
      window.open('#/barcodeNew', '_blank');
    }
    if (action == 'print') {
      this.printed = true;
      window.open('#/barcode', '_blank');
    }

  }

  searchBarcode() {
    this.spin.show();
    if (this.data.line.partner_item_barcode == null || this.data.line.partner_item_barcode == "") {
      let obj: any = {};
      obj.referenceField1 = [this.data.line.manufacturerName];
      obj.itemCode = [this.data.line.itemCode];
      this.reportService.findBarCodeScan(obj).subscribe(res => {
        if (res.length == 1) {
          this.data.line.partner_item_barcode = res[0].barcode;
          this.spin.hide();
        } else {
          //    this.data.line['partner_item_barcode'] = this.data.line.itemCode;
          this.toastr.error(
            "No barcode found",
            "Notification", {
            timeOut: 2000,
            progressBar: false,
          }
          );
          this.spin.hide();
          this.cs.notifyOther(true);
          return;
        }
      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      })
    }

  }


  showDropdown1: true;
  showDropdown() {
    this.data.line.interimStorageBin = null;
    this.showDropdown1 = true;
  }

  multiselectStorageList: any[] = [];
  storageBinList1: any[] = [];
  selectedStorageBin: any[] = [];
  onStorageType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        storageList: this.reportService.getStorageDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
      })
        .subscribe(({ storageList }) => {
          if (storageList != null && storageList.length > 0) {
            this.multiselectStorageList = [];
            this.storageBinList1 = storageList;
            this.storageBinList1.forEach(x => this.multiselectStorageList.push({ value: x.storageBin, label: x.storageBin }))
          }
        });
    }
  }


  selectedIndex: number = 0;
  showSubmit = false;
  tabChanged(tabChangeEvent: MatTabChangeEvent): void {
    this.selectedIndex = tabChangeEvent.index;
  }
  nextStep() {



    if (this.selectedIndex == 0) {
      if (this.data.line.acceptedQty == null || this.data.line.acceptedQty == '' || this.data.line.acceptedQty == 0) {
        this.toastr.error(
          "Please fill accepted qty to continue",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        }
        );
        this.cs.notifyOther(true);
        return;
      }
      else {
        this.data.line['noOfDamageLabel'] = this.data.line.damageQty ? this.data.line.damageQty : 0;
        this.data.line['noOfAcceptedLabel'] = this.data.line.acceptedQty ? this.data.line.acceptedQty : 0;
        this.data.line['totalLabel'] = this.data.line.acceptedQty + this.data.line.damageQty;
        this.selectedIndex = this.selectedIndex + 1;
      }
    }
    if (this.selectedIndex == 2) {

    }
    else {
      this.selectedIndex = this.selectedIndex + 1;
    }
  }


  previousStep() {
    this.selectedIndex = this.selectedIndex - 1;
  }
  showbatchdetails=true;
showexpirtaiondate=false;
showBatchserialNumber=false;
showotherdate=false;
  findbatch(){
    console.log(this.data.line['batchSerialNumber'])
  
    let obj:any={};
    obj.companyCodeId = [this.data.line.companyCode];
    obj.languageId = [this.data.line.languageId];
    obj.plantId = [this.data.line.plantId];
    obj.warehouseId = [this.data.line.warehouseId];
    obj.itemCode = [this.data.line.itemCode];
    obj.manufacturerName = [this.data.line.manufacturerName];
    this.batchService.search(obj).subscribe(res => {
    
    if(res.length !=0){
      
    if(res[0].shelfLifeIndicator == true){
      this.showexpirtaiondate=true;
      this.data.line['storageMethod']=res[0].storageMethod;
      this.data.line['maintenance']=res[0].maintenance;
      if(this.data.line['maintenance']  == 'Internal' && this.data.line['storageMethod']!='Not Applicable' && this.data.line['batchSerialNumber'] == null){
        this.sub.add(this.reportservice.getBatchgenerate(this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId,this.data.line.storageMethod).subscribe(res => {
          if(this.data.line['batchSerialNumber'] !=null){
            this.data.line['batchSerialNumber']=this.data.line['batchSerialNumber'];
          }
          else{
          this.data.line['batchSerialNumber']=(res);
          }
          this.toastr.success(res + " Created successfully!","Notification",{
            timeOut: 2000,
            progressBar: false,
          });
        
    
    
        
      }
    ));
    }
    else if(this.data.line['maintenance']  == 'External' && this.data.line['storageMethod']!='Not Applicable' && this.data.line['batchSerialNumber'] == null){
    // const dialogRef = this.dialog.open(Customdialog2Component, {
    //   // width: '60%', height: '70%',
    //   width: '25%',
    //   maxWidth: '30%',
    //   position: {
    //     top: '6.7%',
    //   },
    
    //   data: { title: "Enter Number" ,body: "Current No" },
    // });
    // dialogRef.afterClosed().subscribe(result => {
    //   
    //   if (result != null) {
    //   this.data.line['batchSerialNumber']=(result.remarks)
    //   }
    // });
    this.showBatchserialNumber=true;

    }
  
    }
  
    else{
      this.showBatchserialNumber=true;
      this.sub.add(this.reportservice.getBatchgenerate(this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId,res[0].storageMethod).subscribe(res => {
        this.data.line['batchSerialNumber']=(res);
        this.toastr.success(res + " Created successfully!","Notification",{
          timeOut: 2000,
          progressBar: false,
        });
      
  
  
      
    }
  ));
    }
  }
  else{
    
    this.showotherdate=true;
    this.showbatchdetails=false;
    if(this.data.line['batchSerialNumber'] != null){
      this.data.line['batchSerialNumber']=this.data.line['batchSerialNumber']
      this.showBatchserialNumber=true;
    }
    this.data.line['referenceField8']=(this.cs.todayapi())
    // console.log(this.data.line['referenceField8'])
    
  }
  
    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    })
  }

  


  
  findDimensions() {
    let obj: any = {};
    obj.companyCodeId = [this.data.line.companyCode];
    obj.languageId = [this.data.line.languageId];
    obj.plantId = [this.data.line.plantId];
    obj.warehouseId = [this.data.line.warehouseId];
    obj.itemCode = [this.data.line.itemCode];
    obj.manufacturerPartNo = [this.data.line.manufacturerName];

    this.basicdataService.search1(obj).subscribe(res => {
      if (res.length == 0) {
        this.toastr.error(
          "No record found",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        }
        );
      }
      
      if (res.length == 1 && res[0].length == null && res[0].width == null && res[0].height == null && res[0].capacityCheck == true) {
        this.toastr.error(
          "Please maintain records for the corresponding part no in the basic data master to continue",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        }
        );
      }
      else {
        this.data.line['length'] = res[0].length;
        this.data.line['width'] = res[0].width;
        this.data.line['height'] = res[0].height;

        this.data.line['cbmQty'] = (Number(this.data.line.length ? this.data.line.length : 0) * Number(this.data.line.width ? this.data.line.width : 0) * Number(this.data.line.height ? this.data.line.height : 0));
        this.data.line['acceptedCbmQty'] = (Number(this.data.line.length ? this.data.line.length : 0) * Number(this.data.line.width ? this.data.line.width : 0) * Number(this.data.line.height ? this.data.line.height : 0) * Number(this.data.line.acceptedQty ? this.data.line.acceptedQty : 0));
        this.data.line['rejectedCbmQty'] = (Number(this.data.line.length ? this.data.line.length : 0) * Number(this.data.line.width ? this.data.line.width : 0) * Number(this.data.line.height ? this.data.line.height : 0) * Number(this.data.line.damageQty ? this.data.line.damageQty : 0));
        if(this.data.line['threePLUom'] == "CBM"){
          this.data.line.threePLCbm=(Number(this.data.line.length ? this.data.line.length : 0) * Number(this.data.line.width ? this.data.line.width : 0) * Number(this.data.line.height ? this.data.line.height : 0) * Number(this.data.line.acceptedQty ? this.data.line.acceptedQty : 0));
          this.data.line.cbmQuantity=this.data.line.cbmQty;
        }
        if(this.data.line['threePLUom'] == "Weight"){
        this.data.line.threePLCbm=this.data.line['totalweight'];
        this.data.line.cbmQuantity=this.data.line.cbm;
        }
        if(this.data.line['threePLUom']=='ECH'){
          this.data.line.threePLCbm=this.data.line['totalqty'];
          this.data.line.cbmQuantity=this.data.line.qty;
        }
      }
    })

  }
}
