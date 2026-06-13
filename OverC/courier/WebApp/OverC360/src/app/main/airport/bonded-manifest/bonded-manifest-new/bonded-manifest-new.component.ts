import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { BondedManifestService } from '../bonded-manifest.service';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';


@Component({
  selector: 'app-bonded-manifest-new',
  templateUrl: './bonded-manifest-new.component.html',
  styleUrl: './bonded-manifest-new.component.scss'
})
export class BondedManifestNewComponent {

  active: number | undefined = 0;

  status: any[] = [];

  flag: any[] = [];

  billOfLadingFor: any[] = [];

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: BondedManifestService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
    this.flag = [
      { value: '0', label: 'False'},
      { value: '1', label: 'True'}
    ];
    this.billOfLadingFor = [
      { value: 'Import', label: 'Import' },
      { value: 'Export', label: 'Export' },
      { value: 'Free Zone', label: 'Free Zone' },
      { value: 'Transit', label: 'Transit' },
    ];
  }

  pageToken: any;
  // form builder initialize
  form = this.fb.group({
    bondedId: [],
    // billOfLandingNumber: [,Validators.required],
    billOfLandingDate: [],
    description: [],
    netWeigth: [],
    manifestedGrossWeight: [],
    grossWeight: [],
    tareWeight: [],
    manifestedQuantity: [],
    landedQuantity: [],
    totalQuantity: [],
    volume: [],
    airportOriginCode: [],
    portOfShipping: [],
    finalDestination: [],
    consigneeCivilId: [,],
    notifyParty: [],
    consigneeName: [],
    shipper: [, ],
    remark: [],
    isConsolidatedShipment: [],
    isSplitBillOfLanding: [],
    consolidatedBillNo: [],
    isPendingShipment: [],  
    bwhInvestor: [],
    createdOn: ['',],
    createdBy: [],
    updatedOn: ['',],
    updatedBy: [],

    actualCurrency: [],
    billOfLoadingFor: [],
    chasisNo: [],
    companyId: [this.auth.companyId],
    consignmentCurrency: [],
    consignmentValue: [],
    containerNo: [],
    containerSize: [],
    containerType: [],
    countryOfOrigin: [],
    currency: [],
    declaredValue: [],
    engineNo: [],
    enginePower: [],
    eventCode: [],
    eventText: [],
    eventTimestamp: [],
    fclLcl: [],
    freightCharges: [],
    freightCurrency: [],
    goodsDescription: [],
    goodsType: [],
    houseAirwayBill: [],
    hsCode: [],
    incoTerms: [],
    invoiceDate: [],
    invoiceNumber: [],
    invoiceSupplierName: [],
    invoiceType: [],
    kind: [],
    languageId: [this.auth.languageId],
    load: [],
    markId: [],
    markType: [],
    masterAirwayBill: [],
    billOfLadingFor: ['Import',],
    netWeight: [],
    noOfPackagesMawb: [],
    noOfPiecesHawb:[],
    numberOfCylinders: [],
    partnerHouseAirwayBill: [, Validators.required],
    partnerId: [],
    partnerMasterAirwayBill: [, Validators.required],
    partnerName:[],
    partnerType: [],
    passenger: [],
    paymentType: [],
    productId: [],
    productName: [],
    quantity: [],
    remarks: [],
    sealNo: [],
    pieceId: [],
    pieceItemId: [],
    serviceTypeId: [],
    serviceTypeName: [],
    shipperId: [],
    shipperName: [],
    statusTimestamp: [],
    subProductId: [],
    subProductName: [],
    totalDuty: [],
    vehicleBodyColor: [],
    vehicleBrand: [],
    vehicleModel: [],
    vehicleNationality: [],
    vehicleType: [],
    yearOfManufacture: [],
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

  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Mid-Mile', 'BondedManifest', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.form.controls.shipper.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
      this.form.controls.bondedId.disable();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  countryIdList: any[] = [];
  consigneeCivilIdList: any[] = [];
  consignorIdList: any[] = [];
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.customer.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.countryIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.country.key); 
        const consitnor = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.consignor.key);
        const customer = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.customer.key);
        customer.forEach(x => this.consignorIdList.push(x));
        consitnor.forEach(x => this.consignorIdList.push(x));
        this.consignorIdList = this.cs.removeDuplicatesFromArrayList(this.consignorIdList, 'value')
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
            detail:  res[0].bondedId + ' has been updated successfully',
          });
          this.router.navigate(['/main/airport/bondedManifest']);
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
              detail: 'Record has been created successfully',
            });
            this.router.navigate(['/main/airport/bondedManifest']);
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
}
