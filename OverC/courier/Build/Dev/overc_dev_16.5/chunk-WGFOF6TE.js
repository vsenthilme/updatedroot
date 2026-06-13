import {
  DeleteComponent
} from "./chunk-ONTB56N3.js";
import {
  NumberrangeService
} from "./chunk-5VNCQIEH.js";
import {
  CommonAPIService
} from "./chunk-EGWAMV4K.js";
import {
  PathNameService
} from "./chunk-O6NDCGFA.js";
import {
  CustomTableComponent
} from "./chunk-5BMWMTJQ.js";
import {
  ActivatedRoute,
  AuthService,
  Checkbox,
  Chips,
  CommonServiceService,
  DatePipe,
  DefaultValueAccessor,
  Dropdown,
  FormBuilder,
  FormControl,
  FormControlName,
  FormGroupDirective,
  HttpClient,
  IconField,
  InputIcon,
  InputText,
  KeyFilter,
  MatDialog,
  MatError,
  MatMenu,
  MatMenuContent,
  MatMenuItem,
  MaxLengthValidator,
  MessageService,
  MultiSelect,
  NgClass,
  NgControlStatus,
  NgControlStatusGroup,
  NgForOf,
  NgIf,
  NgModel,
  NgxSpinnerService,
  OverlayPanel,
  PrimeTemplate,
  Router,
  RouterLink,
  SortIcon,
  SortableColumn,
  Stepper,
  StepperPanel,
  Table,
  TableHeaderCheckbox,
  TrimDirective,
  Validators,
  ɵNgNoValidate,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵattribute,
  ɵɵclassProp,
  ɵɵdefineComponent,
  ɵɵdefineInjectable,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementContainerEnd,
  ɵɵelementContainerStart,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵgetCurrentView,
  ɵɵinject,
  ɵɵlistener,
  ɵɵloadQuery,
  ɵɵnextContext,
  ɵɵpipe,
  ɵɵpipeBind2,
  ɵɵproperty,
  ɵɵpureFunction0,
  ɵɵqueryRefresh,
  ɵɵreference,
  ɵɵresetView,
  ɵɵrestoreView,
  ɵɵstyleMap,
  ɵɵtemplate,
  ɵɵtemplateRefExtractor,
  ɵɵtext,
  ɵɵtextInterpolate,
  ɵɵtextInterpolate1,
  ɵɵtwoWayBindingSet,
  ɵɵtwoWayListener,
  ɵɵtwoWayProperty,
  ɵɵviewQuery
} from "./chunk-HJPREMTO.js";

