import {
  AuthService,
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-HJPREMTO.js";

// src/app/main/master/customs-charges-master/customs-charges-master.service.ts
var _CustomsChargesMasterService = class _CustomsChargesMasterService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(sortingId) {
    return this.http.get("/overc-idmaster-service/sortmaster/create-sort-master/" + sortingId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/customCharge/create/list", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/customCharge/update/list", obj);
  }
  Delete(obj) {
    return this.http.post("/overc-idmaster-service/customCharge/delete/list", obj);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/customCharge/find", obj);
  }
};
_CustomsChargesMasterService.\u0275fac = function CustomsChargesMasterService_Factory(t) {
  return new (t || _CustomsChargesMasterService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_CustomsChargesMasterService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _CustomsChargesMasterService, factory: _CustomsChargesMasterService.\u0275fac, providedIn: "root" });
var CustomsChargesMasterService = _CustomsChargesMasterService;

export {
  CustomsChargesMasterService
};
//# sourceMappingURL=chunk-YOZWP5LT.js.map
