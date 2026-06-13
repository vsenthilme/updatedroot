import {
  AuthService,
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-HJPREMTO.js";

// src/app/main/id-masters/uom/uom.service.ts
var _UomService = class _UomService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(uomId) {
    return this.http.get("/overc-idmaster-service/uom/" + uomId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/uom", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/uom/" + obj.uomId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId, obj);
  }
  Delete(uomId) {
    return this.http.delete("/overc-idmaster-service/uom/" + uomId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/uom/find", obj);
  }
};
_UomService.\u0275fac = function UomService_Factory(t) {
  return new (t || _UomService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_UomService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _UomService, factory: _UomService.\u0275fac, providedIn: "root" });
var UomService = _UomService;

export {
  UomService
};
//# sourceMappingURL=chunk-SCTWSUDV.js.map
