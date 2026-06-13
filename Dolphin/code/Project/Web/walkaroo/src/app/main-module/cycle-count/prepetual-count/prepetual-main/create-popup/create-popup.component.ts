import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PrepetualCountService } from '../../prepetual-count.service';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource, MatTable } from '@angular/material/table';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Customdialog2Component } from 'src/app/common-field/customdialog2/customdialog2.component';
import { PreinboundaddlinesComponent } from 'src/app/main-module/Inbound/preinbound/preinbound-upload/preinboundaddlines/preinboundaddlines.component';
import { PreinboundService } from 'src/app/main-module/Inbound/preinbound/preinbound.service';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { MasterService } from 'src/app/shared/master.service';
import { AddliinesComponent } from './addliines/addliines.component';

export interface SelectItem {
  id: number;
  movementTypeId: number;
  name: string;
}

@Component({
  selector: 'app-create-popup',
  templateUrl: './create-popup.component.html',
  styleUrls: ['./create-popup.component.scss']
})
export class CreatePopupComponent implements OnInit {
  inventory: any[] = [];
selectedbinner : any[] = [];
  email = new FormControl('', [Validators.required, Validators.email, Validators.pattern("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}")]);
  displayedColumns: string[] = ['containerNumber', 'expectedDate', 'expectedQty', 'invoiceNumber', 'lineReference', 'manufacturerName',
    'manufacturerCode', 'packQty', 'sku', 'skuDescription', 'supplierCode',
    'supplierPartNumber', 'uom', 'delete'];
  ELEMENT_DATA: any[] = [];
  dataSource = new MatTableDataSource<any>(this.ELEMENT_DATA);
  screenid = 1037;

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

  sub = new Subscription();

  perpetualHeaderV1 = this.fb.group({
    branchName:[],
    wareHouseId:[this.auth.warehouseId],
    companyCode:[this.auth.companyId],
    branchCode:[this.auth.plantId],
    cycleCountNo:[],
    isCancelled:[],
    isCompleted:[],
    isNew:[],
    updatedOn:[],
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
    private service: PrepetualCountService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private bonService: BusinessPartnerService,
    private masterService: MasterService,
    private auth: AuthService,
    private business: BusinessPartnerService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private router: Router,
    private cs: CommonService,
  ) { }

  ngOnInit(): void {
    console.log(this.selectedbinner);
  if(this.selectedbinner.length>0 ){
    console.log(1);
  }
    this.dropdownfill();
  this.perpetualHeaderV1.get('wareHouseId')?.disable();
  this.perpetualHeaderV1.get('branchCode')?.disable();
  this.perpetualHeaderV1.get('companyCode')?.disable();
  }

  multiWarehouseList: any[] = [];
  partnerList: any[] = [];

  dropdownfill() {
    this.spin.show();
    forkJoin({
      warehouse: this.masterService.getWarehouseMasterDetails().pipe(catchError(err => of(err))),
    })
      .subscribe(({ warehouse }) => {
        warehouse.forEach(x => this.multiWarehouseList.push({ value: x.warehouseId, label: x.warehouseId }));
        this.perpetualHeaderV1.controls.wareHouseId.patchValue(this.auth.warehouseId)
      })
  

    this.spin.hide();
  }

  submit() {

    let obj: any = {};
    let asnHeader: any = {};
    asnHeader.isCompleted = this.perpetualHeaderV1.controls.isCompleted.value;
    asnHeader.isCancelled = this.perpetualHeaderV1.controls.isCancelled.value;
    asnHeader.cycleCountNo=this.perpetualHeaderV1.controls.cycleCountNo.value;
    asnHeader.isNew = this.perpetualHeaderV1.controls.isNew.value;
    asnHeader.wareHouseId = this.auth.warehouseId;
    asnHeader.companyCode=this.auth.companyId;
    asnHeader.branchCode=this.auth.plantId,
    obj.perpetualHeaderV1 = asnHeader;
    for(let i=0;i<this.inventory.length;i++){
      this.inventory[i].cycleCountNo=this.perpetualHeaderV1.controls.cycleCountNo.value;
    }
    obj.perpetualLineV1 = this.inventory;

    this.sub.add(this.service.createPerpetualorderCreateV2(obj).subscribe(res => {
      if (res) {
        this.toastr.success("Order created successfully!", "Notification");
        this.router.navigate(['main/cycle-count/Prepetual-main/count']);
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
  onChange() {
    const choosen= this.selectedbinner[this.selectedbinner.length - 1];   
    this.selectedbinner.length = 0;
    this.selectedbinner.push(choosen);
  } 
  elementdata: any;
  @ViewChild(MatTable)
  table!: MatTable<any>;
  assigninvoice() {
    const dialogRef = this.dialog.open(Customdialog2Component, {
      // width: '60%', height: '70%',
      width: '25%',
      maxWidth: '30%',
      position: {
        top: '6.7%',
      },

      data: { title: "Assign Invoice", body: "Invoice Number" },
    });
    dialogRef.afterClosed().subscribe(result => {
      
      if (result != null) {
        for(let i=0;i<this.selectedbinner.length;i++){
       this.selectedbinner[i].invoiceNumber = result.remarks;
        }
      }
    });
  }

  add() {
    const dialogRef = this.dialog.open(AddliinesComponent, {
      disableClose: true,
      width: '70%',
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
  }
  openDialog(data: any,rowIndex): void {
    const dialogRef = this.dialog.open(AddliinesComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      data: {pageflow: data,code:this.inventory[rowIndex]},
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


















