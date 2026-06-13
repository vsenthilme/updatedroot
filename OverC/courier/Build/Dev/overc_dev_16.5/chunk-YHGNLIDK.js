import {
  PathNameService
} from "./chunk-O6NDCGFA.js";
import {
  ActivatedRoute,
  AuthService,
  BreakpointObserver,
  ChangeDetectorRef,
  CommonModule,
  EventEmitter,
  MatIcon,
  MatMenu,
  MatMenuContent,
  MatMenuItem,
  MatMenuTrigger,
  MatNavList,
  MatSidenav,
  MatSidenavContainer,
  MatSidenavContent,
  NavigationEnd,
  NgClass,
  NgForOf,
  NgIf,
  NgStyle,
  Router,
  RouterLink,
  RouterLinkActive,
  RouterModule,
  RouterOutlet,
  SharedModule,
  Subscription,
  Title,
  animate,
  filter,
  state,
  style,
  transition,
  trigger,
  ɵsetClassDebugInfo,
  ɵɵadvance,
  ɵɵclassProp,
  ɵɵdefineComponent,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵdirectiveInject,
  ɵɵelement,
  ɵɵelementEnd,
  ɵɵelementStart,
  ɵɵgetCurrentView,
  ɵɵlistener,
  ɵɵnextContext,
  ɵɵproperty,
  ɵɵpureFunction0,
  ɵɵpureFunction1,
  ɵɵpureFunction2,
  ɵɵreference,
  ɵɵresetView,
  ɵɵrestoreView,
  ɵɵsanitizeUrl,
  ɵɵtemplate,
  ɵɵtext,
  ɵɵtextInterpolate
} from "./chunk-HJPREMTO.js";

