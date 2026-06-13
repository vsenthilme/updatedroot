import { Component } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { CourierPartnerService } from '../courier-partner.service';
import { ProvinceService } from '../../../id-masters/province/province.service';
import { DistrictService } from '../../../id-masters/district/district.service';

@Component({
  selector: 'app-courier-partner-new',
  templateUrl: './courier-partner-new.component.html',
  styleUrl: './courier-partner-new.component.scss'
})
export class CourierPartnerNewComponent {

  active: number | undefined = 0;
  status: any[] = []

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private provinceService: ProvinceService,
    private districtService: DistrictService,
    private service: CourierPartnerService,
    private numberRangeService: NumberrangeService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService
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
    languageId: [this.auth.languageId],
    languageDescription: [],
    companyId: [this.auth.companyId],
    companyName: [],
    courierPartnerId: [, Validators.required],
    courierPartnerText: [],
    cityId: [],
    provinceId: [],
    districtId: [],
    partnerId: [],
    partnerType: [],
    routeId: [],
    assignedHubCode: [],
    statusId: ["16",],
    statusDescription: [],
    remark: [],
    referenceField1: [],
    referenceField10: [],
    referenceField2: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    createdOn: ['',],
    createdBy: [],
    updatedOn: ['',],
    updatedBy: [],
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

  nextNumber: any;
  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Master', 'Courier Partner', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.courierPartnerId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  cityIdList: any[] = [];
  provinceIdList: any[] = [];
  districtIdList: any[] = [];
  routeIdList: any[] = [];
  hubCodeList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.city.url,
      this.cas.dropdownlist.setup.province.url,
      this.cas.dropdownlist.setup.district.url,
      this.cas.dropdownlist.setup.route.url,
      this.cas.dropdownlist.setup.hub.url,
    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.cityIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.city.key);
        this.provinceIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.province.key);
        this.districtIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.district.key);
        this.routeIdList = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.route.key);
        this.hubCodeList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.hub.key);
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
      this.service.Update(this.form.getRawValue()).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res.courierPartnerId + ' has been updated successfully',
          });
          this.router.navigate(['/main/master/courierPartner']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.service.Create(this.form.getRawValue()).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res.courierPartnerId + ' has been created successfully',
            });
            this.router.navigate(['/main/master/courierPartner']);
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
  cityChanged() {

    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.cityId = [this.form.controls.cityId.value]

    this.districtIdList = [];
    this.spin.show();
    this.districtService.search(obj).subscribe({
      next: (result : any) => {
        this.districtIdList = this.cas.foreachlist(result, { key: 'districtId', value: 'districtName' });
        this.spin.hide();
      }, error: (err : any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }
  districtChanged() {

    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.cityId = [this.form.controls.cityId.value];
    obj.districtId = [this.form.controls.districtId.value];


    this.provinceIdList = [];
    this.spin.show();
    this.provinceService.search(obj).subscribe({
      next: (result : any) => {
        this.provinceIdList = this.cas.foreachlist(result, { key: 'provinceId', value: 'provinceName' });
        this.spin.hide();
      }, error: (err : any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }
}
