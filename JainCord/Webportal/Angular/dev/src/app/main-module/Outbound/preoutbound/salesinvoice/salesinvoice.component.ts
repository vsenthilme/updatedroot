import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { PreoutboundService } from '../preoutbound.service';
import { MatTableDataSource, MatTable } from '@angular/material/table';
import { catchError } from 'rxjs/operators';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { InterwarehouselinesComponent } from '../interwarehousecreate/interwarehouselines/interwarehouselines.component';
import { SalesorderlinesComponent } from './salesorderlines/salesorderlines.component';
import { DialogExampleComponent } from 'src/app/common-field/innerheader/dialog-example/dialog-example.component';

@Component({
  selector: 'app-salesinvoice',
  templateUrl: './salesinvoice.component.html',
  styleUrls: ['./salesinvoice.component.scss']
})
export class SalesinvoiceComponent implements OnInit {
  inventory: any[] = [];
  selectedbinner : any[] = [];
  email = new FormControl('', [Validators.required, Validators.email, Validators.pattern("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}")]);
  displayedColumns: string[] = ['no','lineReference', 'orderType','orderedQty','sku','skuDescription','uom', 'delete'];
  dataSource = new MatTableDataSource<any>([]);
  screenid: 1037 | undefined;

  sub = new Subscription();


  isShowDiv = false;
  public icon = 'expand_more';
  showFloatingButtons: any;
  toggle = true;

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

 

  salesOrderHeader= this.fb.group({
    branchCode: [this.auth.plantId],
    companyCode: [this.auth.companyId],
    pickListNumber: [],
    requiredDeliveryDate: [],
    salesOrderNumber: [],
    status: [],
    storeID: [],
    storeName: [],
    warehouseId:[this.auth.warehouseId],
});
  submitted = false;
  disabled = false;
  step = 0;
  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

 panelOpenState = false;
  constructor(
    private business: BusinessPartnerService,
    private service: PreoutboundService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private bonService: BusinessPartnerService,
    private dialog: MatDialog,
    private masterService: MasterService,
    private auth: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private cs: CommonService,
  ) { }

  ngOnInit(): void {
    this.dropdownfill();
    
    let obj1: any = {};
  obj1.companyCodeId = [this.auth.companyId];
   obj1.plantId = [this.auth.plantId];
   obj1.languageId = [this.auth.languageId];
  obj1.warehouseId = [this.auth.warehouseId];
  obj1.businessPartnerType=[1];
  this.spin.show();
  this.business.searchSpark(obj1).subscribe((res: any[]) => {
    res.forEach(element => {
      this.multiPartnerList.push({value: element.partnerCode, label: element.partnerCode + '-' + element.partnerName,partnerName:element.partnerName});
       });
  })
  this.spin.hide();
    this.salesOrderHeader.get('branchCode')?.disable();
  this.salesOrderHeader.get('companyCode')?.disable();
  this.salesOrderHeader.get('warehouseId')?.disable();
  }

 multiWarehouseList: any[] = [];
  multiPartnerList: any[] = [];
  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),
      partner: this.bonService.search({warehouseId: [this.auth.warehouseId], businessPartnerType: [2]}).pipe(catchError(err => of(err))),
    })
  
    this.spin.hide();
  }

  currencychange(value){
    console.log(value);
    const currency = this.multiPartnerList.find(currency => currency.value === value);
  
    console.log(currency); 
 
    if (currency) {
       
        this.salesOrderHeader.controls.storeName.patchValue(currency.partnerName);
    } else {
        
        console.error('module not found');
    }

  }
  

  submit() {
    let obj: any = {};
    let salesOrderHeader: any = {};
    salesOrderHeader.branchCode = this.auth.plantId;
    salesOrderHeader.companyCode = this.auth.companyId;
    salesOrderHeader.pickListNumber = this.salesOrderHeader.controls.pickListNumber.value;
    salesOrderHeader.salesOrderNumber = this.salesOrderHeader.controls.pickListNumber.value;
    salesOrderHeader.status = this.salesOrderHeader.controls.status.value;
    salesOrderHeader.storeID=this.salesOrderHeader.controls.storeID.value;
    salesOrderHeader.storeName=this.salesOrderHeader.controls.storeName.value;
    //soHeader.requiredDeliveryDate = this.soHeader.controls.requiredDeliveryDate.value;
    const originalDate = this.salesOrderHeader.controls.requiredDeliveryDate.value;
    salesOrderHeader.requiredDeliveryDate = this.cs.dateddMMYY(originalDate);
    
    obj.salesOrderHeader = salesOrderHeader;
   obj.salesOrderLine= this.inventory,

this.salesOrderHeader.controls.requiredDeliveryDate.patchValue(salesOrderHeader.requiredDeliveryDate);
    this.sub.add(this.service.createsalesorder(obj).subscribe(res => {
  if(res){
    this.toastr.success("Order created successfully!",   "Notification");
    this.router.navigate(['/main/outbound/preoutbound']);
    this.spin.hide();
  }

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  };

  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
  }

  elementdata: any;
  @ViewChild(MatTable)
  table!: MatTable<any>;
  add() {
    const dialogRef = this.dialog.open(SalesorderlinesComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: this.inventory.length + 1
    });

    dialogRef.afterClosed().subscribe(result => {
     console.log(result.length);
     if(result){
      this.inventory
     
      if (result.length > 0) {
        this.inventory.push(result);
      }

      this.inventory.push(result);
    }
     this.inventory=[...this.inventory];
    });
  }
  removeRow(index: any) {
    const rowNo = this.inventory.indexOf(index);
    if (rowNo == -1) {
      this.inventory.splice(index, 1);
    }
    else {
      this.inventory.splice(rowNo, 1);
    }
    this.inventory=[...this.inventory];
  }   openDialog(data: any,rowIndex): void {
    const dialogRef = this.dialog.open(SalesorderlinesComponent, {
      disableClose: true,
      width: '55%',
      maxWidth: '80%',
      data: {pageflow :data,code:this.inventory[rowIndex]},
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.inventory.splice(rowIndex,0);
        this.inventory.splice(rowIndex, 1, result);
        
      //this.form.patchValue(result);
      this.inventory = [...this.inventory]
  
  }});
  }

}






