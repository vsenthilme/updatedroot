import {
  CountryService,
  DistrictService,
  ProvinceService
} from "./chunk-NDC7B7MG.js";
import {
  ConsignorService,
  CustomerService
} from "./chunk-P24FUOPU.js";
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
  Calendar,
  Checkbox,
  Chips,
  CommonModule,
  CommonServiceService,
  DatePipe,
  DefaultValueAccessor,
  Dropdown,
  FormBuilder,
  FormControl,
  FormControlName,
  FormGroupDirective,
  FormGroupName,
  HttpClient,
  IconField,
  InputIcon,
  InputText,
  MatDialog,
  MatError,
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
  RouterModule,
  SharedModule,
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
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
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

// src/app/main/last-mile/pickup/pickup.service.ts
var _PickupService = class _PickupService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(pickupId) {
    return this.http.get("/overc-lastmile-service/pickup/get" + pickupId);
  }
  Create(obj) {
    return this.http.post("/overc-lastmile-service/pickup", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-lastmile-service/pickup/update/list", obj);
  }
  Delete(obj) {
    return this.http.delete("/overc-lastmile-service/pickup/delete/list");
  }
  search(obj) {
    return this.http.post("/overc-lastmile-service/pickup/find", obj);
  }
};
_PickupService.\u0275fac = function PickupService_Factory(t) {
  return new (t || _PickupService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_PickupService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _PickupService, factory: _PickupService.\u0275fac, providedIn: "root" });
var PickupService = _PickupService;

