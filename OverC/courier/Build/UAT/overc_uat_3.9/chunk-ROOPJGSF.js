import {
  AuthService
} from "./chunk-XFYC4BWK.js";
import {
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-Z5YEPTLN.js";

// src/app/main/master/customer/customer.service.ts
var _CustomerService = class _CustomerService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(customerId, productId, subProductId) {
    return this.http.get("/overc-idmaster-service/customer/" + customerId + "?productId=" + productId + "&subProductId=" + subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/customer", obj);
  }
  CreateBulk(obj) {
    return this.http.post("/overc-idmaster-service/customer/create/list", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/customer/" + obj.customerId + "?productId=" + obj.productId + "&subProductId=" + obj.subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId, obj);
  }
  UpdateBulk(obj) {
    return this.http.patch("/overc-idmaster-service/customer/update/list", obj);
  }
  Delete(obj) {
    return this.http.delete("/overc-idmaster-service/customer/", obj);
  }
  DeleteBulk(obj) {
    return this.http.post("/overc-idmaster-service/customer/delete/list", obj);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/customer/find", obj);
  }
};
_CustomerService.\u0275fac = function CustomerService_Factory(t) {
  return new (t || _CustomerService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_CustomerService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _CustomerService, factory: _CustomerService.\u0275fac, providedIn: "root" });
var CustomerService = _CustomerService;

// src/app/main/master/consignor/consignor.service.ts
var _ConsignorService = class _ConsignorService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(consignorId, customerId, productId, subProductId) {
    return this.http.get("/overc-idmaster-service/consignor/" + consignorId + "?customerId=" + customerId + "&productId=" + productId + "&subProductId=" + subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/consignor", obj);
  }
  CreateBulk(obj) {
    return this.http.post("/overc-idmaster-service/consignor/create/list", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/consignor/" + obj.consignorId + "?customerId=" + obj.customerId + "&productId=" + obj.productId + "&subProductId=" + obj.subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId, obj);
  }
  UpdateBulk(obj) {
    return this.http.patch("/overc-idmaster-service/consignor/update/list", obj);
  }
  Delete(consignorId, customerId, productId, subProductId) {
    return this.http.delete("/overc-idmaster-service/consignor/" + consignorId + "?customerId=" + customerId + "&productId=" + productId + "&subProductId=" + subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  DeleteBulk(obj) {
    return this.http.post("/overc-idmaster-service/consignor/delete/list", obj);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/consignor/find", obj);
  }
};
_ConsignorService.\u0275fac = function ConsignorService_Factory(t) {
  return new (t || _ConsignorService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_ConsignorService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _ConsignorService, factory: _ConsignorService.\u0275fac, providedIn: "root" });
var ConsignorService = _ConsignorService;

export {
  CustomerService,
  ConsignorService
};
//# sourceMappingURL=chunk-ROOPJGSF.js.map
