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
import { ConsignmentUpdatebulkComponent } from '../../../operation/consignment/consignment-updatebulk/consignment-updatebulk.component';
import { ConsignmentService } from '../../../operation/consignment/consignment.service';
import { PrealertService } from '../prealert.service';

@Component({
  selector: 'app-pre-alert-bulk',
  templateUrl: './pre-alert-bulk.component.html',
  styleUrl: './pre-alert-bulk.component.scss'
})
export class PreAlertBulkComponent {
  status: any[] = [];
  incoTerms: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ConsignmentUpdatebulkComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsignmentService,
    private messageService: MessageService,
    private prealertService: PrealertService,
    private cas: CommonAPIService,
    private auth: AuthService,
  ) {
    this.incoTerms = [
      { value: 'DDU', label: 'DDU' },
      { value: 'DDP', label: 'DDP' }
    ];
  }

  // form builder initialize
  form = this.fb.group({
    actualCurrency: [],
    actualValue: [],
    bondedId: [],
    airportOriginList: [],
    bayanHv: [],
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
    flightName: [],
    eventTimestamp: [],
    estimatedTimeOfArrival: [],
    estimatedTimeOfDeparture: [],
    hubCode: [],
    expectedDuty: [],
    finalDestination: [],
    freightCharges: [],
    freightCurrency: [],
    goodsDescription: [],
    goodsType: [],
    grossWeight: [],
    houseAirwayBill: [],
    hsCode: [],
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
    origin: [],
    originCode: [],
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
    referenceField2: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
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
    referenceField20: [],
    selectedConsole: [],
    remarks: [],
    serviceTypeId: [],
    serviceTypeName: [],
    shipmentBagId: [],
    shipperId: [],
    shipperName: [],
    specialApprovalValue: [],
    statusId: [],
    statusTimestamp: [],
    subProductId: [],
    subProductName: [],
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
  hubList: any[] = [];
  Consigment: any[] = [];
  currencyIdList: any[] = [];
  airportOriginList: any[] = [];

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
      this.cas.dropdownlist.setup.currency.url,
      this.cas.dropdownlist.setup.airportOrigin.url,
    ]).subscribe({
      next: (results: any) => {

        this.statusList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.opStatus.key);
        this.countryIdList = this.cas.forLanguageFilter(results[1], this.cas.dropdownlist.setup.country.key);
        this.hsCodeList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.hsCode.key);
        this.eventList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.event.key);
        const consitnor = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.consignor.key);
        const customer = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.customer.key);
        this.hubList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.hub.key);
        this.currencyIdList = this.cas.foreachlist(results[7], this.cas.dropdownlist.setup.currency.key);
        this.airportOriginList = this.cas.forLanguageFilter(results[8], this.cas.dropdownlist.setup.airportOrigin.key);
        customer.forEach(x => this.consignorIdList.push(x));
        consitnor.forEach(x => this.consignorIdList.push(x));
        this.consignorIdList = this.cs.removeDuplicatesFromArrayList(this.consignorIdList, 'value')
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
    console.log(this.data)
    console.log(this.data.title)
    this.Consigment = this.data.code;
    console.log(this.currencyIdList)
  }

  showHub = false;
  eventChange() {
    if ((this.form.controls.eventCode.value == '15') && (this.data.title == 'Consignment')) {
      this.showHub = true;
    }
  }
  save() {
    this.spin.show();
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
    if (this.form.controls.hsCode.value != null) {
      this.Consigment.forEach((x: any) => {
        x.hsCode = this.form.controls.hsCode.value;
      });
    }
    // if (this.form.controls.incoTerms.value != null) {
    //   this.Consigment.forEach((x: any) => {
    //     x.incoTerms = this.form.controls.incoTerms.value;
    //   });
    // }
    if (this.form.controls.bayanHv.value != null) {
      this.Consigment.forEach((x: any) => {
        x.bayanHv = this.form.controls.bayanHv.value;
      });
    }
    if (this.form.controls.flightName.value != null) {
      this.Consigment.forEach((x: any) => {
        x.flightName = this.form.controls.flightName.value;
      });
    }
    // if (this.form.controls.currency.value != null) {
    //   this.Consigment.forEach((x: any) => {
    //     x.currency = this.form.controls.currency.value;
    //   });
    // }
    if (this.form.controls.origin.value != null) {
      this.Consigment.forEach((x: any) => {
        x.origin = this.form.controls.origin.value;
      });
    }
    if (this.form.controls.airportOriginList.value != null) {
      this.Consigment.forEach((x: any) => {
        x.airportOriginList = this.form.controls.airportOriginList.value;
      });
    }
    if (this.form.controls.estimatedTimeOfArrival.value != null) {
      this.Consigment.forEach((x: any) => {
        x.estimatedTimeOfArrival = this.form.controls.estimatedTimeOfArrival.value;
      });
    }
    if (this.form.controls.estimatedTimeOfDeparture.value != null) {
      this.Consigment.forEach((x: any) => {
        x.estimatedTimeOfDeparture = this.form.controls.estimatedTimeOfDeparture.value;
      });
    }
      this.prealertService.update(this.Consigment).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: 'Selected Values has been updated successfully',
          });

          if (this.data.title == 'Consignment') {
            this.dialogRef.close()
            this.router.navigate(['/main/operation/consignment']);
          }
          else {
            this.dialogRef.close()
            this.router.navigate(['/main/airport/preAlertManifest']);
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
   
  }
  
  cancel() {
    if (this.data.title.value == 'Consignment') {
      this.dialogRef.close()
      this.router.navigate(['/main/operation/consignment']);
    }
    if (this.data.title == 'PreAlertManifest') {
      this.dialogRef.close()
      // this.router.navigate(['/main/airport/preAlertManifest']);
    }
    if (this.data.title == 'PreAlertManifest - Edit') {
      this.dialogRef.close()
      this.router.navigate(['/main/airport/preAlertManifest-update']);
    }
    if (this.data.line == 'Bonded Manifest') {
      this.dialogRef.close()
      this.router.navigate(['/main/airport/bondedManifest']);
    }
  }

}




