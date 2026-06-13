import {
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-HJPREMTO.js";

// src/app/main/airport/pre-alert-manifest/prealert.service.ts
var _PrealertService = class _PrealertService {
  constructor(http) {
    this.http = http;
  }
  search(obj) {
    return this.http.post("/overc-midmile-service/prealert/findPrealert", obj);
  }
  Delete(obj) {
    return this.http.post("/overc-midmile-service/prealert/delete/list", obj);
  }
  update(obj) {
    return this.http.patch("/overc-midmile-service/prealert/update/list", obj);
  }
};
_PrealertService.\u0275fac = function PrealertService_Factory(t) {
  return new (t || _PrealertService)(\u0275\u0275inject(HttpClient));
};
_PrealertService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _PrealertService, factory: _PrealertService.\u0275fac, providedIn: "root" });
var PrealertService = _PrealertService;

export {
  PrealertService
};
//# sourceMappingURL=chunk-JJZSPPOK.js.map
