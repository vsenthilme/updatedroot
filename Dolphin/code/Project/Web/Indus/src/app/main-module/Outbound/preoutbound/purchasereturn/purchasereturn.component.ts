import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource, MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { MasterService } from 'src/app/shared/master.service';
import { PopupComponent } from '../preoutbound-new/popup/popup.component';
import { PreoutboundService } from '../preoutbound.service';
import { PurchasereturnNewComponent } from './purchasereturn-new/purchasereturn-new.component';

@Component({
  selector: 'app-purchasereturn',
  templateUrl: './purchasereturn.component.html',
  styleUrls: ['./purchasereturn.component.scss']
})
export class PurchasereturnComponent implements OnInit {
  inventory: any[] = [];
  selectedbinner : any[] = [];
  email = new FormControl('', [Validators.required, Validators.email, Validators.pattern("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}")]);
  displayedColumns: string[] = ['no','lineReference', 'orderType','orderedQty','sku','skuDescription','uom', 'delete'];
  dataSource = new MatTableDataSource<any>([]);
  screenid= 1037;

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

 

  returnPOHeader= this.fb.group({
    branchCode: [[this.auth.plantId]],
    companyCode: [[this.auth.companyId]],
    isCancelled: [],
    isCompleted: [],
    languageId: [[this.auth.languageId]],
    middlewareId: [],
    middlewareTable: [],
    poNumber: [],
    requiredDeliveryDate: [],
    storeID: [],
    storeName: [],
    updatedOn: [],
    wareHouseId: [[this.auth.warehouseId]],
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

    private service: PreoutboundService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private bonService: BusinessPartnerService,
    private masterService: MasterService,
    private auth: AuthService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private router: Router,
    private cs: CommonService,
  ) { }

  ngOnInit(): void {
    this.dropdownfill();
    this.returnPOHeader.get('wareHouseId')?.disable();
  this.returnPOHeader.get('branchCode')?.disable();
  this.returnPOHeader.get('companyCode')?.disable();
  this.returnPOHeader.get('languageId')?.disable();
  }

 multiWarehouseList: any[] = [];
  multiPartnerList: any[] = [];
  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),
      partner: this.bonService.search({warehouseId: [this.auth.warehouseId], businessPartnerType: [2]}).pipe(catchError(err => of(err))),
    })
    .subscribe(({ warehouse, partner }) => {
      warehouse.forEach(x => this.multiWarehouseList.push({ value: x.warehouseId, label: x.warehouseId }));
      partner.forEach(x => this.multiPartnerList.push({ value: x.partnerCode, label: x.partnerCode + ' - ' + x.partnerName }));
      this.returnPOHeader.get('soHeader.wareHouseId')?.patchValue(this.auth.warehouseId)
    })
    this.spin.hide();
  }


  submit() {
    let obj: any = {};
    let returnPOHeader: any = {};
    returnPOHeader.storeID = this.auth.plantId;
    returnPOHeader.storeName = this.returnPOHeader.controls.storeName.value;
    returnPOHeader.poNumber = this.returnPOHeader.controls.poNumber.value;
    returnPOHeader.wareHouseId = this.auth.warehouseId;
    returnPOHeader.branchCode=this.auth.plantId;
    returnPOHeader.companyCode=this.auth.companyId;
    returnPOHeader.languageId=this.auth.languageId;
    returnPOHeader.isCancelled=this.returnPOHeader.controls.isCancelled.value;
    returnPOHeader.isCompleted=this.returnPOHeader.controls.isCompleted.value;
    //soHeader.requiredDeliveryDate = this.soHeader.controls.requiredDeliveryDate.value;
    const originalDate = this.returnPOHeader.controls.requiredDeliveryDate.value;
    returnPOHeader.requiredDeliveryDate = this.cs.dateddMMYY(originalDate);
    obj.returnPOHeader = returnPOHeader;
   obj.returnPOLine= this.inventory,
console.log(returnPOHeader.requiredDeliveryDate);
this.returnPOHeader.controls.requiredDeliveryDate.patchValue(returnPOHeader.requiredDeliveryDate);
    this.sub.add(this.service.createretrunpo(obj).subscribe(res => {
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
    const dialogRef = this.dialog.open(PurchasereturnNewComponent, {
      disableClose: true,
      width: '50%',
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
    console.log(this.inventory[rowIndex])
    const dialogRef = this.dialog.open(PurchasereturnNewComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      data:{pageflow:data,code:this.inventory[rowIndex]},
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





