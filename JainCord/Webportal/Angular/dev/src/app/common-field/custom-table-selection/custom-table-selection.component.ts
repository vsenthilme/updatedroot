import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';
import { HandlingUnitService } from 'src/app/main-module/Masters -1/other-masters/handling-unit/handling-unit.service';

@Component({
  selector: 'app-custom-table-selection',
  templateUrl: './custom-table-selection.component.html',
  styleUrls: ['./custom-table-selection.component.scss']
})
export class CustomTableSelectionComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<any>,
     @Inject(MAT_DIALOG_DATA) public data: any,
     private bom: BOMService,
     private cs: CommonService,
     private HandlingUnitService: HandlingUnitService,
     public toastr: ToastrService,
     private auth: AuthService,
     private spin: NgxSpinnerService) { }
 
 
   line: any[] = [];
   sortField:any;
   sortOrder:any;
   private intervalId: any;
 
   ngOnInit(): void {
 
    if (this.data.pageFrom == 'pallet') {
      this.callhenumber();
  }

   }


   callhenumber(){
    let obj: any = {};
    obj.warehouseId = [this.auth.warehouseId];
    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];

    this.HandlingUnitService.search(obj).subscribe((res: any[]) => {
      this.line = [];
      this.line = res;
    })
   }

   selectedRow:any[] = [];

   onChange() {
    const choosen= this.selectedRow[this.selectedRow.length - 1];   
    this.selectedRow.length = 0;
    this.selectedRow.push(choosen);
  } 

  save(){
    if (this.data.pageFrom == 'pallet') {
      this.dialogRef.close(this.selectedRow[0].handlingUnit)
    }
  }

  close(){
    this.dialogRef.close();
  }
 
  }
 