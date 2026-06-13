import {
  AuthService
} from "./chunk-XFYC4BWK.js";
import {
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-Z5YEPTLN.js";

// src/app/main/id-masters/product/product.service.ts
var _ProductService = class _ProductService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(productId, subProductId) {
    return this.http.get("/overc-idmaster-service/product/" + productId + "?subProductId=" + subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/product", obj);
  }
  CreateBulk(obj) {
    return this.http.post("/overc-idmaster-service/product/create/list", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/product/" + obj.productId + "?subProductId=" + obj.subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId, obj);
  }
  UpdateBulk(obj) {
    return this.http.patch("/overc-idmaster-service/product/update/list", obj);
  }
  Delete(productId, subProductId) {
    return this.http.delete("/overc-idmaster-service/product/" + productId + "?subProductId=" + subProductId + "&companyId=" + this.auth.companyId + "&languageId=" + this.auth.languageId);
  }
  DeleteBulk(obj) {
    return this.http.post("/overc-idmaster-service/product/delete/list", obj);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/product/find", obj);
  }
};
_ProductService.\u0275fac = function ProductService_Factory(t) {
  return new (t || _ProductService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_ProductService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _ProductService, factory: _ProductService.\u0275fac, providedIn: "root" });
var ProductService = _ProductService;

export {
  ProductService
};
//# sourceMappingURL=chunk-QNQSICHN.js.map
