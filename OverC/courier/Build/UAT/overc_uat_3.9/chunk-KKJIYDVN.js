import {
  UomService
} from "./chunk-O7TYOAD3.js";
import {
  ProductService
} from "./chunk-QNQSICHN.js";
import {
  ConsignmentUpdatebulkComponent
} from "./chunk-J2VHCBGS.js";
import {
  ConsignorService,
  CustomerService
} from "./chunk-ROOPJGSF.js";
import {
  ConsignmentLabelComponent
} from "./chunk-7FQSG277.js";
import "./chunk-5BHKZR5U.js";
import {
  CostingSheetService,
  InvoicepdfComponent
} from "./chunk-LMO3BHKE.js";
import {
  DeleteComponent
} from "./chunk-5SKNGDL5.js";
import {
  ConsignmentService
} from "./chunk-S73KOQBB.js";
import {
  CommonAPIService
} from "./chunk-CC4KTCN6.js";
import {
  PathNameService
} from "./chunk-HCEORX54.js";
import {
  CustomTableComponent
} from "./chunk-ZBBD2MUA.js";
import {
  AuthService,
  ButtonDirective,
  Calendar,
  Chips,
  CommonServiceService,
  DefaultValueAccessor,
  Dropdown,
  FormBuilder,
  FormControl,
  FormControlName,
  FormGroup,
  FormGroupDirective,
  FormGroupName,
  FrozenColumn,
  IconField,
  InputGroup,
  InputIcon,
  InputText,
  KeyFilter,
  MAT_DIALOG_DATA,
  MatAccordion,
  MatDialog,
  MatDialogClose,
  MatDialogRef,
  MatError,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle,
  MatInput,
  MatMenu,
  MatMenuContent,
  MatMenuItem,
  MaxLengthValidator,
  MessageService,
  MultiSelect,
  NgControlStatus,
  NgControlStatusGroup,
  NgModel,
  NgxSpinnerService,
  OverlayPanel,
  PrimeTemplate,
  SharedModule,
  SortIcon,
  SortableColumn,
  TabPanel,
  TabView,
  Table,
  TableCheckbox,
  TableHeaderCheckbox,
  TrimDirective,
  Validators,
  animate,
  state,
  style,
  transition,
  trigger,
  ɵNgNoValidate
} from "./chunk-XFYC4BWK.js";
import {
  ActivatedRoute,
  CommonModule,
  DatePipe,
  DomSanitizer,
  ElementRef,
  NgClass,
  NgForOf,
  NgIf,
  Router,
  RouterLink,
  RouterModule,
  __spreadProps,
  __spreadValues,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵattribute,
  ɵɵdefineComponent,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementContainerEnd,
  ɵɵelementContainerStart,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵgetCurrentView,
  ɵɵlistener,
  ɵɵloadQuery,
  ɵɵnextContext,
  ɵɵpipe,
  ɵɵpipeBind2,
  ɵɵproperty,
  ɵɵpureFunction0,
  ɵɵpureFunction1,
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
} from "./chunk-Z5YEPTLN.js";

