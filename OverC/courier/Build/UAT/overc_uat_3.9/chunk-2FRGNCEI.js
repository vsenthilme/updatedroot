import {
  CostingSheetComponent
} from "./chunk-ZSWBHVU4.js";
import "./chunk-FHYWOUQA.js";
import {
  InvoicepdfComponent
} from "./chunk-LMO3BHKE.js";
import {
  DeleteComponent
} from "./chunk-5SKNGDL5.js";
import {
  PrealertService
} from "./chunk-6TPRUFMS.js";
import "./chunk-ZSJAMNZP.js";
import "./chunk-S73KOQBB.js";
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
  Calendar,
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
  InputNumber,
  InputText,
  MatDialog,
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
  Table,
  TableCheckbox,
  TableHeaderCheckbox,
  TrimDirective,
  Validators,
  ɵNgNoValidate
} from "./chunk-XFYC4BWK.js";
import {
  ActivatedRoute,
  CommonModule,
  DatePipe,
  HttpClient,
  NgClass,
  NgForOf,
  NgIf,
  Router,
  RouterLink,
  RouterModule,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵattribute,
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
} from "./chunk-Z5YEPTLN.js";

// src/app/main/finance/customs-invoice/invoice.service.ts
var _InvoiceService = class _InvoiceService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Create(obj) {
    return this.http.post("/overc-midmile-service/invoiceHeader", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-midmile-service/invoiceHeader/update", obj);
  }
  Delete(obj) {
    return this.http.post("/overc-midmile-service/invoiceHeader/delete", obj);
  }
  search(obj) {
    return this.http.post("/overc-midmile-service/invoiceHeader/find", obj);
  }
  executeInvoiceReport(obj) {
    return this.http.post("/overc-midmile-service/customsClearanceInvoiceReport/find", obj);
  }
};
_InvoiceService.\u0275fac = function InvoiceService_Factory(t) {
  return new (t || _InvoiceService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_InvoiceService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _InvoiceService, factory: _InvoiceService.\u0275fac, providedIn: "root" });
var InvoiceService = _InvoiceService;

