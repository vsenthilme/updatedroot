import { Component, ElementRef, Inject } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { PreAlertUpdateComponent } from '../pre-alert-update/pre-alert-update.component';
import { ConsignmentService } from '../../../operation/consignment/consignment.service';
import { CustomerService } from '../../../master/customer/customer.service';
import { ConsignorService } from '../../../master/consignor/consignor.service';

@Component({
  selector: 'app-pre-alert-editpopup',
  templateUrl: './pre-alert-editpopup.component.html',
  styleUrl: './pre-alert-editpopup.component.scss'
})
export class PreAlertEditpopupComponent {

  status: any[] = [];

  partnerType: any[] = []

  constructor(
    public dialogRef: MatDialogRef<PreAlertUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsignmentService,
    private customerService: CustomerService,
    private consignorService: ConsignorService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private el: ElementRef
  ) {
    this.partnerType = [
      { value: 'customer', label: 'Customer' },
      { value: 'consignor', label: 'Consignor' },
    ];
   }

  OriginDetails = this.fb.group({
    name: [],
    companyName: [],
    country: [],
  });

  DestinationDetails = this.fb.group({
    name: [],
    companyName: [],
    country: [],
  });

  // form builder initialize
  form = this.fb.group({
    companyId: [this.auth.companyId, Validators.required],
    languageId: [this.auth.languageId, Validators.required],
    partnerHouseAirwayBill: [],
    partnerMasterAirwayBill: [],
    originDetails: this.OriginDetails,
    destinationDetails: this.DestinationDetails,
    goodsDescription: [],
    consigneeName: [],
    incoTerm: [],
    shipper: [],
    description: [],
    weight: [,],
    consignmentValue: [],
    consignmentValueLocal: [],
    masterAirwayBill: [],
    houseAirwayBill: [],
    consignmentCurrency: [],
    currency: [],
    airportDestinationCode: [],
    hsCode: [],
    noOfPieces:[],
    iata: [],
    flightNo: [],
    flightName: [],
    bayanHv: [],
    partnerType: ['',],
    countryOfOrigin: [],
    originCode: [],
    origin: [],
    countryOfDestination: [],
    estimatedTimeOfArrival: ['',],
    estimatedTimeOfArrivalFE:  [new Date,],
    estimatedDepartureTime: ['',],
    estimatedDepartureTimeFE: [new Date,],
    noOfPackageMawb: [],
    noOfCrt: [],
    totalWeight: [],
    totalShipmentWeight: [],
    totalValue: [],
    createdOn: ['',],
    createdBy: [],
    updatedBy: [],
    updatedOn: ['',],
    partnerId: ['', Validators.required]
  })

  pageToken: any;

  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = "required") {
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
    
    this.dropdownlist();

    this.form.controls.companyId.disable();

    if (this.data.pageflow == 'Edit') {
      this.form.patchValue(this.data.code);

      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.form.controls.partnerMasterAirwayBill.disable();
      this.form.controls.partnerHouseAirwayBill.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }

  companyIdList: any[] = [];
  languageIdList: any[] = [];
  iataIdList: any[] = [];
  countryIdList: any[] = [];
  customerIdList: any[] = [];
  currencyIdList: any[] = [];
  hsCodeList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.currency.url,
      this.cas.dropdownlist.setup.hsCode.url,
      this.cas.dropdownlist.setup.iata.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.customer.url,


    ]).subscribe({
      next: (results: any) => {
        this.companyIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.company.key);
        this.languageIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.language.key);
        this.countryIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.country.key);
        this.currencyIdList = this.cas.foreachlist(results[3], this.cas.dropdownlist.setup.currency.key);
        this.hsCodeList = this.cas.forLanguageFilterWithoutKey(results[4], this.cas.dropdownlist.setup.hsCode.key);
        this.iataIdList = this.cas.forLanguageFilterWithoutKey(results[5], this.cas.dropdownlist.setup.iata.key);
        const consitnor = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.consignor.key);
        const customer = this.cas.forLanguageFilter(results[7], this.cas.dropdownlist.setup.customer.key);
        customer.forEach(x => this.customerIdList.push(x));
        consitnor.forEach(x => this.customerIdList.push(x));
        this.customerIdList = this.cs.removeDuplicatesFromArrayList(this.customerIdList, 'value')

        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }

  shipperType: any;
  partnerTypeChanged() {
    if (this.form.controls.partnerType.value == "customer") {
      let obj: any = {};
      obj.companyId = [this.auth.companyId];

      this.customerIdList = [];
      this.spin.show();
      this.customerService.search(obj).subscribe({
        next: (result) => {
          this.customerIdList = this.cas.foreachlist(result, { key: 'customerId', value: 'customerName' });
          this.shipperType = "Customer";
          this.form.controls.shipper.patchValue(this.shipperType)
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    } else {
      let obj: any = {};
      obj.companyId = [this.auth.companyId];

      this.customerIdList = [];
      this.spin.show();
      this.consignorService.search(obj).subscribe({
        next: (result) => {
          this.customerIdList = this.cas.foreachlist(result, { key: 'consignorId', value: 'consignorName' });
          this.shipperType = "Consignor";
          this.form.controls.shipper.patchValue(this.shipperType)
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })

    }
  }

  save() {
    this.submitted = true;
    if (this.form.invalid) {
      for (const control in this.form.controls) {
        const controlInstance = this.form.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: 'smooth', block: 'center' });
            break;
          }
        }
      }
    }
    if (this.form.invalid) {
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Please fill required fields to continue' });
      return;
    }
    const date = this.cs.jsonDate(this.form.controls.estimatedDepartureTimeFE.value)
    this.form.controls.estimatedDepartureTime.patchValue(date)

    const date1 = this.cs.jsonDate(this.form.controls.estimatedTimeOfArrivalFE.value)
    this.form.controls.estimatedTimeOfArrival.patchValue(date1)

    this.service.UpdatePreAlertManifest([this.form.getRawValue()]).subscribe({
      next: (res: any) => {
        this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res[0].partnerHouseAirwayBill + ' has been updated successfully' });
        this.dialogRef.close();
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }


}