// src/app/main/nav-data.ts
var navbarData = [
  {
    routerId: 1e3,
    routerLink: "dashboard",
    src: "./assets/sidebar/dashboard/DashboardLight.png",
    srcDark: "./assets/sidebar/dashboard/Dashboard.png",
    label: "Dashboard"
  },
  {
    routerId: 2e3,
    routerLink: "/main/idMaster",
    src: "./assets/sidebar/setting/Seting.png",
    srcDark: "./assets/sidebar/setting/SetingDark.png",
    label: "Setup",
    items: [
      {
        routerId: 2001,
        // routerLink: 'setup/list',
        label: "Admin",
        items: [
          {
            routerId: 2101,
            routerLink: "/main/idMaster/language",
            label: "Language"
          },
          {
            routerId: 2102,
            routerLink: "/main/idMaster/status",
            label: "Status"
          },
          {
            routerId: 2103,
            routerLink: "/main/idMaster/currency",
            label: "Currency"
          },
          {
            routerId: 2104,
            routerLink: "/main/idMaster/menu",
            label: "Menu"
          },
          {
            routerId: 2105,
            routerLink: "/main/idMaster/module",
            label: "Module"
          },
          {
            routerId: 2106,
            routerLink: "/main/idMaster/numberrange",
            label: "Number Series"
          },
          {
            routerId: 2107,
            routerLink: "/main/idMaster/userrole",
            label: "User Role"
          },
          {
            routerId: 2108,
            routerLink: "/main/idMaster/users",
            label: "User"
          },
          {
            routerId: 2109,
            routerLink: "/main/idMaster/notification",
            label: "Notification"
          },
          {
            routerId: 2110,
            routerLink: "/main/idMaster/appUser",
            label: "App User"
          },
          {
            routerId: 2111,
            routerLink: "/main/idMaster/uom",
            label: "UOM"
          }
        ]
      },
      {
        routerId: 2002,
        // routerLink: 'setup/list',
        label: "Organization",
        items: [
          {
            routerId: 2201,
            routerLink: "/main/idMaster/company",
            label: "Company"
          },
          {
            routerId: 2202,
            routerLink: "/main/idMaster/product",
            label: "Product"
          },
          {
            routerId: 2203,
            routerLink: "/main/idMaster/subProduct",
            label: "Sub Product"
          },
          {
            routerId: 2204,
            routerLink: "/main/idMaster/country",
            label: "Country"
          },
          {
            routerId: 2205,
            routerLink: "/main/idMaster/province",
            label: "Province"
          },
          {
            routerId: 2206,
            routerLink: "/main/idMaster/district",
            label: "District"
          },
          {
            routerId: 2207,
            routerLink: "/main/idMaster/city",
            label: "City"
          }
        ]
      },
      {
        routerId: 2003,
        // routerLink: 'setup/list',
        label: "Business",
        items: [
          {
            routerId: 2301,
            routerLink: "/main/idMaster/serviceType",
            label: "Service Type"
          },
          {
            routerId: 2302,
            routerLink: "/main/idMaster/consignmentType",
            label: "Consignment Type"
          },
          {
            routerId: 2303,
            routerLink: "/main/idMaster/loadType",
            label: "Load Type"
          },
          {
            routerId: 2304,
            routerLink: "/main/idMaster/rateParameter",
            label: "Rate Parameter"
          },
          {
            routerId: 2305,
            routerLink: "/main/idMaster/statusevent",
            label: "Status Event"
          }
        ]
      }
    ]
  },
  {
    routerId: 3e3,
    routerLink: "/main/master",
    src: "./assets/sidebar/masters/Database.png",
    srcDark: "./assets/sidebar/masters/DatabaseDark.png",
    label: "Masters",
    items: [
      // {
      //     routerId: 3002,
      //     // routerLink: 'setup/list',
      //     label: 'Admin',
      //     items: [
      //         // {
      //         //     routerId: 3201,
      //         //     routerLink: '/main/master/hub',
      //         //     label: 'Hub'
      //         // },
      //     ]
      // },
      // {
      //     routerId: 3001,
      //     // routerLink: 'setup/list',
      //     label: 'Operations',
      //     items: [
      //         {
      //             routerId: 3101,
      //             routerLink: '/main/master/consignor',
      //             label: 'Consignor'
      //         },
      //     ]
      // },
      {
        routerId: 3003,
        // routerLink: 'setup/list',
        label: "Mid-Mile",
        items: [
          {
            routerId: 3301,
            routerLink: "/main/master/iata",
            label: "IATA"
          },
          {
            routerId: 3302,
            routerLink: "/main/master/specialApproval",
            label: "Special Approval"
          },
          {
            routerId: 3303,
            routerLink: "/main/master/hsCode",
            label: "HS Code"
          },
          {
            routerId: 3304,
            routerLink: "/main/master/logicMaster",
            label: "Console Validation"
          }
        ]
      },
      {
        routerId: 3004,
        // routerLink: 'setup/list',
        label: "Finance",
        items: [
          {
            routerId: 3401,
            routerLink: "/main/master/billMode",
            label: "Bill Mode"
          },
          {
            routerId: 3402,
            routerLink: "/main/master/rate",
            label: "Rate"
          },
          {
            routerId: 3403,
            routerLink: "/main/master/currencyExchangeRate",
            label: "Exchange Rate"
          },
          {
            routerId: 3404,
            routerLink: "/main/master/clearanceCharges",
            label: "Clearance Charges"
          },
          {
            routerId: 3405,
            routerLink: "/main/master/customer",
            label: "Customer"
          },
          {
            routerId: 3406,
            routerLink: "/main/master/customsChargesMaster",
            label: "Custom Charges"
          }
        ]
      },
      {
        routerId: 3005,
        // routerLink: 'setup/list',
        label: "Last-Mile",
        items: [
          {
            routerId: 3501,
            routerLink: "/main/master/cityMapping",
            label: "City Mapping"
          },
          {
            routerId: 3502,
            routerLink: "/main/master/districtMapping",
            label: "District Mapping"
          },
          {
            routerId: 3503,
            routerLink: "/main/master/provinceMapping",
            label: "Province Mapping"
          },
          {
            routerId: 3504,
            routerLink: "/main/master/countryMapping",
            label: "Country Mapping"
          },
          {
            routerId: 3505,
            routerLink: "/main/master/hubPartnerAssignment",
            label: "Hub Partner Assignment"
          },
          {
            routerId: 3506,
            routerLink: "/main/master/courierPartner",
            label: "Courier Partner "
          },
          {
            routerId: 3507,
            routerLink: "/main/master/route",
            label: "Route"
          },
          {
            routerId: 3508,
            routerLink: "/main/master/vehicle",
            label: "Vehicle"
          },
          {
            routerId: 3509,
            routerLink: "/main/master/driverRouteAssignment",
            label: "Driver Route Assignment"
          },
          {
            routerId: 3510,
            routerLink: "/main/master/zoneMaster",
            label: "Zone Master"
          },
          {
            routerId: 3511,
            routerLink: "/main/master/storageTypeMaster",
            label: "Storage Type Master"
          },
          {
            routerId: 3512,
            routerLink: "/main/master/zoneTypeMaster",
            label: "Zone Type Master"
          }
        ]
      }
    ]
  },
  // {
  //     routerId: 1000,
  //     routerLink: 'master',
  //     src: './assets/sidebar/service/Service.png',
  //     srcDark: './assets/sidebar/service/ServiceDark.png',
  //     label: 'Customer',
  // items: [
  //     {
  //         routerId: 1000,
  //         routerLink: 'setup/list',
  //         label: 'Rates'
  //     },
  //     {
  //         routerId: 1000,
  //         routerLink: 'setup/list',
  //         label: 'Users'
  //     },
  //     {
  //         routerId: 1000,
  //         routerLink: 'setup/list',
  //         label: 'Product'
  //     },
  // ]
  // },
  {
    routerId: 5e3,
    routerLink: "/main/airport",
    src: "./assets/sidebar/airport/Airport Hub.png",
    srcDark: "./assets/sidebar/airport/Airport HubDark.png",
    label: "Mid-Mile",
    items: [
      {
        routerId: 5101,
        routerLink: "/main/airport/preAlertManifest",
        label: "Pre Alert Manifest"
      },
      // {
      //     routerId: 5102,
      //     routerLink: '/main/airport/bondedManifest',
      //     label: 'Bonded Manifest'
      // },
      {
        routerId: 5103,
        routerLink: "/main/airport/console",
        label: "Console"
      },
      {
        routerId: 5108,
        routerLink: "/main/airport/costingSheet",
        label: "Customs Costing"
      },
      {
        routerId: 5109,
        routerLink: "/main/airport/dduInvoice",
        label: "DDU Invoice"
      },
      {
        routerId: 5001,
        label: "Reports",
        items: [
          {
            routerId: 5105,
            routerLink: "/main/reports/consoleTracking",
            label: "Console Tracking"
          },
          {
            routerId: 5106,
            routerLink: "/main/reports/pendingCustoms",
            label: "Pending\xA0Customs"
          },
          {
            routerId: 5107,
            routerLink: "/main/reports/inventoryScanning",
            label: "Inventory Scan"
          },
          {
            routerId: 5108,
            routerLink: "/main/reports/customsCalculations",
            label: "Customs Calculation"
          }
        ]
      }
      // {
      //     routerId: 5103,
      //     routerLink: '/main/airport/consolidatedManifest',
      //     label: 'Consolidated Manifest'
      // },
      // {
      //     routerId: 5104,
      //     routerLink: '/main/airport/ccr',
      //     label: 'CCR'
      // },
    ]
  },
  {
    routerId: 6e3,
    routerLink: "dashboard",
    src: "./assets/sidebar/operations/oprations.png",
    srcDark: "./assets/sidebar/operations/oprationsDark.png",
    label: "Operations",
    items: [
      {
        routerId: 6101,
        routerLink: "/main/operation/consignment",
        label: "Consignment"
      },
      // {
      //     routerId: 6102,
      //     routerLink: '/main/operation/consignmentStatus',
      //     label: 'Consignment Status'
      // },
      // {
      //     routerId: 6103,
      //     routerLink: '/main/operation/consignmentUploadProgram',
      //     label: 'Consignment Upload Program'
      // }
      {
        routerId: 6102,
        routerLink: "/main/operation/assignment",
        label: "Assignment"
      }
    ]
  },
  {
    routerId: 7e3,
    routerLink: "masters",
    src: "./assets/sidebar/billing/billing.png",
    srcDark: "./assets/sidebar/billing/billingDark.png",
    label: "Finance",
    items: [
      {
        routerId: 7101,
        routerLink: "/main/finance/costingSheet",
        label: "Customs Costing"
      },
      {
        routerId: 7102,
        routerLink: "/main/finance/invoice",
        label: "Customs Invoice"
      },
      {
        routerId: 7001,
        label: "Reports",
        items: [
          {
            routerId: 7103,
            routerLink: "/main/reports/costSheet",
            label: "Customs Costing"
          },
          {
            routerId: 7104,
            routerLink: "/main/reports/expenseSheet",
            label: "Customs Fees"
          }
        ]
      }
      // {
      //     routerId: 1000,
      //     routerLink: 'setup/list',
      //     label: 'Rates'
      // },
      // {
      //     routerId: 1000,
      //     routerLink: 'setup/list',
      //     label: 'Users'
      // },
      // {
      //     routerId: 1000,
      //     routerLink: 'setup/list',
      //     label: 'Product'
      // },
    ]
  },
  {
    routerId: 1e3,
    routerLink: "masters",
    src: "./assets/sidebar/integration/Integration.png",
    srcDark: "./assets/sidebar/integration/IntegrationDark.png",
    label: "Integration",
    items: [
      {
        routerId: 1e3,
        routerLink: "setup/list",
        label: "Rates"
      },
      {
        routerId: 1e3,
        routerLink: "setup/list",
        label: "Users"
      },
      {
        routerId: 1e3,
        routerLink: "setup/list",
        label: "Product"
      }
    ]
  },
  {
    routerId: 1e3,
    routerLink: "masters",
    src: "./assets/sidebar/reports/report.png",
    srcDark: "./assets/sidebar/reports/reportDark.png",
    label: "Reports",
    items: [
      {
        routerId: 1e3,
        routerLink: "setup/list",
        label: "Rates"
      },
      {
        routerId: 1e3,
        routerLink: "setup/list",
        label: "Users"
      },
      {
        routerId: 1e3,
        routerLink: "setup/list",
        label: "Product"
      }
    ]
  }
];

