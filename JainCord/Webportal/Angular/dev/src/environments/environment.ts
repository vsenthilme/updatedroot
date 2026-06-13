// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  title: 'CLASSIC WMS',
 // apiURL: 'https://d.classicwms.com:8186/classicwms',
 apiURL: 'https://d.classicwms.com:8186/classicwms',
  //apiURL: 'http://192.168.3.123:8086/classicwms',
  //apiURL: 'https://dev.classicwms.com:8086/classicwms',    
 //apiURL: 'https://qa.classicwms.com:8086/classicwms',
  OAUTH_CLIENT: 'pixeltrice',
  OAUTH_SECRET: 'pixeltrice-secret-key',
  name: 'dev',
  webScoketURL:'https://d.classicwms.com:8185/classicwms/api/wms-idmaster-service/',
  //webScoketURL:'http://35.154.84.178:7073/mnrclara/api/mnr-crm-service/',
  //webScoketURL:'https://mrclara.com:7073/mnrclara/api/mnr-crm-service/',


  

  firebase: {
    apiKey: "AIzaSyCrdO9_MMIvKVaIHT4E1yoUGP3U7xH0FUE",
    authDomain: "almailem-29a5e.firebaseapp.com",
    projectId: "almailem-29a5e",
    storageBucket: "almailem-29a5e.appspot.com",
    messagingSenderId: "930706163709",
    appId: "1:930706163709:web:7d399a955c6d1716d93b94",
    measurementId: "G-29P8C25FRN",
    vapidKey: 'BLy2-JA9Xo99YgTCTRz6T9sM-Wb7PZs0TH29pk91F1DIKyezeorGOsAMopBs5FZYEdJPQydPlsTAEGwb0QDPdrk'
  },
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