// src/app/main/operation/consignment/download-template/download-template.component.ts
var _c0 = () => ({ "width": "100%" });
function DownloadTemplateComponent_ng_template_12_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 19);
  }
}
function DownloadTemplateComponent_div_13_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 19);
  }
}
function DownloadTemplateComponent_div_13_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 20)(1, "p", 10);
    \u0275\u0275text(2, "Partner Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "p-dropdown", 21);
    \u0275\u0275listener("onChange", function DownloadTemplateComponent_div_13_Template_p_dropdown_onChange_3_listener() {
      \u0275\u0275restoreView(_r1);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.partnerTypeChanged());
    });
    \u0275\u0275template(4, DownloadTemplateComponent_div_13_ng_template_4_Template, 1, 0, "ng-template", 12);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    let tmp_4_0;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(6, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r1.partnerType)("ngClass", ((tmp_4_0 = ctx_r1.form.get("partnerType")) == null ? null : tmp_4_0.invalid) && ctx_r1.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(7, _c0));
  }
}
function DownloadTemplateComponent_div_14_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 19);
  }
}
function DownloadTemplateComponent_div_14_Template(rf, ctx) {
  if (rf & 1) {
    const _r3 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 22)(1, "p", 10);
    \u0275\u0275text(2, "Partner");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "p-dropdown", 23, 0);
    \u0275\u0275listener("onChange", function DownloadTemplateComponent_div_14_Template_p_dropdown_onChange_3_listener() {
      \u0275\u0275restoreView(_r3);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.partnerChanged());
    });
    \u0275\u0275template(5, DownloadTemplateComponent_div_14_ng_template_5_Template, 1, 0, "ng-template", 12);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    let tmp_5_0;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(6, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r1.partnerNameList)("ngClass", ((tmp_5_0 = ctx_r1.form.get("partnerId")) == null ? null : tmp_5_0.invalid) && ctx_r1.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(7, _c0));
  }
}
function DownloadTemplateComponent_div_15_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 19);
  }
}
function DownloadTemplateComponent_div_15_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 24)(1, "p", 10);
    \u0275\u0275text(2, "Product");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "p-dropdown", 25, 1);
    \u0275\u0275listener("onChange", function DownloadTemplateComponent_div_15_Template_p_dropdown_onChange_3_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.productChanged());
    });
    \u0275\u0275template(5, DownloadTemplateComponent_div_15_ng_template_5_Template, 1, 0, "ng-template", 12);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    let tmp_5_0;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(6, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r1.productNameList)("ngClass", ((tmp_5_0 = ctx_r1.form.get("productName")) == null ? null : tmp_5_0.invalid) && ctx_r1.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(7, _c0));
  }
}
var _DownloadTemplateComponent = class _DownloadTemplateComponent {
  constructor(dialogRef, data, cs, spin, route, router, path, fb, service, messageService, cas, auth, customerService, consignorService, productService) {
    this.dialogRef = dialogRef;
    this.data = data;
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
    this.customerService = customerService;
    this.consignorService = consignorService;
    this.productService = productService;
    this.partnerType = [];
    this.creationType = [];
    this.form = this.fb.group({
      partnerType: [""],
      partnerId: [""],
      creationType: ["Single", Validators.required],
      consignorId: [],
      customerId: [],
      productId: [""],
      productName: [""],
      fileName: [""]
    });
    this.partnerNameList = [];
    this.productNameList = [];
    this.submitted = false;
    this.partnerType = [
      { value: "customer", label: "Customer" },
      { value: "consignor", label: "Consignor" }
    ];
    this.creationType = [
      { value: "Single", label: "Single" },
      { value: "Bulk", label: "Bulk" }
    ];
  }
  ngOnInit() {
  }
  partnerTypeChanged() {
    if (this.form.controls.partnerType.value == "customer") {
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.partnerNameList = [];
      this.productNameList = [];
      this.spin.show();
      this.customerService.search(obj).subscribe({
        next: (result) => {
          this.partnerNameList = this.cas.foreachlist(result, { key: "customerId", value: "customerName", value2: "productName" });
          this.partnerNameList = this.cs.removeDuplicatesFromArrayList(this.partnerNameList, ["value"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
    if (this.form.controls.partnerType.value == "consignor") {
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.partnerNameList = [];
      this.productNameList = [];
      this.spin.show();
      this.consignorService.search(obj).subscribe({
        next: (result) => {
          this.partnerNameList = this.cas.foreachlist(result, { key: "consignorId", value: "consignorName", value2: "productName" });
          this.partnerNameList = this.cs.removeDuplicatesFromArrayList(this.partnerNameList, ["value"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
  partnerChanged() {
    if (this.form.controls.partnerType.value == "customer") {
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.productNameList = [];
      this.spin.show();
      this.customerService.search(obj).subscribe({
        next: (result) => {
          this.productNameList = this.cas.foreachlistWithoutKey(result, { key: "productId", value: "productName" });
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
    if (this.form.controls.partnerType.value == "consignor") {
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.productNameList = [];
      this.spin.show();
      this.consignorService.search(obj).subscribe({
        next: (result) => {
          this.productNameList = this.cas.foreachlistWithoutKey(result, { key: "productId", value: "productName" });
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
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
    this.dialogRef.close(this.form.getRawValue());
  }
  productChanged() {
    const selectedPartner = this.partnerNameList.find((value) => value.value === this.form.controls.productId.value);
    this.form.controls.productName.patchValue(selectedPartner.value2);
    if (selectedPartner.value2 == "1 - INTERNATIONALINBOUND") {
      this.form.controls.fileName.patchValue("1_-_INTERNATIONALINBOUND.xlsx");
    }
    if (selectedPartner.value2 == "2 - INTERNATIONALOUTBOUND") {
      this.form.controls.fileName.patchValue("2_-_INTERNATIONALOUTBOUND.xlsx");
    }
    if (selectedPartner.value2 == "3 - DOMESTIC") {
      this.form.controls.fileName.patchValue("3_-_DOMESTIC.xlsx");
    }
  }
};
_DownloadTemplateComponent.\u0275fac = function DownloadTemplateComponent_Factory(t) {
  return new (t || _DownloadTemplateComponent)(\u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(CustomerService), \u0275\u0275directiveInject(ConsignorService), \u0275\u0275directiveInject(ProductService));
};
_DownloadTemplateComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _DownloadTemplateComponent, selectors: [["app-download-template"]], decls: 21, vars: 13, consts: [["partnerChange", ""], ["productChange", ""], [1, "bgWhite", "text-black", "w-93", "h-100", "m-4", "mt-4"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], [1, "mt-4", "pt-2"], [3, "formGroup"], [1, "d-flex-row"], [1, "row"], ["id", "creationType", 1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["formControlName", "creationType", "appendTo", "body", "placeholder", "Select", 3, "showClear", "options", "ngClass", "panelStyle"], ["pTemplate", "filter"], ["class", "col-6 marginFieldNew borderRadius12", "id", "partnerType", 4, "ngIf"], ["class", "col-6 marginFieldNew borderRadius12", "id", "partnerId", 4, "ngIf"], ["class", "col-6 marginFieldNew borderRadius12", "id", "productId", 4, "ngIf"], [1, "d-flex", "float-right", "py-2"], ["mat-dialog-close", "", 1, "buttom1", "textBlack", "mx-1"], ["type", "button", 1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], ["id", "partnerType", 1, "col-6", "marginFieldNew", "borderRadius12"], ["formControlName", "partnerType", "appendTo", "body", "placeholder", "Select", 3, "onChange", "showClear", "options", "ngClass", "panelStyle"], ["id", "partnerId", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerId", "appendTo", "body", 3, "onChange", "showClear", "options", "ngClass", "panelStyle"], ["id", "productId", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "productId", "appendTo", "body", 3, "onChange", "showClear", "options", "ngClass", "panelStyle"]], template: function DownloadTemplateComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 2)(1, "div", 3)(2, "p", 4);
    \u0275\u0275text(3, "Create Consignment");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div", 5)(5, "form", 6)(6, "div", 7)(7, "div", 8)(8, "div", 9)(9, "p", 10);
    \u0275\u0275text(10, "Creation Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "p-dropdown", 11);
    \u0275\u0275template(12, DownloadTemplateComponent_ng_template_12_Template, 1, 0, "ng-template", 12);
    \u0275\u0275elementEnd()();
    \u0275\u0275template(13, DownloadTemplateComponent_div_13_Template, 5, 8, "div", 13)(14, DownloadTemplateComponent_div_14_Template, 6, 8, "div", 14)(15, DownloadTemplateComponent_div_15_Template, 6, 8, "div", 15);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "div", 16)(17, "button", 17);
    \u0275\u0275text(18, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(19, "button", 18);
    \u0275\u0275listener("click", function DownloadTemplateComponent_Template_button_click_19_listener() {
      return ctx.save();
    });
    \u0275\u0275text(20);
    \u0275\u0275elementEnd()()()()()();
  }
  if (rf & 2) {
    let tmp_4_0;
    \u0275\u0275advance(5);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance(6);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(11, _c0));
    \u0275\u0275property("showClear", true)("options", ctx.creationType)("ngClass", ((tmp_4_0 = ctx.form.get("creationType")) == null ? null : tmp_4_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(12, _c0));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.form.controls.creationType.value == "Bulk");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.form.controls.creationType.value == "Bulk");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.form.controls.creationType.value == "Bulk");
    \u0275\u0275advance(5);
    \u0275\u0275textInterpolate(ctx.form.controls.creationType.value == "Single" ? "Create" : "Download");
  }
}, dependencies: [NgClass, NgIf, PrimeTemplate, Dropdown, InputText, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, MatDialogClose, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 20rem);\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=download-template.component.css.map */"] });
var DownloadTemplateComponent = _DownloadTemplateComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(DownloadTemplateComponent, { className: "DownloadTemplateComponent", filePath: "src\\app\\main\\operation\\consignment\\download-template\\download-template.component.ts", lineNumber: 21 });
})();

// src/app/main/operation/consignment/consignment.component.ts
var _c02 = ["consignment"];
var _c1 = () => ({ width: "180" });
var _c2 = () => ({ width: "80vw" });
var _c3 = () => ({ "width": "100%" });
var _c4 = () => ({ "width": "130rem" });
var _c5 = (a0) => ({ "selectedRow": a0 });
function ConsignmentComponent_ng_template_65_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 50);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 51);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275property("pSortableColumn", col_r6.field);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r6.header, " ");
    \u0275\u0275advance();
    \u0275\u0275property("field", col_r6.field);
  }
}
function ConsignmentComponent_ng_template_65_th_9_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 52);
    \u0275\u0275listener("input", function ConsignmentComponent_ng_template_65_th_9_Template_input_input_1_listener($event) {
      const col_r8 = \u0275\u0275restoreView(_r7).$implicit;
      \u0275\u0275nextContext(2);
      const consignmentTag_r5 = \u0275\u0275reference(64);
      return \u0275\u0275resetView(consignmentTag_r5.filter($event.target == null ? null : $event.target.value, col_r8.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r8 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const consignmentTag_r5 = \u0275\u0275reference(64);
    \u0275\u0275advance();
    \u0275\u0275property("value", consignmentTag_r5.filters[col_r8.field] == null ? null : consignmentTag_r5.filters[col_r8.field].value);
  }
}
function ConsignmentComponent_ng_template_65_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 44);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 45);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, ConsignmentComponent_ng_template_65_th_3_Template, 3, 3, "th", 46);
    \u0275\u0275elementStart(4, "th", 47);
    \u0275\u0275text(5, " Actions ");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(6, "tr")(7, "th", 44);
    \u0275\u0275element(8, "p-tableHeaderCheckbox", 48);
    \u0275\u0275elementEnd();
    \u0275\u0275template(9, ConsignmentComponent_ng_template_65_th_9_Template, 2, 1, "th", 49);
    \u0275\u0275element(10, "th", 47);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r9 = ctx.$implicit;
    \u0275\u0275advance(3);
    \u0275\u0275property("ngForOf", columns_r9);
    \u0275\u0275advance(5);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r9);
  }
}
function ConsignmentComponent_ng_template_66_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r11 = \u0275\u0275nextContext().$implicit;
    const rowData_r12 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r12[col_r11.field], " ");
  }
}
function ConsignmentComponent_ng_template_66_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r13 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 65);
    \u0275\u0275listener("click", function ConsignmentComponent_ng_template_66_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r13);
      const rowData_r12 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r13 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r13.openCrud("Edit", rowData_r12));
    });
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r11 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r12 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(rowData_r12[col_r11.field]);
  }
}
function ConsignmentComponent_ng_template_66_td_3_ng_template_2_span_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span");
    \u0275\u0275text(1);
    \u0275\u0275pipe(2, "date");
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r11 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r12 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(\u0275\u0275pipeBind2(2, 1, rowData_r12[col_r11.field], "dd-MM-yyyy HH:mm"));
  }
}
function ConsignmentComponent_ng_template_66_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, ConsignmentComponent_ng_template_66_td_3_ng_template_2_span_0_Template, 2, 1, "span", 63)(1, ConsignmentComponent_ng_template_66_td_3_ng_template_2_span_1_Template, 3, 4, "span", 64);
  }
  if (rf & 2) {
    const col_r11 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r11.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r11.format == "date");
  }
}
function ConsignmentComponent_ng_template_66_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, ConsignmentComponent_ng_template_66_td_3_ng_container_1_Template, 2, 1, "ng-container", 62)(2, ConsignmentComponent_ng_template_66_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 5, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r11 = ctx.$implicit;
    const dateTemplate_r15 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r11.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r11.format !== "date" && col_r11.format !== "hyperLink")("ngIfElse", dateTemplate_r15);
  }
}
function ConsignmentComponent_ng_template_66_Template(rf, ctx) {
  if (rf & 1) {
    const _r10 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr", 53)(1, "td", 54);
    \u0275\u0275element(2, "p-tableCheckbox", 55);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, ConsignmentComponent_ng_template_66_td_3_Template, 4, 4, "td", 56);
    \u0275\u0275elementStart(4, "td", 57)(5, "img", 58);
    \u0275\u0275listener("click", function ConsignmentComponent_ng_template_66_Template_img_click_5_listener($event) {
      \u0275\u0275restoreView(_r10);
      const label_r16 = \u0275\u0275reference(7);
      return \u0275\u0275resetView(label_r16.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "p-overlayPanel", null, 4)(8, "div", 14)(9, "button", 15);
    \u0275\u0275listener("click", function ConsignmentComponent_ng_template_66_Template_button_click_9_listener() {
      const rowData_r12 = \u0275\u0275restoreView(_r10).$implicit;
      const label_r16 = \u0275\u0275reference(7);
      const ctx_r13 = \u0275\u0275nextContext();
      label_r16.hide();
      return \u0275\u0275resetView(ctx_r13.downloadLabel(rowData_r12, "download"));
    });
    \u0275\u0275element(10, "img", 59);
    \u0275\u0275text(11, "Download");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "button", 15);
    \u0275\u0275listener("click", function ConsignmentComponent_ng_template_66_Template_button_click_12_listener() {
      const rowData_r12 = \u0275\u0275restoreView(_r10).$implicit;
      const label_r16 = \u0275\u0275reference(7);
      const ctx_r13 = \u0275\u0275nextContext();
      label_r16.hide();
      return \u0275\u0275resetView(ctx_r13.downloadLabel(rowData_r12, "print"));
    });
    \u0275\u0275element(13, "img", 60);
    \u0275\u0275text(14, "Print");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "button", 61);
    \u0275\u0275listener("click", function ConsignmentComponent_ng_template_66_Template_button_click_15_listener() {
      const rowData_r12 = \u0275\u0275restoreView(_r10).$implicit;
      const label_r16 = \u0275\u0275reference(7);
      const ctx_r13 = \u0275\u0275nextContext();
      label_r16.hide();
      return \u0275\u0275resetView(ctx_r13.downloadInvoice(rowData_r12, "download"));
    });
    \u0275\u0275element(16, "img", 17);
    \u0275\u0275text(17, "Invoice");
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    const rowData_r12 = ctx.$implicit;
    const columns_r17 = ctx.columns;
    const ctx_r13 = \u0275\u0275nextContext();
    \u0275\u0275property("ngClass", \u0275\u0275pureFunction1(6, _c5, ctx_r13.isSelected(rowData_r12)));
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r12);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r17);
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(8, _c1));
    \u0275\u0275advance(9);
    \u0275\u0275property("disabled", rowData_r12.incoTerms != "DDU" && rowData_r12.hawbTypeId != 7);
  }
}
function ConsignmentComponent_ng_template_67_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 66);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
var _ConsignmentComponent = class _ConsignmentComponent {
  constructor(messageService, cs, router, path, service, invoiceService, dialog, datePipe, auth, spin, pdf, fb, label, invoice) {
    this.messageService = messageService;
    this.cs = cs;
    this.router = router;
    this.path = path;
    this.service = service;
    this.invoiceService = invoiceService;
    this.dialog = dialog;
    this.datePipe = datePipe;
    this.auth = auth;
    this.spin = spin;
    this.pdf = pdf;
    this.fb = fb;
    this.label = label;
    this.invoice = invoice;
    this.consignmentTable = [];
    this.selectedConsignment = [];
    this.cols = [];
    this.target = [];
    this.selectedFiles = null;
    this.searchform = this.fb.group({
      houseAirwayBill: [],
      masterAirwayBill: [],
      partnerId: [],
      pieceId: [],
      pieceItemId: [],
      shipperId: [],
      statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
      // startDate: this.calculateStartDate(),
      // endDate: new Date().toISOString()
    });
    this.fieldDisplayNames = {
      houseAirwayBill: "Consignment No",
      masterAirwayBill: "MAWB",
      partnerId: "Partner",
      pieceId: "Piece",
      pieceItemId: "Piece Item",
      shipperId: "Shipper",
      statusId: "Status"
    };
    this.houseAirwayBillDropdown = [];
    this.masterAirwayBillDropdown = [];
    this.partnerDropdown = [];
    this.statusDropdown = [];
    this.uniqueHouseAirway = [];
    this.uniquePieceId = [];
  }
  ngOnInit() {
    const dataToSend = ["Operations", "Consignment "];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "houseAirwayBill", header: "Consignment No", format: "hyperLink", style: "min-width: 5rem" },
      { field: "partnerHouseAirwayBill", header: "Partner HAWB" },
      { field: "hawbTypeDescription", header: "Status", style: "min-width: 5rem" },
      { field: "hawbTimeStamp", header: "Time", format: "date", style: "min-width: 5rem" },
      { field: "partnerName", header: "Partner", style: "min-width: 5rem" },
      { field: "productName", header: "Product", style: "min-width: 10rem" },
      //   { field: 'subProductName', header: 'Sub Product', style: 'min-width: 10rem' },
      { field: "countryOfOrigin", header: "Origin", style: "min-width: 5rem" },
      { field: "countryOfDestination", header: "Destination", style: "min-width: 5rem" },
      { field: "serviceTypeText", header: "Service Type", style: "min-width: 5rem" },
      { field: "paymentType", header: "Payment Type", style: "min-width: 5rem" },
      { field: "incoTerms", header: "Inco Terms", style: "min-width: 5rem" },
      { field: "createdBy", header: "Created By", style: "min-width: 5rem" },
      { field: "createdOn", header: "Created On", format: "date" }
    ];
    this.target = [
      { field: "statusId", header: "Status ID" },
      { field: "languageId", header: "Language ID" },
      { field: "countryId", header: "Country ID" },
      { field: "countryName", header: "Country Name" },
      { field: "provinceId", header: "Province ID" },
      { field: "provinceName", header: "Province Name" },
      { field: "districtId", header: "District ID" },
      { field: "districtName", header: "District Name" },
      { field: "cityId", header: "City ID" },
      { field: "cityName", header: "City Name" },
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
          this.consignmentTable = res;
          this.getSearchDropdown();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }, 2e3);
  }
  onChange() {
    const choosen = this.selectedConsignment[this.selectedConsignment.length - 1];
    this.selectedConsignment.length = 0;
    this.selectedConsignment.push(choosen);
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
        this.deleterecord(this.selectedConsignment[0]);
      }
    });
  }
  updateBulk() {
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
      return;
    }
    const dialogRef = this.dialog.open(ConsignmentUpdatebulkComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" },
      data: { title: "Consignment", code: this.selectedConsignment }
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.initialCall();
    });
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedConsignment = linedata;
    }
    if (this.selectedConsignment.length === 0 && type != "New") {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsignment[0] : linedata, pageflow: type });
      this.router.navigate(["/main/operation/consignment-new/" + paramdata]);
    }
  }
  deleteDialog() {
    if (this.selectedConsignment.length === 0) {
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
        line: this.selectedConsignment,
        module: "Consignment",
        body: "This action cannot be undone. All values associated with this field will be lost."
      }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedConsignment);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({
          severity: "success",
          summary: "Deleted",
          key: "br",
          detail: "Selected records deleted successfully"
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
    const exportData = this.consignmentTable.map((item) => {
      const exportItem = {};
      this.cols.forEach((col) => {
        if (col.format == "date") {
          exportItem[col.header] = this.datePipe.transform(item[col.field], "dd-MM-yyyy");
        } else {
          exportItem[col.header] = item[col.field];
        }
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, "Consignment");
  }
  downloadLabel(line, type) {
    this.pdf.generatePdfBarocde(line, type);
  }
  downloadInvoice(line, type) {
    this.spin.show();
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.partnerHouseAirwayBill = [line.partnerHouseAirwayBill];
    obj.houseAirwayBill = [line.houseAirwayBill];
    this.invoiceService.customsClearanceInvoice(obj).subscribe({
      next: (result) => {
        if (result.length > 0) {
          this.invoice.generateDDUInvoice(result[0], type);
          this.spin.hide();
        } else {
          this.messageService.add({
            severity: "warn",
            summary: "No Data",
            key: "br",
            detail: "No Data found"
          });
          this.spin.hide();
        }
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  onFileSelected(event) {
    const file = event.target.files[0];
    this.selectedFiles = file;
    this.spin.show();
    this.service.uploadConsignment(this.selectedFiles).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: "success",
          summary: "Uploaded",
          key: "br",
          detail: "File uploaded successfully"
        });
        this.selectedFiles = null;
        this.clearFileInput(event.target);
        this.initialCall();
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.selectedFiles = null;
        this.clearFileInput(event.target);
        this.cs.commonerrorNew(err);
      }
    });
  }
  clearFileInput(input) {
    input.value = "";
  }
  getSearchDropdown() {
    this.consignmentTable.forEach((res) => {
      if (res.houseAirwayBill != null) {
        this.houseAirwayBillDropdown.push({ value: res.houseAirwayBill, label: res.houseAirwayBill });
        this.houseAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.houseAirwayBillDropdown, "value");
      }
      if (res.partnerId != null) {
        this.partnerDropdown.push({ value: res.partnerId, label: res.partnerName });
        this.partnerDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerDropdown, "value");
      }
      if (res.masterAirwayBill != null) {
        this.masterAirwayBillDropdown.push({ value: res.masterAirwayBill, label: res.masterAirwayBill });
        this.masterAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.masterAirwayBillDropdown, "value");
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
        this.consignmentTable = res;
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
      houseAirwayBill: [],
      masterAirwayBill: [],
      partnerId: [],
      pieceId: [],
      pieceItemId: [],
      shipperId: [],
      statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
      // startDate: this.calculateStartDate(),
      // endDate: new Date().toISOString()
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
  isSelected(item) {
    return this.selectedConsignment.includes(item);
  }
  generateInvoice() {
    this.uniqueHouseAirway = [];
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
      return;
    }
    this.uniqueHouseAirway = this.selectedConsignment.map((item) => item.houseAirwayBill);
    this.label.getResultInvoice(this.uniqueHouseAirway);
  }
  generateLabel() {
    this.uniquePieceId = [];
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
      return;
    }
    this.uniqueHouseAirway = this.selectedConsignment.map((item) => item.houseAirwayBill);
    this.label.getResultLabelFromConsignment(this.uniqueHouseAirway);
  }
  downloadTemplate() {
    const dialogRef = this.dialog.open(DownloadTemplateComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        if (result.creationType == "Single") {
          this.openCrud("New");
        } else {
          let obj = {};
          obj.value = { imageRefId: result.fileName, referenceImageUrl: "/consignment/templates/" };
          this.label.downloadDocument(obj);
        }
      }
    });
  }
  calculateStartDate() {
    const currentDate = /* @__PURE__ */ new Date();
    const startDate = /* @__PURE__ */ new Date();
    startDate.setDate(currentDate.getDate() - 30);
    return startDate.toISOString();
  }
};
_ConsignmentComponent.\u0275fac = function ConsignmentComponent_Factory(t) {
  return new (t || _ConsignmentComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(CostingSheetService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ConsignmentLabelComponent), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(ConsignmentLabelComponent), \u0275\u0275directiveInject(InvoicepdfComponent));
};
_ConsignmentComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ConsignmentComponent, selectors: [["app-consignment"]], viewQuery: function ConsignmentComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c02, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 68, vars: 48, consts: [["fileInput", ""], ["op", ""], ["consignment", ""], ["consignmentTag", ""], ["label", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "file", 2, "display", "none", 3, "change"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], [1, "d-flex", "flex-column"], ["mat-menu-item", "", 1, "w-100", 2, "width", "8rem", 3, "click"], ["src", "./assets/common/upload.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/document.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common2x/invoice.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "masterAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "houseAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "multiple", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "consignmentId", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "selection", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], ["pFrozenColumn", "", 2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], ["pFrozenColumn", "", "alignFrozen", "right", 2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [3, "ngClass"], ["pFrozenColumn", "", 2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "value"], [3, "style", 4, "ngFor", "ngForOf"], ["pFrozenColumn", "", "alignFrozen", "right", 2, "width", "5rem", "justify-content", "center"], ["type", "button", "src", "./assets/common/actions.png", "alt", "", "srcset", "", 2, "height", "1.4rem", 3, "click"], ["src", "./assets/common/download.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/print.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["mat-menu-item", "", 1, "w-100", 2, "width", "8rem", 3, "click", "disabled"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"]], template: function ConsignmentComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 6)(1, "div", 7)(2, "p", 8);
    \u0275\u0275text(3, "Consignment ");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 7)(5, "input", 9, 0);
    \u0275\u0275listener("change", function ConsignmentComponent_Template_input_change_5_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.onFileSelected($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 10);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "img", 11);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_img_click_8_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "img", 12);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_img_click_9_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "img", 13);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_img_click_10_listener($event) {
      \u0275\u0275restoreView(_r1);
      const op_r2 = \u0275\u0275reference(12);
      return \u0275\u0275resetView(op_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "p-overlayPanel", null, 1)(13, "div", 14)(14, "button", 15);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_button_click_14_listener() {
      \u0275\u0275restoreView(_r1);
      const fileInput_r3 = \u0275\u0275reference(6);
      const op_r2 = \u0275\u0275reference(12);
      op_r2.hide();
      return \u0275\u0275resetView(fileInput_r3.click());
    });
    \u0275\u0275element(15, "img", 16);
    \u0275\u0275text(16, "Upload");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(17, "button", 15);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_button_click_17_listener() {
      \u0275\u0275restoreView(_r1);
      const op_r2 = \u0275\u0275reference(12);
      op_r2.hide();
      return \u0275\u0275resetView(ctx.updateBulk());
    });
    \u0275\u0275element(18, "img", 16);
    \u0275\u0275text(19, "Bulk Update");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "button", 15);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_button_click_20_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.generateLabel());
    });
    \u0275\u0275element(21, "img", 17);
    \u0275\u0275text(22, "Label");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(23, "button", 15);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_button_click_23_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.generateInvoice());
    });
    \u0275\u0275element(24, "img", 18);
    \u0275\u0275text(25, "Invoice");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(26, "button", 19);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_button_click_26_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadTemplate());
    });
    \u0275\u0275element(27, "i", 20);
    \u0275\u0275text(28, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(29, "div", 21)(30, "p-iconField", 22)(31, "p-inputIcon", 23);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_p_inputIcon_click_31_listener($event) {
      \u0275\u0275restoreView(_r1);
      const consignment_r4 = \u0275\u0275reference(34);
      return \u0275\u0275resetView(consignment_r4.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(32, "input", 24);
    \u0275\u0275listener("input", function ConsignmentComponent_Template_input_input_32_listener($event) {
      \u0275\u0275restoreView(_r1);
      const consignmentTag_r5 = \u0275\u0275reference(64);
      return \u0275\u0275resetView(consignmentTag_r5.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(33, "p-overlayPanel", 25, 2)(35, "form", 26)(36, "div", 27)(37, "div", 28)(38, "p", 29);
    \u0275\u0275text(39, "MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(40, "p-multiSelect", 30);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "div", 28)(42, "p", 29);
    \u0275\u0275text(43, "HAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(44, "p-multiSelect", 31);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(45, "div", 28)(46, "p", 29);
    \u0275\u0275text(47, "Partner");
    \u0275\u0275elementEnd();
    \u0275\u0275element(48, "p-multiSelect", 32);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(49, "div", 28)(50, "p", 29);
    \u0275\u0275text(51, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275element(52, "p-multiSelect", 33);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(53, "div", 34)(54, "div")(55, "img", 35);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_img_click_55_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(56, "div")(57, "button", 36);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_button_click_57_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(58, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(59, "button", 37);
    \u0275\u0275listener("click", function ConsignmentComponent_Template_button_click_59_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(60, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(61, "p-chips", 38);
    \u0275\u0275listener("onRemove", function ConsignmentComponent_Template_p_chips_onRemove_61_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function ConsignmentComponent_Template_p_chips_ngModelChange_61_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(62, "div", 39)(63, "p-table", 40, 3);
    \u0275\u0275twoWayListener("selectionChange", function ConsignmentComponent_Template_p_table_selectionChange_63_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedConsignment, $event) || (ctx.selectedConsignment = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(65, ConsignmentComponent_ng_template_65_Template, 11, 3, "ng-template", 41)(66, ConsignmentComponent_ng_template_66_Template, 18, 9, "ng-template", 42)(67, ConsignmentComponent_ng_template_67_Template, 4, 1, "ng-template", 43);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(11);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(37, _c1));
    \u0275\u0275advance(22);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(38, _c2));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(39, _c3));
    \u0275\u0275property("showClear", true)("options", ctx.masterAirwayBillDropdown)("panelStyle", \u0275\u0275pureFunction0(40, _c3));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(41, _c3));
    \u0275\u0275property("showClear", true)("options", ctx.houseAirwayBillDropdown)("panelStyle", \u0275\u0275pureFunction0(42, _c3));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(43, _c3));
    \u0275\u0275property("showClear", true)("options", ctx.partnerDropdown)("panelStyle", \u0275\u0275pureFunction0(44, _c3));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(45, _c3));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(46, _c3));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.consignmentTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx.selectedConsignment);
    \u0275\u0275property("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(47, _c4));
  }
}, dependencies: [NgClass, NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, FrozenColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, InputIcon, IconField, InputText, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenuItem, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=consignment.component.css.map */"] });
var ConsignmentComponent = _ConsignmentComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ConsignmentComponent, { className: "ConsignmentComponent", filePath: "src\\app\\main\\operation\\consignment\\consignment.component.ts", lineNumber: 26 });
})();

// src/app/main/operation/consignment/consignment-new/dimension/dimension.component.ts
var _c03 = () => ({ "width": "100%" });
function DimensionComponent_ng_template_24_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 28);
  }
}
function DimensionComponent_ng_template_33_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 28);
  }
}
function DimensionComponent_ng_template_42_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 28);
  }
}
var _DimensionComponent = class _DimensionComponent {
  constructor(dialogRef, data, cs, spin, route, router, fb, messageService, service, cas, auth) {
    this.dialogRef = dialogRef;
    this.data = data;
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.fb = fb;
    this.messageService = messageService;
    this.service = service;
    this.cas = cas;
    this.auth = auth;
    this.form = new FormGroup({});
    this.languageIdList = [];
    this.companyIdList = [];
    this.dimensionList = [];
    this.weightList = [];
    this.volumeList = [];
  }
  ngOnInit() {
    this.dimensionChange();
    if (this.data.module == "piece") {
      this.form = this.fb.group({
        codAmount: [""],
        declaredValue: [""],
        description: [""],
        dimensionUnit: [""],
        height: [""],
        itemDetails: this.fb.array([]),
        length: [""],
        packReferenceNumber: [""],
        partnerType: [""],
        pieceId: [""],
        volume: [""],
        volumeUnit: [""],
        weight: [""],
        weightUnit: [""],
        width: [""],
        hsCode: [""],
        pieceValue: [""],
        pieceCurrency: [""]
      });
    }
    if (this.data.module == "item") {
      this.form = this.fb.group({
        codAmount: [],
        declaredValue: [],
        description: [],
        dimensionUnit: [],
        height: [],
        hsCode: [],
        imageRefId: [],
        itemCode: [],
        length: [],
        partnerName: [],
        partnerType: [],
        pieceItemId: [],
        pdfUrl: [],
        volume: [],
        volumeUnit: [],
        weight: [],
        weightUnit: [],
        width: []
      });
    }
    this.form.patchValue(this.data.line.value);
  }
  save() {
    console.log(this.form.getRawValue());
    this.dialogRef.close(this.form.value);
  }
  calculateVolume(formName) {
    const volume = formName.controls.length.value * formName.controls.width.value * formName.controls.height.value;
    formName.controls.volume.patchValue(volume);
  }
  dimensionChange() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    this.dimensionList = [];
    this.weightList = [];
    this.volumeList = [];
    this.spin.show();
    this.service.search(obj).subscribe({
      next: (result) => {
        result.forEach((element) => {
          if (element.uomType == "Dimension") {
            this.dimensionList.push({
              value: element.uomId,
              label: element.uomId + " - " + element.uomDescription
            });
          }
          if (element.uomType == "Weight") {
            this.weightList.push({
              value: element.uomId,
              label: element.uomId + " - " + element.uomDescription
            });
          }
          if (element.uomType == "Volume") {
            this.volumeList.push({
              value: element.uomId,
              label: element.uomId + " - " + element.uomDescription
            });
          }
        });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
};
_DimensionComponent.\u0275fac = function DimensionComponent_Factory(t) {
  return new (t || _DimensionComponent)(\u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(UomService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService));
};
_DimensionComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _DimensionComponent, selectors: [["app-dimension"]], decls: 48, vars: 22, consts: [[1, "bgWhite", "text-black", "w-93", "h-100", "m-4", "mt-4"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], [1, "mt-4", "pt-2"], [3, "formGroup"], [1, "d-flex-row"], [1, "row"], ["id", "length", 1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "length", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "change"], ["id", "width", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "width", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "change"], ["id", "height", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "height", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "change"], ["id", "dimensionUnit", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "dimensionUnit", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["pTemplate", "filter"], ["id", "volume", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "volume", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "volumeUnit", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "volumeUnit", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["id", "weight", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "weight", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "weightUnit", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "weightUnit", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "float-right", "py-2"], ["mat-dialog-close", "", 1, "buttom1", "textBlack", "mx-1"], ["type", "button", 1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"]], template: function DimensionComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1)(2, "p", 2);
    \u0275\u0275text(3, "Dimension");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div", 3)(5, "form", 4)(6, "div", 5)(7, "div", 6)(8, "div", 7)(9, "p", 8);
    \u0275\u0275text(10, "Length");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "input", 9);
    \u0275\u0275listener("change", function DimensionComponent_Template_input_change_11_listener() {
      return ctx.calculateVolume(ctx.form);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(12, "div", 10)(13, "p", 8);
    \u0275\u0275text(14, "Width");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "input", 11);
    \u0275\u0275listener("change", function DimensionComponent_Template_input_change_15_listener() {
      return ctx.calculateVolume(ctx.form);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(16, "div", 12)(17, "p", 8);
    \u0275\u0275text(18, "Height");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(19, "input", 13);
    \u0275\u0275listener("change", function DimensionComponent_Template_input_change_19_listener() {
      return ctx.calculateVolume(ctx.form);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(20, "div", 14)(21, "p", 8);
    \u0275\u0275text(22, "Dimension Unit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(23, "p-dropdown", 15);
    \u0275\u0275template(24, DimensionComponent_ng_template_24_Template, 1, 0, "ng-template", 16);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(25, "div", 17)(26, "p", 8);
    \u0275\u0275text(27, "Volume");
    \u0275\u0275elementEnd();
    \u0275\u0275element(28, "input", 18);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(29, "div", 19)(30, "p", 8);
    \u0275\u0275text(31, "Volume Unit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(32, "p-dropdown", 20);
    \u0275\u0275template(33, DimensionComponent_ng_template_33_Template, 1, 0, "ng-template", 16);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(34, "div", 21)(35, "p", 8);
    \u0275\u0275text(36, "Weight");
    \u0275\u0275elementEnd();
    \u0275\u0275element(37, "input", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(38, "div", 23)(39, "p", 8);
    \u0275\u0275text(40, "Weight Unit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "p-dropdown", 24);
    \u0275\u0275template(42, DimensionComponent_ng_template_42_Template, 1, 0, "ng-template", 16);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(43, "div", 25)(44, "button", 26);
    \u0275\u0275text(45, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(46, "button", 27);
    \u0275\u0275listener("click", function DimensionComponent_Template_button_click_46_listener() {
      return ctx.save();
    });
    \u0275\u0275text(47, "Save");
    \u0275\u0275elementEnd()()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(5);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance(18);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(16, _c03));
    \u0275\u0275property("showClear", true)("options", ctx.dimensionList)("panelStyle", \u0275\u0275pureFunction0(17, _c03));
    \u0275\u0275advance(9);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(18, _c03));
    \u0275\u0275property("showClear", true)("options", ctx.volumeList)("panelStyle", \u0275\u0275pureFunction0(19, _c03));
    \u0275\u0275advance(9);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(20, _c03));
    \u0275\u0275property("showClear", true)("options", ctx.weightList)("panelStyle", \u0275\u0275pureFunction0(21, _c03));
  }
}, dependencies: [PrimeTemplate, Dropdown, InputText, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, KeyFilter, MatDialogClose, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 35rem);\n  overflow-y: scroll;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.2rem;\n}\n/*# sourceMappingURL=dimension.component.css.map */"] });
var DimensionComponent = _DimensionComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(DimensionComponent, { className: "DimensionComponent", filePath: "src\\app\\main\\operation\\consignment\\consignment-new\\dimension\\dimension.component.ts", lineNumber: 17 });
})();

// src/app/main/operation/consignment/consignment-new/image-upload/image-upload.component.ts
var _c04 = () => ({ width: "180" });
function ImageUploadComponent_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th");
    \u0275\u0275text(2, "File Name");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "th");
    \u0275\u0275text(4, "Location");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "th");
    \u0275\u0275text(6, "Actions");
    \u0275\u0275elementEnd()();
  }
}
function ImageUploadComponent_ng_template_12_Template(rf, ctx) {
  if (rf & 1) {
    const _r3 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr", 15)(1, "td");
    \u0275\u0275element(2, "input", 16);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "td");
    \u0275\u0275element(4, "input", 17);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "td", 18)(6, "img", 19);
    \u0275\u0275listener("click", function ImageUploadComponent_ng_template_12_Template_img_click_6_listener($event) {
      \u0275\u0275restoreView(_r3);
      const op_r4 = \u0275\u0275reference(8);
      return \u0275\u0275resetView(op_r4.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "p-overlayPanel", null, 1)(9, "div", 20)(10, "button", 21);
    \u0275\u0275listener("click", function ImageUploadComponent_ng_template_12_Template_button_click_10_listener() {
      const i_r5 = \u0275\u0275restoreView(_r3).rowIndex;
      const op_r4 = \u0275\u0275reference(8);
      const ctx_r5 = \u0275\u0275nextContext();
      op_r4.hide();
      return \u0275\u0275resetView(ctx_r5.removeItem(i_r5));
    });
    \u0275\u0275element(11, "img", 22);
    \u0275\u0275text(12, "Delete");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "button", 21);
    \u0275\u0275listener("click", function ImageUploadComponent_ng_template_12_Template_button_click_13_listener() {
      const rowData_r7 = \u0275\u0275restoreView(_r3).$implicit;
      const op_r4 = \u0275\u0275reference(8);
      const ctx_r5 = \u0275\u0275nextContext();
      op_r4.hide();
      return \u0275\u0275resetView(ctx_r5.download(rowData_r7));
    });
    \u0275\u0275element(14, "img", 23);
    \u0275\u0275text(15, "Download");
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    const item_r8 = ctx.$implicit;
    \u0275\u0275property("formGroup", item_r8);
    \u0275\u0275advance(7);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(3, _c04));
  }
}
var _ImageUploadComponent = class _ImageUploadComponent {
  constructor(dialogRef, data, cs, spin, route, sanitizer, router, fb, service, messageService, cas, auth, download1) {
    this.dialogRef = dialogRef;
    this.data = data;
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.sanitizer = sanitizer;
    this.router = router;
    this.fb = fb;
    this.service = service;
    this.messageService = messageService;
    this.cas = cas;
    this.auth = auth;
    this.download1 = download1;
    this.imageForm = this.fb.group({
      referenceImageList: this.fb.array([])
      // Initialize an empty FormArray
    });
    this.selectedFiles = null;
    this.imageDetailsTable = [];
  }
  get imageDetails() {
    return this.imageForm.get("referenceImageList");
  }
  removeItem(index) {
    this.imageDetails.removeAt(index);
  }
  ngOnInit() {
    console.log(this.data);
    this.patchForm(this.data.line.value);
  }
  selectFiles(event) {
    this.selectedFiles = event.target.files;
    this.uploadFile(event);
  }
  uploadFile(event) {
    if (!this.selectedFiles || this.selectedFiles.length === 0) {
      console.log("No files selected for upload.");
      return;
    }
    this.fileLocation = "/" + (/* @__PURE__ */ new Date()).getDate() + "-" + ((/* @__PURE__ */ new Date()).getMonth() + 1) + "-" + (/* @__PURE__ */ new Date()).getFullYear() + "_" + this.cs.timeFormat(/* @__PURE__ */ new Date()) + "/";
    this.service.uploadFiles(this.selectedFiles, this.fileLocation).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: "success",
          summary: "Updated",
          key: "br",
          detail: "File uploaded successfully"
        });
        result.forEach((x) => {
          x["referenceImageUrl"] = x.filePath;
          x["imageRefId"] = x.fileName;
        });
        this.patchForm(result);
        this.selectedFiles = null;
        this.clearFileInput(event.target);
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  clearFileInput(input) {
    input.value = "";
  }
  save() {
    this.dialogRef.close(this.imageForm.controls.referenceImageList.value);
  }
  patchForm(shipmentData) {
    const itemsArray = this.imageForm.get("referenceImageList");
    shipmentData.forEach((piece) => {
      itemsArray.push(this.patchReferenceImages(piece));
    });
    console.log(this.imageForm);
  }
  patchReferenceImages(image) {
    return this.fb.group({
      imageRefId: [image.imageRefId],
      pdfUrl: [image.pdfUrl],
      referenceImageUrl: [image.referenceImageUrl]
    });
  }
  download(element) {
    this.download1.downloadDocument(element);
  }
};
_ImageUploadComponent.\u0275fac = function ImageUploadComponent_Factory(t) {
  return new (t || _ImageUploadComponent)(\u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(DomSanitizer), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(ConsignmentLabelComponent));
};
_ImageUploadComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ImageUploadComponent, selectors: [["app-image-upload"]], decls: 18, vars: 2, consts: [["fileInput", ""], ["op", ""], [1, "bgWhite", "text-black", "w-93", "h-100", "mx-4", "my-2", "mt-4"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "file", "multiple", "", 2, "display", "none", 3, "change"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "mt-4", "pt-2"], ["scrollHeight", "calc(100vh - 24.8rem)", 3, "scrollable", "value"], ["pTemplate", "header"], ["pTemplate", "body"], [1, "d-flex", "my-3", "justify-content-end"], ["mat-dialog-close", "", 1, "buttom1", "textBlack", "mx-1"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [3, "formGroup"], ["type", "text", "matInput", "", "formControlName", "imageRefId", 1, "inputborderLess", "pl-3"], ["type", "text", "formControlName", "referenceImageUrl", 1, "inputborderLess", "pl-3"], [2, "padding-left", "1.5rem !important"], ["type", "button", "src", "./assets/common/actions.png", "alt", "", "srcset", "", 2, "height", "1.4rem", 3, "click"], [1, "d-flex", "flex-column"], ["mat-menu-item", "", 1, "w-100", 2, "width", "8rem", 3, "click"], ["src", "./assets/common/delete1x.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/download.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"]], template: function ImageUploadComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 2)(1, "div", 3)(2, "p", 4);
    \u0275\u0275text(3, "Image Upload");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "input", 5, 0);
    \u0275\u0275listener("change", function ImageUploadComponent_Template_input_change_4_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.selectFiles($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "button", 6);
    \u0275\u0275listener("click", function ImageUploadComponent_Template_button_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      const fileInput_r2 = \u0275\u0275reference(5);
      return \u0275\u0275resetView(fileInput_r2.click());
    });
    \u0275\u0275element(7, "i", 7);
    \u0275\u0275text(8, " Add New");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(9, "div", 8)(10, "p-table", 9);
    \u0275\u0275template(11, ImageUploadComponent_ng_template_11_Template, 7, 0, "ng-template", 10)(12, ImageUploadComponent_ng_template_12_Template, 16, 4, "ng-template", 11);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(13, "div", 12)(14, "button", 13);
    \u0275\u0275text(15, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "button", 14);
    \u0275\u0275listener("click", function ImageUploadComponent_Template_button_click_16_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.save());
    });
    \u0275\u0275text(17, "Save");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(10);
    \u0275\u0275property("scrollable", true)("value", ctx.imageDetails.controls);
  }
}, dependencies: [PrimeTemplate, Table, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, OverlayPanel, MatInput, MatMenuItem, MatDialogClose, FormGroupDirective, FormControlName], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 35rem);\n  overflow-y: scroll;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.2rem;\n}\n.inputborderLess[_ngcontent-%COMP%] {\n  border: none !important;\n}\n/*# sourceMappingURL=image-upload.component.css.map */"] });
var ImageUploadComponent = _ImageUploadComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ImageUploadComponent, { className: "ImageUploadComponent", filePath: "src\\app\\main\\operation\\consignment\\consignment-new\\image-upload\\image-upload.component.ts", lineNumber: 21 });
})();

