import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { QualityService } from 'src/app/main-module/Inbound/quality/quality.service';

@Component({
  selector: 'app-customdialog2',
  templateUrl: './customdialog2.component.html',
  styleUrls: ['./customdialog2.component.scss']
})
export class Customdialog2Component implements OnInit {
  orderManagement: any[] = [];
  selectedOrderManagement: any[] = [];
  form = this.fb.group({
    remarks: [],
  });
  constructor(public dialogRef: MatDialogRef<Customdialog2Component>, private fb: FormBuilder,private qulaityService: QualityService, public auth: AuthService, public cs: CommonService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {

    this.fill();
  }
  onNoClick() { this.dialogRef.close(this.data); }
  fill() {
    if(this.data.title == 'Quality Details'){
      let obj: any = {};
      obj.languageId=[this.auth.languageId];
      obj.companyCodeId=[this.auth.companyId];
      obj.warehouseId=[this.auth.warehouseId];
      obj.plantId=[this.auth.plantId];
      obj.refDocNumber=[this.data.data.refDocNumber];
      obj.itemCode=[this.data.data.itemCode];
      (this.qulaityService.searchQualityLines(obj).subscribe(res => {
        this.orderManagement = res;
 
        //this.totalRecords = this.dataSource.data.length
      },
        err => {
          this.cs.commonerrorNew(err);
        
        }));
    }
    if (this.data.value != null) {
      this.form.controls.remarks.patchValue(this.data.value);
    }
  }
  cancel() {
    if (this.data.value != null) {
      this.data.remarks = this.form.controls.remarks.value;
      this.data.button = "cancel";
      this.dialogRef.close(this.data);

    }
    else {
      this.dialogRef.close();
    }
  }
  submit() {
    this.data.remarks = this.form.controls.remarks.value;


    this.dialogRef.close(this.data);
  }

}

