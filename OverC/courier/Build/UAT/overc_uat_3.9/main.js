import {
  require_JsBarcode
} from "./chunk-5BHKZR5U.js";
import "./chunk-5SKNGDL5.js";
import "./chunk-ZBBD2MUA.js";
import {
  AuthService,
  BrowserAnimationsModule,
  MessageService,
  NgxSpinnerComponent,
  NgxSpinnerModule,
  SharedModule,
  Toast,
  environment,
  initializeApp,
  registerVersion
} from "./chunk-XFYC4BWK.js";
import {
  BehaviorSubject,
  BrowserModule,
  Component,
  DatePipe,
  DecimalPipe,
  HTTP_INTERCEPTORS,
  HashLocationStrategy,
  HttpClient,
  HttpClientModule,
  HttpParams,
  Input,
  InputFlags,
  LocationStrategy,
  NgModule,
  Renderer2,
  Router,
  RouterModule,
  RouterOutlet,
  ViewChild,
  __toESM,
  catchError,
  platformBrowser,
  setClassMetadata,
  switchMap,
  throwError,
  ɵsetClassDebugInfo,
  ɵɵNgOnChangesFeature,
  ɵɵclassMap,
  ɵɵdefineComponent,
  ɵɵdefineInjectable,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵinject,
  ɵɵloadQuery,
  ɵɵqueryRefresh,
  ɵɵtext,
  ɵɵviewQuery
} from "./chunk-Z5YEPTLN.js";

// src/app/app-routing.module.ts
var routes = [
  { path: "", loadChildren: () => import("./chunk-JA6DMS64.js").then((m) => m.LoginModule) },
  { path: "main", loadChildren: () => import("./chunk-YE4GGIDX.js").then((m) => m.MainModule) }
];
var _AppRoutingModule = class _AppRoutingModule {
};
_AppRoutingModule.\u0275fac = function AppRoutingModule_Factory(t) {
  return new (t || _AppRoutingModule)();
};
_AppRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _AppRoutingModule });
_AppRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forRoot(routes), RouterModule] });
var AppRoutingModule = _AppRoutingModule;

// src/app/app.component.ts
var _AppComponent = class _AppComponent {
  constructor(http, router) {
    this.http = http;
    this.router = router;
    this.title = "angular17.0";
  }
  ngOnInit() {
  }
};
_AppComponent.\u0275fac = function AppComponent_Factory(t) {
  return new (t || _AppComponent)(\u0275\u0275directiveInject(HttpClient), \u0275\u0275directiveInject(Router));
};
_AppComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _AppComponent, selectors: [["app-root"]], decls: 5, vars: 0, consts: [["position", "bottom-center", "key", "br"], ["bdColor", "rgba(51,51,51,0.8)", "size", "medium", "color", "#fff", "type", "ball-scale"], [2, "font-size", "20px", "color", "white"]], template: function AppComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "p-toast", 0);
    \u0275\u0275elementStart(1, "ngx-spinner", 1)(2, "p", 2);
    \u0275\u0275text(3, "Loading...");
    \u0275\u0275elementEnd()();
    \u0275\u0275element(4, "router-outlet");
  }
}, dependencies: [RouterOutlet, Toast, NgxSpinnerComponent] });
var AppComponent = _AppComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(AppComponent, { className: "AppComponent", filePath: "src\\app\\app.component.ts", lineNumber: 10 });
})();

// src/app/core/common.interceptor.ts
var _CommonInterceptor = class _CommonInterceptor {
  constructor(auth) {
    this.auth = auth;
  }
  intercept(req, next) {
    let authReq;
    let url = environment.apiURL;
    const bearerToken = this.auth.getToken(req.url.split("/")[1]);
    if (bearerToken) {
      authReq = req.clone({
        url: `${url}${req.url}`,
        params: new HttpParams().set("authToken", bearerToken).set("loginUserID", this.auth.userID)
      });
    } else {
      authReq = req.clone({
        url: `${url}${req.url}`
      });
    }
    return next.handle(authReq);
  }
};
_CommonInterceptor.\u0275fac = function CommonInterceptor_Factory(t) {
  return new (t || _CommonInterceptor)(\u0275\u0275inject(AuthService));
};
_CommonInterceptor.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _CommonInterceptor, factory: _CommonInterceptor.\u0275fac });
var CommonInterceptor = _CommonInterceptor;

