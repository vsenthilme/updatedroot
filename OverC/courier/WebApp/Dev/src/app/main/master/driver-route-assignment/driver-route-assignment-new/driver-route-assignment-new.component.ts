import { Component } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { DistrictService } from '../../../id-masters/district/district.service';
import { ProvinceService } from '../../../id-masters/province/province.service';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { DriverRouteAssignmentService } from '../driver-route-assignment.service';
import { AppUserService } from '../../../id-masters/app-user/app-user.service';
import { CourierPartnerService } from '../../courier-partner/courier-partner.service';

@Component({
  selector: 'app-driver-route-assignment-new',
  templateUrl: './driver-route-assignment-new.component.html',
  styleUrl: './driver-route-assignment-new.component.scss'
})
export class DriverRouteAssignmentNewComponent {


  active: number | undefined = 0;
  status: any[] = []

  courierType: any[] = []
  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: DriverRouteAssignmentService,
    private numberRangeService: NumberrangeService,
    private appUserService: AppUserService,
    private serviceProviderService: CourierPartnerService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private provinceService: ProvinceService,
    private districtService: DistrictService) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
    this.courierType = [
      { value: 'IW Express', label: 'IW Express' },
      { value: 'Partner', label: 'Partner' },
    ];
  }

  numCondition: any;
  pageToken: any;

  //form builder initialize
  form = this.fb.group({
    courierId: [, Validators.required],
    courierType: [],
    routeId: [, Validators.required],
    languageId: [this.auth.languageId, Validators.required],
    languageDescription: [],
    companyId: [this.auth.companyId, Validators.required],
    companyName: [],
    vehicleRegNumber: [, Validators.required],
    assignedHubCode: [, Validators.required],
    statusDescription: [],
    createdOn: ['',],
    createdBy: [],
    updatedBy: [],
    updatedOn: ['',],
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
    remark: [],
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

    const dataToSend = ['Master', 'Driver Route Assignment', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.courierId.disable();
      this.form.controls.courierType.disable();
      this.form.controls.routeId.disable();
      this.form.controls.vehicleRegNumber.disable();
      this.form.controls.assignedHubCode.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  courierTypeList: any[] = []; 
  hubCodeList: any[] = [];
  routeIdList: any[] = [];
  vehicleRegNumberList: any[] = []; 
  


  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.hub.url,
      this.cas.dropdownlist.setup.route.url,
      this.cas.dropdownlist.setup.vehicle.url,
     

    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.hubCodeList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.hub.key);
        this.routeIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.route.key);
        this.vehicleRegNumberList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.vehicle.key);

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
    this.courierTypeChanged();
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
      this.service.Update(this.form.getRawValue()).subscribe({
        next: (res: any) => {
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res.courierId + ' has been updated successfully' });
          this.router.navigate(['/main/master/driverRouteAssignment']);
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    } else {
      this.spin.show()
      this.service.Create(this.form.getRawValue()).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res.courierId + ' has been created successfully' });
            this.router.navigate(['/main/master/driverRouteAssignment']);
            this.spin.hide();
          }
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }

  // countryChanged() {

  //   let obj: any = {};
  //   obj.languageId = [this.auth.languageId];
  //   obj.companyId = [this.auth.companyId];
  //   obj.countryId = [this.form.controls.countryId.value]

  //   this.provinceIdList = [];
  //   this.spin.show();
  //   this.provinceService.search(obj).subscribe({
  //     next: (result) => {
  //       this.provinceIdList = this.cas.foreachlist(result, { key: 'provinceId', value: 'provinceName' });
  //       this.spin.hide();
  //     }, error: (err) => {
  //       this.spin.hide();
  //       this.cs.commonerrorNew(err);
  //     }
  //   })
  // }
  // provinceChanged() {

  //   let obj: any = {};
  //   obj.languageId = [this.auth.languageId];
  //   obj.companyId = [this.auth.companyId];
  //   obj.countryId = [this.form.controls.countryId.value]
  //   obj.provinceId = [this.form.controls.provinceId.value]


  //   this.districtIdList = [];
  //   this.spin.show();
  //   this.districtService.search(obj).subscribe({
  //     next: (result) => {
  //       this.districtIdList = this.cas.foreachlist(result, { key: 'districtId', value: 'districtName' });
  //       this.spin.hide();
  //     }, error: (err) => {
  //       this.spin.hide();
  //       this.cs.commonerrorNew(err);
  //     }
  //   })
  // }

  courierTypeChanged() {
    if (this.form.controls.courierType.value == 'IW Express') {
      let obj: any = {};
      obj.companyId = [this.auth.companyId];
      this.courierTypeList = [];
      this.spin.show();
      this.appUserService.search(obj).subscribe({
        next: (result) => {
          this.courierTypeList = this.cas.foreachlistWithoutKey(result, { key: 'appUserType', value: 'appUserType'});
          this.courierTypeList =  this.cs.removeDuplicatesFromArrayList( this.courierTypeList, 'value');
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }

    if (this.form.controls.courierType.value == 'Partner') {
      let obj: any = {};
      obj.companyId = [this.auth.companyId];
      this.courierTypeList = [];
      this.spin.show();
      this.serviceProviderService.search(obj).subscribe({
        next: (result : any) => {
          this.courierTypeList = this.cas.foreachlist(result, { key: 'serviceProvidersId', value: 'serviceProvidersText', value2: 'serviceProvidersText' });
          this.courierTypeList =  this.cs.removeDuplicatesFromArrayList( this.courierTypeList, 'value');
          this.spin.hide();
        }, error: (err : any) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }
}
