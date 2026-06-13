import {
  MAT_DIALOG_DATA,
  MatDialogClose,
  MatDialogRef,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵdefineComponent,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵlistener,
  ɵɵtext,
  ɵɵtextInterpolate,
  ɵɵtextInterpolate1
} from "./chunk-HJPREMTO.js";

// src/app/common-dialog/delete/delete.component.ts
var _DeleteComponent = class _DeleteComponent {
  constructor(dialogRef, data) {
    this.dialogRef = dialogRef;
    this.data = data;
  }
  delete() {
    this.dialogRef.close("delete");
  }
};
_DeleteComponent.\u0275fac = function DeleteComponent_Factory(t) {
  return new (t || _DeleteComponent)(\u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA));
};
_DeleteComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _DeleteComponent, selectors: [["app-delete"]], decls: 12, vars: 2, consts: [[1, "bgBlack", "w-100", "text-white", "deleteDialog"], [1, "d-flex", "justify-content-center"], ["src", "./assets/common/delete.png", "alt", "", "srcset", "", 1, "text-center", 2, "width", "3rem"], [1, "deleteHeader", "mb-0", "text-center", "mt-3"], [1, "deleteBody", "px-3", "mb-0", "text-center"], [1, "d-flex", "justify-content-center", "align-items-center", "py-2"], ["mat-dialog-close", "", "type", "button", 1, "btn", "mr-2", "cancelButton"], ["type", "button", 1, "btn", "ml-2", "saveButton", 3, "click"]], template: function DeleteComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1);
    \u0275\u0275element(2, "img", 2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "h1", 3);
    \u0275\u0275text(4);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "p", 4);
    \u0275\u0275text(6);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "div", 5)(8, "button", 6);
    \u0275\u0275text(9, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "button", 7);
    \u0275\u0275listener("click", function DeleteComponent_Template_button_click_10_listener() {
      return ctx.delete();
    });
    \u0275\u0275text(11, "Yes");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(4);
    \u0275\u0275textInterpolate1("Are you sure, Delete ", ctx.data.module, "?");
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx.data.body);
  }
}, dependencies: [MatDialogClose], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n/*# sourceMappingURL=delete.component.css.map */"] });
var DeleteComponent = _DeleteComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(DeleteComponent, { className: "DeleteComponent", filePath: "src\\app\\common-dialog\\delete\\delete.component.ts", lineNumber: 9 });
})();

export {
  DeleteComponent
};
//# sourceMappingURL=chunk-ONTB56N3.js.map
