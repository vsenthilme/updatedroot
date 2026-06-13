import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserRoleService } from '../user-role.service';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { AuthService } from '../../../../core/core';
import { Subscription } from 'rxjs';
import { ModuleService } from '../../module/module.service';


@Component({
  selector: 'app-user-role-new',
  templateUrl: './user-role-new.component.html',
  styleUrl: './user-role-new.component.scss'
})
export class UserRoleNewComponent {

  userRoleTable: any[] = [];
  selectedUserRole: any[] = [];
  cols: any[] = [];
  target: any[] = [];

  active: number | undefined = 0;
  status: any[] = [];
  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: UserRoleService,
    private moduleService: ModuleService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService
  ) {
    this.status = [
      { value: '17', label: 'Inactive' },
      { value: '16', label: 'Active' }
    ];
  }

  disabled = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

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

  sub = new Subscription();
  modeOfImplementation: any;
  ngOnInit() {
    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);

    let obj: any = {};
    obj.companyId = this.auth.companyId;
    obj.languageId = this.auth.languageId;

    const dataToSend = ['Setup', 'User Role', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken)
      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.form.controls.roleId.disable();
    }

    if (this.pageToken.pageflow == 'New') {
      this.form.controls.languageId.patchValue(this.auth.languageId);
      this.form.controls.companyId.patchValue(this.auth.companyId);

      this.form.controls.languageId.disable();
      this.form.controls.companyId.disable();
      this.getModuleId();
    }
  }

  languageIdList: any[] = [];
  companyIdList: any[] = [];
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,

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

  isbtntext = true;
  btntext = "Save";
  menuList: any[] = [];
  menuListRaw: any[] = [];
  moduleResult: any[] = [];
  menuListraw: any[] =
    [
      {
        mainMenu: "Shortcuts", Menu: [
          { menuId: 1, subMenuId: 1000, referenceField2: 'Setup', referenceField1: "Company", createUpdate: true, delete: true, view: true, },
          { menuId: 1, subMenuId: 1001, referenceField2: 'Setup', referenceField1: "Language", createUpdate: true, delete: true, view: true, },
        ]
      },
    ];
  fill(data: any) { 
    if (data.pageflow != 'New') {
      this.btntext = 'Updated';
      if (data.pageflow == 'Display') {
        this.form.disable();
        this.isbtntext = false;
      }

      this.spin.show();
      this.moduleService.search({ languageId: [this.auth.languageId], companyId: [this.auth.companyId] }).subscribe(moduleRes => {
        this.sub.add(this.service.Get(data.line.roleId).subscribe(res => {
          console.log(res)
          this.form.patchValue(res[0]);
          let combined = this.cs.removeDuplicateObj(res, moduleRes);
          this.menuList = [];
          this.menuList.push({
            mainMenu: "Setup",
            Menu: combined.filter((x: any) => x.moduleId == 2000)
          });
          this.menuList.push({
            mainMenu: "Masters",
            Menu: combined.filter((x: any) => x.moduleId == 3000)
          });
          this.menuList.push({
            mainMenu: "Airport",
            Menu: combined.filter((x: any) => x.moduleId == 5000)
          });
          this.menuList.push({
            mainMenu: "Customer Service",
            Menu: combined.filter((x: any) => x.moduleId == 4000)
          });
          this.menuList.push({
            mainMenu: "Operations",
            Menu: combined.filter((x: any) => x.moduleId == 6000)
          });
          this.menuList.push({
            mainMenu: "Billings",
            Menu: combined.filter((x: any) => x.moduleId == 7000)
          });
          this.menuList.push({
            mainMenu: "Integrations",
            Menu: combined.filter((x: any) => x.moduleId == 8000)
          });
          this.menuList.push({
            mainMenu: "Reports",
            Menu: combined.filter((x: any) => x.moduleId == 9000)
          });
          this.spin.hide();
        }, err => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }))
      });
    }
  }

  reset() {
    this.menuList = this.menuListRaw;
  }

  resetAll() {
    this.menuList.forEach((x: any) => {
      x.Menu.forEach((element: { createUpdate: boolean; delete: boolean; view: boolean; }) => {
        element.createUpdate = false, element.delete = false, element.view = false
      });
    })
  }

  addScreens() {
    let paramdata = "";
    paramdata = this.cs.encrypt({ lines: this.menuList, });
    this.router.navigate(['/main/idMaster/userrole-new/' + paramdata]);
  }

  checkUncheckAll(menu: any, event: any) {
    if (event.checked) { this.menuList.find(x => x.mainMenu == menu).Menu.forEach((x: any) => { x.createUpdate = true, x.delete = true, x.view = true }) }
    else { this.menuList.find(x => x.mainMenu == menu).Menu.forEach((x: any) => { }) }
  }

  onChange(menu: any, event: any) {
    menu.view = event.checked; menu.createUpdate = event.checked; menu.delete = event.checked;
  }

  // Form builder Initialize
  form = this.fb.group({
    statusId: ["16", [Validators.required]],
    roleId: [,],
    userRoleName: [, [Validators.required]],
    languageId: [, [Validators.required]],
    companyId: [, [Validators.required]],
  });

  onLanguageChange(value: any) {
    this.moduleService.search({ languageId: [value.value] }).subscribe(res => {
      this.companyIdList = [];
      res.forEach((element: { companyId: string; description: string; }) => {
        this.companyIdList.push({ value: element.companyId, lable: element.companyId + '-' + element.description });
      });
    });
    this.moduleService.search({ companyId: [this.auth.companyId], languageId: [this.auth.languageId] }).subscribe(res => {
      this.moduleResult = [];
      this.moduleResult.push(res);
      this.menuList = [];
      this.menuList.push({
        mainMenu: "Setup",
        Menu: res.filter((x: any) => x.module == 2000)
      });
      this.menuList.push({
        mainMenu: "Master",
        Menu: res.filter((x: any) => x.module == 3000)
      });
      this.menuList.push({
        mainMenu: "Airport",
        Menu: res.filter((x: any) => x.moduleId == 5000)
      });
      this.menuList.push({
        mainMenu: "Customer Service",
        Menu: res.filter((x: any) => x.moduleId == 4000)
      });
      this.menuList.push({
        mainMenu: "Operations",
        Menu: res.filter((x: any) => x.moduleId == 6000)
      });
      this.menuList.push({
        mainMenu: "Billings",
        Menu: res.filter((x: any) => x.moduleId == 7000)
      });
      this.menuList.push({
        mainMenu: "Integrations",
        Menu: res.filter((x: any) => x.moduleId == 8000)
      });
      this.menuList.push({
        mainMenu: "Reports",
        Menu: res.filter((x: any) => x.moduleId == 9000)
      });
    });
  }

  onCompanyChange(value: any) {
    this.moduleService.search({ companyId: [value.value], languageId: [this.auth.languageId] }).subscribe(res => {
      this.moduleResult = [];
      this.moduleResult.push(res);
      this.menuList = [];
      this.menuList.push({
        mainMenu: "Setup",
        Menu: res.filter((x: any) => x.moduleId == 2000)
      });
      this.menuList.push({
        mainMenu: "Masters",
        Menu: res.filter((x: any) => x.moduleId == 3000)
      });
      this.menuList.push({
        mainMenu: "Airport",
        Menu: res.filter((x: any) => x.moduleId == 5000)
      });
      this.menuList.push({
        mainMenu: "Customer Service",
        Menu: res.filter((x: any) => x.moduleId == 4000)
      });
      this.menuList.push({
        mainMenu: "Operations",
        Menu: res.filter((x: any) => x.moduleId == 6000)
      });
      this.menuList.push({
        mainMenu: "Billings",
        Menu: res.filter((x: any) => x.moduleId == 7000)
      });
      this.menuList.push({
        mainMenu: "Integrations",
        Menu: res.filter((x: any) => x.moduleId == 8000)
      });
      this.menuList.push({
        mainMenu: "Reports",
        Menu: res.filter((x: any) => x.moduleId == 9000)
      });
    })
  }

  save() {
    // this.form.controls.statusDescription.patchValue(this.form.controls.statusDescription.value == 'Active' ? true : false);

    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({ severity: 'warn', summary: 'Error', key: 'br', detail: 'Please fill the required fields to continue' });
      return;
    }

    this.form.updateValueAndValidity;
    let data: any[] = [];
    this.menuList.forEach((x: any) => {
      x.Menu.forEach((y: any) => {
        y.languageId = this.form.controls.languageId.value;
        y.companyId = this.form.controls.companyId.value;
        y.statusId = this.form.controls.statusId.value;
        y.userRoleName = this.form.controls.userRoleName.value;
        y.edit = true;
        data.push(y);
      });
    })

    this.spin.show();

    if (this.pageToken.pageflow != 'New') {
      this.service.Update(data).subscribe({
        next: (res) => {
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res[0].roleId.value + 'has been updated successfully' });
          this.router.navigate(['/main/idMaster/userrole'])
          this.spin.hide();
        }, error: (err) => {
          this.form.controls.statusId.patchValue(this.form.controls.statusId.value ? 'Active' : 'Inactive');
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }
      });
    } else {
      this.service.Create(data).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res[0].roleId.value + 'has been created successfully' });
            this.router.navigate(['/main/idMaster/userrole']);
            this.spin.hide();
          }
        }, error: (err) => {
          this.form.controls.statusId.patchValue(this.form.controls.statusId.value ? 'Active' : 'InActive');

          this.cs.commonerrorNew(err);
          this.spin.hide();
        }
      });
    }
  };

  getModuleId() {
    this.moduleService.search({ companyId: [this.auth.companyId], languageId: [this.auth.languageId] }).subscribe(res => {
      this.moduleResult = [];
      this.moduleResult = res;

      this.menuList = [];
      this.menuList.push({
        mainMenu: "Setup",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 2000)
      });
      this.menuList.push({
        mainMenu: "Masters",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 3000)
      });
      this.menuList.push({
        mainMenu: "Airport",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 5000)
      });
      this.menuList.push({
        mainMenu: "Customer Service",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 4000)
      });
      this.menuList.push({
        mainMenu: "Operations",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 6000)
      });
      this.menuList.push({
        mainMenu: "Billings",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 7000)
      });
      this.menuList.push({
        mainMenu: "Integrations",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 8000)
      });
      this.menuList.push({
        mainMenu: "Reports",
        Menu: this.moduleResult.filter((x: any) => x.moduleId == 9000)
      });
    });
  }

  getModuleIDSuperAdmin() {
    this.moduleService.search({ companyId: [this.form.controls.companyId.value], languageId: this.form.controls.languageId.value }).subscribe(res => {
      this.moduleResult = [];
      this.moduleResult.push(res);
      this.menuList = [];
      this.menuList.push({
        mainMenu: "Setup",
        Menu: res.filter((x: any) => x.moduleId == 2000)
      });
      this.menuList.push({
        mainMenu: "Masters",
        Menu: res.filter((x: any) => x.moduleId == 3000)
      });
      this.menuList.push({
        mainMenu: "Airport",
        Menu: res.filter((x: any) => x.moduleId == 5000)
      });
      this.menuList.push({
        mainMenu: "Customer Service",
        Menu: res.filter((x: any) => x.moduleId == 4000)
      });
      this.menuList.push({
        mainMenu: "Operations",
        Menu: res.filter((x: any) => x.moduleId == 6000)
      });
      this.menuList.push({
        mainMenu: "Billings",
        Menu: res.filter((x: any) => x.moduleId == 7000)
      });
      this.menuList.push({
        mainMenu: "Integrations",
        Menu: res.filter((x: any) => x.moduleId == 8000)
      });
      this.menuList.push({
        mainMenu: "Reports",
        Menu: res.filter((x: any) => x.moduleId == 9000)
      });
    });
  }

}
