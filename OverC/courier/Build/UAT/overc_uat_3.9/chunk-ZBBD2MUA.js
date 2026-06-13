import {
  MAT_DIALOG_DATA,
  MatDialogClose,
  MatDialogRef,
  PickList,
  PrimeTemplate
} from "./chunk-XFYC4BWK.js";
import {
  ChangeDetectorRef,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵdefineComponent,
  ɵɵdirectiveInject,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵproperty,
  ɵɵtemplate,
  ɵɵtext,
  ɵɵtextInterpolate1
} from "./chunk-Z5YEPTLN.js";

// src/app/common-dialog/custom-table/custom-table.component.ts
function CustomTableComponent_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 6)(1, "div", 7)(2, "span", 8);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const product_r1 = ctx.$implicit;
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1(" ", product_r1.field, " ");
  }
}
var _CustomTableComponent = class _CustomTableComponent {
  constructor(cdr, dialogRef, data) {
    this.cdr = cdr;
    this.dialogRef = dialogRef;
    this.data = data;
  }
  ngOnInit() {
    this.sourceProducts = this.data.source;
    this.targetProducts = this.data.target;
  }
};
_CustomTableComponent.\u0275fac = function CustomTableComponent_Factory(t) {
  return new (t || _CustomTableComponent)(\u0275\u0275directiveInject(ChangeDetectorRef), \u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA));
};
_CustomTableComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CustomTableComponent, selectors: [["app-custom-table"]], decls: 8, vars: 4, consts: [[1, ""], ["sourceHeader", "Available Columns", "targetHeader", "Shown Columns", "filterBy", "field", 3, "source", "target", "dragdrop", "responsive"], ["pTemplate", "item"], [1, "d-flex", "justify-content-center", "align-items-center", "py-2"], ["mat-dialog-close", "", "type", "button", 1, "btn", "mr-2", "cancelButton"], ["type", "button", 1, "btn", "ml-2", "saveButton"], [1, "flex", "flex-wrap", "p-2", "align-items-center", "gap-3"], [1, "flex-1", "flex", "flex-column", "gap-2"], [1, "font-bold"]], template: function CustomTableComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "p-pickList", 1);
    \u0275\u0275template(2, CustomTableComponent_ng_template_2_Template, 4, 1, "ng-template", 2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "div", 3)(4, "button", 4);
    \u0275\u0275text(5, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "button", 5);
    \u0275\u0275text(7, "Yes");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275property("source", ctx.sourceProducts)("target", ctx.targetProducts)("dragdrop", true)("responsive", true);
  }
}, dependencies: [PrimeTemplate, PickList, MatDialogClose], styles: ["\n\n.p-picklist[_ngcontent-%COMP%]   .p-picklist-list-wrapper[_ngcontent-%COMP%] {\n  background-color: var(--black) !important;\n  color: white;\n}\n.header[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 16px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n/*# sourceMappingURL=custom-table.component.css.map */"] });
var CustomTableComponent = _CustomTableComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CustomTableComponent, { className: "CustomTableComponent", filePath: "src\\app\\common-dialog\\custom-table\\custom-table.component.ts", lineNumber: 9 });
})();

export {
  CustomTableComponent
};
//# sourceMappingURL=chunk-ZBBD2MUA.js.map
