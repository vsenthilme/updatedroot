import {
  AuthService
} from "./chunk-XFYC4BWK.js";
import {
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-Z5YEPTLN.js";

// src/app/main/id-masters/currency/currency.service.ts
var _CurrencyService = class _CurrencyService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(currencyId) {
    return this.http.get("/overc-idmaster-service/currency/" + currencyId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/currency", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/currency/" + obj.currencyId, obj);
  }
  Delete(currencyId) {
    return this.http.delete("/overc-idmaster-service/currency/" + currencyId);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/currency/find", obj);
  }
};
_CurrencyService.\u0275fac = function CurrencyService_Factory(t) {
  return new (t || _CurrencyService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_CurrencyService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _CurrencyService, factory: _CurrencyService.\u0275fac, providedIn: "root" });
var CurrencyService = _CurrencyService;

export {
  CurrencyService
};
//# sourceMappingURL=chunk-2ENCXAH3.js.map
