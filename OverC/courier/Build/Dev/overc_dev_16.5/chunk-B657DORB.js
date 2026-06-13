import {
  ConsoleService
} from "./chunk-QZFR5JK7.js";
import {
  ConsignmentLabelComponent
} from "./chunk-DWUKJWN3.js";
import "./chunk-S3SFHHBA.js";
import {
  PrealertService
} from "./chunk-JJZSPPOK.js";
import {
  NumberrangeService
} from "./chunk-5VNCQIEH.js";
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
  FrozenColumn,
  HttpClient,
  IconField,
  InputIcon,
  InputText,
  Location,
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
  RouterModule,
  SharedModule,
  SortIcon,
  SortableColumn,
  Table,
  TableCheckbox,
  TableHeaderCheckbox,
  TrimDirective,
  Validators,
  __spreadProps,
  __spreadValues,
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
} from "./chunk-HJPREMTO.js";

// src/app/main/reports/report.service.ts
var _ReportService = class _ReportService {
  constructor(http) {
    this.http = http;
    this.apiName = "/overc-midmile-service/reports";
  }
  search(obj) {
    return this.http.post(this.apiName + "/consoleTrackingReport", obj);
  }
  customssearch() {
    return this.http.get("/overc-midmile-service/customsCalculation/report");
  }
  executeCostSheet(obj) {
    return this.http.post("/overc-midmile-service/prealert/findPrealert", obj);
  }
  executeExpenseSheet(obj) {
    return this.http.post("/overc-midmile-service/prealert/findPrealert", obj);
  }
};
_ReportService.\u0275fac = function ReportService_Factory(t) {
  return new (t || _ReportService)(\u0275\u0275inject(HttpClient));
};
_ReportService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _ReportService, factory: _ReportService.\u0275fac, providedIn: "root" });
var ReportService = _ReportService;

