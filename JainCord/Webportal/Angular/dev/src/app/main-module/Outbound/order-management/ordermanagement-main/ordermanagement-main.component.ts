import { SelectionModel } from "@angular/cdk/collections";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { IDropdownSettings } from "ng-multiselect-dropdown";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { CommonService, dropdownelement1 } from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { AssignInvoiceComponent } from "src/app/main-module/Inbound/preinbound/preinbound-new/assign-invoice/assign-invoice.component";
import { PreoutboundService } from "../../preoutbound/preoutbound.service";
import { BinLocationComponent } from "./bin-location/bin-location.component";
import { OrdermanagementService } from "./ordermanagement.service";
import { ReallocateComponent } from "./reallocate/reallocate.component";
import { Table } from "primeng/table";
import { ProposedBinLocationComponent } from "./proposed-bin-location/proposed-bin-location.component";
import { ScratchComponent } from "./proposed-bin-location/scratch/scratch.component";

export interface ordermanagement {
  no: string;
  lineno: string;
  partner: string;
  product: string;
  description: string;
  refdocno: string;
  variant: string;
  order: string;
  type: string;
  preoutboundno: string;
  uom: string;
  req: string;
  allocated: string;
  reallocated: string;
  status: string;
  sname: string;
  sotype: string;
  ref1: string;
  ref2: string;
  ref3: string;
}

const ELEMENT_DATA: ordermanagement[] = [
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },
  { no: "1", lineno: 'readonly', partner: 'readonly', product: 'readonly', sotype: 'readonly', sname: 'readonly', ref1: 'readonly', ref2: 'readonly', ref3: 'readonly', description: 'readonly', refdocno: 'readonly', variant: 'readonly', order: 'readonly', uom: 'dropdowm', req: 'readonly', allocated: 'readonly', reallocated: 'readonly', status: 'readonly', preoutboundno: 'readonly', type: 'readonly', },

];
@Component({
  selector: 'app-ordermanagement-main',
  templateUrl: './ordermanagement-main.component.html',
  styleUrls: ['./ordermanagement-main.component.scss']
})
export class OrdermanagementMainComponent implements OnInit {
  screenid = 3060;
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

  cols: any[] = [
    { header: 'Actions', field: 'actions', sortable: true, format: 'actions'},
    { header: 'Branch', field: 'plantDescription', sortable: true, format: 'normal'},
    { header: 'Warehouse', field: 'warehouseDescription', sortable: true, format: 'normal'},
    { header: 'Target Branch', field: 'targetBranchCode', sortable: true, format: 'target'},
    { header: 'Sales Order No', field: 'salesOrderNumber', sortable: true, format: 'normal'},
    { header: 'Customer Code', field: 'partnerCode', sortable: true, format: 'normal'},
    { header: 'Customer Name', field: 'partnerName', sortable: true, format: 'normal'},
    { header: 'Status', field: 'statusDescription', sortable: true, format: 'normal'},
    { header: 'Order No', field: 'refDocNumber', sortable: true, format: 'normal'},
    { header: 'Order Type', field: 'referenceDocumentType', sortable: true, format: 'normal'},
    { header: 'Line No', field: 'lineNumber', sortable: true, format: 'normal'},
    { header: 'Mfr Name', field: 'manufacturerName', sortable: true, format: 'normal'},
    { header: 'Part No', field: 'itemCode', sortable: true, format: 'normal'},
    { header: 'Batch Serial Number', field: 'proposedBatchSerialNumber', sortable: true, format: 'normal'},
    { header: 'Order Qty', field: 'orderQty', sortable: true, format: 'normal'},
    { header: 'Inventory Qty', field: 'inventoryQty', sortable: true, format: 'normal'},
    { header: 'Allocated Qty', field: 'allocatedQty', sortable: true, format: 'normal'},
    { header: 'Barcode Id', field: 'partnerItemBarcode', sortable: true, format: 'normal'},
    { header: 'Bin Location', field: 'proposedStorageBin', sortable: true, format: 'normal'},
    { header: 'Description', field: 'description', sortable: true, format: 'normal'},
    { header: 'Preoutbound No', field: 'preOutboundNo', sortable: true, format: 'normal'},
    { header: 'Level', field: 'levelId', sortable: true, format: 'normal'}
  ];

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
    public toastr: ToastrService, public dialog: MatDialog,
    private spin: NgxSpinnerService, private router: Router,
    public auth: AuthService,
    private fb: FormBuilder,
    public cs: CommonService,) { }
  sub = new Subscription();
  RA: any = {};
  ngOnInit(): void {
    this.RA = this.auth.getRoleAccess(this.screenid);
    this.fifteenDaysDate.setDate(this.currentDate.getDate() + 7);
    this.ondeDaysBeforeDate.setDate(this.currentDate.getDate() - 1);
    //this.getstorename()
    this.search(true);
  }


  warehouseId = this.auth.warehouseId
  statusId = 48;
  searhform = this.fb.group({
    endRequiredDeliveryDate: [],
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
   statusId: [[47, 48]], //showing only unallocated //[42, 43, 47]
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
    const exportData = this.orderManagement.map(item => {
      const exportItem: any = {};
      this.cols.forEach(col => {
        if (col.format == 'date') {
          exportItem[col.header] = this.cs.excelDate(item[col.field]);
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, 'Order Management');
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
       // position: { top: '9%', },
        data: {line},
      });
  
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }, 2000);

  }
}