// src/app/core/error.interceptor.ts
var _ErrorInterceptor = class _ErrorInterceptor {
  constructor(auth) {
    this.auth = auth;
    this.isRefreshing = false;
    this.refreshTokenSubject = new BehaviorSubject(null);
  }
  intercept(request, next) {
    return next.handle(request).pipe(catchError((error) => {
      if (error.status === 401 || error.status === 400) {
        if (request.url.includes("/doc-storage/multiUpload")) {
          return throwError(error);
        }
        if (request.url.includes("/consignment/upload/v2")) {
          return throwError(error);
        }
        if (request.url.includes("/pdf/extract")) {
          return throwError(error);
        }
        if (request.url.includes("pdf/merge")) {
          return throwError(error);
        }
        if (request.url.includes("preAlert/upload")) {
          return throwError(error);
        }
        if (request.url.includes("download")) {
          return throwError(error);
        }
        return this.handle401Error(request, next, error);
      }
      return throwError(error);
    }));
  }
  handle401Error(request, next, error) {
    this.isRefreshing = true;
    this.refreshTokenSubject.next(null);
    ;
    const token = this.auth.token;
    let apiName = "";
    apiName = request.url.split("/")[4];
    return this.auth.refreshToken(apiName).pipe(switchMap((token2) => {
      this.auth.saveToken(token2.access_token, apiName);
      this.refreshTokenSubject.next(token2.access_token);
      return next.handle(this.addTokenHeader(request, token2.access_token));
    }), catchError((err) => {
      if (err.statusc == 401) {
        this.auth.logout();
      }
      return throwError(err);
    }));
  }
  addTokenHeader(request, token) {
    if (request.url.includes("wms-idmaster-service/login") || request.url.includes("wms-idmaster-service/docchecklist/findDocCheckList"))
      return request.clone({ params: new HttpParams().append("authToken", token) });
    else {
      return request.clone({ params: new HttpParams().append("authToken", token).append("loginUserID", this.auth.userID) });
    }
  }
};
_ErrorInterceptor.\u0275fac = function ErrorInterceptor_Factory(t) {
  return new (t || _ErrorInterceptor)(\u0275\u0275inject(AuthService));
};
_ErrorInterceptor.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _ErrorInterceptor, factory: _ErrorInterceptor.\u0275fac });
var ErrorInterceptor = _ErrorInterceptor;

// src/app/core/httpInterceptorProviders.ts
var httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: CommonInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
];