// src/app/main/reports/console-tracking/console-tracking.component.ts
var _c0 = ["consoleTrackingReport"];
var _c1 = () => ({ width: "80vw" });
var _c2 = () => ({ "width": "100%" });
function ConsoleTrackingComponent_ng_template_35_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 36);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 37);
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
function ConsoleTrackingComponent_ng_template_35_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 38);
    \u0275\u0275listener("input", function ConsoleTrackingComponent_ng_template_35_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const consoleTrackingReportTag_r3 = \u0275\u0275reference(34);
      return \u0275\u0275resetView(consoleTrackingReportTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const consoleTrackingReportTag_r3 = \u0275\u0275reference(34);
    \u0275\u0275advance();
    \u0275\u0275property("value", consoleTrackingReportTag_r3.filters[col_r6.field] == null ? null : consoleTrackingReportTag_r3.filters[col_r6.field].value);
  }
}
function ConsoleTrackingComponent_ng_template_35_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 32);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 33);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, ConsoleTrackingComponent_ng_template_35_th_3_Template, 3, 3, "th", 34);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 32);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 33);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, ConsoleTrackingComponent_ng_template_35_th_7_Template, 2, 1, "th", 35);
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
function ConsoleTrackingComponent_ng_template_36_td_3_ng_container_1_Template(rf, ctx) {
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
function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r12 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 45);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r12);
      const rowData_r11 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.noOfShipments("Edit", rowData_r11));
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
function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r13 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 45);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_1_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r13);
      const rowData_r11 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.noOfConsoles("Edit", rowData_r11));
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
function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r14 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 45);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_2_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r14);
      const rowData_r11 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.noOfUnconsolidated("Edit", rowData_r11));
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
function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_3_Template(rf, ctx) {
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
function ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_0_Template, 2, 1, "span", 43)(1, ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_1_Template, 2, 1, "span", 43)(2, ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_2_Template, 2, 1, "span", 43)(3, ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_span_3_Template, 3, 4, "span", 44);
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r10.field == "noOfShipmentsScanned");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.field == "noOfConsoles");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.field == "noOfUnconsolidatedShipments");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format == "date");
  }
}
function ConsoleTrackingComponent_ng_template_36_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, ConsoleTrackingComponent_ng_template_36_td_3_ng_container_1_Template, 2, 1, "ng-container", 42)(2, ConsoleTrackingComponent_ng_template_36_td_3_ng_template_2_Template, 4, 4, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = ctx.$implicit;
    const dateTemplate_r15 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r10.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format !== "date" && col_r10.format !== "hyperLink")("ngIfElse", dateTemplate_r15);
  }
}
function ConsoleTrackingComponent_ng_template_36_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr")(1, "td", 39)(2, "p-checkbox", 40);
    \u0275\u0275listener("onChange", function ConsoleTrackingComponent_ng_template_36_Template_p_checkbox_onChange_2_listener() {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.onChange());
    });
    \u0275\u0275twoWayListener("ngModelChange", function ConsoleTrackingComponent_ng_template_36_Template_p_checkbox_ngModelChange_2_listener($event) {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r8.selectedConsoleTracking, $event) || (ctx_r8.selectedConsoleTracking = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275template(3, ConsoleTrackingComponent_ng_template_36_td_3_Template, 4, 4, "td", 41);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r11 = ctx.$implicit;
    const columns_r16 = ctx.columns;
    const ctx_r8 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r8.selectedConsoleTracking[0] === rowData_r11);
    \u0275\u0275advance(2);
    \u0275\u0275twoWayProperty("ngModel", ctx_r8.selectedConsoleTracking);
    \u0275\u0275property("value", rowData_r11);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r16);
  }
}
function ConsoleTrackingComponent_ng_template_37_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 46);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function ConsoleTrackingComponent_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "button", 47);
    \u0275\u0275text(1, "Edit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(2, "button", 47);
    \u0275\u0275text(3, "Confirm");
    \u0275\u0275elementEnd();
  }
}
var _ConsoleTrackingComponent = class _ConsoleTrackingComponent {
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
    this.consoleTrackingReportTable = [];
    this.selectedConsoleTracking = [];
    this.cols = [];
    this.target = [];
    this.searchform = this.fb.group({
      partnerMasterAirwayBill: [],
      partnerHouseAirwayBill: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      partnerMasterAirwayBill: "Partner MAWB",
      partnerHouseAirwayBill: "Partner HAWB"
    };
    this.languageDropdown = [];
    this.companyDropdown = [];
    this.partnerMAWBDropdown = [];
    this.partnerHAWBDropdown = [];
  }
  ngOnInit() {
    const dataToSend = ["Mid-Mile", "Console Tracking"];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "partnerMasterAirwayBill", header: "Partner MAWB" },
      { field: "noOfShipmentsScanned", header: "Total Shipments", format: "hyperLink" },
      { field: "noOfConsoles", header: "Consolidated Shipments", format: "hyperLink" },
      { field: "noOfUnconsolidatedShipments", header: "Unconsolidated Shipments", format: "hyperLink" }
    ];
    this.target = [
      { field: "languageId", header: "Language ID" },
      { field: "companyId", header: "Company ID" }
    ];
  }
  initialCall() {
    this.spin.show();
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    this.service.search(obj).subscribe({
      next: (res = []) => {
        this.consoleTrackingReportTable = res;
        this.getSearchDropdown();
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  onChange() {
    const choosen = this.selectedConsoleTracking[this.selectedConsoleTracking.length - 1];
    this.selectedConsoleTracking.length = 0;
    this.selectedConsoleTracking.push(choosen);
  }
  customTable() {
    const dialogRef = this.dialog.open(CustomTableComponent, {
      disableClose: true,
      width: "70%",
      maxWidth: "80%",
      position: { top: "6.5%", left: "30%" },
      data: { target: this.cols, source: this.target }
    });
  }
  downloadExcel() {
    const exportData = this.consoleTrackingReportTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, "Console Tracking");
  }
  getSearchDropdown() {
    this.consoleTrackingReportTable.forEach((res) => {
      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, "value");
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, "value");
      }
      if (res.partnerMasterAirwayBill != null) {
        this.partnerMAWBDropdown.push({ value: res.partnerMasterAirwayBill, label: res.partnerMasterAirwayBill });
        this.partnerMAWBDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerMAWBDropdown, "value");
      }
      if (res.partnerHouseAirwayBill != null) {
        this.partnerHAWBDropdown.push({ value: res.partnerHouseAirwayBill, label: res.partnerHouseAirwayBill });
        this.partnerHAWBDropdown = this.cs.removeDuplicatesFromArrayList(this.partnerHAWBDropdown, "value");
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
        this.consoleTrackingReportTable = res;
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
      partnerMasterAirwayBill: [],
      partnerHouseAirwayBill: [],
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
  noOfShipments(type = "New", linedata = null) {
    if (linedata) {
      this.selectedConsoleTracking = linedata;
    }
    if (this.selectedConsoleTracking.length === 0) {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any row" });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsoleTracking[0] : linedata, pageflow: type, report: true });
      this.router.navigate(["/main/airport/preAlertManifest-update/" + paramdata]);
    }
  }
  noOfConsoles(type = "New", linedata = null) {
    if (linedata) {
      this.selectedConsoleTracking = linedata;
    }
    if (this.selectedConsoleTracking.length === 0 && type != "New") {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any Row" });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsoleTracking[0] : linedata, pageflow: type, report: true, module: "consolidated" });
      this.router.navigate(["/main/airport/console-edit/" + paramdata]);
    }
  }
  noOfUnconsolidated(type = "New", linedata = null) {
    if (linedata) {
      this.selectedConsoleTracking = linedata;
    }
    if (this.selectedConsoleTracking.length === 0 && type != "New") {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any Row" });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsoleTracking[0] : linedata, pageflow: type, report: true, module: "unconsolidated" });
      this.router.navigate(["/main/airport/console-edit/" + paramdata]);
    }
  }
};
_ConsoleTrackingComponent.\u0275fac = function ConsoleTrackingComponent_Factory(t) {
  return new (t || _ConsoleTrackingComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(ReportService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NgxSpinnerService));
};
_ConsoleTrackingComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _ConsoleTrackingComponent, selectors: [["app-console-tracking"]], viewQuery: function ConsoleTrackingComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c0, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 41, vars: 28, consts: [["consoleTrackingReport", ""], ["consoleTrackingReportTag", ""], ["menu", "matMenu"], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem", 3, "click"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerMasterAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerHouseAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "single", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "columns", "value", "scrollable", "paginator", "rows", "showCurrentPageReport", "sortOrder"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [1, "customClass"], ["matMenuContent", ""], [2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "onChange", "ngModelChange", "ngModel", "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"], ["mat-menu-item", ""]], template: function ConsoleTrackingComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 4)(1, "div", 5)(2, "p", 6);
    \u0275\u0275text(3, "Console Tracking");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 5)(5, "img", 7);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 8);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.customTable());
    });
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(7, "div", 9)(8, "p-iconField", 10)(9, "p-inputIcon", 11);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_Template_p_inputIcon_click_9_listener($event) {
      \u0275\u0275restoreView(_r1);
      const consoleTrackingReport_r2 = \u0275\u0275reference(12);
      return \u0275\u0275resetView(consoleTrackingReport_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "input", 12);
    \u0275\u0275listener("input", function ConsoleTrackingComponent_Template_input_input_10_listener($event) {
      \u0275\u0275restoreView(_r1);
      const consoleTrackingReportTag_r3 = \u0275\u0275reference(34);
      return \u0275\u0275resetView(consoleTrackingReportTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(11, "p-overlayPanel", 13, 0)(13, "form", 14)(14, "div", 15)(15, "div", 16)(16, "p", 17);
    \u0275\u0275text(17, "Partner MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(18, "p-multiSelect", 18);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(19, "div", 16)(20, "p", 17);
    \u0275\u0275text(21, "Partner HAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(22, "p-multiSelect", 19);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(23, "div", 20)(24, "div")(25, "img", 21);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_Template_img_click_25_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(26, "div")(27, "button", 22);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_Template_button_click_27_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(28, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(29, "button", 23);
    \u0275\u0275listener("click", function ConsoleTrackingComponent_Template_button_click_29_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(30, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(31, "p-chips", 24);
    \u0275\u0275listener("onRemove", function ConsoleTrackingComponent_Template_p_chips_onRemove_31_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function ConsoleTrackingComponent_Template_p_chips_ngModelChange_31_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(32, "div", 25)(33, "p-table", 26, 1);
    \u0275\u0275template(35, ConsoleTrackingComponent_ng_template_35_Template, 8, 4, "ng-template", 27)(36, ConsoleTrackingComponent_ng_template_36_Template, 4, 5, "ng-template", 28)(37, ConsoleTrackingComponent_ng_template_37_Template, 4, 1, "ng-template", 29);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(38, "mat-menu", 30, 2);
    \u0275\u0275template(40, ConsoleTrackingComponent_ng_template_40_Template, 4, 0, "ng-template", 31);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    \u0275\u0275advance(11);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(23, _c1));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(24, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.partnerMAWBDropdown)("panelStyle", \u0275\u0275pureFunction0(25, _c2));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(26, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.partnerHAWBDropdown)("panelStyle", \u0275\u0275pureFunction0(27, _c2));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.consoleTrackingReportTable)("scrollable", true)("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1);
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, SortIcon, TableHeaderCheckbox, InputIcon, IconField, InputText, Checkbox, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenu, MatMenuItem, MatMenuContent, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 2.5rem;\n}\n.scrollNew[_ngcontent-%COMP%] {\n  height: calc(100vh - 24rem);\n  overflow-y: scroll;\n}\n/*# sourceMappingURL=console-tracking.component.css.map */"] });
var ConsoleTrackingComponent = _ConsoleTrackingComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(ConsoleTrackingComponent, { className: "ConsoleTrackingComponent", filePath: "src\\app\\main\\reports\\console-tracking\\console-tracking.component.ts", lineNumber: 20 });
})();

// src/app/main/reports/inventory-scanning/inventory-scanning.component.ts
var _c02 = ["inventoryScanningReport"];
var _c12 = () => ({ width: "80vw" });
var _c22 = () => ({ "width": "100%" });
var _c3 = (a0) => ({ "selectedRow": a0 });
function InventoryScanningComponent_ng_template_49_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th");
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r4 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r4.header, " ");
  }
}
function InventoryScanningComponent_ng_template_49_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 45);
    \u0275\u0275listener("input", function InventoryScanningComponent_ng_template_49_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const inventoryScanningTableTag_r3 = \u0275\u0275reference(48);
      return \u0275\u0275resetView(inventoryScanningTableTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const inventoryScanningTableTag_r3 = \u0275\u0275reference(48);
    \u0275\u0275advance();
    \u0275\u0275property("value", inventoryScanningTableTag_r3.filters[col_r6.field] == null ? null : inventoryScanningTableTag_r3.filters[col_r6.field].value);
  }
}
function InventoryScanningComponent_ng_template_49_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 40);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 41);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, InventoryScanningComponent_ng_template_49_th_3_Template, 2, 1, "th", 42);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 43);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 44);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, InventoryScanningComponent_ng_template_49_th_7_Template, 2, 1, "th", 42);
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
function InventoryScanningComponent_ng_template_50_td_3_ng_container_1_Template(rf, ctx) {
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
function InventoryScanningComponent_ng_template_50_td_3_ng_template_2_span_0_Template(rf, ctx) {
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
function InventoryScanningComponent_ng_template_50_td_3_ng_template_2_span_1_Template(rf, ctx) {
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
    \u0275\u0275textInterpolate(\u0275\u0275pipeBind2(2, 1, rowData_r11[col_r10.field], "dd-MM-yyyy"));
  }
}
function InventoryScanningComponent_ng_template_50_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, InventoryScanningComponent_ng_template_50_td_3_ng_template_2_span_0_Template, 3, 4, "span", 50)(1, InventoryScanningComponent_ng_template_50_td_3_ng_template_2_span_1_Template, 3, 4, "span", 50);
  }
  if (rf & 2) {
    const col_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r10.format === "date");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format === "date1");
  }
}
function InventoryScanningComponent_ng_template_50_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, InventoryScanningComponent_ng_template_50_td_3_ng_container_1_Template, 2, 1, "ng-container", 49)(2, InventoryScanningComponent_ng_template_50_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r10 = ctx.$implicit;
    const dateTemplate_r12 = \u0275\u0275reference(3);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r10.format !== "date" && col_r10.format !== "date1" && col_r10.format !== "boolean" && col_r10.format !== "hyperLink")("ngIfElse", dateTemplate_r12);
  }
}
function InventoryScanningComponent_ng_template_50_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr", 46)(1, "td", 47)(2, "p-checkbox", 48);
    \u0275\u0275listener("onChange", function InventoryScanningComponent_ng_template_50_Template_p_checkbox_onChange_2_listener() {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r8.onChange());
    });
    \u0275\u0275twoWayListener("ngModelChange", function InventoryScanningComponent_ng_template_50_Template_p_checkbox_ngModelChange_2_listener($event) {
      \u0275\u0275restoreView(_r8);
      const ctx_r8 = \u0275\u0275nextContext();
      \u0275\u0275twoWayBindingSet(ctx_r8.selectedInventoryScanning, $event) || (ctx_r8.selectedInventoryScanning = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275template(3, InventoryScanningComponent_ng_template_50_td_3_Template, 4, 2, "td", 42);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r11 = ctx.$implicit;
    const columns_r13 = ctx.columns;
    const ctx_r8 = \u0275\u0275nextContext();
    \u0275\u0275property("ngClass", \u0275\u0275pureFunction1(4, _c3, ctx_r8.isSelected(rowData_r11)));
    \u0275\u0275advance(2);
    \u0275\u0275twoWayProperty("ngModel", ctx_r8.selectedInventoryScanning);
    \u0275\u0275property("value", rowData_r11);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function InventoryScanningComponent_ng_template_51_Template(rf, ctx) {
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
function InventoryScanningComponent_ng_template_54_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "button", 52);
    \u0275\u0275text(1, "Edit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(2, "button", 52);
    \u0275\u0275text(3, "Confirm");
    \u0275\u0275elementEnd();
  }
}
var _InventoryScanningComponent = class _InventoryScanningComponent {
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
    this.inventoryScanningTable = [];
    this.selectedInventoryScanning = [];
    this.cols = [];
    this.target = [];
    this.searchform = this.fb.group({
      partnerHouseAirwayBill: [],
      partnerMasterAirwayBill: [],
      hawbTypeId: [],
      hawbTimeStamp: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      partnerHouseAirwayBill: "Partner HAWB",
      partnerMasterAirwayBill: "Partner MAWB",
      hawbTypeId: "Action",
      hawbTimeStamp: "Date Time"
    };
    this.houseAirwayBillDropdown = [];
    this.masterAirwayBillDropdown = [];
    this.statusDropdown = [];
    this.timeStampDropdown = [];
  }
  ngOnInit() {
    const link = this.router.url;
    this.activeLink = link.split("/")[3];
    if (this.activeLink == "pendingCustoms") {
      const dataToSend = ["Mid-Mile", "Pending Customs"];
      this.path.setData(dataToSend);
      this.pageFlow = "Pending Customs";
    } else {
      const dataToSend = ["Mid-Mile", "Inventory Scan"];
      this.path.setData(dataToSend);
      this.pageFlow = "Inventory Scan";
    }
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      // { field: 'hawbTypeId', header: 'HAWB Type ID' },
      // { field: 'hawbType', header: 'HAWB Type' },
      // { field: 'hawbTypeDescription', header: 'HAWB Type Description' },
      { field: "partnerHouseAirwayBill", header: "Partner HAWB" },
      { field: "partnerMasterAirwayBill", header: "Partner MAWB" },
      { field: "houseAirwayBill", header: "Consignment No" },
      { field: "pieceId", header: "Piece ID" },
      // { field: 'masterAirwayBill', header: 'Master Airway Bill' },
      // { field: 'pieceTypeId', header: 'Piece Type ID' },
      // { field: 'pieceType', header: 'Piece Type' },
      // { field: 'pieceTypeDescription', header: 'Piece Type Description' },
      { field: "hawbTimeStamp", header: "Scanned Time", format: "date" },
      // { field: 'pieceTimeStamp', header: 'Scanned Time', format: 'date' },
      { field: "updatedBy", header: "Scanned Officer" }
      // { field: 'createdOn', header: 'Created On', format: 'date' },
    ];
    this.target = [
      { field: "pieceId", header: "Piece ID" },
      { field: "companyName", header: "Company" },
      { field: "languageDescription", header: "Language" },
      { field: "companyId", header: " Company ID" },
      { field: "languageId", header: "Language ID" },
      { field: "bagId", header: "Bag ID" }
    ];
  }
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      if (this.activeLink == "pendingCustoms") {
        obj.hawbTypeId = ["6"];
      } else {
        obj.hawbTypeId = ["47"];
      }
      this.service.searchStatus(obj).subscribe({
        next: (res) => {
          this.inventoryScanningTable = res;
          this.inventoryScanningTable = this.cs.removeDuplicatesFromArrayList(this.inventoryScanningTable, "partnerMasterAirwayBill");
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
  isSelected(item) {
    return this.selectedInventoryScanning.includes(item);
  }
  onChange() {
    const choosen = this.selectedInventoryScanning[this.selectedInventoryScanning.length - 1];
    this.selectedInventoryScanning.length = 0;
    this.selectedInventoryScanning.push(choosen);
  }
  downloadExcel() {
    const inventoryScanningReport = [
      { field: "hawbTypeId", header: "HAWB Type ID" },
      { field: "hawbType", header: "HAWB Type" },
      { field: "hawbTypeDescription", header: "HAWB Type Description" },
      { field: "hawbTimeStamp", header: "Time Stamp", format: "date" },
      { field: "houseAirwayBill", header: "Consignment No" },
      { field: "masterAirwayBill", header: "Master Airway Bill" },
      { field: "partnerHouseAirwayBill", header: "Partner HAWB" },
      { field: "partnerMasterAirwayBill", header: "Partner MAWB" },
      { field: "pieceTypeId", header: "Piece Type ID" },
      { field: "pieceType", header: "Piece Type" },
      { field: "pieceTypeDescription", header: "Piece Type Description" },
      { field: "pieceTimeStamp", header: "Time Stamp", format: "date" },
      { field: "pieceId", header: "Piece ID" },
      { field: "companyName", header: "Company" },
      { field: "languageDescription", header: "Language" },
      { field: "companyId", header: " Company ID" },
      { field: "languageId", header: "Language ID" },
      { field: "bagId", header: "Bag ID" },
      { field: "createdBy", header: "Created By" },
      { field: "createdOn", header: "Created On", format: "date" }
    ];
    const exportData = this.inventoryScanningTable.map((item) => {
      const exportItem = {};
      inventoryScanningReport.forEach((col) => {
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, "Inventory Scan Report");
  }
  getSearchDropdown() {
    this.inventoryScanningTable.forEach((res) => {
      if (res.partnerHouseAirwayBill != null) {
        this.houseAirwayBillDropdown.push({ value: res.partnerHouseAirwayBill, label: res.partnerHouseAirwayBill });
        this.houseAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.houseAirwayBillDropdown, "value");
      }
      if (res.partnerMasterAirwayBill != null) {
        this.masterAirwayBillDropdown.push({ value: res.partnerMasterAirwayBill, label: res.partnerMasterAirwayBill });
        this.masterAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.masterAirwayBillDropdown, "value");
      }
      if (res.hawbTypeId != null) {
        this.statusDropdown = [{ value: 6, label: "6 - Pending Customs" }, { value: 47, label: "47 - Gateway Inventory" }];
      }
      if (res.hawbTimeStamp != null) {
        const formattedDate = this.datePipe.transform(res.hawbTimeStamp, "MMM d, y, h:mm a");
        this.timeStampDropdown.push({ value: res.hawbTimeStamp, label: formattedDate });
        this.timeStampDropdown = this.cs.removeDuplicatesFromArrayList(this.timeStampDropdown, "value");
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
    this.service.searchStatus(this.searchform.getRawValue()).subscribe({
      next: (res) => {
        this.inventoryScanningTable = res;
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
      partnerHouseAirwayBill: [],
      partnerMasterAirwayBill: [],
      hawbTypeId: [],
      hawbTimeStamp: [],
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
_InventoryScanningComponent.\u0275fac = function InventoryScanningComponent_Factory(t) {
  return new (t || _InventoryScanningComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NgxSpinnerService));
};
_InventoryScanningComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _InventoryScanningComponent, selectors: [["app-inventory-scanning"]], viewQuery: function InventoryScanningComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c02, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 55, vars: 40, consts: [["inventoryScanningReport", ""], ["inventoryScanningTableTag", ""], ["menu", "matMenu"], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem"], ["type", "button", "disabled", "", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-between", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], [1, "d-flex", "justify-content-end", "align-items-center"], ["iconPosition", "right"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerMasterAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerHouseAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "hawbTypeId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "col-3", "marginFieldNew", "borderRadius12"], ["dateFormat", "dd/mm/yy", "formControlName", "hawbTimeStamp", "appendTo", "body", "placeholder", "Select Date", 1, "w-100", "small-calendar", 3, "iconDisplay", "showIcon"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "multiple", "sortField", "createdOn", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "sortOrder", "selection", "paginator", "rows", "showCurrentPageReport"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [1, "customClass"], ["matMenuContent", ""], [2, "width", "5rem"], [1, "pl-3"], [4, "ngFor", "ngForOf"], ["pFrozenColumn", "", 2, "width", "5rem"], [1, "pl-3", 3, "disabled"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [3, "ngClass"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "onChange", "ngModelChange", "ngModel", "value"], [4, "ngIf", "ngIfElse"], [4, "ngIf"], [1, "my-2", "w-100", "pl-3"], ["mat-menu-item", ""]], template: function InventoryScanningComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 4)(1, "div", 5)(2, "p", 6);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 5);
    \u0275\u0275element(5, "img", 7);
    \u0275\u0275elementStart(6, "img", 8);
    \u0275\u0275listener("click", function InventoryScanningComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275element(7, "img", 9)(8, "img", 10);
    \u0275\u0275elementStart(9, "button", 11);
    \u0275\u0275element(10, "i", 12);
    \u0275\u0275text(11, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 13)(13, "div", 14)(14, "p-iconField", 15)(15, "p-inputIcon", 16);
    \u0275\u0275listener("click", function InventoryScanningComponent_Template_p_inputIcon_click_15_listener($event) {
      \u0275\u0275restoreView(_r1);
      const inventoryScanningReport_r2 = \u0275\u0275reference(18);
      return \u0275\u0275resetView(inventoryScanningReport_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "input", 17);
    \u0275\u0275listener("input", function InventoryScanningComponent_Template_input_input_16_listener($event) {
      \u0275\u0275restoreView(_r1);
      const inventoryScanningTableTag_r3 = \u0275\u0275reference(48);
      return \u0275\u0275resetView(inventoryScanningTableTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "p-overlayPanel", 18, 0)(19, "form", 19)(20, "div", 20)(21, "div", 21)(22, "p", 22);
    \u0275\u0275text(23, "Partner MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(24, "p-multiSelect", 23);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(25, "div", 21)(26, "p", 22);
    \u0275\u0275text(27, "Partner HAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(28, "p-multiSelect", 24);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(29, "div", 21)(30, "p", 22);
    \u0275\u0275text(31, "Action");
    \u0275\u0275elementEnd();
    \u0275\u0275element(32, "p-multiSelect", 25);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(33, "div", 26)(34, "p", 22);
    \u0275\u0275text(35, "Date");
    \u0275\u0275elementEnd();
    \u0275\u0275element(36, "p-calendar", 27);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(37, "div", 28)(38, "div")(39, "img", 29);
    \u0275\u0275listener("click", function InventoryScanningComponent_Template_img_click_39_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(40, "div")(41, "button", 30);
    \u0275\u0275listener("click", function InventoryScanningComponent_Template_button_click_41_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(42, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(43, "button", 31);
    \u0275\u0275listener("click", function InventoryScanningComponent_Template_button_click_43_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(44, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(45, "p-chips", 32);
    \u0275\u0275listener("onRemove", function InventoryScanningComponent_Template_p_chips_onRemove_45_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function InventoryScanningComponent_Template_p_chips_ngModelChange_45_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(46, "div", 33)(47, "p-table", 34, 1);
    \u0275\u0275twoWayListener("selectionChange", function InventoryScanningComponent_Template_p_table_selectionChange_47_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedInventoryScanning, $event) || (ctx.selectedInventoryScanning = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(49, InventoryScanningComponent_ng_template_49_Template, 8, 3, "ng-template", 35)(50, InventoryScanningComponent_ng_template_50_Template, 4, 6, "ng-template", 36)(51, InventoryScanningComponent_ng_template_51_Template, 4, 1, "ng-template", 37);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(52, "mat-menu", 38, 2);
    \u0275\u0275template(54, InventoryScanningComponent_ng_template_54_Template, 4, 0, "ng-template", 39);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate(ctx.pageFlow);
    \u0275\u0275advance(14);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(33, _c12));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.searchform);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(34, _c22));
    \u0275\u0275property("showClear", true)("options", ctx.masterAirwayBillDropdown)("panelStyle", \u0275\u0275pureFunction0(35, _c22));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(36, _c22));
    \u0275\u0275property("showClear", true)("options", ctx.houseAirwayBillDropdown)("panelStyle", \u0275\u0275pureFunction0(37, _c22));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(38, _c22));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(39, _c22));
    \u0275\u0275advance(4);
    \u0275\u0275property("iconDisplay", "input")("showIcon", true);
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.inventoryScanningTable)("scrollable", true)("sortOrder", -1);
    \u0275\u0275twoWayProperty("selection", ctx.selectedInventoryScanning);
    \u0275\u0275property("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1);
  }
}, dependencies: [NgClass, NgForOf, NgIf, PrimeTemplate, Table, FrozenColumn, TableHeaderCheckbox, InputIcon, IconField, InputText, Calendar, Checkbox, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenu, MatMenuItem, MatMenuContent, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n.contentBox[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  margin: 10px 10px 10px 0px;\n  flex-direction: row;\n  width: 100%;\n  justify-content: center;\n  align-items: center;\n  gap: 80px;\n  padding: 1rem 2rem 0px 2rem;\n  border-radius: 16px;\n  border: solid 1px var(--black);\n  background-color: var(--white);\n  color: var(--black);\n  transition: background-color 0.3s ease;\n}\n.contentBox[_ngcontent-%COMP%]:hover {\n  background-color: var(--black);\n  color: var(--white);\n}\n.lineBorder[_ngcontent-%COMP%] {\n  width: 8px;\n  flex-grow: 0;\n  margin: 10px 7px 10px 0;\n  border-radius: 20px;\n  background-color: var(--overcOrange);\n}\n.chip[_ngcontent-%COMP%] {\n  padding: 3px 1rem 3px 0px;\n  border-radius: 6px;\n  text-align: center;\n}\n.headerText[_ngcontent-%COMP%] {\n  flex-grow: 1;\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  padding-bottom: 3px;\n}\n.valueText[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: bold;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n}\n.spacingBottom[_ngcontent-%COMP%] {\n  padding-bottom: 0.8rem;\n}\n.hoverButton[_ngcontent-%COMP%] {\n  border: 1px solid var(--black) !important;\n  color: var(--black);\n}\n.hoverButton[_ngcontent-%COMP%]:hover {\n  background-color: var(--overcOrange);\n  border: none !important;\n  color: var(--white);\n}\n.green[_ngcontent-%COMP%] {\n  color: #00742b;\n  background-color: #8efab6;\n  font-size: 10px;\n  padding: 2px 12px !important;\n}\n.red[_ngcontent-%COMP%] {\n  color: #ef4444;\n  background-color: #ffacac;\n  font-size: 10px;\n  padding: 2px 12px !important;\n}\n.pointer-cursor[_ngcontent-%COMP%] {\n  cursor: pointer;\n}\n/*# sourceMappingURL=inventory-scanning.component.css.map */"] });
var InventoryScanningComponent = _InventoryScanningComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(InventoryScanningComponent, { className: "InventoryScanningComponent", filePath: "src\\app\\main\\reports\\inventory-scanning\\inventory-scanning.component.ts", lineNumber: 19 });
})();

// src/app/main/reports/customs-calculation-report/customs-calculation-report.component.ts
var _c03 = ["inventoryScanningReport"];
var _c13 = () => ({ width: "80vw" });
var _c23 = () => ({ "width": "100%" });
var _c32 = (a0) => ({ "selectedRow": a0 });
function CustomsCalculationReportComponent_ng_template_45_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 44);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 45);
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
function CustomsCalculationReportComponent_ng_template_45_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 46);
    \u0275\u0275listener("input", function CustomsCalculationReportComponent_ng_template_45_th_7_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const ccrTag_r3 = \u0275\u0275reference(44);
      return \u0275\u0275resetView(ccrTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const ccrTag_r3 = \u0275\u0275reference(44);
    \u0275\u0275advance();
    \u0275\u0275property("value", ccrTag_r3.filters[col_r6.field] == null ? null : ccrTag_r3.filters[col_r6.field].value);
  }
}
function CustomsCalculationReportComponent_ng_template_45_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 39);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 40);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsCalculationReportComponent_ng_template_45_th_3_Template, 3, 3, "th", 41);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 39);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 42);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, CustomsCalculationReportComponent_ng_template_45_th_7_Template, 2, 1, "th", 43);
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
function CustomsCalculationReportComponent_ng_template_46_td_4_ng_container_1_Template(rf, ctx) {
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
function CustomsCalculationReportComponent_ng_template_46_td_4_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r10 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 54);
    \u0275\u0275listener("click", function CustomsCalculationReportComponent_ng_template_46_td_4_ng_template_2_span_0_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r10);
      const rowData_r9 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r10 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r10.openEdit("Edit", rowData_r9));
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
function CustomsCalculationReportComponent_ng_template_46_td_4_ng_template_2_span_1_Template(rf, ctx) {
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
function CustomsCalculationReportComponent_ng_template_46_td_4_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, CustomsCalculationReportComponent_ng_template_46_td_4_ng_template_2_span_0_Template, 2, 1, "span", 52)(1, CustomsCalculationReportComponent_ng_template_46_td_4_ng_template_2_span_1_Template, 3, 4, "span", 53);
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r8.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r8.format == "date");
  }
}
function CustomsCalculationReportComponent_ng_template_46_td_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, CustomsCalculationReportComponent_ng_template_46_td_4_ng_container_1_Template, 2, 1, "ng-container", 51)(2, CustomsCalculationReportComponent_ng_template_46_td_4_ng_template_2_Template, 2, 2, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
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
function CustomsCalculationReportComponent_ng_template_46_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "tr");
    \u0275\u0275elementStart(1, "tr", 47)(2, "td", 48);
    \u0275\u0275element(3, "p-tableCheckbox", 49);
    \u0275\u0275elementEnd();
    \u0275\u0275template(4, CustomsCalculationReportComponent_ng_template_46_td_4_Template, 4, 4, "td", 50);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r9 = ctx.$implicit;
    const columns_r13 = ctx.columns;
    const ctx_r10 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r10.selectedConsole[0] === rowData_r9);
    \u0275\u0275advance();
    \u0275\u0275property("ngClass", \u0275\u0275pureFunction1(5, _c32, ctx_r10.isSelected(rowData_r9)));
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r9);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function CustomsCalculationReportComponent_ng_template_47_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 55);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function CustomsCalculationReportComponent_ng_template_50_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "button", 56);
    \u0275\u0275text(1, "Edit");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(2, "button", 56);
    \u0275\u0275text(3, "Confirm");
    \u0275\u0275elementEnd();
  }
}
var _CustomsCalculationReportComponent = class _CustomsCalculationReportComponent {
  constructor(messageService, cs, router, path, service, consigmentservice, dialog, datePipe, auth, fb, spin) {
    this.messageService = messageService;
    this.cs = cs;
    this.router = router;
    this.path = path;
    this.service = service;
    this.consigmentservice = consigmentservice;
    this.dialog = dialog;
    this.datePipe = datePipe;
    this.auth = auth;
    this.fb = fb;
    this.spin = spin;
    this.inventoryScanningTable = [];
    this.selectedConsole = [];
    this.cols = [];
    this.target = [];
    this.totalDeclaredValue = 0;
    this.totalCustomsValue = 0;
    this.totalDutyValue = 0;
    this.customsCaltulationTable = [];
    this.form = this.fb.group({
      actualCurrency: [],
      actualValue: [],
      addIata: [],
      addInsurance: [],
      airportOriginCode: [],
      bondedId: [],
      calculatedTotalDuty: [],
      companyName: [],
      consigneeCivilId: [],
      consigneeName: [],
      consignmentCurrency: [],
      consignmentLocalId: [],
      consignmentValue: [],
      consignmentValueLocal: [],
      consoleGroupName: [],
      consoleId: [],
      consoleName: [],
      countryOfOrigin: [],
      createdBy: [],
      createdOn: [],
      currency: [],
      customsCurrency: [],
      customsInsurance: [],
      customsKd: [],
      customsValue: [],
      dduCharge: [],
      declaredValue: [],
      deletionIndicator: [],
      description: [],
      duty: [],
      dutyPercentage: [],
      exchangeRate: [],
      exemptionBeneficiary: [],
      exemptionFor: [],
      exemptionReference: [],
      expectedDuty: [],
      finalDestination: [],
      freightCharges: [],
      freightCurrency: [],
      goodsDescription: [],
      goodsType: [],
      grossWeight: [],
      hawbTimeStamp: [],
      hawbType: [],
      hawbTypeDescription: [],
      hawbTypeId: [],
      hsCode: [],
      hubCode: [],
      iata: [],
      iataCharge: [],
      incoTerms: [],
      invoiceDate: [],
      invoiceNumber: [],
      invoiceSupplierName: [],
      invoiceType: [],
      isConsolidatedShipment: [],
      isExempted: [],
      isPendingShipment: [],
      isSplitBillOfLading: [],
      landedQuantity: [],
      languageDescription: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]],
      manifestedGrossWeight: [],
      manifestedQuantity: [],
      netWeight: [],
      noOfPackageMawb: [],
      noOfPieceHawb: [],
      noOfPieces: [],
      notifyParty: [],
      partnerHouseAirwayBill: [],
      partnerId: [],
      partnerMasterAirwayBill: [],
      partnerName: [],
      partnerType: [],
      paymentType: [],
      pieceId: [],
      pieceTimeStamp: [],
      pieceType: [],
      pieceTypeDescription: [],
      pieceTypeId: [],
      primaryDo: [],
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
      remarks: [],
      secondaryDo: [],
      serviceTypeId: [],
      serviceTypeName: [],
      shipmentBagId: [],
      shipperId: [],
      shipperName: [],
      specialApprovalCharge: [],
      specialApprovalValue: [],
      subProductId: [],
      subProductName: [],
      tareWeight: [],
      totalQuantity: [],
      updatedBy: [],
      updatedOn: [],
      volume: [],
      statusText: [],
      statusId: []
    });
    this.fieldDisplayNames = {
      partnerHouseAirwayBill: "Partner HAWB",
      partnerMasterAirwayBill: "Partner MAWB",
      hawbTypeId: "Action",
      hawbTimeStamp: "Date Time"
    };
    this.houseAirwayBillDropdown = [];
    this.masterAirwayBillDropdown = [];
    this.statusDropdown = [];
    this.timeStampDropdown = [];
  }
  ngOnInit() {
    const link = this.router.url;
    this.activeLink = link.split("/")[3];
    const dataToSend = ["Mid-Mile", "Customs Calculation Report"];
    this.path.setData(dataToSend);
    this.pageFlow = "Customs Calculation Report";
    this.tableStyle = { "width": "170rem" };
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "partnerMasterAirwayBill", header: "Partner MAWB", format: "hyperLink" },
      { field: "noOfShipments", header: "No of Consignment" },
      { field: "shipper", header: "Shipper" },
      { field: "totalConsignmentValue", header: "Total Declared Value" },
      { field: "currency", header: "Currency" },
      { field: "totalCustomsValue", header: "Total Customs Value" },
      { field: "iata", header: "IATA Charges" },
      { field: "customsInsurance", header: "Insurance" },
      // { field: 'duty', header: 'Customs Duty %' },
      { field: "totalDutyValue", header: "Total Duty Value" }
    ];
    this.target = [];
  }
  initialCall() {
    setTimeout(() => {
      this.spin.show();
      this.service.customssearch().subscribe({
        next: (res = []) => {
          this.customsCaltulationTable = res;
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }, 1e3);
  }
  openEdit(type = "New", linedata = null) {
    if (linedata) {
      this.selectedConsole = linedata;
    }
    if (this.selectedConsole.length === 0 && type != "New") {
      this.messageService.add({ severity: "warn", summary: "Warning", key: "br", detail: "Kindly select any Row" });
    } else {
      let paramdata = this.cs.encrypt({ line: linedata == null ? this.selectedConsole[0] : linedata, pageflow: type });
      this.router.navigate(["/main/reports/customsCalculationsline/" + paramdata]);
    }
  }
  isSelected(item) {
    return this.selectedConsole.includes(item);
  }
  onChange() {
    const choosen = this.selectedConsole[this.selectedConsole.length - 1];
    this.selectedConsole.length = 0;
    this.selectedConsole.push(choosen);
  }
  downloadExcel() {
    const inventoryScanningReport = [
      { field: "partnerMasterAirwayBill", header: "Partner MAWB" },
      { field: "countHawb", header: "No of Consignment" },
      { field: "shipper", header: "Shipper" },
      { field: "totalDeclaredValue", header: "Total Declared Value" },
      { field: "currency", header: "Currency" },
      { field: "totalCustomsValue", header: "Total Customs Value" },
      { field: "iata", header: "IATA Charges" },
      { field: "customsInsurance", header: "Insurance" },
      { field: "duty", header: "Customs Duty %" },
      { field: "calculatedTotalDuty", header: "Total Duty Value" }
    ];
    const exportData = this.customsCaltulationTable.map((item) => {
      const exportItem = {};
      inventoryScanningReport.forEach((col) => {
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, "Customs Calculation Report");
  }
  getSearchDropdown() {
    this.customsCaltulationTable.forEach((res) => {
      if (res.partnerHouseAirwayBill != null) {
        this.houseAirwayBillDropdown.push({ value: res.partnerHouseAirwayBill, label: res.partnerHouseAirwayBill });
        this.houseAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.houseAirwayBillDropdown, "value");
      }
      if (res.partnerMasterAirwayBill != null) {
        this.masterAirwayBillDropdown.push({ value: res.partnerMasterAirwayBill, label: res.partnerMasterAirwayBill });
        this.masterAirwayBillDropdown = this.cs.removeDuplicatesFromArrayList(this.masterAirwayBillDropdown, "value");
      }
      if (res.hawbTypeId != null) {
        this.statusDropdown = [{ value: 6, label: "6 - Pending Customs" }, { value: 47, label: "47 - Gateway Inventory" }];
      }
      if (res.hawbTimeStamp != null) {
        const formattedDate = this.datePipe.transform(res.hawbTimeStamp, "MMM d, y, h:mm a");
        this.timeStampDropdown.push({ value: res.hawbTimeStamp, label: formattedDate });
        this.timeStampDropdown = this.cs.removeDuplicatesFromArrayList(this.timeStampDropdown, "value");
      }
    });
  }
  closeOverLay() {
    this.overlayPanel.hide();
  }
  search() {
    this.fieldsWithValue = null;
    const formValues = this.form.value;
    this.fieldsWithValue = Object.keys(formValues).filter((key) => formValues[key] !== null && formValues[key] !== void 0 && key !== "companyId" && key !== "languageId").map((key) => this.fieldDisplayNames[key] || key);
    this.spin.show();
    this.consigmentservice.searchPrealert(this.form.getRawValue()).subscribe({
      next: (res) => {
        let totalDeclaredValue = 0;
        let totalCustomsValue = 0;
        let totalDutyValue = 0;
        const groupedData = res.reduce((acc, item) => {
          if (!acc[item.partnerMasterAirwayBill]) {
            acc[item.partnerMasterAirwayBill] = __spreadProps(__spreadValues({}, item), {
              totalDeclaredValue: 0,
              totalCustomsValue: 0,
              totalDutyValue: 0
            });
          }
          acc[item.partnerMasterAirwayBill].totalDeclaredValue += parseFloat(String(item.consignmentValue || "0"));
          acc[item.partnerMasterAirwayBill].totalCustomsValue += parseFloat(String(item.customsValue || "0"));
          acc[item.partnerMasterAirwayBill].totalDutyValue += parseFloat(String(item.calculatedTotalDuty || "0"));
          acc[item.partnerMasterAirwayBill].totalDeclaredValue = Number(acc[item.partnerMasterAirwayBill].totalDeclaredValue.toFixed(3));
          acc[item.partnerMasterAirwayBill].totalCustomsValue = Number(acc[item.partnerMasterAirwayBill].totalCustomsValue.toFixed(3));
          acc[item.partnerMasterAirwayBill].totalDutyValue = Number(acc[item.partnerMasterAirwayBill].totalDutyValue.toFixed(3));
          return acc;
        }, {});
        const processedData = Object.values(groupedData);
        totalDeclaredValue = processedData.reduce((sum, item) => sum + item.totalDeclaredValue, 0);
        totalCustomsValue = processedData.reduce((sum, item) => sum + item.totalCustomsValue, 0);
        totalDutyValue = processedData.reduce((sum, item) => sum + item.totalDutyValue, 0);
        totalDeclaredValue = Number(totalDeclaredValue.toFixed(3));
        totalCustomsValue = Number(totalCustomsValue.toFixed(3));
        totalDutyValue = Number(totalCustomsValue.toFixed(3));
        this.totalDeclaredValue = totalDeclaredValue;
        this.totalCustomsValue = totalCustomsValue;
        this.totalDutyValue = totalDutyValue;
        this.totalDeclaredValue = this.totalDeclaredValue;
        this.customsCaltulationTable = this.cs.removeDuplicatesFromArrayList(processedData, "partnerMasterAirwayBill");
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
    this.form.reset();
    this.search();
  }
  chipClear(value) {
    const formControlKey = Object.keys(this.fieldDisplayNames).find((key) => this.fieldDisplayNames[key] === value.value);
    if (formControlKey) {
      this.form.get(formControlKey)?.reset();
      this.search();
    }
  }
};
_CustomsCalculationReportComponent.\u0275fac = function CustomsCalculationReportComponent_Factory(t) {
  return new (t || _CustomsCalculationReportComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(ReportService), \u0275\u0275directiveInject(ConsignmentService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(NgxSpinnerService));
};
_CustomsCalculationReportComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CustomsCalculationReportComponent, selectors: [["app-customs-calculation-report"]], viewQuery: function CustomsCalculationReportComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c03, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 51, vars: 34, consts: [["inventoryScanningReport", ""], ["ccrTag", ""], ["menu", "matMenu"], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem"], ["type", "button", "disabled", "", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-between", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], [1, "d-flex", "justify-content-end", "align-items-center"], ["iconPosition", "right"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerMasterAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerHouseAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "col-3", "marginFieldNew", "borderRadius12"], ["dateFormat", "dd/mm/yy", "formControlName", "hawbTimeStamp", "appendTo", "body", "placeholder", "Select Date", 1, "w-100", "small-calendar", 3, "iconDisplay", "showIcon"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "multiple", "sortField", "createdOn", "scrollHeight", "calc(100vh - 23rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "sortOrder", "selection", "paginator", "rows", "showCurrentPageReport", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [1, "customClass"], ["matMenuContent", ""], ["pFrozenColumn", "", 2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [3, "ngClass"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"], ["mat-menu-item", ""]], template: function CustomsCalculationReportComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 4)(1, "div", 5)(2, "p", 6);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 5);
    \u0275\u0275element(5, "img", 7);
    \u0275\u0275elementStart(6, "img", 8);
    \u0275\u0275listener("click", function CustomsCalculationReportComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275element(7, "img", 9)(8, "img", 10);
    \u0275\u0275elementStart(9, "button", 11);
    \u0275\u0275element(10, "i", 12);
    \u0275\u0275text(11, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 13)(13, "div", 14)(14, "p-iconField", 15)(15, "p-inputIcon", 16);
    \u0275\u0275listener("click", function CustomsCalculationReportComponent_Template_p_inputIcon_click_15_listener($event) {
      \u0275\u0275restoreView(_r1);
      const inventoryScanningReport_r2 = \u0275\u0275reference(18);
      return \u0275\u0275resetView(inventoryScanningReport_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "input", 17);
    \u0275\u0275listener("input", function CustomsCalculationReportComponent_Template_input_input_16_listener($event) {
      \u0275\u0275restoreView(_r1);
      const ccrTag_r3 = \u0275\u0275reference(44);
      return \u0275\u0275resetView(ccrTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(17, "p-overlayPanel", 18, 0)(19, "form", 19)(20, "div", 20)(21, "div", 21)(22, "p", 22);
    \u0275\u0275text(23, "Partner MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(24, "p-multiSelect", 23);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(25, "div", 21)(26, "p", 22);
    \u0275\u0275text(27, "Partner HAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(28, "p-multiSelect", 24);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(29, "div", 25)(30, "p", 22);
    \u0275\u0275text(31, "Date");
    \u0275\u0275elementEnd();
    \u0275\u0275element(32, "p-calendar", 26);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(33, "div", 27)(34, "div")(35, "img", 28);
    \u0275\u0275listener("click", function CustomsCalculationReportComponent_Template_img_click_35_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(36, "div")(37, "button", 29);
    \u0275\u0275listener("click", function CustomsCalculationReportComponent_Template_button_click_37_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(38, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(39, "button", 30);
    \u0275\u0275listener("click", function CustomsCalculationReportComponent_Template_button_click_39_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(40, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(41, "p-chips", 31);
    \u0275\u0275listener("onRemove", function CustomsCalculationReportComponent_Template_p_chips_onRemove_41_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function CustomsCalculationReportComponent_Template_p_chips_ngModelChange_41_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(42, "div", 32)(43, "p-table", 33, 1);
    \u0275\u0275twoWayListener("selectionChange", function CustomsCalculationReportComponent_Template_p_table_selectionChange_43_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedConsole, $event) || (ctx.selectedConsole = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(45, CustomsCalculationReportComponent_ng_template_45_Template, 8, 3, "ng-template", 34)(46, CustomsCalculationReportComponent_ng_template_46_Template, 5, 7, "ng-template", 35)(47, CustomsCalculationReportComponent_ng_template_47_Template, 4, 1, "ng-template", 36);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(48, "mat-menu", 37, 2);
    \u0275\u0275template(50, CustomsCalculationReportComponent_ng_template_50_Template, 4, 0, "ng-template", 38);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate(ctx.pageFlow);
    \u0275\u0275advance(14);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(29, _c13));
    \u0275\u0275property("dismissable", false)("styleClass", "custom-overlay-panel");
    \u0275\u0275advance(2);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(30, _c23));
    \u0275\u0275property("showClear", true)("options", ctx.masterAirwayBillDropdown)("panelStyle", \u0275\u0275pureFunction0(31, _c23));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(32, _c23));
    \u0275\u0275property("showClear", true)("options", ctx.houseAirwayBillDropdown)("panelStyle", \u0275\u0275pureFunction0(33, _c23));
    \u0275\u0275advance(4);
    \u0275\u0275property("iconDisplay", "input")("showIcon", true);
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.customsCaltulationTable)("scrollable", true)("sortOrder", -1);
    \u0275\u0275twoWayProperty("selection", ctx.selectedConsole);
    \u0275\u0275property("sortOrder", -1)("paginator", true)("rows", 100)("showCurrentPageReport", true)("tableStyle", ctx.tableStyle);
  }
}, dependencies: [NgClass, NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, FrozenColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, InputIcon, IconField, InputText, Calendar, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenu, MatMenuItem, MatMenuContent, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n.contentBox[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  margin: 10px 10px 10px 0px;\n  flex-direction: row;\n  width: 100%;\n  justify-content: center;\n  align-items: center;\n  gap: 80px;\n  padding: 1rem 2rem 0px 2rem;\n  border-radius: 16px;\n  border: solid 1px var(--black);\n  background-color: var(--white);\n  color: var(--black);\n  transition: background-color 0.3s ease;\n}\n.contentBox[_ngcontent-%COMP%]:hover {\n  background-color: var(--black);\n  color: var(--white);\n}\n.lineBorder[_ngcontent-%COMP%] {\n  width: 8px;\n  flex-grow: 0;\n  margin: 10px 7px 10px 0;\n  border-radius: 20px;\n  background-color: var(--overcOrange);\n}\n.chip[_ngcontent-%COMP%] {\n  padding: 3px 1rem 3px 0px;\n  border-radius: 6px;\n  text-align: center;\n}\n.headerText[_ngcontent-%COMP%] {\n  flex-grow: 1;\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  padding-bottom: 3px;\n}\n.valueText[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: bold;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n}\n.spacingBottom[_ngcontent-%COMP%] {\n  padding-bottom: 0.8rem;\n}\n.hoverButton[_ngcontent-%COMP%] {\n  border: 1px solid var(--black) !important;\n  color: var(--black);\n}\n.hoverButton[_ngcontent-%COMP%]:hover {\n  background-color: var(--overcOrange);\n  border: none !important;\n  color: var(--white);\n}\n.green[_ngcontent-%COMP%] {\n  color: #00742b;\n  background-color: #8efab6;\n  font-size: 10px;\n  padding: 2px 12px !important;\n}\n.red[_ngcontent-%COMP%] {\n  color: #ef4444;\n  background-color: #ffacac;\n  font-size: 10px;\n  padding: 2px 12px !important;\n}\n.pointer-cursor[_ngcontent-%COMP%] {\n  cursor: pointer;\n}\n/*# sourceMappingURL=customs-calculation-report.component.css.map */"] });
var CustomsCalculationReportComponent = _CustomsCalculationReportComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CustomsCalculationReportComponent, { className: "CustomsCalculationReportComponent", filePath: "src\\app\\main\\reports\\customs-calculation-report\\customs-calculation-report.component.ts", lineNumber: 83 });
})();

// src/app/main/reports/customs-calculation-report/customs-calculation-report-lines/customs-calculation-report-lines.component.ts
var _c04 = () => ({ "width": "100%" });
function CustomsCalculationReportLinesComponent_ng_template_14_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 25);
  }
}
function CustomsCalculationReportLinesComponent_mat_error_15_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 26)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function CustomsCalculationReportLinesComponent_ng_template_20_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 25);
  }
}
function CustomsCalculationReportLinesComponent_mat_error_21_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 26)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function CustomsCalculationReportLinesComponent_ng_template_29_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 32);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 33);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r3 = ctx.$implicit;
    \u0275\u0275property("pSortableColumn", col_r3.field);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r3.header, " ");
    \u0275\u0275advance();
    \u0275\u0275property("field", col_r3.field);
  }
}
function CustomsCalculationReportLinesComponent_ng_template_29_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 34);
    \u0275\u0275listener("input", function CustomsCalculationReportLinesComponent_ng_template_29_th_7_Template_input_input_1_listener($event) {
      const col_r5 = \u0275\u0275restoreView(_r4).$implicit;
      \u0275\u0275nextContext(2);
      const customsCalTag_r6 = \u0275\u0275reference(28);
      return \u0275\u0275resetView(customsCalTag_r6.filter($event.target == null ? null : $event.target.value, col_r5.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r5 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const customsCalTag_r6 = \u0275\u0275reference(28);
    \u0275\u0275advance();
    \u0275\u0275property("value", customsCalTag_r6.filters[col_r5.field] == null ? null : customsCalTag_r6.filters[col_r5.field].value);
  }
}
function CustomsCalculationReportLinesComponent_ng_template_29_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 27);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 28);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsCalculationReportLinesComponent_ng_template_29_th_3_Template, 3, 3, "th", 29);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 27);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 30);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, CustomsCalculationReportLinesComponent_ng_template_29_th_7_Template, 2, 1, "th", 31);
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
function CustomsCalculationReportLinesComponent_ng_template_30_td_3_ng_container_1_Template(rf, ctx) {
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
function CustomsCalculationReportLinesComponent_ng_template_30_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275text(0);
    \u0275\u0275pipe(1, "date");
  }
  if (rf & 2) {
    const rowData_r9 = \u0275\u0275nextContext(2).$implicit;
    \u0275\u0275textInterpolate1(" ", \u0275\u0275pipeBind2(1, 1, rowData_r9.createdOn, "dd-MM-yyyy HH:mm"), " ");
  }
}
function CustomsCalculationReportLinesComponent_ng_template_30_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, CustomsCalculationReportLinesComponent_ng_template_30_td_3_ng_container_1_Template, 2, 1, "ng-container", 36)(2, CustomsCalculationReportLinesComponent_ng_template_30_td_3_ng_template_2_Template, 2, 4, "ng-template", null, 1, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r8 = ctx.$implicit;
    const dateTemplate_r10 = \u0275\u0275reference(3);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r8.format !== "date")("ngIfElse", dateTemplate_r10);
  }
}
function CustomsCalculationReportLinesComponent_ng_template_30_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td", 27);
    \u0275\u0275element(2, "p-tableCheckbox", 35);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsCalculationReportLinesComponent_ng_template_30_td_3_Template, 4, 2, "td", 31);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r9 = ctx.$implicit;
    const columns_r11 = ctx.columns;
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275classProp("selectedRow", ctx_r1.selectedConsole[0] === rowData_r9);
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r9);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r11);
  }
}
function CustomsCalculationReportLinesComponent_ng_template_31_ng_container_2_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275elementStart(1, "td");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd();
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r12 = \u0275\u0275nextContext().$implicit;
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate1(" ", ctx_r1.calculateFooterTotal(col_r12.field) != 0 ? ctx_r1.calculateFooterTotal(col_r12.field) : "", " ");
  }
}
function CustomsCalculationReportLinesComponent_ng_template_31_ng_container_2_ng_container_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275element(1, "td");
    \u0275\u0275elementContainerEnd();
  }
}
function CustomsCalculationReportLinesComponent_ng_template_31_ng_container_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275template(1, CustomsCalculationReportLinesComponent_ng_template_31_ng_container_2_ng_container_1_Template, 3, 1, "ng-container", 37)(2, CustomsCalculationReportLinesComponent_ng_template_31_ng_container_2_ng_container_2_Template, 2, 0, "ng-container", 37);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r12 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r12.showFooter);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", !col_r12.showFooter);
  }
}
function CustomsCalculationReportLinesComponent_ng_template_31_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr");
    \u0275\u0275element(1, "td");
    \u0275\u0275template(2, CustomsCalculationReportLinesComponent_ng_template_31_ng_container_2_Template, 3, 2, "ng-container", 31);
    \u0275\u0275element(3, "td");
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r13 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function CustomsCalculationReportLinesComponent_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 38);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
var _CustomsCalculationReportLinesComponent = class _CustomsCalculationReportLinesComponent {
  constructor(cs, spin, route, router, path, fb, service, numberRangeService, messageService, cas, auth, dialog, datePipe, label, location) {
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
    this.dialog = dialog;
    this.datePipe = datePipe;
    this.label = label;
    this.location = location;
    this.active = 0;
    this.status = [];
    this.selectedConsole = [];
    this.form = this.fb.group({
      actualCurrency: [],
      actualValue: [],
      addIata: [],
      addInsurance: [],
      airportOriginCode: [],
      bondedId: [],
      calculatedTotalDuty: [],
      companyId: [this.auth.companyId],
      companyName: [],
      consigneeCivilId: [],
      consigneeName: [],
      consignmentCurrency: [],
      consignmentLocalId: [],
      consignmentValue: [],
      consignmentValueLocal: [],
      consoleGroupName: [],
      consoleId: [],
      consoleName: [],
      countryOfOrigin: [],
      createdBy: [],
      createdOn: [],
      currency: [],
      customsCurrency: [],
      customsInsurance: [],
      customsKd: [],
      customsValue: [],
      dduCharge: [],
      declaredValue: [],
      deletionIndicator: [],
      description: [],
      duty: [],
      dutyPercentage: [],
      exchangeRate: [],
      exemptionBeneficiary: [],
      exemptionFor: [],
      exemptionReference: [],
      expectedDuty: [],
      finalDestination: [],
      freightCharges: [],
      freightCurrency: [],
      goodsDescription: [],
      goodsType: [],
      grossWeight: [],
      hawbTimeStamp: [],
      hawbType: [],
      hawbTypeDescription: [],
      hawbTypeId: [],
      hsCode: [],
      hubCode: [],
      iata: [],
      iataCharge: [],
      incoTerms: [],
      invoiceDate: [],
      invoiceNumber: [],
      invoiceSupplierName: [],
      invoiceType: [],
      isConsolidatedShipment: [],
      isExempted: [],
      isPendingShipment: [],
      isSplitBillOfLading: [],
      landedQuantity: [],
      languageDescription: [],
      languageId: [this.auth.languageId],
      manifestedGrossWeight: [],
      manifestedQuantity: [],
      netWeight: [],
      noOfPackageMawb: [],
      noOfPieceHawb: [],
      noOfPieces: [],
      notifyParty: [],
      partnerHouseAirwayBill: [],
      partnerId: [],
      partnerMasterAirwayBill: [],
      partnerName: [],
      partnerType: [],
      paymentType: [],
      pieceId: [],
      pieceTimeStamp: [],
      pieceType: [],
      pieceTypeDescription: [],
      pieceTypeId: [],
      primaryDo: [],
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
      remarks: [],
      secondaryDo: [],
      serviceTypeId: [],
      serviceTypeName: [],
      shipmentBagId: [],
      shipperId: [],
      shipperName: [],
      specialApprovalCharge: [],
      specialApprovalValue: [],
      subProductId: [],
      subProductName: [],
      tareWeight: [],
      totalQuantity: [],
      updatedBy: [],
      updatedOn: [],
      volume: [],
      statusText: [],
      statusId: []
    });
    this.submitted = false;
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.cols = [];
    this.target = [];
    this.languageIdList = [];
    this.companyIdList = [];
    this.customsCalculationArray = [];
    this.consoleManifest = [];
    this.invoices = [];
    this.invoiceItems = [];
    this.panelOpenState = false;
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
  onChange() {
    const choosen = this.selectedConsole[this.selectedConsole.length - 1];
    this.selectedConsole.length = 0;
    this.selectedConsole.push(choosen);
  }
  ngOnInit() {
    let code = this.route.snapshot.params["code"];
    this.pageToken = this.cs.decrypt(code);
    this.dropdownlist();
    this.pageFlow = "Customs Calculation Report";
    const dataToSend = ["Mid-Mile", "Customs Calculation Report"];
    this.path.setData(dataToSend);
    this.consolidatedReportTableHeader();
    this.tableStyle = { "width": "290rem" };
    this.form.controls.languageId.disable();
    this.form.controls.companyId.disable();
    this.form.controls.partnerMasterAirwayBill.disable();
    this.form.controls.statusText.disable();
    if (this.pageToken.pageflow != "New") {
      this.fill(this.pageToken.line);
      this.form.controls.subProductId.disable();
      this.form.controls.updatedBy.disable();
      this.form.controls.createdBy.disable();
      this.form.controls.updatedOn.disable();
      this.form.controls.createdOn.disable();
      this.form.controls.statusId.disable();
    }
  }
  consolidatedReportTableHeader() {
    this.cols = [
      { field: "partnerHouseAirwayBill", header: "Partner HAWB", showFooter: false },
      { field: "houseAirwayBill", header: "Consignment", showFooter: false },
      { field: "incoTerm", header: "Inco Terms", showFooter: false },
      { field: "createdOn", header: "Date", format: "date", showFooter: false },
      { field: "shipperName", header: "Shipper/Customer", showFooter: false },
      // { field: 'createdOn', header: 'From ', format: 'date', showFooter: false },
      // { field: 'createdOn', header: 'To', format: 'date', showFooter: false },
      { field: "consignmentValue", header: "Declared Value", showFooter: false },
      { field: "currency", header: "Currency", showFooter: false },
      // { field: 'exchangeRate', header: 'Exchange Rate', showFooter: false },
      { field: "iata", header: "IATA Charges", showFooter: false },
      { field: "customsInsurance", header: "Insurance", showFooter: false },
      { field: "consignmentValueLocal", header: "Customs Value KD", showFooter: false },
      { field: "addIata", header: "Adding IATA", showFooter: false },
      { field: "addInsurance", header: "Adding Insurance", showFooter: false },
      { field: "customsValue", header: "Final Customs value", showFooter: false },
      { field: "duty", header: "Customs Duty > 100", showFooter: false },
      { field: "calculatedTotalDuty", header: "Total Duty", showFooter: true }
    ];
    this.target = [];
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.language.url,
      this.cas.dropdownlist.setup.company.url
    ]).subscribe({
      next: (results) => {
        this.languageIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.language.key);
        this.companyIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.company.key);
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  addValues() {
  }
  removeItem(index) {
    this.customsCalculationArray.splice(index, 1);
  }
  fill(line) {
    this.form.patchValue(line);
    setTimeout(() => {
      this.spin.show();
      let obj = {};
      obj.languageId = [this.auth.languageId];
      obj.companyId = [this.auth.companyId];
      obj.partnerMasterAirwayBill = [line.partnerMasterAirwayBill];
      this.service.search(obj).subscribe({
        next: (res) => {
          this.customsCalculationArray = res;
          this.customsCalculationArray = this.customsCalculationArray.map((item) => __spreadProps(__spreadValues({}, item), {
            duty: item.duty != null ? item.duty : 0,
            consignmentValue: this.formatNumber(item.consignmentValue),
            consignmentValueLocal: this.formatNumber(item.consignmentValueLocal),
            addIata: this.formatNumber(item.addIata),
            addInsurance: this.formatNumber(item.addInsurance),
            customsValue: this.formatNumber(item.customsValue),
            calculatedTotalDuty: this.formatNumber(item.calculatedTotalDuty)
          }));
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }, 500);
  }
  //format the decimal of values directly in result to 3 digits
  formatNumber(value) {
    const num = Number(value);
    return isNaN(num) ? 0 : parseFloat(num.toFixed(3));
  }
  calculateFooterTotal(field) {
    let total = 0;
    this.customsCalculationArray.forEach((item) => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(3));
  }
  downloadExcel() {
    const customsCalculationReport = [
      { field: "partnerHouseAirwayBill", header: "Partner HAWB", showFooter: false },
      { field: "houseAirwayBill", header: "Consignment", showFooter: false },
      { field: "incoTerm", header: "Inco Terms", showFooter: false },
      { field: "createdOn", header: "Date", format: "date", showFooter: false },
      { field: "shipperName", header: "Shipper/Customer", showFooter: false },
      // { field: 'createdOn', header: 'From ', format: 'date', showFooter: false },
      // { field: 'createdOn', header: 'To', format: 'date', showFooter: false },
      { field: "consignmentValue", header: "Declared Value", showFooter: false },
      { field: "currency", header: "Currency", showFooter: false },
      // { field: 'exchangeRate', header: 'Exchange Rate', showFooter: false },
      { field: "iata", header: "IATA Charges", showFooter: false },
      { field: "customsInsurance", header: "Insurance", showFooter: false },
      { field: "consignmentValueLocal", header: "Customs Value KD", showFooter: false },
      { field: "addIata", header: "Adding IATA", showFooter: false },
      { field: "addInsurance", header: "Adding Insurance", showFooter: false },
      { field: "customsValue", header: "Final Customs value", showFooter: false },
      { field: "duty", header: "Customs Duty > 100", showFooter: false },
      { field: "calculatedTotalDuty", header: "Total Duty", showFooter: true }
    ];
    const exportData = this.customsCalculationArray.map((item) => {
      const exportItem = {};
      customsCalculationReport.forEach((col) => {
        exportItem[col.header] = item[col.field];
      });
      return exportItem;
    });
    this.cs.exportAsExcel(exportData, "Customs Calculation Report");
  }
  back() {
    this.location.back();
  }
};
_CustomsCalculationReportLinesComponent.\u0275fac = function CustomsCalculationReportLinesComponent_Factory(t) {
  return new (t || _CustomsCalculationReportLinesComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(ConsoleService), \u0275\u0275directiveInject(NumberrangeService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(ConsignmentLabelComponent), \u0275\u0275directiveInject(Location));
};
_CustomsCalculationReportLinesComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CustomsCalculationReportLinesComponent, selectors: [["app-customs-calculation-report-lines"]], decls: 36, vars: 30, consts: [["customsCalTag", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], [1, "mx-auto", "mt-4"], [3, "formGroup"], [1, "d-flex-row"], [1, "row", "scrollNew"], [1, "col-3", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["formControlName", "languageId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["class", "text-danger", 4, "ngIf"], ["formControlName", "companyId", "appendTo", "body", 3, "showClear", "options", "disabled", "panelStyle"], ["maxlength", "50", "pInputText", "", "appTrim", "", "formControlName", "partnerMasterAirwayBill", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], [1, "tableProperties"], ["selectionMode", "multiple", "sortField", "createdOn", "scrollHeight", "calc(100vh - 23rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "sortOrder", "selection", "paginator", "rows", "showCurrentPageReport", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "footer"], ["pTemplate", "emptymessage"], [1, "d-flex", "mt-1", "justify-content-end", 2, "position", "absolute", "right", "50%", "bottom", "7%"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], [1, "text-danger"], ["pFrozenColumn", "", 2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [1, "pl-3", 3, "value"], [4, "ngIf", "ngIfElse"], [4, "ngIf"], [1, "my-2", "w-100", "pl-3"]], template: function CustomsCalculationReportLinesComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 2)(1, "div", 3)(2, "p", 4);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 3)(5, "img", 5);
    \u0275\u0275listener("click", function CustomsCalculationReportLinesComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(6, "div", 6)(7, "form", 7)(8, "div", 8)(9, "div", 9)(10, "div", 10)(11, "p", 11);
    \u0275\u0275text(12, "Language");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(13, "p-dropdown", 12);
    \u0275\u0275template(14, CustomsCalculationReportLinesComponent_ng_template_14_Template, 1, 0, "ng-template", 13);
    \u0275\u0275elementEnd();
    \u0275\u0275template(15, CustomsCalculationReportLinesComponent_mat_error_15_Template, 3, 1, "mat-error", 14);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "div", 10)(17, "p", 11);
    \u0275\u0275text(18, "Company");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(19, "p-dropdown", 15);
    \u0275\u0275template(20, CustomsCalculationReportLinesComponent_ng_template_20_Template, 1, 0, "ng-template", 13);
    \u0275\u0275elementEnd();
    \u0275\u0275template(21, CustomsCalculationReportLinesComponent_mat_error_21_Template, 3, 1, "mat-error", 14);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(22, "div", 10)(23, "p", 11);
    \u0275\u0275text(24, "Partner MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(25, "input", 16);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(26, "div", 17)(27, "p-table", 18, 0);
    \u0275\u0275twoWayListener("selectionChange", function CustomsCalculationReportLinesComponent_Template_p_table_selectionChange_27_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedConsole, $event) || (ctx.selectedConsole = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(29, CustomsCalculationReportLinesComponent_ng_template_29_Template, 8, 3, "ng-template", 19)(30, CustomsCalculationReportLinesComponent_ng_template_30_Template, 4, 4, "ng-template", 20)(31, CustomsCalculationReportLinesComponent_ng_template_31_Template, 4, 1, "ng-template", 21)(32, CustomsCalculationReportLinesComponent_ng_template_32_Template, 4, 1, "ng-template", 22);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(33, "div", 23)(34, "button", 24);
    \u0275\u0275listener("click", function CustomsCalculationReportLinesComponent_Template_button_click_34_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.back());
    });
    \u0275\u0275text(35, "Cancel");
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate(ctx.pageFlow);
    \u0275\u0275advance(4);
    \u0275\u0275property("formGroup", ctx.form);
    \u0275\u0275advance(6);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(26, _c04));
    \u0275\u0275property("showClear", true)("options", ctx.languageIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(27, _c04));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandling("languageId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(28, _c04));
    \u0275\u0275property("showClear", true)("options", ctx.companyIdList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(29, _c04));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.errorHandling("companyId"));
    \u0275\u0275advance(6);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.customsCalculationArray)("scrollable", true)("sortOrder", -1);
    \u0275\u0275twoWayProperty("selection", ctx.selectedConsole);
    \u0275\u0275property("sortOrder", -1)("paginator", true)("rows", 100)("showCurrentPageReport", true)("tableStyle", ctx.tableStyle);
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, FrozenColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, Dropdown, InputText, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, MatError, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.img-icon[_ngcontent-%COMP%] {\n  gap: 2rem;\n}\n/*# sourceMappingURL=customs-calculation-report-lines.component.css.map */"] });
var CustomsCalculationReportLinesComponent = _CustomsCalculationReportLinesComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CustomsCalculationReportLinesComponent, { className: "CustomsCalculationReportLinesComponent", filePath: "src\\app\\main\\reports\\customs-calculation-report\\customs-calculation-report-lines\\customs-calculation-report-lines.component.ts", lineNumber: 28 });
})();

// src/app/main/reports/finance/customs-invoice/customs-invoice.component.ts
var _c05 = ["myStartCalendar"];
var _c14 = () => ({ "width": "100%" });
var _c24 = () => ({ "width": "220rem" });
function CustomsInvoiceComponent_mat_error_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 27)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function CustomsInvoiceComponent_ng_template_31_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 33);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 34);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r3 = ctx.$implicit;
    \u0275\u0275property("pSortableColumn", col_r3.field);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r3.header, " ");
    \u0275\u0275advance();
    \u0275\u0275property("field", col_r3.field);
  }
}
function CustomsInvoiceComponent_ng_template_31_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 35);
    \u0275\u0275listener("input", function CustomsInvoiceComponent_ng_template_31_th_7_Template_input_input_1_listener($event) {
      const col_r5 = \u0275\u0275restoreView(_r4).$implicit;
      \u0275\u0275nextContext(2);
      const expenseSheetTag_r6 = \u0275\u0275reference(30);
      return \u0275\u0275resetView(expenseSheetTag_r6.filter($event.target == null ? null : $event.target.value, col_r5.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r5 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const expenseSheetTag_r6 = \u0275\u0275reference(30);
    \u0275\u0275advance();
    \u0275\u0275property("value", expenseSheetTag_r6.filters[col_r5.field] == null ? null : expenseSheetTag_r6.filters[col_r5.field].value);
  }
}
function CustomsInvoiceComponent_ng_template_31_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 28);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 29);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsInvoiceComponent_ng_template_31_th_3_Template, 3, 3, "th", 30);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 28);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 31);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, CustomsInvoiceComponent_ng_template_31_th_7_Template, 2, 1, "th", 32);
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
function CustomsInvoiceComponent_ng_template_32_td_3_ng_container_1_Template(rf, ctx) {
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
function CustomsInvoiceComponent_ng_template_32_td_3_ng_template_2_ng_container_0_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r9 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r9[col_r8.field] != null ? rowData_r9[col_r8.field].toFixed(3) : "", " ");
  }
}
function CustomsInvoiceComponent_ng_template_32_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, CustomsInvoiceComponent_ng_template_32_td_3_ng_template_2_ng_container_0_Template, 2, 1, "ng-container", 40);
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r8.format == "Input");
  }
}
function CustomsInvoiceComponent_ng_template_32_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, CustomsInvoiceComponent_ng_template_32_td_3_ng_container_1_Template, 2, 1, "ng-container", 39)(2, CustomsInvoiceComponent_ng_template_32_td_3_ng_template_2_Template, 1, 1, "ng-template", null, 2, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r8 = ctx.$implicit;
    const dateTemplate_r10 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r8.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r8.format !== "Input")("ngIfElse", dateTemplate_r10);
  }
}
function CustomsInvoiceComponent_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td", 36);
    \u0275\u0275element(2, "p-tableCheckbox", 37);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsInvoiceComponent_ng_template_32_td_3_Template, 4, 4, "td", 38);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r9 = ctx.$implicit;
    const columns_r11 = ctx.columns;
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r9);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r11);
  }
}
function CustomsInvoiceComponent_ng_template_33_ng_container_2_td_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r12 = \u0275\u0275nextContext().$implicit;
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(ctx_r1.calculateFooterTotal(col_r12.field));
  }
}
function CustomsInvoiceComponent_ng_template_33_ng_container_2_td_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "td");
  }
}
function CustomsInvoiceComponent_ng_template_33_ng_container_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275template(1, CustomsInvoiceComponent_ng_template_33_ng_container_2_td_1_Template, 2, 1, "td", 40)(2, CustomsInvoiceComponent_ng_template_33_ng_container_2_td_2_Template, 1, 0, "td", 40);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r12 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r12.showFooter);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", !col_r12.showFooter);
  }
}
function CustomsInvoiceComponent_ng_template_33_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr");
    \u0275\u0275element(1, "td");
    \u0275\u0275template(2, CustomsInvoiceComponent_ng_template_33_ng_container_2_Template, 3, 2, "ng-container", 32);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r13 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function CustomsInvoiceComponent_ng_template_34_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 41);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
