import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { ModuleService } from '../module.service';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { AuthService } from '../../../../core/core';
import { MenuService } from '../../menu/menu.service';
import { NumberrangeService } from '../../numberrange/numberrange.service';
import { Stepper } from 'primeng/stepper';

@Component({
  selector: 'app-module-new',
  templateUrl: './module-new.component.html',
  styleUrl: './module-new.component.scss'
})
export class ModuleNewComponent {
  active: number | undefined = 0;


  status: any[] = []
  moduleTable: any[] = [];
  selectedModule: any[] = [];
  cols: any[] = [];

  constructor(
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ModuleService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private numberRangeService: NumberrangeService,
    private auth: AuthService,
    private menuService: MenuService,
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
    companyId: [this.auth.companyId,],
    languageId: [this.auth.languageId,],
    addModule: [],
    companyIdAndDescription: [],
    createUpdate: [true,],
    createdBy: [],
    createdOn: ['',],
    delete: [true,],
    deletionIndicator: [],
    languageIdAndDescription: [],
    menuId: [, Validators.required],
    menuName: [],
    moduleDescription: [, Validators.required],
    moduleId: [, Validators.required],
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
    subMenuId: [],
    statusId: ["16",],
    statusDescription: [],
    subMenuName: [],
    updatedBy: [],
    updatedOn: ['',],
    view: [true,],
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

    const dataToSend = ['Setup', 'Module', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    this.dropdownlist();

    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();

    if (this.pageToken.pageflow != 'New') {
      this.fill(this.pageToken.line);
      this.form.controls.moduleId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
    else {
      this.spin.show();
      let obj: any = {};
      obj.numberRangeObject = ['MODULE'];
      this.numberRangeService.search(obj).subscribe({
        next: (res: any) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.moduleId.patchValue(this.nextNumber);
            this.numCondition = 'true';
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.moduleId.disable();
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
  subMenuIdList: any[] = [];
  menuIdList: any[] = [];

  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.menu.url,

    ]).subscribe({
      next: (results: any) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.menuIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.menu.key);
        this.menuIdList = this.cs.removeDuplicatesFromArrayList(this.menuIdList, 'value');
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }

  fill(line: any) {
    this.service.Get(line.moduleId).subscribe({
      next: (res) => {
        if (res) {
          this.form.patchValue(res[0]);
          const uniqueMenuIds: any = Array.from(new Set(res.map((x: any) => x.menuId)));
          this.form.controls.menuId.patchValue(uniqueMenuIds);
          this.patchTableUpdate(res, uniqueMenuIds);
        }
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    })
  }

  patchTableUpdate(res: any, uniqueMenuIds:any) {
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.menuId = uniqueMenuIds;

    this.menuService.search(obj).subscribe({
      next: (menures) => {
        this.moduleTable = [];
        this.selectedModule = [];

        let combined = this.cs.removeDuplicateObj(res, menures);
        this.moduleTable = combined;
        this.selectedModule = res;

        this.cols = [
          { field: 'menuId', header: 'Menu ID' },
          { field: 'menuName', header: 'Menu Name' },
          { field: 'subMenuId', header: 'Sub Menu ID' },
          { field: 'subMenuName', header: 'Sub Menu Name' }
        ];

      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    })
  }

  space(event: any) {
    if (event.target.selectionStart === 0 && event.code === 'Space') {
      event.preventDefault();
    }
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

    this.selectedModule.forEach(x => {
      x.moduleDescription = this.form.controls.moduleDescription.value;
      x.moduleId = this.form.controls.moduleId.value;
    })



    if (this.pageToken.pageflow != 'New') {
      this.spin.show();
      this.service.Update(this.selectedModule).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Updated',
            key: 'br',
            detail: res[0].moduleId + ' has been updated successfully',
          });
          this.router.navigate(['/main/idMaster/module']);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    } else {
      this.spin.show();
      this.service.Create(this.selectedModule).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: 'success',
              summary: 'Created',
              key: 'br',
              detail: res[0].moduleId + ' has been created successfully',
            });
            this.router.navigate(['/main/idMaster/module']);
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
  menuIdChanged(event: any) {
    let obj: any = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.menuId = event.value;
    this.menuService.search(obj).subscribe({
      next: (res) => {
        this.moduleTable = [];
        this.selectedModule = [];
        this.moduleTable = res;
        this.selectedModule = res;

        this.cols = [
          { field: 'menuId', header: 'Menu ID' },
          { field: 'menuName', header: 'Menu Name' },
          { field: 'subMenuId', header: 'Sub Menu ID' },
          { field: 'subMenuName', header: 'Sub Menu Name' }
        ];

      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    })

  }
  nextToSecond() {
    this.submitted = true;
    if (!this.form.controls.moduleId.value || !this.form.controls.moduleDescription.value || !this.form.controls.menuId.value) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        key: 'br',
        detail: 'Please fill required fields to continue',
      });
      return;
    }
    this.active = 1;
    this.submitted = false;
  }
}
