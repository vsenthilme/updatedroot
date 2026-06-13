import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { InboundConfirmationService } from 'src/app/main-module/Inbound/inbound-confirmation/inbound-confirmation.service';
import { PreinboundService } from 'src/app/main-module/Inbound/preinbound/preinbound.service';
import { StatusidService } from 'src/app/main-module/other-setup/statusid/statusid.service';
import { MasterService } from 'src/app/shared/master.service';
import { ReportsService } from '../../reports.service';

@Component({
  selector: 'app-inventory-putway',
  templateUrl: './inventory-putway.component.html',
  styleUrls: ['./inventory-putway.component.scss']
})
export class InventoryPutwayComponent implements OnInit {
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
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private spin: NgxSpinnerService,
    public cs: CommonService, 
    private service: PreinboundService,
    private reportService: ReportsService,
    private serviceLine: InboundConfirmationService,
    private status: StatusidService,
    public auth: AuthService,
    private masterService: MasterService,) { 
    
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
   manufacturerName:[],
   inboundOrderTypeId: [],
   manufacturerPartNo: [],
   refDocNumber: [],
   statusId: [],
   startCreatedOn: [],
   startCreatedOnFE:[],
   endCreatedOn: [],
   endCreatedOnFE: [],
   sourceBranchCode: [],
   startConfirmedOn: [],
  });

  ngOnInit(): void {
   ;
   this.filtersearch();
   
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
this.form.controls.endConfirmedOn.patchValue(this.cs.day_callapiSearch(this.form.controls.endConfirmedOn.value));
this.form.controls.startConfirmedOn.patchValue(this.cs.day_callapiSearch(this.form.controls.startConfirmedOn.value));
this.form.controls.itemCode.patchValue([this.data.itm_code]);
this.form.controls.manufacturerName.patchValue([this.data.mfr_code]);
//this.form.controls.refDocNumber.patchValue([this.data.referenceDocumentNo]);
console.log(this.form.getRawValue())
    this.sub.add(this.serviceLine.searchLinespark(this.form.getRawValue()).subscribe(res => {
      
      this.inbound = res.filter((x: any) => x.statusDescription != 'Receipt Confirmed');
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
