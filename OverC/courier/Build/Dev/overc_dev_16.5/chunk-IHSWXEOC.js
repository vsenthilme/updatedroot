import {
  ConsignmentService
} from "./chunk-2GU6DF4U.js";
import {
  CommonAPIService
} from "./chunk-EGWAMV4K.js";
import {
  PathNameService
} from "./chunk-O6NDCGFA.js";
import {
  ActivatedRoute,
  AuthService,
  CommonServiceService,
  DefaultValueAccessor,
  Dropdown,
  FormBuilder,
  FormControlName,
  FormGroupDirective,
  InputText,
  MAT_DIALOG_DATA,
  MatDialogClose,
  MatDialogRef,
  MaxLengthValidator,
  MessageService,
  NgControlStatus,
  NgControlStatusGroup,
  NgIf,
  NgxSpinnerService,
  PrimeTemplate,
  Router,
  TrimDirective,
  ɵNgNoValidate,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵdefineComponent,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵlistener,
  ɵɵnextContext,
  ɵɵproperty,
  ɵɵpureFunction0,
  ɵɵstyleMap,
  ɵɵtemplate,
  ɵɵtext,
  ɵɵtextInterpolate1
} from "./chunk-HJPREMTO.js";

// src/app/main/operation/consignment/consignment-updatebulk/consignment-updatebulk.component.ts
var _c0 = () => ({ "width": "100%" });
function ConsignmentUpdatebulkComponent_ng_template_16_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 21);
  }
}
function ConsignmentUpdatebulkComponent_ng_template_21_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 21);
  }
}
function ConsignmentUpdatebulkComponent_div_22_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 21);
  }
}
function ConsignmentUpdatebulkComponent_div_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 7)(1, "p", 8);
    \u0275\u0275text(2, "HUB");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(3, "p-dropdown", 22);
    \u0275\u0275template(4, ConsignmentUpdatebulkComponent_div_22_ng_template_4_Template, 1, 0, "ng-template", 11);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r0 = \u0275\u0275nextContext();
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(5, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r0.hubList)("panelStyle", \u0275\u0275pureFunction0(6, _c0));
  }
}
function ConsignmentUpdatebulkComponent_ng_template_31_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 21);
  }
}
function ConsignmentUpdatebulkComponent_ng_template_36_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 21);
  }
}
function ConsignmentUpdatebulkComponent_ng_template_41_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 21);
  }
}
var _ConsignmentUpdatebulkComponent = class _ConsignmentUpdatebulkComponent {
  constructor(dialogRef, data, cs, spin, route, router, path, fb, service, messageService, cas, auth) {
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
    this.status = [];
    this.incoTerms = [];
    this.form = this.fb.group({
      actualCurrency: [],
      actualValue: [],
      airportOriginCode: [],
      bondedId: [],
      companyId: [],
      companyName: [],
      consigneeCivilId: [],
      consigneeName: [],
      consignmentCurrency: [],
      consignmentValue: [],
      consoleId: [],
      countryOfOrigin: [],
      createdBy: [],
      createdOn: [],
      currency: [],
      customsCurrency: [],
      customsKd: [],
      customsValue: [],
      declaredValue: [],
      deletionIndicator: [],
      description: [],
      eventCode: [],
      eventText: [],
      flightNo: [],
      eventTimestamp: [],
      hubCode: [],
      expectedDuty: [],
      finalDestination: [],
      freightCharges: [],
      freightCurrency: [],
      goodsDescription: [],
      goodsType: [],
      grossWeight: [],
      houseAirwayBill: [],
      hsCode: [],
      iataKd: [],
      incoTerms: [],
      invoiceDate: [],
      invoiceNumber: [],
      invoiceSupplierName: [],
      invoiceType: [],
      isConsolidatedShipment: [],
      isPendingShipment: [],
      isSplitBillOfLading: [],
      landedQuantity: [],
      languageDescription: [],
      languageId: [],
      manifestedGrossWeight: [],
      manifestedQuantity: [],
      masterAirwayBill: [],
      netWeight: [],
      noOfPackageMawb: [],
      noOfPieceHawb: [],
      notifyParty: [],
      partnerHouseAirwayBill: [],
      partnerId: [],
      partnerMasterAirwayBill: [],
      partnerName: [],
      partnerType: [],
      paymentType: [],
      productId: [],
      productName: [],
      quantity: [],
      referenceField1: [],
      referenceField10: [],
      referenceField11: [],
      referenceField12: [],
      referenceField13: [],
      referenceField14: [],
      referenceField15: [],
      referenceField16: [],
      referenceField17: [],
      eventDescription: [],
      referenceField18: [],
      referenceField19: [],
      referenceField2: [],
      referenceField20: [],
      referenceField3: [],
      referenceField4: [],
      referenceField5: [],
      referenceField6: [],
      referenceField7: [],
      referenceField8: [],
      referenceField9: [],
      selectedConsole: [],
      remarks: [],
      serviceTypeId: [],
      serviceTypeName: [],
      shipmentBagId: [],
      shipperId: [],
      shipperName: [],
      specialApprovalValue: [],
      statusId: [],
      statusTimestamp: [],
      subProductId: [],
      subProductName: [],
      tareWeight: [],
      totalQuantity: [],
      updatedBy: [],
      updatedOn: [],
      volume: []
    });
    this.languageIdList = [];
    this.companyIdList = [];
    this.countryIdList = [];
    this.mawbList = [];
    this.hawbList = [];
    this.hsCodeList = [];
    this.originCodeList = [];
    this.statusList = [];
    this.consignorIdList = [];
    this.eventList = [];
    this.hubList = [];
    this.Consigment = [];
    this.showHub = false;
    this.incoTerms = [
      { value: "DDU", label: "DDU" },
      { value: "DDP", label: "DDP" }
    ];
    this.statusList = [
      { value: "11", label: "Reached Transit DC" },
      { value: "12", label: "Reached Final DC" },
      { value: "13", label: "Sorted" },
      { value: "39", label: "Bagged" }
    ];
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.opStatus.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.hsCode.url,
      this.cas.dropdownlist.setup.event.url,
      this.cas.dropdownlist.setup.consignor.url,
      this.cas.dropdownlist.setup.customer.url,
      this.cas.dropdownlist.setup.hub.url
    ]).subscribe({
      next: (results) => {
        this.countryIdList = this.cas.forLanguageFilter(results[1], this.cas.dropdownlist.setup.country.key);
        this.hsCodeList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.hsCode.key);
        this.eventList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.event.key);
        const consitnor = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.consignor.key);
        const customer = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.customer.key);
        this.hubList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.hub.key);
        customer.forEach((x) => this.consignorIdList.push(x));
        consitnor.forEach((x) => this.consignorIdList.push(x));
        this.consignorIdList = this.cs.removeDuplicatesFromArrayList(this.consignorIdList, "value");
        this.eventList = this.cs.removeDuplicatesFromArrayList(this.eventList, "value");
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  ngOnInit() {
    this.dropdownlist();
    console.log(this.data);
    console.log(this.data.title);
    this.Consigment = this.data.code;
  }
  eventChange() {
    if (this.form.controls.eventCode.value == "15" && this.data.title == "Consignment") {
      this.showHub = true;
      console.log();
    }
  }
  save() {
    if (this.form.controls.partnerMasterAirwayBill != null) {
      this.Consigment.forEach((x) => {
        x.partnerMasterAirwayBill = this.form.controls.partnerMasterAirwayBill.value;
      });
    }
    if (this.form.controls.countryOfOrigin != null) {
      this.Consigment.forEach((x) => {
        x.countryOfOrigin = this.form.controls.countryOfOrigin.value;
      });
    }
    if (this.form.controls.statusId != null) {
      this.Consigment.forEach((x) => {
        x.statusId = this.form.controls.statusId.value;
      });
    }
    if (this.form.controls.eventCode != null) {
      this.Consigment.forEach((x) => {
        x.eventCode = this.form.controls.eventCode.value;
      });
    }
    if (this.form.controls.flightNo != null) {
      this.Consigment.forEach((x) => {
        x.flightNo = this.form.controls.flightNo.value;
      });
    }
    if (this.form.controls.shipperId != null) {
      this.Consigment.forEach((x) => {
        x.shipperId = this.form.controls.shipperId.value;
      });
    }
    if (this.form.controls.hsCode != null) {
      this.Consigment.forEach((x) => {
        x.hsCode = this.form.controls.hsCode.value;
      });
    }
    if (this.form.controls.incoTerms != null) {
      this.Consigment.forEach((x) => {
        x.incoTerms = this.form.controls.incoTerms.value;
      });
    }
    if (this.form.controls.hubCode != null) {
      this.Consigment.forEach((x) => {
        x.hubCode = this.form.controls.hubCode.value;
      });
    }
    if (this.data.title != "Bonded Manifest") {
      this.service.Update(this.Consigment).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Updated",
            key: "br",
            detail: "Selected Values has been updated successfully"
          });
          if (this.data.title == "Consignment") {
            this.dialogRef.close();
            this.router.navigate(["/main/operation/consignment"]);
          } else {
            this.dialogRef.close();
            this.router.navigate(["/main/airport/preAlertManifest"]);
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      this.service.UpdateBondedManifest(this.Consigment).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Updated",
            key: "br",
            detail: "Selected Values has been updated successfully"
          });
          this.dialogRef.close();
          this.router.navigate(["/main/airport/bondedManifest"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
  cancel() {
    if (this.data.title.value == "Consignment") {
      this.dialogRef.close();
      this.router.navigate(["/main/operation/consignment"]);
    }
    if (this.data.title == "PreAlertManifest") {
      this.dialogRef.close();
      this.router.navigate(["/main/airport/preAlertManifest"]);
    }
    if (this.data.line == "Bonded Manifest") {
      this.dialogRef.close();
      this.router.navigate(["/main/airport/bondedManifest"]);
    }
  }
};
_ConsignmentUpdatebulkComponent.\u0275fac = function ConsignmentUpdatebulkComponent_Factory(t) {
  return new (t || _ConsignmentUpdatebulkComponent)(\u0275\u0275directiveInject(MatDialogRef), \u0275\u0275directiveInject(MAT_DIALOG_DATA), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService));
};
_ConsignmentUpdatebulkComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ConsignmentUpdatebulkComponent, selectors: [["app-consignment-updatebulk"]], decls: 47, vars: 40, consts: [[1, "bgWhite", "text-black", "w-93", "h-100", "m-4", "mt-4"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], [1, "mt-4", "pt-2"], [3, "formGroup"], [1, "d-flex-row"], [1, "row", "scrollNew"], [1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["maxlength", "50", "pInputText", "", "appTrim", "", "formControlName", "partnerMasterAirwayBill", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["formControlName", "countryOfOrigin", "appendTo", "body", "placeholder", "Select", 3, "showClear", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["class", "col-6 marginFieldNew borderRadius12", 4, "ngIf"], ["maxlength", "50", "pInputText", "", "appTrim", "", "formControlName", "flightNo", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["formControlName", "shipperId", "appendTo", "body", "placeholder", "Select", 3, "showClear", "options", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "incoTerms", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "hsCode", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "float-right", "py-2"], ["mat-dialog-close", "", 1, "buttom1", "textBlack", "mx-1", 3, "click"], ["type", "button", 1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], ["placeholder", "Select", "filter", "true", "formControlName", "hubCode", "appendTo", "body", 3, "showClear", "options", "panelStyle"]], template: function ConsignmentUpdatebulkComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1)(2, "p", 2);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div", 3)(5, "form", 4)(6, "div", 5)(7, "div", 6)(8, "div", 7)(9, "p", 8);
    \u0275\u0275text(10, "Partner MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(11, "input", 9);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "div", 7)(13, "p", 8);
    \u0275\u0275text(14, "Origin Code");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "p-dropdown", 10);
    \u0275\u0275template(16, ConsignmentUpdatebulkComponent_ng_template_16_Template, 1, 0, "ng-template", 11);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "div", 7)(18, "p", 8);
    \u0275\u0275text(19, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "p-dropdown", 12);
    \u0275\u0275template(21, ConsignmentUpdatebulkComponent_ng_template_21_Template, 1, 0, "ng-template", 11);
    \u0275\u0275elementEnd()();
    \u0275\u0275template(22, ConsignmentUpdatebulkComponent_div_22_Template, 5, 7, "div", 13);
    \u0275\u0275elementStart(23, "div", 7)(24, "p", 8);
    \u0275\u0275text(25, "Flight No ");
    \u0275\u0275elementEnd();
    \u0275\u0275element(26, "input", 14);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(27, "div", 7)(28, "p", 8);
    \u0275\u0275text(29, "Customer/Consignor");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(30, "p-dropdown", 15);
    \u0275\u0275template(31, ConsignmentUpdatebulkComponent_ng_template_31_Template, 1, 0, "ng-template", 11);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(32, "div", 7)(33, "p", 8);
    \u0275\u0275text(34, "Inco Terms");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(35, "p-dropdown", 16);
    \u0275\u0275template(36, ConsignmentUpdatebulkComponent_ng_template_36_Template, 1, 0, "ng-template", 11);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(37, "div", 7)(38, "p", 8);
    \u0275\u0275text(39, "HS Code");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(40, "p-dropdown", 17);
    \u0275\u0275template(41, ConsignmentUpdatebulkComponent_ng_template_41_Template, 1, 0, "ng-template", 11);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(42, "div", 18)(43, "button", 19);
    \u0275\u0275listener("click", function ConsignmentUpdatebulkComponent_Template_button_click_43_listener() {
      return ctx.cancel();
    });
    \u0275\u0275text(44, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(45, "button", 20);
    \u0275\u0275listener("click", function ConsignmentUpdatebulkComponent_Template_button_click_45_listener() {
      return ctx.save();
    });
    \u0275\u0275text(46, "Save");
    \u0275\u0275elementEnd()()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1("", ctx.data.title, " - Bulk Update");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance(10);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(30, _c0));
    \u0275\u0275property("showClear", true)("options", ctx.countryIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(31, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(32, _c0));
    \u0275\u0275property("showClear", true)("options", ctx.statusList)("panelStyle", \u0275\u0275pureFunction0(33, _c0));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.showHub == true);
    \u0275\u0275advance(8);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(34, _c0));
    \u0275\u0275property("showClear", true)("options", ctx.consignorIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(35, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(36, _c0));
    \u0275\u0275property("showClear", true)("options", ctx.incoTerms)("panelStyle", \u0275\u0275pureFunction0(37, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(38, _c0));
    \u0275\u0275property("showClear", true)("options", ctx.hsCodeList)("panelStyle", \u0275\u0275pureFunction0(39, _c0));
  }
}, dependencies: [NgIf, PrimeTemplate, Dropdown, InputText, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, MatDialogClose, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.deleteBody[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: 400;\n  line-height: 20.8px;\n  text-align: center;\n  color: rgba(255, 255, 255, 0.8);\n  padding-bottom: 1rem;\n}\n.deleteHeader[_ngcontent-%COMP%] {\n  font-family: Nunito;\n  font-size: 22px;\n  font-weight: 700;\n  line-height: 31.2px;\n  text-align: left;\n  padding-bottom: 1rem;\n}\n.cancelButton[_ngcontent-%COMP%] {\n  padding: 7px 25px 7px 25px;\n  gap: 10px;\n  border-radius: 6px;\n  border: 1px 0px 0px 0px;\n  opacity: 0px;\n  background-color: var(--white);\n  color: var(--black);\n}\n.saveButton[_ngcontent-%COMP%] {\n  padding: 7px 35px 7px 35px;\n  gap: 10px;\n  border-radius: 6px;\n  opacity: 0px;\n  background-color: var(--overcOrange);\n  color: var(--black);\n}\n  .mat-mdc-dialog-container .mdc-dialog__surface {\n  border-radius: 12px !important;\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.deleteDialog[_ngcontent-%COMP%] {\n  padding: 2rem 1rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 20rem);\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=consignment-updatebulk.component.css.map */"] });
var ConsignmentUpdatebulkComponent = _ConsignmentUpdatebulkComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ConsignmentUpdatebulkComponent, { className: "ConsignmentUpdatebulkComponent", filePath: "src\\app\\main\\operation\\consignment\\consignment-updatebulk\\consignment-updatebulk.component.ts", lineNumber: 18 });
})();

export {
  ConsignmentUpdatebulkComponent
};
//# sourceMappingURL=chunk-IHSWXEOC.js.map
