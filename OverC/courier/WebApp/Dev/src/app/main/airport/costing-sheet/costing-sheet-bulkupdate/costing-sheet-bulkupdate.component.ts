import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { ConsignmentService } from '../../../operation/consignment/consignment.service';
import { CostingSheetService } from '../costing-sheet.service';
import { CustomsChargesMasterService } from '../../../master/customs-charges-master/customs-charges-master.service';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';

@Component({
  selector: 'app-costing-sheet-bulkupdate',
  templateUrl: './costing-sheet-bulkupdate.component.html',
  styleUrl: './costing-sheet-bulkupdate.component.scss'
})
export class CostingSheetBulkupdateComponent {
  status: any[] = [];
  incoTerms: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<CostingSheetBulkupdateComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private CostingSheetService: CostingSheetService,
    private numberRangeService: NumberrangeService,
    private service: CustomsChargesMasterService,
    private cas: CommonAPIService,
    private auth: AuthService,
  ) {
    this.incoTerms = [
      { value: 'FoodApprovals', label: 'FoodApprovals' },
      { value: 'HandlingFees', label: 'HandlingFees' },
      { value: 'OtherApprovals', label: 'OtherApprovals' },
      { value: 'OtherCharges', label: 'OtherCharges' }
    ];
  }

  form = this.fb.group({
  costAmount: [],
  costDescription: [],
  cashNumber:[],
  lineNumber:[],
  partnerId:[],
  remark:[],
  });

  updateCalculation:any[]=[];
  ngOnInit(): void {
    console.log(this.data.code)
     if(this.data.title == 'Customs Calculation'){
       this.form.controls.costDescription.patchValue(this.data.code[0].costDescription)
       this.form.controls.costDescription.disable();
      this.intialCall();
      this.cashNumberCall();

     }
  }
 intialCall(){
    setTimeout(() => {
      this.spin.show();
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.service.search(obj).subscribe({
        next: (res: any) => {
          this.form.controls.costAmount.patchValue(res[0].docHandling)
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }, 2000);
  
 }
 lineRef:any;
 cashNumber:any;
 nextNumber: any;
 cashNumberCall(){
  setTimeout(() => {
    this.spin.show();
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.costCenter=[this.data.code[0].costCenter]
    this.CostingSheetService.search(obj).subscribe({
      next: (res: any) => {
        if(res.length>0){
        this.form.controls.cashNumber.patchValue(res[0].cashNumber)
        this.form.controls.lineNumber.patchValue((res.length)+1)
        this.form.controls.partnerId.patchValue(res[0].partnerId)
        }
        else{
         this.lineRef='1';
        this.form.controls.lineNumber.patchValue(this.lineRef)
        this.form.controls.partnerId.patchValue(this.data.code[0].partnerId)
        this.spin.show();
        let obj: any = {};
        obj.numberRangeObject = ['CashNumber'];
        this.numberRangeService.search(obj).subscribe({
          next: (res: any) => {
            if (res.length > 0) {
              this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
              this.form.controls.cashNumber.patchValue(this.nextNumber);
            }
            this.spin.hide();
          },
          error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          },
        });
        }
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }, 2000);

}
  selectedCustomCosting: any[] = [];
  customCostingCreate:any[]=[];
  save() {
    if(this.data.title == 'Custom Calculations'){
    this.data.code.forEach((element:any) => {
      element.lineNumber = null;
      element.costDescription = this.form.controls.costDescription.value;
      element.costAmount = this.form.controls.costAmount.value;
      element.statusId = 4;
      element.statusDescription = 'Send to operations';
    });
    
   this.spin.show();
    this.CostingSheetService.CustomsCalculationBulkUpdate(this.data.code).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: 'Selected Values has been updated successfully',
        });
        this.dialogRef.close()
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }
  if(this.data.title == 'Custom Calculation'){
    const element = {
      lineNumber: this.form.controls.lineNumber.value,
      costAmount: this.form.controls.costAmount.value,
      costCenter: this.data.code[0].costCenter,
      languageId:this.auth.languageId,
      companyId:this.auth.companyId,
    cashNumber:this.form.controls.cashNumber.value,
    partnerId:this.form.controls.partnerId.value,
    };
    this.customCostingCreate.push(element);
    this.CostingSheetService.Create(this.customCostingCreate).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Created',
          key: 'br',
          detail: 'Selected Values has been Created successfully',
        });
        this.dialogRef.close()
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }
  else{
    if((this.data.title != 'Custom Calculation') && (this.data.title !='Custom Calculations')){
    this.spin.show();
    let obj:any={};
    obj.costCenter=[this.data.code[0].costCenter];
    this.CostingSheetService.searchCalculation(obj).subscribe({
      next: (res) => {
        this.updateCalculation=res;
     
    this.updateCalculation.forEach((element:any) => {
      element.remark = this.form.controls.remark.value;
      element.statusId='4';
      element.statusDescription='Send to Operation';
    
    });
    this.CostingSheetService.bulkUpdate(this.updateCalculation).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Updated',
          key: 'br',
          detail: 'Selected Values has been updated successfully',
        });
        this.dialogRef.close()
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
    this.spin.hide();
  },
  error: (err) => {
    this.spin.hide();
    this.cs.commonerrorNew(err);
  },
});
  }
}
}

}





