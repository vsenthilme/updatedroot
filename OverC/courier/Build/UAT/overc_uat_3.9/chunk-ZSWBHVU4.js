import {
  CustomsChargesMasterService
} from "./chunk-FHYWOUQA.js";
import {
  CostingSheetService
} from "./chunk-LMO3BHKE.js";
import {
  DeleteComponent
} from "./chunk-5SKNGDL5.js";
import {
  NumberrangeService
} from "./chunk-ZSJAMNZP.js";
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
  AuthService,
  Chips,
  CommonServiceService,
  DefaultValueAccessor,
  Dropdown,
  FormBuilder,
  FormControlName,
  FormGroupDirective,
  FrozenColumn,
  IconField,
  InputIcon,
  InputText,
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogClose,
  MatDialogRef,
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
  SortIcon,
  SortableColumn,
  Table,
  TableCheckbox,
  TableHeaderCheckbox,
  TrimDirective,
  ɵNgNoValidate
} from "./chunk-XFYC4BWK.js";
import {
  ActivatedRoute,
  DatePipe,
  DomSanitizer,
  NgForOf,
  NgIf,
  Router,
  __async,
  __spreadValues,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵattribute,
  ɵɵclassProp,
  ɵɵdefineComponent,
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
  ɵɵqueryRefresh,
  ɵɵreference,
  ɵɵresetView,
  ɵɵrestoreView,
  ɵɵstyleMap,
  ɵɵtemplate,
  ɵɵtext,
  ɵɵtextInterpolate,
  ɵɵtextInterpolate1,
  ɵɵtwoWayBindingSet,
  ɵɵtwoWayListener,
  ɵɵtwoWayProperty,
  ɵɵviewQuery
} from "./chunk-Z5YEPTLN.js";

