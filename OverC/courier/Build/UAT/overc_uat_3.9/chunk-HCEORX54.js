import {
  BehaviorSubject,
  ɵɵdefineInjectable
} from "./chunk-Z5YEPTLN.js";

// src/app/common-service/path-name.service.ts
var _PathNameService = class _PathNameService {
  constructor() {
    this.dataArraySubject = new BehaviorSubject([]);
    this.dataArray$ = this.dataArraySubject.asObservable();
  }
  setData(data) {
    this.dataArraySubject.next(data);
  }
};
_PathNameService.\u0275fac = function PathNameService_Factory(t) {
  return new (t || _PathNameService)();
};
_PathNameService.\u0275prov = /* @__PURE__ */ \u0275\u0275defineInjectable({ token: _PathNameService, factory: _PathNameService.\u0275fac, providedIn: "root" });
var PathNameService = _PathNameService;

export {
  PathNameService
};
//# sourceMappingURL=chunk-HCEORX54.js.map
