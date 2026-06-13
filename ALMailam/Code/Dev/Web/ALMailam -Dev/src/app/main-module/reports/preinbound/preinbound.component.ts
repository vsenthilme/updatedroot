import { SelectionModel } from '@angular/cdk/collections';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { PreinboundService } from '../../Inbound/preinbound/preinbound.service';
import { InboundConfirmationService } from '../../Inbound/inbound-confirmation/inbound-confirmation.service';
import { catchError } from 'rxjs/operators';
import { ReportsService } from '../reports.service';
import { Table } from 'primeng/table';
import { StatusidService } from '../../other-setup/statusid/statusid.service';

@Component({
  selector: 'app-preinbound',
  templateUrl: './preinbound.component.html',
  styleUrls: ['./preinbound.component.scss']
})
export class PreinboundComponent implements OnInit {
  screenid=3185;
  inbound: any[] = [];
  selectedinbound : any[] = [];
  @ViewChild('inboundTag') inboundTag: Table | any;
 
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;



  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  showFiller = false;

 // displayedColumns: string[] = [ 'statusId','itemCode','proposedStorageBin', 'confirmedStorageBin', 'packBarcodes', 'putAwayQuantity', 'putawayConfirmedQty', 'confirmedBy','confirmedOn','createdOn', 'leadTime'];
      //wms demo
      displayedColumns: string[] = ['refDocNumber', 'lineNo', 'itemCode', 'description', 'orderQty', 'acceptedQty', 'damageQty', 'varianceQty', 'confirmedOn', 'createdOn', 'referenceField10'];
  sub = new Subscription();
  ELEMENT_DATA: any[] = [];

  soTypeList1: any[];
  inboundOrderTypeId: any[];
  sourceBranch: any[];
  sourceBranch1: any[];

