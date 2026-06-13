import { SelectionModel } from '@angular/cdk/collections';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { ReportsService } from '../reports.service';
import { TransactionhistrylinesComponent } from './transactionhistrylines/transactionhistrylines.component';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.scss']
})
export class TransactionHistoryComponent implements OnInit {
  screenid=3208;
  transfer: any[] = [];
  selectedtransfer : any[] = [];
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;
  @ViewChild('transferTag') transferTag: Table | any;


  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  showFiller = false;

  displayedColumns: string[] = ['warehouseId', 'itemCode','itemDescription',  'openingStock', 'inboundQty', 'outboundQty','stockAdjustmentQty','closingStock'];
  sub = new Subscription();
  ELEMENT_DATA: any[] = [];


  constructor(public dialog: MatDialog,
    private http: HttpClient,
    // private cas: CommonApiService,
    private fb: FormBuilder,
    public toastr: ToastrService,
    private router: Router,
    private spin: NgxSpinnerService,
    public cs: CommonService,
    private service: ReportsService,
  private masterService: MasterService,
    public auth: AuthService) { }
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  animal: string | undefined;
 
  ngOnInit(): void {
    this.dropdownfill();
  }

  multiselectWarehouseList: any[] = [];
  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  
  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.service.getItemCodeDropDown2(searchVal.trim(),this.auth.companyId,this.auth.plantId,this.auth.warehouseId,this.auth.languageId).pipe(catchError(err => of(err))),
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
      this.searhform.controls.manufacturerName.patchValue(SelectedArray2);
  }
  warehouseList: any[] = [];
  selectedWarehouseList: any[] = [];
  selectedItems: any[] = [];
  multiWarehouseList: any[] = [];
  lines(res){
    console.log(res)
    const dialogRef = this.dialog.open(TransactionhistrylinesComponent, {
      //width: '55%',
      maxWidth: '90%',
      position: { top: '8.5%' },
      data: { code: res.itemCode , languageId:this.auth.languageId, companyCodeId:this.auth.companyId,warehouseId: this.auth.warehouseId,plantId: this.auth.plantId,fromCreatedOn:this.searhform.controls.fromCreatedOn.value,toCreatedOn:this.searhform.controls.toCreatedOn.value,manufacturerName:res.manufacturerName}
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }
  
  
  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),

    })
    .subscribe(({ warehouse }) => {
      if(this.auth.userTypeId != 3){
        this.warehouseList = warehouse.filter((x: any) => x.warehouseId == this.auth.warehouseId);
      }else{
        this.warehouseList = warehouse
      }
        this.warehouseList.forEach(x => this.multiWarehouseList.push({ value: x.warehouseId, label: x.warehouseId + (x.description == null ? '' : '- ' + x.description) }));
        this.multiselectWarehouseList = this.multiWarehouseList;
        this.multiselectWarehouseList.forEach((warehouse: any) => {
          if (warehouse.value == this.auth.warehouseId)
            this.searhform.controls.warehouseId.patchValue(warehouse.value);
        })
      }, (err) => {
        this.toastr.error(
          err,
          ""
        );
      });
    this.spin.hide();

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


  searhform = this.fb.group({
    companyCodeId:[this.auth.companyId],
    plantId:[this.auth.plantId],
    languageId:[this.auth.languageId],
    warehouseId: [this.auth.warehouseId],
    itemCode: [],
    manufacturerName:[],
    itemCodeFE: [],
    fromCreatedOn: [new Date("03/01/24 00:00:00"),Validators.required],
    toCreatedOn: [this.cs.todayapi(),Validators.required],
  });

  totalRecords = 0;
  getopenQty(){
    let total = 0;
    this.transfer.forEach(x =>{
      total = total + (x.openingStock != null ? x.openingStock : 0)
    })
    return Math.round(total *100 / 100)
  }

   getinboundQty(){
    let total = 0;
    this.transfer.forEach(x =>{
      total = total + (x.inboundQty != null ? x.inboundQty : 0)
    })
    return Math.round(total *100 / 100)
  }
  getoutboundQty(){
    let total = 0;
    this.transfer.forEach(x =>{
      total = total + (x.outboundQty != null ? x.outboundQty : 0)
    })
    return Math.round(total *100 / 100)
  }
  getstockajustQty(){
    let total = 0;
    this.transfer.forEach(x =>{
      total = total + (x.stockAdjustmentQty != null ? x.stockAdjustmentQty : 0)
    })
    return Math.round(total *100 / 100)
  }
  getclosedQty(){
    let total = 0;
    this.transfer.forEach(x =>{
      total = total + (x.closingStock != null ? x.closingStock : 0)
    })
    return Math.round(total *100 / 100)
  }
  getMovementQty(){
    let total = 0;
    this.transfer.forEach(x =>{
      total = total + (x.systemInventory != null ? x.systemInventory : 0)
    })
    return Math.round(total *100 / 100)
  }
  getvariance(){
    let total = 0;
    this.transfer.forEach(x =>{
      total = total + (x.variance != null ? x.variance : 0)
    })
    return Math.round(total *100 / 100)
  }
  reset(){
    this.searhform.reset();
    this.searhform.controls.companyCodeId.patchValue(this.auth.companyId)
    this.searhform.controls.languageId.patchValue(this.auth.languageId)
    this.searhform.controls.plantId.patchValue(this.auth.plantId)
    this.searhform.controls.warehouseId.patchValue(this.auth.warehouseId)
  }
  filtersearch(excel = false) {
this.spin.show();

    if (this.searhform.invalid) {
      this.toastr.error(
        "Please fill required fields to continue",
        ""
      );

      this.cs.notifyOther(true);
      this.spin.hide();
      return;
    }

     
    this.searhform.controls.fromCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.fromCreatedOn.value));
    this.searhform.controls.toCreatedOn.patchValue(this.cs.day_callapiSearch(this.searhform.controls.toCreatedOn.value));

    this.service.getTransactionHistoryReport(this.searhform.value).subscribe(res => {
      this.transfer = res;
      this.calculateTotals(this.transfer);

   
      this.table = true;
      this.search = false;
      this.fullscreen = false;
      this.back = true;
      this.spin.hide();
    }, err => {

      this.cs.commonerror(err);
      this.spin.hide();

    });
  }
  downloadexcel() {
    // if (excel)
    var res: any = [];
    this.transfer.forEach(x => {
      res.push({
        "Company":x.companyDescription,
        "Branch":x.plantDescription,
        "Warehouse":x.warehouseDescription,
        'Mfr Name ': x.manufacturerName,
        "Part No": x.itemCode,
        "Description":x.itemDescription,
        'Opening Stock': x.openingStock,
        "Inbound Qty": x.inboundQty,
        "Outbound Qty": x.outboundQty,
        "Stock Adjustment Qty ": x.stockAdjustmentQty,
        "Closing Qty ": x.closingStock,
        "System Stock":x.systemInventory,
        "Variance":x.variance,
      });


    })
    res.push({
      "Company":"",
      "Branch":"",
      "Warehouse":"",
      'Mfr Name ': "",
      "Part No": "",
      "Description":"Total",
      'Opening Stock': this.getopenQty(),
      "Inbound Qty": this.getinboundQty(),
      "Outbound Qty": this.getoutboundQty(),
      "Stock Adjustment Qty ": this.getstockajustQty(),
      "Closing Qty ": this.getclosedQty(),
      "System Stock":this.getMovementQty(),
      "Variance":this.getvariance(),

      
    });
    this.cs.exportAsExcel(res, "Transaction History");
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

  handleSearch1(filterInput){
    console.log(filterInput);
     if (filterInput) {
       this.calculateTotals(this.transferTag.filteredValue);
     }
     else {
       this.calculateTotals(this.transfer);
   
     }
   }
 
   filteredInventory: any[] = []; // Filtered inventory data array, if applicable
   totalInventoryQuantity: number = 0; // Total inventory quantity
   totalAllocatedQuantity: number = 0; // Total allocated quantity
   outboundQty1:number =0;
   stockAdjustmentQty1:number=0;
   closingStock1:number=0;
   systemInventory1:number=0;
   varianceQty1:number=0;
   calculateTotals(data?: any[]): void {
    this.totalInventoryQuantity = 0;
    this.totalAllocatedQuantity = 0;
    this.outboundQty1=0;
    this.stockAdjustmentQty1=0;
    this.closingStock1=0;
    this.systemInventory1=0;
    this.varianceQty1=0;
     if (data && data.length > 0) {
       this.totalInventoryQuantity = data.reduce((total, item) => total + (item.openingStock != null ? item.openingStock : 0), 0);
       this.totalAllocatedQuantity = data.reduce((total, item) => total + (item.inboundQty != null ? item.inboundQty : 0), 0);
       this.outboundQty1 = data.reduce((total, item) => total + (item.outboundQty != null ? item.outboundQty : 0), 0);
       this.stockAdjustmentQty1 = data.reduce((total, item) => total + (item.stockAdjustmentQty != null ? item.stockAdjustmentQty : 0), 0);
       this.closingStock1 = data.reduce((total, item) => total + (item.closingStock != null ? item.closingStock : 0), 0);
       this.systemInventory1 = data.reduce((total, item) => total + (item.systemInventory != null ? item.systemInventory : 0), 0);
       this.varianceQty1= data.reduce((total, item) => total + (item.variance != null ? item.variance : 0), 0);
      
     } else {
       this.transfer.forEach(x => {
         this.totalInventoryQuantity = 0;
         this.totalAllocatedQuantity = 0;
         this.outboundQty1 = 0;
         this.stockAdjustmentQty1 = 0;
         this.closingStock1 = 0;
         this.systemInventory1 = 0;
         this.varianceQty1 = 0;
       })
 
     // }
   
   }
 }


 


 
}


