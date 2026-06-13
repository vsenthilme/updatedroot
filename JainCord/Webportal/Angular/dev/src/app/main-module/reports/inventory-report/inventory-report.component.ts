


import { SelectionModel } from "@angular/cdk/collections";
import { HttpErrorResponse } from "@angular/common/http";
import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { FormGroup, FormBuilder, FormControl, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialog } from "@angular/material/dialog";
import { MatPaginator, PageEvent } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { IDropdownSettings } from "ng-multiselect-dropdown";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { forkJoin, of, Subscription } from "rxjs";
import { catchError } from "rxjs/operators";
import { CommonService } from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { MasterService } from "src/app/shared/master.service";
import { ReportsService } from "../reports.service";
import { stockElement, StocksampleService } from "../stocksamplereport/stocksample.service";
import { Table } from "primeng/table";
import { InventoryLinesComponent } from "./inventory-lines/inventory-lines.component";
import { InventoryPutwayComponent } from "./inventory-putway/inventory-putway.component";
import { BasicdataService } from "../../Masters -1/masternew/basicdata/basicdata.service";


@Component({
  selector: 'app-inventory-report',
  templateUrl: './inventory-report.component.html',
  styleUrls: ['./inventory-report.component.scss']
})
export class InventoryReportComponent implements OnInit {
  screenid = 3164;
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;
  selectedCompany: any[] = [];
  warehouseList: any[] = [];
  itemCodeList: any[] = [];
  selectedplant: any[] = [];
  selectedWarehouseList: any[] = [];
  selectedItemCode = null;

  sendingWarehouseList: any[] = [];

  selectedItems: any[] = [];

  multiselectWarehouseList: any[] = [];
  multiselectItemCodeList: any[] = [];

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
  inventory: any[] = [];
  inventorynew: any[] = [];
  selectedbinner: any[] = [];
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  showFiller = false;
  displayedColumns: string[] = ['plantDescription', 'itemCode', 'referenceField8', 'manufacturerName', 'storageBin', 'referenceField10', 'barcodeId', 'inventoryQuantity', 'allocatedQuantity', 'total', 'stockTypeId'];
  sub = new Subscription();
  ELEMENT_DATA: stockElement[] = [];
  multibinclassId: any[] = [];
  pageSize = 500;
  pageNumber = 0;
  totalRecords = 0;
  storageBinCode: any = null;
  barcodeId: any = null;
  itemCode: any = null;
  constructor(public dialog: MatDialog,
    private service: StocksampleService,
    // private cas: CommonApiService,
    public toastr: ToastrService,
    private router: Router,
    private fb: FormBuilder,
    private spin: NgxSpinnerService,
    private cs: CommonService,
    private masterService: MasterService,
    private reportService: ReportsService,
    private basicDataService: BasicdataService,
    public auth: AuthService) {
    this.multibinclassId = [{
      label: '1-LIVELOCATION',
      value: 1
    },
    {
      label: '2-RESERVE',
      value: 2
    },
    {
      label: '3-RECEVING STAGING',
      value: 3
    },
    {
      label: '7-RETURNBIN',
      value: 7
    },

    ];
  }
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  animal: string | undefined;
  businesspartnerList:any[]=[];

