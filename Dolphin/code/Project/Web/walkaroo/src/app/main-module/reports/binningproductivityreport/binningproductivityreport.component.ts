import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription, forkJoin, of } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PickingproductivitylinesComponent } from '../pickingproductivity/pickingproductivitylines/pickingproductivitylines.component';
import { ReportsService } from '../reports.service';
import { StocksampleService } from '../stocksamplereport/stocksample.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { catchError } from 'rxjs/operators';
import { CommonApiService } from 'src/app/common-service/common-api.service';

@Component({
  selector: 'app-binningproductivityreport',
  templateUrl: './binningproductivityreport.component.html',
  styleUrls: ['./binningproductivityreport.component.scss']
})
export class BinningproductivityreportComponent implements OnInit {
  screenid=3226;
  binner: any[] = [];
  selectedbinner: any[] = [];
  @ViewChild('binnerTag') binnerTag: Table | any;
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;
  selectedCompany:any[]=[];

  multiOrderType:any[]=[];
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
    public auth: AuthService,
  
   ) { 
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
    this.multiOrderType = [
      {value: 1, label: '1 - Supplier Invoice'},
      {value: 2, label: '2 - Sales Return'},
      {value: 3, label: '3 - Non-WMS to WMS'},
      {value: 4, label: '4 - WMS to WMS'},
      {value: 5, label: '5 - Direct Receipt'}
  ];
    }
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  animal: string | undefined;
  selectedplant:any[]=[];
  selectedwarehouse:any[]=[];
  ngOnInit(): void {
    this.getDropdown();
    //this.dropdownfill();
    this.selectedCompany=this.auth.companyIdAndDescription;
    this.selectedplant=this.auth.plantIdAndDescription;
    this.selectedwarehouse=this.auth.warehouseIdAndDescription;
    let currentDate = new Date();
    let currentMonthStartDate = new Date();
    currentMonthStartDate.setDate(1); 
    
    this.searhform.controls.toConfirmedDateFE.patchValue(currentDate);
    this.searhform.controls.fromConfirmedDateFE.patchValue(currentMonthStartDate);
  }
  multipickerId: any[] = [];
  multisalesorder:any[]= [];
  multilevel:any[]=[];
  getDropdown(){
    this.sub.add(this.reportService.searchhht({warehouseId : [this.auth.warehouseId],companyCodeId:[this.auth.companyId],plantId:[this.auth.plantId],languageId:[this.auth.languageId]}).subscribe(res => {
      res.forEach((x: any) => this.multipickerId.push({ value: x.userId, label: x.userName }));
      this.multipickerId=this.cs.removeDuplicatesFromArrayNewstatus(this.multipickerId);

    }))
  }


  warehouseList: any[] = [];
  selectedWarehouseList: any[] = [];
  selectedItems: any[] = [];
  multiselectWarehouseList: any[] = [];
  multiWarehouseList: any[] = [];
  CustomeridList: any[] = [];

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
    fromConfirmedDateFE:[],
    inboundOrderTypeId:[],
    statusId:[],
    toConfirmedDate:[],
    toConfirmedDateFE:[],
    confirmedBy:[],
  });
binnernew:any[]=[];
  filtersearch() {
    this.spin.show();
    let obj: any = {};


   
    //    this.form.controls.updatedOnFE.patchValue(this.cs.dateTimeApi(this.form.controls.updatedOn.value));
    // obj.toPickConfirmedOn = this.toPickConfirmedOn
    // obj.fromPickConfirmedOn = this.fromPickConfirmedOn
    obj.warehouseId = [this.auth.warehouseId];
    
    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];
    this.searhform.controls.fromConfirmedDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.fromConfirmedDateFE.value));
    this.searhform.controls.toConfirmedDate.patchValue(this.cs.day_callapiSearch(this.searhform.controls.toConfirmedDateFE.value));

    
   
    this.sub.add(this.reportService.getBinnerReportsparknormal(this.searhform.getRawValue()).subscribe(res => {
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
    // if (excel)
    var res: any = [];
    this.binner.forEach(x => {
      res.push({
       
        "Branch":x.plant_text,
        "Warehouse":x.wh_text,
        "Status": x.status_text,
        "Mfr Name": x.mfr_name,
        "Part No": x.itm_code,
        "Order No": x.ref_doc_no,
        "Order Type":x.ref_doc_type,
        "Proposed StorageBin ": x.prop_st_bin,
        'Confirmed Storage Bin': x.cnf_st_bin,
        'Barcode': x.barcode_id,
        'Order Qty': x.pa_qty,
        'Binned Qty': x.pa_cnf_qty,
        //"Assigned User":x.assignedUserId,
        'User': x.pa_cnf_by,
        "Assigned On":this.cs.dateapiwithTime(x.pa_ctd_on),
        "Binned On":this.cs.dateapiwithTime(x.pa_cnf_on),
        "Lead Time": x.ref_field_1,
        "Company":x.c_text,
        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Binning Productivity Report");
  }

  getProductivity() {
    let total = 0;
    let total2=0;
    let totalLeadTimeInMinutes = 0;
    this.binner.forEach(element => {
      total2+= element.pa_cnf_qty;
      if (element.ref_field_1) {
        total+=Number(element.ref_field_1);
      }
    });

    let percentage = (total > 0) ? (total2 / total) : 0;
    return percentage*60;
}
gettotal(){
  let total2 = 0;
  this.binner.forEach(element => {
   
    
      total2+= element.pa_cnf_qty;
  
});
return total2;
}
getAvgLeadTime() {
  let totalLeadTimeInMinutes = 0;
  let count = 0;
  let totalTime=0;
  let total2 = 0;
  this.binner.forEach(element => {
    if (element.ref_field_1) {
      total2+= element.pa_cnf_qty;
      totalTime+=Number(element.ref_field_1);
      count=count+1
       
      
    }
  });

  let averageLeadTime = (count > 0) ? totalTime / total2 : 0;
  return averageLeadTime.toFixed(2);
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
      this.totalInventoryQuantity = data.reduce((total, item) => total + (item.pa_qty != null ? item.pa_qty : 0), 0);
      this.totalAllocatedQuantity = data.reduce((total, item) => total + (item.pa_cnf_qty != null ? item.pa_cnf_qty : 0), 0);
     
    } else {
      this.binner.forEach(x => {
        this.totalInventoryQuantity = 0;
        this.totalAllocatedQuantity = 0;
      })

    // }
  
  }
}
}
