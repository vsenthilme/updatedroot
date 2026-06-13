import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class HubService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(hubCode: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/hub/' + hubCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/hub', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/hub/' + obj.hubCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(hubCode: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/hub/' + hubCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/hub/' + 'find', obj);
  }
}
