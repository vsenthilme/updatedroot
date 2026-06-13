import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { BOMService } from '../../../bom/bom.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-recipe-child',
  templateUrl: './recipe-child.component.html',
  styleUrls: ['./recipe-child.component.scss']
})
export class RecipeChildComponent implements OnInit {
  
  advanceFilterShow: boolean;
  @ViewChild('Setupstoragesection') Setupstoragesection: Table | undefined;
  bomLines: any[] = []; // Initialize bomLines as an array
  resultCode: any[] = []; // Initialize resultCode as an array

  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

  constructor(
    public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public cs: CommonService,
    public currency: BOMService,
    private fb: FormBuilder,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    ;
   // console.log(this.data.pageflow);

    if (this.data.pageflow === "Edit") {
      // Fetch initial bomLines based on this.data.code2[0].referenceField1
      let obj2: any = {
        companyCodeId: [this.auth.companyId],
        plantId: [this.auth.plantId],
        languageId: [this.auth.languageId],
        warehouseId: [this.auth.warehouseId],
        parentItemCode: [this.data.code2[0].referenceField1]
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
          this.fetchResultCode();
        },
        (err: any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      );
    } else if (this.data.pageflow === 'Display') {
      // Fetch bomLines based on receipeId for display mode
      let obj2: any = {
        companyCodeId: [this.auth.companyId],
        plantId: [this.auth.plantId],
        languageId: [this.auth.languageId],
        warehouseId: [this.auth.warehouseId],
        receipeId: [this.data.code.receipeId]
      };

      this.currency.searchMasterReceipe(obj2).subscribe(
        (res: any) => {
          this.spin.hide();
          this.bomLines = res;
         // console.log(this.bomLines);
        },
        (err: any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      );
    } else if (this.data.pageflow === 'New') {
      // Fetch initial bomLines for new entry based on this.data.code2[0].referenceField1
      let obj2: any = {
        companyCodeId: [this.auth.companyId],
        plantId: [this.auth.plantId],
        languageId: [this.auth.languageId],
        warehouseId: [this.auth.warehouseId],
        parentItemCode: [this.data.code2[0].referenceField1]
      };

      this.currency.search(obj2).subscribe(
        (res: any) => {
          this.spin.hide();
          this.bomLines = res[0].bomLines;
          //console.log(this.bomLines);
        },
        (err: any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      );
    }
  }

  fetchResultCode() {
    // Fetch resultCode based on phaseNumber and receipeId
    for (let i = 0; i < this.data.code3.length; i++) {
      if (this.data.code.phaseNumber === this.data.code3[i].phaseNumber) {
        let obj1: any = {
          companyCodeId: [this.auth.companyId],
          plantId: [this.auth.plantId],
          languageId: [this.auth.languageId],
          warehouseId: [this.auth.warehouseId],
          phaseNumber: [this.data.code.phaseNumber],
          receipeId: [this.data.code3[i].receipeId]
        };

        this.currency.searchMasterReceipe(obj1).subscribe(
          (res: any) => {
            this.spin.hide();
            this.resultCode = res;
          

            this.bomLines.forEach(line => {
              let found = false;
              this.resultCode.forEach(code => {
                if (code.childItemCode === line.childItemCode) {
                  line.requiredQuantity = code.requiredQuantity;
                  found = true;
                }
              });
  
               // If no matching itemcode found, set requiredQuantity to null
               if (!found) {
                 line.requiredQuantity = null;
               }
            });
  
         
          },
          (err: any) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
          }
        );
      }
    }
  }

  nonZeroBOMline: any[] = [];
  
  Submit(){
    console.log(this.bomLines)
    for(let i=0;i<this.bomLines.length;i++){
      this.bomLines[i].phaseNumber=this.data.code.phaseNumber
      this.bomLines[i].phaseDescription=this.data.code.referenceField1
      this.bomLines[i].openClose=true;
      this.bomLines[i].companyCodeId=this.auth.companyId;
    
        this.nonZeroBOMline.push(this.bomLines[i]);
      
    }
    console.log(this.nonZeroBOMline)
    this.dialogRef.close(this.nonZeroBOMline)
   }  

  cancel() {
    this.dialogRef.close();
  }
}