import { Subscription } from "rxjs";
import { Injectable, OnDestroy } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Router } from "@angular/router";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { catchError, switchMap } from "rxjs/operators";
import { CommonService } from "src/app/common-service/common-service.service";
import { UserroleService } from "src/app/main-module/userman/userrole/userrole.service";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { environment } from 'src/environments/environment';

            // Initialize Firebase and retrieve the FCM token
import { initializeApp } from 'firebase/app';
//import { WebSocketAPIService } from "src/app/WebSocketAPIService";
@Injectable()
export class AuthService implements OnDestroy {
  redirectUrl: any;
  user: any;
  userData!: boolean;
  token!: string;
  // Subscription
  sub = new Subscription();
 // webSocketAPI: WebSocketAPIService = new WebSocketAPIService();
  constructor(
    private http: HttpClient,
    private router: Router,
    private spin: NgxSpinnerService,
    public toastr: ToastrService,
    public cs: CommonService,
    private userrole: UserroleService

  ) {

    this.userData = true;


    // if (JSON.parse(sessionStorage.getItem("user") as '{}')) {
    //   this.userData = true;
    // }
  }

  get portalLoggedIn() {
    return this.userData ? true : false;
  }

  login(user: { userName: string; password: any; }) {
    sessionStorage.clear();
    this.spin.show();

    return new Promise((resolve, reject) => {
      let obj: any = {};
      obj.userId = user.userName;
      obj.loginPassword = user.password;

      this.http.post<any>('/wms-idmaster-service/login/v2', obj).subscribe((res) => {
            this.spin.hide();
            sessionStorage.setItem("user", JSON.stringify(res.users[0]))
           /// this.sub.add(this.userrole.Get(res.userRoleId, res.warehouseId, res.companyCode, res.plantId, res.languageId).subscribe(resuser => {
            sessionStorage.setItem('menu', JSON.stringify(res.userRole));
            let array1 = this.cs.removeDuplicatesFromArrayList(res.userModule, 'moduleId');
            sessionStorage.setItem('module', JSON.stringify(array1));
              // if (res.portalLoggedIn == true && res.userTypeId != 1) {
              //   this.toastr.error(
              //     "User already logged in",
              //     "Notification", {
              //     timeOut: 2000,
              //     progressBar: false,
              //   }
              //   );
              //   this.spin.hide();
              //   return;
              // } else {
              //   this.http.patch<any>(`/wms-idmaster-service/usermanagement/${res.userId}?companyCode=${res.companyCode}&languageId=${res.languageId}&plantId=${res.plantId}&warehouseId=${res.warehouseId}&userRoleId=${res.userRoleId}`, { portalLoggedIn: true, companyCode: res.companyCode, plantId: res.plantId, languageId: res.languageId, userRoleId: res.userRoleId }).subscribe(userres => {
              //   });
              // }


              this.router.navigate(["/main/dashboard/landingPage"]);
              this.requestPermission(res);
            // }, err => {
            //   this.cs.commonerrorNew(err);
            //   this.spin.hide();
            // }));
          }
          ,
          (rej) => {
            this.spin.hide();
            this.toastr.error(rej.error.error, 'Notification');
            resolve(false);
            setTimeout(() => {
              window.location.reload();
            }, 1000);
          }
        )
    });







  }
  login1(user: { userName: string; password: any; }, authResult) {
    sessionStorage.clear();
    this.spin.show();
    return new Promise((resolve, reject) => {
      let obj: any = {};
    obj.userId = user.userName;
    obj.loginPassword = user.password;
    obj.plantId = authResult.plantId;  
    
  this.http.post<any>('/wms-idmaster-service/login/v2', obj).subscribe((res) => {
       
            sessionStorage.setItem("user", JSON.stringify(res.users[0]))
       //     this.sub.add(this.userrole.Get(authResult.userRoleId, authResult.warehouseId, authResult.companyCode, authResult.plantId, authResult.languageId).subscribe(resuser => {
              sessionStorage.setItem('menu', JSON.stringify(res.userRole));
              let array1 = this.cs.removeDuplicatesFromArrayList(res.userModule, 'moduleId');
              sessionStorage.setItem('module', JSON.stringify(array1));
              // if (authResult.portalLoggedIn == true && res.userTypeId != 1) {
              //   this.toastr.error(
              //     "User already logged in",
              //     "Notification", {
              //     timeOut: 2000,
              //     progressBar: false,
              //   }
              //   );
              //   this.spin.hide();
              //   return;
              // }
              // else {
              //   this.http.patch<any>(`/wms-idmaster-service/usermanagement/${authResult.userId}?companyCode=${authResult.companyCode}&languageId=${authResult.languageId}&plantId=${authResult.plantId}&warehouseId=${authResult.warehouseId}&userRoleId=${authResult.userRoleId}`, { portalLoggedIn: true, companyCode: authResult.companyCode, plantId: authResult.plantId, languageId: authResult.languageId, userRoleId: authResult.userRoleId }).subscribe(userres => {
              //   });
              // }


              // let resp: any = res.result.response[0];
              // // For token
              // sessionStorage.setItem("token", res.result.bearerToken);
              // sessionStorage.setItem("token", res.result.bearerToken);
              // if(res.userTypeId == 6){
              //   this.router.navigate(["/main/reports/report-list"]); 
              // }else{
              //   this.router.navigate(["/main/dashboard/landingPage"]);
              // }
              this.spin.hide();
              this.router.navigate(["/main/dashboard/landingPage"]);


              this.requestPermission(res);
              this.listen();
            // }, err => {
            //   this.cs.commonerrorNew(err);
            //   this.spin.hide();
            // }));
          }
          ,
          (rej) => {
            this.spin.hide();
            this.toastr.error(rej.error.error, 'Notification');
            resolve(false);
            setTimeout(() => {
              window.location.reload();
            }, 1000);
          }
        )
    });







  }
  // RefreshToken() {
  //   var use2r = {
  //     clientId: "pixeltrice",
  //     clientSecretKey: "pixeltrice-secret-key",
  //     grantType: "password",
  //     oauthPassword: "welcome",
  //     oauthUserName: "muru"
  //   }


