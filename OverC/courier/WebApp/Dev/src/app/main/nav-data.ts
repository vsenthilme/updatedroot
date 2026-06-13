export interface INavbarData {
    routerLink?: string;
    routerId: number;
    src?: string;
    srcDark?: string;
    label: string;
    expanded?: boolean;
    items?: INavbarData[];
}

export const navbarData: INavbarData[] = [
    {
        routerId: 1000,
        routerLink: 'dashboard',
        src: './assets/sidebar/dashboard/DashboardLight.png',
        srcDark: './assets/sidebar/dashboard/Dashboard.png',
        label: 'Dashboard'
    },
    {
        routerId: 2000,
        routerLink: '/main/idMaster',
        src: './assets/sidebar/setting/Seting.png',
        srcDark: './assets/sidebar/setting/SetingDark.png',
        label: 'Setup',
        items: [
            {
                routerId: 2001,
                // routerLink: 'setup/list',
                label: 'Admin',
                items: [
                    {
                        routerId: 2101,
                        routerLink: '/main/idMaster/language',
                        label: 'Language'
                    },
                    {
                        routerId: 2102,
                        routerLink: '/main/idMaster/status',
                        label: 'Status'
                    },
                    {
                        routerId: 2103,
                        routerLink: '/main/idMaster/currency',
                        label: 'Currency'
                    },
                    {
                        routerId: 2104,
                        routerLink: '/main/idMaster/menu',
                        label: 'Menu'
                    },
                    {
                        routerId: 2105,
                        routerLink: '/main/idMaster/module',
                        label: 'Module'
                    },
                    {
                        routerId: 2106,
                        routerLink: '/main/idMaster/numberrange',
                        label: 'Number Series'
                    },
                    {
                        routerId: 2107,
                        routerLink: '/main/idMaster/userrole',
                        label: 'User Role'
                    },
                    {
                        routerId: 2108,
                        routerLink: '/main/idMaster/users',
                        label: 'User'
                    },
                    {
                        routerId: 2109,
                        routerLink: '/main/idMaster/notification',
                        label: 'Notification'
                    },
                    {
                        routerId: 2110,
                        routerLink: '/main/idMaster/appUser',
                        label: 'App User'
                    },
                    {
                        routerId: 2111,
                        routerLink: '/main/idMaster/uom',
                        label: 'UOM'
                    },
                ],
            },
            {
                routerId: 2002,
                // routerLink: 'setup/list',
                label: 'Organization',
                items: [
                    {
                        routerId: 2201,
                        routerLink: '/main/idMaster/company',
                        label: 'Company'
                    },
                    {
                        routerId: 2202,
                        routerLink: '/main/idMaster/product',
                        label: 'Product'
                    },
                    {
                        routerId: 2203,
                        routerLink: '/main/idMaster/subProduct',
                        label: 'Sub Product'
                    },
                    {
                        routerId: 2204,
                        routerLink: '/main/idMaster/country',
                        label: 'Country'
                    },
                    {
                        routerId: 2205,
                        routerLink: '/main/idMaster/province',
                        label: 'Province'
                    },
                    {
                        routerId: 2206,
                        routerLink: '/main/idMaster/district',
                        label: 'District'
                    },
                    {
                        routerId: 2207,
                        routerLink: '/main/idMaster/city',
                        label: 'City'
                    },
                ]
            },
            {
                routerId: 2003,
                // routerLink: 'setup/list',
                label: 'Business',
                items: [
                    {
                        routerId: 2301,
                        routerLink: '/main/idMaster/serviceType',
                        label: 'Service Type'
                    },
                    {
                        routerId: 2302,
                        routerLink: '/main/idMaster/consignmentType',
                        label: 'Consignment Type'
                    },
                    {
                        routerId: 2303,
                        routerLink: '/main/idMaster/loadType',
                        label: 'Load Type'
                    },
                    {
                        routerId: 2304,
                        routerLink: '/main/idMaster/rateParameter',
                        label: 'Rate Parameter'
                    },
                    {
                        routerId: 2305,
                        routerLink: '/main/idMaster/statusevent',
                        label: 'Status Event'
                    },
                ]
            },

        ]
    },
    {
        routerId: 3000,
        routerLink: '/main/master',
        src: './assets/sidebar/masters/Database.png',
        srcDark: './assets/sidebar/masters/DatabaseDark.png',
        label: 'Masters',
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
                label: 'Mid-Mile',
                items: [
                    {
                        routerId: 3301,
                        routerLink: '/main/master/iata',
                        label: 'IATA'
                    },
                    {
                        routerId: 3302,
                        routerLink: '/main/master/specialApproval',
                        label: 'Special Approval'
                    },
                    {
                        routerId: 3303,
                        routerLink: '/main/master/hsCode',
                        label: 'HS Code'
                    },
                    {
                        routerId: 3304,
                        routerLink: '/main/master/logicMaster',
                        label: 'Console Validation'
                    },

                ]
            },
            {
                routerId: 3004,
                // routerLink: 'setup/list',
                label: 'Finance',
                items: [
                    {
                        routerId: 3401,
                        routerLink: '/main/master/billMode',
                        label: 'Bill Mode'
                    },
                    {
                        routerId: 3402,
                        routerLink: '/main/master/rate',
                        label: 'Rate'
                    },
                    {
                        routerId: 3403,
                        routerLink: '/main/master/currencyExchangeRate',
                        label: 'Exchange Rate'
                    },
                    {
                        routerId: 3404,
                        routerLink: '/main/master/clearanceCharges',
                        label: 'Clearance Charges'
                    },
                    {
                        routerId: 3405,
                        routerLink: '/main/master/customer',
                        label: 'Customer'
                    },
                    {
                        routerId: 3406,
                        routerLink: '/main/master/customsChargesMaster',
                        label: 'Custom Charges'
                    },

                ]
            },
            {
                routerId: 3005,
                // routerLink: 'setup/list',
                label: 'Last-Mile',
                items: [
                   
                    {
                        routerId: 3501,
                        routerLink: '/main/master/cityMapping',
                        label: 'City Mapping'
                    },
                    {
                        routerId: 3502,
                        routerLink: '/main/master/districtMapping',
                        label: 'District Mapping'
                    },
                    {
                        routerId: 3503,
                        routerLink: '/main/master/provinceMapping',
                        label: 'Province Mapping'
                    },
                    {
                        routerId: 3504,
                        routerLink: '/main/master/countryMapping',
                        label: 'Country Mapping'
                    },
                    {
                        routerId: 3505,
                        routerLink: '/main/master/hubPartnerAssignment',
                        label: 'Hub Partner Assignment'
                    },
                    {
                        routerId: 3506,
                        routerLink: '/main/master/courierPartner',
                        label: 'Courier Partner '
                    },
                    {
                        routerId: 3507,
                        routerLink: '/main/master/route',
                        label: 'Route'
                    },
                    {
                        routerId: 3508,
                        routerLink: '/main/master/vehicle',
                        label: 'Vehicle'
                    },
                    {
                        routerId: 3509,
                        routerLink: '/main/master/driverRouteAssignment',
                        label: 'Driver Route Assignment'
                    },
                    {
                        routerId: 3510,
                        routerLink: '/main/master/zoneMaster',
                        label: 'Zone Master'
                    },
                    {
                        routerId: 3511,
                        routerLink: '/main/master/storageTypeMaster',
                        label: 'Storage Type Master'
                    },
                    {
                        routerId: 3512,
                        routerLink: '/main/master/zoneTypeMaster',
                        label: 'Zone Type Master'
                    },

                ]
            },
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
        routerId: 5000,
        routerLink: '/main/airport',
        src: './assets/sidebar/airport/Airport Hub.png',
        srcDark: './assets/sidebar/airport/Airport HubDark.png',
        label: 'Mid-Mile',
        items: [

            {
                routerId: 5101,
                routerLink: '/main/airport/preAlertManifest',
                label: 'Pre Alert Manifest'
            },
            // {
            //     routerId: 5102,
            //     routerLink: '/main/airport/bondedManifest',
            //     label: 'Bonded Manifest'
            // },
            {
                routerId: 5103,
                routerLink: '/main/airport/console',
                label: 'Console'
            },
            {
                routerId: 5108,
                routerLink: '/main/airport/costingSheet',
                label: 'Customs Costing'
            },
            {
                routerId: 5109,
                routerLink: '/main/airport/dduInvoice',
                label: 'DDU Invoice'
            },
            {
                routerId: 5001,
                label: 'Reports',
                items: [

                    {
                        routerId: 5105,
                        routerLink: '/main/reports/consoleTracking',
                        label: 'Console Tracking'
                    },
                    {
                        routerId: 5106,
                        routerLink: '/main/reports/pendingCustoms',
                        label: 'Pending Customs'
                    },
                    {
                        routerId: 5107,
                        routerLink: '/main/reports/inventoryScanning',
                        label: 'Inventory Scan'
                    },
                    {
                        routerId: 5108,
                        routerLink: '/main/reports/customsCalculations',
                        label: 'Customs Calculation'
                    },
                ]
            },



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
        routerId: 6000,
        routerLink: 'dashboard',
        src: './assets/sidebar/operations/oprations.png',
        srcDark: './assets/sidebar/operations/oprationsDark.png',
        label: 'Operations',
        items: [
            {
                routerId: 6101,
                routerLink: '/main/operation/consignment',
                label: 'Consignment'
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
                routerLink: '/main/operation/assignment',
                label: 'Assignment'
            }

        ]
    },

    {
        routerId: 7000,
        routerLink: 'masters',
        src: './assets/sidebar/billing/billing.png',
        srcDark: './assets/sidebar/billing/billingDark.png',
        label: 'Finance',
        items: [
            {
                routerId: 7101,
                routerLink: '/main/finance/costingSheet',
                label: 'Customs Costing'
            },
            {
                routerId: 7102,
                routerLink: '/main/finance/invoice',
                label: 'Customs Invoice'
            },
            {
                routerId: 7001,
                label: 'Reports',
                items: [

                    {
                        routerId: 7103,
                        routerLink: '/main/reports/costSheet',
                        label: 'Customs Costing'
                    },
                    {
                        routerId: 7104,
                        routerLink: '/main/reports/expenseSheet',
                        label: 'Customs Fees'
                    },
                ]
            },
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
        routerId: 1000,
        routerLink: 'masters',
        src: './assets/sidebar/integration/Integration.png',
        srcDark: './assets/sidebar/integration/IntegrationDark.png',
        label: 'Integration',
        items: [
            {
                routerId: 1000,
                routerLink: 'setup/list',
                label: 'Rates'
            },
            {
                routerId: 1000,
                routerLink: 'setup/list',
                label: 'Users'
            },
            {
                routerId: 1000,
                routerLink: 'setup/list',
                label: 'Product'
            },
        ]
    },
    {
        routerId: 1000,
        routerLink: 'masters',
        src: './assets/sidebar/reports/report.png',
        srcDark: './assets/sidebar/reports/reportDark.png',
        label: 'Reports',
        items: [
            {
                routerId: 1000,
                routerLink: 'setup/list',
                label: 'Rates'
            },
            {
                routerId: 1000,
                routerLink: 'setup/list',
                label: 'Users'
            },
            {
                routerId: 1000,
                routerLink: 'setup/list',
                label: 'Product'
            },
        ]
    },

];