// src/app/main/sublevel-menu.component.ts
var _c0 = () => ({ transitionParams: "400ms cubic-bezier(0.86, 0, 0.07, 1)", height: "*" });
var _c1 = (a0) => ({ value: "visible", params: a0 });
var _c2 = () => ({ transitionParams: "400ms cubic-bezier(0.86, 0, 0.07, 1)", height: "0" });
var _c3 = (a0) => ({ value: "hidden", params: a0 });
var _c4 = (a0, a1) => ({ "active": a0, "d-none": a1 });
var _c5 = () => ({ exact: true });
function SublevelMenuComponent_ul_0_li_1_a_3_span_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span", 12);
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const item_r2 = \u0275\u0275nextContext(2).$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(item_r2.label);
  }
}
function SublevelMenuComponent_ul_0_li_1_a_3_mat_icon_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "mat-icon", 13);
    \u0275\u0275text(1, "keyboard_arrow_right");
    \u0275\u0275elementEnd();
  }
}
function SublevelMenuComponent_ul_0_li_1_a_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "a", 9);
    \u0275\u0275template(1, SublevelMenuComponent_ul_0_li_1_a_3_span_1_Template, 2, 1, "span", 10)(2, SublevelMenuComponent_ul_0_li_1_a_3_mat_icon_2_Template, 2, 0, "mat-icon", 11);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const item_r2 = \u0275\u0275nextContext().$implicit;
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275property("ngClass", ctx_r2.checkMenu(item_r2.routerId) ? "d-none" : "");
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.collapsed);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", item_r2.items && item_r2.items.length > 0);
  }
}
function SublevelMenuComponent_ul_0_li_1_a_4_span_1_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span", 12);
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const item_r2 = \u0275\u0275nextContext(2).$implicit;
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(item_r2.label);
  }
}
function SublevelMenuComponent_ul_0_li_1_a_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "a", 14);
    \u0275\u0275template(1, SublevelMenuComponent_ul_0_li_1_a_4_span_1_Template, 2, 1, "span", 10);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const item_r2 = \u0275\u0275nextContext().$implicit;
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275property("ngClass", ctx_r2.checkSubMenu(item_r2.routerId) ? "d-none" : "")("routerLinkActiveOptions", \u0275\u0275pureFunction0(3, _c5));
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.collapsed);
  }
}
function SublevelMenuComponent_ul_0_li_1_div_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div", 15);
    \u0275\u0275element(1, "app-sublevel-menu", 16);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const item_r2 = \u0275\u0275nextContext().$implicit;
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275advance();
    \u0275\u0275property("collapsed", ctx_r2.collapsed)("multiple", ctx_r2.multiple)("expanded", item_r2.expanded)("data", item_r2);
  }
}
function SublevelMenuComponent_ul_0_li_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "li", 3);
    \u0275\u0275listener("click", function SublevelMenuComponent_ul_0_li_1_Template_li_click_0_listener() {
      const item_r2 = \u0275\u0275restoreView(_r1).$implicit;
      const ctx_r2 = \u0275\u0275nextContext(2);
      return \u0275\u0275resetView(ctx_r2.handleClick(item_r2));
    });
    \u0275\u0275element(1, "div", 4);
    \u0275\u0275elementStart(2, "div", 5);
    \u0275\u0275template(3, SublevelMenuComponent_ul_0_li_1_a_3_Template, 3, 3, "a", 6);
    \u0275\u0275elementEnd();
    \u0275\u0275template(4, SublevelMenuComponent_ul_0_li_1_a_4_Template, 2, 4, "a", 7)(5, SublevelMenuComponent_ul_0_li_1_div_5_Template, 2, 4, "div", 8);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const item_r2 = ctx.$implicit;
    const ctx_r2 = \u0275\u0275nextContext(2);
    \u0275\u0275classProp("expanded", item_r2.expanded);
    \u0275\u0275property("routerLink", item_r2.routerLink);
    \u0275\u0275advance();
    \u0275\u0275property("ngClass", \u0275\u0275pureFunction2(7, _c4, ctx_r2.isActive(item_r2.routerLink), ctx_r2.checkSubMenu(item_r2.routerId)));
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", item_r2.items && item_r2.items.length > 0);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", !item_r2.items || item_r2.items && item_r2.items.length === 0);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", item_r2.items && item_r2.items.length > 0);
  }
}
function SublevelMenuComponent_ul_0_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "ul", 1);
    \u0275\u0275template(1, SublevelMenuComponent_ul_0_li_1_Template, 6, 10, "li", 2);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("@submenu", ctx_r2.expanded ? \u0275\u0275pureFunction1(3, _c1, \u0275\u0275pureFunction0(2, _c0)) : \u0275\u0275pureFunction1(6, _c3, \u0275\u0275pureFunction0(5, _c2)));
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", ctx_r2.data.items);
  }
}
var _SublevelMenuComponent = class _SublevelMenuComponent {
  constructor(router, auth) {
    this.router = router;
    this.auth = auth;
    this.data = {
      routerId: 1,
      routerLink: "",
      src: "",
      srcDark: "",
      label: "",
      items: []
    };
    this.collapsed = false;
  }
  ngOnInit() {
  }
  handleClick(item) {
    if (!this.multiple) {
      if (this.data.items && this.data.items.length > 0) {
        for (let modelItem of this.data.items) {
          if (item !== modelItem && modelItem.expanded) {
            modelItem.expanded = false;
          }
        }
      }
    }
    item.expanded = !item.expanded;
  }
  isActive(routerLink) {
    if (!routerLink) {
      return false;
    }
    const options = {
      paths: "exact",
      queryParams: "ignored",
      matrixParams: "exact",
      fragment: "exact"
    };
    return this.router.isActive(routerLink, options);
  }
  checkMenu(id) {
    let fileterdata = this.auth.MenuData.filter((x) => x.menuId == id && (x.view || x.delete || x.createUpdate));
    if (fileterdata.length > 0) {
      return false;
    }
    return true;
  }
  checkSubMenu(id) {
    let fileterdata = this.auth.MenuData.filter((x) => x.subMenuId == id && (x.view || x.delete || x.createUpdate));
    if (fileterdata.length > 0) {
      return false;
    }
    return true;
  }
};
_SublevelMenuComponent.\u0275fac = function SublevelMenuComponent_Factory(t) {
  return new (t || _SublevelMenuComponent)(\u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(AuthService));
};
_SublevelMenuComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _SublevelMenuComponent, selectors: [["app-sublevel-menu"]], inputs: { data: "data", collapsed: "collapsed", animating: "animating", expanded: "expanded", multiple: "multiple" }, decls: 1, vars: 1, consts: [["class", "sublevel-nav", 4, "ngIf"], [1, "sublevel-nav"], ["class", "sublevel-nav-item", 3, "expanded", "routerLink", "click", 4, "ngFor", "ngForOf"], [1, "sublevel-nav-item", 3, "click", "routerLink"], [1, "listSelector", 3, "ngClass"], [1, "sublevel-nav-box"], ["class", "sublevel-nav-link", 3, "ngClass", 4, "ngIf"], ["class", "sublevel-nav-link", "routerLinkActive", "active-sublevel", 3, "ngClass", "routerLinkActiveOptions", 4, "ngIf"], ["class", "sublevel-nav-link-container", 4, "ngIf"], [1, "sublevel-nav-link", 3, "ngClass"], ["class", "sublevel-link-text", 4, "ngIf"], ["class", "arrow-icon", 4, "ngIf"], [1, "sublevel-link-text"], [1, "arrow-icon"], ["routerLinkActive", "active-sublevel", 1, "sublevel-nav-link", 3, "ngClass", "routerLinkActiveOptions"], [1, "sublevel-nav-link-container"], [3, "collapsed", "multiple", "expanded", "data"]], template: function SublevelMenuComponent_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275template(0, SublevelMenuComponent_ul_0_Template, 2, 8, "ul", 0);
  }
  if (rf & 2) {
    \u0275\u0275property("ngIf", ctx.collapsed && ctx.data.items && ctx.data.items.length > 0);
  }
}, dependencies: [NgClass, NgForOf, NgIf, RouterLink, RouterLinkActive, MatIcon, _SublevelMenuComponent], styles: ["\n\nmat-sidenav-container[_ngcontent-%COMP%] {\n  height: 100%;\n  scroll-behavior: smooth;\n}\nmat-sidenav-container[_ngcontent-%COMP%]   mat-sidenav-content[_ngcontent-%COMP%] {\n  transition: all 0.6s ease;\n}\n  .mat-drawer-container {\n  background-color: #ededed !important;\n}\nmat-sidenav[_ngcontent-%COMP%]   .entry[_ngcontent-%COMP%] {\n  display: flex;\n  align-items: center;\n  gap: 1rem;\n}\nmat-sidenav[_ngcontent-%COMP%]   mat-nav-list[_ngcontent-%COMP%] {\n  margin: 0;\n  padding: 0;\n}\n#overc-container[_ngcontent-%COMP%] {\n  display: flex;\n  align-items: center;\n  justify-content: center;\n}\n.sidenav[_ngcontent-%COMP%] {\n  background: #000000;\n  transition: all 0.6s ease;\n  position: fixed;\n  z-index: 1;\n  top: 0;\n  width: 6.875rem;\n  height: 100vh;\n  border-radius: 0 40px 40px 0;\n}\n.sidenav[_ngcontent-%COMP%]   .toggle-btn[_ngcontent-%COMP%] {\n  cursor: pointer;\n  background-color: whitesmoke;\n  width: 30px;\n  height: 30px;\n  border-radius: 80px;\n  box-shadow: 4px 0 10px 0 rgba(0, 0, 0, 0.1);\n  z-index: 1;\n  float: right;\n  margin-top: -16px;\n  margin-right: -13px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  transition: all 0.6s ease;\n}\n.sidenav[_ngcontent-%COMP%]   .toggle-btn[_ngcontent-%COMP%]   .btn[_ngcontent-%COMP%] {\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  font-size: x-large;\n}\n.sidenav[_ngcontent-%COMP%]   .logo-container[_ngcontent-%COMP%] {\n  display: flex;\n  justify-content: flex-start;\n  flex-grow: 0;\n  margin: 25px 20px 5px 15px;\n  transition: all 0.6s ease;\n}\n.sidenav[_ngcontent-%COMP%]   .hrline[_ngcontent-%COMP%] {\n  width: 6.875rem;\n  height: 1px;\n  background-color: rgba(255, 255, 255, 0.1);\n  transition: all 0.6s ease;\n  margin-bottom: 0px;\n}\n.sidenav-collapsed[_ngcontent-%COMP%] {\n  width: 18.75rem;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .logo-container[_ngcontent-%COMP%] {\n  display: flex;\n  justify-content: stretch;\n  flex-grow: 0;\n  margin: 25px 20px 5px 70px;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .toggle-btn[_ngcontent-%COMP%] {\n  cursor: pointer;\n  background-color: whitesmoke;\n  width: 30px;\n  height: 30px;\n  border-radius: 80px;\n  box-shadow: 4px 0 10px 0 rgba(0, 0, 0, 0.1);\n  z-index: 1;\n  float: right;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .hrline[_ngcontent-%COMP%] {\n  width: 18.75rem;\n  height: 1px;\n  background-color: rgba(255, 255, 255, 0.1);\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%] {\n  transition: all 0.4s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%] {\n  transition: all 0.4s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link[_ngcontent-%COMP%] {\n  display: flex;\n  flex-direction: row;\n  align-items: center;\n  justify-content: flex-start;\n  gap: 1rem;\n  width: 16.063rem;\n  height: 3rem;\n  flex-grow: 0;\n  padding: 0.75rem 1.25rem;\n  font-size: 16px;\n  color: #f3f3f3;\n  text-decoration: none;\n  border-radius: 0.75rem;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%] {\n  background-color: #fb5;\n  color: #000000;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%]   .sidenav-link-text[_ngcontent-%COMP%] {\n  color: #000000;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%]   .menu-collapse-icon[_ngcontent-%COMP%] {\n  color: #000000;\n}\n.sidenav-nav[_ngcontent-%COMP%] {\n  list-style: none;\n  padding: 0.938rem;\n  margin: 0;\n  display: flex;\n  flex-direction: column;\n  align-items: center;\n  height: 900px;\n  cursor: pointer;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%] {\n  width: 100%;\n  margin-bottom: 0.8rem;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link[_ngcontent-%COMP%] {\n  display: flex;\n  flex-direction: row;\n  align-items: center;\n  justify-content: flex-start;\n  gap: 1rem;\n  width: 4rem;\n  height: 3rem;\n  flex-grow: 0;\n  padding: 0.75rem 1.25rem;\n  color: #f3f3f3;\n  text-decoration: none;\n  border-radius: 0.75rem;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link[_ngcontent-%COMP%]   .sidenav-link-text[_ngcontent-%COMP%] {\n  margin-left: 0.5rem;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]:hover   .sidenav-nav-link[_ngcontent-%COMP%] {\n  background-color: rgba(255, 255, 255, 0.05);\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%] {\n  background-color: #fb5;\n  color: #000000;\n}\n.menu-collapse-icon[_ngcontent-%COMP%] {\n  font-size: 20px;\n  width: 24px;\n  height: 24px;\n  margin: auto 0 auto auto;\n  text-align: center;\n  color: #f3f3f3;\n}\n.sublevel-nav[_ngcontent-%COMP%] {\n  list-style: none;\n  margin-left: 50px;\n  padding: 0;\n  position: relative;\n  width: 200px;\n}\n.sublevel-nav-item[_ngcontent-%COMP%]:hover {\n  background-color: rgba(255, 255, 255, 0.05);\n}\n.sublevel-nav-item[_ngcontent-%COMP%] {\n  width: 165px;\n  border-radius: 4px;\n  display: flex;\n  margin-top: 10px;\n  flex-grow: 0;\n  flex-direction: row;\n  justify-content: flex-start;\n  align-items: center;\n  position: relative;\n}\n.sublevel-nav-link[_ngcontent-%COMP%] {\n  width: 170px;\n  display: flex;\n  justify-content: space-between;\n  align-items: center;\n  height: 40px;\n  color: #8690a3;\n  text-decoration: none;\n  border-radius: 0.625rem;\n  transition: all 0.3s ease;\n}\n.sublevel-link-text[_ngcontent-%COMP%] {\n  margin-left: 2rem;\n  font-size: 14px;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n}\n.listSelector[_ngcontent-%COMP%] {\n  width: 4px;\n  height: 30px;\n  background: white;\n  margin-left: -10px;\n  border-radius: 20px;\n  opacity: 0;\n  transition: opacity 0.3s ease;\n}\n.listSelector.active[_ngcontent-%COMP%] {\n  opacity: 1;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%] {\n  position: absolute;\n  top: 0;\n  left: 125%;\n  background-color: #181717;\n  border-radius: 16px;\n  padding: 2px 0;\n  gap: 10px;\n  display: none;\n  z-index: 1000;\n  flex-direction: column;\n  width: 180px;\n  height: 250px;\n  overflow-x: hidden;\n  overflow-y: scroll;\n  transition: all 0.4s ease-in;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .listSelector[_ngcontent-%COMP%] {\n  width: 5px;\n  height: 34.4px;\n  background: white;\n  margin-left: 5px;\n  border-radius: 20px;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav[_ngcontent-%COMP%] {\n  margin-left: 10px;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-item[_ngcontent-%COMP%] {\n  width: 80%;\n  margin-top: 5px;\n  cursor: pointer;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-link[_ngcontent-%COMP%] {\n  height: 1.5rem;\n  color: white;\n  display: contents;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-link-text[_ngcontent-%COMP%] {\n  margin-left: 1.2rem;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-item[_ngcontent-%COMP%]:hover {\n  background-color: #2b2a2a;\n}\n.sublevel-nav-item.expanded[_ngcontent-%COMP%]   .sublevel-nav-link-container[_ngcontent-%COMP%] {\n  display: flex;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-link[_ngcontent-%COMP%] {\n  margin-top: 10px;\n  width: 80%;\n}\n[_ngcontent-%COMP%]::-webkit-scrollbar {\n  display: none !important;\n}\n/*# sourceMappingURL=main.component.css.map */"], data: { animation: [
  trigger("submenu", [
    state("hidden", style({
      height: "0",
      overflow: "hidden"
    })),
    state("visible", style({
      height: "*"
    })),
    transition("visible <=> hidden", [
      style({ overflow: "hidden" }),
      animate("{{transitionParams}}")
    ]),
    transition("void => *", animate(0))
  ])
] } });
var SublevelMenuComponent = _SublevelMenuComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(SublevelMenuComponent, { className: "SublevelMenuComponent", filePath: "src\\app\\main\\sublevel-menu.component.ts", lineNumber: 65 });
})();

