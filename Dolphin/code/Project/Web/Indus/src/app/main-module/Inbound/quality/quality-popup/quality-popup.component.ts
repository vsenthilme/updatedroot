import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/Auth/auth.service';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';


@Component({
  selector: 'app-quality-popup',
  templateUrl: './quality-popup.component.html',
  styleUrls: ['./quality-popup.component.scss']
})
export class QualityPopupComponent implements OnInit {
  statusIdList:any[]=[];
  form = this.fb.group({
    remarks:[],
    impurities:[],
    statusId:[],
    storageSectionId:[],
    sampleQuantity:[],
    warehouseId:[],
   });
   constructor(public dialogRef: MatDialogRef<QualityPopupComponent>,private fb: FormBuilder,   private basicDataService: BasicdataService, private cs: CommonService, private auth: AuthService,
    private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
     @Inject(MAT_DIALOG_DATA) public data: any) {
      this.statusIdList = [
        { value: '94', label: 'Approved' },
        { value: '95', label: 'Rejected' },
        { value: '96', label: 'Conditionally Approved' }
      ];
      }
 
   ngOnInit(): void {
   this.dropdownlist();
   this.fill();

   }
   sub = new Subscription();
   storagesectionList: any = [];
   multistoragelist: any[] = [];
   multiSelectstorageList: any[] = [];
   filterstoragesectionList: any[] = [];
   dropdownlist() {
    this.spin.show();
    let obj: any = {};
    obj.warehouseId = [this.auth.warehouseId];
    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];
    this.sub.add(this.basicDataService.searchStorage(obj).subscribe(res => {
      res.forEach(x => this.multistoragelist.push({ value: x.storageSectionId, label: x.storageSection }))
      this.multiSelectstorageList = this.multistoragelist;
      this.multiSelectstorageList = this.cs.removeDuplicatesFromArrayNewstatus(this.multiSelectstorageList)
      this.spin.hide();
    },
      err => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    ));
  }



   onNoClick() { this.dialogRef.close(this.data); }
 fill(){
 ;
 if(this.data.value != null){
   this.form.controls.remarks.patchValue(this.data.value);
 }
 }
 cancel(){
   ;
   if(this.data.value !=null){
   this.data.remarks = this.form.controls.remarks.value;
   this.data.button="cancel";
   this.dialogRef.close(this.data); 
   
   }
   else{
     this.dialogRef.close();
   }
 }
   submit(){
     this.data.analysis = this.form.controls.remarks.value;
    this.data.impurities=this.form.controls.impurities.value;
     this.data.statusId=this.form.controls.statusId.value;
     this.data.storageSectionId=this.form.controls.storageSectionId.value;
     this.data.sampleQuantity=this.form.controls.sampleQuantity.value;
     this.dialogRef.close(this.data);
    // this.dialogRef.close(this.data.remarks);
   }
   
  }
 
 