// src/app/main/finance/customs-invoice/customs-invoice.component.ts
var _c0 = ["invoice"];
var _c1 = () => ({ width: "80vw" });
var _c2 = () => ({ "width": "100%" });
var _c3 = () => ({ "width": "100rem" });
var _c4 = (a0) => ({ "selectedRow": a0 });
var _c5 = () => ({ width: "180" });
function CustomsInvoiceComponent_ng_template_40_th_3_Template(rf, ctx) {
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
function CustomsInvoiceComponent_ng_template_40_th_9_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "th")(1, "input", 43);
    \u0275\u0275listener("input", function CustomsInvoiceComponent_ng_template_40_th_9_Template_input_input_1_listener($event) {
      const col_r6 = \u0275\u0275restoreView(_r5).$implicit;
      \u0275\u0275nextContext(2);
      const invoiceTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(invoiceTag_r3.filter($event.target == null ? null : $event.target.value, col_r6.field, "contains"));
    });
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const col_r6 = ctx.$implicit;
    \u0275\u0275nextContext(2);
    const invoiceTag_r3 = \u0275\u0275reference(39);
    \u0275\u0275advance();
    \u0275\u0275property("value", invoiceTag_r3.filters[col_r6.field] == null ? null : invoiceTag_r3.filters[col_r6.field].value);
  }
}
function CustomsInvoiceComponent_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 34);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 35);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsInvoiceComponent_ng_template_40_th_3_Template, 3, 3, "th", 36);
    \u0275\u0275elementStart(4, "th", 37);
    \u0275\u0275text(5, " Actions ");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(6, "tr")(7, "th", 34);
    \u0275\u0275element(8, "p-tableHeaderCheckbox", 38);
    \u0275\u0275elementEnd();
    \u0275\u0275template(9, CustomsInvoiceComponent_ng_template_40_th_9_Template, 2, 1, "th", 39);
    \u0275\u0275elementStart(10, "th");
    \u0275\u0275element(11, "input", 40);
    \u0275\u0275elementEnd()();
  }
  if (rf & 2) {
    const columns_r7 = ctx.$implicit;
    \u0275\u0275advance(3);
    \u0275\u0275property("ngForOf", columns_r7);
    \u0275\u0275advance(5);
    \u0275\u0275property("disabled", true);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r7);
  }
}
function CustomsInvoiceComponent_ng_template_41_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275text(1);
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r9 = \u0275\u0275nextContext().$implicit;
    const rowData_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate1(" ", rowData_r10[col_r9.field], " ");
  }
}
function CustomsInvoiceComponent_ng_template_41_td_3_ng_template_2_span_0_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "span", 57);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_ng_template_41_td_3_ng_template_2_span_0_Template_span_click_0_listener() {
      \u0275\u0275restoreView(_r11);
      const rowData_r10 = \u0275\u0275nextContext(3).$implicit;
      const ctx_r11 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r11.openCrud("Edit", rowData_r10));
    });
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r9 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(rowData_r10[col_r9.field]);
  }
}
function CustomsInvoiceComponent_ng_template_41_td_3_ng_template_2_span_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span");
    \u0275\u0275text(1);
    \u0275\u0275pipe(2, "date");
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r9 = \u0275\u0275nextContext(2).$implicit;
    const rowData_r10 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(\u0275\u0275pipeBind2(2, 1, rowData_r10[col_r9.field], "dd-MM-yyyy HH:mm"));
  }
}
function CustomsInvoiceComponent_ng_template_41_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, CustomsInvoiceComponent_ng_template_41_td_3_ng_template_2_span_0_Template, 2, 1, "span", 55)(1, CustomsInvoiceComponent_ng_template_41_td_3_ng_template_2_span_1_Template, 3, 4, "span", 56);
  }
  if (rf & 2) {
    const col_r9 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("ngIf", col_r9.format == "hyperLink");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r9.format == "date");
  }
}
function CustomsInvoiceComponent_ng_template_41_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, CustomsInvoiceComponent_ng_template_41_td_3_ng_container_1_Template, 2, 1, "ng-container", 54)(2, CustomsInvoiceComponent_ng_template_41_td_3_ng_template_2_Template, 2, 2, "ng-template", null, 3, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r9 = ctx.$implicit;
    const dateTemplate_r13 = \u0275\u0275reference(3);
    \u0275\u0275styleMap(col_r9.style);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r9.format !== "date" && col_r9.format !== "hyperLink")("ngIfElse", dateTemplate_r13);
  }
}
function CustomsInvoiceComponent_ng_template_41_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "tr", 44)(1, "td", 45);
    \u0275\u0275element(2, "p-tableCheckbox", 46);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsInvoiceComponent_ng_template_41_td_3_Template, 4, 4, "td", 47);
    \u0275\u0275elementStart(4, "td", 48)(5, "img", 49);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_ng_template_41_Template_img_click_5_listener($event) {
      \u0275\u0275restoreView(_r8);
      const op_r14 = \u0275\u0275reference(7);
      return \u0275\u0275resetView(op_r14.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "p-overlayPanel", null, 2)(8, "div", 50)(9, "button", 51);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_ng_template_41_Template_button_click_9_listener() {
      const rowData_r10 = \u0275\u0275restoreView(_r8).$implicit;
      const op_r14 = \u0275\u0275reference(7);
      const ctx_r11 = \u0275\u0275nextContext();
      ctx_r11.download(rowData_r10, "download");
      return \u0275\u0275resetView(op_r14.hide());
    });
    \u0275\u0275element(10, "img", 52);
    \u0275\u0275text(11, "Download");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(12, "button", 51);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_ng_template_41_Template_button_click_12_listener() {
      const rowData_r10 = \u0275\u0275restoreView(_r8).$implicit;
      const op_r14 = \u0275\u0275reference(7);
      const ctx_r11 = \u0275\u0275nextContext();
      ctx_r11.download(rowData_r10, "download");
      return \u0275\u0275resetView(op_r14.hide());
    });
    \u0275\u0275element(13, "img", 53);
    \u0275\u0275text(14, "Report");
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    const rowData_r10 = ctx.$implicit;
    const columns_r15 = ctx.columns;
    const ctx_r11 = \u0275\u0275nextContext();
    \u0275\u0275property("ngClass", \u0275\u0275pureFunction1(5, _c4, ctx_r11.isSelected(rowData_r10)));
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r10);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r15);
    \u0275\u0275advance(3);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(7, _c5));
  }
}
function CustomsInvoiceComponent_ng_template_42_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 58);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
var _CustomsInvoiceComponent = class _CustomsInvoiceComponent {
  constructor(messageService, cs, router, path, service, dialog, datePipe, fb, auth, spin, invoice) {
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
    this.invoice = invoice;
    this.invoiceTable = [];
    this.cols = [];
    this.target = [];
    this.selectedInvoice = [];
    this.searchform = this.fb.group({
      invoiceNo: [],
      // statusId: [],
      companyId: [[this.auth.companyId]],
      languageId: [[this.auth.languageId]]
    });
    this.fieldDisplayNames = {
      invoiceNo: "Invoice",
      statusId: "Status"
    };
    this.languageDropdown = [];
    this.companyDropdown = [];
    this.invoiceDropdown = [];
    this.statusDropdown = [];
  }
  ngOnInit() {
    const dataToSend = ["Finance", "Customs Invoice"];
    this.path.setData(dataToSend);
    this.callTableHeader();
    this.initialCall();
  }
  callTableHeader() {
    this.cols = [
      { field: "companyId", header: "Company" },
      { field: "invoiceNo", header: "Invoice No", format: "hyperLink" },
      { field: "partnerName", header: "Partner Name" },
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
          this.invoiceTable = res;
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
    const choosen = this.selectedInvoice[this.selectedInvoice.length - 1];
    this.selectedInvoice.length = 0;
    this.selectedInvoice.push(choosen);
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
        this.deleterecord(this.selectedInvoice[0]);
      }
    });
  }
  openCrud(type = "New", linedata = null) {
    if (linedata) {
      this.selectedInvoice = linedata;
    }
    if (this.selectedInvoice.length === 0 && type != "New") {
      this.messageService.add({
        severity: "warn",
        summary: "Warning",
        key: "br",
        detail: "Kindly select any row"
      });
    } else {
      let paramdata = this.cs.encrypt({
        line: linedata == null ? this.selectedInvoice[0] : linedata,
        pageflow: type
      });
      this.router.navigate(["/main/finance/invoice-new/" + paramdata]);
    }
  }
  deleteDialog() {
    if (this.selectedInvoice.length === 0) {
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
        line: this.selectedInvoice,
        module: "Customs Invoice",
        body: "This action cannot be undone. All values associated with this field will be lost."
      }
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleterecord(this.selectedInvoice);
      }
    });
  }
  deleterecord(lines) {
    this.spin.show();
    this.service.Delete(lines).subscribe({
      next: (res) => {
        this.messageService.add({ severity: "success", summary: "Deleted", key: "br", detail: "Lines deleted successfully" });
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
    const exportData = this.invoiceTable.map((item) => {
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
    this.cs.exportAsExcel(exportData, "Customs Invoice");
  }
  getSearchDropdown() {
    this.invoiceTable.forEach((res) => {
      if (res.languageId != null) {
        this.languageDropdown.push({ value: res.languageId, label: res.languageDescription });
        this.languageDropdown = this.cs.removeDuplicatesFromArrayList(this.languageDropdown, "value");
      }
      if (res.companyId != null) {
        this.companyDropdown.push({ value: res.companyId, label: res.companyName });
        this.companyDropdown = this.cs.removeDuplicatesFromArrayList(this.companyDropdown, "value");
      }
      if (res.invoiceNo != null) {
        this.invoiceDropdown.push({ value: res.invoiceNo, label: res.invoiceNo });
        this.invoiceDropdown = this.cs.removeDuplicatesFromArrayList(this.invoiceDropdown, "value");
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
        this.invoiceTable = res;
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
      invoiceNo: [],
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
  download(line, type) {
    this.invoice.invoiceDownload(line);
  }
  download1(line, type) {
    this.spin.show();
    let obj = {};
    obj.languageId = [this.auth.languageId];
    obj.companyId = [this.auth.companyId];
    obj.invoo = [line.houseAirwayBill];
    this.service.search(obj).subscribe({
      next: (result) => {
        if (result.length > 0) {
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
  isSelected(item) {
    return this.selectedInvoice.includes(item);
  }
};
_CustomsInvoiceComponent.\u0275fac = function CustomsInvoiceComponent_Factory(t) {
  return new (t || _CustomsInvoiceComponent)(\u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(InvoiceService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(DatePipe), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(InvoicepdfComponent));
};
_CustomsInvoiceComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CustomsInvoiceComponent, selectors: [["app-customs-invoicepdf"]], viewQuery: function CustomsInvoiceComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c0, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.overlayPanel = _t.first);
  }
}, decls: 43, vars: 30, consts: [["invoice", ""], ["invoiceTag", ""], ["op", ""], ["dateTemplate", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", "src", "./assets/common/edit.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/download.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/deletetool.png", "alt", "", "srcset", "", 2, "margin", "0 17px", "height", "1.5rem", 3, "click"], ["type", "button", "src", "./assets/common/settings.png", "alt", "", "srcset", "", 2, "margin-left", "19px", "margin-right", "28px", "height", "1.5rem"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click"], [1, "bi", "bi-plus-square", "pr-2"], [1, "d-flex", "justify-content-start", "align-items-center", 2, "margin-bottom", "1.1rem !important", "margin-top", "0.8rem"], ["iconPosition", "right", 1, "mr-2"], ["type", "button", "styleClass", "pi pi-filter", 3, "click"], ["type", "text", "id", "toolInput1", "pInputText", "", "appTrim", "", "placeholder", "Advance filter", 3, "input"], ["appendTo", "body", 3, "dismissable", "styleClass"], [2, "padding", "2rem", 3, "formGroup"], [1, "row"], [1, "col-3", "mb-3", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["placeholder", "Select", "filter", "true", "formControlName", "invoiceNo", "appendTo", "body", 3, "showClear", "panelStyle"], ["placeholder", "Select", "filter", "true", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "options", "panelStyle"], [1, "d-flex", "justify-content-between"], ["type", "button", "src", "./assets/common/reset.png", "alt", "", "srcset", "", 3, "click"], [1, "buttom1", "textBlack", "mx-1", 3, "click"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"], [1, "inputborderLess", 3, "onRemove", "ngModelChange", "ngModel"], [1, "tableProperties"], ["selectionMode", "multiple", "scrollHeight", "calc(100vh - 20.8rem)", "sortField", "createdOn", "currentPageReportTemplate", "Showing {first} to {last} of {totalRecords} entries", "styleClass", "p-datatable-sm", 1, "custom-table", 3, "selectionChange", "columns", "value", "scrollable", "selection", "paginator", "rows", "showCurrentPageReport", "sortOrder", "tableStyle"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [2, "width", "5rem"], [1, "pl-3"], [3, "pSortableColumn", 4, "ngFor", "ngForOf"], ["pFrozenColumn", "", "alignFrozen", "right", 2, "width", "5rem"], [1, "pl-3", 3, "disabled"], [4, "ngFor", "ngForOf"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter"], [3, "pSortableColumn"], [3, "field"], ["pInputText", "", "appTrim", "", "type", "text", "placeholder", "Search", 1, "p-column-filter", 3, "input", "value"], [3, "ngClass"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-3", 3, "value"], [3, "style", 4, "ngFor", "ngForOf"], ["pFrozenColumn", "", "alignFrozen", "right", 2, "padding-left", "1.5rem !important"], ["type", "button", "src", "./assets/common/actions.png", "alt", "", "srcset", "", 2, "height", "1.4rem", 3, "click"], [1, "d-flex", "flex-column"], ["mat-menu-item", "", 1, "w-100", 2, "width", "8rem", 3, "click"], ["src", "./assets/common/download.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], ["src", "./assets/common/document.png", "srcset", "", 2, "padding-right", "10px", "width", "2rem"], [4, "ngIf", "ngIfElse"], ["style", "cursor:pointer", "class", "textOrange font-weight-bold hvr-underline-from-center", 3, "click", 4, "ngIf"], [4, "ngIf"], [1, "textOrange", "font-weight-bold", "hvr-underline-from-center", 2, "cursor", "pointer", 3, "click"], [1, "my-2", "w-100", "pl-3"]], template: function CustomsInvoiceComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "div", 4)(1, "div", 5)(2, "p", 6);
    \u0275\u0275text(3, "Customs Invoice");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 5)(5, "img", 7);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_img_click_5_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("Edit"));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(6, "img", 8);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_img_click_6_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.downloadExcel());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "img", 9);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_img_click_7_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.deleteDialog());
    });
    \u0275\u0275elementEnd();
    \u0275\u0275element(8, "img", 10);
    \u0275\u0275elementStart(9, "button", 11);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_button_click_9_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.openCrud("New"));
    });
    \u0275\u0275element(10, "i", 12);
    \u0275\u0275text(11, " Add New");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(12, "div", 13)(13, "p-iconField", 14)(14, "p-inputIcon", 15);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_p_inputIcon_click_14_listener($event) {
      \u0275\u0275restoreView(_r1);
      const invoice_r2 = \u0275\u0275reference(17);
      return \u0275\u0275resetView(invoice_r2.toggle($event));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "input", 16);
    \u0275\u0275listener("input", function CustomsInvoiceComponent_Template_input_input_15_listener($event) {
      \u0275\u0275restoreView(_r1);
      const invoiceTag_r3 = \u0275\u0275reference(39);
      return \u0275\u0275resetView(invoiceTag_r3.filterGlobal($event.target.value, "contains"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(16, "p-overlayPanel", 17, 0)(18, "form", 18)(19, "div", 19)(20, "div", 20)(21, "p", 21);
    \u0275\u0275text(22, "Invoice Number");
    \u0275\u0275elementEnd();
    \u0275\u0275element(23, "p-multiSelect", 22);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(24, "div", 20)(25, "p", 21);
    \u0275\u0275text(26, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275element(27, "p-multiSelect", 23);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(28, "div", 24)(29, "div")(30, "img", 25);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_img_click_30_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.reset());
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(31, "div")(32, "button", 26);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_button_click_32_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeOverLay());
    });
    \u0275\u0275text(33, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(34, "button", 27);
    \u0275\u0275listener("click", function CustomsInvoiceComponent_Template_button_click_34_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.search());
    });
    \u0275\u0275text(35, "Apply");
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(36, "p-chips", 28);
    \u0275\u0275listener("onRemove", function CustomsInvoiceComponent_Template_p_chips_onRemove_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.chipClear($event));
    });
    \u0275\u0275twoWayListener("ngModelChange", function CustomsInvoiceComponent_Template_p_chips_ngModelChange_36_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.fieldsWithValue, $event) || (ctx.fieldsWithValue = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(37, "div", 29)(38, "p-table", 30, 1);
    \u0275\u0275twoWayListener("selectionChange", function CustomsInvoiceComponent_Template_p_table_selectionChange_38_listener($event) {
      \u0275\u0275restoreView(_r1);
      \u0275\u0275twoWayBindingSet(ctx.selectedInvoice, $event) || (ctx.selectedInvoice = $event);
      return \u0275\u0275resetView($event);
    });
    \u0275\u0275template(40, CustomsInvoiceComponent_ng_template_40_Template, 12, 3, "ng-template", 31)(41, CustomsInvoiceComponent_ng_template_41_Template, 15, 8, "ng-template", 32)(42, CustomsInvoiceComponent_ng_template_42_Template, 4, 1, "ng-template", 33);
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
    \u0275\u0275property("showClear", true)("panelStyle", \u0275\u0275pureFunction0(26, _c2));
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(27, _c2));
    \u0275\u0275property("showClear", true)("options", ctx.statusDropdown)("panelStyle", \u0275\u0275pureFunction0(28, _c2));
    \u0275\u0275advance(9);
    \u0275\u0275twoWayProperty("ngModel", ctx.fieldsWithValue);
    \u0275\u0275advance(2);
    \u0275\u0275property("columns", ctx.cols)("value", ctx.invoiceTable)("scrollable", true);
    \u0275\u0275twoWayProperty("selection", ctx.selectedInvoice);
    \u0275\u0275property("paginator", true)("rows", 100)("showCurrentPageReport", true)("sortOrder", -1)("tableStyle", \u0275\u0275pureFunction0(29, _c3));
  }
}, dependencies: [NgClass, NgForOf, NgIf, PrimeTemplate, Table, SortableColumn, FrozenColumn, SortIcon, TableCheckbox, TableHeaderCheckbox, InputIcon, IconField, InputText, \u0275NgNoValidate, NgControlStatus, NgControlStatusGroup, NgModel, Chips, OverlayPanel, MultiSelect, MatMenuItem, FormGroupDirective, FormControlName, TrimDirective, DatePipe], styles: ["\n\n  .customClass {\n  border-radius: 12px !important;\n  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;\n}\n.custom-height[_ngcontent-%COMP%] {\n  height: calc(100vh - 19.3rem) !important;\n  overflow-y: scroll !important;\n}\n/*# sourceMappingURL=customs-invoice.component.css.map */"] });
var CustomsInvoiceComponent = _CustomsInvoiceComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CustomsInvoiceComponent, { className: "CustomsInvoiceComponent", filePath: "src\\app\\main\\finance\\customs-invoice\\customs-invoice.component.ts", lineNumber: 22 });
})();

