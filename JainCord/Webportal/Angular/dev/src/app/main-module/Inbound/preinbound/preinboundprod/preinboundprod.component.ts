import { Component, OnInit, ViewChild } from '@angular/core';
import { PreinboundprodNewComponent } from './preinboundprod-new/preinboundprod-new.component';
import { FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource, MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Customdialog2Component } from 'src/app/common-field/customdialog2/customdialog2.component';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BusinessPartnerService } from 'src/app/main-module/Masters -1/other-masters/business-partner/business-partner.service';
import { MasterService } from 'src/app/shared/master.service';
import { PreinboundaddlinesComponent } from '../preinbound-upload/preinboundaddlines/preinboundaddlines.component';
import { PreinboundService } from '../preinbound.service';

@Component({
  selector: 'app-preinboundprod',
  templateUrl: './preinboundprod.component.html',
  styleUrls: ['./preinboundprod.component.scss']
})
export class PreinboundprodComponent implements OnInit {
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
  
    asnHeader = this.fb.group({
      asnNumber: [],
      supplierCode:[],
      wareHouseId: [this.auth.warehouseId],
      companyCode:[this.auth.companyId],
      branchCode:[this.auth.plantId],
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
      private service: PreinboundService,
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
    this.asnHeader.get('wareHouseId')?.disable();
    this.asnHeader.get('branchCode')?.disable();
    this.asnHeader.get('companyCode')?.disable();
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
          this.asnHeader.controls.wareHouseId.patchValue(this.auth.warehouseId)
        })
        let obj1: any = {};
  obj1.companyCodeId = [this.auth.companyId];
   obj1.plantId = [this.auth.plantId];
   obj1.languageId = [this.auth.languageId];
  obj1.warehouseId = [this.auth.warehouseId];
  obj1.businessPartnerType=[2];
  this.spin.show();
  this.business.searchSpark(obj1).subscribe((res: any[]) => {
    res.forEach(element => {
      this.partnerList.push({value: element.partnerCode, label: element.partnerCode + '-' + element.partnerName});
       });
  })
  
      this.spin.hide();
    }
  
    submit() {
  
      let obj: any = {};
      let asnHeader: any = {};
      asnHeader.asnNumber = this.asnHeader.controls.asnNumber.value;
      asnHeader.purchaseOrderNumber=this.asnHeader.controls.asnNumber.value;
      asnHeader.wareHouseId = this.auth.warehouseId;
      asnHeader.companyCode=this.auth.companyId;
      asnHeader.branchCode=this.auth.plantId,
      asnHeader.inboundOrderTypeId=6;
      obj.asnHeader = asnHeader;
      for(let i=0;i<this.inventory.length;i++){
        this.inventory[i].supplierCode=this.asnHeader.controls.supplierCode.value;
        this.inventory[i].purchaseOrderNumber=this.asnHeader.controls.asnNumber.value;
      }
      obj.asnLine = this.inventory;
  
      this.sub.add(this.service.createAsnOrderV2(obj).subscribe(res => {
        if (res) {
          this.toastr.success("Order created successfully!", "Notification");
          this.router.navigate(['/main/inbound/preinbound']);
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
      const dialogRef = this.dialog.open(PreinboundprodNewComponent, {
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
      const dialogRef = this.dialog.open(PreinboundaddlinesComponent, {
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
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  