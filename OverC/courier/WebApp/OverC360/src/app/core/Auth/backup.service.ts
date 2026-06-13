import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackupService {

  redirectUrl: any;
  user: any;
  userData!: boolean;
  token!: string;
  // Subscription
  sub = new Subscription();
  constructor(
    private http: HttpClient,
    private router: Router,
    //private headerComponent: HeaderComponent,
//  /   private email: LoginComponent,

  ) {

    this.userData = true;
    //this.isuserdata();


    // if (JSON.parse(sessionStorage.getItem("user") as '{}')) {
    //   this.userData = true;
    // }
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
  isuserdata() {
    if (!this.isLoggedIn)
      this.logout();

  }
  isMenudata() {
    if (!this.isMenu)
      this.logout();

  }


logout(){
  
}

ngOnDestroy(){
  
}

login1(a: any){
}
  login(user: { userName: string; password: any; }) {
    sessionStorage.clear();
    localStorage.clear();
    // this.router.navigate(["main/dashboard"]);
    return new Promise((resolve, reject) => {
      this.sub.add(
        this.http.get<any>(`/wms-idmaster-service/login?userId=${user.userName}&password=${user.password}`).subscribe(
          (res) => {
          }
          ,
          (rej) => {
            resolve(false);
          }
        )
      );
    });
  }

 
  refreshToken(apiName: string) {
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
    return 'raj';
    // return JSON.parse(sessionStorage.getItem("user") as '{}').userId;
  }
 
}
