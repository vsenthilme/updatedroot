import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './core';

@Injectable()
export class CommonInterceptor implements HttpInterceptor {
  cur: any;
  constructor(private authService: AuthService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq;
    let url = environment.apiURL;
    const bearerToken = this.authService.getToken(req.url.split('/')[1]);//sessionStorage.getItem('token');
    
    if (bearerToken) {

      if (req.url.split('/')[1] == 'wms-idmaster-service' && req.url.split('/')[2] == 'company' && req.url.split('/').length == 4 && req.method == 'DELETE') {
        
        authReq = req.clone({
          url: `${url}${req.url}`,
          params: new HttpParams().set('loginUserID', this.authService.userID).set('companyId', req.url.split('/')[3])      //.set("authToken", bearerToken)
        });

      }
      if (req.url.split('/')[1] == 'wms-idmaster-service' && req.url.split('/')[2] == "doc-storage" &&  req.method == 'POST') {
        
        authReq = req.clone({
          url: `${url}${req.url}`,
         params: new HttpParams()
        });

      }

      else if (req.url.split('/')[1] == 'wms-idmaster-service' && req.url.split('/')[2] == 'company' && req.url.split('/').length == 4 && req.method == 'GET') {
      
        authReq = req.clone({
          url: `${url}${req.url}`,
          params: new HttpParams().set('companyId', req.url.split('/')[3]) //.set("authToken", bearerToken)
        });

      }
      else {
        authReq = req.clone({
          headers: req.headers.set('Content-Type', 'application/json').set('Accept', 'application/json'),
          url: `${url}${req.url}`,
          params: new HttpParams().set('loginUserID', this.authService.userID)        //.set("authToken", bearerToken)
        });

      }
    }
    if (req.url.includes('/wms-idmaster-service/login')){
      authReq = req.clone({
        url: `${url}${req.url}`,
      });
    }
    else {
      authReq = req.clone({
        headers: req.headers.set('Content-Type', 'application/json').set('Accept', 'application/json'),
        url: `${url}${req.url}`,        
        params: new HttpParams().set('loginUserID', this.authService.userID)
      });
      // else
      // {
      //   if(req.url.split('/')[1] == 'wms-enterprise-service' && req.method == 'POST')
      //   {
      //     let user = JSON.parse(sessionStorage.getItem("user") || '{}');
      //     authReq = req.clone({
      //       // setHeaders: { MyHeader: `${regcompany}` },
      //       // setHeaders: { Authorization: `${bearerToken}` },
      //       headers: req.headers.set('Content-Type', 'application/json').set('Accept', 'application/json'),
      //       url: `${this.url}${req.url}`,
      //       params: new HttpParams().set("authToken", bearerToken ? bearerToken : '').append("loginUserID", user.username ? user.username : '')
      //     });
      //   }
      //   else
      //   {
      //     authReq = req.clone({
      //       // setHeaders: { MyHeader: `${regcompany}` },
      //       // setHeaders: { Authorization: `${bearerToken}` },
      //       headers: req.headers.set('Content-Type', 'application/json').set('Accept', 'application/json'),
      //       url: `${this.url}${req.url}`,
      //       params: new HttpParams().set("authToken", bearerToken ? bearerToken : '')
      //     });
      //   }

    }
    return next.handle(authReq);
  }
}
