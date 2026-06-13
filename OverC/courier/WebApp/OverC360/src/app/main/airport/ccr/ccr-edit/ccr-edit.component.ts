import { Component } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { ConsoleTransferComponent } from '../../console/console-transfer/console-transfer.component';
import { CcrService } from '../ccr.service';
import { CcrEditpopupComponent } from '../ccr-editpopup/ccr-editpopup.component';

@Component({
  selector: 'app-ccr-edit',
  templateUrl: './ccr-edit.component.html',
  styleUrl: './ccr-edit.component.scss'
})
export class CcrEditComponent {
  active: number | undefined = 0;
  status: any[] = [];
  selectedCcr: any[] = [];
  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: CcrService,
    private numberRangeService: NumberrangeService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    public dialog: MatDialog,
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  pageToken: any;
  numCondition: any;

  // form builder initialize
  form = this.fb.group({
    actualCurrency: [],
    airportOriginCode: [],
    bondedId: [],
    ccrId: [],
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
    customsKd: [],
    declaredValue: [],
    deletionIndicator: [],
    description: [],
    eventCode: [],
    eventText: [],
    eventTimestamp: [],
    exemptionBeneficiary: [],
    exemptionFor: [],
    exemptionReference: [],
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
    iataKd: [],
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
    statusText: [],
    statusTimestamp: [],
    subProductId: [],
    subProductName: [],
    tareWeight: [],
    totalDuty: [],
    totalQuantity: [],
    updatedBy: [],
    updatedOn: [],
    volume: [],
    iata: [],
    dduCharge: [],
    customsValue: [],
    specialApprovalCharge: [],
    exchangeRate: [],
    consignmentValueLocal: [],
    duty: ['5%',],
    addInsurance: [],
    customsInsurance: [],
    addIATA: [],
    actualDuty: [],
    calculatedTotalDuty: [],
  });

  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = 'required') {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  onChange() {
    const choosen = this.selectedCcr[this.selectedCcr.length - 1];
    this.selectedCcr.length = 0;
    this.selectedCcr.push(choosen);
  }
  nextNumber: any;
  cols: any[] = [];
  target: any[] = [];

  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Mid-Mile', 'CCR', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();
    this.callTableHeader()
    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();


    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.ccrId.disable();
      this.form.controls.statusId.disable();
      this.form.controls.statusText.disable();
    }

  }


  callTableHeader() {
    this.cols = [
      { field: 'masterAirwayBill', header: 'MAWB', showFooter: false },
      { field: 'houseAirwayBill', header: 'Consignment No', showFooter: false },
      { field: 'partnerMasterAirwayBill', header: 'Partner MAWB', showFooter: false },
      { field: 'partnerHouseAirwayBill', header: 'Partner HAWB', showFooter: false },
      { field: 'customsCcrNo', header: 'Customs CCR No', showFooter: false },
      { field: 'countryOfOrigin', header: 'Origin', showFooter: false },
      { field: 'airportOriginCode', header: 'Airport Origin Code', showFooter: false },
      { field: 'hsCode', header: 'HS Code', showFooter: false },
      { field: 'goodsDescription', header: 'Commodity', showFooter: false },
      { field: 'invoiceNumber', header: 'Invoice No', showFooter: false },
      { field: 'invoiceType', header: 'Invoice Type', showFooter: false },
      { field: 'invoiceDate', header: 'Invoice Date', showFooter: false },
      { field: 'invoiceSupplierName', header: 'Invoice Supplier Name', showFooter: false },
      { field: 'isExempted', header: 'Is Exempted', showFooter: false },
      { field: 'exemptionFor', header: 'Exempted For', showFooter: false },
      { field: 'exemptionBeneficiary', header: 'Exempted Beneficiary', showFooter: false },
      { field: 'exemptionReference', header: 'Exempted Reference', showFooter: false },
      { field: 'consigneeName', header: 'Consignee Name', showFooter: false },
      { field: 'consignmentValue', header: 'Consignment Value', showFooter: false },
      { field: 'consignmentCurrency', header: 'Consignment Currency', showFooter: false },
      { field: 'exchangeRate', header: 'Exchange Rate', showFooter: false },
      { field: 'iata', header: 'IATA', showFooter: false },
      { field: 'customsInsurance', header: 'Customs Insurance', showFooter: false },
      { field: 'duty', header: 'Duty', showFooter: true },
      { field: 'consignmentValueLocal', header: 'Consignment Value Local', showFooter: false },
      { field: 'addIata', header: 'Add IATA', showFooter: false },
      { field: 'addInsurance', header: 'Add Insurance', showFooter: false },
      { field: 'customsValue', header: 'Custom', showFooter: false },
      { field: 'calculatedTotalDuty', header: 'Calculated Total duty', showFooter: true },
      { field: 'dduCharge', header: 'DDU Charge', showFooter: false },
      { field: 'specialApprovalCharge', header: 'Spl Approval Charge', showFooter: false },
      { field: 'totalDuty', header: 'Duty From Bayan', showFooter: true },
      { field: 'createdOn', header: 'Created On', format: 'date', showFooter: false },
    ];
    this.target = [
    ];
  }


  languageIdList: any[] = [];
  companyIdList: any[] = [];

  dropdownlist() {
    // this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        // this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }

  addValues() { }

  subProductArray: any[] = [];



  removeItem(index: number) {
    this.subProductArray.splice(index, 1);
  }

  fill(line: any) {
    this.spin.show();
    this.form.patchValue(this.pageToken.line);
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.ccrId = [line.ccrId];
    this.service.search(obj).subscribe({
      next: (res: any) => {
        this.subProductArray = res;
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
    // this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    // this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
  }
  lineSentforFill: any;
  save() {
    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }


    if (this.pageToken.pageflow != 'New') {
      if (this.selectedCcr.length == 0) {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          key: 'br',
          detail: 'Kindly select any row',
        });
        return;
      }
      this.spin.show();
      const a = this.subProductArray.filter(x => x.eventCode == 10);

      this.service.UpdateList(this.selectedCcr).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].consoleId + ' has been updated successfully',
          });
          if (this.subProductArray.length == a.length) {
            this.router.navigate(['/main/airport/ccr']);
          } else {
            setTimeout(() => {
              this.fill(this.pageToken.line);
            }, 2000);
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  editItem(data: any, item: any): void {
    const dialogRef = this.dialog.open(CcrEditpopupComponent, {
      disableClose: true,
      width: '70%',
      //height: '50%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: { pageflow: data, code: item },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.subProductArray.splice(item, 0);
        this.subProductArray.splice(item, 1, result);
        this.subProductArray = [...this.subProductArray]

      }
    });
  }
  transfer() {
    if (this.selectedCcr.length === 0) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', key: 'br', detail: 'Kindly select any row' });
    }

    const dialogRef = this.dialog.open(ConsoleTransferComponent, {
      disableClose: true,
      width: '70%',
      height: '40%',
      maxWidth: '82%',
      position: { top: '6.5%', left: '30%' },
      data: this.selectedCcr,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.subProductArray.push(result);
      }
    });
  }

  calculateFooterTotal(field: string): number {
    let total = 0;
    this.subProductArray.forEach(item => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(3));
  }
  
}

