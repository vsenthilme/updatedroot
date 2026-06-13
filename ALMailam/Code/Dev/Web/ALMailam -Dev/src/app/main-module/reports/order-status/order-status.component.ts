import { SelectionModel } from '@angular/cdk/collections';
import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of, Subscription } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { DeliveryService } from '../../Outbound/delivery/delivery.service';
import { ReportsService } from '../reports.service';

import { FormBuilder } from '@angular/forms';
import { Table } from 'primeng/table';
// export interface any {


//   no: string;
//   actions: string;
//   status: string;
//   warehouseno: string;
//   preinboundno: string;
//   countno: string;
//   by: string;
//   damage: string;
//   available: string;
//   hold: string;
//   stype: string;
//   balance: string;
//   opening: string;
//   time: string;
//   perct: string;
//   status1: string;
// }

interface SelectItem {
  item_id: string;
  item_text: string;
}

interface Outbound {
  warehouseId?: string[];
}

export class OutboundCls implements Outbound {
  constructor(public warehouseId?: string[]) {

  }
}

// const ELEMENT_DATA: any[] = [
//   { no: "1", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "2", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "3", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "4", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "5", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "6", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "7", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "8", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "9", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "10", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "11", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
//   { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', perct: 'readonly', status1: 'readonly', stype: 'readonly', opening: 'readonly', balance: 'readonly', time: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },

// ];
@Component({
  selector: 'app-order-status',
  templateUrl: './order-status.component.html',
  styleUrls: ['./order-status.component.scss']
})
export class OrderStatusComponent implements OnInit {
  screenid=3168;
  inbound: any[] = [];
  selectedinbound : any[] = [];
  @ViewChild('inboundTag') inboundTag: Table | any;
    isShowDiv = false;
    table = false;
    fullscreen = false;
    search = true;
    back = false;
    selectedItems: any[] = [];
    selectedItems2: SelectItem[] = [];
    selectedItems3: SelectItem[] = [];
    selectedItems4: SelectItem[] = [];
    selectedWarehouse = '';
  
    selectedOrderTypeList: any[] = [];
    selectedStatusIdList: any[] = [];
    selectedCustomerCodeList: any[] = [];
  
    sendingOrderTypeList: any[] = [];
    sendingStatusIdList: any[] = [];
    sendingCustomerCodeList: any[] = [];
  
    warehouseList: any[] = [];
    multiWarehouseList: any[] = [];
    multiOrderTypeList: any[] = [];
    multiStatusList: any[] = [];
    multiCustomerCodeList: SelectItem[] = [];
  
    outboundParam = new OutboundCls();
  
  
   
    sendingFromCreatedOn = '';
    sendingToCreatedOn = '';
  
    multiselectWarehouseList: SelectItem[] = [];
    multiselectOrderTypeList: SelectItem[] = [];
    multiselectStatusList: SelectItem[] = [];
    multiselectCustomerCodeList: SelectItem[] = [];
  