// node_modules/ngx-barcode6/fesm2022/ngx-barcode6.mjs
var import_jsbarcode = __toESM(require_JsBarcode(), 1);
var _c0 = ["bcElement"];
var _NgxBarcode6Component = class _NgxBarcode6Component {
  get options() {
    return {
      format: this.format,
      lineColor: this.lineColor,
      width: this.width,
      height: this.height,
      displayValue: this.displayValue,
      fontOptions: this.fontOptions,
      font: this.font,
      textAlign: this.textAlign,
      textPosition: this.textPosition,
      textMargin: this.textMargin,
      fontSize: this.fontSize,
      background: this.background,
      margin: this.margin,
      marginTop: this.marginTop,
      marginBottom: this.marginBottom,
      marginLeft: this.marginLeft,
      marginRight: this.marginRight,
      valid: this.valid
    };
  }
  constructor(renderer) {
    this.renderer = renderer;
    this.elementType = "svg";
    this.cssClass = "barcode";
    this.format = "CODE128";
    this.lineColor = "#000000";
    this.width = 2;
    this.height = 100;
    this.displayValue = false;
    this.fontOptions = "";
    this.font = "monospace";
    this.textAlign = "center";
    this.textPosition = "bottom";
    this.textMargin = 2;
    this.fontSize = 20;
    this.background = "#ffffff";
    this.margin = 10;
    this.marginTop = 10;
    this.marginBottom = 10;
    this.marginLeft = 10;
    this.marginRight = 10;
    this.value = "";
    this.valid = () => true;
  }
  ngAfterViewInit() {
    this.createBarcode();
  }
  ngOnChanges() {
    if (this.bcElement) {
      this.createBarcode();
    }
  }
  createBarcode() {
    if (!this.value) {
      return;
    }
    let element;
    switch (this.elementType) {
      case "img":
        element = this.renderer.createElement("img");
        break;
      case "canvas":
        element = this.renderer.createElement("canvas");
        break;
      case "svg":
      default:
        element = this.renderer.createElement("svg", "svg");
    }
    (0, import_jsbarcode.default)(element, this.value, this.options);
    for (const node of this.bcElement.nativeElement.childNodes) {
      this.renderer.removeChild(this.bcElement.nativeElement, node);
    }
    this.renderer.appendChild(this.bcElement.nativeElement, element);
  }
};
_NgxBarcode6Component.\u0275fac = function NgxBarcode6Component_Factory(t) {
  return new (t || _NgxBarcode6Component)(\u0275\u0275directiveInject(Renderer2));
};
_NgxBarcode6Component.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({
  type: _NgxBarcode6Component,
  selectors: [["ngx-barcode6"]],
  viewQuery: function NgxBarcode6Component_Query(rf, ctx) {
    if (rf & 1) {
      \u0275\u0275viewQuery(_c0, 5);
    }
    if (rf & 2) {
      let _t;
      \u0275\u0275queryRefresh(_t = \u0275\u0275loadQuery()) && (ctx.bcElement = _t.first);
    }
  },
  inputs: {
    elementType: [InputFlags.None, "bc-element-type", "elementType"],
    cssClass: [InputFlags.None, "bc-class", "cssClass"],
    format: [InputFlags.None, "bc-format", "format"],
    lineColor: [InputFlags.None, "bc-line-color", "lineColor"],
    width: [InputFlags.None, "bc-width", "width"],
    height: [InputFlags.None, "bc-height", "height"],
    displayValue: [InputFlags.None, "bc-display-value", "displayValue"],
    fontOptions: [InputFlags.None, "bc-font-options", "fontOptions"],
    font: [InputFlags.None, "bc-font", "font"],
    textAlign: [InputFlags.None, "bc-text-align", "textAlign"],
    textPosition: [InputFlags.None, "bc-text-position", "textPosition"],
    textMargin: [InputFlags.None, "bc-text-margin", "textMargin"],
    fontSize: [InputFlags.None, "bc-font-size", "fontSize"],
    background: [InputFlags.None, "bc-background", "background"],
    margin: [InputFlags.None, "bc-margin", "margin"],
    marginTop: [InputFlags.None, "bc-margin-top", "marginTop"],
    marginBottom: [InputFlags.None, "bc-margin-bottom", "marginBottom"],
    marginLeft: [InputFlags.None, "bc-margin-left", "marginLeft"],
    marginRight: [InputFlags.None, "bc-margin-right", "marginRight"],
    value: [InputFlags.None, "bc-value", "value"],
    valid: [InputFlags.None, "bc-valid", "valid"]
  },
  features: [\u0275\u0275NgOnChangesFeature],
  decls: 2,
  vars: 2,
  consts: [["bcElement", ""]],
  template: function NgxBarcode6Component_Template(rf, ctx) {
    if (rf & 1) {
      \u0275\u0275element(0, "div", null, 0);
    }
    if (rf & 2) {
      \u0275\u0275classMap(ctx.cssClass);
    }
  },
  encapsulation: 2
});
var NgxBarcode6Component = _NgxBarcode6Component;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(NgxBarcode6Component, [{
    type: Component,
    args: [{
      selector: "ngx-barcode6",
      template: `<div #bcElement [class]="cssClass"></div>`
    }]
  }], () => [{
    type: Renderer2
  }], {
    elementType: [{
      type: Input,
      args: ["bc-element-type"]
    }],
    cssClass: [{
      type: Input,
      args: ["bc-class"]
    }],
    format: [{
      type: Input,
      args: ["bc-format"]
    }],
    lineColor: [{
      type: Input,
      args: ["bc-line-color"]
    }],
    width: [{
      type: Input,
      args: ["bc-width"]
    }],
    height: [{
      type: Input,
      args: ["bc-height"]
    }],
    displayValue: [{
      type: Input,
      args: ["bc-display-value"]
    }],
    fontOptions: [{
      type: Input,
      args: ["bc-font-options"]
    }],
    font: [{
      type: Input,
      args: ["bc-font"]
    }],
    textAlign: [{
      type: Input,
      args: ["bc-text-align"]
    }],
    textPosition: [{
      type: Input,
      args: ["bc-text-position"]
    }],
    textMargin: [{
      type: Input,
      args: ["bc-text-margin"]
    }],
    fontSize: [{
      type: Input,
      args: ["bc-font-size"]
    }],
    background: [{
      type: Input,
      args: ["bc-background"]
    }],
    margin: [{
      type: Input,
      args: ["bc-margin"]
    }],
    marginTop: [{
      type: Input,
      args: ["bc-margin-top"]
    }],
    marginBottom: [{
      type: Input,
      args: ["bc-margin-bottom"]
    }],
    marginLeft: [{
      type: Input,
      args: ["bc-margin-left"]
    }],
    marginRight: [{
      type: Input,
      args: ["bc-margin-right"]
    }],
    value: [{
      type: Input,
      args: ["bc-value"]
    }],
    valid: [{
      type: Input,
      args: ["bc-valid"]
    }],
    bcElement: [{
      type: ViewChild,
      args: ["bcElement"]
    }]
  });
})();
var _NgxBarcode6Module = class _NgxBarcode6Module {
};
_NgxBarcode6Module.\u0275fac = function NgxBarcode6Module_Factory(t) {
  return new (t || _NgxBarcode6Module)();
};
_NgxBarcode6Module.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({
  type: _NgxBarcode6Module
});
_NgxBarcode6Module.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({});
var NgxBarcode6Module = _NgxBarcode6Module;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(NgxBarcode6Module, [{
    type: NgModule,
    args: [{
      declarations: [NgxBarcode6Component],
      imports: [],
      exports: [NgxBarcode6Component]
    }]
  }], null, null);
})();

