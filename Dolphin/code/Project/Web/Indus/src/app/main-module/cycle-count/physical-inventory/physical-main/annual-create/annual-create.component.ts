import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MasterService } from 'src/app/shared/master.service';
import { AddliinesComponent } from '../../../prepetual-count/prepetual-main/create-popup/addliines/addliines.component';
import { FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatTableDataSource, MatTable } from '@angular/material/table';
import { Customdialog2Component } from 'src/app/common-field/customdialog2/customdialog2.component';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { PrepetualCountService } from '../../../prepetual-count/prepetual-count.service';

@Component({
  selector: 'app-annual-create',
  templateUrl: './annual-create.component.html',
  styleUrls: ['./annual-create.component.scss']
})
export class AnnualCreateComponent implements OnInit {
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
  
    periodicHeaderV1 = this.fb.group({
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
    this.periodicHeaderV1.get('wareHouseId')?.disable();
    this.periodicHeaderV1.get('branchCode')?.disable();
    this.periodicHeaderV1.get('companyCode')?.disable();
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
          this.periodicHeaderV1.controls.wareHouseId.patchValue(this.auth.warehouseId)
        })
    
  
      this.spin.hide();
    }
  
    submit() {
  
      let obj: any = {};
      let asnHeader: any = {};
      asnHeader.isCompleted = this.periodicHeaderV1.controls.isCompleted.value;
      asnHeader.isCancelled = this.periodicHeaderV1.controls.isCancelled.value;
      asnHeader.cycleCountNo=this.periodicHeaderV1.controls.cycleCountNo.value;
      asnHeader.isNew = this.periodicHeaderV1.controls.isNew.value;
      asnHeader.wareHouseId = this.auth.warehouseId;
      asnHeader.companyCode=this.auth.companyId;
      asnHeader.branchCode=this.auth.plantId,
      obj.periodicHeaderV1 = asnHeader;
      for(let i=0;i<this.inventory.length;i++){
        this.inventory[i].cycleCountNo=this.periodicHeaderV1.controls.cycleCountNo.value;
      }
      obj.periodicLineV1 = this.inventory;
  
      this.sub.add(this.service.createPeriodicorderCreateV2(obj).subscribe(res => {
        if (res) {
          this.toastr.success("Order created successfully!", "Notification");
          this.router.navigate(['main/cycle-count/physical-main']);
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
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  