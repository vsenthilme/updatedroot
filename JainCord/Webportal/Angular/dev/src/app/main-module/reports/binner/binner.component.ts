    import { SelectionModel } from "@angular/cdk/collections";
    import { HttpClient } from "@angular/common/http";
    import { Component, OnInit, ViewChild } from "@angular/core";
    import { FormBuilder } from "@angular/forms";
    import { MatDialog } from "@angular/material/dialog";
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
    import { ContainerReceiptService } from "../../Inbound/Container-receipt/container-receipt.service";
    import { PickingService } from "../../Outbound/picking/picking.service";
    import { ReportsService } from "../reports.service";
    import  { stockElement, StocksampleService }  from "../stocksamplereport/stocksample.service";
    import { Table } from "primeng/table";
    import { CommonApiService } from "src/app/common-service/common-api.service";
  
  @Component({
    selector: 'app-binner',
    templateUrl: './binner.component.html',
    styleUrls: ['./binner.component.scss']
  })
  export class BinnerComponent implements OnInit {
    screenid=3166;
    binner: any[] = [];
    selectedbinner: any[] = [];
    @ViewChild('binnerTag') binnerTag: Table | any;
    isShowDiv = false;
    table = false;
    fullscreen = false;
    search = true;
    back = false;
    selectedCompany:any[]=[];
  
  
    showFloatingButtons: any;
    toggle = true;
    public icon = 'expand_more';
    showFiller = false;
  
    // displayedColumns: string[] = [ 'statusId','itemCode','proposedStorageBin', 'confirmedStorageBin', 'packBarcodes', 'putAwayQuantity', 'putawayConfirmedQty', 'confirmedBy','confirmedOn','createdOn', 'leadTime'];
    //wms demo
    displayedColumns: string[] = ['statusId', 'itemCode', 'proposedStorageBin', 'confirmedStorageBin', 'packBarcodes', 'putAwayQuantity', 'putawayConfirmedQty', 'confirmedBy', 'confirmedOn',];
    sub = new Subscription();
    ELEMENT_DATA: any[] = [];
  
  
    constructor(public dialog: MatDialog,
      private cas: CommonApiService,
      private service: StocksampleService,
      private http: HttpClient,
      // private cas: CommonApiService,
      private fb: FormBuilder,
      public toastr: ToastrService,
      private router: Router,
      private spin: NgxSpinnerService,
      public cs: CommonService, private reportService: ReportsService,
      private pickingService: PickingService,
      public auth: AuthService,
      private containerservice: ContainerReceiptService,
      private masterService: MasterService,) { 
        this.multistatusList = [
        {
          label: 'Putaway confirmed',
          value: 20
        },
        {
          label: 'Putaway reversed',
          value: 22
        },
        {
          label: 'Receipt Confirmed',
          value: 24
        },
      ];
      }
    routeto(url: any, id: any) {
      sessionStorage.setItem('crrentmenu', id);
      this.router.navigate([url]);
    }
    animal: string | undefined;
    selectedplant:any[]=[];
    selectedwarehouse:any[]=[];

    cols: any[] = [
      { header: 'Plant', field: 'plantDescription', format: 'normal' ,showFooter: false },
      { header: 'Warehouse', field: 'warehouseDescription', format: 'normal' ,showFooter: false },
      { header: 'Status', field: 'statusDescription', format: 'normal' ,showFooter: false },
      { header: 'Mfr Name', field: 'manufacturerName', format: 'normal' ,showFooter: false },
      { header: 'Part No', field: 'itemCode', format: 'normal' ,showFooter: false },
      { header: 'Batch Serial Number', field: 'batchSerialNumber', format: 'normal' ,showFooter: false },
      { header: 'Order No', field: 'refDocNumber', format: 'normal' ,showFooter: false },
      { header: 'Order Type', field: 'referenceDocumentType', format: 'normal' ,showFooter: false },
      { header: 'Proposed Storage Bin', field: 'proposedStorageBin', format: 'normal' ,showFooter: false },
      { header: 'Confirmed Storage Bin', field: 'confirmedStorageBin', format: 'normal' ,showFooter: false },
      { header: 'Barcode', field: 'barcodeId', format: 'normal' ,showFooter: false },
      { header: 'Order Qty', field: 'putAwayQuantity', format: 'normal' ,showFooter: true },
      { header: 'Binned Qty', field: 'putawayConfirmedQty', format: 'normal' ,showFooter: true },
      { header: 'Binner', field: 'confirmedBy', format: 'normal' ,showFooter: false },
      { header: 'Charge Value', field: 'referenceField10', format: 'normal' ,showFooter: false },
      { header: 'Charge Unit', field: 'referenceField9', format: 'normal' ,showFooter: false },
      { header: 'Assigned On', field: 'createdOn', format: 'date' ,showFooter: false },
      { header: 'Binned On', field: 'confirmedOn', format: 'date' ,showFooter: false },
      { header: 'Lead Time', field: 'leadTime', format: 'lead' ,showFooter: false },
      { header: 'Company', field: 'companyDescription', format: 'normal' ,showFooter: false }
    ];

    ngOnInit(): void {
      this.getDropdown();
      this.dropdownfill();
      this.selectedCompany=this.auth.companyIdAndDescription;
      this.selectedplant=this.auth.plantIdAndDescription;
      this.selectedwarehouse=this.auth.warehouseIdAndDescription;
    
    }
  
    calculateFooterTotal(field: string): number {
      let total = 0;
      this.binner.forEach(item => {
        total += Number.parseFloat(item[field]) || 0;
      });
      return total;
    }
    multiOrderNo: any[] = [];
    getDropdown() {
      this.sub.add(this.reportService.getInboundStatusReportv2({ warehouseId: [this.auth.warehouseId],companyCodeId:[this.auth.companyId],plantId:[this.auth.plantId],languageId:[this.auth.languageId] }).subscribe(res => {
        res.forEach((x: any) => this.multiOrderNo.push({ value: x.refDocNumber, label: x.refDocNumber }));
        this.multiOrderNo=this.cs.removeDuplicatesFromArrayNewstatus(this.multiOrderNo);
      }))
    }
  
  
    warehouseList: any[] = [];
    selectedWarehouseList: any[] = [];
    selectedItems: any[] = [];
    multiselectWarehouseList: any[] = [];
    multiWarehouseList: any[] = [];
    CustomeridList: any[] = [];
    dropdownfill() {
      this.spin.show();
      forkJoin({
        warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),
  
      })
        .subscribe(({ warehouse }) => {
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
          this.multiselectWarehouseList=this.cas.removeDuplicatesFromArray(this.multiselectWarehouseList);
        }, (err) => {
          this.toastr.error(
            err,
            ""
          );
        });
      this.spin.hide();
  
    }
    multistatusList:any[]=[];
  getStatus(){
    this.sub.add(this.reportService.getstatus({languageId:[this.auth.languageId]}).subscribe(res => {
      res.forEach((x: any) => this.multistatusList.push({ value: x.statusId, label: x.statusDescription }));
    
      
       
          this.multistatusList=this.cs.removeDuplicatesFromArrayNew(this.multistatusList);
  
    }))
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
              this.itemCodeList.forEach(x => this.multiselectItemCodeList.push({ value: x.itemCode + '/' + x.manufacturerName, label: x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description })); //+x.manufacturerName
            }
          });
      }
    }
    itemCodeChanged(e){
      console.log(e);
      let selectedArray: any ;
      let SelectedArray2:any;
        let splittedValue = e.value.split('/')
        selectedArray=(splittedValue[0])
        SelectedArray2=(splittedValue[1])
        console.log(selectedArray);
        console.log(SelectedArray2);
        this.searhform.controls.itemCode.patchValue([selectedArray]);
        this.searhform.controls.manufacturerName.patchValue([SelectedArray2]);
    }
    multiselectStorageList: any[] = [];
    storageBinList1: any[] = [];
    selectedStorageBin: any[] = [];
    onStorageType(searchKey) {
      let searchVal = searchKey?.filter;
      if (searchVal !== '' && searchVal !== null) {
        forkJoin({
          storageList: this.reportService.getStorageDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId).pipe(catchError(err => of(err))),
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
    searhform = this.fb.group({
      companyCodeId:[[this.auth.companyId],],
      plantId:[[this.auth.plantId],],
      languageId:[[this.auth.languageId],],
      warehouseId:[[this.auth.warehouseId],],
      itemCode: [],
      itemCodeFE: [],
      manufacturerName:[],
      confirmedStorageBin:[],
      confirmedStorageBinFE:[],
      refDocNumber:[],
      fromConfirmedDate:[],
      statusId:[],
      toConfirmedDate:[],
      
    });
  binnernew:any[]=[];
    filtersearch() {
      this.spin.show();
      let obj: any = {};
  
  
      if(this.searhform.controls.confirmedStorageBinFE.value != null){
        this.searhform.controls.confirmedStorageBin.patchValue([this.searhform.controls.confirmedStorageBinFE.value]);
      }
          
          if(this.searhform.controls.confirmedStorageBin.value == null){
            this.searhform.controls.confirmedStorageBin.patchValue(this.searhform.controls.confirmedStorageBinFE.value);
          }
      //    this.form.controls.updatedOnFE.patchValue(this.cs.dateTimeApi(this.form.controls.updatedOn.value));
      // obj.toPickConfirmedOn = this.toPickConfirmedOn
      // obj.fromPickConfirmedOn = this.fromPickConfirmedOn
      obj.warehouseId = [this.auth.warehouseId];
      
      obj.companyCode = [this.auth.companyId];
      obj.languageId = [this.auth.languageId];
      obj.plantId = [this.auth.plantId];
      this.searhform.controls.fromConfirmedDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.fromConfirmedDate.value));
      this.searhform.controls.toConfirmedDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.toConfirmedDate.value));

      
      this.sub.add(this.reportService.getBinnerReportspark(this.searhform.getRawValue()).subscribe(res => {
        this.binner = res;
        this.binnernew=this.binner;
        this.calculateTotals(this.binner)
        this.table = true;
        this.search = false;
        this.fullscreen = false;
        this.back = true;
        this.spin.hide();
        //this.totalRecords = this.dataSource.data.length
      },
        err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }));
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
      const exportData = this.binner.map(item => {
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
      this.cs.exportAsExcel(exportData, 'Binning Report');
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
    getToQtyQty() {
      let total = 0;
      this.binner.forEach(x => {
        total = total + (x.putAwayQuantity != null ? x.putAwayQuantity : 0)
      })
      return Math.round(total * 100 / 100)
    }
    getBinnerQty() {
      let total = 0;
      this.binner.forEach(x => {
        total = total + (x.putawayConfirmedQty != null ? x.putawayConfirmedQty : 0)
      })
      return Math.round(total * 100 / 100)
    }
  
    reset() {
    this.searhform.reset();
    this.searhform.controls.warehouseId.patchValue([this.auth.warehouseId]);
  this.searhform.controls.companyCodeId.patchValue([this.auth.companyId]);
  this.searhform.controls.plantId.patchValue([this.auth.plantId]);
  this.searhform.controls.languageId.patchValue([this.auth.languageId]);
    }
    handleSearch1(filterInput){
     console.log(filterInput);
      if (filterInput) {
        this.calculateTotals(this.binnerTag.filteredValue);
      }
      else {
        this.calculateTotals(this.binner);
    
      }
    }
  
    filteredInventory: any[] = []; // Filtered inventory data array, if applicable
    totalInventoryQuantity: number = 0; // Total inventory quantity
    totalAllocatedQuantity: number = 0; // Total allocated quantity
  totalQty1:number =0;
    calculateTotals(data?: any[]): void {
     this.totalInventoryQuantity = 0;
     this.totalAllocatedQuantity = 0;
      if (data && data.length > 0) {
        this.totalInventoryQuantity = data.reduce((total, item) => total + (item.putAwayQuantity != null ? item.putAwayQuantity : 0), 0);
        this.totalAllocatedQuantity = data.reduce((total, item) => total + (item.putawayConfirmedQty != null ? item.putawayConfirmedQty : 0), 0);
       
      } else {
        this.binner.forEach(x => {
          this.totalInventoryQuantity = 0;
          this.totalAllocatedQuantity = 0;
        })
  
      // }
    
    }
  }
  }
  