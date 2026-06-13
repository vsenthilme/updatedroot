import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { CcrService } from '../ccr.service';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';
import { ConsignmentService } from '../../../operation/consignment/consignment.service';


@Component({
  selector: 'app-ccr-new',
  templateUrl: './ccr-new.component.html',
  styleUrl: './ccr-new.component.scss'
})
export class CcrNewComponent {

  active: number | undefined = 0;

  status: any[] = [];

  flag: any[] = [];

  value: any[] = [];

  constructor(
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
    private consignmentService: ConsignmentService,
  ) {
    this.status = [
      { value: '2', label: 'Inactive' },
      { value: '1', label: 'Active' }
    ];
    this.flag = [
      { value: '0', label: 'False'},
      { value: '1', label: 'True'}
    ];
    this.value = [
      { value: 'Yes', label: 'Yes' },
      { value: 'No', label: 'No' },
    ]
  }

  pageToken: any;
  // form builder initialize
  form = this.fb.group({

    masterAirwayBill: [, Validators.required],
    houseAirwayBill: [, Validators.required],
    consigneeName: [, Validators.required],
    consigneeCivilId: [],
    invoiceNumber: [],
    invoiceDate: [],
    invoiceType: [],
    currency: [],
    invoiceSupplierName: [],
    freightCurrency: [],
    freightCharges: [],
    countryOfSupply: [],
    billNumber: [],
    hsCode: [, Validators.required],
    goodsDescription: [],
    countryOfOrigin: [],
    manufacturer: [],
    noOfPackages: [],
    itemTotalPrice: [],
    packageType: [],
    quantity: [],
    netWeight: [],
    grossWeight: [],
    isExempted: [],
    exemptionFor: [],
    exemptionBeneficiary: [],
    exemptionReference: [],
    partnerHouseAirwayBil: [],
    partnerMasterAirwayBill: [],
    createdOn: ['',],
    createdBy: [],
    updatedOn: ['',],
    updatedBy: [],
    pieceId: [],
    pieceItemId: [],
    airportOriginCode: [],
    actualCurrency: [],
    companyId: [this.auth.companyId],
    consignmentCurrency: [],
    consignmentValue: [],
    consoleId: [],
    customsCcrNo: [],
    customsKd: [],
    declaredValue: [],
    description: [],
    eventCode: [],
    eventText: [],
    eventTimestamp: [],
    finalDestination: [],
    flightArrivalTime: [],
    flightNo: [],
    iataKd: [],
    incoTerms: [],
    isConsolidatedShipment: [],
    isPendingShipment: [],
    isSplitBillOfLading: [],
    landedQuantity: [],
    languageId: [this.auth.languageId],
    manifestedGrossWeight: [],
    manifestedQuantity: [],
    noOfPackageMawb: [],
    noOfPieceHawb: [],
    notifyParty: [],
    partnerHouseAirwayBill: [],
    partnerId: [],
    partnerName: [],
    partnerType: [],
    paymentType: [],
    productId: [],
    productName: [],
    remarks: [],
    serviceTypeId: [],
    serviceTypeName: [],
    shipperId: [],
    shipperName: [],
    specialApprovalValue: [],
    statusTimestamp: [],
    subProductId: [],
    subProductName: [],
    tareWeight: [],
    totalDuty: [],
    totalQuantity: [],
    volume: [],
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

  ngOnInit(): void {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Mid-Mile', 'CCR', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.mawbDropdown();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.form.controls.masterAirwayBill.disable();
      this.form.controls.houseAirwayBill.disable();
      this.form.controls.hsCode.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  countryIdList: any[] = [];
  mawbList: any[] = [];
  hawbList: any[] = [];
  hsCodeList: any[] = [];
  currencyIdList: any[] = [];
  consignorIdList: any[] = [];
  
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.currency.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.hsCode.url,
      this.cas.dropdownlist.setup.hsCode.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.currencyIdList = this.cas.foreachlist(results[2], this.cas.dropdownlist.setup.currency.key);
        this.countryIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.country.key);
        this.consignorIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.consignor.key);

        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });
  }

  fill(line: any) {
    this.form.patchValue(line);
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
  }

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
      this.spin.show();
      this.service.Update([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].ccrId + ' has been updated successfully',
          });
          this.router.navigate(['/main/airport/ccr']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.service.Create([this.form.getRawValue()]).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res.partnerId + ' has been created successfully',
            });
            this.router.navigate(['/main/airport/ccr']);
            this.spin.hide();
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  mawbDropdown(){

    let obj: any = {};
    obj.companyId = [this.auth.companyId];

    this.mawbList = [];
    this.spin.show();
    this.consignmentService.search(obj).subscribe({next: (result) => {
    this.mawbList = this.cas.foreachlist(result, {key: 'masterAirwayBill', value: 'masterAirwayBill'});
    this.spin.hide();
    }, error: (err) =>{
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }

  mawbChanged(){
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.masterAirwayBill = [this.form.controls.masterAirwayBill.value]

    this.hawbList = [];
    this.spin.show();
    this.consignmentService.search(obj).subscribe({next: (result) => {
      this.hawbList = this.cas.foreachlist(result, {key: 'houseAirwayBill', value: 'houseAirwayBill'});
      this.spin.hide();
    }, error: (err) =>{
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }

  hawbChanged(){
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.masterAirwayBill = [this.form.controls.masterAirwayBill.value]
    obj.houseAirwayBill = [this.form.controls.houseAirwayBill.value]
    this.spin.show();
    this.consignmentService.search(obj).subscribe({next: (result) => {
      this.form.patchValue(result[0]);
      this.spin.hide();
    }, error: (err) =>{
      this.spin.hide();
      this.cs.commonerrorNew(err);
    }})
  }
}
