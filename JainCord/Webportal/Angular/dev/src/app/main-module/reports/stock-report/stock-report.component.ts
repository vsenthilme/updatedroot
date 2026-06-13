import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, of, Subscription } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MasterService } from 'src/app/shared/master.service';
import { ReportsService } from '../reports.service';
import { StockListComponent } from './stock-list/stock-list.component';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { Table } from 'primeng/table';
export interface variant {


  no: string;
  actions: string;
  status: string;
  warehouseno: string;
  preinboundno: string;
  countno: string;
  by: string;
  damage: string;
  available: string;
  hold: string;
}

interface SelectItem {
  item_id: string;
  item_text: string;
}

const ELEMENT_DATA: variant[] = [
  { no: "1", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "2", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "3", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "4", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "5", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "6", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "7", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "8", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "9", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "10", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "11", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },
  { no: "12", warehouseno: 'readonly', by: 'readonly', countno: 'readonly', damage: 'readonly', hold: 'readonly', available: 'readonly', preinboundno: 'readonly', status: 'readonly', actions: 's' },

];
@Component({
  selector: 'app-stock-report',
  templateUrl: './stock-report.component.html',
  styleUrls: ['./stock-report.component.scss']
})
export class StockReportComponent implements OnInit {
  screenid=3163;
  stock: any[] = [];
  selectedstock : any[] = [];
  @ViewChild('stockreporTag') stockreporTag: Table | any;
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;
  itemText = '';
  itemCode = '';
  manufacturingName = '';
  stockTypeText = 'All';
  warehouseList: any[] = [];
  isValid = true;

  selectedItems: any[] = [];

  multiselectWarehouseList: any[] = [];

  multiWarehouseList: any[] = [];

  dropdownSettings: IDropdownSettings = {
    singleSelection: false,
    idField: 'item_id',
    textField: 'item_text',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };

 

  pageNumber = 0;
  pageSize = 100;
  totalRecords = 0;

  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

  showFiller = false;
  // displayedColumns: string[] = ['warehouseno', 'countno', 'by', 'preinboundno', 'status', 'damage', 'hold', 'available'];

  displayedColumns: string[] = ['warehouseId', 'itemCode', 'manufacturerSKU', 'itemText', 'onHandQty', 'damageQty', 'holdQty', 'availableQty'];

  sub = new Subscription();
  ELEMENT_DATA: variant[] = [];

 

  constructor(
    private router: Router,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private masterService: MasterService,
    public cs: CommonService,
    private reportService: ReportsService,
    public auth: AuthService
  ) { }

  cols: any[] =  [
    { header: 'Company', field: 'companyDescription', format: 'normal' , showFooter: false },
    { header: 'Plant', field: 'plantDescription', format: 'normal' , showFooter: false },
    { header: 'Warehouse', field: 'warehouseDescription', format: 'normal' , showFooter: false },
    { header: 'Mfr Name', field: 'manufacturerName', format: 'normal' , showFooter: false },
    { header: 'Part No', field: 'itemCode', format: 'normal' , showFooter: false },
    { header: 'Description', field: 'itemText', format: 'normal' , showFooter: false },
    { header: 'Inventory Quantity', field: 'invQty', format: 'normal' , showFooter: true },
    { header: 'Allocated Quantity', field: 'allocQty', format: 'normal' , showFooter: true },
    { header: 'Total Quantity', field: 'totalQty', format: 'normal' , showFooter: true }
  ];