// src/app/main/finance/customs-invoice/customs-invoice-create/customs-invoice-create.component.ts
var _c02 = ["myStartCalendar"];
var _c12 = () => ({ "width": "120rem" });
var _c22 = () => ({ "width": "100%" });
function CustomsInvoiceCreateComponent_form_9_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 27);
  }
}
function CustomsInvoiceCreateComponent_form_9_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "form", 18)(1, "div", 19)(2, "div", 20)(3, "p", 21);
    \u0275\u0275text(4, "Date");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "p-calendar", 22, 0);
    \u0275\u0275listener("onSelect", function CustomsInvoiceCreateComponent_form_9_Template_p_calendar_onSelect_5_listener() {
      \u0275\u0275restoreView(_r1);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.filter("date"));
    });
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(7, "div", 20)(8, "p", 23);
    \u0275\u0275text(9, "Customer");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "p-dropdown", 24);
    \u0275\u0275listener("onChange", function CustomsInvoiceCreateComponent_form_9_Template_p_dropdown_onChange_10_listener() {
      \u0275\u0275restoreView(_r1);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.customerChange());
    });
    \u0275\u0275template(11, CustomsInvoiceCreateComponent_form_9_ng_template_11_Template, 1, 0, "ng-template", 25);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(12, "div", 20)(13, "p", 21);
    \u0275\u0275text(14, "MAWB");
    \u0275\u0275elementEnd();
    \u0275\u0275element(15, "p-multiSelect", 26);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275property("formGroup", ctx_r1.searchForm);
    \u0275\u0275advance(5);
    \u0275\u0275property("yearNavigator", true)("showButtonBar", true)("monthNavigator", true)("hideOnDateTimeSelect", true)("readonlyInput", true);
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(18, _c22));
    \u0275\u0275property("showClear", true)("filter", true)("options", ctx_r1.customerList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(19, _c22));
    \u0275\u0275advance(5);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(20, _c22));
    \u0275\u0275property("showClear", true)("options", ctx_r1.mawbList)("panelStyle", \u0275\u0275pureFunction0(21, _c22));
  }
}
function CustomsInvoiceCreateComponent_form_11_div_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 20)(1, "p", 23);
    \u0275\u0275text(2, "Invoice Number");
    \u0275\u0275elementEnd();
    \u0275\u0275element(3, "input", 34);
    \u0275\u0275elementEnd();
  }
}
function CustomsInvoiceCreateComponent_form_11_ng_template_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 27);
  }
}
function CustomsInvoiceCreateComponent_form_11_ng_template_20_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 27);
  }
}
function CustomsInvoiceCreateComponent_form_11_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "form", 18)(1, "div", 28);
    \u0275\u0275template(2, CustomsInvoiceCreateComponent_form_11_div_2_Template, 4, 0, "div", 29);
    \u0275\u0275elementStart(3, "div", 20)(4, "p", 23);
    \u0275\u0275text(5, "Invoice Date");
    \u0275\u0275elementEnd();
    \u0275\u0275element(6, "p-calendar", 30);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "div", 20)(8, "p", 21);
    \u0275\u0275text(9, "Customer");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(10, "p-dropdown", 31);
    \u0275\u0275template(11, CustomsInvoiceCreateComponent_form_11_ng_template_11_Template, 1, 0, "ng-template", 25);
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(12, "div", 20)(13, "p", 21);
    \u0275\u0275text(14, "Invoice Description");
    \u0275\u0275elementEnd();
    \u0275\u0275element(15, "input", 32);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(16, "div", 20)(17, "p", 21);
    \u0275\u0275text(18, "Status");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(19, "p-dropdown", 33);
    \u0275\u0275template(20, CustomsInvoiceCreateComponent_form_11_ng_template_20_Template, 1, 0, "ng-template", 25);
    \u0275\u0275elementEnd()()()();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275property("formGroup", ctx_r1.form);
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx_r1.pageToken.pageflow != "New");
    \u0275\u0275advance(4);
    \u0275\u0275property("iconDisplay", "input")("showIcon", true);
    \u0275\u0275advance(4);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(17, _c22));
    \u0275\u0275property("showClear", true)("filter", true)("options", ctx_r1.customerList)("disabled", true)("panelStyle", \u0275\u0275pureFunction0(18, _c22));
    \u0275\u0275advance(9);
    \u0275\u0275styleMap(\u0275\u0275pureFunction0(19, _c22));
    \u0275\u0275property("showClear", true)("filter", true)("options", ctx_r1.status)("panelStyle", \u0275\u0275pureFunction0(20, _c22));
  }
}
function CustomsInvoiceCreateComponent_ng_template_14_th_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "th");
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r3 = ctx.$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(col_r3.header);
  }
}
function CustomsInvoiceCreateComponent_ng_template_14_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "th", 35);
    \u0275\u0275element(2, "p-tableHeaderCheckbox", 36);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsInvoiceCreateComponent_ng_template_14_th_3_Template, 2, 1, "th", 37);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r4 = ctx.$implicit;
    \u0275\u0275advance(3);
    \u0275\u0275property("ngForOf", columns_r4);
  }
}
function CustomsInvoiceCreateComponent_ng_template_15_td_3_ng_container_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementContainerStart(0);
    \u0275\u0275elementStart(1, "p-inputNumber", 41);
    \u0275\u0275listener("change", function CustomsInvoiceCreateComponent_ng_template_15_td_3_ng_container_1_Template_p_inputNumber_change_1_listener() {
      \u0275\u0275restoreView(_r5);
      const rowData_r6 = \u0275\u0275nextContext(2).$implicit;
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.calculcateTotal(rowData_r6));
    });
    \u0275\u0275elementEnd();
    \u0275\u0275elementContainerEnd();
  }
  if (rf & 2) {
    const col_r7 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275advance();
    \u0275\u0275property("minFractionDigits", 3)("formControlName", col_r7.field);
  }
}
function CustomsInvoiceCreateComponent_ng_template_15_td_3_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "input", 42);
  }
  if (rf & 2) {
    const col_r7 = \u0275\u0275nextContext().$implicit;
    \u0275\u0275property("formControlName", col_r7.field);
  }
}
function CustomsInvoiceCreateComponent_ng_template_15_td_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "td");
    \u0275\u0275template(1, CustomsInvoiceCreateComponent_ng_template_15_td_3_ng_container_1_Template, 2, 2, "ng-container", 40)(2, CustomsInvoiceCreateComponent_ng_template_15_td_3_ng_template_2_Template, 1, 1, "ng-template", null, 1, \u0275\u0275templateRefExtractor);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const col_r7 = ctx.$implicit;
    const others_r8 = \u0275\u0275reference(3);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", col_r7.format !== "Input")("ngIfElse", others_r8);
  }
}
function CustomsInvoiceCreateComponent_ng_template_15_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr", 18)(1, "td", 38);
    \u0275\u0275element(2, "p-tableCheckbox", 39);
    \u0275\u0275elementEnd();
    \u0275\u0275template(3, CustomsInvoiceCreateComponent_ng_template_15_td_3_Template, 4, 2, "td", 37);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const columns_r9 = ctx.columns;
    const item_r10 = ctx.$implicit;
    const rowData_r6 = ctx.$implicit;
    \u0275\u0275property("formGroup", item_r10);
    \u0275\u0275advance(2);
    \u0275\u0275property("value", rowData_r6);
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", columns_r9);
  }
}
function CustomsInvoiceCreateComponent_ng_template_16_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "tr")(1, "td")(2, "p", 43);
    \u0275\u0275text(3, "No records found");
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance();
    \u0275\u0275attribute("colspan", 4);
  }
}
function CustomsInvoiceCreateComponent_button_20_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "button", 44);
    \u0275\u0275listener("click", function CustomsInvoiceCreateComponent_button_20_Template_button_click_0_listener() {
      \u0275\u0275restoreView(_r11);
      const ctx_r1 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r1.save());
    });
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const ctx_r1 = \u0275\u0275nextContext();
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(ctx_r1.pageToken.pageflow != "New" ? "Update" : "Create Invoice");
  }
}
var _CustomsInvoiceCreateComponent = class _CustomsInvoiceCreateComponent {
  constructor(cs, spin, route, router, path, fb, service, messageService, cas, auth, dialog, PrealertService2) {
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
    this.dialog = dialog;
    this.PrealertService = PrealertService2;
    this.active = 0;
    this.status = [];
    this.selectedInvoice = [];
    this.invoiceTable = [];
    this.submitted = false;
    this.form = this.fb.group({
      companyId: [this.auth.companyId],
      createdBy: [],
      createdOn: [],
      deletionIndicator: [],
      description: [],
      invoiceDate: [],
      invoiceDateFE: [, Validators.required],
      invoiceHeaderId: [],
      invoiceLines: this.fb.array([]),
      invoiceNo: [],
      languageId: [this.auth.languageId],
      partnerId: [],
      partnerName: [],
      referenceField1: [""],
      referenceField10: [""],
      referenceField2: [""],
      referenceField3: [""],
      referenceField4: [""],
      referenceField5: [""],
      referenceField6: [""],
      referenceField7: [""],
      referenceField8: [""],
      referenceField9: [""],
      statusId: ["1"],
      updatedBy: [],
      updatedOn: []
    });
    this.searchForm = this.fb.group({
      fromDate: [""],
      partnerId: [, Validators.required],
      partnerMasterAirwayBill: [],
      toDate: [""],
      fullDate: []
    });
    this.cols = [];
    this.target = [];
    this.customerList = [];
    this.mawbList = [];
    this.status = [
      { value: "2", label: "Updated" },
      { value: "1", label: "Created" }
    ];
  }
  get invoiceLines() {
    return this.form.get("invoiceLines");
  }
  patchForm(data) {
    const itemsArray = this.form.get("invoiceLines");
    data.forEach((line) => {
      itemsArray.push(this.patchLine(line));
    });
  }
  patchLine(data) {
    return this.fb.group({
      approvals: [data.approvals],
      clearanceCharge: [data.clearanceCharge],
      companyId: [this.auth.companyId],
      createdBy: [data.createdBy],
      createdOn: [data.createdOn],
      customDuty: [data.customDuty || 0],
      handlingFees: [data.handlingFees || 0],
      costPerShipment: [data.costPerShipment || 0],
      deletionIndicator: [data.deletionIndicator || 0],
      foodApproval: [data.foodApproval],
      invoiceLineId: [data.invoiceLineId || null],
      invoiceNo: [data.invoiceNo],
      languageId: [this.auth.languageId],
      noOfShipments: [data.noOfShipments || 0],
      otherApproval: [data.otherApproval],
      partnerMasterAirwayBill: [data.partnerMasterAirwayBill],
      referenceField1: [data.referenceField1],
      referenceField10: [data.referenceField10],
      referenceField2: [data.referenceField2],
      referenceField3: [data.referenceField3],
      referenceField4: [data.referenceField4],
      referenceField5: [data.referenceField5],
      referenceField6: [data.referenceField6],
      referenceField7: [data.referenceField7],
      referenceField8: [data.referenceField8],
      referenceField9: [data.referenceField9],
      totalValue: [data.totalValue || 0],
      totalApproval: [data.totalApproval || 0],
      updatedBy: [data.updatedBy],
      updatedOn: [data.updatedOn]
    });
  }
  onChange() {
    const choosen = this.selectedInvoice[this.selectedInvoice.length - 1];
    this.selectedInvoice.length = 0;
    this.selectedInvoice.push(choosen);
  }
  ngOnInit() {
    let code = this.route.snapshot.params["code"];
    this.pageToken = this.cs.decrypt(code);
    this.dropdownlist();
    const dataToSend = ["Finance", "Customs Invoice", this.pageToken.pageflow];
    this.path.setData(dataToSend);
    this.callTableHeader();
    if (this.pageToken.pageflow != "New") {
      this.form.controls.invoiceNo.disable();
      this.fill(this.pageToken.line);
    }
  }
  callTableHeader() {
    this.cols = [
      { field: "partnerMasterAirwayBill", header: "MAWB", format: "Input" },
      { field: "noOfShipments", header: "Total No of Shipments", format: "Input" },
      { field: "clearanceCharge", header: "Clearance Charges AWBs" },
      { field: "totalApproval", header: "Total Approvals" },
      { field: "handlingFees", header: "Handling Fees" },
      { field: "customDuty", header: "Custom Duty 5%" },
      { field: "totalValue", header: "Total Value" }
    ];
    this.target = [];
  }
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.customer.url
    ]).subscribe({
      next: (results) => {
        this.customerList = this.cas.forLanguageFilter(results[0], this.cas.dropdownlist.setup.customer.key);
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  fill(line) {
    this.service.search({ invoiceNo: [line.invoiceNo] }).subscribe({
      next: (result) => {
        this.invoiceLines.clear();
        this.form.patchValue(result[0]);
        if (this.form.controls.invoiceDate.value) {
          let date = this.cs.pCalendar(this.form.controls.invoiceDate.value);
          this.form.controls.invoiceDateFE.patchValue(date);
        }
        this.patchForm(result[0].invoiceLines);
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  save() {
    if (this.form.invalid) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        key: "br",
        detail: "Please fill required fields to continue"
      });
      return;
    }
    if (this.pageToken.pageflow == "New") {
      this.spin.show();
      let date = this.cs.jsonDate(this.form.controls.invoiceDateFE.value);
      this.form.controls.invoiceDate.patchValue(date);
      const selectedValues = this.selectedInvoice.map((item) => item.getRawValue());
      this.invoiceLines.clear();
      this.patchForm(selectedValues);
      this.service.Create([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Created",
            key: "br",
            detail: res[0].invoiceNo + " has been created successfully"
          });
          this.router.navigate(["/main/finance/invoice"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    } else {
      let date = this.cs.jsonDate(this.form.controls.invoiceDateFE.value);
      this.form.controls.invoiceDate.patchValue(date);
      this.spin.show();
      this.service.Update([this.form.getRawValue()]).subscribe({
        next: (res) => {
          this.messageService.add({
            severity: "success",
            summary: "Updated",
            key: "br",
            detail: res[0].invoiceNo + " has been updated successfully"
          });
          this.router.navigate(["/main/finance/invoice"]);
          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      });
    }
  }
  getColspan() {
    return this.cols.length + 2;
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
    if (this.searchForm.invalid) {
      this.messageService.add({ severity: "warn", summary: "Error", key: "br", detail: "Please fill the customer field to continue" });
      return;
    }
    this.spin.show();
    this.service.executeInvoiceReport(this.searchForm.getRawValue()).subscribe({
      next: (res) => {
        if (res.length > 0) {
          this.submitted = true;
          this.invoiceLines.clear();
          this.patchForm(res);
          this.selectedInvoice = this.invoiceLines.controls;
        }
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  customerChange() {
    const list = this.customerList.filter((x) => x.value == this.searchForm.controls.partnerId.value);
    this.form.controls.partnerName.patchValue(list[0].label);
    this.form.controls.partnerId.patchValue(list[0].value);
    this.form.controls.partnerId.disable();
    let obj = {};
    obj.companyId = [this.auth.companyId];
    obj.partnerId = [this.form.controls.partnerId.value];
    this.PrealertService.search(obj).subscribe({
      next: (result) => {
        this.mawbList = this.cas.foreachlistWithoutKey(result, { key: "partnerMasterAirwayBill", value: "partnerMasterAirwayBill" });
        this.spin.hide();
      },
      error: (err) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      }
    });
  }
  // To calculate total table value
  calculcateTotal(line) {
    let lines = line.getRawValue();
    let totalValue = 0;
    totalValue = Number(lines.clearanceCharge) + Number(lines.totalApproval) + Number(lines.handlingFees) + Number(lines.costPerShipment) + Number(lines.customDuty);
    let totalValue1 = totalValue.toFixed(3);
    line.controls.totalValue.patchValue(totalValue1);
  }
  isSelected(item) {
    return this.selectedInvoice.includes(item);
  }
};
_CustomsInvoiceCreateComponent.\u0275fac = function CustomsInvoiceCreateComponent_Factory(t) {
  return new (t || _CustomsInvoiceCreateComponent)(\u0275\u0275directiveInject(CommonServiceService), \u0275\u0275directiveInject(NgxSpinnerService), \u0275\u0275directiveInject(ActivatedRoute), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(InvoiceService), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(CommonAPIService), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(MatDialog), \u0275\u0275directiveInject(PrealertService));
};
_CustomsInvoiceCreateComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _CustomsInvoiceCreateComponent, selectors: [["app-customs-invoice-create"]], viewQuery: function CustomsInvoiceCreateComponent_Query(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275viewQuery(_c02, 5);
  }
  if (rf & 2) {
    let _t;
    \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.startCalendar = _t.first);
  }
}, decls: 21, vars: 11, consts: [["myStartCalendar", ""], ["others", ""], [1, "componentBody"], [1, "d-flex", "justify-content-between", "align-items-center"], [1, "componentHeader", "f600", "mb-0"], ["type", "button", 1, "btn", "ml-2", "addNewbuttom", "bgBlack", "text-white", 3, "click", "disabled"], [1, "bi", "bi-check2", "pr-2"], [1, "mx-auto", "mt-4", "md-4"], [3, "formGroup", 4, "ngIf"], [1, "d-flex-row"], [1, "tableProperties"], ["scrollHeight", "calc(100vh - 24.8rem)", "selectionMode", "multiple", 3, "selectionChange", "scrollable", "columns", "tableStyle", "value", "selection"], ["pTemplate", "header"], ["pTemplate", "body"], ["pTemplate", "emptymessage"], [1, "d-flex", "mt-1", "justify-content-end", 2, "position", "absolute", "right", "43%", "bottom", "7%"], ["routerLink", "/main/finance/invoice", 1, "buttom1", "textBlack", "mx-1"], ["class", "buttom1 bgBlack text-white mx-1", 3, "click", 4, "ngIf"], [3, "formGroup"], [1, "row"], [1, "col-3", "marginFieldNew", "borderRadius12"], [1, "formControlLabel", "f600", "textBlack", "mb-0"], ["yearRange", "2000:2099", "dateFormat", "dd/mm/yy", "formControlName", "fullDate", "placeholder", "Choose Date", "selectionMode", "range", "inputId", "range", 3, "onSelect", "yearNavigator", "showButtonBar", "monthNavigator", "hideOnDateTimeSelect", "readonlyInput"], [1, "formControlLabel", "f600", "textBlack", "mb-0", "required"], ["placeholder", "Select", "formControlName", "partnerId", "appendTo", "body", 3, "onChange", "showClear", "filter", "options", "disabled", "panelStyle"], ["pTemplate", "filter"], ["placeholder", "Select", "filter", "true", "formControlName", "partnerMasterAirwayBill", "appendTo", "body", 3, "showClear", "options", "panelStyle"], ["type", "text", "pInputText", "", "appTrim", "", "appTrim", "", "placeholder", "Search"], [1, "row", "scrollNew"], ["class", "col-3 marginFieldNew borderRadius12", 4, "ngIf"], ["dateFormat", "dd/mm/yy", "formControlName", "invoiceDateFE", "appendTo", "body", "placeholder", "Select Date", 1, "w-100", "small-calendar", 3, "iconDisplay", "showIcon"], ["placeholder", "Select", "formControlName", "partnerId", "appendTo", "body", 3, "showClear", "filter", "options", "disabled", "panelStyle"], ["pInputText", "", "appTrim", "", "formControlName", "description", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], ["placeholder", "Select", "formControlName", "statusId", "appendTo", "body", 3, "showClear", "filter", "options", "panelStyle"], ["maxlength", "50", "pInputText", "", "appTrim", "", "formControlName", "invoiceNo", "pInputText", "", "appTrim", "", "placeholder", "Enter", 1, "w-100"], [2, "width", "5rem"], [1, "pl-3"], [4, "ngFor", "ngForOf"], [2, "width", "5rem", "justify-content", "center"], [1, "pl-4", 3, "value"], [4, "ngIf", "ngIfElse"], [1, "inputborderLess1", "pl-3", 3, "change", "minFractionDigits", "formControlName"], [1, "inputborderLess", "pl-3", 3, "formControlName"], [1, "my-2", "w-100", "pl-3"], [1, "buttom1", "bgBlack", "text-white", "mx-1", 3, "click"]], template: function CustomsInvoiceCreateComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 2)(1, "div", 3)(2, "p", 4);
    \u0275\u0275text(3);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(4, "div", 3)(5, "button", 5);
    \u0275\u0275listener("click", function CustomsInvoiceCreateComponent_Template_button_click_5_listener() {
      return ctx.execute();
    });
    \u0275\u0275element(6, "i", 6);
    \u0275\u0275text(7, "Execute");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(8, "div", 7);
    \u0275\u0275template(9, CustomsInvoiceCreateComponent_form_9_Template, 16, 22, "form", 8);
    \u0275\u0275elementStart(10, "div", 9);
    \u0275\u0275template(11, CustomsInvoiceCreateComponent_form_11_Template, 21, 21, "form", 8);
    \u0275\u0275elementStart(12, "div", 10)(13, "p-table", 11);
    \u0275\u0275twoWayListener("selectionChange", function CustomsInvoiceCreateComponent_Template_p_table_selectionChange_13_listener($event) {
      \u0275\u0275twoWayBindingSet(ctx.selectedInvoice, $event) || (ctx.selectedInvoice = $event);
      return $event;
    });
    \u0275\u0275template(14, CustomsInvoiceCreateComponent_ng_template_14_Template, 4, 1, "ng-template", 12)(15, CustomsInvoiceCreateComponent_ng_template_15_Template, 4, 3, "ng-template", 13)(16, CustomsInvoiceCreateComponent_ng_template_16_Template, 4, 1, "ng-template", 14);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(17, "div", 15)(18, "button", 16);
    \u0275\u0275text(19, "Cancel");
    \u0275\u0275elementEnd();
    \u0275\u0275template(20, CustomsInvoiceCreateComponent_button_20_Template, 2, 1, "button", 17);
    \u0275\u0275elementEnd()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(3);
    \u0275\u0275textInterpolate1("Customs Invoice - ", ctx.pageToken.pageflow, "");
    \u0275\u0275advance(2);
    \u0275\u0275property("disabled", ctx.pageToken.pageflow != "New");
    \u0275\u0275advance(4);
    \u0275\u0275property("ngIf", ctx.pageToken.pageflow == "New");
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", ctx.submitted || ctx.pageToken.pageflow != "New");
    \u0275\u0275advance(2);
    \u0275\u0275property("scrollable", true)("columns", ctx.cols)("tableStyle", \u0275\u0275pureFunction0(10, _c12))("value", ctx.invoiceLines.controls);
    \u0275\u0275twoWayProperty("selection", ctx.selectedInvoice);
    \u0275\u0275advance(7);
    \u0275\u0275property("ngIf", ctx.submitted || ctx.pageToken.pageflow != "New");
  }
}, dependencies: [NgForOf, NgIf, RouterLink, PrimeTemplate, Table, TableCheckbox, TableHeaderCheckbox, Dropdown, InputText, Calendar, InputNumber, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, MaxLengthValidator, MultiSelect, FormGroupDirective, FormControlName, TrimDirective], styles: ["\n\n.circle[_ngcontent-%COMP%] {\n  width: 44px;\n  height: 44px;\n  flex-grow: 0;\n  margin: 0 6px 12px;\n  border-radius: 100%;\n  padding: 12px 17px 11px;\n  background-color: var(--white);\n}\n.borderCircle[_ngcontent-%COMP%] {\n  border: solid 1.5px var(--black);\n}\n.marginFieldNew[_ngcontent-%COMP%] {\n  margin-bottom: 1.5rem;\n}\n.img-icon[_ngcontent-%COMP%] {\n  gap: 2rem;\n}\n/*# sourceMappingURL=customs-invoice-create.component.css.map */"] });
var CustomsInvoiceCreateComponent = _CustomsInvoiceCreateComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(CustomsInvoiceCreateComponent, { className: "CustomsInvoiceCreateComponent", filePath: "src\\app\\main\\finance\\customs-invoice\\customs-invoice-create\\customs-invoice-create.component.ts", lineNumber: 21 });
})();

