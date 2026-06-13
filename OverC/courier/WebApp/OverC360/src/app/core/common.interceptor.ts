    import { Injectable, inject } from '@angular/core';
    import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpParams } from '@angular/common/http';
    import { Observable } from 'rxjs';
    import { AuthService } from './core';
import { environment } from '../../environments/environment';

    @Injectable()
    export class CommonInterceptor implements HttpInterceptor {
    
    constructor(private auth: AuthService,) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq;
        let url = environment.apiURL;

        const bearerToken = this.auth.getToken(req.url.split('/')[1]);
    
        if (bearerToken) {
          authReq = req.clone({
            url: `${url}${req.url}`,
            params: new HttpParams().set("authToken", bearerToken).set('loginUserID', this.auth.userID)
          });
        }
        else {
          authReq = req.clone({
            url: `${url}${req.url}`
          });
        }
        return next.handle(authReq);
      }
    }