  calculateFooterTotal(field: string): number {
    let total = 0;
    this.stock.forEach(item => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return total;
  }
  ngOnInit(): void {
    // this.auth.isuserdata();

    this.dropdownfill();
  }


  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  div1Function() {
    this.table = !this.table;
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

  
  reset() {
    this.itemCode = '';
    this.itemText = '';
    this.stockTypeText = '';
  }

  filtersearch() {
    if ((this.selectedItems != null && this.selectedItems != undefined && this.selectedItems.length > 0) &&
      (this.stockTypeText != null && this.stockTypeText != undefined && this.stockTypeText != '')) {
      this.spin.show();
      this.getStockReportData();
      this.table = true;
      this.search = false;
      this.fullscreen = false;
      this.back = true;
      this.isValid = true;
    }
    else {
      this.toastr.error("Please fill the required fields. Required fields are Warehouse No, Stock Type", "", {
        timeOut: 2000,
        progressBar: false,
      });

      this.isValid = false;
    }
  }

  filterResult: any[] = [];
  stocknew:any[]=[];

  getStockReportData() {
    this.filterResult = []
    let selectedWarehouseList: any[] = [];
    // this.selectedItems.forEach(element => {
    //   selectedWarehouseList.push(element.item_id);
    // });
   
    let obj: any = {};
    if(this.itemCode == ""){
      obj.itemCode =null
    }
    else{
      obj.itemCode = this.itemCode;
    }
    if(this.manufacturingName == ""){
      obj.manufacturerName=null
    }
    else{
      obj.manufacturerName=this.manufacturingName;
    }
    obj.companyCodeId = this.auth.companyId;
    obj.languageId = this.auth.languageId;
    obj.plantId = this.auth.plantId;
    obj.warehouseId = this.auth.warehouseId;
    obj.stockTypeText=this.stockTypeText;
   // obj.manufacturerName = this.manufacturingName;
    if(this.itemText== ""){
      obj.itemText=null
    }
    else{
    obj.itemText = this.itemText;
    }
    
    
    this.reportService.getAllStockReportStoreProcedure(obj).subscribe(
      result => {
        result.forEach(element => {
          if(element.onHandQty < 0){
            element.onHandQty = 0;
          }
        });
        result.forEach(element => {
          if(element.availableQty < 0){
            element.availableQty = 0;
          }
        });

        result.forEach(element => {
          if(element.availableQty != 0){
           // console.log(element)
           this.filterResult.push(element)
          }
        });
      //  this.filterResult = this.cs.removeDuplicatesFromArrayList(this.filterResult, 'itemCode')
        this.spin.hide();
        this.stock =this.filterResult;
        this.stocknew=this.stock;
        this.calculateTotals(this.stocknew);
      },
      error => {
        
        this.spin.hide();
      }
    );
  }

  pageHandler($event: PageEvent) {
    this.pageNumber = $event.pageIndex;
    this.pageSize = $event.pageSize;
    this.filtersearch();
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






  itemCodeChanged(e){
    this.manufacturingName = e.manufacturingName;
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


  downloadexcel() {
    const exportData = this.stock.map(item => {
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
    this.cs.exportAsExcel(exportData, 'Stock Report By Item');
  }

  

  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),
    })
      .subscribe(({ warehouse }) => {
        if(this.auth.userTypeId != 1){
          this.warehouseList = warehouse.filter((x: any) => x.warehouseId == this.auth.warehouseId);
        }else{
          this.warehouseList = warehouse
        }
        this.warehouseList.forEach(x => this.multiWarehouseList.push({ value: x.warehouseId, label: x.warehouseId + (x.description == null ? '' : '- ' + x.description) }));
        this.multiselectWarehouseList = this.multiWarehouseList;
        console.log(this.multiselectWarehouseList)
        this.multiselectWarehouseList.forEach((warehouse: any) => {
          if (warehouse.value == this.auth.warehouseId)
            this.selectedItems = [warehouse.value];
        })
        this.multiselectWarehouseList =  this.cs.removeDuplicatesFromArrayNewstatus(this.multiselectWarehouseList)
      }, (err) => {
        this.toastr.error(
          err,
          ""
        );
      });
    this.spin.hide();

  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }

  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
  }

  getOnhandQty(){
    let total = 0;
    this.stock.forEach(element => {
      total = total + (element.allocQty != null ? element.allocQty : 0);
    })
    return (Math.round(total * 100) / 100);
  }
  getDamageQty(){
    let total = 0;
    this.stock.forEach(element => {
      total = total + (element.totalQty != null ? element.totalQty : 0);
    })
    return (Math.round(total * 100) / 100);
  }

  getHoldQty(){
    let total = 0;
    this.stock.forEach(element => {
      total = total + (element.invQty != null ? element.invQty : 0);
    })
    return (Math.round(total * 100) / 100);
  }
  getavailableQty(){
    let total = 0;
    this.stock.forEach(element => {
      total = total + (element.availableQty != null ? element.availableQty : 0);
    })
    return (Math.round(total * 100) / 100);
  }
  onChange() {
    const choosen= this.selectedstock[this.selectedstock.length - 1];   
    this.selectedstock.length = 0;
    this.selectedstock.push(choosen);
  }
  
  handleSearch1(filterInput){
    this.calculateTotals(this.stockreporTag.filteredValue);
  }

  filteredInventory: any[] = []; // Filtered inventory data array, if applicable
  totalInventoryQuantity: number = 0; // Total inventory quantity
  totalAllocatedQuantity: number = 0; // Total allocated quantity
totalQty1:number =0;
  calculateTotals(data?: any[]): void {
   this.totalInventoryQuantity = 0;
   this.totalAllocatedQuantity = 0;
   this.totalQty1=0;
    if (data && data.length > 0) {
      this.totalInventoryQuantity = data.reduce((total, item) => total + (item.invQty != null ? item.invQty : 0), 0);
      this.totalAllocatedQuantity = data.reduce((total, item) => total + (item.allocQty != null ? item.allocQty : 0), 0);
      this.totalQty1=data.reduce((total, item) => total + (item.totalQty != null ? item.totalQty : 0), 0);
    } else {
      this.stock.forEach(x => {
        this.totalInventoryQuantity = 0;
        this.totalAllocatedQuantity = 0;
        this.totalQty1=0;
      })

    // }
  
  }
}
}