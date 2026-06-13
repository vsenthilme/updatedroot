import {
  AuthService,
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-HJPREMTO.js";

// src/app/main/airport/console/console.service.ts
var _ConsoleService = class _ConsoleService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(consoleId) {
    return this.http.get("/overc-midmile-service/console/" + consoleId);
  }
  Create(obj) {
    return this.http.post("/overc-midmile-service/console/create/list", obj);
  }
  CreateManual(obj) {
    return this.http.post("/overc-midmile-service/manual/console/create", obj);
  }
  CreateFromConsignment(obj) {
    return this.http.post("/overc-midmile-service/console/prealert", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-midmile-service/console/update/list", obj);
  }
  updateSingle(obj) {
    return this.http.patch("/overc-midmile-service/console/update", obj);
  }
  createCCR(obj) {
    return this.http.patch("/overc-midmile-service/console/update/ccr/create", obj);
  }
  UpdateCCR(obj) {
    return this.http.patch("/overc-midmile-service/ccr/update/list", obj);
  }
  UpdateStatusforConsole(obj) {
    return this.http.post("/overc-midmile-service/console/status-event/update", obj);
  }
  UpdateGatewayScan(obj) {
    return this.http.patch("/overc-midmile-service/console/update/mobileApp", obj);
  }
  Transfer(obj) {
    return this.http.post("/overc-midmile-service/console/transfer", obj);
  }
  Delete(obj) {
    return this.http.post("/overc-midmile-service/console/delete/list", obj);
  }
  search(obj) {
    return this.http.post("/overc-midmile-service/console/findConsole", obj);
  }
  searchUnconsole(obj) {
    return this.http.post("/overc-midmile-service/unconsolidation/find", obj);
  }
  uploadBayan(file, filePath) {
    const formData = new FormData();
    formData.append("file", file);
    return this.http.post("/pdf/extract?filePath=" + filePath + "&loginUserID=" + this.auth.userID, formData);
  }
  createLocation(obj) {
    return this.http.post("/overc-midmile-service/reports/locationSheet", obj);
  }
  searchLocation(obj) {
    return this.http.post("/overc-midmile-service/reports/locationSheet", obj);
  }
};
_ConsoleService.\u0275fac = function ConsoleService_Factory(t) {
  return new (t || _ConsoleService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_ConsoleService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _ConsoleService, factory: _ConsoleService.\u0275fac, providedIn: "root" });
var ConsoleService = _ConsoleService;

export {
  ConsoleService
};
//# sourceMappingURL=chunk-QZFR5JK7.js.map
