import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BOMService } from '../../bom/bom.service';

@Component({
  selector: 'app-masterrecipe-table',
  templateUrl: './masterrecipe-table.component.html',
  styleUrls: ['./masterrecipe-table.component.scss']
})
export class MasterrecipeTableComponent implements OnInit {

 
  advanceFilterShow: boolean;
  @ViewChild('Setupstoragesection') Setupstoragesection: Table | undefined;
  OrderDetails: any;
  selectedOrderDetails : any;
  
  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  constructor( public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
   // private cas: CommonApiService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public cs: CommonService,
   public currency: BOMService,
   // private excel: ExcelService,
    private fb: FormBuilder,
    private auth: AuthService,) { }
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
  applyFilterGlobal($event: any, stringVal: any) {
    this.Setupstoragesection!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
  
  
  bomLines: any[]=[];
  ngOnInit(): void {
    
    if(this.data.pageflow != 'Edit'){
    this.bomLines = this.data.bomLines;
    }
    if(this.data.pageflow == 'Edit'){
      let obj2: any = {
        companyCodeId: [this.auth.companyId],
        plantId: [this.auth.plantId],
        languageId: [this.auth.languageId],
        warehouseId: [this.auth.warehouseId],
        bomNumber: [this.data.code.bomNumber]
      };

      this.currency.search(obj2).subscribe(
        (res: any) => {
          this.spin.hide();
          this.bomLines = res[0].bomLines;
          // // Initialize requiredQuantity based on childItemQuantity
          // this.bomLines.forEach(line => {
          //   line.requiredQuantity = line.childItemQuantity;
          // });
         // console.log(this.bomLines);
          // After fetching bomLines, fetch resultCode from master recipe API
          //this.fetchResultCode();
        },
        (err: any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      );

    }
  }
  
  
  }
  
  
  
  
  