// src/app/main/finance/finance-routing.module.ts
var routes = [
  { path: "costingSheet", component: CostingSheetComponent, data: { title: "Finance", module: "Customs Costing" } },
  { path: "invoice", component: CustomsInvoiceComponent, data: { title: "Finance", module: "Invoice" } },
  { path: "invoice-new/:code", component: CustomsInvoiceCreateComponent, data: { title: "Finance", module: "Invoice-New" } }
];
var _FinanceRoutingModule = class _FinanceRoutingModule {
};
_FinanceRoutingModule.\u0275fac = function FinanceRoutingModule_Factory(t) {
  return new (t || _FinanceRoutingModule)();
};
_FinanceRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _FinanceRoutingModule });
_FinanceRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forChild(routes), RouterModule] });
var FinanceRoutingModule = _FinanceRoutingModule;

// src/app/main/finance/finance.module.ts
var _FinanceModule = class _FinanceModule {
};
_FinanceModule.\u0275fac = function FinanceModule_Factory(t) {
  return new (t || _FinanceModule)();
};
_FinanceModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _FinanceModule });
_FinanceModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [
  CommonModule,
  FinanceRoutingModule,
  SharedModule
] });
var FinanceModule = _FinanceModule;
export {
  FinanceModule
};
//# sourceMappingURL=chunk-2FRGNCEI.js.map
