import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/Auth/auth.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RateService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(partnerId: string, rateParameterId: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/rate/' + partnerId + "?rateParameterId=" + rateParameterId + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/rate', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/rate/' + obj.partnerId + "?rateParameterId=" + obj.rateParameterId + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(partnerId: string, rateParameterId: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/rate/' + partnerId + "?rateParameterId=" + rateParameterId + '&companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/rate/' + 'find', obj);
  }

}
