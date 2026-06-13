import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BinLocationComponent } from 'src/app/main-module/Outbound/order-management/ordermanagement-main/bin-location/bin-location.component';
import { OrdermanagementService } from 'src/app/main-module/Outbound/order-management/ordermanagement-main/ordermanagement.service';
import { ProposedBinLocationComponent } from 'src/app/main-module/Outbound/order-management/ordermanagement-main/proposed-bin-location/proposed-bin-location.component';
import { ReallocateComponent } from 'src/app/main-module/Outbound/order-management/ordermanagement-main/reallocate/reallocate.component';

@Component({
  selector: 'app-inventory-lines',
  templateUrl: './inventory-lines.component.html',
  styleUrls: ['./inventory-lines.component.scss']
})
export class InventoryLinesComponent implements OnInit {
  orderManagement: any[] = [];
  selectedOrderManagement: any[] = [];
  @ViewChild('orderManagementTag') orderManagementTag: Table | any;


  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;
  storecodeList: any;
  currentDate = new Date();
  fifteenDaysDate = new Date();
  ondeDaysBeforeDate = new Date();
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
  reallocate(): void {

    const dialogRef = this.dialog.open(ReallocateComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '9%', },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  binlocation(): void {

    const dialogRef = this.dialog.open(BinLocationComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      position: { top: '9%', },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
  
  title1 = "Outbound";
  title2 = "Order Management";
  constructor(private service: OrdermanagementService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService, public dialog: MatDialog,
    private spin: NgxSpinnerService, private router: Router,
    private auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,) { }
  sub = new Subscription();
  RA: any = {};
  ngOnInit(): void {
   ;
    //this.RA = this.auth.getRoleAccess(this.screenid);
    this.fifteenDaysDate.setDate(this.currentDate.getDate() + 7);
    this.ondeDaysBeforeDate.setDate(this.currentDate.getDate() - 1);
    //this.getstorename()
    this.search(true);
  }


  warehouseId = this.auth.warehouseId
  statusId = 48;
  searhform = this.fb.group({
    endRequiredDeliveryDate: [],
    manufacturerName:[],
    endOrderDate: [,],
    //endOrderDate : [this.currentDate, ],
    itemCode: [],
    outboundOrderTypeId: [],
    partnerCode: [],
    preOutboundNo: [],
    refDocNumber: [],
    soType: [],
    companyCodeId: [[this.auth.companyId]],
    plantId: [[this.auth.plantId]],
    languageId: [[this.auth.languageId]],
    startOrderDate: [,],
    // startOrderDate: [this.yesterdayDate, ],
    startRequiredDeliveryDate: [],
   statusId: [], //showing only unallocated //[42, 43, 47]
    warehouseId: [[this.auth.warehouseId]],

  });




  dropdownSettings = {
    singleSelection: false,
    text: "Select",
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    enableSearchFilter: true,
    badgeShowLimit: 2
  };

  itemCodeListselected: any[] = [];
  itemCodeList: any[] = [];

  outboundOrderTypeIdListselected: any[] = [];
  outboundOrderTypeIdList: any[] = [];

  partnerCodeListselected: any[] = [];
  partnerCodeList: any[] = [];

  preOutboundNoselected: any[] = [];
  preOutboundNoList: any[] = [];

  refDocNumberListselected: any[] = [];
  refDocNumberList: any[] = [];

  soTypeListselected: any[] = [];
  soTypeList: any[] = [];

  statusIdListselected: any[] = [];
  statusIdList: any[] = [];

  // getstorename() {



  //   this.sub.add(this.service.GetStoreCode().subscribe(res => {
  //     this.storecodeList = res;
  //     // this.spin.hide();
  //   },
  //     err => {
  //       this.cs.commonerrorNew(err);;
  //       //  this.spin.hide();
  //     }));
  // }


  search(ispageload = false) {
    if (!ispageload) {
      //dateconvertion
      this.searhform.controls.endRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endRequiredDeliveryDate.value));
      this.searhform.controls.startRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startRequiredDeliveryDate.value));

      this.searhform.controls.endOrderDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endOrderDate.value));
      this.searhform.controls.startOrderDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startOrderDate.value));

    }
    this.searhform.controls.endOrderDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.endOrderDate.value));
    this.searhform.controls.startOrderDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.startOrderDate.value));
    this.spin.show();
    this.searhform.controls.itemCode.patchValue([this.data.itm_code]);
    this.searhform.controls.manufacturerName.patchValue([this.data.mfr_code]);
    this.service.searchSpark(this.searhform.value).subscribe(res => {
      let result = res; //.filter((x: any) => x.statusId == 47 || x.statusId == 42 || x.statusId == 43);

      let deletionResult = result.filter((x: any) => x.deletionIndicator == 0);
      if (ispageload) {
        let tempitemCodeList: any[] = []
        const itemCode = [...new Set(deletionResult.map(item => item.itemCode))].filter(x => x != null)
        itemCode.forEach(x => tempitemCodeList.push({ value: x, label: x }));
        this.itemCodeList = tempitemCodeList;

        let tempoutboundOrderTypeIdList: any[] = []
        //const outboundOrderTypeId = [...new Set(deletionResult.map(item => item.outboundOrderTypeId))].filter(x => x != null)
        //outboundOrderTypeId.forEach(x => tempoutboundOrderTypeIdList.push({ value: x, label: ref }));
        res.forEach(x => tempoutboundOrderTypeIdList.push({ value: x.outboundOrderTypeId, label: x.referenceDocumentType }));
        this.outboundOrderTypeIdList = tempoutboundOrderTypeIdList;
        this.outboundOrderTypeIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.outboundOrderTypeIdList);

        let temppartnerCodeList: any[] = []
        const partnerCode = [...new Set(deletionResult.map(item => item.partnerCode))].filter(x => x != null)
        partnerCode.forEach(x => temppartnerCodeList.push({ value: x, label: x }));
        this.partnerCodeList = temppartnerCodeList;

        let temppreOutboundNoList: any[] = []
        const preOutboundNo = [...new Set(deletionResult.map(item => item.preOutboundNo))].filter(x => x != null)
        preOutboundNo.forEach(x => temppreOutboundNoList.push({ value: x, label: x }));
        this.preOutboundNoList = temppreOutboundNoList;

        let temprefDocNumberList: any[] = []
        const refDocNumber = [...new Set(deletionResult.map(item => item.refDocNumber))].filter(x => x != null)
        refDocNumber.forEach(x => temprefDocNumberList.push({ value: x, label: x }));
        this.refDocNumberList = temprefDocNumberList;

        let tempsoTypeList: any[] = []
        const soType = [...new Set(deletionResult.map(item => item.soType))].filter(x => x != null)
        soType.forEach(x => tempsoTypeList.push({ value: x, label: x }));
        this.soTypeList = tempsoTypeList;

        let tempstatusIdList: any[] = []
        // const statusId = [...new Set(deletionResult.map(item => item.statusId))].filter(x => x != null)
        res.forEach(x => tempstatusIdList.push({ value: x.statusId, label: x.statusDescription }));
        this.statusIdList = tempstatusIdList;
        this.statusIdList = this.cs.removeDuplicatesFromArrayNewstatus(this.statusIdList);
      }

      this.orderManagement = deletionResult;
      this.spin.hide();
    }, err => {

      this.cs.commonerrorNew(err);;
      this.spin.hide();

    });


  }
  reload() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
  }


  Unallocate(data: any) {
    this.spin.show();
    this.sub.add(this.service.UnallocateV2(data.itemCode, data.lineNumber, data.partnerCode, data.preOutboundNo, data.refDocNumber, data.warehouseId, data.proposedPackBarCode, data.proposedStorageBin, this.auth.companyId, this.auth.plantId, this.auth.languageId).subscribe(res => {
      this.spin.hide();
      this.toastr.success("Unallocated successfully", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      this.search();
    }, err => {

      this.cs.commonerrorNew(err);;
      this.spin.hide();

    }));
  }
  Allocate(data: any) {
    this.spin.show();
    this.sub.add(this.service.AllocateV2(data.itemCode, data.lineNumber, data.partnerCode, data.preOutboundNo, data.refDocNumber, data.warehouseId, this.auth.companyId, this.auth.plantId, this.auth.languageId).subscribe(res => {
      
      this.spin.hide();
      if (res.allocatedQty > 0) {
        this.toastr.success("Allocated successfully", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
      }

      if (res.allocatedQty <= 0) {
        this.toastr.info("Stock not available for allocation",
          "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
      }

      this.search();
    }, err => {

      this.cs.commonerrorNew(err);;
      this.spin.hide();

    }));
  }

  downloadexcel() {
    // if (excel)
    var res: any = [];
    this.orderManagement.forEach(x => {
      res.push({
        "Branch":x.plantDescription,
        "Warehouse":x.warehouseDescription,
        "Target Branch":x.targetBranchCode,
        "Token Number":x.tokenNumber,
        "Sales Order No":x.salesOrderNumber,
        "Status  ": x.statusDescription,
        "Order No  ": x.refDocNumber,
        'Order Type': x.referenceDocumentType,
        "Line No": x.lineNumber,
        "Mfr Name":x.manufacturerName,
        "Part No": x.itemCode,
        "Order Qty": x.orderQty,
        "Inv Qty": x.inventoryQty,
        "Allocated Qty": x.allocatedQty,
        "HU Serial No": x.partnerItemBarcode,
        "Description": x.description,
        "Preoutbound No": x.preOutboundNo,
        "Level": x.el,
        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Order Management");
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  OnItemDeSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }
  onDeSelectAll(items: any) {
    console.log(items);
  }
  onChange() {
    const choosen = this.selectedOrderManagement[this.selectedOrderManagement.length - 1];
    this.selectedOrderManagement.length = 0;
    this.selectedOrderManagement.push(choosen);
  }
  allocateBulk() {
    if (this.selectedOrderManagement.length === 0) {
      this.toastr.error("Kindly select any row", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
      this.spin.show();
      this.sub.add(this.service.allocateBatch(this.selectedOrderManagement).subscribe(res => {
        this.spin.hide();

        if (res) {
          this.toastr.success("Allocated successfully", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });
        }

        // if (res.allocatedQty > 0) {
        //   this.toastr.success("Allocated successfully", "Notification", {
        //     timeOut: 2000,
        //     progressBar: false,
        //   });
        // }
        
  
        // if (res.allocatedQty <= 0) {
        //   this.toastr.info("Stock not available for allocation",
        //     "Notification", {
        //     timeOut: 2000,
        //     progressBar: false,
        //   });
        // }
  
        this.search();
      }, err => {
  
        this.cs.commonerrorNew(err);;
        this.spin.hide();
  
      }));
  }
  unallocateBulk() {
    if (this.selectedOrderManagement.length === 0) {
      this.toastr.error("Kindly select any row", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      return;
    }
      this.spin.show();
      this.sub.add(this.service.unallocateBatch(this.selectedOrderManagement).subscribe(res => {
        this.spin.hide();

        if (res) {
          this.toastr.success("Unallocated successfully", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });
        }

        // if (res.allocatedQty > 0) {
        //   this.toastr.success("Allocated successfully", "Notification", {
        //     timeOut: 2000,
        //     progressBar: false,
        //   });
        // }
        
  
        // if (res.allocatedQty <= 0) {
        //   this.toastr.info("Stock not available for allocation",
        //     "Notification", {
        //     timeOut: 2000,
        //     progressBar: false,
        //   });
        // }
  
        this.search();
      }, err => {
  
        this.cs.commonerrorNew(err);;
        this.spin.hide();
  
      }));
  }

  iotBinLocation(line): void {
    this.spin.show()
    setTimeout(() => {
     this.spin.hide();
      const dialogRef = this.dialog.open(ProposedBinLocationComponent, {
        disableClose: true,
        width: '70%',
        maxWidth: '80%',
        position: { top: '9%', },
        data: {line},
      });
  
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }, 2000);

  }
}

