import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class HsCodeService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(hsCode: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/hsCode/' + hsCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/hsCode', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/hsCode/' + obj.hsCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(hsCode: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/hsCode/' + hsCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/hsCode/' + 'find', obj);
  }

}
