import { Component } from '@angular/core';
import { CityService } from '../city.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { ProvinceService } from '../../province/province.service';
import { DistrictService } from '../../district/district.service';
import { NumberrangeService } from '../../numberrange/numberrange.service';

@Component({
  selector: 'app-city-new',
  templateUrl: './city-new.component.html',
  styleUrl: './city-new.component.scss'
})
export class CityNewComponent {


  active: number | undefined = 0;
  status: any[] = []
  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: CityService,
    private numberRangeService: NumberrangeService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
    private provinceService: ProvinceService,
    private districtService: DistrictService) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  numCondition: any;
  pageToken: any;

  //form builder initialize
  form = this.fb.group({
    cityId: [, Validators.required],
    cityName: [, Validators.required],
    districtId: [, Validators.required],
    districtName: [],
    languageId: [this.auth.languageId, Validators.required],
    languageDescription: [],
    companyId: [this.auth.companyId, Validators.required],
    companyName: [],
    countryId: [, Validators.required],
    countryName: [],
    provinceId: [, Validators.required],
    provinceName: [],
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

    const dataToSend = ['Setup', 'City', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.cityId.disable();
      this.form.controls.countryId.disable();
      this.form.controls.provinceId.disable();
      this.form.controls.districtId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
    else {
      this.spin.show();
      let obj: any = {};
      obj.numberRangeObject = ['CITY'];
      this.numberRangeService.search(obj).subscribe({
        next: (res: any) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.cityId.patchValue(this.nextNumber);
            this.numCondition = 'true';
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.cityId.disable();
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
  countryIdList: any[] = [];
  provinceIdList: any[] = [];
  districtIdList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.province.url,
      this.cas.dropdownlist.setup.district.url,

    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.countryIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.country.key);
        this.provinceIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.province.key);
        this.districtIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.district.key);
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
      this.service.Update(this.form.getRawValue()).subscribe({
        next: (res: any) => {
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res.cityId + ' has been updated successfully' });
          this.router.navigate(['/main/idMaster/city']);
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
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res.cityId + ' has been created successfully' });
            this.router.navigate(['/main/idMaster/city']);
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
    obj.countryId = [this.form.controls.countryId.value]

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
    obj.countryId = [this.form.controls.countryId.value]
    obj.provinceId = [this.form.controls.provinceId.value]


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
}

