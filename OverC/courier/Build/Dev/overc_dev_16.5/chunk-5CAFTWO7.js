import {
  AuthService,
  CommonModule,
  DefaultValueAccessor,
  FormBuilder,
  FormControlName,
  FormGroupDirective,
  InputText,
  MatButton,
  MessageService,
  NgControlStatus,
  NgControlStatusGroup,
  Password,
  Router,
  RouterModule,
  SharedModule,
  TrimDirective,
  Validators,
  ɵNgNoValidate,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵdefineComponent,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵlistener,
  ɵɵproperty,
  ɵɵtext
} from "./chunk-HJPREMTO.js";

// src/app/login/login.component.ts
var _LoginComponent = class _LoginComponent {
  constructor(router, fb, messageService, auth) {
    this.router = router;
    this.fb = fb;
    this.messageService = messageService;
    this.auth = auth;
    this.hide = true;
    this.lgForm = this.fb.group({
      userName: ["", Validators.required],
      password: ["", Validators.required]
    });
  }
  login() {
    if (this.lgForm.invalid) {
      this.messageService.add({ severity: "error", summary: "Error", key: "br", detail: "Please fill required fields to continue" });
      return;
    }
    this.auth.login(this.lgForm.value);
  }
};
_LoginComponent.\u0275fac = function LoginComponent_Factory(t) {
  return new (t || _LoginComponent)(\u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(FormBuilder), \u0275\u0275directiveInject(MessageService), \u0275\u0275directiveInject(AuthService));
};
_LoginComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _LoginComponent, selectors: [["app-login"]], decls: 17, vars: 3, consts: [[1, "d-flex", "vh-100", "justify-content-center", "align-items-center", "loginBg"], [1, "cardLogin"], ["src", "./assets/login/logo.png", "alt", "", "srcset", "", 1, "img-fluid", 2, "width", "15rem"], [1, "Welcome"], [1, "subText"], [1, "d-flex", "justify-content-center", "align-items-center", "mt-4", 3, "ngSubmit", "formGroup"], [1, "mx-auto"], [1, "form-group", "mb-0", "mt-3"], ["type", "text", "placeholder", "User ID", "pInputText", "", "appTrim", "", "formControlName", "userName", 1, "w-100"], ["placeholder", "Password", "formControlName", "password", 3, "feedback", "toggleMask"], [1, "Forgot", "mt-1", "d-flex", "float-right"], ["mat-button", "", "type", "Submit", 1, "signIn", 2, "color", "white", "width", "21rem"]], template: function LoginComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 0)(1, "div", 1);
    \u0275\u0275element(2, "img", 2);
    \u0275\u0275elementStart(3, "p", 3);
    \u0275\u0275text(4, "Welcome");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(5, "p", 4);
    \u0275\u0275text(6, "Enter your user id and password to log in.");
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(7, "form", 5);
    \u0275\u0275listener("ngSubmit", function LoginComponent_Template_form_ngSubmit_7_listener() {
      return ctx.login();
    });
    \u0275\u0275elementStart(8, "div", 6)(9, "div", 7);
    \u0275\u0275element(10, "input", 8);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(11, "div", 7);
    \u0275\u0275element(12, "p-password", 9);
    \u0275\u0275elementStart(13, "div", 10);
    \u0275\u0275text(14, "Forgot Password?");
    \u0275\u0275elementEnd()();
    \u0275\u0275elementStart(15, "button", 11);
    \u0275\u0275text(16, "Sign In");
    \u0275\u0275elementEnd()()()()();
  }
  if (rf & 2) {
    \u0275\u0275advance(7);
    \u0275\u0275property("formGroup", ctx.lgForm);
    \u0275\u0275advance(5);
    \u0275\u0275property("feedback", false)("toggleMask", true);
  }
}, dependencies: [InputText, \u0275NgNoValidate, DefaultValueAccessor, NgControlStatus, NgControlStatusGroup, Password, MatButton, FormGroupDirective, FormControlName, TrimDirective], styles: ['\n\n.loginBg[_ngcontent-%COMP%] {\n  background-image: url("./media/loginPage.png");\n  background-size: cover;\n  background-repeat: no-repeat;\n  background-position: center;\n}\n.cardLogin[_ngcontent-%COMP%] {\n  display: flex;\n  flex-direction: column;\n  justify-content: center;\n  align-items: center;\n  padding: 4rem;\n  border-radius: 30px;\n  box-shadow: 0 0 200px 0 rgba(0, 0, 0, 0.25);\n  background-color: var(--white);\n}\n.Welcome[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 24px;\n  font-weight: bold;\n  font-stretch: normal;\n  padding-top: 3rem;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: center;\n  color: #000;\n}\n.subText[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 14px;\n  font-weight: normal;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: center;\n  color: #767676;\n}\n.Forgot[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 12px;\n  font-weight: 500;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n  color: var(--black);\n}\n.signIn[_ngcontent-%COMP%] {\n  flex-grow: 0;\n  font-family: Nunito;\n  font-size: 16px;\n  font-weight: 500;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: center;\n  color: var(--white);\n  margin-top: 2.7rem !important;\n  flex-grow: 0;\n  display: flex;\n  flex-direction: row;\n  justify-content: center;\n  align-items: center;\n  border-radius: 8px;\n  background-color: var(--black);\n}\n/*# sourceMappingURL=login.component.css.map */'] });
var LoginComponent = _LoginComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(LoginComponent, { className: "LoginComponent", filePath: "src\\app\\login\\login.component.ts", lineNumber: 12 });
})();

// src/app/login/login-routing.module.ts
var routes = [{ path: "", component: LoginComponent }];
var _LoginRoutingModule = class _LoginRoutingModule {
};
_LoginRoutingModule.\u0275fac = function LoginRoutingModule_Factory(t) {
  return new (t || _LoginRoutingModule)();
};
_LoginRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _LoginRoutingModule });
_LoginRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forChild(routes), RouterModule] });
var LoginRoutingModule = _LoginRoutingModule;

// src/app/login/login.module.ts
var _LoginModule = class _LoginModule {
};
_LoginModule.\u0275fac = function LoginModule_Factory(t) {
  return new (t || _LoginModule)();
};
_LoginModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _LoginModule });
_LoginModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [
  CommonModule,
  LoginRoutingModule,
  SharedModule
] });
var LoginModule = _LoginModule;
export {
  LoginModule
};
//# sourceMappingURL=chunk-5CAFTWO7.js.map
