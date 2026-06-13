import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/core';

@Injectable({
  providedIn: 'root'
})
export class ProvinceService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  Get() {

  }

  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/province', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/province/' + obj.provinceId + '?companyId=' + this.auth.companyId + '&countryId=' + obj.countryId + '&languageId=' + this.auth.languageId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/province/' + obj.provinceId + '?companyId=' + this.auth.companyId + '&countryId=' + obj.countryId + '&languageId=' + this.auth.languageId );
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/province/find', obj);
  }

}