  cols: any[] = [
    { header: 'Branch', field: 'plant_text', format: 'normal', showFooter: false },
    { header: 'Warehouse', field: 'wh_text', format: 'normal', showFooter: false },
    { header: 'Mfr Name', field: 'mfr_code', format: 'normal', showFooter: false },
    { header: 'Part No', field: 'itm_code', format: 'normal', showFooter: false },
    { header: 'Description', field: 'ref_field_8', format: 'normal', showFooter: false },
    { header: 'Partner', field: 'partner_code', format: 'normal', showFooter: false },
    { header: 'Level', field: 'level_id', format: 'normal', showFooter: false },
    { header: 'Batch Serial Number', field: 'str_no', format: 'normal', showFooter: false },
    { header: 'Storage Bin', field: 'st_bin', format: 'bin', showFooter: false },
    { header: 'Barcode', field: 'barcode_id', format: 'normal', showFooter: false },
    { header: 'Inv Qty', field: 'inv_qty', format: 'normal', showFooter: true },
    { header: 'Alloc Qty', field: 'alloc_qty', format: 'alloc', showFooter: true },
    { header: 'Total', field: 'ref_field_4', format: 'normal', showFooter: true },
    { header: 'Stock Type', field: 'stck_typ_text', format: 'normal', showFooter: false },
    { header: 'Manufactured Date', field: 'mfr_date', format: 'date', showFooter: false },
    { header: 'Expiration Date', field: 'exp_date', format: 'date', showFooter: false },
    { header: 'Storage Section', field: 'ref_field_10', format: 'normal', showFooter: false },
    { header: 'Company', field: 'c_text', format: 'normal', showFooter: false }
  ];
  ngOnInit(): void {
    // this.auth.isuserdata();
    this.dropdownfill();
    this.dropdownlist();
    this.masterService.searchbuisnesspartner({ warehouseId: [this.auth.warehouseId],companyCodeId:[this.auth.companyId],plantId:[this.auth.plantId],languageId:[this.auth.languageId]}).subscribe(res1 => {
      this.businesspartnerList = [];
      res1.forEach(element => {
       this.businesspartnerList.push({value: element.partnerCode, label: element.partnerCode+'-'+element.partnerName});
    })
   });
    this.selectedCompany = this.auth.companyId;
    this.selectedplant = this.auth.plantId;
  }
  calculateFooterTotal(field: string): number {
    let total = 0;
    this.inventory.forEach(item => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return total;
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

  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
  }
  putaway(res){
    if(res.st_bin=="RECEIPTSTAGING"){
    
    const dialogRef = this.dialog.open(InventoryPutwayComponent, {
      width: '72%',
      maxWidth: '90%',
      height:'55%',
      position: { top: '3.5%' },
      data: res 
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }
  else{
    console.log('Not a value')
  }
  }
  lines(res){
    
    const dialogRef = this.dialog.open(InventoryLinesComponent, {
      //width: '55%',
      maxWidth: '90%',
      position: { top: '8.5%' },
      data: res 
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }
  @ViewChild(MatSort, { static: true })
  sort!: MatSort;
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator; // Pagination
  // Pagination

  totalQty = 0;

  nonZeroTotal: any[] = [];
  nonZeroExcel: any[] = [];


  storageSectionId: any[] = [];
  filtersearch(excel = false) {

    this.searhform.controls.storageBin.patchValue([this.searhform.controls.storageBinFE.value]);
    if (this.searhform.controls.barcodeIdFE.value != null) {
      this.searhform.controls.barcodeId.patchValue([this.searhform.controls.barcodeIdFE.value]);
    }
    this.searhform.controls.storageSectionId.patchValue(this.searhform.controls.storageSectionIdFE.value);



    if (this.searhform.controls.storageBinFE.value == null) {
      this.searhform.controls.storageBin.patchValue(this.searhform.controls.storageBinFE.value);
    }
    if (this.searhform.controls.barcodeIdFE.value == null) {
      this.searhform.controls.barcodeId.patchValue(this.searhform.controls.barcodeIdFE.value);
    }
    if (this.searhform.controls.storageSectionIdFE.value == null) {
      this.searhform.controls.storageSectionId.patchValue(this.searhform.controls.storageSectionIdFE.value);
    }

    this.spin.show();
    this.totalQty = 0;

    if (!excel) {
      this.inventory = [];
      this.nonZeroTotal = []
      this.sub.add(this.service.searchNewSparkforcore(this.searhform.getRawValue()).subscribe((res: any) => {
        this.spin.hide();
        res.forEach(element => {
          this.nonZeroTotal.push(element)
         
          this.totalQty += element.inv_qty;

         // element.total = element.inventoryQuantity + element.allocatedQuantity;
        
         

        });

        // res.forEach(element => {
        //   if (element.total > 0) {
        //     this.nonZeroTotal.push(element)
        //   }
        // });
        this.inventory = this.nonZeroTotal;
        this.inventorynew = this.inventory
        this.calculateTotals(this.inventory);
        // this.totalRecords = res.totalElements;
        this.table = true;
        this.search = false;
        this.fullscreen = false;
        this.back = true;
      }, err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
    } else {
      this.downloadexcel();
    }
  }
  searhform = this.fb.group({
    companyCodeId: [[this.auth.companyId],],
    plantId: [[this.auth.plantId],],
    languageId: [[this.auth.languageId],],
    warehouseId: [[this.auth.warehouseId],],
    itemCode: [],
    itemCodeFE: [],
    itemTypeId: [],
    binClassId: [],
    storageBin: [],
    storageBinFE: [],
    manufacturerName:[],
    storageSectionId: [],
    storageSectionIdFE: [],
    barcodeId: [],
    barcodeIdFE: [],
    partnerCode:[],
  });



  reset() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
    this.searhform.controls.plantId.patchValue([this.auth.plantId]);
    this.searhform.controls.languageId.patchValue([this.auth.languageId]);
    this.searhform.controls.barcodeId.patchValue([""]);
    this.totalQty = 0;
  }
  downloadexcel() {
    const exportData = this.inventorynew.map(item => {
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
    this.cs.exportAsExcel(exportData, 'Inventory Report By Bin Location');
  }

  pageHandler($event: PageEvent) {
    this.pageNumber = $event.pageIndex;
    this.pageSize = $event.pageSize;
    this.filtersearch(false);
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
  multiSelectStorageSectionID1: any[] = [];
  multiSelectStorageSectionID: any[] = [];
  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),
      storageSection: this.masterService.getStorageSectionMasterDetails().pipe(catchError(err => of(err)))
    })
      .subscribe(({ warehouse, storageSection }) => {
        //   .subscribe(({ warehouse, itemCode, storageSection }) => {
        if (this.auth.userTypeId != 3) {
          this.warehouseList = warehouse.filter((x: any) => x.warehouseId == this.auth.warehouseId);
        } else {
          this.warehouseList = warehouse
        }
        this.warehouseList.forEach(x => this.multiWarehouseList.push({ value: x.warehouseId, label: x.warehouseId + (x.description == null ? '' : '- ' + x.description) }));
        this.multiselectWarehouseList = this.multiWarehouseList;
        this.multiselectWarehouseList.forEach((warehouse: any) => {
          if (warehouse.value == this.auth.warehouseId)
            this.selectedItems = [warehouse.value];
        })
        this.multiSelectStorageSectionID1 = storageSection.filter((x: any) => x.warehouseId == this.auth.warehouseId);
        this.multiSelectStorageSectionID1.forEach(x => this.multiSelectStorageSectionID.push({ value: x.storageSectionId, label: x.storageSectionId }));
        this.multiSelectStorageSectionID = this.cs.removeDuplicatesFromArrayNewstatus(this.multiSelectStorageSectionID)
        this.multiselectWarehouseList = this.cs.removeDuplicatesFromArrayNewstatus(this.multiselectWarehouseList)
      }, (err) => {
        this.toastr.error(
          err,
          ""
        );
      });
    this.spin.hide();

  }

  storagesectionList: any = [];
  multistoragelist: any[] = [];
  multiSelectstorageList: any[] = [];
  filterstoragesectionList: any[] = [];
  itemTypeList: any[] = [];
  dropdownlist() {
   this.masterService.searchItemType({companyCodeId: this.auth.companyId, plantId: this.auth.plantId,warehouseId:this.auth.warehouseId.value, languageId: [this.auth.languageId]}).subscribe(res => {
    this.itemTypeList = [];
    res.forEach(element => {
      this.itemTypeList.push({value: element.itemTypeId, label: element.itemTypeId + '-' + element.itemType});
    })
  });
 }

  onItemType(searchKey) {
    console.log(2)
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.reportService.getItemCodeDropDown2(searchVal.trim(), this.auth.companyId, this.auth.plantId, this.auth.warehouseId, this.auth.languageId).pipe(catchError(err => of(err))),
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

  selectItemCode1(e) {
    let selectedArray: any = [];
    let SelectedArray1:any=[];
    e.value.forEach(x => {
      let splittedValue = x.split('/')
      selectedArray.push(splittedValue[0])
      SelectedArray1.push(splittedValue[1]);
    })
    this.searhform.controls.itemCode.patchValue(selectedArray);
    this.searhform.controls.manufacturerName.patchValue(SelectedArray1);
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

  onSelectAll(items: any) {
  }

  /** Whether the number of selected elements matches the total number of rows. */







  // getBillableAmount() {
  //   let total = 0;
  //   this.dataSource.data.forEach(element => {
  //     total = total + (element.s != null ? element.s : 0);
  //   })
  //   return (Math.round(total * 100) / 100);
  // }


  async downloadAll() {
    const blob = await this.reportService.getInventoryFile()
      .catch((err: HttpErrorResponse) => {
        this.cs.commonerrorNew(err);
      });
    if (blob) {

      var downloadURL = window.URL.createObjectURL(blob);
      var link = document.createElement('a');
      link.href = downloadURL;
      link.download = "Inventory Report.xlsx";
      link.click();
    }
  }
  getTotal(data: any[] = this.inventory) {
    let total = 0;
    data.forEach(x => {
      total = total + (x.ref_field_4 != null ? x.ref_field_4 : 0)
    })
    return Math.round(total * 100 / 100)
  }

  getAllocQty() {
    let total = 0;
    this.inventory.forEach(x => {
      total = total + (x.alloc_qty != null ? x.alloc_qty : 0)
    })
    return Math.round(total * 100 / 100)
  }
  getInvQty() {
    let total = 0;
    this.inventory.forEach(x => {
      total = total + (x.inv_qty != null ? x.inv_qty : 0)
    })
    return Math.round(total * 100 / 100)
  }
  @ViewChild('binnerTag') binnerTag: Table | any;


  // handleSearch(filterValue: any, field: any): void {
  //   if (filterValue == null) {
  //     this.inventory = this.inventory;
  //     this.calculateTotals();
  //   }
  //   else {
  //     this.filteredInventory = this.inventorynew.filter(item => item[field].includes(filterValue));
  //     this.calculateTotals(this.filteredInventory);
  //     this.inventory = this.filteredInventory;
  //   }
  //   this.inventory = this.inventory;
  // }

  handleSearch1(filterInput){
    if (filterInput) {
      this.calculateTotals(this.binnerTag.filteredValue);
    }
    else {
      this.calculateTotals(this.inventory);
  
    }
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
     this.totalInventoryQuantity =  data.reduce((total, item) => total + (item.inv_qty != null ? item.inv_qty : 0), 0);
     this.totalAllocatedQuantity =  data.reduce((total, item) => total + (item.alloc_qty != null ? item.alloc_qty : 0), 0);
     this.totalQty1= data.reduce((total, item) => total + (item.ref_field_4 != null ? item.ref_field_4 : 0), 0);
   } else {
     this.inventory.forEach(x => {
       this.totalInventoryQuantity = 0;
       this.totalAllocatedQuantity = 0;
       this.totalQty1=0;
     })

   // }
  
  }
}

}