var _CustomsInvoiceComponent = class _CustomsInvoiceComponent {
  constructor(cs, spin, route, router, path, fb, datePipe, service, messageService, cas, auth, dialog, PrealertService2) {
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.path = path;
    this.fb = fb;
    this.datePipe = datePipe;
    this.service = service;
    this.messageService = messageService;
    this.cas = cas;
    this.auth = auth;
    this.dialog = dialog;
    this.PrealertService = PrealertService2;
    this.expenseSheetTable = [];
    this.active = 0;
    this.status = [];
    this.selectedInvoice = [];
    this.invoiceTable = [];
    this.submitted = false;
    this.searchForm = this.fb.group({
      fromDate: [""],
      partnerId: [, Validators.required],
      partnerMasterAirwayBill: [],
      toDate: [""],
      fullDate: []
    });
    this.cols = [];
    this.target = [];
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.customerList = [];
    this.mawbList = [];
    this.status = [
      { value: "2", label: "Updated" },
      { value: "1", label: "Created" }
    ];
  }
  onChange() {
    const choosen = this.selectedInvoice[this.selectedInvoice.length - 1];
    this.selectedInvoice.length = 0;
    this.selectedInvoice.push(choosen);
  }
  ngOnInit() {
    this.dropdownlist();
    const dataToSend = ["Finance", "Reports", "Customs Fees"];
    this.path.setData(dataToSend);
    this.callTableHeader();
  }
  callTableHeader() {
    this.cols = [
      { field: "partnerMasterAirwayBill", header: "Manifest", showFooter: false },
      { field: "partnerHouseAirwayBill", header: "Partner HAWB", showFooter: false },
      { field: "consigneePhoneNo", header: "Consignee Phone", showFooter: false },
      { field: "addDestinationDetails", header: "Consignee Address", showFooter: false },
      { field: "description", header: "Description", showFooter: false },
      { field: "hsCode", header: "HS Code", showFooter: false },
      { field: "noOfPieces", header: "No Of Piece", showFooter: false },
      { field: "totalWeight", header: "Weight", showFooter: false },
      { field: "incoTerm", header: "Inco Terms", showFooter: false },
      { field: "consignmentValue", header: "Value", showFooter: false },
      { field: "currency", header: "Currency", showFooter: false },
      { field: "customsValue", header: "ValueKd", format: "Input", showFooter: true },
      { field: "clearanceCharge", header: "Clearance Charge", format: "Input", showFooter: true },
      { field: "handlingFees", header: "Handling Fee", format: "Input", showFooter: true },
      { field: "customDuty", header: "Custom Duty", format: "Input", showFooter: true },
      { field: "approvals", header: "Approvals", format: "Input", showFooter: true },
      { field: "specialApprovals", header: "Special Approval", format: "Input", showFooter: true },
      { field: "totalValueShipment", header: "Value per Shipment", format: "Input", showFooter: true }
    ];
    this.target = [];
  }
  errorHandling(control, error = "required") {
    const controlInstance = this.searchForm.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError("required")) {
      return " Field should not be blank";
    }
    return this.email.hasError("email") ? "Not a valid email" : "";
  }
  reset() {
    this.searchForm.reset();
  }
  dropdownlist() {
    this.spin.show();
    let obj = {};
    obj.companyId = [this.auth.companyId];
    this.PrealertService.search(obj).subscribe({
      next: (result) => {
        this.mawbList = this.cas.foreachlistWithoutKey(result, { key: "partnerMasterAirwayBill", value: "partnerMasterAirwayBill" });
        this.customerList = this.cas.foreachlistWithoutKey(result, { key: "partnerId", value: "partnerName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  filter(type) {
    if (this.searchForm.controls.fullDate.value != null) {
      this.searchForm.controls.fromDate.patchValue(this.cs.jsonDate(this.searchForm.controls.fullDate.value[0]) ? this.searchForm.controls.fullDate.value[0] : null);
      this.searchForm.controls.toDate.patchValue(this.cs.jsonDate(this.searchForm.controls.fullDate.value[1]) ? this.searchForm.controls.fullDate.value[1] : null);
      if (this.searchForm.controls.toDate.value == null) {
        return;
      }
    }
  }
  execute() {
    this.spin.show();
    this.submitted = true;
    if (this.searchForm.invalid) {
      this.messageService.add({ severity: "error", summary: "Error", key: "br", detail: "Please fill required fields to continue" });
      this.spin.hide();
      return;
    } else {
      this.service.executeExpenseSheet(this.searchForm.getRawValue()).subscribe({
        next: (res) => {
          if (res.length > 0) {
            this.submitted = true;
            this.expenseSheetTable = res;
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
  // To calculate total table value
  calculcateTotal(line) {
    let lines = line.getRawValue();
    let totalValue = 0;
    totalValue = Number(lines.clearanceCharge) + Number(lines.otherApproval) + Number(lines.foodApproval) + Number(lines.approvals) + Number(lines.handlingFees) + Number(lines.costPerShipment) + Number(lines.customDuty);
    let totalValue1 = totalValue.toFixed(3);
    line.controls.totalValue.patchValue(totalValue1);
  }
  downloadExcel() {
    const exportData = this.expenseSheetTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, " Customs Fees");
  }
  calculateFooterTotal(field) {
    let total = 0;
    this.expenseSheetTable.forEach((item) => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(3));
  }
};
_CustomsInvoiceComponent.\u0275fac = function CustomsInvoiceComponent_Factory(t) {
  return new (t || _CustomsInvoiceComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(ReportService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(PrealertService));
};
_CustomsInvoiceComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CustomsInvoiceComponent, selectors: [["app-customs-invoice"]], viewQuery: function CustomsInvoiceComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c05, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.startCalendar = _t.first);
  }
}, decls: 35, vars: 33, consts: [["myStartCalendar", ""], ["expenseSheetTag", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/Restart.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-check2", "pr-2"], [1, "mx-auto", "mt-4", "md-4"], [3, "formGroup"], [1, "row"], [1, "col-3", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["yearRange", "2000:2099", "dateFormat", "dd/mm/yy", "formControlName", "fullDate", "placeholder", "Choose Date", "selectionMode", "range", "inputId", "range", 3, "onSelect", "yearNavigator", "showButtonBar", "monthNavigator", "hideOnDateTimeSelect", "readonlyInput"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["placeholder", "Select", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "filter", "options", "disabled", "panelStyle"], ["class", "text-danger", 4, "ngIf"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerMasterAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex-row"], [1, "tableProperties"], ["selectionMode", "multiple", "scrollHeight", "calc(100vh - 22.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "selection", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "footer"], ["pTemplate", "emptymessage"], [1, "text-danger"], [2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], [4, "ngIf"], [1, "my-2", "w-100", "pl-3"]], template: function CustomsInvoiceComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 3)(1, "div", 4)(2, "p", 5);
    \u0275\u0275text(3, "Customs Fees");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 4)(5, "img", 6);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 7);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "button", 8);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_button_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.execute());
    });
    \u0275\u0275element(8, "i", 9);
    \u0275\u0275text(9, "Execute");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(10, "div", 10)(11, "form", 11)(12, "div", 12)(13, "div", 13)(14, "p", 14);
    \u0275\u0275text(15, "Date");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-calendar", 15, 0);
    \u0275\u0275listener("onSelect", function CustomsInvoiceComponent_Template_p_calendar_onSelect_16_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.filter("date"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(18, "div", 13)(19, "p", 16);
    \u0275\u0275text(20, "Customer");
    \u0275\u0275elementEnd();
    \u0275\u0275element(21, "p-multiSelect", 17);
    \u0275\u0275template(22, CustomsInvoiceComponent_mat_error_22_Template, 3, 1, "mat-error", 18);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(23, "div", 13)(24, "p", 14);
    \u0275\u0275text(25, "MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(26, "p-multiSelect", 19);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(27, "div", 20)(28, "div", 21)(29, "p-table", 22, 1);
    \u0275\u0275twoWayListener("selectionChange", function CustomsInvoiceComponent_Template_p_table_selectionChange_29_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedExpenseSheet, $event) || (ctx.selectedExpenseSheet = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(31, CustomsInvoiceComponent_ng_template_31_Template, 8, 3, "ng-template", 23)(32, CustomsInvoiceComponent_ng_template_32_Template, 4, 2, "ng-template", 24)(33, CustomsInvoiceComponent_ng_template_33_Template, 3, 1, "ng-template", 25)(34, CustomsInvoiceComponent_ng_template_34_Template, 4, 1, "ng-template", 26);
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(11);
    \u0275\u0275property("formGroup", ctx.searchForm);
    \u0275\u0275advance(5);
    \u0275\u0275property("yearNavigator", true)("showButtonBar", true)("monthNavigator", true)("hideOnDateTimeSelect", true)("readonlyInput", true);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(28, _c14));
    \u0275\u0275property("showClear", true)("filter", true)("options", ctx.customerList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(29, _c14));
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandling("partnerId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(30, _c14));
    \u0275\u0275property("showClear", true)("options", ctx.mawbList)("panelStyle", \u0275\u0275pureFunction0(31, _c14));
    \u0275\u0275advance(3);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.expenseSheetTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx.selectedExpenseSheet);
    \u0275\u0275property("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(32, _c24));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, InputText, Calendar, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, MultiSelect, MatError, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.img-icon[_ngcontent-%COMP%] {\n  gap: 2rem;\n}\n/*# sourceMappingURL=customs-invoice.component.css.map */"] });
var CustomsInvoiceComponent = _CustomsInvoiceComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CustomsInvoiceComponent, { className: "CustomsInvoiceComponent", filePath: "src\\app\\main\\reports\\finance\\customs-invoice\\customs-invoice.component.ts", lineNumber: 22 });
})();

// src/app/main/reports/finance/customs-costing/customs-costing.component.ts
var _c06 = ["myStartCalendar"];
var _c15 = () => ({ "width": "100%" });
var _c25 = () => ({ "width": "240rem" });
function CustomsCostingComponent_mat_error_22_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-error", 27)(1, "small");
    \u0275\u0275text(2);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx_r1.getErrorMessage());
  }
}
function CustomsCostingComponent_ng_template_31_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th", 33);
    \u0275\u0275text(1);
    \u0275\u0275element(2, "p-sortIcon", 34);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r3 = ctx.$implicit;
    \u0275\u0275property("pSortableColumn", col_r3.field);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", col_r3.header, " ");
    \u0275\u0275advance();
    \u0275\u0275property("field", col_r3.field);
  }
}
function CustomsCostingComponent_ng_template_31_th_7_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 35);
    \u0275\u0275listener("input", function CustomsCostingComponent_ng_template_31_th_7_Template_input_input_1_listener($event) {
      const col_r5 = \u0275\u0275restoreView(_r4).$implicit;
      \u0275\u0275nextContext(2);
      const costSheetTag_r6 = \u0275\u0275reference(30);
      return \u0275\u0275resetView(costSheetTag_r6.filter($event.target == null ? null : $event.target.value, col_r5.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r5 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const costSheetTag_r6 = \u0275\u0275reference(30);
    \u0275\u0275advance();
    \u0275\u0275property("value", costSheetTag_r6.filters[col_r5.field] == null ? null : costSheetTag_r6.filters[col_r5.field].value);
  }
}
function CustomsCostingComponent_ng_template_31_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 28);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 29);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsCostingComponent_ng_template_31_th_3_Template, 3, 3, "th", 30);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "tr")(5, "th", 28);
    \u0275\u0275element(6, "p-tableHeaderCheckbox", 31);
    \u0275\u0275elementEnd();
    \u0275\u0275template(7, CustomsCostingComponent_ng_template_31_th_7_Template, 2, 1, "th", 32);
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
function CustomsCostingComponent_ng_template_32_td_3_ng_container_1_Template(rf, ctx) {
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
function CustomsCostingComponent_ng_template_32_td_3_ng_template_2_ng_container_0_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r9 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r9[col_r8.field] != null ? rowData_r9[col_r8.field].toFixed(3) : "", " ");
  }
}
function CustomsCostingComponent_ng_template_32_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, CustomsCostingComponent_ng_template_32_td_3_ng_template_2_ng_container_0_Template, 2, 1, "ng-container", 40);
  }
  if (rf & 2) {
    const col_r8 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r8.format == "Input");
  }
}
function CustomsCostingComponent_ng_template_32_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, CustomsCostingComponent_ng_template_32_td_3_ng_container_1_Template, 2, 1, "ng-container", 39)(2, CustomsCostingComponent_ng_template_32_td_3_ng_template_2_Template, 1, 1, "ng-template", null, 2, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r8 = ctx.$implicit;
    const dateTemplate_r10 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r8.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r8.format !== "Input")("ngIfElse", dateTemplate_r10);
  }
}
function CustomsCostingComponent_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td", 36);
    \u0275\u0275element(2, "p-tableCheckbox", 37);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsCostingComponent_ng_template_32_td_3_Template, 4, 4, "td", 38);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const rowData_r9 = ctx.$implicit;
    const columns_r11 = ctx.columns;
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r9);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r11);
  }
}
function CustomsCostingComponent_ng_template_33_ng_container_2_td_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r12 = \u0275\u0275nextContext().$implicit;
    const ctx_r1 = \u0275\u0275nextContext(2);
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(ctx_r1.calculateFooterTotal(col_r12.field));
  }
}
function CustomsCostingComponent_ng_template_33_ng_container_2_td_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "td");
  }
}
function CustomsCostingComponent_ng_template_33_ng_container_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275template(1, CustomsCostingComponent_ng_template_33_ng_container_2_td_1_Template, 2, 1, "td", 40)(2, CustomsCostingComponent_ng_template_33_ng_container_2_td_2_Template, 1, 0, "td", 40);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r12 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r12.showFooter);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", !col_r12.showFooter);
  }
}
function CustomsCostingComponent_ng_template_33_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr");
    \u0275\u0275element(1, "td");
    \u0275\u0275template(2, CustomsCostingComponent_ng_template_33_ng_container_2_Template, 3, 2, "ng-container", 32);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r13 = ctx.$implicit;
    \u0275\u0275advance(2);
    \u0275\u0275property("ngForOf", columns_r13);
  }
}
function CustomsCostingComponent_ng_template_34_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 41);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
var _CustomsCostingComponent = class _CustomsCostingComponent {
  constructor(cs, spin, route, router, path, fb, datePipe, service, messageService, PrealertService2, cas, auth, dialog) {
    this.cs = cs;
    this.spin = spin;
    this.route = route;
    this.router = router;
    this.path = path;
    this.fb = fb;
    this.datePipe = datePipe;
    this.service = service;
    this.messageService = messageService;
    this.PrealertService = PrealertService2;
    this.cas = cas;
    this.auth = auth;
    this.dialog = dialog;
    this.costSheetTable = [];
    this.active = 0;
    this.status = [];
    this.selectedInvoice = [];
    this.invoiceTable = [];
    this.submitted = false;
    this.searchForm = this.fb.group({
      fromDate: [,],
      partnerId: [, Validators.required],
      partnerMasterAirwayBill: [],
      toDate: [,],
      fullDate: []
    });
    this.cols = [];
    this.target = [];
    this.email = new FormControl("", [Validators.required, Validators.email]);
    this.customerList = [];
    this.mawbList = [];
    this.status = [
      { value: "2", label: "Updated" },
      { value: "1", label: "Created" }
    ];
  }
  onChange() {
    const choosen = this.selectedInvoice[this.selectedInvoice.length - 1];
    this.selectedInvoice.length = 0;
    this.selectedInvoice.push(choosen);
  }
  ngOnInit() {
    this.dropdownlist();
    const dataToSend = ["Finance", "Reports", "Customs Costing"];
    this.path.setData(dataToSend);
    this.callTableHeader();
  }
  callTableHeader() {
    this.cols = [
      { field: "partnerMasterAirwayBill", header: "Manifest", showFooter: false },
      { field: "partnerHouseAirwayBill", header: "Partner HAWB", showFooter: false },
      { field: "houseAirwayBill", header: "Consignment No", showFooter: false },
      { field: "shipper", header: "Shipper", showFooter: false },
      { field: "consigneePhoneNo", header: "Consignee Phone", showFooter: false },
      { field: "addDestinationDetails", header: "Consignee Address", showFooter: false },
      { field: "description", header: "Description", showFooter: false },
      { field: "hsCode", header: "HS Code", showFooter: false },
      { field: "noOfPieces", header: "No Of Piece", showFooter: false },
      { field: "totalWeight", header: "Weight", showFooter: false },
      { field: "incoTerm", header: "IncoTerm", showFooter: false },
      { field: "consignmentValue", header: "Value", showFooter: false },
      { field: "currency", header: "Currency", showFooter: false },
      { field: "customsValue", header: "Value KD", showFooter: false },
      { field: "nasDelivery", header: "NAS Delivery", format: "Input", showFooter: true },
      { field: "global", header: "Global", format: "Input", showFooter: true },
      { field: "handlingFork", header: "Handling Fork", format: "Input", showFooter: true },
      { field: "stampCharges", header: "Stamp Charges", format: "Input", showFooter: true },
      { field: "labours", header: "Labours", format: "Input", showFooter: true },
      { field: "otherCharges", header: "Other Charges", format: "Input", showFooter: true },
      { field: "customDuty", header: "Custom Duty", format: "Input", showFooter: true },
      { field: "approvals", header: "Approvals", format: "Input", showFooter: true },
      { field: "totalCostPerShipment", header: "Cost per Shipment", format: "Input", showFooter: true }
    ];
    this.target = [];
  }
  errorHandling(control, error = "required") {
    const controlInstance = this.searchForm.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError("required")) {
      return " Field should not be blank";
    }
    return this.email.hasError("email") ? "Not a valid email" : "";
  }
  dropdownlist() {
    this.spin.show();
    let obj = {};
    obj.companyId = [this.auth.companyId];
    this.PrealertService.search(obj).subscribe({
      next: (result) => {
        this.mawbList = this.cas.foreachlistWithoutKey(result, { key: "partnerMasterAirwayBill", value: "partnerMasterAirwayBill" });
        this.customerList = this.cas.foreachlistWithoutKey(result, { key: "partnerId", value: "partnerName" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  filter(type) {
    if (this.searchForm.controls.fullDate.value != null) {
      this.searchForm.controls.fromDate.patchValue(this.cs.jsonDate(this.searchForm.controls.fullDate.value[0]) ? this.searchForm.controls.fullDate.value[0] : null);
      this.searchForm.controls.toDate.patchValue(this.cs.jsonDate(this.searchForm.controls.fullDate.value[1]) ? this.searchForm.controls.fullDate.value[1] : null);
      if (this.searchForm.controls.toDate.value == null) {
        return;
      }
    }
  }
  execute() {
    this.spin.show();
    this.submitted = true;
    if (this.searchForm.invalid) {
      this.messageService.add({ severity: "error", summary: "Error", key: "br", detail: "Please fill required fields to continue" });
      this.spin.hide();
      return;
    } else {
      this.service.executeCostSheet(this.searchForm.getRawValue()).subscribe({
        next: (res) => {
          if (res.length > 0) {
            this.submitted = true;
            this.costSheetTable = res;
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
  // To calculate total table value
  calculcateTotal(line) {
    let lines = line.getRawValue();
    let totalValue = 0;
    totalValue = Number(lines.clearanceCharge) + Number(lines.otherApproval) + Number(lines.foodApproval) + Number(lines.approvals) + Number(lines.handlingFees) + Number(lines.costPerShipment) + Number(lines.customDuty);
    let totalValue1 = totalValue.toFixed(3);
    line.controls.totalValue.patchValue(totalValue1);
  }
  reset() {
    this.searchForm.reset();
  }
  downloadExcel() {
    const exportData = this.costSheetTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, " Customs Costing");
  }
  calculateFooterTotal(field) {
    let total = 0;
    this.costSheetTable.forEach((item) => {
      total += Number.parseFloat(item[field]) || 0;
    });
    return parseFloat(total.toFixed(3));
  }
};
_CustomsCostingComponent.\u0275fac = function CustomsCostingComponent_Factory(t) {
  return new (t || _CustomsCostingComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(ReportService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(PrealertService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(MatDialog));
};
_CustomsCostingComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CustomsCostingComponent, selectors: [["app-customs-costing"]], viewQuery: function CustomsCostingComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c06, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.startCalendar = _t.first);
  }
}, decls: 35, vars: 33, consts: [["myStartCalendar", ""], ["costSheetTag", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/Restart.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-check2", "pr-2"], [1, "mx-auto", "mt-4", "md-4"], [3, "formGroup"], [1, "row"], [1, "col-3", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["yearRange", "2000:2099", "dateFormat", "dd/mm/yy", "formControlName", "fullDate", "placeholder", "Choose Date", "selectionMode", "range", "inputId", "range", 3, "onSelect", "yearNavigator", "showButtonBar", "monthNavigator", "hideOnDateTimeSelect", "readonlyInput"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["placeholder", "Select", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "filter", "options", "disabled", "panelStyle"], ["class", "text-danger", 4, "ngIf"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerMasterAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex-row"], [1, "tableProperties"], ["selectionMode", "multiple", "scrollHeight", "calc(100vh - 22.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "selection", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "footer"], ["pTemplate", "emptymessage"], [1, "text-danger"], [2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "value"], [3, "style", 4, "ngFor", "ngForOf"], [4, "ngIf", "ngIfElse"], [4, "ngIf"], [1, "my-2", "w-100", "pl-3"]], template: function CustomsCostingComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 3)(1, "div", 4)(2, "p", 5);
    \u0275\u0275text(3, "Customs Costing");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 4)(5, "img", 6);
    \u0275\u0275listener("click", function CustomsCostingComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 7);
    \u0275\u0275listener("click", function CustomsCostingComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "button", 8);
    \u0275\u0275listener("click", function CustomsCostingComponent_Template_button_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.execute());
    });
    \u0275\u0275element(8, "i", 9);
    \u0275\u0275text(9, "Execute");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(10, "div", 10)(11, "form", 11)(12, "div", 12)(13, "div", 13)(14, "p", 14);
    \u0275\u0275text(15, "Date");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "p-calendar", 15, 0);
    \u0275\u0275listener("onSelect", function CustomsCostingComponent_Template_p_calendar_onSelect_16_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.filter("date"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(18, "div", 13)(19, "p", 16);
    \u0275\u0275text(20, "Customer");
    \u0275\u0275elementEnd();
    \u0275\u0275element(21, "p-multiSelect", 17);
    \u0275\u0275template(22, CustomsCostingComponent_mat_error_22_Template, 3, 1, "mat-error", 18);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(23, "div", 13)(24, "p", 14);
    \u0275\u0275text(25, "MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(26, "p-multiSelect", 19);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(27, "div", 20)(28, "div", 21)(29, "p-table", 22, 1);
    \u0275\u0275twoWayListener("selectionChange", function CustomsCostingComponent_Template_p_table_selectionChange_29_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedCostSheet, $event) || (ctx.selectedCostSheet = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(31, CustomsCostingComponent_ng_template_31_Template, 8, 3, "ng-template", 23)(32, CustomsCostingComponent_ng_template_32_Template, 4, 2, "ng-template", 24)(33, CustomsCostingComponent_ng_template_33_Template, 3, 1, "ng-template", 25)(34, CustomsCostingComponent_ng_template_34_Template, 4, 1, "ng-template", 26);
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(11);
    \u0275\u0275property("formGroup", ctx.searchForm);
    \u0275\u0275advance(5);
    \u0275\u0275property("yearNavigator", true)("showButtonBar", true)("monthNavigator", true)("hideOnDateTimeSelect", true)("readonlyInput", true);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(28, _c15));
    \u0275\u0275property("showClear", true)("filter", true)("options", ctx.customerList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(29, _c15));
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.errorHandling("partnerId"));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(30, _c15));
    \u0275\u0275property("showClear", true)("options", ctx.mawbList)("panelStyle", \u0275\u0275pureFunction0(31, _c15));
    \u0275\u0275advance(3);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.costSheetTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx.selectedCostSheet);
    \u0275\u0275property("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(32, _c25));
  }
}, dependencies: [NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, InputText, Calendar, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, MultiSelect, MatError, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.img-icon[_ngcontent-%COMP%] {\n  gap: 2rem;\n}\n/*# sourceMappingURL=customs-costing.component.css.map */"] });
var CustomsCostingComponent = _CustomsCostingComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CustomsCostingComponent, { className: "CustomsCostingComponent", filePath: "src\\app\\main\\reports\\finance\\customs-costing\\customs-costing.component.ts", lineNumber: 22 });
})();

// src/app/main/reports/reports-routing.module.ts
var routes = [
  { path: "consoleTracking", component: ConsoleTrackingComponent, data: { title: "Reports", module: "Console Tracking Report" } },
  { path: "inventoryScanning", component: InventoryScanningComponent, data: { title: "Reports", module: "Inventory Scan" } },
  { path: "pendingCustoms", component: InventoryScanningComponent, data: { title: "Reports", module: "Pending Customs" } },
  { path: "customsCalculations", component: CustomsCalculationReportComponent, data: { title: "Reports", module: "Customs Calculations Report" } },
  { path: "customsCalculationsline/:code", component: CustomsCalculationReportLinesComponent, data: { title: "Mid-Mile", module: "Customs Calculations Report" } },
  { path: "expenseSheet", component: CustomsInvoiceComponent, data: { title: "Reports", module: "Customs Fees" } },
  { path: "costSheet", component: CustomsCostingComponent, data: { title: "Mid-Mile", module: "Customs Costing" } }
];
var _ReportsRoutingModule = class _ReportsRoutingModule {
};
_ReportsRoutingModule.\u0275fac = function ReportsRoutingModule_Factory(t) {
  return new (t || _ReportsRoutingModule)();
};
_ReportsRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _ReportsRoutingModule });
_ReportsRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forChild(routes), RouterModule] });
var ReportsRoutingModule = _ReportsRoutingModule;

// src/app/main/reports/reports.module.ts
var _ReportsModule = class _ReportsModule {
};
_ReportsModule.\u0275fac = function ReportsModule_Factory(t) {
  return new (t || _ReportsModule)();
};
_ReportsModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _ReportsModule });
_ReportsModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [
  CommonModule,
  ReportsRoutingModule,
  SharedModule
] });
var ReportsModule = _ReportsModule;
export {
  ReportsModule
};
//# sourceMappingURL=chunk-B657DORB.js.map