// src/app/main/last-mile/pickup/pickup-new/pickup-new.component.ts
var _c0 = () => ({ "width": "100%" });
function PickupNewComponent_ng_template_11_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 16);
    \u0275\u0275text(1, "1");
    \u0275\u0275elementEnd();
  }
}
function PickupNewComponent_ng_template_11_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 17);
  }
}
function PickupNewComponent_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 11);
    \u0275\u0275listener("click", function PickupNewComponent_ng_template_11_Template_div_click_0_listener() {
      const onClick_r2 = \u0275\u0275restoreView(_r1).onClick;
      return \u0275\u0275resetView(onClick_r2.emit());
    });
    \u0275\u0275elementStart(1, "div", 12);
    \u0275\u0275template(2, PickupNewComponent_ng_template_11_p_2_Template, 2, 0, "p", 13)(3, PickupNewComponent_ng_template_11_img_3_Template, 1, 0, "img", 14);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 15);
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
function PickupNewComponent_ng_template_12_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_12_mat_error_6_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 43)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function PickupNewComponent_ng_template_12_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_12_ng_template_16_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_12_ng_template_21_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_12_ng_template_30_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_12_mat_error_35_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 43)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function PickupNewComponent_ng_template_12_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_12_ng_template_62_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_12_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 18)(1, "div", 19)(2, "p", 20);
    \u0275\u0275text(3, "Company");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "p-dropdown", 21);
    \u0275\u0275template(5, PickupNewComponent_ng_template_12_ng_template_5_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275template(6, PickupNewComponent_ng_template_12_mat_error_6_Template, 3, 1, "mat-error", 23);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "div", 24)(8, "p", 25);
    \u0275\u0275text(9, "Partner Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "p-dropdown", 26);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_12_Template_p_dropdown_onChange_10_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.partnerTypeChanged());
    });
    \u0275\u0275template(11, PickupNewComponent_ng_template_12_ng_template_11_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(12, "div", 27)(13, "p", 25);
    \u0275\u0275text(14, "Partner");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "p-dropdown", 28);
    \u0275\u0275template(16, PickupNewComponent_ng_template_12_ng_template_16_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "div", 19)(18, "p", 20);
    \u0275\u0275text(19, "Service Type ID");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "p-dropdown", 29);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_12_Template_p_dropdown_onChange_20_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.serviceTypeChanged());
    });
    \u0275\u0275template(21, PickupNewComponent_ng_template_12_ng_template_21_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(22, "div", 19)(23, "p", 20);
    \u0275\u0275text(24, "Piece Count");
    \u0275\u0275elementEnd();
    \u0275\u0275element(25, "input", 30);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "div", 19)(27, "p", 20);
    \u0275\u0275text(28, "Pickup Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(29, "p-dropdown", 31);
    \u0275\u0275template(30, PickupNewComponent_ng_template_12_ng_template_30_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(31, "div", 19)(32, "p", 25);
    \u0275\u0275text(33, "Pickup ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(34, "input", 32);
    \u0275\u0275template(35, PickupNewComponent_ng_template_12_mat_error_35_Template, 3, 1, "mat-error", 23);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(36, "div", 19)(37, "p", 20);
    \u0275\u0275text(38, "Payment Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(39, "p-dropdown", 33);
    \u0275\u0275template(40, PickupNewComponent_ng_template_12_ng_template_40_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(41, "div", 19)(42, "p", 20);
    \u0275\u0275text(43, "Total Shipment Weight");
    \u0275\u0275elementEnd();
    \u0275\u0275element(44, "input", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(45, "div", 19)(46, "p", 20);
    \u0275\u0275text(47, "COD Amount");
    \u0275\u0275elementEnd();
    \u0275\u0275element(48, "input", 35);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(49, "div", 19)(50, "p", 20);
    \u0275\u0275text(51, "Pickup Time Slot");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(52, "p-calendar", 36, 0);
    \u0275\u0275listener("onSelect", function PickupNewComponent_ng_template_12_Template_p_calendar_onSelect_52_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.filter("date"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(54, "div", 19)(55, "p", 20);
    \u0275\u0275text(56, "Remark");
    \u0275\u0275elementEnd();
    \u0275\u0275element(57, "input", 37);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(58, "div", 19)(59, "p", 20);
    \u0275\u0275text(60, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(61, "p-dropdown", 38);
    \u0275\u0275template(62, PickupNewComponent_ng_template_12_ng_template_62_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(63, "div", 39)(64, "button", 40);
    \u0275\u0275text(65, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(66, "button", 41);
    \u0275\u0275listener("click", function PickupNewComponent_ng_template_12_Template_button_click_66_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(67);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    let tmp_28_0;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(48, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.companyIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(49, _c0));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("companyId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(50, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.partnerType)("panelStyle", \u0275\u0275pureFunction0(51, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(52, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.partnerNameList)("panelStyle", \u0275\u0275pureFunction0(53, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(54, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.serviceTypeIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(55, _c0));
    \u0275\u0275advance(9);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(56, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.pickupTypeList)("options", ctx_r2.pickupType)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(57, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275property("ngClass", ((tmp_28_0 = ctx_r2.form.get("pickUpId")) == null ? null : tmp_28_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("pickUpId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(58, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.paymentType)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(59, _c0));
    \u0275\u0275advance(13);
    \u0275\u0275property("yearNavigator", true)("showButtonBar", true)("monthNavigator", true)("hideOnDateTimeSelect", true)("readonlyInput", true);
    \u0275\u0275advance(9);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(60, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.status);
    \u0275\u0275advance(6);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function PickupNewComponent_ng_template_14_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 16);
    \u0275\u0275text(1, "2");
    \u0275\u0275elementEnd();
  }
}
function PickupNewComponent_ng_template_14_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 17);
  }
}
function PickupNewComponent_ng_template_14_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 11);
    \u0275\u0275listener("click", function PickupNewComponent_ng_template_14_Template_div_click_0_listener() {
      const onClick_r6 = \u0275\u0275restoreView(_r5).onClick;
      return \u0275\u0275resetView(onClick_r6.emit());
    });
    \u0275\u0275elementStart(1, "div", 12);
    \u0275\u0275template(2, PickupNewComponent_ng_template_14_p_2_Template, 2, 0, "p", 13)(3, PickupNewComponent_ng_template_14_img_3_Template, 1, 0, "img", 14);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 15);
    \u0275\u0275text(6, "Pickup Details");
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
function PickupNewComponent_ng_template_15_ng_template_17_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_15_ng_template_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_15_ng_template_27_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_15_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_15_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 18)(1, "div", 44)(2, "p", 20);
    \u0275\u0275text(3, " Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(4, "input", 45);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "div", 46)(6, "p", 20);
    \u0275\u0275text(7, "Address Line 1");
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "input", 47);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "div", 48)(10, "p", 20);
    \u0275\u0275text(11, "Address Line 2");
    \u0275\u0275elementEnd();
    \u0275\u0275element(12, "input", 49);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 50)(14, "p", 20);
    \u0275\u0275text(15, "City");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-dropdown", 51);
    \u0275\u0275template(17, PickupNewComponent_ng_template_15_ng_template_17_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(18, "div", 52)(19, "p", 20);
    \u0275\u0275text(20, "District");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(21, "p-dropdown", 53);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_15_Template_p_dropdown_onChange_21_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.districtChanged());
    });
    \u0275\u0275template(22, PickupNewComponent_ng_template_15_ng_template_22_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(23, "div", 54)(24, "p", 20);
    \u0275\u0275text(25, "State");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "p-dropdown", 55);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_15_Template_p_dropdown_onChange_26_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.provinceChanged());
    });
    \u0275\u0275template(27, PickupNewComponent_ng_template_15_ng_template_27_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 52)(29, "p", 20);
    \u0275\u0275text(30, "Country");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(31, "p-dropdown", 56);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_15_Template_p_dropdown_onChange_31_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.countryChanged());
    });
    \u0275\u0275template(32, PickupNewComponent_ng_template_15_ng_template_32_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(33, "div", 57)(34, "p", 20);
    \u0275\u0275text(35, "Pincode");
    \u0275\u0275elementEnd();
    \u0275\u0275element(36, "input", 58);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(37, "div", 59)(38, "p", 20);
    \u0275\u0275text(39, "Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(40, "input", 60);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "div", 59)(42, "p", 20);
    \u0275\u0275text(43, "Alternate Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(44, "input", 61);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(45, "div", 39)(46, "button", 40);
    \u0275\u0275text(47, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(48, "button", 41);
    \u0275\u0275listener("click", function PickupNewComponent_ng_template_15_Template_button_click_48_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(49);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(16);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(25, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.cityIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(26, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.districtIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(28, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(29, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.provinceIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(30, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(31, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.countryIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(32, _c0));
    \u0275\u0275advance(18);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function PickupNewComponent_ng_template_17_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 16);
    \u0275\u0275text(1, "3");
    \u0275\u0275elementEnd();
  }
}
function PickupNewComponent_ng_template_17_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 17);
  }
}
function PickupNewComponent_ng_template_17_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 11);
    \u0275\u0275listener("click", function PickupNewComponent_ng_template_17_Template_div_click_0_listener() {
      const onClick_r9 = \u0275\u0275restoreView(_r8).onClick;
      return \u0275\u0275resetView(onClick_r9.emit());
    });
    \u0275\u0275elementStart(1, "div", 12);
    \u0275\u0275template(2, PickupNewComponent_ng_template_17_p_2_Template, 2, 0, "p", 13)(3, PickupNewComponent_ng_template_17_img_3_Template, 1, 0, "img", 14);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 15);
    \u0275\u0275text(6, "Destination Details");
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
function PickupNewComponent_ng_template_18_ng_template_17_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_18_ng_template_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_18_ng_template_27_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_18_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
}
function PickupNewComponent_ng_template_18_Template(rf, ctx) {
  if (rf & 1) {
    const _r10 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 18)(1, "div", 62)(2, "p", 20);
    \u0275\u0275text(3, " Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(4, "input", 45);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "div", 63)(6, "p", 20);
    \u0275\u0275text(7, "Address Line 1");
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "input", 47);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "div", 64)(10, "p", 20);
    \u0275\u0275text(11, "Address Line 2");
    \u0275\u0275elementEnd();
    \u0275\u0275element(12, "input", 49);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 65)(14, "p", 20);
    \u0275\u0275text(15, "City");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-dropdown", 51);
    \u0275\u0275template(17, PickupNewComponent_ng_template_18_ng_template_17_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(18, "div", 66)(19, "p", 20);
    \u0275\u0275text(20, "District");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(21, "p-dropdown", 53);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_18_Template_p_dropdown_onChange_21_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.districtChanged());
    });
    \u0275\u0275template(22, PickupNewComponent_ng_template_18_ng_template_22_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(23, "div", 67)(24, "p", 20);
    \u0275\u0275text(25, "State");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "p-dropdown", 55);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_18_Template_p_dropdown_onChange_26_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.provinceChanged());
    });
    \u0275\u0275template(27, PickupNewComponent_ng_template_18_ng_template_27_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 66)(29, "p", 20);
    \u0275\u0275text(30, "Country");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(31, "p-dropdown", 68);
    \u0275\u0275listener("onChange", function PickupNewComponent_ng_template_18_Template_p_dropdown_onChange_31_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.countryChanged());
    });
    \u0275\u0275template(32, PickupNewComponent_ng_template_18_ng_template_32_Template, 1, 0, "ng-template", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(33, "div", 69)(34, "p", 20);
    \u0275\u0275text(35, "Pincode");
    \u0275\u0275elementEnd();
    \u0275\u0275element(36, "input", 58);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(37, "div", 70)(38, "p", 20);
    \u0275\u0275text(39, "Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(40, "input", 60);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "div", 70)(42, "p", 20);
    \u0275\u0275text(43, "Alternate Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(44, "input", 61);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(45, "div", 39)(46, "button", 40);
    \u0275\u0275text(47, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(48, "button", 41);
    \u0275\u0275listener("click", function PickupNewComponent_ng_template_18_Template_button_click_48_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(49);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(16);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(25, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.cityIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(26, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.districtIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(28, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(29, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.provinceIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(30, _c0));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(31, _c0));
    \u0275\u0275property("showClear", true)("options", ctx_r2.countryIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(32, _c0));
    \u0275\u0275advance(18);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function PickupNewComponent_p_stepperPanel_19_ng_template_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 11);
    \u0275\u0275listener("click", function PickupNewComponent_p_stepperPanel_19_ng_template_1_Template_div_click_0_listener() {
      const onClick_r12 = \u0275\u0275restoreView(_r11).onClick;
      return \u0275\u0275resetView(onClick_r12.emit());
    });
    \u0275\u0275elementStart(1, "div", 12)(2, "p", 71);
    \u0275\u0275text(3, " 2");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(4, "div")(5, "p", 72);
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
function PickupNewComponent_p_stepperPanel_19_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r13 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 73)(1, "div", 19)(2, "p", 20);
    \u0275\u0275text(3, "Created By");
    \u0275\u0275elementEnd();
    \u0275\u0275element(4, "input", 74);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "div", 19)(6, "p", 20);
    \u0275\u0275text(7, "Created On");
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "input", 75);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "div", 19)(10, "p", 20);
    \u0275\u0275text(11, "Updated By");
    \u0275\u0275elementEnd();
    \u0275\u0275element(12, "input", 76);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 19)(14, "p", 20);
    \u0275\u0275text(15, "Updated On");
    \u0275\u0275elementEnd();
    \u0275\u0275element(16, "input", 77);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "div", 39)(18, "button", 78);
    \u0275\u0275listener("click", function PickupNewComponent_p_stepperPanel_19_ng_template_2_Template_button_click_18_listener() {
      const prevCallback_r14 = \u0275\u0275restoreView(_r13).prevCallback;
      return \u0275\u0275resetView(prevCallback_r14.emit());
    });
    \u0275\u0275text(19, "Back");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "button", 41);
    \u0275\u0275listener("click", function PickupNewComponent_p_stepperPanel_19_ng_template_2_Template_button_click_20_listener() {
      \u0275\u0275restoreView(_r13);
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
function PickupNewComponent_p_stepperPanel_19_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p-stepperPanel");
    \u0275\u0275template(1, PickupNewComponent_p_stepperPanel_19_ng_template_1_Template, 7, 2, "ng-template", 8)(2, PickupNewComponent_p_stepperPanel_19_ng_template_2_Template, 22, 1, "ng-template", 9);
    \u0275\u0275elementEnd();
  }
}
var _PickupNewComponent = class _PickupNewComponent {
  constructor(cs, spin, route, router, path, fb, service, customerService, consignorService, messageService, numberRangeService, provinceService, countryService, districtService, auth, cas) {
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.path = path;
    this.fb = fb;
    this.service = service;
    this.customerService = customerService;
    this.consignorService = consignorService;
    this.messageService = messageService;
    this.numberRangeService = numberRangeService;
    this.provinceService = provinceService;
    this.countryService = countryService;
    this.districtService = districtService;
    this.auth = auth;
    this.cas = cas;
    this.active = 0;
    this.status = [];
    this.paymentType = [];
    this.partnerType = [];
    this.pickupType = [];
    this.PickupDetails = this.fb.group({
      pickupHubCode: [],
      pickupDetailId: [],
      companyName: [],
      emailId: [],
      name: [],
      phone: [],
      alternatePhone: [],
      addressLine1: [],
      addressLine2: [],
      pinCode: [],
      district: [],
      city: [],
      country: [],
      state: [],
      latitude: [],
      longitude: [],
      pickupAddress: []
    });
    this.DestinationDetails = this.fb.group({
      imageReferenceList: [],
      destinationDetailId: [],
      reverseReason: [],
      destinationAddress: [],
      emailId: [],
      name: [],
      companyName: [],
      phone: [],
      alternatePhone: [],
      addressLine1: [],
      addressLine2: [],
      pinCode: [],
      district: [],
      city: [],
      country: [],
      state: [],
      latitude: [],
      longitude: [],
      pickupAddress: []
    });
    this.form = this.fb.group({
      partnerId: [],
      partnerName: [],
      consignmentBagId: [],
      pickupType: ["Forward"],
      paymentType: [],
      pieceId: [],
      pieceCount: [],
      actualSequenceNo: [],
      assignedHubCode: [],
      codFavorOf: [],
      consignmentType: [],
      courierId: [],
      courierType: [],
      customerCode: [],
      customerReferenceNumber: [],
      invoiceAmount: [],
      isCustomsDeclarable: [],
      loadType: [],
      movementType: [],
      packageType: [],
      partnerType: [],
      paymentLink: [],
      pickupAttemptCount: [],
      invoiceUrl: [],
      totalShipmentWeight: [],
      serviceTypeId: [],
      houseAirwayBill: [],
      codAmount: [],
      codCollectionMode: [],
      priority: [],
      customerPickupDate: [],
      pickupTimeSlotStart: [],
      pickupTimeSlotEnd: [],
      pickUpId: [, Validators.required],
      languageId: [this.auth.languageId, Validators.required],
      languageDescription: [],
      companyId: [this.auth.companyId, Validators.required],
      companyName: [],
      pickupDetails: this.PickupDetails,
      destinationDetails: this.DestinationDetails,
      pickupEntityId: [],
      pickupFailedReason: [],
      pickupOtp: [],
      fullDate: [],
      fromDate: [],
      toDate: [],
      pickupServiceTime: [],
      productCode: [],
      productId: [],
      productName: [],
      reverseReason: [],
      routeId: [],
      rtoOtp: [],
      sequenceNo: [],
      serviceTypeText: [],
      statusTimeStamp: [],
      vehicleRegNumber: [],
      statusDescription: [],
      description: [],
      remarks: [],
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
      referenceField11: [],
      referenceField12: [],
      referenceField13: [],
      referenceField14: [],
      referenceField15: [],
      referenceField16: [],
      referenceField17: [],
      referenceField18: [],
      referenceField19: [],
      referenceField20: [],
      createdOn: [""],
      createdBy: [],
      updatedBy: [],
      updatedOn: [""],
      statusId: ["16"]
    });
    this.submitted = false;
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.languageIdList = [];
    this.companyIdList = [];
    this.partnerIdList = [];
    this.pieceIdList = [];
    this.serviceTypeIdList = [];
    this.houseAirwayBillList = [];
    this.codCollectionModeList = [];
    this.priorityList = [];
    this.courierTypeList = [];
    this.courierIdList = [];
    this.pickupTypeList = [];
    this.districtIdList = [];
    this.cityIdList = [];
    this.countryIdList = [];
    this.provinceIdList = [];
    this.pickupHubCodeList = [];
    this.emailIdList = [];
    this.partnerNameList = [];
    this.status = [
      { value: "17", label: "Inactive" },
      { value: "16", label: "Active" }
    ];
    this.pickupType = [
      { value: "Reverse", label: "Reverse" },
      { value: "Forward", label: "Forward" }
    ];
    this.paymentType = [
      { value: "Cash", label: "Cash" },
      { value: "Online Payment", label: "Online Payment" },
      { value: "K-NET", label: "K-NET" }
    ];
    this.partnerType = [
      { value: "customer", label: "Customer" },
      { value: "consignor", label: "Consignor" }
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
    const dataToSend = ["LastMile", "Pickup", this.pageToken.pageflow];
    this.path.setData(dataToSend);
    this.dropdownlist();
    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();
    if (this.pageToken.pageflow != "New") {
      this.fill(this.pageToken.line);
      this.form.controls.pickUpId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    } else {
      this.spin.show();
      let obj = {};
      obj.numberRangeObject = ["PICKUPID"];
      this.numberRangeService.search(obj).subscribe({
        next: (res) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.pickUpId.patchValue(this.nextNumber);
            this.numCondition = "true";
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.pickUpId.disable();
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
      this.spin.show();
      let obj1 = {};
      obj1.numberRangeObject = ["PIECEID"];
      this.numberRangeService.search(obj1).subscribe({
        next: (res) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.pieceId.patchValue(this.nextNumber);
            this.numCondition = "true";
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.pieceId.disable();
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.serviceType.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.district.url,
      this.cas.dropdownlist.setup.province.url,
      this.cas.dropdownlist.setup.city.url
    ]).subscribe({
      next: (results) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.serviceTypeIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.serviceType.key);
        this.countryIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.country.key);
        this.districtIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.district.key);
        this.provinceIdList = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.province.key);
        this.cityIdList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.city.key);
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
  filter(type) {
    if (this.form.controls.fullDate.value != null) {
      this.form.controls.fromDate.patchValue(this.cs.jsonDate(this.form.controls.fullDate.value[0]) ? this.form.controls.fullDate.value[0] : null);
      this.form.controls.toDate.patchValue(this.cs.jsonDate(this.form.controls.fullDate.value[1]) ? this.form.controls.fullDate.value[1] : null);
      if (this.form.controls.toDate.value == null) {
        return;
      }
    }
  }
  save() {
    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({ severity: "error", summary: "Error", key: "br", detail: "Please fill required fields to continue" });
      return;
    }
    if (this.pageToken.pageflow != "New") {
      this.spin.show();
      this.service.Update([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({ severity: "success", summary: "Updated", key: "br", detail: res.pickUpId + " has been updated successfully" });
          this.router.navigate(["/main/lastMile/pickup"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      this.spin.show();
      this.service.Create([this.form.getRawValue()]).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({ severity: "success", summary: "Created", key: "br", detail: res.pickUpId + " has been created successfully" });
            this.router.navigate(["/main/lastMile/pickup"]);
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
  countryChanged() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.country = [this.form.get("pickupDetails")?.get("country")?.value];
    this.provinceIdList = [];
    this.spin.show();
    this.provinceService.search(obj).subscribe({
      next: (result) => {
        this.provinceIdList = this.cas.foreachlist(result, { key: "provinceId", value: "provinceName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  provinceChanged() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.country = [this.form.get("pickupDetails")?.get("country")?.value];
    obj.country = [this.form.get("pickupDetails")?.get("state")?.value];
    this.districtIdList = [];
    this.spin.show();
    this.districtService.search(obj).subscribe({
      next: (result) => {
        this.districtIdList = this.cas.foreachlist(result, { key: "districtId", value: "districtName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  districtChanged() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.country = [this.form.get("pickupDetails")?.get("country")?.value];
    obj.country = [this.form.get("pickupDetails")?.get("state")?.value];
    obj.country = [this.form.get("pickupDetails")?.get("district")?.value];
    this.districtIdList = [];
    this.spin.show();
    this.districtService.search(obj).subscribe({
      next: (result) => {
        this.districtIdList = this.cas.foreachlist(result, { key: "cityId", value: "cityName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  partnerTypeChanged() {
    if (this.form.controls.partnerType.value == "customer") {
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
    if (this.form.controls.partnerType.value == "consignor") {
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
  serviceTypeChanged() {
    let descriptionText = this.serviceTypeIdList.filter((x) => x.value == this.form.controls.serviceTypeId.value);
    this.form.controls.serviceTypeText.patchValue(descriptionText[0].label);
  }
};
_PickupNewComponent.\u0275fac = function PickupNewComponent_Factory(t) {
  return new (t || _PickupNewComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(PickupService), \u0275\u0275directiveInject(CustomerService), \u0275\u0275directiveInject(ConsignorService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(NumberrangeService), \u0275\u0275directiveInject(ProvinceService), \u0275\u0275directiveInject(CountryService), \u0275\u0275directiveInject(DistrictService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(CommonAPIService));
};
_PickupNewComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _PickupNewComponent, selectors: [["app-pickup-new"]], decls: 20, vars: 4, consts: [["myStartCalendar", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", 1, "invisible", "buttom1", "bgBlack", "text-white", "ml-2"], [1, "w-75", "mt-4", "mx-auto"], [3, "formGroup"], [3, "activeStepChange", "activeStep"], ["pTemplate", "header"], ["pTemplate", "content"], [4, "ngIf"], [1, "d-flex", "flex-column", "align-items-center", 3, "click"], [1, "d-flex", "justify-content-center", "align-items-center"], ["class", "circle borderCircle mb-0", 4, "ngIf"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", "", 4, "ngIf"], [1, "mb-0", "mt-2", "f600", "textBlack"], [1, "circle", "borderCircle", "mb-0"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", ""], [1, "row", "scrollNew"], [1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["formControlName", "companyId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["class", "text-danger", 4, "ngIf"], ["id", "partnerType", 1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["formControlName", "partnerType", "appendTo", "body", "placeholder", "Select", 3, "onChange", "showClear", "options", "panelStyle"], ["id", "partnerId", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "serviceTypeId", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["maxlength", "50", "formControlName", "pieceCount", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["placeholder", "Select", "filter", "true", "formControlName", "pickupType", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["pInputText", "", "appTrim", "", "formControlName", "pickUpId", "maxlength", "50", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["placeholder", "Select", "filter", "true", "formControlName", "paymentType", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["maxlength", "50", "formControlName", "totalShipmentWeight", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["maxlength", "50", "formControlName", "codAmount", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["yearRange", "2000:2099", "dateFormat", "dd/mm/yy", "appendTo", "body", "formControlName", "fullDate", "placeholder", "Choose Date", "selectionMode", "range", "inputId", "range", 3, "onSelect", "yearNavigator", "showButtonBar", "monthNavigator", "hideOnDateTimeSelect", "readonlyInput"], ["maxlength", "2000", "formControlName", "remarks", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["appendTo", "body", "formControlName", "statusId", "placeholder", "Select", "filter", "true", 3, "showClear", "options"], [1, "d-flex", "mt-1", "justify-content-end", 2, "position", "absolute", "right", "3.5%", "bottom", "7%"], ["routerLink", "/main/lastMile/pickup", 1, "buttom1", "textBlack", "mx-1"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], [1, "text-danger"], ["id", "name", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "name", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "addressLine1", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "addressLine1", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "addressLine2", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "addressLine2", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "city", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "city", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["id", "district", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "district", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["id", "state", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "state", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "Country", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["id", "pincode", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "pincode", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "phone", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "phone", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["maxlength", "50", "formControlName", "alternatePhone", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "name", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "addressLine1", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "addressLine2", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "city", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "district", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "state", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "country", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["id", "pincode", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "phone", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], [1, "circle", "mb-0", 3, "ngClass"], [1, "mb-0", "mt-2", "f600", 3, "ngClass"], [1, "row"], ["type", "text", "formControlName", "createdBy", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "createdOn", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "updatedBy", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["type", "text", "formControlName", "updatedOn", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], [1, "buttom1", "textBlack", "mx-1", 3, "click"]], template: function PickupNewComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 1)(1, "div", 2)(2, "p", 3);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 2)(5, "button", 4);
    \u0275\u0275text(6, "Transfer");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(7, "div", 5)(8, "form", 6)(9, "p-stepper", 7);
    \u0275\u0275twoWayListener("activeStepChange", function PickupNewComponent_Template_p_stepper_activeStepChange_9_listener($event) {
      \u0275\u0275twoWayBindingSet(ctx.active, $event) || (ctx.active = $event);
      return $event;
    });
    \u0275\u0275elementStart(10, "p-stepperPanel");
    \u0275\u0275template(11, PickupNewComponent_ng_template_11_Template, 7, 2, "ng-template", 8)(12, PickupNewComponent_ng_template_12_Template, 68, 61, "ng-template", 9);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "p-stepperPanel");
    \u0275\u0275template(14, PickupNewComponent_ng_template_14_Template, 7, 2, "ng-template", 8)(15, PickupNewComponent_ng_template_15_Template, 50, 33, "ng-template", 9);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-stepperPanel");
    \u0275\u0275template(17, PickupNewComponent_ng_template_17_Template, 7, 2, "ng-template", 8)(18, PickupNewComponent_ng_template_18_Template, 50, 33, "ng-template", 9);
    \u0275\u0275elementEnd();
    \u0275\u0275template(19, PickupNewComponent_p_stepperPanel_19_Template, 3, 0, "p-stepperPanel", 10);
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1("Pickup - ", ctx.pageToken.pageflow, "");
    \u0275\u0275advance(5);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance();
    \u0275\u0275twoWayProperty("activeStep", ctx.active);
    \u0275\u0275advance(10);
    \u0275\u0275property("ngIf", ctx.pageToken.pageflow != "New");
  }
}, dependencies: [NgClass, NgIf, RouterLink, PrimeTemplate, Dropdown, InputText, Calendar, Stepper, StepperPanel, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, MatError, FormGroupDirective, FormControlName, FormGroupName, TrimDirective], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 2.5rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 24rem);\n  overflow-y: scroll;\n}\n/*# sourceMappingURL=pickup-new.component.css.map */"] });
var PickupNewComponent = _PickupNewComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(PickupNewComponent, { className: "PickupNewComponent", filePath: "src\\app\\main\\last-mile\\pickup\\pickup-new\\pickup-new.component.ts", lineNumber: 23 });
})();

// src/app/main/last-mile/pickup/pickup.component.ts
var _c02 = ["pickup"];
var _c1 = () => ({ width: "80vw" });
var _c2 = () => ({ "width": "100%" });
var _c3 = () => ({ "width": "100rem" });
function PickupComponent_ng_template_40_th_3_Template(rf, ctx) {
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
function PickupComponent_ng_template_40_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 39);
    \u0275\u0275listener("input", function PickupComponent_ng_template_40_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const pickupTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(pickupTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const pickupTag_r3 = \u0275\u0275reference(39);
    \u0275\u0275advance();
    \u0275\u0275property("value", pickupTag_r3.filters[col_r6.field] == null ? null : pickupTag_r3.filters[col_r6.field].value);
  }
}
function PickupComponent_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 33);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, PickupComponent_ng_template_40_th_3_Template, 3, 3, "th", 35);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 33);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, PickupComponent_ng_template_40_th_7_Template, 2, 1, "th", 36);
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
function PickupComponent_ng_template_41_td_3_ng_container_1_Template(rf, ctx) {
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
function PickupComponent_ng_template_41_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r12 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 46);
    \u0275\u0275listener("click", function PickupComponent_ng_template_41_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
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
function PickupComponent_ng_template_41_td_3_ng_template_2_span_1_Template(rf, ctx) {
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
function PickupComponent_ng_template_41_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, PickupComponent_ng_template_41_td_3_ng_template_2_span_0_Template, 2, 1, "span", 44)(1, PickupComponent_ng_template_41_td_3_ng_template_2_span_1_Template, 3, 4, "span", 45);
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r10.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format == "date");
  }
}
function PickupComponent_ng_template_41_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, PickupComponent_ng_template_41_td_3_ng_container_1_Template, 2, 1, "ng-container", 43)(2, PickupComponent_ng_template_41_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 2, \u0275\u0275templateRefExtractor);
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
function PickupComponent_ng_template_41_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr")(1, "td", 40)(2, "p-checkbox", 41);
    \u0275\u0275listener("onChange", function PickupComponent_ng_template_41_Template_p_checkbox_onChange_2_listener() {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.onChange());
    });
    \u0275\u0275twoWayListener("ngModelChange", function PickupComponent_ng_template_41_Template_p_checkbox_ngModelChange_2_listener($event) {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r8.selectedPickup, $event) || (ctx_r8.selectedPickup = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275template(3, PickupComponent_ng_template_41_td_3_Template, 4, 4, "td", 42);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r11 = ctx.$implicit;
    const columns_r14 = ctx.columns;
    const ctx_r8 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r8.selectedPickup[0] === rowData_r11);
    \u0275\u0275advance(2);
    \u0275\u0275twoWayProperty("ngModel", ctx_r8.selectedPickup);
    \u0275\u0275property("value", rowData_r11);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r14);
  }
}
function PickupComponent_ng_template_42_Template(rf, ctx) {
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
var _PickupComponent = class _PickupComponent {
  constructor(messageService, cs, router, path, service, dialog, datePipe, fb, auth, spin) {
    this.messageService = messageService;
    this.cs = cs;
    this.router = router;
    this.path = path;
    this.service = service;
    this.dialog = dialog;
    this.datePipe = datePipe;
    this.fb = fb;
    this.auth = auth;
    this.spin = spin;
    this.pickupTable = [];
    this.selectedPickup = [];
    this.cols = [];
    this.target = [];
    this.searchform = this.fb.group({
      pickUpId: [],
      // statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      pickUpId: "Pickup",
      statusId: "Status"
    };
    this.languageDropdown = [];
    this.companyDropdown = [];
    this.pickupDropdown = [];
    this.statusDropdown = [];
  }
  ngOnInit() {
    const dataToSend = ["LastMile", "Pickup"];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "companyName", header: "Company" },
      { field: "pickUpId", header: "ID", format: "hyperLink" },
      { field: "partnerType", header: "Partner Type" },
      { field: "partnerId", header: "Partner ID" },
      { field: "serviceTypeId", header: "Service Type" },
      { field: "consignmentBagId", header: "Consignment Bag ID" },
      { field: "pickupAddress", header: "Pickup Address" },
      { field: "remarks", header: "Remarks" },
      { field: "statusDescription", header: "Status" },
      { field: "createdBy", header: "Created By" },
      { field: "createdOn", header: "Created On", format: "date" }
    ];
    this.target = [
      { field: "languageId", header: "Language ID" },
      { field: "languageDescription", header: "Language" },
      { field: "companyId", header: "Company ID" },
      { field: "statusId", header: "Status ID" },
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
          this.pickupTable = res;
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
    const choosen = this.selectedPickup[this.selectedPickup.length - 1];
    this.selectedPickup.length = 0;
    this.selectedPickup.push(choosen);
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
        this.deleterecord(this.selectedPickup[0]);
      }
    });
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedPickup = linedata;
    }
    if (this.selectedPickup.length === 0 && type != "New") {
      this.messageService.add({
        severity: "warn",
        summary: "Warning",
        key: "br",
        detail: "Kindly select any row"
      });
    } else {
      let paramdata = this.cs.encrypt({
        line: linedata == null ? this.selectedPickup[0] : linedata,
        pageflow: type
      });
      this.router.navigate(["/main/lastMile/pickup-new/" + paramdata]);
    }
  }
  deleteDialog() {
    if (this.selectedPickup.length === 0) {
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
        line: this.selectedPickup,
        module: "Pickup",
        body: "This action cannot be undone. All values associated with this field will be lost."
      }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedPickup[0]);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: "success", summary: "Deleted", key: "br", detail: lines.pickUpId + " deleted successfully" });
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
    const exportData = this.pickupTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, "Pickup");
  }
  getSearchDropdown() {
    this.pickupTable.forEach((res) => {
      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, "value");
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, "value");
      }
      if (res.pickUpId != null) {
        this.pickupDropdown.push({ value: res.pickUpId, label: res.pickUpId });
        this.pickupDropdown = this.cs.removeDuplicatesFromArrayList(this.pickupDropdown, "value");
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
        this.pickupTable = res;
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
      pickUpId: [],
      // statusId: [],
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
_PickupComponent.\u0275fac = function PickupComponent_Factory(t) {
  return new (t || _PickupComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(PickupService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(NgxSpinnerService));
};
_PickupComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _PickupComponent, selectors: [["app-pickup"]], viewQuery: function PickupComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c02, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 43, vars: 30, consts: [["pickup", ""], ["pickupTag", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "pickUpId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "single", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "columns", "value", "scrollable", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "onChange", "ngModelChange", "ngModel", "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"]], template: function PickupComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 3)(1, "div", 4)(2, "p", 5);
    \u0275\u0275text(3, "Pickup");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 4)(5, "img", 6);
    \u0275\u0275listener("click", function PickupComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 7);
    \u0275\u0275listener("click", function PickupComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 8);
    \u0275\u0275listener("click", function PickupComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "img", 9);
    \u0275\u0275listener("click", function PickupComponent_Template_img_click_8_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.customTable());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "button", 10);
    \u0275\u0275listener("click", function PickupComponent_Template_button_click_9_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("New"));
    });
    \u0275\u0275element(10, "i", 11);
    \u0275\u0275text(11, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 12)(13, "p-iconField", 13)(14, "p-inputIcon", 14);
    \u0275\u0275listener("click", function PickupComponent_Template_p_inputIcon_click_14_listener($event) {
      \u0275\u0275restoreView(_r1);
      const pickup_r2 = \u0275\u0275reference(17);
      return \u0275\u0275resetView(pickup_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "input", 15);
    \u0275\u0275listener("input", function PickupComponent_Template_input_input_15_listener($event) {
      \u0275\u0275restoreView(_r1);
      const pickupTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(pickupTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(16, "p-overlayPanel", 16, 0)(18, "form", 17)(19, "div", 18)(20, "div", 19)(21, "p", 20);
    \u0275\u0275text(22, "Pickup ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(23, "p-multiSelect", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(24, "div", 19)(25, "p", 20);
    \u0275\u0275text(26, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275element(27, "p-multiSelect", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 23)(29, "div")(30, "img", 24);
    \u0275\u0275listener("click", function PickupComponent_Template_img_click_30_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(31, "div")(32, "button", 25);
    \u0275\u0275listener("click", function PickupComponent_Template_button_click_32_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(33, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(34, "button", 26);
    \u0275\u0275listener("click", function PickupComponent_Template_button_click_34_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(35, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(36, "p-chips", 27);
    \u0275\u0275listener("onRemove", function PickupComponent_Template_p_chips_onRemove_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function PickupComponent_Template_p_chips_ngModelChange_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(37, "div", 28)(38, "p-table", 29, 1);
    \u0275\u0275template(40, PickupComponent_ng_template_40_Template, 8, 4, "ng-template", 30)(41, PickupComponent_ng_template_41_Template, 4, 5, "ng-template", 31)(42, PickupComponent_ng_template_42_Template, 4, 1, "ng-template", 32);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(16);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(24, _c1));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(25, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.pickupDropdown)("panelStyle", \u0275\u0275pureFunction0(26, _c2));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(28, _c2));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.pickupTable)("scrollable", true)("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(29, _c3));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, SortIcon, TableHeaderCheckbox, InputIcon, IconField, InputText, Checkbox, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=pickup.component.css.map */"] });
var PickupComponent = _PickupComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(PickupComponent, { className: "PickupComponent", filePath: "src\\app\\main\\last-mile\\pickup\\pickup.component.ts", lineNumber: 21 });
})();

// src/app/main/last-mile/delivery/delivery.component.ts
var _c03 = ["pickup"];
var _c12 = () => ({ width: "80vw" });
var _c22 = () => ({ "width": "100%" });
var _c32 = () => ({ "width": "100rem" });
function DeliveryComponent_ng_template_40_th_3_Template(rf, ctx) {
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
function DeliveryComponent_ng_template_40_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 39);
    \u0275\u0275listener("input", function DeliveryComponent_ng_template_40_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const pickupTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(pickupTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const pickupTag_r3 = \u0275\u0275reference(39);
    \u0275\u0275advance();
    \u0275\u0275property("value", pickupTag_r3.filters[col_r6.field] == null ? null : pickupTag_r3.filters[col_r6.field].value);
  }
}
function DeliveryComponent_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 33);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, DeliveryComponent_ng_template_40_th_3_Template, 3, 3, "th", 35);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 33);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, DeliveryComponent_ng_template_40_th_7_Template, 2, 1, "th", 36);
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
function DeliveryComponent_ng_template_41_td_3_ng_container_1_Template(rf, ctx) {
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
function DeliveryComponent_ng_template_41_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r12 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 46);
    \u0275\u0275listener("click", function DeliveryComponent_ng_template_41_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
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
function DeliveryComponent_ng_template_41_td_3_ng_template_2_span_1_Template(rf, ctx) {
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
function DeliveryComponent_ng_template_41_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, DeliveryComponent_ng_template_41_td_3_ng_template_2_span_0_Template, 2, 1, "span", 44)(1, DeliveryComponent_ng_template_41_td_3_ng_template_2_span_1_Template, 3, 4, "span", 45);
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r10.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format == "date");
  }
}
function DeliveryComponent_ng_template_41_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, DeliveryComponent_ng_template_41_td_3_ng_container_1_Template, 2, 1, "ng-container", 43)(2, DeliveryComponent_ng_template_41_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 2, \u0275\u0275templateRefExtractor);
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
function DeliveryComponent_ng_template_41_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr")(1, "td", 40)(2, "p-checkbox", 41);
    \u0275\u0275listener("onChange", function DeliveryComponent_ng_template_41_Template_p_checkbox_onChange_2_listener() {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.onChange());
    });
    \u0275\u0275twoWayListener("ngModelChange", function DeliveryComponent_ng_template_41_Template_p_checkbox_ngModelChange_2_listener($event) {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r8.selectedDelivery, $event) || (ctx_r8.selectedDelivery = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275template(3, DeliveryComponent_ng_template_41_td_3_Template, 4, 4, "td", 42);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r11 = ctx.$implicit;
    const columns_r14 = ctx.columns;
    const ctx_r8 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r8.selectedDelivery[0] === rowData_r11);
    \u0275\u0275advance(2);
    \u0275\u0275twoWayProperty("ngModel", ctx_r8.selectedDelivery);
    \u0275\u0275property("value", rowData_r11);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r14);
  }
}
function DeliveryComponent_ng_template_42_Template(rf, ctx) {
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
var _DeliveryComponent = class _DeliveryComponent {
  constructor(messageService, cs, router, path, service, dialog, datePipe, fb, auth, spin) {
    this.messageService = messageService;
    this.cs = cs;
    this.router = router;
    this.path = path;
    this.service = service;
    this.dialog = dialog;
    this.datePipe = datePipe;
    this.fb = fb;
    this.auth = auth;
    this.spin = spin;
    this.deliveryTable = [];
    this.selectedDelivery = [];
    this.cols = [];
    this.target = [];
    this.searchform = this.fb.group({
      pickUpId: [],
      // statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      pickUpId: "Pickup",
      statusId: "Status"
    };
    this.languageDropdown = [];
    this.companyDropdown = [];
    this.pickupDropdown = [];
    this.statusDropdown = [];
  }
  ngOnInit() {
    const dataToSend = ["LastMile", "Delivery"];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "companyName", header: "Company" },
      { field: "pickUpId", header: "ID", format: "hyperLink" },
      { field: "partnerType", header: "Partner Type" },
      { field: "partnerId", header: "Partner ID" },
      { field: "partnerName", header: "Partner Name" },
      { field: "consignmentBagId", header: "Consignment Bag ID" },
      { field: "remarks", header: "Remarks" },
      { field: "statusDescription", header: "Status" },
      { field: "createdBy", header: "Created By" },
      { field: "createdOn", header: "Created On", format: "date" }
    ];
    this.target = [
      { field: "languageId", header: "Language ID" },
      { field: "languageDescription", header: "Language" },
      { field: "companyId", header: "Company ID" },
      { field: "statusId", header: "Status ID" },
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
          this.deliveryTable = res;
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
    const choosen = this.selectedDelivery[this.selectedDelivery.length - 1];
    this.selectedDelivery.length = 0;
    this.selectedDelivery.push(choosen);
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
        this.deleterecord(this.selectedDelivery[0]);
      }
    });
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedDelivery = linedata;
    }
    if (this.selectedDelivery.length === 0 && type != "New") {
      this.messageService.add({
        severity: "warn",
        summary: "Warning",
        key: "br",
        detail: "Kindly select any row"
      });
    } else {
      let paramdata = this.cs.encrypt({
        line: linedata == null ? this.selectedDelivery[0] : linedata,
        pageflow: type
      });
      this.router.navigate(["/main/lastMile/delivery-new/" + paramdata]);
    }
  }
  deleteDialog() {
    if (this.selectedDelivery.length === 0) {
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
        line: this.selectedDelivery,
        module: "Pickup",
        body: "This action cannot be undone. All values associated with this field will be lost."
      }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedDelivery[0]);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: "success", summary: "Deleted", key: "br", detail: lines.pickUpId + " deleted successfully" });
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
    const exportData = this.deliveryTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, "Pickup");
  }
  getSearchDropdown() {
    this.deliveryTable.forEach((res) => {
      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, "value");
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, "value");
      }
      if (res.pickUpId != null) {
        this.pickupDropdown.push({ value: res.pickUpId, label: res.pickUpId });
        this.pickupDropdown = this.cs.removeDuplicatesFromArrayList(this.pickupDropdown, "value");
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
        this.deliveryTable = res;
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
      pickUpId: [],
      // statusId: [],
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
_DeliveryComponent.\u0275fac = function DeliveryComponent_Factory(t) {
  return new (t || _DeliveryComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(PickupService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(NgxSpinnerService));
};
_DeliveryComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _DeliveryComponent, selectors: [["app-delivery"]], viewQuery: function DeliveryComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c03, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 43, vars: 30, consts: [["pickup", ""], ["pickupTag", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "pickUpId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "single", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "columns", "value", "scrollable", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "onChange", "ngModelChange", "ngModel", "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"]], template: function DeliveryComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 3)(1, "div", 4)(2, "p", 5);
    \u0275\u0275text(3, "Delivery");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 4)(5, "img", 6);
    \u0275\u0275listener("click", function DeliveryComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 7);
    \u0275\u0275listener("click", function DeliveryComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 8);
    \u0275\u0275listener("click", function DeliveryComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(8, "img", 9);
    \u0275\u0275listener("click", function DeliveryComponent_Template_img_click_8_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.customTable());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "button", 10);
    \u0275\u0275listener("click", function DeliveryComponent_Template_button_click_9_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("New"));
    });
    \u0275\u0275element(10, "i", 11);
    \u0275\u0275text(11, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 12)(13, "p-iconField", 13)(14, "p-inputIcon", 14);
    \u0275\u0275listener("click", function DeliveryComponent_Template_p_inputIcon_click_14_listener($event) {
      \u0275\u0275restoreView(_r1);
      const pickup_r2 = \u0275\u0275reference(17);
      return \u0275\u0275resetView(pickup_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "input", 15);
    \u0275\u0275listener("input", function DeliveryComponent_Template_input_input_15_listener($event) {
      \u0275\u0275restoreView(_r1);
      const pickupTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(pickupTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(16, "p-overlayPanel", 16, 0)(18, "form", 17)(19, "div", 18)(20, "div", 19)(21, "p", 20);
    \u0275\u0275text(22, "Pickup ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(23, "p-multiSelect", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(24, "div", 19)(25, "p", 20);
    \u0275\u0275text(26, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275element(27, "p-multiSelect", 22);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 23)(29, "div")(30, "img", 24);
    \u0275\u0275listener("click", function DeliveryComponent_Template_img_click_30_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(31, "div")(32, "button", 25);
    \u0275\u0275listener("click", function DeliveryComponent_Template_button_click_32_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(33, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(34, "button", 26);
    \u0275\u0275listener("click", function DeliveryComponent_Template_button_click_34_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(35, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(36, "p-chips", 27);
    \u0275\u0275listener("onRemove", function DeliveryComponent_Template_p_chips_onRemove_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function DeliveryComponent_Template_p_chips_ngModelChange_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(37, "div", 28)(38, "p-table", 29, 1);
    \u0275\u0275template(40, DeliveryComponent_ng_template_40_Template, 8, 4, "ng-template", 30)(41, DeliveryComponent_ng_template_41_Template, 4, 5, "ng-template", 31)(42, DeliveryComponent_ng_template_42_Template, 4, 1, "ng-template", 32);
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
    \u0275\u0275property("showClear", true)("options", ctx.pickupDropdown)("panelStyle", \u0275\u0275pureFunction0(26, _c22));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c22));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(28, _c22));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.deliveryTable)("scrollable", true)("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(29, _c32));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, SortIcon, TableHeaderCheckbox, InputIcon, IconField, InputText, Checkbox, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=delivery.component.css.map */"] });
var DeliveryComponent = _DeliveryComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(DeliveryComponent, { className: "DeliveryComponent", filePath: "src\\app\\main\\last-mile\\delivery\\delivery.component.ts", lineNumber: 21 });
})();

// src/app/main/last-mile/delivery/delivery-new/delivery-new.component.ts
var _c04 = () => ({ "width": "100%" });
function DeliveryNewComponent_ng_template_11_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 14);
    \u0275\u0275text(1, "1");
    \u0275\u0275elementEnd();
  }
}
function DeliveryNewComponent_ng_template_11_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 15);
  }
}
function DeliveryNewComponent_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 9);
    \u0275\u0275listener("click", function DeliveryNewComponent_ng_template_11_Template_div_click_0_listener() {
      const onClick_r2 = \u0275\u0275restoreView(_r1).onClick;
      return \u0275\u0275resetView(onClick_r2.emit());
    });
    \u0275\u0275elementStart(1, "div", 10);
    \u0275\u0275template(2, DeliveryNewComponent_ng_template_11_p_2_Template, 2, 0, "p", 11)(3, DeliveryNewComponent_ng_template_11_img_3_Template, 1, 0, "img", 12);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 13);
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
function DeliveryNewComponent_ng_template_12_ng_template_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_mat_error_6_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 44)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_16_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_21_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_30_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_mat_error_35_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 44)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r2.getErrorMessage());
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_44_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_57_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_62_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_67_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_72_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_ng_template_89_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_12_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 16)(1, "div", 17)(2, "p", 18);
    \u0275\u0275text(3, "Company");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "p-dropdown", 19);
    \u0275\u0275template(5, DeliveryNewComponent_ng_template_12_ng_template_5_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd();
    \u0275\u0275template(6, DeliveryNewComponent_ng_template_12_mat_error_6_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "div", 17)(8, "p", 18);
    \u0275\u0275text(9, "Partner ID");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "p-dropdown", 22);
    \u0275\u0275template(11, DeliveryNewComponent_ng_template_12_ng_template_11_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(12, "div", 17)(13, "p", 18);
    \u0275\u0275text(14, "HAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "p-dropdown", 23);
    \u0275\u0275template(16, DeliveryNewComponent_ng_template_12_ng_template_16_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "div", 17)(18, "p", 18);
    \u0275\u0275text(19, "Service Type ID");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(20, "p-dropdown", 24);
    \u0275\u0275template(21, DeliveryNewComponent_ng_template_12_ng_template_21_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(22, "div", 17)(23, "p", 18);
    \u0275\u0275text(24, "Piece Count");
    \u0275\u0275elementEnd();
    \u0275\u0275element(25, "input", 25);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "div", 17)(27, "p", 18);
    \u0275\u0275text(28, "Pickup Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(29, "p-dropdown", 26);
    \u0275\u0275template(30, DeliveryNewComponent_ng_template_12_ng_template_30_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(31, "div", 17)(32, "p", 27);
    \u0275\u0275text(33, "Pickup ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(34, "input", 28);
    \u0275\u0275template(35, DeliveryNewComponent_ng_template_12_mat_error_35_Template, 3, 1, "mat-error", 21);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(36, "div", 17)(37, "p", 18);
    \u0275\u0275text(38, "Piece ID");
    \u0275\u0275elementEnd();
    \u0275\u0275element(39, "input", 29);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(40, "div", 17)(41, "p", 18);
    \u0275\u0275text(42, "Payment Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(43, "p-dropdown", 30);
    \u0275\u0275template(44, DeliveryNewComponent_ng_template_12_ng_template_44_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(45, "div", 17)(46, "p", 18);
    \u0275\u0275text(47, "Total Shipment Weight");
    \u0275\u0275elementEnd();
    \u0275\u0275element(48, "input", 31);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(49, "div", 17)(50, "p", 18);
    \u0275\u0275text(51, "COD Amount");
    \u0275\u0275elementEnd();
    \u0275\u0275element(52, "input", 32);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(53, "div", 17)(54, "p", 18);
    \u0275\u0275text(55, "COD Collection Mode");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(56, "p-dropdown", 33);
    \u0275\u0275template(57, DeliveryNewComponent_ng_template_12_ng_template_57_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(58, "div", 17)(59, "p", 18);
    \u0275\u0275text(60, "Priority");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(61, "p-dropdown", 34);
    \u0275\u0275template(62, DeliveryNewComponent_ng_template_12_ng_template_62_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(63, "div", 17)(64, "p", 18);
    \u0275\u0275text(65, "Courier Type");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(66, "p-dropdown", 35);
    \u0275\u0275template(67, DeliveryNewComponent_ng_template_12_ng_template_67_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(68, "div", 17)(69, "p", 18);
    \u0275\u0275text(70, "Courier ID");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(71, "p-dropdown", 36);
    \u0275\u0275template(72, DeliveryNewComponent_ng_template_12_ng_template_72_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(73, "div", 17)(74, "p", 18);
    \u0275\u0275text(75, "Zone Type Text");
    \u0275\u0275elementEnd();
    \u0275\u0275element(76, "input", 37);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(77, "div", 17)(78, "p", 18);
    \u0275\u0275text(79, "Zone Type Text");
    \u0275\u0275elementEnd();
    \u0275\u0275element(80, "input", 37);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(81, "div", 17)(82, "p", 18);
    \u0275\u0275text(83, "Remark");
    \u0275\u0275elementEnd();
    \u0275\u0275element(84, "input", 38);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(85, "div", 17)(86, "p", 18);
    \u0275\u0275text(87, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(88, "p-dropdown", 39);
    \u0275\u0275template(89, DeliveryNewComponent_ng_template_12_ng_template_89_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(90, "div", 40)(91, "button", 41);
    \u0275\u0275text(92, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(93, "button", 42);
    \u0275\u0275listener("click", function DeliveryNewComponent_ng_template_12_Template_button_click_93_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(94);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    let tmp_28_0;
    let tmp_48_0;
    let tmp_54_0;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(70, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.companyIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(71, _c04));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("companyId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(72, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.partnerIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(73, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(74, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.houseAirwayBillList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(75, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(76, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.serviceTypeIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(77, _c04));
    \u0275\u0275advance(9);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(78, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.pickupTypeList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(79, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275property("ngClass", ((tmp_28_0 = ctx_r2.form.get("pickUpId")) == null ? null : tmp_28_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.errorHandling("pickUpId"));
    \u0275\u0275advance(8);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(80, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.paymentTypeList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(81, _c04));
    \u0275\u0275advance(13);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(82, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.codCollectionModeList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(83, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(84, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.priorityList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(85, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(86, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.courierTypeList)("ngClass", ((tmp_48_0 = ctx_r2.form.get("courierType")) == null ? null : tmp_48_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "")("disabled", true)("panelStyle", \u0275\u0275pureFunction0(87, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(88, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.courierIdList)("ngClass", ((tmp_54_0 = ctx_r2.form.get("courierId")) == null ? null : tmp_54_0.invalid) && ctx_r2.submitted ? "ng-invalid ng-dirty" : "")("disabled", true)("panelStyle", \u0275\u0275pureFunction0(89, _c04));
    \u0275\u0275advance(17);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(90, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.status);
    \u0275\u0275advance(6);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function DeliveryNewComponent_ng_template_14_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 14);
    \u0275\u0275text(1, "2");
    \u0275\u0275elementEnd();
  }
}
function DeliveryNewComponent_ng_template_14_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 15);
  }
}
function DeliveryNewComponent_ng_template_14_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 9);
    \u0275\u0275listener("click", function DeliveryNewComponent_ng_template_14_Template_div_click_0_listener() {
      const onClick_r6 = \u0275\u0275restoreView(_r5).onClick;
      return \u0275\u0275resetView(onClick_r6.emit());
    });
    \u0275\u0275elementStart(1, "div", 10);
    \u0275\u0275template(2, DeliveryNewComponent_ng_template_14_p_2_Template, 2, 0, "p", 11)(3, DeliveryNewComponent_ng_template_14_img_3_Template, 1, 0, "img", 12);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 13);
    \u0275\u0275text(6, "Pickup Details");
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
function DeliveryNewComponent_ng_template_15_ng_template_17_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_15_ng_template_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_15_ng_template_27_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_15_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_15_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 16)(1, "div", 45)(2, "p", 18);
    \u0275\u0275text(3, " Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(4, "input", 46);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "div", 47)(6, "p", 18);
    \u0275\u0275text(7, "Address Line 1");
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "input", 48);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "div", 49)(10, "p", 18);
    \u0275\u0275text(11, "Address Line 2");
    \u0275\u0275elementEnd();
    \u0275\u0275element(12, "input", 50);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 51)(14, "p", 18);
    \u0275\u0275text(15, "City");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-dropdown", 52);
    \u0275\u0275template(17, DeliveryNewComponent_ng_template_15_ng_template_17_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(18, "div", 53)(19, "p", 18);
    \u0275\u0275text(20, "District");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(21, "p-dropdown", 54);
    \u0275\u0275listener("onChange", function DeliveryNewComponent_ng_template_15_Template_p_dropdown_onChange_21_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.districtChanged());
    });
    \u0275\u0275template(22, DeliveryNewComponent_ng_template_15_ng_template_22_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(23, "div", 55)(24, "p", 18);
    \u0275\u0275text(25, "State");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "p-dropdown", 56);
    \u0275\u0275listener("onChange", function DeliveryNewComponent_ng_template_15_Template_p_dropdown_onChange_26_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.provinceChanged());
    });
    \u0275\u0275template(27, DeliveryNewComponent_ng_template_15_ng_template_27_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 53)(29, "p", 18);
    \u0275\u0275text(30, "Country");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(31, "p-dropdown", 57);
    \u0275\u0275listener("onChange", function DeliveryNewComponent_ng_template_15_Template_p_dropdown_onChange_31_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.countryChanged());
    });
    \u0275\u0275template(32, DeliveryNewComponent_ng_template_15_ng_template_32_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(33, "div", 58)(34, "p", 18);
    \u0275\u0275text(35, "Pincode");
    \u0275\u0275elementEnd();
    \u0275\u0275element(36, "input", 59);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(37, "div", 60)(38, "p", 18);
    \u0275\u0275text(39, "Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(40, "input", 61);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "div", 60)(42, "p", 18);
    \u0275\u0275text(43, "Alternate Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(44, "input", 62);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(45, "div", 40)(46, "button", 41);
    \u0275\u0275text(47, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(48, "button", 42);
    \u0275\u0275listener("click", function DeliveryNewComponent_ng_template_15_Template_button_click_48_listener() {
      \u0275\u0275restoreView(_r7);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(49);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(16);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(25, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.cityIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(26, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.districtIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(28, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(29, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.provinceIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(30, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(31, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.countryIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(32, _c04));
    \u0275\u0275advance(18);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
function DeliveryNewComponent_ng_template_17_p_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "p", 14);
    \u0275\u0275text(1, "3");
    \u0275\u0275elementEnd();
  }
}
function DeliveryNewComponent_ng_template_17_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 15);
  }
}
function DeliveryNewComponent_ng_template_17_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 9);
    \u0275\u0275listener("click", function DeliveryNewComponent_ng_template_17_Template_div_click_0_listener() {
      const onClick_r9 = \u0275\u0275restoreView(_r8).onClick;
      return \u0275\u0275resetView(onClick_r9.emit());
    });
    \u0275\u0275elementStart(1, "div", 10);
    \u0275\u0275template(2, DeliveryNewComponent_ng_template_17_p_2_Template, 2, 0, "p", 11)(3, DeliveryNewComponent_ng_template_17_img_3_Template, 1, 0, "img", 12);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div")(5, "p", 13);
    \u0275\u0275text(6, "Destination Details");
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
function DeliveryNewComponent_ng_template_18_ng_template_17_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_18_ng_template_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_18_ng_template_27_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_18_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 43);
  }
}
function DeliveryNewComponent_ng_template_18_Template(rf, ctx) {
  if (rf & 1) {
    const _r10 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 16)(1, "div", 63)(2, "p", 18);
    \u0275\u0275text(3, " Name");
    \u0275\u0275elementEnd();
    \u0275\u0275element(4, "input", 46);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "div", 64)(6, "p", 18);
    \u0275\u0275text(7, "Address Line 1");
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "input", 48);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "div", 65)(10, "p", 18);
    \u0275\u0275text(11, "Address Line 2");
    \u0275\u0275elementEnd();
    \u0275\u0275element(12, "input", 50);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "div", 66)(14, "p", 18);
    \u0275\u0275text(15, "City");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-dropdown", 52);
    \u0275\u0275template(17, DeliveryNewComponent_ng_template_18_ng_template_17_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(18, "div", 67)(19, "p", 18);
    \u0275\u0275text(20, "District");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(21, "p-dropdown", 54);
    \u0275\u0275listener("onChange", function DeliveryNewComponent_ng_template_18_Template_p_dropdown_onChange_21_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.districtChanged());
    });
    \u0275\u0275template(22, DeliveryNewComponent_ng_template_18_ng_template_22_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(23, "div", 68)(24, "p", 18);
    \u0275\u0275text(25, "State");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(26, "p-dropdown", 56);
    \u0275\u0275listener("onChange", function DeliveryNewComponent_ng_template_18_Template_p_dropdown_onChange_26_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.provinceChanged());
    });
    \u0275\u0275template(27, DeliveryNewComponent_ng_template_18_ng_template_27_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 67)(29, "p", 18);
    \u0275\u0275text(30, "Country");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(31, "p-dropdown", 57);
    \u0275\u0275listener("onChange", function DeliveryNewComponent_ng_template_18_Template_p_dropdown_onChange_31_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.countryChanged());
    });
    \u0275\u0275template(32, DeliveryNewComponent_ng_template_18_ng_template_32_Template, 1, 0, "ng-template", 20);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(33, "div", 69)(34, "p", 18);
    \u0275\u0275text(35, "Pincode");
    \u0275\u0275elementEnd();
    \u0275\u0275element(36, "input", 59);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(37, "div", 70)(38, "p", 18);
    \u0275\u0275text(39, "Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(40, "input", 61);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(41, "div", 70)(42, "p", 18);
    \u0275\u0275text(43, "Alternate Phone");
    \u0275\u0275elementEnd();
    \u0275\u0275element(44, "input", 62);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(45, "div", 40)(46, "button", 41);
    \u0275\u0275text(47, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(48, "button", 42);
    \u0275\u0275listener("click", function DeliveryNewComponent_ng_template_18_Template_button_click_48_listener() {
      \u0275\u0275restoreView(_r10);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.save());
    });
    \u0275\u0275text(49);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance(16);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(25, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.cityIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(26, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.districtIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(28, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(29, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.provinceIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(30, _c04));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(31, _c04));
    \u0275\u0275property("showClear", true)("options", ctx_r2.countryIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(32, _c04));
    \u0275\u0275advance(18);
    \u0275\u0275textInterpolate(ctx_r2.pageToken.pageflow != "New" ? "Update" : "Save");
  }
}
var _DeliveryNewComponent = class _DeliveryNewComponent {
  constructor(cs, spin, route, router, path, fb, service, messageService, numberRangeService, provinceService, countryService, districtService, auth, cas) {
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.path = path;
    this.fb = fb;
    this.service = service;
    this.messageService = messageService;
    this.numberRangeService = numberRangeService;
    this.provinceService = provinceService;
    this.countryService = countryService;
    this.districtService = districtService;
    this.auth = auth;
    this.cas = cas;
    this.active = 0;
    this.status = [];
    this.pickupType = [];
    this.PickupDetails = this.fb.group({
      pickupHubCode: [],
      pickupDetailId: [],
      companyName: [],
      emailId: [],
      name: [],
      phone: [],
      alternatePhone: [],
      addressLine1: [],
      addressLine2: [],
      pinCode: [],
      district: [],
      city: [],
      country: [],
      state: [],
      latitude: [],
      longitude: [],
      pickupAddress: []
    });
    this.DestinationDetails = this.fb.group({
      imageReferenceList: [],
      destinationDetailId: [],
      reverseReason: [],
      destinationAddress: [],
      emailId: [],
      name: [],
      companyName: [],
      phone: [],
      alternatePhone: [],
      addressLine1: [],
      addressLine2: [],
      pinCode: [],
      district: [],
      city: [],
      country: [],
      state: [],
      latitude: [],
      longitude: [],
      pickupAddress: []
    });
    this.form = this.fb.group({
      partnerId: [],
      partnerName: [],
      consignmentBagId: [],
      pickupType: ["Forward"],
      paymentType: [],
      pieceId: [],
      pieceCount: [],
      actualSequenceNo: [],
      assignedHubCode: [],
      codFavorOf: [],
      consignmentType: [],
      courierId: [],
      courierType: [],
      customerCode: [],
      customerReferenceNumber: [],
      invoiceAmount: [],
      isCustomsDeclarable: [],
      loadType: [],
      movementType: [],
      packageType: [],
      partnerType: [],
      paymentLink: [],
      pickupAttemptCount: [],
      invoiceUrl: [],
      totalShipmentWeight: [],
      serviceTypeId: [],
      houseAirwayBill: [],
      codAmount: [],
      codCollectionMode: [],
      priority: [],
      customerPickupDate: [],
      pickupTimeSlotStart: [],
      pickupTimeSlotEnd: [],
      pickUpId: [, Validators.required],
      languageId: [this.auth.languageId, Validators.required],
      languageDescription: [],
      companyId: [this.auth.companyId, Validators.required],
      companyName: [],
      pickupDetails: this.PickupDetails,
      destinationDetails: this.DestinationDetails,
      pickupEntityId: [],
      pickupFailedReason: [],
      pickupOtp: [],
      pickupServiceTime: [],
      productCode: [],
      productId: [],
      productName: [],
      reverseReason: [],
      routeId: [],
      rtoOtp: [],
      sequenceNo: [],
      serviceTypeText: [],
      statusTimeStamp: [],
      vehicleRegNumber: [],
      statusDescription: [],
      description: [],
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
      referenceField11: [],
      referenceField12: [],
      referenceField13: [],
      referenceField14: [],
      referenceField15: [],
      referenceField16: [],
      referenceField17: [],
      referenceField18: [],
      referenceField19: [],
      referenceField20: [],
      createdOn: [""],
      createdBy: [],
      updatedBy: [],
      updatedOn: [""],
      statusId: ["16"]
    });
    this.submitted = false;
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.languageIdList = [];
    this.companyIdList = [];
    this.partnerIdList = [];
    this.pieceIdList = [];
    this.paymentTypeList = [];
    this.serviceTypeIdList = [];
    this.houseAirwayBillList = [];
    this.codCollectionModeList = [];
    this.priorityList = [];
    this.courierTypeList = [];
    this.courierIdList = [];
    this.pickupTypeList = [];
    this.districtIdList = [];
    this.cityIdList = [];
    this.countryIdList = [];
    this.provinceIdList = [];
    this.pickupHubCodeList = [];
    this.emailIdList = [];
    this.status = [
      { value: "17", label: "Inactive" },
      { value: "16", label: "Active" }
    ];
    this.pickupType = [
      { value: "Reverse", label: "Reverse" },
      { value: "Forward", label: "Forward" }
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
    const dataToSend = ["LastMile", "Delivery", this.pageToken.pageflow];
    this.path.setData(dataToSend);
    this.dropdownlist();
    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();
    if (this.pageToken.pageflow != "New") {
      this.fill(this.pageToken.line);
      this.form.controls.pickUpId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
    } else {
      this.spin.show();
      let obj = {};
      obj.numberRangeObject = ["PICKUPID"];
      this.numberRangeService.search(obj).subscribe({
        next: (res) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.pickUpId.patchValue(this.nextNumber);
            this.numCondition = "true";
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.pickUpId.disable();
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
      this.spin.show();
      let obj1 = {};
      obj1.numberRangeObject = ["PIECEID"];
      this.numberRangeService.search(obj1).subscribe({
        next: (res) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.pieceId.patchValue(this.nextNumber);
            this.numCondition = "true";
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.pieceId.disable();
          }
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url,
      this.cas.dropdownlist.setup.serviceType.url,
      this.cas.dropdownlist.setup.country.url,
      this.cas.dropdownlist.setup.district.url,
      this.cas.dropdownlist.setup.province.url,
      this.cas.dropdownlist.setup.city.url
    ]).subscribe({
      next: (results) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.serviceTypeIdList = this.cas.forLanguageFilter(results[2], this.cas.dropdownlist.setup.serviceType.key);
        this.countryIdList = this.cas.forLanguageFilter(results[3], this.cas.dropdownlist.setup.country.key);
        this.districtIdList = this.cas.forLanguageFilter(results[4], this.cas.dropdownlist.setup.district.key);
        this.provinceIdList = this.cas.forLanguageFilter(results[5], this.cas.dropdownlist.setup.province.key);
        this.cityIdList = this.cas.forLanguageFilter(results[6], this.cas.dropdownlist.setup.city.key);
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
      this.service.Update([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({ severity: "success", summary: "Updated", key: "br", detail: res.pickUpId + " has been updated successfully" });
          this.router.navigate(["/main/lastMile/pickup"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      this.spin.show();
      this.service.Create([this.form.getRawValue()]).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({ severity: "success", summary: "Created", key: "br", detail: res.pickUpId + " has been created successfully" });
            this.router.navigate(["/main/lastMile/pickup"]);
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
  countryChanged() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.country = [this.form.get("pickupDetails")?.get("country")?.value];
    this.provinceIdList = [];
    this.spin.show();
    this.provinceService.search(obj).subscribe({
      next: (result) => {
        this.provinceIdList = this.cas.foreachlist(result, { key: "provinceId", value: "provinceName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  provinceChanged() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.country = [this.form.get("pickupDetails")?.get("country")?.value];
    obj.country = [this.form.get("pickupDetails")?.get("state")?.value];
    this.districtIdList = [];
    this.spin.show();
    this.districtService.search(obj).subscribe({
      next: (result) => {
        this.districtIdList = this.cas.foreachlist(result, { key: "districtId", value: "districtName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  districtChanged() {
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.country = [this.form.get("pickupDetails")?.get("country")?.value];
    obj.country = [this.form.get("pickupDetails")?.get("state")?.value];
    obj.country = [this.form.get("pickupDetails")?.get("district")?.value];
    this.districtIdList = [];
    this.spin.show();
    this.districtService.search(obj).subscribe({
      next: (result) => {
        this.districtIdList = this.cas.foreachlist(result, { key: "cityId", value: "cityName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
};
_DeliveryNewComponent.\u0275fac = function DeliveryNewComponent_Factory(t) {
  return new (t || _DeliveryNewComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(PickupService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(NumberrangeService), \u0275\u0275directiveInject(ProvinceService), \u0275\u0275directiveInject(CountryService), \u0275\u0275directiveInject(DistrictService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(CommonAPIService));
};
_DeliveryNewComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _DeliveryNewComponent, selectors: [["app-delivery-new"]], decls: 19, vars: 3, consts: [[1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", 1, "invisible", "buttom1", "bgBlack", "text-white", "ml-2"], [1, "w-75", "mt-4", "mx-auto"], [3, "formGroup"], [3, "activeStepChange", "activeStep"], ["pTemplate", "header"], ["pTemplate", "content"], [1, "d-flex", "flex-column", "align-items-center", 3, "click"], [1, "d-flex", "justify-content-center", "align-items-center"], ["class", "circle borderCircle mb-0", 4, "ngIf"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", "", 4, "ngIf"], [1, "mb-0", "mt-2", "f600", "textBlack"], [1, "circle", "borderCircle", "mb-0"], ["src", "./assets/dashboard/tick.png", "alt", "", "srcset", ""], [1, "row", "scrollNew"], [1, "col-6", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["formControlName", "companyId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["class", "text-danger", 4, "ngIf"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "houseAirwayBill", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "serviceTypeId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["maxlength", "50", "formControlName", "pieceCount", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["placeholder", "Select", "filter", "true", "formControlName", "pickupType", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["pInputText", "", "appTrim", "", "formControlName", "pickUpId", "maxlength", "50", "placeholder", "Enter", 1, "w-100", 3, "ngClass"], ["pInputText", "", "appTrim", "", "formControlName", "pieceId", "maxlength", "50", "placeholder", "Enter", 1, "w-100"], ["placeholder", "Select", "filter", "true", "formControlName", "paymentType", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["maxlength", "50", "formControlName", "totalShipmentWeight", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["maxlength", "50", "formControlName", "codAmount", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["placeholder", "Select", "filter", "true", "formControlName", "codCollectionMode", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "priority", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "courierType", "appendTo", "body", 3, "showClear", "options", "ngClass", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "courierId", "appendTo", "body", 3, "showClear", "options", "ngClass", "disabled", "panelStyle"], ["maxlength", "50", "formControlName", "zoneTypeText", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["maxlength", "2000", "formControlName", "remarks", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["appendTo", "body", "formControlName", "statusId", "placeholder", "Select", "filter", "true", 3, "showClear", "options"], [1, "d-flex", "mt-1", "justify-content-end", 2, "position", "absolute", "right", "3.5%", "bottom", "7%"], ["routerLink", "/main/lastMile/pickup", 1, "buttom1", "textBlack", "mx-1"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], [1, "text-danger"], ["id", "name", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "name", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "addressLine1", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "addressLine1", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "addressLine2", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "addressLine2", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "city", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "city", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["id", "district", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "district", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["id", "state", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["placeholder", "Select", "filter", "true", "formControlName", "state", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "Country", "appendTo", "body", 3, "onChange", "showClear", "options", "disabled", "panelStyle"], ["id", "pincode", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "pincode", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "phone", "formGroupName", "pickupDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["maxlength", "50", "formControlName", "phone", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["maxlength", "50", "formControlName", "alternatePhone", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["id", "name", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "addressLine1", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "addressLine2", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "city", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "district", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "state", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "pincode", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"], ["id", "phone", "formGroupName", "destinationDetails", 1, "col-6", "marginFieldNew", "borderRadius12"]], template: function DeliveryNewComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1)(2, "p", 2);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 1)(5, "button", 3);
    \u0275\u0275text(6, "Transfer");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(7, "div", 4)(8, "form", 5)(9, "p-stepper", 6);
    \u0275\u0275twoWayListener("activeStepChange", function DeliveryNewComponent_Template_p_stepper_activeStepChange_9_listener($event) {
      \u0275\u0275twoWayBindingSet(ctx.active, $event) || (ctx.active = $event);
      return $event;
    });
    \u0275\u0275elementStart(10, "p-stepperPanel");
    \u0275\u0275template(11, DeliveryNewComponent_ng_template_11_Template, 7, 2, "ng-template", 7)(12, DeliveryNewComponent_ng_template_12_Template, 95, 91, "ng-template", 8);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "p-stepperPanel");
    \u0275\u0275template(14, DeliveryNewComponent_ng_template_14_Template, 7, 2, "ng-template", 7)(15, DeliveryNewComponent_ng_template_15_Template, 50, 33, "ng-template", 8);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-stepperPanel");
    \u0275\u0275template(17, DeliveryNewComponent_ng_template_17_Template, 7, 2, "ng-template", 7)(18, DeliveryNewComponent_ng_template_18_Template, 50, 33, "ng-template", 8);
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1("Delivery - ", ctx.pageToken.pageflow, "");
    \u0275\u0275advance(5);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance();
    \u0275\u0275twoWayProperty("activeStep", ctx.active);
  }
}, dependencies: [NgClass, NgIf, RouterLink, PrimeTemplate, Dropdown, InputText, Stepper, StepperPanel, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, MatError, FormGroupDirective, FormControlName, FormGroupName, TrimDirective], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 2.5rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 24rem);\n  overflow-y: scroll;\n}\n/*# sourceMappingURL=delivery-new.component.css.map */"] });
var DeliveryNewComponent = _DeliveryNewComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(DeliveryNewComponent, { className: "DeliveryNewComponent", filePath: "src\\app\\main\\last-mile\\delivery\\delivery-new\\delivery-new.component.ts", lineNumber: 21 });
})();

// src/app/main/last-mile/last-mile-routing.module.ts
var routes = [
  { path: "pickup", component: PickupComponent, data: { title: "LastMile", module: "Pickup" } },
  { path: "pickup-new/:code", component: PickupNewComponent, data: { title: "LastMile", module: "Pickup - Add New" } },
  { path: "delivery", component: DeliveryComponent, data: { title: "LastMile", module: "Delivery" } },
  { path: "delivery-new/:code", component: DeliveryNewComponent, data: { title: "LastMile", module: "Delivery - Add New" } }
];
var _LastMileRoutingModule = class _LastMileRoutingModule {
};
_LastMileRoutingModule.\u0275fac = function LastMileRoutingModule_Factory(t) {
  return new (t || _LastMileRoutingModule)();
};
_LastMileRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _LastMileRoutingModule });
_LastMileRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forChild(routes), RouterModule] });
var LastMileRoutingModule = _LastMileRoutingModule;

// src/app/main/last-mile/last-mile.module.ts
var _LastMileModule = class _LastMileModule {
};
_LastMileModule.\u0275fac = function LastMileModule_Factory(t) {
  return new (t || _LastMileModule)();
};
_LastMileModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _LastMileModule });
_LastMileModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [
  CommonModule,
  LastMileRoutingModule,
  SharedModule
] });
var LastMileModule = _LastMileModule;
export {
  LastMileModule
};
//# sourceMappingURL=chunk-FVSV5NLU.js.map
