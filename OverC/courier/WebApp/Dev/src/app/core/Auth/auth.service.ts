import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { CommonServiceService } from '../../common-service/common-service.service';
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { environment } from '../../../environments/environment';
import { UserRoleService } from '../../main/id-masters/user-role/user-role.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  redirectUrl: any;
  user: any;
  userData!: boolean;
  token!: string;
  // Subscription
  sub = new Subscription();
  constructor(
    private http: HttpClient,
    private router: Router,
    private spin: NgxSpinnerService,
    private cs: CommonServiceService,
  ) {
    this.userData = true;
  }

  get isLoggedIn() {
    return sessionStorage.getItem("user") ? true : false;
  }
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
    if (!this.isLoggedIn)
      this.logout();

  }
  isMenudata() {
    if (!this.isMenu)
      this.logout();
  }


logout(){
  this.router.navigate(['']);
  sessionStorage.clear();
 }

ngOnDestroy(){
  
}

  login(user : any) {
    sessionStorage.clear();
    localStorage.clear();
    return new Promise((resolve, reject) => {
      this.spin.show();
      this.sub.add(
        this.http.get<any>(`/overc-idmaster-service/login?userID=${user.userName}&password=${user.password}`).subscribe({next: (res) =>{
          this.http.get<any>(`/overc-idmaster-service/roleAccess?roleId=${res.userRoleId}&companyId=${res.companyId}&languageId=${res.languageId}`).subscribe({next: (resuser) =>{
            sessionStorage.setItem("user", JSON.stringify(res));
            sessionStorage.setItem('menu', JSON.stringify(resuser));
            let array1 = this.cs.removeDuplicatesFromArrayList(resuser, 'moduleId');
            sessionStorage.setItem('module', JSON.stringify(array1));
            this.router.navigate(['/main/airport/preAlertManifest']);
            this.spin.hide();
          }})
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }}) 
      );
    });
  }

 
  refreshToken(apiName: any) {
    return this.http.post<any>('/auth-token', {
      clientId: "pixeltrice",
      clientSecretKey: "pixeltrice-secret-key",
      grantType: "password",
      oauthPassword: "overc",
      oauthUserName: "overc",
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
    if (sessionStorage.getItem("user"))
     return JSON.parse(sessionStorage.getItem("user") as '{}').userId;
  }
  get userName() {
    if (sessionStorage.getItem("user"))
     return JSON.parse(sessionStorage.getItem("user") as '{}').userName;
  }
  get companyId() {
    if (sessionStorage.getItem("user"))
      return JSON.parse(sessionStorage.getItem("user") as '{}').companyId;
  }
  get companyName() {
    if (sessionStorage.getItem("user"))
      return JSON.parse(sessionStorage.getItem("user") as '{}').companyIdAndDescription;
  }
  get languageId() {
    if (sessionStorage.getItem("user"))
    return JSON.parse(sessionStorage.getItem("user") as '{}').languageId;
  }
  get partnerId() {
    if (sessionStorage.getItem("user"))
     return JSON.parse(sessionStorage.getItem("user") as '{}').partnerId;
  }


  message: any = null;
  requestPermission(res:any) {
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
      console.log('An error occurred while retrieving token. ', err);
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

  saveTokenToTable(currentToken:any, result:any) {
    let obj: any = {};
    obj.companyId = result.companyId;
    obj.isLoggedIn = true;
    obj.languageId = result.languageId;
    obj.tokenId = currentToken;
    obj.userId = result.userId;
    obj.deviceId = currentToken;

    this.http.post<any>(`/overc-idmaster-service/hhtnotification/createnotification`, obj).subscribe(res => {
    },(err) => {
      this.cs.commonerrorNew(err);
    })
  }
}