// src/app/main/id-masters/numberrange/numberrange-new/numberrange-new.component.ts
var _c0 = () => ({ "width": "100%" });
function NumberrangeNewComponent_ng_template_11_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 15);
    \u0275\u0275text(1, "1");
    \u0275\u0275elementEnd();
  }
}
function NumberrangeNewComponent_ng_template_11_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 16);
  }
}
function NumberrangeNewComponent_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 10);
    \u0275\u0275listener("click", function NumberrangeNewComponent_ng_template_11_Template_div_click_0_listener() {
      const onClick_r2 = \u0275\u0275restoreView(_r1).onClick;
      return \u0275\u0275resetView(onClick_r2.emit());
    });
    \u0275\u0275elementStart(1, "div", 11);
    \u0275\u0275template(2, NumberrangeNewComponent_ng_template_11_p_2_Template, 2, 0, "p", 12)(3, NumberrangeNewComponent_ng_template_11_img_3_Template, 1, 0, "img", 13);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 14);
    \u0275\u0275text(6, "General");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.active == 0);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.active != 0);
  }
}
function NumberrangeNewComponent_ng_template_12_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 33);
  }
}
function NumberrangeNewComponent_ng_template_12_mat_error_6_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 34)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function NumberrangeNewComponent_ng_template_12_mat_error_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 34)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function NumberrangeNewComponent_ng_template_12_mat_error_16_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 34)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function NumberrangeNewComponent_ng_template_12_mat_error_21_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 34)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function NumberrangeNewComponent_ng_template_12_mat_error_26_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 34)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function NumberrangeNewComponent_ng_template_12_mat_error_31_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 34)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function NumberrangeNewComponent_ng_template_12_ng_template_36_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 33);
  }
}
function NumberrangeNewComponent_ng_template_12_mat_error_37_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 34)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function NumberrangeNewComponent_ng_template_12_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 17)(1, "div", 18)(2, "p", 19);
    \u0275\u0275text(3, "Language");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "p-dropdown", 20);
    \u0275\u0275template(5, NumberrangeNewComponent_ng_template_12_ng_template_5_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(6, NumberrangeNewComponent_ng_template_12_mat_error_6_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "div", 18)(8, "p", 23);
    \u0275\u0275text(9, "ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(10, "input", 24);
    \u0275\u0275template(11, NumberrangeNewComponent_ng_template_12_mat_error_11_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "div", 18)(13, "p", 23);
    \u0275\u0275text(14, "Process");
    \u0275\u0275elementEnd();
    \u0275\u0275element(15, "input", 25);
    \u0275\u0275template(16, NumberrangeNewComponent_ng_template_12_mat_error_16_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(17, "div", 18)(18, "p", 23);
    \u0275\u0275text(19, "From Number");
    \u0275\u0275elementEnd();
    \u0275\u0275element(20, "input", 26);
    \u0275\u0275template(21, NumberrangeNewComponent_ng_template_12_mat_error_21_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(22, "div", 18)(23, "p", 23);
    \u0275\u0275text(24, "To Number");
    \u0275\u0275elementEnd();
    \u0275\u0275element(25, "input", 27);
    \u0275\u0275template(26, NumberrangeNewComponent_ng_template_12_mat_error_26_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(27, "div", 18)(28, "p", 23);
    \u0275\u0275text(29, "Current Number");
    \u0275\u0275elementEnd();
    \u0275\u0275element(30, "input", 28);
    \u0275\u0275template(31, NumberrangeNewComponent_ng_template_12_mat_error_31_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(32, "div", 18)(33, "p", 23);
    \u0275\u0275text(34, "Number Range Status");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(35, "p-dropdown", 29);
    \u0275\u0275template(36, NumberrangeNewComponent_ng_template_12_ng_template_36_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(37, NumberrangeNewComponent_ng_template_12_mat_error_37_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(38, "div", 30)(39, "button", 31);
    \u0275\u0275text(40, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "button", 32);
    \u0275\u0275listener("click", function NumberrangeNewComponent_ng_template_12_Template_button_click_41_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(42);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    let tmp_8_0;
    let tmp_10_0;
    let tmp_12_0;
    let tmp_14_0;
    let tmp_16_0;
    let tmp_21_0;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(24, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.languageIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(25, _c0));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("languageId"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_8_0 = ctx_r2.form.get("numberRangeCode")) == null ? null : tmp_8_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("numberRangeCode"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_10_0 = ctx_r2.form.get("numberRangeObject")) == null ? null : tmp_10_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("numberRangeObject"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_12_0 = ctx_r2.form.get("numberRangeFrom")) == null ? null : tmp_12_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("numberRangeFrom"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_14_0 = ctx_r2.form.get("numberRangeTo")) == null ? null : tmp_14_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("numberRangeTo"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_16_0 = ctx_r2.form.get("numberRangeCurrent")) == null ? null : tmp_16_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("numberRangeCurrent"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(26, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.status)("ngClass", ((tmp_21_0 = ctx_r2.form.get("numberRangeStatus")) == null ? null : tmp_21_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("numberRangeStatus"));
    \u0275\u0275advance(5);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function NumberrangeNewComponent_p_stepperPanel_13_ng_template_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 10);
    \u0275\u0275listener("click", function NumberrangeNewComponent_p_stepperPanel_13_ng_template_1_Template_div_click_0_listener() {
      const onClick_r6 = \u0275\u0275restoreView(_r5).onClick;
      return \u0275\u0275resetView(onClick_r6.emit());
    });
    \u0275\u0275elementStart(1, "div", 11)(2, "p", 35);
    \u0275\u0275text(3, " 2");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div")(5, "p", 36);
    \u0275\u0275text(6, " Admin");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275property("ngClass", ctx_r2.active == 2 ? "borderCircle" : "disabled text-muted");
    \u0275\u0275advance(3);
    \u0275\u0275property("ngClass", ctx_r2.active == 2 ? "textBlack f600" : "text-muted");
  }
}
function NumberrangeNewComponent_p_stepperPanel_13_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 37)(1, "div", 18)(2, "p", 19);
    \u0275\u0275text(3, "Created By");
    \u0275\u0275elementEnd();
    \u0275\u0275element(4, "input", 38);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "div", 18)(6, "p", 19);
    \u0275\u0275text(7, "Created On");
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "input", 39);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "div", 18)(10, "p", 19);
    \u0275\u0275text(11, "Updated By");
    \u0275\u0275elementEnd();
    \u0275\u0275element(12, "input", 40);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 18)(14, "p", 19);
    \u0275\u0275text(15, "Updated On");
    \u0275\u0275elementEnd();
    \u0275\u0275element(16, "input", 41);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "div", 30)(18, "button", 42);
    \u0275\u0275listener("click", function NumberrangeNewComponent_p_stepperPanel_13_ng_template_2_Template_button_click_18_listener() {
      const prevCallback_r8 = \u0275\u0275restoreView(_r7).prevCallback;
      return \u0275\u0275resetView(prevCallback_r8.emit());
    });
    \u0275\u0275text(19, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "button", 32);
    \u0275\u0275listener("click", function NumberrangeNewComponent_p_stepperPanel_13_ng_template_2_Template_button_click_20_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext(2);
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(21);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(21);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function NumberrangeNewComponent_p_stepperPanel_13_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p-stepperPanel");
    \u0275\u0275template(1, NumberrangeNewComponent_p_stepperPanel_13_ng_template_1_Template, 7, 2, "ng-template", 7)(2, NumberrangeNewComponent_p_stepperPanel_13_ng_template_2_Template, 22, 1, "ng-template", 8);
    \u0275\u0275elementEnd();
  }
}
var _NumberrangeNewComponent = class _NumberrangeNewComponent {
  constructor(cs, spin, route, router, path, fb, service, messageService, cas, auth) {
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.path = path;
    this.fb = fb;
    this.service = service;
    this.messageService = messageService;
    this.cas = cas;
    this.auth = auth;
    this.active = 0;
    this.status = [];
    this.form = this.fb.group({
      languageId: [this.auth.languageId, Validators.required],
      // languageDescription: [],
      numberRangeCode: [, Validators.required],
      numberRangeObject: [, Validators.required],
      numberRangeFrom: [, Validators.required],
      numberRangeTo: [, Validators.required],
      numberRangeCurrent: [, Validators.required],
      numberRangeStatus: ["16"],
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
      createdOn: [""],
      createdBy: [],
      updatedBy: [],
      updatedOn: [""]
    });
    this.submitted = false;
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.languageIdList = [];
    this.status = [
      { value: "17", label: "Inactive" },
      { value: "16", label: "Active" }
    ];
  }
  errorHandling(control, error = "required") {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError("required")) {
      return " Field should not be blank";
    }
    return this.email.hasError("email") ? "Not a valid email" : "";
  }
  ngOnInit() {
    let code = this.route.snapshot.params["code"];
    this.pageToken = this.cs.decrypt(code);
    const dataToSend = ["Setup", "Number Series", this.pageToken.pageflow];
    this.path.setData(dataToSend);
    this.dropdownlist();
    this.form.controls.languageId.disable();
    if (this.pageToken.pageflow != "New") {
      this.fill(this.pageToken.line);
      this.form.controls.languageId.disable();
      this.form.controls.numberRangeCode.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url
    ]).subscribe({
      next: (results) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  fill(line) {
    this.form.patchValue(line);
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
  }
  save() {
    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({ severity: "error", summary: "Error", key: "br", detail: "Please fill required fields to continue" });
      return;
    }
    if (this.pageToken.pageflow != "New") {
      this.spin.show();
      this.service.Update(this.form.getRawValue()).subscribe({
        next: (res) => {
          this.messageService.add({ severity: "success", summary: "Updated", key: "br", detail: res.numberRangeCode + " has been updated successfully" });
          this.router.navigate(["/main/idMaster/numberrange"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      this.spin.show();
      this.service.Create(this.form.getRawValue()).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({ severity: "success", summary: "Created", key: "br", detail: res.numberRangeCode + " has been created successfully" });
            this.router.navigate(["/main/idMaster/numberrange"]);
            this.spin.hide();
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
};
_NumberrangeNewComponent.\u0275fac = function NumberrangeNewComponent_Factory(t) {
  return new (t || _NumberrangeNewComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NumberrangeService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService));
};
_NumberrangeNewComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _NumberrangeNewComponent, selectors: [["app-numberrange-new"]], decls: 14, vars: 4, consts: [[1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", 1, "invisible", "buttom1", "bgBlack", "text-white", "ml-2"], [1, "w-75", "mt-4", "mx-auto"], [3, "formGroup"], [3, "activeStepChange", "activeStep"], ["pTemplate", "header"], ["pTemplate", "content"], [4, "ngIf"], [1, "d-flex", "flex-column", "align-items-center", 3, "click"], [1, "d-flex", "justify-content-center", "align-items-center"], ["class", "circle borderCircle mb-0", 4, "ngIf"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", "", 4, "ngIf"], [1, "mb-0", "mt-2", "f600", "textBlack"], [1, "circle", "borderCircle", "mb-0"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", ""], [1, "row", "scrollNew"], [1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["formControlName", "languageId", "appendTo", "body", "placeholder", "Select", "filter", "true", 3, "showClear", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["class", "text-danger", 4, "ngIf"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["maxlength", "50", "formControlName", "numberRangeCode", "pInputText", "", "appTrim", "", "pKeyFilter", "num", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["maxlength", "100", "formControlName", "numberRangeObject", "pInputText", "", "appTrim", "", "pKeyFilter", "alphanum", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["maxlength", "50", "formControlName", "numberRangeFrom", "pInputText", "", "appTrim", "", "pKeyFilter", "alphanum", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["maxlength", "50", "formControlName", "numberRangeTo", "pInputText", "", "appTrim", "", "pKeyFilter", "alphanum", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["maxlength", "50", "formControlName", "numberRangeCurrent", "pInputText", "", "appTrim", "", "pKeyFilter", "alphanum", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["appendTo", "body", "formControlName", "numberRangeStatus", "placeholder", "Select", "filter", "true", 3, "showClear", "options", "ngClass"], [1, "d-flex", "mt-1", "justify-content-end", 2, "position", "absolute", "right", "3.5%", "bottom", "7%"], ["routerLink", "/main/idMaster/numberrange", 1, "buttom1", "textBlack", "mx-1"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], [1, "text-danger"], [1, "circle", "mb-0", 3, "ngClass"], [1, "mb-0", "mt-2", "f600", 3, "ngClass"], [1, "row"], ["type", "text", "formControlName", "createdBy", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "createdOn", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "updatedBy", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "updatedOn", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], [1, "buttom1", "textBlack", "mx-1", 3, "click"]], template: function NumberrangeNewComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1)(2, "p", 2);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 1)(5, "button", 3);
    \u0275\u0275text(6, "Transfer");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(7, "div", 4)(8, "form", 5)(9, "p-stepper", 6);
    \u0275\u0275twoWayListener("activeStepChange", function NumberrangeNewComponent_Template_p_stepper_activeStepChange_9_listener($event) {
      \u0275\u0275twoWayBindingSet(ctx.active, $event) || (ctx.active = $event);
      return $event;
    });
    \u0275\u0275elementStart(10, "p-stepperPanel");
    \u0275\u0275template(11, NumberrangeNewComponent_ng_template_11_Template, 7, 2, "ng-template", 7)(12, NumberrangeNewComponent_ng_template_12_Template, 43, 27, "ng-template", 8);
    \u0275\u0275elementEnd();
    \u0275\u0275template(13, NumberrangeNewComponent_p_stepperPanel_13_Template, 3, 0, "p-stepperPanel", 9);
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1("Number Series - ", ctx.pageToken.pageflow, "");
    \u0275\u0275advance(5);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance();
    \u0275\u0275twoWayProperty("activeStep", ctx.active);
    \u0275\u0275advance(4);
    \u0275\u0275property("ngIf", ctx.pageToken.pageflow != "New");
  }
}, dependencies: [NgClass, NgIf, RouterLink, PrimeTemplate, Dropdown, InputText, Stepper, StepperPanel, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, KeyFilter, MatError, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 2.5rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 24rem);\n  overflow-y: scroll;\n}\n/*# sourceMappingURL=numberrange-new.component.css.map */"] });
var NumberrangeNewComponent = _NumberrangeNewComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(NumberrangeNewComponent, { className: "NumberrangeNewComponent", filePath: "src\\app\\main\\id-masters\\numberrange\\numberrange-new\\numberrange-new.component.ts", lineNumber: 18 });
})();

// src/app/main/id-masters/numberrange/numberrange.component.ts
var _c02 = ["numberRange"];
var _c1 = () => ({ width: "80vw" });
var _c2 = () => ({ "width": "100%" });
var _c3 = () => ({ "width": "100rem" });
function NumberrangeComponent_ng_template_44_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 41);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 42);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r4 = ctx.$implicit;
    \u0275\u0275property("pSortableColumn", col_r4.field);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r4.header, " ");
    \u0275\u0275advance();
    \u0275\u0275property("field", col_r4.field);
  }
}
function NumberrangeComponent_ng_template_44_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 43);
    \u0275\u0275listener("input", function NumberrangeComponent_ng_template_44_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const numberRangeTag_r3 = \u0275\u0275reference(43);
      return \u0275\u0275resetView(numberRangeTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const numberRangeTag_r3 = \u0275\u0275reference(43);
    \u0275\u0275advance();
    \u0275\u0275property("value", numberRangeTag_r3.filters[col_r6.field] == null ? null : numberRangeTag_r3.filters[col_r6.field].value);
  }
}
function NumberrangeComponent_ng_template_44_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 37);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 38);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, NumberrangeComponent_ng_template_44_th_3_Template, 3, 3, "th", 39);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 37);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 38);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, NumberrangeComponent_ng_template_44_th_7_Template, 2, 1, "th", 40);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r7 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r7);
    \u0275\u0275advance(3);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r7);
  }
}
function NumberrangeComponent_ng_template_45_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    const rowData_r11 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r11[col_r10.field], " ");
  }
}
function NumberrangeComponent_ng_template_45_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r12 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 50);
    \u0275\u0275listener("click", function NumberrangeComponent_ng_template_45_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r12);
      const rowData_r11 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.openCrud("Edit", rowData_r11));
    });
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r11 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(rowData_r11[col_r10.field]);
  }
}
function NumberrangeComponent_ng_template_45_td_3_ng_template_2_span_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span");
    \u0275\u0275text(1);
    \u0275\u0275pipe(2, "date");
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r11 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(\u0275\u0275pipeBind2(2, 1, rowData_r11[col_r10.field], "dd-MM-yyyy HH:mm"));
  }
}
function NumberrangeComponent_ng_template_45_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, NumberrangeComponent_ng_template_45_td_3_ng_template_2_span_0_Template, 2, 1, "span", 48)(1, NumberrangeComponent_ng_template_45_td_3_ng_template_2_span_1_Template, 3, 4, "span", 49);
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r10.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format == "date");
  }
}
function NumberrangeComponent_ng_template_45_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, NumberrangeComponent_ng_template_45_td_3_ng_container_1_Template, 2, 1, "ng-container", 47)(2, NumberrangeComponent_ng_template_45_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = ctx.$implicit;
    const dateTemplate_r13 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r10.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format !== "date" && col_r10.format !== "hyperLink")("ngIfElse", dateTemplate_r13);
  }
}
function NumberrangeComponent_ng_template_45_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr")(1, "td", 44)(2, "p-checkbox", 45);
    \u0275\u0275listener("onChange", function NumberrangeComponent_ng_template_45_Template_p_checkbox_onChange_2_listener() {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.onChange());
    });
    \u0275\u0275twoWayListener("ngModelChange", function NumberrangeComponent_ng_template_45_Template_p_checkbox_ngModelChange_2_listener($event) {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r8.selectedNumberRange, $event) || (ctx_r8.selectedNumberRange = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275template(3, NumberrangeComponent_ng_template_45_td_3_Template, 4, 4, "td", 46);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r11 = ctx.$implicit;
    const columns_r14 = ctx.columns;
    const ctx_r8 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r8.selectedNumberRange[0] === rowData_r11);
    \u0275\u0275advance(2);
    \u0275\u0275twoWayProperty("ngModel", ctx_r8.selectedNumberRange);
    \u0275\u0275property("value", rowData_r11);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r14);
  }
}
function NumberrangeComponent_ng_template_46_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 51);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function NumberrangeComponent_ng_template_49_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "button", 52);
    \u0275\u0275text(1, "Edit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(2, "button", 52);
    \u0275\u0275text(3, "Confirm");
    \u0275\u0275elementEnd();
  }
}
var _NumberrangeComponent = class _NumberrangeComponent {
  constructor(messageService, cs, router, path, service, dialog, datePipe, auth, fb, spin) {
    this.messageService = messageService;
    this.cs = cs;
    this.router = router;
    this.path = path;
    this.service = service;
    this.dialog = dialog;
    this.datePipe = datePipe;
    this.auth = auth;
    this.fb = fb;
    this.spin = spin;
    this.numberRangeTable = [];
    this.selectedNumberRange = [];
    this.cols = [];
    this.target = [];
    this.searchform = this.fb.group({
      numberRangeCode: [],
      numberRangeObject: [],
      statusId: [],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      numberRangeCode: "ID",
      numberRangeObject: "Process/Master",
      statusId: "Status"
    };
    this.languageDropdown = [];
    this.numberRangeCodeDropdown = [];
    this.numberRangeObjectDropdown = [];
    this.statusDropdown = [];
  }
  ngOnInit() {
    const dataToSend = ["Setup", "Number Series"];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "numberRangeCode", header: "ID", format: "hyperLink" },
      { field: "numberRangeObject", header: "Process/Master" },
      { field: "numberRangeFrom", header: "From Number" },
      { field: "numberRangeTo", header: "To Number" },
      { field: "numberRangeCurrent", header: "Current Number" },
      { field: "statusDescription", header: "Status" },
      { field: "createdBy", header: "Created By" },
      { field: "createdOn", header: "Created On", format: "date" }
    ];
    this.target = [
      { field: "numberRangeStatus", header: "Number Range Status" },
      { field: "referenceField1", header: "Reference Field 1" },
      { field: "referenceField2", header: "Reference Field 2" },
      { field: "referenceField3", header: "Reference Field 3" },
      { field: "referenceField4", header: "Reference Field 4" },
      { field: "referenceField5", header: "Reference Field 5" },
      { field: "referenceField6", header: "Reference Field 6" },
      { field: "referenceField7", header: "Reference Field 7" },
      { field: "referenceField8", header: "Reference Field 8" },
      { field: "referenceField9", header: "Reference Field 9" },
      { field: "referenceField10", header: "Reference Field 10" },
      { field: "updatedBy", header: "Updated By" },
      { field: "updatedOn", header: "Updated On", format: "date" }
    ];
  }
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.service.search(obj).subscribe({
        next: (res) => {
          console.log(res);
          this.numberRangeTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }, 1e3);
  }
  onChange() {
    const choosen = this.selectedNumberRange[this.selectedNumberRange.length - 1];
    this.selectedNumberRange.length = 0;
    this.selectedNumberRange.push(choosen);
  }
  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" },
      data: { target: this.cols, source: this.target }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedNumberRange[0]);
      }
    });
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedNumberRange = linedata;
    }
    if (this.selectedNumberRange.length === 0 && type != "New") {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedNumberRange[0] : linedata, pageflow: type });
      this.router.navigate(["/main/idMaster/numberrange-new/" + paramdata]);
    }
  }
  deleteDialog() {
    if (this.selectedNumberRange.length === 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: "60%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "30%" },
      data: { line: this.selectedNumberRange, module: "Number Series", body: "This action cannot be undone. All values associated with this field will be lost." }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedNumberRange[0]);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: "success", summary: "Deleted", key: "br", detail: lines.numberRangeCode + " deleted successfully" });
        this.spin.hide();
        this.initialCall();
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    });
  }
  downloadExcel() {
    const exportData = this.numberRangeTable.map((item) => {
      const exportItem = {};
      this.cols.forEach((col) => {
        if (col.format == "data") {
          console.log(3);
          exportItem[col.header] = this.datePipe.transform(item[col.field], "dd-MM-yyyy");
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, "Number Series");
  }
  getSearchDropdown() {
    this.numberRangeTable.forEach((res) => {
      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, "value");
      }
      if (res.numberRangeCode != null) {
        this.numberRangeCodeDropdown.push({ value: res.numberRangeCode, label: res.numberRangeCode });
        this.numberRangeCodeDropdown = this.cs.removeDuplicatesFromArrayList(this.numberRangeCodeDropdown, "value");
      }
      if (res.numberRangeObject != null) {
        this.numberRangeObjectDropdown.push({ value: res.numberRangeObject, label: res.numberRangeObject });
        this.numberRangeObjectDropdown = this.cs.removeDuplicatesFromArrayList(this.numberRangeObjectDropdown, "value");
      }
      if (res.numberRangeStatus != null) {
        this.statusDropdown.push({ value: res.numberRangeStatus, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, "value");
      }
    });
  }
  closeOverLay() {
    this.overlayPanel.hide();
  }
  search() {
    this.fieldsWithValue = null;
    const formValues = this.searchform.value;
    this.fieldsWithValue = Object.keys(formValues).filter((key) => formValues[key] !== null && formValues[key] !== void 0 && key !== "companyId" && key !== "languageId").map((key) => this.fieldDisplayNames[key] || key);
    this.spin.show();
    this.service.search(this.searchform.getRawValue()).subscribe({
      next: (res) => {
        this.numberRangeTable = res;
        this.spin.hide();
        this.overlayPanel.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  reset() {
    this.searchform.reset();
    this.searchform = this.fb.group({
      numberRangeCode: [],
      numberRangeObject: [],
      statusId: [],
      languageId: [[this.auth.languageId]]
    });
    this.search();
  }
  chipClear(value) {
    const formControlKey = Object.keys(this.fieldDisplayNames).find((key) => this.fieldDisplayNames[key] === value.value);
    if (formControlKey) {
      this.searchform.get(formControlKey)?.reset();
      this.search();
    }
  }
};
_NumberrangeComponent.\u0275fac = function NumberrangeComponent_Factory(t) {
  return new (t || _NumberrangeComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(NumberrangeService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NgxSpinnerService));
};
_NumberrangeComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _NumberrangeComponent, selectors: [["app-numberrange"]], viewQuery: function NumberrangeComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c02, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 50, vars: 37, consts: [["numberRange", ""], ["numberRangeTag", ""], ["menu", "matMenu"], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "numberRangeObject", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "numberRangeCode", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "single", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "columns", "value", "scrollable", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [1, "customClass"], ["matMenuContent", ""], [2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "onChange", "ngModelChange", "ngModel", "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"], ["mat-menu-item", ""]], template: function NumberrangeComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 4)(1, "div", 5)(2, "p", 6);
    \u0275\u0275text(3, "Number Series ");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 5)(5, "img", 7);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 8);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 9);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "img", 10);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_img_click_8_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.customTable());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "button", 11);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_button_click_9_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("New"));
    });
    \u0275\u0275element(10, "i", 12);
    \u0275\u0275text(11, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 13)(13, "p-iconField", 14)(14, "p-inputIcon", 15);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_p_inputIcon_click_14_listener($event) {
      \u0275\u0275restoreView(_r1);
      const numberRange_r2 = \u0275\u0275reference(17);
      return \u0275\u0275resetView(numberRange_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "input", 16);
    \u0275\u0275listener("input", function NumberrangeComponent_Template_input_input_15_listener($event) {
      \u0275\u0275restoreView(_r1);
      const numberRangeTag_r3 = \u0275\u0275reference(43);
      return \u0275\u0275resetView(numberRangeTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(16, "p-overlayPanel", 17, 0)(18, "form", 18)(19, "div", 19)(20, "div", 20)(21, "p", 21);
    \u0275\u0275text(22, "Process/Master");
    \u0275\u0275elementEnd();
    \u0275\u0275element(23, "p-multiSelect", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(24, "div", 20)(25, "p", 21);
    \u0275\u0275text(26, "ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(27, "p-multiSelect", 23);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(28, "div", 20)(29, "p", 21);
    \u0275\u0275text(30, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275element(31, "p-multiSelect", 24);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(32, "div", 25)(33, "div")(34, "img", 26);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_img_click_34_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(35, "div")(36, "button", 27);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_button_click_36_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(37, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(38, "button", 28);
    \u0275\u0275listener("click", function NumberrangeComponent_Template_button_click_38_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(39, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(40, "p-chips", 29);
    \u0275\u0275listener("onRemove", function NumberrangeComponent_Template_p_chips_onRemove_40_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function NumberrangeComponent_Template_p_chips_ngModelChange_40_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(41, "div", 30)(42, "p-table", 31, 1);
    \u0275\u0275template(44, NumberrangeComponent_ng_template_44_Template, 8, 4, "ng-template", 32)(45, NumberrangeComponent_ng_template_45_Template, 4, 5, "ng-template", 33)(46, NumberrangeComponent_ng_template_46_Template, 4, 1, "ng-template", 34);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(47, "mat-menu", 35, 2);
    \u0275\u0275template(49, NumberrangeComponent_ng_template_49_Template, 4, 0, "ng-template", 36);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    \u0275\u0275advance(16);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(29, _c1));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(30, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.numberRangeObjectDropdown)("panelStyle", \u0275\u0275pureFunction0(31, _c2));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(32, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.numberRangeCodeDropdown)("panelStyle", \u0275\u0275pureFunction0(33, _c2));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(34, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(35, _c2));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.numberRangeTable)("scrollable", true)("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(36, _c3));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, SortIcon, TableHeaderCheckbox, InputIcon, IconField, InputText, Checkbox, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenu, MatMenuItem, MatMenuContent, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 2.5rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 24rem);\n  overflow-y: scroll;\n}\n/*# sourceMappingURL=numberrange.component.css.map */"] });
var NumberrangeComponent = _NumberrangeComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(NumberrangeComponent, { className: "NumberrangeComponent", filePath: "src\\app\\main\\id-masters\\numberrange\\numberrange.component.ts", lineNumber: 22 });
})();

// src/app/main/id-masters/app-user/app-user.service.ts
var _AppUserService = class _AppUserService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(appUserId) {
    return this.http.get("/overc-idmaster-service/appUser/" + appUserId + "?companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/appUser", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/appUser/" + obj.appUserId + "?companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId, obj);
  }
  Delete(appUserId) {
    return this.http.delete("/overc-idmaster-service/appUser/" + appUserId + "?companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/appUser/find", obj);
  }
};
_AppUserService.\u0275fac = function AppUserService_Factory(t) {
  return new (t || _AppUserService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_AppUserService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _AppUserService, factory: _AppUserService.\u0275fac, providedIn: "root" });
var AppUserService = _AppUserService;

// src/app/main/id-masters/app-user/app-user.component.ts
var _c03 = ["appUser"];
var _c12 = () => ({ width: "80vw" });
var _c22 = () => ({ "width": "100%" });
var _c32 = () => ({ "width": "100rem" });
function AppUserComponent_ng_template_40_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 37);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 38);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r4 = ctx.$implicit;
    \u0275\u0275property("pSortableColumn", col_r4.field);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r4.header, " ");
    \u0275\u0275advance();
    \u0275\u0275property("field", col_r4.field);
  }
}
function AppUserComponent_ng_template_40_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 39);
    \u0275\u0275listener("input", function AppUserComponent_ng_template_40_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const appUserTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(appUserTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const appUserTag_r3 = \u0275\u0275reference(39);
    \u0275\u0275advance();
    \u0275\u0275property("value", appUserTag_r3.filters[col_r6.field] == null ? null : appUserTag_r3.filters[col_r6.field].value);
  }
}
function AppUserComponent_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 33);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, AppUserComponent_ng_template_40_th_3_Template, 3, 3, "th", 35);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 33);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, AppUserComponent_ng_template_40_th_7_Template, 2, 1, "th", 36);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r7 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r7);
    \u0275\u0275advance(3);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r7);
  }
}
function AppUserComponent_ng_template_41_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    const rowData_r11 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r11[col_r10.field], " ");
  }
}
function AppUserComponent_ng_template_41_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r12 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 46);
    \u0275\u0275listener("click", function AppUserComponent_ng_template_41_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r12);
      const rowData_r11 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.openCrud("Edit", rowData_r11));
    });
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r11 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(rowData_r11[col_r10.field]);
  }
}
function AppUserComponent_ng_template_41_td_3_ng_template_2_span_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span");
    \u0275\u0275text(1);
    \u0275\u0275pipe(2, "date");
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r11 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(\u0275\u0275pipeBind2(2, 1, rowData_r11[col_r10.field], "dd-MM-yyyy HH:mm"));
  }
}
function AppUserComponent_ng_template_41_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, AppUserComponent_ng_template_41_td_3_ng_template_2_span_0_Template, 2, 1, "span", 44)(1, AppUserComponent_ng_template_41_td_3_ng_template_2_span_1_Template, 3, 4, "span", 45);
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r10.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format == "date");
  }
}
function AppUserComponent_ng_template_41_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, AppUserComponent_ng_template_41_td_3_ng_container_1_Template, 2, 1, "ng-container", 43)(2, AppUserComponent_ng_template_41_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 2, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = ctx.$implicit;
    const dateTemplate_r13 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r10.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format !== "date" && col_r10.format !== "hyperLink")("ngIfElse", dateTemplate_r13);
  }
}
function AppUserComponent_ng_template_41_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr")(1, "td", 40)(2, "p-checkbox", 41);
    \u0275\u0275listener("onChange", function AppUserComponent_ng_template_41_Template_p_checkbox_onChange_2_listener() {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.onChange());
    });
    \u0275\u0275twoWayListener("ngModelChange", function AppUserComponent_ng_template_41_Template_p_checkbox_ngModelChange_2_listener($event) {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r8.selectedAppUser, $event) || (ctx_r8.selectedAppUser = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275template(3, AppUserComponent_ng_template_41_td_3_Template, 4, 4, "td", 42);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r11 = ctx.$implicit;
    const columns_r14 = ctx.columns;
    const ctx_r8 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r8.selectedAppUser[0] === rowData_r11);
    \u0275\u0275advance(2);
    \u0275\u0275twoWayProperty("ngModel", ctx_r8.selectedAppUser);
    \u0275\u0275property("value", rowData_r11);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r14);
  }
}
function AppUserComponent_ng_template_42_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 47);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
var _AppUserComponent = class _AppUserComponent {
  constructor(messageService, cs, router, path, service, dialog, datePipe, auth, fb, spin) {
    this.messageService = messageService;
    this.cs = cs;
    this.router = router;
    this.path = path;
    this.service = service;
    this.dialog = dialog;
    this.datePipe = datePipe;
    this.auth = auth;
    this.fb = fb;
    this.spin = spin;
    this.appUserTable = [];
    this.selectedAppUser = [];
    this.cols = [];
    this.target = [];
    this.searchform = this.fb.group({
      appUserId: [],
      statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      appUserId: "App User",
      statusId: "Status"
    };
    this.languageDropdown = [];
    this.companyDropdown = [];
    this.appUserDropdown = [];
    this.statusDropdown = [];
  }
  ngOnInit() {
    const dataToSend = ["Setup", "App User"];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "companyName", header: "Company" },
      { field: "appUserId", header: "App User ID", format: "hyperLink" },
      { field: "appUserName", header: "App User Name" },
      { field: "appUserType", header: "App User Type" },
      { field: "remark", header: "Remarks" },
      { field: "statusDescription", header: "Status" },
      { field: "createdBy", header: "Created By" },
      { field: "createdOn", header: "Created On", format: "date" }
    ];
    this.target = [
      { field: "languageId", header: "Language ID" },
      { field: "languageDescription", header: "Language" },
      { field: "companyId", header: "Company ID" },
      { field: "statusId", header: "Status ID" },
      { field: "password", header: "Password" },
      { field: "mobileNumber", header: "Mobile Number" },
      { field: "vehicleRegNumber", header: "Vehicle Reg No" },
      { field: "routeId", header: "Route ID" },
      { field: "assignedHubCode", header: "Assigned Hub Code" },
      { field: "referenceField1", header: "Reference Field 1" },
      { field: "referenceField2", header: "Reference Field 2" },
      { field: "referenceField3", header: "Reference Field 3" },
      { field: "referenceField4", header: "Reference Field 4" },
      { field: "referenceField5", header: "Reference Field 5" },
      { field: "referenceField6", header: "Reference Field 6" },
      { field: "referenceField7", header: "Reference Field 7" },
      { field: "referenceField8", header: "Reference Field 8" },
      { field: "referenceField9", header: "Reference Field 9" },
      { field: "referenceField10", header: "Reference Field 10" },
      { field: "updatedBy", header: "Updated By" },
      { field: "updatedOn", header: "Updated On", format: "date" }
    ];
  }
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.service.search(obj).subscribe({
        next: (res) => {
          console.log(res);
          this.appUserTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }, 1e3);
  }
  onChange() {
    const choosen = this.selectedAppUser[this.selectedAppUser.length - 1];
    this.selectedAppUser.length = 0;
    this.selectedAppUser.push(choosen);
  }
  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" },
      data: { target: this.cols, source: this.target }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedAppUser[0]);
      }
    });
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedAppUser = linedata;
    }
    if (this.selectedAppUser.length === 0 && type != "New") {
      this.messageService.add({
        severity: "warn",
        summary: "Warning",
        key: "br",
        detail: "Kindly select any row"
      });
    } else {
      let paramdata = this.cs.encrypt({
        line: linedata == null ? this.selectedAppUser[0] : linedata,
        pageflow: type
      });
      this.router.navigate(["/main/idMaster/appUser-new/" + paramdata]);
    }
  }
  deleteDialog() {
    if (this.selectedAppUser.length === 0) {
      this.messageService.add({
        severity: "warn",
        summary: "Warning",
        key: "br",
        detail: "Kindly select any row"
      });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: "60%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "30%" },
      data: {
        line: this.selectedAppUser,
        module: "App User",
        body: "This action cannot be undone. All values associated with this field will be lost."
      }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedAppUser[0]);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    this.service.Delete(lines.appUserId).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: "success",
          summary: "Deleted",
          key: "br",
          detail: lines.appUserId + " Deleted successfully"
        });
        this.spin.hide();
        this.initialCall();
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    });
  }
  downloadExcel() {
    const exportData = this.appUserTable.map((item) => {
      const exportItem = {};
      this.cols.forEach((col) => {
        if (col.format == "date") {
          console.log(3);
          exportItem[col.header] = this.datePipe.transform(item[col.field], "dd-MM-yyyy");
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, "App User");
  }
  getSearchDropdown() {
    this.appUserTable.forEach((res) => {
      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, "value");
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, "value");
      }
      if (res.appUserId != null) {
        this.appUserDropdown.push({ value: res.appUserId, label: res.appUserName });
        this.appUserDropdown = this.cs.removeDuplicatesFromArrayList(this.appUserDropdown, "value");
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusDescription });
        this.statusDropdown = this.cs.removeDuplicatesFromArrayList(this.statusDropdown, "value");
      }
    });
  }
  closeOverLay() {
    this.overlayPanel.hide();
  }
  search() {
    this.fieldsWithValue = null;
    const formValues = this.searchform.value;
    this.fieldsWithValue = Object.keys(formValues).filter((key) => formValues[key] !== null && formValues[key] !== void 0 && key !== "companyId" && key !== "languageId").map((key) => this.fieldDisplayNames[key] || key);
    this.spin.show();
    this.service.search(this.searchform.getRawValue()).subscribe({
      next: (res) => {
        this.appUserTable = res;
        this.spin.hide();
        this.overlayPanel.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  reset() {
    this.searchform.reset();
    this.searchform = this.fb.group({
      appUserId: [],
      statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.search();
  }
  chipClear(value) {
    const formControlKey = Object.keys(this.fieldDisplayNames).find((key) => this.fieldDisplayNames[key] === value.value);
    if (formControlKey) {
      this.searchform.get(formControlKey)?.reset();
      this.search();
    }
  }
};
_AppUserComponent.\u0275fac = function AppUserComponent_Factory(t) {
  return new (t || _AppUserComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(AppUserService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NgxSpinnerService));
};
_AppUserComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _AppUserComponent, selectors: [["app-app-user"]], viewQuery: function AppUserComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c03, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 43, vars: 30, consts: [["appUser", ""], ["appUserTag", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "appUserId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "single", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "columns", "value", "scrollable", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "onChange", "ngModelChange", "ngModel", "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"]], template: function AppUserComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 3)(1, "div", 4)(2, "p", 5);
    \u0275\u0275text(3, "App User ");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 4)(5, "img", 6);
    \u0275\u0275listener("click", function AppUserComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 7);
    \u0275\u0275listener("click", function AppUserComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 8);
    \u0275\u0275listener("click", function AppUserComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "img", 9);
    \u0275\u0275listener("click", function AppUserComponent_Template_img_click_8_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.customTable());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "button", 10);
    \u0275\u0275listener("click", function AppUserComponent_Template_button_click_9_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("New"));
    });
    \u0275\u0275element(10, "i", 11);
    \u0275\u0275text(11, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 12)(13, "p-iconField", 13)(14, "p-inputIcon", 14);
    \u0275\u0275listener("click", function AppUserComponent_Template_p_inputIcon_click_14_listener($event) {
      \u0275\u0275restoreView(_r1);
      const appUser_r2 = \u0275\u0275reference(17);
      return \u0275\u0275resetView(appUser_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "input", 15);
    \u0275\u0275listener("input", function AppUserComponent_Template_input_input_15_listener($event) {
      \u0275\u0275restoreView(_r1);
      const appUserTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(appUserTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(16, "p-overlayPanel", 16, 0)(18, "form", 17)(19, "div", 18)(20, "div", 19)(21, "p", 20);
    \u0275\u0275text(22, "App User ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(23, "p-multiSelect", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(24, "div", 19)(25, "p", 20);
    \u0275\u0275text(26, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275element(27, "p-multiSelect", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 23)(29, "div")(30, "img", 24);
    \u0275\u0275listener("click", function AppUserComponent_Template_img_click_30_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(31, "div")(32, "button", 25);
    \u0275\u0275listener("click", function AppUserComponent_Template_button_click_32_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(33, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(34, "button", 26);
    \u0275\u0275listener("click", function AppUserComponent_Template_button_click_34_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(35, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(36, "p-chips", 27);
    \u0275\u0275listener("onRemove", function AppUserComponent_Template_p_chips_onRemove_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function AppUserComponent_Template_p_chips_ngModelChange_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(37, "div", 28)(38, "p-table", 29, 1);
    \u0275\u0275template(40, AppUserComponent_ng_template_40_Template, 8, 4, "ng-template", 30)(41, AppUserComponent_ng_template_41_Template, 4, 5, "ng-template", 31)(42, AppUserComponent_ng_template_42_Template, 4, 1, "ng-template", 32);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(16);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(24, _c12));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(25, _c22));
    \u0275\u0275property("showClear", true)("options", ctx.appUserDropdown)("panelStyle", \u0275\u0275pureFunction0(26, _c22));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c22));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(28, _c22));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.appUserTable)("scrollable", true)("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(29, _c32));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, SortIcon, TableHeaderCheckbox, InputIcon, IconField, InputText, Checkbox, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=app-user.component.css.map */"] });
var AppUserComponent = _AppUserComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(AppUserComponent, { className: "AppUserComponent", filePath: "src\\app\\main\\id-masters\\app-user\\app-user.component.ts", lineNumber: 22 });
})();

// src/app/main/id-masters/app-user/app-user-new/app-user-new.component.ts
var _c04 = () => ({ "width": "100%" });
function AppUserNewComponent_ng_template_11_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 15);
    \u0275\u0275text(1, "1");
    \u0275\u0275elementEnd();
  }
}
function AppUserNewComponent_ng_template_11_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 16);
  }
}
function AppUserNewComponent_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 10);
    \u0275\u0275listener("click", function AppUserNewComponent_ng_template_11_Template_div_click_0_listener() {
      const onClick_r2 = \u0275\u0275restoreView(_r1).onClick;
      return \u0275\u0275resetView(onClick_r2.emit());
    });
    \u0275\u0275elementStart(1, "div", 11);
    \u0275\u0275template(2, AppUserNewComponent_ng_template_11_p_2_Template, 2, 0, "p", 12)(3, AppUserNewComponent_ng_template_11_img_3_Template, 1, 0, "img", 13);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 14);
    \u0275\u0275text(6, "General");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.active == 0);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.active != 0);
  }
}
function AppUserNewComponent_ng_template_12_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 38);
  }
}
function AppUserNewComponent_ng_template_12_mat_error_6_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 38);
  }
}
function AppUserNewComponent_ng_template_12_mat_error_12_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_mat_error_17_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_mat_error_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_mat_error_27_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_mat_error_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_mat_error_37_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_ng_template_42_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 38);
  }
}
function AppUserNewComponent_ng_template_12_mat_error_43_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_ng_template_48_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 38);
  }
}
function AppUserNewComponent_ng_template_12_mat_error_49_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_ng_template_54_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 38);
  }
}
function AppUserNewComponent_ng_template_12_mat_error_55_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 39)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function AppUserNewComponent_ng_template_12_ng_template_60_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 38);
  }
}
function AppUserNewComponent_ng_template_12_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 17)(1, "div", 18)(2, "p", 19);
    \u0275\u0275text(3, "Language");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "p-dropdown", 20);
    \u0275\u0275template(5, AppUserNewComponent_ng_template_12_ng_template_5_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(6, AppUserNewComponent_ng_template_12_mat_error_6_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "div", 18)(8, "p", 19);
    \u0275\u0275text(9, "Company");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "p-dropdown", 23);
    \u0275\u0275template(11, AppUserNewComponent_ng_template_12_ng_template_11_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(12, AppUserNewComponent_ng_template_12_mat_error_12_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 18)(14, "p", 24);
    \u0275\u0275text(15, "App User ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(16, "input", 25);
    \u0275\u0275template(17, AppUserNewComponent_ng_template_12_mat_error_17_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(18, "div", 18)(19, "p", 24);
    \u0275\u0275text(20, "App User Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(21, "input", 26);
    \u0275\u0275template(22, AppUserNewComponent_ng_template_12_mat_error_22_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(23, "div", 18)(24, "p", 19);
    \u0275\u0275text(25, "App User Type");
    \u0275\u0275elementEnd();
    \u0275\u0275element(26, "input", 27);
    \u0275\u0275template(27, AppUserNewComponent_ng_template_12_mat_error_27_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(28, "div", 18)(29, "p", 24);
    \u0275\u0275text(30, "Password");
    \u0275\u0275elementEnd();
    \u0275\u0275element(31, "input", 28);
    \u0275\u0275template(32, AppUserNewComponent_ng_template_12_mat_error_32_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(33, "div", 18)(34, "p", 19);
    \u0275\u0275text(35, "Mobile Number");
    \u0275\u0275elementEnd();
    \u0275\u0275element(36, "input", 29);
    \u0275\u0275template(37, AppUserNewComponent_ng_template_12_mat_error_37_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(38, "div", 18)(39, "p", 19);
    \u0275\u0275text(40, "Vehicle Reg No");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "p-dropdown", 30);
    \u0275\u0275template(42, AppUserNewComponent_ng_template_12_ng_template_42_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(43, AppUserNewComponent_ng_template_12_mat_error_43_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(44, "div", 18)(45, "p", 19);
    \u0275\u0275text(46, "Route ID");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(47, "p-dropdown", 31);
    \u0275\u0275template(48, AppUserNewComponent_ng_template_12_ng_template_48_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(49, AppUserNewComponent_ng_template_12_mat_error_49_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(50, "div", 18)(51, "p", 19);
    \u0275\u0275text(52, "Assigned Hub Code");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(53, "p-dropdown", 32);
    \u0275\u0275template(54, AppUserNewComponent_ng_template_12_ng_template_54_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(55, AppUserNewComponent_ng_template_12_mat_error_55_Template, 3, 1, "mat-error", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(56, "div", 18)(57, "p", 19);
    \u0275\u0275text(58, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(59, "p-dropdown", 33);
    \u0275\u0275template(60, AppUserNewComponent_ng_template_12_ng_template_60_Template, 1, 0, "ng-template", 21);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(61, "div", 18)(62, "p", 19);
    \u0275\u0275text(63, "Remark");
    \u0275\u0275elementEnd();
    \u0275\u0275element(64, "input", 34);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(65, "div", 35)(66, "button", 36);
    \u0275\u0275text(67, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(68, "button", 37);
    \u0275\u0275listener("click", function AppUserNewComponent_ng_template_12_Template_button_click_68_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(69);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    let tmp_14_0;
    let tmp_16_0;
    let tmp_18_0;
    let tmp_20_0;
    let tmp_22_0;
    let tmp_29_0;
    let tmp_36_0;
    let tmp_43_0;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(53, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.languageIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(54, _c04));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("languageId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(55, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.companyIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(56, _c04));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("companyId"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_14_0 = ctx_r2.form.get("appUserId")) == null ? null : tmp_14_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("appUserId"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_16_0 = ctx_r2.form.get("appUserName")) == null ? null : tmp_16_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("appUserName"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_18_0 = ctx_r2.form.get("appUserType")) == null ? null : tmp_18_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("appUserType"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_20_0 = ctx_r2.form.get("password")) == null ? null : tmp_20_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("password"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_22_0 = ctx_r2.form.get("mobileNumber")) == null ? null : tmp_22_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("mobileNumber"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(57, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.vehcileRegNoList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(58, _c04))("ngClass", ((tmp_29_0 = ctx_r2.form.get("vehicleRegNumber")) == null ? null : tmp_29_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("vehicleRegNumber"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(59, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.routeIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(60, _c04))("ngClass", ((tmp_36_0 = ctx_r2.form.get("routeId")) == null ? null : tmp_36_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("routeId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(61, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.hubCodeList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(62, _c04))("ngClass", ((tmp_43_0 = ctx_r2.form.get("assignedHubCode")) == null ? null : tmp_43_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("assignedHubCode"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(63, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.status);
    \u0275\u0275advance(10);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function AppUserNewComponent_p_stepperPanel_13_ng_template_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 10);
    \u0275\u0275listener("click", function AppUserNewComponent_p_stepperPanel_13_ng_template_1_Template_div_click_0_listener() {
      const onClick_r6 = \u0275\u0275restoreView(_r5).onClick;
      return \u0275\u0275resetView(onClick_r6.emit());
    });
    \u0275\u0275elementStart(1, "div", 11)(2, "p", 40);
    \u0275\u0275text(3, " 2");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div")(5, "p", 41);
    \u0275\u0275text(6, " Admin");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275property("ngClass", ctx_r2.active == 2 ? "borderCircle" : "disabled text-muted");
    \u0275\u0275advance(3);
    \u0275\u0275property("ngClass", ctx_r2.active == 2 ? "textBlack f600" : "text-muted");
  }
}
function AppUserNewComponent_p_stepperPanel_13_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 42)(1, "div", 18)(2, "p", 19);
    \u0275\u0275text(3, "Created By");
    \u0275\u0275elementEnd();
    \u0275\u0275element(4, "input", 43);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "div", 18)(6, "p", 19);
    \u0275\u0275text(7, "Created On");
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "input", 44);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "div", 18)(10, "p", 19);
    \u0275\u0275text(11, "Updated By");
    \u0275\u0275elementEnd();
    \u0275\u0275element(12, "input", 45);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 18)(14, "p", 19);
    \u0275\u0275text(15, "Updated On");
    \u0275\u0275elementEnd();
    \u0275\u0275element(16, "input", 46);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "div", 35)(18, "button", 47);
    \u0275\u0275listener("click", function AppUserNewComponent_p_stepperPanel_13_ng_template_2_Template_button_click_18_listener() {
      const prevCallback_r8 = \u0275\u0275restoreView(_r7).prevCallback;
      return \u0275\u0275resetView(prevCallback_r8.emit());
    });
    \u0275\u0275text(19, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "button", 37);
    \u0275\u0275listener("click", function AppUserNewComponent_p_stepperPanel_13_ng_template_2_Template_button_click_20_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext(2);
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(21);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(21);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function AppUserNewComponent_p_stepperPanel_13_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p-stepperPanel");
    \u0275\u0275template(1, AppUserNewComponent_p_stepperPanel_13_ng_template_1_Template, 7, 2, "ng-template", 7)(2, AppUserNewComponent_p_stepperPanel_13_ng_template_2_Template, 22, 1, "ng-template", 8);
    \u0275\u0275elementEnd();
  }
}
var _AppUserNewComponent = class _AppUserNewComponent {
  constructor(cs, spin, route, router, path, fb, service, numberRangeService, messageService, cas, auth) {
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.path = path;
    this.fb = fb;
    this.service = service;
    this.numberRangeService = numberRangeService;
    this.messageService = messageService;
    this.cas = cas;
    this.auth = auth;
    this.active = 0;
    this.status = [];
    this.form = this.fb.group({
      languageId: [this.auth.languageId],
      languageDescription: [],
      companyId: [this.auth.companyId],
      companyName: [],
      appUserId: [, Validators.required],
      appUserName: [, Validators.required],
      appUserType: [],
      vehicleRegNumber: [],
      password: [, Validators.required],
      routeId: [],
      assignedHubCode: [],
      statusId: ["16"],
      statusDescription: [],
      remark: [],
      mobileNumber: [],
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
      createdOn: [""],
      createdBy: [],
      updatedOn: [""],
      updatedBy: []
    });
    this.submitted = false;
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.languageIdList = [];
    this.companyIdList = [];
    this.routeIdList = [];
    this.hubCodeList = [];
    this.vehcileRegNoList = [];
    this.status = [
      { value: "17", label: "Inactive" },
      { value: "16", label: "Active" }
    ];
  }
  errorHandling(control, error = "required") {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError("required")) {
      return " Field should not be blank";
    }
    return this.email.hasError("email") ? "Not a valid email" : "";
  }
  ngOnInit() {
    let code = this.route.snapshot.params["code"];
    this.pageToken = this.cs.decrypt(code);
    const dataToSend = ["Setup", "App User", this.pageToken.pageflow];
    this.path.setData(dataToSend);
    this.dropdownlist();
    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();
    if (this.pageToken.pageflow != "New") {
      this.fill(this.pageToken.line);
      this.form.controls.appUserId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.route.url,
      this.cas.dropdownlist.setup.hub.url,
      this.cas.dropdownlist.setup.vehicle.url
    ]).subscribe({
      next: (results) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.routeIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.route.key);
        this.hubCodeList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.hub.key);
        this.vehcileRegNoList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.vehicle.key);
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  fill(line) {
    this.form.patchValue(line);
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
  }
  save() {
    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        key: "br",
        detail: "Please fill required fields to continue"
      });
      return;
    }
    if (this.pageToken.pageflow != "New") {
      this.spin.show();
      this.service.Update(this.form.getRawValue()).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Updated",
            key: "br",
            detail: res.appUserId + " has been updated successfully"
          });
          this.router.navigate(["/main/idMaster/appUser"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      this.spin.show();
      this.service.Create(this.form.getRawValue()).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({
              severity: "success",
              summary: "Created",
              key: "br",
              detail: res.appUserId + " has been created successfully"
            });
            this.router.navigate(["/main/idMaster/appUser"]);
            this.spin.hide();
          }
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
};
_AppUserNewComponent.\u0275fac = function AppUserNewComponent_Factory(t) {
  return new (t || _AppUserNewComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(AppUserService), \u0275\u0275directiveInject(NumberrangeService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService));
};
_AppUserNewComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _AppUserNewComponent, selectors: [["app-app-user-new"]], decls: 14, vars: 4, consts: [[1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", 1, "invisible", "buttom1", "bgBlack", "text-white", "ml-2"], [1, "w-75", "mt-4", "mx-auto"], [3, "formGroup"], [3, "activeStepChange", "activeStep"], ["pTemplate", "header"], ["pTemplate", "content"], [4, "ngIf"], [1, "d-flex", "flex-column", "align-items-center", 3, "click"], [1, "d-flex", "justify-content-center", "align-items-center"], ["class", "circle borderCircle mb-0", 4, "ngIf"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", "", 4, "ngIf"], [1, "mb-0", "mt-2", "f600", "textBlack"], [1, "circle", "borderCircle", "mb-0"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", ""], [1, "row", "scrollNew"], [1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["formControlName", "languageId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["class", "text-danger", 4, "ngIf"], ["formControlName", "companyId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["maxlength", "100", "pInputText", "", "appTrim", "", "formControlName", "appUserId", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["maxlength", "100", "pInputText", "", "appTrim", "", "formControlName", "appUserName", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["maxlength", "100", "pInputText", "", "appTrim", "", "formControlName", "appUserType", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["type", "password", "maxlength", "100", "pInputText", "", "appTrim", "", "formControlName", "password", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["maxlength", "100", "pInputText", "", "appTrim", "", "formControlName", "mobileNumber", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["placeholder", "Select", "filter", "true", "formControlName", "vehicleRegNumber", "appendTo", "body", "placeholder", "Select", "filter", "true", 3, "showClear", "options", "disabled", "panelStyle", "ngClass"], ["placeholder", "Select", "filter", "true", "formControlName", "routeId", "appendTo", "body", "placeholder", "Select", "filter", "true", 3, "showClear", "options", "disabled", "panelStyle", "ngClass"], ["placeholder", "Select", "filter", "true", "formControlName", "assignedHubCode", "appendTo", "body", "placeholder", "Select", "filter", "true", 3, "showClear", "options", "disabled", "panelStyle", "ngClass"], ["appendTo", "body", "formControlName", "statusId", "placeholder", "Select", "filter", "true", 3, "showClear", "options"], ["maxlength", "2000", "formControlName", "remark", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], [1, "d-flex", "mt-1", "justify-content-end", 2, "position", "absolute", "right", "3.5%", "bottom", "7%"], ["routerLink", "/main/idMaster/appUser", 1, "buttom1", "textBlack", "mx-1"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], [1, "text-danger"], [1, "circle", "mb-0", 3, "ngClass"], [1, "mb-0", "mt-2", "f600", 3, "ngClass"], [1, "row"], ["type", "text", "formControlName", "createdBy", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "createdOn", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "updatedBy", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "updatedOn", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], [1, "buttom1", "textBlack", "mx-1", 3, "click"]], template: function AppUserNewComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1)(2, "p", 2);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 1)(5, "button", 3);
    \u0275\u0275text(6, "Transfer");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(7, "div", 4)(8, "form", 5)(9, "p-stepper", 6);
    \u0275\u0275twoWayListener("activeStepChange", function AppUserNewComponent_Template_p_stepper_activeStepChange_9_listener($event) {
      \u0275\u0275twoWayBindingSet(ctx.active, $event) || (ctx.active = $event);
      return $event;
    });
    \u0275\u0275elementStart(10, "p-stepperPanel");
    \u0275\u0275template(11, AppUserNewComponent_ng_template_11_Template, 7, 2, "ng-template", 7)(12, AppUserNewComponent_ng_template_12_Template, 70, 64, "ng-template", 8);
    \u0275\u0275elementEnd();
    \u0275\u0275template(13, AppUserNewComponent_p_stepperPanel_13_Template, 3, 0, "p-stepperPanel", 9);
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1("App User- ", ctx.pageToken.pageflow, "");
    \u0275\u0275advance(5);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance();
    \u0275\u0275twoWayProperty("activeStep", ctx.active);
    \u0275\u0275advance(4);
    \u0275\u0275property("ngIf", ctx.pageToken.pageflow != "New");
  }
}, dependencies: [NgClass, NgIf, RouterLink, PrimeTemplate, Dropdown, InputText, Stepper, StepperPanel, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, MatError, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 2.5rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 24rem);\n  overflow-y: scroll;\n}\n/*# sourceMappingURL=app-user-new.component.css.map */"] });
var AppUserNewComponent = _AppUserNewComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(AppUserNewComponent, { className: "AppUserNewComponent", filePath: "src\\app\\main\\id-masters\\app-user\\app-user-new\\app-user-new.component.ts", lineNumber: 18 });
})();

// src/app/main/id-masters/sub-product/sub-product.service.ts
var _SubProductService = class _SubProductService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(subProductId, subProductValue) {
    return this.http.get("/overc-idmaster-service/subProduct/" + subProductId + "?subProductValue=" + subProductValue + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/subProduct", obj);
  }
  CreateBulk(obj) {
    return this.http.post("/overc-idmaster-service/subProduct/create/list", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/subProduct/" + obj.subProductId + "?subProductValue=" + obj.subProductValue + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId, obj);
  }
  UpdateBulk(obj) {
    return this.http.patch("/overc-idmaster-service/subProduct/update/list", obj);
  }
  Delete(subProductId, subProductValue) {
    return this.http.delete("/overc-idmaster-service/subProduct/" + subProductId + "?subProductValue=" + subProductValue + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  DeleteBulk(obj) {
    return this.http.post("/overc-idmaster-service/subProduct/delete/list", obj);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/subProduct/find", obj);
  }
};
_SubProductService.\u0275fac = function SubProductService_Factory(t) {
  return new (t || _SubProductService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_SubProductService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _SubProductService, factory: _SubProductService.\u0275fac, providedIn: "root" });
var SubProductService = _SubProductService;

// src/app/main/id-masters/city/city.service.ts
var _CityService = class _CityService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(cityId) {
    return this.http.get("/overc-idmaster-service/city/" + cityId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/city", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/city/" + obj.cityId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&countryId=" + obj.countryId + "&provinceId=" + obj.provinceId + "&districtId=" + obj.districtId, obj);
  }
  Delete(obj) {
    return this.http.delete("/overc-idmaster-service/city/" + obj.cityId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&countryId=" + obj.countryId + "&provinceId=" + obj.provinceId + "&districtId=" + obj.districtId);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/city/find", obj);
  }
};
_CityService.\u0275fac = function CityService_Factory(t) {
  return new (t || _CityService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_CityService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _CityService, factory: _CityService.\u0275fac, providedIn: "root" });
var CityService = _CityService;

export {
  SubProductService,
  CityService,
  NumberrangeNewComponent,
  NumberrangeComponent,
  AppUserService,
  AppUserComponent,
  AppUserNewComponent
};
//# sourceMappingURL=chunk-WGFOF6TE.js.map
