import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class AppUserService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(appUserId: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/appUser/' + appUserId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/appUser', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/appUser/' + obj.appUserId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(appUserId: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/appUser/' + appUserId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/appUser/' + 'find', obj);
  }

}
