import { AuthService } from './Auth/auth.service';
import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpParams,
    HttpHeaders
} from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { BackupService } from './Auth/backup.service';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private auth: AuthService) { }
    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
    intercept(
        request: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {

        return next.handle(request).pipe(catchError(error => {

            if (error.status === 401 || error.status === 400) {

                if(request.url.includes('/doc-storage/multiUpload')){
                    return throwError(error);
                }
                if(request.url.includes('/consignment/upload/v2')){
                    return throwError(error);
                }
                if(request.url.includes('/pdf/extract')){
                    return throwError(error);
                }
                if(request.url.includes('pdf/merge')){
                    return throwError(error);
                }
                if(request.url.includes('preAlert/upload')){
                    return throwError(error);
                }
                if(request.url.includes('download')){
                    return throwError(error);
                }
                return this.handle401Error(request, next, error);
            }
            return throwError(error);
        }));
    }



    private handle401Error(request: HttpRequest<any>, next: HttpHandler, error: any) {


        this.isRefreshing = true;
        this.refreshTokenSubject.next(null);
        ;
        const token = this.auth.token;

        let apiName = "";
        apiName = request.url.split('/')[4];
        return this.auth.refreshToken(apiName).pipe(
            switchMap((token: any) => {

                this.auth.saveToken(token.access_token, apiName);
                this.refreshTokenSubject.next(token.access_token);


                return next.handle(this.addTokenHeader(request, token.access_token));
            }),
            catchError((err) => {

                if (err.statusc == 401) {

                    this.auth.logout();
                }
                return throwError(err);
            })
        );
    }
    private addTokenHeader(request: HttpRequest<any>, token: string) {

        if (request.url.includes('wms-idmaster-service/login') || request.url.includes('wms-idmaster-service/docchecklist/findDocCheckList'))
            return request.clone({ params: new HttpParams().append("authToken", token) });

        else {
            return request.clone({ params: new HttpParams().append("authToken", token).append('loginUserID', this.auth.userID) });
        }
    }

}


