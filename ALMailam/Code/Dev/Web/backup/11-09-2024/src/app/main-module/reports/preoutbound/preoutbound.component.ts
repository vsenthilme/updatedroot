import { SelectionModel } from "@angular/cdk/collections";
import { HttpClient } from "@angular/common/http";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { Subscription, forkJoin, of } from "rxjs";
import { catchError } from "rxjs/operators";
import { CommonService } from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { MasterService } from "src/app/shared/master.service";
import { ContainerReceiptService } from "../../Inbound/Container-receipt/container-receipt.service";
import { PickingService } from "../../Outbound/picking/picking.service";
import { ReportsService } from "../reports.service";
import { StocksampleService } from "../stocksamplereport/stocksample.service";
import { PreoutboundService } from "../../Outbound/preoutbound/preoutbound.service";
import { Table } from "primeng/table";
import { DeliveryService } from "../../Outbound/delivery/delivery.service";


@Component({
  selector: 'app-preoutbound',
  templateUrl: './preoutbound.component.html',
  styleUrls: ['./preoutbound.component.scss']
})
export class PreoutboundComponent implements OnInit {
  screenid=3186;
  inbound: any[] = [];
  selectedinbound : any[] = [];
  @ViewChild('inboundTag') inboundTag: Table | any;
  isShowDiv = false;
  table = false;
  fullscreen = false;
  search = true;
  back = false;


  sourceBranch1:any[]=[];
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  showFiller = false;

 // displayedColumns: string[] = [ 'statusId','itemCode','proposedStorageBin', 'confirmedStorageBin', 'packBarcodes', 'putAwayQuantity', 'putawayConfirmedQty', 'confirmedBy','confirmedOn','createdOn', 'leadTime'];
      //wms demo
      displayedColumns: string[] = ['select', 'referenceField10', 'partnerCode', 'outboundOrderTypeId', 'referenceField1', 'preOutboundNo', 'refDocNumber', 'refDocDate', 'requiredDeliveryDate',];
  sub = new Subscription();
  ELEMENT_DATA: any[] = [];
  sourceBranch:any[]=[];
  soTypeList1: any[];
  constructor(public dialog: MatDialog,
    private http: HttpClient,
    // private cas: CommonApiService,
    private fb: FormBuilder,
    public toastr: ToastrService,
    private router: Router,
    private spin: NgxSpinnerService,
    private reportService: ReportsService,
    public cs: CommonService, 
    private service: DeliveryService,
    public auth: AuthService,
    private masterService: MasterService,) { 
      this.soTypeList1 = [
        {value: 'N', label: 'N'},
        {value: 'S', label: 'S'},
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
  ];  this.multiOrderType = [
    {value: 3, label: ' PICK LIST'},
    {value: 1, label: 'WMS to WMS'},
    {value: 0, label: 'WMS to Non-WMS'},
]; this.multistatus = [
  {value: 59, label: 'Delivered'},
  {value: 57, label: 'Delivery Open'},
  {value: 47, label: 'No Stock Item'},
  {value: 48, label: 'In Picking'},
  {value: 50, label: 'In Quality'},
  {value: 43, label: 'ALLOCATED'},
  {value: 51, label: 'Picker Denial'},
];

}
  routeto(url: any, id: any) {
    sessionStorage.setItem('crrentmenu', id);
    this.router.navigate([url]);
  }
  animal: string | undefined;
  // applyFilter(event: Event) {
  //   const filterValue = (event.target as HTMLInputElement).value;

  //   this.dataSource.filter = filterValue.trim().toLowerCase();

  //   if (this.dataSource.paginator) {
  //     this.dataSource.paginator.firstPage();
  //   }
  // }

  form = this.fb.group({
    createdBy: [],
    endCreatedOn:[],
    endOrderDate:[],
    endRequiredDeliveryDate:[],
    outboundOrderTypeId: [],
    partnerCode: [],
    preOutboundNo: [],
    refDocNumber:[],
    soNumber: [],
    soType: [,],
    startCreatedOn:[],
    itemCode:[],
    itemCodeFE:[],
    targetBranchCode:[],
    startOrderDate:[],
    startRequiredDeliveryDate:[],
    statusId: [],
    companyCodeId: [[this.auth.companyId]],
    languageId: [[this.auth.languageId] ],
    plantId: [[this.auth.plantId] ],
    warehouseId: [[this.auth.warehouseId] ],
    
 
  });

  ngOnInit(): void {
   // this.getDropdown();
    this.form.controls.companyCodeId.patchValue(this.auth.companyId);
    this.form.controls.plantId.patchValue(this.auth.plantId);
    this.form.controls.plantId.disable();
    this.form.controls.companyCodeId.disable();
    this.form.controls.warehouseId.patchValue(this.auth.warehouseId);
    this.form.controls.warehouseId.disable();
    let currentDate = new Date();
    let currentMonthStartDate = new Date();
    currentMonthStartDate.setDate(1); 
    this.form.controls.endOrderDate.patchValue(new Date());
    this.form.controls.startOrderDate.patchValue(currentMonthStartDate);
  }

 

  // ngAfterViewInit() {
  //   this.dataSource.paginator = this.paginator;
  // }





 
  itemCode: any;
  confirmedStorageBin: any;
  refDocNumber = '';
  warehouseId = '';