// src/app/main/operation/consignment/consignment-new/item-details/item-details.component.ts
var _c05 = () => ({ "width": "100%" });
var _c12 = () => ({ width: "180" });
function ItemDetailsComponent_ng_template_10_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th");
    \u0275\u0275text(2, "HS Code");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "th");
    \u0275\u0275text(4, "Description");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "th");
    \u0275\u0275text(6, "Qty");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "th");
    \u0275\u0275text(8, "Unit Value");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "th");
    \u0275\u0275text(10, "Total Value");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "th");
    \u0275\u0275text(12, "Currency");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "th");
    \u0275\u0275text(14, "Actions");
    \u0275\u0275elementEnd()();
  }
}
function ItemDetailsComponent_ng_template_11_p_dropdown_2_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 32);
  }
}
function ItemDetailsComponent_ng_template_11_p_dropdown_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r2 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "p-dropdown", 31, 1);
    \u0275\u0275listener("dblclick", function ItemDetailsComponent_ng_template_11_p_dropdown_2_Template_p_dropdown_dblclick_0_listener() {
      \u0275\u0275restoreView(_r2);
      const ctx_r2 = \u0275\u0275nextContext(2);
      return \u0275\u0275resetView(ctx_r2.toggleInput());
    })("onChange", function ItemDetailsComponent_ng_template_11_p_dropdown_2_Template_p_dropdown_onChange_0_listener() {
      \u0275\u0275restoreView(_r2);
      const hscode_r4 = \u0275\u0275reference(1);
      const rowData_r5 = \u0275\u0275nextContext().$implicit;
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.hsCodeChange(hscode_r4, rowData_r5));
    });
    \u0275\u0275template(2, ItemDetailsComponent_ng_template_11_p_dropdown_2_ng_template_2_Template, 1, 0, "ng-template", 23);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(5, _c05));
    \u0275\u0275property("showClear", true)("options", ctx_r2.hsCodeList)("panelStyle", \u0275\u0275pureFunction0(6, _c05));
  }
}
function ItemDetailsComponent_ng_template_11_input_3_Template(rf, ctx) {
  if (rf & 1) {
    const _r6 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "input", 33);
    \u0275\u0275listener("dblclick", function ItemDetailsComponent_ng_template_11_input_3_Template_input_dblclick_0_listener() {
      \u0275\u0275restoreView(_r6);
      const ctx_r2 = \u0275\u0275nextContext(2);
      return \u0275\u0275resetView(ctx_r2.toggleInput());
    });
    \u0275\u0275elementEnd();
  }
}
function ItemDetailsComponent_ng_template_11_ng_template_14_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 32);
  }
}
function ItemDetailsComponent_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr", 15)(1, "td");
    \u0275\u0275template(2, ItemDetailsComponent_ng_template_11_p_dropdown_2_Template, 3, 7, "p-dropdown", 16)(3, ItemDetailsComponent_ng_template_11_input_3_Template, 1, 0, "input", 17);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "td");
    \u0275\u0275element(5, "input", 18);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "td")(7, "input", 19);
    \u0275\u0275listener("change", function ItemDetailsComponent_ng_template_11_Template_input_change_7_listener() {
      const rowData_r5 = \u0275\u0275restoreView(_r1).$implicit;
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.calculateTotalValue(rowData_r5));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(8, "td")(9, "input", 20);
    \u0275\u0275listener("change", function ItemDetailsComponent_ng_template_11_Template_input_change_9_listener() {
      const rowData_r5 = \u0275\u0275restoreView(_r1).$implicit;
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.calculateTotalValue(rowData_r5));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(10, "td");
    \u0275\u0275element(11, "input", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "td")(13, "p-dropdown", 22);
    \u0275\u0275template(14, ItemDetailsComponent_ng_template_11_ng_template_14_Template, 1, 0, "ng-template", 23);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(15, "td", 24)(16, "img", 25);
    \u0275\u0275listener("click", function ItemDetailsComponent_ng_template_11_Template_img_click_16_listener($event) {
      \u0275\u0275restoreView(_r1);
      const op_r7 = \u0275\u0275reference(18);
      return \u0275\u0275resetView(op_r7.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(17, "p-overlayPanel", null, 0)(19, "div", 26)(20, "button", 27);
    \u0275\u0275listener("click", function ItemDetailsComponent_ng_template_11_Template_button_click_20_listener() {
      const i_r8 = \u0275\u0275restoreView(_r1).rowIndex;
      const op_r7 = \u0275\u0275reference(18);
      const ctx_r2 = \u0275\u0275nextContext();
      op_r7.hide();
      return \u0275\u0275resetView(ctx_r2.dimension(ctx_r2.data.pageflow, "item", i_r8));
    });
    \u0275\u0275element(21, "img", 28);
    \u0275\u0275text(22, "Dimension");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(23, "button", 27);
    \u0275\u0275listener("click", function ItemDetailsComponent_ng_template_11_Template_button_click_23_listener() {
      const ctx_r8 = \u0275\u0275restoreView(_r1);
      const i_r8 = ctx_r8.rowIndex;
      const rowData_r5 = ctx_r8.$implicit;
      const op_r7 = \u0275\u0275reference(18);
      const ctx_r2 = \u0275\u0275nextContext();
      op_r7.hide();
      return \u0275\u0275resetView(ctx_r2.removeItem(i_r8, rowData_r5));
    });
    \u0275\u0275element(24, "img", 29);
    \u0275\u0275text(25, "Delete");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "button", 27);
    \u0275\u0275listener("click", function ItemDetailsComponent_ng_template_11_Template_button_click_26_listener() {
      const i_r8 = \u0275\u0275restoreView(_r1).rowIndex;
      const op_r7 = \u0275\u0275reference(18);
      const ctx_r2 = \u0275\u0275nextContext();
      op_r7.hide();
      return \u0275\u0275resetView(ctx_r2.imageupload(ctx_r2.data.pageflow, i_r8));
    });
    \u0275\u0275element(27, "img", 30);
    \u0275\u0275text(28, "Image");
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    const item_r10 = ctx.$implicit;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("formGroup", item_r10);
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", !ctx_r2.showInput);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.showInput);
    \u0275\u0275advance(10);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(10, _c05));
    \u0275\u0275property("showClear", true)("options", ctx_r2.currencyDropdown)("panelStyle", \u0275\u0275pureFunction0(11, _c05));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(12, _c12));
  }
}
var _ItemDetailsComponent = class _ItemDetailsComponent {
  constructor(cs, spin, router, fb, service, messageService, cas, auth, dialogRef, dialog, data) {
    this.cs = cs;
    this.spin = spin;
    this.router = router;
    this.fb = fb;
    this.service = service;
    this.messageService = messageService;
    this.cas = cas;
    this.auth = auth;
    this.dialogRef = dialogRef;
    this.dialog = dialog;
    this.data = data;
    this.itemForm = this.fb.group({
      itemDetails: this.fb.array([])
      // Initialize an empty FormArray
    });
    this.hsCodeList = [];
    this.currencyDropdown = [];
    this.showInput = true;
  }
  get itemDetails() {
    return this.itemForm.get("itemDetails");
  }
  addItem() {
    this.itemDetails.push(this.createItemFormGroup());
  }
  createItemFormGroup() {
    return this.fb.group({
      codAmount: [],
      declaredValue: [],
      description: [],
      dimensionUnit: [],
      height: [],
      hsCode: [],
      imageRefId: [],
      itemCode: [],
      length: [],
      partnerName: [],
      partnerType: [],
      pieceItemId: [],
      pdfUrl: [],
      volume: [],
      volumeUnit: [],
      weight: [],
      weightUnit: [],
      width: [],
      quantity: [""],
      unitValue: [""],
      currency: [""],
      masterAirwayBill: [""],
      houseAirwayBill: [""],
      partnerId: [""],
      referenceImageList: this.fb.array([])
    });
  }
  removeItem(index, data) {
    this.itemDetails.removeAt(index);
    this.service.DeleteItem(data.getRawValue()).subscribe({ next: (res) => {
    } });
  }
  ngOnInit() {
    this.dropdownlist();
    this.patchForm(this.data.line.value);
  }
  save() {
    const control = this.itemForm.controls.itemDetails;
    const pieceValue = control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.declaredValue), 0);
    const length = control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.length), 0);
    const width = control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.width), 0);
    const height = control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.height), 0);
    const weight = control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.weight), 0);
    const volume = control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.volume), 0);
    let obj = {};
    obj.lines = this.itemForm.controls.itemDetails.value;
    obj.pieceValue = pieceValue;
    obj.length = length;
    obj.width = width;
    obj.height = height;
    obj.weight = weight;
    obj.volume = volume;
    obj.tags = control.length;
    obj.volumeUnit = control.value[0].volumeUnit;
    obj.dimensionUnit = control.value[0].dimensionUnit;
    obj.weightUnit = control.value[0].weightUnit;
    obj.currency = control.value[0].currency;
    this.dialogRef.close(obj);
  }
  calculateVolume(formName) {
    const volume = formName.controls.length.value * formName.controls.width.value * formName.controls.height.value;
    formName.controls.volume.patchValue(volume);
  }
  patchForm(shipmentData) {
    const itemsArray = this.itemForm.get("itemDetails");
    shipmentData.forEach((piece) => {
      itemsArray.push(this.patchItemDetail(piece));
    });
  }
  patchItemDetail(item) {
    return this.fb.group({
      codAmount: [item.codAmount],
      declaredValue: [item.declaredValue],
      description: [item.description],
      dimensionUnit: [item.dimensionUnit],
      height: [item.height],
      hsCode: [item.hsCode],
      imageRefId: [item.imageRefId],
      itemCode: [item.itemCode],
      length: [item.length],
      partnerName: [item.partnerName],
      partnerType: [item.partnerType],
      pieceItemId: [item.pieceItemId],
      referenceField1: [item.referenceField1],
      referenceField10: [item.referenceField10],
      referenceField11: [item.referenceField11],
      referenceField12: [item.referenceField12],
      referenceField13: [item.referenceField13],
      referenceField14: [item.referenceField14],
      referenceField15: [item.referenceField15],
      referenceField16: [item.referenceField16],
      referenceField17: [item.referenceField17],
      referenceField18: [item.referenceField18],
      referenceField19: [item.referenceField19],
      referenceField2: [item.referenceField2],
      referenceField20: [item.referenceField20],
      referenceField3: [item.referenceField3],
      referenceField4: [item.referenceField4],
      referenceField5: [item.referenceField5],
      referenceField6: [item.referenceField6],
      referenceField7: [item.referenceField7],
      referenceField8: [item.referenceField8],
      referenceField9: [item.referenceField9],
      masterAirwayBill: [item.masterAirwayBill],
      partnerId: [item.partnerId],
      houseAirwayBill: [item.houseAirwayBill],
      referenceImageList: this.patchReferenceImages(item.referenceImageList),
      volume: [item.volume],
      volumeUnit: [item.volumeUnit],
      weight: [item.weight],
      weightUnit: [item.weightUnit],
      width: [item.width],
      quantity: [item.quantity],
      unitValue: [item.unitValue],
      currency: [item.currency]
    });
  }
  patchReferenceImages(referenceImageList) {
    if (referenceImageList == null) {
      return;
    }
    return this.fb.array(referenceImageList.map((image) => this.fb.group({
      imageRefId: [image.imageRefId],
      pdfUrl: [image.pdfUrl],
      referenceImageUrl: [image.referenceImageUrl]
    })));
  }
  dimension(type = "New", module, index) {
    const dialogRef = this.dialog.open(DimensionComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "25%" },
      data: { pageflow: type, module, line: this.itemForm.controls.itemDetails.at(index) }
    });
    dialogRef.afterClosed().subscribe((result) => {
      const control = this.itemForm.controls.itemDetails.at(index);
      control.patchValue(result);
    });
  }
  imageupload(type = "New", index) {
    const dialogRef = this.dialog.open(ImageUploadComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "25%" },
      data: { pageflow: type, line: this.itemForm.controls.itemDetails.at(index).get("referenceImageList"), lineDetails: this.itemForm.controls.itemDetails.at(index), type: "item" }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const imageDetailsFormArray = this.itemForm.controls.itemDetails.at(index).get("referenceImageList");
        imageDetailsFormArray.clear();
        result.forEach((image) => {
          imageDetailsFormArray.push(this.fb.group({
            imageRefId: image.imageRefId,
            pdfUrl: image.pdfUrl,
            referenceImageUrl: image.referenceImageUrl
          }));
        });
        console.log(this.itemForm);
      }
    });
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.hsCode.url,
      this.cas.dropdownlist.setup.currency.url
    ]).subscribe({
      next: (results) => {
        this.hsCodeList = this.cas.forLanguageFilter(results[0], { key: "hsCode", value: "itemGroup", languageId: "languageId", companyId: "companyId" });
        this.currencyDropdown = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.currency.key);
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  calculateTotalValue(value) {
    value.controls.declaredValue.patchValue(value.controls.quantity.value * value.controls.unitValue.value);
  }
  hsCodeChange(value, control) {
    let desc = this.hsCodeList.find((option) => option.value === control.controls.hsCode.value)?.label;
    control.controls.description.patchValue(desc);
  }
  toggleInput() {
    this.showInput = !this.showInput;
    console.log(this.showInput);
  }
};
_ItemDetailsComponent.\u0275fac = function ItemDetailsComponent_Factory(t) {
  return new (t || _ItemDetailsComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(MAT_DIALOG_DATA));
};
_ItemDetailsComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ItemDetailsComponent, selectors: [["app-item-details"]], decls: 17, vars: 3, consts: [["op", ""], ["hscode", ""], [1, "bgWhite", "text-black", "w-93", "h-100", "mx-4", "my-2", "mt-4"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "mt-4", "pt-2"], [1, "mt-1", "mx-3", 3, "formGroup"], ["scrollHeight", "calc(100vh - 24.8rem)", 1, "paddingZero", 3, "scrollable", "value"], ["pTemplate", "header"], ["pTemplate", "body"], [1, "d-flex", "my-3", "justify-content-end"], ["mat-dialog-close", "", 1, "buttom1", "textBlack", "mx-1"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [3, "formGroup"], ["placeholder", "Select", "filter", "true", "formControlName", "hsCode", "appendTo", "body", 3, "showClear", "options", "style", "panelStyle", "dblclick", "onChange", 4, "ngIf"], ["class", "inputborderLess pl-3", "placeholder", "Enter", "formControlName", "hsCode", 3, "dblclick", 4, "ngIf"], ["placeholder", "Enter", "formControlName", "description", 1, "inputborderLess", "pl-3"], ["placeholder", "Enter", "formControlName", "quantity", 1, "inputborderLess", "pl-3", 3, "change"], ["placeholder", "Enter", "formControlName", "unitValue", 1, "inputborderLess", "pl-3", 3, "change"], ["placeholder", "Enter", "formControlName", "declaredValue", 1, "inputborderLess", "pl-3"], ["placeholder", "Select", "filter", "true", "formControlName", "currency", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["pTemplate", "filter"], [2, "padding-left", "1.5rem !important"], ["type", "button", "src", "./assets/common/actions.png", "alt", "", "srcset", "", 2, "height", "1.4rem", 3, "click"], [1, "d-flex", "flex-column"], ["mat-menu-item", "", 1, "w-100", 2, "width", "8rem", 3, "click"], ["src", "./assets/common/dimension.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/delete1x.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/image.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["placeholder", "Select", "filter", "true", "formControlName", "hsCode", "appendTo", "body", 3, "dblclick", "onChange", "showClear", "options", "panelStyle"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], ["placeholder", "Enter", "formControlName", "hsCode", 1, "inputborderLess", "pl-3", 3, "dblclick"]], template: function ItemDetailsComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 2)(1, "div", 3)(2, "p", 4);
    \u0275\u0275text(3, "List Items");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "button", 5);
    \u0275\u0275listener("click", function ItemDetailsComponent_Template_button_click_4_listener() {
      return ctx.addItem();
    });
    \u0275\u0275element(5, "i", 6);
    \u0275\u0275text(6, " Add New");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(7, "div", 7)(8, "form", 8)(9, "p-table", 9);
    \u0275\u0275template(10, ItemDetailsComponent_ng_template_10_Template, 15, 0, "ng-template", 10)(11, ItemDetailsComponent_ng_template_11_Template, 29, 13, "ng-template", 11);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 12)(13, "button", 13);
    \u0275\u0275text(14, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "button", 14);
    \u0275\u0275listener("click", function ItemDetailsComponent_Template_button_click_15_listener() {
      return ctx.save();
    });
    \u0275\u0275text(16, "Save");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(8);
    \u0275\u0275property("formGroup", ctx.itemForm);
    \u0275\u0275advance();
    \u0275\u0275property("scrollable", true)("value", ctx.itemDetails.controls);
  }
}, dependencies: [NgIf, PrimeTemplate, Table, Dropdown, InputText, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, OverlayPanel, MatMenuItem, MatDialogClose, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 35rem);\n  overflow-y: scroll;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.2rem;\n}\n.inputborderLess[_ngcontent-%COMP%] {\n  border: none !important;\n}\n/*# sourceMappingURL=item-details.component.css.map */"] });
var ItemDetailsComponent = _ItemDetailsComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ItemDetailsComponent, { className: "ItemDetailsComponent", filePath: "src\\app\\main\\operation\\consignment\\consignment-new\\item-details\\item-details.component.ts", lineNumber: 19 });
})();

// src/app/main/operation/consignment/consignment-new/consignment-status-popup/consignment-status-popup.component.ts
function ConsignmentStatusPopupComponent_tr_19_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr", 11)(1, "td", 12);
    \u0275\u0275text(2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "td", 12);
    \u0275\u0275text(4);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "td", 12);
    \u0275\u0275text(6);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "td", 12);
    \u0275\u0275text(8);
    \u0275\u0275pipe(9, "date");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "td", 12);
    \u0275\u0275text(11);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const item_r1 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(item_r1.pieceId);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(item_r1.pieceType);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(item_r1.pieceTypeDescription);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(\u0275\u0275pipeBind2(9, 5, item_r1.pieceTimeStamp, "dd-MM-yyyy HH:mm"));
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate(item_r1.updatedBy);
  }
}
var _ConsignmentStatusPopupComponent = class _ConsignmentStatusPopupComponent {
  constructor(dialogRef, data, cs, spin, service, auth) {
    this.dialogRef = dialogRef;
    this.data = data;
    this.cs = cs;
    this.spin = spin;
    this.service = service;
    this.auth = auth;
    this.cnTable = [];
  }
  ngOnInit() {
    this.callCNTable();
  }
  callCNTable() {
    let obj = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.pieceId = [this.data.code.pieceId];
    obj.hawbType = [this.data.code.hawbType];
    this.service.searchStatus(obj).subscribe({ next: (res) => {
      this.cnTable = res;
      console.log(this.cnTable);
    }, error: (err) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    } });
  }
};
_ConsignmentStatusPopupComponent.\u0275fac = function ConsignmentStatusPopupComponent_Factory(t) {
  return new (t || _ConsignmentStatusPopupComponent)(\u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(AuthService));
};
_ConsignmentStatusPopupComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ConsignmentStatusPopupComponent, selectors: [["app-consignment-status-popup"]], decls: 23, vars: 1, consts: [[1, "bgWhite", "text-black", "w-93", "h-100", "m-4", "mt-4"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], [1, "d-flex", "scrollNew", "mt-4"], [1, "table", 2, "width", "80rem"], [1, "sticky-top", "table-style"], ["scope", "col"], [1, "table-style-body"], ["style", "height: 20px !important;", 4, "ngFor", "ngForOf"], [1, "d-flex", "float-right", "py-2", 2, "position", "absolute", "right", "3.5%", "bottom", "5%"], ["mat-dialog-close", "", 1, "buttom1", "textBlack", "mx-1"], [2, "height", "20px !important"], ["scope", "row"]], template: function ConsignmentStatusPopupComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1)(2, "p", 2);
    \u0275\u0275text(3, "Piece Details");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div", 3)(5, "table", 4)(6, "thead", 5)(7, "tr")(8, "th", 6);
    \u0275\u0275text(9, "Piece ID");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "th", 6);
    \u0275\u0275text(11, "Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "th", 6);
    \u0275\u0275text(13, "Description");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(14, "th", 6);
    \u0275\u0275text(15, "Time");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "th", 6);
    \u0275\u0275text(17, "User");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(18, "tbody", 7);
    \u0275\u0275template(19, ConsignmentStatusPopupComponent_tr_19_Template, 12, 8, "tr", 8);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(20, "div", 9)(21, "button", 10);
    \u0275\u0275text(22, "Cancel");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(19);
    \u0275\u0275property("ngForOf", ctx.cnTable);
  }
}, dependencies: [NgForOf, MatDialogClose, DatePipe], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  max-height: calc(100vh - 33rem);\n  overflow-y: scroll;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.2rem;\n}\n.sticky-top[_ngcontent-%COMP%] {\n  position: sticky;\n  top: -1px;\n  z-index: 1020;\n}\n/*# sourceMappingURL=consignment-status-popup.component.css.map */"] });
var ConsignmentStatusPopupComponent = _ConsignmentStatusPopupComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ConsignmentStatusPopupComponent, { className: "ConsignmentStatusPopupComponent", filePath: "src\\app\\main\\operation\\consignment\\consignment-new\\consignment-status-popup\\consignment-status-popup.component.ts", lineNumber: 13 });
})();