// src/app/main/main.component.ts
var _c02 = (a0) => ({ "width": a0 });
var _c12 = (a0) => ({ "margin-left": a0 });
var _c22 = (a0, a1) => ({ "active": a0, "d-none": a1 });
var _c32 = (a0) => [a0];
var _c42 = (a0) => ({ "active": a0 });
function MainComponent_img_4_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 34);
  }
}
function MainComponent_img_5_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 34);
  }
}
function MainComponent_mat_icon_13_Template(rf, ctx) {
  if (rf & 1) {
    const _r2 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "mat-icon", 35);
    \u0275\u0275listener("click", function MainComponent_mat_icon_13_Template_mat_icon_click_0_listener() {
      \u0275\u0275restoreView(_r2);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.closeSidenav());
    });
    \u0275\u0275text(1, "chevron_left");
    \u0275\u0275elementEnd();
  }
}
function MainComponent_mat_icon_14_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "mat-icon", 35);
    \u0275\u0275listener("click", function MainComponent_mat_icon_14_Template_mat_icon_click_0_listener() {
      \u0275\u0275restoreView(_r4);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.toggleCollapse());
    });
    \u0275\u0275text(1, "chevron_right");
    \u0275\u0275elementEnd();
  }
}
function MainComponent_li_18_a_1_span_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span", 44);
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const data_r7 = \u0275\u0275nextContext(2).$implicit;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("@fadeText", !ctx_r2.collapsed ? "fade-out" : "fade-in");
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(data_r7.label);
  }
}
function MainComponent_li_18_a_1_img_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275element(0, "img", 45);
  }
  if (rf & 2) {
    const i_r8 = \u0275\u0275nextContext(2).index;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("src", ctx_r2.isActive(i_r8) ? "./assets/sidebar/Alt Arrow Down Dark.png" : "./assets/sidebar/Alt Arrow Down.png", \u0275\u0275sanitizeUrl);
  }
}
function MainComponent_li_18_a_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "a", 40);
    \u0275\u0275listener("click", function MainComponent_li_18_a_1_Template_a_click_0_listener() {
      \u0275\u0275restoreView(_r5);
      const ctx_r5 = \u0275\u0275nextContext();
      const data_r7 = ctx_r5.$implicit;
      const i_r8 = ctx_r5.index;
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.handleClick(data_r7, i_r8));
    })("click", function MainComponent_li_18_a_1_Template_a_click_0_listener() {
      \u0275\u0275restoreView(_r5);
      const i_r8 = \u0275\u0275nextContext().index;
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.setActiveLink(i_r8));
    });
    \u0275\u0275element(1, "img", 41);
    \u0275\u0275template(2, MainComponent_li_18_a_1_span_2_Template, 2, 2, "span", 42)(3, MainComponent_li_18_a_1_img_3_Template, 1, 1, "img", 43);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const ctx_r5 = \u0275\u0275nextContext();
    const data_r7 = ctx_r5.$implicit;
    const i_r8 = ctx_r5.index;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("ngClass", \u0275\u0275pureFunction2(4, _c22, ctx_r2.activeLinkIndex === i_r8, ctx_r2.checkModule(data_r7.routerId)));
    \u0275\u0275advance();
    \u0275\u0275property("src", ctx_r2.isActive(i_r8) ? data_r7.srcDark : data_r7.src, \u0275\u0275sanitizeUrl);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.collapsed);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", data_r7.items && ctx_r2.collapsed);
  }
}
function MainComponent_li_18_a_2_span_2_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "span", 44);
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const data_r7 = \u0275\u0275nextContext(2).$implicit;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("@fadeText", !ctx_r2.collapsed ? "fade-out" : "fade-in");
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(data_r7.label);
  }
}
function MainComponent_li_18_a_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r9 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "a", 46);
    \u0275\u0275listener("click", function MainComponent_li_18_a_2_Template_a_click_0_listener() {
      \u0275\u0275restoreView(_r9);
      const ctx_r5 = \u0275\u0275nextContext();
      const data_r7 = ctx_r5.$implicit;
      const i_r8 = ctx_r5.index;
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.handleClick(data_r7, i_r8));
    })("click", function MainComponent_li_18_a_2_Template_a_click_0_listener() {
      \u0275\u0275restoreView(_r9);
      const i_r8 = \u0275\u0275nextContext().index;
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.setActiveLink(i_r8));
    });
    \u0275\u0275element(1, "img", 41);
    \u0275\u0275template(2, MainComponent_li_18_a_2_span_2_Template, 2, 2, "span", 42);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const ctx_r5 = \u0275\u0275nextContext();
    const data_r7 = ctx_r5.$implicit;
    const i_r8 = ctx_r5.index;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("routerLink", \u0275\u0275pureFunction1(4, _c32, data_r7.routerLink))("ngClass", \u0275\u0275pureFunction1(6, _c42, ctx_r2.activeLinkIndex === i_r8));
    \u0275\u0275advance();
    \u0275\u0275property("src", ctx_r2.isActive(i_r8) ? data_r7.srcDark : data_r7.src, \u0275\u0275sanitizeUrl);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.collapsed);
  }
}
function MainComponent_li_18_div_3_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "div");
    \u0275\u0275element(1, "app-sublevel-menu", 47);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const data_r7 = \u0275\u0275nextContext().$implicit;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance();
    \u0275\u0275property("data", data_r7)("collapsed", ctx_r2.collapsed)("multiple", ctx_r2.multiple)("expanded", data_r7.expanded);
  }
}
function MainComponent_li_18_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "li", 36);
    \u0275\u0275template(1, MainComponent_li_18_a_1_Template, 4, 7, "a", 37)(2, MainComponent_li_18_a_2_Template, 3, 8, "a", 38)(3, MainComponent_li_18_div_3_Template, 2, 4, "div", 39);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const data_r7 = ctx.$implicit;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", data_r7.items && data_r7.items.length > 0);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", !data_r7.items || data_r7.items && data_r7.items.length === 0);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx_r2.collapsed && data_r7.items && data_r7.items.length > 0);
  }
}
function MainComponent_li_26_Template(rf, ctx) {
  if (rf & 1) {
    \u0275\u0275elementStart(0, "li", 48);
    \u0275\u0275text(1);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const element_r10 = ctx.$implicit;
    const j_r11 = ctx.index;
    const ctx_r2 = \u0275\u0275nextContext();
    \u0275\u0275property("ngClass", j_r11 == ctx_r2.dataArray.length - 1 ? "active" : "");
    \u0275\u0275advance();
    \u0275\u0275textInterpolate(element_r10);
  }
}
function MainComponent_ng_template_40_Template(rf, ctx) {
  if (rf & 1) {
    const _r12 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "button", 49);
    \u0275\u0275listener("click", function MainComponent_ng_template_40_Template_button_click_0_listener() {
      \u0275\u0275restoreView(_r12);
      const ctx_r2 = \u0275\u0275nextContext();
      return \u0275\u0275resetView(ctx_r2.logout());
    });
    \u0275\u0275element(1, "img", 50);
    \u0275\u0275text(2, "Logout");
    \u0275\u0275elementEnd();
  }
}
var _MainComponent = class _MainComponent {
  toggleVisibility() {
    this.isVisible = !this.isVisible;
  }
  constructor(observer, path, router, cdRef, auth, titleService, activatedRoute) {
    this.observer = observer;
    this.path = path;
    this.router = router;
    this.cdRef = cdRef;
    this.auth = auth;
    this.titleService = titleService;
    this.activatedRoute = activatedRoute;
    this.isVisible = false;
    this.title = "material-responsive-sidenav";
    this.isMobile = true;
    this.dataArray = [];
    this.dataSubscription = new Subscription();
    this.isCollapsed = true;
    this.expanded = false;
    this.onToggleSideNav = new EventEmitter();
    this.collapsed = false;
    this.screenWidth = 0;
    this.navData = navbarData;
    this.multiple = false;
    this.activeLinkIndex = null;
    this.selectedItem = null;
    this.setActive = false;
    this.activeIcons = [];
    this.showIcon1 = true;
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(() => {
      var rt = this.getChild(this.activatedRoute);
      rt.data.subscribe((data) => {
        this.titleService.setTitle("Overc360 - " + data.module);
      });
    });
  }
  getChild(activatedRoute) {
    if (activatedRoute.firstChild) {
      return this.getChild(activatedRoute.firstChild);
    } else {
      return activatedRoute;
    }
  }
  ngOnInit() {
    this.dataSubscription = this.path.dataArray$.subscribe((data) => {
      this.dataArray = data;
      this.cdRef.detectChanges();
    });
    this.observer.observe(["(max-width: 800px)"]).subscribe((screenSize) => {
      if (screenSize.matches) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });
    this.updateActiveLink();
  }
  ngOnDestroy() {
    this.dataSubscription.unsubscribe();
  }
  toggleSidebar() {
    this.expanded = !this.expanded;
  }
  toggleCollapse() {
    this.collapsed = !this.collapsed;
    this.onToggleSideNav.emit({ collapse: this.collapsed, screenWidth: this.screenWidth });
  }
  // This method checks if any subnav is active
  isAnySubnavActive() {
    return this.navData.some((data) => data.items?.some((item) => item.expanded));
  }
  // Method to close all menus
  closeAllMenus() {
    if (!this.collapsed) {
      this.closeSidenav();
    }
    this.collapseSublevelMenus();
  }
  // Collapse all sublevel menus
  collapseSublevelMenus() {
    this.navData.forEach((item) => {
      if (item.items) {
        item.expanded = false;
        item.items.forEach((subItem) => {
          subItem.expanded = false;
        });
      }
    });
  }
  closeSidenav() {
    this.collapsed = false;
    this.onToggleSideNav.emit({ collapse: this.collapsed, screenWidth: this.screenWidth });
  }
  toggleIcon() {
    this.showIcon1 = !this.showIcon1;
  }
  handleClick(item, index) {
    if (!this.multiple) {
      this.navData.forEach((modelItem) => {
        if (item !== modelItem && modelItem.expanded) {
          modelItem.expanded = false;
        }
      });
    }
    item.expanded = !item.expanded;
    this.setActiveLink(index);
  }
  setActiveLink(index) {
    this.activeLinkIndex = index;
  }
  isActive(index) {
    return this.activeLinkIndex === index;
  }
  updateActiveLink() {
    const currentUrl = this.router.url;
    const matchingNavItemIndex = this.navData.findIndex((navItem) => {
      return navItem.routerLink && currentUrl.includes(navItem.routerLink);
    });
    if (matchingNavItemIndex !== -1) {
      this.setActiveLink(matchingNavItemIndex);
    } else {
      this.activeLinkIndex = null;
    }
  }
  logout() {
    this.auth.logout();
  }
  checkModule(id) {
    let fileterdata = this.auth.MenuData.filter((x) => x.moduleId == id && (x.view || x.delete || x.createUpdate));
    if (fileterdata.length > 0) {
      return false;
    }
    return true;
  }
};
_MainComponent.\u0275fac = function MainComponent_Factory(t) {
  return new (t || _MainComponent)(\u0275\u0275directiveInject(BreakpointObserver), \u0275\u0275directiveInject(PathNameService), \u0275\u0275directiveInject(Router), \u0275\u0275directiveInject(ChangeDetectorRef), \u0275\u0275directiveInject(AuthService), \u0275\u0275directiveInject(Title), \u0275\u0275directiveInject(ActivatedRoute));
};
_MainComponent.\u0275cmp = /* @__PURE__ */ \u0275\u0275defineComponent({ type: _MainComponent, selectors: [["app-main"]], outputs: { onToggleSideNav: "onToggleSideNav" }, decls: 41, vars: 21, consts: [["menu", "matMenu"], [1, "bgBlack", 3, "mode", "opened"], [1, "sidenav", "d-flex-column", 3, "ngClass"], [1, "logo-container"], ["src", "./assets/dashboard/Circle Icon.svg", "style", "width: 3.3rem !important; height: 3.3rem !important;", "alt", "", 4, "ngIf"], ["src", "./assets/dashboard/Circle Icon.svg", "style", "width: 3.3rem !important; height: 3.3rem !important; ", "alt", "", 4, "ngIf"], ["id", "overc-container"], ["id", "overc"], ["src", "./assets/dashboard/Later.svg", "alt", "", 2, "width", "4.3rem !important", "height", "3.3rem !important", "margin-left", "-25px"], ["id", "span360"], ["src", "./assets/dashboard/360.svg", "alt", "", 2, "width", "3.3rem !important", "height", "1rem !important", "margin-top", "-4px"], [1, "hrline"], [1, "toggle-btn"], ["class", "btn", 3, "click", 4, "ngIf"], [1, "Menu", 2, "padding", "5px"], [1, "sidenav-nav", 2, "overflow-y", "scroll", "height", "calc(100vh - 9rem)", 3, "ngStyle"], ["class", "sidenav-nav-item", 4, "ngFor", "ngForOf"], [1, "bodyBackGround", 2, "margin-left", "120px", 3, "click", "ngStyle"], [1, "d-flex", "justify-content-between"], ["id", "breadcrumbs"], [1, "headerPageName"], ["aria-label", "breadcrumb"], [1, "breadcrumb"], ["class", "breadcrumb-item", 3, "ngClass", 4, "ngFor", "ngForOf"], ["id", "logout"], [1, "d-flex", "justify-content-between", "align-items-center"], ["src", "./assets/dashboard/notifications.png", "alt", "", "srcset", "", 1, "img-fluid", "mr-5"], [1, "d-flex", "flex-column", "mr-2"], [1, "mb-1", "headerPageName", "text-right"], [1, "mb-0", "breadcrumb-item", "text-right"], ["src", "./assets/dashboard/User.png", "alt", "", "srcset", "", 1, "img-fluid", 3, "matMenuTriggerFor"], [1, "componentBackground"], [1, "customClass"], ["matMenuContent", ""], ["src", "./assets/dashboard/Circle Icon.svg", "alt", "", 2, "width", "3.3rem !important", "height", "3.3rem !important"], [1, "btn", 3, "click"], [1, "sidenav-nav-item"], ["class", "sidenav-nav-link", 3, "ngClass", "click", 4, "ngIf"], ["class", "sidenav-nav-link", 3, "routerLink", "ngClass", "click", 4, "ngIf"], [4, "ngIf"], [1, "sidenav-nav-link", 3, "click", "ngClass"], ["alt", "", 1, "sidenav-nav-icon", 3, "src"], ["class", "sidenav-link-text", 4, "ngIf"], ["class", "menu-collapse-icon", "alt", "", 3, "src", 4, "ngIf"], [1, "sidenav-link-text"], ["alt", "", 1, "menu-collapse-icon", 3, "src"], [1, "sidenav-nav-link", 3, "click", "routerLink", "ngClass"], [3, "data", "collapsed", "multiple", "expanded"], [1, "breadcrumb-item", 3, "ngClass"], ["mat-menu-item", "", 1, "listItems", 3, "click"], ["src", "./assets/login/Logout.png", "srcset", "", 2, "padding-right", "10px"]], template: function MainComponent_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = \u0275\u0275getCurrentView();
    \u0275\u0275elementStart(0, "mat-sidenav-container")(1, "mat-sidenav", 1)(2, "div", 2)(3, "div", 3);
    \u0275\u0275template(4, MainComponent_img_4_Template, 1, 0, "img", 4)(5, MainComponent_img_5_Template, 1, 0, "img", 5);
    \u0275\u0275elementStart(6, "h1", 6)(7, "span", 7);
    \u0275\u0275element(8, "img", 8);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(9, "span", 9);
    \u0275\u0275element(10, "img", 10);
    \u0275\u0275elementEnd()()();
    \u0275\u0275element(11, "div", 11);
    \u0275\u0275elementStart(12, "div", 12);
    \u0275\u0275template(13, MainComponent_mat_icon_13_Template, 2, 0, "mat-icon", 13)(14, MainComponent_mat_icon_14_Template, 2, 0, "mat-icon", 13);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(15, "div", 14)(16, "mat-nav-list")(17, "ul", 15);
    \u0275\u0275template(18, MainComponent_li_18_Template, 4, 3, "li", 16);
    \u0275\u0275elementEnd()()()()();
    \u0275\u0275elementStart(19, "mat-sidenav-content", 17);
    \u0275\u0275listener("click", function MainComponent_Template_mat_sidenav_content_click_19_listener() {
      \u0275\u0275restoreView(_r1);
      return \u0275\u0275resetView(ctx.closeAllMenus());
    });
    \u0275\u0275elementStart(20, "div", 18)(21, "div", 19)(22, "p", 20);
    \u0275\u0275text(23);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(24, "nav", 21)(25, "ol", 22);
    \u0275\u0275template(26, MainComponent_li_26_Template, 2, 2, "li", 23);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(27, "div", 24)(28, "div", 25);
    \u0275\u0275element(29, "img", 26);
    \u0275\u0275elementStart(30, "div", 27)(31, "p", 28);
    \u0275\u0275text(32);
    \u0275\u0275elementEnd();
    \u0275\u0275elementStart(33, "p", 29);
    \u0275\u0275text(34);
    \u0275\u0275elementEnd()();
    \u0275\u0275element(35, "img", 30);
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(36, "div", 31);
    \u0275\u0275element(37, "router-outlet");
    \u0275\u0275elementEnd()()();
    \u0275\u0275elementStart(38, "mat-menu", 32, 0);
    \u0275\u0275template(40, MainComponent_ng_template_40_Template, 3, 0, "ng-template", 33);
    \u0275\u0275elementEnd();
  }
  if (rf & 2) {
    const menu_r13 = \u0275\u0275reference(39);
    \u0275\u0275advance();
    \u0275\u0275property("mode", ctx.isMobile ? "over" : "side")("opened", ctx.isMobile ? "false" : "true");
    \u0275\u0275advance();
    \u0275\u0275property("ngClass", ctx.collapsed ? "sidenav-collapsed" : "");
    \u0275\u0275advance(2);
    \u0275\u0275property("ngIf", !ctx.collapsed);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", ctx.collapsed);
    \u0275\u0275advance(2);
    \u0275\u0275property("@fadeLater", !ctx.collapsed ? "fade-out" : "fade-in");
    \u0275\u0275advance(2);
    \u0275\u0275property("@move360", !ctx.collapsed ? "move-center" : "move-left");
    \u0275\u0275advance(4);
    \u0275\u0275property("ngIf", ctx.collapsed);
    \u0275\u0275advance();
    \u0275\u0275property("ngIf", !ctx.collapsed);
    \u0275\u0275advance(3);
    \u0275\u0275property("ngStyle", \u0275\u0275pureFunction1(17, _c02, ctx.isAnySubnavActive() ? "160%" : "100%"));
    \u0275\u0275advance();
    \u0275\u0275property("ngForOf", ctx.navData);
    \u0275\u0275advance();
    \u0275\u0275property("ngStyle", \u0275\u0275pureFunction1(19, _c12, ctx.collapsed ? "300px" : "105px"));
    \u0275\u0275advance(4);
    \u0275\u0275textInterpolate(ctx.dataArray[0]);
    \u0275\u0275advance(3);
    \u0275\u0275property("ngForOf", ctx.dataArray);
    \u0275\u0275advance(6);
    \u0275\u0275textInterpolate(ctx.auth.userName);
    \u0275\u0275advance(2);
    \u0275\u0275textInterpolate(ctx.auth.companyName);
    \u0275\u0275advance();
    \u0275\u0275property("matMenuTriggerFor", menu_r13);
  }
}, dependencies: [NgClass, NgForOf, NgIf, NgStyle, RouterOutlet, RouterLink, MatMenu, MatMenuItem, MatMenuContent, MatMenuTrigger, MatSidenav, MatSidenavContainer, MatSidenavContent, MatNavList, MatIcon, SublevelMenuComponent], styles: ["\n\nmat-sidenav-container[_ngcontent-%COMP%] {\n  height: 100%;\n  scroll-behavior: smooth;\n}\nmat-sidenav-container[_ngcontent-%COMP%]   mat-sidenav-content[_ngcontent-%COMP%] {\n  transition: all 0.6s ease;\n}\n  .mat-drawer-container {\n  background-color: #ededed !important;\n}\nmat-sidenav[_ngcontent-%COMP%]   .entry[_ngcontent-%COMP%] {\n  display: flex;\n  align-items: center;\n  gap: 1rem;\n}\nmat-sidenav[_ngcontent-%COMP%]   mat-nav-list[_ngcontent-%COMP%] {\n  margin: 0;\n  padding: 0;\n}\n#overc-container[_ngcontent-%COMP%] {\n  display: flex;\n  align-items: center;\n  justify-content: center;\n}\n.sidenav[_ngcontent-%COMP%] {\n  background: #000000;\n  transition: all 0.6s ease;\n  position: fixed;\n  z-index: 1;\n  top: 0;\n  width: 6.875rem;\n  height: 100vh;\n  border-radius: 0 40px 40px 0;\n}\n.sidenav[_ngcontent-%COMP%]   .toggle-btn[_ngcontent-%COMP%] {\n  cursor: pointer;\n  background-color: whitesmoke;\n  width: 30px;\n  height: 30px;\n  border-radius: 80px;\n  box-shadow: 4px 0 10px 0 rgba(0, 0, 0, 0.1);\n  z-index: 1;\n  float: right;\n  margin-top: -16px;\n  margin-right: -13px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  transition: all 0.6s ease;\n}\n.sidenav[_ngcontent-%COMP%]   .toggle-btn[_ngcontent-%COMP%]   .btn[_ngcontent-%COMP%] {\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  font-size: x-large;\n}\n.sidenav[_ngcontent-%COMP%]   .logo-container[_ngcontent-%COMP%] {\n  display: flex;\n  justify-content: flex-start;\n  flex-grow: 0;\n  margin: 25px 20px 5px 15px;\n  transition: all 0.6s ease;\n}\n.sidenav[_ngcontent-%COMP%]   .hrline[_ngcontent-%COMP%] {\n  width: 6.875rem;\n  height: 1px;\n  background-color: rgba(255, 255, 255, 0.1);\n  transition: all 0.6s ease;\n  margin-bottom: 0px;\n}\n.sidenav-collapsed[_ngcontent-%COMP%] {\n  width: 18.75rem;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .logo-container[_ngcontent-%COMP%] {\n  display: flex;\n  justify-content: stretch;\n  flex-grow: 0;\n  margin: 25px 20px 5px 70px;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .toggle-btn[_ngcontent-%COMP%] {\n  cursor: pointer;\n  background-color: whitesmoke;\n  width: 30px;\n  height: 30px;\n  border-radius: 80px;\n  box-shadow: 4px 0 10px 0 rgba(0, 0, 0, 0.1);\n  z-index: 1;\n  float: right;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .hrline[_ngcontent-%COMP%] {\n  width: 18.75rem;\n  height: 1px;\n  background-color: rgba(255, 255, 255, 0.1);\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%] {\n  transition: all 0.4s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%] {\n  transition: all 0.4s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link[_ngcontent-%COMP%] {\n  display: flex;\n  flex-direction: row;\n  align-items: center;\n  justify-content: flex-start;\n  gap: 1rem;\n  width: 16.063rem;\n  height: 3rem;\n  flex-grow: 0;\n  padding: 0.75rem 1.25rem;\n  font-size: 16px;\n  color: #f3f3f3;\n  text-decoration: none;\n  border-radius: 0.75rem;\n  transition: all 0.6s ease;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%] {\n  background-color: #fb5;\n  color: #000000;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%]   .sidenav-link-text[_ngcontent-%COMP%] {\n  color: #000000;\n}\n.sidenav-collapsed[_ngcontent-%COMP%]   .sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%]   .menu-collapse-icon[_ngcontent-%COMP%] {\n  color: #000000;\n}\n.sidenav-nav[_ngcontent-%COMP%] {\n  list-style: none;\n  padding: 0.938rem;\n  margin: 0;\n  display: flex;\n  flex-direction: column;\n  align-items: center;\n  height: 900px;\n  cursor: pointer;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%] {\n  width: 100%;\n  margin-bottom: 0.8rem;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link[_ngcontent-%COMP%] {\n  display: flex;\n  flex-direction: row;\n  align-items: center;\n  justify-content: flex-start;\n  gap: 1rem;\n  width: 4rem;\n  height: 3rem;\n  flex-grow: 0;\n  padding: 0.75rem 1.25rem;\n  color: #f3f3f3;\n  text-decoration: none;\n  border-radius: 0.75rem;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]   .sidenav-nav-link[_ngcontent-%COMP%]   .sidenav-link-text[_ngcontent-%COMP%] {\n  margin-left: 0.5rem;\n  transition: all 0.6s ease;\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-item[_ngcontent-%COMP%]:hover   .sidenav-nav-link[_ngcontent-%COMP%] {\n  background-color: rgba(255, 255, 255, 0.05);\n}\n.sidenav-nav[_ngcontent-%COMP%]   .sidenav-nav-link.active[_ngcontent-%COMP%] {\n  background-color: #fb5;\n  color: #000000;\n}\n.menu-collapse-icon[_ngcontent-%COMP%] {\n  font-size: 20px;\n  width: 24px;\n  height: 24px;\n  margin: auto 0 auto auto;\n  text-align: center;\n  color: #f3f3f3;\n}\n.sublevel-nav[_ngcontent-%COMP%] {\n  list-style: none;\n  margin-left: 50px;\n  padding: 0;\n  position: relative;\n  width: 200px;\n}\n.sublevel-nav-item[_ngcontent-%COMP%]:hover {\n  background-color: rgba(255, 255, 255, 0.05);\n}\n.sublevel-nav-item[_ngcontent-%COMP%] {\n  width: 165px;\n  border-radius: 4px;\n  display: flex;\n  margin-top: 10px;\n  flex-grow: 0;\n  flex-direction: row;\n  justify-content: flex-start;\n  align-items: center;\n  position: relative;\n}\n.sublevel-nav-link[_ngcontent-%COMP%] {\n  width: 170px;\n  display: flex;\n  justify-content: space-between;\n  align-items: center;\n  height: 40px;\n  color: #8690a3;\n  text-decoration: none;\n  border-radius: 0.625rem;\n  transition: all 0.3s ease;\n}\n.sublevel-link-text[_ngcontent-%COMP%] {\n  margin-left: 2rem;\n  font-size: 14px;\n  font-stretch: normal;\n  font-style: normal;\n  line-height: 1.3;\n  letter-spacing: normal;\n  text-align: left;\n}\n.listSelector[_ngcontent-%COMP%] {\n  width: 4px;\n  height: 30px;\n  background: white;\n  margin-left: -10px;\n  border-radius: 20px;\n  opacity: 0;\n  transition: opacity 0.3s ease;\n}\n.listSelector.active[_ngcontent-%COMP%] {\n  opacity: 1;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%] {\n  position: absolute;\n  top: 0;\n  left: 125%;\n  background-color: #181717;\n  border-radius: 16px;\n  padding: 2px 0;\n  gap: 10px;\n  display: none;\n  z-index: 1000;\n  flex-direction: column;\n  width: 180px;\n  height: 250px;\n  overflow-x: hidden;\n  overflow-y: scroll;\n  transition: all 0.4s ease-in;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .listSelector[_ngcontent-%COMP%] {\n  width: 5px;\n  height: 34.4px;\n  background: white;\n  margin-left: 5px;\n  border-radius: 20px;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav[_ngcontent-%COMP%] {\n  margin-left: 10px;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-item[_ngcontent-%COMP%] {\n  width: 80%;\n  margin-top: 5px;\n  cursor: pointer;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-link[_ngcontent-%COMP%] {\n  height: 1.5rem;\n  color: white;\n  display: contents;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-link-text[_ngcontent-%COMP%] {\n  margin-left: 1.2rem;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-item[_ngcontent-%COMP%]:hover {\n  background-color: #2b2a2a;\n}\n.sublevel-nav-item.expanded[_ngcontent-%COMP%]   .sublevel-nav-link-container[_ngcontent-%COMP%] {\n  display: flex;\n}\n.sublevel-nav-link-container[_ngcontent-%COMP%]   .sublevel-nav-link[_ngcontent-%COMP%] {\n  margin-top: 10px;\n  width: 80%;\n}\n[_ngcontent-%COMP%]::-webkit-scrollbar {\n  display: none !important;\n}\n/*# sourceMappingURL=main.component.css.map */"], data: { animation: [
  trigger("fadeLater", [
    state("fade-in", style({ opacity: 1, transform: "translateX(0)" })),
    state("fade-out", style({ opacity: 0, transform: "translateX(-70px)" })),
    transition("fade-in <=> fade-out", animate("0.6s ease-in-out"))
  ]),
  trigger("move360", [
    state("move-left", style({ transform: "translateX(0)" })),
    state("move-center", style({ transform: "translateX(-70px)" })),
    transition("move-left <=> move-center", animate("0.6s ease-in-out"))
  ]),
  trigger("fadeText", [
    state("fade-in", style({ opacity: 1, transform: "translateX(0)" })),
    state("fade-out", style({ opacity: 0, transform: "translateX(70px)" })),
    transition("fade-in <=> fade-out", animate("0.7s ease-in-out"))
  ])
] } });
var MainComponent = _MainComponent;
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && \u0275setClassDebugInfo(MainComponent, { className: "MainComponent", filePath: "src\\app\\main\\main.component.ts", lineNumber: 40 });
})();