  //   this.http.post<any>(`/auth-token`, use2r).subscribe(
  //     (res) => {
  //       ;
  //       this.spin.hide();
  //       // let resp: any = res.result.response[0];
  //       // // For token
  //       // sessionStorage.setItem("token", res.result.bearerToken);
  //       // sessionStorage.setItem("token", res.result.bearerToken);
  //       sessionStorage.setItem('token', res.access_token);
  //       this.token = res.access_token;
  //       var token = res.access_token;
  //       return this.token;
  //     }
  //     ,
  //     (rej) => {
  //       this.spin.hide();
  //       // this.toastr.error(rej.result.error_description, "Error");
  //     }
  //   );
  // }

  refreshToken(apiName: string) {
    return this.http.post<any>('/auth-token', {
      clientId: "pixeltrice",
      clientSecretKey: "pixeltrice-secret-key",
      grantType: "password",
      oauthPassword: "wms",
      oauthUserName: "wms",
      apiName: apiName
    });
  }
  public saveToken(token: any, apiName: string): void {
    window.sessionStorage.removeItem(apiName);
    window.sessionStorage.setItem(apiName, token);
  }
  public saveTokenfrom(token: any, apiName: string): void {
    window.sessionStorage.removeItem(apiName);
    window.sessionStorage.setItem(apiName, token.access_token);
  }

