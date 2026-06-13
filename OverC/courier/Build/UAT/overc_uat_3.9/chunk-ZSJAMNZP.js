import {
  AuthService
} from "./chunk-XFYC4BWK.js";
import {
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-Z5YEPTLN.js";

// src/app/main/id-masters/numberrange/numberrange.service.ts
var _NumberrangeService = class _NumberrangeService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(obj) {
    return this.http.get("/overc-idmaster-service/numberRange" + obj.numberRangeCode + "?languageId=" + obj.languageId + "&numberRangeObject=" + obj.numberRangeObject);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/numberRange", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/numberRange/" + obj.numberRangeCode + "?languageId=" + this.auth.languageId + "&numberRangeObject=" + obj.numberRangeObject, obj);
  }
  Delete(obj) {
    return this.http.delete("/overc-idmaster-service/numberRange/" + obj.numberRangeCode + "?languageId=" + this.auth.languageId + "&numberRangeObject=" + obj.numberRangeObject);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/numberRange/find", obj);
  }
};
_NumberrangeService.\u0275fac = function NumberrangeService_Factory(t) {
  return new (t || _NumberrangeService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_NumberrangeService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _NumberrangeService, factory: _NumberrangeService.\u0275fac, providedIn: "root" });
var NumberrangeService = _NumberrangeService;

export {
  NumberrangeService
};
//# sourceMappingURL=chunk-ZSJAMNZP.js.map
