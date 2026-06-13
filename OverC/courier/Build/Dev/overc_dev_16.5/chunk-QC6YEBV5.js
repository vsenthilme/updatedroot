import {
  AuthService,
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-HJPREMTO.js";

// src/app/main/master/iata/iata.service.ts
var _IataService = class _IataService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(obj) {
    return this.http.get("/overc-idmaster-service/iata" + obj.originCode + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&origin=" + obj.origin);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/iata", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/iata/" + obj.originCode + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&origin=" + obj.origin, obj);
  }
  Delete(obj) {
    return this.http.delete("/overc-idmaster-service/iata/" + obj.originCode + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&origin=" + obj.origin);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/iata/find", obj);
  }
};
_IataService.\u0275fac = function IataService_Factory(t) {
  return new (t || _IataService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_IataService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _IataService, factory: _IataService.\u0275fac, providedIn: "root" });
var IataService = _IataService;

export {
  IataService
};
//# sourceMappingURL=chunk-QC6YEBV5.js.map
