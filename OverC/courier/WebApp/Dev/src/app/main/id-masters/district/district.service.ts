import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../../../core/Auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class DistrictService {

  constructor(private http: HttpClient, private auth: AuthService) { }


  Get(districtId: string) {
    return this.http.get<any>('/overc-idmaster-service/district/' + districtId);
  }
 
  Create(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/district', obj);
  }

  Update(obj: any) {
    return this.http.patch<any>('/overc-idmaster-service/district/'+ obj.districtId +'?languageId='+ this.auth.languageId 
      +'&companyId='+ this.auth.companyId + '&countryId='+ obj.countryId + '&provinceId='+ obj.provinceId, obj);
  }

  Delete(obj: any) {
    return this.http.delete<any>('/overc-idmaster-service/district/' + obj.districtId +'?languageId='+ this.auth.languageId +'&companyId='+ this.auth.companyId
      + '&countryId='+ obj.countryId + '&provinceId='+ obj.provinceId);
  }

  search(obj: any) {
    return this.http.post<any>('/overc-idmaster-service/district/find', obj);
  }
}


 