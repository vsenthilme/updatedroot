import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class CourierPartnerService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get(courierPartnerId: string) {
    return this.http.get<any>(
      '/overc-idmaster-service/courierPartner/' + courierPartnerId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/courierPartner', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>(
      '/overc-idmaster-service/courierPartner/' + obj.courierPartnerId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(courierPartnerId: string) {
    return this.http.delete<any>(
      '/overc-idmaster-service/courierPartner/' + courierPartnerId + '?companyId=' + this.auth.companyId + '&languageId=' + this.auth.languageId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/courierPartner/find', obj);
  }

}
