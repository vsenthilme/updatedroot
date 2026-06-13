import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonServiceService } from '../../../../../common-service/common-service.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConsignmentService } from '../../consignment.service';
import { AuthService } from '../../../../../core/core';

@Component({
  selector: 'app-consignment-status-popup',
  templateUrl: './consignment-status-popup.component.html',
  styleUrl: './consignment-status-popup.component.scss'
})
export class ConsignmentStatusPopupComponent { 

  constructor(
    public dialogRef: MatDialogRef<ConsignmentStatusPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private service: ConsignmentService,
    private auth: AuthService,

  ) { }

  ngOnInit(): void {
    
    // console.log(this.data.code);
    this.callCNTable();
  }

  cnTable: any[] = [];

  callCNTable(){
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.pieceId = [this.data.code.pieceId];
    obj.hawbType = [this.data.code.hawbType];

    this.service.searchStatus(obj).subscribe({next: res=> {
      // console.log(res);
      // res = this.cs.removeDuplicatesFromArrayList(res, 'houseAirwayBill');
      this.cnTable =  res;
      console.log(this.cnTable);
    },error: err => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }

}