  constructor(public dialog: MatDialog,
    private http: HttpClient,
    // private cas: CommonApiService,
    private fb: FormBuilder,
    public toastr: ToastrService,
    private router: Router,
    private spin: NgxSpinnerService,
    public cs: CommonService, 
    private service: PreinboundService,
    private reportService: ReportsService,
    private serviceLine: InboundConfirmationService,
    private status: StatusidService,
    public auth: AuthService,
    private masterService: MasterService,) { 
      this.soTypeList1 = [
        {value: 'N', label: 'N'},
        {value: 'S', label: 'S'},
    ];

    this.inboundOrderTypeId = [
      {value: 1, label: '1 - Supplier Invoice'},
      {value: 2, label: '2 - Sales Return'},
      {value: 3, label: '3 - Non-WMS to WMS'},
      {value: 4, label: '4 - WMS to WMS'},
      {value: 5, label: '5 - Direct Receipt'}
  ];

  this.sourceBranch = [
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
]
    }
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
   endConfirmedOn: [],
   itemCode: [],
   itemCodeFE: [],
   inboundOrderTypeId: [],
   manufacturerPartNo: [],
   refDocNumber:[],
startCreatedOn: [],
   startCreatedOnFE:[],
   endCreatedOnFE: [],
   sourceBranchCode: [],
   startConfirmedOn: [],
   endConfirmedOnFE:[],
   startConfirmedOnFE: [],
  });

  ngOnInit(): void {
    this.getDropdown();
   
    this.form.controls.companyCode.patchValue(this.auth.companyId);
    this.form.controls.plantId.patchValue(this.auth.plantId);
    this.form.controls.plantId.disable();
    this.form.controls.companyCode.disable();
    this.form.controls.warehouseId.patchValue(this.auth.warehouseId);
    this.form.controls.warehouseId.disable();
    let currentDate = new Date();
    let currentMonthStartDate = new Date();
    currentMonthStartDate.setDate(1); 
    this.form.controls.endConfirmedOnFE.patchValue(new Date());
    this.form.controls.startConfirmedOnFE.patchValue(currentMonthStartDate);
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
      this.form.controls.itemCode.patchValue([selectedArray]);
      this.form.controls.manufacturerName.patchValue([SelectedArray2]);
  }
  multiOrderNo: any[] = [];
  statusDropdown: any[] = [];
    getDropdown(){
      this.spin.show();
      let obj: any = {};
      obj.companyCode = [this.auth.companyId];
      obj.languageId = [this.auth.languageId];
      obj.plantId = [this.auth.plantId];
      obj.warehouseId = [this.auth.warehouseId];
      this.sub.add(this.serviceLine.searchstream(obj).subscribe(res => {
      res.forEach(x => {
        this.multiOrderNo.push({ value: x.refDocNumber, label: x.refDocNumber }); 
        this.multiOrderNo=this.cs.removeDuplicatesFromArrayNewstatus(this.multiOrderNo);
      });
        this.spin.hide();
      }, err => {
        this.spin.hide();
        this.cs.commonerrorNew(err);

      }))

      this.sub.add(this.status.search({statusId: [24, 17, 20, 5, 14]}).subscribe(res => {
        res.forEach(x => {  
          this.statusDropdown.push({ value: x.statusId, label: x.status}); 
        });
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
inboundnew:any[]=[];
  filtersearch(){
   
    this.form.controls.warehouseId.patchValue([this.auth.warehouseId]);
    this.form.controls.companyCode.patchValue([this.auth.companyId]);
    this.form.controls.languageId.patchValue([this.auth.languageId]);
    this.form.controls.plantId.patchValue([this.auth.plantId]);
this.spin.show();
this.form.controls.endConfirmedOn.patchValue(this.cs.day_callapiSearch(this.form.controls.endConfirmedOnFE.value));
this.form.controls.startConfirmedOn.patchValue(this.cs.day_callapiSearch(this.form.controls.startConfirmedOnFE.value));


let obj: any = {};
obj.companyCodeId = [this.auth.companyId];
obj.languageId = [this.auth.languageId];
obj.plantId = [this.auth.plantId];
obj.warehouseId = [this.auth.warehouseId];
obj.itemCode = [this.form.controls.itemCodeFE.value];
obj.refDocNumber = this.form.controls.refDocNumber.value;
obj.startConfirmedOn=this.form.controls.startConfirmedOn.value;
obj.endConfirmedOn=this.form.controls.endConfirmedOn.value;
    this.sub.add(this.serviceLine.searchLinespark(this.form.getRawValue()).subscribe(res => {
      console.log(res)
      this.inbound = res;
      this.inboundnew = this.inbound
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
        "Company":x.companyDescription,
        "Branch":x.plantDescription,
        "Warehouse":x.warehouseDescription,
        "Source Branch":x.sourceBranchCode,
        "Order No ": x.refDocNumber,
        "Line No": x.lineNo,
        "Order Type":x.referenceDocumentType,
        "Mfr Name":x.manufacturerName,
        'Part No': x.itemCode,
        'Description': x.description,
        "Order Qty":x.orderQty,
        'Acc Qty': x.acceptedQty,
        'Dam Qty': x.damageQty,
        'Variance Qty': x.varianceQty,
        'Created Date':this.cs.dateapiwithTime(x.createdOn),
        'Confirmed Date':this.cs.dateapiwithTime(x.confirmedOn),
        "Status ": x.statusDescription,
      });
    })
    res.push({
      "Company":'',
      "Branch":'',
      "Warehouse":'',
      "Order No ":'',
      "Line No": '',
      "Order Type":'',
      "Mfr Name":'',
      'Part No': '',
      'Description':'',
      "Order Qty":this.getorderQty(),
      'Acc Qty': this.getacceptedQty(),
      'Dam Qty': this.getdamageQty(),
      'Variance Qty': '',
      'Confirmed Date':'',
      'Created Date':'',
      "Status ":'',
    });
    this.cs.exportAsExcel(res, "Inbound Order Details Report");
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
  totalvarianceQty:number = 0;
totalQty1:number =0;
  calculateTotals(data?: any[]): void {
   this.totalInventoryQuantity = 0;
   this.totalAllocatedQuantity = 0;
   this.totalQty1=0;
    if (data && data.length > 0) {
      this.totalInventoryQuantity = data.reduce((total, item) => total + (item.orderQty != null ? item.orderQty : 0), 0);
      this.totalAllocatedQuantity = data.reduce((total, item) => total + (item.acceptedQty != null ? item.acceptedQty : 0), 0);
      this.totalQty1=data.reduce((total, item) => total + (item.damageQty != null ? item.damageQty : 0), 0);
      this.totalvarianceQty=data.reduce((total, item) => total + (item.varianceQty != null ? item.varianceQty : 0), 0);
    } else {
      this.inbound.forEach(x => {
        this.totalInventoryQuantity = 0;
        this.totalAllocatedQuantity =0;
        this.totalQty1=0;
        this.totalvarianceQty=0;
      })

    // }
  
  }
}




  
  

  reset(){
   this.form.reset();
   this.form.controls.companyCode.patchValue([this.auth.companyId])
   this.form.controls.languageId.patchValue([this.auth.languageId])
   this.form.controls.plantId.patchValue([this.auth.plantId])
   this.form.controls.warehouseId.patchValue([this.auth.warehouseId])
  }

  getorderQty(){
      let total = 0;
      this.inbound.forEach(x =>{
        total = total + (x.orderQty != null ? x.orderQty : 0)
      })
      return Math.round(total *100 / 100)
  }

  getacceptedQty(){
    let total = 0;
    this.inbound.forEach(x =>{
      total = total + (x.acceptedQty != null ? x.acceptedQty : 0)
    })
    return Math.round(total *100 / 100)
}
getdamageQty(){
  let total = 0;
  this.inbound.forEach(x =>{
    total = total + (x.damageQty != null ? x.damageQty : 0)
  })
  return Math.round(total *100 / 100)
}
getvarianceQty(){
  let total = 0;
  this.inbound.forEach(x =>{
    total = total + (x.varianceQty != null ? x.varianceQty : 0)
  })
  return Math.round(total *100 / 100)
}
}
