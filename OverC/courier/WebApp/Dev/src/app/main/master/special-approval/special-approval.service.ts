import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class SpecialApprovalService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(specialApprovalId: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/specialApproval/' + specialApprovalId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/specialApproval', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/specialApproval/' + obj.specialApprovalId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(specialApprovalId: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/specialApproval/' + specialApprovalId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/specialApproval/' + 'find', obj);
  }

}
