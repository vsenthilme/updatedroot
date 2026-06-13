import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { CcrService } from '../ccr.service';

@Component({
  selector: 'app-ccr-editpopup',
  templateUrl: './ccr-editpopup.component.html',
  styleUrl: './ccr-editpopup.component.scss'
})
export class CcrEditpopupComponent {

  status: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<CcrEditpopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: CcrService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
  ) {

  }

  // form builder initialize
  form = this.fb.group({
    actualCurrency: [],
    airportOriginCode: [, Validators.required],
    bondedId: [],
    ccrId: [],
    companyId: [],
    companyName: [],
    consigneeCivilId: [],
    consigneeName: [],
    consignmentCurrency: [, Validators.required],
    consignmentValue: [],
    consignmentValueLocal: [],
    consoleId: [],
    countryOfOrigin: [],
    createdBy: [],
    createdOn: [],
    currency: [],
    customsCcrNo: [],
    customsKd: [, Validators.required],
    customsValue: [],
    customsInsurance: [],
    calculatedTotalDuty: [],
    declaredValue: [],
    deletionIndicator: [],
    description: [],
    duty: [],
    dduCharge: [],
    eventCode: [],
    eventText: [],
    eventTimestamp: [],
    exemptionBeneficiary: [],
    exemptionFor: [],
    exemptionReference: [],
    exchangeRate: [],
    finalDestination: [],
    flightArrivalTime: [],
    flightNo: [],
    freightCharges: [],
    freightCurrency: [],
    goodsDescription: [],
    goodsType: [],
    grossWeight: [],
    houseAirwayBill: [],
    hsCode: [],
    iata: [, Validators.required],
    addIata: [],
    addInsurance: [],
    incoTerms: [],
    invoiceDate: [],
    invoiceNumber: [],
    invoiceSupplierName: [],
    invoiceType: [],
    isConsolidatedShipment: [],
    isExempted: [],
    isPendingShipment: [],
    isSplitBillOfLading: [],
    landedQuantity: [],
    languageDescription: [],
    languageId: [],
    manifestedGrossWeight: [],
    manifestedQuantity: [],
    manufacturer: [],
    masterAirwayBill: [],
    netWeight: [],
    noOfPackageMawb: [],
    noOfPieceHawb: [],
    notifyParty: [],
    packageType: [],
    partnerHouseAirwayBill: [],
    partnerId: [],
    partnerMasterAirwayBill: [],
    partnerName: [],
    partnerType: [],
    paymentType: [],
    pieceId: [],
    pieceItemId: [],
    primaryDo: [],
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
    remarks: [],
    secondaryDo: [],
    serviceTypeId: [],
    serviceTypeName: [],
    shipperId: [],
    shipperName: [],
    specialApprovalValue: [],
    statusId: [],
    statusTimestamp: [],
    subProductId: [],
    subProductName: [],
    tareWeight: [],
    totalDuty: [],
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
  iataList: any[] = [];
  originCodeList: any[] = [];
  currencyIdList: any[] = [];
  consignorIdList: any[] = [];


  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.currency.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.iata.url,
    ]).subscribe({
      next: (results: any) => {
        this.currencyIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.currency.key);
        this.countryIdList = this.cas.forLanguageFilter(results[1], this.cas.dropdownlist.setup.country.key);
        this.consignorIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.consignor.key);
        this.iataList = this.cas.forLanguageFilterWithoutKey(results[3], this.cas.dropdownlist.setup.iata.key);
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
    if (this.data.pageflow == "Edit") {
      this.form.patchValue(this.data.code)
      this.form.controls.houseAirwayBill.disable();
      this.form.controls.masterAirwayBill.disable();
      this.form.controls.partnerHouseAirwayBill.disable();
      this.form.controls.partnerMasterAirwayBill.disable();
    }

  }
  selecetedTrasnfer: any[] = [];
  save() {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }
    this.dialogRef.close(this.form.getRawValue());
  }

}


