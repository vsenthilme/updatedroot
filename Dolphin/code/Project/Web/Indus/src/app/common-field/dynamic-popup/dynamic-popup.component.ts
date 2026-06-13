import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { CommonService } from 'src/app/common-service/common-service.service';
import { BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';

@Component({
  selector: 'app-dynamic-popup',
  templateUrl: './dynamic-popup.component.html',
  styleUrls: ['./dynamic-popup.component.scss']
})
export class DynamicPopupComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private bom: BOMService,
    private cs: CommonService,
    private spin: NgxSpinnerService) { }


  line: any[] = [];
  sortField:any;
  sortOrder:any;
  ngOnInit(): void {
    if (this.data.pageFrom == 'masterReceipe') {
      let obj: any = {};
      obj.operationNumber = [this.data.lines.operationNumber];
      obj.receipeId = [this.data.lines.receipeId];
      obj.itemCode = [this.data.lines.itemCode];
      this.bom.searchMasterReceipe(obj).subscribe(res => {
        this.line = res;
        this.sortField = 'phaseNumber';
        this.sortOrder = '1';
      }, error => {
        this.cs.commonerrorNew(error);
        this.spin.hide();
      })
    }
  }





  callTableHeader() {

  }
}
