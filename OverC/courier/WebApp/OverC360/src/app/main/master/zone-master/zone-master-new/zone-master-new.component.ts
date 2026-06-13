import { Component } from '@angular/core';
import { ZoneMasterService } from '../zone-master.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { NumberrangeService } from '../../../id-masters/numberrange/numberrange.service';
import { ZoneTypeMasterService } from '../../zone-type-master/zone-type-master.service';

@Component({
  selector: 'app-zone-master-new',
  templateUrl: './zone-master-new.component.html',
  styleUrl: './zone-master-new.component.scss'
})
export class ZoneMasterNewComponent {

  active: number | undefined = 0;
  status: any[] = [];

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ZoneMasterService,
    private zoneTypeservice: ZoneTypeMasterService,
    private messageService: MessageService,
    private numberRangeService: NumberrangeService,
    private auth: AuthService,
    private cas: CommonAPIService) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  numCondition: any;
  pageToken: any;

  //form builder initialize
  form = this.fb.group({
    zoneText: [],
    zoneId: [, Validators.required],
    languageId: [this.auth.languageId, Validators.required],
    languageDescription: [],
    companyId: [this.auth.companyId, Validators.required],
    companyName: [],
    zoneType: [, Validators.required],
    zoneTypeText: [],
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

    const dataToSend = ['Master', 'Zone Master', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.zoneId.disable();
      this.form.controls.zoneType.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }

  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  zoneTypeList: any[] = [];
  zoneTypeTextList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,      
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.zoneTypeMaster.url,


    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.zoneTypeList = this.cas.forLanguageFilterWithoutKey(results[2], this.cas.dropdownlist.setup.zoneTypeMaster.key);

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
        next: (res) => {
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res.zoneId + ' has been updated successfully' });
          this.router.navigate(['/main/master/zoneMaster']);
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
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res.zoneId + ' has been created successfully' });
            this.router.navigate(['/main/master/zoneMaster']);
            this.spin.hide();
          }
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }
  zoneTypeChanged() {
    let obj: any = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.zoneType = [this.form.controls.zoneType.value]

    this.zoneTypeTextList = [];
    this.spin.show();
    this.zoneTypeservice.search(obj).subscribe({
      next: (result) => {
        this.form.controls.zoneTypeText.patchValue(result[0].zoneTypeText)
        this.spin.hide();
      }, error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    })
  }
}


