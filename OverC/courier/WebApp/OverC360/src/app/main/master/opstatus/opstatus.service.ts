import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class OpstatusService {

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  Get(obj: any) {
    return this.http.get<any>('/overc-idmaster-service/opStatus/' + obj.statusCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId );
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/opStatus', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/opStatus/' + obj.statusCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/opStatus/' + obj.statusCode + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId );
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/opStatus/find', obj);
  }
}
