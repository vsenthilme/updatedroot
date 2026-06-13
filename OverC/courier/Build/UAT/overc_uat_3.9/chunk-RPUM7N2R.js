import {
  CommonModule,
  RouterModule,
  ɵɵdefineInjector,
  ɵɵdefineNgModule
} from "./chunk-Z5YEPTLN.js";

// src/app/main/last-mile/last-mile-routing.module.ts
var routes = [];
var _LastMileRoutingModule = class _LastMileRoutingModule {
};
_LastMileRoutingModule.\u0275fac = function LastMileRoutingModule_Factory(t) {
  return new (t || _LastMileRoutingModule)();
};
_LastMileRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _LastMileRoutingModule });
_LastMileRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forChild(routes), RouterModule] });
var LastMileRoutingModule = _LastMileRoutingModule;

// src/app/main/last-mile/last-mile.module.ts
var _LastMileModule = class _LastMileModule {
};
_LastMileModule.\u0275fac = function LastMileModule_Factory(t) {
  return new (t || _LastMileModule)();
};
_LastMileModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _LastMileModule });
_LastMileModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [
  CommonModule,
  LastMileRoutingModule
] });
var LastMileModule = _LastMileModule;
export {
  LastMileModule
};
//# sourceMappingURL=chunk-RPUM7N2R.js.map
