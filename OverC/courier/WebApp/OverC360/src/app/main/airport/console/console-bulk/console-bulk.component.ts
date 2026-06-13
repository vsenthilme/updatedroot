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
import { ConsoleService } from '../console.service';

@Component({
  selector: 'app-console-bulk',
  templateUrl: './console-bulk.component.html',
  styleUrl: './console-bulk.component.scss'
})
export class ConsoleBulkComponent {
  status: any[] = [];
  incoTerms: any[] = [];
  constructor(
    public dialogRef: MatDialogRef<ConsoleBulkComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsoleService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
  ) {
    this.incoTerms = [
      { value: 'DDU', label: 'DDU' },
      { value: 'DDU', label: 'DDU' }
    ];

  }

  // form builder initialize
  form = this.fb.group({
    actualCurrency: [],
    actualValue: [],
    airportOriginCode: [],
    bondedId: [],
    companyId: [],
    companyName: [],
    consigneeCivilId: [],
    consigneeName: [],
    consignmentCurrency: [],
    consignmentValue: [],
    consoleId: [],
    countryOfOrigin: [],
    createdBy: [],
    createdOn: [],
    currency: [],
    customsCurrency: [],
    customsKd: [],
    customsValue: [],
    declaredValue: [],
    deletionIndicator: [],
    description: [],
    eventCode: [],
    eventText: [],
    flightNo: [],
    eventTimestamp: [],
    expectedDuty: [],
    finalDestination: [],
    freightCharges: [],
    freightCurrency: [],
    goodsDescription: [],
    pieceId: [],
    pieceItemId: [],
    goodsType: [],
    grossWeight: [],
    houseAirwayBill: [],
    hsCode: [],
    hubCode: [],
    iataKd: [],
    incoTerms: [],
    invoiceDate: [],
    invoiceNumber: [],
    invoiceSupplierName: [],
    invoiceType: [],
    isConsolidatedShipment: [],
    isPendingShipment: [],
    isSplitBillOfLading: [],
    landedQuantity: [],
    languageDescription: [],
    languageId: [],
    manifestedGrossWeight: [],
    manifestedQuantity: [],
    masterAirwayBill: [],
    netWeight: [],
    noOfPackageMawb: [],
    noOfPieceHawb: [],
    notifyParty: [],
    partnerHouseAirwayBill: [],
    partnerId: [],
    partnerMasterAirwayBill: [],
    partnerName: [],
    partnerType: [],
    paymentType: [],
    productId: [],
    productName: [],
    quantity: [],
    referenceField1: [],
    referenceField10: [],
    referenceField11: [],
    referenceField12: [],
    referenceField13: [],
    referenceField14: [],
    referenceField15: [],
    referenceField16: [],
    referenceField17: [],
    eventDescription: [],
    referenceField18: [],
    referenceField19: [],
    referenceField2: [],
    referenceField20: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    selectedConsole: [],
    remarks: [],
    serviceTypeId: [],
    serviceTypeName: [],
    consoleGroupName: [],
    shipmentBagId: [],
    shipperId: [],
    shipperName: [],
    specialApprovalValue: [],
    statusId: [],
    statusTimestamp: [],
    customsCcrNo: [],
    subProductId: [],
    subProductName: [],
    primaryDo: [],
    secondaryDo: [],
    tareWeight: [],
    totalQuantity: [],
    updatedBy: [],
    updatedOn: [],
    volume: [],

  });

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  countryIdList: any[] = [];
  mawbList: any[] = [];
  hawbList: any[] = [];
  hsCodeList: any[] = [];
  originCodeList: any[] = [];
  statusList: any[] = [];
  consignorIdList: any[] = [];
  eventList: any[] = [];
  Consigment: any[] = [];
  hubList: any[] = [];
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.opStatus.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.hsCode.url,
      this.cas.dropdownlist.setup.event.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.customer.url,
      this.cas.dropdownlist.setup.hub.url,
    ]).subscribe({
      next: (results: any) => {

        this.statusList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.opStatus.key);
        this.countryIdList = this.cas.forLanguageFilter(results[1], this.cas.dropdownlist.setup.country.key);
        this.hsCodeList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.hsCode.key);
        this.eventList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.event.key);
        const consitnor = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.consignor.key);
        const customer = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.customer.key);
        customer.forEach(x => this.consignorIdList.push(x));
        consitnor.forEach(x => this.consignorIdList.push(x));
        this.consignorIdList = this.cs.removeDuplicatesFromArrayList(this.consignorIdList, 'value')
        this.hubList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.hub.key);
        this.statusList = this.cs.removeDuplicatesFromArrayList(this.statusList, 'value')
        this.eventList = this.cs.removeDuplicatesFromArrayList(this.eventList, 'value')
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }
  ngOnInit(): void {
    this.dropdownlist();
    this.Consigment = this.data.code;

  }
  showHub = false;
  eventChange() {
    if ((this.form.controls.eventCode.value == '15') && (this.data.title != 'Console')) {
      this.showHub = true;
      console.log()
    }
  }
  save() {
    if (this.form.controls.partnerMasterAirwayBill.value != null) {
      this.Consigment.forEach((x: any) => {
        x.partnerMasterAirwayBill = this.form.controls.partnerMasterAirwayBill.value;
      });
    }
    if (this.form.controls.countryOfOrigin.value != null) {
      this.Consigment.forEach((x: any) => {
        x.countryOfOrigin = this.form.controls.countryOfOrigin.value;
      });
    }
    if (this.form.controls.flightNo.value != null) {
      this.Consigment.forEach((x: any) => {
        x.flightNo = this.form.controls.flightNo.value;
      });
    }
    if (this.form.controls.shipperId.value != null) {
      this.Consigment.forEach((x: any) => {
        x.shipperId = this.form.controls.shipperId.value;
      });
    }
    if (this.form.controls.primaryDo.value != null) {
      this.Consigment.forEach((x: any) => {
        x.primaryDo = this.form.controls.primaryDo.value;
      });
    }
    if (this.form.controls.secondaryDo.value != null) {
      this.Consigment.forEach((x: any) => {
        x.secondaryDo = this.form.controls.secondaryDo.value;
      });
    }
    if (this.form.controls.customsCcrNo.value != null) {
      this.Consigment.forEach((x: any) => {
        x.customsCcrNo = this.form.controls.customsCcrNo.value;
      });
    }
    if (this.form.controls.consoleGroupName.value != null) {
      this.Consigment.forEach((x: any) => {
        x.consoleGroupName = this.form.controls.consoleGroupName.value;
      });
    }
    if (this.data.title == 'Console') {
      this.spin.show();
      this.service.updateSingle(this.Consigment).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: 'Selected Values has been updated successfully',
          });
          this.spin.hide();
          if (this.data.title == 'Console') {
            this.dialogRef.close()
            this.router.navigate(['/main/airport/console']);
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
    else {

      this.service.UpdateCCR(this.Consigment).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: 'Selected Values has been updated successfully',
          });
          this.dialogRef.close()
          this.router.navigate(['/main/airport/ccr']);

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