  public getToken(apiName: string): string | null {
    return window.sessionStorage.getItem(apiName);
  }
  get userID() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').userId;
    //return JSON.parse(sessionStorage.getItem("user") as '{}').username;
  }
  get plantId() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').plantId;
    //return JSON.parse(sessionStorage.getItem("user") as '{}').username;
  }
  get userRoleId() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').userRoleId;
  }
  get warehouseId() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').warehouseId;
    //return JSON.parse(sessionStorage.getItem("user") as '{}').username;
  }
  get companyId() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').companyCode;
    //return JSON.parse(sessionStorage.getItem("user") as '{}').username;
  }
  get languageId() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').languageId;
    //return JSON.parse(sessionStorage.getItem("user") as '{}').username;
  }
  get username() {
    return JSON.parse(sessionStorage.getItem("user") as '{}')
      ? JSON.parse(sessionStorage.getItem("user") as '{}').username
      : null;


  }

  get userType() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').userType;
  }

  get userTypeId() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').userTypeId;
  }

  get warehouseType() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').warehouseType;
  }

  get warehouseTypeName() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').warehouseTypeName;
  }

  get warehouseList() {
    const warehouseList = JSON.parse(sessionStorage.getItem("user") as '{}')
      .warehouseList;
    if (warehouseList) {
      return warehouseList;
    } else {
      return [];
    }
  }

  get company() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').company;
  }

  get companyCurrency() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').companyCurrency;
  }

  get companyIdAndDescription() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').companyIdAndDescription;
  }

  get plantIdAndDescription() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').plantIdAndDescription;
  }
  get warehouseIdAndDescription() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').warehouseIdAndDescription;
  }

  get isAdmin() {
    if (
      this.userType == "SuperAdmin" &&
      this.userID == "1"
    ) {
      return true;
    }
    return false;
  }

  get accessJson() {
    return JSON.parse(sessionStorage.getItem("user") as '{}').accessJson;
  }

  logout() {


    //  sessionStorage.clear();
    // this.userData = false;
    // const data = {
    //   userName: [this.username],
    //   requestType: "Logout",
    // };
    this.spin.show();
    this.router.navigate(["/"]);
    window.sessionStorage.clear();
    sessionStorage.clear();
    setTimeout(() => {
      window.location.reload();
    }, 200);
    this.spin.hide();
    // this.http.post<any>(`Login/Logout`, data).subscribe(
    //   (res) => {

    //     this.router.navigate(["/"]);
    //     this.spin.hide();
    //   },
    //   (err) => {
    //     this.userData = false;
    //     this.router.navigate(["/"]);
    //     this.spin.hide();
    //   }
    // );
  }

  ngOnDestroy() {
    if (this.sub != null) {
      this.sub.unsubscribe();
    }
  }
  // get dateformat() {
  //   if (this.userData) {
  //     return this.company.dateformat;
  //   }
  //   return "";
  // }


  get isMenu() {
    return sessionStorage.getItem("menu") ? true : false;
  }
  get MenuData() {
    return JSON.parse(sessionStorage.getItem("menu") as '[]')
  }
  get ModuleData() {
    return JSON.parse(sessionStorage.getItem("module") as '[]')
  }
  isuserdata() {
    if (!this.portalLoggedIn)
      this.logout();

  }
  isMenudata() {
    if (!this.isMenu)
      this.logout();

  }

  getRoleAccess(id: any) {

    // debugger
    this.isuserdata();
    this.isMenudata();
    let fileterdata = this.MenuData.filter((x: any) => x.subMenuId == id && (x.view || x.delete || x.createUpdate));
    if (fileterdata.length > 0) {
      return fileterdata[0];
    }
    else {
      this.toastr.error("You Don't have access to the screen", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
    }
  }



  message: any = null;
  requestPermission1(res) {
    const messaging = getMessaging();
    getToken(messaging, { vapidKey: environment.firebase.vapidKey }).then((currentToken) => {
      if (currentToken) {
        console.log("Hurraaa!!! we got the token.....")
        console.log(currentToken);
        this.saveTokenToTable(currentToken, res);
      } else {
        // Show permission request UI
        console.log('No registration token available. Request permission to generate one.');
        // ...
      }
    }).catch((err) => {
      console.log( err);
      // ...
    });

  }
  listen() {
    const messaging = getMessaging();
    onMessage(messaging, (payload) => {
      console.log('Message received. ', payload);
      this.message = payload;
    });
  }


  requestPermission(res) {
    if ('serviceWorker' in navigator && 'Notification' in window) {
      navigator.serviceWorker.register('/firebase-messaging-sw.js')
        .then((registration) => {
          console.log('ServiceWorker registration successful with scope: ', registration.scope);
          
          // Request permission to receive notifications
          return Notification.requestPermission();
        })
        .then((permission):any => {
          if (permission === 'granted') {
            console.log('Notification permission granted.');
  
            const firebaseConfig = {
              apiKey: "AIzaSyCrdO9_MMIvKVaIHT4E1yoUGP3U7xH0FUE",
              authDomain: "almailem-29a5e.firebaseapp.com",
              projectId: "almailem-29a5e",
              storageBucket: "almailem-29a5e.appspot.com",
              messagingSenderId: "930706163709",
              appId: "1:930706163709:web:7d399a955c6d1716d93b94",
              measurementId: "G-29P8C25FRN",
              vapidKey: 'BLy2-JA9Xo99YgTCTRz6T9sM-Wb7PZs0TH29pk91F1DIKyezeorGOsAMopBs5FZYEdJPQydPlsTAEGwb0QDPdrk'
            };
  
            // Initialize Firebase
            const app = initializeApp(environment.firebase);
            const messaging = getMessaging(app);
  
            return getToken(messaging, { vapidKey: 'BLy2-JA9Xo99YgTCTRz6T9sM-Wb7PZs0TH29pk91F1DIKyezeorGOsAMopBs5FZYEdJPQydPlsTAEGwb0QDPdrk' });
          } else {
            console.log('Notification permission denied.');
          }
        })
        .then((currentToken) => {
          if (currentToken) {
            console.log('FCM Token:', currentToken);
            window.sessionStorage.setItem('Firebase', currentToken);
            this.saveTokenToTable(currentToken, res);

            // Send the token to your server or save it for later use
          } else {
            console.log('No registration token available. Request permission to generate one.');
          }
        })
        .catch((err) => {
          console.log('An error occurred: ', err);
        });
    } else {
      console.log('Service Workers or Notifications are not supported in this browser.');
    }
  }
  
  

  saveTokenToTable(currentToken, result) {
    let obj: any = {};
    obj.companyId = result.companyCode;
    obj.plantId = result.plantId;
    obj.warehouseId = result.warehouseId;
    obj.isLoggedIn = true;
    obj.languageId = result.languageId;
    obj.tokenId = currentToken;
    obj.userId = result.userId;
    obj.deviceId = currentToken;

    this.http.post<any>(`/wms-idmaster-service/hhtnotification/createnotification`, obj).subscribe(res => {
    },(err) => {
      this.cs.commonerrorNew(err);
    })
  }
}