  filtersearch(){
this.spin.show();
this.form.controls.endRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.form.controls.endRequiredDeliveryDate.value));
this.form.controls.startRequiredDeliveryDate.patchValue(this.cs.day_callapiSearch(this.form.controls.startRequiredDeliveryDate.value));
this.form.controls.endOrderDate.patchValue(this.cs.day_callapiSearch(this.form.controls.endOrderDate.value));
this.form.controls.startOrderDate.patchValue(this.cs.day_callapiSearch(this.form.controls.startOrderDate.value));
let obj: any = {};
obj.companyCodeId = [this.auth.companyId];
obj.languageId = [this.auth.languageId];
obj.plantId = [this.auth.plantId];
obj.warehouseId = [this.auth.warehouseId];
obj.preOutboundNo=this.form.controls.preOutboundNo.value;
obj.startRequiredDeliveryDate=this.form.controls.startRequiredDeliveryDate.value;
obj.endRequiredDeliveryDate=this.form.controls.endRequiredDeliveryDate.value;
obj.endOrderDate=this.form.controls.endOrderDate.value;
obj.startOrderDate=this.form.controls.startOrderDate.value;
obj.soType=this.form.controls.soType.value;
obj.refDocNumber=this.form.controls.refDocNumber.value;
if(this.form.controls.outboundOrderTypeId.value == null){
  obj.outboundOrderTypeId=null;
}
else{
obj.outboundOrderTypeId=this.form.controls.outboundOrderTypeId.value;
}
if(this.form.controls.targetBranchCode.value == null){
  obj.targetBranchCode=null;
}
else{
obj.targetBranchCode=this.form.controls.targetBranchCode.value;
}
if(this.form.controls.refDocNumber.value ==null || this.form.controls.refDocNumber.value == ""){
  obj.refDocNumber=(null);
  }
  else{
    obj.refDocNumber=([this.form.controls.refDocNumber.value]);
  }
obj.statusId=this.form.controls.statusId.value;
    this.sub.add(this.service.searchsparkreport(obj).subscribe(res => {
      console.log(res)
      this.inbound = res;
      this.table = true;
      this.search = false;
      this.fullscreen = false;
      this.back = true;
      this.spin.hide();
      this.totalRecords = this.inbound.length
    },
      err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }));
  }

  multiOrderNo: any[] = [];
  multistatus:any[]=[];
  multiOrderType:any[]=[];
  multitargetbranch:any[]=[];
  getDropdown(){
    this.spin.show();
    let obj: any = {};
    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];
    obj.warehouseId = [this.auth.warehouseId];
    this.sub.add(this.service.searchv2(obj).subscribe(res => {
      res.forEach((x: any) => this.multiOrderNo.push({ value: x.refDocNumber, label: x.refDocNumber }));
      res.forEach((x: any) =>
      this.multistatus.push({ value: x.statusId, label: x.statusDescription })
      );
      res.forEach((x: any) =>
      this.multiOrderType.push({ value: x.outboundOrderTypeId, label: x.referenceDocumentType })
      );
      res.forEach((x: any) =>
      this.multitargetbranch.push({ value: x.partnerCode, label: x.partnerCode })
      );
      this.multiOrderNo=this.cs.removeDuplicateInArray(this.multiOrderNo);
      this.multistatus=this.cs.removeDuplicateInArray(this.multistatus);
      this.multiOrderType=this.cs.removeDuplicateInArray(this.multiOrderType);
      this.multitargetbranch=this.cs.removeDuplicateInArray(this.multitargetbranch);
      this.spin.hide();
    }, err => {
      this.spin.hide();
      this.cs.commonerrorNew(err);

    }))
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
        "Company":x.c_text,
        "Branch":x.plant_text,
        "Warehouse":x.wh_text,
        "Status  ": x.status_text,
        'Target Branch': x.target_branch_code,
        'Order Type': x.ref_doc_typ,
        "Sales Order No": x.sales_order_number,
        "Preoutbound No": x.pre_ob_no,
        "Order No": x.ref_doc_no,
        "Ordered Date":this.cs.dateapiwithTime(x.ref_doc_date),
        "Delivery Date":this.cs.dateapiwithTime(x.dlv_cnf_on),
      

        // 'Created By': x.createdBy,
        // 'Date': this.cs.dateapi(x.createdOn),
      });

    })
    this.cs.exportAsExcel(res, "Outbound Order Summary Report");
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

  // /** Whether the number of selected elements matches the total number of rows. */
  // isAllSelected() {
  //   const numSelected = this.selection.selected.length;
  //   const numRows = this.dataSource.data.length;
  //   return numSelected === numRows;
  // }

  // /** Selects all rows if they are not all selected; otherwise clear selection. */
  // masterToggle() {
  //   if (this.isAllSelected()) {
  //     this.selection.clear();
  //     return;
  //   }

  //   this.selection.select(...this.dataSource.data);
  // }

  // /** The label for the checkbox on the passed row */
  // checkboxLabel(row?: any): string {
  //   if (!row) {
  //     return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
  //   }
  //   return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.warehouseId + 1}`;
  // }



  // clearselection(row: any) {

  //   this.selection.clear();
  //   this.selection.toggle(row);
  // }
  

   reset(){
    this.form.reset();
    this.form.controls.companyCodeId.patchValue([this.auth.companyId])
    this.form.controls.languageId.patchValue([this.auth.languageId])
    this.form.controls.plantId.patchValue([this.auth.plantId])
    this.form.controls.warehouseId.patchValue([this.auth.warehouseId])
   }
}