// src/app/main/operation/consignment/consignment-new/consignment-new.component.ts
var _c06 = () => ({ "width": "100%" });
var _c13 = () => ({ width: "180" });
var _c22 = () => ({ "width": "84rem" });
var _c32 = () => ({ "width": "130rem" });
function ConsignmentNewComponent_ng_template_19_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_20_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_25_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_26_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_31_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_37_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_38_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_43_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_44_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_49_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_50_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_56_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_57_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_62_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_67_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_72_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_73_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_78_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_79_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_87_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_94_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_div_95_mat_error_7_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_div_95_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 17)(1, "p", 18);
    \u0275\u0275text(2, "Invoice URL ");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "p-inputGroup");
    \u0275\u0275element(4, "input", 178);
    \u0275\u0275elementStart(5, "button", 45);
    \u0275\u0275listener("click", function ConsignmentNewComponent_div_95_Template_button_click_5_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.downloadInvoice(ctx_r1.shipmentInfo.controls.invoiceUrl.value));
    });
    \u0275\u0275element(6, "i", 179);
    \u0275\u0275elementEnd()();
    \u0275\u0275template(7, ConsignmentNewComponent_div_95_mat_error_7_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    let tmp_2_0;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_2_0 = ctx_r1.shipmentInfo.get("invoiceUrl")) == null ? null : tmp_2_0.invalid) && ctx_r1.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance(3);
    \u0275\u0275property("ngIf", ctx_r1.errorHandlingShipment("invoiceUrl"));
  }
}
function ConsignmentNewComponent_ng_template_100_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_101_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_106_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_107_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_div_108_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_div_108_mat_error_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_div_108_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 180)(1, "p", 18);
    \u0275\u0275text(2, "COD Collection Mode");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "p-dropdown", 181);
    \u0275\u0275template(4, ConsignmentNewComponent_div_108_ng_template_4_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(5, ConsignmentNewComponent_div_108_mat_error_5_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    let tmp_5_0;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(7, _c06));
    \u0275\u0275property("showClear", true)("options", ctx_r1.codCollectionMode)("ngClass", ((tmp_5_0 = ctx_r1.shipmentInfo.get("codCollectionMode")) == null ? null : tmp_5_0.invalid) && ctx_r1.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(8, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r1.errorHandlingShipment("codCollectionMode"));
  }
}
function ConsignmentNewComponent_div_109_mat_error_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_div_109_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 182)(1, "p", 18);
    \u0275\u0275text(2, "COD Amount");
    \u0275\u0275elementEnd();
    \u0275\u0275element(3, "input", 183);
    \u0275\u0275template(4, ConsignmentNewComponent_div_109_mat_error_4_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    let tmp_2_0;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275property("ngClass", ((tmp_2_0 = ctx_r1.shipmentInfo.get("codAmount")) == null ? null : tmp_2_0.invalid) && ctx_r1.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r1.errorHandlingShipment("codAmount"));
  }
}
function ConsignmentNewComponent_div_110_mat_error_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_div_110_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 184)(1, "p", 18);
    \u0275\u0275text(2, "COD Favor Of");
    \u0275\u0275elementEnd();
    \u0275\u0275element(3, "input", 185);
    \u0275\u0275template(4, ConsignmentNewComponent_div_110_mat_error_4_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    let tmp_2_0;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275property("ngClass", ((tmp_2_0 = ctx_r1.shipmentInfo.get("codFavorOf")) == null ? null : tmp_2_0.invalid) && ctx_r1.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r1.errorHandlingShipment("codFavorOf"));
  }
}
function ConsignmentNewComponent_ng_template_119_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th");
    \u0275\u0275text(2, "Piece No");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "th");
    \u0275\u0275text(4, "Product Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "th");
    \u0275\u0275text(6, "No of Items");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "th");
    \u0275\u0275text(8, "Total Value");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "th");
    \u0275\u0275text(10, "Currency");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "th", 186);
    \u0275\u0275text(12, " Actions");
    \u0275\u0275elementEnd()();
  }
}
function ConsignmentNewComponent_ng_template_120_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_ng_template_120_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr", 15)(1, "td");
    \u0275\u0275element(2, "input", 187);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "td");
    \u0275\u0275element(4, "input", 188);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "td");
    \u0275\u0275element(6, "input", 189);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "td");
    \u0275\u0275element(8, "input", 190);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "td")(10, "p-dropdown", 191);
    \u0275\u0275template(11, ConsignmentNewComponent_ng_template_120_ng_template_11_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(12, "td", 192)(13, "img", 193);
    \u0275\u0275listener("click", function ConsignmentNewComponent_ng_template_120_Template_img_click_13_listener($event) {
      \u0275\u0275restoreView(_r5);
      const op_r6 = \u0275\u0275reference(15);
      return \u0275\u0275resetView(op_r6.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(14, "p-overlayPanel", null, 1)(16, "div", 194)(17, "button", 195);
    \u0275\u0275listener("click", function ConsignmentNewComponent_ng_template_120_Template_button_click_17_listener() {
      const i_r7 = \u0275\u0275restoreView(_r5).rowIndex;
      const op_r6 = \u0275\u0275reference(15);
      const ctx_r1 = \u0275\u0275nextContext();
      ctx_r1.dimension(ctx_r1.pageToken.pageflow, "piece", i_r7);
      return \u0275\u0275resetView(op_r6.hide());
    });
    \u0275\u0275element(18, "img", 196);
    \u0275\u0275text(19, "Dimension");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "button", 195);
    \u0275\u0275listener("click", function ConsignmentNewComponent_ng_template_120_Template_button_click_20_listener() {
      const i_r7 = \u0275\u0275restoreView(_r5).rowIndex;
      const op_r6 = \u0275\u0275reference(15);
      const ctx_r1 = \u0275\u0275nextContext();
      ctx_r1.opendialog(ctx_r1.pageToken.pageflow, i_r7);
      return \u0275\u0275resetView(op_r6.hide());
    });
    \u0275\u0275element(21, "img", 197);
    \u0275\u0275text(22, "Item");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(23, "button", 195);
    \u0275\u0275listener("click", function ConsignmentNewComponent_ng_template_120_Template_button_click_23_listener() {
      const ctx_r7 = \u0275\u0275restoreView(_r5);
      const i_r7 = ctx_r7.rowIndex;
      const rowData_r9 = ctx_r7.$implicit;
      const op_r6 = \u0275\u0275reference(15);
      const ctx_r1 = \u0275\u0275nextContext();
      ctx_r1.removePieceDetail(i_r7, rowData_r9);
      return \u0275\u0275resetView(op_r6.hide());
    });
    \u0275\u0275element(24, "img", 198);
    \u0275\u0275text(25, "Delete");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "button", 195);
    \u0275\u0275listener("click", function ConsignmentNewComponent_ng_template_120_Template_button_click_26_listener() {
      const i_r7 = \u0275\u0275restoreView(_r5).rowIndex;
      const op_r6 = \u0275\u0275reference(15);
      const ctx_r1 = \u0275\u0275nextContext();
      ctx_r1.imageupload(ctx_r1.pageToken.pageflow, i_r7);
      return \u0275\u0275resetView(op_r6.hide());
    });
    \u0275\u0275element(27, "img", 199);
    \u0275\u0275text(28, "Image");
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    const item_r10 = ctx.$implicit;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275property("formGroup", item_r10);
    \u0275\u0275advance(10);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(8, _c06));
    \u0275\u0275property("showClear", true)("options", ctx_r1.currencyDropdown)("panelStyle", \u0275\u0275pureFunction0(9, _c06));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(10, _c13));
  }
}
function ConsignmentNewComponent_mat_error_133_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_138_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_143_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_148_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_153_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_158_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_163_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_168_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_173_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_178_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_183_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_188_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_193_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_198_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_203_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_208_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_213_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_218_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_223_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_228_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_233_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_234_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_247_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_252_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_257_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_262_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_267_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_268_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_273_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_274_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_279_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_280_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_285_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_286_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_291_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_296_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_301_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_306_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_311_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_316_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_321_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_326_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_339_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_344_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_349_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_354_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_355_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_360_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_361_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_366_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_367_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_ng_template_372_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 176);
  }
}
function ConsignmentNewComponent_mat_error_373_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_378_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_383_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_388_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_393_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_398_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_403_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_408_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_413_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_418_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_p_tabPanel_424_ng_template_3_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th");
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r12 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r12.header, " ");
  }
}
function ConsignmentNewComponent_p_tabPanel_424_ng_template_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 205);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 206);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, ConsignmentNewComponent_p_tabPanel_424_ng_template_3_th_3_Template, 2, 1, "th", 204);
    \u0275\u0275elementStart(4, "th", 207);
    \u0275\u0275text(5, " Piece Details ");
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const columns_r13 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function ConsignmentNewComponent_p_tabPanel_424_ng_template_4_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r15 = \u0275\u0275nextContext().$implicit;
    const rowData_r16 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r16[col_r15.field], " ");
  }
}
function ConsignmentNewComponent_p_tabPanel_424_ng_template_4_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275text(0);
    \u0275\u0275pipe(1, "date");
  }
  if (rf & 2) {
    const rowData_r16 = \u0275\u0275nextContext(2).$implicit;
    \u0275\u0275textInterpolate1(" ", \u0275\u0275pipeBind2(1, 1, rowData_r16.createdOn, "dd-MM-yyyy HH:mm"), " ");
  }
}
function ConsignmentNewComponent_p_tabPanel_424_ng_template_4_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, ConsignmentNewComponent_p_tabPanel_424_ng_template_4_td_3_ng_container_1_Template, 2, 1, "ng-container", 213)(2, ConsignmentNewComponent_p_tabPanel_424_ng_template_4_td_3_ng_template_2_Template, 2, 4, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r15 = ctx.$implicit;
    const dateTemplate_r17 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r15.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r15.format !== "date")("ngIfElse", dateTemplate_r17);
  }
}
function ConsignmentNewComponent_p_tabPanel_424_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    const _r14 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr")(1, "td", 208);
    \u0275\u0275element(2, "p-tableCheckbox", 209);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, ConsignmentNewComponent_p_tabPanel_424_ng_template_4_td_3_Template, 4, 4, "td", 210);
    \u0275\u0275elementStart(4, "td", 211)(5, "img", 212);
    \u0275\u0275listener("click", function ConsignmentNewComponent_p_tabPanel_424_ng_template_4_Template_img_click_5_listener() {
      const rowData_r16 = \u0275\u0275restoreView(_r14).$implicit;
      const ctx_r1 = \u0275\u0275nextContext(2);
      return \u0275\u0275resetView(ctx_r1.cnTablePopup("Popup", rowData_r16));
    });
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const rowData_r16 = ctx.$implicit;
    const columns_r18 = ctx.columns;
    \u0275\u0275advance(2);
    \u0275\u0275property("disabled", true)("value", rowData_r16);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r18);
  }
}
function ConsignmentNewComponent_p_tabPanel_424_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 214);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function ConsignmentNewComponent_p_tabPanel_424_mat_expansion_panel_7_mat_expansion_panel_6_tr_19_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr", 223)(1, "td", 224);
    \u0275\u0275text(2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "td", 224);
    \u0275\u0275text(4);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "td", 224);
    \u0275\u0275text(6);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "td", 224);
    \u0275\u0275text(8);
    \u0275\u0275pipe(9, "date");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "td", 224);
    \u0275\u0275text(11);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const item_r21 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(item_r21.pieceId);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(item_r21.pieceType);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(item_r21.pieceTypeDescription);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate1("", \u0275\u0275pipeBind2(9, 5, item_r21.pieceTimeStamp, "dd-MM-yyyy HH:mm"), " ");
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate(item_r21.updatedBy);
  }
}
function ConsignmentNewComponent_p_tabPanel_424_mat_expansion_panel_7_mat_expansion_panel_6_Template(rf, ctx) {
  if (rf & 1) {
    const _r19 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "mat-expansion-panel")(1, "mat-expansion-panel-header", 216);
    \u0275\u0275listener("click", function ConsignmentNewComponent_p_tabPanel_424_mat_expansion_panel_7_mat_expansion_panel_6_Template_mat_expansion_panel_header_click_1_listener() {
      const line_r20 = \u0275\u0275restoreView(_r19).$implicit;
      const ctx_r1 = \u0275\u0275nextContext(3);
      return \u0275\u0275resetView(ctx_r1.onPieceIdClick(line_r20.pieceId));
    });
    \u0275\u0275elementStart(2, "mat-panel-title");
    \u0275\u0275text(3);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div", 217)(5, "table", 218)(6, "thead", 219)(7, "tr")(8, "th", 220);
    \u0275\u0275text(9, "Piece ID");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "th", 220);
    \u0275\u0275text(11, "Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "th", 220);
    \u0275\u0275text(13, "Description");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(14, "th", 220);
    \u0275\u0275text(15, "Time");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "th", 220);
    \u0275\u0275text(17, "User");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(18, "tbody", 221);
    \u0275\u0275template(19, ConsignmentNewComponent_p_tabPanel_424_mat_expansion_panel_7_mat_expansion_panel_6_tr_19_Template, 12, 8, "tr", 222);
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    const line_r20 = ctx.$implicit;
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1(" Piece Id: ", line_r20.pieceId, " ");
    \u0275\u0275advance(16);
    \u0275\u0275property("ngForOf", line_r20.lines);
  }
}
function ConsignmentNewComponent_p_tabPanel_424_mat_expansion_panel_7_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-expansion-panel")(1, "mat-expansion-panel-header")(2, "mat-panel-title");
    \u0275\u0275text(3);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div", 215)(5, "mat-accordion");
    \u0275\u0275template(6, ConsignmentNewComponent_p_tabPanel_424_mat_expansion_panel_7_mat_expansion_panel_6_Template, 20, 2, "mat-expansion-panel", 204);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const houseAirwayBill_r22 = ctx.$implicit;
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1(" Consignment No: ", houseAirwayBill_r22.houseAirwayBill, " ");
    \u0275\u0275advance(3);
    \u0275\u0275property("ngForOf", ctx_r1.getReorderedGroupByPieceId());
  }
}
function ConsignmentNewComponent_p_tabPanel_424_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "p-tabPanel", 200)(1, "p-table", 201, 2);
    \u0275\u0275twoWayListener("selectionChange", function ConsignmentNewComponent_p_tabPanel_424_Template_p_table_selectionChange_1_listener($event) {
      \u0275\u0275restoreView(_r11);
      const ctx_r1 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r1.selectedCNTable, $event) || (ctx_r1.selectedCNTable = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(3, ConsignmentNewComponent_p_tabPanel_424_ng_template_3_Template, 6, 2, "ng-template", 64)(4, ConsignmentNewComponent_p_tabPanel_424_ng_template_4_Template, 6, 3, "ng-template", 65)(5, ConsignmentNewComponent_p_tabPanel_424_ng_template_5_Template, 4, 1, "ng-template", 202);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "mat-accordion", 203);
    \u0275\u0275template(7, ConsignmentNewComponent_p_tabPanel_424_mat_expansion_panel_7_Template, 7, 2, "mat-expansion-panel", 204);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "div", 58)(9, "button", 66);
    \u0275\u0275listener("click", function ConsignmentNewComponent_p_tabPanel_424_Template_button_click_9_listener() {
      \u0275\u0275restoreView(_r11);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.activeIndex = ctx_r1.activeIndex - 1);
    });
    \u0275\u0275text(10, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_p_tabPanel_424_Template_button_click_11_listener() {
      \u0275\u0275restoreView(_r11);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.activeIndex = ctx_r1.activeIndex + 1);
    });
    \u0275\u0275text(12, "Next");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance();
    \u0275\u0275property("columns", ctx_r1.cnCols)("value", ctx_r1.cnTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx_r1.selectedCNTable);
    \u0275\u0275property("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(7, _c22));
    \u0275\u0275advance(6);
    \u0275\u0275property("ngForOf", ctx_r1.groupByHouseAirway);
  }
}
function ConsignmentNewComponent_mat_error_440_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_445_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_450_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_455_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_mat_error_460_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 177)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_3_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th");
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r24 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r24.header, " ");
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 205);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 206);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, ConsignmentNewComponent_p_tabPanel_466_ng_template_3_th_3_Template, 2, 1, "th", 204);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r25 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r25);
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_4_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r26 = \u0275\u0275nextContext().$implicit;
    const rowData_r27 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r27[col_r26.field], " ");
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_4_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275text(0);
    \u0275\u0275pipe(1, "date");
  }
  if (rf & 2) {
    const rowData_r27 = \u0275\u0275nextContext(2).$implicit;
    \u0275\u0275textInterpolate1(" ", \u0275\u0275pipeBind2(1, 1, rowData_r27.createdOn, "dd-MM-yyyy HH:mm"), " ");
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_4_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, ConsignmentNewComponent_p_tabPanel_466_ng_template_4_td_3_ng_container_1_Template, 2, 1, "ng-container", 213)(2, ConsignmentNewComponent_p_tabPanel_466_ng_template_4_td_3_ng_template_2_Template, 2, 4, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r26 = ctx.$implicit;
    const dateTemplate_r28 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r26.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r26.format !== "date")("ngIfElse", dateTemplate_r28);
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td", 208);
    \u0275\u0275element(2, "p-tableCheckbox", 209);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, ConsignmentNewComponent_p_tabPanel_466_ng_template_4_td_3_Template, 4, 4, "td", 210);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r27 = ctx.$implicit;
    const columns_r29 = ctx.columns;
    \u0275\u0275advance(2);
    \u0275\u0275property("disabled", true)("value", rowData_r27);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r29);
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_5_ng_container_2_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275elementStart(1, "td");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r30 = \u0275\u0275nextContext().$implicit;
    const ctx_r1 = \u0275\u0275nextContext(3);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate1(" ", ctx_r1.calculateFooterTotal(col_r30.field) != 0 ? ctx_r1.calculateFooterTotal(col_r30.field) : "", " ");
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_5_ng_container_2_ng_container_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275element(1, "td");
    \u0275\u0275elementContainerEnd();
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_5_ng_container_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275template(1, ConsignmentNewComponent_p_tabPanel_466_ng_template_5_ng_container_2_ng_container_1_Template, 3, 1, "ng-container", 228)(2, ConsignmentNewComponent_p_tabPanel_466_ng_template_5_ng_container_2_ng_container_2_Template, 2, 0, "ng-container", 228);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r30 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r30.showFooter);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", !col_r30.showFooter);
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr");
    \u0275\u0275element(1, "td");
    \u0275\u0275template(2, ConsignmentNewComponent_p_tabPanel_466_ng_template_5_ng_container_2_Template, 3, 2, "ng-container", 204);
    \u0275\u0275element(3, "td");
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r31 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("ngForOf", columns_r31);
  }
}
function ConsignmentNewComponent_p_tabPanel_466_ng_template_6_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 214);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function ConsignmentNewComponent_p_tabPanel_466_Template(rf, ctx) {
  if (rf & 1) {
    const _r23 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "p-tabPanel", 225)(1, "p-table", 226, 4);
    \u0275\u0275twoWayListener("selectionChange", function ConsignmentNewComponent_p_tabPanel_466_Template_p_table_selectionChange_1_listener($event) {
      \u0275\u0275restoreView(_r23);
      const ctx_r1 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r1.selectedConsignment, $event) || (ctx_r1.selectedConsignment = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(3, ConsignmentNewComponent_p_tabPanel_466_ng_template_3_Template, 4, 2, "ng-template", 64)(4, ConsignmentNewComponent_p_tabPanel_466_ng_template_4_Template, 4, 3, "ng-template", 65)(5, ConsignmentNewComponent_p_tabPanel_466_ng_template_5_Template, 4, 1, "ng-template", 227)(6, ConsignmentNewComponent_p_tabPanel_466_ng_template_6_Template, 4, 1, "ng-template", 202);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "div", 58)(8, "button", 66);
    \u0275\u0275listener("click", function ConsignmentNewComponent_p_tabPanel_466_Template_button_click_8_listener() {
      \u0275\u0275restoreView(_r23);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.activeIndex = ctx_r1.activeIndex - 1);
    });
    \u0275\u0275text(9, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_p_tabPanel_466_Template_button_click_10_listener() {
      \u0275\u0275restoreView(_r23);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.saveBilling());
    });
    \u0275\u0275text(11, "Save");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance();
    \u0275\u0275property("columns", ctx_r1.cols)("value", ctx_r1.billingTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx_r1.selectedConsignment);
    \u0275\u0275property("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(6, _c32));
  }
}
var _ConsignmentNewComponent = class _ConsignmentNewComponent {
  constructor(cs, spin, route, router, path, fb, service, messageService, cas, auth, el, dialog, productService, customerService, consignorService, download) {
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
    this.el = el;
    this.dialog = dialog;
    this.productService = productService;
    this.customerService = customerService;
    this.consignorService = consignorService;
    this.download = download;
    this.activeIndex = 0;
    this.panelOpenState = false;
    this.status = [];
    this.partnerType = [];
    this.paymentType = [];
    this.incoTerms = [];
    this.codCollectionMode = [];
    this.disabledCarrier = true;
    this.disabledSender = true;
    this.disabledDelivery = true;
    this.disabledBilling = true;
    this.disabledConsignment = true;
    this.disabledPiece = true;
    this.disabledDelivered = true;
    this.disabledReturn = true;
    this.shipmentInfo = this.fb.group({
      companyId: [this.auth.companyId],
      priority: [],
      incoTerms: [],
      partnerType: ["", Validators.required],
      consignmentId: [],
      partnerId: [, Validators.required],
      partnerName: [,],
      statusId: [,],
      paymentType: [,],
      productId: [],
      productName: [],
      subProductId: [],
      subProductName: [],
      serviceTypeId: [, Validators.required],
      serviceTypeText: [],
      manufacturer: [],
      masterAirwayBill: [],
      houseAirwayBill: [],
      noOfPieceHawb: [],
      noOfPackageMawb: [],
      countryOfOrigin: [],
      countryOfDestination: [],
      consignmentType: [,],
      customerCode: [],
      customerId: [],
      codAmount: [],
      codCollectionMode: [],
      codFavorOf: [],
      customerReferenceNumber: [],
      actionType: [],
      movementType: [],
      forwardReferenceNumber: [],
      workerCode: [],
      loadType: [,],
      loadTypeId: [, Validators.required],
      courierAccount: [],
      courierPartner: [],
      invoiceNumber: [],
      courierPartnerReferenceNumber: [],
      invoiceAmount: [],
      invoiceUrl: []
    });
    this.carrierInfo = this.fb.group({});
    this.OriginDetails = this.fb.group({
      accountId: [],
      addressHubCode: [,],
      addressLine1: [],
      addressLine2: [],
      alternatePhone: [],
      city: [],
      companyName: [],
      country: [],
      district: [],
      email: [],
      latitude: [],
      longitude: [],
      name: [],
      phone: [],
      pinCode: [],
      state: []
    });
    this.DestinationDetails = this.fb.group({
      accountId: [,],
      addressHubCode: [],
      addressLine1: [],
      addressLine2: [],
      alternatePhone: [],
      city: [,],
      companyName: [],
      country: [],
      district: [],
      email: [],
      latitude: [],
      longitude: [],
      name: [],
      phone: [],
      pinCode: [],
      state: []
    });
    this.senderInfo = this.fb.group({
      shipperId: [],
      shipperName: [],
      originDetails: this.OriginDetails
    });
    this.deliveryInfo = this.fb.group({
      consigneeName: [,],
      consigneeCivilId: [],
      destinationDetails: this.DestinationDetails
    });
    this.billing = this.fb.group({
      // incoTerms: [],
      // paymentType: [],
      //currency: [],
      // freightCurrency: [],
      //freightCharges: [],
      countryOfSupply: [],
      declaredValue: [],
      // consignmentCurrency: [],
      // consignmentValue: [],
      //actualCurrency: [],
      totalDuty: [],
      customsCurrency: [],
      costPerShipment: [],
      specialApprovalValue: [],
      // codAmount: [],
      // codFavorOf: [],
      // codCollectionMode: [],
      declaredValueWithoutTax: [],
      // invoiceAmount: [],
      // invoiceUrl: [],
      productCode: [],
      //  amount: [],
      //isCustomsDeclarable: [],
      iata: [],
      dduCharge: [],
      customsValue: [],
      specialApprovalCharge: [],
      exchangeRate: [],
      consignmentValueLocal: [],
      dutyPercentage: ["5%"],
      addInsurance: [],
      customsInsurance: [],
      addIata: [],
      actualDuty: [],
      calculatedTotalDuty: []
    });
    this.consignment = this.fb.group({
      length: [],
      width: [],
      height: [],
      dimensionUnit: [],
      volume: [],
      volumeUnit: [],
      weight: [],
      weightUnit: [],
      invoiceDate: [,],
      invoiceDateFE: [/* @__PURE__ */ new Date()],
      invoiceSupplierName: [],
      goodsDescription: [],
      notifyParty: [],
      consignmentCurrency: [],
      consignmentValue: [],
      partnerHouseAirwayBill: [],
      partnerMasterAirwayBill: [],
      airportOriginCode: [],
      flightArrivalTime: [],
      noOfPackages: [],
      flightName: [],
      flightNo: [],
      packageType: [],
      netWeight: [],
      grossWeight: []
    });
    this.piece = this.fb.group({
      pieceDetails: this.fb.array([])
    });
    this.form = this.fb.group({
      updatedBy: [,],
      updatedOn: [""],
      createdOn: [""],
      createdBy: [,],
      originDetails: []
    });
    this.submitted = false;
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.companyIdList = [];
    this.districtIdList = [];
    this.productIdList = [];
    this.subProductIdList = [];
    this.serviceTypeIdList = [];
    this.consignmentTypeIdList = [];
    this.loadTypeIdList = [];
    this.countryIdListOrigin = [];
    this.countryIdListDestination = [];
    this.cityIdList = [];
    this.provinceIdList = [];
    this.partnerName = [];
    this.currencyDropdown = [];
    this.partnerNameList = [];
    this.billingTable = [];
    this.selectedConsignment = [];
    this.cols = [];
    this.cnTable = [];
    this.selectedCNTable = [];
    this.cnCols = [];
    this.selectedPieceId = null;
    this.mainForm = new FormGroup({});
    this.showPaymentTypeFields = false;
    this.subProductValueList = [];
    this.selectedFiles = null;
    this.imageDetailsTable = [];
    this.status = [
      { value: "17", label: "Inactive" },
      { value: "16", label: "Active" }
    ];
    this.partnerType = [
      { value: "customer", label: "Customer" },
      { value: "consignor", label: "Consignor" }
    ];
    this.paymentType = [
      { value: "Prepaid", label: "Prepaid" },
      { value: "COD", label: "COD" }
    ];
    this.incoTerms = [
      { value: "DDU", label: "DDU" },
      { value: "DDP", label: "DDP" }
    ];
    this.codCollectionMode = [
      { value: "Cash", label: "Cash" },
      { value: "Cheque", label: "Cheque" },
      { value: "Online", label: "Online" },
      { value: "Card", label: "Card" }
    ];
  }
  initPieceDetail() {
    const control = this.piece.controls.pieceDetails;
    return this.fb.group({
      codAmount: [""],
      declaredValue: [""],
      description: [""],
      dimensionUnit: [""],
      height: [""],
      itemDetails: this.fb.array([]),
      length: [""],
      packReferenceNumber: [control.value.length + 1],
      masterAirwayBill: [""],
      partnerId: [""],
      houseAirwayBill: [""],
      pieceProductCode: [""],
      tags: [""],
      partnerType: [""],
      pieceId: [""],
      referenceField1: [""],
      referenceField10: [""],
      referenceField11: [""],
      referenceField12: [""],
      referenceField13: [""],
      referenceField14: [""],
      referenceField15: [""],
      referenceField16: [""],
      referenceField17: [""],
      referenceField18: [""],
      referenceField19: [""],
      referenceField2: [""],
      referenceField20: [""],
      referenceField3: [""],
      referenceField4: [""],
      referenceField5: [""],
      referenceField6: [""],
      referenceField7: [""],
      referenceField8: [""],
      referenceField9: [""],
      referenceImageList: this.fb.array([]),
      volume: [""],
      volumeUnit: [""],
      weight: [""],
      weightUnit: [""],
      width: [""],
      hsCode: [""],
      pieceValue: [""],
      pieceCurrency: [""]
    });
  }
  initItemDetail() {
    return this.fb.group({
      codAmount: [""],
      declaredValue: [""],
      description: [""],
      dimensionUnit: [""],
      height: [""],
      hsCode: [""],
      imageRefId: [""],
      itemCode: [""],
      length: [""],
      partnerName: [""],
      partnerType: [""],
      pieceItemId: [""],
      referenceField1: [""],
      referenceField10: [""],
      referenceField11: [""],
      referenceField12: [""],
      referenceField13: [""],
      referenceField14: [""],
      referenceField15: [""],
      referenceField16: [""],
      referenceField17: [""],
      referenceField18: [""],
      referenceField19: [""],
      referenceField2: [""],
      referenceField20: [""],
      referenceField3: [""],
      referenceField4: [""],
      referenceField5: [""],
      referenceField6: [""],
      referenceField7: [""],
      referenceField8: [""],
      referenceField9: [""],
      referenceImageList: this.fb.array([]),
      volume: [""],
      volumeUnit: [""],
      weight: [""],
      weightUnit: [""],
      width: [""],
      quantity: [""],
      unitValue: [""],
      currency: [""]
    });
  }
  addPieceDetail() {
    const control = this.piece.controls.pieceDetails;
    control.push(this.initPieceDetail());
  }
  removePieceDetail(index, data) {
    const control = this.piece.controls.pieceDetails;
    control.removeAt(index);
    this.service.DeletePiece(data.getRawValue()).subscribe({ next: (res) => {
    } });
  }
  addItemDetail(pieceIndex) {
    const control = this.piece.controls.pieceDetails.at(pieceIndex).get("itemDetails");
    control.push(this.initItemDetail());
  }
  removeItemDetail(pieceIndex, itemIndex) {
    const control = this.piece.controls.pieceDetails.at(pieceIndex).get("itemDetails");
    control.removeAt(itemIndex);
  }
  patchForm(shipmentData) {
    const piecesArray = this.piece.get("pieceDetails");
    shipmentData.pieceDetails.forEach((piece) => {
      piecesArray.push(this.patchPieceDetail(piece));
    });
  }
  patchPieceDetail(piece) {
    return this.fb.group({
      codAmount: [piece.codAmount],
      declaredValue: [piece.declaredValue],
      description: [piece.description],
      dimensionUnit: [piece.dimensionUnit],
      height: [piece.height],
      itemDetails: this.patchItemDetails(piece.itemDetails),
      length: [piece.length],
      packReferenceNumber: [piece.packReferenceNumber],
      masterAirwayBill: [piece.masterAirwayBill],
      houseAirwayBill: [piece.houseAirwayBill],
      partnerId: [piece.partnerId],
      partnerType: [piece.partnerType],
      pieceId: [piece.pieceId],
      referenceField1: [piece.referenceField1],
      referenceField10: [piece.referenceField10],
      referenceField11: [piece.referenceField11],
      referenceField12: [piece.referenceField12],
      referenceField13: [piece.referenceField13],
      referenceField14: [piece.referenceField14],
      referenceField15: [piece.referenceField15],
      referenceField16: [piece.referenceField16],
      referenceField17: [piece.referenceField17],
      referenceField18: [piece.referenceField18],
      referenceField19: [piece.referenceField19],
      referenceField2: [piece.referenceField2],
      referenceField20: [piece.referenceField20],
      referenceField3: [piece.referenceField3],
      referenceField4: [piece.referenceField4],
      referenceField5: [piece.referenceField5],
      referenceField6: [piece.referenceField6],
      referenceField7: [piece.referenceField7],
      referenceField8: [piece.referenceField8],
      referenceField9: [piece.referenceField9],
      referenceImageList: this.patchReferenceImages(piece.referenceImageList),
      volume: [piece.volume],
      volumeUnit: [piece.volumeUnit],
      weight: [piece.weight],
      weightUnit: [piece.weightUnit],
      width: [piece.width],
      hsCode: [piece.hsCode],
      pieceValue: [piece.pieceValue],
      pieceCurrency: [piece.pieceCurrency],
      pieceProductCode: [piece.pieceProductCode],
      tags: [piece.tags]
    });
  }
  patchItemDetails(itemDetails) {
    return this.fb.array(itemDetails.map((item) => this.patchItemDetail(item)));
  }
  patchItemDetail(item) {
    return this.fb.group({
      codAmount: [item.codAmount],
      declaredValue: [item.declaredValue],
      description: [item.description],
      dimensionUnit: [item.dimensionUnit],
      height: [item.height],
      hsCode: [item.hsCode],
      imageRefId: [item.imageRefId],
      itemCode: [item.itemCode],
      length: [item.length],
      partnerName: [item.partnerName],
      partnerType: [item.partnerType],
      pieceItemId: [item.pieceItemId],
      referenceField1: [item.referenceField1],
      referenceField10: [item.referenceField10],
      referenceField11: [item.referenceField11],
      referenceField12: [item.referenceField12],
      referenceField13: [item.referenceField13],
      referenceField14: [item.referenceField14],
      referenceField15: [item.referenceField15],
      referenceField16: [item.referenceField16],
      referenceField17: [item.referenceField17],
      referenceField18: [item.referenceField18],
      referenceField19: [item.referenceField19],
      referenceField2: [item.referenceField2],
      referenceField20: [item.referenceField20],
      referenceField3: [item.referenceField3],
      referenceField4: [item.referenceField4],
      referenceField5: [item.referenceField5],
      referenceField6: [item.referenceField6],
      referenceField7: [item.referenceField7],
      referenceField8: [item.referenceField8],
      referenceField9: [item.referenceField9],
      referenceImageList: this.patchReferenceImages(item.referenceImageList),
      volume: [item.volume],
      volumeUnit: [item.volumeUnit],
      weight: [item.weight],
      weightUnit: [item.weightUnit],
      width: [item.width],
      quantity: [item.quantity],
      unitValue: [item.unitValue],
      currency: [item.currency],
      masterAirwayBill: [item.masterAirwayBill],
      houseAirwayBill: [item.houseAirwayBill],
      partnerId: [item.partnerId]
    });
  }
  patchReferenceImages(referenceImageList) {
    if (referenceImageList == null) {
      return;
    }
    return this.fb.array(referenceImageList.map((image) => this.fb.group({
      imageRefId: [image.imageRefId],
      pdfUrl: [image.pdfUrl],
      referenceImageUrl: [image.referenceImageUrl]
    })));
  }
  errorHandlingShipment(control, error = "required") {
    const controlInstance = this.shipmentInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingCarrier(control, error = "required") {
    const controlInstance = this.carrierInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingSender(control, error = "required") {
    const controlInstance = this.senderInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingDelivery(control, error = "required") {
    const controlInstance = this.deliveryInfo.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingBilling(control, error = "required") {
    const controlInstance = this.billing.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  errorHandlingConsignment(control, error = "required") {
    const controlInstance = this.consignment.get(control);
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
    const dataToSend = ["Operations", "Consignment", this.pageToken.pageflow];
    this.path.setData(dataToSend);
    this.dropdownlist();
    this.shipmentInfo.controls.companyId.disable();
    if (this.pageToken.pageflow != "New") {
      this.fill(this.pageToken.line);
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    }
    this.callCNTable();
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.district.url,
      this.cas.dropdownlist.setup.product.url,
      this.cas.dropdownlist.setup.subProduct.url,
      this.cas.dropdownlist.setup.serviceType.url,
      this.cas.dropdownlist.setup.consignmentType.url,
      this.cas.dropdownlist.setup.loadType.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.city.url,
      this.cas.dropdownlist.setup.province.url,
      this.cas.dropdownlist.setup.currency.url
    ]).subscribe({
      next: (results) => {
        this.companyIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.company.key);
        this.districtIdList = this.cas.forLanguageFilter(results[1], this.cas.dropdownlist.setup.district.key);
        this.productIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.product.key);
        this.subProductIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.subProduct.key);
        this.serviceTypeIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.serviceType.key);
        this.consignmentTypeIdList = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.consignmentType.key);
        this.loadTypeIdList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.loadType.key);
        this.countryIdListOrigin = this.cas.forLanguageFilter(results[7], this.cas.dropdownlist.setup.country.key);
        this.countryIdListDestination = this.cas.forLanguageFilter(results[7], this.cas.dropdownlist.setup.country.key);
        this.cityIdList = this.cas.forLanguageFilter(results[8], this.cas.dropdownlist.setup.city.key);
        this.provinceIdList = this.cas.forLanguageFilter(results[9], this.cas.dropdownlist.setup.province.key);
        this.currencyDropdown = this.cas.foreachlist(results[10], this.cas.dropdownlist.setup.currency.key);
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  partnerTypeChanged() {
    if (this.shipmentInfo.controls.partnerType.value == "customer") {
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.partnerNameList = [];
      this.spin.show();
      this.customerService.search(obj).subscribe({
        next: (result) => {
          this.partnerNameList = this.cas.foreachlist(result, { key: "customerId", value: "customerName" });
          this.partnerNameList = this.cs.removeDuplicatesFromArrayList(this.partnerNameList, "value");
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
    if (this.shipmentInfo.controls.partnerType.value == "consignor") {
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.partnerNameList = [];
      this.spin.show();
      this.consignorService.search(obj).subscribe({
        next: (result) => {
          this.partnerNameList = this.cas.foreachlist(result, { key: "consignorId", value: "consignorName" });
          this.partnerNameList = this.cs.removeDuplicatesFromArrayList(this.partnerNameList, "value");
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
  fill(line) {
    this.form.patchValue(line);
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
    this.disabledConsignment = false;
    this.disabledPiece = false;
    this.disabledSender = false;
    this.disabledDelivery = false;
    this.disabledBilling = false;
    this.shipmentInfo.patchValue(line), this.consignment.patchValue(line), this.senderInfo.patchValue(line), this.deliveryInfo.patchValue(line), this.billing.patchValue(line);
    this.consignmentCurrency = this.consignment.controls.consignmentCurrency.value;
    this.consignmentValue = this.consignment.controls.consignmentValue.value;
    this.patchForm(line);
    if (this.consignment.controls.invoiceDate.value) {
      this.consignment.controls.invoiceDateFE.patchValue(this.cs.pCalendar(this.consignment.controls.invoiceDate.value));
    }
    this.partnerTypeChanged();
    this.shipmentInfo.controls.masterAirwayBill.disable();
    this.shipmentInfo.controls.houseAirwayBill.disable();
    this.callCNTableHeader();
    this.callItemLevel(line);
    this.callTableHeader();
  }
  // CN Tracking Code
  callCNTableHeader() {
    this.cnCols = [
      { field: "houseAirwayBill", header: "Consignment ID" },
      { field: "hawbType", header: "Type" },
      // { field: 'bagId', header: 'Bag ID' },
      { field: "hawbTypeDescription", header: "Description" },
      { field: "hubName", header: "Hub Name" },
      { field: "hawbTimeStamp", header: "Time", format: "date" },
      { field: "updatedBy", header: "User" }
    ];
  }
  onRowExpand(event) {
  }
  onRowCollapse(event) {
  }
  getColspan() {
    return this.cols.length + 2;
  }
  callCNTable() {
    let obj = {};
    obj.companyId = [this.auth.companyId];
    obj.languageId = [this.auth.languageId];
    obj.houseAirwayBill = [this.pageToken.line.houseAirwayBill];
    this.service.searchStatus(obj).subscribe({ next: (res) => {
      this.cnTable = res;
      this.groupByHouseAirway = this.cs.groupByDynamicField(res, "houseAirwayBill");
      this.groupByPieceId = this.cs.groupByDynamicField(this.groupByHouseAirway[0].lines, "pieceId");
      console.log(this.groupByHouseAirway);
      console.log(this.groupByPieceId);
    }, error: (err) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    } });
  }
  onPieceIdClick(pieceId) {
    this.selectedPieceId = pieceId;
  }
  getReorderedGroupByPieceId() {
    if (!this.selectedPieceId) {
      return this.groupByPieceId;
    }
    return [
      this.groupByPieceId.find((piece) => piece.pieceId === this.selectedPieceId),
      ...this.groupByPieceId.filter((piece) => piece.pieceId !== this.selectedPieceId)
    ];
  }
  cnTablePopup(data, line) {
    const dialogRef = this.dialog.open(ConsignmentStatusPopupComponent, {
      disableClose: true,
      width: "80%",
      height: "50%",
      maxWidth: "95%",
      position: { top: "6.5%", left: "14%" },
      data: { pageflow: data, code: line }
    });
  }
  callTableHeader() {
    this.cols = [
      { field: "consignmentCurrency", header: "Consignment Currency", showFooter: false },
      { field: "consignmentValue", header: "Consignment Value", showFooter: true },
      { field: "exchangeRate", header: "Exchange Rate", showFooter: false },
      { field: "iataCharge", header: "IATA", showFooter: false },
      { field: "customsInsurance", header: "Customs Insurance", showFooter: true },
      { field: "dutyPercentage", header: "Duty", showFooter: true },
      { field: "consignmentValueLocal", header: "Consignment Value Local", showFooter: true },
      { field: "addIata", header: "Add IATA", showFooter: true },
      { field: "addInsurance", header: "Add Insurance", showFooter: true },
      { field: "customsValue", header: "Custom", showFooter: true },
      { field: "calculatedTotalDuty", header: "Calculated Total duty", showFooter: true },
      { field: "dduCharge", header: "DDU Charge", showFooter: true },
      { field: "specialApprovalCharge", header: "Spl Approval Charge", showFooter: false },
      { field: "totalDuty", header: "Duty From Bayan", showFooter: false }
    ];
  }
  callItemLevel(line) {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.houseAirwayBill = [line.houseAirwayBill];
    this.service.search(obj).subscribe({ next: (res) => {
      this.billingTable = res;
    }, error: (err) => {
      this.spin.hide();
      this.cs.commonerrorNew(err);
    } });
  }
  calculateFooterTotal(field) {
    let total = 0;
    this.billingTable.forEach((item) => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(2));
  }
  opendialog(type = "New", index) {
    const dialogRef = this.dialog.open(ItemDetailsComponent, {
      disableClose: true,
      width: "90%",
      maxWidth: "95%",
      position: { top: "6.5%", left: "10%" },
      data: { pageflow: type, line: this.piece.controls.pieceDetails.at(index).get("itemDetails") }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const itemDetailsFormArray = this.piece.controls.pieceDetails.at(index).get("itemDetails");
        itemDetailsFormArray.clear();
        const getPieceForm = this.piece.controls.pieceDetails.at(index);
        getPieceForm.patchValue({
          pieceValue: result.pieceValue,
          length: result.length,
          width: result.width,
          height: result.height,
          weight: result.weight,
          tags: result.tags,
          volume: result.volume,
          weightUnit: result.weightUnit,
          volumeUnit: result.volumeUnit,
          dimensionUnit: result.dimensionUnit,
          pieceCurrency: result.currency
        });
        result.lines.forEach((item) => {
          itemDetailsFormArray.push(this.fb.group({
            codAmount: item.codAmount,
            declaredValue: item.declaredValue,
            description: item.description,
            dimensionUnit: item.dimensionUnit,
            height: item.height,
            hsCode: item.hsCode,
            imageRefId: item.imageRefId,
            itemCode: item.itemCode,
            length: item.length,
            partnerName: item.partnerName,
            partnerType: item.partnerType,
            pieceItemId: item.pieceItemId,
            volume: item.volume,
            volumeUnit: item.volumeUnit,
            weight: item.weight,
            weightUnit: item.weightUnit,
            width: item.width,
            quantity: item.quantity,
            unitValue: item.unitValue,
            currency: item.currency,
            masterAirwayBill: item.masterAirwayBill,
            partnerId: item.partnerId,
            houseAirwayBill: item.houseAirwayBill,
            referenceImageList: this.patchReferenceImages(item.referenceImageList)
          }));
        });
        console.log(this.piece);
      }
    });
  }
  dimension(type = "New", module, index) {
    const dialogRef = this.dialog.open(DimensionComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "25%" },
      data: { pageflow: type, module, line: this.piece.controls.pieceDetails.at(index) }
    });
    dialogRef.afterClosed().subscribe((result) => {
      const control = this.piece.controls.pieceDetails.at(index);
      control.patchValue(result);
    });
  }
  imageupload(type = "New", index) {
    const dialogRef = this.dialog.open(ImageUploadComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "25%" },
      data: { pageflow: type, line: this.piece.controls.pieceDetails.at(index).get("referenceImageList"), lineDetails: this.piece.controls.pieceDetails.at(index), type: "piece" }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const imageDetailsFormArray = this.piece.controls.pieceDetails.at(index).get("referenceImageList");
        imageDetailsFormArray.clear();
        result.forEach((image) => {
          imageDetailsFormArray.push(this.fb.group({
            imageRefId: image.imageRefId,
            pdfUrl: image.pdfUrl,
            referenceImageUrl: image.referenceImageUrl
          }));
        });
      }
    });
  }
  save() {
    this.submitted = true;
    if (this.form.invalid) {
      for (const control in this.form.controls) {
        const controlInstance = this.form.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          console.log(invalidControl);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: "smooth", block: "center" });
            break;
          }
        }
      }
    }
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
            detail: res.consignmentId + " has been updated successfully"
          });
          this.router.navigate(["/main/master/rate"]);
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
              detail: res.consignmentId + " has been created successfully"
            });
            this.router.navigate(["/main/master/rate"]);
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
  calculateVolume(formName) {
    const volume = formName.controls.length.value * formName.controls.width.value * formName.controls.height.value;
    formName.controls.volume.patchValue(volume);
  }
  saveShipment() {
    this.submitted = true;
    if (this.shipmentInfo.invalid) {
      for (const control in this.shipmentInfo.controls) {
        const controlInstance = this.shipmentInfo.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: "smooth", block: "center" });
            break;
          }
        }
      }
    }
    if (this.shipmentInfo.invalid) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        key: "br",
        detail: "Please fill required fields to continue"
      });
      return;
    } else {
      this.activeIndex = 1;
      this.disabledPiece = false;
      this.submitted = false;
    }
  }
  saveConsignment() {
    this.submitted = true;
    if (this.consignment.invalid) {
      for (const control in this.consignment.controls) {
        const controlInstance = this.consignment.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: "smooth", block: "center" });
            break;
          }
        }
      }
    }
    if (this.consignment.invalid) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        key: "br",
        detail: "Please fill required fields to continue"
      });
      return;
    } else {
      this.activeIndex = 3;
      this.submitted = false;
      this.disabledSender = false;
    }
  }
  savePiece() {
    const control = this.piece.controls.pieceDetails;
    if (control.value.length > 0) {
      this.consignment.controls.length.patchValue(control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.length), 0));
      this.consignment.controls.width.patchValue(control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.width), 0));
      this.consignment.controls.height.patchValue(control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.height), 0));
      this.consignment.controls.volume.patchValue(control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.volume), 0));
      this.consignment.controls.weight.patchValue(control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.weight), 0));
      this.consignment.controls.consignmentValue.patchValue(control.value.reduce((acc, item) => parseInt(acc) + parseInt(item.pieceValue), 0));
      this.consignment.controls.volumeUnit.patchValue(control.value[0].volumeUnit);
      this.consignment.controls.dimensionUnit.patchValue(control.value[0].dimensionUnit);
      this.consignment.controls.weightUnit.patchValue(control.value[0].weightUnit);
      this.consignment.controls.consignmentCurrency.patchValue(control.value[0].pieceCurrency);
    }
    this.activeIndex = 2;
    this.submitted = false;
    this.disabledConsignment = false;
  }
  saveSender() {
    this.submitted = true;
    if (this.senderInfo.invalid) {
      for (const control in this.senderInfo.controls) {
        const controlInstance = this.senderInfo.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: "smooth", block: "center" });
            break;
          }
        }
      }
    }
    if (this.senderInfo.invalid) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        key: "br",
        detail: "Please fill required fields to continue"
      });
      return;
    } else {
      this.activeIndex = 4;
      this.disabledDelivery = false;
      this.submitted = false;
    }
  }
  saveDelivery() {
    this.submitted = true;
    if (this.deliveryInfo.invalid) {
      for (const control in this.deliveryInfo.controls) {
        const controlInstance = this.deliveryInfo.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: "smooth", block: "center" });
            break;
          }
        }
      }
    }
    if (this.deliveryInfo.invalid) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        key: "br",
        detail: "Please fill required fields to continue"
      });
      return;
    } else {
      this.activeIndex = 5;
      this.submitted = false;
      this.disabledBilling = false;
    }
  }
  saveBilling() {
    this.submitted = true;
    if (this.billing.invalid) {
      for (const control in this.billing.controls) {
        const controlInstance = this.billing.get(control);
        if (controlInstance?.invalid) {
          const invalidControl = this.el.nativeElement.querySelector(`#${control}`);
          if (invalidControl) {
            invalidControl.scrollIntoView({ behavior: "smooth", block: "center" });
            break;
          }
        }
      }
    }
    if (this.billing.invalid) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        key: "br",
        detail: "Please fill required fields to continue"
      });
      return;
    } else {
      this.saveFinal();
    }
  }
  saveFinal() {
    this.mainForm = this.fb.group(__spreadProps(__spreadValues(__spreadValues(__spreadValues(__spreadValues(__spreadValues({}, this.shipmentInfo.getRawValue()), this.consignment.getRawValue()), this.senderInfo.getRawValue()), this.deliveryInfo.getRawValue()), this.billing.getRawValue()), {
      pieceDetails: this.piece.controls.pieceDetails,
      updatedBy: [,],
      updatedOn: [""],
      createdOn: [""],
      createdBy: [,],
      companyId: [this.auth.companyId],
      languageId: [this.auth.languageId],
      invoiceDate: this.cs.jsonDate(this.consignment.controls.invoiceDateFE.value)
    }));
    if (this.pageToken.pageflow != "New") {
      this.spin.show();
      this.service.Update([this.mainForm.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Updated",
            key: "br",
            detail: res[0].houseAirwayBill + " has been updated successfully"
          });
          this.spin.hide();
          this.router.navigate(["/main/operation/consignment"]);
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      this.spin.show();
      this.service.Create([this.mainForm.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Created",
            key: "br",
            detail: res[0].houseAirwayBill + " has been created successfully"
          });
          this.spin.hide();
          this.router.navigate(["/main/operation/consignment"]);
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
  paymentChange() {
    const paymentTypeValue = this.shipmentInfo.controls.paymentType.value;
    if (typeof paymentTypeValue === "string" && paymentTypeValue === "COD") {
      this.showPaymentTypeFields = true;
    } else {
      this.showPaymentTypeFields = false;
    }
  }
  productChanged() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.productId = [this.shipmentInfo.controls.productId.value];
    this.subProductIdList = [];
    this.spin.show();
    this.productService.search(obj).subscribe({
      next: (result) => {
        this.subProductIdList = this.cas.foreachlist(result, { key: "subProductName", value: "referenceField1" });
        this.subProductIdList = this.cs.removeDuplicatesFromArrayList(this.subProductIdList, "value");
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  selectFiles(event) {
    this.selectedFiles = event.target.files;
    this.uploadFile(event);
  }
  uploadFile(event) {
    if (!this.selectedFiles || this.selectedFiles.length === 0) {
      console.log("No files selected for upload.");
      return;
    }
    this.fileLocation = "/Invoice/";
    this.service.uploadFiles(this.selectedFiles, this.fileLocation).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: "success",
          summary: "Updated",
          key: "br",
          detail: "File uploaded successfully"
        });
        this.shipmentInfo.controls.invoiceUrl.patchValue(result[0].fileName);
        this.selectedFiles = null;
        this.clearFileInput(event.target);
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  clearFileInput(input) {
    input.value = "";
  }
  downloadInvoice(value) {
    let obj = {};
    obj.value = { imageRefId: value, referenceImageUrl: "/Invoice/" };
    this.download.downloadDocument(obj);
  }
};
_ConsignmentNewComponent.\u0275fac = function ConsignmentNewComponent_Factory(t) {
  return new (t || _ConsignmentNewComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(ElementRef), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(ProductService), \u0275\u0275directiveInject(CustomerService), \u0275\u0275directiveInject(ConsignorService), \u0275\u0275directiveInject(ConsignmentLabelComponent));
};
_ConsignmentNewComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ConsignmentNewComponent, selectors: [["app-consignment-new"]], decls: 467, vars: 314, consts: [["fileInput", ""], ["op", ""], ["consignmentStatusTag", ""], ["dateTemplate", ""], ["consignmentTag", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "routerLink", "/main/operation/consignment", "src", "./assets/common2x/close1.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem"], ["type", "button", "src", "./assets/common2x/tick.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click", "disabled"], [1, "bi", "bi-plus-square", "pr-2"], [1, "mt-4", "mx-auto"], [3, "activeIndexChange", "activeIndex"], ["header", "Shipment Info"], [3, "formGroup"], [1, "row", "scrollNew22"], [1, "col-3", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "companyId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["pTemplate", "filter"], ["class", "text-danger", 4, "ngIf"], ["id", "productId", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "productId", "appendTo", "body", 3, "onChange", "showClear", "options", "ngClass", "panelStyle"], ["id", "serviceTypeId", 1, "col-3", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["placeholder", "Select", "filter", "true", "formControlName", "serviceTypeId", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "partnerType", 1, "col-3", "marginFieldNew", "borderRadius12"], ["formControlName", "partnerType", "appendTo", "body", "placeholder", "Select", 3, "onChange", "showClear", "options", "ngClass", "panelStyle"], ["id", "partnerId", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "consignmentType", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "serviceTypeId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["id", "loadTypeId", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "loadTypeId", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "customerReferenceNumber", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "customerReferenceNumber", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "noOfPieceHawb", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "noOfPieceHawb", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "countryOfOrigin", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "countryOfOrigin", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["id", "countryOfDestination", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "countryOfDestination", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["id", "invoiceNumber", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "invoiceNumber", "pInputText", "", "appTrim", "", "placeholder", "Enter", 3, "ngClass"], ["type", "button", "pButton", "", 1, "bgBlack", "border-0", 3, "click"], [1, "bi", "bi-upload"], ["type", "file", 2, "display", "none", 3, "change"], ["id", "invoiceAmount", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "invoiceAmount", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["class", "col-3 marginFieldNew borderRadius12", 4, "ngIf"], ["id", "incoTerms", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "incoTerms", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "paymentType", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "paymentType", "appendTo", "body", 3, "onChange", "showClear", "options", "ngClass", "panelStyle"], ["class", "col-3 marginFieldNew borderRadius12", "id", "codCollectionMode", 4, "ngIf"], ["class", "col-3 marginFieldNew borderRadius12", "id", "codAmount", 4, "ngIf"], ["class", "col-3 marginFieldNew borderRadius12", "id", "codFavorOf", 4, "ngIf"], [1, "d-flex", "mt-1", "justify-content-end", 2, "position", "absolute", "right", "3.5%", "bottom", "7%"], ["routerLink", "/main/operation/consignment", 1, "buttom1", "textBlack", "mx-1"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["header", "Package Details", 3, "disabled"], [1, "mt-1", 3, "formGroup"], ["scrollHeight", "calc(100vh - 24.8rem)", 1, "paddingZero", 3, "scrollable", "value"], ["pTemplate", "header"], ["pTemplate", "body"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], ["header", "Consignment", 3, "disabled"], ["id", "partnerMasterAirwayBill", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "partnerMasterAirwayBill", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "partnerHouseAirwayBill", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "partnerHouseAirwayBill", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "airportOriginCode", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "airportOriginCode", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "flightNo", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "flightNo", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "flightArrivalTime", 1, "col-3", "marginFieldNew", "borderRadius12"], ["dateFormat", "dd/mm/yy", "formControlName", "flightArrivalTime", "appendTo", "body", "placeholder", "Select Date", 1, "w-100", "small-calendar", 3, "ngClass", "iconDisplay", "showIcon"], ["id", "noOfPackages", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "noOfPackages", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "flightName", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "flightName", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "length", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "length", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "change", "ngClass"], ["id", "width", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "width", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "change", "ngClass"], ["id", "height", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "height", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "change", "ngClass"], ["id", "dimensionUnit", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "dimensionUnit", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "weight", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "pKeyFilter", "num", "formControlName", "weight", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "weightUnit", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "weightUnit", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "volume", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "volume", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "volumeUnit", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "volumeUnit", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "netWeight", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "netWeight", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "grossWeight", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "grossWeight", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "invoiceDateFE", 1, "col-3", "marginFieldNew", "borderRadius12"], ["dateFormat", "dd/mm/yy", "formControlName", "invoiceDateFE", "appendTo", "body", "placeholder", "Select Date", 1, "w-100", "small-calendar", 3, "ngClass", "iconDisplay", "showIcon"], ["id", "goodsDescription", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "500", "formControlName", "goodsDescription", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "consignmentValue", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "500", "formControlName", "consignmentValue", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "consignmentCurrency", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "consignmentCurrency", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["header", "Origin", 3, "disabled"], ["id", "name", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "name", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "companyName", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "companyName", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "addressLine1", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "addressLine1", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "addressLine2", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "addressLine2", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "country", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "country", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "state", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "state", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "district", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "district", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "city", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "city", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "pinCode", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "pinCode", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "latitude", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "latitude", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "longitude", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "longitude", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "phone", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "phone", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "alternatePhone", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "alternatePhone", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "email", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "email", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "addressHubCode", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "addressHubCode", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "accountId", "formGroupName", "originDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "accountId", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["header", "Destination", 3, "disabled"], ["id", "name", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "addressLine1", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "addressLine2", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "country", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "state", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "district", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "city", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "pinCode", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "latitude", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "longitude", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "phone", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "alternatePhone", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "email", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "companyName", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "addressHubCode", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["id", "accountId", "formGroupName", "destinationDetails", 1, "col-3", "marginFieldNew", "borderRadius12"], ["header", "CN Tracking", 4, "ngIf"], ["header", "Billing", 3, "disabled"], [1, "row"], [1, "col-3", "marginFieldNew", "borderRadius12", 3, "formGroup"], ["maxlength", "500", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "value", "disabled"], ["id", "customsInsurance", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "customsInsurance", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "dutyPercentage", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "dutyPercentage", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "dduCharge", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "dduCharge", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "specialApprovalCharge", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "specialApprovalCharge", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "costPerShipment", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "costPerShipment", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["header", "Duty Charges", 4, "ngIf"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], [1, "text-danger"], ["maxlength", "500", "formControlName", "invoiceUrl", "pInputText", "", "appTrim", "", "placeholder", "Enter", 3, "ngClass"], [1, "bi", "bi-download"], ["id", "codCollectionMode", 1, "col-3", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "codCollectionMode", "appendTo", "body", 3, "showClear", "options", "ngClass", "panelStyle"], ["id", "codAmount", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "codAmount", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["id", "codFavorOf", 1, "col-3", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "codFavorOf", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], [2, "min-width", "9rem", "justify-content", "center"], ["placeholder", "Enter", "formControlName", "pieceId", 1, "inputborderLess", "pl-3"], ["placeholder", "Enter", "formControlName", "pieceProductCode", 1, "inputborderLess", "pl-3"], ["placeholder", "Enter", "formControlName", "tags", 1, "inputborderLess", "pl-3"], ["placeholder", "Enter", "formControlName", "pieceValue", 1, "inputborderLess", "pl-3"], ["placeholder", "Select", "filter", "true", "formControlName", "pieceCurrency", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [2, "padding-left", "1.5rem !important"], ["type", "button", "src", "./assets/common/actions.png", "alt", "", "srcset", "", 2, "height", "1.4rem", 3, "click"], [1, "d-flex", "flex-column"], ["mat-menu-item", "", 1, "w-100", 2, "width", "8rem", 3, "click"], ["src", "./assets/common/dimension.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/add.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/delete1x.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/image.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["header", "CN Tracking"], ["selectionMode", "multiple", "scrollHeight", "calc(100vh - 22.8rem)", "sortField", "hawbTimeStamp", "styleClass", "p-datatable-sm", 1, "custom-table", "d-none", 3, "selectionChange", "columns", "value", "scrollable", "selection", "sortOrder", "tableStyle"], ["pTemplate", "emptymessage"], [1, ""], [4, "ngFor", "ngForOf"], [2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [2, "width", "6rem"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "disabled", "value"], [3, "style", 4, "ngFor", "ngForOf"], [2, "width", "5rem", "text-align", "center"], ["src", "./assets/common/document.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem", "cursor", "pointer", 3, "click"], [4, "ngIf", "ngIfElse"], [1, "my-2", "w-100", "pl-3"], [1, "scrollNew27"], [3, "click"], [1, "scrollNew32"], [1, "table", "p-2"], [1, "sticky-top", "table-style"], ["scope", "col"], [1, "table-style-body"], ["style", "height: 20px !important;", 4, "ngFor", "ngForOf"], [2, "height", "20px !important"], ["scope", "row"], ["header", "Duty Charges"], ["selectionMode", "multiple", "scrollHeight", "calc(100vh - 22.8rem)", "sortField", "consignmentId", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "selection", "sortOrder", "tableStyle"], ["pTemplate", "footer"], [4, "ngIf"]], template: function ConsignmentNewComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 5)(1, "div", 6)(2, "p", 7);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 6);
    \u0275\u0275element(5, "img", 8);
    \u0275\u0275elementStart(6, "img", 9);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.saveFinal());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "button", 10);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.addPieceDetail());
    });
    \u0275\u0275element(8, "i", 11);
    \u0275\u0275text(9, " Add Piece");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(10, "div", 12)(11, "p-tabView", 13);
    \u0275\u0275twoWayListener("activeIndexChange", function ConsignmentNewComponent_Template_p_tabView_activeIndexChange_11_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.activeIndex, $event) || (ctx.activeIndex = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementStart(12, "p-tabPanel", 14)(13, "form", 15)(14, "div", 16)(15, "div", 17)(16, "p", 18);
    \u0275\u0275text(17, "Company");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(18, "p-dropdown", 19);
    \u0275\u0275template(19, ConsignmentNewComponent_ng_template_19_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(20, ConsignmentNewComponent_mat_error_20_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(21, "div", 22)(22, "p", 18);
    \u0275\u0275text(23, "Product");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(24, "p-dropdown", 23);
    \u0275\u0275listener("onChange", function ConsignmentNewComponent_Template_p_dropdown_onChange_24_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.productChanged());
    });
    \u0275\u0275template(25, ConsignmentNewComponent_ng_template_25_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(26, ConsignmentNewComponent_mat_error_26_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(27, "div", 24)(28, "p", 25);
    \u0275\u0275text(29, "Service Type ");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(30, "p-dropdown", 26);
    \u0275\u0275template(31, ConsignmentNewComponent_ng_template_31_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(32, ConsignmentNewComponent_mat_error_32_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(33, "div", 27)(34, "p", 25);
    \u0275\u0275text(35, "Partner Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(36, "p-dropdown", 28);
    \u0275\u0275listener("onChange", function ConsignmentNewComponent_Template_p_dropdown_onChange_36_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.partnerTypeChanged());
    });
    \u0275\u0275template(37, ConsignmentNewComponent_ng_template_37_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(38, ConsignmentNewComponent_mat_error_38_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(39, "div", 29)(40, "p", 25);
    \u0275\u0275text(41, "Partner");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(42, "p-dropdown", 30);
    \u0275\u0275template(43, ConsignmentNewComponent_ng_template_43_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(44, ConsignmentNewComponent_mat_error_44_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(45, "div", 31)(46, "p", 18);
    \u0275\u0275text(47, "Consignment Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(48, "p-dropdown", 32);
    \u0275\u0275template(49, ConsignmentNewComponent_ng_template_49_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(50, ConsignmentNewComponent_mat_error_50_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(51, "div", 33)(52, "p", 25);
    \u0275\u0275text(53, "Load Type ");
    \u0275\u0275element(54, "Code");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(55, "p-dropdown", 34);
    \u0275\u0275template(56, ConsignmentNewComponent_ng_template_56_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(57, ConsignmentNewComponent_mat_error_57_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(58, "div", 35)(59, "p", 18);
    \u0275\u0275text(60, "Customer Ref No");
    \u0275\u0275elementEnd();
    \u0275\u0275element(61, "input", 36);
    \u0275\u0275template(62, ConsignmentNewComponent_mat_error_62_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(63, "div", 37)(64, "p", 18);
    \u0275\u0275text(65, "No of packages");
    \u0275\u0275elementEnd();
    \u0275\u0275element(66, "input", 38);
    \u0275\u0275template(67, ConsignmentNewComponent_mat_error_67_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(68, "div", 39)(69, "p", 18);
    \u0275\u0275text(70, "Country of Origin");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(71, "p-dropdown", 40);
    \u0275\u0275template(72, ConsignmentNewComponent_ng_template_72_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(73, ConsignmentNewComponent_mat_error_73_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(74, "div", 41)(75, "p", 18);
    \u0275\u0275text(76, "Country of Destination");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(77, "p-dropdown", 42);
    \u0275\u0275template(78, ConsignmentNewComponent_ng_template_78_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(79, ConsignmentNewComponent_mat_error_79_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(80, "div", 43)(81, "p", 18);
    \u0275\u0275text(82, "Invoice Number");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(83, "p-inputGroup");
    \u0275\u0275element(84, "input", 44);
    \u0275\u0275elementStart(85, "button", 45);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_85_listener() {
      \u0275\u0275restoreView(_r1);
      const fileInput_r3 = \u0275\u0275reference(89);
      return \u0275\u0275resetView(fileInput_r3.click());
    });
    \u0275\u0275element(86, "i", 46);
    \u0275\u0275elementEnd()();
    \u0275\u0275template(87, ConsignmentNewComponent_mat_error_87_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(88, "input", 47, 0);
    \u0275\u0275listener("change", function ConsignmentNewComponent_Template_input_change_88_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.selectFiles($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(90, "div", 48)(91, "p", 18);
    \u0275\u0275text(92, "Invoice Amount ");
    \u0275\u0275elementEnd();
    \u0275\u0275element(93, "input", 49);
    \u0275\u0275template(94, ConsignmentNewComponent_mat_error_94_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(95, ConsignmentNewComponent_div_95_Template, 8, 2, "div", 50);
    \u0275\u0275elementStart(96, "div", 51)(97, "p", 18);
    \u0275\u0275text(98, "Inco Terms");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(99, "p-dropdown", 52);
    \u0275\u0275template(100, ConsignmentNewComponent_ng_template_100_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(101, ConsignmentNewComponent_mat_error_101_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(102, "div", 53)(103, "p", 18);
    \u0275\u0275text(104, "Payment Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(105, "p-dropdown", 54);
    \u0275\u0275listener("onChange", function ConsignmentNewComponent_Template_p_dropdown_onChange_105_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.paymentChange());
    });
    \u0275\u0275template(106, ConsignmentNewComponent_ng_template_106_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(107, ConsignmentNewComponent_mat_error_107_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275template(108, ConsignmentNewComponent_div_108_Template, 6, 9, "div", 55)(109, ConsignmentNewComponent_div_109_Template, 5, 2, "div", 56)(110, ConsignmentNewComponent_div_110_Template, 5, 2, "div", 57);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(111, "div", 58)(112, "button", 59);
    \u0275\u0275text(113, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(114, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_114_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.saveShipment());
    });
    \u0275\u0275text(115, "Next");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(116, "p-tabPanel", 61)(117, "form", 62)(118, "p-table", 63);
    \u0275\u0275template(119, ConsignmentNewComponent_ng_template_119_Template, 13, 0, "ng-template", 64)(120, ConsignmentNewComponent_ng_template_120_Template, 29, 11, "ng-template", 65);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(121, "div", 58)(122, "button", 66);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_122_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.activeIndex = ctx.activeIndex - 1);
    });
    \u0275\u0275text(123, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(124, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_124_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.savePiece());
    });
    \u0275\u0275text(125, "Next");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(126, "p-tabPanel", 67)(127, "form", 15)(128, "div", 16)(129, "div", 68)(130, "p", 18);
    \u0275\u0275text(131, "Partner MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(132, "input", 69);
    \u0275\u0275template(133, ConsignmentNewComponent_mat_error_133_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(134, "div", 70)(135, "p", 18);
    \u0275\u0275text(136, "Partner HAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(137, "input", 71);
    \u0275\u0275template(138, ConsignmentNewComponent_mat_error_138_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(139, "div", 72)(140, "p", 18);
    \u0275\u0275text(141, "Airport Origin Code");
    \u0275\u0275elementEnd();
    \u0275\u0275element(142, "input", 73);
    \u0275\u0275template(143, ConsignmentNewComponent_mat_error_143_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(144, "div", 74)(145, "p", 18);
    \u0275\u0275text(146, "Flight No");
    \u0275\u0275elementEnd();
    \u0275\u0275element(147, "input", 75);
    \u0275\u0275template(148, ConsignmentNewComponent_mat_error_148_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(149, "div", 76)(150, "p", 18);
    \u0275\u0275text(151, "Flight ETA");
    \u0275\u0275elementEnd();
    \u0275\u0275element(152, "p-calendar", 77);
    \u0275\u0275template(153, ConsignmentNewComponent_mat_error_153_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(154, "div", 78)(155, "p", 18);
    \u0275\u0275text(156, "No of Packages");
    \u0275\u0275elementEnd();
    \u0275\u0275element(157, "input", 79);
    \u0275\u0275template(158, ConsignmentNewComponent_mat_error_158_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(159, "div", 80)(160, "p", 18);
    \u0275\u0275text(161, "Flight Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(162, "input", 81);
    \u0275\u0275template(163, ConsignmentNewComponent_mat_error_163_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(164, "div", 82)(165, "p", 18);
    \u0275\u0275text(166, "Length");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(167, "input", 83);
    \u0275\u0275listener("change", function ConsignmentNewComponent_Template_input_change_167_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.calculateVolume(ctx.consignment));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275template(168, ConsignmentNewComponent_mat_error_168_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(169, "div", 84)(170, "p", 18);
    \u0275\u0275text(171, "Width");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(172, "input", 85);
    \u0275\u0275listener("change", function ConsignmentNewComponent_Template_input_change_172_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.calculateVolume(ctx.consignment));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275template(173, ConsignmentNewComponent_mat_error_173_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(174, "div", 86)(175, "p", 18);
    \u0275\u0275text(176, "Height");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(177, "input", 87);
    \u0275\u0275listener("change", function ConsignmentNewComponent_Template_input_change_177_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.calculateVolume(ctx.consignment));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275template(178, ConsignmentNewComponent_mat_error_178_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(179, "div", 88)(180, "p", 18);
    \u0275\u0275text(181, "Dimension Unit");
    \u0275\u0275elementEnd();
    \u0275\u0275element(182, "input", 89);
    \u0275\u0275template(183, ConsignmentNewComponent_mat_error_183_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(184, "div", 90)(185, "p", 18);
    \u0275\u0275text(186, "Weight");
    \u0275\u0275elementEnd();
    \u0275\u0275element(187, "input", 91);
    \u0275\u0275template(188, ConsignmentNewComponent_mat_error_188_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(189, "div", 92)(190, "p", 18);
    \u0275\u0275text(191, "Weight Unit");
    \u0275\u0275elementEnd();
    \u0275\u0275element(192, "input", 93);
    \u0275\u0275template(193, ConsignmentNewComponent_mat_error_193_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(194, "div", 94)(195, "p", 18);
    \u0275\u0275text(196, "Volume");
    \u0275\u0275elementEnd();
    \u0275\u0275element(197, "input", 95);
    \u0275\u0275template(198, ConsignmentNewComponent_mat_error_198_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(199, "div", 96)(200, "p", 18);
    \u0275\u0275text(201, "Volume Unit");
    \u0275\u0275elementEnd();
    \u0275\u0275element(202, "input", 97);
    \u0275\u0275template(203, ConsignmentNewComponent_mat_error_203_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(204, "div", 98)(205, "p", 18);
    \u0275\u0275text(206, "Net Weight");
    \u0275\u0275elementEnd();
    \u0275\u0275element(207, "input", 99);
    \u0275\u0275template(208, ConsignmentNewComponent_mat_error_208_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(209, "div", 100)(210, "p", 18);
    \u0275\u0275text(211, "Gross Weight");
    \u0275\u0275elementEnd();
    \u0275\u0275element(212, "input", 101);
    \u0275\u0275template(213, ConsignmentNewComponent_mat_error_213_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(214, "div", 102)(215, "p", 18);
    \u0275\u0275text(216, "Invoice Date");
    \u0275\u0275elementEnd();
    \u0275\u0275element(217, "p-calendar", 103);
    \u0275\u0275template(218, ConsignmentNewComponent_mat_error_218_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(219, "div", 104)(220, "p", 18);
    \u0275\u0275text(221, "Goods Description");
    \u0275\u0275elementEnd();
    \u0275\u0275element(222, "input", 105);
    \u0275\u0275template(223, ConsignmentNewComponent_mat_error_223_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(224, "div", 106)(225, "p", 18);
    \u0275\u0275text(226, "Consignment Value");
    \u0275\u0275elementEnd();
    \u0275\u0275element(227, "input", 107);
    \u0275\u0275template(228, ConsignmentNewComponent_mat_error_228_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(229, "div", 108)(230, "p", 18);
    \u0275\u0275text(231, "Consignment Currency");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(232, "p-dropdown", 109);
    \u0275\u0275template(233, ConsignmentNewComponent_ng_template_233_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(234, ConsignmentNewComponent_mat_error_234_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(235, "div", 58)(236, "button", 66);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_236_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.activeIndex = ctx.activeIndex - 1);
    });
    \u0275\u0275text(237, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(238, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_238_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.saveConsignment());
    });
    \u0275\u0275text(239, "Next");
    \u0275\u0275elementEnd()()()();
    \u0275\u0275elementStart(240, "p-tabPanel", 110)(241, "form", 15)(242, "div", 16)(243, "div", 111)(244, "p", 18);
    \u0275\u0275text(245, "Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(246, "input", 112);
    \u0275\u0275template(247, ConsignmentNewComponent_mat_error_247_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(248, "div", 113)(249, "p", 18);
    \u0275\u0275text(250, "Company Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(251, "input", 114);
    \u0275\u0275template(252, ConsignmentNewComponent_mat_error_252_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(253, "div", 115)(254, "p", 18);
    \u0275\u0275text(255, "Address Line 1 ");
    \u0275\u0275elementEnd();
    \u0275\u0275element(256, "input", 116);
    \u0275\u0275template(257, ConsignmentNewComponent_mat_error_257_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(258, "div", 117)(259, "p", 18);
    \u0275\u0275text(260, "Address Line 2 ");
    \u0275\u0275elementEnd();
    \u0275\u0275element(261, "input", 118);
    \u0275\u0275template(262, ConsignmentNewComponent_mat_error_262_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(263, "div", 119)(264, "p", 18);
    \u0275\u0275text(265, "Country");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(266, "p-dropdown", 120);
    \u0275\u0275template(267, ConsignmentNewComponent_ng_template_267_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(268, ConsignmentNewComponent_mat_error_268_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(269, "div", 121)(270, "p", 18);
    \u0275\u0275text(271, "State");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(272, "p-dropdown", 122);
    \u0275\u0275template(273, ConsignmentNewComponent_ng_template_273_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(274, ConsignmentNewComponent_mat_error_274_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(275, "div", 123)(276, "p", 18);
    \u0275\u0275text(277, "District");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(278, "p-dropdown", 124);
    \u0275\u0275template(279, ConsignmentNewComponent_ng_template_279_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(280, ConsignmentNewComponent_mat_error_280_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(281, "div", 125)(282, "p", 18);
    \u0275\u0275text(283, "City");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(284, "p-dropdown", 126);
    \u0275\u0275template(285, ConsignmentNewComponent_ng_template_285_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(286, ConsignmentNewComponent_mat_error_286_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(287, "div", 127)(288, "p", 18);
    \u0275\u0275text(289, "Pincode");
    \u0275\u0275elementEnd();
    \u0275\u0275element(290, "input", 128);
    \u0275\u0275template(291, ConsignmentNewComponent_mat_error_291_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(292, "div", 129)(293, "p", 18);
    \u0275\u0275text(294, "Latitude");
    \u0275\u0275elementEnd();
    \u0275\u0275element(295, "input", 130);
    \u0275\u0275template(296, ConsignmentNewComponent_mat_error_296_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(297, "div", 131)(298, "p", 18);
    \u0275\u0275text(299, "Longitude");
    \u0275\u0275elementEnd();
    \u0275\u0275element(300, "input", 132);
    \u0275\u0275template(301, ConsignmentNewComponent_mat_error_301_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(302, "div", 133)(303, "p", 18);
    \u0275\u0275text(304, "Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(305, "input", 134);
    \u0275\u0275template(306, ConsignmentNewComponent_mat_error_306_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(307, "div", 135)(308, "p", 18);
    \u0275\u0275text(309, "Alternate Phone No");
    \u0275\u0275elementEnd();
    \u0275\u0275element(310, "input", 136);
    \u0275\u0275template(311, ConsignmentNewComponent_mat_error_311_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(312, "div", 137)(313, "p", 18);
    \u0275\u0275text(314, "Email");
    \u0275\u0275elementEnd();
    \u0275\u0275element(315, "input", 138);
    \u0275\u0275template(316, ConsignmentNewComponent_mat_error_316_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(317, "div", 139)(318, "p", 18);
    \u0275\u0275text(319, "Address Hub Code");
    \u0275\u0275elementEnd();
    \u0275\u0275element(320, "input", 140);
    \u0275\u0275template(321, ConsignmentNewComponent_mat_error_321_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(322, "div", 141)(323, "p", 18);
    \u0275\u0275text(324, "Account ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(325, "input", 142);
    \u0275\u0275template(326, ConsignmentNewComponent_mat_error_326_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(327, "div", 58)(328, "button", 66);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_328_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.activeIndex = ctx.activeIndex - 1);
    });
    \u0275\u0275text(329, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(330, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_330_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.saveSender());
    });
    \u0275\u0275text(331, "Next");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(332, "p-tabPanel", 143)(333, "form", 15)(334, "div", 16)(335, "div", 144)(336, "p", 18);
    \u0275\u0275text(337, "Consignee Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(338, "input", 112);
    \u0275\u0275template(339, ConsignmentNewComponent_mat_error_339_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(340, "div", 145)(341, "p", 18);
    \u0275\u0275text(342, "Address Line 1 ");
    \u0275\u0275elementEnd();
    \u0275\u0275element(343, "input", 116);
    \u0275\u0275template(344, ConsignmentNewComponent_mat_error_344_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(345, "div", 146)(346, "p", 18);
    \u0275\u0275text(347, "Address Line 2 ");
    \u0275\u0275elementEnd();
    \u0275\u0275element(348, "input", 118);
    \u0275\u0275template(349, ConsignmentNewComponent_mat_error_349_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(350, "div", 147)(351, "p", 18);
    \u0275\u0275text(352, "Country");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(353, "p-dropdown", 120);
    \u0275\u0275template(354, ConsignmentNewComponent_ng_template_354_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(355, ConsignmentNewComponent_mat_error_355_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(356, "div", 148)(357, "p", 18);
    \u0275\u0275text(358, "State");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(359, "p-dropdown", 122);
    \u0275\u0275template(360, ConsignmentNewComponent_ng_template_360_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(361, ConsignmentNewComponent_mat_error_361_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(362, "div", 149)(363, "p", 18);
    \u0275\u0275text(364, "District");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(365, "p-dropdown", 124);
    \u0275\u0275template(366, ConsignmentNewComponent_ng_template_366_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(367, ConsignmentNewComponent_mat_error_367_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(368, "div", 150)(369, "p", 18);
    \u0275\u0275text(370, "City");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(371, "p-dropdown", 126);
    \u0275\u0275template(372, ConsignmentNewComponent_ng_template_372_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(373, ConsignmentNewComponent_mat_error_373_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(374, "div", 151)(375, "p", 18);
    \u0275\u0275text(376, "Pincode");
    \u0275\u0275elementEnd();
    \u0275\u0275element(377, "input", 128);
    \u0275\u0275template(378, ConsignmentNewComponent_mat_error_378_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(379, "div", 152)(380, "p", 18);
    \u0275\u0275text(381, "Latitude");
    \u0275\u0275elementEnd();
    \u0275\u0275element(382, "input", 130);
    \u0275\u0275template(383, ConsignmentNewComponent_mat_error_383_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(384, "div", 153)(385, "p", 18);
    \u0275\u0275text(386, "Longitude");
    \u0275\u0275elementEnd();
    \u0275\u0275element(387, "input", 132);
    \u0275\u0275template(388, ConsignmentNewComponent_mat_error_388_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(389, "div", 154)(390, "p", 18);
    \u0275\u0275text(391, "Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(392, "input", 134);
    \u0275\u0275template(393, ConsignmentNewComponent_mat_error_393_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(394, "div", 155)(395, "p", 18);
    \u0275\u0275text(396, "Alternate Phone No");
    \u0275\u0275elementEnd();
    \u0275\u0275element(397, "input", 136);
    \u0275\u0275template(398, ConsignmentNewComponent_mat_error_398_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(399, "div", 156)(400, "p", 18);
    \u0275\u0275text(401, "Email");
    \u0275\u0275elementEnd();
    \u0275\u0275element(402, "input", 138);
    \u0275\u0275template(403, ConsignmentNewComponent_mat_error_403_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(404, "div", 157)(405, "p", 18);
    \u0275\u0275text(406, "Company Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(407, "input", 114);
    \u0275\u0275template(408, ConsignmentNewComponent_mat_error_408_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(409, "div", 158)(410, "p", 18);
    \u0275\u0275text(411, "Address Hub Code");
    \u0275\u0275elementEnd();
    \u0275\u0275element(412, "input", 140);
    \u0275\u0275template(413, ConsignmentNewComponent_mat_error_413_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(414, "div", 159)(415, "p", 18);
    \u0275\u0275text(416, "Account ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(417, "input", 142);
    \u0275\u0275template(418, ConsignmentNewComponent_mat_error_418_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(419, "div", 58)(420, "button", 66);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_420_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.activeIndex = ctx.activeIndex - 1);
    });
    \u0275\u0275text(421, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(422, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_422_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.saveDelivery());
    });
    \u0275\u0275text(423, "Next");
    \u0275\u0275elementEnd()()()();
    \u0275\u0275template(424, ConsignmentNewComponent_p_tabPanel_424_Template, 13, 8, "p-tabPanel", 160);
    \u0275\u0275elementStart(425, "p-tabPanel", 161)(426, "form", 15)(427, "div", 162)(428, "div", 163)(429, "p", 18);
    \u0275\u0275text(430, "Consignment Currency");
    \u0275\u0275elementEnd();
    \u0275\u0275element(431, "input", 164);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(432, "div", 163)(433, "p", 18);
    \u0275\u0275text(434, "Consignment Value");
    \u0275\u0275elementEnd();
    \u0275\u0275element(435, "input", 164);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(436, "div", 165)(437, "p", 18);
    \u0275\u0275text(438, "Customs Insurance");
    \u0275\u0275elementEnd();
    \u0275\u0275element(439, "input", 166);
    \u0275\u0275template(440, ConsignmentNewComponent_mat_error_440_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(441, "div", 167)(442, "p", 18);
    \u0275\u0275text(443, "Duty Percentage");
    \u0275\u0275elementEnd();
    \u0275\u0275element(444, "input", 168);
    \u0275\u0275template(445, ConsignmentNewComponent_mat_error_445_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(446, "div", 169)(447, "p", 18);
    \u0275\u0275text(448, "DDU Charge");
    \u0275\u0275elementEnd();
    \u0275\u0275element(449, "input", 170);
    \u0275\u0275template(450, ConsignmentNewComponent_mat_error_450_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(451, "div", 171)(452, "p", 18);
    \u0275\u0275text(453, "Special Approval Charge");
    \u0275\u0275elementEnd();
    \u0275\u0275element(454, "input", 172);
    \u0275\u0275template(455, ConsignmentNewComponent_mat_error_455_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(456, "div", 173)(457, "p", 18);
    \u0275\u0275text(458, "Cost per Shipment");
    \u0275\u0275elementEnd();
    \u0275\u0275element(459, "input", 174);
    \u0275\u0275template(460, ConsignmentNewComponent_mat_error_460_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(461, "div", 58)(462, "button", 66);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_462_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.activeIndex = ctx.activeIndex - 1);
    });
    \u0275\u0275text(463, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(464, "button", 60);
    \u0275\u0275listener("click", function ConsignmentNewComponent_Template_button_click_464_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.saveBilling());
    });
    \u0275\u0275text(465, "Save");
    \u0275\u0275elementEnd()()();
    \u0275\u0275template(466, ConsignmentNewComponent_p_tabPanel_466_Template, 12, 7, "p-tabPanel", 175);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    let tmp_13_0;
    let tmp_19_0;
    let tmp_25_0;
    let tmp_31_0;
    let tmp_42_0;
    let tmp_45_0;
    let tmp_47_0;
    let tmp_59_0;
    let tmp_61_0;
    let tmp_67_0;
    let tmp_73_0;
    let tmp_85_0;
    let tmp_87_0;
    let tmp_89_0;
    let tmp_91_0;
    let tmp_93_0;
    let tmp_97_0;
    let tmp_99_0;
    let tmp_101_0;
    let tmp_103_0;
    let tmp_105_0;
    let tmp_107_0;
    let tmp_109_0;
    let tmp_111_0;
    let tmp_113_0;
    let tmp_115_0;
    let tmp_117_0;
    let tmp_119_0;
    let tmp_121_0;
    let tmp_125_0;
    let tmp_127_0;
    let tmp_132_0;
    let tmp_137_0;
    let tmp_139_0;
    let tmp_141_0;
    let tmp_143_0;
    let tmp_148_0;
    let tmp_154_0;
    let tmp_160_0;
    let tmp_166_0;
    let tmp_169_0;
    let tmp_171_0;
    let tmp_173_0;
    let tmp_175_0;
    let tmp_177_0;
    let tmp_179_0;
    let tmp_181_0;
    let tmp_183_0;
    let tmp_187_0;
    let tmp_189_0;
    let tmp_191_0;
    let tmp_196_0;
    let tmp_202_0;
    let tmp_208_0;
    let tmp_214_0;
    let tmp_217_0;
    let tmp_219_0;
    let tmp_221_0;
    let tmp_223_0;
    let tmp_225_0;
    let tmp_227_0;
    let tmp_229_0;
    let tmp_231_0;
    let tmp_233_0;
    let tmp_244_0;
    let tmp_246_0;
    let tmp_248_0;
    let tmp_250_0;
    let tmp_252_0;
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1("Consignment - ", ctx.pageToken.pageflow, "");
    \u0275\u0275advance(4);
    \u0275\u0275property("disabled", ctx.activeIndex != 1);
    \u0275\u0275advance(4);
    \u0275\u0275twoWayProperty("activeIndex", ctx.activeIndex);
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.shipmentInfo);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(274, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.companyIdList)("panelStyle", \u0275\u0275pureFunction0(275, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("companyId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(276, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.productIdList)("ngClass", ((tmp_13_0 = ctx.shipmentInfo.get("productId")) == null ? null : tmp_13_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(277, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("productId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(278, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.serviceTypeIdList)("ngClass", ((tmp_19_0 = ctx.shipmentInfo.get("serviceTypeId")) == null ? null : tmp_19_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(279, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("serviceTypeId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(280, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.partnerType)("ngClass", ((tmp_25_0 = ctx.shipmentInfo.get("partnerType")) == null ? null : tmp_25_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(281, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("partnerType"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(282, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.partnerNameList)("ngClass", ((tmp_31_0 = ctx.shipmentInfo.get("partnerId")) == null ? null : tmp_31_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(283, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("partnerId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(284, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.consignmentTypeIdList)("panelStyle", \u0275\u0275pureFunction0(285, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("consignmentType"));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(286, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.loadTypeIdList)("ngClass", ((tmp_42_0 = ctx.shipmentInfo.get("loadTypeId")) == null ? null : tmp_42_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(287, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("loadTypeId"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_45_0 = ctx.shipmentInfo.get("customerReferenceNumber")) == null ? null : tmp_45_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("customerReferenceNumber"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_47_0 = ctx.shipmentInfo.get("noOfPieceHawb")) == null ? null : tmp_47_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("noOfPieceHawb"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(288, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.countryIdListOrigin)("panelStyle", \u0275\u0275pureFunction0(289, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("countryOfOrigin"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(290, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.countryIdListOrigin)("panelStyle", \u0275\u0275pureFunction0(291, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("countryOfDestination"));
    \u0275\u0275advance(5);
    \u0275\u0275property("ngClass", ((tmp_59_0 = ctx.shipmentInfo.get("invoiceNumber")) == null ? null : tmp_59_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance(3);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("invoiceNumber"));
    \u0275\u0275advance(6);
    \u0275\u0275property("ngClass", ((tmp_61_0 = ctx.consignment.get("invoiceAmount")) == null ? null : tmp_61_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("invoiceAmount"));
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.shipmentInfo.controls.invoiceUrl.value);
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(292, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.incoTerms)("ngClass", ((tmp_67_0 = ctx.shipmentInfo.get("incoTerms")) == null ? null : tmp_67_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(293, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("incoTerms"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(294, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.paymentType)("ngClass", ((tmp_73_0 = ctx.shipmentInfo.get("paymentType")) == null ? null : tmp_73_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(295, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingShipment("paymentType"));
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.showPaymentTypeFields);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.showPaymentTypeFields);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.showPaymentTypeFields);
    \u0275\u0275advance(6);
    \u0275\u0275property("disabled", ctx.disabledPiece);
    \u0275\u0275advance();
    \u0275\u0275property("formGroup", ctx.piece);
    \u0275\u0275advance();
    \u0275\u0275property("scrollable", true)("value", ctx.piece.controls.pieceDetails.controls);
    \u0275\u0275advance(8);
    \u0275\u0275property("disabled", ctx.disabledConsignment);
    \u0275\u0275advance();
    \u0275\u0275property("formGroup", ctx.consignment);
    \u0275\u0275advance(5);
    \u0275\u0275property("ngClass", ((tmp_85_0 = ctx.consignment.get("partnerMasterAirwayBill")) == null ? null : tmp_85_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("partnerMasterAirwayBill"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_87_0 = ctx.consignment.get("partnerHouseAirwayBill")) == null ? null : tmp_87_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("partnerHouseAirwayBill"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_89_0 = ctx.consignment.get("airportOriginCode")) == null ? null : tmp_89_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("airportOriginCode"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_91_0 = ctx.consignment.get("flightNo")) == null ? null : tmp_91_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("flightNo"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_93_0 = ctx.consignment.get("flightArrivalTime")) == null ? null : tmp_93_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("iconDisplay", "input")("showIcon", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("flightArrivalTime"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_97_0 = ctx.consignment.get("noOfPackages")) == null ? null : tmp_97_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("noOfPackages"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_99_0 = ctx.consignment.get("flightName")) == null ? null : tmp_99_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("flightName"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_101_0 = ctx.consignment.get("length")) == null ? null : tmp_101_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("length"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_103_0 = ctx.consignment.get("width")) == null ? null : tmp_103_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("width"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_105_0 = ctx.consignment.get("height")) == null ? null : tmp_105_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("height"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_107_0 = ctx.consignment.get("dimensionUnit")) == null ? null : tmp_107_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("dimensionUnit"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_109_0 = ctx.consignment.get("weight")) == null ? null : tmp_109_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("weight"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_111_0 = ctx.consignment.get("weightUnit")) == null ? null : tmp_111_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("weightUnit"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_113_0 = ctx.consignment.get("volume")) == null ? null : tmp_113_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("volume"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_115_0 = ctx.consignment.get("volumeUnit")) == null ? null : tmp_115_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("volumeUnit"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_117_0 = ctx.consignment.get("netWeight")) == null ? null : tmp_117_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("netWeight"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_119_0 = ctx.consignment.get("grossWeight")) == null ? null : tmp_119_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("grossWeight"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_121_0 = ctx.consignment.get("invoiceDateFE")) == null ? null : tmp_121_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("iconDisplay", "input")("showIcon", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("invoiceDateFE"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_125_0 = ctx.consignment.get("goodsDescription")) == null ? null : tmp_125_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("goodsDescription"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_127_0 = ctx.consignment.get("consignmentValue")) == null ? null : tmp_127_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("consignmentValue"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(296, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.currencyDropdown)("ngClass", ((tmp_132_0 = ctx.consignment.get("consignmentCurrency")) == null ? null : tmp_132_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(297, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingConsignment("consignmentCurrency"));
    \u0275\u0275advance(6);
    \u0275\u0275property("disabled", ctx.disabledSender);
    \u0275\u0275advance();
    \u0275\u0275property("formGroup", ctx.senderInfo);
    \u0275\u0275advance(5);
    \u0275\u0275property("ngClass", ((tmp_137_0 = ctx.senderInfo.get("originDetails.name")) == null ? null : tmp_137_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.name"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_139_0 = ctx.senderInfo.get("companyName")) == null ? null : tmp_139_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("companyName"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_141_0 = ctx.senderInfo.get("originDetails.addressLine1")) == null ? null : tmp_141_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.addressLine1"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_143_0 = ctx.senderInfo.get("originDetails.addressLine2")) == null ? null : tmp_143_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.addressLine2"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(298, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.countryIdListOrigin)("ngClass", ((tmp_148_0 = ctx.senderInfo.get("originDetails.country")) == null ? null : tmp_148_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(299, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.country"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(300, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.provinceIdList)("ngClass", ((tmp_154_0 = ctx.senderInfo.get("originDetails.state")) == null ? null : tmp_154_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(301, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.state"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(302, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.districtIdList)("ngClass", ((tmp_160_0 = ctx.senderInfo.get("originDetails.district")) == null ? null : tmp_160_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(303, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.district"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(304, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.cityIdList)("ngClass", ((tmp_166_0 = ctx.senderInfo.get("originDetails.city")) == null ? null : tmp_166_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(305, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.city"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_169_0 = ctx.senderInfo.get("originDetails.pinCode")) == null ? null : tmp_169_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.pinCode"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_171_0 = ctx.senderInfo.get("originDetails.latitude")) == null ? null : tmp_171_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.latitude"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_173_0 = ctx.senderInfo.get("originDetails.longitude")) == null ? null : tmp_173_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.longitude"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_175_0 = ctx.senderInfo.get("originDetails.phone")) == null ? null : tmp_175_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.phone"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_177_0 = ctx.senderInfo.get("originDetails.alternatePhone")) == null ? null : tmp_177_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.alternatePhone"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_179_0 = ctx.senderInfo.get("originDetails.email")) == null ? null : tmp_179_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.email"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_181_0 = ctx.senderInfo.get("originDetails.addressHubCode")) == null ? null : tmp_181_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.addressHubCode"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_183_0 = ctx.senderInfo.get("originDetails.accountId")) == null ? null : tmp_183_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("originDetails.accountId"));
    \u0275\u0275advance(6);
    \u0275\u0275property("disabled", ctx.disabledDelivery);
    \u0275\u0275advance();
    \u0275\u0275property("formGroup", ctx.deliveryInfo);
    \u0275\u0275advance(5);
    \u0275\u0275property("ngClass", ((tmp_187_0 = ctx.deliveryInfo.get("destinationDetails.name")) == null ? null : tmp_187_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.name"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_189_0 = ctx.deliveryInfo.get("destinationDetails.addressLine1")) == null ? null : tmp_189_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.addressLine1"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_191_0 = ctx.deliveryInfo.get("destinationDetails.addressLine2")) == null ? null : tmp_191_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.addressLine2"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(306, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.countryIdListDestination)("ngClass", ((tmp_196_0 = ctx.deliveryInfo.get("destinationDetails.country")) == null ? null : tmp_196_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(307, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.country"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(308, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.provinceIdList)("ngClass", ((tmp_202_0 = ctx.deliveryInfo.get("destinationDetails.state")) == null ? null : tmp_202_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(309, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.state"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(310, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.districtIdList)("ngClass", ((tmp_208_0 = ctx.deliveryInfo.get("destinationDetails.district")) == null ? null : tmp_208_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(311, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.district"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(312, _c06));
    \u0275\u0275property("showClear", true)("options", ctx.cityIdList)("ngClass", ((tmp_214_0 = ctx.deliveryInfo.get("destinationDetails.city")) == null ? null : tmp_214_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "")("panelStyle", \u0275\u0275pureFunction0(313, _c06));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.city"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_217_0 = ctx.senderInfo.get("destinationDetails.pinCode")) == null ? null : tmp_217_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingSender("destinationDetails.pinCode"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_219_0 = ctx.deliveryInfo.get("destinationDetails.latitude")) == null ? null : tmp_219_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.latitude"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_221_0 = ctx.deliveryInfo.get("destinationDetails.longitude")) == null ? null : tmp_221_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.longitude"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_223_0 = ctx.deliveryInfo.get("destinationDetails.phone")) == null ? null : tmp_223_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.phone"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_225_0 = ctx.deliveryInfo.get("destinationDetails.alternatePhone")) == null ? null : tmp_225_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.alternatePhone"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_227_0 = ctx.deliveryInfo.get("destinationDetails.email")) == null ? null : tmp_227_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.email"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_229_0 = ctx.deliveryInfo.get("destinationDetails.companyName")) == null ? null : tmp_229_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.companyName"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_231_0 = ctx.deliveryInfo.get("destinationDetails.addressHubCode")) == null ? null : tmp_231_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.addressHubCode"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_233_0 = ctx.deliveryInfo.get("destinationDetails.accountId")) == null ? null : tmp_233_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingDelivery("destinationDetails.accountId"));
    \u0275\u0275advance(6);
    \u0275\u0275property("ngIf", ctx.pageToken.pageflow != "New");
    \u0275\u0275advance();
    \u0275\u0275property("disabled", ctx.disabledBilling);
    \u0275\u0275advance();
    \u0275\u0275property("formGroup", ctx.billing);
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.consignment);
    \u0275\u0275advance(3);
    \u0275\u0275property("value", ctx.consignment.controls.consignmentCurrency.value)("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("formGroup", ctx.consignment);
    \u0275\u0275advance(3);
    \u0275\u0275property("value", ctx.consignment.controls.consignmentValue.value)("disabled", true);
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_244_0 = ctx.billing.get("customsInsurance")) == null ? null : tmp_244_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingBilling("customsInsurance"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_246_0 = ctx.billing.get("dutyPercentage")) == null ? null : tmp_246_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingBilling("dutyPercentage"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_248_0 = ctx.billing.get("dduCharge")) == null ? null : tmp_248_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingBilling("dduCharge"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_250_0 = ctx.billing.get("specialApprovalCharge")) == null ? null : tmp_250_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingBilling("specialApprovalCharge"));
    \u0275\u0275advance(4);
    \u0275\u0275property("ngClass", ((tmp_252_0 = ctx.senderInfo.get("costPerShipment")) == null ? null : tmp_252_0.invalid) && ctx.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandlingBilling("costPerShipment"));
    \u0275\u0275advance(6);
    \u0275\u0275property("ngIf", ctx.pageToken.pageflow != "New");
  }
}, dependencies: [NgClass, NgForOf, NgIf, RouterLink, ButtonDirective, PrimeTemplate, Table, TableCheckbox, TableHeaderCheckbox, Dropdown, InputText, Calendar, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, KeyFilter, TabView, TabPanel, OverlayPanel, InputGroup, MatError, MatMenuItem, MatAccordion, MatExpansionPanel, MatExpansionPanelHeader, MatExpansionPanelTitle, FormGroupDirective, FormControlName, FormGroupName, TrimDirective, DatePipe], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 2.5rem;\n}\n.scrollNew27[_ngcontent-%COMP%] {\n  height: calc(100vh - 27rem);\n  overflow-y: scroll;\n}\n.scrollNew22[_ngcontent-%COMP%] {\n  height: calc(100vh - 22rem);\n  overflow-y: scroll;\n}\n.scrollNew32[_ngcontent-%COMP%] {\n  height: calc(100vh - 32rem);\n  overflow-y: scroll;\n}\n.inputborderLess[_ngcontent-%COMP%] {\n  border: none !important;\n}\n  .mat-expansion-panel:not([class*=mat-elevation-z]) {\n  box-shadow: none !important;\n  border-bottom: solid 1px #cccccc !important;\n}\n.contentBox[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  margin: 10px 10px 10px 0px;\n  flex-direction: row;\n  width: 50%;\n  justify-content: center;\n  align-items: center;\n  gap: 80px;\n  padding: 1rem 2rem 1rem 2rem;\n  border-radius: 16px;\n  border: solid 1px var(--black);\n  background-color: var(--white);\n  color: var(--black);\n  transition: background-color 0.3s ease;\n}\n.lineBorder[_ngcontent-%COMP%] {\n  width: 8px;\n  flex-grow: 0;\n  margin: 10px 7px 10px 0;\n  border-radius: 20px;\n  background-color: var(--overcOrange);\n}\n.small-calendar[_ngcontent-%COMP%]   .p-datepicker[_ngcontent-%COMP%] {\n  font-size: 12px;\n  width: 200px;\n  max-width: 100%;\n  max-height: 300px;\n}\n/*# sourceMappingURL=consignment-new.component.css.map */"], data: { animation: [
  trigger("fadeLater", [
    state("fade-in", style({ opacity: 1, transform: "translateY(0)" })),
    state("fade-out", style({ opacity: 0, transform: "translateY(0)" })),
    transition("fade-in <=> fade-out", animate("0.6s ease-in-out"))
  ])
] } });
var ConsignmentNewComponent = _ConsignmentNewComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ConsignmentNewComponent, { className: "ConsignmentNewComponent", filePath: "src\\app\\main\\operation\\consignment\\consignment-new\\consignment-new.component.ts", lineNumber: 37 });
})();

// src/app/main/operation/assignemnt-popup/assignemnt-popup.component.ts
function AssignemntPopupComponent_div_5_div_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 11)(1, "div", 12)(2, "div", 13)(3, "span", 14);
    \u0275\u0275text(4, "person");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(5, "div", 15)(6, "div", 16)(7, "h5", 17);
    \u0275\u0275text(8);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(9, "p", 18);
    \u0275\u0275text(10);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(11, "div", 19);
    \u0275\u0275element(12, "img", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(13, "div", 21)(14, "div", 22)(15, "div", 23);
    \u0275\u0275text(16);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(17, "div", 24);
    \u0275\u0275text(18, "Assigned");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(19, "div", 25)(20, "div", 26);
    \u0275\u0275text(21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(22, "div", 27);
    \u0275\u0275text(23, "Delivered");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(24, "div", 28)(25, "div", 29);
    \u0275\u0275text(26);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(27, "div", 30);
    \u0275\u0275text(28, "ETA");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(29, "div", 31)(30, "div", 32);
    \u0275\u0275text(31);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(32, "div", 33);
    \u0275\u0275text(33, "Current Locations");
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    const raider_r1 = ctx.$implicit;
    \u0275\u0275advance(8);
    \u0275\u0275textInterpolate(raider_r1.name);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(raider_r1.phone);
    \u0275\u0275advance(6);
    \u0275\u0275textInterpolate(raider_r1.assigned);
    \u0275\u0275advance(5);
    \u0275\u0275textInterpolate(raider_r1.delivered);
    \u0275\u0275advance(5);
    \u0275\u0275textInterpolate(raider_r1.eta);
    \u0275\u0275advance(5);
    \u0275\u0275textInterpolate(raider_r1.currentLocation);
  }
}
function AssignemntPopupComponent_div_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div")(1, "div", 9);
    \u0275\u0275template(2, AssignemntPopupComponent_div_5_div_2_Template, 34, 6, "div", 10);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275property("ngForOf", ctx_r1.raiders);
  }
}
function AssignemntPopupComponent_p_6_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 34);
    \u0275\u0275text(1, "No riders available.");
    \u0275\u0275elementEnd();
  }
}
var _AssignemntPopupComponent = class _AssignemntPopupComponent {
  constructor() {
    this.raiders = [
      {
        name: "Marvin McKinney",
        details: "Experienced rider with 5 years on the road.",
        phone: "(229) 555-0109",
        delivered: 12,
        assigned: 17,
        eta: "2.5 ",
        currentLocation: " Salmiya"
      },
      {
        name: "Mark",
        details: "Skilled rider specializing in long-distance deliveries.",
        phone: "(229) 555-0109",
        delivered: 12,
        assigned: 17,
        eta: "2.5 ",
        currentLocation: "Salmiya"
      },
      {
        name: "Mark",
        details: "Skilled rider specializing in long-distance deliveries.",
        phone: "(229) 555-0109",
        delivered: 12,
        assigned: 17,
        eta: "2.5 ",
        currentLocation: "Salmiya"
      }
      // Add more riders as needed
    ];
  }
};
_AssignemntPopupComponent.\u0275fac = function AssignemntPopupComponent_Factory(t) {
  return new (t || _AssignemntPopupComponent)();
};
_AssignemntPopupComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _AssignemntPopupComponent, selectors: [["app-assignemnt-popup"]], decls: 12, vars: 2, consts: [[1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "header"], [1, "w-100", "mt-0"], [4, "ngIf"], ["class", "text-center", 4, "ngIf"], [1, "d-flex", "justify-content-end", "py-2", "popup-container"], ["mat-dialog-close", "", 1, "button", "textBlack", "mx-1"], ["type", "button", 1, "button", "bgBlack", "text-white", "mx-1"], [1, "raider-tiles-container"], ["class", "raider-tile", 4, "ngFor", "ngForOf"], [1, "raider-tile"], [1, "raider-header"], [1, "LocationName"], [1, "material-icons"], [1, "raider-info"], [1, "raider-name-container"], [1, "raider-name"], [1, "raider-phone"], [1, "Location"], ["src", "../../../../assets/location.png", 1, "LocationPng"], [1, "raider-data"], [1, "data-item1"], [1, "data-value1"], [1, "data-label1"], [1, "data-item2"], [1, "data-value2"], [1, "data-label2"], [1, "data-item3"], [1, "data-value3"], [1, "data-label3"], [1, "data-item4"], [1, "data-value4"], [1, "data-label4"], [1, "text-center"]], template: function AssignemntPopupComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1)(2, "p", 2);
    \u0275\u0275text(3, "Rider List");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div", 3);
    \u0275\u0275template(5, AssignemntPopupComponent_div_5_Template, 3, 1, "div", 4)(6, AssignemntPopupComponent_p_6_Template, 2, 0, "p", 5);
    \u0275\u0275elementStart(7, "div", 6)(8, "button", 7);
    \u0275\u0275text(9, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "button", 8);
    \u0275\u0275text(11, "Save");
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(5);
    \u0275\u0275property("ngIf", ctx.raiders.length > 0);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.raiders.length === 0);
  }
}, dependencies: [NgForOf, NgIf, MatDialogClose], styles: ["\n\n.raider-tiles-container[_ngcontent-%COMP%] {\n  display: flex;\n  flex-direction: column;\n  gap: 12px;\n  padding: 20px;\n}\n.raider-tile[_ngcontent-%COMP%] {\n  height: 146px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: column;\n  justify-content: flex-start;\n  align-items: stretch;\n  padding: 16px 20px;\n  border-radius: 8px;\n  transition:\n    transform 0.3s ease,\n    box-shadow 0.3s ease,\n    background-color 0.3s ease;\n  border: solid 1.5px #dbdbdb;\n  background-color: var(--white);\n  cursor: pointer;\n}\n.raider-tile[_ngcontent-%COMP%]:hover {\n  transform: scale(1.05);\n  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);\n  border: solid 1.5px #eaab4c;\n}\n.raider-header[_ngcontent-%COMP%] {\n  height: 50px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: row;\n  justify-content: flex-start;\n  align-items: center;\n  padding: 20px;\n  padding: 0;\n}\n.raider-icon[_ngcontent-%COMP%] {\n  width: 50px;\n  height: 50px;\n  flex-grow: 0;\n  border-radius: 170px;\n}\n.LocationName[_ngcontent-%COMP%] {\n  width: 50px;\n  height: 50px;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: row;\n  align-items: center;\n  justify-content: center;\n  border-radius: 170px;\n  background-color: var(--white);\n  border: solid 1px #dbdbdb;\n}\n.raider-info[_ngcontent-%COMP%] {\n  height: 45px;\n  flex-grow: 1;\n  display: flex;\n  font-family: Nunito;\n  flex-direction: column;\n  justify-content: center;\n  align-items: flex-start;\n  padding: 4px;\n  margin-left: 20px;\n}\n.raider-name-container[_ngcontent-%COMP%] {\n  height: 23px;\n  flex-grow: 1;\n  display: flex;\n  flex-direction: column;\n  justify-content: center;\n  align-items: flex-start;\n  gap: 4px;\n  padding: 0;\n  margin-top: 18.5px;\n}\n.raider-name[_ngcontent-%COMP%] {\n  height: 23px;\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 18px;\n  font-weight: 600;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: var(--black);\n}\n.Location[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: row;\n  justify-content: center;\n  align-items: center;\n  gap: 8px;\n  padding: 12px;\n  margin-left: 20px;\n  border-radius: 106px;\n  border: solid 1px #dbdbdb;\n  background-color: var(--white);\n}\n.Location2[_ngcontent-%COMP%] {\n  width: 20px;\n  height: 20px;\n  flex-grow: 0;\n  object-fit: contain;\n}\n.raider-phone[_ngcontent-%COMP%] {\n  width: 103px;\n  height: 18px;\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: #828282;\n}\n.header[_ngcontent-%COMP%] {\n  height: 23px;\n  align-self: stretch;\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 18px;\n  font-weight: 600;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: #1c1b18;\n}\n.raider-data[_ngcontent-%COMP%] {\n  height: 44px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: row;\n  justify-content: space-between;\n  align-items: stretch;\n  padding: 0;\n  margin-top: 22px;\n}\n.data-item1[_ngcontent-%COMP%] {\n  width: 51px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: column;\n  justify-content: center;\n  align-items: flex-start;\n  gap: 5px;\n  padding: 0;\n}\n.data-item2[_ngcontent-%COMP%] {\n  width: 53px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: column;\n  justify-content: center;\n  align-items: flex-start;\n  gap: 5px;\n  padding: 0;\n}\n.data-item2[_ngcontent-%COMP%] {\n  width: 53px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: column;\n  justify-content: center;\n  align-items: flex-start;\n  gap: 5px;\n  padding: 0;\n}\n.data-item3[_ngcontent-%COMP%] {\n  width: 51px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: column;\n  justify-content: center;\n  align-items: flex-start;\n  gap: 5px;\n  padding: 0;\n  margin-left: -15px;\n}\n.data-item4[_ngcontent-%COMP%] {\n  width: 96px;\n  align-self: stretch;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: column;\n  justify-content: center;\n  align-items: flex-start;\n  gap: 5px;\n  margin-left: -30px;\n}\n.data-label1[_ngcontent-%COMP%] {\n  font-size: 12px;\n  margin-bottom: 0.25rem;\n  font-size: 0.9rem;\n  width: 51px;\n  color: #828282;\n  font-family: Nunito;\n  font-size: 12px;\n  font-weight: 500;\n  font-stretch: normal;\n  font-style: normal;\n}\n.data-label2[_ngcontent-%COMP%] {\n  font-size: 12px;\n  margin-bottom: 0.25rem;\n  font-size: 0.9rem;\n  width: 51px;\n  color: #828282;\n  font-family: Nunito;\n  font-size: 12px;\n  font-weight: 500;\n  font-stretch: normal;\n  font-style: normal;\n}\n.data-label3[_ngcontent-%COMP%] {\n  font-size: 12px;\n  margin-bottom: 0.25rem;\n  font-size: 0.9rem;\n  width: 51px;\n  margin-left: -15px;\n  color: #828282;\n  font-family: Nunito;\n  font-size: 12px;\n  font-weight: 500;\n  font-stretch: normal;\n  font-style: normal;\n}\n.data-label4[_ngcontent-%COMP%] {\n  font-size: 12px;\n  margin-bottom: 0.25rem;\n  font-size: 0.9rem;\n  width: 140px;\n  margin-left: -15px;\n  color: #828282;\n  font-family: Nunito;\n  font-size: 12px;\n  font-weight: 500;\n  font-stretch: normal;\n  font-style: normal;\n}\n.popup-container[_ngcontent-%COMP%] {\n  padding-right: 20px;\n  box-sizing: border-box;\n}\n.header[_ngcontent-%COMP%] {\n  margin-top: 20px;\n  margin-left: 20px;\n  font-weight: 500;\n  margin-bottom: -5px;\n}\n.data-value1[_ngcontent-%COMP%] {\n  width: 51px;\n  height: 16px;\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 18px;\n  font-weight: bold;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: var(--black);\n  margin-bottom: 5px;\n}\n.data-value2[_ngcontent-%COMP%] {\n  width: 51px;\n  height: 16px;\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 18px;\n  font-weight: bold;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: var(--black);\n  margin-bottom: 5px;\n}\n.data-value3[_ngcontent-%COMP%] {\n  width: 51px;\n  height: 16px;\n  flex-grow: 0;\n  margin-left: -15px;\n  font-family: Nunito;\n  font-size: 18px;\n  font-weight: bold;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: var(--black);\n  margin-bottom: 5px;\n}\n.data-value4[_ngcontent-%COMP%] {\n  width: 110px;\n  height: 16px;\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 18px;\n  font-weight: bold;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: var(--black);\n  margin-bottom: 5px;\n  margin-left: -15px;\n}\n.text-center[_ngcontent-%COMP%] {\n  text-align: center;\n}\n.button[_ngcontent-%COMP%] {\n  width: 100px;\n  height: 34px;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: row;\n  justify-content: center;\n  align-items: center;\n  gap: 10px;\n  padding: 11px 20px;\n  border-radius: 8px;\n  border: solid 1px var(--black);\n  margin-left: -20px;\n}\n.textBlack[_ngcontent-%COMP%] {\n  color: #000;\n}\n.bgBlack[_ngcontent-%COMP%] {\n  background-color: #000;\n}\n.text-white[_ngcontent-%COMP%] {\n  color: #fff;\n}\n.componentBody[_ngcontent-%COMP%] {\n  border-radius: 50px;\n  padding: 8px;\n  border-radius: 20px;\n}\n.mx-1[_ngcontent-%COMP%] {\n  margin-left: 0.25rem;\n  margin-right: 0.25rem;\n}\n.d-flex[_ngcontent-%COMP%] {\n  display: flex;\n}\n.justify-content-end[_ngcontent-%COMP%] {\n  justify-content: flex-end;\n}\n.py-2[_ngcontent-%COMP%] {\n  padding-top: 0.5rem;\n  padding-bottom: 0.5rem;\n}\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 20rem);\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=assignemnt-popup.component.css.map */"] });
var AssignemntPopupComponent = _AssignemntPopupComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(AssignemntPopupComponent, { className: "AssignemntPopupComponent", filePath: "src\\app\\main\\operation\\assignemnt-popup\\assignemnt-popup.component.ts", lineNumber: 8 });
})();

// src/app/main/operation/assignment/assignment.component.ts
var _c07 = ["assignment"];
var _c14 = () => ({ width: "80vw" });
var _c23 = () => ({ "width": "100%" });
var _c33 = () => ({ "width": "130rem" });
function AssignmentComponent_ng_template_35_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 40);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 41);
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
function AssignmentComponent_ng_template_35_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 42);
    \u0275\u0275listener("input", function AssignmentComponent_ng_template_35_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const consignmentTag_r3 = \u0275\u0275reference(34);
      return \u0275\u0275resetView(consignmentTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const consignmentTag_r3 = \u0275\u0275reference(34);
    \u0275\u0275advance();
    \u0275\u0275property("value", consignmentTag_r3.filters[col_r6.field] == null ? null : consignmentTag_r3.filters[col_r6.field].value);
  }
}
function AssignmentComponent_ng_template_35_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 34);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 35);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, AssignmentComponent_ng_template_35_th_3_Template, 3, 3, "th", 36);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 37);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 38);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, AssignmentComponent_ng_template_35_th_7_Template, 2, 1, "th", 39);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r7 = ctx.$implicit;
    \u0275\u0275advance(3);
    \u0275\u0275property("ngForOf", columns_r7);
    \u0275\u0275advance(3);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r7);
  }
}
function AssignmentComponent_ng_template_36_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext().$implicit;
    const rowData_r9 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r9[col_r8.field], " ");
  }
}
function AssignmentComponent_ng_template_36_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r10 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 49);
    \u0275\u0275listener("click", function AssignmentComponent_ng_template_36_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r10);
      const rowData_r9 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r10 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r10.openCrud("Edit", rowData_r9));
    });
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r9 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(rowData_r9[col_r8.field]);
  }
}
function AssignmentComponent_ng_template_36_td_3_ng_template_2_span_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span");
    \u0275\u0275text(1);
    \u0275\u0275pipe(2, "date");
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r9 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(\u0275\u0275pipeBind2(2, 1, rowData_r9[col_r8.field], "dd-MM-yyyy HH:mm"));
  }
}
function AssignmentComponent_ng_template_36_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, AssignmentComponent_ng_template_36_td_3_ng_template_2_span_0_Template, 2, 1, "span", 47)(1, AssignmentComponent_ng_template_36_td_3_ng_template_2_span_1_Template, 3, 4, "span", 48);
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r8.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r8.format == "date");
  }
}
function AssignmentComponent_ng_template_36_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, AssignmentComponent_ng_template_36_td_3_ng_container_1_Template, 2, 1, "ng-container", 46)(2, AssignmentComponent_ng_template_36_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r8 = ctx.$implicit;
    const dateTemplate_r12 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r8.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r8.format !== "date" && col_r8.format !== "hyperLink")("ngIfElse", dateTemplate_r12);
  }
}
function AssignmentComponent_ng_template_36_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td", 43);
    \u0275\u0275element(2, "p-tableCheckbox", 44);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, AssignmentComponent_ng_template_36_td_3_Template, 4, 4, "td", 45);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r9 = ctx.$implicit;
    const columns_r13 = ctx.columns;
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r9);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function AssignmentComponent_ng_template_37_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 50);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function AssignmentComponent_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "button", 51);
    \u0275\u0275text(1, "Edit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(2, "button", 51);
    \u0275\u0275text(3, "Confirm");
    \u0275\u0275elementEnd();
  }
}
var _AssignmentComponent = class _AssignmentComponent {
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
    this.consignmentTable = [];
    this.selectedConsignment = [];
    this.cols = [];
    this.target = [];
    this.searchform = this.fb.group({
      statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      statusId: "Status"
    };
    this.languageDropdown = [];
    this.companyDropdown = [];
    this.statusDropdown = [];
  }
  ngOnInit() {
    const dataToSend = ["Operations", "Assignment"];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "partnerHouseAirwayBill", header: "CN#" },
      { field: "hawbTypeDescription", header: "Status" },
      { field: "serviceTypeText", header: "Service Type" },
      { field: "hubCode", header: "Current Hub" },
      { field: "deliveryAttempts", header: "Delivery Attempts" },
      { field: "hawbTimeStamp", header: "Last Event Time", format: "date" },
      { field: "paymentType", header: "Paid By Card" }
    ];
    this.target = [];
  }
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.service.search(obj).subscribe({
        next: (res) => {
          this.consignmentTable = res;
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
    const choosen = this.selectedConsignment[this.selectedConsignment.length - 1];
    this.selectedConsignment.length = 0;
    this.selectedConsignment.push(choosen);
  }
  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" },
      data: { title: "Rider Assignment", code: this.selectedConsignment }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedConsignment[0]);
      }
    });
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedConsignment = linedata;
    }
    if (this.selectedConsignment.length === 0 && type != "New") {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsignment[0] : linedata, pageflow: type });
      this.router.navigate(["/main/operation/assignment-new/" + paramdata]);
    }
  }
  deleteDialog() {
    if (this.selectedConsignment.length === 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: "60%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "30%" },
      data: { line: this.selectedConsignment, module: "Assignment", body: "This action cannot be undone. All values associated with this field will be lost." }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedConsignment[0]);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: "success", summary: "Deleted", key: "br", detail: "Deleted successfully" });
        this.spin.hide();
        this.initialCall();
      },
      error: (err) => {
        this.cs.commonerrorNew(err);
        this.spin.hide();
      }
    });
  }
  assignRider() {
    const dialogRef = this.dialog.open(AssignemntPopupComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" },
      data: { target: this.cols, source: this.target }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedConsignment[0]);
      }
    });
  }
  downloadExcel() {
    const exportData = this.consignmentTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, "Assignment");
  }
  getSearchDropdown() {
    this.consignmentTable.forEach((res) => {
      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, "value");
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, "value");
      }
      if (res.statusId != null) {
        this.statusDropdown.push({ value: res.statusId, label: res.statusId });
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
        this.consignmentTable = res;
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
_AssignmentComponent.\u0275fac = function AssignmentComponent_Factory(t) {
  return new (t || _AssignmentComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NgxSpinnerService));
};
_AssignmentComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _AssignmentComponent, selectors: [["app-assignment"]], viewQuery: function AssignmentComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c07, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 41, vars: 25, consts: [["assignment", ""], ["consignmentTag", ""], ["menu", "matMenu"], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click", "disabled"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "multiple", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "consignmentId", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "selection", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [1, "customClass"], ["matMenuContent", ""], ["pFrozenColumn", "", 2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], ["pFrozenColumn", "", 2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"], ["mat-menu-item", ""]], template: function AssignmentComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 4)(1, "div", 5)(2, "p", 6);
    \u0275\u0275text(3, "Rider Assignment");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 5)(5, "img", 7);
    \u0275\u0275listener("click", function AssignmentComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 8);
    \u0275\u0275listener("click", function AssignmentComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 9);
    \u0275\u0275listener("click", function AssignmentComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "img", 10);
    \u0275\u0275listener("click", function AssignmentComponent_Template_img_click_8_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.customTable());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "button", 11);
    \u0275\u0275listener("click", function AssignmentComponent_Template_button_click_9_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.assignRider());
    });
    \u0275\u0275text(10, " Assign Rider ");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(11, "div", 12)(12, "p-iconField", 13)(13, "p-inputIcon", 14);
    \u0275\u0275listener("click", function AssignmentComponent_Template_p_inputIcon_click_13_listener($event) {
      \u0275\u0275restoreView(_r1);
      const assignment_r2 = \u0275\u0275reference(16);
      return \u0275\u0275resetView(assignment_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(14, "input", 15);
    \u0275\u0275listener("input", function AssignmentComponent_Template_input_input_14_listener($event) {
      \u0275\u0275restoreView(_r1);
      const consignmentTag_r3 = \u0275\u0275reference(34);
      return \u0275\u0275resetView(consignmentTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(15, "p-overlayPanel", 16, 0)(17, "form", 17)(18, "div", 18)(19, "div", 19)(20, "p", 20);
    \u0275\u0275text(21, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275element(22, "p-multiSelect", 21);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(23, "div", 22)(24, "div")(25, "img", 23);
    \u0275\u0275listener("click", function AssignmentComponent_Template_img_click_25_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(26, "div")(27, "button", 24);
    \u0275\u0275listener("click", function AssignmentComponent_Template_button_click_27_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(28, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(29, "button", 25);
    \u0275\u0275listener("click", function AssignmentComponent_Template_button_click_29_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(30, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(31, "p-chips", 26);
    \u0275\u0275listener("onRemove", function AssignmentComponent_Template_p_chips_onRemove_31_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function AssignmentComponent_Template_p_chips_ngModelChange_31_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(32, "div", 27)(33, "p-table", 28, 1);
    \u0275\u0275twoWayListener("selectionChange", function AssignmentComponent_Template_p_table_selectionChange_33_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedConsignment, $event) || (ctx.selectedConsignment = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(35, AssignmentComponent_ng_template_35_Template, 8, 3, "ng-template", 29)(36, AssignmentComponent_ng_template_36_Template, 4, 2, "ng-template", 30)(37, AssignmentComponent_ng_template_37_Template, 4, 1, "ng-template", 31);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(38, "mat-menu", 32, 2);
    \u0275\u0275template(40, AssignmentComponent_ng_template_40_Template, 4, 0, "ng-template", 33);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    \u0275\u0275advance(9);
    \u0275\u0275property("disabled", ctx.selectedConsignment.length <= 0);
    \u0275\u0275advance(6);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(21, _c14));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(22, _c23));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(23, _c23));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.consignmentTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx.selectedConsignment);
    \u0275\u0275property("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(24, _c33));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, FrozenColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, InputIcon, IconField, InputText, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenu, MatMenuItem, MatMenuContent, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=assignment.component.css.map */"] });
var AssignmentComponent = _AssignmentComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(AssignmentComponent, { className: "AssignmentComponent", filePath: "src\\app\\main\\operation\\assignment\\assignment.component.ts", lineNumber: 22 });
})();

// src/app/main/operation/operation-routing.module.ts
var routes = [
  { path: "consignment", component: ConsignmentComponent, data: { title: "Operations", module: "Consignment" } },
  { path: "consignment-new/:code", component: ConsignmentNewComponent, data: { title: "Operations", module: "Consignment" } },
  { path: "assignment", component: AssignmentComponent, data: { title: "Operations", module: "Assignment" } }
];
var _OperationRoutingModule = class _OperationRoutingModule {
};
_OperationRoutingModule.\u0275fac = function OperationRoutingModule_Factory(t) {
  return new (t || _OperationRoutingModule)();
};
_OperationRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _OperationRoutingModule });
_OperationRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forChild(routes), RouterModule] });
var OperationRoutingModule = _OperationRoutingModule;

// src/app/main/operation/operation.module.ts
var _OperationModule = class _OperationModule {
};
_OperationModule.\u0275fac = function OperationModule_Factory(t) {
  return new (t || _OperationModule)();
};
_OperationModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _OperationModule });
_OperationModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [
  CommonModule,
  OperationRoutingModule,
  SharedModule
] });
var OperationModule = _OperationModule;
export {
  OperationModule
};
//# sourceMappingURL=chunk-KKJIYDVN.js.map
