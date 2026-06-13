import { Component } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { CountryService } from '../../../id-masters/country/country.service';
import { DistrictService } from '../../../id-masters/district/district.service';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { ProvinceService } from '../../../id-masters/province/province.service';
import { PickupService } from '../../pickup/pickup.service';

@Component({
  selector: 'app-delivery-new',
  templateUrl: './delivery-new.component.html',
  styleUrl: './delivery-new.component.scss'
})
export class DeliveryNewComponent {

  active: number | undefined = 0;
  status: any[] = [];
  pickupType: any[] = [];
  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: PickupService,
    private messageService: MessageService,
    private numberRangeService: NumberrangeService,
    private provinceService: ProvinceService,
    private countryService: CountryService,
    private districtService: DistrictService,
    private auth: AuthService,
    private cas: CommonAPIService) {
    
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
    this.pickupType = [
      { value: 'Reverse', label: 'Reverse' },
      { value: 'Forward', label: 'Forward' }
    ];
  }
  PickupDetails = this.fb.group({
    pickupHubCode: [],
    pickupDetailId: [],
    companyName: [],
    emailId: [],
    name: [],
    phone: [],
    alternatePhone: [],
    addressLine1: [],
    addressLine2: [],
    pinCode: [],
    district: [],
    city: [],
    country:[],
    state: [],
    latitude: [],
    longitude: [],
    pickupAddress: [],
  });

  DestinationDetails = this.fb.group({
    imageReferenceList: [],
    destinationDetailId:[],
    reverseReason: [],
    destinationAddress: [],
    emailId: [],
    name: [],
    companyName: [],
    phone: [],
    alternatePhone: [],
    addressLine1: [],
    addressLine2: [],
    pinCode: [],
    district: [],
    city: [],
    country:[],
    state: [],
    latitude: [],
    longitude: [],
    pickupAddress: [],
  });

  numCondition: any;
  pageToken: any;

  //form builder initialize
  form = this.fb.group({
    partnerId: [],  
    partnerName: [],
    consignmentBagId: [],
    pickupType: ["Forward",],
    paymentType: [],
    pieceId: [],
    pieceCount: [],
    actualSequenceNo: [],
    assignedHubCode:[],
    codFavorOf: [],
    consignmentType: [],
    courierId: [],
    courierType: [],
    customerCode: [],
    customerReferenceNumber: [],
    invoiceAmount: [],
    isCustomsDeclarable: [],
    loadType: [],
    movementType: [],
    packageType: [],
    partnerType: [],
    paymentLink: [],
    pickupAttemptCount: [],
    invoiceUrl: [],
    totalShipmentWeight: [],
    serviceTypeId: [],
    houseAirwayBill: [],
    codAmount: [],
    codCollectionMode: [],
    priority: [],
    customerPickupDate: [],
    pickupTimeSlotStart: [],
    pickupTimeSlotEnd: [],
    pickUpId: [, Validators.required],
    languageId: [this.auth.languageId, Validators.required],
    languageDescription: [],
    companyId: [this.auth.companyId, Validators.required],
    companyName: [],
    pickupDetails: this.PickupDetails,
    destinationDetails: this.DestinationDetails,
    pickupEntityId: [],
    pickupFailedReason: [],
    pickupOtp: [],
    pickupServiceTime: [],
    productCode: [],
    productId: [],
    productName: [],
    reverseReason: [],
    routeId: [],
    rtoOtp: [],
    sequenceNo: [],
    serviceTypeText: [],
    statusTimeStamp: [],
    vehicleRegNumber: [],
    statusDescription: [],
    description: [],
    remark: [],
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
    referenceField18: [],
    referenceField19: [],
    referenceField20: [],
    createdOn: ['',],
    createdBy: [],
    updatedBy: [],
    updatedOn: ['',],
    statusId: ["16",],
  });

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

  nextNumber: any;
  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['LastMile', 'Delivery', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.pickUpId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
    else {
      this.spin.show();
      let obj: any = {};
      obj.numberRangeObject = ['PICKUPID'];
      this.numberRangeService.search(obj).subscribe({
        next: (res: any) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.pickUpId.patchValue(this.nextNumber);
            this.numCondition = 'true';
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.pickUpId.disable();
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
      this.spin.show();
      let obj1: any = {};
      obj1.numberRangeObject = ['PIECEID'];
      this.numberRangeService.search(obj1).subscribe({
        next: (res: any) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.pieceId.patchValue(this.nextNumber);
            this.numCondition = 'true';
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.pieceId.disable();
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  partnerIdList: any[] = [];
  pieceIdList: any[] = [];
  paymentTypeList: any[] = [];
  serviceTypeIdList: any[] = [];
  houseAirwayBillList: any[] = [];
  codCollectionModeList: any[] = [];
  priorityList: any[] = [];
  courierTypeList: any[] = [];
  courierIdList: any[] = [];
  pickupTypeList: any[] = [];
  districtIdList: any[] = [];
  cityIdList: any[] = [];
  countryIdList: any[] = [];
  provinceIdList: any[] = [];
  pickupHubCodeList: any[] = [];
  emailIdList: any[] = [];

  

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.serviceType.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.district.url,
      this.cas.dropdownlist.setup.province.url,
      this.cas.dropdownlist.setup.city.url,




    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.serviceTypeIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.serviceType.key);
        this.countryIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.country.key);
        this.districtIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.district.key);
        this.provinceIdList = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.province.key);
        this.cityIdList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.city.key);

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
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Please fill required fields to continue' });
      return;
    }

    if (this.pageToken.pageflow != 'New') {
      this.spin.show()
      this.service.Update([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res.pickUpId + ' has been updated successfully' });
          this.router.navigate(['/main/lastMile/pickup']);
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    } else {
      this.spin.show()
      this.service.Create([this.form.getRawValue()]).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res.pickUpId + ' has been created successfully' });
            this.router.navigate(['/main/lastMile/pickup']);
            this.spin.hide();
          }
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
     }
     countryChanged() {

      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      obj.country = [this.form.get('pickupDetails')?.get('country')?.value]
  
      this.provinceIdList = [];
      this.spin.show();
      this.provinceService.search(obj).subscribe({
        next: (result) => {
          this.provinceIdList = this.cas.foreachlist(result, { key: 'provinceId', value: 'provinceName' });
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
    provinceChanged() {
  
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      obj.country = [this.form.get('pickupDetails')?.get('country')?.value]
      obj.country = [this.form.get('pickupDetails')?.get('state')?.value]
  
  
      this.districtIdList = [];
      this.spin.show();
      this.districtService.search(obj).subscribe({
        next: (result) => {
          this.districtIdList = this.cas.foreachlist(result, { key: 'districtId', value: 'districtName' });
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
    districtChanged() {
  
      let obj: any = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      obj.country = [this.form.get('pickupDetails')?.get('country')?.value]
      obj.country = [this.form.get('pickupDetails')?.get('state')?.value]
      obj.country = [this.form.get('pickupDetails')?.get('district')?.value]

      this.districtIdList = [];
      this.spin.show();
      this.districtService.search(obj).subscribe({
        next: (result) => {
          this.districtIdList = this.cas.foreachlist(result, { key: 'cityId', value: 'cityName' });
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }

}
