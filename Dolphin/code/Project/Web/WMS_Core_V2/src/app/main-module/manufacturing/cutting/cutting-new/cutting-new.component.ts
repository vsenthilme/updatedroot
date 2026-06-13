import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Table } from 'primeng/table';
import { Subscription } from 'rxjs';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { PreinboundService } from 'src/app/main-module/Inbound/preinbound/preinbound.service';
import { BasicdataService } from 'src/app/main-module/Masters -1/masternew/basicdata/basicdata.service';
import { BOMService } from 'src/app/main-module/Masters -1/other-masters/bom/bom.service';
import { ReportsService } from 'src/app/main-module/reports/reports.service';

@Component({
  selector: 'app-cutting-new',
  templateUrl: './cutting-new.component.html',
  styleUrls: ['./cutting-new.component.scss']
})
export class CuttingNewComponent implements OnInit {
  confirmedActivityTime: any;
  activityTime: any;
  confirmSetupTime: any;
  confirmedMachineTime: any;
  machineTime: any;
  setupTime: any;
  confirmedQty: any;
  inputQty: any;

  advanceFilterShow: boolean;
  @ViewChild('Setupstoragesection') Setupstoragesection: Table | undefined;
  OrderDetails: any;
  selectedOrderDetails: any;

  sub = new Subscription();
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';
  constructor(public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    // private cas: CommonApiService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    private preinboundService: PreinboundService,
    private BasicdataService: BasicdataService,
    public cs: CommonService,
    public bom: BOMService,
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

  applyFilterGlobal($event: any, stringVal: any) {
    this.Setupstoragesection!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
  bomLines: any[] = [];
  itemCodeList: any[] = [];

  ngOnInit(): void {

    this.activityTime = 4;
    this.machineTime = 15;
    this.setupTime = 10;
    this.inputQty = 1;

    // this.bomLines = [];
    // this.bom.search({ parentItemCode: [this.data.itemCode] }).subscribe(res => {
    //   this.OrderDetails = res[0].bomLines;
    //   this.OrderDetails.filter(x => this.itemCodeList.push(x.childItemCode))
    // })
  }


  save() {
    this.toastr.success("Created successfully", "Notification", {
      timeOut: 2000,
      progressBar: false,
    });

    let obj: any = {};

    obj.confirmedActivityTime = this.confirmedActivityTime;
    obj.activityTime = this.activityTime;
    obj.confirmSetupTime = this.confirmSetupTime;
    obj.confirmedMachineTime = this.confirmedMachineTime;
    obj.machineTime = this.machineTime;
    obj.setupTime = this.setupTime;
    obj.confirmedQty = this.confirmedQty;
    obj.inputQty = this.inputQty;

    this.dialogRef.close("success");
  }


  generateRandomNumber(): number {
    return Math.floor(100000 + Math.random() * 900000);
  }

  inboundLines: any[] = [];
  submit() {
    this.spin.show();

    this.spin.hide();
    this.inboundLines = [];
   // this.OrderDetails.forEach((element, index) => {

      let obj2: any = {};
      obj2.companyCodeId = [this.auth.companyId];
      obj2.languageId = [this.auth.languageId];
      obj2.plantId = [this.auth.plantId];
      obj2.warehouseId = [this.auth.warehouseId];
      obj2.itemCode = [this.data.itemCode];

      this.BasicdataService.searchSpark(obj2).subscribe(res => {
        let obj1: any = {};
           obj1.lineReference =  1,
          obj1.containerNumber = 'SD-3222',
          obj1.expectedDate = this.cs.dateMMYY(new Date()),
          obj1.expectedQty = 1
          obj1.invoiceNumber = this.generateRandomNumber();
          obj1.sku = res[0].itemCode,
          obj1.skuDescription = res[0].description,
          obj1.manufacturerCode = res[0].manufacturerPartNo,
          obj1.manufacturerName = res[0].manufacturerPartNo,
          obj1.uom = 'ECH',
          obj1.supplierCode = 'NIS',
          obj1.supplierPartNumber = 'TS-233',
          obj1.inboundOrderTypeId = 5,
          this.inboundLines.push(obj1);
 
   // });

    let obj: any = {};
    let asnHeader: any = {};
    asnHeader.asnNumber = 'ASN-' + this.generateRandomNumber();
    asnHeader.wareHouseId = this.auth.warehouseId;
    asnHeader.branchCode = this.auth.plantId;
    asnHeader.companyCode = this.auth.companyId;
    asnHeader.languageId = this.auth.languageId;
    asnHeader.inboundOrderTypeId = 5;

    obj.asnHeader = asnHeader;
    obj.asnLine = this.inboundLines;

    this.sub.add(this.preinboundService.createAsnOrderV2(obj).subscribe(res => {
      this.toastr.success("Created successfully", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
        this.spin.hide();
        this.dialogRef.close("success");

    }, err => {
      this.cs.commonerrorNew(err);
      this.spin.hide();
    }));
  })
  }
}




