import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BomDialogComponent } from './bom-dialog/bom-dialog.component';
import { OrderDetailsNewComponent } from './order-details-new/order-details-new.component';
import { Router } from '@angular/router';
import { PreoutboundService } from '../../Outbound/preoutbound/preoutbound.service';
import { BOMService } from '../../Masters -1/other-masters/bom/bom.service';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
export interface variant1 {
  orderDetails: string;
  itemCode: string;
  description: string;
  bom: string;
  orderQty: string;
  routingNo: string;
  startDate: Date;
  endDate: Date;
  status: string;
}
const ELEMENT_DATA1: variant1[] = [
  {
    orderDetails: 'TF-2907', itemCode: 'EG60363', description: 'PULLEY TAHOE/YUKON 00-13', bom: '1', orderQty: '1', routingNo: '5',
    startDate: new Date(), endDate: new Date(), status: 'Active'
  }
];


@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent implements OnInit {

  advanceFilterShow: boolean;
  @ViewChild('Setupstoragesection') Setupstoragesection: Table | undefined;
  OrderDetails: any;
  selectedOrderDetails: any;

  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  constructor(public dialog: MatDialog,

    private BasicdataService: BasicdataService,
    // private cas: CommonApiService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public cs: CommonService,
    private router: Router,
    private bomService: BOMService,
    private service: PreoutboundService,
    // private excel: ExcelService,
    private fb: FormBuilder,
    private auth: AuthService,) { }
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
  applyFilterGlobal($event: any, stringVal: any) {
    this.Setupstoragesection!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
  ngOnInit(): void {
    this.OrderDetails = ELEMENT_DATA1;
    this.initialLoad();
  }
  onChange() {
    const choosen = this.selectedOrderDetails[this.selectedOrderDetails.length - 1];
    this.selectedOrderDetails.length = 0;
    this.selectedOrderDetails.push(choosen);
  }


  openDialog(element, type) {
    const dialogRef = this.dialog.open(BomDialogComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      // data: { pageflow: data, code: data != 'New' ? this.selectedaisle[0].aisleId : null, floorId: data != 'New' ? this.selectedaisle[0].floorId : null, storageSectionId: data != 'New' ? this.selectedaisle[0].storageSectionId : null,}
      data: { lines: element, type: type }
    });

    dialogRef.afterClosed().subscribe(result => {
      //  this.getAll();
    });
  }

  openNew() {
    // let  paramdata = element
    this.router.navigate(['/main/manufacturing/orderNew'])
  }


  openDialogNew() {
    const dialogRef = this.dialog.open(OrderDetailsNewComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      //data: element
    });
    dialogRef.afterClosed().subscribe(result => {
      //  this.getAll();
    });
  }

  generateRandomNumber(): number {
    return Math.floor(100000 + Math.random() * 900000);
  }

  outBoundLinesArray:any[] = [];

  initialLoad(){
    this.bomService.search({ parentItemCode: ['EG60363'] }).subscribe(res => {

      this.spin.hide();
      let outBoundLines: any[] = [];

      res[0].bomLines.forEach((element, index) => {
        this.outBoundLinesArray = []
        let obj2: any = {};
        obj2.companyCodeId = [this.auth.companyId];
        obj2.languageId = [this.auth.languageId];
        obj2.plantId = [this.auth.plantId];
        obj2.warehouseId = [this.auth.warehouseId];
        obj2.itemCode = [element.childItemCode];

        this.BasicdataService.searchSpark(obj2).subscribe(basicDataResult => {
          let obj1: any = {};
          obj1.lineReference = index + 1,
            obj1.orderType = 'N',
            obj1.orderedQty = element.childItemQuantity,
            obj1.sku = element.childItemCode,
            obj1.skuDescription = basicDataResult[0].description,
            obj1.uom = 'ECH',
            obj1.manufacturerCode = basicDataResult[0].manufacturerPartNo,
            obj1.manufacturerName = basicDataResult[0].manufacturerPartNo,

            outBoundLines.push(obj1);
            this.outBoundLinesArray = obj;
        }, err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        })
      });

      let obj: any = {};
      let soHeader: any = {};
      soHeader.storeID = "HYN";
      soHeader.storeName = "Hundai";
      soHeader.salesOrderNumber = 'SO-' + this.generateRandomNumber();
      soHeader.pickListNumber = 'PI-' + this.generateRandomNumber();  
      soHeader.branchCode = this.auth.plantId;
      soHeader.companyCode = this.auth.companyId;
      soHeader.requiredDeliveryDate = this.cs.dateMMYY(new Date());
      soHeader.status= 'Active';  
      
      obj.salesOrderHeader = soHeader;
      obj.salesOrderLine = outBoundLines;
    
    }, err => {
      this.spin.hide();
      this.cs.commonerror(err);
    })
  }

  callSave(){
    this.generatePickList(this.outBoundLinesArray)
  }
  generatePickList(obj) {
    this.spin.show();
    this.sub.add(this.service.createShipmentOrderV2(obj).subscribe(res => {
      if (res) {
        this.toastr.success("Order created successfully!", "Notification");
        this.spin.hide();
      }

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));



  }
}