    dropdownSettings: IDropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  
    dropdownSettings2: IDropdownSettings = {
      singleSelection: true,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  
    div1Function() {
      this.table = !this.table;
    }
    showFloatingButtons: any;
    toggle = true;
    public icon = 'expand_more';
    sourceBranch1:any[]=[];
    sourceBranch:any[]=[];
    showFiller = false;
    displayedColumns: string[] = ['warehouseno', 'by', 'status', 'damage', 'hold', 'available', 'orderType','orderedQty', 'deliveredQty', 'orderReceivedDate', 'deliveryConfirmedOn', 'expectedDeliveryDate', 'percentageOfDelivered', 'statusId',];
    sub = new Subscription();
    ELEMENT_DATA: any[] = [];
    constructor(
      private router: Router, public toastr: ToastrService, private spin: NgxSpinnerService, private reportService: ReportsService, private fb: FormBuilder,
      public datepipe: DatePipe, private masterService: MasterService, private deliveryService: DeliveryService, public auth: AuthService, public cs: CommonService,) { this.sourceBranch = [
        {value: 200, label: '200-ASP HO Branch'},
        {value: 201, label: '201-Shop 1, Swk. Main (closed)'},
        {value: 202, label: '202-Shop 2, Shq.'},
        {value: 203, label: '203-Shop 3, Swk (closed)'},
        {value: 204, label: '204-Shop 4, Jhr.'},
        {value: 205, label: '205-Shop 5, Swk.'},
        {value: 206, label: '206-Shop 6, Swk. H.O (closed)'},
        {value: 207, label: '207-Shop 7, Fhl.(closed)'},
        {value: 208, label: '208-Shop 9, Swk, Sp Ins.'},
        {value: 209, label: '209-Shop 10, Swk-AAT (closed)'},
        {value: 210, label: '210-Shop No.8, H.O.(Exports) Shuwa'},
        {value: 211, label: '211-Out Door Sales'},
        {value: 212, label: '212-AMR - Warehouse 2, Amghara'},
        {value: 213, label: '213-WHO3 New Farwania F2'},
        {value: 214, label: '214-WHO4 Old Farwania F1'},
        {value: 215, label: '215-Van Sales'},
        {value: 216, label: '216-ASP-Sales Return Warranty Dmg'},
        {value: 217, label: '217-Reserve Parts'},
        {value: 218, label: '218-Ardiya New WH'},
        {value: 219, label: '219-Shop 7,Fhl New'},
        {value: 220, label: '220-Ins Rain Claim'},
        {value: 221, label: '221-Farw New'},
        {value: 222, label: '222-AutoLab Shwk'},
    ];
    
    this.sourceBranch1 = [
      {value: 100, label: '100-JSP HO Branch'},
      {value: 101, label: '101-JAP - Shop 1, Swk. Main'},
      {value: 102, label: '102-JAP - Shop 2, Swk (closed)'},
      {value: 103, label: '103-JAP - Shop 3, Shq.'},
      {value: 104, label: '104-JAP - Shop 4, Jhr.'},
      {value: 105, label: '105-JAP - Shop 5, Swk. H O(closed)'},
      {value: 106, label: '106-JAP - Shop 6, Fhl.(closed)'},
      {value: 107, label: '107-JAP - Shop 7, Swk.'},
      {value: 108, label: '108-JAP - Shop 9, Sp, Ins.'},
      {value: 109, label: '109-JAP - Shop 10, Swk AAT(closed)'},
      {value: 110, label: '110-JAP- Shop 10, Swk AAT-(closed)'},
      {value: 111, label: '111-Shop No.8, H.O.(Exports) Shuwa'},
      {value: 112, label: '112-Reserve Parts - OD'},
      {value: 113, label: '113-JAP - Warehouse 1, Ardiya'},
      {value: 114, label: '114-WHO2 Old Farwania F1'},
      {value: 115, label: '115-JAP - Warehouse 3, Amghara'},
      {value: 116, label: '116-WHO2 New Farwania F2'},
      {value: 117, label: '117-JAP-Sales Return Warranty Dmg'},
      {value: 118, label: '118-Amg New_Fhl'},
      {value: 119, label: '119-WH07SWK (AAT-9)'},
      {value: 120, label: '120-Van Sales'},
      {value: 121, label: '121-Ins Rain Claim'},
      {value: 122, label: '122-Ardiya New WH'},
      {value: 123, label: '123-JAP - Shop 6, Fhl New'},
      {value: 125, label: '125-JAP - AutoLab Shwk'},
    ];    this.multiOrderType = [
      {value: 3, label: ' PICK LIST'},
      {value: 1, label: 'WMS to WMS'},
      {value: 0, label: 'WMS to Non-WMS'},
      {value: 2, label: 'PURCHASE RETURN'},
  ]; this.multistatus = [
    {value: 59, label: 'Delivered'},
    {value: 57, label: 'Delivery Open'},
    {value: 47, label: 'No Stock Item'},
    {value: 48, label: 'In Picking'},
    {value: 50, label: 'In Quality'},
    {value: 43, label: 'ALLOCATED'},
    {value: 51, label: 'Picker Denial'},
  ];}
    routeto(url: any, id: any) {
      sessionStorage.setItem('crrentmenu', id);
      this.router.navigate([url]);
    
   
    }
    animal: string | undefined;
  
  
    form = this.fb.group({
      warehouseId: [[this.auth.warehouseId]],
      companyCode: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]],
      plantId: [[this.auth.plantId]],
      fromDeliveryDate: [],
      itemCode: [],
      itemCodeFE: [],
      manufacturerName: [],
      salesOrderNumber:[],
      targetBranchCode:[],
      refDocNumber: [],
      statusId: [],
      orderType:[],
      toDeliveryDate: [],
      fromCreatedOn:[],
      fromCreatedOnFE:[],
      toCreatedOn:[],
      toCreatedOnFE:[],
     });
   
    ngOnInit(): void {
      //this.getDropdown();
    
       this.form.controls.companyCode.patchValue(this.auth.companyId);
       this.form.controls.plantId.patchValue(this.auth.plantId);
       this.form.controls.plantId.disable();
       this.form.controls.companyCode.disable();
       this.form.controls.warehouseId.patchValue(this.auth.warehouseId);
       this.form.controls.warehouseId.disable();
       let currentDate = new Date();
       let currentMonthStartDate = new Date();
       currentMonthStartDate.setDate(1); 
       let yesterday = new Date();
   yesterday.setDate(currentDate.getDate() - 1);
   this.form.controls.toCreatedOnFE.patchValue(currentDate);
       this.form.controls.fromCreatedOnFE.patchValue(currentMonthStartDate);
    }
     multiselectItemCodeList: any[] = [];
     itemCodeList: any[] = [];
  
     onItemType(searchKey) {
       let searchVal = searchKey?.filter;
       if (searchVal !== '' && searchVal !== null) {
         forkJoin({
           itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId).pipe(catchError(err => of(err))),
         })
           .subscribe(({ itemList }) => {
             if (itemList != null && itemList.length > 0) {
               this.multiselectItemCodeList = [];
               this.itemCodeList = itemList;
               this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({value: x.itemCode, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description, manufacturingName: x.manufacturerName }))
             }
           });
      }
    }
   
     multisalesOrderNo: any[]=[];
     multiOrderNo: any[] = [];
     multimanufacturerName:any[]=[];
     multitarget:any[]=[];
     multistatus:any[]=[];
     multiOrderType:any[]=[];
       getDropdown(){
         this.spin.show();
         let obj: any = {};
         obj.companyCode = [this.auth.companyId];
         obj.languageId = [this.auth.languageId];
         obj.plantId = [this.auth.plantId];
         obj.warehouseId = [this.auth.warehouseId];
         this.sub.add(this.reportService.getoutboundlinestream(obj).subscribe(res => {
           res.forEach((x: any) =>
            this.multiOrderNo.push({ value: x.refDocNumber, label: x.refDocNumber })
            );
            res.forEach((x: any) => {
              if (x.salesOrderNumber !== null) {
                  this.multisalesOrderNo.push({ value: x.salesOrderNumber, label: x.salesOrderNumber });
              }
          });
            res.forEach((x: any) =>
            this.multimanufacturerName.push({ value: x.manufacturerName, label: x.manufacturerName })
            );
            res.forEach((x: any) =>
            this.multiOrderType.push({ value: x.outboundOrderTypeId, label: x.referenceDocumentType })
            );
            res.forEach((x: any) =>
            this.multistatus.push({ value: x.statusId, label: x.statusDescription })
            );
            res.forEach((x: any) =>
            this.multitarget = res
            .filter((x: any) => x.targetBranchCode !== null)
            .map((x: any) => ({ value: x.targetBranchCode, label: x.targetBranchCode })));
          
           this.multiOrderNo=this.cs.removeDuplicateInArray(this.multiOrderNo);
            this.multimanufacturerName=this.cs.removeDuplicateInArray(this.multimanufacturerName);
            this.multistatus=this.cs.removeDuplicateInArray(this.multistatus);
            this.multitarget=this.cs.removeDuplicateInArray(this.multitarget);
            this.multisalesOrderNo=this.cs.removeDuplicateInArray(this.multisalesOrderNo);
            this.multiOrderType=this.cs.removeDuplicateInArray(this.multiOrderType);
           this.spin.hide();
         }, err => {
           this.spin.hide();
           this.cs.commonerrorNew(err);
  
         }))
       }
  
  
  
  
    
     
      
    
     itemCode: any;
     confirmedStorageBin: any;
     refDocNumber = '';
     warehouseId = '';
     manufacturingName:any;
  inboundnew:any[]=[];
     itemCodeChange(e){
      this.manufacturingName = e.manufacturingName;
    }
     filtersearch(){
       if(this.form.controls.itemCodeFE.value != null){
         this.form.controls.itemCode.patchValue([this.form.controls.itemCodeFE.value]);
    }
       if(this.form.controls.itemCodeFE.value == null){
         this.form.controls.itemCode.patchValue(this.form.controls.itemCodeFE.value);
    }
       this.form.controls.warehouseId.patchValue([this.auth.warehouseId]);
       this.form.controls.companyCode.patchValue([this.auth.companyId]);
       this.form.controls.languageId.patchValue([this.auth.languageId]);
       this.form.controls.plantId.patchValue([this.auth.plantId]);
   this.spin.show();
   this.form.controls.toDeliveryDate.patchValue(this.cs.day_callapiSearch(this.form.controls.toCreatedOnFE.value));
   this.form.controls.fromDeliveryDate.patchValue(this.cs.day_callapiSearch(this.form.controls.fromCreatedOnFE.value));
   if(this.form.controls.refDocNumber.value ==null || this.form.controls.refDocNumber.value == ""){
    this.form.controls.refDocNumber.patchValue(null);
    }
    else{
      this.form.controls.refDocNumber.patchValue([this.form.controls.refDocNumber.value]);
    }
  if(this.form.controls.salesOrderNumber.value ==null){
  this.form.controls.salesOrderNumber.patchValue(this.form.controls.salesOrderNumber.value);
  }
  else{
    this.form.controls.salesOrderNumber.patchValue([this.form.controls.salesOrderNumber.value]);
  }
  
  
      let obj: any = {};
      obj.companyCodeId = [this.auth.companyId];
      obj.languageId = [this.auth.languageId];
      obj.plantId = [this.auth.plantId];
   obj.warehouseId = [this.auth.warehouseId];
   obj.itemCode = [this.form.controls.itemCodeFE.value];
   obj.refDocNumber = this.form.controls.refDocNumber.value;
   if(this.manufacturingName != null){
   this.form.controls.manufacturerName.patchValue([this.manufacturingName]);
      }
      else{
    this.form.controls.manufacturerName.patchValue(null);
      }
      if(this.form.controls.orderType.value == null){
        this.form.controls.orderType.patchValue(null);
      }
      else{
        this.form.controls.orderType.patchValue(this.form.controls.orderType.value);
      }
       this.sub.add(this.reportService.getoutboundline(this.form.getRawValue()).subscribe(res => {
         console.log(res)
         this.inbound = res;
        this.inboundnew=this.inbound;
        this.calculateTotals(this.inbound);
            
         this.table = true;
         this.search = false;
         this.fullscreen = false;
         this.back = true;
         this.spin.hide();
  
        },
         err => {
           this.cs.commonerrorNew(err);
          this.spin.hide();
         }));
    }
    handleSearch1(filterInput){
      if (filterInput) {
        this.calculateTotals(this.inboundTag.filteredValue);
      }
      else {
        this.calculateTotals(this.inbound);
    
      }
    }
  
  
    filteredInventory: any[] = []; // Filtered inventory data array, if applicable
    totalInventoryQuantity: number = 0; // Total inventory quantity
    totalAllocatedQuantity: number = 0; // Total allocated quantity
    totalQty2:number =0;
  totalQty1:number =0;
    calculateTotals(data?: any[]): void {
     this.totalInventoryQuantity = 0;
     this.totalAllocatedQuantity = 0;
     this.totalQty1=0;
     this.totalQty2=0;
      if (data && data.length > 0) {
        this.totalInventoryQuantity = data.reduce((total, item) => total + (item.orderQty != null ? item.orderQty : 0), 0);
        this.totalAllocatedQuantity = data.reduce((total, item) => total + Number(item.referenceField9 != null ? item.referenceField9 : 0), 0);
        this.totalQty1=data.reduce((total, item) => total + Number(item.referenceField10 != null ? item.referenceField10 : 0), 0);
        this.totalQty2=data.reduce((total, item) => total + (item.deliveryQty != null ? item.deliveryQty : 0), 0);
      } else {
        this.inbound.forEach(x => {
          this.totalInventoryQuantity =0;
          this.totalAllocatedQuantity = 0;
          this.totalQty1=0;
          this.totalQty2=0;
        })
  
      // }
    
      }
    } 
       
     
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
  
     ngOnDestroy() {
       if (this.sub != null) {
         this.sub.unsubscribe();
    }
              }
  
     @ViewChild(MatSort, { static: true })
     sort!: MatSort;
     @ViewChild(MatPaginator, { static: true })
     paginator: MatPaginator; // Pagination
     // Pagination
   
   
  
  
     totalRecords = 0;
    downloadexcel() {
      // if (excel)
      var res: any = [];
      this.inbound.forEach(x => {
        res.push({
           "Branch":x.plantDescription,
           "Warehouse":x.warehouseDescription,
           "Order No ": x.refDocNumber,
          "Order Type" : x.referenceDocumentType,
         "Sales Invoice Number":x.salesOrderNumber,
         "Sales Order No":x.salesOrderNumber,
         "Target Branch":x.targetBranchCode,
   
           "Mfr Name":x.manufacturerName,
           'Part No': x.itemCode,
           'Description': x.description,
           "Order Qty":x.orderQty,
           'Picker Qty': x.referenceField9,
           'QA Qty': x.referenceField10,
          'Delivered Qty': x.deliveryQty,
           'Created Date':this.cs.dateapiwithTime(x.createdOn),
           "Status ": x.statusDescription,
        });
  
      })
      this.cs.exportAsExcel(res, "Outbound Order Details Report");
    }
  
  
     togglesearch() {
       this.search = false;
       this.table = true;
       this.fullscreen = false;
       this.back = true;
     }
     backsearch() {
       this.table = true;
       this.search = true;
       this.fullscreen = true;
       this.back = false;
     }
  
  
     onItemSelect(item: any) {
     }
  
     onSelectAll(items: any) {
    }
  
    getorderQty(){
      let total = 0;
      this.inbound.forEach(x =>{
        total = total + (x.orderQty != null ? x.orderQty : 0)
      })
      return Math.round(total *100 / 100)
  }
    
  getpickedQty() {
    let total = 0;
    this.inbound.forEach(x => {
        total += x.referenceField9 != null ? parseFloat(x.referenceField9) : 0;
    });
    return parseFloat(total.toFixed(2)); // Round to two decimal places
  }
   
  getqaQty() {
    let total = 0;
    this.inbound.forEach(x => {
        total += x.referenceField10 != null ? parseFloat(x.referenceField10) : 0;
    });
    return parseFloat(total.toFixed(2)); // Round to two decimal places
  }
  
  getdeliveredQty(){
    let total = 0;
    this.inbound.forEach(x =>{
      total = total + (x.deliveredQty != null ? x.deliveredQty : 0)
    })
    return Math.round(total *100 / 100)
    }
   
   
   
     
     
   
     reset(){
      this.form.reset();
      this.form.controls.companyCode.patchValue([this.auth.companyId])
      this.form.controls.languageId.patchValue([this.auth.languageId])
      this.form.controls.plantId.patchValue([this.auth.plantId])
      this.form.controls.warehouseId.patchValue([this.auth.warehouseId])
      this.form.controls.manufacturerName.patchValue(null);
      this.manufacturingName=null;
    }
    }
   