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
import { ConsoleTransferComponent } from '../console-transfer/console-transfer.component';
import { ConsoleService } from '../console.service';

@Component({
  selector: 'app-console-editpopup',
  templateUrl: './console-editpopup.component.html',
  styleUrl: './console-editpopup.component.scss'
})
export class ConsoleEditpopupComponent {

  status: any[] = [];
  isExempted: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ConsoleTransferComponent>,
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
    this.isExempted = [
      { value: 'Yes', label: 'Yes' },
      { value: 'No', label: 'No' }
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
    customsCcrNo: [],
    customsCurrency: [],
    customsKd: [],
    customsValue: [],
    declaredValue: [],
    deletionIndicator: [],
    description: [],
    eventCode: [],
    eventText: [],
    pieceId: [],
    eventTimestamp: [],
    expectedDuty: [],
    finalDestination: [],
    freightCharges: [],
    freightCurrency: [],
    goodsDescription: [],
    goodsType: [],
    grossWeight: [],
    houseAirwayBill: [],
    hsCode: [],
    iata: [],
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
    noOfPieces: [],
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
    selectedConsole:[],
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
    consoleGroupName: [],
    consoleName: [],
    primaryDo: [],
    secondaryDo: [],
    
    isExempted: [],
    exemptionFor: [],
    exemptionBeneficiary: [],
    exemptionReference: [],
   
  });

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  countryIdList: any[] =[];
  mawbList: any[] = [];
  hawbList: any[] = [];
  iataList: any[] = [];
  originCodeList: any[] =[];
  currencyIdList: any[] = [];
  consignorIdList: any[] = [];


  dropdownlist(){
    this.spin.show();
    this.cas.getalldropdownlist([
      
      this.cas.dropdownlist.setup.currency.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.iata.url,


    ]).subscribe({next: (results: any) => {
     
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
    if(this.data.pageflow == "Edit"){
      this.form.patchValue(this.data.code)
      this.form.controls.masterAirwayBill.disable();
      this.form.controls.partnerHouseAirwayBill.disable();
      this.form.controls.houseAirwayBill.disable();
      this.form.controls.partnerMasterAirwayBill.disable();
    }

  }
selecetedTrasnfer:any[]=[];
save() {
  this.spin.hide();
  this.service.updateSingle([this.form.getRawValue()]).subscribe({
    next: (res) => {
      this.messageService.add({
        severity: 'success',
        summary: 'Updated',
        key: 'br',
        detail: res[0].consoleId + ' has been updated successfully',
      });
      this.dialogRef.close(this.form.getRawValue());
    },
    error: (err) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    },
  });
 } 
  isExemptedChanged(value:any){
  console.log(value)
  }
}