// node_modules/firebase/app/dist/esm/index.esm.js
var name = "firebase";
var version = "10.12.4";
registerVersion(name, version, "app");

// src/app/app.module.ts
initializeApp(environment.firebase);
var _AppModule = class _AppModule {
};
_AppModule.\u0275fac = function AppModule_Factory(t) {
  return new (t || _AppModule)();
};
_AppModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _AppModule, bootstrap: [AppComponent] });
_AppModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ providers: [
  MessageService,
  DatePipe,
  DecimalPipe,
  { provide: LocationStrategy, useClass: HashLocationStrategy },
  httpInterceptorProviders
], imports: [
  BrowserModule,
  AppRoutingModule,
  SharedModule,
  BrowserAnimationsModule,
  HttpClientModule,
  NgxBarcode6Module,
  NgxSpinnerModule
] });
var AppModule = _AppModule;

// src/main.ts
platformBrowser().bootstrapModule(AppModule).catch((err) => console.error(err));
/*! Bundled license information:

firebase/app/dist/esm/index.esm.js:
  (**
   * @license
   * Copyright 2020 Google LLC
   *
   * Licensed under the Apache License, Version 2.0 (the "License");
   * you may not use this file except in compliance with the License.
   * You may obtain a copy of the License at
   *
   *   http://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   * See the License for the specific language governing permissions and
   * limitations under the License.
   *)
*/
//# sourceMappingURL=main.js.map
