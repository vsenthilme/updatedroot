import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { UsersService } from '../users.service';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';


@Component({
  selector: 'app-users-new',
  templateUrl: './users-new.component.html',
  styleUrl: './users-new.component.scss'
})
export class UsersNewComponent {

  active: number | undefined = 0;
  userType: any[] = []

  status: any[] = [];

  flag: any[] = [];

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: UsersService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService
  ) {
    this.userType = [
      { value: 1, label: 'Portal' },
      { value: 2, label: 'Customer' },
      { value: 3, label: 'App' }
    ];
    this.status = [
      { value: 17, label: 'Inactive' },
      { value: 16, label: 'Active' }
    ];
    this.flag = [
      { value: '0', label: 'False'},
      { value: '1', label: 'True'}
    ]
  }

  pageToken: any;
  // Form builder Initialize
  form = this.fb.group({
    languageId: [this.auth.languageId, Validators.required],
    languageIDAndDescription: [],
    companyId: [this.auth.companyId, Validators.required],
    companyIdAndDescription: [],
    userId: [],
    userRoleId: [],
    userRoleIdAndDescription: [],
    userTypeIdAndDescription: [],
    userTypeId: [],
    password: [],
    userName: [],
    firstName: [],
    lastName: [],
    statusId: ["16",],
    dateFormatId: [],
    currencyDecimal: [0,],
    createHhtUser: [false, ],
    timeZone: [],
    portalLoggedIn: [false, ],
    hhtLoggedIn: [false, ],
    resetPassword: [false, ],
    emailId: [],
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
    createdOn: [],
    createdBy: [],
    updatedBy: [],
    updatedOn: [],
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

  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    const dataToSend = ['Setup', 'Users', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line)
      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.form.controls.userId.disable();
      this.form.controls.userRoleId.disable();
      this.form.controls.userTypeId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup,

    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
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
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res.userId + ' has been updated successfully' });
          this.router.navigate(['/main/idMaster/users']);
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
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res.userId + ' has been created successfully' });
            this.router.navigate(['/main/idMaster/users']);
            this.spin.hide();
          }
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }
}
