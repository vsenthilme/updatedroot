import {
  AuthService,
  HttpClient,
  ɵɵdefineInjectable,
  ɵɵinject
} from "./chunk-HJPREMTO.js";

// src/app/main/id-masters/province/province.service.ts
var _ProvinceService = class _ProvinceService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get() {
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/province", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/province/" + obj.provinceId + "?companyId=" + this.auth.companyId + "&countryId=" + obj.countryId + "&languageId=" + this.auth.languageId, obj);
  }
  Delete(obj) {
    return this.http.delete("/overc-idmaster-service/province/" + obj.provinceId + "?companyId=" + this.auth.companyId + "&countryId=" + obj.countryId + "&languageId=" + this.auth.languageId);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/province/find", obj);
  }
};
_ProvinceService.\u0275fac = function ProvinceService_Factory(t) {
  return new (t || _ProvinceService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_ProvinceService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _ProvinceService, factory: _ProvinceService.\u0275fac, providedIn: "root" });
var ProvinceService = _ProvinceService;

// src/app/main/id-masters/district/district.service.ts
var _DistrictService = class _DistrictService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(districtId) {
    return this.http.get("/overc-idmaster-service/district/" + districtId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/district", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/district/" + obj.districtId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&countryId=" + obj.countryId + "&provinceId=" + obj.provinceId, obj);
  }
  Delete(obj) {
    return this.http.delete("/overc-idmaster-service/district/" + obj.districtId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId + "&countryId=" + obj.countryId + "&provinceId=" + obj.provinceId);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/district/find", obj);
  }
};
_DistrictService.\u0275fac = function DistrictService_Factory(t) {
  return new (t || _DistrictService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_DistrictService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _DistrictService, factory: _DistrictService.\u0275fac, providedIn: "root" });
var DistrictService = _DistrictService;

// src/app/main/id-masters/country/country.service.ts
var _CountryService = class _CountryService {
  constructor(http, auth) {
    this.http = http;
    this.auth = auth;
  }
  Get(countryId) {
    return this.http.get("/overc-idmaster-service/country/" + countryId);
  }
  Create(obj) {
    return this.http.post("/overc-idmaster-service/country", obj);
  }
  Update(obj) {
    return this.http.patch("/overc-idmaster-service/country/" + obj.countryId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId, obj);
  }
  Delete(countryId) {
    return this.http.delete("/overc-idmaster-service/country/" + countryId + "?languageId=" + this.auth.languageId + "&companyId=" + this.auth.companyId);
  }
  search(obj) {
    return this.http.post("/overc-idmaster-service/country/find", obj);
  }
};
_CountryService.\u0275fac = function CountryService_Factory(t) {
  return new (t || _CountryService)(\u0275\u0275inject(HttpClient), \u0275\u0275inject(AuthService));
};
_CountryService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _CountryService, factory: _CountryService.\u0275fac, providedIn: "root" });
var CountryService = _CountryService;

export {
  ProvinceService,
  DistrictService,
  CountryService
};
//# sourceMappingURL=chunk-NDC7B7MG.js.map
