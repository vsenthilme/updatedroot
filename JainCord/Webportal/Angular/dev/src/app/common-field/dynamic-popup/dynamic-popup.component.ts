import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PreinboundService } from 'src/app/main-module/Inbound/preinbound/preinbound.service';
import { BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';
import { preoutbound } from 'src/app/main-module/Outbound/preoutbound/preoutbound-main/preoutbound-main.component';
import { PreoutboundService } from 'src/app/main-module/Outbound/preoutbound/preoutbound.service';

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
    private inbound: PreinboundService,
    public toastr: ToastrService,
    private outbound: PreoutboundService,
    private auth: AuthService,
    private spin: NgxSpinnerService) { }


  line: any[] = [];
  sortField:any;
  sortOrder:any;
  private intervalId: any;

  ngOnInit(): void {

    if (this.data.pageFrom == 'inboundOrder') {
      this.inboundapiCall();
      this.intervalId = setInterval(() => {
        this.inboundapiCall();
      }, 5000);
  }

    if (this.data.pageFrom == 'outboundOrder') {
      this.outboundapiCall();
      this.intervalId = setInterval(() => {
        this.outboundapiCall();
      }, 5000);
    }

    if (this.data.pageFrom == 'orderManagement') {
      this.line = this.data.lines;    
      this.sortField = 'orderReceivedOn';
      this.sortOrder = '1';
    }

  }

  stopInterval() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = null;
    }
  }


  ngOnDestroy() {
    this.stopInterval();
  }


  inboundapiCall() {
    let obj: any = {};
    obj.processedStatusId = [0, 100]
    obj.warehouseId = [this.auth.warehouseId];
    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];

    this.inbound.searchInboundHeader(obj).subscribe((res: any[]) => {
      this.line = [];
      this.line = res;
      this.sortField = 'orderReceivedOn';
      this.sortOrder = '1';

      if (res.length === 0) {
        this.toastr.warning("All orders have been processed", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.dialogRef.close()
        return;
      }
    })
  }

  outboundapiCall() {
    let obj: any = {};
    obj.processedStatusId = [0, 100]
    obj.warehouseId = [this.auth.warehouseId];
    obj.companyCode = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.plantId = [this.auth.plantId];

    this.outbound.searchOutboundHeader(obj).subscribe((res: any[]) => {
      this.line = [];
      this.line = res;
      this.sortField = 'orderReceivedOn';
      this.sortOrder = '1';

      if (res.length === 0) {
        this.toastr.warning("All orders have been processed", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.dialogRef.close()
        return;
      }
    })
  }


  callTableHeader() {

  }

  close() {
    this.dialogRef.close();
  }
}