// src/app/main/main-routing.module.ts
var routes = [{
  path: "",
  component: MainComponent,
  children: [
    { path: "idMaster", loadChildren: () => import("./chunk-FNJN7AMK.js").then((m) => m.IdMastersModule) },
    { path: "master", loadChildren: () => import("./chunk-LWTHMKYQ.js").then((m) => m.MasterModule) },
    { path: "airport", loadChildren: () => import("./chunk-LDO6JYS7.js").then((m) => m.AirportModule) },
    { path: "operation", loadChildren: () => import("./chunk-VO544ISP.js").then((m) => m.OperationModule) },
    { path: "reports", loadChildren: () => import("./chunk-B657DORB.js").then((m) => m.ReportsModule) },
    { path: "finance", loadChildren: () => import("./chunk-ST4CJM6B.js").then((m) => m.FinanceModule) },
    { path: "lastMile", loadChildren: () => import("./chunk-FVSV5NLU.js").then((m) => m.LastMileModule) }
  ]
}];
var _MainRoutingModule = class _MainRoutingModule {
};
_MainRoutingModule.\u0275fac = function MainRoutingModule_Factory(t) {
  return new (t || _MainRoutingModule)();
};
_MainRoutingModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _MainRoutingModule });
_MainRoutingModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [RouterModule.forChild(routes), RouterModule] });
var MainRoutingModule = _MainRoutingModule;

// src/app/main/main.module.ts
var _MainModule = class _MainModule {
};
_MainModule.\u0275fac = function MainModule_Factory(t) {
  return new (t || _MainModule)();
};
_MainModule.\u0275mod = /* @__PURE__ */ \u0275\u0275defineNgModule({ type: _MainModule });
_MainModule.\u0275inj = /* @__PURE__ */ \u0275\u0275defineInjector({ imports: [
  CommonModule,
  MainRoutingModule,
  SharedModule
] });
var MainModule = _MainModule;
export {
  MainModule
};
//# sourceMappingURL=chunk-YHGNLIDK.js.map