// src/app/main/airport/costing-sheet/costing-sheet-bulkupdate/costing-sheet-bulkupdate.component.ts
var _c0 = () => ({ "width": "100%" });
function CostingSheetBulkupdateComponent_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 11);
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const ctx_r0 = \u0275\u0275nextContext();
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1("", ctx_r0.data.title, " - Bulk Update");
  }
}
function CostingSheetBulkupdateComponent_p_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 11);
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const ctx_r0 = \u0275\u0275nextContext();
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(ctx_r0.data.title);
  }
}
function CostingSheetBulkupdateComponent_div_8_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 16);
  }
}
function CostingSheetBulkupdateComponent_div_8_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 12)(1, "p", 13);
    \u0275\u0275text(2, "Cost Description");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "p-dropdown", 14);
    \u0275\u0275template(4, CostingSheetBulkupdateComponent_div_8_ng_template_4_Template, 1, 0, "ng-template", 15);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r0 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(6, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r0.incoTerms)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(7, _c0));
  }
}
function CostingSheetBulkupdateComponent_div_9_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 12)(1, "p", 13);
    \u0275\u0275text(2, "Cost Description");
    \u0275\u0275elementEnd();
    \u0275\u0275element(3, "input", 17);
    \u0275\u0275elementEnd();
  }
}
function CostingSheetBulkupdateComponent_div_10_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 12)(1, "p", 13);
    \u0275\u0275text(2, "Cost Amount");
    \u0275\u0275elementEnd();
    \u0275\u0275element(3, "input", 18);
    \u0275\u0275elementEnd();
  }
}
function CostingSheetBulkupdateComponent_div_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 12)(1, "p", 13);
    \u0275\u0275text(2, "Remarks");
    \u0275\u0275elementEnd();
    \u0275\u0275element(3, "input", 19);
    \u0275\u0275elementEnd();
  }
}
var _CostingSheetBulkupdateComponent = class _CostingSheetBulkupdateComponent {
  constructor(dialogRef, data, cs, spin, route, router, path, fb, messageService, CostingSheetService2, numberRangeService, service, cas, auth) {
    this.dialogRef = dialogRef;
    this.data = data;
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.path = path;
    this.fb = fb;
    this.messageService = messageService;
    this.CostingSheetService = CostingSheetService2;
    this.numberRangeService = numberRangeService;
    this.service = service;
    this.cas = cas;
    this.auth = auth;
    this.status = [];
    this.incoTerms = [];
    this.form = this.fb.group({
      costAmount: [],
      costDescription: [],
      cashNumber: [],
      lineNumber: [],
      partnerId: [],
      remark: []
    });
    this.updateCalculation = [];
    this.selectedCustomCosting = [];
    this.customCostingCreate = [];
    this.incoTerms = [
      { value: "FoodApprovals", label: "FoodApprovals" },
      { value: "HandlingFees", label: "HandlingFees" },
      { value: "OtherApprovals", label: "OtherApprovals" },
      { value: "OtherCharges", label: "OtherCharges" }
    ];
  }
  ngOnInit() {
    console.log(this.data.code);
    if (this.data.title == "Customs Calculation") {
      this.form.controls.costDescription.patchValue(this.data.code[0].costDescription);
      this.form.controls.costDescription.disable();
      this.intialCall();
      this.cashNumberCall();
    }
  }
  intialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      this.service.search(obj).subscribe({
        next: (res) => {
          this.form.controls.costAmount.patchValue(res[0].docHandling);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }, 2e3);
  }
  cashNumberCall() {
    setTimeout(() => {
      this.spin.show();
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      obj.costCenter = [this.data.code[0].costCenter];
      this.CostingSheetService.search(obj).subscribe({
        next: (res) => {
          if (res.length > 0) {
            this.form.controls.cashNumber.patchValue(res[0].cashNumber);
            this.form.controls.lineNumber.patchValue(res.length + 1);
            this.form.controls.partnerId.patchValue(res[0].partnerId);
          } else {
            this.lineRef = "1";
            this.form.controls.lineNumber.patchValue(this.lineRef);
            this.form.controls.partnerId.patchValue(this.data.code[0].partnerId);
            this.spin.show();
            let obj2 = {};
            obj2.numberRangeObject = ["CashNumber"];
            this.numberRangeService.search(obj2).subscribe({
              next: (res2) => {
                if (res2.length > 0) {
                  this.nextNumber = Number(res2[0].numberRangeCurrent) + 1;
                  this.form.controls.cashNumber.patchValue(this.nextNumber);
                }
                this.spin.hide();
              },
              error: (err) => {
                this.spin.hide();
                this.cs.commonerrorNew(err);
              }
            });
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }, 2e3);
  }
  save() {
    if (this.data.title == "Custom Calculations") {
      this.data.code.forEach((element) => {
        element.lineNumber = null;
        element.costDescription = this.form.controls.costDescription.value;
        element.costAmount = this.form.controls.costAmount.value;
        element.statusId = 4;
        element.statusDescription = "Send to operations";
      });
      this.spin.show();
      this.CostingSheetService.CustomsCalculationBulkUpdate(this.data.code).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Updated",
            key: "br",
            detail: "Selected Values has been updated successfully"
          });
          this.dialogRef.close();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
    if (this.data.title == "Custom Calculation") {
      const element = {
        lineNumber: this.form.controls.lineNumber.value,
        costAmount: this.form.controls.costAmount.value,
        costCenter: this.data.code[0].costCenter,
        languageId: this.auth.languageId,
        companyId: this.auth.companyId,
        cashNumber: this.form.controls.cashNumber.value,
        partnerId: this.form.controls.partnerId.value
      };
      this.customCostingCreate.push(element);
      this.CostingSheetService.Create(this.customCostingCreate).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Created",
            key: "br",
            detail: "Selected Values has been Created successfully"
          });
          this.dialogRef.close();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      if (this.data.title != "Custom Calculation" && this.data.title != "Custom Calculations") {
        this.spin.show();
        let obj = {};
        obj.costCenter = [this.data.code[0].costCenter];
        this.CostingSheetService.searchCalculation(obj).subscribe({
          next: (res) => {
            this.updateCalculation = res;
            this.updateCalculation.forEach((element) => {
              element.remark = this.form.controls.remark.value;
              element.statusId = "4";
              element.statusDescription = "Send to Operation";
            });
            this.CostingSheetService.bulkUpdate(this.updateCalculation).subscribe({
              next: (res2) => {
                this.messageService.add({
                  severity: "success",
                  summary: "Updated",
                  key: "br",
                  detail: "Selected Values has been updated successfully"
                });
                this.dialogRef.close();
                this.spin.hide();
              },
              error: (err) => {
                this.spin.hide();
                this.cs.commonerrorNew(err);
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
    }
  }
};
_CostingSheetBulkupdateComponent.\u0275fac = function CostingSheetBulkupdateComponent_Factory(t) {
  return new (t || _CostingSheetBulkupdateComponent)(\u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CostingSheetService), \u0275\u0275directiveInject(NumberrangeService), \u0275\u0275directiveInject(CustomsChargesMasterService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService));
};
_CostingSheetBulkupdateComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CostingSheetBulkupdateComponent, selectors: [["app-costing-sheet-bulkupdate"]], decls: 17, vars: 7, consts: [[1, "bgWhite", "text-black", "w-93", "h-100", "m-4", "mt-4"], [1, "d-flex", "justify-content-between", "align-items-center"], ["class", "componentHeader f600 mb-0", 4, "ngIf"], [1, "mt-4", "pt-2"], [3, "formGroup"], [1, "d-flex-row"], [1, "row", "scrollNew"], ["class", "col-6 marginFieldNew borderRadius12", 4, "ngIf"], [1, "d-flex", "float-right", "py-2"], ["mat-dialog-close", "", 1, "buttom1", "textBlack", "mx-1"], ["type", "button", 1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "componentHeader", "f600", "mb-0"], [1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["formControlName", "costDescription", "appendTo", "body", "placeholder", "Select", 3, "showClear", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], ["maxlength", "50", "pInputText", "", "appTrim", "", "formControlName", "costDescription", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["maxlength", "50", "pInputText", "", "appTrim", "", "formControlName", "costAmount", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["maxlength", "50", "pInputText", "", "appTrim", "", "formControlName", "remark", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"]], template: function CostingSheetBulkupdateComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1);
    \u0275\u0275template(2, CostingSheetBulkupdateComponent_p_2_Template, 2, 1, "p", 2)(3, CostingSheetBulkupdateComponent_p_3_Template, 2, 1, "p", 2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 3)(5, "form", 4)(6, "div", 5)(7, "div", 6);
    \u0275\u0275template(8, CostingSheetBulkupdateComponent_div_8_Template, 5, 8, "div", 7)(9, CostingSheetBulkupdateComponent_div_9_Template, 4, 0, "div", 7)(10, CostingSheetBulkupdateComponent_div_10_Template, 4, 0, "div", 7)(11, CostingSheetBulkupdateComponent_div_11_Template, 4, 0, "div", 7);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "div", 8)(13, "button", 9);
    \u0275\u0275text(14, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "button", 10);
    \u0275\u0275listener("click", function CostingSheetBulkupdateComponent_Template_button_click_15_listener() {
      return ctx.save();
    });
    \u0275\u0275text(16, "Save");
    \u0275\u0275elementEnd()()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.data.title == "Custom Calculations");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.data.title != "Custom Calculations");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance(3);
    \u0275\u0275property("ngIf", ctx.data.title == "Custom Calculations");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.data.title == "Custom Calculation");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.data.title == "Custom Calculation" || ctx.data.title == "Custom Calculations");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.data.title == "Send To Operation");
  }
}, dependencies: [NgIf, PrimeTemplate, Dropdown, InputText, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, MatDialogClose, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 40rem);\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=costing-sheet-bulkupdate.component.css.map */"] });
var CostingSheetBulkupdateComponent = _CostingSheetBulkupdateComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CostingSheetBulkupdateComponent, { className: "CostingSheetBulkupdateComponent", filePath: "src\\app\\main\\airport\\costing-sheet\\costing-sheet-bulkupdate\\costing-sheet-bulkupdate.component.ts", lineNumber: 21 });
})();

// src/app/main/airport/costing-sheet/costing-sheet.component.ts
var _c02 = ["costingSheet"];
var _c1 = () => ({ width: "180" });
var _c2 = () => ({ width: "80vw" });
var _c3 = () => ({ "width": "100%" });
var _c4 = () => ({ "width": "120rem" });
function CostingSheetComponent_button_12_Template(rf, ctx) {
  if (rf & 1) {
    const _r3 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "button", 40);
    \u0275\u0275listener("click", function CostingSheetComponent_button_12_Template_button_click_0_listener() {
      \u0275\u0275restoreView(_r3);
      const ctx_r3 = \u0275\u0275nextContext();
      const op_r2 = \u0275\u0275reference(10);
      ctx_r3.sendToFinance();
      return \u0275\u0275resetView(op_r2.hide());
    });
    \u0275\u0275element(1, "img", 41);
    \u0275\u0275text(2, " Send to Finance");
    \u0275\u0275elementEnd();
  }
}
function CostingSheetComponent_button_13_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "button", 40);
    \u0275\u0275listener("click", function CostingSheetComponent_button_13_Template_button_click_0_listener() {
      \u0275\u0275restoreView(_r5);
      const ctx_r3 = \u0275\u0275nextContext();
      const op_r2 = \u0275\u0275reference(10);
      ctx_r3.updateBulk();
      return \u0275\u0275resetView(op_r2.hide());
    });
    \u0275\u0275element(1, "img", 42);
    \u0275\u0275text(2, "Bulk Update");
    \u0275\u0275elementEnd();
  }
}
function CostingSheetComponent_button_14_Template(rf, ctx) {
  if (rf & 1) {
    const _r6 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "button", 40);
    \u0275\u0275listener("click", function CostingSheetComponent_button_14_Template_button_click_0_listener() {
      \u0275\u0275restoreView(_r6);
      const ctx_r3 = \u0275\u0275nextContext();
      const op_r2 = \u0275\u0275reference(10);
      ctx_r3.sendToOperations();
      return \u0275\u0275resetView(op_r2.hide());
    });
    \u0275\u0275element(1, "img", 41);
    \u0275\u0275text(2, "Send to Operations");
    \u0275\u0275elementEnd();
  }
}
function CostingSheetComponent_button_15_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "button", 40);
    \u0275\u0275listener("click", function CostingSheetComponent_button_15_Template_button_click_0_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r3 = \u0275\u0275nextContext();
      const op_r2 = \u0275\u0275reference(10);
      ctx_r3.approve("null", "Bulk");
      return \u0275\u0275resetView(op_r2.hide());
    });
    \u0275\u0275element(1, "img", 41);
    \u0275\u0275text(2, "Approve");
    \u0275\u0275elementEnd();
  }
}
function CostingSheetComponent_ng_template_51_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 50);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 51);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const column_r10 = ctx.$implicit;
    \u0275\u0275property("pSortableColumn", column_r10);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", column_r10, " ");
    \u0275\u0275advance();
    \u0275\u0275property("field", column_r10);
  }
}
function CostingSheetComponent_ng_template_51_th_9_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 52);
    \u0275\u0275listener("input", function CostingSheetComponent_ng_template_51_th_9_Template_input_input_1_listener($event) {
      const column_r12 = \u0275\u0275restoreView(_r11).$implicit;
      \u0275\u0275nextContext(2);
      const expenseTag_r9 = \u0275\u0275reference(50);
      return \u0275\u0275resetView(expenseTag_r9.filter($event.target == null ? null : $event.target.value, column_r12, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const column_r12 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const expenseTag_r9 = \u0275\u0275reference(50);
    \u0275\u0275advance();
    \u0275\u0275property("value", expenseTag_r9.filters[column_r12] == null ? null : expenseTag_r9.filters[column_r12].value);
  }
}
function CostingSheetComponent_ng_template_51_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 43);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 44);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CostingSheetComponent_ng_template_51_th_3_Template, 3, 3, "th", 45);
    \u0275\u0275elementStart(4, "th", 46);
    \u0275\u0275text(5, "Actions");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(6, "tr")(7, "th", 43);
    \u0275\u0275element(8, "p-tableHeaderCheckbox", 47);
    \u0275\u0275elementEnd();
    \u0275\u0275template(9, CostingSheetComponent_ng_template_51_th_9_Template, 2, 1, "th", 48);
    \u0275\u0275elementStart(10, "th", 46);
    \u0275\u0275element(11, "input", 49);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const columns_r13 = ctx.$implicit;
    \u0275\u0275advance(3);
    \u0275\u0275property("ngForOf", columns_r13);
    \u0275\u0275advance(5);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function CostingSheetComponent_ng_template_52_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const column_r15 = \u0275\u0275nextContext().$implicit;
    const rowData_r16 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r16[column_r15], " ");
  }
}
function CostingSheetComponent_ng_template_52_td_3_ng_container_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275pipe(2, "date");
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const column_r15 = \u0275\u0275nextContext().$implicit;
    const rowData_r16 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", \u0275\u0275pipeBind2(2, 1, rowData_r16[column_r15], "dd-MM-yyyy"), " ");
  }
}
function CostingSheetComponent_ng_template_52_td_3_ng_container_3_Template(rf, ctx) {
  if (rf & 1) {
    const _r17 = \u0275\u0275getCurrentView();
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275elementStart(1, "span", 60);
    \u0275\u0275listener("click", function CostingSheetComponent_ng_template_52_td_3_ng_container_3_Template_span_click_1_listener() {
      \u0275\u0275restoreView(_r17);
      const rowData_r16 = \u0275\u0275nextContext(2).$implicit;
      const ctx_r3 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r3.openCrud("Edit", rowData_r16));
    });
    \u0275\u0275text(2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const column_r15 = \u0275\u0275nextContext().$implicit;
    const rowData_r16 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(rowData_r16[column_r15]);
  }
}
function CostingSheetComponent_ng_template_52_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, CostingSheetComponent_ng_template_52_td_3_ng_container_1_Template, 2, 1, "ng-container", 59)(2, CostingSheetComponent_ng_template_52_td_3_ng_container_2_Template, 3, 4, "ng-container", 59)(3, CostingSheetComponent_ng_template_52_td_3_ng_container_3_Template, 3, 1, "ng-container", 59);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const column_r15 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", column_r15 != "Created On" && column_r15 != "Manifest");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", column_r15 === "Created On");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", column_r15 === "Manifest");
  }
}
function CostingSheetComponent_ng_template_52_button_17_Template(rf, ctx) {
  if (rf & 1) {
    const _r20 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "button", 40);
    \u0275\u0275listener("click", function CostingSheetComponent_ng_template_52_button_17_Template_button_click_0_listener() {
      \u0275\u0275restoreView(_r20);
      const rowData_r16 = \u0275\u0275nextContext().$implicit;
      const op_r18 = \u0275\u0275reference(7);
      const ctx_r3 = \u0275\u0275nextContext();
      ctx_r3.approve(rowData_r16, "single");
      return \u0275\u0275resetView(op_r18.hide());
    });
    \u0275\u0275element(1, "img", 41);
    \u0275\u0275text(2, "Approve");
    \u0275\u0275elementEnd();
  }
}
function CostingSheetComponent_ng_template_52_Template(rf, ctx) {
  if (rf & 1) {
    const _r14 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr")(1, "td", 53);
    \u0275\u0275element(2, "p-tableCheckbox", 54);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CostingSheetComponent_ng_template_52_td_3_Template, 4, 3, "td", 48);
    \u0275\u0275elementStart(4, "td", 55)(5, "img", 56);
    \u0275\u0275listener("click", function CostingSheetComponent_ng_template_52_Template_img_click_5_listener($event) {
      \u0275\u0275restoreView(_r14);
      const op_r18 = \u0275\u0275reference(7);
      return \u0275\u0275resetView(op_r18.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "p-overlayPanel", null, 0)(8, "div", 12)(9, "input", 57, 4);
    \u0275\u0275listener("change", function CostingSheetComponent_ng_template_52_Template_input_change_9_listener($event) {
      const rowData_r16 = \u0275\u0275restoreView(_r14).$implicit;
      const ctx_r3 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r3.onFileSelected($event, rowData_r16));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "button", 40);
    \u0275\u0275listener("click", function CostingSheetComponent_ng_template_52_Template_button_click_11_listener() {
      \u0275\u0275restoreView(_r14);
      const op_r18 = \u0275\u0275reference(7);
      const fileInput_r19 = \u0275\u0275reference(10);
      fileInput_r19.click();
      return \u0275\u0275resetView(op_r18.hide());
    });
    \u0275\u0275element(12, "img", 42);
    \u0275\u0275text(13, "Upload");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(14, "button", 40);
    \u0275\u0275listener("click", function CostingSheetComponent_ng_template_52_Template_button_click_14_listener() {
      const rowData_r16 = \u0275\u0275restoreView(_r14).$implicit;
      const op_r18 = \u0275\u0275reference(7);
      const ctx_r3 = \u0275\u0275nextContext();
      ctx_r3.downloadMerge(rowData_r16);
      return \u0275\u0275resetView(op_r18.hide());
    });
    \u0275\u0275element(15, "img", 58);
    \u0275\u0275text(16, "Download");
    \u0275\u0275elementEnd();
    \u0275\u0275template(17, CostingSheetComponent_ng_template_52_button_17_Template, 3, 0, "button", 13);
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    const rowData_r16 = ctx.$implicit;
    const columns_r21 = ctx.columns;
    const ctx_r3 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r3.selectedCostingSheet[0] === rowData_r16);
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r16);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r21);
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(7, _c1));
    \u0275\u0275advance(11);
    \u0275\u0275property("ngIf", ctx_r3.activeLink == "finance");
  }
}
function CostingSheetComponent_ng_template_53_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 61);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function CostingSheetComponent_ng_template_56_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "button", 62);
    \u0275\u0275text(1, "Edit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(2, "button", 62);
    \u0275\u0275text(3, "Confirm");
    \u0275\u0275elementEnd();
  }
}
var _CostingSheetComponent = class _CostingSheetComponent {
  constructor(messageService, cs, router, path, service, dialog, datePipe, auth, fb, spin, sanitizer, ConsignmentService2) {
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
    this.sanitizer = sanitizer;
    this.ConsignmentService = ConsignmentService2;
    this.expenseTable = [];
    this.selectedCostingSheet = [];
    this.cols = [];
    this.target = [];
    this.columns = [];
    this.rowsArray = [];
    this.searchform = this.fb.group({
      companyId: [[this.auth.companyId]],
      costCenter: [],
      cashNumber: [],
      partnerId: [],
      languageId: [[this.auth.languageId]],
      statusId: []
    });
    this.fieldDisplayNames = {
      costCenter: "Cost Center",
      cashNumber: "Cash Number",
      partnerId: "Partner ID"
    };
    this.partnerIdDropdown = [];
    this.cashNumberDropdown = [];
    this.costCenterDropdown = [];
    this.selectedFiles = null;
  }
  ngOnInit() {
    const link = this.router.url;
    this.activeLink = link.split("/")[2];
    if (this.activeLink == "airport") {
      const dataToSend = ["Mid-Mile", "Customs Costing"];
      this.path.setData(dataToSend);
    } else {
      const dataToSend = ["Finance", "Customs Costing"];
      this.path.setData(dataToSend);
    }
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "costCenter", header: "Manifest", format: "hyperLink" },
      { field: "partnerName", header: "Customer" },
      { field: "costDescription", header: "Global" },
      { field: "costDescription", header: "Approval" },
      { field: "createdBy", header: "Customs" },
      { field: "createdBy", header: "Stamps" },
      { field: "statusDescription", header: "Status" },
      { field: "total", header: "Total" },
      { field: "createdOn", header: "Created On", format: "date" }
    ];
    this.target = [];
  }
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      this.service.search(this.searchform.getRawValue()).subscribe({
        next: (res) => {
          if (this.activeLink == "finance") {
            var result = res.filter((x) => x.finance == true);
            this.transformData(result);
          } else {
            this.transformData(res);
          }
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
    const choosen = this.selectedCostingSheet[this.selectedCostingSheet.length - 1];
    this.selectedCostingSheet.length = 0;
    this.selectedCostingSheet.push(choosen);
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedCostingSheet = linedata;
    }
    if (this.selectedCostingSheet.length === 0 && type != "New") {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
      return;
    } else {
      if (type != "New") {
        if ((linedata.statusId == "3" || linedata.statusId == "5") && this.activeLink == "airport") {
          let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCostingSheet[0] : linedata, pageflow: "Display", module: this.activeLink == "finance" ? "Finance" : "Mid-Mile" });
          this.router.navigate(["/main/airport/costingSheet-new/" + paramdata]);
          return;
        } else {
          let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCostingSheet[0] : linedata, pageflow: type, module: this.activeLink == "finance" ? "Finance" : "Mid-Mile" });
          this.router.navigate(["/main/airport/costingSheet-new/" + paramdata]);
          return;
        }
      } else {
        let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedCostingSheet[0] : linedata, pageflow: type, module: this.activeLink == "finance" ? "Finance" : "Mid-Mile" });
        this.router.navigate(["/main/airport/costingSheet-new/" + paramdata]);
        return;
      }
    }
  }
  deleteDialog() {
    if (this.selectedCostingSheet.length === 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
      return;
    }
    const dialogRef = this.dialog.open(DeleteComponent, {
      disableClose: true,
      width: "60%",
      maxWidth: "82%",
      position: { top: "6.5%", left: "30%" },
      data: { line: this.selectedCostingSheet, module: "Customs  Costing", body: "This action cannot be undone. All values associated with this field will be lost." }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedCostingSheet[0]);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    const costCenter = this.selectedCostingSheet.map((item) => item.costCenter);
    this.service.searchCalculation({ costCenter }).subscribe({
      next: (result) => {
        this.service.Delete(result).subscribe({
          next: (res) => {
            this.messageService.add({ severity: "success", summary: "Deleted", key: "br", detail: lines.costCenter + " deleted successfully" });
            this.spin.hide();
            this.initialCall();
          },
          error: (err) => {
            this.cs.commonerrorNew(err);
            this.spin.hide();
          }
        });
      }
    });
  }
  sendToFinance() {
    const costCenterResult = this.selectedCostingSheet.map((item) => item.costCenter);
    this.spin.show();
    this.service.searchCalculation({ costCenter: costCenterResult }).subscribe({
      next: (res) => {
        res.forEach((x) => {
          x.finance = true;
          x.statusId = 3;
          x.statusDescription = "Send to finance";
        });
        this.service.UpdateCustomCosting(res).subscribe({
          next: (res2) => {
            this.messageService.add({
              severity: "success",
              summary: "Updated",
              key: "br",
              detail: "Selected Values has been updated successfully"
            });
            this.router.navigate(["/main/airport/costingSheet"]);
            this.spin.hide();
          },
          error: (err) => {
            this.spin.hide();
            this.cs.commonerrorNew(err);
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
  downloadExcel() {
    const exportData = this.expenseTable.map((item) => {
      const exportItem = {};
      const keys = Object.keys(item).filter((key) => key !== "Created By" && key !== "Created On");
      keys.push("Created By", "Created On");
      keys.forEach((key) => {
        if (key === "Created On") {
          exportItem[key] = this.datePipe.transform(item[key], "dd-MM-yyyy");
        } else {
          exportItem[key] = item[key];
        }
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, "Customs Costing");
  }
  transformData(result) {
    console.log(result);
    const tempColumns = /* @__PURE__ */ new Set();
    const tempRows = {};
    result.forEach((item) => {
      tempColumns.add(item.costDescription);
      if (!tempRows[item.costCenter]) {
        tempRows[item.costCenter] = {};
      }
      tempRows[item.costCenter][item.costDescription] = item.costAmount;
      tempRows[item.costCenter]["Customer"] = item.partnerName;
      tempRows[item.costCenter]["Manifest"] = item.costCenter;
      tempRows[item.costCenter]["costCenter"] = item.costCenter;
      tempRows[item.costCenter]["partnerId"] = item.partnerId;
      tempRows[item.costCenter]["companyId"] = item.companyId;
      tempRows[item.costCenter]["languageId"] = item.languageId;
      tempRows[item.costCenter]["Status"] = item.statusDescription;
      tempRows[item.costCenter]["Created By"] = item.createdBy;
      tempRows[item.costCenter]["Created On"] = item.createdOn;
      tempRows[item.costCenter]["cashNumber"] = item.cashNumber;
      tempRows[item.costCenter]["finance"] = item.finance;
      tempRows[item.costCenter]["statusId"] = item.statusId;
      tempRows[item.costCenter]["Total"] = item.totalCostAmount;
      tempRows[item.costCenter]["referenceField1"] = item.referenceField1;
    });
    this.columns = Array.from(tempColumns);
    this.columns.unshift("Customer");
    this.columns.unshift("Manifest");
    this.columns.unshift("Status");
    this.columns.push("Total");
    this.columns.push("Created By", "Created On");
    this.expenseTable = Object.keys(tempRows).map((key) => {
      return __spreadValues({ costCenter: key }, tempRows[key]);
    });
  }
  getSearchDropdown() {
    this.expenseTable.forEach((res) => {
      if (res.partnerId != null) {
        this.partnerIdDropdown.push({ value: res.partnerId, label: res.partnerId });
        this.partnerIdDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerIdDropdown, "value");
      }
      if (res.costCenter != null) {
        this.costCenterDropdown.push({ value: res.costCenter, label: res.costCenter });
        this.costCenterDropdown = this.cs.removeDuplicatesFromArrayList(this.costCenterDropdown, "value");
      }
      if (res.cashNumber != null) {
        this.cashNumberDropdown.push({ value: res.cashNumber, label: res.cashNumber });
        this.cashNumberDropdown = this.cs.removeDuplicatesFromArrayList(this.cashNumberDropdown, "value");
      }
    });
  }
  closeOverLay() {
    this.overlayPanel.hide();
  }
  search() {
    this.fieldsWithValue = null;
    const formValues = this.searchform.value;
    this.fieldsWithValue = Object.keys(formValues).filter((key) => formValues[key] !== null && formValues[key] !== void 0).map((key) => this.fieldDisplayNames[key] || key);
    this.spin.show();
    this.service.search(this.searchform.getRawValue()).subscribe({
      next: (res) => {
        if (this.activeLink == "finance") {
          var result = res.filter((x) => x.finance == true);
          this.transformData(result);
        } else {
          this.transformData(res);
        }
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
    this.search();
  }
  chipClear(value) {
    const formControlKey = Object.keys(this.fieldDisplayNames).find((key) => this.fieldDisplayNames[key] === value.value);
    if (formControlKey) {
      this.searchform.get(formControlKey)?.reset();
      this.search();
    }
  }
  downloadMerge(element) {
    return __async(this, null, function* () {
      console.log(element);
      this.spin.show();
      let obj = {};
      obj.path = "customsCosting/" + element.costCenter;
      obj.outputPath = "customsCosting/" + element.costCenter + ".pdf";
      const blob = yield this.service.mergeInvoice(obj).catch((err) => {
        this.cs.commonerrorNew(err);
      });
      this.spin.hide();
      if (blob) {
        const blobOb = new Blob([blob], {
          type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        });
        this.fileUrldownload = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blobOb));
        let docurl = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = docurl;
        a.download = "customsCosting/" + element.costCenter + ".pdf";
        a.click();
        URL.revokeObjectURL(docurl);
      }
      this.spin.hide();
    });
  }
  onFileSelected(event, line) {
    const file = event.target.files[0];
    this.selectedFiles = file;
    const newFileName = line.referenceField1;
    const newFile = new File([file], newFileName, { type: file.type });
    let location = "customsCosting/" + line.costCenter + "/";
    this.ConsignmentService.uploadPDFConvert(newFile, location).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: "success",
          summary: "Updated",
          key: "br",
          detail: "File uploaded successfully"
        });
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
  updateBulk() {
    const dialogRef = this.dialog.open(CostingSheetBulkupdateComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" },
      data: { title: "Custom Calculations", code: this.selectedCostingSheet }
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.initialCall();
    });
  }
  sendToOperations() {
    if (this.selectedCostingSheet.length == 0) {
      this.messageService.add({
        severity: "warn",
        summary: "Warning",
        key: "br",
        detail: "Kindly select any row"
      });
    } else {
      const dialogRef = this.dialog.open(CostingSheetBulkupdateComponent, {
        disableClose: true,
        width: "70%",
        maxWidth: "80%",
        position: { top: "6.5%", left: "30%" },
        data: { title: "Send To Operation", code: this.selectedCostingSheet }
      });
      dialogRef.afterClosed().subscribe((result) => {
        this.initialCall();
      });
    }
  }
  approve(data = null, type = null) {
    if (type == "single") {
      let obj = {};
      obj.companyId = [this.auth.companyId];
      obj.languageId = [this.auth.languageId];
      obj.partnerMasterAirWayBill = [data.costCenter];
      this.spin.show();
      this.service.approveCostSheet(obj).subscribe({
        next: (res) => {
          this.messageService.add({ severity: "success", summary: "Approved", key: "br", detail: "Records approved successfully" });
          this.initialCall();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
    if (type == "Bulk") {
      if (this.selectedCostingSheet.length === 0) {
        this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
        return;
      }
      let obj = {};
      obj.companyId = [this.auth.companyId];
      obj.languageId = [this.auth.languageId];
      obj.partnerMasterAirWayBill = this.selectedCostingSheet.map((item) => item.costCenter);
      this.spin.show();
      this.service.approveCostSheet(obj).subscribe({
        next: (res) => {
          this.messageService.add({ severity: "success", summary: "Approved", key: "br", detail: "Records approved successfully" });
          this.initialCall();
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
};
_CostingSheetComponent.\u0275fac = function CostingSheetComponent_Factory(t) {
  return new (t || _CostingSheetComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(CostingSheetService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(DomSanitizer), \u0275\u0275directiveInject(ConsignmentService));
};
_CostingSheetComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CostingSheetComponent, selectors: [["app-costing-sheet"]], viewQuery: function CostingSheetComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c02, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 57, vars: 42, consts: [["op", ""], ["costingSheet", ""], ["expenseTag", ""], ["menu", "matMenu"], ["fileInput", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], [1, "d-flex", "flex-column"], ["style", "width: 8rem;", "mat-menu-item", "", "class", "w-100", 3, "click", 4, "ngIf"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "cashNumber", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "costCenter", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "multiple", "sortField", "cashNumber", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "selection", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [1, "customClass"], ["matMenuContent", ""], ["mat-menu-item", "", 1, "w-100", 2, "width", "8rem", 3, "click"], ["src", "./assets/common2x/tick.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/upload.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], [2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], ["pFrozenColumn", "", "alignFrozen", "right"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "value"], ["pFrozenColumn", "", "alignFrozen", "right", 2, "padding-left", "1.5rem !important"], ["type", "button", "src", "./assets/common/actions.png", "alt", "", "srcset", "", 2, "height", "1.4rem", 3, "click"], ["type", "file", 2, "display", "none", 3, "change"], ["src", "./assets/common/download.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"], ["mat-menu-item", ""]], template: function CostingSheetComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 5)(1, "div", 6)(2, "p", 7);
    \u0275\u0275text(3, "Customs Costing ");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 6)(5, "img", 8);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 9);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 10);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "img", 11);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_img_click_8_listener($event) {
      \u0275\u0275restoreView(_r1);
      const op_r2 = \u0275\u0275reference(10);
      return \u0275\u0275resetView(op_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "p-overlayPanel", null, 0)(11, "div", 12);
    \u0275\u0275template(12, CostingSheetComponent_button_12_Template, 3, 0, "button", 13)(13, CostingSheetComponent_button_13_Template, 3, 0, "button", 13)(14, CostingSheetComponent_button_14_Template, 3, 0, "button", 13)(15, CostingSheetComponent_button_15_Template, 3, 0, "button", 13);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(16, "button", 14);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_button_click_16_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("New"));
    });
    \u0275\u0275element(17, "i", 15);
    \u0275\u0275text(18, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(19, "div", 16)(20, "p-iconField", 17)(21, "p-inputIcon", 18);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_p_inputIcon_click_21_listener($event) {
      \u0275\u0275restoreView(_r1);
      const costingSheet_r8 = \u0275\u0275reference(24);
      return \u0275\u0275resetView(costingSheet_r8.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(22, "input", 19);
    \u0275\u0275listener("input", function CostingSheetComponent_Template_input_input_22_listener($event) {
      \u0275\u0275restoreView(_r1);
      const expenseTag_r9 = \u0275\u0275reference(50);
      return \u0275\u0275resetView(expenseTag_r9.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(23, "p-overlayPanel", 20, 1)(25, "form", 21)(26, "div", 22)(27, "div", 23)(28, "p", 24);
    \u0275\u0275text(29, "Cash Number");
    \u0275\u0275elementEnd();
    \u0275\u0275element(30, "p-multiSelect", 25);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(31, "div", 23)(32, "p", 24);
    \u0275\u0275text(33, "Cost Center");
    \u0275\u0275elementEnd();
    \u0275\u0275element(34, "p-multiSelect", 26);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(35, "div", 23)(36, "p", 24);
    \u0275\u0275text(37, "Partner ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(38, "p-multiSelect", 27);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(39, "div", 28)(40, "div")(41, "img", 29);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_img_click_41_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(42, "div")(43, "button", 30);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_button_click_43_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(44, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(45, "button", 31);
    \u0275\u0275listener("click", function CostingSheetComponent_Template_button_click_45_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(46, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(47, "p-chips", 32);
    \u0275\u0275listener("onRemove", function CostingSheetComponent_Template_p_chips_onRemove_47_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function CostingSheetComponent_Template_p_chips_ngModelChange_47_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(48, "div", 33)(49, "p-table", 34, 2);
    \u0275\u0275twoWayListener("selectionChange", function CostingSheetComponent_Template_p_table_selectionChange_49_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedCostingSheet, $event) || (ctx.selectedCostingSheet = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(51, CostingSheetComponent_ng_template_51_Template, 12, 3, "ng-template", 35)(52, CostingSheetComponent_ng_template_52_Template, 18, 8, "ng-template", 36)(53, CostingSheetComponent_ng_template_53_Template, 4, 1, "ng-template", 37);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(54, "mat-menu", 38, 3);
    \u0275\u0275template(56, CostingSheetComponent_ng_template_56_Template, 4, 0, "ng-template", 39);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    \u0275\u0275advance(9);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(33, _c1));
    \u0275\u0275advance(3);
    \u0275\u0275property("ngIf", ctx.activeLink == "airport");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.activeLink == "finance");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.activeLink == "finance");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.activeLink == "finance");
    \u0275\u0275advance(8);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(34, _c2));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(35, _c3));
    \u0275\u0275property("showClear", true)("options", ctx.cashNumberDropdown)("panelStyle", \u0275\u0275pureFunction0(36, _c3));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(37, _c3));
    \u0275\u0275property("showClear", true)("options", ctx.costCenterDropdown)("panelStyle", \u0275\u0275pureFunction0(38, _c3));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(39, _c3));
    \u0275\u0275property("showClear", true)("options", ctx.partnerIdDropdown)("panelStyle", \u0275\u0275pureFunction0(40, _c3));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.columns)("value", ctx.expenseTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx.selectedCostingSheet);
    \u0275\u0275property("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(41, _c4));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, FrozenColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, InputIcon, IconField, InputText, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenu, MatMenuItem, MatMenuContent, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=costing-sheet.component.css.map */"] });
var CostingSheetComponent = _CostingSheetComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CostingSheetComponent, { className: "CostingSheetComponent", filePath: "src\\app\\main\\airport\\costing-sheet\\costing-sheet.component.ts", lineNumber: 24 });
})();

export {
  CostingSheetBulkupdateComponent,
  CostingSheetComponent
};
//# sourceMappingURL=chunk-ZSWBHVU4.js.map
